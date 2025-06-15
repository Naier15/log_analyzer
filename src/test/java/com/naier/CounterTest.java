package com.naier;

import static org.junit.Assert.assertTrue;
import java.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import com.naier.utils.Counter;
import com.naier.exceptions.IllegalException;


public class CounterTest {

    Counter counter;

    @Before
    public void init() {
        counter = new Counter();
    }

    @Test
    public void shouldBeWorking() {
        counter.setTimestamp(LocalTime.now());
        counter.increment(false);
        assertTrue(counter.inProgress());
    }

    @Test(expected = IllegalException.class)
    public void shouldNotSetTimestampTwice() {
        counter.setTimestamp(LocalTime.now());
        counter.increment(false);
        counter.setTimestamp(LocalTime.now());
    }

    @Test
    public void shouldBe100Percents() {
        counter.increment(false);
        counter.increment(false);
        counter.increment(false);
        assertTrue(counter.summarize().availability().equals(100f));
    }

    @Test
    public void shouldBe25Percents() {
        counter.increment(true);
        counter.increment(true);
        counter.increment(true);
        counter.increment(false);
        assertTrue(counter.summarize().availability().equals(25f));
    }
}
