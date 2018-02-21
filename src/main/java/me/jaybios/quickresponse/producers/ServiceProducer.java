package me.jaybios.quickresponse.producers;

import me.jaybios.quickresponse.daos.CodeDAO;
import me.jaybios.quickresponse.daos.PersistentDAO;
import me.jaybios.quickresponse.models.Code;
import me.jaybios.quickresponse.services.CodeService;
import me.jaybios.quickresponse.services.ResourceService;

import javax.enterprise.inject.Produces;
import java.util.UUID;

public class ServiceProducer {
    @Produces @CodeService
    public ResourceService<Code, UUID> createSecureCodeService(@CodeDAO PersistentDAO<Code, UUID> dao) {
        return new ResourceService<>(dao);
    }
}
