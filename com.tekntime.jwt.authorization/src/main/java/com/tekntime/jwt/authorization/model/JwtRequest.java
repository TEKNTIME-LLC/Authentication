package com.tekntime.jwt.authorization.model;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter 
@Setter 
@NoArgsConstructor
public class JwtRequest implements Serializable {

private static final long serialVersionUID = 1L;

private String loginName;

private String password;



}
