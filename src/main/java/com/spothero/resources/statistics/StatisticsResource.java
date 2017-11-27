package com.spothero.resources.statistics;

import com.spothero.model.domain.Statistics;
import com.spothero.repositories.statistics.StatisticsRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Path("/statistics")
public class StatisticsResource {
    private final StatisticsRepository repository;

    @Inject
    public StatisticsResource(StatisticsRepository repository) {
        this.repository = repository;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Map<String, Statistics> get() {
        return repository.getStatistics();
    }
}
