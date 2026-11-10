package com.example.exception;

public class FlightValidationExpection extends Exception {
    public FlightValidationExpection(String message) {
        super(message);
    }
}