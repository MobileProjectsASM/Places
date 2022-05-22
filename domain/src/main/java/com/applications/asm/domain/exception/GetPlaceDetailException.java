package com.applications.asm.domain.exception;

public class GetPlaceDetailException extends Exception {
    private final GetPlaceDetailError error;

    public GetPlaceDetailException(GetPlaceDetailError error) {
        this.error = error;
    }

    public GetPlaceDetailError getError() {
        return error;
    }
}
