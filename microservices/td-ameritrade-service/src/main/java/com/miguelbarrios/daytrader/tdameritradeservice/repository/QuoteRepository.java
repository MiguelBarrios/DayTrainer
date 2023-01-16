package com.miguelbarrios.daytrader.tdameritradeservice.repository;

import com.miguelbarrios.daytrader.tdameritradeservice.entities.Quote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends CrudRepository<Quote, String> {
}
