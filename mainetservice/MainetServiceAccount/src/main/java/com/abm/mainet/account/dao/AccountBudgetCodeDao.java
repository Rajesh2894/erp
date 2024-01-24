
package com.abm.mainet.account.dao;

import java.util.List;

import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;

/**
 * @author prasad.kancharla
 *
 */
public interface AccountBudgetCodeDao {

    List<AccountBudgetCodeEntity> findByAllGridSearchData(Long dpDeptid, Long fundId, Long fieldId, Long functionId,
            Long sacHeadId, String cpdIdStatusFlag, Long orgId, String objectHeadType);

    public Boolean isCombinationExists(Long dpDeptid, Long fundId, Long functionId, Long fieldId, Long sacId, Long orgId,
            String objectHeadType);

}
