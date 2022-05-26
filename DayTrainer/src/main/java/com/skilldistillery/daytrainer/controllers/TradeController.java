package com.skilldistillery.daytrainer.controllers;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.daytrainer.entities.Trade;
import com.skilldistillery.daytrainer.services.TradeService;


@RestController
@RequestMapping("api")
public class TradeController {
	
	@Autowired
	private TradeService tradeService;
	
	@GetMapping("trades")
	public List<Trade> getUserTrades( @RequestHeader HttpHeaders header){
		String username = getUserName(header);
		return tradeService.getUserTrades(username);
	}
	
	@GetMapping("trades/{tid}")
	public Trade getTradeById(@PathVariable Integer tid, HttpServletResponse response) {
		
		Trade trade = tradeService.getTradeById(tid);
		if(trade == null) {
			response.setStatus(404);
		}
		
		return trade;
	}
	
	@PostMapping("trades")
	public Trade create(@RequestHeader HttpHeaders header, @RequestBody Trade trade, HttpServletResponse response) {
		//TODO: PERSIST STOCK SYMBOL IF NOT PRESENT
		//TODO: SET COMPLETED DATE
		//TODO: IF SELL CHECK IF USER HAS SHARES
		//TODO: IF SELL CREDIT MONEY TO USER'S ACCOUNT
		//TODO: IF BUY CHECK IF USER HAS ENOUGH FUNDS
		//TODO: IF BUY SUBTRACT MONEY FROM USERS ACCOUNT
		String username = getUserName(header);
		Trade res = tradeService.createTrade(username, trade);
		if(res == null) {
			response.setStatus(404);
		}
		return res;
	}
	
	@GetMapping("trades/{symbol}")
	public void getNumberOfShares(@PathVariable String symbol, @RequestHeader HttpHeaders header ) {
		String username = getUserName(header);
		//TODO: FINISH
	}
	
	public String getUserName(HttpHeaders header) {
		String encoded = header.getFirst("authorization").split(" ")[1];
		byte[] decodedBytes = Base64.getDecoder().decode(encoded);
		String decodedString = new String(decodedBytes);
		String[] credentials = decodedString.split(":");
		String username = credentials[0];
		return username;	
	}
	

}
