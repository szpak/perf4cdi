Perf4CDI - Perf4j integration for CDI (JSR-299)
Copyright (C) 2010 Marcin Zajaczkowski
Email: mszpak ATT wp DOTT pl
Homepage: http://perf4cdi.sourceforge.net/

Perf4CDI is available under the terms of GNU Lesser General Public License version 2.1 or (at your opinion) any later version.


Perf4CDI is an add-on package for implementations of Contexts and Dependency Injection for the Java EE platform (CDI - JSR-299) providing convenient integration with Perf4j - performance monitoring library for Java applications.
Perf4CDI was confirmed to work with OpenWebBeans 1.0.0-M4.
Due to a problem with Weld 1.0.1 final (see https://jira.jboss.org/browse/WELD-507) Perf4CDI should be able to work with upcomming Weld 1.0.2.CR1.


Those things are already available and ready to use:
 - a convenient integration with Perf4j - by annotating methods
 - a simple stop watch mechanism for succinct timing statements (Perf4j's feature)
 - a command line tool for parsing log files that generates aggregated statistics and performance graphs (Perf4j's feature)
 - an ability to expose performance statistics as JMX attributes, and to send notifications when statistics exceed specified thresholds (Perf4j's feature)
 - a servlet for exposing performance graphs in a web application (Perf4j's feature)

Things planned to implement in the nearest future:
 - CDI factory to produce loggers
 - ability to work as CDI extension (needed?)
 - move project's SCM to SourceForge - DONE
 - add unit tests - DONE
 - put project's jars into public Maven repository - DONE
 - further suggestions are welcome

Note: Perf4CDI in current version does its job, but there are a few things to improve, so it is not possible to quarantine a backward compatibility of the future versions. Therefore it should be used in production environmental carefully.


The best way to get support is the forum:
http://sourceforge.net/projects/seam-perf4j/forums/forum/1092214

Bugs can be reported using project's issue tracker:
http://sourceforge.net/tracker/?group_id=304978&atid=1285292


===================


Getting started guide

Maven configuration

For Maven projects it's required to add perf4cdi dependency to application's pom.xml:
<dependency>
    <groupId>net.sf.perf4cdi</groupId>
    <artifactId>perf4cdi</artifactId>
    <version>0.4.0</version>
</dependency>

Because Perf4CDI was created for CDI based applications it is assumed that cdi-api (tested with 1.0-SP1) is provided by runtime environment.
Perf4CDI internally uses slf4j API for logging. slf4j-api (tested with 1.6.0) should be provided as well.


CDI configuration

It is required to register additional interceptor 'net.sf.perf4cdi.Perf4jProfiledInterceptor' in beans.xml in CDI application to profile:

<beans>
    (...)
    <interceptors>
        <class>net.sf.perf4cdi.Perf4jProfiledInterceptor</class>
    </interceptors>
    (...)
</beans>


Application configuration

The last step is to annotate profiled method:
@Perf4jProfiled
@Profiled(tag = "profiledMethodTag")
public void profiledMethod() {...}

Note: Due to CDI architecture it's required to use marker @Perf4jProfiled annotation to associate interceptor with a method in addition to original @Profiled annotation from Perf4j. @Profiled annotation can be omitted (logging with default values takes place then).

After execution of profiled method(s) you should be able to see message similar to following in the application log:
INFO  [org.perf4j.TimingLogger] start[1267557706784] time[1075] tag[profiledMethodTag]

Happy profiling with CDI and Perf4j!


===================

Developer's guide - only for contributors

Perf4CDI itself depends only on packages which are available in Maven Central Repository. For test purposes however it uses mechanisms from weld-core-test which depends on JARs available only in JBoss' Maven Repository. Because of that to build Perf4CDI from source (only contributors would need to do that - for normal usage in end applications Maven Central Repository should be enough) it's required to add repository in settings.xml located in user's home directory:

<repositories>
    (... - some other repositories)
    <repository>
        <id>jboss</id>
        <url>http://repository.jboss.com/maven2</url>
        <releases>
            <enabled>true<enabled>
        </releases>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
    </repository>
</repositories>

