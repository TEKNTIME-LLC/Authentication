package com.tekntime.jwt.authorization.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tekntime.jwt.authorization.model.UserLogin;
import com.tekntime.jwt.authorization.repository.UserRepository;
import com.tekntime.jwt.authorization.util.MD5SecurityImpl;
import com.tekntime.jwt.authorization.util.SHASecurityImpl;

@Service
public class TekntimeUserDetailsService {
	private static final Logger logger   = LoggerFactory.getLogger(TekntimeUserDetailsService.class);	
	
	@Autowired
	@Qualifier("SHASecurityImpl")
	SHASecurityImpl security;
	@Autowired
	@Qualifier ("MD5SecurityImpl")
	MD5SecurityImpl md5security;


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
	  final UserLogin userLogin = loadUserByLoginName(user.getLoginName());
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
				result.put("authentication failed", "401");
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
				result.put("authentication failed", "401");
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
				result.put("authentication failed", "401");
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


