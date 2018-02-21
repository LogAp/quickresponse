package me.jaybios.quickresponse.controllers;

import me.jaybios.quickresponse.controllers.requests.CodeRequest;
import me.jaybios.quickresponse.controllers.requests.MailRequest;
import me.jaybios.quickresponse.models.AuthenticatedUser;
import me.jaybios.quickresponse.models.Code;
import me.jaybios.quickresponse.models.User;
import me.jaybios.quickresponse.producers.HttpParam;
import me.jaybios.quickresponse.services.CodeService;
import me.jaybios.quickresponse.services.ResourceService;
import me.jaybios.quickresponse.services.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.UUID;

@Named
@RequestScoped
public class CodeController {

    @Inject
    @CodeService
    private ResourceService<Code, UUID> codeService;

    @Inject
    private UserService userService;

    @Inject
    @AuthenticatedUser
    private User user;

    @Inject @HttpParam
    private String uuid;

    private String qrcode;

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

    public String getQrcode() {
        return qrcode;
    }

    public List<Code> getUserCodes() {
        return userService.getCodes(user);
    }

    public String editCode(Code code) {
        uuid = code.getUuid().toString();
        return "pretty:view-code-edit";
    }

    public void removeCode(Code code) {
        codeService.deleteById(code.getUuid());
    }

    public String findUserCode() {
        Code code;
        try {
            code = codeService.findById(UUID.fromString(uuid));
            if (!code.getUser().getUuid().equals(user.getUuid())) {
                return "pretty:view-home";
            }
        } catch(IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
            return "pretty:view-home";
        }

        qrcode = code.generateImage();
        codeRequest = CodeRequest.from(code);

        return null;
    }

    public void update() {
        Code code = codeService.findById(UUID.fromString(uuid));
        code.setUri(codeRequest.getUri());
        code.setSecure(codeRequest.getSecure());
        code.setPassword(codeRequest.getPassword());
        codeService.update(code);
        codeRequest.setPassword(null);
        qrcode = code.generateImage();
    }

    public String store() {
        Code code = new Code();
        code.setUri(codeRequest.getUri());
        code.setSecure(codeRequest.getSecure());
        code.setUser(user);
        code.setPassword(codeRequest.getPassword());
        codeService.store(code);
        uuid = code.getUuid().toString();
        return "pretty:view-result";
    }

    public String storeMail() {
        codeRequest.setUri(mailRequest.getUri());
        return store();
    }
}
