package com.spothero.model.client;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.EnumSet;

public class Rate {
    private final EnumSet<DayOfWeek> days;
    private final LocalTime start;
    private final LocalTime end;
    private final BigDecimal price;

    public Rate(EnumSet<DayOfWeek> days, LocalTime start, LocalTime end, BigDecimal price) {
        this.days = days;
        this.start = start;
        this.end = end;
        this.price = price;
    }

    public EnumSet<DayOfWeek> getDays() {
        return days;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
