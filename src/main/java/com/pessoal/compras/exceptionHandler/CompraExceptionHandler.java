package com.pessoal.compras.exceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//capituram exceções de respostas de entidades
@ControllerAdvice // esta anotação faz esta classe ser um controlador de toda a aplicação
public class CompraExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	//Objeto do spring(componente do Spring) que informa as mensagens da aplicação
	private MessageSource messageSource;
	
	@Override
	//(handleHttpMessageNotReadable)capitura as mensagens quando tentar salvar um objeto com atributos a mais
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		//parâmetros abaixo são três sendo eles("mensagem.invalida"= código do erro referenciado no message.propertis, null, locale corrente da aplicação)
		String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	//handleMethodArgumentNotValid-> Valida quando um argumento não é válido ex: um nome Null que será checado com o @valid e o @notNull
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		//Quando ocorre erros em vários campos validados. Criar uma lista de erros para ser retornada 404 bad request (pedido ruim)
		List<Erro> erros = criarListaDeErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	//O método não é sobrescrito ele é criado para atender uma demanda específica 404 not found não tem a causa já é a exceção direto
	@ExceptionHandler({ EmptyResultDataAccessException.class }) //Exceção de acesso a dados de resultado vazio
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	//Quando o usuário envia uma atividade para salvar onde um ID de uma associação não existe
	@ExceptionHandler({DataIntegrityViolationException.class})
	protected ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
			 WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("recurso.operacao-nao-permitida", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	//Uma lista de erros para serem passados e usados por outros métodos (bindingResult é onde pega todos os erros)
	private List<Erro> criarListaDeErros(BindingResult bindingResult) {
		List<Erro> erros = new ArrayList<>();
		
		// abaixo devolve todos os erros que existirem nos campos do model que estão com @NotNull
		for (FieldError campoComErro : bindingResult.getFieldErrors()) {
			String mensagemUsuario = messageSource.getMessage(campoComErro, LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = campoComErro.toString();
			erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));//instancia a classe Erro passando para o construtor
		}
			
		return erros;
	}
	
	// A classe abaixo está sendo intanciada acima e recebendo dois parâmetros
	public static class Erro {
		
		private String mensagemUsuario;
		private String mensagemDesenvolvedor;
		
		public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}

		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;
		}
		
	}
	
}