package com.skilldistillery.daytrainer.market;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.skilldistillery.daytrainer.tda.TDAService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/market")
@CrossOrigin({ "*", "http://localhost" })
public class MarketController {
	

	@Autowired
	private TDAService tdaService;
	
	@RequestMapping("isopen")
	public JsonNode isMarketOpen(HttpServletResponse response) {
		JsonNode data = tdaService.isMarketOpen();
		if(data == null) {
			response.setStatus(404);
		}
		return data;
		
	}

}
