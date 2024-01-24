package com.abm.mainet.securitymanagement.dao;

import java.util.List;

import com.abm.mainet.securitymanagement.domain.DeploymentOfStaff;

public interface IDeploymentOfStaffDao {

	List<DeploymentOfStaff> buildQuery(Long empTypeId, Long vendorId, Long cpdShiftId, Long locId, Long orgId);
	
	// List<DeploymentOfStaff> findStaffDetails(Long empTypeId,Long vendorId,Long cpdShiftId, Long locId,Long orgId);
}
