package com.tekntime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tekntime.component.EventLogToJSONComponent;
@RestController
public class EventLogController {
	
	@Autowired
	EventLogToJSONComponent loadEvent;
	
	@RequestMapping( path="/event/load", method=RequestMethod.GET)
	public String read( @RequestParam String javaFileHandling) {
		loadEvent.loadEvent();
	    return "success";
	}

}
