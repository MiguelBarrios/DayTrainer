package com.skilldistillery.daytrainer.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private double balance;
	
	@Column(name="margin_enabled")
	private boolean marginEnabled;
	
	@Column(name="margin_amount")
	private double marginAmount;

	public Account() {
		super();
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
		return marginEnabled;
	}

	public void setMarginEnabled(boolean marginEnabled) {
		this.marginEnabled = marginEnabled;
	}

	public double getMarginAmount() {
		return marginAmount;
	}

	public void setMarginAmount(double marginAmount) {
		this.marginAmount = marginAmount;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", balance=" + balance + ", marginEnabled=" + marginEnabled + ", marginAmount="
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
