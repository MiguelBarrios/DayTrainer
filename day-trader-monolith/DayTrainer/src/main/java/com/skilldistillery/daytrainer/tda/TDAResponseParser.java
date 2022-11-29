package com.skilldistillery.daytrainer.tda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j

public class TDAResponseParser {
	
	private ObjectMapper objectMapper;
	
	public TDAResponseParser() {
		this.objectMapper = new ObjectMapper();
	}

	public List<TDAQuote> parseGetQuotesRequest(String json){
		
		final ObjectNode node;
		
		 try {
			node = objectMapper.readValue(json, ObjectNode.class);
		} catch (Exception e) {
			log.error(e.getMessage());
			return Collections.emptyList();
		} 
		
		List<TDAQuote> quotes = new ArrayList<>();
		Iterator<JsonNode> iter = node.elements();
		while(iter.hasNext()) {
			try {
				String quoteJson = iter.next().toString();
				TDAQuote quote = parseQuoteJson(quoteJson);
				quotes.add(quote);
			} catch (Exception e) {
				log.error(e.getMessage());
			} 
		}
		
		return quotes;
	}
	
	public TDAQuote parseQuoteJson(String quoteJson) throws JsonMappingException, JsonProcessingException {
		TDAQuote quote = this.objectMapper.readValue(quoteJson, TDAQuote.class);	
		return quote;
	}
}
