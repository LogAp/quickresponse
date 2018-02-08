package me.jaybios.quickresponse.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import java.util.Locale;

@ManagedBean
@SessionScoped
public class I18nController {
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
