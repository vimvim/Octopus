package ui;

import com.google.common.eventbus.EventBus;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 *
 */
@Configuration
public class UIConfiguration {

    private static HelpManager helpManager;

    @Bean
    public HelpManager helpManager() throws Exception {
        return helpManager;
    }

    public static void setHelpManager(HelpManager helpManager) {
        UIConfiguration.helpManager = helpManager;
    }
}
