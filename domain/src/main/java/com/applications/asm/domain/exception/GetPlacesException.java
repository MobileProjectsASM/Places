package com.applications.asm.domain.exception;

public class GetPlacesException extends Exception {
    private final GetPlacesError error;

    public GetPlacesException(GetPlacesError error) {
        this.error = error;
    }

    public GetPlacesError getPlaceError() {
        return error;
    }
}
