package com.applications.asm.domain.entities;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {
    private final T data;
    private final List<String> errors;

    private Response(T data, List<String> errors) {
        this.data = data;
        this.errors = errors;
    }

    public T getData() {
        return data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(data, new ArrayList<>());
    }

    public static <T> Response<T> error(List<String> errors) {
        return new Response<>(null, errors);
    }
}
