package com.skilldistillery.daytrainer.entities;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Holding {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	private int amount;

	@OneToOne(mappedBy="holding")
	private Stock stock;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	//methods
	public Holding() {
		super();
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


	public Stock getStock() {
		return stock;
	}


	public void setStock(Stock stock) {
		this.stock = stock;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


	@Override
	public String toString() {
		return "Holding [id=" + id + ", amount=" + amount + "]";
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
		Holding other = (Holding) obj;
		return id == other.id;
	}
	
	
	
	
	
}
