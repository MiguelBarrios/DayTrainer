package com.miguelbarrios.daytrader.accountservice.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin({ "*", "http://localhost" })
@RestController
@RequestMapping("api/v1/account")
public class AccountController {

    @GetMapping("ping")
    public String ping(){
        return "pong";
    }
}
