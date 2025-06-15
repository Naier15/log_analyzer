package com.naier.interfaces;

import java.time.LocalTime;


public interface ICountable {
    boolean isNewTimestamp(LocalTime timestamp);
    void setTimestamp(LocalTime timestamp);
    void increment(boolean is_failed);
    boolean inProgress();
    Pair<Float, LocalTime> summarize();
}