package com.abm.mainet.securitymanagement.service;

import java.util.List;

import com.abm.mainet.securitymanagement.dto.TransferSchedulingOfStaffDTO;

public interface ITransferSchedulingOfStaffService {

	List<TransferSchedulingOfStaffDTO> findStaffDetails(Long empTypeId,String empCode, Long vendorId,Long cpdShiftId,Long locId ,Long orgId);

	List<TransferSchedulingOfStaffDTO> saveOrUpdate(List<TransferSchedulingOfStaffDTO> transferSchedulingOfStaffDTOList,
			TransferSchedulingOfStaffDTO transferSchedulingOfStaffDTO);
	
	List<TransferSchedulingOfStaffDTO> checkIfStaffExists(List<TransferSchedulingOfStaffDTO> transferSchedulingOfStaffDTOList,
			TransferSchedulingOfStaffDTO transferSchedulingOfStaffDTO);
	
	TransferSchedulingOfStaffDTO checkDateWithAppointDate(String contStaffIdNo,Long orgId);
}
