package com.skilldistillery.daytrainer.services;

import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.skilldistillery.daytrainer.Config;
import com.skilldistillery.daytrainer.repository.StockRepository;

@Service
public class TDAService {

	@Autowired
	private StockRepository stockRepo;
	
	private String[] stocksSymbols = null;
	Hashtable<String, String> table = new Hashtable<>();


	private static String url ="https://api.tdameritrade.com/v1/marketdata/";
	private final RestTemplate restTemplate;

	public TDAService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public String getQuote(String symbol) {
		symbol = symbol.toUpperCase();
		if(table.containsKey(symbol)) {
			return table.get(symbol);
		}else {
			System.err.println("Non smp 500 quote requested");
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
	
	public String getQuotes(String symbols) {
		symbols = symbols.toUpperCase();
		//"https://api.tdameritrade.com/v1/marketdata/quotes?apikey=V6DTLTMJNGVWTXDOGACC59RLTM6NTQGH%40&symbol=A,QYLD"
		String requestUrl = this.url + "/quotes?apikey=" + Config.getTDAKEY() + "&symbol=" + symbols;
		System.out.println(requestUrl);
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
