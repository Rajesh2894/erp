package com.abm.mainet.swm.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.swm.domain.SanitationMaster;

/**
 * The Class SanitationMasterDAOImpl.
 * @author Lalit.Prusti
 *
 * Created Date : 17-May-2018
 */
@Repository
public class SanitationMasterDAO extends AbstractDAO<SanitationMaster> implements ISanitationMasterDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.dao.SanitationMasterDAO#searchByYearAndWordZoneBlock(java.lang.Long, java.lang.Long,
     * java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SanitationMaster> searchToiletLocation(String status, Long number, Long type, String name, Long ward,
            Long zone, Long block, Long route, Long subRoute, Long orgId) {

        Query jpaQuery = this.createQuery(buildQuery(status, number, type, name, ward, zone, block, route, subRoute));
        jpaQuery.setParameter("orgId", orgId);

        if (StringUtils.isNotEmpty(status)) {
            jpaQuery.setParameter("status", status);
        }

        if (null != number) {
            jpaQuery.setParameter("number", number);
        }
        if (null != type) {
            jpaQuery.setParameter("type", type);
        }

        if (StringUtils.isNotEmpty(name)) {
            jpaQuery.setParameter("name", name);
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

        return jpaQuery.getResultList();

    }

    /**
     * Builds the query.
     *
     * @param number : Toilet Number
     * @param type : Toilet Type
     * @param name : Toilet Name
     * @param ward the ward
     * @param zone the zone
     * @param block the block
     * @param route the route
     * @param subRoute the sub route
     * @return the string
     */
    private String buildQuery(String status, Long number, Long type, String name, Long ward, Long zone, Long block, Long route,
            Long subRoute) {
        StringBuilder query = new StringBuilder(" SELECT sm FROM  SanitationMaster sm WHERE sm.orgid = :orgId ");

        if (StringUtils.isNotEmpty(status)) {
            query.append(" AND sm.sanActive like :status ");
        }

        if (null != number) {
            query.append(" AND sm.sanId != :number");
        }
        if (null != type) {
            query.append(" AND sm.sanType = :type");
        }

        if (StringUtils.isNotEmpty(name)) {
            query.append(" AND sm.sanName = :name");
        }
        if (null != ward) {
            query.append(" AND sm.codWard1 = :ward");
        }
        if (null != zone) {
            query.append(" AND sm.codWard2 = :zone");
        }
        if (null != block) {
            query.append(" AND sm.codWard3 = :block");
        }
        if (null != route) {
            query.append(" AND sm.codWard4 = :route");
        }
        if (null != subRoute) {
            query.append(" AND sm.codWard4 = :subRoute");
        }
        query.append(" ORDER BY sm.sanId DESC");
        return query.toString();
    }

}
