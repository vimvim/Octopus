package ui;

import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 *
 */
@org.springframework.stereotype.Component
public class SideBar {

    VerticalLayout layout;

    @Autowired
    public SideBar(Menu menu) {

        layout = new VerticalLayout();

        layout.addStyleName("sidebar");
        layout.setWidth(null);
        layout.setHeight("100%");

        // Branding element
        layout.addComponent(new CssLayout() {
            {
                addStyleName("branding");

                Label logo = new Label("<span>QuickTickets</span> Dashboard", ContentMode.HTML);
                logo.setSizeUndefined();

                addComponent(logo);

                // addComponent(new Image(null, new
                // ThemeResource(
                // "img/branding.png")));
            }
        });

        Component menuComponent = menu.getComponent();

        layout.addComponent(menuComponent);
        layout.setExpandRatio(menuComponent, 1);

        // User menu
        // TODO: Move to the another component
        layout.addComponent(new VerticalLayout() {
            {
                setSizeUndefined();
                addStyleName("user");

                Image profilePic = new Image(null, new ThemeResource("img/profile-pic.png"));
                profilePic.setWidth("34px");
                addComponent(profilePic);

                Label userName = new Label("John Doe");
                userName.setSizeUndefined();
                addComponent(userName);

                MenuBar.Command cmd = new MenuBar.Command() {
                    @Override
                    public void menuSelected(
                            MenuBar.MenuItem selectedItem) {
                        Notification
                                .show("Not implemented in this demo");
                    }
                };

                MenuBar settings = new MenuBar();

                MenuBar.MenuItem settingsMenu = settings.addItem("", null);
                settingsMenu.setStyleName("icon-cog");
                settingsMenu.addItem("Settings", cmd);
                settingsMenu.addItem("Preferences", cmd);
                settingsMenu.addSeparator();
                settingsMenu.addItem("My Account", cmd);

                addComponent(settings);

                Button exit = new NativeButton("Exit");
                exit.addStyleName("icon-cancel");
                exit.setDescription("Sign Out");

                addComponent(exit);

                exit.addClickListener(new Button.ClickListener() {

                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        // buildLoginView(true);
                    }
                });
            }
        });
    }

    public Component getComponent() {
        return layout;
    }

}
