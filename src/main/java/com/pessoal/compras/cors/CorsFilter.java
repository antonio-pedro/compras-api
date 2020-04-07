package com.pessoal.compras.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.pessoal.compras.config.property.ComprasApiProperty;

@Component                          //Fala para ser um componente do spring
@Order(Ordered.HIGHEST_PRECEDENCE)  //Indica uma prioridade alta para ser processado
public class CorsFilter implements Filter {
	
	@Autowired
	private ComprasApiProperty comprasApiProperty;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		response.setHeader("Access-Control-Allow-Origin", comprasApiProperty.getOriginPermitida());
        response.setHeader("Access-Control-Allow-Credentials", "true");
		
        //Se for um Options entra neste método e adiciona headers na requisição
		if ("OPTIONS".equals(request.getMethod()) && comprasApiProperty.getOriginPermitida().equals(request.getHeader("Origin"))) {
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");    //métodos permitidos
        	response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");//cabeçalhos permitidos
        	response.setHeader("Access-Control-Max-Age", "3600");                                     //1 hora depois que o brouse fará a próxima requisição
			
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, resp);
		}	
	}
}
