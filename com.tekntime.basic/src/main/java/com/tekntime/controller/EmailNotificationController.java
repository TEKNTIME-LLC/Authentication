package com.tekntime.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tekntime.model.EmailNotification;
import com.tekntime.service.EmailNotificationService;
@RestController
public class EmailNotificationController {
	
	@Autowired
	private EmailNotificationService service;
	
	@RequestMapping( path="/email", method=RequestMethod.POST)
	public Map<String,String> email(@RequestBody EmailNotification emailNotification) {
		Map<String,String> result =service.emailNotify(emailNotification);
	     return result;
	}
	
	@PostMapping("/send")
	public void sendMail(@RequestParam(value = "receiver") String[] receivers,
			@RequestParam(value = "from") String from,
	        @RequestParam(value = "subject") String subject, @RequestParam(value = "content") String content,
	        @RequestParam(value = "files", required = false) MultipartFile[] files) {
		EmailNotification emailNotification=new EmailNotification();
		emailNotification.setMultiparts(Arrays.asList(files));
		emailNotification.setFrom(from);
		emailNotification.setToList(Arrays.asList(receivers));
		emailNotification.setSubject(subject);
		service.send(emailNotification);
	}
	
}
