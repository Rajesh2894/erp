
package com.abm.mainet.account.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.abm.mainet.account.domain.AccountBudgetProjectedRevenueEntryEntity;
import com.abm.mainet.account.dto.AccountBudgetProjectedRevenueEntryBean;
import com.abm.mainet.account.dto.AccountBudgetProjectedRevenueEntryUploadDto;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author prasad.kancharla
 *
 */
public interface AccountBudgetProjectedRevenueEntryService {

    List<Object[]> findByRenueOrgAmount(Long faYearid, Long budgCodeid, Long orgId);

    List<AccountBudgetProjectedRevenueEntryBean> findByFinancialId(Long faYearid, Long orgId);

    AccountBudgetProjectedRevenueEntryBean saveBudgetProjectedRevenueEntryFormData(
            AccountBudgetProjectedRevenueEntryBean tbAcBudgetProjectedRevenueEntry, Organisation ordid, int langId);

    AccountBudgetProjectedRevenueEntryBean getDetailsUsingProjectionId(
            AccountBudgetProjectedRevenueEntryBean tbAcBudgetProjectedRevenueEntry, Long orgId);

    public Boolean isCombinationExists(Long faYearid, Long prBudgetCodeid, Long orgId,Long deptId, Long fieldId);

    public List<AccountBudgetProjectedRevenueEntryBean> getListOfAllFinacialYearDates(Long orgId, Long faYearid)
            throws ParseException;

    public List<Object[]> findBudgetCodeIdFromBudgetProjectedRevenueEntry(Long faYearid, Long cpdBugsubtypeId, Long dpDeptid,
            Long orgid);

    /**
     * @param valueOf
     * @param orgid
     * @return
     */
    List<Object[]> getBudgetCodeInRevenue(Long finYearId, long orgid);

    List<Object[]> getAllDepartmentIdsData(Long orgid);

    public String getAllCollectedAmount(Long faYearid, Long prBudgetCodeId, Long orgId);

    public Boolean isCombinationCheckTransactions(Long prProjectionId, Long faYearId, Long orgId);

    public Map<Long, String> findByAllRevBudgetHeads(Long orgId);
    
    public Map<Long, String> findByAllRevBudgetHeads(final Long orgId,final Long BudgetCodeId);

    List<AccountBudgetProjectedRevenueEntryBean> findBudgetProjectedRevenueEntrysByOrgId(Long orgId);

    List<Object[]> getAccountHeadCodeInRevenue(long orgid, Long activeStatusId);

    List<AccountBudgetProjectedRevenueEntryBean> findByGridAllData(Long faYearid, Long fundId, Long functionId,
            Long cpdBugsubtypeId, Long dpDeptid, Long prBudgetCodeid, Long fieldId,Long orgId);

    List<AccountBudgetProjectedRevenueEntryEntity> getBudgetProjectedRevenueEntry(long orgId);

    void saveBudgetProjectedRevenueEntryExportData(
            AccountBudgetProjectedRevenueEntryUploadDto accountBudgetProjectedRevenueEntryUploadDto, long orgId,
            int langId);
    
   public String getAllCollectedAmountByBasedOnDept(Long faYearid, final Long prBudgetCodeId, final Long orgId,final Long deptId);
	String getAllCollectedAmountByBasedOnDeptField(Long faYearid, Long prBudgetCodeId, Long orgId, Long deptId,
			Long fieldId);

	Map<Long, String> findByAllRevBudgetHeadsWithFieldId(Long orgId, Long BudgetCodeId, Long fieldId);

	Map<Long, String> findByAllRevBudgetHeadsFieldId(Long orgId, Long fieldId);


}
