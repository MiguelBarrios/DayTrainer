package com.miguelbarrios.daytrader.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserNameUnavailableException extends RuntimeException{
    public UserNameUnavailableException(String message){
        super(message);
    }
}
