package com.miguelbarrios.stockservice.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/stocks")
public class StockController {

    @GetMapping("/quote/{symbol}")
    public String requestQuote(@PathVariable String symbol){
        return "Quote for: " + symbol;
    }

    @PutMapping
    public String saveQuote(){
        return "";
    }
}
