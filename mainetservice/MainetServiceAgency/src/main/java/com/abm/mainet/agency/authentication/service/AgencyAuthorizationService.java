package com.abm.mainet.agency.authentication.service;

import java.util.List;

import com.abm.mainet.common.dto.EmployeeDTO;
import com.abm.mainet.common.integration.dms.dto.CFCAttachmentsDTO;

/**
 * @author Arun.Chavda
 *
 */
public interface AgencyAuthorizationService {

    public boolean saveApprovalStatus(EmployeeDTO employee);

    /**
     * @param rowId
     * @param orgId
     * @return
     */
    List<CFCAttachmentsDTO> getAgencyAttachmentsByRowId(Long rowId, Long orgId);

}
