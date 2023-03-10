package com.skilldistillery.daytrainer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private double balance;

	@Column(name = "margin_enable")
	private boolean marginEnable;

	@Column(name = "margin_amount")
	private double marginAmount;

	private double deposit;

	@OneToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;

	// methods
	public Account() {
		super();
	}
	
	public boolean hasSufficientFunds(Trade trade) {
		double fundsRequired = (trade.getPricePerShare() * trade.getQuantity());
		return fundsRequired <= balance;
	}
	
	public void proccessSellOrder(Trade trade) {
		double updatedBalance = balance + (trade.getPricePerShare() * trade.getQuantity());
		setBalance(updatedBalance);
	}
	
	public void prossesBuyOrder(Trade trade) {
		double updatedBalance = balance - (trade.getPricePerShare() * trade.getQuantity());
		setBalance(updatedBalance);
	}

	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
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
	
	@Override
	public String toString() {
		return "Account [id=" + id + ", balance=" + balance + ", marginEnabled=" + marginEnable + ", marginAmount="
				+ marginAmount + "]";
	}

}
