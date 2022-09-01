package com.skilldistillery.daytrainer.email;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.skilldistillery.daytrainer.Config;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	public boolean sendEmail(Message data) throws AddressException, MessagingException {
		

        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Config.getUsername(), Config.getPassword());
                    }
                });

        try {

        	MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("miguelb.server@gmail.com"));
            message.setRecipients(
            		MimeMessage.RecipientType.TO,
                    InternetAddress.parse("miguelbarrios67@gmail.com")
            );
            message.setSubject("Message From: " + data.name);
            message.setText(data.getMessage() + "\n\n" 
            		+ "from: " + data.email);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
	}

}
