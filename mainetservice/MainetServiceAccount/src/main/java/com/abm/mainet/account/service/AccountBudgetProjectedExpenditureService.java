
package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.abm.mainet.account.domain.AccountBudgetProjectedExpenditureEntity;
import com.abm.mainet.account.dto.AccountBudgetProjectedExpenditureBean;
import com.abm.mainet.account.dto.AccountBudgetProjectedExpenditureUploadDto;
import com.abm.mainet.account.dto.AccountIncomeAndExpenditureDto;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;

/**
 * @author prasad.kancharla
 *
 */
public interface AccountBudgetProjectedExpenditureService {

    List<AccountBudgetProjectedExpenditureBean> findBudgetProjectedExpenditureByFinId(Long faYearid, Long orgId);

    AccountBudgetProjectedExpenditureBean saveBudgetProjectedExpenditureFormData(
            AccountBudgetProjectedExpenditureBean tbAcBudgetProjectedExpenditure, Organisation orgid, int langId);

    AccountBudgetProjectedExpenditureBean getDetailsUsingProjectionId(
            AccountBudgetProjectedExpenditureBean tbAcBudgetProjectedExpenditure, Long orgId);

    public Boolean isBudgeExpAlreadyEntered(Long faYearid, Long prBudgetCodeid, Long orgId,Long deptId, Long fieldId);

    List<Object[]> findByExpOrgAmount(Long faYearid, Long budgCodeid, Long orgId);

    public List<AccountBudgetProjectedExpenditureBean> getListOfAllFinacialYearDates(Long orgId, Long faYearid)
            throws ParseException;

    public List<Object[]> findBudgetProjExpBudgetCodes(Long faYearid, Long cpdBugsubtypeId, Long dpDeptid, Long orgid);

    List<Object[]> getBudgetProjExpDeptIds(Long orgid);

    public String getAllExpenditureAmount(Long faYearid, Long prBudgetCodeId, Long orgId);

    public Boolean isCombinationCheckTransactions(Long prExpenditureId, Long faYearId, Long orgId);

    Map<Long, String> findByAllExpBudgetHeads(Long dpDeptid, Long orgId);

    public Map<Long, String> findByAllExpBudgetHeads(Long orgId);

    List<AccountBudgetProjectedExpenditureBean> findAllBudgetProjectedExpenditureByOrgId(Long orgId);

    public VendorBillApprovalDTO viewExpenditureDetails(final Long orgId, final Long budgetCodeId,
            final BigDecimal sanctionedAmount);

    /**
     * @param orgId
     * @param finYearId
     * @return
     */
    List<AccountBudgetProjectedExpenditureBean> findExpenditureDataByFinYearId(Long orgId, Long finYearId);

    /**
     * @param orgId
     * @param budgetCode
     * @param finYearId
     */
    Long getDepartmentFromBudgetProjectedExpenditureByBudgetCode(Long orgId, Long budgetCode, Long finYearId);

    public VendorBillApprovalDTO getExpenditureDetails(final Long orgId, final Long languageId);

    List<AccountBudgetProjectedExpenditureEntity> getExpenditureDetailsForBillEntryFormView(Long orgId, Long finYearId,
            Long sacHeadId);

    List<AccountBudgetProjectedExpenditureEntity> getExpenditureDetailsForPaymentEntryFormView(Long orgId, Long finYearId,
            Long billId,Long bmId);

    List<AccountBudgetProjectedExpenditureEntity> getExpenditureDetailsForDirectPaymentEntryFormView(Long orgId, Long finYearId,
            Long budgetCodeId);

    List<Object[]> findBudgetProjectedExpenditureByBudgetCode(Long budgCodeid, Long orgId);

    List<Object[]> findBudgetProjectedExpenditureByBudgetCodes(Long budgCodeid, Long orgId, Long yearId);

    List<AccountBudgetProjectedExpenditureEntity> getBudgetProjectedExpenditure(Long orgId);

    void saveBudgetProjectedExpenditureExportData(
            AccountBudgetProjectedExpenditureUploadDto accountBudgetProjectedExpenditureUploadDto, long orgId,
            int langId);

    boolean getBudgetFlagExists(Long orgId, Long budgetCodeId);

    List<AccountBudgetProjectedExpenditureBean> findByGridAllData(Long faYearid, Long fundId, Long functionId,
            Long cpdBugsubtypeId, Long dpDeptid, Long prBudgetCodeid,Long fieldId ,Long orgId);

    VendorBillApprovalDTO viewInvoiceSalaryBillBudgetDetails(VendorBillApprovalDTO vendorApprovalDto);

    AccountIncomeAndExpenditureDto getIncomeAndExpenditureReportData(Long orgId, Long primaryAcHeadId, Long faYearId,
            String type);

    AccountIncomeAndExpenditureDto getAssetsAndLiabilitiesData(Long orgId, Long primaryAcHeadId, Long faYearId, String type);

    List<Object[]> queryforPrimaryHead();
    
    public Map<Long, String> findByAllExpBudgetHeadsByBudgetCodeID(final Long orgId,final Long budgetCodeId); 
    
    public String getAllExpenditureAmountByDeptId(Long faYearid, final Long prBudgetCodeId, final Long orgId ,final Long deptId);
    
    public String getAllAcrualExpenditureAmount(Long faYearid, final Long prBudgetCodeId, final Long orgId,Long deptId);

	List<AccountBudgetProjectedExpenditureEntity> getExpenditureDetailsForBillEntryFormViewWithFieldId(Long orgId,
			Long finYearId, Long sacHeadId, Long fieldId);

	List<AccountBudgetProjectedExpenditureEntity> getExpenditureDetailsForPaymentEntryFormViewWithFieldId(Long orgId,
			Long finYearId, Long billId, Long bmId, Long fieldId);

	String getAllAcrualExpenditureAmountFieldId(Long faYearid, Long prBudgetCodeId, Long orgId, Long deptId,
			Long fieldId);

	String getAllExpenditureAmountByDeptIdFieldId(Long faYearid, Long prBudgetCodeId, Long orgId, Long deptId,
			Long fieldId);

	Map<Long, String> findByAllExpBudgetHeadsByBudgetCodeIDFieldId(Long orgId, Long budgetCodeId, Long fieldId);

	Map<Long, String> findByAllExpBudgetHeadsFieldId(Long orgId, Long fieldId);

	List<Object[]> getExpenditureDetails(Long orgId, Long finYearId, Long sacHeadId,
			Long fieldId, Long deptId);

	VendorBillApprovalDTO viewCouncilBillBudgetDetails(VendorBillApprovalDTO vendorApprovalDto);

    
}
