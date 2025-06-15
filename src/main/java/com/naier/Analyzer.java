package com.naier;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.naier.interfaces.ICountable;
import com.naier.interfaces.IReportable;
import com.naier.interfaces.Pair;
import com.naier.utils.LogRecord;


class Analyzer {
    private static final Logger logger = LogManager.getLogger(Analyzer.class);
    private final ICountable counter;
    private final IReportable report;

    private float accessThreshold = 100f;
    private float timeThreshold = 10f;

    Analyzer(ICountable counter, IReportable report) {
        this.counter = counter;
        this.report = report;
    }

    public void setAccessThreshold(float accessThreshold) {
        this.accessThreshold = accessThreshold;
    }

    public void setTimeThreshold(float timeThreshold) {
        this.timeThreshold = timeThreshold;
    }

    public void analyze(LogRecord record) {
        LocalTime time = record.getDate();
         
        if (!this.report.inProgress()) { // Новый отчет
            this.report.start(time);
        }
        if (this.counter.isNewTimestamp(time)) { // Дата поменялась
            this.summarize(Optional.empty());
        }
        if (!this.counter.inProgress()) { // Новый счетчик
            this.counter.setTimestamp(time);
        }
        this.counter.increment(record.getStatus() == 500 || record.getTime() > this.timeThreshold);
    }

    private void summarize(Optional<Boolean> toFinish) {
        Pair<Float, LocalTime> result = this.counter.summarize();
        float availability = result.availability();
        LocalTime finishedTime = result.finishedTime();

        if (availability < this.accessThreshold) {
            this.report.add(availability, finishedTime);
        } 
        if (availability >= this.accessThreshold || toFinish.orElse(false)) {
            String report = this.report.show();
            if (Objects.nonNull(report)) {
                logger.info(report);
            }
            this.report.reset();
        }
    }

    public void summarize() {
        this.summarize(Optional.of(true));
    }
}
