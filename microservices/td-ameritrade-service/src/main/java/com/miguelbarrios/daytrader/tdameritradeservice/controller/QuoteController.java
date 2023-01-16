package com.miguelbarrios.daytrader.tdameritradeservice.controller;

import com.miguelbarrios.daytrader.tdameritradeservice.entities.Quote;
import com.miguelbarrios.daytrader.tdameritradeservice.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("api/v1/quotes")
@RestController
public class QuoteController {

    @Autowired
    private QuoteRepository quoteRepository;

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }

    @GetMapping("/pong")
    public String ping2(){
        return "yooo";
    }

    @GetMapping("/test")
    public String ping3(){
        return "success";
    }

    @PutMapping
    public Quote saveQuote(@RequestBody Quote quote){
        System.out.println("Saving quote");
        quoteRepository.save(quote);

        Optional<Quote> managedQuote = quoteRepository.findById(quote.getSymbol());
        return managedQuote.get();
    }
}
