package me.jaybios.quickresponse.daos;

import me.jaybios.quickresponse.util.JPAUtility;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;

public abstract class DatabaseHandler<T, I extends Serializable> implements DAO<T, I> {
    private EntityManager currentSession;
    private EntityTransaction currentTransaction;

    public EntityManager openSession() {
        currentSession = JPAUtility.getEntityManager();
        return currentSession;
    }

    public EntityManager openSessionWithTransaction() {
        currentSession = JPAUtility.getEntityManager();
        currentTransaction = currentSession.getTransaction();
        currentTransaction.begin();
        return currentSession;
    }

    public void closeSession() {
        currentSession.close();
    }

    public void closeSessionAndCommit() {
        currentTransaction.commit();
        currentSession.close();
    }

    EntityManager getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(EntityManager currentSession) {
        this.currentSession = currentSession;
    }

    public EntityTransaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(EntityTransaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }
}
