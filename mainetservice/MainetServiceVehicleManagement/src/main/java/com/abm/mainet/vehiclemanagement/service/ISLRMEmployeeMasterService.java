package com.abm.mainet.vehiclemanagement.service;

import java.util.List;

import com.abm.mainet.vehiclemanagement.dto.SLRMEmployeeMasterDTO;

public interface ISLRMEmployeeMasterService {
    
    void saveEmployeeDetails(SLRMEmployeeMasterDTO sLRMEmployeeMasterDTO);
    
    void updateEmployeeDetails(SLRMEmployeeMasterDTO sLRMEmployeeMasterDTO);
    
    SLRMEmployeeMasterDTO searchEmployeeDetails(Long empId, Long orgId);
    
    List<SLRMEmployeeMasterDTO> searchEmployeeList(Long empId,String empUId, Long mrfId, Long orgId);
    
    Long checkDuplicateMobileNo (Long orgId,String empMobNo );

	public boolean checkEmpCodeByEmpCode(String empUId, Long orgid);
	
	boolean checkDuplicateMob(Long orgId, String empMobNo, Long empId);

	SLRMEmployeeMasterDTO getEmpDetails(Long empUId, long languageId);

	String getDriverFullNameById(Long empId);

	SLRMEmployeeMasterDTO searchEmployeeDetails(Long empId, Long orgId, long languageId);

	List<Object[]> getEmployeesForVehicleDriverMas(Long orgId, String desgDriver);

}
