package com.skilldistillery.daytrainer.tda;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.skilldistillery.daytrainer.Config;
import com.skilldistillery.daytrainer.stock.StockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;

@Service
public class TDAService {
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZZZZZ");

	@Autowired
	private StockRepository stockRepo;
	
	private String[] stocksSymbols = null;
	
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

	public TDAService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	
	public boolean isMarketOpen() {
		LocalDate today = LocalDate.now();
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
		
		
		String url = this.url + "EQUITY/hours?apikey=" + Config.getTDAKEY()  + "&date=" + today.toString();
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

	public String getQuote(String symbol) {
		symbol = symbol.toUpperCase();
		if(table.containsKey(symbol)) {
			System.out.println("Quote requested: " + symbol + " " + table.get(symbol));
			return table.get(symbol);
		}else {
			System.err.println("Non smp 500 quote requested: " + symbol);
			String requestUrl = url + symbol + "/quotes?apikey=" + Config.getTDAKEY();
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

	}
	
	public boolean isInitialized() {
		return this.table.size() > 0;
	}
	
	public String getQuotes(String symbols) {
		symbols = symbols.toUpperCase();
		String requestUrl = this.url + "/quotes?apikey=" + Config.getTDAKEY() + "&symbol=" + symbols;
		String json = this.restTemplate.getForObject(requestUrl, String.class);
		return json;
	}
	
	public void updateQuotesAll() {
		
		if(this.stocksSymbols == null) {
			List<String> symbols = this.stockRepo.getAllSymbols();
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
		
		for(String symbols : this.stocksSymbols) {
			
			String requestUrl = this.url + "/quotes?apikey=" + Config.getTDAKEY() + "&symbol=" + symbols;
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
