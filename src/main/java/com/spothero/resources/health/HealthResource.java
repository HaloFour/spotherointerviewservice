package com.spothero.resources.health;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/health")
public class HealthResource {
    @GET
    public Response get() {
        return Response.ok().build();
    }
}
