package me.jaybios.quickresponse.controllers;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Locale;

@Named
@SessionScoped
public class I18nController implements Serializable {
    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void localeChanged(ValueChangeEvent e) {
        locale = (Locale) e.getNewValue();
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
}
