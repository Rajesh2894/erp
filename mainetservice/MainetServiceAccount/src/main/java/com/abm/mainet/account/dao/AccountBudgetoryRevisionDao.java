
package com.abm.mainet.account.dao;

import java.util.List;

import com.abm.mainet.account.domain.AccountBudgetoryRevisionEntity;

/**
 * @author prasad.kancharla
 *
 */
public interface AccountBudgetoryRevisionDao {

    public Boolean isBudgetoryRevisionEntryExists(Long faYearid, Long prRevBudgetCode, Long orgId);

    List<AccountBudgetoryRevisionEntity> findByGridAllData(Long faYearid, Long cpdBugtypeId, Long dpDeptid, Long prBudgetCodeid,
            Long orgId);

    List<Object[]> findByAllBudgetCodeId(Long faYearid, Long fundId, Long functionId, Long cpdBugtypeId, Long prBudgetCodeid,
            Long dpDeptid, Long orgId);
}
