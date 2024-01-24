package com.abm.mainet.common.master.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.LocationOperationWZMapping;

@Repository
public class OperationWZMappingDAO extends AbstractDAO<LocationOperationWZMapping> implements IOperationWZMappingDAO {

    Logger logger = Logger.getLogger(OperationWZMappingDAO.class);

    @Override
    public List<Long> findLocations(final Long orgId, final Long deptId, final Long fLevel, final Long sLevel, final Long tLevel,
            final Long foLevel,
            final Long fiLevel) {
        final StringBuilder queryStr = new StringBuilder(
                "select o.locationMasEntity.locId from LocationOperationWZMapping o where o.orgId = :orgId and o.dpDeptId = :dpDeptId and o.codIdOperLevel1 = :fLevel  ");
        if (sLevel != null) {
            queryStr.append(" and o.codIdOperLevel2 = :sLevel");
        }
        if (tLevel != null) {
            queryStr.append(" and o.codIdOperLevel3 = :tLevel");
        }
        if (foLevel != null) {
            queryStr.append(" and o.codIdOperLevel4 = :foLevel");
        }
        if (fiLevel != null) {
            queryStr.append(" and o.codIdOperLevel5 = :fiLevel");
        }
        final Query query = entityManager.createQuery(queryStr.toString());
        query.setParameter("orgId", orgId);
        query.setParameter("dpDeptId", deptId);
        query.setParameter("fLevel", fLevel);
        if (sLevel != null) {
            query.setParameter("sLevel", sLevel);
        }
        if (tLevel != null) {
            query.setParameter("tLevel", tLevel);
        }
        if (foLevel != null) {
            query.setParameter("foLevel", foLevel);
        }
        if (fiLevel != null) {
            query.setParameter("fiLevel", fiLevel);
        }
        @SuppressWarnings("unchecked")
        final List<Long> entities = query.getResultList();
        return entities;
    }

    @Override
    public List<LocationMasEntity> findLocationOperationWZMapping(final Long orgId, final Long deptId,
            final Long codIdOperLevel1, final Long codIdOperLevel2, final Long codIdOperLevel3,
            final Long codIdOperLevel4,
            final Long codIdOperLevel5) {

        final StringBuilder queryStr = new StringBuilder(
                "select l from LocationOperationWZMapping o left join o.locationMasEntity l where o.orgId = :orgId and o.dpDeptId = :dpDeptId  ");

        if (codIdOperLevel1 != null) {
            queryStr.append(" and o.codIdOperLevel1 = :codIdOperLevel1");
        }
        if (codIdOperLevel2 != null) {
            queryStr.append(" and o.codIdOperLevel2 = :codIdOperLevel2");
        }
        if (codIdOperLevel3 != null) {
            queryStr.append(" and o.codIdOperLevel3 = :codIdOperLevel3");
        }
        if (codIdOperLevel4 != null) {
            queryStr.append(" and o.codIdOperLevel4 = :codIdOperLevel4");
        }
        if (codIdOperLevel5 != null) {
            queryStr.append(" and o.codIdOperLevel5 = :codIdOperLevel5");
        }
        final Query query = entityManager.createQuery(queryStr.toString());
        query.setParameter("orgId", orgId);
        query.setParameter("dpDeptId", deptId);

        if (codIdOperLevel1 != null) {
            query.setParameter("codIdOperLevel1", codIdOperLevel1);
        }
        if (codIdOperLevel2 != null) {
            query.setParameter("codIdOperLevel2", codIdOperLevel2);
        }
        if (codIdOperLevel3 != null) {
            query.setParameter("codIdOperLevel3", codIdOperLevel3);
        }
        if (codIdOperLevel4 != null) {
            query.setParameter("codIdOperLevel4", codIdOperLevel4);
        }
        if (codIdOperLevel5 != null) {
            query.setParameter("codIdOperLevel5", codIdOperLevel5);
        }
        @SuppressWarnings("unchecked")
        final List<LocationMasEntity> entities = query.getResultList();
        return entities;
    }
    
    @Override
    public List<LocationOperationWZMapping> getLocationOperationWZMappingByLocId(Long locId,Long orgId){
    	final StringBuilder queryStr = new StringBuilder(
                "select o from LocationOperationWZMapping o where o.orgId = :orgId and o.locationMasEntity.locId = :locId");

    	TypedQuery<LocationOperationWZMapping> query = entityManager.createQuery(queryStr.toString(),LocationOperationWZMapping.class);
        query.setParameter("orgId", orgId);
        query.setParameter("locId", locId);
        List<LocationOperationWZMapping> entities = query.getResultList();
        return entities;
    }

}
