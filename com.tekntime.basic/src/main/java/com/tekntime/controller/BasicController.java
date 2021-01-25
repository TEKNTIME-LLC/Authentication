package com.tekntime.controller;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class BasicController {
	Logger logger = LoggerFactory.getLogger(BasicController.class);
	
	@Value("${basic.auth.host}")
	private String BASICAUTHHOSTNAME;

	
	@GetMapping("/app")
    public Map<String, String> getApp(HttpServletRequest request) {
		logger.info("reached basic-auth app" );
		Map<String, String> map = new HashMap<String, String>();
		if(! BASICAUTHHOSTNAME.equalsIgnoreCase(request.getHeader("host"))||
			!"https".equalsIgnoreCase(request.getScheme())
		  ) {
			map.put("app", "BASIC-AUTH is NOT accessible");
			return map;
	    }
		
		map.put("scheme", request.getScheme());
		map.put("server", request.getServerName());
		map.put("method", request.getMethod());
		map.put("contextpath", request.getContextPath());
		map.put("protocol", request.getProtocol());
		map.put("port", ""+request.getServerPort());
		map.put("LocalName", request.getLocalName());
		map.put("requestURI", request.getRequestURI());
		map.put("queryString", request.getQueryString());
		map.put("cookies", Arrays.toString(request.getCookies()));
		
		Enumeration<String> params = request.getParameterNames(); 
		while(params.hasMoreElements()){
		 String paramName = params.nextElement();
		 map.put("Parameter Name - "+paramName, "Value - "+request.getParameter(paramName));
		}
		
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        map.put("app", "BASIC-AUTH");
       
        return map;
    }

}
