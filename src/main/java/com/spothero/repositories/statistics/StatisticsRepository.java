package com.spothero.repositories.statistics;

import com.spothero.model.domain.Statistics;

import java.util.Map;

public interface StatisticsRepository {
    void addStatistic(String path, long duration);
    Map<String, Statistics> getStatistics();
    Statistics getStatistics(String path);
}
