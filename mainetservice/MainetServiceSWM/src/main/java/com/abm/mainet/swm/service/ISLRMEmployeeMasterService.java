package com.abm.mainet.swm.service;

import java.util.List;

import com.abm.mainet.swm.dto.SLRMEmployeeMasterDTO;

public interface ISLRMEmployeeMasterService {
    
    void saveEmployeeDetails(SLRMEmployeeMasterDTO sLRMEmployeeMasterDTO);
    
    void updateEmployeeDetails(SLRMEmployeeMasterDTO sLRMEmployeeMasterDTO);
    
    SLRMEmployeeMasterDTO searchEmployeeDetails(Long empId, Long orgId);
    
    List<SLRMEmployeeMasterDTO> searchEmployeeList(Long empId,Long empUId, Long mrfId, Long orgId);
    
    Long checkDuplicateMobileNo (Long orgId,String empMobNo );

}
