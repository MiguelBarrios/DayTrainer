package com.miguelbarrios.tradeservice.services;

import com.miguelbarrios.tradeservice.models.StockPosition;
import com.miguelbarrios.tradeservice.models.Trade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeServiceImpl implements TradeService{
    @Override
    public List<Trade> getUserTrades(String username) {
        return null;
    }

    @Override
    public List<Trade> getUserTradesPaginated(String username, int pageNumber, int pageSize) {
        return null;
    }

    @Override
    public Trade placeTrade(String username, Trade trade) {
        return null;
    }

    @Override
    public StockPosition getUserStockPosition(String username, String ticker) {
        return null;
    }

    @Override
    public List<StockPosition> getUserStockPositions(String username) {
        return null;
    }
}
