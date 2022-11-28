package com.miguelbarrios.quoteservice.services.tdaservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miguelbarrios.quoteservice.models.MarketHours;
import com.miguelbarrios.quoteservice.models.Quote;
import com.miguelbarrios.quoteservice.models.QuoteWrapper;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    public static Map<String, Quote> parseMultipleQuotesRequest(String jsonResponse){

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Quote> map = objectMapper.readValue(jsonResponse, new TypeReference<Map<String, Quote>>() {});
            return map;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static MarketHours parseMarketHoursRequest(String jsonResponse){
        return null;
    }

}
