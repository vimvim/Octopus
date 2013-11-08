package ui;

import com.google.common.eventbus.EventBus;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.ComponentContainer;
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

    @Bean(name="ui")
    public UI ui() throws Exception {
        return UI.getCurrent();
    }

    /*
    @Bean(name="uiContentContainer")
    public ComponentContainer componentContainer() throws Exception {

        BackendUI ui = (BackendUI) UI.getCurrent();

        return UI.getCurrent();
    }
    */
}
