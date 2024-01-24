/*
 *
 */
package com.abm.mainet.swm.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.swm.domain.SanitationStaffTarget;

/**
 * The Class SanitationStaffTargetDAOImpl.
 */
@Repository
public class SanitationStaffTargetDAO extends AbstractDAO<SanitationStaffTarget> implements ISanitationStaffTargetDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.dao.SanitationStaffTargetDAO#serchDisposalSiteBySiteNumberAndSiteName(java.lang.Long,
     * java.lang.String, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SanitationStaffTarget> searchSanitationStaffTarget(Long sanType, Date fromDate, Date toDate, Long orgId) {

        Query query = this.createQuery(buildQuery(sanType, fromDate, toDate));

        query.setParameter("orgid", orgId);

        if (sanType != null) {
            query.setParameter("sanType", sanType);
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
     * @param siteNumber the site number
     * @param siteName the site name
     * @return the string
     */
    private String buildQuery(Long sanType, Date fromDate, Date toDate) {
        StringBuilder searchQuery = new StringBuilder(
                " SELECT sst FROM SanitationStaffTarget sst  WHERE sst.orgid = :orgid ");

        if (sanType != null) {
            searchQuery.append(" AND sst.sanType = :sanType ");
        }

        if (fromDate != null) {
            searchQuery.append(" AND sst.sanTgfromdt >= :fromDate ");
        }
        if (toDate != null) {
            searchQuery.append(" AND sst.sanTgtodt <= :toDate ");
        }
        searchQuery.append(" ORDER BY sst.sanId DESC ");

        return searchQuery.toString();
    }

}
