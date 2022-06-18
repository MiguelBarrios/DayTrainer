package com.skilldistillery.daytrainer.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.daytrainer.services.AccountService;

@RequestMapping("api")
@RestController
@Configuration
@CrossOrigin({ "*", "http://localhost" })
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("account/depositsum")
	public Double getTotalDeposited(Principal principal) {
		return accountService.getAccountDeposits(principal.getName());
	}

}
