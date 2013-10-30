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

    CssLayout root = new CssLayout();

    CssLayout menu = new CssLayout();
    CssLayout content = new CssLayout();

    @Override
    protected void init(VaadinRequest vaadinRequest) {

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
    }

}
