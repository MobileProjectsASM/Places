package com.applications.asm.domain.exception;

public enum GetPlacesError {
    ANY_VALUE_IS_NULL("Some value is null"),
    LAT_LON_OUT_OF_RANGE("Coordinates out of range"),
    NEGATIVE_RADIUS("Radius is negative"),
    NEGATIVE_PAGE("Page is negative"),
    CONNECTION_WITH_SERVER_ERROR("Error to connect with the server"),
    REQUEST_RESPONSE_ERROR("Error to communicate with the server"),
    RESPONSE_NULL("Response is null");

    private final String message;
    GetPlacesError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
