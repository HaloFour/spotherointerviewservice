package com.spothero.config.exception;

import javax.ws.rs.core.Response;

public interface ApiError {
    String name();
    Response.Status status();
    String description();
}
