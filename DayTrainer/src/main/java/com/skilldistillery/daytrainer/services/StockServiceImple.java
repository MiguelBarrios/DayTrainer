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
	
	private Stock getStock(Stock stock) {
		Optional<Stock> option = stockRepo.findById(stock.getName());
		Stock managed = option.isPresent() ?  option.get() : null;
		
		if(managed == null) {
			stockRepo.saveAndFlush(managed);
		}
		
		
		return managed;
	}
	

}
