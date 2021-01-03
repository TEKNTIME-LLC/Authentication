package com.tekntime.util;

public interface Security {
	public String encode (String arg);
	public String decode (String arg);
	public String encrypt (String arg) throws Exception;
	public String decrypt (String arg);
}
