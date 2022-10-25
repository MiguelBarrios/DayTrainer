package com.skilldistillery.daytrainer.trade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.skilldistillery.daytrainer.account.AccountRepository;
import com.skilldistillery.daytrainer.entities.Account;
import com.skilldistillery.daytrainer.entities.Stock;
import com.skilldistillery.daytrainer.entities.StockPosition;
import com.skilldistillery.daytrainer.entities.Trade;
import com.skilldistillery.daytrainer.entities.User;
import com.skilldistillery.daytrainer.exceptions.TradeNotFoundException;
import com.skilldistillery.daytrainer.exceptions.UserNotAuthorizedException;
import com.skilldistillery.daytrainer.stock.StockService;
import com.skilldistillery.daytrainer.tda.TDAService;
import com.skilldistillery.daytrainer.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TradeServiceImpl implements TradeService {

	@Autowired
	private TradeRepository tradeRepository;
	
	@Autowired
	private TradePaginationRepository tradePaginationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private StockService stockService;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private TDAService tdaService;
	
	@Override
	public List<Trade> getUserTrades(String username) {
		return tradeRepository.getUserTrades(username);
	}
	
	@Override
	public Integer getNumUserTrades(String username) {
		return tradeRepository.getNumUserTrades(username);
	}
	
	@Override
	public List<Trade> getUserTradesPagnated(String username, int pageNumber, int pageSize){
		User user = userRepository.findByUsername(username);
		Pageable pages = PageRequest.of(pageNumber, pageSize);
		return tradePaginationRepository.findByUser_id(user.getId(), pages);
	}
	
	@Override
	public Trade getTradeById(int tid, String username) {
		
		Trade trade = tradeRepository.findById(tid)
			.orElseThrow(() -> new TradeNotFoundException(String.format("Trade with %d not found", tid)) );
		
		String authorizedUser = trade.getUser().getUsername();
		if(!username.equals(authorizedUser)) {
			throw new UserNotAuthorizedException("Unauthorized");
		}
		
		return trade;
		
	}
	
	
	
	@Override
	public Trade placeTrade(String username, Trade trade) {
		String orderType = trade.getOrderType().getName();
		if(orderType.equals("Market")) {
			Trade managedTrade = createMarketTrade(username, trade);
			return managedTrade;
			
		}
		else if(orderType.equals("Limit")) {
			return null;
		}
		else {
			return null;
		}
	}
	
	@Override
	public Trade createMarketTrade(String username, Trade trade) {
		
		
	
		//PERSIST STOCK SYMBOL IF NOT PRESENT
		Stock stock = trade.getStock();
		stock = stockService.getStock(stock);
		
		User user = userRepository.findByUsername(username);
		trade.setUser(user);
		
		Account account = user.getAccount();
		
		if(trade.isBuy()) {
//			System.out.println("User bought shares: " + stock);
//			System.out.println("*" + trade);
			double updatedBalance = account.getBalance() - (trade.getPricePerShare() * trade.getQuantity());
			account.setBalance(updatedBalance);
			accountRepository.saveAndFlush(account);
			trade.setCompletionDate(LocalDateTime.now());
			tradeRepository.saveAndFlush(trade);
			
		}else {
//			System.out.println("User sold shares: " + stock);
			//Check if user Has shares
			int positionSize = this.getCurrentHolding(username, stock.getSymbol());
			if(trade.getQuantity() > positionSize) {
//				System.err.println("User tried to sell stock he does not have");
				return null;
			}else {
				double updatedBalance = account.getBalance() + (trade.getPricePerShare() * trade.getQuantity());
				account.setBalance(updatedBalance);
				accountRepository.saveAndFlush(account);
				trade.setCompletionDate(LocalDateTime.now());
				tradeRepository.saveAndFlush(trade);
			}

			
		}		
		return trade;
	}
	
	private int getCurrentHolding(String username, String symbol) {
		List<Trade> holding = tradeRepository.getUserTradesByStock(username, symbol);
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
		List<String> stocks = tradeRepository.getUserStocks(username);
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
//					System.err.println("lastPrice" + " not found");
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
		List<Trade> purchases = tradeRepository.getUserStockPurchases(username, ticker);
		if(purchases.size() == 0) {
			return new StockPosition(ticker, 0,0, 0);
		}
		
		Integer sharesSold = tradeRepository.getNumSharesSold(username, ticker);
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
