package com.applications.asm.data.exception;
public class PlacesDataSourceWSException extends Exception {
    private final PlacesDataSourceWSError error;

    public PlacesDataSourceWSException(PlacesDataSourceWSError error) {
        this.error = error;
    }

    public PlacesDataSourceWSError getError() {
        return error;
    }
}
