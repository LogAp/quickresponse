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
        ResourceService<SecureCode, UUID> service = new ResourceService<SecureCode, UUID>(new SecureCodeDAO());
        try {
            code = service.findById(UUID.fromString(uuid));
        } catch(IllegalArgumentException e) {
            return "pretty:view-home";
        }

        if (code == null) {
            return "pretty:view-home";
        }

        return null;
    }

    public void unlock() throws IOException {
        if (code.checkPassword(password)) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(code.getUri());
        }
    }
}
