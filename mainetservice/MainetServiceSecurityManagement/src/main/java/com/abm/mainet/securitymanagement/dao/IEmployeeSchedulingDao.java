package com.abm.mainet.securitymanagement.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.securitymanagement.domain.EmployeeScheduling;

public interface IEmployeeSchedulingDao {

	List<EmployeeScheduling> search(String empTypeId, Long vendorId, Long locId, Long cpdShiftId, Date contStaffSchFrom,
			Date contStaffSchTo, String contStaffName, String contStaffIdNo, Long emplScdlId, Long orgid);
}
