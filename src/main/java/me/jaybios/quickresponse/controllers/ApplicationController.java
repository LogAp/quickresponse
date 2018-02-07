package me.jaybios.quickresponse.controllers;

import org.apache.commons.text.WordUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.jar.Manifest;

@ManagedBean(eager = true)
@ApplicationScoped
public class ApplicationController {
    private Manifest manifest = null;
    private Boolean debug;
    private String version;
    private List<SelectItem> supportedLocales;

    @PostConstruct
    public void init() {
        InputStream inputStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/META-INF/MANIFEST.MF");
        try {
            manifest = new Manifest(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String debugEnv = System.getenv("DEBUG");
        debug =  debugEnv != null && debugEnv.equals("true");

        String stagingEnv = System.getenv("STAGING");
        Boolean staging = stagingEnv != null && stagingEnv.equals("true");

        String releaseVersion = manifest.getMainAttributes().getValue("Implementation-Version");
        String commitHash = manifest.getMainAttributes().getValue("Commit-Hash");
        version = staging ? commitHash : releaseVersion;

        supportedLocales = new ArrayList<>();
        FacesContext
                .getCurrentInstance()
                .getApplication()
                .getSupportedLocales()
                .forEachRemaining(locale -> supportedLocales.add(convertLocaleToSelectItem(locale)));
    }

    public boolean getDebug() {
        return debug;
    }

    public String getVersion() {
        return version;
    }

    public List<SelectItem> getSupportedLocales() {
        return supportedLocales;
    }

    private SelectItem convertLocaleToSelectItem(Locale locale) {
        String label = WordUtils.capitalize(locale.getDisplayName(locale));
        return new SelectItem(locale, label);
    }
}
