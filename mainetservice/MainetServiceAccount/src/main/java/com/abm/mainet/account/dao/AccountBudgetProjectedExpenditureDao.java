
package com.abm.mainet.account.dao;

import java.util.List;

import com.abm.mainet.account.domain.AccountBudgetProjectedExpenditureEntity;

/**
 * @author prasad.kancharla
 *
 */
public interface AccountBudgetProjectedExpenditureDao {

    public List<AccountBudgetProjectedExpenditureEntity> getBudgetProjectedExpenditureByFinYearBudgetCode(Long faYearid,
            Long prBudgetCodeid,
            Long orgId,Long deptId,Long fieldId);

    public List<Object[]> getOrgEsmtAmtsLFYear(Long faYearIds, Long budgCodeid, Long orgId);

    public Boolean isCombinationCheckTransactions(Long prExpenditureId, Long faYearId, Long orgId);

    public List<Object[]> findByGridAllData(Long faYearid, Long fundId, Long functionId, Long cpdBugsubtypeId, Long dpDeptid,
            Long prBudgetCodeid, Long fieldId,Long orgId);

	List<Object[]> findByGridAllDatas(Long faYearid, Long fundId, Long functionId, Long cpdBugsubtypeId, Long dpDeptid,
			Long prBudgetCodeid, Long fieldId, Long orgId);

}
