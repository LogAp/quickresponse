package me.jaybios.quickresponse.producers;

import me.jaybios.quickresponse.controllers.SessionController;
import me.jaybios.quickresponse.models.AuthenticatedUser;
import me.jaybios.quickresponse.models.User;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

public class UserProducer {

    @Inject
    private SessionController session;

    @Produces @AuthenticatedUser
    public User getAuthenticatedUser() {
        return session.getUser();
    }
}
