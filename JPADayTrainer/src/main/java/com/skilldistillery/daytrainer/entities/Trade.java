package com.skilldistillery.daytrainer.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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

	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToOne
	@JoinColumn(name="stock_id")
	private Stock stock;
	
	
	@OneToOne
	@JoinColumn(name="transaction_id")
	private Transaction transaction;
	
	@OneToMany(mappedBy="user")
	private List<Comment>comments;
	
	//methods
	public Trade() {
		super();
	}



	public List<Comment> getComments() {
		return comments;
	}



	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}



	public Transaction getTransaction() {
		return transaction;
	}



	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}



	public Stock getStock() {
		return stock;
	}



	public void setStock(Stock stock) {
		this.stock = stock;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
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
