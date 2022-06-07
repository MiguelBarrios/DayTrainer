package com.skilldistillery.daytrainer.services;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class TDAService {
	
	private final RestTemplate restTemplate;
	
	public TDAService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	
	public String getQuote(String symbol) {
		symbol = symbol.toUpperCase();
		String url = "https://api.tdameritrade.com/v1/marketdata/" + symbol + "/quotes?apikey=V6DTLTMJNGVWTXDOGACC59RLTM6NTQGH";
		String json =  this.restTemplate.getForObject(url, String.class);
		
		
		try {
			//Get Quote, check if quote is present
			final ObjectNode node = new ObjectMapper().readValue(json, ObjectNode.class);
			if (node.has(symbol)) {
			    System.out.println("content: " + node.get(symbol));
			}    
			
		}catch(Exception e) {
			e.printStackTrace();
		}

		
//		ObjectMapper mapper = new ObjectMapper();
//		try {
//			System.out.println(json);
//			Quote quote = mapper.readValue(json, Quote.class);	
//			System.out.println(quote);
//			return null;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		} 	
//		
		return null;
	}

}
