package com.applications.asm.domain.exception;

public enum PlacesRepositoryError {
    ANY_VALUE_IS_NULL("Any value is null"),
    CONNECTION_WITH_SERVER_ERROR("Error to connect with the server"),
    CREATE_REQUEST_ERROR("Error to create request"),
    DO_REQUEST_ERROR("Error to do request"),
    SERVER_ERROR("Error in the server"),
    DECODING_RESPONSE_ERROR("Error to decoding response"),
    RESPONSE_NULL("Error response is null"),
    PAGE_OUT_OF_RANGE("Page out of range");

    private final String message;
    PlacesRepositoryError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
