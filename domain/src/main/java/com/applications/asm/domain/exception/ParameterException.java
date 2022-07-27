package com.applications.asm.domain.exception;

public class ParameterException extends Exception {
    private final ParameterError parameterError;

    public ParameterException(ParameterError parameterError) {
        super();
        this.parameterError = parameterError;
    }

    public ParameterError getParameterError() {
        return parameterError;
    }
}
