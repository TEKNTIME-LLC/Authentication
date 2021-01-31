package com.tekntime.repository;

import org.springframework.data.repository.CrudRepository;

import com.tekntime.model.EventLog;

public interface EventLogRepository extends CrudRepository <EventLog, Integer>{
	EventLog findById (int id);
}