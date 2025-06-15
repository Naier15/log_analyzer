package com.naier;

import static org.junit.Assert.assertTrue;
import java.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import com.naier.exceptions.IllegalException;
import com.naier.utils.Report;


public class ReportTest {

    Report report;

    @Before
    public void init() {
        report = new Report();
    }

    @Test(expected = IllegalException.class)
    public void shouldNotSetTimestampTwice() {
        report.start(LocalTime.now());
        report.start(LocalTime.now());
    }
    
    @Test
    public void shouldBeInProgress() {
        report.start(LocalTime.now());
        assertTrue(report.inProgress());
    }

    @Test
    public void shouldReturnStatus() {
        report.start(LocalTime.of(10, 00, 00));
        report.add(100f, LocalTime.of(10, 05, 00));
        report.add(50f, LocalTime.of(10, 10, 00));
        assertTrue(report.show().equals("10:00:00 10:10:00 75,0"));
    }
}