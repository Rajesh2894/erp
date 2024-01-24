
package com.abm.mainet.account.dao;

import java.util.List;

import com.abm.mainet.account.domain.AccountBudgetOpenBalanceEntity;

/**
 * @author prasad.kancharla
 *
 */
public interface AccountBudgetOpenBalanceDao {

    public Boolean isCombinationExists(Long faYearid, Long fundId, Long fieldId, String cpdIdDrcr, Long sacHeadId, Long orgId);

    public List<AccountBudgetOpenBalanceEntity> findByGridDataFinancialId(Long faYearid, String cpdIdDrcr, Long sacHeadId,
            String status, Long orgId);

}
