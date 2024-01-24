package com.abm.mainet.rts.service;

import com.abm.mainet.rts.dto.RtsExternalServicesDTO;

public interface IRtsExternalServices {
	
	public RtsExternalServicesDTO saveCertificateData(RtsExternalServicesDTO rtsExternalServicesDTO);
	
	void updateStatus(Long applicationId, Long orgId, String status);

}
