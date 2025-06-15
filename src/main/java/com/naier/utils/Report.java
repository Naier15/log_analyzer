package com.naier.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import com.naier.App;
import com.naier.exceptions.IllegalException;
import com.naier.interfaces.IReportable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Report extends ArrayList<Float> implements IReportable {
    private static final Logger logger = LogManager.getLogger(App.class);
    private LocalTime startTime;
    private LocalTime finishTime;

    public void start(LocalTime timestamp) {
        logger.debug("Report started");
        if (Objects.nonNull(this.startTime)) {
            throw new IllegalException("You might not assign a timestamp to working report");
        }
        this.startTime = timestamp;        
    }

    public boolean inProgress() {
        return Objects.nonNull(this.startTime);
    }

    public boolean add(Float percent, LocalTime timestamp) {
        this.finishTime = timestamp;
        boolean result = this.add(percent);
        logger.debug("Added new percent {} from {} to {}", percent, this.startTime, this.finishTime);
        return result;
    }

    public void reset() {
        this.startTime = null;
        this.clear();
        logger.debug("Reset");
    }

    public String show() {
        if (this.size() == 0) {
            return null;
        }
        float availability = this.stream().reduce(0f, Float::sum) / this.size();
        return "%s %s %.1f".formatted(
            this.startTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")), 
            this.finishTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
            availability
        );
    }
}
