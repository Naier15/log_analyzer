package com.naier.interfaces;

import java.time.LocalTime;


public interface IReportable {
    void start(LocalTime timestamp);
    boolean add(Float percent, LocalTime timestamp);
    void reset();
    String show();
    boolean inProgress();
}