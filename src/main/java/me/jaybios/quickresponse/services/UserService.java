package me.jaybios.quickresponse.services;

import me.jaybios.quickresponse.daos.ActivationTokenDAO;
import me.jaybios.quickresponse.daos.PersistentDAO;
import me.jaybios.quickresponse.models.ActivationToken;
import me.jaybios.quickresponse.models.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.UUID;

public class UserService extends ResourceService<User, UUID> {

    @Inject
    public UserService(EntityManager entityManager) {
        super(new PersistentDAO<>(User.class, entityManager));
    }

    @Inject
    @ActivationTokenDAO
    private PersistentDAO<ActivationToken, Integer> activationTokenDAO;

    public User findByUsername(String username) {
        return dao.findByProperty("username", username);
    }

    public User findByEmail(String email) {
        return dao.findByProperty("email", email);
    }

    public ActivationToken generateActivationToken(User user) {
        ActivationToken activationToken = new ActivationToken();
        activationToken.setToken(UUID.randomUUID());
        activationToken.setUser(user);
        activationTokenDAO.openTransaction();
        activationTokenDAO.persist(activationToken);
        activationTokenDAO.commit();
        return activationToken;
    }
}
