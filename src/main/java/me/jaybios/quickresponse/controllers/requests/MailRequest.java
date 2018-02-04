package me.jaybios.quickresponse.controllers.requests;

public class MailRequest {
    private String address;
    private String subject;
    private String body;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUri() {
        return String.format("mailto:%s?subject=%s&body=%s", address, subject, body);
    }
}
