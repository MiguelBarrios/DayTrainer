package com.skilldistillery.daytrainer.trade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.skilldistillery.daytrainer.account.AccountRepository;
import com.skilldistillery.daytrainer.entities.Account;
import com.skilldistillery.daytrainer.entities.QuoteWrapper;
import com.skilldistillery.daytrainer.entities.Stock;
import com.skilldistillery.daytrainer.entities.StockPosition;
import com.skilldistillery.daytrainer.entities.Trade;
import com.skilldistillery.daytrainer.entities.User;
import com.skilldistillery.daytrainer.stock.StockRepository;
import com.skilldistillery.daytrainer.stock.StockService;
import com.skilldistillery.daytrainer.tda.TDAService;
import com.skilldistillery.daytrainer.user.UserRepository;

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
	
	@Autowired
	private TDAService tdaService;
	
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
			System.out.println("*" + trade);
			double updatedBalance = account.getBalance() - (trade.getPricePerShare() * trade.getQuantity());
			account.setBalance(updatedBalance);
			accountRepo.saveAndFlush(account);
			trade.setCompletionDate(LocalDateTime.now());
			tradeRepo.saveAndFlush(trade);
			
		}else {
			System.out.println("User sold shares: " + stock);
			//Check if user Has shares
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

		return positionSize;
	
	}
	
	@Override
	public List<StockPosition> getUserPositions(String username){
		List<String> stocks = tradeRepo.getUserStocks(username);
		List<StockPosition> positions = new ArrayList<>();
		for(String stock : stocks) {
			StockPosition pos = this.getUserPosition(username, stock);
			if(pos.getNumberOfShares() > 0)
				positions.add(pos);
		}
		
		// Add Last price to Position
		for(StockPosition pos : positions) {
			String symbol = pos.getSymbol();
			String quote = this.tdaService.getQuote(symbol);
			// Get Quote, check if quote is present
			ObjectNode node;
			try {
				node = new ObjectMapper().readValue(quote, ObjectNode.class);
				if (node.has("lastPrice")) {
					String lastPrice =  node.get("lastPrice").toString();
					pos.setLastPrice(Double.parseDouble(lastPrice));
					
				}else {
					System.err.println("lastPrice" + " not found");
					pos.setLastPrice(-1);
				}
			}  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
  
		return positions;
	}
	
	@Override
	public StockPosition getUserPosition(String username, String ticker) {
		List<Trade> purchases = tradeRepo.getUserStockPurchases(username, ticker);
		if(purchases.size() == 0) {
			return new StockPosition(ticker, 0,0, 0);
		}
		
		Integer sharesSold = tradeRepo.getNumSharesSold(username, ticker);
		if(sharesSold == null) {
			sharesSold = 0;
		}

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
		return new StockPosition(ticker, remainingShares, avgCostPerShare, 0);
	}
	
	
	
	
	

	
}
