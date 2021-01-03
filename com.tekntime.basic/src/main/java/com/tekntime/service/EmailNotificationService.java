package com.tekntime.service;
import java.util.HashMap;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tekntime.model.EmailNotification;


@Component
public class EmailNotificationService {
	Logger logger = LoggerFactory.getLogger(EmailNotificationService.class);
	
	@Value("${spring.mail.host}")
	private String SMTPHOST;
	@Value("${spring.mail.port}")
	private String SMTPPORT;
	@Value("${spring.mail.smtp.auth}")
	private String SMTPAUTH;
	@Value("${spring.mail.smtp.starttls}")
	private String SMTPSTARTTLS;
	@Value("${spring.mail.username}")
	private String SMTPUSER;
	@Value("${spring.mail.password}")
	private String SMTPPASSWORD;
	
/* write a method for emailNotify
 *  */
    private void notify (EmailNotification emailNotification) {
    	Map<String,String> result=new HashMap();
        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", SMTPHOST);
        properties.put("mail.smtp.port", SMTPPORT); //do a similar configuration like that of SMTP host
        properties.put("mail.smtp.starttls.enable", SMTPSTARTTLS); //do a similar configuration like that of SMTP host
        properties.put("mail.smtp.auth", SMTPAUTH); //do a similar configuration like that of SMTP host
        
        

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTPUSER, SMTPPASSWORD);
            }
        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            //if email notification has 'from' use it, else use SMTPUser
            message.setFrom(new InternetAddress(SMTPUSER));
            
            //write code to validate email address pattern    
            String tos="";
            
                       	
            for(String to :emailNotification.getToList()) {
            	tos+=to+",";
            	}
            
            // Set To: header field of the header.
            message.addRecipients(Message.RecipientType.TO, tos);

            //write code if cc list exist, add to the list
            if (emailNotification.getCcList()!= null) {
            	String ccs="";
                for (String cc :emailNotification.getCcList()) {
                	ccs+=cc+",";
                	}
                message.setRecipients(Message.RecipientType.CC, ccs);
            	}
            
                        
            //write code if bcc list exist, add to the list
            if (emailNotification.getBccList()!=null) {
            
            	String bccs="";
                for (String bcc:emailNotification.getBccList()) {
                	bccs+=bcc+",";
                	}
                message.setRecipients(Message.RecipientType.BCC, bccs);   
            	}
              
            // Set Subject: header field
            message.setSubject(emailNotification.getSubject());
            
            // Now set the actual message
            Multipart multipart = new MimeMultipart();
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(emailNotification.getBodyMessage(), "text/html");
            multipart.addBodyPart(messageBodyPart);             
   
                 	
        	  for(String filename: emailNotification.getAttachmentList()){ //how to compare String to file
        			  addAttachment( multipart,  filename);
              }
            
         
            //multipart.setParent(messageBodyPart);
              
               
          
            message.setContent(multipart);

            // Send message
            Transport.send(message);
            logger.info("Email was sent successfully", message);
        } catch (MessagingException mex) {	      	        	
        	mex.printStackTrace();
        }
    }
 private void addAttachment(Multipart multipart, String filename) throws MessagingException
    {
        DataSource source = new FileDataSource(filename);
        BodyPart messageBodyPart = new MimeBodyPart();        
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
        multipart.addBodyPart(messageBodyPart);        
    }
 
 public Map<String,String> send (EmailNotification emailNotification) {
	 Map<String,String> result=new HashMap();
	 if (emailNotification.getToList()==null || emailNotification.getToList().isEmpty()) {
     	result.put("Recipient address is empty", "400");
     	
     }	
	 if (emailNotification.getSubject()==null || emailNotification.getSubject().isEmpty()) {
     	result.put("Subject field is empty", "400");
     }
	 if (!result.isEmpty()) {
			return result;
		}
	 
	 //notify(emailNotification);
	 result.put("Successfully sent message", "200");
	 logger.info("Email was sent successfully");
	 return result;
 }
        
 public Map<String,String> emailNotify (EmailNotification emailNotification) {
	 Map<String,String> result=new HashMap();
	 if (emailNotification.getToList()==null || emailNotification.getToList().isEmpty()) {
     	result.put("Recipient address is empty", "400");
     }	
	 if (emailNotification.getSubject()==null || emailNotification.getSubject().isEmpty()) {
     	result.put("Subject field is empty", "400");
     }
	 for(String filename: emailNotification.getAttachmentList()){ 
		  File file = new File(filename);
		  if(!file.exists()) { 
			  result.put(filename + " File does not exits " , "400");
		  }
     }
	 if (!result.isEmpty()) {
			return result;
		}
	 
	 notify(emailNotification);
	 result.put("Successfully sent message", "200");
	 logger.info("Email was sent successfully");
	 return result;
 }
}