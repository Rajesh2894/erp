package com.abm.mainet.swm.dao;

import java.util.List;

import com.abm.mainet.swm.domain.VehicleMaintenanceMaster;

/**
 * @author Ajay.Kumar
 *
 */
public interface IVehicleMaintenanceMasterDAO {

    /**
     * serch VehicleMaintenance By VehicleType And veDowntime And veDowntime Unit
     * @param status
     * @param vehicleType
     * @param veMeId
     * @param orgId
     * @return
     */
    List<VehicleMaintenanceMaster> serchVehicleMaintenanceByVehicleTypeAndveDowntimeAndveDowntimeUnit(String status,
            Long vehicleType,
            Long veMeId, Long orgId);

}
