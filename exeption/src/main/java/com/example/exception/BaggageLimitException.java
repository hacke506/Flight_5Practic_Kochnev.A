package com.example.exception;

public class BaggageLimitException extends RuntimeException {
    public BaggageLimitException(String message) {
        super(message);
    }

}
