package ui;

import com.google.common.eventbus.EventBus;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import spring.SpringContextHolder;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 *
 */
@Theme("dashboard")
@Title("Octopus")
public class BackendUI extends UI  {

    HelpManager helpManager;

    CssLayout root = new CssLayout();

    CssLayout menu = new CssLayout();
    CssLayout content = new CssLayout();

    LoginView loginView;

    EventBus eventBus;

    Map<String,Object> componentsStorage = new HashMap();

    public Map<String,Object> getComponentsStorage() {
        return componentsStorage;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        helpManager = new HelpManager(this);

        eventBus = (EventBus) SpringContextHolder.getContext().getBean("uiEventBus");
        loginView = SpringContextHolder.getContext().getBean(LoginView.class);

        /*
        setContent(new Button("Click me!", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Notification.show("Hello World!");
            }
        }));
        */

        //  getSession().setConverterFactory(new MyConverterFactory());

        // octopus.Global.ctx();

        setLocale(Locale.US);

        setContent(root);
        root.addStyleName("root");
        root.setSizeFull();

        // Unfortunate to use an actual widget here, but since CSS generated
        // elements can't be transitioned yet, we must
        Label bg = new Label();
        bg.setSizeUndefined();
        bg.addStyleName("login-bg");
        root.addComponent(bg);

        HelpOverlay w = helpManager
                .addOverlay(
                        "Welcome to the Dashboard Demo Application",
                        "<p>This application is not real, it only demonstrates an application built with the <a href=\"http://vaadin.com\">Vaadin framework</a>.</p><p>No username or password is required, just click the ‘Sign In’ button to continue. You can try out a random username and password, though.</p>",
                        "login");
        w.center();
        addWindow(w);

        addStyleName("login");

        // loginView = new LoginView(this.helpManager);
        // loginView.create();
        root.addComponent(loginView.getComponent());
    }

}
