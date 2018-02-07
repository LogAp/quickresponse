package me.jaybios.quickresponse.controllers;

import me.jaybios.quickresponse.daos.CodeDAO;
import me.jaybios.quickresponse.models.Code;
import me.jaybios.quickresponse.services.ResourceService;
import net.glxn.qrgen.javase.QRCode;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@ManagedBean
@RequestScoped
public class ResultController {
    private Code code;

    @ManagedProperty(value = "#{param.uuid}")
    private String uuid;

    public String findCode() {
        ResourceService<Code, UUID> service = new ResourceService<>(new CodeDAO());
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getQrCode() {
        return code.generateImage();
    }

    public void download() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        QRCode qrCode = code.generateQR().withSize(500, 500);

        externalContext.responseReset();
        externalContext.setResponseContentType("image/png");
        externalContext.setResponseContentLength((int)qrCode.file().length());
        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"QRCode.png\"");
        OutputStream output = externalContext.getResponseOutputStream();

        qrCode.writeTo(output);

        facesContext.responseComplete();
    }
}
