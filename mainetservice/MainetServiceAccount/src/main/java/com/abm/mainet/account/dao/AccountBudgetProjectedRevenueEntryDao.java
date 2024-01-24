
package com.abm.mainet.account.dao;

import java.util.List;

/**
 * @author prasad.kancharla
 *
 */
public interface AccountBudgetProjectedRevenueEntryDao {

    public Boolean isCombinationExists(Long faYearid, Long prBudgetCodeid, Long orgId,Long deptID,Long fieldId);

    public List<Object[]> getOrgEsmtAmtsLFYear(Long faYearIds, Long budgCodeid, Long orgId);

    public Boolean isCombinationCheckTransactions(Long prProjectionId, Long faYearId, Long orgId);

    public List<Object[]> findByGridAllData(Long faYearid, Long fundId, Long functionId, Long cpdBugsubtypeId,
            Long dpDeptid, Long prBudgetCodeid,Long fieldId, Long orgId);

	List<Object[]> findByGridAllDatas(Long faYearid, Long fundId, Long functionId, Long cpdBugsubtypeId, Long dpDeptid,
			Long prBudgetCodeid, Long fieldId, Long orgId);
}
