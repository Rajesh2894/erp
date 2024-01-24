
package com.abm.mainet.account.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.abm.mainet.account.dto.AccountBudgetAllocationBean;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author prasad.kancharla
 *
 */
public interface AccountBudgetAllocationService {

    AccountBudgetAllocationBean saveBudgetAllocationFormData(AccountBudgetAllocationBean tbAcBudgetAllocation, int LanguageId,
            Organisation Organisation) throws ParseException;

    List<AccountBudgetAllocationBean> findBudgetAllocationByFinYearId(Long faYearid, Long orgId);

    AccountBudgetAllocationBean getDetailsUsingBudgetAllocationId(AccountBudgetAllocationBean tbAcBudgetAllocation,
            int LanguageId, Organisation Organisation);

    public Boolean isBudgetAllocationEntryExists(Long faYearid, Long prRevBudgetCode, Long orgId);

    List<AccountBudgetAllocationBean> findByGridAllData(Long faYearid, Long cpdBugtypeId, Long dpDeptid, Long prBudgetCodeid,
            Long orgId);

    Map<String, String> findBudgetIdCodeFromBudgetAllocation(Long faYearid, Long fundId, Long functionId, Long cpdBugtypeId,
            Long prBudgetCodeid,
            Long dpDeptid, Long orgId);
}
