package me.jaybios.quickresponse.controllers;

import me.jaybios.quickresponse.controllers.requests.CodeRequest;
import me.jaybios.quickresponse.controllers.requests.MailRequest;
import me.jaybios.quickresponse.models.AuthenticatedUser;
import me.jaybios.quickresponse.models.Code;
import me.jaybios.quickresponse.models.SecureCode;
import me.jaybios.quickresponse.models.User;
import me.jaybios.quickresponse.services.CodeService;
import me.jaybios.quickresponse.services.ResourceService;

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
    @AuthenticatedUser
    private User user;

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

    public String store() {
        Code code;
        code = codeRequest.getSecure() ? createSecure() : createUnsecure();
        code.setUri(codeRequest.getUri());
        code.setUser(user);
        codeService.store(code);
        uuid = code.getUuid().toString();
        return "pretty:view-result";
    }

    public String storeMail() {
        codeRequest.setUri(mailRequest.getUri());
        return store();
    }

    private Code createSecure() {
        SecureCode code = new SecureCode();
        code.setPassword(codeRequest.getPassword());
        return code;
    }

    private Code createUnsecure() {
        return new Code();
    }
}
