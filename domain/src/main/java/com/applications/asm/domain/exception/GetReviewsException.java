package com.applications.asm.domain.exception;

public class GetReviewsException extends Exception {
    private final GetReviewsError error;

    public GetReviewsException(GetReviewsError error) {
        this.error = error;
    }

    public GetReviewsError getError() {
        return error;
    }
}
