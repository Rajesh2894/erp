package com.abm.mainet.care.dao;

import java.util.List;
import java.util.Map;

import com.abm.mainet.care.domain.CareRequest;

public interface ComplaintRequestDAO {
	
	 List<CareRequest> getComplaintRegisterDetail(String colname,String Colvalue,Long orgId,Long deptId,Map<String, Object> argumentsMap);
}
