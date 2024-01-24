package com.abm.mainet.common.audit.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import org.springframework.stereotype.Service;

/**
 * @author ritesh.patil
 *
 */

@Service
public class AuditServiceImpl implements AuditService {

    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    @Override
    public void createHistory(final Object sourceObject, final Object targetObject) {
    }

    @Override
    public void createHistoryForObject(final Object obj) {
        final EntityManager entitymanager = entityManagerFactory.createEntityManager();
        final EntityTransaction enTransaction = entitymanager.getTransaction();
        enTransaction.begin();
        entitymanager.persist(obj);
        enTransaction.commit();
        entitymanager.close();
    }

}
