package com.tekntime.oauth.server.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
	
	public static void main(String[] args) {
	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
	    String password [] = {"pass", "secret", "Password", "Password1"};
	    for(int i = 0; i < password.length; i++) {
	        System.out.println(passwordEncoder.encode(password[i]));
	    }
	}
}
