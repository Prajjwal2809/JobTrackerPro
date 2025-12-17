package com.jobtracker.common.exceptions;

public class UnauthorisedException extends RuntimeException {
    public UnauthorisedException(String message) {
        super(message);
    }
    
}
