package com.skilldistillery.daytrainer.services;
import java.util.Collection;
import java.util.List;

import com.skilldistillery.daytrainer.entities.Position;
import com.skilldistillery.daytrainer.entities.StockPosition;
import com.skilldistillery.daytrainer.entities.Trade;

public interface TradeService {

	List<Trade> getUserTrades(String username);

	Trade getTradeById(int tid);

	Trade createMarketTrade(String username, Trade trade);

	Collection<Position> getUserPortfolio(String username);

	StockPosition getUserPosition(String username, String ticker);
	
}
