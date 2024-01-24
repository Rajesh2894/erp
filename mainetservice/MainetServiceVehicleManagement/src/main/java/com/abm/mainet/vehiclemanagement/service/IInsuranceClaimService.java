package com.abm.mainet.vehiclemanagement.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.vehiclemanagement.dto.InsuranceClaimDTO;
import com.abm.mainet.vehiclemanagement.dto.InsuranceDetailsDTO;

public interface IInsuranceClaimService {
	
	public InsuranceClaimDTO saveClaim(InsuranceClaimDTO insuranceDetailsDTO);
	
	public List<InsuranceClaimDTO> searchInsuranceClaim(Long department, Long vehicleType, Long veid, Long orgid);
	
	public List<InsuranceClaimDTO> insuranceClaim(Date issueDate, Date endDate, Long insurDetId, Long veid, Long orgid);
	
	List<InsuranceClaimDTO> getAllVehicles(Long orgid);
	
	public InsuranceClaimDTO getDetailById(Long insuranceDetId);

}
