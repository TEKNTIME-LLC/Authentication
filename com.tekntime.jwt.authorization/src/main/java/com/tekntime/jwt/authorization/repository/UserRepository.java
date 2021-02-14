package com.tekntime.jwt.authorization.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tekntime.jwt.authorization.model.TekntimeUserDetails;
@Repository
public interface UserRepository extends CrudRepository<TekntimeUserDetails, Integer> {
	TekntimeUserDetails findByUsername(String name);
}

    
   
