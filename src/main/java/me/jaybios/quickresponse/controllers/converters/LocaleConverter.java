package me.jaybios.quickresponse.controllers.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.Locale;

@FacesConverter(value = "localeConverter")
public class LocaleConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return new Locale(s.split("_")[0], s.split("_")[1]);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        Locale locale = (Locale)o;
        return locale.toString();
    }
}
