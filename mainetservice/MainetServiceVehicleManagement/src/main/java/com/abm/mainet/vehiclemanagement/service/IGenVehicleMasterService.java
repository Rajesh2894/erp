package com.abm.mainet.vehiclemanagement.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.QueryParam;

import org.springframework.data.repository.query.Param;

import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.OEMWarrantyDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleScheduleDTO;



/**
 * The Interface VehicleMasterService.
 *
 * @author Lalit.Prusti Created Date : 04-May-2018
 */

@WebService
public interface IGenVehicleMasterService {

    /**
     * Search vehicle by vehicle type and vehicle reg no.
     *
     * @param vehicleType the vehicle type
     * @param vehicleRegNo the vehicle reg no
     * @param orgId the org id
     * @return the list
     */
    List<GenVehicleMasterDTO> searchVehicleByVehicleTypeAndVehicleRegNo(Long vehicleType, String vehicleRegNo,Long deptId,Long location, Long orgId);
    
    List<GenVehicleMasterDTO> fetchVeNoByVehicleTypeIdAndDeptId(Long vehicleType,Long deptId, Long orgId);
    
    

    /**s
     * Gets the vehicle by vehicle id.
     *
     * @param vehicleId the vehicle id
     * @return the vehicle by vehicle id
     */
    GenVehicleMasterDTO getVehicleByVehicleId(Long vehicleId);

    /**
     * Save vehicle.
     *
     * @param vehicleIdDetails the vehicle id details
     * @return the vehicle master DTO
     */
 //   GenVehicleMasterDTO saveVehicles(GenVehicleMasterDTO vehicleIdDetails);

    /**
     * Update vehicle.
     *
     * @param vehicleIdDetails the vehicle id details
     * @return the vehicle master DTO
     */
    GenVehicleMasterDTO updateVehicle(GenVehicleMasterDTO vehicleIdDetails);

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
    //List<GenVehicleMasterDTO> searchVehicle(Long vehicleType, String vehicleRegNo,Long deptId,Long location, Long orgId);
	List<GenVehicleMasterDTO> searchVehicle(Long vehicleType, String vehicleRegNo, String veChasisSrno, Long deptId,
			Long location, Long orgId);

    /**
     * validate Vehiclen Master
     * @param vehicle
     * @return
     */
    boolean validateVehiclenMaster(GenVehicleMasterDTO vehicle);

    /**
     * search Scheduled Vehicle By orgId
     * @param orgId
     * @return
     */
    List<GenVehicleMasterDTO> searchScheduledVehicleByorgId(Long orgId);
    
    List<GenVehicleMasterDTO> findAll(Long orgid);

	List<Object[]> getAllVehicles(Long orgId);

	GenVehicleMasterDTO saveVehicle(GenVehicleMasterDTO vehicleIdDetails);

	List<GenVehicleMasterDTO> searchVehicleByVehTypeAndVehRegNo(Long vehicleType, String vehicleRegNo, Long orgId, String mode);
	
	 List<GenVehicleMasterDTO> searchVehicleByVehNoAndVehTypeAndVehRegNoAndrentToDate(Long veId,Long vehicleType,String vehicleRegNo,Long veDriverName,String rentToDateFlag,String veActiveStaus,Long orgId);	      

	List<GenVehicleMasterDTO> searchFuelByVehRegNo(Long vehicleType, String vehicleRegNo, Long orgId);

	List<GenVehicleMasterDTO> searchVehicleTypeByDeptId(Long deptId, String vehicleRegNo, Long orgId);

	String fetchVehicleNoByVeId(Long veId);

	List<Object[]> getVehicleByNumber(Long veid, Long orgid);
	
	List<Object[]> getVehicleByNo(Long veNo, Long orgid);

	List<VehicleScheduleDTO> getVehicleByNumberVe(Long veid, Long orgid);

	boolean validateVehicle(GenVehicleMasterDTO genVehicleMasterDTO);

	List<GenVehicleMasterDTO> searchVehicleNoByDeptId(Long department, Long orgid);

	List<GenVehicleMasterDTO> getVehDet(String veId, Date vesFromdt, Date vesTodt);

	List<Object[]> getAllVehiclesWithoutEmp(Long orgId);

	String fetchChasisNoByVeIdAndOrgid(Long veId, Long orgId);

	List<Object[]> getUlbActiveVehiclesForMaintMasterAdd(Long orgId);

	List<Object[]> getAllVehicleIdNumberObjectList(Long orgId);

	List<GenVehicleMasterDTO> getActiveVehiclesForMaintenanceAlert(List<Long> activeMaintMasVehicleIdList, Long orgId);


}
