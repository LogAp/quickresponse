package me.jaybios.quickresponse.services;

import me.jaybios.quickresponse.daos.PersistentDAO;

import java.io.Serializable;
import java.util.List;

public class ResourceService<T, I extends Serializable> {

    PersistentDAO<T, I> dao;

    public ResourceService(PersistentDAO<T, I> dao) {
        this.dao = dao;
    }

    public void store(T entity) {
        dao.openTransaction();
        dao.persist(entity);
        dao.commit();
    }

    public void update(T entity) {
        dao.openTransaction();
        dao.update(entity);
        dao.commit();
    }

    public void deleteById(I id) {
        dao.openTransaction();
        T code = dao.findByID(id);
        dao.delete(code);
        dao.commit();
    }

    public T findById(I id) {
        return dao.findByID(id);
    }

    public List<T> findAll() {
        return dao.findAll();
    }

    public void deleteAll() {
        dao.openTransaction();
        dao.deleteAll();
        dao.commit();
    }
}
