package me.jaybios.quickresponse.producers;

import me.jaybios.quickresponse.daos.ActivationTokenDAO;
import me.jaybios.quickresponse.daos.PersistentDAO;
import me.jaybios.quickresponse.models.ActivationToken;

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

    @Produces
    @TokenParam
    ActivationToken getActivationToken(InjectionPoint injectionPoint, @ActivationTokenDAO PersistentDAO<ActivationToken, Integer> dao) {
        String paramName = injectionPoint.getAnnotated().getAnnotation(TokenParam.class).value();
        if ("".equals(paramName)) paramName = injectionPoint.getMember().getName();
        UUID token;
        try {
            token = UUID.fromString(facesContext.getExternalContext().getRequestParameterMap().get(paramName));
        } catch (IllegalArgumentException e) {
            return null;
        }
        return dao.findByProperty("token", token);
    }
}
