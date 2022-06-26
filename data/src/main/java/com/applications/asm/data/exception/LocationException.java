package com.applications.asm.data.exception;

public class LocationException extends Exception {
    private final String message;

    public LocationException(String message) {
        this.message = message;
    }

    public String getError() {
        return message;
    }
}
