package com.applications.asm.data.framework.network.api_rest;

import androidx.annotation.Nullable;

public class RestException extends Exception {
    private final String message;

    public RestException(String message) {
        this.message = message;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }
}
