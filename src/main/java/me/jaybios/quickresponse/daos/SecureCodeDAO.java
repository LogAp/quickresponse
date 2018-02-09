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

    public SecureCode findByProperty(String property, String value) {
        TypedQuery<SecureCode> query = getCurrentSession()
                .createQuery(String.format("select c from SecureCode c where c.%s = '%s'", property, value), SecureCode.class);
        return query.getSingleResult();
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
