package com.skilldistillery.daytrainer.trade;

import java.util.List;

import com.skilldistillery.daytrainer.entities.StockPosition;
import com.skilldistillery.daytrainer.entities.Trade;
import com.skilldistillery.daytrainer.entities.User;

public interface TradeService {

	public List<Trade> getUserTrades(String username);

	public Trade getTradeById(int tid, String username);

	public Trade createMarketTrade(String username, Trade trade);

	public StockPosition getUserPosition(String username, String ticker);

	List<StockPosition> getUserPositions(String username);

	List<Trade> getUserTradesPagnated(String username, int pageNumber, int pageSize);

	Integer getNumUserTrades(String username);

	Trade placeTrade(String username, Trade trade);

	void executeBuyOrder(Trade trade);

	void executeSellOrder(Trade trade);

	double getAverageCostPerShare(List<Trade> buyOrders, Integer numberOfShares);

}
