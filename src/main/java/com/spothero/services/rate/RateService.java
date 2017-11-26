package com.spothero.services.rate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.CompletionStage;

public interface RateService {
    CompletionStage<BigDecimal> findRate(LocalDateTime start, LocalDateTime end);
}
