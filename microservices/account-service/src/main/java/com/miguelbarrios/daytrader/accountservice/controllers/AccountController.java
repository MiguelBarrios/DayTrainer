package com.miguelbarrios.daytrader.accountservice.controllers;

import com.miguelbarrios.daytrader.accountservice.entities.Account;
import com.miguelbarrios.daytrader.accountservice.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

@CrossOrigin({ "*", "http://localhost" })
@RestController
@RequestMapping("api/v1/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("ping")
    public String ping(){
        return "pong";
    }

    @PostMapping("/{userId}")
    public Account createAccount(@PathVariable int userId, HttpServletRequest request, HttpServletResponse hsr){
        Account account = accountService.createAccount(userId);
        if(account == null){
            hsr.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        }
        else{
            String resourcePath = "/api/v1/account/" + userId;
            hsr.setStatus(HttpStatus.CREATED.value());
            hsr.setHeader("Location", resourcePath);
        }
        return account;
    }

    @GetMapping("/{userId}")
    public Account getAccountInfo(@PathVariable Integer userId,  HttpServletResponse response){
        Account account = accountService.getAccountInformation(userId);
        if(account == null){
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
        return account;
    }

    @DeleteMapping("/{userId}")
    public void deleteAccount(@PathVariable Integer userId, HttpServletResponse response){
        boolean accountDeleted = accountService.deleteAccount(userId);
        if(!accountDeleted){
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    @PutMapping("/{userId}/transactionAmount/{amount}")
    public Account processTransaction(@PathVariable Integer userId, @PathVariable BigDecimal amount){
        return accountService.updateBalanceAfterTransaction(userId, amount);
    }
}
