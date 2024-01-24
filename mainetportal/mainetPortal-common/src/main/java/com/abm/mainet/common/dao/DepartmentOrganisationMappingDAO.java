package com.abm.mainet.common.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.DeptOrgMap;

/**
 * @author Pranit.Mhatre
 * @since 05 February, 2014
 */
@Repository
public class DepartmentOrganisationMappingDAO extends AbstractDAO<DeptOrgMap> implements IDepartmentOrganisationMappingDAO {
    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IDepartmentOrganisationMappingDAO#getaMapping(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<DeptOrgMap> getaMapping(final String activeStatus) {

        final Query query = createQuery("Select dm from DeptOrgMap dm where dm.mapStatus = ?1 ");
        query.setParameter(1, activeStatus);
        final List<DeptOrgMap> list = query.getResultList();
        return list;

    }
}
