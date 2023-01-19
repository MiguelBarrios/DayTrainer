package com.miguelbarrios.daytrader.tdameritradeservice.controller;
import com.miguelbarrios.daytrader.tdameritradeservice.entities.TDAQuote;
import com.miguelbarrios.daytrader.tdameritradeservice.services.QuoteService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/tda")
@RestController
public class QuoteController {

    private QuoteService quoteService;

    public QuoteController(QuoteService quoteService){
        this.quoteService = quoteService;
    }

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }

    @GetMapping("/quotes/{symbol}")
    public TDAQuote getQuote(@PathVariable String symbol){
        return quoteService.getQuote(symbol);
    }

}
