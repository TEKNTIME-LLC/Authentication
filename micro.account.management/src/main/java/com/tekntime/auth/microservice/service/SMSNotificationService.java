package com.tekntime.auth.microservice.service;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tekntime.auth.microservice.model.SMSNotification;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
@Component
public class SMSNotificationService {
	Logger logger = LoggerFactory.getLogger(SMSNotificationService.class);
    // Find your Account Sid and Auth Token at twilio.com/console
    public static final String ACCOUNT_SID =
            "AC3ac3075d9fec37a5ee693df91af3651d";
    public static final String AUTH_TOKEN =
            "1e3586f65fd687933dab316e45c79d0a";
	private static final String Message = null;

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        PhoneNumber from = new PhoneNumber ("+12057821230");
        
        PhoneNumber to = new PhoneNumber ("+13177269140");
        
        Message message = com.twilio.rest.api.v2010.account.Message.creator(to, from, "Thank you for fixing this part of the SMS notification with Twilio").create();
       
        System.out.println(message.getSid()); 
    }
    
    public  Map<String,String> send(SMSNotification smsNotification) {
    	Map<String,String> result =new HashMap();
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        
        // validate to, from body
        if (smsNotification.getFrom()==null || smsNotification.getFrom().isEmpty()) {
        	result.put("Phone number of the sender is empty", "400");
        }
        
        if (smsNotification.getTo()==null || smsNotification.getTo().isEmpty()) {
        	result.put("Phone number of the receiver is empty", "400");
        }
        
        if (smsNotification.getBodyMessage()==null || smsNotification.getBodyMessage().isEmpty()) {
        	result.put("The body of the message is empty", "400");
        }
        
        if (!result.isEmpty()) {
        	return  result;
        } 
        PhoneNumber to = new PhoneNumber (smsNotification.getTo());
        PhoneNumber from = new PhoneNumber (smsNotification.getFrom());
        String bodyMessage= smsNotification.getBodyMessage();
        Message message = com.twilio.rest.api.v2010.account.Message.creator(to, from, bodyMessage).create();
        result.put("Message sent successfully", "200");
        logger.info("SMS Sent Successfully to{}", to);
        return result;
    	        // success result
} 

    
    

}
