package com.skilldistillery.daytrainer.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Quote {
    private String assetType;
    private String assetMainType; 
    private String cusip;
    private String assetSubType; 
    private String symbol;
    
    public Quote() {}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getAssetMainType() {
		return assetMainType;
	}

	public void setAssetMainType(String assetMainType) {
		this.assetMainType = assetMainType;
	}

	public String getCusip() {
		return cusip;
	}

	public void setCusip(String cusip) {
		this.cusip = cusip;
	}

	public String getAssetSubType() {
		return assetSubType;
	}

	public void setAssetSubType(String assetSubType) {
		this.assetSubType = assetSubType;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String toString() {
		return "Quote [assetType=" + assetType + ", assetMainType=" + assetMainType + ", cusip=" + cusip
				+ ", assetSubType=" + assetSubType + ", symbol=" + symbol + "]";
	}
    
    
    
	
}
