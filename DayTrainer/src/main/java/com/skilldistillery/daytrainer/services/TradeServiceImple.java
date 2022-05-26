package com.skilldistillery.daytrainer.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.daytrainer.entities.Account;
import com.skilldistillery.daytrainer.entities.OrderType;
import com.skilldistillery.daytrainer.entities.Stock;
import com.skilldistillery.daytrainer.entities.Trade;
import com.skilldistillery.daytrainer.entities.User;
import com.skilldistillery.daytrainer.repository.StockRepository;
import com.skilldistillery.daytrainer.repository.TradeRepository;
import com.skilldistillery.daytrainer.repository.UserRepository;

@Service
public class TradeServiceImple implements TradeService {

	
	@Autowired
	private TradeRepository tradeRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private StockRepository stockRepo;
	
	
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

		//TODO: PERSIST STOCK SYMBOL IF NOT PRESENT
		String stockname = trade.getStock().getName();
		
		Optional<Stock> option = stockRepo.findById(stockname);
		Stock stock = option.isPresent() ? option.get() : null;
		
		if(stock == null) {
			stock = trade.getStock();
			stockRepo.saveAndFlush(stock);
		}
		

		Account account = user.getAccount();
		double balance = account.getBalance();
		
		//TODO: IF SELL ORDER CHECK IF USER HAS SHARES
		if(trade.isBuy()) {
			double totalCost = trade.getPricePerShare() * trade.getQuantity();
			if(totalCost < balance) {
				
			}
			
			
			//TODO: Check to see if User has enough funds
			//TODO: SUBTRACT MONEY FROM USERS ACCOUNT

			
		}else {//Sell order
			//TODO: Check to see if use has enough shares
			//TODO: IF SELL CREDIT MONEY TO USER'S ACCOUNT
			
		}
		
		OrderType order = trade.getOrderType();
		if(order.getName() == "Market") {
			//TODO: SET COMPLETED DATE
			trade.setCompletionDate(LocalDateTime.now());
		}
		
		
		
		
		
		
		
		
		
		trade.setUser(user);
		tradeRepo.saveAndFlush(trade);
		return trade;
	}
	
	
}
