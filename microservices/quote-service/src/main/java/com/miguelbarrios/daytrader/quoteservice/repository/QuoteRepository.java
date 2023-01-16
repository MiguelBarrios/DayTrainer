package com.miguelbarrios.daytrader.quoteservice.repository;

import com.miguelbarrios.daytrader.quoteservice.entities.Quote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends CrudRepository<Quote, String> {
}
