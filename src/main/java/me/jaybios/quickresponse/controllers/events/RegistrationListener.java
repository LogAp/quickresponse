package me.jaybios.quickresponse.controllers.events;

import me.jaybios.quickresponse.models.User;
import me.jaybios.quickresponse.services.UserService;

import javax.ejb.Asynchronous;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class RegistrationListener {

    @Inject
    private UserService userService;

    @Asynchronous
    public void register(@Observes User user) {
        userService.store(user);
        System.out.println(userService.generateActivationToken(user).getToken());
    }
}
