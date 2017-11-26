package com.spothero.config.exception;

import com.spothero.model.domain.ErrorBody;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ApiErrorExceptionMapper implements ExceptionMapper<ApiErrorException> {
    @Override
    public Response toResponse(ApiErrorException exception) {
        ApiError error = exception.getError();
        ErrorBody body = new ErrorBody(error.name(), error.description());
        return Response.status(error.status()).entity(body).build();
    }
}
