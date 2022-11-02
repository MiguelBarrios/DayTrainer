package com.miguelbarrios.quoteservice.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class MarketHours {
    LocalDateTime marketOpen;
    LocalDateTime marketClose;
}
