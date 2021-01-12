package com.tekntime.jwt.authorization.repository;

import org.springframework.data.repository.CrudRepository;

import com.tekntime.jwt.authorization.model.TekntimeUser;

public interface UserRepository extends CrudRepository<TekntimeUser, Integer> {
	TekntimeUser findByLoginName(String name);
}

    
   
