/*
 * 
 */
package com.abm.mainet.vehiclemanagement.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.vehiclemanagement.domain.VehicleScheduleData;

/**
 * The Class VehicleScheduleDAOImpl.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 06-Jun-2018
 */
@Repository
public class VehicleSchedulingDAO extends AbstractDAO<VehicleScheduleData> implements IVehicleScheduleDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.dao.VehicleScheduleDAO#searchVehicleScheduleByVehicleTypeAndVehicleNo(java.lang.Long,
     * java.lang.Long, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<VehicleScheduleData> searchVehicleScheduleByVehicleTypeAndVehicleNo(Long vehicleType, Long vehicleNo, Date fromDate,
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
                " SELECT vs FROM VehicleScheduleData vs WHERE vs.orgid = :orgid and vs.isDeleted = 'N' ");
        

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
        searchQuery.append(" ORDER BY vs.vesId DESC ");
        return searchQuery.toString();
    }

}
