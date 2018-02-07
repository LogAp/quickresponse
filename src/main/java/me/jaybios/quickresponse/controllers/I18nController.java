package me.jaybios.quickresponse.controllers;

import org.apache.commons.text.WordUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;
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

    public List<SelectItem> getSupportedLocales() {
        List<SelectItem> supportedLocales = new ArrayList<>();
        FacesContext
                .getCurrentInstance()
                .getApplication()
                .getSupportedLocales().forEachRemaining(locale -> supportedLocales.add(convertLocaleToSelectItem(locale)));
        return supportedLocales;
    }

    private SelectItem convertLocaleToSelectItem(Locale locale) {
        String label = WordUtils.capitalize(locale.getDisplayName(this.locale));
        return new SelectItem(locale, label);
    }
}
