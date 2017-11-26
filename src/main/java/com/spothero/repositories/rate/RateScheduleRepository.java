package com.spothero.repositories.rate;

import com.spothero.model.client.RateSchedule;

import java.util.concurrent.CompletionStage;

public interface RateScheduleRepository {
    CompletionStage<RateSchedule> getRateSchedule();
}
