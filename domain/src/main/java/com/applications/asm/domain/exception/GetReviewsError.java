package com.applications.asm.domain.exception;

public enum GetReviewsError {
    CONNECTION_WITH_SERVER_ERROR("Error to connect with the server"),
    REQUEST_RESPONSE_ERROR("Error to communicate with the server"),
    NULL_ID("Id is null"),
    SERVER_ERROR("An error occurred on the server"),
    RESPONSE_NULL("Response is null");

    private final String message;
    GetReviewsError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
