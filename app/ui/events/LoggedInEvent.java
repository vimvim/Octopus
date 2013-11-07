package ui.events;

import models.User;

/**
 *
 */
public class LoggedInEvent {

    User user;

    public LoggedInEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
