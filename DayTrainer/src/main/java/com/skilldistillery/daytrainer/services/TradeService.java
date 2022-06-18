package com.skilldistillery.daytrainer.services;

import java.util.Collection;
import java.util.List;

import com.skilldistillery.daytrainer.entities.Position;
import com.skilldistillery.daytrainer.entities.StockPosition;
import com.skilldistillery.daytrainer.entities.Trade;

public interface TradeService {

	public List<Trade> getUserTrades(String username);

	public Trade getTradeById(int tid);

	public Trade createMarketTrade(String username, Trade trade);

	public StockPosition getUserPosition(String username, String ticker);

	List<StockPosition> getUserPositions(String username);
	
}
