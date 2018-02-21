package me.jaybios.quickresponse.daos;

import java.io.Serializable;
import java.util.List;

public interface DAO<T, I extends Serializable> {

    List<T> findAll();

    T findByID(I id);

    T findByProperty(String property, Object value);

    <R, V> List<R> listRelationshipEntities(String idColumn, V idValue, T entity, Class<R> relationshipClass);

    void persist(T entity);

    void update(T entity);

    <U, V> void updateType(String idColumn, U id, String typeColumn, V typeValue);

    void delete(T entity);

    void deleteAll();

}
