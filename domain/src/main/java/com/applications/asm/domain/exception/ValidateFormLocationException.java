package com.applications.asm.domain.exception;

public class ValidateFormLocationException extends Exception {
    private final ValidateFormLocationError error;

    public ValidateFormLocationException(ValidateFormLocationError error) {
        this.error = error;
    }

    public ValidateFormLocationError getError() {
        return error;
    }
}
