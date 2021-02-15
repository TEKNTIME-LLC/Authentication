package com.tekntime.jwt.authorization.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tekntime.jwt.authorization.model.UserLogin;
@Repository
public interface UserRepository extends CrudRepository<UserLogin, Integer> {
	UserLogin findByLoginName(String name);
}

    
   
