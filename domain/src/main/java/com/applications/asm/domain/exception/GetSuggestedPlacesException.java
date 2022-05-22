package com.applications.asm.domain.exception;

public class GetSuggestedPlacesException extends Exception {
    private final GetSuggestedPlacesError error;

    public GetSuggestedPlacesException(GetSuggestedPlacesError error) {
        this.error = error;
    }

    public GetSuggestedPlacesError getError() {
        return error;
    }
}
