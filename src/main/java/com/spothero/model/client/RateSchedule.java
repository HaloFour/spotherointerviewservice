package com.spothero.model.client;

import java.util.List;

public class RateSchedule {
    private List<Rate> rates;

    public RateSchedule() { }
    public RateSchedule(List<Rate> rates) {
        this.rates = rates;
    }

    public List<Rate> getRates() {
        return rates;
    }
}
