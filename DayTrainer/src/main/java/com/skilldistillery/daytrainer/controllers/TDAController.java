package com.skilldistillery.daytrainer.controllers;

import java.security.Principal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@CrossOrigin({ "*", "http://localhost" })
public class TDAController {
	
	@GetMapping("tda/quote/{symbol}")
	public void getQuote(Principal principal, @PathVariable String symbol) {
		System.out.println("Success: in TDA controller");
		
	}
}
