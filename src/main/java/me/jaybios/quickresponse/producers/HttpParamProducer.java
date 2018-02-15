package me.jaybios.quickresponse.producers;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.UUID;

public class HttpParamProducer {

    @Inject
    private FacesContext facesContext;

    @Produces
    @HttpParam
    String createHttpParam(InjectionPoint injectionPoint) {
        String name = injectionPoint.getAnnotated().getAnnotation(HttpParam.class).value();
        if ("".equals(name)) name = injectionPoint.getMember().getName();
        return facesContext.getExternalContext().getRequestParameterMap().get(name);
    }

    @Produces
    @HttpParam
    UUID createUUIDHttpParam(InjectionPoint injectionPoint) {
        String name = injectionPoint.getAnnotated().getAnnotation(HttpParam.class).value();
        if ("".equals(name)) name = injectionPoint.getMember().getName();
        return UUID.fromString(facesContext.getExternalContext().getRequestParameterMap().get(name));
    }
}
