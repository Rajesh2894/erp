package com.abm.mainet.vehiclemanagement.dao;

import java.util.List;

import com.abm.mainet.vehiclemanagement.domain.VehicleMaintenanceMast;

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
    List<VehicleMaintenanceMast> serchVehicleMaintenanceByVehicleTypeAndveDowntimeAndveDowntimeUnit(String status,
            Long vehicleType, Long vehicleId, Long veMeId, Long orgId);

}
