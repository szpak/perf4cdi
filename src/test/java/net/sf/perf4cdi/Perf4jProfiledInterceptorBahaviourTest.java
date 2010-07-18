package net.sf.perf4cdi;

import org.perf4j.aop.DefaultProfiled;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Class testing bahaviour of Perf4jProfiledInterceptor.
 *
 * @author Marcin Zajączkowski, 2010-07-18
 */
@Test
public class Perf4jProfiledInterceptorBahaviourTest {

    public void shouldUseDefaultProfiledWhenNoProfiledAnnotationUsed() throws Exception {
        Perf4jProfiledTestBean testBean = new WithoutProfiledPerf4jProfiledTestBean();
        RememberingArgumentsInMemoryCDITestTimingAspect cdiInMemoryTimingAspect = new RememberingArgumentsInMemoryCDITestTimingAspect();
        shouldProfileMethodAndAssertIfNot(testBean, 40, Perf4jProfiledTestBean.PROFILED_METHOD_NAME, cdiInMemoryTimingAspect);
        Assert.assertEquals(cdiInMemoryTimingAspect.getProfiled(), DefaultProfiled.INSTANCE, "profiled instance");
    }

    public void shouldSimpleProfileMethod() throws Exception {
        Perf4jProfiledTestBean profiledBean = new SimplePerf4jProfiledTestBean();
        shouldProfileMethodAndAssertIfNot(profiledBean, 50, Perf4jProfiledTestBean.PROFILED_METHOD_NAME);
    }

    public void shouldProfileMethodWithCustomTag() throws Exception {
        Perf4jProfiledTestBean profiledBean = new CustomTagPerf4jProfiledTestBean();
        shouldProfileMethodAndAssertIfNot(profiledBean, 60, CustomTagPerf4jProfiledTestBean.CUSTOM_TAG);
    }

    public void shouldProfileMethodWithoutProfiledWithMethodName() throws Exception {
        Perf4jProfiledTestBean profiledBean = new WithoutProfiledPerf4jProfiledTestBean();
        shouldProfileMethodAndAssertIfNot(profiledBean, 70, Perf4jProfiledTestBean.PROFILED_METHOD_NAME);
    }

    public void shouldNotProfileMethodWithoutMarkerAnnotation() throws Exception {
        Perf4jProfiledTestBean profiledBean = new NoProfiledPerf4jProfiledTestBean();
        //no profiling should be made - no message should be logged
        shouldProfileMethodAndAssertIfNot(profiledBean, 80, null);
    }

    private void shouldProfileMethodAndAssertIfNot(Perf4jProfiledTestBean profiledBean, long sleepTime,
                           String expectedString) throws Exception {
        shouldProfileMethodAndAssertIfNot(profiledBean, sleepTime, expectedString, new CDIInMemoryTimingAspect());
    }

    private void shouldProfileMethodAndAssertIfNot(Perf4jProfiledTestBean profiledBean, long sleepTime,
                           String expectedString, CDIInMemoryTimingAspect cdiInMemoryTimingAspect) throws Exception {
        //given
        //profiledBean passed as an argument for convenience
        Perf4jProfiledInterceptor pi = createPerf4jProfiledInterceptorWithInMemoryTimingAspect(cdiInMemoryTimingAspect);
        InvocationContext ic = prepareMockInvocationContextForTestBean(profiledBean, sleepTime);
        //when
        long result = (Long) pi.aroundInvoke(ic);
        //then
        Assert.assertEquals(result, sleepTime, "result");
        assertContains(cdiInMemoryTimingAspect.getLastLoggedMessage(), expectedString, "lastLoggedMessage");
    }

    private void assertContains(String actual, String expected, String message) {
        if (expected == null) {
            Assert.assertEquals(actual, expected, message);
        } else {
            if (actual == null || !actual.contains(expected)) {
                Assert.assertTrue(false, message + " should contain: '" + expected + "', but: '" + actual + "'");
            }
        }
    }

    private Perf4jProfiledInterceptor createPerf4jProfiledInterceptorWithInMemoryTimingAspect(
            final CDITimingAspect cdiInMemoryTimingAspect) {
        return new Perf4jProfiledInterceptor() {
            @Override
            protected CDITimingAspect newTimingAspect() {
                return cdiInMemoryTimingAspect;
            }
        };
    }

    //TODO: MZA: Should be moved to a separate class?
    private InvocationContext prepareMockInvocationContextForTestBean(
            final Perf4jProfiledTestBean profiledBean, final long sleepTime) throws Exception {

        final Method method = profiledBean.getClass().getMethod(
                Perf4jProfiledTestBean.PROFILED_METHOD_NAME, long.class);

        return new MockInvocationContext() {
            @Override
            public Method getMethod() {
                return method;
            }

            @Override
            public Object proceed() throws Exception {
                return profiledBean.profiledMethod(sleepTime);
            }
        };
    }

    private class MockInvocationContext implements InvocationContext {
        public Object getTarget() {
            return null;
        }
        public Map<String, Object> getContextData() {
            return null;
        }
        public Method getMethod() {
            return null;
        }
        public Object[] getParameters() {
            return new Object[0];
        }
        public Object proceed() throws Exception {
            return null;
        }
        public void setParameters(Object[] params) {
            //nothing
        }
        public Object getTimer() {
            return null;
        }
    }

}
