package me.jaybios.quickresponse.daos;

import me.jaybios.quickresponse.models.Code;


import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

public class CodeDAO extends DatabaseHandler<Code, UUID> {

    public List<Code> findAll() {
        TypedQuery<Code> query = getCurrentSession().createQuery("select c from Code c", Code.class);
        return query.getResultList();
    }

    public Code findByID(UUID uuid) {
        return getCurrentSession().find(Code.class, uuid);
    }

    public void persist(Code entity) {
        getCurrentSession().persist(entity);
    }

    public void update(Code entity) {
        getCurrentSession().merge(entity);
    }

    public void delete(Code entity) {
        getCurrentSession().remove(entity);
    }

    public void deleteAll() {
        List<Code> codes = findAll();
        for (Code code : codes) {
            delete(code);
        }
    }
}
