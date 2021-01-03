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
	Map<String,String> result=new HashMap();
	if (user.getLoginName()==null ||user.getLoginName().isEmpty()) {
		result.put("Login name is empty", "400");
	} else {
		String newName= user.getLoginName().replaceAll(" ", "");
		user.setLoginName(newName);
	}
	
	if (user.getPassword()==null ||user.getPassword().isEmpty()) {
		result.put("password is empty", "400");
		
	//create logic for password to have lower case, upper case, and symbols with at least 9 characters		

	}else if (!user.getPassword().matches(PASSWORD_PATTERN)) {
		result.put("Password is invalid", "400");
		}
		
	if (user.getFirstName()==null || user.getFirstName().isEmpty()) {
		result.put("First Name is empty", "400");
	}

	if (user.getLastName()==null || user.getLastName().isEmpty()) {
		result.put("Last Name is empty", "400");
	}
	
	if (user.getEmail()==null || user.getEmail().isEmpty()) {
		result.put("Email address is empty", "400");
		
	}else if (!user.getEmail().matches(EMAIL_PATTERN)) {
		result.put("Email address is invalid", "400");
	}

	if (user.getPhone()==null || user.getPhone().isEmpty()) { 
		result.put ("Phone number is empty", "400");
	}else {
	    String newPhone= user.getPhone().replaceAll(" ", "");
	    newPhone=newPhone.replaceAll("-", "");
	   // newPhone=newPhone.replaceAll("(", "");
	   //newPhone=newPhone.replaceAll(")", "");
	    user.setPhone(newPhone);
	    
	if (!user.getPhone().matches (REGEX)) {
			result.put("Phone number is invalid", "400");
	} 
	}
	
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
	Map<String,String> result=new HashMap();
	
	//verify account is locked or deleted. If locked or deleted authentication failed
	if (userLogin.isLocked()) {
		result.put("Your account is locked", "500");
		logger.info("User account is locked", userLogin.isLocked());
		return result;
	}
	// create logic if account is deleted, authentication should failed
	if (userLogin.isDeleted()) {
		result.put("Your account is deleted", "500");
		logger.info("User account is deleted", userLogin.isDeleted());
		return result;
	}
	// write a logic if account is not active it should not authenticate
	if (!userLogin.isActive()) {
		result.put("Your account is not active", "500");
		logger.info("User account is not active", userLogin.isActive());
		return result;
	}
	
	if (userLogin.getLoginAttempt()>= 5 ) {
		result.put("Too many attempts", "500");
		userLogin.setLocked(true);
		repository.save(userLogin);
		logger.info("User account is locked. Too many attempts {} ", loginName);		
		return result;
	}
	
	int expiredate = userLogin.getLastLoginDate().compareTo(userLogin.getUpdateDate());
	if (expiredate >= 1 ) {	
		result.put("Your password is expired", "500");	
		logger.warn("User account password expired");
		return result;
	} else {
	    long diff= dateDiffIndays(userLogin.getExpiryDate(), new Date());
		result.put("Your password expires in " + diff+ " days", "200");
		if (diff <= 0) {
		logger.warn("User account password expires in", diff);
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
	
	//code for the logger if is 200 then info or else error

	if (result.put("successfully authenticated", "200")!=null) {
		logger.info("Authentication successful");
		
	
		
		
		}else {
			logger.error("Authentication failed", result);
		}
	
	//logger.info("Authentication login result for user {} is {}", userLogin.getLoginName(), result);
	return result;
	
	}
}

	private long dateDiffIndays(Date date1, Date date2){
 
    long diffInMillies = Math.abs(date2.getTime() - date1.getTime());
    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    return diff;		
}

	
}