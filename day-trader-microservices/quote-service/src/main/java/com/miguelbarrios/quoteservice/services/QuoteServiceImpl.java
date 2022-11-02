package com.miguelbarrios.quoteservice.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.miguelbarrios.quoteservice.models.MarketHours;
import com.miguelbarrios.quoteservice.models.Quote;
import com.miguelbarrios.quoteservice.models.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.error.Mark;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@Slf4j
@Service
public class QuoteServiceImpl implements QuoteService{
    @Value("${config.tda.apikey}")
    private  String tdaApiKey;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
    private static List<String> symbols = new ArrayList<>();
    private static String[] smp500symbolBatches = null;
    Hashtable<String, String> table = new Hashtable<>();
    private static String url ="https://api.tdameritrade.com/v1/marketdata/";
    private final RestTemplate restTemplate;

    static {
        loadCSVFile();
    }

    public static void loadCSVFile(){
        String pathToCSV = System.getProperty("user.dir");
        pathToCSV = pathToCSV + "/stock-service/src/main/resources/" + "tickers.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(pathToCSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Stock stock = Stock.builder()
                        .symbol(values[0])
                        .name(values[1])
                        .sector(values[2]).build();
                symbols.add(stock.getName());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public QuoteServiceImpl(RestTemplateBuilder restTemplateBuilder ) {
        this.restTemplate = restTemplateBuilder.build();
        initSymbolList();
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
            MarketHours marketHours = MarketHours.builder()
                    .marketOpen(marketOpen)
                    .marketClose(marketClose)
                    .build();

            return marketHours;
        } catch (Exception e){
            log.info("Error parsing market hours request");
            return null;
        }


    }

    public MarketHours getMarketHours() {
        LocalDate date = getNextDayMarketIsOpen();
        String url = this.url + "EQUITY/hours?apikey=" + tdaApiKey  + "&date=" + date.toString();
        String response = this.restTemplate.getForObject(url, String.class);
        MarketHours marketHours = parseMarketHoursRequest(response);
        return  marketHours;
    }

    public LocalDate getNextDayMarketIsOpen(){
        LocalDate today = LocalDate.now();
        if(LocalDateTime.now().getHour() >= 20) {
            today = today.plusDays(1);
        }
        return today;
    }


    public String requestQuote(String symbol) {
        String requestUrl = url + symbol + "/quotes?apikey=" + tdaApiKey;
        String json = this.restTemplate.getForObject(requestUrl, String.class);
        String quote = null;

        try {
            // Get Quote, check if quote is present
            final ObjectNode node = new ObjectMapper().readValue(json, ObjectNode.class);
            if (node.has(symbol)) {
                quote =  node.get(symbol).toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return quote;
    }

    @Override
    public Quote getQuote(String symbol) {

        if(isMarketOpen()){
            System.err.println("Market is open");
        }
        else{
            System.err.println("Market is closed");
        }

        symbol = symbol.toUpperCase();
        String quote = null;
        if(table.containsKey(symbol)) {
            quote = table.get(symbol);
        }else {
            quote = requestQuote(symbol);
        }


        return null;
    }

    @Override
    public List<Quote> getQuotes(String[] symbol) {
        return null;
    }

    public boolean isInitialized() {
        return this.table.size() > 0;
    }

    public Double getLastPrice(String symbol) {
        return null;
    }

    public String getQuotes(String symbols) {
        symbols = symbols.toUpperCase();
        String requestUrl = this.url + "/quotes?apikey=" + tdaApiKey + "&symbol=" + symbols;
        String json = this.restTemplate.getForObject(requestUrl, String.class);
        return json;
    }

    public void initSymbolList(){

        this.smp500symbolBatches = new String[]{
                String.join(",", symbols.subList(0, 84)),
                String.join(",", symbols.subList(84, 168)),
                String.join(",", symbols.subList(168, 252)),
                String.join(",", symbols.subList(252, 336)),
                String.join(",", symbols.subList(336, 420)),
                String.join(",", symbols.subList(420, symbols.size())),
        };
    }

    public void updateSMP500Quotes() {
        for(String symbols : symbols) {

            String requestUrl = this.url + "/quotes?apikey=" + tdaApiKey + "&symbol=" + symbols;
            String json = this.restTemplate.getForObject(requestUrl, String.class);
            String[] keys = symbols.split(",");
            String quote = "";
            for(String key : keys) {
                try {
                    // Get Quote, check if quote is present
                    final ObjectNode node = new ObjectMapper().readValue(json, ObjectNode.class);
                    if (node.has(key)) {
                        quote =  node.get(key).toString();
                        table.put(key, quote);
                    }else {
                        System.err.println(key + " not found");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }



}
