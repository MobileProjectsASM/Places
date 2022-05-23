package com.applications.asm.domain.exception;

public enum PlacesRepositoryError {
    CONNECTION_WITH_SERVER_ERROR("Error to connect with the server"),
    CREATE_REQUEST_ERROR("Error to create request"),
    SERVER_ERROR("Error in the server"),
    DECODING_RESPONSE_ERROR("Error to decoding response"),
    RESPONSE_NULL("Error response is null"),
    NEGATIVE_PAGE("Error the page is negative");

    private final String message;
    PlacesRepositoryError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
