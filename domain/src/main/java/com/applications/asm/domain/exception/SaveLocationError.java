package com.applications.asm.domain.exception;

public enum SaveLocationError {
    ANY_VALUE_IS_NULL("Any value is null");

    private final String message;
    SaveLocationError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
