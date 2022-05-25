package com.skilldistillery.daytrainer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skilldistillery.daytrainer.entities.Trade;

public interface TradeRepository extends JpaRepository<Trade, Integer>{
	
	@Query("SELECT t FROM Trade t WHERE t.user.username = :username")
	List<Trade> getUserTrades(@Param("username") String username);

}
