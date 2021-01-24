package com.tekntime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
public class BasicAuthApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(BasicAuthApplication.class, args);
	}

}
