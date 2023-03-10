package com.skilldistillery.daytrainer.tda;

import com.skilldistillery.daytrainer.config.Config;

import java.time.LocalDate;
import java.util.List;


public class TDARequestBuilder {
	private static final String TDA_BASE_URL ="https://api.tdameritrade.com/v1/marketdata/";

	
	public static String getMarketHoursRequest() {
		String today = LocalDate.now().toString();
		return TDA_BASE_URL + "EQUITY/hours?apikey=" + Config.getTDAKEY()  + "&date=" + today;
	}
	
	public static String buildGetQuotesRequest(List<String> symbols) {
		String params = String.join(",", symbols);
		return TDA_BASE_URL + "/quotes?apikey=" + Config.getTDAKEY() + "&symbol=" + params;
	}
}
