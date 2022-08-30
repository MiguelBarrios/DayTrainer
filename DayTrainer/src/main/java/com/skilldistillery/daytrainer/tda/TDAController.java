package com.skilldistillery.daytrainer.tda;

import java.security.Principal;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@CrossOrigin({ "*", "http://localhost" })
public class TDAController {
	
	
	@Autowired
	private TDAService tdaService;
	
	@RequestMapping(path = "tda/quote/{symbol}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getQuote(Principal principal, @PathVariable String symbol, HttpServletResponse response) {
		String res = tdaService.getQuote(symbol);
		if(res == null) {
			response.setStatus(404);
		}
		return res;
	}
	
	@RequestMapping(path = "tda/quotes/{symbols}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getQuotes(Principal principal, @PathVariable String symbols) {
		return tdaService.getQuotes(symbols);
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
