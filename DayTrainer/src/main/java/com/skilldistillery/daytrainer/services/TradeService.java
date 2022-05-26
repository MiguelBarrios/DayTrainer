package com.skilldistillery.daytrainer.services;
import java.util.List;
import com.skilldistillery.daytrainer.entities.Trade;

public interface TradeService {

	List<Trade> getUserTrades(String username);

	Trade getTradeById(int tid);

	Trade createTrade(String username, Trade trade);

	
	
}
