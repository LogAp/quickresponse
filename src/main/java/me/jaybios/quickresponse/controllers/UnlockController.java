package me.jaybios.quickresponse.controllers;

import me.jaybios.quickresponse.models.SecureCode;
import me.jaybios.quickresponse.producers.HttpParam;
import me.jaybios.quickresponse.services.ResourceService;
import me.jaybios.quickresponse.services.SecureCodeService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.UUID;

@Named
@RequestScoped
public class UnlockController {

    @Inject
    @SecureCodeService
    private ResourceService<SecureCode, UUID> secureCodeService;

    @Inject
    private SecureCode code;

    @Inject
    @HttpParam
    private UUID id;

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String findCode() {
        try {
            code = secureCodeService.findById(id);
        } catch (IllegalArgumentException | NullPointerException e) {
            return "pretty:view-home";
        }
        return code == null ? "pretty:view-home" : null;
    }

    public Boolean getInvalid() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        return (Boolean) externalContext.getFlash().getOrDefault("invalid", false);
    }

    public String unlock() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        if (code.getHasher().verify(password, code.getPassword())) {
            externalContext.redirect(code.getUri());
            return null;
        }
        externalContext.getFlash().put("invalid", true);
        externalContext.getFlash().setKeepMessages(true);
        return "pretty";
    }
}
