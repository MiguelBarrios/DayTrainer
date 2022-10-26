package com.skilldistillery.daytrainer.trade;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skilldistillery.daytrainer.entities.Trade;

public interface TradeRepository extends JpaRepository<Trade, Integer> {
	
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
	
	@Query("SELECT COUNT(t) FROM Trade t WHERE t.user.username = :username")
	Integer getNumUserTrades(@Param("username") String username);
	
	@Query(value = "SELECT SUM(IF(buy, quantity, quantity * -1))\"shares\" FROM trade where user_id = :userId AND stock_symbol = :symbol"
			,nativeQuery = true)
	Integer getNumberOfAvailableShares(@Param("userId") Integer userId, @Param("symbol") String symbol);
	
	@Query(value = "SELECT * FROM trade\n"
			+ "WHERE user_id = :userId \n"
			+ "AND stock_symbol = :symbol \n"
			+ "AND buy \n"
			+ "ORDER BY created_at DESC",
			nativeQuery = true)
	List<Trade> getPreviousBuyOrders(@Param("userId") Integer userId, @Param("symbol") String symbol);
}
