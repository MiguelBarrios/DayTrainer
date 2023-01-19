package com.miguelbarrios.daytrader.tdameritradeservice.repository;

import com.miguelbarrios.daytrader.tdameritradeservice.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;




public interface StockRepository extends JpaRepository<Stock, String> {
	
//	@Query("SELECT s.symbol FROM Stock s WHERE s.symbol like ':ticker%'")
//	List<Trade> findStockBySymbol(@Param("ticker") String ticker);

	
	@Query("Select s.symbol From Stock s")
	List<String> getAllSymbols();
}