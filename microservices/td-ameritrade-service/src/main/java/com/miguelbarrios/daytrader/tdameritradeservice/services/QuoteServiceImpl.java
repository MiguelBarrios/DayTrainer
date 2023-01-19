package com.miguelbarrios.daytrader.tdameritradeservice.services;

import com.miguelbarrios.daytrader.tdameritradeservice.entities.TDAQuote;
import com.miguelbarrios.daytrader.tdameritradeservice.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuoteServiceImpl implements QuoteService {
    private QuoteRepository quoteRepository;

    public QuoteServiceImpl(QuoteRepository quoteRepository){
        this.quoteRepository = quoteRepository;
    }

    @Override
    public TDAQuote getQuote(String symbol) {
        return null;
    }

    @Override
    public List<TDAQuote> getQuotes(List<String> symbols) {
        return null;
    }
}
