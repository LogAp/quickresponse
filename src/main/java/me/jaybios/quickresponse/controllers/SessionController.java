package me.jaybios.quickresponse.controllers;

import me.jaybios.quickresponse.models.User;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class SessionController implements Serializable {
    private User user;

    public User getUser() {
        return user;
    }

    void create(User user) {
        this.user = user;
    }

    void destroy() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        this.user = null;
    }

    public boolean isUserAuthenticated() {
        return user != null;
    }
}
