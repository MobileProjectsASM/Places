package com.applications.asm.domain.exception;

public class GetCategoryException extends Exception{
    private final GetCategoryError error;

    public GetCategoryException(GetCategoryError error) {
        this.error = error;
    }

    public GetCategoryError getError() {
        return error;
    }
}
