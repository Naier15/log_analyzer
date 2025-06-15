package com.naier;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import com.naier.utils.LogRecord;


public class LogRecordTest {

    LogRecord logRecord;

    @Before
    public void init() {
        logRecord = new LogRecord(
            "192.168.32.181 - - [14/06/2017:16:48:52 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=c002522b HTTP/1.1\" 200 2 26.992194 \"-\" \"@list-item-updater\" prio:0"
        );
    }
    
    @Test
    public void shouldReturnDate() {
        assertTrue(logRecord.getDate().toString().equals("16:48:52"));
    }

    @Test
    public void shouldReturnStatus() {
        assertTrue(logRecord.getStatus() == 200);
    }

    @Test
    public void shouldReturnTime() {
        assertTrue(logRecord.getTime() == 26.992194f);
    }

    @Test
    public void shouldCacheFields() {
        assertTrue(logRecord.getDate() == logRecord.getDate());
    }
}
