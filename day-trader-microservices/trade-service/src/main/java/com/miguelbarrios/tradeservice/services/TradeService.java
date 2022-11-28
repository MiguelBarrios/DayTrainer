package com.miguelbarrios.tradeservice.services;


import com.miguelbarrios.tradeservice.models.StockPosition;
import com.miguelbarrios.tradeservice.models.Trade;

import java.util.List;

public interface TradeService {

    public List<Trade> getUserTrades(String username);

    List<Trade> getUserTradesPaginated(String username, int pageNumber, int pageSize);

    Trade placeTrade(String username, Trade trade);

    public StockPosition getUserStockPosition(String username, String ticker);

    List<StockPosition> getUserStockPositions(String username);
}