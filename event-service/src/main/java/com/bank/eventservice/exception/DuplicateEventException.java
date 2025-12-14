package com.bank.eventservice.exception;

public class DuplicateEventException extends RuntimeException {
    public DuplicateEventException(String message) {
        super(message);
    }
}
