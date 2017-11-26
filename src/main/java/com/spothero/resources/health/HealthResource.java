package com.spothero.resources.health;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/health")
public class HealthResource {
    @GET
    public String get() {
        return "ok";
    }
}
