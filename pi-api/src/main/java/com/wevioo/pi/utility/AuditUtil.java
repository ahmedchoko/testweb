package com.wevioo.pi.utility;


import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Utility class for handling audit-related functionalities within the application.
 * Provides methods to interact with the application context and retrieve beans.
 */
@Component
public class AuditUtil  implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext (ApplicationContext applicationContext) {

        setContext (applicationContext);

    }
    /**
     * Retrieves a bean of the specified class type from the application context.
     *
     * @param beanClass The class type of the bean to retrieve.
     * @param <T>       The type of the bean.
     * @return          The bean instance.
     */
    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    /**
     * Sets the application context.
     *
     * @param context The application context to set.
     */
    static void setContext (ApplicationContext context) {

        AuditUtil.context = context;

    }

}
