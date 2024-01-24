package com.abm.mainet.common.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;

@Repository
public class DepartmentLocationDAO extends AbstractDAO<LocationMasEntity> implements Serializable, IDepartmentLocationDAO {
    private static final long serialVersionUID = 8399989172682533131L;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IDepartmentLocationDAO#saveOrUpdate(com.abm.mainet.domain.core.DepartmentLocation)
     */

    @Override
    public LocationMasEntity saveOrUpdate(final LocationMasEntity location) {

        return create(location);
    }

    @Override
    public LocationMasEntity getDepartmentLocationForRegistraion(final Organisation organisation, final String isDeleted) {

        final StringBuilder queryAppender = new StringBuilder(
                "Select dl from LocationMasEntity dl where dl.organisation = ?1 and UPPER(dl.departmentName) = UPPER ( ?2 ) ");

        if ((isDeleted != null) && (!isDeleted.equalsIgnoreCase(""))) {
            queryAppender.append(" and dl.isDeleted =?3 ");
        }
        final Query query = createQuery(queryAppender.toString());
        query.setParameter(1, organisation);
        query.setParameter(2, "Citizen");

        if ((isDeleted != null) && (!isDeleted.equalsIgnoreCase(""))) {
            query.setParameter(3, isDeleted);
        }

        final LocationMasEntity location = null;

        @SuppressWarnings("unchecked")
        final List<LocationMasEntity> locationList = query.getResultList();

        if ((locationList == null) || locationList.isEmpty()) {
            return location;
        }
        return locationList.get(0);
    }
}
