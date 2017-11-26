package com.spothero.config.exception;

public class ApiErrorException extends Exception {
    private final ApiError error;

    public ApiErrorException(ApiError error) {
        super(error.description());
        this.error = error;
    }

    public ApiErrorException(ApiError error, Throwable cause) {
        super(error.description(), cause);
        this.error = error;
    }

    public ApiError getError() {
        return this.error;
    }
}
