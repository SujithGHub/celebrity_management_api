package com.example.celebrity_management.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidDataException extends RuntimeException {

    public InvalidDataException(final String message) {
        super(message);
    }
}
