package com.miguelbarrios.quoteservice.services.tdaservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miguelbarrios.quoteservice.exceptions.ErrorParsingQuoteException;
import com.miguelbarrios.quoteservice.models.Quote;
import com.miguelbarrios.quoteservice.models.QuoteWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public String requestQuotes(String symbols){
        String requestUrl = this.url + "/quotes?apikey=" + tdaApiKey + "&symbol=" + symbols;
        String json = this.restTemplate.getForObject(requestUrl, String.class);
        return json;
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
