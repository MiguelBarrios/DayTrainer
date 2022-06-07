package com.skilldistillery.daytrainer.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.daytrainer.services.TDAService;

@RestController
@RequestMapping("api")
@CrossOrigin({ "*", "http://localhost" })
public class TDAController {
	
	@Autowired
	private TDAService tdaService;
	
	@GetMapping("tda/quote/{symbol}")
	public void getQuote(Principal principal, @PathVariable String symbol) {
		System.out.println("Success: in TDA controller");
		String res = tdaService.getQuote(symbol);
		System.out.println(res);
		
	}
}
