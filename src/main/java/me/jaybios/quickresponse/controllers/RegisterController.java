package me.jaybios.quickresponse.controllers;

import me.jaybios.quickresponse.models.User;

import javax.enterprise.event.Event;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class RegisterController implements Serializable {

    @Inject
    private Event<User> userEvent;

    @Inject
    private User user;

    private String confirmPassword;

    private UIInput emailInput;
    private UIInput userInput;
    private UIInput confirmPasswordInput;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public UIInput getEmailInput() {
        return emailInput;
    }

    public void setEmailInput(UIInput emailInput) {
        this.emailInput = emailInput;
    }

    public UIInput getUserInput() {
        return userInput;
    }

    public void setUserInput(UIInput userInput) {
        this.userInput = userInput;
    }

    public UIInput getConfirmPasswordInput() {
        return confirmPasswordInput;
    }

    public void setConfirmPasswordInput(UIInput confirmPasswordInput) {
        this.confirmPasswordInput = confirmPasswordInput;
    }

    public boolean isWrong() {
        return !FacesContext.getCurrentInstance().getMessageList().isEmpty();
    }

    public String register() {
        userEvent.fire(user);
        return "pretty:view-register-activate";
    }
}
