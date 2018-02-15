package me.jaybios.quickresponse.controllers.validators;

import me.jaybios.quickresponse.producers.DialogBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FacesValidator(value = "emailValidator", managed = true)
public class EmailValidator implements Validator {

    @Inject
    @DialogBundle
    private ResourceBundle dialog;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\." +
            "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
            "(\\.[A-Za-z]{2,})$";

    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        Matcher matcher = pattern.matcher(o.toString());

        if (!matcher.matches()) {
            String message = dialog.getString("messages.register.email.pattern");
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
            throw new ValidatorException(facesMessage);
        }
    }
}
