package com.skilldistillery.daytrainer.tda;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skilldistillery.daytrainer.Config;
import com.skilldistillery.daytrainer.entities.Quote;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TDAClient {

	private static final String TDA_BASE_URL ="https://api.tdameritrade.com/v1/marketdata/";

	private final RestTemplate restTemplate;
	
	private TDAResponseParser tdaResponseParser;
	
	
	public TDAClient(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
		this.tdaResponseParser = new TDAResponseParser();
	}
	
	public List<TDAQuote> getQuotes(List<String> symbols){
		List<TDAQuote> quotes = new ArrayList<>(symbols.size());
		for(int i = 0; i < symbols.size(); i += 84) {
			int end = (i + 84 < symbols.size()) ? i + 84 : symbols.size();
			List<String> batch =  symbols.subList(i, end);
			String request = TDARequestBuilder.buildGetQuotesRequest(batch);
			String json = this.restTemplate.getForObject(request, String.class);
			List<TDAQuote> curQuotes = tdaResponseParser.parseGetQuotesRequest(json);
			quotes.addAll(curQuotes);
		}
				
		return quotes;
	}
	
    public static List<Quote> parseMultipleQuotesRequest(String jsonResponse){

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Quote> map = objectMapper.readValue(jsonResponse, new TypeReference<Map<String, Quote>>() {});
            System.out.println(map);
            return new ArrayList<>(map.values());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
	
	public MarketHours getMarketHours() {
		MarketHours hours = new MarketHours();
		hours.setIsMarketOpen(isMarketOpen());
		return hours;
	}
	
	public boolean isMarketOpen() {
		try {
			String request = TDARequestBuilder.getMarketHoursRequest();
			String json = restTemplate.getForObject(request, String.class);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(json);
			JsonNode data = jsonNode.get("equity").get("EQ");
			return data.get("isOpen").asBoolean();

		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}

	}
	
	public JsonNode getMarketHoursString() {
		try {
			String request = TDARequestBuilder.getMarketHoursRequest();
			String json = restTemplate.getForObject(request, String.class);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(json);
			return jsonNode.get("equity").get("EQ");			
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException("Error getting market hours");
		}

	}


}
