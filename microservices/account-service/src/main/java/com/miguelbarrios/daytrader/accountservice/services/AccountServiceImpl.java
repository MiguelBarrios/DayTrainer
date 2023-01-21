package com.miguelbarrios.daytrader.accountservice.services;

import com.miguelbarrios.daytrader.accountservice.entities.Account;
import com.miguelbarrios.daytrader.accountservice.exceptions.InsufficientFundsException;
import com.miguelbarrios.daytrader.accountservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account createAccount(int userId) {

        Account account = accountRepository.findByUserId(userId);
        if(account == null){
            account = new Account();
            account.setUserId(userId);
            account.setBalance(new BigDecimal(10_000));
            account.setDeposit(new BigDecimal(10_000));
            account.setMarginEnable(false);
            accountRepository.save(account);
        }
        return account;

    }

    @Override
    public boolean deleteAccount(int userId) {
        Account account = accountRepository.findByUserId(userId);
        if(account != null){
            accountRepository.delete(account);
        }

        return !accountRepository.existsByUserId(userId);
    }

    @Override
    public Account updateBalanceAfterTransaction(int userId, BigDecimal transactionAmount) {
        Account account = accountRepository.findByUserId(userId);

        BigDecimal balance = account.getBalance();
        if(transactionAmount.signum() == -1){
            transactionAmount = transactionAmount.abs();
            if(transactionAmount.compareTo(balance) <= 0){
                balance = balance.subtract(transactionAmount);
                account.setBalance(balance);
            }
            else{
                throw new InsufficientFundsException("Insufficient Funds");
            }
        }
        else if(transactionAmount.signum() == 1){
            balance = balance.add(transactionAmount);
            account.setBalance(balance);
        }

        return account;
    }

    @Override
    public Account getAccountInformation(int userId) {
        return accountRepository.findByUserId(userId);
    }
}
