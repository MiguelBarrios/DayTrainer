package com.skilldistillery.daytrainer.services;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.skilldistillery.daytrainer.Config;

@Service
public class TDAService {

	private static String url ="https://api.tdameritrade.com/v1/marketdata/";
	private final RestTemplate restTemplate;

	public TDAService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public String getQuote(String symbol) {
		symbol = symbol.toUpperCase();
		String requestUrl = this.url + symbol + "/quotes?apikey=" + Config.getTDAKEY();
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
	
	public String getQuotes(String symbols) {
		symbols = symbols.toUpperCase();
		//"https://api.tdameritrade.com/v1/marketdata/quotes?apikey=V6DTLTMJNGVWTXDOGACC59RLTM6NTQGH%40&symbol=A,QYLD"
		String requestUrl = this.url + "/quotes?apikey=" + Config.getTDAKEY() + "&symbol=" + symbols;
		System.out.println(requestUrl);
		String json = this.restTemplate.getForObject(requestUrl, String.class);
		return json;
	}
	

}
