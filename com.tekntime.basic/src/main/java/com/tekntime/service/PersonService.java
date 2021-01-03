package com.tekntime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tekntime.model.Person;
import com.tekntime.repository.PersonRepository;
@Service
public class PersonService {
@Autowired	
PersonRepository repository;

public Person getPerson(String lastName) {
	return repository.findByLastName(lastName);
}


}
