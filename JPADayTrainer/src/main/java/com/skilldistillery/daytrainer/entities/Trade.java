package com.skilldistillery.daytrainer.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Trade {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private double price;
	
	private int quantity;
	
	private LocalDateTime created;
	
	@Column(name= "order_type")
	private String orderType;
	
	@Column(name= "trade_type")
	private String tradeType;
	
	@Column(name= "has_executed")
	private boolean hasExecuted;

	
	
	//methods
	public Trade() {
		super();
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public double getPrice() {
		return price;
	}



	public void setPrice(double price) {
		this.price = price;
	}



	public int getQuantity() {
		return quantity;
	}



	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}



	public LocalDateTime getCreated() {
		return created;
	}



	public void setCreated(LocalDateTime created) {
		this.created = created;
	}



	public String getOrderType() {
		return orderType;
	}



	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}



	public String getTradeType() {
		return tradeType;
	}



	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}



	public boolean isHasExecuted() {
		return hasExecuted;
	}



	public void setHasExecuted(boolean hasExecuted) {
		this.hasExecuted = hasExecuted;
	}



	@Override
	public String toString() {
		return "Trade [id=" + id + ", price=" + price + ", quantity=" + quantity + ", created=" + created
				+ ", orderType=" + orderType + ", tradeType=" + tradeType + ", hasExecuted=" + hasExecuted + "]";
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
		Trade other = (Trade) obj;
		return id == other.id;
	}
	
	
	

}
