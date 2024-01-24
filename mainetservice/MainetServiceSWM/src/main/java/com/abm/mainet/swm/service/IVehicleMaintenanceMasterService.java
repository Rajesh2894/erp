package com.abm.mainet.swm.service;

import java.util.List;

import com.abm.mainet.swm.dto.VehicleMaintenanceMasterDTO;

/**
 * The IVehicleMaintenanceMaster Service
 * @author Ajay.Kumar
 *
 */
public interface IVehicleMaintenanceMasterService {

    /**
     * save Vehicle Maintenance Master
     * @param vehicleMaintenanceMasterDTO
     * @return
     */
    VehicleMaintenanceMasterDTO saveVehicleMaintenanceMaster(VehicleMaintenanceMasterDTO vehicleMaintenanceMasterDTO);

    /**
     * @param vehicleMaintenanceMasterDTO
     * @return
     */
    VehicleMaintenanceMasterDTO updateVehicleMaintenance(VehicleMaintenanceMasterDTO vehicleMaintenanceMasterDTO);

    /**
     * delete Vehicle Maintenance Master
     * @param vehicleMaintenanceId
     * @param empId
     * @param ipMacAdd
     */
    void deleteVehicleMaintenanceMaster(Long vehicleMaintenanceId, Long empId, String ipMacAdd);

    /**
     * get Vehicle Maintenance Master
     * @param vehicleMaintenanceId
     * @return
     */
    VehicleMaintenanceMasterDTO getVehicleMaintenanceMaster(Long vehicleMaintenanceId);

    /**
     * get All Vehicle Maintenance
     * @param vehicleType
     * @param orgId
     * @return
     */
    List<VehicleMaintenanceMasterDTO> getAllVehicleMaintenance(Long vehicleType, Long orgId);

    /**
     * serch Vehicle Maintenance By veDowntime And veDowntimeUnit
     * @param vehicleType
     * @param orgId
     * @return
     */
    List<VehicleMaintenanceMasterDTO> serchVehicleMaintenanceByveDowntimeAndveDowntimeUnit(Long vehicleType, Long orgId);

    /**
     * validate Vehicle Maintenance Master
     * @param vehicleMaintenanceMasterDTO
     * @return
     */
    boolean validateVehicleMaintenanceMaster(VehicleMaintenanceMasterDTO vehicleMaintenanceMasterDTO);

    /**
     * serch Vehicle Maintenance
     * @param vehicleType
     * @param orgId
     * @return
     */
    List<VehicleMaintenanceMasterDTO> serchVehicleMaintenance(Long vehicleType, Long orgId);

}
