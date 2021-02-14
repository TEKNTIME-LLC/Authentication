package com.tekntime.oauth.server.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class OAuthController {
	private static final Logger logger   = LoggerFactory.getLogger(OAuthController.class);	

    @GetMapping("/authenticate")
    public ResponseEntity<Principal> authenticate(final Principal principal) {
    	logger.info("Authenticating {}...", principal.getName());
        return ResponseEntity.ok(principal);
    }

}
