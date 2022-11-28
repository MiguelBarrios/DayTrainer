package com.miguelbarrios.quoteservice.services.quoteservice;

import com.miguelbarrios.quoteservice.models.Quote;

import java.util.List;
import java.util.Map;

public interface QuoteService {

    Quote getQuote(String symbol);
    Map<String, Quote> getQuotes(List<String> symbols);
    public void updateSMP500Quotes();

}
