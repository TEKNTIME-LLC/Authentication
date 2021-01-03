package com.tekntime.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tekntime.model.SMSNotification;
import com.tekntime.service.SMSNotificationService;
@RestController
public class SMSNotificationController {
	
	@Autowired
	private SMSNotificationService service;
	
	@RequestMapping( path="/sendSms", method=RequestMethod.POST)
	public Map<String,String> sendSms(@RequestBody SMSNotification smsNotification) {
	
		Map<String,String> result =service.send(smsNotification);
		
	     return result;
	}
	

	
}
