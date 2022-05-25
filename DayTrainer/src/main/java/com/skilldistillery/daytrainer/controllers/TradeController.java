package com.skilldistillery.daytrainer.controllers;


import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.daytrainer.entities.Trade;
import com.skilldistillery.daytrainer.services.TradeService;


@RestController
@RequestMapping("api")
public class TradeController {
	
	@Autowired
	private TradeService tradeSvc;
	
	@GetMapping("trades")
	public List<Trade> getUserTrades( @RequestHeader HttpHeaders header){
		String username = extractUserName(header);
		return tradeSvc.getUserTrades(username);
	}
	
	public String extractUserName(HttpHeaders header) {
		String encoded = header.getFirst("authorization").split(" ")[1];
		byte[] decodedBytes = Base64.getDecoder().decode(encoded);
		String decodedString = new String(decodedBytes);
		String[] credentials = decodedString.split(":");
		String username = credentials[0];
		return username;
		
	}
	

}
