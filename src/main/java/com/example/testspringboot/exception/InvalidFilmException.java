package com.example.testspringboot.exception;

public class InvalidFilmException extends RuntimeException{
    public InvalidFilmException(String message) {
        super(message);
    }
}
