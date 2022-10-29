package com.skilldistillery.daytrainer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class InsufficientFundsException extends RuntimeException{
	public InsufficientFundsException() {
		super();
	}
	
	public InsufficientFundsException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public InsufficientFundsException(String message) {
		super(message);
	}
	
	public InsufficientFundsException(Throwable cause) {
		super(cause);
	}
	

}
