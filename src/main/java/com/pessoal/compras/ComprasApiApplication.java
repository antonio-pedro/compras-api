package com.pessoal.compras;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.pessoal.compras.config.property.ComprasApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(ComprasApiProperty.class)
public class ComprasApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComprasApiApplication.class, args);
	}

}
