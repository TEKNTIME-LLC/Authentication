package com.tekntime.jwt.authorization.service;

import java.util.Base64;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tekntime.jwt.authorization.model.UserLogin;
import com.tekntime.jwt.authorization.repository.UserRepository;

@Service
public class TekntimeUserDetailsService {
	private static final Logger logger   = LoggerFactory.getLogger(TekntimeUserDetailsService.class);	

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository repository;
	
	
	public UserLogin loadUserByLoginName(String username) throws UsernameNotFoundException {
		UserLogin user =repository.findByLoginName(username);
		if(user !=null ) {
			logger.info("--> Found user {}: ", user);
			return user;
		} else {
			logger.error("User not found with username: " + username);
			throw new UsernameNotFoundException("User not found with username: " + username);
	   }
	}
	
	public Map<String, HttpStatus> validateToken (UserLogin user) throws Exception {
		final UserLogin userLogin =loadUserByLoginName(user.getLoginName());
		  
			Map<String,HttpStatus> result=userLogin.isValidAccount();
			
			logger.info("--> Found user {}: ", result);
			
			if (!result.isEmpty()) {
				logger.error("Login failed {}", result);
				return result;
			
			}
			return result;
	}
	
  public Map<String, HttpStatus> authenticate (UserLogin user) throws Exception {
	  final UserLogin userLogin = loadUserByLoginName(user.getLoginName());
	  final String password=user.getPassword();
	  
		Map<String,HttpStatus> result=userLogin.isValidAccount();
		
		if (result.containsValue( HttpStatus.UNAUTHORIZED)) {
			logger.error("Login failed {}", result);
			return result;
		}
		
		if (userLogin.getHashType()== null) {
			//decrypt the password and validate
			String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
			if (encodedPassword.equals(password)) {
				result.put("successfully authenticated",  HttpStatus.OK);
				userLogin.setLoginAttempt(0);
			}else{
				result.put("authentication failed",  HttpStatus.UNAUTHORIZED);
				int attempt = userLogin.getLoginAttempt();
				attempt++;
				userLogin.setLoginAttempt(attempt);
			}
		} else {
			String shPassword = passwordEncoder.encode(password);
			if (userLogin.getPassword().equals(shPassword)) {				
				result.put("successfully authenticated", HttpStatus.OK);
				userLogin.setLoginAttempt(0);

			}else{
				result.put("authentication failed",  HttpStatus.UNAUTHORIZED);
				int attempt = userLogin.getLoginAttempt();
				attempt++;
				userLogin.setLoginAttempt(attempt);
			}
		} 
		
		repository.save(userLogin);

		if (result.containsValue( HttpStatus.UNAUTHORIZED)) {
			logger.error("Authentication failed {}", result);
		}else {
			logger.info("Authentication successful");	
		}
		return result;
	}

}


