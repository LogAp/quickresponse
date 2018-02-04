package me.jaybios.quickresponse.daos;

import java.io.Serializable;
import java.util.List;

public interface DAO<T, ID extends Serializable> {
    public List<T> findAll();
    public T findByID(ID id);
    public void persist(T entity);
    public void update(T entity);
    public void delete(T entity);
    public void deleteAll();
}
