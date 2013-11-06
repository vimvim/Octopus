package spring;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 */
public class SpringContextHolder {

    private static final AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

    public static AbstractApplicationContext getContext() {
        return context;
    }

}
