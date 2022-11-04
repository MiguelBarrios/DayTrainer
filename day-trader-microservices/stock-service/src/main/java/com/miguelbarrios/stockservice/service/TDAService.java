package com.miguelbarrios.stockservice.service;

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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.miguelbarrios.stockservice.models.Stock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TDAService {

	@Value("${config.tda.apikey}")
	private  String tdaApiKey;
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
	
	private static List<String> symbols = new ArrayList<>();

	private static String[] stocksSymbols = null;

	Hashtable<String, String> table = new Hashtable<>();

	private static String url ="https://api.tdameritrade.com/v1/marketdata/";

	private final RestTemplate restTemplate;
	
	private LocalDate lastDate = null;
	private LocalDateTime marketOpen = null;
	private LocalDateTime marketClose = null;
	
	{
		// So that get market hours request will run on first run
		this.lastDate = LocalDate.now();
		this.lastDate = this.lastDate.minusDays(1);		
	}

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
				System.out.println(stock);
				symbols.add(stock.getName());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}


	}

	public TDAService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	
    public static boolean isWeekend(final LocalDate ld)
    {
        DayOfWeek day = DayOfWeek.of(ld.get(ChronoField.DAY_OF_WEEK));
        return day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
    }
	
	public boolean isMarketOpen() {
		LocalDate today = LocalDate.now();
		if(isWeekend(today)) {
			return false;
		}
		if(!lastDate.isEqual(today)) {
			lastDate = today;

			JsonNode json = this.getMarketHours();
			json = json.get("sessionHours");
			JsonNode regularMarketHours = json.get("regularMarket").get(0);
			String regularMarketOpen = regularMarketHours.get("start").asText();
			String regularMarketClose = regularMarketHours.get("end").asText();

			this.marketOpen = LocalDateTime.parse(regularMarketOpen, formatter);
			this.marketClose = LocalDateTime.parse(regularMarketClose,formatter);
		}
		
		LocalDateTime now = LocalDateTime.now();
		boolean isOpen = false;
		if(now.isAfter(marketOpen) && now.isBefore(marketClose)){
			isOpen = true;
		}
		
		return isOpen;
	}
	
	public JsonNode getMarketHours() {
		
		LocalDate today = LocalDate.now();
		if(LocalDateTime.now().getHour() >= 20) {
			today = today.plusDays(1);
		}
		
		String url = this.url + "EQUITY/hours?apikey=" + tdaApiKey  + "&date=" + today.toString();
		String json = this.restTemplate.getForObject(url, String.class);
		ObjectMapper mapper = new ObjectMapper();

		JsonNode marketHours = null;
		try {
			JsonNode jsonNode = mapper.readTree(json);
			marketHours = jsonNode.get("equity").get("EQ");			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return marketHours;
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

	public String getQuote(String symbol) {
		symbol = symbol.toUpperCase();
		String quote = null;
		if(table.containsKey(symbol)) {
			quote = table.get(symbol);
		}else {
			quote = requestQuote(symbol);
		}
		
		return quote;
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
		String[] symbolLists = {
			String.join(",", symbols.subList(0, 84)),
			String.join(",", symbols.subList(84, 168)),
			String.join(",", symbols.subList(168, 252)),
			String.join(",", symbols.subList(252, 336)),
			String.join(",", symbols.subList(336, 420)),
			String.join(",", symbols.subList(420, symbols.size())),
		};

		this.stocksSymbols = symbolLists;
	}
	
	public void updateQuotesAll() {
		
		if(symbols == null) {
			initSymbolList();
		}

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