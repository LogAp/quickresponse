package me.jaybios.quickresponse.controllers.validators;

import me.jaybios.quickresponse.models.User;
import me.jaybios.quickresponse.producers.DialogBundle;
import me.jaybios.quickresponse.services.UserService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import java.util.ResourceBundle;

@FacesValidator(value = "availabilityValidator", managed = true)
public class AvailabilityValidator implements Validator {

    @Inject
    @DialogBundle
    private ResourceBundle dialog;

    @Inject
    private UserService service;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        User user;
        String message;
        Object inputType = uiComponent.getAttributes().get("type");
        if (inputType != null && inputType.toString().equals("email")) {
            user = service.findByEmail(o.toString());
            message = dialog.getString("messages.register.email.taken");
        } else {
            user = service.findByUsername(o.toString());
            message = dialog.getString("messages.register.username.taken");
        }

        if (user != null) {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
            throw new ValidatorException(facesMessage);
        }
    }
}
