package com.miguelbarrios.quoteservice.services.tdaservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miguelbarrios.quoteservice.models.MarketHours;
import com.miguelbarrios.quoteservice.models.Quote;
import com.miguelbarrios.quoteservice.models.QuoteWrapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TDARequestParser {

    public static Quote parseSingleQuoteRequest(String json, String symbol){
        json = json.replaceFirst(symbol, "quote");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            QuoteWrapper wrapper = objectMapper.readValue(json, QuoteWrapper.class );
            return wrapper.getQuote();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Quote> parseMultipleQuotesRequest(String jsonResponse){

        return null;
    }

    public static MarketHours parseMarketHoursRequest(String jsonResponse){
        return null;
    }

}
