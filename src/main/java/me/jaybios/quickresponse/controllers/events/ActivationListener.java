package me.jaybios.quickresponse.controllers.events;

import me.jaybios.quickresponse.models.ActivationEmail;
import me.jaybios.quickresponse.models.ActivationToken;
import me.jaybios.quickresponse.services.UserService;

import javax.ejb.Asynchronous;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ActivationListener {

    @Inject
    private UserService userService;

    @Inject
    private ActivationEmail email;

    @Asynchronous
    public void activate(@Observes @ActivateAccountEvent ActivationToken token) {
        token.getUser().setActive(true);
        userService.update(token.getUser());
    }

    @Asynchronous
    public void sendActivationEmail(@Observes @SendActivationEmailEvent ActivationToken activationToken) {
        mail(activationToken, false);
    }

    @Asynchronous
    public void reSendActivationEmail(@Observes @ResendActivationEmailEvent ActivationToken activationToken) {
        mail(activationToken, true);
    }

    private void mail(ActivationToken token, Boolean re) {
        email.setActivationToken(token);
        email.setResend(re);
        System.out.println(email);
    }
}
