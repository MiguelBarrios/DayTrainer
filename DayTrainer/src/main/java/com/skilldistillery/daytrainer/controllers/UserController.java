package com.skilldistillery.daytrainer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.daytrainer.services.UserService;

@RestController
@CrossOrigin({"*", "http://localhost"})
public class UserController {
	
	@Autowired
	private UserService userSvc;
	
}
