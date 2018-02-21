package me.jaybios.quickresponse.producers;

import me.jaybios.quickresponse.daos.ActivationTokenDAO;
import me.jaybios.quickresponse.daos.CodeDAO;
import me.jaybios.quickresponse.daos.PersistentDAO;
import me.jaybios.quickresponse.models.ActivationToken;
import me.jaybios.quickresponse.models.Code;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import java.util.UUID;

public class DAOProducer {
    @Produces @CodeDAO
    public PersistentDAO<Code, UUID> createCodeDAO(EntityManager entityManager) {
        return new PersistentDAO<>(Code.class, entityManager);
    }

    @Produces @ActivationTokenDAO
    public PersistentDAO<ActivationToken, Integer> createActivationTokenDAO(EntityManager entityManager) {
        return new PersistentDAO<>(ActivationToken.class, entityManager);
    }
}
