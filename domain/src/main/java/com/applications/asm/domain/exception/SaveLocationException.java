package com.applications.asm.domain.exception;

public class SaveLocationException extends Exception {
    private final SaveLocationError error;

    public SaveLocationException(SaveLocationError error) {
        this.error = error;
    }

    public SaveLocationError getError() {
        return error;
    }
}
