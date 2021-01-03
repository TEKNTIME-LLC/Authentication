package com.tekntime.repository;

import org.springframework.data.repository.CrudRepository;

import com.tekntime.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    Person findByLastName(String name);

	Person findBysocialSecurity(int socialSecurity);

}
