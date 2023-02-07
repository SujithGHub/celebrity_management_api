package com.example.celebrity_management.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(final String message){
        super(message);
    }
}
