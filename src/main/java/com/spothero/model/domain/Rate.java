package com.spothero.model.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spothero.config.LocalDateTimeXmlAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@XmlRootElement(name = "rate")
public class Rate {
    private LocalDateTime start;
    private LocalDateTime end;
    private BigDecimal price;

    public Rate() { }
    public Rate(LocalDateTime start, LocalDateTime end, BigDecimal price) {
        this.start = start;
        this.end = end;
        this.price = price;
    }

    @XmlElement(name = "start")
    @XmlJavaTypeAdapter(value = LocalDateTimeXmlAdapter.class)
    @JsonProperty("start")
    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    @XmlElement(name = "end")
    @XmlJavaTypeAdapter(value = LocalDateTimeXmlAdapter.class)
    @JsonProperty("end")
    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @XmlElement(name = "price")
    @JsonProperty("price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rate rate = (Rate) o;

        if (start != null ? !start.equals(rate.start) : rate.start != null) return false;
        if (end != null ? !end.equals(rate.end) : rate.end != null) return false;
        return price != null ? price.equals(rate.price) : rate.price == null;
    }

    @Override
    public int hashCode() {
        int result = start != null ? start.hashCode() : 0;
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
