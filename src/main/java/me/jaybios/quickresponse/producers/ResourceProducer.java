package me.jaybios.quickresponse.producers;

import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.ResourceBundle;

public class ResourceProducer {

    @Inject
    private FacesContext facesContext;

    @Produces @DialogBundle
    public ResourceBundle getDialogBundle() {
        return facesContext.getApplication().getResourceBundle(facesContext, "dialog");
    }
}
