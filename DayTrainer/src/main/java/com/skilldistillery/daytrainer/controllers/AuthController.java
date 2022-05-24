package com.skilldistillery.daytrainer.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.daytrainer.entities.User;
import com.skilldistillery.daytrainer.services.AuthService;


@RestController
@CrossOrigin({"*","http://localhost"})
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping(path = "/register")
	public User register(@RequestBody User user, HttpServletResponse res) {
	    if (user == null) {
	        res.setStatus(400);
	    }
	    user = authService.register(user);
	    return user;
	}

	@GetMapping(path = "/authenticate")
	public User authenticate(Principal principal) {
	    return authService.getUserByUsername(principal.getName());
	}

}
