
package com.abm.mainet.account.dao;

import java.util.List;

import com.abm.mainet.account.domain.AccountBudgetEstimationPreparationEntity;
import com.abm.mainet.account.dto.AccountBudgetEstimationPreparationBean;

/**
 * @author prasad.kancharla
 *
 */
public interface AccountBudgetEstimationPreparationDao {

    public Boolean isBudgetEstimationPreparationEntryExists(Long faYearid, Long prRevBudgetCode, Long orgId,Long dpDeptid);

    List<AccountBudgetEstimationPreparationEntity> findByGridAllData(Long faYearid, Long cpdBugtypeId, Long dpDeptid,
            Long prBudgetCodeid, Long orgId);

    List<Object[]> findByAllBudgetCodeId(Long faYearid, Long fundId, Long functionId, Long cpdBugtypeId, Long prBudgetCodeid,
            Long dpDeptid, Long orgId);
    
    List<Object[]> findByAllBudgetCodeIdBulkEdit(Long faYearid, Long cpdBugtypeId, Long dpDeptid,
			Long cpdBugsubtypeId, Long fieldId, Long orgId);
}
