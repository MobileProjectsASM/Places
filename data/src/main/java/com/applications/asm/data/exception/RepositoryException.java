package com.applications.asm.data.exception;

import androidx.annotation.Nullable;

public class RepositoryException extends Exception {
    private final String message;

    public RepositoryException(String message) {
        this.message = message;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }
}
