package com.miguelbarrios.quoteservice.services.quoteservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miguelbarrios.quoteservice.services.tdaservice.TDAClient;
import com.miguelbarrios.quoteservice.models.MarketHours;
import com.miguelbarrios.quoteservice.models.Quote;
import com.miguelbarrios.quoteservice.models.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.*;

@Slf4j
@Service
public class QuoteServiceImpl implements QuoteService{

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
    private static List<String> smp500Symbols = new ArrayList<>();

    private Hashtable<String, String> table = new Hashtable<>();

    private TDAClient tdaClient;

    static {
        readSMP500SymbolsCSV();
    }
    public QuoteServiceImpl(TDAClient tdaClient) {
        this.tdaClient = tdaClient;
    }

    public static void readSMP500SymbolsCSV(){
        String pathToCSV = System.getProperty("user.dir");
        pathToCSV = pathToCSV + "/stock-service/src/main/resources/" + "tickers.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(pathToCSV))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                Stock stock = parseLine(line);
                smp500Symbols.add(stock.getSymbol());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Stock parseLine(String line){
        String[] values = line.split(",");
        return Stock.builder()
                .symbol(values[0])
                .name(values[1])
                .sector(values[2]).build();
    }


    public static boolean isWeekend() {
        LocalDate today = LocalDate.now();
        DayOfWeek day = DayOfWeek.of(today.get(ChronoField.DAY_OF_WEEK));
        return day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
    }

    public boolean isMarketOpen() {

        if(isWeekend()){
            return false;
        }

        MarketHours marketHours = getMarketHours();
        LocalDateTime marketOpen = marketHours.getMarketOpen();
        LocalDateTime marketClose = marketHours.getMarketClose();
        LocalDateTime currentTime = LocalDateTime.now();
        return (currentTime.isBefore(marketClose) && currentTime.isAfter(marketOpen));
    }

    public MarketHours getMarketHours() {
        String marketHoursJson = tdaClient.requestMarketHours();
        return parseMarketHoursRequest(marketHoursJson);
    }

    public MarketHours parseMarketHoursRequest(String marketHoursJson){
        ObjectMapper mapper = new ObjectMapper();
        try{
            JsonNode parentNode = mapper.readTree(marketHoursJson);
            JsonNode jsonNode = parentNode.get("equity").get("EQ");
            jsonNode = jsonNode.get("sessionHours");
            JsonNode regularMarketHours = jsonNode.get("regularMarket").get(0);
            String regularMarketOpen = regularMarketHours.get("start").asText();
            String regularMarketClose = regularMarketHours.get("end").asText();

            LocalDateTime marketOpen = LocalDateTime.parse(regularMarketOpen, formatter);
            LocalDateTime marketClose = LocalDateTime.parse(regularMarketClose,formatter);

            return MarketHours.builder()
                    .marketOpen(marketOpen)
                    .marketClose(marketClose)
                    .build();
        } catch (Exception e){
            log.info("Error parsing market hours request");
            return null;
        }
    }


    @Override
    public Quote getQuote(String symbol) {
        System.out.println("getting quote for : " + symbol);
        Quote quote = tdaClient.requestQuote(symbol);


//        if(isMarketOpen()){
//            System.err.println("Market is open");
//        }
//        else{
//            System.err.println("Market is closed");
//        }
//
//        symbol = symbol.toUpperCase();
//        String quote = null;
//        if(table.containsKey(symbol)) {
//            quote = table.get(symbol);
//        }else {
//            quote = requestQuote(symbol);
//        }


        return quote;
    }

    @Override
    public List<Quote> getQuotes(String[] symbol) {
        return null;
    }




    @Override
    public void updateSMP500Quotes() {
        Map<String, Quote> quotes = tdaClient.requestQuotes(smp500Symbols);
        
    }
}
