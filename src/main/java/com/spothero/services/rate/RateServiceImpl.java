package com.spothero.services.rate;

import com.spothero.model.client.Rate;
import com.spothero.repositories.rate.RateScheduleRepository;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class RateServiceImpl implements RateService {
    private final RateScheduleRepository rateScheduleRepository;

    @Inject
    public RateServiceImpl(RateScheduleRepository rateScheduleRepository) {
        this.rateScheduleRepository = rateScheduleRepository;
    }

    @Override
    public CompletionStage<BigDecimal> findRate(LocalDateTime start, LocalDateTime end) {
        Objects.requireNonNull(start, "start must not be null.");
        Objects.requireNonNull(end, "end must not be null.");
        if (start.getDayOfWeek() != end.getDayOfWeek()) {
            return CompletableFuture.completedFuture(null);
        }
        if (start.isAfter(end)) {
            return CompletableFuture.completedFuture(null);
        }
        return rateScheduleRepository.getRateSchedule()
                .thenApply(schedule -> schedule.getRates().stream()
                        .filter(rate -> rate.getDays().contains(start.getDayOfWeek())
                                && !rate.getStart().isAfter(start.toLocalTime())
                                && !rate.getEnd().isBefore(end.toLocalTime()))
                        .map(Rate::getPrice)
                        .findFirst()
                        .orElse(null)
                );
    }
}
