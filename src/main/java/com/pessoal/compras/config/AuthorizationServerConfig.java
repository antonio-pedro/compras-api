package com.pessoal.compras.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.pessoal.compras.config.token.CustomTokenEnhancer;

@Profile("oauth-security")
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	//Cada cliente abaixo terá acesso para pegar um token no Oauth(Configurador de serviço de detalhes do cliente)
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient("clienteAngular")
				.secret("$2a$10$cNeCfTaZcg8p7IUJY/vaXe9.jVdirkx1XROhcU5gqlbyt7N.8y6V.") // admin
				.scopes("read", "write")//escopo do cliente Angular
				.authorizedGrantTypes("password", "refresh_token")//Tipo de concessão
				.accessTokenValiditySeconds(1800)//segundos de validade do token de acesso
				.refreshTokenValiditySeconds(3600 * 24)//segundos de validade do refreshToken (1 dia)
			.and()
				.withClient("mobile")
				.secret("$2a$10$lKQOPmVUilDeNq/z1UU5DO0PrZ.//M/POP7kZgcia8/T2ebyGenNi") // 24626538
				.scopes("read") //escopo do cliente Angular
				.authorizedGrantTypes("password", "refresh_token")
				.accessTokenValiditySeconds(1800)
				.refreshTokenValiditySeconds(3600 * 24);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
		
		endpoints
			.tokenStore(tokenStore())
			.tokenEnhancer(tokenEnhancerChain)
			.reuseRefreshTokens(false)     //Com esta propriedade o usuário sempre que estiver usando o sistema o refreshToken será renovado
			.userDetailsService(userDetailsService)
			.authenticationManager(authenticationManager);
	}
	
	@Bean  //conversor de accesstoken
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("compras");//aqui está a chave que valida o token lá no JWT que é a palavra secreta
		return accessTokenConverter;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
	@Bean //Aprimorador de token
	public TokenEnhancer tokenEnhancer() {
	    return new CustomTokenEnhancer();
	}
	
}
