package com.skilldistillery.daytrainer.tda;

import java.time.LocalDate;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TDAService {
	

	private static final String TDA_BASE_URL ="https://api.tdameritrade.com/v1/marketdata/";

	private static String[] SYMBOL_BATCHES = null;

	@Autowired
	private StockRepository stockRepository;
	
	private Hashtable<String, String> quoteLookupTable = new Hashtable<>();

	private final RestTemplate restTemplate;
	
	private TDAClient tdaClient;
	
	public TDAService(RestTemplateBuilder restTemplateBuilder, TDAClient tdaClient) {
		this.restTemplate = restTemplateBuilder.build();
		this.tdaClient = tdaClient;
	}
	
	public boolean isMarketOpen() {
		MarketHours marketHours = this.tdaClient.getMarketHours();
		return marketHours.getIsMarketOpen();
	}
	

	
	public String requestQuote(String symbol) {
		String requestUrl = TDA_BASE_URL + symbol + "/quotes?apikey=" + Config.getTDAKEY();
		String json = this.restTemplate.getForObject(requestUrl, String.class);
		String quote = null;

		try {
			// Get Quote, check if quote is present
			final ObjectNode node = new ObjectMapper().readValue(json, ObjectNode.class);
			if (node.has(symbol)) {
				quote =  node.get(symbol).toString();
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return quote;
	}

	public String getQuote(String symbol) {
		symbol = symbol.toUpperCase();
		String quote = null;
		if(quoteLookupTable.containsKey(symbol)) {
			quote = quoteLookupTable.get(symbol);
		}else {
			quote = requestQuote(symbol);
		}
		
		return quote;
	}
	
	public boolean isInitialized() {
		return this.quoteLookupTable.size() > 0;
	}
	
	
	public String getQuotes(String symbols) {
		symbols = symbols.toUpperCase();
		String requestUrl = TDA_BASE_URL + "/quotes?apikey=" + Config.getTDAKEY() + "&symbol=" + symbols;
		return this.restTemplate.getForObject(requestUrl, String.class);
	}
	
	public void initSymbolList(){
		List<String> symbols = this.stockRepository.getAllSymbols();
		String[] symbolLists = {
			String.join(",", symbols.subList(0, 84)),
			String.join(",", symbols.subList(84, 168)),
			String.join(",", symbols.subList(168, 252)),
			String.join(",", symbols.subList(252, 336)),
			String.join(",", symbols.subList(336, 420)),
			String.join(",", symbols.subList(420, symbols.size())),
		};
		
		SYMBOL_BATCHES = symbolLists;
	}
	
	public void updateQuotesAll() {
		tdaClient.getQuotes(this.stockRepository.getAllSymbols());
		
		if(SYMBOL_BATCHES == null) {
			initSymbolList();
		}
		
		StringBuilder notFound = new StringBuilder();

		for(String symbols : SYMBOL_BATCHES) {
			
			String requestUrl = TDA_BASE_URL + "/quotes?apikey=" + Config.getTDAKEY() + "&symbol=" + symbols;
			String json = this.restTemplate.getForObject(requestUrl, String.class);
			for(String key : symbols.split(",")) {
				try {
					// Get Quote, check if quote is present
					final ObjectNode node = new ObjectMapper().readValue(json, ObjectNode.class);
					if (node.has(key)) {
						quoteLookupTable.put(key, node.get(key).toString());
					}else {
						notFound.append(key).append(",");
					}

				} catch (Exception e) {
					log.error("error parsing request");
				}
			}				
		}
		log.info("stocks not found: "  + notFound.toString());
		
	}
	

}
