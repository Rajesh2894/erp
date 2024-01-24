
package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.abm.mainet.account.domain.AccountBudgetEstimationPreparationEntity;
import com.abm.mainet.account.dto.AccountBudgetEstimationPreparationBean;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author prasad.kancharla
 *
 */
public interface AccountBudgetEstimationPreparationService {

    public Boolean isBudgetEstimationPreparationEntryExists(Long faYearid, Long prRevBudgetCode, Long orgId,Long deptId);

    AccountBudgetEstimationPreparationBean saveBudgetEstimationPreparationFormData(
            AccountBudgetEstimationPreparationBean BudgetEstimationPreparation, int languageId, Organisation org)
            throws Exception;

    List<AccountBudgetEstimationPreparationBean> findBudgetEstimationPreparationByFinId(Long orgId);

    AccountBudgetEstimationPreparationBean getDetailsUsingEstimationPreparationId(
            AccountBudgetEstimationPreparationBean tbAcBudgetEstimationPreparation, int languageId, Organisation org, Long orgId);
    
    AccountBudgetEstimationPreparationBean getDetailsUsingEstimationPreparationIdBulkEdit(
            AccountBudgetEstimationPreparationBean tbAcBudgetEstimationPreparation, int languageId, Organisation org, Long orgId,String budgetCodeIds);

    List<BigDecimal> findBudgetEstimationPreparationLastYearCountAmountData(Long faYearid, Long budgCodeid, Long orgId)
            throws Exception;

    List<BigDecimal> findBudgetEstimationPreparationLastYearCountAmountDataExp(Long faYearid, Long budgCodeid, Long orgId)
            throws Exception;
    
    public List<BigDecimal> findBudgetEstimationPreparationLastYearExpActualCountAmountDataExp(final Long faYearid, final Long budgCodeid,
            final Long orgId , final Long deptId);
    
    public List<BigDecimal> findBudgetEstimationPreparationLastYearActualCountAmountDataRev(final Long faYearid, final Long budgCodeid,
            final Long orgId , final Long deptId);

    List<AccountBudgetEstimationPreparationBean> findByGridAllData(Long faYearid, Long cpdBugtypeId, Long dpDeptid,
            Long prBudgetCodeid, Long orgId);

    Map<String, String> findByBudgetIds(Long faYearid, Long fundId, Long functionId, Long cpdBugtypeId, Long prBudgetCodeid,
            Long dpDeptid, Long orgId);
    
    Map<String, String> findByBudgetIdsBulkEdit(Long faYearid, Long cpdBugtypeId, Long dpDeptid, Long cpdBugsubtypeId, Long fieldId,Long orgId);

	AccountBudgetEstimationPreparationBean saveBudgetEstimationPreparationFormDataBulkEdit(
			AccountBudgetEstimationPreparationBean budgetEstimationPreparation, int langId, Organisation org)
			throws Exception;
	
	Map<String, String> findAccountBudgetSummaryBasedOnNFY(Long nextFaYearid);
}
