package com.abm.mainet.validitymaster.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.abm.mainet.validitymaster.dto.EmployeeWardZoneMappingDetailDto;
import com.abm.mainet.validitymaster.dto.EmployeeWardZoneMappingDto;

/**
 * @author cherupelli.srikanth
 * @since 29 Nov 2021
 */

public interface IEmployeeWardZoneMappingService {

	void saveEmployeeWardZoneapping(EmployeeWardZoneMappingDto employeeWardZoneDto);
	
	List<EmployeeWardZoneMappingDetailDto> getWardZoneDetailList(Long empId, Long orgId);
	
	 boolean checkWardZoneMappingFlag(Long empId, Long orgId, Long ward1, Long ward2, Long ward3, Long ward4, Long ward5);
	 
	 List<EmployeeWardZoneMappingDto> getWardZoneMappingByOrgId(@Param("orgId") Long orgId);
	 
	 EmployeeWardZoneMappingDto getWardZoneMappingByOrgIdAndEmpId(@Param("orgId") Long orgId, Long empId);
	
}
