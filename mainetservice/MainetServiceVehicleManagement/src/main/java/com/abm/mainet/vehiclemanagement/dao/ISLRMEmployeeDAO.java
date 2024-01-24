package com.abm.mainet.vehiclemanagement.dao;

import java.util.List;

import com.abm.mainet.vehiclemanagement.domain.VehicleEmployeeMaster;

public interface ISLRMEmployeeDAO {
    
    List<VehicleEmployeeMaster> searchEmployeeList(Long empId,String empUId, Long mrfId, Long orgId);

}
