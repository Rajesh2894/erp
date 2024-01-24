
package com.abm.mainet.account.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.account.domain.AccountTenderEntryEntity;
import com.abm.mainet.account.dto.AccountTenderEntryBean;

public interface AccountTenderEntryService {
    /**
     * Loads an entity from the database using its Primary Key
     *
     * @param trTenderId
     * @return entity
     */
    AccountTenderEntryEntity findById(Long trTenderId);

    /**
     * Creates the given entity in the database
     *
     * @param entity
     * @return
     * @throws Exception
     */
    AccountTenderEntryBean create(AccountTenderEntryBean entity) throws Exception;

    /**
     * @param fundId
     * @param functionId
     * @param fieldId
     * @param pacId
     * @param sacId
     *
     */
    Boolean isCombinationExists(Long fundId, Long functionId, Long fieldId, Long pacId, Long sacId);

    /**
     * @param orgId
     * @return
     */
    List<AccountTenderEntryBean> findAll(Long orgId);

    Map<Long, Long> findDepositTypeEmdData(Long vmVendorid, Long emdId, Long orgId);

    List<AccountTenderEntryBean> findByAllGridSearchData(String trTenderNo, Long vmVendorid, Long trTypeCpdId,
            Long sacHeadId, String trTenderAmount, String statusId, Long orgId);

    AccountTenderEntryEntity findByTenderNo(String trTenderNo, Long orgId);
}
