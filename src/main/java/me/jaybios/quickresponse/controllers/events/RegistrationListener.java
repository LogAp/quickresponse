package me.jaybios.quickresponse.controllers.events;

import me.jaybios.quickresponse.models.ActivationToken;
import me.jaybios.quickresponse.models.User;
import me.jaybios.quickresponse.services.UserService;

import javax.ejb.Asynchronous;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class RegistrationListener {

    @Inject
    private UserService userService;

    @Inject
    @SendActivationEmailEvent
    private Event<ActivationToken> sendActivationEmailEvent;

    @Asynchronous
    public void register(@Observes User user) {
        userService.store(user);
        ActivationToken token = userService.generateActivationToken(user);
        sendActivationEmailEvent.fire(token);
    }
}
