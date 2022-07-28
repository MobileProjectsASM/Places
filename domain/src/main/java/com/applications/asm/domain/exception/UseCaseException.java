package com.applications.asm.domain.exception;

public class UseCaseException extends Exception {
    private final String message;

    public UseCaseException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
