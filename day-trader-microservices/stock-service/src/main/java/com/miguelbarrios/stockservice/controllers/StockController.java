package com.miguelbarrios.stockservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/stocks")
public class StockController {

    @GetMapping("/quote/{symbol}")
    public String requestQuote(@PathVariable String symbol){
        return "Quote for: " + symbol;
    }
}
