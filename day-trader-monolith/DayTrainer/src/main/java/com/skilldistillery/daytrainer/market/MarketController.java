package com.skilldistillery.daytrainer.market;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.skilldistillery.daytrainer.tda.TDAClient;
import com.skilldistillery.daytrainer.tda.TDAService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/market")
@CrossOrigin({ "*", "http://localhost" })
public class MarketController {
	

	@Autowired
	private TDAService tdaService;
	
	@Autowired
	private TDAClient tdaClient;
	
	@GetMapping("hours")
	public String isMarketOpen(HttpServletResponse response) {
		tdaService.isMarketOpen();
		JsonNode data = tdaClient.getMarketHours2();
		if(data == null) {
			response.setStatus(404);
		}

		return data.toString();
		
	}

}
