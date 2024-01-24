
package com.abm.mainet.account.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.account.dto.AccountBudgetCodeUploadDto;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountBudgetCodeBean;
import com.abm.mainet.common.integration.acccount.dto.SecondaryheadMaster;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;

/**
 * @author prasad.kancharla
 *
 */
public interface BudgetCodeService {

    public Boolean isCombinationExists(Long dpDeptid, Long fundId, Long functionId, Long fieldId, Long sacId, Long orgId,
            String objectHeadType);

    AccountBudgetCodeBean saveBudgetCodeFormData(AccountBudgetCodeBean tbAcBudgetCode, Organisation ordid, int langId);

    AccountBudgetCodeBean getDetailsUsingPrBudgetCodeId(AccountBudgetCodeBean tbAcBudgetCode, Long orgId,
            String primaryStatusLookUpCode);

    List<SecondaryheadMaster> findByAllGridData(Long orgId);

    List<Object[]> getBudgetHeads(Long superOrgId, Long orgId, Long cpdIdHeadType);

    List<Object[]> getTDSDeductionBudgetHeads(Long superOrgId, Long orgId);

    List<Object[]> getExpenditutreBudgetHeads(Long orgId, Long budgetCodeId);

    public Map<Long, String> finAllBudgetCodeByOrg(Long orgid);

    public Map<Long, String> finAllBudgetCodeByOrganization(Long orgid);

    public Map<Long, String> findByAllRevBudgetHeads(Long faYearid, Long cpdBugtypeid, Long cpdBugsubtypeId, Long dpDeptid,
            Organisation organisation, int langId);

    public Map<Long, String> findByAllRevObjectTypeBudgetHeads(Long faYearid, Long cpdBugtypeid, Long cpdBugsubtypeId,
            Long dpDeptid, Organisation organisation, int langId);

    public Map<Long, String> findByAllExpBudgetHeads(Long faYearid, Long cpdBugtypeid, Long cpdBugsubtypeId, Long dpDeptid,
            Organisation organisation, int langId);

    public Map<Long, String> findByAllExpObjectTypeBudgetHeads(Long faYearid, Long cpdBugtypeid, Long cpdBugsubtypeId,
            Long dpDeptid, Organisation organisation, int langId);

    public String getBudgetCode(Long prBudgetCodeid, Long orgId);

    List<AccountBudgetCodeBean> findByAllGridSearchData(Long dpDeptid, Long fundId, Long fieldId, Long functionId, Long sacHeadId,
            String cpdIdStatusFlag, Long orgId, String objectHeadType);

    List<Object[]> getFundCode(Long prBudgetCodeId, Long orgId);

    List<Object[]> getFunctionCode(Long prBudgetCodeId, Long orgId);

    public Map<String, String> getAllBudgetCodes(Long prBudgetCodeid, Long orgId);

    public List<Object[]> getJournalBudgetCode(long orgid);

    public List<Object[]> getVoucherAccountHead(Long orgId, Long cpdId1, Long cpdId2, Long activeStatusId);

    Long getBudgetCodeIdForPayMode(Long cpdIdPayMode, Long orgId);

    Long getBankBudgetCodeIdByAccountId(Long bankAccountId, Long orgId);

    public List<Object[]> findSacBudgetHeadIdDescAllData(long orgId);

    List<String> findBudgetCodeDetails(Long budgetCodeId, Long orgId);

    List<Object[]> findBudgetCode(Long budgetCodeId, Long orgId);

    String findAccountHeadCodeBySacHeadId(Long sacHeadId, Long orgId);

    List<Object[]> findAccountHeadCode(Long budgetCodeId, Long orgId);

    public VendorBillApprovalDTO geDeductionDetails(final Long orgId, final Long superOrgId);

    public void saveBudgetHeadExportData(AccountBudgetCodeUploadDto accountBudgetCodeUploadDto,
            Organisation defaultOrg, int langId, String primaryHeadFlag, String objectHeadFlag);

    public List<AccountBudgetCodeEntity> getBudgetHeadCodes(long orgid, String cpdIdStatusFlag);

    public Map<Long, String> findByAllExpFieldBudgetHeads(Long faYearid, Long cpdBugtypeid, Long cpdBugsubtypeId,
            Long dpDeptid, Organisation organisation, int langId, Long fieldId);

    public Map<Long, String> findByAllExpObjectTypeFieldBudgetHeads(Long faYearid, Long cpdBugtypeid,
            Long cpdBugsubtypeId, Long dpDeptid, Organisation organisation, int langId, Long fieldId);

    public Map<Long, String> findByAllExpFunctionBudgetHeads(Long faYearid, Long cpdBugtypeid, Long cpdBugsubtypeId,
            Long dpDeptid, Organisation organisation, int langId, Long functionId);

    public Map<Long, String> findByAllExpObjectTypeFunctionBudgetHeads(Long faYearid, Long cpdBugtypeid,
            Long cpdBugsubtypeId, Long dpDeptid, Organisation organisation, int langId, Long functionId);

    public Map<Long, String> findByAllRevFieldBudgetHeads(Long faYearid, Long cpdBugtypeid, Long cpdBugsubtypeId,
            Long dpDeptid, Organisation organisation, int langId, Long fieldId);

    public Map<Long, String> findByAllRevObjectTypeFieldBudgetHeads(Long faYearid, Long cpdBugtypeid,
            Long cpdBugsubtypeId, Long dpDeptid, Organisation organisation, int langId, Long fieldId);

    public Map<Long, String> findByAllRevFunctionBudgetHeads(Long faYearid, Long cpdBugtypeid, Long cpdBugsubtypeId,
            Long dpDeptid, Organisation organisation, int langId, Long functionId);

    public Map<Long, String> findByAllRevObjectTypeFunctionBudgetHeads(Long faYearid, Long cpdBugtypeid,
            Long cpdBugsubtypeId, Long dpDeptid, Organisation organisation, int langId, Long functionId);

    AccountBudgetCodeEntity findBudgetHeadIdBySacHeadId(Long sacHeadId, Long orgId);
    
    
    public Long getBudgetAccTypeByBudgetCodeId(Long orgId, Long BudgetCodeId);

	List<Object[]> findByAllObjectBudgetHeadId(Long orgId, Long finYearId, Long dpDeptid, Long fieldId);

	List<Object[]> getSecondaryHeadcodesWithField(Long orgId, Long fieldId);

	List<AccountHeadSecondaryAccountCodeMasterEntity> getSecondaryHeadcodesWithDeptField(Long orgId, Long dpDeptId,
			Long fieldId);

	List<AccountHeadSecondaryAccountCodeMasterEntity> getSecondaryHeadcodesField(Long orgId, Long fieldId);

	List<Object[]> getSecondaryHeadcodesDeptField(Long orgId, Long dpDeptId, Long fieldId);

	public String getBudgetCodes(Long prBudgetCodeid, Long orgId);

}
