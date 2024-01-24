
package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.abm.mainet.account.dto.AccountBudgetoryRevisionBean;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author prasad.kancharla
 *
 */
public interface AccountBudgetoryRevisionService {

    public Boolean isBudgetoryRevisionEntryExists(Long faYearid, Long prRevBudgetCode, Long orgId);

    AccountBudgetoryRevisionBean saveBudgetoryRevisionFormData(AccountBudgetoryRevisionBean BudgetoryRevision, int LanguageId,
            Organisation org) throws ParseException;

    List<AccountBudgetoryRevisionBean> findByFinancialId(Long orgId);

    AccountBudgetoryRevisionBean getDetailsUsingBudgetoryRevisionId(AccountBudgetoryRevisionBean tbAcBudgetoryRevision,
            int languageId, Organisation org) throws ParseException;

    BigDecimal findBudgetgetRevisionActualTillNovFromDecAmountData(Long faYearid, Long budgCodeid, Long orgId) throws Exception;

    BigDecimal findBudgetgetRevisionActualTillNovFromDecAmountExpData(Long faYearid, Long budgCodeid, Long orgId)
            throws Exception;

    List<AccountBudgetoryRevisionBean> findByGridAllData(Long faYearid, Long cpdBugtypeId, Long dpDeptid, Long prBudgetCodeid,
            Long orgId);

    Map<String, String> findBudgetCodeIdCodeFromBudgetoryRevision(Long faYearid, Long fundId, Long functionId, Long cpdBugtypeId,
            Long prBudgetCodeid,
            Long dpDeptid, Long orgId);
}
