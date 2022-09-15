package com.applications.asm.domain.exception;

public class UseCaseException extends Exception {
    private final Integer code;
    private final String message;

    public UseCaseException(Integer code, String message) {
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
