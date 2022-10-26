package com.skilldistillery.daytrainer.trade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.skilldistillery.daytrainer.account.AccountRepository;
import com.skilldistillery.daytrainer.entities.Account;
import com.skilldistillery.daytrainer.entities.OrderType;
import com.skilldistillery.daytrainer.entities.StockPosition;
import com.skilldistillery.daytrainer.entities.Trade;
import com.skilldistillery.daytrainer.entities.User;
import com.skilldistillery.daytrainer.exceptions.InsufficientFundsException;
import com.skilldistillery.daytrainer.exceptions.InsufficientSharesException;
import com.skilldistillery.daytrainer.exceptions.TradeNotFoundException;
import com.skilldistillery.daytrainer.exceptions.UserNotAuthorizedException;
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
		Trade managedTrade = null;
		
		OrderType orderType = trade.getOrderType();
		if(orderType.isMarketOrder()) {
			managedTrade = createMarketTrade(username, trade);			
		}
	
		// TODO: implement logic for limit orders
		
		return managedTrade;
	}
		
	@Override
	public Trade createMarketTrade(String username, Trade trade) {
				
		User user = userRepository.findByUsername(username);
		trade.setUser(user);
				
		if(trade.isBuyOrder()) {
			executeBuyOrder(trade);
		}else {
			executeSellOrder(trade);
		}		
		
		return trade;
	}
	
	@Override
	public void executeBuyOrder(Trade trade){
		User user = trade.getUser();
		
		if(!user.hasSufficientFunds(trade)) {
			throw new InsufficientFundsException();
		}
		
		Account account = user.getAccount();
		account.prossesBuyOrder(trade);
		accountRepository.saveAndFlush(account);
		
		trade.setCompletionDate(LocalDateTime.now());
		tradeRepository.saveAndFlush(trade);
	}
	
	@Override
	public void executeSellOrder(Trade trade) {
		User user = trade.getUser();
		String username = user.getUsername();
		String stockSymbol = trade.getStock().getSymbol();
		int numberOfSharesToSell = trade.getQuantity();
		int availableShares = getNumberOfSharesOfStockByUser(username, stockSymbol);
		
		if(numberOfSharesToSell > availableShares) {
			throw new InsufficientSharesException();
		}
		
		Account account = user.getAccount();
		account.proccessSellOrder(trade);
		accountRepository.saveAndFlush(account);
		
		trade.setCompletionDate(LocalDateTime.now());
		tradeRepository.saveAndFlush(trade);
	}
	
	
	
	private int getNumberOfSharesOfStockByUser(String username, String symbol) {
		List<Trade> trades = tradeRepository.getUserTradesByStock(username, symbol);
		
		int positionSize = 0;
		for(Trade trade : trades) {
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
		User user = userRepository.findByUsername(username);
		int userId = user.getId();
		Integer numberOfAvailableShares = tradeRepository.getNumberOfAvailableShares(userId, ticker);
		List<Trade> buyOrders = tradeRepository.getPreviousBuyOrders(userId, ticker);
		
		double averageCost = getAverageCostPerShare(buyOrders, numberOfAvailableShares);
		
		return new StockPosition(ticker, numberOfAvailableShares, averageCost, 0);
	}
	
	@Override
	public double getAverageCostPerShare(List<Trade> buyOrders, Integer numberOfShares) {
		int totalNumber = numberOfShares;
		double averageCost = 0;
		for(int i = 0; i < buyOrders.size() && numberOfShares > 0; ++i) {
			Trade trade = buyOrders.get(i);
			int quantity = trade.getQuantity();
			int cur = Math.min(quantity , numberOfShares );
			averageCost += trade.getPricePerShare() * cur;
			numberOfShares -= cur;
		}
		return averageCost / totalNumber;
	}
}
