package com.skilldistillery.daytrainer.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Stock {

	@Id
	private String symbol;

	private String name;

	@Column(name="exchange_name")
	private String exchangeName;
	
	@OneToOne(mappedBy="stock")
	private Trade trade;

	// methods
	public Stock() {
		super();
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(symbol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		return Objects.equals(symbol, other.symbol);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Stock [symbol=");
		builder.append(symbol);
		builder.append(", name=");
		builder.append(name);
		builder.append(", exchangeName=");
		builder.append(exchangeName);
		builder.append("]");
		return builder.toString();
	}

}