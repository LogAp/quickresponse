package me.jaybios.quickresponse.controllers.validators;

import me.jaybios.quickresponse.producers.DialogBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import java.util.ResourceBundle;

@FacesValidator(value = "matchPasswordValidator", managed = true)
public class MatchPasswordValidator implements Validator {

    @Inject
    @DialogBundle
    private ResourceBundle dialog;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String password = o.toString();
        UIInput matchInput = (UIInput) uiComponent.getAttributes().get("match");
        String matchPassword = matchInput.getSubmittedValue().toString();

        if (password == null || password.isEmpty() || matchPassword == null || matchPassword.isEmpty()) {
            return;
        }

        if (!password.equals(matchPassword)) {
            matchInput.setValid(false);
            String message = dialog.getString("messages.register.password.match");
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
            throw new ValidatorException(facesMessage);
        }
    }
}
