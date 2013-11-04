package ui;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;

import java.util.Locale;


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

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        this.helpManager = new HelpManager(this);

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

        this.loginView = new LoginView(this.helpManager);

        HelpOverlay w = helpManager
                .addOverlay(
                        "Welcome to the Dashboard Demo Application",
                        "<p>This application is not real, it only demonstrates an application built with the <a href=\"http://vaadin.com\">Vaadin framework</a>.</p><p>No username or password is required, just click the ‘Sign In’ button to continue. You can try out a random username and password, though.</p>",
                        "login");
        w.center();
        addWindow(w);
    }

}
