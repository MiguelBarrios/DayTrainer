package com.skilldistillery.daytrainer.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Account {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private double balance;
	
	@Column(name="margin_enable")
	private boolean marginEnable;
	
	@Column(name="margin_amount")
	private double marginAmount;

	@OneToOne
	private User user;
	
	
	
	
	//methods
	public Account() {
		super();
	}

	public boolean isMarginEnable() {
		return marginEnable;
	}

	public void setMarginEnable(boolean marginEnable) {
		this.marginEnable = marginEnable;
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

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public boolean isMarginEnabled() {
		return marginEnable;
	}

	public void setMarginEnabled(boolean marginEnabled) {
		this.marginEnable = marginEnabled;
	}

	public double getMarginAmount() {
		return marginAmount;
	}

	public void setMarginAmount(double marginAmount) {
		this.marginAmount = marginAmount;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", balance=" + balance + ", marginEnabled=" + marginEnable + ", marginAmount="
				+ marginAmount + "]";
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
		Account other = (Account) obj;
		return id == other.id;
	}
	
	
}
