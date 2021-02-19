package com.tekntime.jwt.authorization.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tekntime.jwt.authorization.model.Authority;
import com.tekntime.jwt.authorization.repository.AuthorityRepository;

@Service
public class AuthorityService {
	@Autowired
	private AuthorityRepository repository;
	
	
	public List<Authority> loadByLoginId(int loginId) throws UsernameNotFoundException {
		List<Authority>  authorities =repository.findByUserid(loginId);
		return authorities;
	}

}
