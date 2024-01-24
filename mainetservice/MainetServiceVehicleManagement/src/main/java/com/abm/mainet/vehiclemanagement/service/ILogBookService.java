package com.abm.mainet.vehiclemanagement.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.vehiclemanagement.dto.VehicleLogBookDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleScheduleDTO;

public interface ILogBookService {

	/**
	 * This method will save form content in the database
	 * 
	 * @param vehicleLogBookDto
	 */

	public VehicleLogBookDTO saveVehicleDetails(VehicleLogBookDTO vehicleLogBookDto);

	/**
	 * This will help us to get VehicleLogBook Details by orgid 
	 * 
	 * @param id
	 * @return
	 */
	public List<VehicleLogBookDTO> getAllRecord(Long orgId);
	
	/**
	 * This will help us to search VehicleLogBook Details by fromDate, toDate , fireStation
	 * 
	 * @param id
	 * @return
	 */
	
	List<VehicleLogBookDTO> searchVehicleDetail(Date fromDate,Date toDate,Long veNo,Long orgid);


	/**
	 * This will help us to get VehicleLogBook Details by veId 
	 * 
	 * @param id
	 * @return
	 */

	public VehicleLogBookDTO getVehicleById(Long veID);
	

   
	
	List<VehicleLogBookDTO>  getAllVehicles(Long orgid);

	

    boolean veLogBookDupCheck(VehicleLogBookDTO vehicleLogBookDTO);

	List<VehicleLogBookDTO> getAllVehiclesWithoutEmp(Long orgid);

	List<VehicleLogBookDTO> getLogBookForMaintenanceAlert(List<Long> activeMaintMasVehicleIdList, Long orgid);

}
	
	

