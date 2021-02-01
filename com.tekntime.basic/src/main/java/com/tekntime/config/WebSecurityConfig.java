package com.tekntime.config;


//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
//@PropertySource(value = "classpath:application.properties")
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//			.authorizeRequests()
//				.antMatchers("**/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll() // whitelist Swagger UI resources
//				.anyRequest().authenticated()
//				.and()
//			.formLogin()
//				.loginPage("/login").permitAll()
//				.and()
//			.logout()
//				.permitAll();
//	}
//
//}
