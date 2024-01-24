/**
 * 
 */
package com.abm.mainet.account.repository;

import com.abm.mainet.account.dto.AdvanceEntryDTO;

/**
 * @author satish.rathore
 *
 */
public interface AdvanceEntryRepositoryCustom {

    public void updateAdvanceEntryAuditDetails(AdvanceEntryDTO advanceEntryDTO, Long OrgId);

}
