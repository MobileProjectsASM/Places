package com.applications.asm.data.exception;
public enum PlacesDataSourceWSError {
    REDIRECTIONS("Error this service has been redirected"),
    CLIENT_ERROR("The client did something wrong in the request"),
    SERVER_ERROR("There is some error on the server");

    private final String message;
    PlacesDataSourceWSError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
