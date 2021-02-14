package com.tekntime.jwt.authorization.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tekntime.jwt.authorization.model.TekntimeUserDetails;
import com.tekntime.jwt.authorization.repository.UserRepository;

@Service
public class TekntimeUserDetailsService implements UserDetailsService{
	private static final Logger logger   = LoggerFactory.getLogger(TekntimeUserDetailsService.class);	


	@Autowired
	private UserRepository userdao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    TekntimeUserDetails user =userdao.findByUsername(username);
		if(user !=null ) {
			return user;
		} else {
			logger.error("User not found with username: " + username);
			throw new UsernameNotFoundException("User not found with username: " + username);
	   }
	}
}


