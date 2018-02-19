package me.jaybios.quickresponse.controllers;

import me.jaybios.quickresponse.models.User;
import me.jaybios.quickresponse.services.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class AuthenticationController {
    @Inject
    private SessionController session;

    @Inject
    private UserService userService;

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        User user = userService.findByUsername(username);
        if (user == null) {
            return null;
        }
        if (user.getHasher().verify(password, user.getPassword())) {
            session.create(user);
            return "pretty:view-home";
        }
        return null;
    }

    public String logout() {
        session.destroy();
        return "pretty";
    }
}
