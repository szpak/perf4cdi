/*
 * Perf4CDI - Perf4j integration for CDI (JSR-299)
 * Copyright (C) 2010 Marcin Zajaczkowski
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package net.sf.perf4cdi;

import org.perf4j.LoggingStopWatch;
import org.perf4j.aop.AgnosticTimingAspect;
import org.perf4j.aop.Profiled;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.LoggerFactory;


/**
 * An implementation of Perf4j's AgnosticTimingAspect using SLF4J logger to log results.
 *
 * @author Marcin Zajączkowski, 2010-05-22
 */
public class CDITimingAspect extends AgnosticTimingAspect {

    /**
     * Executes the real profiling method from Perf4j.
     *
     * @param cjp CDI join point
     * @param profiled profiled annotation from profiled method
     * @return return value from executed methof
     * @throws Throwable exception thrown by executing method
     */
    public Object doPerfLogging(CDIJoinPoint cjp, Profiled profiled) throws Throwable {
        return runProfiledMethod(cjp, profiled, newStopWatch(profiled.logger(), profiled.level()));
    }

    /**
     * Returns an implementation of StopWatch using Slf4j logger.
     *
     * @param loggerName logger name passed to StopWatch
     * @param levelName logging level name passed to StopWatch
     * @return LoggingStopWatch implementation
     */
    protected LoggingStopWatch newStopWatch(String loggerName, String levelName) {
        //TODO: MZA: A CDI factory could be created to produce loggers 
        return new Slf4JStopWatch(LoggerFactory.getLogger(loggerName), Slf4JStopWatch.mapLevelName(levelName));
    }
}
