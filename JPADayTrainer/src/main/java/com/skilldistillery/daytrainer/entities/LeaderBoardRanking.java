package com.skilldistillery.daytrainer.entities;

import java.util.List;

public class LeaderBoardRanking {
	
	private User user;
	
	private double accountBalance;
	
	private List<StockPosition> positions;
	
	public LeaderBoardRanking() {
		
	}

	public LeaderBoardRanking(User user,double accountBalance, List<StockPosition> positions) {
		super();
		this.user = user;
		this.positions = positions;
		this.accountBalance = accountBalance;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<StockPosition> getPositions() {
		return positions;
	}

	public void setPositions(List<StockPosition> positions) {
		this.positions = positions;
	}
	
	
	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	@Override
	public String toString() {
		return "LeaderBoardRanking [user=" + user + ", accountBalance=" + accountBalance + ", positions=" + positions
				+ "]";
	}
	
	

}
