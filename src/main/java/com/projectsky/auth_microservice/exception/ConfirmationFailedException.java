package com.projectsky.auth_microservice.exception;

public class ConfirmationFailedException extends RuntimeException {
    public ConfirmationFailedException(String message) {
        super(message);
    }
}
