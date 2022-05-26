package com.skilldistillery.daytrainer.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.daytrainer.entities.Account;
import com.skilldistillery.daytrainer.entities.Stock;
import com.skilldistillery.daytrainer.entities.Trade;
import com.skilldistillery.daytrainer.entities.User;
import com.skilldistillery.daytrainer.services.StockService;
import com.skilldistillery.daytrainer.services.TradeService;
import com.skilldistillery.daytrainer.services.UserService;


@RestController
@RequestMapping("api")
public class TradeController {
	
	@Autowired
	private TradeService tradeService;
	
	@Autowired 
	private StockService stockService;
	
	@Autowired
	private UserService userService;
	
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
		
		//PERSIST STOCK SYMBOL IF NOT PRESENT
		Stock stock = trade.getStock();
		stock = stockService.getStock(stock);
		
		User user = userService.getUserByUsername(principal.getName());
		System.out.println(user);
		System.out.println(user.getAccount());
		Account account = user.getAccount();
		System.out.println(account);
//		double balance = account.getBalance();
//		System.out.println(account);
//		
//		OrderType orderType = trade.getOrderType();
//		if(orderType.getName().equals("Market")) {
//			if(trade.isBuy()) {
//				double totalCost = trade.getPricePerShare() * trade.getPricePerShare();
//				if(totalCost < balance) {
//					//Determine status codes for incuficient funds
//				}else {
//					//User has enough funds for trade to execute
//					Trade executedTrade = tradeService.createMarketTrade(principal.getName(), trade);
//					return executedTrade;
//				}
//			}else {
//				
//			}
//		}
//		else {
//			
//		}
//		
		
//		String username = principal.getName();
//		Trade res = tradeService.createTrade(username, trade);
//		if(res == null) {
//			response.setStatus(404);
//		}
//		return res;
		return null;
	}

}
