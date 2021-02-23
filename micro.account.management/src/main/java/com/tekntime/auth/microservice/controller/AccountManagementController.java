package com.tekntime.auth.microservice.controller;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tekntime.auth.microservice.model.UserLogin;
import com.tekntime.auth.microservice.service.AccountManagementService;

@RestController
@RequestMapping("/account")
public class AccountManagementController {
	
	Logger logger = LoggerFactory.getLogger(AccountManagementController.class);


	@Autowired
	private AccountManagementService service; 
		
	@RequestMapping( path="/create", method=RequestMethod.POST)
	public Map<String,HttpStatus> create( @RequestBody UserLogin user) throws Exception {
		 Map<String,HttpStatus> result = service.saveLogin(user);
	
	     return result;
	}
	@RequestMapping(path="/update", method=RequestMethod.PUT)
	public Map<String,HttpStatus> update( @RequestBody UserLogin user) throws Exception {
		 Map<String,HttpStatus> result =  service.saveLogin(user);
	     return result;
	}	
	
	@RequestMapping(path="/reset", method=RequestMethod.PUT)
	public  Map<String,HttpStatus> reset(@RequestParam String loginName) {
		UserLogin user = new UserLogin();
		user.setLoginName(loginName);
		 Map<String,HttpStatus> result =service.passwordReset(user);
	     return result;
	}
	
	@RequestMapping( path="/unlock", method=RequestMethod.PUT)
	public  Map<String,HttpStatus> unlock( @RequestParam String loginName) {
		UserLogin user = new UserLogin();
		user.setLoginName(loginName);
		Map<String,HttpStatus> result =service.updateAccount(user);
		return result;
	}
	
	@RequestMapping( path="/activate", method=RequestMethod.PUT)
	public  Map<String,HttpStatus> activate( @RequestParam String loginName) {
		UserLogin user = new UserLogin();
		user.setLoginName(loginName);
		Map<String,HttpStatus> result =service.updateAccount(user);
		return result;
	}
	
	@RequestMapping( path="/undelete", method=RequestMethod.PUT)
	public  Map<String,HttpStatus> undelete( @RequestParam String loginName) {
		UserLogin user = new UserLogin();
		user.setLoginName(loginName);
		Map<String,HttpStatus> result =service.updateAccount(user);
		return result;
	}
	
	@RequestMapping( path="/expirydate", method=RequestMethod.PUT)
	public  Map<String,HttpStatus> expiryDate( @RequestParam String loginName) {
		UserLogin user = new UserLogin();
		user.setLoginName(loginName);
		Map<String,HttpStatus> result =service.updateAccount(user);
		return result;
	}
	


}
