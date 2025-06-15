package com.naier.utils;

import java.time.LocalTime;
import java.util.Objects;
import com.naier.exceptions.IllegalException;
import com.naier.interfaces.ICountable;
import com.naier.interfaces.Pair;
import lombok.Getter;


public class Counter implements ICountable {
    @Getter
    private LocalTime timestamp;
    private int counter = 0;
    private int failed = 0;

    public void setTimestamp(LocalTime timestamp) {
        Objects.requireNonNull(timestamp, "Timestamp cannot be null");
        if (inProgress()) {
            throw new IllegalException("You might not assign a timestamp to working counter");
        }
        this.timestamp = timestamp;
    }

    public boolean inProgress() {
        return this.counter > 0;
    }

    public boolean isNewTimestamp(LocalTime timestamp) {
        return Objects.nonNull(this.timestamp) && !this.timestamp.equals(timestamp);
    }

    public void increment(boolean is_failed) {
        this.counter++;
        if (is_failed) {
            this.failed++;
        }
    }

    private float calculate_availability() {
        if (this.counter == 0) {
            return 100f;
        }
        return 100f - ((float)this.failed / (float)this.counter * 100f);
    }

    public void reset() {
        this.timestamp = null;
        this.counter = 0;
        this.failed = 0;
    }

    public Pair<Float, LocalTime> summarize() {
        float availability = this.calculate_availability();
        LocalTime finishedTime = this.getTimestamp();
        this.reset();
        return new Pair<Float, LocalTime>(availability, finishedTime);
    }
}
