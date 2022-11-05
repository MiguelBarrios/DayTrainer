package com.miguelbarrios.quoteservice.controllers;

import com.miguelbarrios.quoteservice.exceptions.StockNotSupportedException;
import com.miguelbarrios.quoteservice.models.Quote;
import com.miguelbarrios.quoteservice.services.quoteservice.QuoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/quotes")
@CrossOrigin({ "*", "http://localhost" })
@Slf4j
public class QuoteController {

    private final QuoteService quoteService;

    @GetMapping("/{stockSymbol}")
    public ResponseEntity<Quote> getQuote(@PathVariable String stockSymbol){
        ResponseEntity<Quote> response;

        try{
            Quote quote = quoteService.getQuote(stockSymbol);
            response = new ResponseEntity<Quote>(quote, HttpStatus.OK);
        }
        catch (StockNotSupportedException e){
            log.info("Invalid stock requested: " + stockSymbol);
            response = new ResponseEntity<Quote>(HttpStatus.NOT_FOUND);
        }

        return response;
    }


    @GetMapping("/symbols")
    public List<Quote> getQuotes(@RequestParam(value="symbol") String[] symbols){
        return quoteService.getQuotes(symbols);
    }

    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
    public void refreshQuotes() {
        quoteService.updateSMP500Quotes();
    }
}
