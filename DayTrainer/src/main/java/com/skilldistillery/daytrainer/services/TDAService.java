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

	private final RestTemplate restTemplate;

	public TDAService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public String getQuote(String symbol) {
		symbol = symbol.toUpperCase();
		String url = "https://api.tdameritrade.com/v1/marketdata/" + symbol
				+ "/quotes?apikey=" + Config.getTDAKEY();
		String json = this.restTemplate.getForObject(url, String.class);
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
