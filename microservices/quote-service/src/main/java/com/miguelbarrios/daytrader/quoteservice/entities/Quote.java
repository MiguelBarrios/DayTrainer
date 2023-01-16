package com.miguelbarrios.daytrader.quoteservice.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;

@Data
@RedisHash("Quote")
public class Quote {

    @Id
    private String symbol;
    private BigDecimal price;

}
