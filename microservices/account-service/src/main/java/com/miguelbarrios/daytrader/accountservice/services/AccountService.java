package com.miguelbarrios.daytrader.accountservice.services;

import com.miguelbarrios.daytrader.accountservice.entities.Account;

import java.math.BigDecimal;

public interface AccountService {
    Account createAccount(int userId);
    boolean deleteAccount(int userId);
    Account updateBalanceAfterTransaction(int userId, BigDecimal transactionAmount);
    Account getAccountInformation(int userId);
}
