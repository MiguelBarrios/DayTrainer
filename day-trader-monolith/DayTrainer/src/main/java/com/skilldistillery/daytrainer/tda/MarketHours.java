package com.skilldistillery.daytrainer.tda;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MarketHours {

	private LocalDateTime marketOpen;
	private LocalDateTime marketClose;
	private Boolean isMarketOpen;
}
