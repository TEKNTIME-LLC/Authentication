package com.tekntime.oauth.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tekntime.oauth.server.model.Authority;
import com.tekntime.oauth.server.repository.AuthorityRepository;

@Service
public class AuthorityService {
	@Autowired
	private AuthorityRepository repository;
	
	
	public List<Authority> loadByLoginId(int loginId) throws UsernameNotFoundException {
		List<Authority>  authorities =repository.findByUserid(loginId);
		return authorities;
	}

}
