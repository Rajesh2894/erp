package com.abm.mainet.common.integration.dms.service;

import com.abm.mainet.common.integration.dms.dto.DmsManagementDto;

public interface IDmsManagementService {
    
    public DmsManagementDto saveDms(DmsManagementDto dto);
    
    public void updateManagementRecord(Long orgId);

}
