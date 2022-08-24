package com.skilldistillery.daytrainer.services;

import java.util.List;

import com.skilldistillery.daytrainer.entities.Stock;
import com.skilldistillery.daytrainer.entities.Trade;

public interface StockService {

	public Stock getStock(Stock stock);

	public Stock getStockBySymbol(String username, String symbol);

	public List<Stock> getAllStocks();
}
