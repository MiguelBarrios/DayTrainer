package com.skilldistillery.daytrainer.entities;

import java.util.Map;

public class QuoteWrapper {
	Map<String, Quote> quotes;

	public QuoteWrapper(Map<String, Quote> quotes) {
		super();
		this.quotes = quotes;
	}

	public Map<String, Quote> getQuotes() {
		return quotes;
	}

	public void setQuotes(Map<String, Quote> quotes) {
		this.quotes = quotes;
	}
	
	
}
