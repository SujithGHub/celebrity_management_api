package com.example.celebrity_management.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenDataException extends RuntimeException {

    public ForbiddenDataException(final String message) {
        super(message);
    }
}