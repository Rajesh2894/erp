package com.abm.mainet.vehiclemanagement.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.vehiclemanagement.dto.InsuranceDetailsDTO;

public interface IInsuranceDetailService {
	
	public InsuranceDetailsDTO save(InsuranceDetailsDTO insuranceDetailsDTO);

	public List<InsuranceDetailsDTO> getAllRecord(Long orgid);

	List<InsuranceDetailsDTO> getAllVehicles(Long orgid);

	public List<InsuranceDetailsDTO> searchInsuranceDetails(Long department, Long vehicleType, Long veid, Long orgid);

	public InsuranceDetailsDTO getDetailById(Long insuranceDetId);

	public List<InsuranceDetailsDTO> insuranceDetails(Date issueDate, Date endDate, Long insurDetId, Long veid, Long orgid);

	public boolean searchVehicleByVehicleTypeAndVehicleRegNo(Long department, Date issueDate, long orgid);

}
