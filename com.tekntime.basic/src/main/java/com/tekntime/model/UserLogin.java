package com.tekntime.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@Getter 
@Setter 
@NoArgsConstructor
@Entity(name = "userLogin")
@Table(name = "userLogin")
public class UserLogin {

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment") 
 	private int id;
	private String loginName;
	private String firstName;
	private String lastName;
	private String middleInitial;
	private String password;
	@CreationTimestamp 
	private Date createDate;
	@UpdateTimestamp
	private Date updateDate;
	private Date expiryDate;
	private boolean isActive;
	private boolean isLocked;
	private Date lastLoginDate;
	private int loginAttempt;
	private boolean isDeleted;
	private String email;
	private String phone;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private int zipCode;
	private boolean isEmailNotification;
	private boolean isTextMessageNotification;
	private String hashType;

}
