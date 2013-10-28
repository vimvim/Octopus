package ui;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;


/**
 *
 */
@Theme("dashboard")
@Title("Octopus")
public class BackendUI extends UI  {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(new Button("Click me!", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Notification.show("Hello World!");
            }
        }));
    }

}
