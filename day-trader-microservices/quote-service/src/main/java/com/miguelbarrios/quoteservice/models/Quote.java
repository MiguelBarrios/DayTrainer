package com.miguelbarrios.quoteservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@JsonIgnoreProperties(ignoreUnknown = true)

@Data
public class Quote {
    String assetType;
    String assetMainType;
    String symbol;
    String description;
    Double bidPrice;
    Integer bidSize;
    Double askPrice;
    Integer askSize;
    Double lastPrice;
    Integer lastSize;
    Double openPrice;
    Double highPrice;
    Double lowPrice;
    Double closePrice;
    Double netChange;
    Long totalVolume;
    Long quoteTimeInLong;
    Long tradeTimeInLong;
    Double mark;
    String exchange;
    String exchangeName;
    Boolean marginable;
    Boolean shortable;
    Double volatility;
    Double peRatio;
    Double divAmount;
    Double divYield;
    String divDate;
    String securityStatus;
    Double netPercentChangeInDouble;
    Double markChangeInDouble;
    Boolean delay;
    @JsonProperty("52WkHigh")
    Double weekHigh52;
    @JsonProperty("52WkLow")
    Double weekLow52;
}
