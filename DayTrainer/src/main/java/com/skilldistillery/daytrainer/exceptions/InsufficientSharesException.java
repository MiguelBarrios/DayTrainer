package com.skilldistillery.daytrainer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class InsufficientSharesException extends RuntimeException{
	public InsufficientSharesException() {
		super();
	}
	
	public InsufficientSharesException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public InsufficientSharesException(String message) {
		super(message);
	}
	
	public InsufficientSharesException(Throwable cause) {
		super(cause);
	}
	
}
