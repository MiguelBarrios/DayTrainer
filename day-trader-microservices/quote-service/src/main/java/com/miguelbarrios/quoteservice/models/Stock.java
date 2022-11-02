package com.miguelbarrios.quoteservice.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Stock {

	private String symbol;

	private String name;

	private String sector;
}