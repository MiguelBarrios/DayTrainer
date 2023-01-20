package com.miguelbarrios.daytrader.tdameritradeservice.externalapi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.miguelbarrios.daytrader.tdameritradeservice.Config;
import com.miguelbarrios.daytrader.tdameritradeservice.entities.MarketHours;
import com.miguelbarrios.daytrader.tdameritradeservice.entities.TDAQuote;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TDAClient {

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
	
	public JsonNode getMarketHours2() {
		
		LocalDate today = LocalDate.now();
		if(LocalDateTime.now().getHour() >= 20) {
			today = today.plusDays(1);
		}
		
		
		String url = "https://api.tdameritrade.com/v1/marketdata/" + "EQUITY/hours?apikey=" + Config.getTDAKEY()  + "&date=" + today.toString();
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

}