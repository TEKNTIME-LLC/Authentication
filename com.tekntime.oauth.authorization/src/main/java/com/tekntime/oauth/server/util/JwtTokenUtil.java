package com.tekntime.oauth.server.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tekntime.oauth.server.model.UserLogin;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

private static final long serialVersionUID = -2550185165626007488L;

public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

private static final Logger logger   = LoggerFactory.getLogger(JwtTokenUtil.class);	

@Value("${jwt.secret.key}")
private  String SECRET;




//retrieve username from jwt token
public  String getUsernameFromToken(String token) {
	logger.info("claiming token -->");
	return getClaimFromToken(token, Claims::getSubject);
}

//retrieve expiration date from jwt token
public  Date getExpirationDateFromToken(String token) {
	logger.info("expiring token -->");
	return getClaimFromToken(token, Claims::getExpiration);
}

public  <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
	final Claims claims = getAllClaimsFromToken(token);
	return claimsResolver.apply(claims);
}

    //for retrieveing any information from token we will need the secret key

private  Claims getAllClaimsFromToken(String token) {
	return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
}

//check if the token has expired

private  Boolean isTokenExpired(String token) {
	final Date expiration = getExpirationDateFromToken(token);
	return expiration.before(new Date());
}

//generate token for user
public String generateToken(UserLogin userDetails) {
	Map<String, Object> claims = new HashMap<>();
	return doGenerateToken(claims, userDetails.getLoginName());
}


	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
	}

	//validate token
	public  Boolean validateToken(String token, UserLogin userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getLoginName()) && !isTokenExpired(token));
	}
	
	//validate token
	public  Boolean validateToken(String token, String username) {
		final String usernameinToken = getUsernameFromToken(token);
		return (usernameinToken.equalsIgnoreCase(username) && !isTokenExpired(token));
	}


}
