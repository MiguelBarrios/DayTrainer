package com.miguelbarrios.quoteservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class TDAClient {

    @Value("${config.tda.apikey}")
    private  static String tdaApiKey;
    private final RestTemplate restTemplate;

    private static String url ="https://api.tdameritrade.com/v1/marketdata/";


    public TDAClient(RestTemplateBuilder restTemplateBuilder ) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String requestQuote(String symbol){
        String requestUrl = url + symbol + "/quotes?apikey=" + tdaApiKey;
        String json = this.restTemplate.getForObject(requestUrl, String.class);
        return json;
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
