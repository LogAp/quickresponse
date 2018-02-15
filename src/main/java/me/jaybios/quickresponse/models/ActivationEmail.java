package me.jaybios.quickresponse.models;

import me.jaybios.quickresponse.controllers.ApplicationController;

import javax.inject.Inject;

public class ActivationEmail {
    private Boolean resend;
    private ActivationToken activationToken;

    @Inject
    private ApplicationController applicationController;

    public void setResend(Boolean resend) {
        this.resend = resend;
    }

    public void setActivationToken(ActivationToken activationToken) {
        this.activationToken = activationToken;
    }

    @Override
    public String toString() {
        return "Welcome " + activationToken.getUser().getUsername() + ", \n" +
               "Access the following link to activate your account:" + '\n' +
               applicationController.getUri() + "/activate/" + activationToken.toString();
    }
}
