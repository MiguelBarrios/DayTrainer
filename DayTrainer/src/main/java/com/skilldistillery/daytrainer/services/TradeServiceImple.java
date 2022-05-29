package com.skilldistillery.daytrainer.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.daytrainer.entities.Account;
import com.skilldistillery.daytrainer.entities.Position;
import com.skilldistillery.daytrainer.entities.Stock;
import com.skilldistillery.daytrainer.entities.StockPosition;
import com.skilldistillery.daytrainer.entities.Trade;
import com.skilldistillery.daytrainer.entities.User;
import com.skilldistillery.daytrainer.repository.AccountRepository;
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
	
	@Autowired 
	private StockService stockService;
	
	@Autowired
	private AccountRepository accountRepo;
	
	
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
	public Trade createMarketTrade(String username, Trade trade) {
	
		//PERSIST STOCK SYMBOL IF NOT PRESENT
		Stock stock = trade.getStock();
		stock = stockService.getStock(stock);
		
		User user = userRepo.findByUsername(username);
		trade.setUser(user);
		
		Account account = user.getAccount();
		
		if(trade.isBuy()) {
			System.out.println("User bought shares: " + stock);
			double updatedBalance = account.getBalance() - (trade.getPricePerShare() * trade.getQuantity());
			account.setBalance(updatedBalance);
			accountRepo.saveAndFlush(account);
			trade.setCompletionDate(LocalDateTime.now());
			tradeRepo.saveAndFlush(trade);
			
		}else {
			System.out.println("User sold shares: " + stock);
			//Check if user Has shares
			//TODO: Brute force, come back and create query
			int positionSize = this.getCurrentHolding(username, stock.getSymbol());
			if(trade.getQuantity() > positionSize) {
				System.err.println("User tried to sell stock he does not have");
				return null;
			}else {
				double updatedBalance = account.getBalance() + (trade.getPricePerShare() * trade.getQuantity());
				account.setBalance(updatedBalance);
				accountRepo.saveAndFlush(account);
				trade.setCompletionDate(LocalDateTime.now());
				tradeRepo.saveAndFlush(trade);
			}

			
		}		
		return trade;
	}
	
	private int getCurrentHolding(String username, String symbol) {
		List<Trade> holding = tradeRepo.getUserTradesByStock(username, symbol);
		int positionSize = 0;
		for(Trade trade : holding) {
			if(trade.isBuy()) {
				positionSize += trade.getQuantity();
			}else {
				positionSize -= trade.getQuantity();
			}
		}
		System.out.println("Number of trades on: " + symbol);
		System.out.println("Position size: " + positionSize);
		return positionSize;
	
	}
	
	@Override
	public Collection<Position> getUserPortfolio(String username) {
		HashMap<String, Position> map = new HashMap<>();
		List<Trade> userTrades = tradeRepo.getUserTrades(username);
		for(Trade trade : userTrades) {
			String key = trade.getStock().getSymbol();
			if(!map.containsKey(key)) {
				Position pos = new Position(trade.getStock().getSymbol(), 0,0,0);
				map.put(key, pos);
			}
			
			Position cur = map.get(key);
			
			if(trade.isBuy()) {
				cur.setAmount(cur.getAmount() + trade.getQuantity());
				cur.setValue(cur.getValue() + trade.getQuantity());
			}else {
				cur.setAmount(cur.getAmount() - trade.getQuantity());
				cur.setValue(cur.getValue() - trade.getQuantity());
			}
		}
		Account account = accountRepo.getAccountByUsername(username);
		Position cash = new Position("cash", 0,0,0);
		cash.setAmount(1);
		cash.setValue(account.getBalance());
		map.put("cash", cash);
		
		Collection<Position> positions = map.values();
		Collection<Position> res = new ArrayList<>(positions.size());
		for(Position pos : positions) {
			if(pos.getAmount() != 0) {
				res.add(pos);
			}
		}
		
		return res;
		
	}
	
	@Override
	public StockPosition getUserPosition(String username, String ticker) {
		List<Trade> purchases = tradeRepo.getUserStockPurchases(username, ticker);
		int sharesSold = tradeRepo.getUserPositionSize(username, ticker);

		for(int i = 0; i < purchases.size(); ++i) {
			Trade cur = purchases.get(i);
			int amount = cur.getQuantity();
			if(sharesSold >= amount) {
				sharesSold -= amount;
				purchases.set(i, null);
			}
			else {
				cur.setQuantity(amount - sharesSold);
				sharesSold = 0;
			}
			
			if(sharesSold == 0) {
				break;
			}
		}
		
		List<Trade> remainingTrades = purchases.stream().filter((t) -> t != null).collect(Collectors.toList());
		
		int remainingShares = 0;
		double totalSpentOnShares = 0;
		for(Trade cur : remainingTrades) {
			remainingShares += cur.getQuantity();
			totalSpentOnShares += cur.getQuantity() * cur.getPricePerShare();
		}
		
		double avgCostPerShare = totalSpentOnShares / remainingShares;
		return new StockPosition(ticker, remainingShares, avgCostPerShare);
	}
	
	
	

	
}
