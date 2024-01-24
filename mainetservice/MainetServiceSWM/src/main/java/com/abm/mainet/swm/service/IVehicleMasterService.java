package com.abm.mainet.swm.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.swm.dto.VehicleMasterDTO;

/**
 * The Interface VehicleMasterService.
 *
 * @author Lalit.Prusti Created Date : 04-May-2018
 */

@WebService
public interface IVehicleMasterService {

    /**
     * Search vehicle by vehicle type and vehicle reg no.
     *
     * @param vehicleType the vehicle type
     * @param vehicleRegNo the vehicle reg no
     * @param orgId the org id
     * @return the list
     */
    List<VehicleMasterDTO> searchVehicleByVehicleTypeAndVehicleRegNo(Long vehicleType, String vehicleRegNo, Long orgId);

    /**
     * Gets the vehicle by vehicle id.
     *
     * @param vehicleId the vehicle id
     * @return the vehicle by vehicle id
     */
    VehicleMasterDTO getVehicleByVehicleId(Long vehicleId);

    /**
     * Save vehicle.
     *
     * @param vehicleIdDetails the vehicle id details
     * @return the vehicle master DTO
     */
    VehicleMasterDTO saveVehicle(VehicleMasterDTO vehicleIdDetails);

    /**
     * Update vehicle.
     *
     * @param vehicleIdDetails the vehicle id details
     * @return the vehicle master DTO
     */
    VehicleMasterDTO updateVehicle(VehicleMasterDTO vehicleIdDetails);

    /**
     * Delete vehicle.
     *
     * @param vehicleId the vehicle id
     */
    void deleteVehicle(Long vehicleId, Long empId, String ipMacAdd);

    /**
     * searchVehicle
     * @param vehicleType
     * @param vehicleRegNo
     * @param orgId
     * @return
     */
    List<VehicleMasterDTO> searchVehicle(Long vehicleType, String vehicleRegNo, Long orgId);

    /**
     * validate Vehiclen Master
     * @param vehicle
     * @return
     */
    boolean validateVehiclenMaster(VehicleMasterDTO vehicle);

    /**
     * search Scheduled Vehicle By orgId
     * @param orgId
     * @return
     */
    List<VehicleMasterDTO> searchScheduledVehicleByorgId(Long orgId);

}
