package me.jaybios.quickresponse.services;

import me.jaybios.quickresponse.daos.DatabaseHandler;

import java.io.Serializable;
import java.util.List;

public class ResourceService<T, I extends Serializable> {
    private DatabaseHandler<T, I> dao;

    public ResourceService(DatabaseHandler<T, I> dao) {
        this.dao = dao;
    }

    public void store(T entity) {
        dao.openSessionWithTransaction();
        dao.persist(entity);
        dao.closeSessionAndCommit();
    }

    public void update(T entity) {
        dao.openSessionWithTransaction();
        dao.update(entity);
        dao.closeSessionAndCommit();
    }

    public void deleteById(I id) {
        dao.openSessionWithTransaction();
        T code = dao.findByID(id);
        dao.delete(code);
        dao.closeSessionAndCommit();
    }

    public T findById(I id) {
        dao.openSession();
        T code = dao.findByID(id);
        dao.closeSession();
        return code;
    }

    public List<T> findAll() {
        dao.openSession();
        List<T> codes = dao.findAll();
        dao.closeSession();
        return codes;
    }

    public void deleteAll() {
        dao.openSessionWithTransaction();
        dao.deleteAll();
        dao.closeSessionAndCommit();
    }
}
