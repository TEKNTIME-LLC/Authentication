package com.tekntime.auth.microservice.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tekntime.auth.microservice.model.EmailNotification;
import com.tekntime.auth.microservice.model.UserLogin;
import com.tekntime.auth.microservice.repository.UserLoginRepository;
import com.tekntime.auth.microservice.util.MD5SecurityImpl;
import com.tekntime.auth.microservice.util.SHASecurityImpl;

@Service
public class AccountManagementService {

Logger logger = LoggerFactory.getLogger(AccountManagementService.class);

@Autowired
UserLoginRepository repository;

@Autowired
EmailNotificationService service;


@Autowired
@Qualifier("SHASecurityImpl")
SHASecurityImpl security;
@Autowired
@Qualifier ("MD5SecurityImpl")
MD5SecurityImpl md5security;

public UserLogin getLogin(String name) {
	return repository.findByLoginName(name);
}

public Map<String,HttpStatus> saveLogin(UserLogin user) throws Exception {
	Map<String,HttpStatus> result=user.validate();
	
	if (!result.isEmpty()) {
		logger.error("Login failed {}", result);
		return result;
	}

	UserLogin dbuser=repository.findByLoginName(user.getLoginName());
	if (dbuser != null){
		result.put("Login name already exist",  HttpStatus.BAD_REQUEST);
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
		result.put("successfully saved",  HttpStatus.OK);
	 return result; 
	 }	



public Map<String,HttpStatus> passwordReset (UserLogin user) {
	Map<String,HttpStatus> result=new HashMap<>();
		UserLogin userReset = getLogin(user.getLoginName());
		
	if (userReset.getLoginName()== null) {
		result.put("Incorrect loginName or loginName does not exists",  HttpStatus.BAD_REQUEST);
		return result;
	}
	//write else if to valid if email address exist in db
		//create random password
		char[] tempPassword = generatePassword(8);
		//convert char tempPassword to string
		String tempPassword2 = String.valueOf(tempPassword);
		//pass generated password 
		userReset.setPassword(tempPassword2);
	
		userReset.setActive(true);
		userReset.setDeleted(false);
		userReset.setLocked(false);	
		setExpiryDate( userReset);		
	
		EmailNotification passwordNotify = new EmailNotification();
	
		passwordNotify.setBodyMessage("Your temporary password is: " + tempPassword2 + " You are required to change this password immediately after login");
		passwordNotify.setSubject("PASSWORD RESET");
		passwordNotify.setFrom("odewenwabraille@gmail.com");
		ArrayList<String> to = new ArrayList<String>(); //passwordNotify.setToList(Arrays.toString(userReset.getEmail())); create instance of ArrayList and add email to it
		to.add(userReset.getEmail());
		passwordNotify.setToList(to);
		service.emailNotify(passwordNotify); 
		
	repository.save(userReset);
	result.put("password changed successfully",  HttpStatus.OK);
	return result;
}
	


public Map<String,HttpStatus> updateAccount(UserLogin user) {
	Map<String,HttpStatus> result=new HashMap<>();
	UserLogin userReset = repository.findByLoginName(user.getLoginName());	
	if (userReset.getLoginName()== null) {
		result.put("Incorrect loginName or loginName does not exists",  HttpStatus.BAD_REQUEST);
		return result;
	}
	userReset.setActive(true);
	userReset.setDeleted(false);
	userReset.setLocked(false);	
	setExpiryDate( userReset);
	repository.save(userReset);
	result.put("Account is successfully updated", HttpStatus.OK);
	return result;
}


private static char[] generatePassword(int length) {
    String capitalCaseLetters = "ABCDEFGHJKMNPQRSTUVWXYZ";
    String lowerCaseLetters = "abcdefghjkmnpqrstuvwxyz";
    String specialCharacters = "!@#$";
    String numbers = "23456789";
    String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
    Random random = new Random();
    char[] password = new char[length];

    password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
    password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
    password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
    password[3] = numbers.charAt(random.nextInt(numbers.length()));
 
    for(int i = 4; i< length ; i++) {
       password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
    }
    return password;
 }

private void setExpiryDate(UserLogin userReset) {
	Calendar admindate = Calendar.getInstance();
	admindate.add(Calendar.DATE, 90);
	Date admndate = admindate.getTime();		
	userReset.setExpiryDate(admndate);
	
}
		
		

}



