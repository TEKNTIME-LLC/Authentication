package com.tekntime.jwt.authorization.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tekntime.jwt.authorization.model.Authority;
@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Integer> {
	List<Authority> findByUserLoginId(int userLoginId);
}

    
   
