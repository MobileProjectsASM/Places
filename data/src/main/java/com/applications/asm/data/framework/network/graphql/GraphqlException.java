package com.applications.asm.data.framework.network.graphql;

import androidx.annotation.Nullable;

public class GraphqlException extends Exception {
    private final String message;

    public GraphqlException(String message) {
        this.message = message;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }
}
