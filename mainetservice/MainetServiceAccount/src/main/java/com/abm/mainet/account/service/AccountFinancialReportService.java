package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;
import org.springframework.ui.ModelMap;

import com.abm.mainet.account.domain.AccountBillEntryMasterEnitity;
import com.abm.mainet.account.domain.VoucherDetailViewEntity;
import com.abm.mainet.account.dto.AccountFinancialReportDTO;
import com.abm.mainet.account.dto.AccountFunctionWiseBudgetReportDto;
import com.abm.mainet.account.dto.AccountGrantMasterDto;
import com.abm.mainet.account.dto.AccountInvestmentMasterDto;
import com.abm.mainet.account.dto.AccountLoanReportDTO;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;

/**
 *
 * @author Vivek.Kumar
 * @since 09-Aug-2017
 */
public interface AccountFinancialReportService {

	List<Object[]> queryDataForReport(String vouDate, Long cpdIdPayMode, Long dpDeptid, Long orgId);

	List<VoucherDetailViewEntity> queryReportDataFromView(String fromDate, Long org, Long cpdIdPayMode, Long sacHeadId,
			String toDate,Long fieldId);

	List<VoucherDetailViewEntity> findReportDataForJournalRegister(String fromDate, Long orgId, Long superOrgId,
			String toDate, Long fieldId);

	List<VoucherDetailViewEntity> findReportDataForGeneralLedgerRegister(String fromDate, String toDate,
			Long accountHeadId, long orgId);


	void initializeOnReportTypeSelect(ModelMap model, String reportTypeCode, long orgId, int langId);

	void processReport(ModelMap model, String reportTypeCode, String transactionDate, String fromDate, String toDate,
			Long accountHeadId, long orgId, Long superOrgId, int langId, Long financialId, String transactionTypeCode,
			Long registerDepTypeId, String categoryId, Long paymodeId, Long vendorId,Long fieldId);

	List<Object[]> queryClassifiedBudgetReceiptSideReportData(Date fromDate, Date toDate, Long orgId,
			Long finanacialYearId);

	List<Object[]> queryClassifiedBudgetPaymentSideReportData(Date fromDate, Date toDate, Long orgId,
			Long finanacialYearId);

	Long queryToFindFinanacialYearID(Date fromDate);

	BigDecimal calculateOpeningBalanceAsOnDateForGeneralBankBook(Long cpdIdPayMode, Long orgId, String voPostingDate);

	void processGeneralBankBookReport(ModelMap model, Long orgId, String fromDate, Long accountHeadId,
			String reportTypeCode, String toDate,Long fieldId);

	AccountFinancialReportDTO mapJournelRegisterResultToDto(List<VoucherDetailViewEntity> resultList);

	List<AccountFinancialReportDTO> mapClassifiedBudgetReceiptSideReportDataToDTO(List<Object[]> receiptSideList,
			String reportTypeCode);

	List<AccountFinancialReportDTO> mapClassifiedBudgetPaymentSideReportDataToDTO(List<Object[]> paymentSideList,
			String reportTypeCode);

	void processRegisterOfDeposit(ModelMap model, String fromDate, String toDate, Long orgId, String reportTypeCode,
			Long accountHeadId, Long registerDepTypeId);

	void processIncomeAndExpenditure(ModelMap model, String fromDate, String toDate, Long orgId, String reportTypeCode,
			Long accountHeadId, int langId);

	List<Object[]> queryReportDataFromViewIncome(String fromDate, String toDate, Long orgId, int langId);

	List<Object[]> findDayBookFromVoucherDetailViewEntity(String fromDate, String toDate, long orgId);

	List<Object[]> findOpeningBalanceReport(Long orgId, Long financialYearId);

	String findFinancialYearByFinancialYearId(Long financialYearId);

	List<Object[]> findOpeningBalanceAmount(Long sacHeadId, Long orgId, Long financialYearId);

	List<Object[]> queryReportDataFromCashView(String transactionDate, Long org, Long accidcHeadId, Date fromDatess);

	List<Object[]> queryClassifiedBudgetReceiptSideReportDataL1(Date fromDate, Date toDate, Long orgId,
			Long finanacialYearId, Long codcofdetId);

	List<Object[]> queryClassifiedBudgetPaymentSideReportDataL1(Date fromDate, Date toDate, Long orgId,
			Long finanacialYearId, Long codcofdetId);

	List<Object[]> queryClassifiedBudgetReceiptSideOpeningReportDataL3(Date fromDate, Date toDate, Long orgId,
			Long codcofdetId);

	List<Object[]> queryClassifiedBudgetReceiptSideNonOpeningReportDataL3(Date fromDate, Date toDate, Long orgId,
			Long codcofdetId);

	List<Object[]> queryClassifiedBudgetPaymentSideOpeningReportDataL3(Date fromDate, Date toDate, Long orgId,
			Long codcofdetId);

	List<Object[]> queryClassifiedBudgetPaymentSideNonOpeningReportDataL3(Date fromDate, Date toDate, Long orgId,
			Long codcofdetId);

	List<Object[]> queryClassifiedBudgetReceiptSideReportDataL2(Date fromDate, Date toDate, Long orgId,
			Long finanacialYearId, Long codcofdetId);

	List<Object[]> queryClassifiedBudgetPaymentSideReportDataL2(Date fromDate, Date toDate, Long orgId,
			Long finanacialYearId, Long codcofdetId);

	List<Object[]> queryClassifiedBudgetReceiptSideReportDataL4(Date fromDate, Date toDate, Long orgId,
			Long finanacialYearId, Long codcofdetId);

	List<Object[]> queryClassifiedBudgetPaymentSideReportDataL4(Date fromDate, Date toDate, Long orgId,
			Long finanacialYearId, Long codcofdetId);

	void processGeneralLedgerReport(ModelMap model, AccountFinancialReportDTO dto, Long orgId);

	AccountFinancialReportDTO getAccountDashBoardReportDetails();

	AccountFinancialReportDTO getInterBankTransactionsReportData(String fromDateId, String toDateId, Long orgId);

	AccountFinancialReportDTO getQuarterlyBudgetVarianceReportData(Long financialYearId, Long orgId);

	AccountFinancialReportDTO getPaymentAndReceiptReportData(String fromDateId,String toDateId, Long orgid);

	AccountFinancialReportDTO getbudgetEstimationSheetsReport(Long financialYearId, Long deptId, Long functionId,
			String reportType,Long orgId);

	AccountFinancialReportDTO getAccountBudgetReceivableReportData(Long financialYearId, Long deptId, Long monthId,Long orgId);
	
	AccountLoanReportDTO loanReportData(String loanCode , Long orgId);
	//edit and add financial year id as well
	
	AccountInvestmentMasterDto getRegisterData(String invstNo, Long orgId);
	
	List<String>getInvestmentId(Long orgId);
	
	 Map<String, String> getGrantName(Long orgId);
	
	List<AccountGrantMasterDto> getGrantRegisterData(String grtName,Long faYearId, Long orgId);
	
	List<String>getLoanCode(Long orgId);
	
	//LOAN REGISTER
	AccountLoanReportDTO dataForRegister(Long orgid, String LoanCode);
	
	AccountBillEntryMasterEnitity  amountPaidData( Long orgid, Long lndetId);
	
	List<TbServiceReceiptMasEntity> receiptsForRegister( Long orgid, Long refId, String receiptTypeFlag);
	
	AccountFinancialReportDTO getbudgetEstimationConsolidationFormat(Long financialYearId, Long deptId,
			String reportType,Long orgId);

	List<AccountFunctionWiseBudgetReportDto> getFunctionWiseBugetReport(Long financialYearId, Long orgID);
	
	public void  getSecondaryHeadCodeDetail(final ModelMap model, final long orgId);

	List<String> getInvestmentIdFromFundId(Long orgId, Long fundId);

	List<Object[]> findReportDataForTrailBalance(String voPostingDate, String fromDate, String toDate, long orgId,
			Long faYearIds, Date beforeDate, Date afterDate);

	
}
