package com.abm.mainet.mobile.dao;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.RestCommonExeception;
import com.abm.mainet.mobile.domain.DeviceRegistrationEntity;

/**
 * @author umashanker.kanaujiya
 *
 */
@Repository
public class DeviceRegistrationDAOImpl extends AbstractDAO<DeviceRegistrationEntity> implements DeviceRegistrationDAO {

    private static final Logger LOG = Logger.getLogger(DeviceRegistrationDAOImpl.class);

    @Override
    public DeviceRegistrationEntity getDevRegistrationService(final Long UserId, final Long OrgId) {

        LOG.info("Start the getDevRegistrationService");
        try {
            final Query query = entityManager
                    .createQuery("select db from DeviceRegistrationEntity  db where db.userId=:userId and db.orgId =:orgId");
            if (null != UserId) {
                query.setParameter("userId", UserId);
            }
            if ((null != OrgId) && (OrgId > 0)) {
                query.setParameter("orgId", OrgId);
            }
            final DeviceRegistrationEntity emp = (DeviceRegistrationEntity) query.getSingleResult();
            return emp;

        } catch (final Exception exception) {
            LOG.error("Exeception occcur in getDevRegistrationService dao", exception);
            throw new RestCommonExeception(" Exeception occcur in getDevRegistrationService Dao", exception);
        }

    }

    @Override
    public Boolean doDevRegistrationService(final DeviceRegistrationEntity entity) {
        LOG.info("Start the doDevRegistrationService");
        try {
            final DeviceRegistrationEntity deviceRegistrationEntity = entityManager.merge(entity);
            entityManager.persist(deviceRegistrationEntity);
            return true;
        } catch (final Exception exception) {
            LOG.error("Exeception occcur in doDevRegistrationService dao", exception);
            throw new RestCommonExeception(" Exeception occcur in doRegistrationService Dao", exception);
        }

    }

}
