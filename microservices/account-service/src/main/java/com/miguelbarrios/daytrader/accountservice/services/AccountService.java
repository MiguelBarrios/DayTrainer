package com.miguelbarrios.daytrader.accountservice.services;

import com.miguelbarrios.daytrader.accountservice.entities.Account;

import java.math.BigDecimal;

public interface AccountService {
    Account createAccount(int userId);
    boolean deleteAccount(int userId);
    Account updateAccountBalance(int userId, BigDecimal amount);
    Account getAccountInformation(int userId);
}
