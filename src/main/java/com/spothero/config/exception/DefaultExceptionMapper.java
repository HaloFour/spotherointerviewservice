package com.spothero.config.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultExceptionMapper.class);

    @Override
    public Response toResponse(Throwable throwable) {
        LOG.error("Unexpected exception.", throwable);
        return Response.serverError().build();
    }
}
