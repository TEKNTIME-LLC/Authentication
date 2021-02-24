package com.tekntime.jwt.authorization.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tekntime.jwt.authorization.model.JwtRequest;
import com.tekntime.jwt.authorization.model.JwtResponse;
import com.tekntime.jwt.authorization.model.UserLogin;
import com.tekntime.jwt.authorization.model.UserProfile;
import com.tekntime.jwt.authorization.service.AuthorityService;
import com.tekntime.jwt.authorization.service.TekntimeUserDetailsService;
import com.tekntime.jwt.authorization.util.JwtTokenUtil;


@RestController
@RequestMapping("/repo")
@CrossOrigin
public class DBAuthenticationController {
	private static final Logger logger   = LoggerFactory.getLogger(DBAuthenticationController.class);	


	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private TekntimeUserDetailsService userDetailsService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
    public String getApp() {
		logger.info("reached JWT login app" );
        return "JWT";
    }
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		logger.info("==>> Received message {} ", authenticationRequest.getLoginName());
		UserLogin userLogin=new UserLogin();
		userLogin.setLoginName(authenticationRequest.getLoginName());
		userLogin.setPassword(authenticationRequest.getPassword());
		Map<String,HttpStatus> result = userDetailsService.authenticate( userLogin);
		if(result.containsValue(HttpStatus.UNAUTHORIZED)) {
			logger.error("-->Authentication failed {}", result);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}
		final String token = jwtTokenUtil.generateToken(userLogin);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	
	@RequestMapping(value = "/token", method = RequestMethod.POST)
	public ResponseEntity<?> validate(@RequestBody UserProfile userProfile) throws Exception {
		logger.info("==>> Received message {} ", userProfile.getLoginName());
		UserLogin userLogin=new UserLogin();
		userLogin.setLoginName(userProfile.getLoginName());
		userLogin.setToken(userProfile.getToken());
		
		Map<String,HttpStatus> result = userDetailsService.validateUser( userLogin);
		if(result.containsValue(HttpStatus.UNAUTHORIZED)) {
			logger.error("-->INVALID USER {}", result);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}

		final Boolean isValid = jwtTokenUtil.validateToken(userProfile.getToken(), userProfile.getLoginName());
		
		if(isValid) {
			UserLogin user =userDetailsService.loadUserByLoginName(userProfile.getLoginName());
			user.setAuthorities(authorityService.loadByLoginId(user.getId()));
			return ResponseEntity.ok(user);
		}else {
			logger.error("-->INVALID TOKEN");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}
		
	}
}
