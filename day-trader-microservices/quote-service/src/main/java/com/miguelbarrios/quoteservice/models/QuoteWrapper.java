package com.miguelbarrios.quoteservice.models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Collections;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)

@Data
public class QuoteWrapper {


    @JsonProperty("quote")
    Quote quote;

    @JsonAnyGetter
    public Map<String, Object> any() {
        //add the custom name here
        //use full HashMap if you need more than one property
        return Collections.singletonMap(quote.getClass().getName(), quote);
    }
}
