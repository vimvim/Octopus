package ui;

import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import ui.services.SessionService;


/**
 *
 */
@org.springframework.stereotype.Component
@Scope("session")
public class LoginView {

    private SessionService sessionService;

    private EventBus eventBus;

    private VerticalLayout layout;

    private CssLayout loginPanel;

    private TextField username;

    private PasswordField password;

    private Button signin;

    @Autowired
    public LoginView(SessionService sessionService) {

        this.sessionService = sessionService;

        layout = new VerticalLayout();
        layout.setSizeFull();
        layout.addStyleName("login-layout");

        loginPanel = new CssLayout();
        loginPanel.addStyleName("login-panel");

        HorizontalLayout labels = new HorizontalLayout();
        labels.setWidth("100%");
        labels.setMargin(true);
        labels.addStyleName("labels");
        loginPanel.addComponent(labels);

        Label welcome = new Label("Welcome");
        welcome.setSizeUndefined();
        welcome.addStyleName("h4");
        labels.addComponent(welcome);
        labels.setComponentAlignment(welcome, Alignment.MIDDLE_LEFT);

        Label title = new Label("Octopus Dashboard");
        title.setSizeUndefined();
        title.addStyleName("h2");
        title.addStyleName("light");
        labels.addComponent(title);
        labels.setComponentAlignment(title, Alignment.MIDDLE_RIGHT);

        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.setMargin(true);
        fields.addStyleName("fields");

        username = new TextField("Username");
        username.focus();
        fields.addComponent(username);

        password = new PasswordField("Password");
        fields.addComponent(password);

        signin = new Button("Sign In");
        signin.addStyleName("default");
        fields.addComponent(signin);
        fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

        final ShortcutListener enter = new ShortcutListener("Sign In",
                ShortcutAction.KeyCode.ENTER, null) {

            @Override
            public void handleAction(Object sender, Object target) {
                signin.click();
            }
        };

        signin.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {

                if (username.getValue() == null || username.getValue().equals("") || password.getValue() == null
                        || password.getValue().equals("")) {

                    onInvalidLogin("Please specify username and password");

                } else {

                    onLogin(username.getValue(), password.getValue());
                }
            }
        });

        signin.addShortcutListener(enter);

        loginPanel.addComponent(fields);

        layout.addComponent(loginPanel);
        layout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
    }

    public Component getComponent() {
        return layout;
    }

    private void onLogin(String username, String password) {

        if (!sessionService.auth(username, password)) {

            onInvalidLogin("Invalid username or password");
        }
    }

    private void onInvalidLogin(String message) {

        if (loginPanel.getComponentCount() > 2) {
            // Remove the previous error message
            loginPanel.removeComponent(loginPanel.getComponent(2));
        }

        // Add new error message
        Label error = new Label(message, ContentMode.HTML);

        error.addStyleName("error");
        error.setSizeUndefined();
        error.addStyleName("light");
        // Add animation
        error.addStyleName("v-animate-reveal");

        loginPanel.addComponent(error);
        username.focus();

    }
}
