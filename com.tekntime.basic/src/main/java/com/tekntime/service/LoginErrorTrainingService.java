package com.tekntime.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tekntime.model.LoginErrorTraining;
import com.tekntime.repository.LoginErrorTrainingRepository;

@Service
public class LoginErrorTrainingService {

@Autowired
LoginErrorTrainingRepository repository;
	

public Map<String,String> errorTraining (LoginErrorTraining training) {
		Map<String,String> result=new HashMap<>();
	if (training.getErrorCode() == null) {
		result.put("Enter appropriate error code", "400");
		return result;
	}
	String errorCode = training.getErrorCode();
	training.setErrorCode(errorCode);
	String errorDescription = training.getErrorDescription();
	training.setErrorDescription(errorDescription);
	String errormsg = training.getErrormsg();
	training.setErrormsg(errormsg);
	String event = training.getEvent();
	training.setEvent(event);
	String message = training.getMessage();
	training.setMessage(message);
	String solutionCode = training.getSolutionCode(); //add if statement
	training.setSolutionCode(solutionCode);
	String solutionDescription = training.getSolutionDescription();
	training.setSolutionDescription(solutionDescription);
	String stacktrace = training.getStacktrace();
	training.setStacktrace(stacktrace);
	String thread = training.getThread();
	training.setThread(thread);
	String time = training.getTime();
	training.setTime(time);
	String date = training.getDate();
	training.setDate(date);
	String classname = training.getClassname();
	training.setClassname(classname);
	String attributesToSolution = training.getAttributesToSolution();
	training.setAttributesToSolution(attributesToSolution);
	String apiURL = training.getApiURL();
	training.setApiURL(apiURL);
	String apiDescription = training.getApiDescription();
	training.setApiDescription(apiDescription);
	String apiCode = training.getApiCode(); //add if statement
	training.setApiCode(apiCode);
	
		repository.save(training);
		result.put("Successfully saved", "200");
		return result;
	}
	
}