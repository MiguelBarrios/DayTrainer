package com.skilldistillery.daytrainer.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.daytrainer.entities.Stock;
import com.skilldistillery.daytrainer.repository.StockRepository;

@Service
public class StockServiceImple implements StockService {
	
	@Autowired
	private StockRepository stockRepo;
	
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
	

}
