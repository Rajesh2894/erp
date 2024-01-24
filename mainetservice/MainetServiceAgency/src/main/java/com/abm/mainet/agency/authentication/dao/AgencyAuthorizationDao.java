package com.abm.mainet.agency.authentication.dao;

import java.util.List;

import com.abm.mainet.common.integration.dms.domain.CFCAttachment;

/**
 * @author Arun.Chavda
 *
 */
public interface AgencyAuthorizationDao {

    /**
     * @param rowId
     * @param orgId
     * @return
     */
    List<CFCAttachment> getAgencyAttachmentsByRowId(Long rowId, long orgId);

    boolean saveAgencyCFCAttachment(CFCAttachment attachments);

}
