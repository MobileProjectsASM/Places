package com.applications.asm.data.framework.network.graphql;

public class GraphqlException extends Exception {
    private final Integer code;
    private final String message;

    public GraphqlException(Integer code, String message) {
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
