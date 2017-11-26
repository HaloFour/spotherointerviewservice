package com.spothero.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.spothero.model.client.Rate;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RateDeserializer extends JsonDeserializer<Rate> {
    private static final Map<String, DayOfWeek> DAYS_OF_WEEK;
    private static final DateTimeFormatter TIME_FORMATTER;
    private static final BigDecimal HUNDRED;

    static {
        DAYS_OF_WEEK = new HashMap<>();
        DAYS_OF_WEEK.put("mon", DayOfWeek.MONDAY);
        DAYS_OF_WEEK.put("tues", DayOfWeek.TUESDAY);
        DAYS_OF_WEEK.put("wed", DayOfWeek.WEDNESDAY);
        DAYS_OF_WEEK.put("thurs", DayOfWeek.THURSDAY);
        DAYS_OF_WEEK.put("fri", DayOfWeek.FRIDAY);
        DAYS_OF_WEEK.put("sat", DayOfWeek.SATURDAY);
        DAYS_OF_WEEK.put("sun", DayOfWeek.SUNDAY);
        TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmm");
        HUNDRED = new BigDecimal(100L);
    }

    @Override
    public Rate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);

        String daysValue = node.get("days").textValue();
        String timesValue = node.get("times").textValue();
        Long priceValue = node.get("price").longValue();

        EnumSet<DayOfWeek> days = parseDays(daysValue);
        LocalTime[] times = parseTimes(timesValue);
        BigDecimal price = parsePrice(priceValue);

        return new Rate(days, times[0], times[1], price);
    }

    /* package */ EnumSet<DayOfWeek> parseDays(String daysValue) {
        String[] parts = daysValue.split(",");
        Set<DayOfWeek> set = new HashSet<>(parts.length);
        for (String part : parts) {
            DayOfWeek day = DAYS_OF_WEEK.get(part);
            if (day != null) {
                set.add(day);
            }
        }
        return EnumSet.copyOf(set);
    }

    /* package */ LocalTime[] parseTimes(String timesValue) {
        String[] parts = timesValue.split("-", 2);
        LocalTime start = LocalTime.parse(parts[0], TIME_FORMATTER);
        LocalTime end = LocalTime.parse(parts[1], TIME_FORMATTER);
        return new LocalTime[] { start, end };
    }

    /* package */ BigDecimal parsePrice(long priceValue) {
        BigDecimal priceDecimal = new BigDecimal(priceValue);
        return priceDecimal.divide(HUNDRED, 2, RoundingMode.UNNECESSARY);
    }
}
