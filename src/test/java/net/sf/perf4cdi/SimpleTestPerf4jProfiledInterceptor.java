package net.sf.perf4cdi;

import net.sf.perf4cdi.api.Perf4jProfiled;

import javax.interceptor.Interceptor;

/**
 * Simple test variation of Perf4jProfiledInterceptor which allows to check if interceptor was called.
 *
 * Note: This implementation is not thread-safe. Cannot be called simultaneously from multiple threads.
 *
 * @author Marcin Zajączkowski, 2010-07-18
 */
@Interceptor
@Perf4jProfiled
public class SimpleTestPerf4jProfiledInterceptor extends Perf4jProfiledInterceptor {

    static boolean aroundInvokeCalled = false;

    @Override
    protected CDITimingAspect newTimingAspect() {
        aroundInvokeCalled = true;
        return super.newTimingAspect();
    }
}
