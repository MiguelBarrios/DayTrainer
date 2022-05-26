package com.skilldistillery.daytrainer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.daytrainer.entities.Stock;

public interface StockRepository extends JpaRepository<Stock, String> {

}
