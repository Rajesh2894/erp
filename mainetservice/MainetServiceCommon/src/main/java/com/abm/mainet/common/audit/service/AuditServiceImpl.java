package com.abm.mainet.common.audit.service;

import java.util.List;

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

    /**
     * default batch size
     */
    private static final int BATCH_SIZE = 100;

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

    @Override
    public void createHistoryForListObj(List<Object> objectList) {
        final EntityManager entitymanager = entityManagerFactory.createEntityManager();
        final EntityTransaction enTransaction = entitymanager.getTransaction();
        int batchSize = 0;
        enTransaction.begin();
        for (Object obj : objectList) {
            batchSize++;
            entitymanager.persist(obj);
            if ((batchSize % BATCH_SIZE) == 0) {
                entitymanager.flush();
                entitymanager.clear();
            }
        }
        enTransaction.commit();
        entitymanager.close();
    }

	@Override
	public void saveDataForProperty(String appId, Long serviceId, String workflowDecision, Long orgId, Long empId,
			String lgIpMac, long level) {
	}
}
