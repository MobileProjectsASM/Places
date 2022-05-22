package com.applications.asm.domain.exception;

public class PlacesRepositoryException extends Exception {
    private final PlacesRepositoryError error;

    public PlacesRepositoryException(PlacesRepositoryError error) {
        this.error = error;
    }

    public PlacesRepositoryError getError() {
        return error;
    }
}
