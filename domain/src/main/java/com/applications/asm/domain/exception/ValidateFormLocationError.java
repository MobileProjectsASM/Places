package com.applications.asm.domain.exception;

public enum ValidateFormLocationError {
    ANY_VALUES_IS_NULL("Any value is null");

    private final String message;
    ValidateFormLocationError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
