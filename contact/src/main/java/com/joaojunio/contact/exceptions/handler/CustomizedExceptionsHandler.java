package com.joaojunio.contact.exceptions.handler;

import com.fasterxml.jackson.databind.DatabindException;
import com.joaojunio.contact.exceptions.ExceptionResponse;
import com.joaojunio.contact.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedExceptionsHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
            ex.getMessage(),
            request.getDescription(false),
            new Date()
        );
        return ResponseEntity.ok(exceptionResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
            ex.getMessage(),
            request.getDescription(false),
            new Date()
        );
        return ResponseEntity.ok(exceptionResponse);
    }
}
