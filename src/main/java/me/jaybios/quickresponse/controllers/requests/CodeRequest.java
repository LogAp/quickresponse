package me.jaybios.quickresponse.controllers.requests;

import me.jaybios.quickresponse.models.Code;

public class CodeRequest {
    private String uri;
    private Boolean secure;
    private String password;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Boolean getSecure() {
        return secure;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static CodeRequest from(Code code) {
        CodeRequest request = new CodeRequest();
        request.setUri(code.getUri());
        request.setSecure(code.isSecure());
        return request;
    }
}
