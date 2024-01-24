package com.abm.mainet.swm.dao;

import java.util.List;

import com.abm.mainet.swm.domain.SLRMEmployeeMaster;

public interface ISLRMEmployeeDAO {
    
    List<SLRMEmployeeMaster> searchEmployeeList(Long empId,Long empUId, Long mrfId, Long orgId);

}
