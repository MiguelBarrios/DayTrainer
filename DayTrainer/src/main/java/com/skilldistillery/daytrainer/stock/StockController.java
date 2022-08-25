package com.skilldistillery.daytrainer.stock;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.daytrainer.entities.Stock;

@RestController
@RequestMapping("api")
@CrossOrigin({ "*", "http://localhost" })
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
	
	@GetMapping("stocks/search/{pattern}")
	public List<String> searchStocks(@PathVariable String pattern, Principal principal){
		
		return null;
	}
	

}
