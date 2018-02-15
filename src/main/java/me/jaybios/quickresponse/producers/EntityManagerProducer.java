package me.jaybios.quickresponse.producers;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class EntityManagerProducer {
    private javax.persistence.EntityManagerFactory entityManagerFactory;

    private static Map<String, Object> overrideConfiguration() {
        Map<String, Object> overrideData = new HashMap<>();
        try {
            URI databaseURI = new URI(System.getenv("DATABASE_URL"));

            String username = "postgres";
            String password = "";

            if (databaseURI.getUserInfo() != null) {
                username = databaseURI.getUserInfo().split(":")[0];
                password = databaseURI.getUserInfo().split(":")[1];
            }

            String databaseURL = String.format("jdbc:postgresql://%s:%s%s",
                    databaseURI.getHost(), databaseURI.getPort(), databaseURI.getPath());

            if (databaseURI.getPath().contains("_test")) {
                overrideData.put("hibernate.hbm2ddl.auto", "create");
            }

            overrideData.put("javax.persistence.jdbc.url", databaseURL);
            overrideData.put("javax.persistence.jdbc.user", username);
            overrideData.put("javax.persistence.jdbc.password", password);

            return overrideData;
        } catch (URISyntaxException | NullPointerException e) {
            return overrideData;
        }
    }

    @PostConstruct
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default", overrideConfiguration());
    }

    @Produces
    @RequestScoped
    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public void closeEntityManager(@Disposes EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }

    @PreDestroy
    private void close() {
        if (entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
