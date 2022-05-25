package com.skilldistillery.daytrainer.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.skilldistillery.daytrainer.entities.Trade;
import com.skilldistillery.daytrainer.repository.TradeRepository;

public interface TradeService {

	List<Trade> getUserTrades(String username);

	Trade getTradeById(int tid);

	Trade createTrade(String username, Trade trade);

	
	
}
