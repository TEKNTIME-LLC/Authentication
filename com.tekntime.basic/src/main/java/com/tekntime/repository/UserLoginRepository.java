package com.tekntime.repository;

import org.springframework.data.repository.CrudRepository;

import com.tekntime.model.UserLogin;

public interface UserLoginRepository extends CrudRepository<UserLogin, Integer> {
    UserLogin findByLoginName(String name);
}

    
   
