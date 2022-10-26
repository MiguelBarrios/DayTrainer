package com.skilldistillery.daytrainer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UserNotAuthorizedException extends RuntimeException {
	
	public UserNotAuthorizedException() {
		super();
	}
	
	public UserNotAuthorizedException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public UserNotAuthorizedException(String message) {
		super(message);
	}
	
	public UserNotAuthorizedException(Throwable cause) {
		super(cause);
	}


}
