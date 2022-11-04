package com.miguelbarrios.quoteservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)

@Data
public class QuoteWrapper {



    Quote quote;
}
