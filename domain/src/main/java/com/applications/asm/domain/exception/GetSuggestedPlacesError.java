package com.applications.asm.domain.exception;

public enum GetSuggestedPlacesError {
    ANY_VALUE_IS_NULL("Some value is null"),
    CONNECTION_WITH_SERVER_ERROR("Error to connect with the server"),
    REQUEST_RESPONSE_ERROR("Error to communicate with the server"),
    LAT_LON_OUT_OF_RANGE("Coordinates out of range"),
    SERVER_ERROR("An error occurred on the server"),
    RESPONSE_NULL("Response is null"),
    NETWORK_ERROR("There some error in the network");

    private final String message;
    GetSuggestedPlacesError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
