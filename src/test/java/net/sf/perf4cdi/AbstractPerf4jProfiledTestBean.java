package net.sf.perf4cdi;

import net.sf.perf4cdi.api.Perf4jProfiled;
import org.perf4j.aop.Profiled;

import javax.inject.Named;

/**
 * Abstract super class for ProfiledTestBeans
 *
 * Common part for Seam-Perf4j and Perf4CDI.
 *
 * @author Marcin Zajączkowski, 2010-03-22
 */
public abstract class AbstractPerf4jProfiledTestBean implements Perf4jProfiledTestBean {

    public long profiledMethod(long sleepTime) throws Exception {
        Thread.sleep(sleepTime);
        return sleepTime;
    }
}

/**
 * org.sf.seam.perf4j.SimplePerf4jProfiledTestBean
 *
 * @author Marcin Zajączkowski, 2010-03-22
 */
@Named("profiledWithCustomTag")
class CustomTagPerf4jProfiledTestBean extends AbstractPerf4jProfiledTestBean {

    public static final String CUSTOM_TAG = "profiledWithCustomTagTag";

    @Profiled(tag = CUSTOM_TAG)
    @Perf4jProfiled
    public long profiledMethod(long sleepTime) throws Exception {
        return super.profiledMethod(sleepTime);
    }
}

/**
 * org.sf.seam.perf4j.SimplePerf4jProfiledTestBean
 *
 * @author Marcin Zajączkowski, 2010-03-22
 */
@Named("simpleProfiled")
class SimplePerf4jProfiledTestBean extends AbstractPerf4jProfiledTestBean {

    @Profiled
    @Perf4jProfiled
    public long profiledMethod(long sleepTime) throws Exception {
        return super.profiledMethod(sleepTime);
    }
}

/**
 * org.sf.seam.perf4j.SimplePerf4jProfiledTestBean
 *
 * @author Marcin Zajączkowski, 2010-03-27
 */
@Named("profiledWithoutProfiledAnnotation")
class WithoutProfiledPerf4jProfiledTestBean extends AbstractPerf4jProfiledTestBean {

    @Perf4jProfiled
    public long profiledMethod(long sleepTime) throws Exception {
        return super.profiledMethod(sleepTime);
    }
}

/**
 * org.sf.seam.perf4j.SimplePerf4jProfiledTestBean
 *
 * @author Marcin Zajączkowski, 2010-03-27
 */
@Named("noProfiled")
class NoProfiledPerf4jProfiledTestBean extends AbstractPerf4jProfiledTestBean {

    public long profiledMethod(long sleepTime) throws Exception {
        return super.profiledMethod(sleepTime);
    }
}
