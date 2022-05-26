package com.skilldistillery.daytrainer.controllers;

import java.security.Principal;
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
	public List<Trade> getUserTrades(Principal principal){
		String username = principal.getName();
		return tradeService.getUserTrades(username);
	}
	
	@GetMapping("trades/{tid}")
	public Trade getTradeById(@PathVariable Integer tid, HttpServletResponse response, Principal principal) {
		
		Trade trade = tradeService.getTradeById(tid);
		if(trade == null) {
			response.setStatus(404);
		}
		
		return trade;
	}
	
	@PostMapping("trades")
	public Trade create(@RequestHeader HttpHeaders header, @RequestBody Trade trade, HttpServletResponse response, Principal principal) {
		
		
		
//		String username = principal.getName();
//		Trade res = tradeService.createTrade(username, trade);
//		if(res == null) {
//			response.setStatus(404);
//		}
//		return res;
		return null;
	}
	
	@GetMapping("trades/stock/{symbol}")
	public void getNumberOfShares(@PathVariable String symbol, Principal principal) {
		String username = principal.getName();
		//TODO: FINISH
	}

}
