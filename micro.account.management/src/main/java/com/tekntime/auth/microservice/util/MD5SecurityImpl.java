package com.tekntime.auth.microservice.util;
import org.springframework.stereotype.Component;
@Component("MD5SecurityImpl")
public class MD5SecurityImpl extends SHASecurityImpl{
	

	
	
	public String encrypt (String protectedString) throws Exception {
		return getHash(protectedString,  "MD5");
		
		
	}

	//Write get MH method
}
