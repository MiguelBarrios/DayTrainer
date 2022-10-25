package com.skilldistillery.daytrainer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TradeNotFoundException extends RuntimeException {
	
	public TradeNotFoundException() {
		super();
	}
	
	public TradeNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public TradeNotFoundException(String message) {
		super(message);
	}
	
	public TradeNotFoundException(Throwable cause) {
		super(cause);
	}

}
