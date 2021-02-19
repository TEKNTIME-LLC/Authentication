package com.tekntime.oauth.server.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tekntime.oauth.server.model.UserLogin;
import com.tekntime.oauth.server.repository.UserRepository;
import com.tekntime.oauth.server.util.MD5SecurityImpl;
import com.tekntime.oauth.server.util.SHASecurityImpl;

@Service
public class TekntimeUserDetailsService implements UserDetailsService{
	private static final Logger logger   = LoggerFactory.getLogger(TekntimeUserDetailsService.class);	
	
	@Autowired
	@Qualifier("SHASecurityImpl")
	SHASecurityImpl security;
	@Autowired
	@Qualifier ("MD5SecurityImpl")
	MD5SecurityImpl md5security;


	@Autowired
	private UserRepository repository;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserLogin user =loadUserByLoginName(username);
		return user;
	}
	
	
	private UserLogin loadUserByLoginName(String username) throws UsernameNotFoundException {
		UserLogin user =repository.findByLoginName(username);
		if(user !=null ) {
			return user;
		} else {
			logger.error("User not found with username: " + username);
			throw new UsernameNotFoundException("User not found with username: " + username);
	   }
	}
	
	public Map<String, String> validateToken (UserLogin user) throws Exception {
		final UserLogin userLogin = repository.findByLoginName(user.getLoginName());
		  
			Map<String,String> result=userLogin.isValidAccount();
			
			if (!result.isEmpty()) {
				logger.error("Login failed {}", result);
				return result;
			
			}
			return result;
	}
	
  public Map<String, String> authenticate (UserLogin user) throws Exception {
	  final UserLogin userLogin = repository.findByLoginName(user.getLoginName());
	  final String password=user.getPassword();
	  
		Map<String,String> result=userLogin.isValidAccount();
		
		if (!result.isEmpty()) {
			logger.error("Login failed {}", result);
			return result;
		}
		
		if (userLogin.getHashType()== null) {
			//decrypt the password and validate
			String decodedPassword = security.decode(userLogin.getPassword());
			if (decodedPassword.equals(password)) {
				result.put("successfully authenticated", "200");
				userLogin.setLoginAttempt(0);
			}else{
				result.put("authentication failed", "400");
				int attempt = userLogin.getLoginAttempt();
				attempt++;
				userLogin.setLoginAttempt(attempt);
			}
		} else if (userLogin.getHashType().equalsIgnoreCase("sha256")) {
			String shPassword = security.encrypt(password);
			if (userLogin.getPassword().equals(shPassword)) {				
				result.put("successfully authenticated", "200");
				userLogin.setLoginAttempt(0);

			}else{
				result.put("authentication failed", "400");
				int attempt = userLogin.getLoginAttempt();
				attempt++;
				userLogin.setLoginAttempt(attempt);
			}
		} else if (userLogin.getHashType().equalsIgnoreCase("MD5")) {
			String md5Password = md5security.encrypt(password);
			if (userLogin.getPassword().equals(md5Password)) {				
				result.put("successfully authenticated", "200");
				userLogin.setLoginAttempt(0);

			}else{
				result.put("authentication failed", "400");
				int attempt = userLogin.getLoginAttempt();
				attempt++;
				userLogin.setLoginAttempt(attempt);
			}
		}  		
		
		repository.save(userLogin);
		

		if (result.put("successfully authenticated", "200")!=null) {
			logger.info("Authentication successful");
		}else {
			logger.error("Authentication failed", result);
		}
		return result;
		
		
	}

}

