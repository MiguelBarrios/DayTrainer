package com.skilldistillery.daytrainer.entities;

public class StockPosition {

	private String symbol;

	private int numberOfShares;

	private double avgCostPerShare;

	public StockPosition() {
	}

	public StockPosition(String symbol, int numberOfShares, double avgCostPerShare) {
		super();
		this.symbol = symbol;
		this.numberOfShares = numberOfShares;
		this.avgCostPerShare = avgCostPerShare;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public int getNumberOfShares() {
		return numberOfShares;
	}

	public void setNumberOfShares(int numberOfShares) {
		this.numberOfShares = numberOfShares;
	}

	public double getAvgCostPerShare() {
		return avgCostPerShare;
	}

	public void setAvgCostPerShare(double avgCostPerShare) {
		this.avgCostPerShare = avgCostPerShare;
	}

	@Override
	public String toString() {
		return "StockPosition [numberOfShares=" + numberOfShares + ", avgCostPerShare=" + avgCostPerShare + "]";
	}

}
