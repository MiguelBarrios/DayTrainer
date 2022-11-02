package com.skilldistillery.daytrainer.trade;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.skilldistillery.daytrainer.entities.StockPosition;
import com.skilldistillery.daytrainer.entities.Trade;
import com.skilldistillery.daytrainer.exceptions.TradeNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("api/v1/trades")
@CrossOrigin({ "*", "http://localhost" })
public class TradeController {
	
	@Autowired
	private TradeService tradeService;
	
	
	@GetMapping
	public List<Trade> getUserTrades(Principal principal){
		String username = principal.getName();
		return tradeService.getUserTrades(username);
	}
	
	@GetMapping("{tid}")
	public Trade getTradeById(@PathVariable Integer tid, HttpServletResponse response, Principal principal) {
		String username = principal.getName();
		Trade trade = tradeService.getTradeById(tid, username);
		return trade;
	}
	
	@PostMapping
	public Trade placeTrade(@RequestBody Trade trade, HttpServletResponse response, Principal principal) {
		
		String orderType = trade.getOrderType().getName();
		if(orderType.equals("Market")){
			trade = tradeService.placeTrade(principal.getName(), trade);

		}else {
			//TODO: Place limit order
		}
		
		return trade;
	}
	
	
	@GetMapping("position/{ticker}")
	public StockPosition getUserStockPosition(Principal principal, @PathVariable String ticker) {
		String username = principal.getName();
		StockPosition pos = tradeService.getUserPosition(username, ticker);
		return pos;
	}
	
	
	@GetMapping("position")
	public List<StockPosition> getUserStockPositions(Principal principal){
		return tradeService.getUserPositions(principal.getName());
	}
	
}
