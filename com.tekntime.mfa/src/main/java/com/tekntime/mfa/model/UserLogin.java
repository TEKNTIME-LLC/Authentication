package com.tekntime.mfa.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
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
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@Transient
	private static final String EMAIL_PATTERN =	"^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	@Transient
	private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{9,}$";
	@Transient
	private static final String REGEX = "\\d{10}";


	private String loginName;
	private String firstName;
	private String lastName;
	private String middleInitial;
	@Column(length = 60)
	private String password;
	@CreationTimestamp 
	private Date createDate;
	@UpdateTimestamp
	private Date updateDate;
	private Date lastLoginDate;
	private int loginAttempt;
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
	private Date expiryDate;
	private boolean isActive;
	private boolean isLocked;
	private boolean isDeleted;
	
    private boolean isMFA;
    private String qrCodeSecret;

    

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Authority> roles;


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((email == null) ? 0 : email.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserLogin user = (UserLogin) obj;
        if (!email.equals(user.email)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("User [id=").append(id).append(", firstName=").append(firstName).append(", lastName=").append(lastName).append(", email=").append(email).append(", password=").append(password).append(", isActive=").append(isActive).append(", isMFA=")
                .append(isMFA).append(", qrCodeSecret=").append(qrCodeSecret).append(", roles=").append(roles).append("]");
        return builder.toString();
    }

}