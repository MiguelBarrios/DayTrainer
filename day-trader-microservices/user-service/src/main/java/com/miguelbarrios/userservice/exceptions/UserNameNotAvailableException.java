package com.miguelbarrios.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class UserNameNotAvailableException extends RuntimeException{
    public UserNameNotAvailableException() {
        super();
    }

    public UserNameNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNameNotAvailableException(String message) {
        super(message);
    }

    public UserNameNotAvailableException(Throwable cause) {
        super(cause);
    }
}
