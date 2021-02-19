package com.tekntime.oauth.server.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class UserLogin implements UserDetails{
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
	
	@Transient
	private String token;
	
	@Transient
	private List<Authority>  roles;
	
	
	public Map<String,String> isValidAccount() {
		Map<String,String> result=new HashMap<>();
		
		//verify account is locked or deleted. If locked or deleted authentication failed
		if (this.isLocked()) {
			result.put("Your account is locked", "401");
		}
		// create logic if account is deleted, authentication should failed
		if (this.isDeleted()) {
			result.put("Your account is deleted", "401");
		}
		// write a logic if account is not active it should not authenticate
		if (!this.isActive()) {
			result.put("Your account is not active", "401");
		}
		
		int expiredate = this.getLastLoginDate().compareTo(this.getUpdateDate());
		if (expiredate >= 1 ) {	
			result.put("Your password is expired", "401");	
		}
		
		if (this.getLoginAttempt()>= 5 ) {
			result.put("Too many attempts", "401");
		}
	
		return result;
	}
	

	
	public Map<String,String> validate() {
		Map<String,String> result=new HashMap<>();
		if (this.getLoginName()==null ||this.getLoginName().isEmpty()) {
			result.put("Login name is empty", "400");
		} else {
			String newName= this.getLoginName().replaceAll(" ", "");
			this.setLoginName(newName);
		}
		
		if (this.getPassword()==null ||this.getPassword().isEmpty()) {
			result.put("password is empty", "400");
		}else if (!this.getPassword().matches(PASSWORD_PATTERN)) {
			result.put("Password is invalid", "400");
		}
			
		if (this.getFirstName()==null || this.getFirstName().isEmpty()) {
			result.put("First Name is empty", "400");
		}

		if (this.getLastName()==null || this.getLastName().isEmpty()) {
			result.put("Last Name is empty", "400");
		}
		
		if (this.getEmail()==null || this.getEmail().isEmpty()) {
			result.put("Email address is empty", "400");
			
		}else if (!this.getEmail().matches(EMAIL_PATTERN)) {
			result.put("Email address is invalid", "400");
		}

		if (this.getPhone()==null || this.getPhone().isEmpty()) { 
			result.put ("Phone number is empty", "400");
		}else {
		    String newPhone= this.getPhone().replaceAll(" ", "");
		    newPhone=newPhone.replaceAll("-", "");
		    this.setPhone(newPhone);
		    
			if (!this.getPhone().matches (REGEX)) {
					result.put("Phone number is invalid", "400");
			} 
		}
		
		return result;

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return null;
		
	}

	@Override
	public String getUsername() {
		return loginName;
	}



	@Override
	public boolean isAccountNonExpired() {
		int expiredate = this.getLastLoginDate().compareTo(this.getUpdateDate());
		if (expiredate >= 1 ) {	
			return true;
		}
		return false;
	}



	@Override
	public boolean isAccountNonLocked() {
		if (this.isLocked()) {
			return true;
		}
		return false;

	}



	@Override
	public boolean isCredentialsNonExpired() {
		int expiredate = this.getLastLoginDate().compareTo(this.getUpdateDate());
		if (expiredate >= 1 ) {	
			return true;
		}
		return false;
		
	}



	@Override
	public boolean isEnabled() {
		// write a logic if account is not active it should not authenticate
		if (!this.isActive()) {
			return false;
		}
		return true;

	}

}
