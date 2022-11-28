package com.miguelbarrios.tradeservice.controllers;

import java.security.Principal;
import java.util.List;

import com.miguelbarrios.tradeservice.models.StockPosition;
import com.miguelbarrios.tradeservice.models.Trade;
import com.miguelbarrios.tradeservice.services.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/trades")
@CrossOrigin({ "*", "http://localhost" })
@RequiredArgsConstructor
public class TradeController {

    private TradeService tradeService;


    @GetMapping
    public List<Trade> getUserTrades(Principal principal){
        String username = principal.getName();
        return tradeService.getUserTrades(username);
    }


    @PostMapping
    public ResponseEntity<Trade> placeTrade(@RequestBody Trade trade, Principal principal) {

        ResponseEntity<Trade> response;
        try{
            trade = tradeService.placeTrade(principal.getName(), trade);
            response = new ResponseEntity<Trade>(trade, HttpStatus.OK);
        }
        catch (Exception e){
            response = new ResponseEntity<Trade>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return response;
    }


    @GetMapping("position/{ticker}")
    public StockPosition getUserStockPosition(Principal principal, @PathVariable String ticker) {
        String username = principal.getName();
        StockPosition pos = tradeService.getUserStockPosition(username, ticker);
        return pos;
    }


    @GetMapping("position")
    public List<StockPosition> getUserStockPositions(Principal principal){
        return tradeService.getUserStockPositions(principal.getName());
    }

}
