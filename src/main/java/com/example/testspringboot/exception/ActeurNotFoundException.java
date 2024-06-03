package com.example.testspringboot.exception;

public class ActeurNotFoundException extends RuntimeException {
    public ActeurNotFoundException(String message) {
        super(message);
    }
}
