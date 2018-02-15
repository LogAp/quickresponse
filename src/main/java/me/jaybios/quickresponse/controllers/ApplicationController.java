package me.jaybios.quickresponse.controllers;

import org.apache.commons.text.WordUtils;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.jar.Manifest;

@Named
@Startup
@Singleton
public class ApplicationController {
    private Manifest manifest = null;
    private Boolean debug;
    private String version;
    private String uri;
    private List<SelectItem> supportedLocales;

    @PostConstruct
    public void startup() {
        InputStream inputStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/META-INF/MANIFEST.MF");
        try {
            manifest = new Manifest(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        uri = System.getenv("APPLICATION_URI");
        if (uri == null)
            uri = "http://localhost:8081";

        String debugEnv = System.getenv("DEBUG");
        debug = debugEnv != null && debugEnv.equals("true");

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

    public String getUri() {
        return uri;
    }

    public List<SelectItem> getSupportedLocales() {
        return supportedLocales;
    }

    private SelectItem convertLocaleToSelectItem(Locale locale) {
        String label = WordUtils.capitalize(locale.getDisplayName(locale));
        return new SelectItem(locale, label);
    }
}
