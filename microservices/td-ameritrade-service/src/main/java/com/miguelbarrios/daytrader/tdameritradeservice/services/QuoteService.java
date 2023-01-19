package com.miguelbarrios.daytrader.tdameritradeservice.services;

import com.miguelbarrios.daytrader.tdameritradeservice.entities.TDAQuote;

import java.util.List;

public interface QuoteService {

    TDAQuote getQuote(String symbol);

    List<TDAQuote> getQuotes(List<String> symbols);
}
