package com.abm.mainet.swm.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.swm.domain.VehicleMaintenanceMaster;

/**
 * @author Ajay.Kumar
 *
 */
@Repository
public class VehicleMaintenanceMasterDAO extends AbstractDAO<VehicleMaintenanceMaster>
        implements IVehicleMaintenanceMasterDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<VehicleMaintenanceMaster> serchVehicleMaintenanceByVehicleTypeAndveDowntimeAndveDowntimeUnit(String status,
            Long vehicleType,
            Long veMeId, Long orgId) {

        Query query = this.createQuery(buildQuery(status, vehicleType, veMeId));

        query.setParameter("orgId", orgId);

        if (StringUtils.isNotEmpty(status)) {
            query.setParameter("status", status);
        }

        if (vehicleType != null) {
            query.setParameter("vehicleType", vehicleType);
        }

        if (veMeId != null) {
            query.setParameter("veMeId", veMeId);
        }

        return query.getResultList();

    }

    /**
     * @param status
     * @param vehicleType
     * @param veMeId
     * @return
     */
    private String buildQuery(String status, Long vehicleType, Long veMeId) {
        StringBuilder vehicleMaintenanceSearchQuery = new StringBuilder(
                " SELECT vm FROM VehicleMaintenanceMaster vm WHERE vm.orgid = :orgId ");

        if (StringUtils.isNotEmpty(status)) {
            vehicleMaintenanceSearchQuery.append(" AND vm.veMeActive like :status ");
        }

        if (vehicleType != null) {
            vehicleMaintenanceSearchQuery.append(" AND vm.veVetype = :vehicleType ");
        }
        if (veMeId != null) {
            vehicleMaintenanceSearchQuery.append(" AND vm.veMeId != :veMeId ");
        }
        vehicleMaintenanceSearchQuery.append(" ORDER BY vm.veMeId DESC ");

        return vehicleMaintenanceSearchQuery.toString();
    }

}
