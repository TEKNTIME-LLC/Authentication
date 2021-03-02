package com.tekntime.mfa.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tekntime.mfa.validation.PasswordMatches;
import com.tekntime.mfa.validation.ValidEmail;
import com.tekntime.mfa.validation.ValidPassword;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter 
@Setter 
@NoArgsConstructor
@PasswordMatches
public class UserDto {
    @NotNull
    @Size(min = 1, message = "{Size.userDto.firstName}")
    private String firstName;

    @NotNull
    @Size(min = 1, message = "{Size.userDto.lastName}")
    private String lastName;

    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 1)
    private String matchingPassword;

    @ValidEmail
    @NotNull
    @Size(min = 1, message = "{Size.userDto.email}")
    private String email;

    private boolean isMFA;
    
    private Integer role;


    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("UserDto [firstName=").append(firstName).append(", lastName=").append(lastName).append(", password=").append(password).append(", matchingPassword=").append(matchingPassword).append(", email=").append(email).append(", isUsing2FA=")
                .append(isMFA).append(", role=").append(role).append("]");
        return builder.toString();
    }

}
