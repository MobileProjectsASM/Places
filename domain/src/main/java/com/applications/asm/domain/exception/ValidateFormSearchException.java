package com.applications.asm.domain.exception;

public class ValidateFormSearchException extends Exception {
    private final ValidateFormSearchError error;

    public ValidateFormSearchException(ValidateFormSearchError error) {
        this.error = error;
    }

    public ValidateFormSearchError getError() {
        return error;
    }
}
