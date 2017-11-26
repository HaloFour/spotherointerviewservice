package com.spothero.resources.rate;

import com.spothero.config.exception.ApiErrorException;
import com.spothero.model.domain.Rate;
import com.spothero.services.rate.RateService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.container.AsyncResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RateResourceTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private RateResource resource;
    private RateService service;
    private AsyncResponse response;

    @Before
    public void setUp() throws Exception {
        service = mock(RateService.class);

        resource = new RateResource(service);

        response = mock(AsyncResponse.class);
    }

    @Test
    public void testFindRate() throws Exception {
        Rate expected = new Rate(LocalDateTime.of(2017, 11, 26, 12, 0), LocalDateTime.of(2017, 11, 26, 13, 0), new BigDecimal("15.00"));

        doReturn(CompletableFuture.completedFuture(new BigDecimal("15.00")))
                .when(service)
                .findRate(eq(LocalDateTime.of(2017, 11, 26, 12, 0)),
                        eq(LocalDateTime.of(2017, 11, 26, 13, 0)));

        resource.findRateJson("2017-11-26T12:00:00Z", "2017-11-26T13:00:00Z", response);

        verify(response, never()).resume(any(Throwable.class));
        verify(response, times(1)).resume(eq(expected));
    }

    @Test
    public void testMissingQueryParamsThrowsException() throws Exception {
        expectedException.expect(ApiErrorException.class);
        expectedException.expectMessage("Start and end date query parameters are required.");

        resource.findRateJson(null, null, response);
    }

    @Test
    public void testQueryParamsInvalidFormat() throws Exception {
        expectedException.expect(ApiErrorException.class);
        expectedException.expectMessage("Date parameters must be in ISO-8601 format.");

        resource.findRateJson("foo", "bar", response);
    }
}
