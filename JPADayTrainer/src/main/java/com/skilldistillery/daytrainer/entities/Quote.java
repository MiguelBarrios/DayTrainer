package com.skilldistillery.daytrainer.entities;

public class Quote {
	private String symbol;
	
	private double lastPrice;

	public Quote(String symbol, double lastPrice) {
		super();
		this.symbol = symbol;
		this.lastPrice = lastPrice;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public double getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(double lastPrice) {
		this.lastPrice = lastPrice;
	}

	@Override
	public String toString() {
		return "Quote [symbol=" + symbol + ", lastPrice=" + lastPrice + "]";
	}
	
	
}
