package com.miguelbarrios.quoteservice.repository;

import com.miguelbarrios.quoteservice.models.Quote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends CrudRepository<Quote, String> {

}
