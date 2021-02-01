package com.tekntime.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tekntime.model.UserLogin;
import com.tekntime.service.AccountManagementService;

@RestController
@RequestMapping("/account")
public class AccountManagementController {

	@Autowired
	private AccountManagementService service; 
	
	@RequestMapping(path="/reset", method=RequestMethod.GET)
	public Map<String, String> reset(@RequestParam String loginName) {
		UserLogin user = new UserLogin();
		user.setLoginName(loginName);
		Map<String, String> result =service.passwordReset(user);
	     return result;
	}
	
	@RequestMapping( path="/unlock", method=RequestMethod.GET)
	public Map<String, String> unlock( @RequestParam String loginName) {
		UserLogin user = new UserLogin();
		user.setLoginName(loginName);
		Map<String,String> result =service.updateAccount(user);
		return result;
	}
	
	@RequestMapping( path="/activate", method=RequestMethod.GET)
	public Map<String, String> activate( @RequestParam String loginName) {
		UserLogin user = new UserLogin();
		user.setLoginName(loginName);
		Map<String,String> result =service.updateAccount(user);
		return result;
	}
	
	@RequestMapping( path="/undelete", method=RequestMethod.GET)
	public Map<String, String> undelete( @RequestParam String loginName) {
		UserLogin user = new UserLogin();
		user.setLoginName(loginName);
		Map<String,String> result =service.updateAccount(user);
		return result;
	}
	
	@RequestMapping( path="/account/expirydate", method=RequestMethod.GET)
	public Map<String, String> expiryDate( @RequestParam String loginName) {
		UserLogin user = new UserLogin();
		user.setLoginName(loginName);
		Map<String,String> result =service.updateAccount(user);
		return result;
	}
	


}
