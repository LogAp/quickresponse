package me.jaybios.quickresponse.controllers;

import me.jaybios.quickresponse.models.Code;
import me.jaybios.quickresponse.producers.HttpParam;
import me.jaybios.quickresponse.services.CodeService;
import me.jaybios.quickresponse.services.ResourceService;
import net.glxn.qrgen.javase.QRCode;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Named
@RequestScoped
public class ResultController {

    @Inject
    @CodeService
    private ResourceService<Code, UUID> codeService;

    private Code code;

    @Inject @HttpParam
    private UUID uuid;

    public String findCode() {
        try {
            code = codeService.findById(uuid);
        } catch(IllegalArgumentException e) {
            return "pretty:view-home";
        }

        if (code == null) {
            return "pretty:view-home";
        }

        return null;
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
