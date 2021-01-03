package com.tekntime.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter 
@Setter 
@NoArgsConstructor
public class SMSNotification {
	private String to;
	private String from;
	private String bodyMessage;
}
