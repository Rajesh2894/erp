/*
 *
 */
package com.abm.mainet.swm.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.swm.domain.TripSheet;

/**
 * The Class TripSheetDAOImpl.
 */
@Repository
public class TripSheetDAO extends AbstractDAO<TripSheet> implements ITripSheetDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.dao.TripSheetDAO#serchDisposalSiteBySiteNumberAndSiteName(java.lang.Long, java.lang.String,
     * java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<TripSheet> searchTripSheet(Long veId, Date fromDate, Date toDate, Long orgId) {

        Query query = this.createQuery(buildQuery(veId, fromDate, toDate));

        query.setParameter("orgid", orgId);

        if (veId != null) {
            query.setParameter("veId", veId);
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
    private String buildQuery(Long veId, Date fromDate, Date toDate) {
        StringBuilder searchQuery = new StringBuilder(
                " SELECT ts FROM TripSheet ts  WHERE ts.orgid = :orgid ");

        if (veId != null) {
            searchQuery.append(" AND ts.veId = :veId ");
        }

        if (fromDate != null) {
            searchQuery.append(" AND ts.tripDate >= :fromDate ");
        }
        if (toDate != null) {
            searchQuery.append(" AND ts.tripDate <= :toDate ");
        }
        searchQuery.append(" ORDER BY ts.tripId DESC ");
        return searchQuery.toString();
    }

}
