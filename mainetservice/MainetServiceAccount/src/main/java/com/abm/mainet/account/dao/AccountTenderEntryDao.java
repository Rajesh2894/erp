
package com.abm.mainet.account.dao;

import java.util.List;

import com.abm.mainet.account.domain.AccountTenderEntryEntity;

/**
 * @author niranjan.rane
 *
 */
public interface AccountTenderEntryDao {
    /**
     * @param fundId
     * @param functionId
     * @param fieldId
     * @param pacId
     * @param sacId
     * @return
     */

    Boolean isCombinationExists(Long fundId, Long functionId, Long fieldId, Long pacId, Long sacId);

    List<AccountTenderEntryEntity> findByAllGridSearchData(String trTenderNo, Long vmVendorid, Long trTypeCpdId, Long sacHeadId,
            String trTenderAmount, String statusId, Long orgId);
}
