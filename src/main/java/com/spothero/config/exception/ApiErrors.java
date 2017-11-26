package com.spothero.config.exception;

import javax.ws.rs.core.Response;

public enum ApiErrors implements ApiError {
    MISSING_DATE_PARAMETERS(Response.Status.BAD_REQUEST, "Start and end date query parameters are required."),
    DATE_BAD_FORMAT(Response.Status.BAD_REQUEST, "Date parameters must be in ISO-8601 format.")
    ;

    private final Response.Status status;
    private final String description;

    ApiErrors(Response.Status status, String description) {
        this.status = status;
        this.description = description;
    }

    @Override
    public Response.Status status() {
        return this.status;
    }

    @Override
    public String description() {
        return this.description;
    }
}
