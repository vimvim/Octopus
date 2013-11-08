package ui.services;

import com.google.common.eventbus.EventBus;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import repositories.UsersRepo;
import services.UserService;
import ui.events.LoggedInEvent;

/**
 */

@Service
@Scope("session")
public class SessionService {

    private UserService userService;

    private UsersRepo usersRepo;

    private EventBus eventBus;

    private User user;

    @Autowired
    public SessionService(UserService userService, UsersRepo usersRepo, EventBus eventBus) {
        this.userService = userService;
        this.eventBus = eventBus;
        this.usersRepo = usersRepo;
    }

    public boolean auth(String username, String password) {

        // user = usersDao.getByUsername(username);

        eventBus.post(new LoggedInEvent(null));

        return true;
    }


}
