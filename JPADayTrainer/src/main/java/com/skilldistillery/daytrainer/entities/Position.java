package com.skilldistillery.daytrainer.entities;

public class Position {
	private String symbol;

	private int amount;

	private double value;

	private double profit;

	public Position(String symbol, int amount, double value, double profit) {
		super();
		this.symbol = symbol;
		this.amount = amount;
		this.value = value;
		this.profit = profit;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	@Override
	public String toString() {
		return "Position [symbol=" + symbol + ", amount=" + amount + ", value=" + value + ", profit=" + profit + "]";
	}

}
