package com.tekntime.jwt.authorization.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService{
	private static final Logger logger   = LoggerFactory.getLogger(JwtUserDetailsService.class);	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// passord is =password and encrpted by BCrypt
			if ("tekntime".equalsIgnoreCase(username)) {
				return new User("tekntime", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
						new ArrayList<>());
			} else {
				logger.error("User not found with username: " + username);
				throw new UsernameNotFoundException("User not found with username: " + username);
			}
		}
}


