package com.spothero.model.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "statistics")
public class Statistics {
    private final String path;
    private final Object lock = new Object();
    private volatile int count;
    private volatile long total;
    private volatile long min;
    private volatile long max;

    public Statistics() {
        this.path = null;
    }

    public Statistics(String path) {
        this.path = path;
    }

    public void increment(long duration) {
        synchronized (lock) {
            count += 1;
            total += duration;
            if (count == 1 || duration < min) {
                min = duration;
            }
            if (count == 1 || duration > max) {
                max = duration;
            }
        }
    }

    @XmlElement
    @JsonProperty
    public int getCount() {
        return count;
    }

    @XmlElement
    @JsonProperty
    public long getTotal() {
        return total;
    }

    @XmlElement
    @JsonProperty
    public double getAverage() {
        synchronized (lock) {
            return ((double)total / count);
        }
    }

    @XmlElement
    @JsonProperty
    public long getMinimum() {
        return min;
    }

    @XmlElement
    @JsonProperty
    public long getMaximum() {
        return max;
    }
}
