package com.tekntime.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tekntime.model.LoginErrorTraining;
import com.tekntime.service.LoginErrorTrainingService;
@RestController
public class LoginErrorTrainingController {

	@Autowired
	LoginErrorTrainingService service;
	
	@RequestMapping( path="/training/login", method=RequestMethod.POST)
	public Map<String,String> create( @RequestBody LoginErrorTraining error) {
		Map<String, String> result = null;
		result = service.errorTraining(error);
		return result;
	}


}

