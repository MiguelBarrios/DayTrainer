package com.miguelbarrios.stockservice.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Objects;

@RedisHash("Stock")
@Builder
@Data
public class Stock {

	@Id
	private String symbol;

	private String name;

	private String sector;


}