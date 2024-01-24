/*
 * 
 */
package com.abm.mainet.swm.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.swm.dto.VehicleFuellingDTO;
import com.abm.mainet.swm.dto.VehicleMaintenanceDTO;
import com.abm.mainet.swm.dto.VehicleScheduleDTO;

/**
 * The Interface VehicleScheduleService.
 *
 * @author Lalit.Prusti Created Date : 04-May-2018
 */

@WebService
public interface IVehicleScheduleService {

    /**
     * Gets the vehicleSchedule by vehicleSchedule id.
     *
     * @param vehicleScheduleId the vehicleSchedule id
     * @return the vehicleSchedule by vehicleSchedule id
     */
    VehicleScheduleDTO getVehicleScheduleByVehicleScheduleId(Long vehicleScheduleId);

    /**
     * Save vehicleSchedule.
     *
     * @param vehicleScheduleIdDetails the vehicleSchedule details
     * @return the vehicleSchedule master DTO
     */
    VehicleScheduleDTO saveVehicleSchedule(VehicleScheduleDTO vehicleScheduleDetails);

    /**
     * Update vehicleSchedule.
     *
     * @param vehicleScheduleIdDetails the vehicleSchedule details
     * @return the vehicleSchedule master DTO
     */
    VehicleScheduleDTO updateVehicleSchedule(VehicleScheduleDTO vehicleScheduleDetails);

    /**
     * Delete vehicleSchedule.
     *
     * @param vehicleScheduleId the vehicleSchedule id
     * @return
     */
    void deleteVehicleSchedule(Long vehicleScheduleId, Long empId, String ipMacAdd);

    /**
     * Search vehicle schedule by vehicle type and vehicle reg no.
     *
     * @param vehicleType the vehicle type
     * @param vehicleNo the vehicle no
     * @param orgId the org id
     * @return the list
     */

    boolean vehicleScheduleValidate(VehicleScheduleDTO vehicleScheduleDto);

    /**
     * search Vehicle Schedule By VehicleType And VehicleNo
     * @param vehicleType
     * @param vehicleNo
     * @param orgId
     * @return
     */
    List<VehicleScheduleDTO> searchVehicleScheduleByVehicleTypeAndVehicleNo(Long vehicleType, Long vehicleNo, Long orgId);

    /**
     * validate Vehicle Schedule
     * @param vehicleScheduleDto
     * @return
     */
    boolean validateVehicleSchedule(VehicleScheduleDTO vehicleScheduleDto);

    /**
     * find Vehicle Schedule Details
     * @param orgId
     * @param veId
     * @param veNo
     * @param fromdate
     * @param todate
     * @return
     */
    VehicleScheduleDTO findVehicleScheduleDetails(Long orgId, Long veId, Long veNo, Date fromdate, Date todate);

    /**
     * find Fuel Expenditure Details
     * @param orgId
     * @param veVetype
     * @param veNo
     * @param fromdate
     * @param todate
     * @param pumpId
     * @return
     */
    VehicleFuellingDTO findFuelExpenditureDetails(Long orgId, Long veVetype, Long veNo, Date fromdate, Date todate,
            Long pumpId);

    /**
     * find Fuel Expenditure Details
     * @param veheicleSchedule
     * @return
     */
    boolean updateCurrentVeheicleCodinates(VehicleScheduleDTO veheicleSchedule);

    /**
     * search Vehicle Schedule By orgId
     * @param orgId
     * @return
     */
    List<VehicleScheduleDTO> searchVehicleScheduleByorgId(Long orgId);

    /**
     * find Maintenance Exp Details
     * @param orgId
     * @param veId
     * @param veNo
     * @param fromdate
     * @param todate
     * @param vemMetype
     * @return
     */
    VehicleMaintenanceDTO findMaintenanceExpDetails(Long orgId, Long veId, Long veNo, Date fromdate, Date todate,
            Long vemMetype);

    /**
     * find scheduled vehicle Details
     * @param orgId
     * @return
     */
    List<Object[]> findscheduledvehicleDetails(Long orgId);

    VehicleScheduleDTO findSweepingDetails(Long orgId, Long beatId, Long monthNo, String wasteTypeId);

    VehicleScheduleDTO findVehicleSchdetailsByVehNo(Long orgId, Long veId, Date date);
    
    public List<VehicleScheduleDTO> searchVehicleScheduleByVehicleTypeAndVehicleNoAndDate(
    		Long vehicleType,Long vehicleNo,Long orgId, Date fromDate , Date toDate);
    
    public List<VehicleScheduleDTO> getVehicleScheduleByFromDtAndToDt(Date fromDate,
            Date toDate, Long orgId);

}
