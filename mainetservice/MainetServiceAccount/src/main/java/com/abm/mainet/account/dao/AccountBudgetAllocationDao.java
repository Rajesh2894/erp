
package com.abm.mainet.account.dao;

import java.util.List;

import com.abm.mainet.account.domain.AccountBudgetAllocationEntity;

/**
 * @author prasad.kancharla
 *
 */
public interface AccountBudgetAllocationDao {

    public Boolean isCombinationExists(Long faYearid, Long prRevBudgetCode, Long orgId);

    List<AccountBudgetAllocationEntity> findByGridAllData(Long faYearid, Long cpdBugtypeId, Long dpDeptid, Long prBudgetCodeid,
            Long orgId);

    List<Object[]> findByAllBudgetCodeId(Long faYearid, Long fundId, Long functionId, Long cpdBugtypeId, Long prBudgetCodeid,
            Long dpDeptid, Long orgId);
}
