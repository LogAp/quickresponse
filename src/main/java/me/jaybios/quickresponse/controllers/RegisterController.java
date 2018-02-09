package me.jaybios.quickresponse.controllers;

import me.jaybios.quickresponse.services.UserService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class RegisterController {
    private String username;
    private boolean usernameAvailable = true;

    private UserService userService = new UserService();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isUsernameAvailable() {
        return usernameAvailable;
    }

    public void checkUsernameAvailability() {
        usernameAvailable = userService.findByUsername(username) == null;
    }
}
