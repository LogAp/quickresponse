package me.jaybios.quickresponse.daos;

import me.jaybios.quickresponse.models.SecureCode;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

public class SecureCodeDAO extends DatabaseHandler<SecureCode, UUID> {
    public List<SecureCode> findAll() {
        TypedQuery<SecureCode> query = getCurrentSession().createQuery("select c from SecureCode c", SecureCode.class);
        return query.getResultList();
    }

    public SecureCode findByID(UUID uuid) {
        return getCurrentSession().find(SecureCode.class, uuid);
    }

    public void persist(SecureCode entity) {
        getCurrentSession().persist(entity);
    }

    public void update(SecureCode entity) {
        getCurrentSession().merge(entity);
    }

    public void delete(SecureCode entity) {
        getCurrentSession().remove(entity);
    }

    public void deleteAll() {
        List<SecureCode> secureCodes = findAll();
        for (SecureCode secureCode : secureCodes) {
            delete(secureCode);
        }
    }
}
