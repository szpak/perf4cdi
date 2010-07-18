package net.sf.perf4cdi;

import org.jboss.interceptor.model.InterceptionModel;
import org.jboss.testharness.impl.packaging.Artifact;
import org.jboss.testharness.impl.packaging.jsr299.BeansXml;
import org.jboss.weld.bean.interceptor.InterceptorBindingsAdapter;
import org.jboss.weld.ejb.spi.InterceptorBindings;
import org.jboss.weld.serialization.spi.helpers.SerializableContextual;
import org.jboss.weld.test.AbstractWeldTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.Interceptor;
import java.util.Collection;

/**
 * Class testing Perf4jProfiledInterceptor in mocked CDI (Weld) context.
 *
 * TODO: MZA: Test class is not thread-safe (tests cannot be called from multiple threads)
 *
 * @author Marcin Zajączkowski, 2010-07-18
 */
@Artifact
@BeansXml("beans-simple-interceptor.xml")
public class Perf4jProfiledInterceptorContextTest extends AbstractWeldTest {

    private static final int ARGUMENT1 = 50;
    private static final int EXPECTED_NUMBER_OF_CONFIGURED_INTERCEPTORS = 1;

    @BeforeMethod
    public void resetInterceptorStatus() {
        SimpleTestPerf4jProfiledInterceptor.aroundInvokeCalled = false;
    }

    @Test
    public void shouldHavePerf4jInterceptorConfigured() {
        //given
        Bean<SimplePerf4jProfiledTestBean> bean = getBean(SimplePerf4jProfiledTestBean.class);
        //and good configuration in beans.xml
        //when
        //mechanisms in @XmlBeans and in superclass
        //then
        InterceptorBindings interceptorBindings = new InterceptorBindingsAdapter(getInterceptorModelForBean(bean));
        Collection<Interceptor<?>> allInterceptors = interceptorBindings.getAllInterceptors();
        Assert.assertEquals(allInterceptors.size(), EXPECTED_NUMBER_OF_CONFIGURED_INTERCEPTORS,
                "number of interceptors");
        Assert.assertEquals(getFirstFromCollection(allInterceptors).getBeanClass(),
                SimpleTestPerf4jProfiledInterceptor.class, "interceptor type");
    }

    private InterceptionModel<Class<?>, SerializableContextual<Interceptor<?>, ?>> getInterceptorModelForBean(Bean<SimplePerf4jProfiledTestBean> bean) {
        return getCurrentManager().getCdiInterceptorsRegistry().getInterceptionModel(bean.getBeanClass());
    }

    private Interceptor<?> getFirstFromCollection(Collection<Interceptor<?>> allInterceptors) {
        return allInterceptors.iterator().next();
    }

    @Test
    public void shouldBeCalledOnProfiledMethodExecution() throws Exception {
        //given
        SimplePerf4jProfiledTestBean profiledBean =
                getReference(SimplePerf4jProfiledTestBean.class);
        //when
        shouldCallProfiledMethodAndAssertInvocation(profiledBean, true);
    }

    @Test
    public void shouldBeOmittedOnNotProfiledMethodExecution() throws Exception {
        //given
        NoProfiledPerf4jProfiledTestBean profiledBean =
                getReference(NoProfiledPerf4jProfiledTestBean.class);
        //when and then
        shouldCallProfiledMethodAndAssertInvocation(profiledBean, false);
    }

    private void shouldCallProfiledMethodAndAssertInvocation(Perf4jProfiledTestBean profiledBean,
                                                             boolean shouldBeInvoked) throws Exception {
        //when
        long result = profiledBean.profiledMethod(ARGUMENT1);
        //then
        assertResult(result);
        assertInvocation(shouldBeInvoked);
    }

    private void assertInvocation(boolean shouldBeInvoked) {
        Assert.assertEquals(
                SimpleTestPerf4jProfiledInterceptor.aroundInvokeCalled, shouldBeInvoked, "aroundInvokeCalled");
    }

    private void assertResult(long result) {
        Assert.assertEquals(result, ARGUMENT1, "result");
    }

}
