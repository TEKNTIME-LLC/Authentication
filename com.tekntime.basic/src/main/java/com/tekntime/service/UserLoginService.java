package com.tekntime.service;

import java.util.Date;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.mail.Transport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tekntime.model.EmailNotification;
import com.tekntime.model.UserLogin;
import com.tekntime.repository.UserLoginRepository;
import com.tekntime.util.MD5SecurityImpl;
import com.tekntime.util.SHASecurityImpl;

@Service
public class UserLoginService {
	Logger logger = LoggerFactory.getLogger(UserLoginService.class);
	
@Autowired	
UserLoginRepository repository;
@Autowired
@Qualifier("SHASecurityImpl")
SHASecurityImpl security;
@Autowired
@Qualifier ("MD5SecurityImpl")
MD5SecurityImpl md5security;

private static final String EMAIL_PATTERN =
"^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{9,}$";

String REGEX = "\\d{10}";

public UserLogin getLogin(String name) {
	logger.info("Getting userlogin by name {}", name);
	return repository.findByLoginName(name);
	
}
public Iterable<UserLogin> getAllLogin() {
	logger.info("Getting AllLogin");
	return repository.findAll();
}
public Map<String,String> saveLogin(UserLogin user) throws Exception {
	Map<String,String> result=user.validate();
	
	if (!result.isEmpty()) {
		logger.error("Login failed {}", result);
		return result;
	
	}
			
	//// encrypt password before saving
	///write a logic to encrypt password
	
		UserLogin dbuser=repository.findByLoginName(user.getLoginName());
	if (dbuser != null){
		result.put("Login name already exist", "400");
		logger.error("Failed to save. Login named already exist{}", user);
		return result;
	}
			
	if (user.getHashType()== null) {
		String encodedPassword = security.encode(user.getPassword());
		user.setPassword(encodedPassword);	
	} else if (user.getHashType().equalsIgnoreCase("sha256")) {
		String shValue= security.encrypt(user.getPassword());
		user.setPassword(shValue);	
	} else if (user.getHashType().equalsIgnoreCase("md5")) {
		String md5Value= md5security.encrypt(user.getPassword());
		user.setPassword(md5Value);
	}
	repository.save(user); 
	logger.info("User created successfully {}", user.getLoginName());
		result.put("successfully saved", "200");
	 return result; 
	 }	


public Map<String, String> delete(String loginName) {
	UserLogin user = getLogin(loginName); 
	Map<String,String> result=new HashMap();
	if (user != null) {
		user.setDeleted(true);
		repository.save(user);
		result.put("User successfully deleted", "200");
		logger.info("User deleted successfully {}",user);
		
	} else {
		result.put("User does not exist", "400");
		logger.info("User does not exist {}",user);
	}
	return result;
}



public Map<String, String> authenticate (String loginName, String password, String email) throws Exception {
	return null;
}

public Map<String, String> authenticate (String loginName, String password) throws Exception {
	UserLogin userLogin=repository.findByLoginName(loginName);
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

	private long dateDiffIndays(Date date1, Date date2){
 
    long diffInMillies = Math.abs(date2.getTime() - date1.getTime());
    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    return diff;		
}

	
}