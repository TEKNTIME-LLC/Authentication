package com.tekntime.auth.microservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.tekntime.auth.microservice.model.UserLogin;

public interface UserLoginRepository extends CrudRepository<UserLogin, Integer> {
    UserLogin findByLoginName(String name);
}

    
   
