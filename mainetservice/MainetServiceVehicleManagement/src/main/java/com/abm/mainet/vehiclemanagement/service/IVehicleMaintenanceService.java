/*
 * 
 */
package com.abm.mainet.vehiclemanagement.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.vehiclemanagement.dto.OEMWarrantyDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleMaintenanceDTO;

/**
 * The Interface VehicleMaintenanceService.
 * @author Lalit.Prusti
 * Created Date : 22-May-2018
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
	 * @param vehType         the veh type
	 * @param maintenanceType the maintenance type
	 * @param fromDate        the from date
	 * @param toDate          the to date
	 * @param orgid           the orgid
	 * @return the list
	 */
	List<VehicleMaintenanceDTO> searchVehicleMaintenance(Long vehType, Long maintenanceType, Date fromDate, Date toDate,
			Long orgid);

	/**
	 * validate Vehicle Maintenance
	 * 
	 * @param vehicleMaintenanceDTO
	 * @return
	 */
	boolean validateVehicleMaintenance(VehicleMaintenanceDTO vehicleMaintenanceDTO);

	/**
	 * get Last Meter Reading
	 * 
	 * @param vehicleId
	 * @param orgid
	 * @return
	 */
	Long getLastMeterReading(Long vehicleId, Long orgid);

	List<VehicleMaintenanceDTO> searchVehicleMaintenanceData(Long vehicleId, Long orgid);

	VehicleMaintenanceDTO getDetailByVehNo(String requestNo, Long orgid);

	String updateWorkFlowService(WorkflowTaskAction workflowTaskAction, VehicleMaintenanceDTO vehicleMaintenanceDTO);

	VehicleMaintenanceDTO saveVehicleMaintenance(VehicleMaintenanceDTO vehicleMaintenanceDTO,
			OEMWarrantyDTO oemWarrantyDto, long levelCheck, WorkflowTaskAction workflowTaskAction,
			ApplicantDetailDTO applicantDetailDTO);

	void updateWorkFlowStatus(Long brId, Long orgId, String wfStatus);

	boolean isForVehicleWorkFlowInProgress(Long veNo, long orgid);

	String getDecisionByAppId(String requestNo, Long orgId);

	List<VehicleMaintenanceDTO> getMaintenanceDataForMaintenanceAlert(List<Long> activeMaintMasVehicleIdList,
			Long orgId);
}
