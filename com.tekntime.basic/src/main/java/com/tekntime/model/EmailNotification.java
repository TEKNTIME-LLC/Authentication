package com.tekntime.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter 
@Setter 
@NoArgsConstructor
public class EmailNotification {
	private List<String> toList;
	private String from;
	private List<String> ccList;
	private List<String> bccList;
	private String subject;
	private String bodyMessage;
	private List<String> attachmentList;
	private String contentType;
	private String files;
	private List<MultipartFile> multiparts;
}
