package com.spothero;

import com.spothero.config.JacksonServerConfig;
import com.spothero.config.ServiceBinder;
import com.spothero.config.StatisticsRequestFilter;
import com.spothero.config.exception.ApiErrorExceptionMapper;
import com.spothero.config.exception.DefaultExceptionMapper;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

public class Program {
    private static final Logger LOG = LoggerFactory.getLogger(Program.class);

    public static void main(String[] args) throws IOException {
        String host = Optional.ofNullable(System.getProperty("HOST_NAME")).orElse("localhost");
        String port = Optional.ofNullable(System.getProperty("PORT")).orElse("8080");
        String baseUri = "http://" + host + ":" + port + "/";
        HttpServer server = createHttpServer(baseUri);
        server.start();
        try {
            LOG.info("Service running at {}", baseUri);
            int ignored = System.in.read();
        }
        finally {
            server.shutdownNow();
        }
    }

    private static HttpServer createHttpServer(String uri) {
        ResourceConfig config = createConfiguration();
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(uri), config, false);
    }

    public static ResourceConfig createConfiguration() {
        ResourceConfig config = new ResourceConfig();
        config.packages("com.spothero.resources");
        config.register(JacksonServerConfig.class);
        config.register(new ServiceBinder());
        config.register(ApiErrorExceptionMapper.class);
        config.register(DefaultExceptionMapper.class);
        config.register(StatisticsRequestFilter.class);
        return config;
    }
}
