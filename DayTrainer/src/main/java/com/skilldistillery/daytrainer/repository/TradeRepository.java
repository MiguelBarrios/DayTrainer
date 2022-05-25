package com.skilldistillery.daytrainer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.skilldistillery.daytrainer.entities.Trade;

public interface TradeRepository {
	
	@Query("SELECT t FROM Trade t WHERE t.user.username = :username")
	List<Trade> getUserTrades(String username);

}
