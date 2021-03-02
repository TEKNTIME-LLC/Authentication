package com.tekntime.mfa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.tekntime.mfa.repository")
public class MFAApplication extends SpringBootServletInitializer {
//public class MFAApplication {

    public static void main(String[] args) {
        SpringApplication.run(MFAApplication.class, args);
    }


}