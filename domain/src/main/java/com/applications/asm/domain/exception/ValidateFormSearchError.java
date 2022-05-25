package com.applications.asm.domain.exception;

public enum ValidateFormSearchError {
    ANY_VALUES_IS_NULL("Any value is null");

    private final String message;
    ValidateFormSearchError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
