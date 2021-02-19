package com.tekntime.oauth.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tekntime.oauth.server.model.UserLogin;
@Repository
public interface UserRepository extends CrudRepository<UserLogin, Integer> {
	UserLogin findByLoginName(String name);
}

    
   
