package me.jaybios.quickresponse.controllers;

import me.jaybios.quickresponse.controllers.events.ActivateAccountEvent;
import me.jaybios.quickresponse.controllers.events.ResendActivationEmailEvent;
import me.jaybios.quickresponse.models.ActivationToken;
import me.jaybios.quickresponse.producers.TokenParam;
import me.jaybios.quickresponse.services.UserService;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class ActivateController {

    @Inject
    @TokenParam
    private ActivationToken token;

    @Inject
    @ActivateAccountEvent
    private Event<ActivationToken> activateAccountEvent;

    @Inject
    @ResendActivationEmailEvent
    private Event<ActivationToken> resendEmailEvent;

    @Inject
    private UserService userService;

    private Boolean invalid = false;
    private Boolean expired = false;
    private Boolean active = false;

    public Boolean getValid() {
        return !invalid && !expired && !active;
    }

    public Boolean getInvalid() {
        return invalid;
    }

    public Boolean getActive() {
        return active;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void checkToken() {
        if (token == null)
            invalid = true;
        else if (token.getUser().isActive())
            active = true;
        else if (token.hasExpired())
            expired = true;
        else
            activateAccountEvent.fire(token);
    }

    public void sendToken() {
        resendEmailEvent.fire(userService.generateActivationToken(token.getUser()));
    }
}
