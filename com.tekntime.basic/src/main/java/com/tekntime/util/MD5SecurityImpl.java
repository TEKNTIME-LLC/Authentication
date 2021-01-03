package com.tekntime.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.security.SecureRandom;

import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Component;
@Component("MD5SecurityImpl")
public class MD5SecurityImpl extends SHASecurityImpl{
	

	
	
	public String encrypt (String protectedString) throws Exception {
		return getHash(protectedString,  "MD5");
		
		
	}

	//Write get MH method
}
