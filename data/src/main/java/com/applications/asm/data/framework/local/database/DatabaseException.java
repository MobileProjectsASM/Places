package com.applications.asm.data.framework.local.database;

public class DatabaseException extends Exception {
    private final Integer code;
    private final String message;

    public DatabaseException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
