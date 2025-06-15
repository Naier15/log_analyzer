package com.naier.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LogRecord {
    private static final Pattern PATTERN = Pattern.compile(
        ":(\\d{2}:\\d{2}:\\d{2})[\\s\\S]*(\\d{3})\\s\\d+\\s([\\d.]+)"
    );
    private final Matcher matches;
    private final boolean found;
    private Integer cachedStatus;
    private Float cachedTime;
    private LocalTime cachedDate;

    public LogRecord(String log) {
        this.matches = PATTERN.matcher(log);
        this.found = this.matches.find();
    }

    public LocalTime getDate() {
        if (this.cachedDate == null && this.found) {
            this.cachedDate = LocalTime.parse(
                this.matches.group(1), 
                DateTimeFormatter.ofPattern("HH:mm:ss")
            );
        }
        return this.cachedDate;
    }

    public int getStatus() {
        if (this.cachedStatus == null && this.found) {
            this.cachedStatus = Integer.parseInt(
                this.matches.group(2)
            );
        }
        return this.cachedStatus != null ? this.cachedStatus.intValue() : 0;
    }

    public float getTime() {
        if (this.cachedTime == null && this.found) {
            this.cachedTime = Float.parseFloat(
                this.matches.group(3)
            );
        }
        return this.cachedTime != null ? this.cachedTime.floatValue() : 0f;
    }
}
