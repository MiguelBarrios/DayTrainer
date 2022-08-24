package com.skilldistillery.daytrainer.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.skilldistillery.daytrainer.entities.StockPosition;
import com.skilldistillery.daytrainer.entities.Trade;
import com.skilldistillery.daytrainer.services.StockService;
import com.skilldistillery.daytrainer.services.TDAService;
import com.skilldistillery.daytrainer.services.TradeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api")
@CrossOrigin({ "*", "http://localhost" })
public class TradeController {
	
	@Autowired
	private TradeService tradeService;
	
	@Autowired 
	private StockService stockService;
	
	
	
	
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
	public Trade create(@RequestBody Trade trade, HttpServletResponse response, Principal principal) {
		System.out.println("TEst");
		
		String orderType = trade.getOrderType().getName();
		if(orderType.equals("Market")){
			trade = tradeService.createMarketTrade(principal.getName(),trade);
			if(trade == null) {
				response.setStatus(401);
			}
		}else {
			//TODO: Place limit order
		}
		
		return trade;
	}
	
	
	@GetMapping("trades/position/{ticker}")
	public StockPosition getUserStockPosition(Principal principal, @PathVariable String ticker) {
		StockPosition pos = tradeService.getUserPosition(principal.getName(), ticker);
		return pos;
	}
	
	@GetMapping("trades/position")
	public List<StockPosition> getUserStockPositions(Principal principal){
		return tradeService.getUserPositions(principal.getName());
	}
	
	@GetMapping("trades/users/{username}")
	public List<Trade> friendsTrades(Principal principal, @PathVariable String username){
		return tradeService.getUserTrades(username);
	}
	

}
