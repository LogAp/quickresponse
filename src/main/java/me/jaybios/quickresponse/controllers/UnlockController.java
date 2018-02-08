package me.jaybios.quickresponse.controllers;

import me.jaybios.quickresponse.daos.SecureCodeDAO;
import me.jaybios.quickresponse.models.SecureCode;
import me.jaybios.quickresponse.services.ResourceService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.UUID;

@ManagedBean
@RequestScoped
public class UnlockController {
    private SecureCode code;

    @ManagedProperty(value = "#{param.id}")
    private String uuid;
    private String password;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String findCode() {
        ResourceService<SecureCode, UUID> service = new ResourceService<>(new SecureCodeDAO());
        try {
            code = service.findById(UUID.fromString(uuid));
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
