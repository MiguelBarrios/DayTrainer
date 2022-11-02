package com.miguelbarrios.stockservice.repository;

import com.miguelbarrios.stockservice.models.Stock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends CrudRepository<Stock, String> {
}
