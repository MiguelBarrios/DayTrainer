package com.miguelbarrios.tradeservice.models;

import lombok.Data;

import java.time.LocalDateTime;


import javax.persistence.*;

@Data
@Entity
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    @Column(name = "price_per_share")
    private double pricePerShare;

    private int quantity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private boolean buy;

    @Column(name = "completion_date")
    private LocalDateTime completionDate;

    @Column(name="stock_symbol")
    private String stockSymbol;

    @Column(name="order_type")
    private String orderType;

}