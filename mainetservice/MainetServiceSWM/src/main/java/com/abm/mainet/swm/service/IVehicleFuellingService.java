package com.abm.mainet.swm.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.swm.dto.VehicleFuellingDTO;

/**
 * The Interface VehicleFuellingService.
 *
 * @author Lalit.Prusti
 * 
 * Created Date : 25-May-2018
 */
public interface IVehicleFuellingService {

    /**
     * Search Vehicle Fueling by vehicle type and vehicle reg no.
     *
     * @param vehicleType the vehicle type
     * @param pumpId the Pump Id
     * @param orgId the org id
     * @return the list
     */
    List<VehicleFuellingDTO> searchVehicleFuelling(Long vehicleType, Long pumpId, Date fromDate,
            Date todDate, Long orgId);

    /**
     * Gets the vehicle by vehicle id.
     *
     * @param vehicleId the vehicle id
     * @return the vehicle by vehicle id
     */
    VehicleFuellingDTO getVehicleByVehicleId(Long vehicleId);

    /**
     * Save vehicle.
     *
     * @param vehicleIdDetails the vehicle id details
     * @return the vehicle master DTO
     */
    VehicleFuellingDTO saveVehicle(VehicleFuellingDTO vehicleDetails);

    /**
     * Update vehicle.
     *
     * @param vehicleIdDetails the vehicle id details
     * @return the vehicle master DTO
     */
    VehicleFuellingDTO updateVehicle(VehicleFuellingDTO vehicleDetails);

    /**
     * Delete vehicle.
     *
     * @param vehicleId the vehicle id
     */
    void deleteVehicle(Long vehicleId, Long empId, String ipMacAdd);

    /**
     * @param pumpId the Pump Id
     * @param fromDate
     * @param toDate
     * @param orgId
     * @return
     */
    List<VehicleFuellingDTO> getVehicleFuellingByAdviceDateAndPumpId(Long pumpId, Date fromDate, Date toDate, Long orgId,
            Boolean paid);

    /**
     * get Last Meter Reading
     * @param vehicleId
     * @param orgid
     * @return
     */
    Long getLastMeterReading(Long vehicleId, Long orgid);

}
