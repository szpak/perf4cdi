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

import net.sf.perf4cdi.api.Perf4jProfiled;
import org.perf4j.aop.DefaultProfiled;
import org.perf4j.aop.Profiled;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;

/**
 * Perf4j interceptor for CDI.
 *
 * Note: it's required to turn on that interceptor in beans.xml interceptors:class.
 *
 * @author Marcin Zajączkowski, 2010-05-17
 *
 * @see net.sf.perf4cdi.api.Perf4jProfiled
 */
@Interceptor
@Perf4jProfiled
public class Perf4jProfiledInterceptor {

    private static final long serialVersionUID = -8572318944020712128L;

    /**
     * Delegates performance logging to specific TimingAspect class for annotated methods.
     *
     * @param ic invocation context
     * @return method execution return value
     * @throws Exception exception thrown during excecution
     */
    @AroundInvoke
    public Object aroundInvoke(InvocationContext ic) throws Exception {

        Method method = ic.getMethod();
        if (shouldMethodBeSkipped(method)) {
            return ic.proceed();
        }

        Profiled profiled = getProperProfiledAnnotation(method);

        //TODO: MZA: Could be moved to separate method
        CDITimingAspect ata = newTimingAspect();
        try {
            return ata.doPerfLogging(new CDIJoinPoint(ic), profiled);
        } catch (Exception e) {
            throw e;
        } catch (Throwable throwable) {
            //construction forced by Throwable in AgnosticTimingAspect.runProfiledMethod() vs.
            //Exception in OptimizedInterceptor.aroundInvoke()
            throw new Exception("Throwable in AgnosticTimingAspect.runProfiledMethod()", throwable);
        }
    }

    private boolean shouldMethodBeSkipped(Method method) {
        //Perf4jProfiled can be used only with method or constructor, but due to CDI requirements Perf4jProfiled
        //annotation has to have also TYPE Target. Interceptor could be also used directly so it's better to
        //verify that there is a method to prevent NPE.
        return method == null || method.getAnnotation(Perf4jProfiled.class) == null;
    }

    private Profiled getProperProfiledAnnotation(Method method) {
        Profiled profiled = method.getAnnotation(Profiled.class);
        //when no Profiled annotation was used default values are used
        if (profiled == null) {
            profiled = DefaultProfiled.INSTANCE;
        }
        return profiled;
    }


    /**
     * Returns new TimingAspect.
     *
     * Made mostly for tests.
     *
     * @return new instance of CDITimingAspect
     */
    protected CDITimingAspect newTimingAspect() {
        return new CDITimingAspect();
    }
}
