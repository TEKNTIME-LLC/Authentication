package com.tekntime.auth.microservice.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.http.HttpStatus;

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
	@Transient
	private static final String EMAIL_PATTERN =	"^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	@Transient
	private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{9,}$";
	@Transient
	private static final String REGEX = "\\d{10}";

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
	
	public Map<String,HttpStatus> isValidAccount() {
		Map<String,HttpStatus> result=new HashMap();
		
		//verify account is locked or deleted. If locked or deleted authentication failed
		if (this.isLocked()) {
			result.put("Your account is locked", HttpStatus.UNAUTHORIZED);
		}
		// create logic if account is deleted, authentication should failed
		if (this.isDeleted()) {
			result.put("Your account is deleted", HttpStatus.UNAUTHORIZED);
		}
		// write a logic if account is not active it should not authenticate
		if (!this.isActive()) {
			result.put("Your account is not active", HttpStatus.UNAUTHORIZED);
		}
		
		int expiredate = this.getLastLoginDate().compareTo(this.getUpdateDate());
		if (expiredate >= 1 ) {	
			result.put("Your password is expired", HttpStatus.UNAUTHORIZED);	
		}
		
		if (this.getLoginAttempt()>= 5 ) {
			result.put("Too many attempts", HttpStatus.UNAUTHORIZED);
		}
	
		return result;
	}
	

	
	public Map<String,HttpStatus> validate() {
		Map<String,HttpStatus> result=new HashMap();
		if (this.getLoginName()==null ||this.getLoginName().isEmpty()) {
			result.put("Login name is empty", HttpStatus.BAD_REQUEST);
		} else {
			String newName= this.getLoginName().replaceAll(" ", "");
			this.setLoginName(newName);
		}
		
		if (this.getPassword()==null ||this.getPassword().isEmpty()) {
			result.put("password is empty", HttpStatus.BAD_REQUEST);
		}else if (!this.getPassword().matches(PASSWORD_PATTERN)) {
			result.put("Password is invalid", HttpStatus.BAD_REQUEST);
		}
			
		if (this.getFirstName()==null || this.getFirstName().isEmpty()) {
			result.put("First Name is empty", HttpStatus.BAD_REQUEST);
		}

		if (this.getLastName()==null || this.getLastName().isEmpty()) {
			result.put("Last Name is empty", HttpStatus.BAD_REQUEST);
		}
		
		if (this.getEmail()==null || this.getEmail().isEmpty()) {
			result.put("Email address is empty", HttpStatus.BAD_REQUEST);
			
		}else if (!this.getEmail().matches(EMAIL_PATTERN)) {
			result.put("Email address is invalid", HttpStatus.BAD_REQUEST);
		}

		if (this.getPhone()==null || this.getPhone().isEmpty()) { 
			result.put ("Phone number is empty", HttpStatus.BAD_REQUEST);
		}else {
		    String newPhone= this.getPhone().replaceAll(" ", "");
		    newPhone=newPhone.replaceAll("-", "");
		    this.setPhone(newPhone);
		    
			if (!this.getPhone().matches (REGEX)) {
					result.put("Phone number is invalid", HttpStatus.BAD_REQUEST);
			} 
		}
		
		return result;

	}

}
