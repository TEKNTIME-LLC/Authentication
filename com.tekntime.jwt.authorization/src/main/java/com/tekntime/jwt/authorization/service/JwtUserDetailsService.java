package com.tekntime.jwt.authorization.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tekntime.jwt.authorization.model.UserLogin;

@Service
public class JwtUserDetailsService {
	private static final Logger logger   = LoggerFactory.getLogger(JwtUserDetailsService.class);	
	
	
	public UserLogin loadUserByLoginName(String username){
		UserLogin user=new UserLogin();
		// passord is =password and encrpted by BCrypt
			if ("tekntime".equalsIgnoreCase(username)) {
				user.setLoginName("tekntime");
				user.setToken("$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6");
				//return new User("tekntime", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",	new ArrayList<>());
				return user;
			} else {
				logger.error("User not found with username: " + username);
				throw new UsernameNotFoundException("User not found with username: " + username);
			}
		}
}


