package me.jaybios.quickresponse.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class JPAUtility {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default", overrideConfiguration());

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

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static void close() {
        entityManagerFactory.close();
    }
}
