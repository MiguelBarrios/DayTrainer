package com.skilldistillery.daytrainer.notifications;

import java.io.IOException;

import com.sendgrid.*;
import com.skilldistillery.daytrainer.Config;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
	
	public static final String ACCOUNT_SID = "AC58078f261a10c8f3fd80dacee48d7cdb"; 
    public static final String TO_NUMBER = "+15808171592";
    public static final String FROM_MESSAGE_SID = "MG91d7c7962c5bf096da39b1a4a55ba30f";
    
    @Autowired
    private Environment env;
    

	public void forwardToPhone(EmailDTO email) {
		
		String token = env.getProperty("twillio.authtoken");
		
        Twilio.init(ACCOUNT_SID, token); 
        Message message = Message.creator( 
                new com.twilio.type.PhoneNumber(TO_NUMBER),  
                FROM_MESSAGE_SID, 
                email.toString())      
            .create();  
        
        log.info(message.getSid()); 
	}
	
	public void forwardToEmail(EmailDTO emailDto) {
	    Email from = new Email("miguelb.services@gmail.com");
	    String subject = "Message from personal website";
	    Email to = new Email("miguelbarriosdavila@gmail.com");
	    Content content = new Content("text/plain", 
	    			"Sender email: " + emailDto.emailAddress + 
	    			"\nName: " + emailDto.name +
	    			"\n" + emailDto.message
	    		);
	    Mail mail = new Mail(from, subject, to, content);

	    SendGrid sg = new SendGrid(Config.getSendGridKey());
	    Request request = new Request();
	    try {
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(mail.build());
	      Response response = sg.api(request);
	      System.out.println(response.getStatusCode());
	      System.out.println(response.getBody());
	      System.out.println(response.getHeaders());
	    } catch (Exception e) {
	    	log.error(e.getMessage());
	    }
	}

}
