package com.abm.mainet.swm.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.swm.domain.WastageSegregation;

/**
 * The Class WastageSegregationImpl.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 13-Jun-2018
 */
@Repository
public class WastageSegregationDAO extends AbstractDAO<WastageSegregationDAO> implements IWastageSegregationDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.dao.WastageSegregationDAO#searchWastageSegregation(java.lang.Long, java.util.Date, java.util.Date,
     * java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<WastageSegregation> searchWastageSegregation(Long deId, Date fromDate, Date toDate, Long orgId) {

        Query query = this.createQuery(buildQuery(deId, fromDate, toDate));

        query.setParameter("orgid", orgId);

        if (deId != null) {
            query.setParameter("deId", deId);
        }
        if (fromDate != null) {
            query.setParameter("fromDate", fromDate);
        }
        if (toDate != null) {
            query.setParameter("toDate", toDate);
        }

        return query.getResultList();
    }

    /**
     * Builds the query.
     *
     * @param deId the veheicle id
     * @param fromDate the from date
     * @param toDate the to date
     * @return the string
     */
    private String buildQuery(Long deId, Date fromDate, Date toDate) {
        StringBuilder searchQuery = new StringBuilder(
                " SELECT ws FROM WastageSegregation ws  WHERE ws.orgid = :orgid ");

        if (deId != null) {
            searchQuery.append(" AND ws.deId = :deId ");
        }

        if (fromDate != null) {
            searchQuery.append(" AND ws.grDate >= :fromDate ");
        }
        if (toDate != null) {
            searchQuery.append(" AND ws.grDate <= :toDate ");
        }
        searchQuery.append(" ORDER BY ws.grId DESC ");
        return searchQuery.toString();
    }

}
