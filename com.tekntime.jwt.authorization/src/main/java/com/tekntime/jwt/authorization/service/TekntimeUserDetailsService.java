package com.tekntime.jwt.authorization.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tekntime.jwt.authorization.repository.UserMongoRepository;

@Service
public class TekntimeUserDetailsService implements UserDetailsService{
	private static final Logger logger   = LoggerFactory.getLogger(TekntimeUserDetailsService.class);	
	
	@Autowired
	private UserMongoRepository userdao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<User> user =userdao.findOne(username);
		if(user !=null && !user.isEmpty() && user.size()==1) {
			return user.get(0);
		} else {
			logger.error("User not found with username: " + username);
		throw new UsernameNotFoundException("User not found with username: " + username);
	   }
	}
}


