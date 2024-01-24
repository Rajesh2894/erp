package com.abm.mainet.cms.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.cms.domain.EIPHome;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.util.UserSession;

/**
 * @author Pranit.Mhatre
 */
@Repository
public class AdminHomeDAO extends AbstractDAO<EIPHome> implements IAdminHomeDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IAdminHomeDAO#findObject(java.lang.String)
     */
    @Override
    public EIPHome findObject(final String activeStatus) {

        /* JPA Query Start */
        final Query query = createQuery("select e from EIPHome e where e.isDeleted =?1 and e.orgId =?2 ");
        query.setParameter(1, activeStatus);
        query.setParameter(2, UserSession.getCurrent().getOrganisation());
        final List<EIPHome> eipHomes = query.getResultList();
        if ((eipHomes == null) || eipHomes.isEmpty()) {
            return null;
        } else {
            return eipHomes.get(0);
            /* JPA Query Ends */
        }

    }

    @Override
    public void persist(final EIPHome entity) {
        entityManager.persist(entity);

    }
}
