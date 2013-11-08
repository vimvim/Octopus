package ui;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import spring.SpringContextHolder;
import ui.events.LoggedInEvent;
import ui.events.NavigateViewEvent;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 *
 */
@Theme("dashboard")
@Title("Octopus")
public class BackendUI extends UI  {

    Map<String,Class> views;

    Map<String,Object> componentsStorage = new HashMap();

    EventBus eventBus;

    Navigator navigator;

    HelpManager helpManager;

    CssLayout root = new CssLayout();

    LoginView loginView;

    HorizontalLayout mainLayout;

    CssLayout content;

    public Map<String,Object> getComponentsStorage() {
        return componentsStorage;
    }

    public ComponentContainer getContentContainer() {
        return content;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        eventBus = (EventBus) SpringContextHolder.getContext().getBean("uiEventBus");
        eventBus.register(this);

        views = (Map<String,Class>) SpringContextHolder.getContext().getBean("uiViews");

        helpManager = SpringContextHolder.getContext().getBean(HelpManager.class);
        loginView = SpringContextHolder.getContext().getBean(LoginView.class);

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

    @Subscribe
    public void onLoggedInEvent(LoggedInEvent event) {

        removeStyleName("login");
        root.removeComponent(loginView.getComponent());

        SideBar sideBar = SpringContextHolder.getContext().getBean(SideBar.class);

        mainLayout = new HorizontalLayout();

        mainLayout.setSizeFull();
        mainLayout.addStyleName("main-view");
        mainLayout.addComponent(sideBar.getComponent());

        content = new CssLayout();

        mainLayout.addComponent(content);
        content.setSizeFull();
        content.addStyleName("view-content");
        mainLayout.setExpandRatio(content, 1);

        navigator = new Navigator(this, content);
        for (String route : views.keySet()) {
            navigator.addView(route, views.get(route));
        }

        root.addComponent(mainLayout);
    }

    @Subscribe
    public void onNavigateEvent(NavigateViewEvent navigateEvent) {
        navigator.navigateTo("/"+navigateEvent.getRoute());
    }

}
