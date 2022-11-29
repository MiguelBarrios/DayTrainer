package com.skilldistillery.daytrainer.trade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {

	private final TradeRepository tradeRepository;
	
	private final TradePaginationRepository tradePaginationRepository;
	
	private final UserRepository userRepository;
	
	private final AccountRepository accountRepository;
	
	private final TDAService tdaService;
	
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
		else {
			throw new RuntimeException("Unsuported Trade");
		}
	
		
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
		log.info("Trade placed: " + trade);
		
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
		int availableShares = tradeRepository.getNumberOfAvailableShares(user.getId(), stockSymbol);
		
		if(numberOfSharesToSell > availableShares) {
			throw new InsufficientSharesException();
		}
		
		Account account = user.getAccount();
		account.proccessSellOrder(trade);
		accountRepository.saveAndFlush(account);
		
		trade.setCompletionDate(LocalDateTime.now());
		tradeRepository.saveAndFlush(trade);
	}
	

	@Override
	public List<StockPosition> getUserPositions(String username){
		List<String> symbols = tradeRepository.getUserStocks(username);
		List<StockPosition> positions = getPositions(symbols, username);
		return positions;
	}
	
	public List<StockPosition> getPositions(List<String> symbols, String username){
		List<StockPosition> positions = new ArrayList<>();
		
		for(String symbol : symbols) {
			StockPosition pos = getUserPosition(username, symbol);
			if(pos.getNumberOfShares() > 0) {
				Double lastPrice = getLastPrice(symbol);
				pos.setLastPrice(lastPrice);
				positions.add(pos);
			}
		}
		return positions;
	}
	
	//TODO: move to tda service
	public Double getLastPrice(String symbol) {
		Double res = -1.0;
		String quote = this.tdaService.getQuote(symbol);
		// Get Quote, check if quote is present
		ObjectNode node;
		try {
			node = new ObjectMapper().readValue(quote, ObjectNode.class);
			if (node.has("lastPrice")) {
				String lastPrice =  node.get("lastPrice").toString();
				res = Double.parseDouble(lastPrice);
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	
	@Override
	public StockPosition getUserPosition(String username, String ticker) {
		User user = userRepository.findByUsername(username);
		int userId = user.getId();
		Integer numberOfAvailableShares = tradeRepository.getNumberOfAvailableShares(userId, ticker);
		List<Trade> buyOrders = tradeRepository.getPreviousBuyOrders(userId, ticker);
		if(buyOrders.size() == 0) {
			return new StockPosition(ticker, 0, 0, 0);
		}
		
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
