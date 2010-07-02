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

import org.perf4j.aop.AbstractJoinPoint;

import javax.interceptor.InvocationContext;

/**
 * An implementation of AbstractJoinPoint from Perf4j library for CDI.
 *
 * CDIJoinPoint should be only created for method execution.
 * 
 * Note: that construction required at least version 0.9.13 of Perf4j.
 *
 * @author Marcin Zajączkowski, 2010-05-22
 */
public class CDIJoinPoint implements AbstractJoinPoint {

    private final InvocationContext ic;

    /**
     * Constructor with CDI InvocationContext to wrap.
     *
     * @param ic original InvocationContext to wrap
     */
    public CDIJoinPoint(InvocationContext ic) {
        this.ic = ic;
    }

    /**
     * {@inheritDoc}
     */
    public Object proceed() throws Exception {
        return ic.proceed();
    }

    /**
     * {@inheritDoc}
     */
    public Object getExecutingObject() {
        return ic.getTarget();
    }

    /**
     * {@inheritDoc}
     */
    public Object[] getParameters() {
        return ic.getParameters();
    }

    /**
     * {@inheritDoc}
     */
    public String getMethodName() {
        return ic.getMethod().getName();
    }
}
