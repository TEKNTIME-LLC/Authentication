package com.tekntime.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter 
@Setter 
@NoArgsConstructor
@Table(name = "Person")

public class Person {

	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String firstName;
	private String lastName;
	private String middleName;
	private String dateOfBirth;
	private int socialSecurity;
	private String gender;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private int zipCode;
	private Date createdDate;
	private Date updatedDate;
	private String createdBy;
	private String updatedBy;
	private boolean isDeleted;
	
	
}
