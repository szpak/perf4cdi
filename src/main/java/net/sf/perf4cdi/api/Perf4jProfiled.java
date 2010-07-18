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
package net.sf.perf4cdi.api;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker annotation for methods profiled with Perf4j.
 *
 * Methods to be profiled should be marked with that annotation.
 * <p/>
 * Due to CDI architecture in addition to original Profiled annotation from Perf4j library (which for example allows
 * to configure logging format) profiled method has to marked using that annotation as well.
 * Profiled annotation can be omitted (logging with default values takes place then).
 *
 * ElementType.TYPE was forced by requirements for CDI interceptors definition. Perf4jProfiled annotation can be
 * only used with mothods and constructors
 *
 * @author Marcin Zajączkowski, 2010-05-15
 *
 * @see net.sf.perf4cdi.Perf4jProfiledInterceptor
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@InterceptorBinding
public @interface Perf4jProfiled {
}
