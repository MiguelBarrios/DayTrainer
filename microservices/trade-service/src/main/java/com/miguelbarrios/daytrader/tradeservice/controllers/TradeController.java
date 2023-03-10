package com.miguelbarrios.daytrader.tradeservice.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/trade")
@CrossOrigin({ "*", "http://localhost" })
public class TradeController {

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }

    @GetMapping("/pingSecured")
    public String ping2(){
        return "pong";
    }


}
