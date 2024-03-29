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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Trade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "price_per_share")
	private double pricePerShare;

	private int quantity;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	private boolean buy;

	@Column(name = "completion_date")
	private LocalDateTime completionDate;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "stock_symbol")
	private Stock stock;

	private String notes;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Comment> comments;

	@Column(name = "strike_price")
	private Double strikePrice;

	@ManyToOne
	@JoinColumn(name = "order_type_id")
	private OrderType orderType;

	public Trade() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isBuy() {
		return buy;
	}
	
	public boolean isBuyOrder() {
		return buy;
	}


	public void setBuy(boolean buy) {
		this.buy = buy;
	}

	public LocalDateTime getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(LocalDateTime completionDate) {
		this.completionDate = completionDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public double getPricePerShare() {
		return pricePerShare;
	}

	public void setPricePerShare(double pricePerShare) {
		this.pricePerShare = pricePerShare;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Double getStrikePrice() {
		return strikePrice;
	}

	public void setStrikePrice(Double strikePrice) {
		this.strikePrice = strikePrice;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
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

	@Override
	public String toString() {
		return "Trade [id=" + id + ", pricePerShare=" + pricePerShare + ", quantity=" + quantity + ", createdAt="
				+ createdAt + ", buy=" + buy + ", completionDate=" + completionDate + ", user=" + user.getUsername() + ", stock="
				+ stock.getName() + ", notes=" + notes + ", strikePrice=" + strikePrice
				+ ", orderType=" + orderType + "]";
	}

	
}