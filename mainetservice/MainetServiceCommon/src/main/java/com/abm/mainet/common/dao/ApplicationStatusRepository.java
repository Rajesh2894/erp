/**
 *
 */
package com.abm.mainet.common.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.ApplicationStatusEntity;
import com.abm.mainet.common.dto.ApplicationStatusRequestVO;

/**
 * @author vishnu.jagdale
 *
 */
@Repository
public class ApplicationStatusRepository implements IApplicationStatusRepository {

    private static Logger log = LoggerFactory.getLogger(ApplicationStatusRepository.class);

    @PersistenceUnit(unitName = "entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    @Override
    public List<ApplicationStatusEntity> getApplicationStatusList() {
        return null;
    }

    @Override
    public List<ApplicationStatusEntity> getApplicationStatusListOpenForUser(final ApplicationStatusRequestVO requestDTO)
            throws RuntimeException {
        final StringBuffer applicationIdList = new StringBuffer();
        final StringBuffer serviceIdList = new StringBuffer();
        final List<String> apmStatusList = new ArrayList<>();

        apmStatusList.add("Application Not Recieved");
        apmStatusList.add("Application Payment Not Recieved");

        List<ApplicationStatusEntity> appSTSEntityList = null;

        for (int i = 0; i < requestDTO.getAppDetailList().size(); i++) {
            if (i < (requestDTO.getAppDetailList().size() - 1)) {
                applicationIdList.append(requestDTO.getAppDetailList().get(i).getApmApplicationId() + ",");
                serviceIdList.append(requestDTO.getAppDetailList().get(i).getServiceId() + ",");
            } else {
                applicationIdList.append(requestDTO.getAppDetailList().get(i).getApmApplicationId());
                serviceIdList.append(requestDTO.getAppDetailList().get(i).getServiceId());
            }
        }

        final EntityManager entityManager = entityManagerFactory.createEntityManager();

        final String QueryString = "Select cd from ApplicationStatusEntity cd where cd.apmApplicationId IN " +
                "(" + applicationIdList.toString() + ")" +
                " and cd.serviceId IN " +
                "(" + serviceIdList.toString() + ")" +
                " and cd.apmStatus NOT IN :apmStatusList" +
                " and cd.orgId = :orgId ";

        System.out.println("---QueryString----\n" + QueryString);

        try {
            final TypedQuery<ApplicationStatusEntity> query = entityManager.createQuery(QueryString,
                    ApplicationStatusEntity.class);
            query.setParameter("orgId", requestDTO.getOrgId());
            query.setParameter("apmStatusList", apmStatusList);
            appSTSEntityList = query.getResultList();

        } catch (final Exception e) {
            e.printStackTrace();
            log.error("Error During getCheckListData call : ", e);

        } finally {
            closeQuetly(entityManager);
        }
        return appSTSEntityList;
    }

    @Override
    public List<ApplicationStatusEntity> getApplicationStatusDetail(final ApplicationStatusRequestVO requestDTO)
            throws RuntimeException {

        List<ApplicationStatusEntity> appSTSEntityList = null;

        final EntityManager entityManager = entityManagerFactory.createEntityManager();

        final String QueryString = "Select cd from ApplicationStatusEntity cd where "
                + "cd.apmApplicationId = :apmApplicationId and " +
                "cd.serviceId = :serviceId ";

        System.out.println("---QueryString----\n" + QueryString);

        try {
            final TypedQuery<ApplicationStatusEntity> query = entityManager.createQuery(QueryString,
                    ApplicationStatusEntity.class);

            query.setParameter("apmApplicationId", requestDTO.getAppDetailList().get(0).getApmApplicationId());
            query.setParameter("serviceId", requestDTO.getAppDetailList().get(0).getServiceId());
            appSTSEntityList = query.getResultList();
        } catch (final Exception e) {
            e.printStackTrace();
            log.error("Error During getCheckListData call : ", e);

        } finally {
            closeQuetly(entityManager);
        }
        return appSTSEntityList;

    }

    private void closeQuetly(final EntityManager entityManager) {
        if (entityManager != null) {
            entityManager.close();
        }

    }
}
