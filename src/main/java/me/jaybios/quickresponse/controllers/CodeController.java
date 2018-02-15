package me.jaybios.quickresponse.controllers;

import me.jaybios.quickresponse.controllers.requests.CodeRequest;
import me.jaybios.quickresponse.controllers.requests.MailRequest;
import me.jaybios.quickresponse.models.Code;
import me.jaybios.quickresponse.models.SecureCode;
import me.jaybios.quickresponse.services.CodeService;
import me.jaybios.quickresponse.services.ResourceService;
import me.jaybios.quickresponse.services.SecureCodeService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@Named
@RequestScoped
public class CodeController {

    @Inject
    @CodeService
    private ResourceService<Code, UUID> codeService;

    @Inject
    @SecureCodeService
    private ResourceService<SecureCode, UUID> secureCodeService;

    private String uuid;

    @Inject
    private CodeRequest codeRequest;

    @Inject
    private MailRequest mailRequest;

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
        SecureCode code = new SecureCode();
        code.setUri(codeRequest.getUri());
        code.setPassword(codeRequest.getPassword());
        secureCodeService.store(code);
        uuid = code.getUuid().toString();
    }

    private void createUnsecure() {
        Code code = new Code();
        code.setUri(codeRequest.getUri());
        codeService.store(code);
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
