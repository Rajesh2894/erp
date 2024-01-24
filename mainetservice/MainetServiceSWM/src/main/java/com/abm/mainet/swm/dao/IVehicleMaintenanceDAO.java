/*
 * 
 */
package com.abm.mainet.swm.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.swm.domain.VehicleMaintenance;

/**
 * The Interface VehicleMaintenanceDAO.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 10-Jun-2018
 */
public interface IVehicleMaintenanceDAO {

    /**
     * Search vehicle maintenance.
     *
     * @param vehicleType the vehicle type
     * @param maintenanceType the maintenance type
     * @param fromDate the from date
     * @param toDate the to date
     * @param orgId the org id
     * @return the list
     */
    List<VehicleMaintenance> searchVehicleMaintenance(Long vehicleType, Long maintenanceType, Date fromDate, Date toDate,
            Long orgId);

}
