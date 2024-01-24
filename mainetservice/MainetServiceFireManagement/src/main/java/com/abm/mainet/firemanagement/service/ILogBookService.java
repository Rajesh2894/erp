package com.abm.mainet.firemanagement.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.firemanagement.dto.VehicleLogBookDTO;

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
	
	List<VehicleLogBookDTO> searchFireCallRegisterwithDate(Date fromDate,Date toDate,String veNo,Long orgid);


	/**
	 * This will help us to get VehicleLogBook Details by veId 
	 * 
	 * @param id
	 * @return
	 */

	public VehicleLogBookDTO getVehicleById(Long veID);
	

   
	
	List<VehicleLogBookDTO>  getAllVehicles(Long orgid);

	List<VehicleLogBookDTO> getAllVehiclesByDept(Long orgid, Long department);

	List<String> getVehicleNoListByFromTodate(Date fromDate, Date toDate, Long orgid);


}
	
	

