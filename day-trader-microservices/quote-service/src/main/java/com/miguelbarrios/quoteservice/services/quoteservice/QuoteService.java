package com.miguelbarrios.quoteservice.services.quoteservice;

import com.miguelbarrios.quoteservice.models.Quote;

import java.util.List;

public interface QuoteService {

    Quote getQuote(String symbol);
    List<Quote> getQuotes(String[] symbol);
    public void updateSMP500Quotes();

}
