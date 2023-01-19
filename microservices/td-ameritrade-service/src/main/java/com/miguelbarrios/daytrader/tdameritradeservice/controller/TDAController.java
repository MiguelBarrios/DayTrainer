package com.miguelbarrios.daytrader.tdameritradeservice.controller;

import java.security.Principal;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import com.miguelbarrios.daytrader.tdameritradeservice.entities.TDAQuote;
import com.miguelbarrios.daytrader.tdameritradeservice.services.TDAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api")
@CrossOrigin({ "*", "http://localhost" })
public class TDAController {

	@Autowired
	private TDAService tdaService;
	
	@RequestMapping(path = "tda/quote/{symbol}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public TDAQuote getQuote(Principal principal, @PathVariable String symbol, HttpServletResponse response) {
		TDAQuote quote = tdaService.getQuote(symbol);
		if(quote == null) {
			response.setStatus(404);
		}
		return quote;
	}
	
	@RequestMapping(path = "tda/quotes/{symbols}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getQuotes(Principal principal, @PathVariable String symbols) {
		return tdaService.getQuotes(symbols);
	}
	
	@GetMapping("v1/tda/isMarketOpen")
	public Boolean isMarketOpen() {
		try {
			return tdaService.isMarketOpen();
		}
		catch(Exception e) {
			log.info(e.getMessage());
			return false;
		}


	}
	
	
	@Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
	public void refreshQuotes() {
		if(!tdaService.isMarketOpen()) {
			if(!tdaService.isInitialized()) {
				tdaService.updateQuotesAll();
			}
		}
		else {
			tdaService.updateQuotesAll();
		}
    }
	
	
}