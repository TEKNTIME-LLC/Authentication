package com.tekntime.repository;

import org.springframework.data.repository.CrudRepository;

import com.tekntime.model.LoginErrorTraining;

public interface LoginErrorTrainingRepository extends CrudRepository <LoginErrorTraining, String>{
	LoginErrorTraining findByErrorCode(String errorCode);
}