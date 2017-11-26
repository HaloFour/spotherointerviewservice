package com.spothero.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.hk2.api.Factory;

public class JacksonClientConfig implements Factory<ObjectMapper> {
    private final ObjectMapper mapper;

    public JacksonClientConfig() {
        mapper = new ObjectMapper();
        mapper.registerModule(new RateModule());
    }

    @Override
    public ObjectMapper provide() {
        return mapper;
    }

    @Override
    public void dispose(ObjectMapper objectMapper) { }
}
