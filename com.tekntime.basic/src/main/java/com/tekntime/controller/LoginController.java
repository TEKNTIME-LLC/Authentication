package com.tekntime.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tekntime.model.UserLogin;
import com.tekntime.service.UserLoginService;
@RestController
@RequestMapping("/login")
public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private UserLoginService service;
	
	@GetMapping("/app")
    public String getApp() {
		logger.info("reached basic-auth login app" );
        return "basicauth";
    }
	
	@RequestMapping( path="/read", method=RequestMethod.GET)
	public UserLogin read( @RequestParam String loginName) {
		logger.info("trying to login with {}", loginName);
		UserLogin result =service.getLogin(loginName);
	     return result;
	}
	
	@RequestMapping( path="/create", method=RequestMethod.POST)
	public Map<String,String> create( @RequestBody UserLogin user) {
		Map<String, String> result = null;
		try {
			result = service.saveLogin(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     return result;
	}
	@RequestMapping(path="/update", method=RequestMethod.POST)
	public Map<String,String> update( @RequestBody UserLogin user) {
		Map<String, String> result = null;
		try {
			result = service.saveLogin(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Updating user {}", user);
	     return result;
	}	
	
	@RequestMapping( path="/authenticate", method=RequestMethod.POST)
	public Map<String, String> authenticate( @RequestParam String loginName, String password) throws Exception {
		logger.info("authenticate user: {}", loginName);
		Map<String,String> result =service.authenticate(loginName, password);
		logger.info("Authentication result user: {}", result);
	     return result;
	}
	
	@RequestMapping( path="/delete", method=RequestMethod.DELETE)
	public Map<String, String> delete ( @RequestParam String loginName) throws Exception {
		Map<String,String> result =service.delete(loginName);
		return result;
}
	}
