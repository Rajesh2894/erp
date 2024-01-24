
package com.abm.mainet.account.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.account.dto.AccountBudgetAdditionalSupplementalBean;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author prasad.kancharla
 *
 */
public interface AccountBudgetAdditionalSupplementalService {

    List<AccountBudgetAdditionalSupplementalBean> findByAuthorizationGridData(Date frmDate, Date todate, Long cpdBugtypeId,
            String status, String budgIdentifyFlag, Long orgId);

    List<AccountBudgetAdditionalSupplementalBean> findBudgetAdditionalSupplementalByFinancialId(Long faYearid,
            String budgIdentifyFlag, Long orgId);

    AccountBudgetAdditionalSupplementalBean saveBudgetAdditionalSupplementalFormData(
            AccountBudgetAdditionalSupplementalBean BudgetAdditionalSupplemental, int langId, Organisation orgId);

    AccountBudgetAdditionalSupplementalBean getDetailsUsingBudgetAdditionalSupplementalId(
            AccountBudgetAdditionalSupplementalBean tbAcBudgetAdditionalSupplemental, int langId, Organisation orgId);

    List<AccountBudgetAdditionalSupplementalBean> findByGridAllData(Long faYearid, Long cpdBugtypeId, Long dpDeptid,
            Long prBudgetCodeid, String budgIdentifyFlag, Long orgId);

    List<AccountBudgetAdditionalSupplementalBean> findBudgetAdditionalSupplementalByOrg(Long orgId, String budgIdentifyFlag);

}
