package com.spothero.repositories.rate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spothero.config.ClientMapper;
import com.spothero.model.client.RateSchedule;

import javax.inject.Inject;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class RateScheduleResourceRepository implements RateScheduleRepository {
    private static final String DEFAULT_RESOURCE_NAME = "com/spothero/repositories/rate/rate-schedules.json";

    private final ObjectMapper mapper;

    @Inject
    public RateScheduleResourceRepository(@ClientMapper ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public CompletionStage<RateSchedule> getRateSchedule() {
        try {
            String resourceName = System.getProperty("rate-schedule-resource", DEFAULT_RESOURCE_NAME);
            try (InputStream resource = loadResource(resourceName)) {
                RateSchedule schedule = mapper.readValue(resource, RateSchedule.class);
                return CompletableFuture.completedFuture(schedule);
            }
        }
        catch (Throwable exception) {
            CompletableFuture<RateSchedule> future = new CompletableFuture<>();
            future.completeExceptionally(exception);
            return future;
        }
    }

    private InputStream loadResource(String RESOURCE_NAME) {
        return getClass().getClassLoader().getResourceAsStream(RESOURCE_NAME);
    }
}
