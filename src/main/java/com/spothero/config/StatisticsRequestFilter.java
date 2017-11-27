package com.spothero.config;

import com.google.common.base.Stopwatch;
import com.spothero.repositories.statistics.StatisticsRepository;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.uri.UriTemplate;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StatisticsRequestFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private static final String STOPWATCH_PROPERTY = StatisticsRequestFilter.class.getName() + ".stopwatch";

    private final StatisticsRepository repository;

    @Inject
    public StatisticsRequestFilter(StatisticsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        containerRequestContext.setProperty(STOPWATCH_PROPERTY, Stopwatch.createStarted());
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        List<UriTemplate> templates = ((ContainerRequest) containerRequestContext).getUriInfo().getMatchedTemplates();
        if (templates.size() > 0) {
            UriTemplate template = templates.get(0);
            String path = template.getTemplate();

            Stopwatch stopwatch = (Stopwatch)containerRequestContext.getProperty(STOPWATCH_PROPERTY);
            stopwatch.stop();
            long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);

            repository.addStatistic(path, elapsed);
        }
    }
}
