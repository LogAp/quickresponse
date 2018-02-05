package me.jaybios.quickresponse.util;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.PrintWriter;
import java.io.StringWriter;

@ManagedBean
@RequestScoped
public class ErrorHandler {
    private Object getFromRequest(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(key);
    }

    public String getStatusCode() {
        Integer statusCode = (Integer)getFromRequest("javax.servlet.error.status_code");
        return String.valueOf(statusCode);
    }

    public String getMessage() {
        return (String)getFromRequest("javax.servlet.error.message");
    }

    public String getExceptionType() {
        return getFromRequest("javax.servlet.error.exception_type")
                .toString();
    }

    public String getException() {
        return getFromRequest("javax.servlet.error.exception")
                .toString();
    }

    public String getStackTrace() {
        Throwable exception = (Exception)getFromRequest("javax.servlet.error.exception");
        if (exception == null) {
            return "";
        }

        StringWriter stringWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(stringWriter, true));
        return stringWriter.toString();
    }

    public String getRequestURI() {
        return (String)getFromRequest("javax.servlet.error.request_uri");
    }

    public String getServletName(){
        return (String)getFromRequest("javax.servlet.error.servlet_name");
    }
}
