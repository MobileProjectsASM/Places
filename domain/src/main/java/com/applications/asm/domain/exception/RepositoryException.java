package com.applications.asm.domain.exception;

public class RepositoryException extends Exception {
    private final Integer code;
    private final String message;

    public RepositoryException(Integer code, String message) {
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
