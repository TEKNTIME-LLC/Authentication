package com.tekntime.jwt.authorization.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tekntime.jwt.authorization.model.JwtRequest;
import com.tekntime.jwt.authorization.model.JwtResponse;
import com.tekntime.jwt.authorization.model.UserProfile;
import com.tekntime.jwt.authorization.service.JwtUserDetailsService;
import com.tekntime.jwt.authorization.util.JwtTokenUtil;


@RestController
@RequestMapping("/authenticate")
@CrossOrigin
public class JwtAuthenticationController {
	private static final Logger logger   = LoggerFactory.getLogger(JwtAuthenticationController.class);	

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	//private TekntimeUserDetailsService userDetailsService;
	private JwtUserDetailsService userDetailsService;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
    public String getApp() {
		logger.info("reached JWT login app" );
        return "JWT";
    }
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}



	@RequestMapping(value = "/token", method = RequestMethod.POST)
	public ResponseEntity<?> validate(@RequestBody UserProfile userProfile) throws Exception {
		UserDetails userDetailsInDB = this.userDetailsService.loadUserByUsername(userProfile.getUsername());
		if(userDetailsInDB==null) {
			logger.error("INVALID_USER");
			throw new Exception("INVALID_USER");
		}
		final Boolean isValid = jwtTokenUtil.validateToken(userProfile.getToken(), userProfile.getUsername());
		if(isValid) {
			return ResponseEntity.ok(userDetailsInDB);
		}else {
			logger.error("INVALID_CREDENTIALS");
			throw new Exception("INVALID_CREDENTIALS");
		}
		
	}
}
