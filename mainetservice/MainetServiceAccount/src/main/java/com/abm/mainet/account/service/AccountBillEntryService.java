package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.abm.mainet.account.domain.AccountBillEntryMasterEnitity;
import com.abm.mainet.account.dto.AccountBillEntryMasterBean;
import com.abm.mainet.account.dto.VoucherReversalDTO;
import com.abm.mainet.account.rest.dto.VendorBillApprovalExtDTO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;

/**
 * @author tejas.kotekar
 *
 */
public interface AccountBillEntryService {

	/**
	 * @param billEntryBean
	 * @return
	 * @throws ParseException
	 */
	AccountBillEntryMasterEnitity createBillEntry(AccountBillEntryMasterBean billEntryBean) throws ParseException;

	/**
	 * @param orgId
	 * @return
	 */
	List<AccountBillEntryMasterEnitity> getBillEntryDetailsByOrgId(Long orgId);

	/**
	 * @param billNo
	 * @param billDate
	 * @param billAmount
	 * @param vendorId
	 * @return
	 */
	List<AccountBillEntryMasterEnitity> getBillEntryDetails(Long orgId, String fromDate, String toDate, String billNo,
			Long billType, Long vendorId, Long departmentId);

	/**
	 * @param orgId
	 * @param bmId
	 * @return
	 */
	AccountBillEntryMasterEnitity findBillEntryById(Long orgId, Long bmId);

	/**
	 * @param bmId
	 */
	void deleteBillEntryData(Long bmId);

	/**
	 * @param orgId
	 * @return
	 */
	List<AccountBillEntryMasterEnitity> getAllBillEntryData(Long orgId);

	String isMakerChecker(Organisation org);

	AccountBillEntryMasterEnitity createBillEntryWithMakerChecker(AccountBillEntryMasterBean billEntryBean,
			String ipMacAddress) throws ParseException;

	List<Object[]> getBillNumbers(Long orgId, Long billTypeId, Long vendorId, Date payDate);

	List<Object[]> getBillNumbers(Long orgId, Long vendorId, Date payDate);

	/**
	 * @param billId
	 * @param orgId
	 * @return
	 */
	List<AccountBillEntryMasterEnitity> getBillDataByBillId(Long billId, Long orgId);

	/**
	 * @param billEntryBean
	 */
	List<VendorBillApprovalDTO> getBillEntryDetailsForSearch(VendorBillApprovalDTO billApprovalDto);

	/**
	 * @param requestDTO
	 * @return
	 */
	String validate(VendorBillApprovalDTO billApprovalDto);

	/**
	 * @param billEntryBean
	 * @return
	 */
	VendorBillApprovalDTO getRecordForView(Long orgId, Long billId);

	/**
	 * @param l
	 * @param organisation
	 * @param m
	 * @return
	 */
	VendorBillApprovalDTO getRecordForEdit(Long orgId, Long billId);

	/**
	 * @param billApprovalDto
	 * @return
	 */
	String validateBillSearch(VendorBillApprovalDTO billApprovalDto);

	/**
	 * @param vendorBillApprovalDto
	 */
	VendorBillApprovalDTO saveBillApproval(VendorBillApprovalDTO vendorBillApprovalDto) throws Exception;

	/**
	 * @param dto
	 * @return
	 */
	String validateInputBeforeSave(VendorBillApprovalDTO dto);

	/**
	 * @param orgId
	 * @param organisation
	 * @param billId
	 * @return
	 */
	String validateForViewAndEdit(Long orgId, Long billId);

	/**
	 * @param orgId
	 * @param status
	 * @return
	 */
	String validateOrgIdAndLangId(Long orgId, Long status);

	/**
	 * @param organisation
	 * @return
	 */
	String validateGetDepartments(Long orgId);

	/**
	 * @param orgId
	 * @param languageId
	 * @return
	 */
	String validateOrgIdAndSuperOrgId(Long orgId, Long superOrgId);

	void reverseBillOrInvoice(List<String> transactionIds, VoucherReversalDTO dto, long fieldId, long orgId, int langId,
			String ipMacAddress);

	/**
	 * @param orgId
	 * @param budgetCodeId
	 * @param sanctionedAmount
	 * @return
	 */
	String validateViewDetails(Long orgId, Long budgetCodeId, BigDecimal sanctionedAmount);

	AccountBillEntryMasterBean populateBillEntryViewData(AccountBillEntryMasterBean dto, Long bmId, Long orgId);

	AccountBillEntryMasterBean populateBillEntryEditData(AccountBillEntryMasterBean dto, Long bmId, Long orgId);

	boolean isPaymentDateisExists(Long orgid, Date paymentDate);

	void forUpdateBillIdInToDepositEntryTable(AccountBillEntryMasterBean dto, AccountBillEntryMasterEnitity entity)
			throws ParseException;

	String findAccountHeadCodeBySacHeadId(Long sacHeadId, Long orgId);

	Long getVendorSacHeadIdByVendorId(Long vendorId, long orgid);

	AccountBudgetCodeEntity findBudgetHeadIdByUsingSacHeadId(Long sacHeadId, Long orgId);

	BigDecimal findPaymentAmount(Long billId, Long orgId);

	AccountBillEntryMasterBean populateBillEntryWorkFlowData(AccountBillEntryMasterBean accountBillEntryBean,
			String appNo, Long orgId, Long actualTaskId);

	void updateUploadInvoiceDeletedRecords(List<Long> removeFileById, Long updatedBy);

	String validateViewBudgetInputDetails(VendorBillApprovalDTO vendorApprovalDto);

	Long getTotalBillAmountByIntRefIdAndBillTypeId(Long intRefId, Long billTypeId, Long orgId);

	List<AccountBillEntryMasterBean> getBillDetailsByIntRefIdAndOrgId(Long intRefId, Long orgId);

	VendorBillApprovalDTO convertExternaDtoToInternaDto(VendorBillApprovalExtDTO dto, List<String> ValidateData);

	List<String> ValidateExternalRequest(VendorBillApprovalExtDTO dto);
	
    List<AccountBillEntryMasterEnitity> getAllPendingPaymentBillEntryData(Long orgId);

	List<Object[]> getBillNumbersWithFieldId(Long orgId, Long billTypeId, Long vendorId, Date payDate, Long fieldId);

}
