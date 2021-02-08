package com.tekntime.oauth.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;
import com.tekntime.oauth.server.controller.OAuthController;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class Swagger {                                    
	private static final Logger logger   = LoggerFactory.getLogger(Swagger.class);	

	@Bean
	public Docket api() {
		logger.info("Swagger initializing ....");	
		return new Docket(DocumentationType.SWAGGER_2).select()
	.apis(RequestHandlerSelectors.basePackage("com.tekntime.oauth.server.controller"))
	.paths(PathSelectors.any()).build();
	}
}
