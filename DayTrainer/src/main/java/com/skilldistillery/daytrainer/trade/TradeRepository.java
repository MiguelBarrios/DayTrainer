package com.skilldistillery.daytrainer.trade;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skilldistillery.daytrainer.entities.Trade;

public interface TradeRepository extends JpaRepository<Trade, Integer>{
	
	@Query("SELECT t FROM Trade t WHERE t.user.username = :username")
	List<Trade> getUserTrades(@Param("username") String username);
	
	@Query("SELECT t FROM Trade t WHERE t.user.username = :username AND t.stock.symbol = :symbol")
	List<Trade> getUserTradesByStock(@Param("username") String username, @Param("symbol") String symbol);
	
	//TODO: change to completionDate when DB fixed
	@Query("SELECT t FROM Trade t WHERE t.user.username = :username AND t.stock.symbol = :symbol AND t.buy = true")
	List<Trade> getUserStockPurchases(@Param("username") String username, @Param("symbol") String symbol);
	
	@Query("SELECT t FROM Trade t WHERE t.user.username = :username AND t.stock.symbol = :symbol AND t.buy = false")
	List<Trade> getUserStockSells(@Param("username") String username, @Param("symbol") String symbol);

	@Query("SELECT SUM(t.quantity) FROM Trade t WHERE t.user.username = :username AND t.stock.symbol = :symbol AND t.buy = 0")
	Integer getNumSharesSold(@Param("username") String username, @Param("symbol") String symbol);
	
	@Query("SELECT DISTINCT(t.stock.symbol) FROM Trade t WHERE t.user.username = :username")
	List<String> getUserStocks(@Param("username") String username);
	
	//distinct(stock_symbol) from trade where buy = true;
	@Query("SELECT DISTINCT(t.stock.symbol) FROM Trade t Where t.buy = true")
	List<String> getCurrentlyHeldStocks();
}
