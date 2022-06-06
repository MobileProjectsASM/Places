package com.applications.asm.domain.exception;

public enum GetPlaceDetailError {
    CONNECTION_WITH_SERVER_ERROR("Error to connect with the server"),
    REQUEST_RESPONSE_ERROR("Error to communicate with the server"),
    NULL_ID("Id is null"),
    SERVER_ERROR("An error occurred on the server"),
    RESPONSE_NULL("Response is null"),
    NETWORK_ERROR("There some error in the network");

    private final String message;
    GetPlaceDetailError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
