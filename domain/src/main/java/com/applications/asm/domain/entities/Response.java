package com.applications.asm.domain.entities;

public class Response<T> {
    private final T data;
    private final String error;

    private Response(T data, String error) {
        this.data = data;
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(data, "");
    }

    public static <T> Response<T> error(String error) {
        return new Response<>(null, error);
    }
}
