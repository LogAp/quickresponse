package me.jaybios.quickresponse.services;

import me.jaybios.quickresponse.daos.UserDAO;
import me.jaybios.quickresponse.models.User;

import java.util.UUID;

public class UserService extends ResourceService<User, UUID> {
    public UserService() {
        super(new UserDAO());
    }

    public User findByUsername(String username) {
        dao.openSession();
        User user = dao.findByProperty("username", username);
        dao.closeSession();
        return user;
    }
}
