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

    public void unlock() throws IOException {
        ResourceService<SecureCode, UUID> service = new ResourceService<SecureCode, UUID>(new SecureCodeDAO());
        SecureCode code = service.findById(UUID.fromString(uuid));
        if (code.checkPassword(password)) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(code.getUri());
        }
    }
}
