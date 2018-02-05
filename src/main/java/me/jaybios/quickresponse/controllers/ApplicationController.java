package me.jaybios.quickresponse.controllers;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Manifest;

@ManagedBean(eager = true)
@ApplicationScoped
public class ApplicationController {
    private Manifest manifest = null;

    @PostConstruct
    public void init() {
        InputStream inputStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/META-INF/MANIFEST.MF");
        try {
            manifest = new Manifest(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean getDebug() {
        String debug = System.getenv("DEBUG");
        if (debug == null) {
            return false;
        }
        return debug.equals("true");
    }

    public String getVersion() {
        return manifest.getMainAttributes().getValue("Implementation-Version");
    }
}
