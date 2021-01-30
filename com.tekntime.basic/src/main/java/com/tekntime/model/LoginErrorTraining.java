package com.tekntime.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "loginErrorTraining")

@Data
@Getter 
@Setter 
@Table(name = "loginErrorTraining")
public class LoginErrorTraining {

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment") 
 	 private int id;
	 private String date;
	 private String time;
	 private String event;
	 private String classname;
	 private String thread;
	 private String message;
	 private String errormsg;
	 private String stacktrace;
	 private String errorCode;
	 private String errorDescription;
	 private String solutionCode;
	 private String solutionDescription;
	 private String attributesToSolution;
	 private String apiCode;
	 private String apiDescription;
	 private String apiURL;
	 
}
