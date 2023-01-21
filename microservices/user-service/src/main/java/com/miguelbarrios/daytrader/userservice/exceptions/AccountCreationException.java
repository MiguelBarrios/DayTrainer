package com.miguelbarrios.daytrader.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AccountCreationException extends RuntimeException{
    public AccountCreationException(String message){
        super(message);
    }
}
