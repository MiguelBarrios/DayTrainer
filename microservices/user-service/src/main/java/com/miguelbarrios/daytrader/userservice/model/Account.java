package com.miguelbarrios.daytrader.userservice.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

public class Account {

    private int id;

    private BigDecimal balance;

    private boolean marginEnable;

    private double marginAmount;

    private BigDecimal deposit;

    private int userId;

    public Account() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public boolean isMarginEnable() {
        return marginEnable;
    }

    public void setMarginEnable(boolean marginEnable) {
        this.marginEnable = marginEnable;
    }

    public double getMarginAmount() {
        return marginAmount;
    }

    public void setMarginAmount(double marginAmount) {
        this.marginAmount = marginAmount;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && userId == account.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", marginEnable=" + marginEnable +
                ", marginAmount=" + marginAmount +
                ", deposit=" + deposit +
                ", userId=" + userId +
                '}';
    }
}