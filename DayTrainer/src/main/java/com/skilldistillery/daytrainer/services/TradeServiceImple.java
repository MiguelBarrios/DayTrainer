package com.skilldistillery.daytrainer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.daytrainer.entities.Trade;
import com.skilldistillery.daytrainer.entities.User;
import com.skilldistillery.daytrainer.repository.TradeRepository;
import com.skilldistillery.daytrainer.repository.UserRepository;

@Service
public class TradeServiceImple implements TradeService {

	
	@Autowired
	private TradeRepository tradeRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	
	@Override
	public List<Trade> getUserTrades(String username) {
		return tradeRepo.getUserTrades(username);
	}
	
	@Override
	public Trade getTradeById(int tid) {
		Optional<Trade> option = tradeRepo.findById(tid);
		return (option.isPresent()) ? option.get() : null;
	}
	
	@Override
	public Trade createTrade(String username, Trade trade) {
		User user = userRepo.findByUsername(username);
		trade.setUser(user);
		tradeRepo.saveAndFlush(trade);
		return trade;
	}
	
	
}
