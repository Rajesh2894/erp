package com.abm.mainet.common.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.domain.ServiceMaster;

@Repository
public class ServiceMasterDao extends AbstractDAO<Object> implements IServiceMasterDao {

    private static final Logger logger = Logger.getLogger(ServiceMasterDao.class);

    @Override
    public Long getSeviceId(final String shortCode, final Long orgId) {
        final PortalService appealMaster = getPortalServiceMaster(shortCode, orgId);
        return appealMaster.getServiceId();
    }

    @Override
    public PortalService getPortalServiceMaster(final String shortCode, final Long orgId) {
        final Query query = createQuery(
                "select am from PortalService am  WHERE am.shortName = :shortName AND am.serviceOrgId = :orgId");
        query.setParameter("shortName", shortCode);
        query.setParameter("orgId", orgId);
        final PortalService appealMaster = (PortalService) query.getSingleResult();
        return appealMaster;
    }

    @Override
    public ServiceMaster create(ServiceMaster serviceMaster) {
        this.entityManager.persist(serviceMaster);
        return serviceMaster;
    }

    @Override
    public void createPortalService(PortalService portalService) {
        this.entityManager.persist(portalService);
    }

    /**
     * Save data into database
     */

    @Override
    public PortalService createRestPortalService(PortalService portalService) {
        this.entityManager.merge(portalService);
        return portalService;
    }

    /**
     * used for get short code on the basis of
     * @param orgid
     * @param shortName
     */

    @Override
    public PortalService getRestPortalService(String shortName, Long orgid) {
        PortalService portalService = null;

        String qselectQuery = "SELECT ps FROM PortalService ps WHERE ps.shortName=:shortName AND ps.serviceOrgId=:orgid";
        final Query query = entityManager.createQuery(qselectQuery);
        query.setParameter("shortName", shortName);
        query.setParameter("orgid", orgid);
        try {
            portalService = (PortalService) query.getSingleResult();
        } catch (Exception exception) {
            logger.error("More than one short code define for code :  " + shortName + " orgid :" + orgid, exception);

        }
        return portalService;

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findAllActiveServicesByDepartment(Long orgid, Long deptId, String activeStatus) {
        String activeServiceQuery = "SELECT sm.serviceId, sm.serviceName, sm.shortName FROM PortalService sm WHERE sm.serviceOrgId=:orgId AND sm.psmDpDeptid=:depId AND sm.isDeleted=:activeStatus AND sm.serviceName IS NOT NULL";
        final Query query = entityManager.createQuery(activeServiceQuery);
        query.setParameter("activeStatus", activeStatus);
        query.setParameter("depId", deptId);
        query.setParameter("orgId", orgid);

        return query.getResultList();
    }

}
