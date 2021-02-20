package com.tekntime.auth.microservice.util;
import java.security.MessageDigest;
import java.util.Base64;

import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Component;
@Component ("SHASecurityImpl")
public class SHASecurityImpl implements Security {
	
	public String encode (String arg) {
		String encoded = Base64.getEncoder().encodeToString(arg.getBytes());
		return encoded;
	}

	public String decode (String encoded) {
		byte[] decodedBytes = Base64.getDecoder().decode(encoded);
		String decodedString = new String(decodedBytes);
		return decodedString;
	}
	
	protected String getHash(String protectedString, String algorithm) throws Exception {	
		MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
		messageDigest.update("TekT1me".getBytes());
		byte[] digestedBytes = messageDigest.digest(protectedString.getBytes());
		String sha256hex = new String(Hex.encode(digestedBytes));
		return sha256hex;
	}
	
	public String encrypt (String protectedString) throws Exception {
		return getHash(protectedString,  "SHA-256");
		
		
	}
	//Write decryption logic for SHA 256
	public String decrypt (String encryptedString, String algorithm) {
		return null;
	}

	@Override
	public String decrypt(String arg) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//Write get MH method
}
