package com.applications.asm.domain.exception;

public enum GetCategoryError {
    CONNECTION_WITH_SERVER_ERROR("Error to connect with the server"),
    REQUEST_RESPONSE_ERROR("Error to communicate with the server"),
    ANY_VALUE_IS_NULL("Some value is null"),
    SERVER_ERROR("An error occurred on the server"),
    RESPONSE_NULL("Response is null");

    private final String message;
    GetCategoryError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
