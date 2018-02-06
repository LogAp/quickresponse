package me.jaybios.quickresponse.daos;

import java.io.Serializable;
import java.util.List;

public interface DAO<T, I extends Serializable> {
    List<T> findAll();
    T findByID(I id);
    void persist(T entity);
    void update(T entity);
    void delete(T entity);
    void deleteAll();
}
