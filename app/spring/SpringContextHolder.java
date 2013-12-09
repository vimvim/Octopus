package spring;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 */
public class SpringContextHolder {

    private static final AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

    private static boolean isInit = false;

    public static void init() {

        if (!isInit) {

            try {
                context.start();
                isInit = true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void shutdown() {

        if (isInit) {

            context.stop();
            isInit = false;
        }
    }

    public static AbstractApplicationContext getContext() {
        return context;
    }

}
