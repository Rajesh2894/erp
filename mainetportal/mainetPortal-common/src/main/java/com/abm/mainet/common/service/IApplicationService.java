package com.abm.mainet.common.service;

import com.abm.mainet.common.dto.ApplicationStatusDTO;

public interface IApplicationService {
	
	ApplicationStatusDTO getApplicationStatus(long applicationId, int langId );

	Long getOrgId(long applicationId);



	Long getConnId(String conNo, Long orgId);

}
