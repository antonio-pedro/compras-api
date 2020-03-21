package com.pessoal.compras.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pessoal.compras.event.RecursoCriadoEvent;
import com.pessoal.compras.exception.PessoaInexistenteOuInativaException;
import com.pessoal.compras.exceptionHandler.CompraExceptionHandler.Erro;
import com.pessoal.compras.model.Compra;
import com.pessoal.compras.repository.CompraRepository;
import com.pessoal.compras.repository.filter.CompraFilter;
import com.pessoal.compras.service.CompraService;


@RestController
@RequestMapping("/compras")
public class CompraResource {
	
	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private CompraService compraService;

	// Publicador de evento de aplicação lança um evento
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping
	public Page<Compra> pesquisar(CompraFilter compraFilter, Pageable pageable) {		
		return compraRepository.filtrar(compraFilter, pageable);
	}

	@PostMapping
	public ResponseEntity<Compra> criar(@Valid @RequestBody Compra compra, HttpServletResponse response) {
		Compra compraSalva = compraService.criar(compra);

		// Lança o evento para adicionar header Location ou seja a localização do recurso criado
		publisher.publishEvent(new RecursoCriadoEvent(this, response, compraSalva.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(compraSalva);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Compra> atualizar(@PathVariable Long id, @Valid @RequestBody Compra compra) {
		Compra comprasAtualizada = compraService.atualizar(id, compra);
		
		return ResponseEntity.ok(comprasAtualizada);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)//retorna 204 deletado mas sem conteúdo
	public void deletar(@PathVariable Long id) {
		compraService.deletar(id);
	}

	@GetMapping("/{id}")
	public Compra buscarPeloId(@PathVariable Long id) {
		Compra comprasBuscadaPeloId = compraService.buscarComprasPeloId(id);
		return comprasBuscadaPeloId;
	}
	
	@ExceptionHandler({ PessoaInexistenteOuInativaException.class })
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex){
		String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}	

}
