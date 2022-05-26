package com.skilldistillery.daytrainer.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.daytrainer.entities.Stock;
import com.skilldistillery.daytrainer.entities.Trade;
import com.skilldistillery.daytrainer.services.StockService;

@RestController
@RequestMapping("api")
public class StockController {
	
	@Autowired 
	private StockService stockService;
	
	@GetMapping("stocks")
	public List<Stock> getAll(HttpServletResponse response, Principal principal){
		return stockService.getAllStocks();
	}
	
	@GetMapping("stocks/{symbol}")
	public Stock getNumberOfShares(@PathVariable String symbol, Principal principal) {
		String username = principal.getName();
		return stockService.getStockBySymbol(username, symbol);
		
	}

}
