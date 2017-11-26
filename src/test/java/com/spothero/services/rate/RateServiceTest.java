package com.spothero.services.rate;

import com.spothero.model.client.Rate;
import com.spothero.model.client.RateSchedule;
import com.spothero.repositories.rate.RateScheduleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class RateServiceTest {
    private List<Rate> rates;
    private RateService service;

    @Before
    public void setUp() throws Exception {
        rates = new ArrayList<>();
        RateSchedule schedule = new RateSchedule(rates);

        RateScheduleRepository repository = mock(RateScheduleRepository.class);
        doReturn(CompletableFuture.completedFuture(schedule)).when(repository).getRateSchedule();

        service = new RateServiceImpl(repository);
    }

    @Test
    public void testFindRate() throws Exception {
        BigDecimal expected = new BigDecimal("15.00");

        rates.add(new Rate(EnumSet.of(DayOfWeek.MONDAY), LocalTime.of(8, 0), LocalTime.of(22, 0), new BigDecimal("15.00")));

        CompletionStage<BigDecimal> stage = service.findRate(LocalDateTime.of(2017, 11, 27, 12, 0), LocalDateTime.of(2017, 11, 27, 13, 0));
        BigDecimal actual = stage.toCompletableFuture().get();

        assertEquals(expected, actual);
    }

    @Test
    public void testMultipleRatesDifferentDays() throws Exception {
        BigDecimal expected = new BigDecimal("15.00");

        rates.add(new Rate(EnumSet.of(DayOfWeek.MONDAY), LocalTime.of(8, 0), LocalTime.of(22, 0), new BigDecimal("15.00")));
        rates.add(new Rate(EnumSet.of(DayOfWeek.SUNDAY), LocalTime.of(8, 0), LocalTime.of(22, 0), new BigDecimal("20.00")));

        CompletionStage<BigDecimal> stage = service.findRate(LocalDateTime.of(2017, 11, 27, 12, 0), LocalDateTime.of(2017, 11, 27, 13, 0));
        BigDecimal actual = stage.toCompletableFuture().get();

        assertEquals(expected, actual);
    }

    @Test
    public void testMultipleRatesSameDay() throws Exception {
        BigDecimal expected = new BigDecimal("20.00");

        rates.add(new Rate(EnumSet.of(DayOfWeek.MONDAY), LocalTime.of(8, 0), LocalTime.of(12, 0), new BigDecimal("15.00")));
        rates.add(new Rate(EnumSet.of(DayOfWeek.MONDAY), LocalTime.of(12, 0), LocalTime.of(22, 0), new BigDecimal("20.00")));

        CompletionStage<BigDecimal> stage = service.findRate(LocalDateTime.of(2017, 11, 27, 12, 0), LocalDateTime.of(2017, 11, 27, 13, 0));
        BigDecimal actual = stage.toCompletableFuture().get();

        assertEquals(expected, actual);
    }

    @Test
    public void testSpansRateSchedule() throws Exception {
        rates.add(new Rate(EnumSet.of(DayOfWeek.MONDAY), LocalTime.of(8, 0), LocalTime.of(12, 0), new BigDecimal("15.00")));
        rates.add(new Rate(EnumSet.of(DayOfWeek.MONDAY), LocalTime.of(12, 0), LocalTime.of(22, 0), new BigDecimal("20.00")));

        CompletionStage<BigDecimal> stage = service.findRate(LocalDateTime.of(2017, 11, 27, 11, 0), LocalDateTime.of(2017, 11, 27, 13, 0));
        BigDecimal actual = stage.toCompletableFuture().get();

        assertNull(actual);
    }

    @Test
    public void testNoRatesSpecifiedDay() throws Exception {
        rates.add(new Rate(EnumSet.of(DayOfWeek.SUNDAY), LocalTime.of(8, 0), LocalTime.of(22, 0), new BigDecimal("15.00")));

        CompletionStage<BigDecimal> stage = service.findRate(LocalDateTime.of(2017, 11, 27, 11, 0), LocalDateTime.of(2017, 11, 27, 13, 0));
        BigDecimal actual = stage.toCompletableFuture().get();

        assertNull(actual);
    }
}
