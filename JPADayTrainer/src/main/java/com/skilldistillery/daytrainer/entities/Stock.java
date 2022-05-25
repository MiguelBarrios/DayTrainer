package com.skilldistillery.daytrainer.entities;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String symbol;

	@OneToOne(mappedBy="stock")
	private Trade trade;
	
	
	@OneToOne
	@JoinColumn(name= "holding_id")
	private Holding holding;
	
	//methods
	public Stock() {
		super();
	}




	public Holding getHolding() {
		return holding;
	}




	public void setHolding(Holding holding) {
		this.holding = holding;
	}




	public Trade getTrade() {
		return trade;
	}




	public void setTrade(Trade trade) {
		this.trade = trade;
	}




	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getSymbol() {
		return symbol;
	}




	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}




	@Override
	public String toString() {
		return "Stock [id=" + id + ", name=" + name + ", symbol=" + symbol + "]";
	}




	@Override
	public int hashCode() {
		return Objects.hash(id);
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
		return id == other.id;
	}
	
	
	
	

}
