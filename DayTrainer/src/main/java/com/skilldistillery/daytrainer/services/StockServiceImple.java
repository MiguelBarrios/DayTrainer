package com.skilldistillery.daytrainer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.daytrainer.entities.Stock;
import com.skilldistillery.daytrainer.entities.Trade;
import com.skilldistillery.daytrainer.entities.User;
import com.skilldistillery.daytrainer.repository.StockRepository;
import com.skilldistillery.daytrainer.repository.UserRepository;

@Service
public class StockServiceImple implements StockService {
	
	@Autowired
	private StockRepository stockRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public Stock getStock(Stock stock) {
		Optional<Stock> option = stockRepo.findById(stock.getSymbol());
		if(!option.isPresent()) {
			stockRepo.saveAndFlush(stock);
			return stock;
		}else {
			return option.get();
		}
	}
	
	@Override
	public Stock getStockBySymbol(String username, String symbol) {
		User currentUser = userRepo.findByUsername(username);
		Optional<Stock> op =  stockRepo.findById(symbol);
		Stock stockFound = op.get();
		if(currentUser != null && stockFound !=null) {
			return stockFound;
		}
		return null;
	}

	@Override
	public List<Stock> getAllStocks() {
		return stockRepo.findAll();
	}
	

}
