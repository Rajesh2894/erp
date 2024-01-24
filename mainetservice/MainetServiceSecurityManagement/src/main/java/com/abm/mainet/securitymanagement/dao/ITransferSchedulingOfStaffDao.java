package com.abm.mainet.securitymanagement.dao;

import java.util.List;

import com.abm.mainet.securitymanagement.domain.ContractualStaffMaster;

public interface ITransferSchedulingOfStaffDao {
	
	List<ContractualStaffMaster> findStaffDetails(Long empTypeId, String empCode, Long vendorId, Long cpdShiftId,
			Long locId, Long orgId);
}
