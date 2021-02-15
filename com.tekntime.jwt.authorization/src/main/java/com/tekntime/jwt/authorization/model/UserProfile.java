package com.tekntime.jwt.authorization.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter 
@Setter 
@NoArgsConstructor
public class UserProfile {
	private String loginName;
	private String token;
}
