package com.skilldistillery.daytrainer.services;

import java.util.List;

import com.skilldistillery.daytrainer.entities.Stock;
import com.skilldistillery.daytrainer.entities.Trade;

public interface StockService {

	Stock getStock(Stock stock);

	Stock getStockBySymbol(String username, String symbol);

	List<Stock> getAllStocks();
}
