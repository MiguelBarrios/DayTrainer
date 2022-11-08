package com.miguelbarrios.tradeservice.models;


public class StockPosition {

    private String symbol;

    private int numberOfShares;

    private double avgCostPerShare;

    private double lastPrice;

    public StockPosition() {
    }

    public StockPosition(String symbol, int numberOfShares, double avgCostPerShare, double lastPrice) {
        super();
        this.symbol = symbol;
        this.numberOfShares = numberOfShares;
        this.avgCostPerShare = avgCostPerShare;
        this.lastPrice = lastPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public double getAvgCostPerShare() {
        return avgCostPerShare;
    }

    public void setAvgCostPerShare(double avgCostPerShare) {
        this.avgCostPerShare = avgCostPerShare;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    @Override
    public String toString() {
        return "StockPosition [symbol=" + symbol + ", numberOfShares=" + numberOfShares + ", avgCostPerShare="
                + avgCostPerShare + ", lastPrice=" + lastPrice + "]";
    }
}
