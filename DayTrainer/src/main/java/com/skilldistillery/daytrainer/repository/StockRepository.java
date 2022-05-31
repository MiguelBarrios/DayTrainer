package com.skilldistillery.daytrainer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skilldistillery.daytrainer.entities.Stock;
import com.skilldistillery.daytrainer.entities.Trade;

public interface StockRepository extends JpaRepository<Stock, String> {
	
	@Query("SELECT s.symbol FROM Stock s WHERE s.symbol like ':ticker%'")
	List<Trade> findStockBySymbol(@Param("ticker") String ticker);

}
