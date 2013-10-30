package ui;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    public void setHelpManager(HelpManager helpManager) {
        UIConfiguration.helpManager = helpManager;
    }
}
