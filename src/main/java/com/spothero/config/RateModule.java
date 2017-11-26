package com.spothero.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.spothero.model.client.Rate;

public class RateModule extends SimpleModule {
    public RateModule() {
        addDeserializer(Rate.class, new RateDeserializer());
    }
}
