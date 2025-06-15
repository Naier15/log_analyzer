package com.naier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.naier.interfaces.ICountable;
import com.naier.interfaces.IReportable;
import com.naier.utils.Counter;
import com.naier.utils.LogRecord;
import com.naier.utils.Report;


public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) throws IOException {
        logger.info("The app started");
        
        ICountable counter = new Counter();
        IReportable report = new Report();
        Analyzer analyzer = new Analyzer(counter, report);

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-u":
                    analyzer.setAccessThreshold(
                        Float.parseFloat(args[++i])
                    );
                    break;
                case "-t":
                    analyzer.setTimeThreshold(
                        Float.parseFloat(args[++i])
                    );
                    break;
                default:
                    break;
            }
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LogRecord record = new LogRecord(line);
                analyzer.analyze(record);
            }
            analyzer.summarize();
            logger.info("Finished successfully");
        } catch (IOException ex) {
            logger.error(ex);
            System.exit(1);
        }
        
    }
}