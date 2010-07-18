package net.sf.perf4cdi;

/**
 * Interface for Perf4j profiled test bean.
 * 
 * Common part for Seam-Perf4j and Perf4CDI.
 *
 * @author Marcin Zajączkowski, 2010-03-22
 */
public interface Perf4jProfiledTestBean {

    public static final String PROFILED_METHOD_NAME = "profiledMethod";
    
    public long profiledMethod(long sleepTime) throws Exception;
}
