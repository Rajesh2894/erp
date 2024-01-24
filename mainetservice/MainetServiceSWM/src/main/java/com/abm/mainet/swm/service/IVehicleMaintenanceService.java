/*
 * 
 */
package com.abm.mainet.swm.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.swm.dto.VehicleMaintenanceDTO;

/**
 * The Interface VehicleMaintenanceService.
 * 
 * @author Lalit.Prusti
 * 
 * Created Date : 22-May-2018
 *
 */
public interface IVehicleMaintenanceService {

    /**
     * Save vehicle maintenance.
     *
     * @param vehicleMaintenanceDTO the vehicle maintenance DTO
     * @return the vehicle maintenance DTO
     */
    VehicleMaintenanceDTO saveVehicleMaintenance(VehicleMaintenanceDTO vehicleMaintenanceDTO);

    /**
     * Update vehicle maintenance.
     *
     * @param vehicleMaintenanceDTO the vehicle maintenance DTO
     * @return the vehicle maintenance DTO
     */
    VehicleMaintenanceDTO updateVehicleMaintenance(VehicleMaintenanceDTO vehicleMaintenanceDTO);

    /**
     * Delete vehicle maintenance.
     *
     * @param vehicleMaintenanceId the vehicle maintenance id
     */
    void deleteVehicleMaintenance(Long vehicleMaintenanceId, Long empId, String ipMacAdd);

    /**
     * Gets the vehicle maintenance.
     *
     * @param vehicleMaintenanceId the vehicle maintenance id
     * @return the vehicle maintenance
     */
    VehicleMaintenanceDTO getVehicleMaintenance(Long vehicleMaintenanceId);

    /**
     * Search vehicle maintenance.
     *
     * @param vehType the veh type
     * @param maintenanceType the maintenance type
     * @param fromDate the from date
     * @param toDate the to date
     * @param orgid the orgid
     * @return the list
     */
    List<VehicleMaintenanceDTO> searchVehicleMaintenance(Long vehType, Long maintenanceType, Date fromDate, Date toDate,
            Long orgid);

    /**
     * validate Vehicle Maintenance
     * @param vehicleMaintenanceDTO
     * @return
     */
    boolean validateVehicleMaintenance(VehicleMaintenanceDTO vehicleMaintenanceDTO);

    /**
     * get Last Meter Reading
     * @param vehicleId
     * @param orgid
     * @return
     */
    Long getLastMeterReading(Long vehicleId, Long orgid);

}
