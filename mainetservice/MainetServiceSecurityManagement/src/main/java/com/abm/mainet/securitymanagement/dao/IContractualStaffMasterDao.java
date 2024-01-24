package com.abm.mainet.securitymanagement.dao;

import java.util.List;

import com.abm.mainet.securitymanagement.domain.ContractualStaffMaster;


public interface IContractualStaffMasterDao {
	
	List<ContractualStaffMaster> findStaffDetails(String contStaffName,Long vendorId,Long locId, Long cpdShiftId,Long dayPrefixId,Long orgId);

	//List<ContractualStaffMaster> searchData(long orgid, String status);
}
