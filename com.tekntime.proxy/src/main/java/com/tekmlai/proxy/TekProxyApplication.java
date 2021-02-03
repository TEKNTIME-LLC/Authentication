package com.tekmlai.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableConfigurationProperties
@RestController
public class TekProxyApplication { //can not be deployed as WAR

	public static void main(String[] args) {
		SpringApplication.run(TekProxyApplication.class, args);
	}

}

