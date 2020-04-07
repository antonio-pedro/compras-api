package com.pessoal.compras.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.pessoal.compras.config.property.ComprasApiProperty;

@ControllerAdvice //É um controlador avançado que irá um pouco antes do retorno para a aplicação tira o refreshToken do corpo e coloca no cookie
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> { //Objeto de retorno do Oauth (corpo com token e refreshToken)
	
	@Autowired
	private ComprasApiProperty comprasApiProperty;

	@Override	//Este método serve para verificar se é a hora de mexer no corpo da requisição que devolve o token e o refreshToken
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getMethod().getName().equals("postAccessToken");
	}

	@Override
	//Este método é chamado se o nome acima for postAccessToken
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		
		HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();     //Convertendo a Requisição
		HttpServletResponse resp = ((ServletServerHttpResponse) response).getServletResponse();//Convertendo a Resposta
		
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;//Converter o body para retirar o token do corpo da requisição
		
		String refreshToken = body.getRefreshToken().getValue(); //Aqui eu tiro o refreshToken do corpo da requisição
		adicionarRefreshTokenNoCookie(refreshToken, req, resp);  //Adicionar o refreshToken no cookie
		removerRefreshTokenDoBody(token);
		
		return body;
	}

	private void removerRefreshTokenDoBody(DefaultOAuth2AccessToken token) {
		token.setRefreshToken(null);//Seto o refreshToken para null de forma ela não ficar mais no corpo da requisição
	}

	private void adicionarRefreshTokenNoCookie(String refreshToken, HttpServletRequest req, HttpServletResponse resp) {
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);     //Criar um cookie
		refreshTokenCookie.setHttpOnly(true);//Só existir em http
		refreshTokenCookie.setSecure(comprasApiProperty.getSeguranca().isEnableHttps()); // TODO: Mudar para true em producao (HTTPS)
		refreshTokenCookie.setPath(req.getContextPath() + "/oauth/token");//Se tiver alguma requisição para o path
		refreshTokenCookie.setMaxAge(2592000); //tempo que o cookie vai expirar(30 dias)
		resp.addCookie(refreshTokenCookie);//adiciono o cookie na resposta
	}

}
