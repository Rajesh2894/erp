package com.abm.mainet.vehiclemanagement.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.vehiclemanagement.domain.VehicleMaintenanceDetails;

/**
 * @author Ajay.Kumar
 *
 */
@Repository
public class VehicleMaintenanceDao extends AbstractDAO<VehicleMaintenanceDetails>
        implements IVehicleMaintenanceDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.dao.IVehicleMaintenanceDAO#searchVehicleMaintenance(java.lang.Long, java.lang.Long, java.util.Date,
     * java.util.Date, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<VehicleMaintenanceDetails> searchVehicleMaintenance(Long vehicleType, Long maintenanceType, Date fromDate, Date toDate,
            Long orgId) {

        Query query = this.createQuery(buildQuery(vehicleType, maintenanceType, fromDate, toDate));

        query.setParameter("orgId", orgId);

        if (vehicleType != null) {
            query.setParameter("vehicleType", vehicleType);
        }
        if (maintenanceType != null) {
            query.setParameter("maintenanceType", maintenanceType);
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
     * @param maintenanceType
     * @param fromDate
     * @param toDate
     * @return
     */
    private String buildQuery(Long vehicleType, Long maintenanceType, Date fromDate, Date toDate) {
        StringBuilder searchQuery = new StringBuilder(
                " SELECT vm FROM VehicleMaintenanceDetails vm WHERE vm.orgid = :orgId ");

        if (vehicleType != null) {
            searchQuery.append(" AND vm.veVetype = :vehicleType ");
        }
        if (maintenanceType != null) {
            searchQuery.append(" AND vm.vemMetype = :maintenanceType ");
        }

        if (fromDate != null) {
            searchQuery.append(" AND vm.vemDate >= :fromDate ");
        }
        if (toDate != null) {
            searchQuery.append(" AND vm.vemDate <= :toDate ");
        }

        searchQuery.append(" ORDER BY vm.vemId DESC ");

        return searchQuery.toString();
    }

}
