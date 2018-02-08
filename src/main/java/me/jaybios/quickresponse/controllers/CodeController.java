package me.jaybios.quickresponse.controllers;

import me.jaybios.quickresponse.controllers.requests.CodeRequest;
import me.jaybios.quickresponse.controllers.requests.MailRequest;
import me.jaybios.quickresponse.daos.CodeDAO;
import me.jaybios.quickresponse.daos.SecureCodeDAO;
import me.jaybios.quickresponse.models.Code;
import me.jaybios.quickresponse.models.SecureCode;
import me.jaybios.quickresponse.services.ResourceService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.UUID;

@ManagedBean
@RequestScoped
public class CodeController {
    private String uuid;
    private CodeRequest codeRequest = new CodeRequest();
    private MailRequest mailRequest = new MailRequest();

    public CodeRequest getCodeRequest() {
        return codeRequest;
    }

    public void setCodeRequest(CodeRequest codeRequest) {
        this.codeRequest = codeRequest;
    }

    public MailRequest getMailRequest() {
        return mailRequest;
    }

    public void setMailRequest(MailRequest mailRequest) {
        this.mailRequest = mailRequest;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    private void createSecure() {
        ResourceService<SecureCode, UUID> service = new ResourceService<>(new SecureCodeDAO());
        SecureCode code = new SecureCode();
        code.setUri(codeRequest.getUri());
        code.setPassword(codeRequest.getPassword());
        service.store(code);
        uuid = code.getUuid().toString();
    }

    private void createUnsecure() {
        ResourceService<Code, UUID> service = new ResourceService<>(new CodeDAO());
        Code code = new Code();
        code.setUri(codeRequest.getUri());
        service.store(code);
        uuid = code.getUuid().toString();
    }

    public String store() {
        if (codeRequest.getSecure()) {
            createSecure();
        } else {
            createUnsecure();
        }
        return "pretty:view-result";
    }

    public String storeMail() {
        codeRequest.setUri(mailRequest.getUri());
        return store();
    }
}
