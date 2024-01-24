/*
 * 
 */
package com.abm.mainet.swm.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.swm.domain.VehicleSchedule;

/**
 * The Class VehicleScheduleDAOImpl.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 06-Jun-2018
 */
@Repository
public class VehicleScheduleDAO extends AbstractDAO<VehicleSchedule> implements IVehicleScheduleDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.dao.VehicleScheduleDAO#searchVehicleScheduleByVehicleTypeAndVehicleNo(java.lang.Long,
     * java.lang.Long, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<VehicleSchedule> searchVehicleScheduleByVehicleTypeAndVehicleNo(Long vehicleType, Long vehicleNo, Date fromDate,
            Date toDate, Long orgId) {
        Query query = this.createQuery(buildQuery(vehicleType, vehicleNo, fromDate, toDate));

        query.setParameter("orgid", orgId);

        if (vehicleType != null) {
            query.setParameter("vehicleType", vehicleType);
        }

        if (vehicleNo != null) {
            query.setParameter("vehicleNo", vehicleNo);
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
     * @param vehicleType
     * @param vehicleNo
     * @param fromDate
     * @param toDate
     * @return
     */
    private String buildQuery(Long vehicleType, Long vehicleNo, Date fromDate,
            Date toDate) {
        StringBuilder searchQuery = new StringBuilder(
                " SELECT vs FROM VehicleSchedule vs WHERE vs.orgid = :orgid ");

        if (vehicleType != null) {
            searchQuery.append(" AND vs.veVetype = :vehicleType ");
        }

        if (vehicleNo != null) {
            searchQuery.append(" AND vs.veId = :vehicleNo ");
        }
        if (fromDate != null) {
            searchQuery.append(" AND vs.vesFromdt >= :fromDate ");
        }
        if (toDate != null) {
            searchQuery.append(" AND vs.vesTodt <= :toDate ");
        }
        searchQuery.append(" AND isActive <> 'N' ");
        searchQuery.append(" ORDER BY vs.vesId DESC ");
        return searchQuery.toString();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<VehicleSchedule> searchVehicleScheduleByVehicleTypeAndVehicleNoAndDate(Long vehicleType, Long vehicleNo, Date fromDate,
            Date toDate, Long orgId) {
    	
    	StringBuilder searchQuery = new StringBuilder(
                " SELECT vs FROM VehicleSchedule vs WHERE vs.orgid = :orgid ");

        if (vehicleType != null) {
            searchQuery.append(" AND vs.veVetype = :vehicleType ");
        }

        if (vehicleNo != null) {
            searchQuery.append(" AND vs.veId = :vehicleNo ");
        }
        if (fromDate != null && toDate != null) {
            searchQuery.append("  AND ( ( (vs.vesFromdt BETWEEN :fromDate AND :toDate ) AND (vs.vesTodt BETWEEN :fromDate AND :toDate) ) ");
            
            searchQuery.append("  OR ( (vs.vesFromdt < :fromDate ) AND (vs.vesTodt BETWEEN :fromDate AND :toDate) ) ");

            searchQuery.append("  OR ( (vs.vesFromdt BETWEEN :fromDate AND :toDate) AND (vs.vesTodt > :toDate) ) ) ");
        }
        searchQuery.append(" AND isActive <> 'N' ");
        searchQuery.append(" ORDER BY vs.vesId DESC ");
        
        Query query = this.createQuery(searchQuery.toString());

        query.setParameter("orgid", orgId);

        if (vehicleType != null) {
            query.setParameter("vehicleType", vehicleType);
        }

        if (vehicleNo != null) {
            query.setParameter("vehicleNo", vehicleNo);
        }

        if (fromDate != null) {
            query.setParameter("fromDate", fromDate);
        }
        if (toDate != null) {
            query.setParameter("toDate", toDate);
        }
        return query.getResultList();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<VehicleSchedule> getVehicleScheduleByFromDtAndToDt(Date fromDate,
            Date toDate, Long orgId) {
    	
    	StringBuilder searchQuery = new StringBuilder(
                " SELECT vs FROM VehicleSchedule vs WHERE vs.orgid = :orgid ");

    	if (fromDate != null && toDate != null) {
            searchQuery.append("  AND ( ( (vs.vesFromdt BETWEEN :fromDate AND :toDate ) AND (vs.vesTodt BETWEEN :fromDate AND :toDate) ) ");
            
            searchQuery.append("  OR ( (vs.vesFromdt < :fromDate ) AND (vs.vesTodt BETWEEN :fromDate AND :toDate) ) ");

            searchQuery.append("  OR ( (vs.vesFromdt BETWEEN :fromDate AND :toDate) AND (vs.vesTodt > :toDate) ) ) ");
        }
        searchQuery.append(" AND isActive <> 'N' ");
        searchQuery.append(" ORDER BY vs.vesId DESC ");
        
        Query query = this.createQuery(searchQuery.toString());

        query.setParameter("orgid", orgId);

        if (fromDate != null) {
            query.setParameter("fromDate", fromDate);
        }
        if (toDate != null) {
            query.setParameter("toDate", toDate);
        }
        return query.getResultList();
    }

}
