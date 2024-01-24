/*
 *
 */
package com.abm.mainet.vehiclemanagement.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.vehiclemanagement.domain.VmVehicleFuelReconciation;

/**
 * The Class VehicleFuelReconciationDAOImpl.
 */
@Repository
public class VehicleFuelReconciliationDAO extends AbstractDAO<VmVehicleFuelReconciation> implements IVehicleFuelReconciationDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.dao.VehicleFuelReconciationDAO#serchDisposalSiteBySiteNumberAndSiteName(java.lang.Long,
     * java.lang.String, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<VmVehicleFuelReconciation> searchVehicleFuelReconciation(Long puId, Date fromDate, Date toDate, Long orgId) {

        Query query = this.createQuery(buildQuery(puId, fromDate, toDate));

        query.setParameter("orgid", orgId);

        if (puId != null) {
            query.setParameter("puId", puId);
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
    private String buildQuery(Long puId, Date fromDate, Date toDate) {
        StringBuilder searchQuery = new StringBuilder(
                " SELECT vfr FROM VmVehicleFuelReconciation vfr  WHERE vfr.orgid = :orgid ");

        if (puId != null) {
            searchQuery.append(" AND vfr.puId = :puId ");
        }

        if (fromDate != null) {
            searchQuery.append(" AND vfr.inrecdInvdate >= :fromDate ");
        }
        if (toDate != null) {
            searchQuery.append(" AND vfr.inrecdInvdate <= :toDate ");
        }

        searchQuery.append(" ORDER BY vfr.inrecId DESC ");

        return searchQuery.toString();
    }

}
