package ui.services;

import com.google.common.eventbus.EventBus;
import models.User;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import repositories.UsersDao;
import services.UsersService;

/**
 */

@Service
@Scope("session")
public class SessionService {

    private UsersService usersService;

    private UsersDao usersDao;

    private EventBus eventBus;

    private User user;

    @Autowired
    public SessionService(UsersService usersService, UsersDao usersDao, EventBus eventBus) {
        this.usersService = usersService;
        this.eventBus = eventBus;
        this.usersDao = usersDao;
    }

    public boolean auth(String username, String password) {

        // user = usersDao.getByUsername(username);

        return true;
    }


}
