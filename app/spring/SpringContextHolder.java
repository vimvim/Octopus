package spring;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 */
public class SpringContextHolder {

    private static AbstractApplicationContext context;

    private static boolean isInit = false;

    public static void init() {

        if (!isInit) {

            try {

                context = new ClassPathXmlApplicationContext("applicationContext.xml");
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
            context = null;

            isInit = false;
        }
    }

    public static AbstractApplicationContext getContext() {
        return context;
    }

}
