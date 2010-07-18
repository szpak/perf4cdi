package net.sf.perf4cdi;

import org.perf4j.aop.Profiled;

/**
 * net.sf.perf4cdi.RememberingArgumentsInMemoryCDITestTimingAspect
 *
 * @author Marcin Zajączkowski, 2010-07-18
 */
public class RememberingArgumentsInMemoryCDITestTimingAspect extends CDIInMemoryTimingAspect {

    private CDIJoinPoint cdiJoinPoint = null;
    private Profiled profiled = null;

    @Override
    public Object doPerfLogging(CDIJoinPoint cjp, Profiled profiled) throws Throwable {
        this.cdiJoinPoint = cjp;
        this.profiled = profiled;
        return super.doPerfLogging(cjp, profiled);
    }

    public CDIJoinPoint getCdiJoinPoint() {
        return cdiJoinPoint;
    }

    public Profiled getProfiled() {
        return profiled;
    }
}
