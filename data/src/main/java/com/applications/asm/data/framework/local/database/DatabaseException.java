package com.applications.asm.data.framework.local.database;

public class DatabaseException extends Exception {
    private final String message;

    public DatabaseException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
