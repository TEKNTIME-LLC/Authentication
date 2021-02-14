package com.tekntime.jwt.authorization;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class JWTAuthorizationApp extends SpringBootServletInitializer{
//public class JWTAuthorizationApp {
	public static void main(String[] args) {
		SpringApplication.run(JWTAuthorizationApp.class, args);
	}
}
