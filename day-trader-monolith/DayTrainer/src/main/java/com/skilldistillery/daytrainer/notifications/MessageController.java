package com.skilldistillery.daytrainer.notifications;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/email")
public class MessageController {
	
	@Autowired
	public NotificationService notificationService;
	
	@GetMapping
	public void sendTextNotification(@RequestBody EmailDTO email) {
		try {
			notificationService.forwardToPhone(email);	
			notificationService.forwardToEmail(email);
		}
		catch(Exception e) {
			log.error(e.getMessage());
		}
		
	}
}
