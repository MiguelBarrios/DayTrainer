package com.miguelbarrios.quoteservice.controllers;

import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api/v1/quotes")
@CrossOrigin({ "*", "http://localhost" })
public class QuoteController {


    @GetMapping("/{stockSymbol}")
    public String  getQuote(@PathVariable String stockSymbol){
        return "Getting quote for " + stockSymbol;
    }

    @GetMapping("/symbols")
    public String[] getQuotes(@RequestParam(value="symbol") String[] symbols){
        return symbols;
    }

    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
    public void refreshQuotes() {

    }
}
