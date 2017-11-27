package com.spothero.repositories.statistics;


import com.spothero.model.domain.Statistics;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class StatisticsRepositoryImpl implements StatisticsRepository {
    private final ConcurrentMap<String, Statistics> statistics;

    public StatisticsRepositoryImpl() {
        this.statistics = new ConcurrentHashMap<>();
    }

    @Override
    public void addStatistic(String path, long duration) {
        statistics.compute(path, (key, existing) -> {
            if (existing == null) {
                existing = new Statistics(key);
            }
            existing.increment(duration);
            return existing;
        });
    }

    @Override
    public Map<String, Statistics> getStatistics() {
        return Collections.unmodifiableMap(statistics);
    }

    @Override
    public Statistics getStatistics(String path) {
        return statistics.computeIfAbsent(path, Statistics::new);
    }
}
