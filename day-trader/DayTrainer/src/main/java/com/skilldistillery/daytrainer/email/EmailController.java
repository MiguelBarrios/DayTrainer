package com.skilldistillery.daytrainer.email;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin({ "*", "http://localhost" })
public class EmailController {
	
	@Autowired
	public EmailService emailService;

	@PutMapping("email")
	public void sendMessage(@RequestBody Message message, HttpServletResponse  response) {		
		try {
			emailService.sendEmail(message);	
		}
		catch(Exception e) {
			e.printStackTrace();
			response.setStatus(404);
		}
	}
}
