package com.abm.mainet.securitymanagement.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.dto.HolidayMasterDto;
import com.abm.mainet.securitymanagement.domain.ShiftMaster;
import com.abm.mainet.securitymanagement.dto.EmployeeSchedulingDTO;
import com.abm.mainet.securitymanagement.dto.EmployeeSchedulingDetDTO;

public interface IEmployeeSchedulingService {

	List<EmployeeSchedulingDTO> findContractualEmpNameById();

	List<EmployeeSchedulingDTO> save(List<EmployeeSchedulingDTO> employeeSchedulingDTOList, EmployeeSchedulingDTO dto);

	List<EmployeeSchedulingDTO> searchEmployees(String empCode, Long vendorId, Long locId, Long cpdShiftId,
			Date contStaffSchFrom, Date contStaffSchTo, String contStaffName, String contStaffIdNo, Long emplScdlId,
			Long orgid);

	List<EmployeeSchedulingDTO> checkIfStaffExists(List<EmployeeSchedulingDTO> employeeSchedulingDTOList,
			EmployeeSchedulingDTO employeeSchedulingDTO);
	
	List<EmployeeSchedulingDTO> getStaffNameByVendorId(Long vendorId, String empCode, Long orgId);
	
	ShiftMaster findShiftById(Long cpdShiftId,Long orgid);
	
	List<HolidayMasterDto> findHolidaysByYear(Date contStaffSchFrom,Date contStaffSchTo, Long orgid);
	
	List<EmployeeSchedulingDetDTO> findStaffDetails(Long emplScdlId,Long orgid);

	void save(List<EmployeeSchedulingDTO> employeeSchedulingDTOList, EmployeeSchedulingDTO employeeSchedulingDTO,
			List<EmployeeSchedulingDTO> vp);

	void saveData(List<EmployeeSchedulingDetDTO> employeeSchedulingDTOList);

	void saveoverTimeData(EmployeeSchedulingDTO employeeSchedulingDTO);

	Long getLatestEmployeeScheduledLocId(String staffId, Long orgId);
}
