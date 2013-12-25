package ui.events;

import octorise.repo.octopus.models.User;

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
