package com.applications.asm.domain.exception;

public class ParameterException extends Exception {
    private final String message;

    public ParameterException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
