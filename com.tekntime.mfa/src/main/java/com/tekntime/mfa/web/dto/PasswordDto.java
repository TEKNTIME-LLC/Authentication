package com.tekntime.mfa.web.dto;

import com.tekntime.mfa.validation.ValidPassword;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter 
@Setter 
@NoArgsConstructor
public class PasswordDto {

    private String oldPassword;

    @ValidPassword
    private String newPassword;

  }
