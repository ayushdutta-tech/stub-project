package com.bank.eventservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEventException.class)
    public ResponseEntity<String> handleDuplicate(DuplicateEventException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }
}
