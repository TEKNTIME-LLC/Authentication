package com.tekntime.oauth.server.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tekntime.oauth.server.model.Authority;
@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Integer> {
	List<Authority> findByUserid(int userid);
}

    
   
