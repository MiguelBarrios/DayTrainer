package com.skilldistillery.daytrainer.tda;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TDAQuote {
	
	private String symbol;
	
	private String description;
	
	private Double lastPrice;
	
	private Double openPrice;
	
	private Double closePrice;
	
	private Double lowPrice;
	
	private Double highPrice;
	
	private Long totalVolume;
	
	private Double netChange;
	
	private Double volatility;
	
	@JsonProperty("52WkHigh")
	Double weekHigh52;
	
	@JsonProperty("52WkLow")
	Double weekLow52;
	
	private Double peRatio;
	
	private Double divAmount;
	
	private String divDate;
	
	private double divYield;
	
}
