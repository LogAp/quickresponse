package me.jaybios.quickresponse.daos;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

public class PersistentDAO<T, I extends Serializable> implements DAO<T, I> {
    private final Class<T> entityClass;

    private EntityManager currentSession;

    private EntityTransaction currentTransaction;

    public PersistentDAO(Class<T> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.currentSession = entityManager;
    }

    public void openTransaction() {
        currentTransaction = currentSession.getTransaction();
        currentTransaction.begin();
    }

    public void commit() {
        currentTransaction.commit();
    }

    @Override
    public List<T> findAll() {
        TypedQuery<T> query = currentSession.createQuery("select e from " + getEntityName() + " e", entityClass);
        return query.getResultList();
    }

    @Override
    public T findByID(I id) {
        return currentSession.find(entityClass, id);
    }

    @Override
    public T findByProperty(String property, String value) {
        TypedQuery<T> query = currentSession
                .createQuery("select e from " + getEntityName() + " e where " + property + " = :pvalue", entityClass);
        query.setParameter("pvalue", value);
        List<T> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public void persist(T entity) {
        currentSession.persist(entity);
    }

    @Override
    public void update(T entity) {
        currentSession.merge(entity);
    }

    @Override
    public void delete(T entity) {
        currentSession.remove(entity);
    }

    @Override
    public void deleteAll() {
        List<T> entities = findAll();
        for (T entity : entities) {
            delete(entity);
        }
    }

    private String getEntityName() {
        if (entityClass == null) return null;
        return entityClass.getSimpleName();
    }
}
