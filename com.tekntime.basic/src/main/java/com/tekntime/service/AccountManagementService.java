package com.tekntime.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Arrays;
import java.util.HashMap;

import java.util.Map;
import java.util.Random;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tekntime.model.EmailNotification;
import com.tekntime.model.UserLogin;
import com.tekntime.repository.UserLoginRepository;
import com.tekntime.controller.EmailNotificationController;
import com.tekntime.service.EmailNotificationService;

@Service
public class AccountManagementService {

Logger logger = LoggerFactory.getLogger(AccountManagementService.class);

@Autowired
UserLoginRepository repository;

@Autowired
EmailNotificationService service;

public UserLogin getLogin(String name) {
	return repository.findByLoginName(name);
}

public Map<String,String> passwordReset (UserLogin user) {
	Map<String,String> result=new HashMap<>();
		UserLogin userReset = getLogin(user.getLoginName());
		
	if (userReset.getLoginName()== null) {
		result.put("Incorrect loginName or loginName does not exists", "500");
		return result;
	}
	//write else if to valid if email address exist in db
		//create random password
		char[] tempPassword = generatePassword(8);
		//convert char tempPassword to string
		String tempPassword2 = String.valueOf(tempPassword);
		//pass generated password 
		userReset.setPassword(tempPassword2);
	
	
	//Do I need a logic to call the EmailNotification here to send newly generated password to customer? YES			
	
		EmailNotification passwordNotify = new EmailNotification();
	
		passwordNotify.setBodyMessage("Your temporary password is: " + tempPassword2 + " You are required to change this password immediately after login");
		passwordNotify.setSubject("PASSWORD RESET");
		ArrayList<String> to = new ArrayList<String>(); //passwordNotify.setToList(Arrays.toString(userReset.getEmail())); create instance of ArrayList and add email to it
		to.add(userReset.getEmail());
		passwordNotify.setToList(to);
		service.emailNotify(passwordNotify); 
		
	repository.save(userReset);
	result.put("password changed successfully", "200");
	return result;
}
	
public Map<String,String> accountUnlock(UserLogin user) {
		Map<String,String> result=new HashMap<>();
		UserLogin userReset = repository.findByLoginName(user.getLoginName());	
		if (userReset.getLoginName()== null) {
			result.put("Incorrect loginName or loginName does not exists", "500");
			return result;
		}
		userReset.setLocked(false);	
		repository.save(userReset);
		result.put("Account successfully unlocked", "200");
		return result;
}

public Map<String,String> undeleteAccount(UserLogin user) {
	Map<String,String> result=new HashMap<>();
	UserLogin userReset = repository.findByLoginName(user.getLoginName());	
	if (userReset.getLoginName()== null) {
		result.put("Incorrect loginName or loginName does not exists", "500");
		return result;
	}
	userReset.setDeleted(false);
	repository.save(userReset);
	result.put("Account successfully undeleted", "200");
	return result;
}

public Map<String,String> activateAccount(UserLogin user) {
	Map<String,String> result=new HashMap<>();
	UserLogin userReset = repository.findByLoginName(user.getLoginName());	
	if (userReset.getLoginName()== null) {
		result.put("Incorrect loginName or loginName does not exists", "500");
		return result;
	}
	userReset.setActive(true);
	repository.save(userReset);
	result.put("Account successfully activated", "200");
	return result;
}

public Map<String,String> setexpiryDate(UserLogin user) {
	Map<String,String> result=new HashMap<>();
	UserLogin userReset = repository.findByLoginName(user.getLoginName());	
	if (userReset.getLoginName()== null) {
		result.put("Incorrect loginName or loginName does not exists", "500");
		return result;
	}
	Calendar admindate = Calendar.getInstance();
	admindate.add(Calendar.DATE, 90);
	Date admndate = admindate.getTime();		
	userReset.setExpiryDate(admndate);
	repository.save(userReset);
	result.put("Expiry date successfully update to 90 days", "200");
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


		
		

}


//unexpired date (loginname)
//set expiry date to 90days (loginname) Done
