package com.tekntime.oauth.server.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
    @Autowired
	private  DataSource dataSource;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
	private static final Logger logger   = LoggerFactory.getLogger(WebSecurityConfiguration.class);	


    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    	logger.info("WebSecurityConfiguration initializing with AuthenticationManagerBuilder....");	
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	logger.info("WebSecurityConfiguration initializing with HttpSecurity....");	
    	
        http
                .csrf().disable()
                .anonymous().disable()
                .authorizeRequests()
                .antMatchers("/api-docs/**").permitAll()
                .antMatchers("/**/*.html").permitAll()
                .antMatchers("/**/*.css").permitAll()
                .antMatchers("/**/*.js").permitAll()
                .antMatchers("/login").permitAll();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        if (userDetailsService == null) {
            userDetailsService = new JdbcDaoImpl();
            ((JdbcDaoImpl) userDetailsService).setDataSource(dataSource);
        }
        return userDetailsService;
    }

}
