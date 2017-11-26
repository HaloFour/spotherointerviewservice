package com.spothero.resources.rate;

import com.spothero.config.exception.ApiErrorException;
import com.spothero.config.exception.ApiErrors;
import com.spothero.model.domain.Rate;
import com.spothero.services.rate.RateService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.spothero.resources.AsyncResource.async;

@Path("/rate")
public class RateResource {
    private final RateService rateService;

    @Inject
    public RateResource(RateService service) {
        this.rateService = service;
    }

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public void findRateJson(@QueryParam("start") String start, @QueryParam("end") String end, @Suspended AsyncResponse response) throws ApiErrorException {
        if (start == null || end == null) {
            throw new ApiErrorException(ApiErrors.MISSING_DATE_PARAMETERS);
        }
        LocalDateTime startDate;
        LocalDateTime endDate;
        try {
            startDate = LocalDateTime.parse(start, DateTimeFormatter.ISO_DATE_TIME);
            endDate = LocalDateTime.parse(end, DateTimeFormatter.ISO_DATE_TIME);
        }
        catch (Throwable exception) {
            throw new ApiErrorException(ApiErrors.DATE_BAD_FORMAT, exception);
        }
        async(response, () -> rateService.findRate(startDate, endDate)
                .thenApply(price -> new Rate(startDate, endDate, price))
        );
    }
}
