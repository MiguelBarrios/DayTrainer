package com.skilldistillery.daytrainer.trade;

import java.util.List;

import com.skilldistillery.daytrainer.entities.Trade;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TradePaginationRepository extends PagingAndSortingRepository<Trade, Integer> {
	
	List<Trade> findByUser_id(int id, Pageable pageable);
}
