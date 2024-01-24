package com.abm.mainet.swm.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.swm.domain.PopulationMaster;

/**
 * @author Ajay.Kumar
 *
 */
@Repository
public class PopulationMasterDAO extends AbstractDAO<PopulationMaster> implements IPopulationMasterDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.dao.PopulationMasterDAO#searchByYearAndWordZoneBlock(java.lang.Long, java.lang.Long,
     * java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PopulationMaster> searchByYearAndWordZoneBlock(String status, Long yearCpdId, Long ward, Long zone,
            Long block, Long route, Long subRoute, Long orgId, Long popId) {

        Query jpaQuery = this.createQuery(buildQuery(status, yearCpdId, ward, zone, block, route, subRoute, popId));
        jpaQuery.setParameter("orgId", orgId);

        if (StringUtils.isNotEmpty(status)) {
            jpaQuery.setParameter("status", status);
        }

        if (null != yearCpdId) {
            jpaQuery.setParameter("yearCpdId", yearCpdId);
        }
        if (null != ward) {
            jpaQuery.setParameter("ward", ward);
        }
        if (null != zone) {
            jpaQuery.setParameter("zone", zone);
        }
        if (null != block) {
            jpaQuery.setParameter("block", block);
        }
        if (null != route) {
            jpaQuery.setParameter("route", route);
        }
        if (null != subRoute) {
            jpaQuery.setParameter("subRoute", subRoute);
        }
        if (null != popId) {
            jpaQuery.setParameter("popId", popId);
        }

        return jpaQuery.getResultList();
    }

    /**
     * Builds the query.
     *
     * @param yearCpdId the year cpd id
     * @param ward the ward
     * @param zone the zone
     * @param block the block
     * @param route the route
     * @param subRoute the sub route
     * @return the string
     */
    private String buildQuery(String status, Long yearCpdId, Long ward, Long zone, Long block, Long route, Long subRoute,
            Long popId) {
        StringBuilder query = new StringBuilder(" SELECT pm FROM  PopulationMaster pm WHERE pm.orgid = :orgId ");

        if (StringUtils.isNotEmpty(status)) {
            query.append(" AND popActive like :status ");
        }

        if (null != yearCpdId) {
            query.append(" AND popYear = :yearCpdId");
        }
        if (null != ward) {
            query.append(" AND codDwzid1 = :ward");
        }
        if (null != zone) {
            query.append(" AND codDwzid2 = :zone");
        }
        if (null != block) {
            query.append(" AND codDwzid3 = :block");
        }
        if (null != route) {
            query.append(" AND codDwzid4 = :route");
        }
        if (null != subRoute) {
            query.append(" AND codDwzid5 = :subRoute");
        }
        if (null != popId) {
            query.append(" AND popId != :popId");
        }
        query.append(" ORDER BY popId ASC ");
        return query.toString();
    }

}
