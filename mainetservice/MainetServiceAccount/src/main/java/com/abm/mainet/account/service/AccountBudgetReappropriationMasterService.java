
package com.abm.mainet.account.service;

import java.text.ParseException;
import java.util.List;

import com.abm.mainet.account.dto.AccountBudgetReappropriationMasterBean;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author prasad.kancharla
 *
 */
public interface AccountBudgetReappropriationMasterService {

    List<AccountBudgetReappropriationMasterBean> findByFinancialId(Long faYearid, String budgIdentifyFlag, Long orgId);

    AccountBudgetReappropriationMasterBean saveBudgetReappropriationFormData(
            AccountBudgetReappropriationMasterBean tbAcBudgetReappropriation, int LanguageId, Organisation Organisation)
            throws ParseException;

    AccountBudgetReappropriationMasterBean getDetailsUsingBudgetReappropriationId(
            AccountBudgetReappropriationMasterBean tbAcBudgetReappropriation, int LanguageId, Organisation Organisation);

    List<AccountBudgetReappropriationMasterBean> findByGridAllData(Long faYearid, Long cpdBugtypeId, Long dpDeptid,
            Long prBudgetCodeid, String budgIdentifyFlag, final Long fieldId, Long orgId);

    List<AccountBudgetReappropriationMasterBean> findBudgetReappropriationMastersByOrgId(Long orgId, String budgIdentifyFlag);
    
    void updateUploadReappropriationDeletedRecords(List<Long> removeFileById, Long updatedBy);

}
