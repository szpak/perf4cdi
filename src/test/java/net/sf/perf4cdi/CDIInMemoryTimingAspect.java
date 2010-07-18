package net.sf.perf4cdi;

import org.perf4j.LoggingStopWatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple in memory timing aspect used in tests.
 *
 * Common part for Seam-Perf4j and Perf4CDI.
 *
 * @author Marcin Zajączkowski, 2010-03-14
 */
public class CDIInMemoryTimingAspect extends CDITimingAspect {

    private List<String> loggedMessages = new ArrayList<String>();
    
    @Override
    protected LoggingStopWatch newStopWatch(String loggerName, String levelName) {

        return new LoggingStopWatch() {
            @Override
            public boolean isLogging() {
                return true;
            }

            @Override
            protected void log(String stopWatchAsString, Throwable exception) {
                loggedMessages.add(stopWatchAsString);
            }
        };
    }

    public String getLastLoggedMessage() {
        if (!loggedMessages.isEmpty()) {
            return loggedMessages.get(loggedMessages.size() - 1);
        } else {
            return null;
        }
    }
}
