package com.miguelbarrios.quoteservice.services.tdaservice;


import com.miguelbarrios.quoteservice.exceptions.ErrorParsingQuoteException;
import com.miguelbarrios.quoteservice.models.Quote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TDAClient {


    private String tdaApiKey;
    private final RestTemplate restTemplate;
    private static String url ="https://api.tdameritrade.com/v1/marketdata/";


    public TDAClient(RestTemplateBuilder restTemplateBuilder, @Value("${tdaapikey}") String tdaApiKey) {
        this.restTemplate = restTemplateBuilder.build();
        this.tdaApiKey = tdaApiKey;
    }

    public Quote requestQuote(String symbol)  {

        try {
            symbol = symbol.toUpperCase();
            String requestUrl = url + symbol + "/quotes?apikey=" + tdaApiKey;
            String json = this.restTemplate.getForObject(requestUrl, String.class);
            Quote quote = TDARequestParser.parseSingleQuoteRequest(json, symbol);
            return quote;
        }catch (Exception e){
            log.info("Error Processing Quote");
            throw new ErrorParsingQuoteException();
        }
    }

    public Map<String, Quote> requestQuotes(List<String> symbols){

        Map<String, Quote> quotes = new HashMap<>((int)(symbols.size() * 1.3));

        int paramsPerRequest = 90;
        for(int i = 0; i < symbols.size(); i += paramsPerRequest){
            String requestParams = String.join(",", symbols.subList(i, Math.min(i + paramsPerRequest, symbols.size())));
            String requestUrl = this.url + "/quotes?apikey=" + tdaApiKey + "&symbol=" + requestParams;
            String jsonString = this.restTemplate.getForObject(requestUrl, String.class);
            Map<String, Quote> current = TDARequestParser.parseMultipleQuotesRequest(jsonString);
            quotes.putAll(current);
        }

        return quotes;
    }

    public Map<String, Quote> requestQuotes(String symbols){
        String requestUrl = this.url + "/quotes?apikey=" + tdaApiKey + "&symbol=" + symbols;
        String jsonString = this.restTemplate.getForObject(requestUrl, String.class);
        Map<String, Quote> map = TDARequestParser.parseMultipleQuotesRequest(jsonString);
        return map;
    }

    public String requestMarketHours(){
        LocalDate date = getNextDayMarketIsOpen();
        String url = this.url + "EQUITY/hours?apikey=" + tdaApiKey  + "&date=" + date.toString();
        String response = this.restTemplate.getForObject(url, String.class);
        return response;
    }

    public LocalDate getNextDayMarketIsOpen(){
        LocalDate today = LocalDate.now();
        if(LocalDateTime.now().getHour() >= 20) {
            today = today.plusDays(1);
        }
        return today;
    }
}
