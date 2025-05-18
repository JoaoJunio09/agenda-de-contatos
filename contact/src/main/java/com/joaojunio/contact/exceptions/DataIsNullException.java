package com.joaojunio.contact.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DataIsNullException extends RuntimeException {
    public DataIsNullException(String message) {
        super(message);
    }
}
