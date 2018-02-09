package me.jaybios.quickresponse.daos;

import me.jaybios.quickresponse.models.User;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

public class UserDAO extends DatabaseHandler<User, UUID> {
    @Override
    public List<User> findAll() {
        TypedQuery<User> query = getCurrentSession().createQuery("select u from User u", User.class);
        return query.getResultList();
    }

    @Override
    public User findByID(UUID id) {
        return getCurrentSession().find(User.class, id);
    }

    @Override
    public User findByProperty(String property, String value) {
        TypedQuery<User> query = getCurrentSession().createQuery("select u from User u where :property=:pvalue", User.class);
        query.setParameter("property", property);
        query.setParameter("pvalue", value);
        return query.getSingleResult();
    }

    @Override
    public void persist(User entity) {
        getCurrentSession().persist(entity);
    }

    @Override
    public void update(User entity) {
        getCurrentSession().merge(entity);
    }

    @Override
    public void delete(User entity) {
        getCurrentSession().remove(entity);
    }

    @Override
    public void deleteAll() {
        List<User> users = findAll();
        for (User user : users) {
            delete(user);
        }
    }
}
