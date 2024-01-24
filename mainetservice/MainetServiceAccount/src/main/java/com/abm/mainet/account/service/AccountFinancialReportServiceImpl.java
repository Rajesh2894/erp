package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.persistence.NonUniqueResultException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.abm.mainet.account.dao.AccountFinanceReportDAO;
import com.abm.mainet.account.domain.AccountBillEntryMasterEnitity;
import com.abm.mainet.account.domain.AccountGrantMasterEntity;
import com.abm.mainet.account.domain.AccountInvestmentMasterEntity;
import com.abm.mainet.account.domain.VoucherDetailViewEntity;
import com.abm.mainet.account.domain.VoucherTemplateDetailEntity;
import com.abm.mainet.account.domain.VoucherTemplateMasterEntity;
import com.abm.mainet.account.dto.AccountBillEntryMasterBean;
import com.abm.mainet.account.dto.AccountBillEntryMasterHistDto;
import com.abm.mainet.account.dto.AccountCollectionSummaryDTO;
import com.abm.mainet.account.dto.AccountFinancialReportDTO;
import com.abm.mainet.account.dto.AccountFunctionWiseBudgetReportDto;
import com.abm.mainet.account.dto.AccountGrantMasterDto;
import com.abm.mainet.account.dto.AccountInvestmentMasterDto;
import com.abm.mainet.account.dto.AccountLoanDetDto;
import com.abm.mainet.account.dto.AccountLoanReportDTO;
import com.abm.mainet.account.dto.SecondarySecondaryHeadCodeDTO;
import com.abm.mainet.account.repository.AccountCollectionSummaryReportJpaRepository;
import com.abm.mainet.account.repository.AccountFinancialReportRepository;
import com.abm.mainet.account.repository.ContraEntryVoucherRepository;
import com.abm.mainet.account.repository.VoucherTemplateRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountFinancialReport;
import com.abm.mainet.common.constant.MainetConstants.ReportType;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountReceiptDTO;
import com.abm.mainet.common.integration.acccount.repository.SecondaryheadMasterJpaRepository;
import com.abm.mainet.common.integration.acccount.repository.TbAcCodingstructureMasJpaRepository;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountFunctionMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.master.repository.EmployeeJpaRepository;
import com.abm.mainet.common.master.repository.TbFinancialyearJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 *
 * @author Vivek.Kumar
 * @since 09-Aug-2017
 */
@Service
public class AccountFinancialReportServiceImpl implements AccountFinancialReportService {

	private static final String REPORT_TYPE = "reportType";
	private static final String BANK_LIST = "bankList";
	private static final String AC_HEAD_LOOKUPS = "acHeadLookUps";
	private static final String VALIDATION_ERROR = "validationError";
	private static final String REPORT_LIST = "reportList";
	private static final String REPORT_DATA = "reportData";
	private static final String TRANSACTION_TYPE_LOOKUPS = "transactionTypeLookUps";
	private static final String VOUCHER_DATE_CANNOT_BE_NULL = "voucherDate cannot be null or empty [vouDate=";
	private static final String AC_Financialyr_LOOKUPS = "aFinancialYr";
	private static final String VENDOR_LIST = "vendorList";
	private static final String GCB = "GCB";
	private static final String JRB = "JRB";
	private static final String GLR = "GLR";
	private static final String TBR = "TBR";
	private static final String CAR = "CAR";
	private static final String RPR = AccountFinancialReport.RPR2;
	private static final String INE = "INE";
	private static final String BSR = "BSR";
	private static final String GBB = "GBB";
	private static final String RDP = "RDP";
	private static final String BRS = "BRS";
	private static final String SDR = "SDR";
	private static final String BER = "BER";
	private static final String DYB = "DYB";
	private static final String CAE = "CAE";
	private static final String RAS = "RAS";
	private static final String OBS = "OBS";
	private static final String PCR = "PCR";
	private static final String DCR = "DCR";
	private static final String BAS = "BAS";
	private static final String CCR = "CCR";
	private static final String TRR = "TRR";
	private static final String EBS = "EBS";
	private static final String RBS = "RBS";
	private static final String CRR = "CRR";
	private static final String CCN = "CCN";
	private static final String CDR = "CDR";
	private static final String CSR = "CSR";
	private static final String TFC = "TFC";
	private static final String ATR = "ATR";
	private static final String CFS = "CFS";

	private static final Logger LOGGER = Logger.getLogger(AccountFinancialReportServiceImpl.class);
	@Resource
	private AccountFinancialReportRepository accountFinancialReportRepository;
	@Resource
	private TbDepartmentService departmentService;
	@Resource
	private SecondaryheadMasterService secondaryheadMasterService;
	@Resource
	private TbAcCodingstructureMasService acCodingstructureMasService;
	@Resource
	private BudgetCodeService budgetCodeService;
	@Autowired
	@Resource
	private TbFinancialyearJpaRepository tbFinancialyearJpaRepository;
	@Resource
	private SecondaryheadMasterJpaRepository tbAcSecondaryheadMasterJpaRepository;
	@Resource
	private EmployeeJpaRepository employeeJpaRepository;
	@Resource
	private VoucherTemplateRepository voucherTemplateRepository;
	@Resource
	AccountCollectionSummaryReportJpaRepository accountCollectionSummaryReportJpaRepository;
	@Resource
	private TbAcVendormasterService vendorMasterService;
	@Resource
	private TbDepartmentService deparmentService;
	@Resource
	private ContraEntryVoucherRepository contraEntryVoucherRepository;
	@Resource
	private AccountChequeDishonourService accountChequeDishonourService;
	@Resource
	private AccountFieldMasterService tbAcFieldMasterService;
	@Resource
	private AccountFunctionMasterService tbAcFunctionMasterService;
	@Resource
	private TbOrganisationService tbOrganisationService;
	@Resource
	private DepartmentService deptService;
	@Resource
	private AccountBudgetProjectedExpenditureService accountBudgetProjectedExpenditureService;
	
	@Resource
	private TbAcCodingstructureMasJpaRepository tbAcCodingstructureMasJpaRepository;
	@Autowired
	private AccountFinanceReportDAO accountFinanceReportDAO;
	@Override
	@Transactional(readOnly = true)
	public List<Object[]> queryDataForReport(final String vouDate, final Long cpdIdPayMode, final Long dpDeptid,
			final Long orgId) {

		if ((vouDate == null) || vouDate.isEmpty()) {
			throw new IllegalArgumentException(VOUCHER_DATE_CANNOT_BE_NULL + vouDate + "]");
		}
		final Date voucherDate = Utility.stringToDate(vouDate);

		return accountFinancialReportRepository.queryDataForReport(voucherDate, AccountConstants.Y.getValue(), orgId,
				cpdIdPayMode, MainetConstants.CommonConstants.ACTIVE);
	}

	private List<AccountFinancialReportDTO> mapResultsToDTO(final List<VoucherDetailViewEntity> resultList,
			final AccountFinancialReportDTO result, String reportTypeCode) {

		final List<AccountFinancialReportDTO> reportList = new ArrayList<>();

		BigDecimal tempOpeningBalance = null;
		for (final VoucherDetailViewEntity viewEntity : resultList) {
			AccountFinancialReportDTO report = new AccountFinancialReportDTO();
			if (result == null) {
				report = mapResultParam(viewEntity, report, null, reportTypeCode);
			} else {
				if (tempOpeningBalance == null) {
					report = mapResultParam(viewEntity, report, result.getOpeningBalance(), reportTypeCode);
				} else {
					report = mapResultParam(viewEntity, report, tempOpeningBalance, reportTypeCode);
				}
				tempOpeningBalance = report.getClosingBalance();
			}

			reportList.add(report);

		}
		return reportList;
	}

	private AccountFinancialReportDTO mapResultParam(final VoucherDetailViewEntity viewEntity,
			final AccountFinancialReportDTO report, final BigDecimal openBalance, String reportTypeCode) {

		report.setVoucherNo(viewEntity.getVouNo());
		if (viewEntity.getVoPostingDate() != null) {
			report.setVoucherDate(Utility.dateToString(viewEntity.getVoPostingDate()));
		}
		report.setAccountCode(null);
		report.setAccountHead(viewEntity.getAcHeadCode());
		report.setPayerPayee(viewEntity.getPayerPayee());
		report.setParticular(viewEntity.getParticulars());

		if (reportTypeCode.equals(GBB)) {
			setDrCrAmountBankBook(report, viewEntity);
		} else {
			setDrCrAmountCashBook(report, viewEntity);

		}

		if (openBalance != null) {
			report.setClosingBalance(
					calculateClosingBalAsOnDate(openBalance, report.getDrAmount(), report.getCrAmount()));
		}

		return report;
	}

	private void setDrCrAmountBankBook(final AccountFinancialReportDTO report,
			final VoucherDetailViewEntity viewEntity) {

		report.setDrAmount(viewEntity.getCrAmount().setScale(2));
		report.setCrAmount(viewEntity.getDrAmount().setScale(2));

	}

	private void setDrCrAmountCashBook(final AccountFinancialReportDTO report,
			final VoucherDetailViewEntity viewEntity) {

		report.setDrAmount(viewEntity.getCrAmount().setScale(2));
		report.setCrAmount(viewEntity.getDrAmount().setScale(2));
	}

	private BigDecimal calculateClosingBalAsOnDate(final BigDecimal openBalance, final BigDecimal drAmount,
			final BigDecimal crAmount) {
		BigDecimal closingBalance = BigDecimal.ZERO.setScale(2);
		if (openBalance != null) {
			closingBalance = closingBalance.add(openBalance);
		}
		if (drAmount != null
				&& !drAmount.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
			closingBalance = closingBalance.add(drAmount);
		}
		if (crAmount != null
				&& !crAmount.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
			closingBalance = closingBalance.subtract(crAmount);
		}
		return closingBalance;
	}

	private BigDecimal calculateClosingBalTranAsOnDate(final BigDecimal openBalance, final BigDecimal drAmount,
			final BigDecimal crAmount) {
		BigDecimal closingBalance = BigDecimal.ZERO.setScale(2);
		if (openBalance != null) {
			closingBalance = closingBalance.add(openBalance);
		}
		if (drAmount != null
				&& !drAmount.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
			closingBalance = closingBalance.add(drAmount);
		}
		if (crAmount != null
				&& !crAmount.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
			closingBalance = crAmount.subtract(closingBalance);
		}
		return closingBalance;
	}

	private BigDecimal calculateClosingBalAsOnDateTrailBalance(final BigDecimal openBalance, final BigDecimal drAmount,
			final BigDecimal crAmount) {
		BigDecimal closingBalance = BigDecimal.ZERO.setScale(2);
		if (openBalance != null) {
			closingBalance = closingBalance.add(openBalance);
		}
		if (drAmount != null
				&& !drAmount.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
			closingBalance = closingBalance.add(drAmount);
		}
		if (crAmount != null
				&& !crAmount.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
			closingBalance = closingBalance.subtract(crAmount);
		}

		/*
		 * if (closingBalance.signum() == -1 || closingBalance.equals(new
		 * BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
		 * closingBalance = null; }
		 */

		return closingBalance;
	}

	@Override
	@Transactional(readOnly = true)
	public List<VoucherDetailViewEntity> queryReportDataFromView(final String fromDate, final Long org,
			final Long cpdIdPayMode, Long sacHeadId, final String toDate,Long fieldId) {
		if ((fromDate == null) || fromDate.isEmpty() && (toDate == null) || toDate.isEmpty()) {
			throw new IllegalArgumentException("toDate and fromDate cannot be null or empty [toDate=" + toDate + "]"
					+ "[fromDate=" + toDate + "]");
		}
		final Date entryDate = Utility.stringToDate(fromDate);
		final Date outDate = Utility.stringToDate(toDate);
		return accountFinanceReportDAO.queryReportDataFromView(entryDate, org, sacHeadId, outDate,fieldId);
	}

	private BigDecimal calculateOpeningBalanceAsOnDate(final Long sacHeadId, final Long orgId,
			final String voPostingDate) {
		List<Object[]> result = null;
		Long financialYearId = tbFinancialyearJpaRepository
				.getFinanciaYearIdByFromDate(Utility.stringToDate(voPostingDate));
		if (financialYearId != null)
			if(sacHeadId>0) {
			result = accountFinancialReportRepository.queryOpenBalanceAmountAndCrDrType(sacHeadId, orgId,
					financialYearId);}
			else {
				result = accountFinancialReportRepository.queryOpenBalanceAmountAndCrDrTypeAll( orgId,
						financialYearId);
			}
		Date reportFromDate = Utility.stringToDate(voPostingDate);// intialize your date to any date
		int year1 = getYearFromDate(reportFromDate);
		String isDateFinancail = "01/04/" + (year1);
		Date finYearStartDate = Utility.stringToDate(isDateFinancail);// intialize your date to any date
		List<Object[]> drCrAmtList = null;
		if (!reportFromDate.equals(finYearStartDate)) {
			Date dateBefore = new Date(reportFromDate.getTime() - 1 * 24 * 3600 * 1000);
			Date financialYFromDate = tbFinancialyearJpaRepository
					.getFromDateFromFinancialYearIdByPassingDate(dateBefore);
			if(sacHeadId>0) {
			drCrAmtList = accountFinancialReportRepository.getSumOfCreditAndSumOfDebitFromVouchers(financialYFromDate,
					dateBefore, sacHeadId, orgId);
			}else {
				drCrAmtList = accountFinancialReportRepository.getSumOfCreditAndSumOfDebitFromVouchersAll(financialYFromDate,
						dateBefore, orgId);
				return openingBalanceAll(result, drCrAmtList, orgId);
			}
		}
		return openingBalance(result, drCrAmtList, orgId);
	}

	private BigDecimal calculateOpeningBalanceAsOnPreviousDate(final Long sacHeadId, final Long orgId,
			final String reportFrmDate) {

		Date reptFromDate = Utility.stringToDate(reportFrmDate);
		int fromDay = getDayFromDate(reptFromDate);
		int fromMonth = getMonthFromDate(reptFromDate);
		int fromYear = getYearFromDate(reptFromDate);
		String fromDates = fromDay + "/" + fromMonth + "/" + (fromYear - 1);

		List<Object[]> result = null;
		Long financialYearId = tbFinancialyearJpaRepository
				.getFinanciaYearIdByFromDate(Utility.stringToDate(fromDates));
		if (financialYearId != null)
			result = accountFinancialReportRepository.queryOpenBalanceAmountAndCrDrType(sacHeadId, orgId,
					financialYearId);
		Date reportFromDate = Utility.stringToDate(fromDates);// intialize your date to any date
		int year1 = getYearFromDate(reportFromDate);
		String isDateFinancail = "01/04/" + (year1);
		Date finYearStartDate = Utility.stringToDate(isDateFinancail);// intialize your date to any date
		List<Object[]> drCrAmtList = null;
		if (!reportFromDate.equals(finYearStartDate)) {
			Date dateBefore = new Date(reportFromDate.getTime() - 1 * 24 * 3600 * 1000);
			Date financialYFromDate = tbFinancialyearJpaRepository
					.getFromDateFromFinancialYearIdByPassingDate(dateBefore);
			drCrAmtList = accountFinancialReportRepository.getSumOfCreditAndSumOfDebitFromVouchers(financialYFromDate,
					dateBefore, sacHeadId, orgId);
		}
		return openingBalance(result, drCrAmtList, orgId);
	}
	private BigDecimal openingBalanceAll(final List<Object[]> result, final List<Object[]> drCrAmtList, Long orgId) {

		if (result.size() > 1) {
			throw new IllegalArgumentException(
					"Multiple Opening Balance Records Found Against Selected Type. and orgId is : " + orgId);
		}
		String openBalnaceAmt = null;
		Long drCrId = null;
		if ((result != null) && !result.isEmpty()) {
			final Object[] openBalAndCrDrArr = result.get(0);
			if (openBalAndCrDrArr[0] != null) {
				openBalnaceAmt = (String) openBalAndCrDrArr[0];
			}
			if (openBalAndCrDrArr[1] != null) {
				drCrId = (Long) openBalAndCrDrArr[1];
			}
		}
		String drCrCode = "";
		if (drCrId != null) {
			drCrCode = CommonMasterUtility.findLookUpDesc(PrefixConstants.DCR, orgId, drCrId);
		} else {
			drCrCode = "DR";
		}

		// sum total opening dr amount
		BigDecimal drSum = BigDecimal.ZERO.setScale(2);
		if (drCrCode.equals("DR")) {
			if ((openBalnaceAmt != null) && !openBalnaceAmt.isEmpty()) {
				drSum = drSum.add(new BigDecimal(openBalnaceAmt));
			}
		}

		// sum total opening Cr amount
		BigDecimal crSum = BigDecimal.ZERO.setScale(2);
		if (drCrCode.equals("CR")) {
			if ((openBalnaceAmt != null) && !openBalnaceAmt.isEmpty()) {
				crSum = crSum.add(new BigDecimal(openBalnaceAmt));
			}
		}
		// TransactionDR And Cr Amount

		BigDecimal drAmount = BigDecimal.ZERO.setScale(2);
		BigDecimal crAmount = BigDecimal.ZERO.setScale(2);
		BigDecimal txCrSum = BigDecimal.ZERO.setScale(2);
		BigDecimal txDrSum = BigDecimal.ZERO.setScale(2);
		if ((drCrAmtList != null) && !drCrAmtList.isEmpty()) {
			final Object[] drCrAmtArr = drCrAmtList.get(0);
			Objects.nonNull(drCrAmtArr[0]);
			if (drCrAmtArr[0] != null) {
				drAmount = new BigDecimal(drCrAmtArr[0].toString());
				txDrSum = txDrSum.add(drAmount);

			}
			if (drCrAmtArr[1] != null) {
				crAmount = new BigDecimal(drCrAmtArr[1].toString());
				txCrSum = txCrSum.add(crAmount);
			}
		}
		BigDecimal totalDr = BigDecimal.ZERO.setScale(2);
		if (drCrCode.equals("DR") || drCrCode.equals("CR")) {
			totalDr = totalDr.add(drSum).add(txDrSum);
		}
		BigDecimal totalCr = BigDecimal.ZERO.setScale(2);
		if (drCrCode.equals("DR") || drCrCode.equals("CR")) {
			totalCr = totalCr.add(crSum).add(txCrSum);
		}
		BigDecimal finalAmount = BigDecimal.ZERO.setScale(2);
		if (drCrCode.equals("DR") || drCrCode.equals("CR")) {
			finalAmount = totalDr.subtract(totalCr);
		}

		return finalAmount;
	}

	private BigDecimal openingBalance(final List<Object[]> result1, final List<Object[]> drCrAmtList, Long orgId) {
		BigDecimal finalAmount = BigDecimal.ZERO.setScale(2);
		for(Object[] result:result1) {
		String openBalnaceAmt = null;
		Long drCrId = null;
		int i=0;
		if ((result != null)) {
			final Object[] openBalAndCrDrArr = result;
			if (openBalAndCrDrArr[0] != null) {
				openBalnaceAmt = (String) openBalAndCrDrArr[0];
			}
			if (openBalAndCrDrArr[1] != null) {
				drCrId = (Long) openBalAndCrDrArr[1];
			}
		}
		String drCrCode = "";
		if (drCrId != null) {
			drCrCode = CommonMasterUtility.findLookUpDesc(PrefixConstants.DCR, orgId, drCrId);
		} else {
			drCrCode = "DR";
		}

		// sum total opening dr amount
		BigDecimal drSum = BigDecimal.ZERO.setScale(2);
		if (drCrCode.equals("DR")) {
			if ((openBalnaceAmt != null) && !openBalnaceAmt.isEmpty()) {
				drSum = drSum.add(new BigDecimal(openBalnaceAmt));
			}
		}

		// sum total opening Cr amount
		BigDecimal crSum = BigDecimal.ZERO.setScale(2);
		if (drCrCode.equals("CR")) {
			if ((openBalnaceAmt != null) && !openBalnaceAmt.isEmpty()) {
				crSum = crSum.add(new BigDecimal(openBalnaceAmt));
			}
		}
		// TransactionDR And Cr Amount

		BigDecimal drAmount = BigDecimal.ZERO.setScale(2);
		BigDecimal crAmount = BigDecimal.ZERO.setScale(2);
		BigDecimal txCrSum = BigDecimal.ZERO.setScale(2);
		BigDecimal txDrSum = BigDecimal.ZERO.setScale(2);
		if ((drCrAmtList != null) && !drCrAmtList.isEmpty()) {
			final Object[] drCrAmtArr = drCrAmtList.get(0);
			Objects.nonNull(drCrAmtArr[0]);
			if (drCrAmtArr[0] != null) {
				drAmount = new BigDecimal(drCrAmtArr[0].toString());
				txDrSum = txDrSum.add(drAmount);

			}
			if (drCrAmtArr[1] != null) {
				crAmount = new BigDecimal(drCrAmtArr[1].toString());
				txCrSum = txCrSum.add(crAmount);
			}
		}
		BigDecimal totalDr = BigDecimal.ZERO.setScale(2);
		if (drCrCode.equals("DR") || drCrCode.equals("CR")) {
			totalDr = totalDr.add(drSum).add(txDrSum);
		}
		BigDecimal totalCr = BigDecimal.ZERO.setScale(2);
		if (drCrCode.equals("DR") || drCrCode.equals("CR")) {
			totalCr = totalCr.add(crSum).add(txCrSum);
		}
		if (drCrCode.equals("DR") || drCrCode.equals("CR")) {
			finalAmount = totalDr.subtract(totalCr);
		}
		}
		return finalAmount;
	}

	@Override
	@Transactional(readOnly = true)
	public List<VoucherDetailViewEntity> findReportDataForJournalRegister(final String fromDate, final Long orgId,
			final Long superOrgId, String toDate,Long fieldId) {
		List<VoucherDetailViewEntity> voTDetlist =new ArrayList<>();
	     if(fieldId!=null && fieldId>0) {
	    	 voTDetlist= accountFinancialReportRepository.queryJournalRegisterDataFromViewByFieldId(
					CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.JV.getValue(),
							AccountPrefix.VOT.toString(), orgId),
					orgId, Utility.stringToDate(fromDate), Utility.stringToDate(toDate),fieldId);
	     }else {
	    	 voTDetlist= accountFinancialReportRepository.queryJournalRegisterDataFromView(
	 				CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.JV.getValue(),
	 						AccountPrefix.VOT.toString(), orgId),
	 				orgId, Utility.stringToDate(fromDate), Utility.stringToDate(toDate)); 
	     }
			if (voTDetlist == null || voTDetlist.isEmpty()) {
				List<VoucherDetailViewEntity> voTDet1list = accountFinancialReportRepository
						.queryJournalRegisterDataFromView(
								CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.JV.getValue(),
										AccountPrefix.VOT.toString(), orgId),
								superOrgId, Utility.stringToDate(fromDate), Utility.stringToDate(toDate));
				return voTDet1list;
			} else {
				return voTDetlist;
			}
		}

	@Override
	@Transactional(readOnly = true)
	public List<VoucherDetailViewEntity> findReportDataForGeneralLedgerRegister(final String fromDate,
			final String toDate, final Long accountHeadId, final long orgId) {
		final List<VoucherDetailViewEntity> list = new ArrayList<>();
		List<Object[]> resultList=  accountFinancialReportRepository.queryForGeneralLedgerDataFromView(Utility.stringToDate(fromDate),
				Utility.stringToDate(toDate), accountHeadId, orgId);
		for (final Object[] viewEntity : resultList) {
			VoucherDetailViewEntity entity = new VoucherDetailViewEntity();
			if (viewEntity[0] != null)
				entity.setVouDetId(Long.valueOf(viewEntity[0].toString()));
			entity.setAcHeadCode((String) viewEntity[1]);
			if (viewEntity[2] != null)
				entity.setCrAmount(new BigDecimal(viewEntity[2].toString()));
			if (viewEntity[3] != null)
				entity.setDrAmount(new BigDecimal(viewEntity[3].toString()));
			entity.setDrCr((String) viewEntity[4]);
			if(viewEntity[5]!=null)
			entity.setFieldId(Long.valueOf(viewEntity[5].toString()));
			if(viewEntity[6]!=null)
			entity.setOrgId(Long.valueOf(viewEntity[6].toString()));
			entity.setParticulars((String) viewEntity[7]);
			entity.setPayerPayee((String) viewEntity[8]);
			entity.setReferenceNo((String) viewEntity[9]);
			if(viewEntity[10]!=null)
			entity.setSacHeadId(Long.valueOf(viewEntity[10].toString()));
			entity.setVoPostingDate((Date)viewEntity[11]);
			entity.setVouDate((Date)viewEntity[12]);
			if(viewEntity[13]!=null)
				entity.setVouId(Long.valueOf(viewEntity[13].toString()));	
			entity.setVouNo((String)viewEntity[14]);
			if(viewEntity[15]!=null)
			entity.setVouSubtypeCpdId(Long.valueOf(viewEntity[15].toString()));
			if(viewEntity[16]!=null)
			entity.setVouTypeCpdId(Long.valueOf(viewEntity[16].toString()));
			if(viewEntity[17]!=null)
			entity.setVoucherAmount(Double.valueOf(viewEntity[17].toString()));
			list.add(entity);
		}
		 return list;
	}

	private AccountFinancialReportDTO mapGeneralLedgerDataToDTO(final List<VoucherDetailViewEntity> resultList,
			AccountFinancialReportDTO dto1) {
		Long voucherType = 673L;
		BigDecimal totalCrAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal totalDrAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal totalRecCrAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal totalRecDrAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		final List<AccountFinancialReportDTO> list = new ArrayList<>();
		for (final VoucherDetailViewEntity viewEntity : resultList) {
			final AccountFinancialReportDTO dto = new AccountFinancialReportDTO();
			dto.setVoucherDate(Utility.dateToString(viewEntity.getVoPostingDate()));
			dto.setParticular(viewEntity.getParticulars() + " " + viewEntity.getReferenceNo());
			dto.setVoucherNo(viewEntity.getVouNo());
			dto.setDrAmount(viewEntity.getDrAmount().setScale(2));
			totalDrAmount = totalDrAmount.add(viewEntity.getDrAmount().setScale(2));
			dto.setCrAmount(viewEntity.getCrAmount().setScale(2));
			totalCrAmount = totalCrAmount.add(viewEntity.getCrAmount().setScale(2));
			if (viewEntity.getVouTypeCpdId().equals(voucherType)) {
				dto.setReceiptDrAmount(viewEntity.getDrAmount().setScale(2));
				totalRecDrAmount = totalRecDrAmount.add(viewEntity.getDrAmount().setScale(2));
				dto.setReceiptCrAmount(viewEntity.getCrAmount().setScale(2));
				totalRecCrAmount = totalRecCrAmount.add(viewEntity.getCrAmount().setScale(2));
			}
			list.add(dto);
		}
		dto1.setSumOfCrAmount(totalCrAmount);
		dto1.setSumOfDrAmount(totalDrAmount);
		dto1.setSumOfreceiptCrAmount(totalRecDrAmount);
		dto1.setSumOfreceiptDrAmount(totalRecCrAmount);
		dto1.setListOfSum(list);
		return dto1;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findReportDataForTrailBalance(final String voPostingDate, final String fromDate,
			final String toDate, final long orgId, Long faYearIds, Date beforeDate, Date afterDate) {

		return accountFinancialReportRepository.queryForTrailBalanceReportData(Utility.stringToDate(fromDate),
				Utility.stringToDate(toDate), orgId, faYearIds, beforeDate, afterDate);
	}

	private AccountFinancialReportDTO mapTrailBalanceDataToDTO(final List<Object[]> resultList, final Long orgId,
			String fromDate, String toDate) {

		BigDecimal sumTransactionDR = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumTransactionCR = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumOpeningCR = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumOpeningDR = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumClosingCR = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumClosingDR = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		final AccountFinancialReportDTO bean = new AccountFinancialReportDTO();
		final List<AccountFinancialReportDTO> list = new ArrayList<>();
		for (final Object[] objects : resultList) {

			final AccountFinancialReportDTO dto = new AccountFinancialReportDTO();
			if (objects[1] != null) {
				dto.setAccountCode(
						budgetCodeService.findAccountHeadCodeBySacHeadId(Long.valueOf(objects[1].toString()), orgId));
			}
			Long cpdIdCrDr = null;

			final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
					PrefixConstants.AccountJournalVoucherEntry.DR, PrefixConstants.DCR, orgId);
			final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
					PrefixConstants.AccountJournalVoucherEntry.CR, PrefixConstants.DCR, orgId);
			if (objects[2] != null) {
				cpdIdCrDr = (Long.valueOf(objects[2].toString()));
			}
			if (cpdIdCrDr != null) {
				if (cpdIdCrDr.equals(drId)) {
					if ((!objects[0].equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))) {
						dto.setOpeningDrAmount(((BigDecimal) objects[0]).setScale(2, RoundingMode.HALF_EVEN));
						dto.setIndCurrOpeningDrAmount(
								CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(objects[0].toString())));
						sumOpeningDR = sumOpeningDR.add(dto.getOpeningDrAmount().setScale(2, RoundingMode.HALF_EVEN));
					}

				} else if (cpdIdCrDr.equals(crId)) {
					if ((!objects[0].equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))) {
						dto.setOpeningCrAmount(((BigDecimal) objects[0]).setScale(2, RoundingMode.HALF_EVEN));
						dto.setIndCurrOpeningCrAmount(
								CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(objects[0].toString())));
						sumOpeningCR = sumOpeningCR.add(dto.getOpeningCrAmount().setScale(2, RoundingMode.HALF_EVEN));
					}
				}
			}

			if (objects[3] != null
					&& !(objects[3].equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))
					&& !(objects[3].equals(BigDecimal.ZERO))) {
				dto.setTransactionCrAmount(((BigDecimal) objects[3]).setScale(2, RoundingMode.HALF_EVEN));
				dto.setIndCurrTransactionCrAmount(
						CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(objects[3].toString())));
				sumTransactionCR = sumTransactionCR.add((BigDecimal) objects[3]);

			}
			if (objects[4] != null
					&& !(objects[4].equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))
					&& !(objects[4].equals(BigDecimal.ZERO))) {
				dto.setTransactionDrAmount(((BigDecimal) objects[4]).setScale(2, RoundingMode.HALF_EVEN));
				dto.setIndCurrTransactionDrAmount(CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(objects[4].toString())));
				sumTransactionDR = sumTransactionDR.add((BigDecimal) objects[4]);

			}
			if (dto.getOpeningCrAmount() != null
					&& !dto.getOpeningCrAmount()
							.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))
					&& !dto.getOpeningCrAmount().equals(BigDecimal.ZERO)) {
				BigDecimal finalCrAmount = calculateClosingBalAsOnDateTrailBalance(dto.getOpeningCrAmount(),
						dto.getTransactionCrAmount(), dto.getTransactionDrAmount());
				if (finalCrAmount.signum() == -1 || finalCrAmount
						.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
					dto.setClosingDrAmount(finalCrAmount.abs());
					dto.setIndCurrClosingDrAmount(CommonMasterUtility.getAmountInIndianCurrency(finalCrAmount.abs()));
					if (dto.getClosingDrAmount() != null && !dto.getClosingDrAmount()
							.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
						sumClosingDR = sumClosingDR.add(dto.getClosingDrAmount());
					}
				} else {
					dto.setClosingCrAmount(finalCrAmount);
					dto.setIndCurrClosingCrAmount(CommonMasterUtility.getAmountInIndianCurrency(finalCrAmount));
					if (dto.getClosingCrAmount() != null && !dto.getClosingCrAmount()
							.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
						sumClosingCR = sumClosingCR.add(dto.getClosingCrAmount());
					}
				}
			} else if (dto.getOpeningDrAmount() != null
					&& !dto.getOpeningDrAmount()
							.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))
					&& !dto.getOpeningDrAmount().equals(BigDecimal.ZERO)) {
				BigDecimal finalDrAmount = calculateClosingBalAsOnDateTrailBalance(dto.getOpeningDrAmount(),
						dto.getTransactionDrAmount(), dto.getTransactionCrAmount());
				if (finalDrAmount.signum() == -1 || finalDrAmount
						.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
					dto.setClosingCrAmount(finalDrAmount.abs());
					dto.setIndCurrClosingCrAmount(CommonMasterUtility.getAmountInIndianCurrency(finalDrAmount.abs()));
					if (dto.getClosingCrAmount() != null && !dto.getClosingCrAmount()
							.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
						sumClosingCR = sumClosingCR.add(dto.getClosingCrAmount());
					}
				} else {
					dto.setClosingDrAmount(finalDrAmount);
					dto.setIndCurrClosingDrAmount(CommonMasterUtility.getAmountInIndianCurrency(finalDrAmount));
					if (dto.getClosingDrAmount() != null && !dto.getClosingDrAmount()
							.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
						sumClosingDR = sumClosingDR.add(dto.getClosingDrAmount());
					}
				}
			} else {
				if (dto.getTransactionCrAmount() != null
						&& !dto.getTransactionCrAmount()
								.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))
						&& !dto.getTransactionCrAmount().equals(BigDecimal.ZERO)) {
					BigDecimal finalTranCrAmt = calculateClosingBalAsOnDateTrailBalance(dto.getOpeningCrAmount(),
							dto.getTransactionCrAmount(), dto.getTransactionDrAmount());
					if (finalTranCrAmt.signum() == -1 || finalTranCrAmt
							.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
						dto.setClosingDrAmount(finalTranCrAmt.abs());
						dto.setIndCurrClosingDrAmount(CommonMasterUtility.getAmountInIndianCurrency(finalTranCrAmt.abs()));
						if (dto.getClosingDrAmount() != null && !dto.getClosingDrAmount()
								.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
							sumClosingDR = sumClosingDR.add(dto.getClosingDrAmount());
						}
					} else {
						dto.setClosingCrAmount(finalTranCrAmt);
						dto.setIndCurrClosingCrAmount(CommonMasterUtility.getAmountInIndianCurrency(finalTranCrAmt));
						if (dto.getClosingCrAmount() != null && !dto.getClosingCrAmount()
								.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
							sumClosingCR = sumClosingCR.add(dto.getClosingCrAmount());
						}
					}
				} else if (dto.getTransactionDrAmount() != null
						&& !dto.getTransactionDrAmount()
								.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))
						&& !dto.getTransactionDrAmount().equals(BigDecimal.ZERO)) {
					BigDecimal finalTranDrAmt = calculateClosingBalAsOnDateTrailBalance(dto.getOpeningDrAmount(),
							dto.getTransactionDrAmount(), dto.getTransactionCrAmount());
					if (finalTranDrAmt.signum() == -1 || finalTranDrAmt
							.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
						dto.setClosingCrAmount(finalTranDrAmt.abs());
						dto.setIndCurrClosingCrAmount(CommonMasterUtility.getAmountInIndianCurrency(finalTranDrAmt.abs()));
						if (dto.getClosingCrAmount() != null && !dto.getClosingCrAmount()
								.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
							sumClosingCR = sumClosingCR.add(dto.getClosingCrAmount());
						}
					} else {
						dto.setClosingDrAmount(finalTranDrAmt);
						dto.setIndCurrClosingDrAmount(CommonMasterUtility.getAmountInIndianCurrency(finalTranDrAmt));
						if (dto.getClosingDrAmount() != null && !dto.getClosingDrAmount()
								.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
							sumClosingDR = sumClosingDR.add(dto.getClosingDrAmount());
						}
					}
				}
			}
			if ((dto.getOpeningCrAmount() != null && dto.getOpeningCrAmount().signum() != 0)
					|| (dto.getOpeningDrAmount() != null && dto.getOpeningDrAmount().signum() != 0)
					|| (dto.getClosingDrAmount() != null && dto.getClosingDrAmount().signum() != 0)
					|| (dto.getClosingCrAmount() != null && dto.getClosingCrAmount().signum() != 0)
					|| (dto.getTransactionDrAmount() != null && dto.getTransactionDrAmount().signum() != 0)
					|| (dto.getTransactionCrAmount() != null && dto.getTransactionCrAmount().signum() != 0)) {
				list.add(dto);
			}
		}
		bean.setFromDate(fromDate);
		bean.setToDate(toDate);
		bean.setListOfSum(list);
		if(sumClosingCR!=null)
			bean.setIndCurrSumClosingCR(CommonMasterUtility.getAmountInIndianCurrency(sumClosingCR));
		if(sumClosingDR!=null)
			bean.setIndCurrSumClosingDR(CommonMasterUtility.getAmountInIndianCurrency(sumClosingDR));
		if (sumOpeningCR.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
			bean.setSumOpeningCR(null);
		} else
			bean.setIndCurrSumOpeningCR(CommonMasterUtility.getAmountInIndianCurrency(sumOpeningCR));

		if (sumOpeningDR.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
			bean.setSumOpeningDR(null);
		} else
			bean.setIndCurrSumOpeningDR(CommonMasterUtility.getAmountInIndianCurrency(sumOpeningDR));
        if(sumTransactionCR!=null)
		bean.setIndCurrSumTransactionCR(CommonMasterUtility.getAmountInIndianCurrency(sumTransactionCR));
        if(sumTransactionDR!=null)
		bean.setIndCurrSumTransactionDR(CommonMasterUtility.getAmountInIndianCurrency(sumTransactionDR));
		return bean;
	}

	@Override
	public void initializeOnReportTypeSelect(final ModelMap model, final String reportTypeCode, final long orgId,
			final int langId) {

		final Organisation org = new Organisation();
		org.setOrgid(orgId);

		final Integer languageId = UserSession.getCurrent().getLanguageId();
		final LookUp lookUpVendorStatus = CommonMasterUtility
				.getLookUpFromPrefixLookUpValue(AccountConstants.AC.getValue(), PrefixConstants.VSS, languageId, org);
		final Long vendorStatus = lookUpVendorStatus.getLookUpId();

		switch (reportTypeCode) {
		case GLR:
			model.addAttribute(REPORT_TYPE, ReportType.GENERAL_LEDGER.stringVal());
			model.addAttribute(AC_HEAD_LOOKUPS, secondaryheadMasterService.findAccountHeadsByOrgId(orgId));
			break;
		case TBR:
			model.addAttribute(REPORT_TYPE, ReportType.TRIAL_BALANCE.stringVal());
			setAcHeadLookUps(model, orgId);
			/*
			 * model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.
			 * FIELD_MASTER_ITEMS,
			 * tbAcFieldMasterService.getFieldMasterLastLevels(UserSession.getCurrent().
			 * getOrganisation().getOrgid()));
			 */	break;
		case CAR:
			model.addAttribute(REPORT_TYPE, ReportType.CLASSIFIED_ABSTRACT.stringVal());
			setAcHeadLookUps(model, orgId);
			break;
		case RPR:
			model.addAttribute(REPORT_TYPE, ReportType.RECEIPTS_AND_PAYMENT_ACCOUNT.stringVal());
			setAcHeadLookUps(model, orgId);
			break;
		case INE:
			model.addAttribute(REPORT_TYPE, ReportType.INCOME_AND_EXPENDITURE_STATEMENT.stringVal());
			setAcHeadLookUps(model, orgId);
			break;
		case BSR:
			model.addAttribute(REPORT_TYPE, ReportType.BALANCE_SHEET.stringVal());
			setAcHeadLookUps(model, orgId);
			break;
		case GBB:
			model.addAttribute(REPORT_TYPE, ReportType.GENERAL_BANK_BOOK.stringVal());
			model.addAttribute(AC_HEAD_LOOKUPS, secondaryheadMasterService.findAccountHeadsByOrgIdBankBook(orgId));
			break;
		case GCB:
			model.addAttribute(REPORT_TYPE, ReportType.GENERAL_CASH_BOOK.stringVal());
			break;
		case RDP:
			model.addAttribute(REPORT_TYPE, ReportType.REGISTER_OF_DEPOSITS.stringVal());
			break;
		case BRS:
			model.addAttribute(REPORT_TYPE, ReportType.BANK_RECONCILIATION_STATEMENT.stringVal());
			model.addAttribute(BANK_LIST, accountChequeDishonourService.getBankAccountData(orgId));
			break;
		case BER:
			model.addAttribute(REPORT_TYPE, ReportType.BUDGET_ESTIMATE.stringVal());
			model.addAttribute(AC_Financialyr_LOOKUPS, secondaryheadMasterService.getAllFinincialYear(orgId, langId));
			break;
		case RAS:
			model.addAttribute(REPORT_TYPE, ReportType.APPROVED_RE_APPROPRIATION.stringVal());
			model.addAttribute(AC_Financialyr_LOOKUPS, secondaryheadMasterService.getAllFinincialYear(orgId, langId));
			break;
		case DYB:
			model.addAttribute(REPORT_TYPE, ReportType.DAY_BOOK.stringVal());
			break;
		case JRB:
			model.addAttribute(REPORT_TYPE, ReportType.JOURNAL_REGISTER.stringVal());
			model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
					tbAcFieldMasterService.getFieldMasterLastLevels(UserSession.getCurrent().getOrganisation().getOrgid()));
			
			break;
		case OBS:
			model.addAttribute(REPORT_TYPE, ReportType.OPENING_BALANCES_REPORT.stringVal());
			model.addAttribute(AC_Financialyr_LOOKUPS, secondaryheadMasterService.getAllFinincialYear(orgId, langId));
			break;
		case PCR:
			model.addAttribute(REPORT_TYPE, ReportType.PAYMENT_CHEQUE_REGISTER.stringVal());
			model.addAttribute(BANK_LIST, accountChequeDishonourService.getBankAccountData(orgId));
			model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
					tbAcFieldMasterService.getFieldMasterLastLevels(UserSession.getCurrent().getOrganisation().getOrgid()));
			break;
		case DCR:
			model.addAttribute(REPORT_TYPE, ReportType.DISHONOR_CHEQUE_REGISTER.stringVal());
			break;
		case BAS:
			model.addAttribute(REPORT_TYPE, ReportType.BANK_ACCOUNTS_SUMMARY_REPORT.stringVal());
			model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
					tbAcFieldMasterService.getFieldMasterLastLevels(UserSession.getCurrent().getOrganisation().getOrgid()));

			break;
		case CCR:
			model.addAttribute(REPORT_TYPE, ReportType.CHEQUE_BOOK_CONTROL_REGISTER.stringVal());
			model.addAttribute(AC_HEAD_LOOKUPS, secondaryheadMasterService.findAccountHeadsByOrgIdBankBook(orgId));
			break;
		case TRR:
			model.addAttribute(REPORT_TYPE, ReportType.TRANSACTION_REVERSAL_REPORT.stringVal());
			model.addAttribute(TRANSACTION_TYPE_LOOKUPS, CommonMasterUtility.getListLookup(
					PrefixConstants.AccountPrefix.TOS.toString(), UserSession.getCurrent().getOrganisation()));
			break;
		case EBS:
			model.addAttribute(REPORT_TYPE, ReportType.EXPENDITURE_BUDGET_STATUS_REPORT.stringVal());
			model.addAttribute(AC_Financialyr_LOOKUPS, secondaryheadMasterService.getAllFinincialYear(orgId, langId));
			break;
		case RBS:
			model.addAttribute(REPORT_TYPE, ReportType.RECEIPTS_BUDGET_STATUS_REPORT.stringVal());
			model.addAttribute(AC_Financialyr_LOOKUPS, secondaryheadMasterService.getAllFinincialYear(orgId, langId));
			model.addAttribute("functionList", getFunctionList());
			model.addAttribute("depList",getDepartmentList());
			break;
		case CRR:
			model.addAttribute(REPORT_TYPE, ReportType.CHEQUE_RECEIVED_REPORT.stringVal());
			break;
		case CCN:
			model.addAttribute(REPORT_TYPE, ReportType.CHEQUE_CANCELLATIN_REPORT.stringVal());
			break;
		case CDR:
			model.addAttribute(REPORT_TYPE, ReportType.COLLECTION_DETAIL_REPORT.stringVal());
			model.addAttribute("depList",getDepartmentList());
			model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
					tbAcFieldMasterService.getFieldMasterLastLevels(UserSession.getCurrent().getOrganisation().getOrgid()));
			
			break;
		case CSR:
			model.addAttribute(REPORT_TYPE, ReportType.COLLECTION_SUMMARY_REPORT.stringVal());
						break;
		case TFC:
			model.addAttribute(REPORT_TYPE, ReportType.TDS_CERTIFICATE.stringVal());
			model.addAttribute(AC_Financialyr_LOOKUPS, secondaryheadMasterService.getAllFinincialYear(orgId, langId));
			model.addAttribute(VENDOR_LIST, vendorMasterService.getActiveVendors(orgId, vendorStatus));
			break;
		case ATR:
			model.addAttribute(REPORT_TYPE, ReportType.AUDIT_TRAIL_REPORT.stringVal());
			break;
		case CFS:
			model.addAttribute(REPORT_TYPE, ReportType.CASH_FLOW_REPORT.stringVal());
			break;
		default:
			break;
		}
	}

	private void setAcHeadLookUps(final ModelMap model, final long orgId) {

		final Long param1 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.AHP.getValue(),
				PrefixConstants.CMD, orgId);

		final Long param2 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.AHS.getValue(),
				PrefixConstants.CMD, orgId);

		model.addAttribute(AC_HEAD_LOOKUPS,
				acCodingstructureMasService.queryAccountHeadByChartOfAccount(param1, param2, orgId));
	}

	@Override
	@Transactional
	public void processReport(final ModelMap model, final String reportTypeCode, final String transactionDate,
			final String fromDate, final String toDate, final Long accountHeadId, final long orgId,
			final Long superOrgId, int langId, Long financialId, String transactionTypeCode, Long registerDepTypeId,
			String categoryId, Long paymodeId, Long vendorId,Long fieldId) {
		switch (reportTypeCode) {
		case GCB:
			processGeneralCashBookReport(model, orgId, fromDate, reportTypeCode, toDate, langId, paymodeId,fieldId);
			break;
		case JRB:
			processJournalRegisterBookReport(model, orgId, fromDate, superOrgId, toDate,fieldId);
			break;
		case TBR:
			processTrailBalanceReport(model, fromDate, toDate, reportTypeCode, orgId);
			break;
		case CAR:
			processClassifiedBudgetReport(model, fromDate, toDate, reportTypeCode, orgId);
			break;
		case RPR:
			processPaymentAndReceiptReport(model, fromDate, toDate, reportTypeCode, orgId, accountHeadId);
			break;
		case GBB:
			processGeneralBankBookReport(model, orgId, fromDate, accountHeadId, reportTypeCode, toDate,fieldId);
			break;
		case RDP:
			processRegisterOfDeposit(model, fromDate, toDate, orgId, reportTypeCode, accountHeadId, registerDepTypeId);
			break;
		case INE:
			processIncomeAndExpenditure(model, fromDate, toDate, orgId, reportTypeCode, accountHeadId, langId);
			break;
		case BRS:
			processBankReconciliationReport(model, transactionDate, orgId, reportTypeCode, accountHeadId, langId);
			break;
		case SDR:
			processSecurityDepositReport(model, fromDate, toDate, orgId, reportTypeCode, accountHeadId, langId);
			break;
		case BER:
			processGeneralBudgetEstimateReport(model, orgId, financialId);
			break;
		case DYB:
			dayBookReport(model, fromDate, toDate, orgId);
			break;
		case CAE:
			cashAndCashEquivalentReport(model, orgId, reportTypeCode, transactionDate, langId);
			break;
		case RAS:
			approvedReAppropriationReport(model, orgId, financialId);
			break;
		case OBS:
			processOpeningBalanceReport(model, financialId, orgId);
			break;
		case PCR:
			paymentChequeRegister(model, orgId, fromDate, toDate,accountHeadId,fieldId);
			break;
		case DCR:
			processDishonorChequeRegister(model, orgId, fromDate, toDate);
			break;
		case BSR:
			processGeneratebBalanceSheet1(model, orgId, transactionDate, langId, accountHeadId, superOrgId);
			break;
		case BAS:
			processBankAccountsSummaryReport(model, orgId, fromDate, toDate,fieldId);
			break;
		case CCR:
			processChequeBookControlRegisterReport(model, orgId, accountHeadId);
			break;
		case TRR:
			processTransactionReversalReport(model, orgId, fromDate, toDate, transactionTypeCode,fieldId);
			break;
		case EBS:
			processExpenditureBudgetStatusReport(model, orgId, financialId);
			break;
		case RBS:
			processReceiptsBudgetStatusReportt(model, orgId, financialId,registerDepTypeId,Long.valueOf(categoryId));
			break;
		case CRR:
			processchequeReceivedReportt(model, orgId, fromDate, toDate, categoryId);
			break;
		case CCN:
			processChequeCancellationReport(model, orgId, fromDate, toDate);
			break;
		case CSR:
			processCollectionSummaryReport(orgId, fromDate, toDate, reportTypeCode, model);
			break;
		case CDR:
			processCollectionDetailReport(orgId, fromDate, toDate, reportTypeCode, superOrgId, model,registerDepTypeId,fieldId);
			break;
		case TFC:
			processTdsCertificateReport(orgId, financialId, vendorId, model);
			break;
		case ATR:
			processAuditTrailReport(orgId, fromDate, toDate, model);
			break;
		case CFS:
			processCashFlowReport(orgId, langId, transactionDate, model);
			break;
		default:
			break;
		}

	}

	private void processSecurityDepositReport(ModelMap model, String fromDate, String toDate, long orgId,
			String reportTypeCode, Long accountHeadId, int langId) {
		// TODO Auto-generated method stub

	}

	private void processGeneralCashBookReport(final ModelMap model, final long orgId, final String fromDate,
			final String reportTypeCode, final String toDate, int langId, Long paymodeId,Long fieldId) {
		BigDecimal totalCrAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal totalDrAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		final long cpdIdPayMode = paymodeId;
		if (cpdIdPayMode == 0l) {
			throw new NullPointerException("Cash Mode is not configured in Prefix[PAY] for orgId=" + orgId);
		}

		Organisation org = new Organisation();
		org.setOrgid(orgId);
		final LookUp lookUpSacStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
				PrefixConstants.ACN, langId, org);

		final Long activeStatusId = lookUpSacStatus.getLookUpId();
		final Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DS.toString(),
				AccountPrefix.TDP.toString(), orgId);
		final Long departmentId = departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString());
		Long sacHeadId = null;
		VoucherTemplateMasterEntity template = voucherTemplateRepository.queryDefinedTemplate(voucherSubTypeId,
				departmentId, orgId, activeStatusId, null);
		Hibernate.initialize(template.getTemplateDetailEntities());
		List<VoucherTemplateDetailEntity> vouDetList = template.getTemplateDetailEntities();
		for (VoucherTemplateDetailEntity voucherTemplateDetailEntity : vouDetList) {
			if (voucherTemplateDetailEntity.getCpdIdPayMode() != null) {
				if (voucherTemplateDetailEntity.getCpdIdPayMode().equals(paymodeId)) {
					sacHeadId = voucherTemplateDetailEntity.getSacHeadId();
					break;
				}
			}
		}
		final List<VoucherDetailViewEntity> records = queryReportDataFromView(fromDate, orgId, cpdIdPayMode, sacHeadId,
				toDate,fieldId);
		if ((records == null) || records.isEmpty()) {

			if (!calculateOpeningBalanceAsOnDate(sacHeadId, orgId, fromDate).equals(new BigDecimal("0.00"))) {
				final AccountFinancialReportDTO dto = new AccountFinancialReportDTO();
				dto.setOpeningBalance(calculateOpeningBalanceAsOnDate(sacHeadId, orgId, fromDate));
				dto.setClosingBalanceAsOn(calculateOpeningBalanceAsOnDate(sacHeadId, orgId, fromDate));
				dto.setFromDate(fromDate);
				dto.setToDate(toDate);
				if (dto.getOpeningBalance().signum() == -1) {
					dto.setDrCrType(" Cr.");
				} else {
					dto.setDrCrType(" Dr.");
				}
				model.addAttribute(REPORT_DATA, dto);
			} else {
				model.addAttribute(VALIDATION_ERROR, AccountConstants.Y.getValue());
				LOGGER.error("No Records found for report from VW_VOUCHER_DETAIL input[cpdIdPayMode=" + cpdIdPayMode
						+ ",authoFlag=Y,cpdStatus=A,orgId=" + orgId);
			}
		} else {
			final AccountFinancialReportDTO dto = new AccountFinancialReportDTO();
			dto.setFromDate(fromDate);
			dto.setToDate(toDate);
			dto.setOpeningBalance(calculateOpeningBalanceAsOnDate(sacHeadId, orgId, fromDate));

			final List<AccountFinancialReportDTO> reportList = mapResultsToDTO(records, dto, reportTypeCode);
			Iterator<AccountFinancialReportDTO> iterator = reportList.iterator();
			while (iterator.hasNext()) {
				final AccountFinancialReportDTO dto1 = (AccountFinancialReportDTO) iterator.next();
				if (dto1.getCrAmount() != null) {
					totalCrAmount = totalCrAmount.add(dto1.getCrAmount());
				}
				if (dto1.getDrAmount() != null)
					totalDrAmount = totalDrAmount.add(dto1.getDrAmount());
			}
			if (!totalCrAmount.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
				dto.setTotalCrAmount(totalCrAmount);
			}
			if (!totalDrAmount.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
				dto.setTotalDrAmount(totalDrAmount);
			}
			model.addAttribute(REPORT_LIST, reportList);
			if (!reportList.isEmpty()) {
				dto.setClosingBalanceAsOn(reportList.get(reportList.size() - 1).getClosingBalance());
			}
			if (dto.getOpeningBalance().signum() == -1) {
				dto.setDrCrType(" Cr.");
			} else {
				dto.setDrCrType(" Dr.");
			}
			model.addAttribute(REPORT_DATA, dto);
		}
		if(fieldId != null && fieldId != -1L) {
			model.addAttribute("fieldName", tbAcFieldMasterService.getFieldDesc(fieldId));
		}
	}

	private void processJournalRegisterBookReport(final ModelMap model, final long orgId, final String fromDate,
			Long superOrgId, String toDate,Long fieldId) {
		final List<VoucherDetailViewEntity> records = findReportDataForJournalRegister(fromDate, orgId, superOrgId,
				toDate,fieldId);
		if ((records == null) || records.isEmpty()) {
			model.addAttribute(VALIDATION_ERROR, AccountConstants.Y.getValue());
			LOGGER.error("No Records found for JournalRegisterBook from VW_VOUCHER_DETAIL input[orgId=" + orgId);
		} else {
			final AccountFinancialReportDTO dto = new AccountFinancialReportDTO();
			dto.setFromDate(fromDate);
			dto.setToDate(toDate);
			model.addAttribute(REPORT_LIST, mapJournelRegisterResultToDto(records));
			model.addAttribute(REPORT_DATA, dto);
			if(fieldId != null && fieldId != -1L) {
				model.addAttribute("fieldName", tbAcFieldMasterService.getFieldDesc(fieldId));
			}
		}
	}

	private void processGeneralLedgerRegisterReport(final ModelMap model, AccountFinancialReportDTO bean,
			final long orgId) {
		Date dateBefore = null;
		BigDecimal totalClosingDr = new BigDecimal(0.00);
		BigDecimal totalClosingCr = new BigDecimal(0.00);
		List<Object[]> OpeningBalanceList = null;

		AccountFinancialReportDTO finalDTO = new AccountFinancialReportDTO();
		List<AccountFinancialReportDTO> finalResultDTO = new ArrayList<AccountFinancialReportDTO>();

		if (bean.isFlag()) {
			List<AccountFinancialReportDTO> selectAllList = new ArrayList<AccountFinancialReportDTO>();
			List<LookUp> acHeadCodeList = secondaryheadMasterService.findAccountHeadsByOrgId(orgId);
			for (LookUp lookUp : acHeadCodeList) {
				AccountFinancialReportDTO selectAllDTO = new AccountFinancialReportDTO();
				selectAllDTO.setAccountHeadId(lookUp.getLookUpId());
				selectAllList.add(selectAllDTO);
			}
			bean.setGeneralLedgerList(selectAllList);
		}
		
		List<AccountFinancialReportDTO> generalLedgerList = bean.getGeneralLedgerList();
		for (AccountFinancialReportDTO accountFinReportDTO : generalLedgerList) {

			final List<VoucherDetailViewEntity> records = findReportDataForGeneralLedgerRegister(bean.getFromDate(),
					bean.getToDate(), accountFinReportDTO.getAccountHeadId(), orgId);
			AccountFinancialReportDTO accountFinancialReportDTO = new AccountFinancialReportDTO();
			mapGeneralLedgerDataToDTO(records, accountFinancialReportDTO);
			String acHeadCode = secondaryheadMasterService.findByAccountHead(accountFinReportDTO.getAccountHeadId());
			Date d = Utility.stringToDate(bean.getFromDate());// intialize your date to any date
			int year1 = getYearFromDate(d);
			String isDateFinancail = "01/04/" + (year1);
			Date d1 = Utility.stringToDate(bean.getToDate());// intialize your date to any date
			int year2 = getYearFromDate(d);
			String isDateFinancail1 = "31/03/" + (year2);

			if (!d.equals(Utility.stringToDate(isDateFinancail))) { // &&
																	// !d1.equals(Utility.stringToDate(isDateFinancail1))
				dateBefore = new Date(d.getTime() - 1 * 24 * 3600 * 1000);

				Date financialYFromDate = tbFinancialyearJpaRepository
						.getFromDateFromFinancialYearIdByPassingDate(dateBefore);
				financialYFromDate = Utility.stringToDate((Utility.dateToString(financialYFromDate)));
				List<Object[]> listOfSum = accountFinancialReportRepository.getSumOfCreditAndSumOfDebitFromVoucher(
						financialYFromDate, dateBefore, accountFinReportDTO.getAccountHeadId(), orgId);
				Long financialYearId = tbFinancialyearJpaRepository
						.getFinanciaYearIdByFromDate(Utility.stringToDate(bean.getFromDate()));
				if (financialYearId != null)
					OpeningBalanceList = findOpeningBalanceAmount(accountFinReportDTO.getAccountHeadId(), orgId,
							financialYearId);

				final AccountFinancialReportDTO dto = new AccountFinancialReportDTO();
				final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
						PrefixConstants.AccountJournalVoucherEntry.DR, PrefixConstants.DCR, orgId);
				final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
						PrefixConstants.AccountJournalVoucherEntry.CR, PrefixConstants.DCR, orgId);
				if ((OpeningBalanceList != null) && !OpeningBalanceList.isEmpty()) {
					for (Object[] OpeningBalanceLists : OpeningBalanceList) {
						if (OpeningBalanceLists[1].equals(drId)) {
							if (OpeningBalanceLists[0] != null) {
								dto.setOpeningDrAmount(new BigDecimal((String) OpeningBalanceLists[0]));
								dto.setClosingDrAmount(new BigDecimal((String) OpeningBalanceLists[0]));
							}
						} else if (OpeningBalanceLists[1].equals(crId)) {
							if (OpeningBalanceLists[0] != null) {
								dto.setOpeningCrAmount(new BigDecimal((String) OpeningBalanceLists[0]));
								dto.setClosingCrAmount(new BigDecimal((String) OpeningBalanceLists[0]));
							}
						}

					}
				}

				if (listOfSum != null && !listOfSum.isEmpty()) {
					for (Object[] listOfSums : listOfSum) {

						if (dto.getOpeningDrAmount() != null && listOfSums[0] != null && listOfSums[1] != null) {
							BigDecimal openingBalance = dto.getOpeningDrAmount()
									.add(new BigDecimal(listOfSums[1].toString()))
									.subtract(new BigDecimal(listOfSums[0].toString()));
							if (openingBalance.signum() == -1) {
								dto.setOpeningCrAmount(openingBalance.abs());
							} else {
								dto.setOpeningDrAmount(openingBalance);
							}
						} else if (dto.getOpeningCrAmount() != null && listOfSums[0] != null && listOfSums[1] != null) {
							BigDecimal openingBalance = dto.getOpeningCrAmount()
									.add(new BigDecimal(listOfSums[0].toString())
											.subtract(new BigDecimal(listOfSums[1].toString())));
							if (openingBalance.signum() == -1) {
								dto.setOpeningDrAmount(openingBalance.abs());
							} else {
								dto.setOpeningCrAmount(openingBalance);
							}
						} else if (dto.getOpeningCrAmount() == null && dto.getOpeningDrAmount() == null
								&& listOfSums[0] != null && listOfSums[1] == null) {
							BigDecimal openingBalance = new BigDecimal(listOfSums[0].toString());
							dto.setOpeningCrAmount(openingBalance);

						} else if (dto.getOpeningCrAmount() == null && dto.getOpeningDrAmount() == null
								&& listOfSums[0] == null && listOfSums[1] != null) {
							BigDecimal openingBalance = new BigDecimal(listOfSums[1].toString());
							dto.setOpeningDrAmount(openingBalance);

						}

						else if (dto.getOpeningCrAmount() == null && dto.getOpeningDrAmount() == null
								&& listOfSums[0] != null && listOfSums[1] != null) {
							BigDecimal openingBalance = new BigDecimal(listOfSums[0].toString())
									.subtract(new BigDecimal(listOfSums[1].toString()));
							if (openingBalance.signum() == -1) {
								dto.setOpeningDrAmount(openingBalance.abs());
							} else {
								dto.setOpeningCrAmount(openingBalance);
							}
						} else if (dto.getOpeningCrAmount() == null && dto.getOpeningDrAmount() == null
								&& listOfSums[0] == null && listOfSums[1] == null) {
							dto.setOpeningCrAmount(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
							dto.setOpeningDrAmount(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
						}

					}
				}

				if (dto.getOpeningCrAmount() != null && accountFinancialReportDTO.getSumOfCrAmount() != null
						&& accountFinancialReportDTO.getSumOfDrAmount() != null) {
					BigDecimal closingBalance = dto.getOpeningCrAmount()
							.add(accountFinancialReportDTO.getSumOfCrAmount())
							.subtract(accountFinancialReportDTO.getSumOfDrAmount());
					if (closingBalance.signum() == -1) {
						dto.setClosingDrAmount(closingBalance.abs());
						dto.setClosingCrAmount(null);
					} else {
						dto.setClosingCrAmount(closingBalance);
						dto.setClosingDrAmount(null);

						// Changed by @ajay Kumar
					}
				}

				else if (dto.getOpeningDrAmount() != null && accountFinancialReportDTO.getSumOfCrAmount() != null
						&& accountFinancialReportDTO.getSumOfDrAmount() != null) {
					BigDecimal closingBalance = dto.getOpeningDrAmount()
							.add(accountFinancialReportDTO.getSumOfDrAmount())
							.subtract(accountFinancialReportDTO.getSumOfCrAmount());
					if (closingBalance.signum() == -1) {
						dto.setClosingCrAmount(closingBalance.abs());
					} else {
						dto.setClosingDrAmount(closingBalance);
					}
				} else if (accountFinancialReportDTO.getSumOfCrAmount() != null
						&& accountFinancialReportDTO.getSumOfDrAmount() != null) {
					BigDecimal closingBalance = accountFinancialReportDTO.getSumOfCrAmount()
							.subtract(accountFinancialReportDTO.getSumOfDrAmount());
					if (closingBalance.signum() == -1) {
						dto.setClosingCrAmount(closingBalance.abs());
					} else {
						dto.setClosingDrAmount(closingBalance);
					}

				}

				dto.setFromDate(bean.getFromDate());
				dto.setToDate(bean.getToDate());
				dto.setAccountHead(acHeadCode);
				mapGeneralLedgerDataToDTO(records, dto);
				finalResultDTO.add(dto);

			} else {
				List<Object[]> listOfSum = accountFinancialReportRepository.getSumOfCreditAndSumOfDebitFromVoucher(
						Utility.stringToDate(bean.getFromDate()), Utility.stringToDate(bean.getToDate()),
						accountFinReportDTO.getAccountHeadId(), orgId);
				Long financialYearId = tbFinancialyearJpaRepository
						.getFinanciaYearIdByFromDate(Utility.stringToDate(bean.getFromDate()));
				if (financialYearId != null)
					OpeningBalanceList = findOpeningBalanceAmount(accountFinReportDTO.getAccountHeadId(), orgId,
							financialYearId);
				final AccountFinancialReportDTO dto = new AccountFinancialReportDTO();
				final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
						PrefixConstants.AccountJournalVoucherEntry.DR, PrefixConstants.DCR, orgId);
				final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
						PrefixConstants.AccountJournalVoucherEntry.CR, PrefixConstants.DCR, orgId);
				if ((OpeningBalanceList != null) && !OpeningBalanceList.isEmpty()) {
					for (Object[] OpeningBalanceLists : OpeningBalanceList) {
						if (OpeningBalanceLists[1].equals(drId)) {
							if (OpeningBalanceLists[0] != null) {
								dto.setOpeningDrAmount(new BigDecimal((String) OpeningBalanceLists[0]).setScale(2,
										RoundingMode.HALF_EVEN));
								dto.setClosingDrAmount(new BigDecimal((String) OpeningBalanceLists[0]).setScale(2,
										RoundingMode.HALF_EVEN));

							}
						} else if (OpeningBalanceLists[1].equals(crId)) {
							if (OpeningBalanceLists[0] != null) {
								dto.setOpeningCrAmount(new BigDecimal((String) OpeningBalanceLists[0]).setScale(2,
										RoundingMode.HALF_EVEN));
								dto.setClosingCrAmount(new BigDecimal((String) OpeningBalanceLists[0]).setScale(2,
										RoundingMode.HALF_EVEN));

							}
						}

					}
				}
				if (listOfSum != null && !listOfSum.isEmpty()) {
					for (Object[] listOfSums : listOfSum) {
						if (dto.getOpeningCrAmount() != null
								&& !dto.getOpeningCrAmount()
										.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))
								&& listOfSums[0] != null && listOfSums[1] != null) {
							/*
							 * dto.setClosingCrAmount(calculateClosingBalAsOnDate(dto.getOpeningCrAmount(),
							 * BigDecimal.valueOf((Double) listOfSums[0]), BigDecimal.valueOf((Double)
							 * listOfSums[1])));
							 */

							BigDecimal closingBalance = calculateClosingBalAsOnDate(dto.getOpeningCrAmount(),
									new BigDecimal(listOfSums[0].toString()), new BigDecimal(listOfSums[1].toString()));

							if (closingBalance.signum() == -1) {
								dto.setClosingDrAmount(closingBalance.abs());
								dto.setClosingCrAmount(null);
							} else {
								dto.setClosingCrAmount(closingBalance);
								dto.setClosingDrAmount(null);
							}

						} else if (dto.getOpeningDrAmount() != null
								&& !dto.getOpeningDrAmount()
										.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))
								&& listOfSums[0] != null && listOfSums[1] != null) {
							/*
							 * dto.setClosingDrAmount(calculateClosingBalAsOnDate(dto.getOpeningDrAmount(),
							 * BigDecimal.valueOf((Double) listOfSums[1]), BigDecimal.valueOf((Double)
							 * listOfSums[0])));
							 */
							BigDecimal closingBalance = calculateClosingBalAsOnDate(dto.getOpeningDrAmount(),
									new BigDecimal(listOfSums[1].toString()), new BigDecimal(listOfSums[0].toString()));

							if (closingBalance.signum() == -1) {
								dto.setClosingCrAmount(closingBalance.abs());
								dto.setClosingDrAmount(null);
							} else {
								dto.setClosingCrAmount(null);
								dto.setClosingDrAmount(closingBalance);
							}

						} else if (listOfSums[0] != null && listOfSums[1] != null) {

							BigDecimal closingBlance = new BigDecimal(0.00);
							if (listOfSums[0] != null && listOfSums[1] != null) {

								closingBlance = calculateClosingBalAsOnDate(dto.getOpeningDrAmount(),
										new BigDecimal(listOfSums[0].toString()),
										new BigDecimal(listOfSums[1].toString()));
							}
							if (closingBlance.signum() == -1) {
								dto.setClosingDrAmount(closingBlance.abs());
							} else {
								dto.setClosingCrAmount(closingBlance);
							}
						}
					}
					if (dto.getOpeningCrAmount() == null) {
						dto.setOpeningCrAmount(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
					}
					if (dto.getOpeningDrAmount() == null) {
						dto.setOpeningDrAmount(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
					}
				}
				dto.setFromDate(bean.getFromDate());
				dto.setToDate(bean.getToDate());
				dto.setAccountHead(acHeadCode);
				mapGeneralLedgerDataToDTO(records, dto);
				finalResultDTO.add(dto);
			}
		}
		finalDTO.setFromDate(bean.getFromDate());
		finalDTO.setToDate(bean.getToDate());
		finalDTO.setGeneralLedgerList(finalResultDTO);
		model.addAttribute(REPORT_LIST, finalDTO);
		model.addAttribute(REPORT_DATA, finalDTO);
	}

	private static int getDayFromDate(Date date) {

		int result = -1;
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			result = cal.get(Calendar.DATE);
		}
		return result;
	}

	private static int getMonthFromDate(Date date) {

		int result = -1;
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			result = cal.get(Calendar.MONTH) + 1;
		}
		return result;
	}

	public static int getYearFromDate(Date date) {
		int result = -1;
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			result = cal.get(Calendar.YEAR);
		}
		return result;
	}

	private void processTrailBalanceReport(final ModelMap model, final String fromDate, final String toDate,
			final String reportTypeCode, final long orgId) {
		List<Object[]> finYearDateList = null;
		Long faYearIds = null;
		List<Object[]> records = null;

		Date myDate = Utility.stringToDate(fromDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.add(Calendar.DATE, -1);
		Date beforeDate = cal.getTime();
		Date afterDate = null;

		int year = getYearFromDate(myDate);
		String year1 = year + "-" + (year + 1);
		int month = getMonthFromDate(myDate);
		String beforDate = "";
		if (month < 4) {
			String year2 = year1.substring(0, year1.lastIndexOf("-"));
			int finalYear = Integer.valueOf(year2) - 1;
			beforDate = "01/04/" + finalYear;
		} else {
			beforDate = "01/04/" + year1.substring(0, year1.lastIndexOf("-"));
		}
		if (beforDate != null) {
			finYearDateList = tbFinancialyearJpaRepository
					.getAllSLIPrefixDateFinincialYear(Utility.stringToDate(beforDate));
			for (Object[] objects : finYearDateList) {
				afterDate = (Date) objects[1];
			}

			faYearIds = tbFinancialyearJpaRepository.getFinanceYearIds(Utility.stringToDate(beforDate));
		}
		if (fromDate != null && toDate != null && faYearIds != null && beforeDate != null && afterDate != null)
			records = findReportDataForTrailBalance(fromDate, fromDate, toDate, orgId, faYearIds, beforeDate,
					afterDate);

		if ((records == null) || records.isEmpty()) {
			model.addAttribute(VALIDATION_ERROR, AccountConstants.Y.getValue());
			LOGGER.error("No Records found for General Ledger Register from VW_VOUCHER_DETAIL ");
		} else {
			model.addAttribute("dto", mapTrailBalanceDataToDTO(records,
					UserSession.getCurrent().getOrganisation().getOrgid(), fromDate, toDate));
		}
	}

	private void processClassifiedBudgetReport(final ModelMap model, final String fromDate, final String toDate,
			final String reportTypeCode, final long orgId) {
		BigDecimal sumBudgetAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumAcutualAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumBudgetAmountPayment = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumAcutualAmountPayment = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumAcutualAmountPaymentBalance = new BigDecimal(
				MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumbalanceRecoverable = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumbalanceOfTillDate = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumbalanceOfPayTilDate = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		final Date frmDate = Utility.stringToDate(fromDate);
		final Date tDate = Utility.stringToDate(toDate);

		long finanacialYearId = queryToFindFinanacialYearID(frmDate);
		Date fromDates = tbFinancialyearJpaRepository.getFromDateFromFinancialYearIdByPassingDate(frmDate);

		final List<Object[]> receiptSideList = queryClassifiedBudgetReceiptSideReportData(frmDate, tDate, orgId,
				finanacialYearId);
		final List<Object[]> receiptSideLists = queryClassifiedBudgetReceiptSideReportData(fromDates, tDate, orgId,
				finanacialYearId);
		final List<Object[]> paymentSideList = queryClassifiedBudgetPaymentSideReportData(frmDate, tDate, orgId,
				finanacialYearId);
		final List<Object[]> paymentSideLists = queryClassifiedBudgetPaymentSideReportData(fromDates, tDate, orgId,
				finanacialYearId);
		final AccountFinancialReportDTO bean1 = new AccountFinancialReportDTO();
		final AccountFinancialReportDTO bean2 = new AccountFinancialReportDTO();
		if (isRecordsFound(receiptSideList, paymentSideList)) {
			final AccountFinancialReportDTO dto = new AccountFinancialReportDTO();
			dto.setFromDate(fromDate);
			dto.setToDate(toDate);

			final List<AccountFinancialReportDTO> list = new ArrayList<>();
			for (Object[] objectListTillYear : receiptSideLists)

			{
				final AccountFinancialReportDTO dto1 = new AccountFinancialReportDTO();
				if (objectListTillYear[1] != null) {
					dto1.setAccountCode((String) objectListTillYear[1]);
				}
				if (objectListTillYear[4] != null && !(objectListTillYear[4]
						.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))) {
					dto1.setBudgetAmount((BigDecimal) objectListTillYear[4]);
					dto1.setBudgetAmountIndianCurrency(
							CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) objectListTillYear[4]));
					sumBudgetAmount = sumBudgetAmount.add((BigDecimal) objectListTillYear[4]);
				} else if (objectListTillYear[3] != null && !(objectListTillYear[3]
						.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))) {
					dto1.setBudgetAmount(((BigDecimal) objectListTillYear[3]).setScale(2, RoundingMode.HALF_EVEN));
					dto1.setBudgetAmountIndianCurrency(
							CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) objectListTillYear[3]));
					sumBudgetAmount = sumBudgetAmount.add((BigDecimal) objectListTillYear[3]);
				}

				for (Object[] objectList : receiptSideList) {
					if (objectListTillYear[0].equals(objectList[0])) {
						if (objectList[2] != null) {
							dto1.setCurrentYearAmount(((BigDecimal) objectList[2]).setScale(2, RoundingMode.HALF_EVEN));
							sumbalanceOfTillDate = sumbalanceOfTillDate.add((BigDecimal) objectList[2]);
						}
					}
				}
				if (objectListTillYear[2] != null && !(objectListTillYear[2]
						.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))) {
					dto1.setActualAmountReceived(
							((BigDecimal) objectListTillYear[2]).setScale(2, RoundingMode.HALF_EVEN));
					dto1.setActualAmountReceivedIndianCurrency(
							CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) objectListTillYear[2]));
					sumAcutualAmount = sumAcutualAmount.add((BigDecimal) objectListTillYear[2]);
				}

				if (reportTypeCode.equals(CAR)) {

					if (objectListTillYear[4] != null) {
						if (objectListTillYear[2] != null && !(objectListTillYear[2]
								.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))) {
							dto1.setBalanceRecoverable(((BigDecimal) objectListTillYear[4])
									.subtract((BigDecimal) objectListTillYear[2]).setScale(2, RoundingMode.HALF_EVEN));
							dto1.setBalanceRecoverableIndianCurrency(
									CommonMasterUtility.getAmountInIndianCurrency(dto1.getBalanceRecoverable()));
							sumbalanceRecoverable = sumbalanceRecoverable.add(dto1.getBalanceRecoverable());

						} else {
							dto1.setBalanceRecoverable(
									(((BigDecimal) objectListTillYear[4])).setScale(2, RoundingMode.HALF_EVEN));
							dto1.setBalanceRecoverableIndianCurrency(
									CommonMasterUtility.getAmountInIndianCurrency(dto1.getBalanceRecoverable()));
							sumbalanceRecoverable = sumbalanceRecoverable.add(dto1.getBalanceRecoverable());

						}

					} else {

						if (objectListTillYear[2] != null
								&& !(objectListTillYear[2]
										.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))
								&& objectListTillYear[3] != null && !(objectListTillYear[3].equals(
										new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))) {
							dto1.setBalanceRecoverable(((BigDecimal) objectListTillYear[3])
									.subtract((BigDecimal) objectListTillYear[2]).setScale(2, RoundingMode.HALF_EVEN));
							dto1.setBalanceRecoverableIndianCurrency(
									CommonMasterUtility.getAmountInIndianCurrency(dto1.getBalanceRecoverable()));
							sumbalanceRecoverable = sumbalanceRecoverable.add(dto1.getBalanceRecoverable());

						} else if (objectListTillYear[3] != null) {
							dto1.setBalanceRecoverable(
									(((BigDecimal) objectListTillYear[3])).setScale(2, RoundingMode.HALF_EVEN));
							dto1.setBalanceRecoverableIndianCurrency(
									CommonMasterUtility.getAmountInIndianCurrency(dto1.getBalanceRecoverable()));
							sumbalanceRecoverable = sumbalanceRecoverable.add(dto1.getBalanceRecoverable());

						}

					}
				}

				list.add(dto1);

			}
			if (sumbalanceOfTillDate != null) {
				bean1.setBalanceAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumbalanceOfTillDate));
			}
			if (sumbalanceRecoverable != null) {
				bean1.setSumbalanceRecoverableIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumbalanceRecoverable));
			}
			if (sumBudgetAmount != null) {
				bean1.setSumBudgetAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(sumBudgetAmount));
			}
			if (sumAcutualAmount != null) {
				bean1.setSumAcutualAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumAcutualAmount));
			}
			bean1.setListOfSum(list);

			final List<AccountFinancialReportDTO> list1 = new ArrayList<>();
			for (Object[] objectListPaymentTillDate : paymentSideLists) {

				final AccountFinancialReportDTO dto1 = new AccountFinancialReportDTO();
				if (objectListPaymentTillDate[1] != null) {
					dto1.setAccountCode((String) objectListPaymentTillDate[1]);
				}

				for (Object[] paymentSideListActual : paymentSideList) {
					if (objectListPaymentTillDate[0].equals(paymentSideListActual[0])) {
						if (paymentSideListActual[2] != null) {
							dto1.setCurrentYearAmount(
									((BigDecimal) paymentSideListActual[2]).setScale(2, RoundingMode.HALF_EVEN));
							sumbalanceOfPayTilDate = sumbalanceOfPayTilDate.add((BigDecimal) paymentSideListActual[2]);
						}
					}
				}

				if (objectListPaymentTillDate[4] != null && !(objectListPaymentTillDate[4]
						.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))) {
					dto1.setBudgetAmount(
							((BigDecimal) objectListPaymentTillDate[4]).setScale(2, RoundingMode.HALF_EVEN));
					dto1.setBudgetAmountIndianCurrency(
							CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) objectListPaymentTillDate[4]));
					sumBudgetAmountPayment = sumBudgetAmountPayment.add((BigDecimal) objectListPaymentTillDate[4]);
				} else if (objectListPaymentTillDate[3] != null && !(objectListPaymentTillDate[3]
						.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))) {
					dto1.setBudgetAmount(
							((BigDecimal) objectListPaymentTillDate[3]).setScale(2, RoundingMode.HALF_EVEN));
					dto1.setBudgetAmountIndianCurrency(
							CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) objectListPaymentTillDate[3]));
					sumBudgetAmountPayment = sumBudgetAmountPayment.add((BigDecimal) objectListPaymentTillDate[3]);
				}

				if (objectListPaymentTillDate[2] != null && !(objectListPaymentTillDate[2]
						.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))) {
					dto1.setActualAmountReceived(
							((BigDecimal) objectListPaymentTillDate[2]).setScale(2, RoundingMode.HALF_EVEN));
					dto1.setActualAmountReceivedIndianCurrency(
							CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) objectListPaymentTillDate[2]));
					sumAcutualAmountPayment = sumAcutualAmountPayment.add((BigDecimal) objectListPaymentTillDate[2]);
				}
				if (reportTypeCode.equals(CAR)) {

					if (objectListPaymentTillDate[4] != null) {
						if (objectListPaymentTillDate[2] != null && !(objectListPaymentTillDate[2]
								.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))) {
							dto1.setBalanceRecoverable(((BigDecimal) objectListPaymentTillDate[4])
									.subtract((BigDecimal) objectListPaymentTillDate[2])
									.setScale(2, RoundingMode.HALF_EVEN));
							dto1.setBalanceRecoverableIndianCurrency(
									CommonMasterUtility.getAmountInIndianCurrency(dto1.getBalanceRecoverable()));
							sumAcutualAmountPaymentBalance = sumAcutualAmountPaymentBalance
									.add(dto1.getBalanceRecoverable());

						} else {
							dto1.setBalanceRecoverable(
									(((BigDecimal) objectListPaymentTillDate[4])).setScale(2, RoundingMode.HALF_EVEN));
							dto1.setBalanceRecoverableIndianCurrency(
									CommonMasterUtility.getAmountInIndianCurrency(dto1.getBalanceRecoverable()));
							sumAcutualAmountPaymentBalance = sumAcutualAmountPaymentBalance
									.add(dto1.getBalanceRecoverable());

						}

					} else {

						if (objectListPaymentTillDate[2] != null
								&& !(objectListPaymentTillDate[2]
										.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))
								&& objectListPaymentTillDate[3] != null && !(objectListPaymentTillDate[3].equals(
										new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))) {
							dto1.setBalanceRecoverable(((BigDecimal) objectListPaymentTillDate[3])
									.subtract((BigDecimal) objectListPaymentTillDate[2])
									.setScale(2, RoundingMode.HALF_EVEN));
							dto1.setBalanceRecoverableIndianCurrency(
									CommonMasterUtility.getAmountInIndianCurrency(dto1.getBalanceRecoverable()));
							sumAcutualAmountPaymentBalance = sumAcutualAmountPaymentBalance
									.add(dto1.getBalanceRecoverable());

						} else if (objectListPaymentTillDate[3] != null) {
							dto1.setBalanceRecoverable(
									(((BigDecimal) objectListPaymentTillDate[3])).setScale(2, RoundingMode.HALF_EVEN));
							dto1.setBalanceRecoverableIndianCurrency(
									CommonMasterUtility.getAmountInIndianCurrency(dto1.getBalanceRecoverable()));
							sumAcutualAmountPaymentBalance = sumAcutualAmountPaymentBalance
									.add(dto1.getBalanceRecoverable());

						}

					}
				}

				list1.add(dto1);
			}
			if (sumBudgetAmountPayment != null) {
				bean2.setSumBudgetAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumBudgetAmountPayment));
			}
			if (sumAcutualAmountPayment != null) {
				bean2.setSumAcutualAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumAcutualAmountPayment));
			}
			if (sumAcutualAmountPaymentBalance != null) {
				bean2.setSubTotalAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumAcutualAmountPaymentBalance));
			}
			if (sumbalanceOfPayTilDate != null) {
				bean2.setTotalDepositIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumbalanceOfPayTilDate));
			}
			bean2.setListOfSum(list1);

			model.addAttribute(MainetConstants.AccountFinancialReport.RECEIPT_SIDE_LIST, bean1);
			model.addAttribute(AccountFinancialReport.PAYMENT_SIDE_LIST, bean2);
			model.addAttribute(REPORT_DATA, dto);
		}

		else

		{
			model.addAttribute(VALIDATION_ERROR, AccountConstants.Y.getValue());
			LOGGER.error("No Records found for Classified Budget Report for [frmDate=" + frmDate + ",tDate=" + tDate
					+ "orgId=" + orgId);
		}
	}

	private boolean isRecordsFound(final List<Object[]> receiptSideList, final List<Object[]> paymentSideList) {

		if (((receiptSideList == null) || receiptSideList.isEmpty())
				&& ((paymentSideList == null) || paymentSideList.isEmpty())) {
			return false;
		}

		return true;
	}

	private boolean isReceiptPaymentRecordsFound(final List<Object[]> openingReceiptSideList,
			final List<Object[]> nonOpeningReceiptSideList, final List<Object[]> openingPaymentSideList,
			final List<Object[]> nonOpeningPaymentSideList) {

		if (((openingReceiptSideList == null) || openingReceiptSideList.isEmpty())
				&& ((nonOpeningReceiptSideList == null) || nonOpeningReceiptSideList.isEmpty())
				&& ((openingPaymentSideList == null) || openingPaymentSideList.isEmpty())
				&& ((nonOpeningPaymentSideList == null) || nonOpeningPaymentSideList.isEmpty())) {
			return false;
		}

		return true;
	}

	@Override
	public List<Object[]> queryClassifiedBudgetReceiptSideReportData(final Date fromDate, final Date toDate,
			final Long orgId, final Long finanacialYearId) {
		return accountFinancialReportRepository.queryClassifiedBudgetReceiptSideReportData(fromDate, toDate, orgId,
				finanacialYearId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> queryClassifiedBudgetPaymentSideReportData(final Date fromDate, final Date toDate,
			final Long orgId, final Long finanacialYearId) {
		return accountFinancialReportRepository.queryClassifiedBudgetPaymentSideReportData(fromDate, toDate, orgId,
				finanacialYearId);
	}

	@Override
	public List<AccountFinancialReportDTO> mapClassifiedBudgetReceiptSideReportDataToDTO(
			final List<Object[]> receiptSideList, final String reportTypeCode) {

		final List<AccountFinancialReportDTO> list = new ArrayList<>();
		BigDecimal totalBudgetaryProvision = BigDecimal.ZERO.setScale(2);
		BigDecimal totalCrAmount = BigDecimal.ZERO.setScale(2);

		for (final Object[] objectArr : receiptSideList) {
			if (objectArr[4] == null) {
				throw new NullPointerException(
						"drCr cannot be null, from queried data at index 4 [" + Arrays.toString(objectArr) + "]");
			} else {
				if (MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_ASSETS
						.equalsIgnoreCase((String) objectArr[4])) {
					AccountFinancialReportDTO dto = new AccountFinancialReportDTO();
					dto.setReportType(reportTypeCode);
					dto.setTotalBudgetaryProvision(totalBudgetaryProvision);
					dto.setTotalCrAmount(totalCrAmount);
					dto = setClassifiedBudgetReceiptSideReportData(dto, objectArr);
					totalCrAmount = dto.getTotalCrAmount();
					dto = mapOtherClassifiedDetail(objectArr, dto);
					totalBudgetaryProvision = dto.getTotalBudgetaryProvision();

					list.add(dto);
				}
			}

		}
		// adding total budget sum and totalCrAmount sum at last of the list
		final AccountFinancialReportDTO dto = new AccountFinancialReportDTO();
		dto.setTotalBudgetaryProvision(totalBudgetaryProvision);
		dto.setTotalCrAmount(totalCrAmount);
		list.add(dto);

		return list;
	}

	@Override
	public List<AccountFinancialReportDTO> mapClassifiedBudgetPaymentSideReportDataToDTO(
			final List<Object[]> paymentSideList, final String reportTypeCode) {

		final List<AccountFinancialReportDTO> list = new ArrayList<>();
		BigDecimal totalBudgetaryProvision = BigDecimal.ZERO.setScale(2);
		BigDecimal totalDrAmount = BigDecimal.ZERO.setScale(2);

		for (final Object[] objectArr : paymentSideList) {
			if (objectArr[4] == null) {
				throw new NullPointerException(
						"drCr cannot be null, from queried data at index 4 [" + Arrays.toString(objectArr) + "]");
			} else {
				if (MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_LIABILITY
						.equalsIgnoreCase((String) objectArr[4])) {
					AccountFinancialReportDTO dto = new AccountFinancialReportDTO();
					dto.setReportType(reportTypeCode);
					dto.setTotalBudgetaryProvision(totalBudgetaryProvision);
					dto.setTotalDrAmount(totalDrAmount);
					dto = setClassifiedBudgetPaymentSideReportData(dto, objectArr);
					totalDrAmount = dto.getTotalDrAmount();
					dto = mapOtherClassifiedDetail(objectArr, dto);
					totalBudgetaryProvision = dto.getTotalBudgetaryProvision();

					list.add(dto);
				}
			}

		}
		// adding total budget sum and totalDrAmount sum at last of the list
		final AccountFinancialReportDTO dto = new AccountFinancialReportDTO();
		dto.setTotalBudgetaryProvision(totalBudgetaryProvision);
		dto.setTotalDrAmount(totalDrAmount);
		list.add(dto);
		return list;
	}

	private AccountFinancialReportDTO mapOtherClassifiedDetail(final Object[] objectArr,
			final AccountFinancialReportDTO dto) {

		if (objectArr[0] == null) {
			throw new NullPointerException(
					"sacHeadCode cannot be null, from queried data at index 0 [" + Arrays.toString(objectArr) + "]");
		}
		dto.setAccountCode((String) objectArr[0]);
		if (objectArr[1] == null) {
			throw new NullPointerException(
					"budgetCode cannot be null, from queried data at index 1 [" + Arrays.toString(objectArr) + "]");
		}
		dto.setAccountHead((String) objectArr[1]);

		if (objectArr[5] != null) {
			if (AccountFinancialReport.RPR2.equals(dto.getReportType())) {
				dto.setTotalBudgetaryProvision(
						dto.getTotalBudgetaryProvision().add((BigDecimal) objectArr[5]).setScale(2));
			} else {
				dto.setBalanceRecoverable(
						calculateBalanceRecoverable(dto.getBudgetaryProvision(), dto.getVoucherAmount()));
			}
			dto.setBudgetaryProvision(((BigDecimal) objectArr[5]).setScale(2));
		}

		return dto;
	}

	private AccountFinancialReportDTO setClassifiedBudgetPaymentSideReportData(final AccountFinancialReportDTO dto,
			final Object[] objectArr) {

		if (objectArr[2] != null) {
			if (AccountFinancialReport.RPR2.equals(dto.getReportType())) {
				dto.setTotalDrAmount(dto.getTotalDrAmount().add((BigDecimal) objectArr[2]).setScale(2));
			}
			dto.setDrAmount(((BigDecimal) objectArr[2]).setScale(2));
		}
		if (objectArr[3] != null) {
			dto.setVoucherAmount(((BigDecimal) objectArr[3]).setScale(2));
		} else {
			dto.setVoucherAmount(BigDecimal.ZERO.setScale(2));
		}
		return dto;
	}

	private AccountFinancialReportDTO setClassifiedBudgetReceiptSideReportData(final AccountFinancialReportDTO dto,
			final Object[] objectArr) {

		if (objectArr[2] != null) {
			if (AccountFinancialReport.RPR2.equals(dto.getReportType())) {
				dto.setTotalCrAmount(dto.getTotalCrAmount().add((BigDecimal) objectArr[2]).setScale(2));
			}
			dto.setCrAmount(((BigDecimal) objectArr[2]).setScale(2));
		}
		if (objectArr[3] != null) {
			dto.setVoucherAmount(((BigDecimal) objectArr[3]).setScale(2));
		} else {
			dto.setVoucherAmount(BigDecimal.ZERO.setScale(2));
		}

		return dto;
	}

	private BigDecimal calculateBalanceRecoverable(final BigDecimal budgetaryProvision,
			final BigDecimal incomeOrExpendditure) {

		final BigDecimal balanceRecoverable = BigDecimal.ZERO.setScale(2);

		return budgetaryProvision != null
				? budgetaryProvision.subtract(incomeOrExpendditure != null ? incomeOrExpendditure : balanceRecoverable)
				: balanceRecoverable.subtract(incomeOrExpendditure != null ? incomeOrExpendditure : balanceRecoverable);
	}

	@Override
	@Transactional(readOnly = true)
	public Long queryToFindFinanacialYearID(Date fromDate) {

		return accountFinancialReportRepository.queryToFindFinanacialYearID(fromDate);

	}

	@Override
	@Transactional(readOnly = true)
	public void processGeneralBankBookReport(final ModelMap model, final Long orgId, final String fromDate,
			final Long accountHeadId, final String reportTypeCode, final String toDate,Long fieldId) {

		BigDecimal totalCrAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal totalDrAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

		final List<VoucherDetailViewEntity> records = queryReportDataFromViewGeneralBankBook(fromDate, orgId,
				accountHeadId, toDate,fieldId);

		String acHeadCode = secondaryheadMasterService.findByAccountHead(accountHeadId);

		/*
		 * if (acHeadCode == null) { model.addAttribute(VALIDATION_ERROR,
		 * AccountConstants.Y.getValue()); LOGGER.error(
		 * "No Records found for report from TB_AC_SECONDARYHEAD_MASTER input[acountHeadId="
		 * + accountHeadId); }
		 */
		if ((records == null) || records.isEmpty()) {

			if (!calculateOpeningBalanceAsOnDate(accountHeadId, orgId, fromDate)
					.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
				final AccountFinancialReportDTO dto = new AccountFinancialReportDTO();
				dto.setOpeningBalance(calculateOpeningBalanceAsOnDate(accountHeadId, orgId, fromDate));
				if (dto.getOpeningBalance() == null) {
					dto.setOpeningBalance(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
				}
				dto.setClosingBalanceAsOn(calculateOpeningBalanceAsOnDate(accountHeadId, orgId, fromDate));
				if (dto.getClosingBalanceAsOn() == null) {
					dto.setClosingBalanceAsOn(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
				}
				dto.setAccountCode(acHeadCode);
				dto.setFromDate(fromDate);
				dto.setToDate(toDate);
				model.addAttribute(REPORT_DATA, dto);
			} else {
				model.addAttribute(VALIDATION_ERROR, AccountConstants.Y.getValue());
				LOGGER.error("No Records found for report from VW_VOUCHER_DETAIL input[accountHeadId=" + accountHeadId
						+ ",authoFlag=Y,cpdStatus=A,orgId=" + orgId);
			}
		} else {
			final AccountFinancialReportDTO dto = new AccountFinancialReportDTO();
			dto.setFromDate(fromDate);
			dto.setToDate(toDate);
			dto.setAccountCode(acHeadCode);
			dto.setOpeningBalance(calculateOpeningBalanceAsOnDate(accountHeadId, orgId, fromDate));
			if (dto.getOpeningBalance() == null) {
				dto.setOpeningBalance(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
			}
			final List<AccountFinancialReportDTO> reportList = mapResultsToDTO(records, dto, reportTypeCode);
			Iterator<AccountFinancialReportDTO> iterator = reportList.iterator();
			while (iterator.hasNext()) {
				final AccountFinancialReportDTO dto1 = (AccountFinancialReportDTO) iterator.next();
				if (!dto1.getCrAmount().equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
					totalCrAmount = totalCrAmount.add(dto1.getCrAmount());
				}
				if (!dto1.getDrAmount().equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))
					totalDrAmount = totalDrAmount.add(dto1.getDrAmount());
			}
			if (!totalCrAmount.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
				dto.setTotalCrAmount(totalCrAmount);
			}
			if (!totalDrAmount.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
				dto.setTotalDrAmount(totalDrAmount);
			}

			model.addAttribute(REPORT_LIST, reportList);
			if (!reportList.isEmpty()) {
				dto.setClosingBalanceAsOn(reportList.get(reportList.size() - 1).getClosingBalance());
			}
			if (dto.getClosingBalanceAsOn() == null) {
				dto.setClosingBalanceAsOn(new BigDecimal(0.00));
			}
			model.addAttribute(REPORT_DATA, dto);
		}
		if(fieldId != null && fieldId != -1L) {
			model.addAttribute("fieldName", tbAcFieldMasterService.getFieldDesc(fieldId));
		}
	}

	private List<VoucherDetailViewEntity> queryReportDataFromViewGeneralBankBook(String fromDate, Long orgId,
			Long accountHeadId, String toDate,Long fieldId) {
		if ((fromDate == null) || fromDate.isEmpty() && (toDate == null) || toDate.isEmpty()) {
			throw new IllegalArgumentException("fromdate and todate cannot be null or empty [from date=" + fromDate
					+ "]" + "[to date=" + toDate + "]");
		}
		final Date entryDate = Utility.stringToDate(fromDate);
		final Date endDate = Utility.stringToDate(toDate);
		return accountFinanceReportDAO.queryReportDataFromViewGeneralBankBook(entryDate, orgId, accountHeadId,
				endDate,fieldId);
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal calculateOpeningBalanceAsOnDateForGeneralBankBook(final Long accountHeadId, final Long orgId,
			final String voPostingDate) {
		Long financialYearId = tbFinancialyearJpaRepository
				.getFinanciaYearIdByFromDate(Utility.stringToDate(voPostingDate));
		final List<Object[]> result = accountFinancialReportRepository.queryOpenBalanceAmountAndCrDrType(accountHeadId,
				orgId, financialYearId);

		final List<Object[]> drCrAmtList = accountFinancialReportRepository.queryDrCrAmountBySacHeadId(accountHeadId,
				orgId, Utility.stringToDate(voPostingDate));

		return openingBalance(result, drCrAmtList, orgId);
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public AccountFinancialReportDTO mapJournelRegisterResultToDto(List<VoucherDetailViewEntity> records) {
		AccountFinancialReportDTO journalRegisterList = new AccountFinancialReportDTO();
		BigDecimal sumOfDrAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal SumOfCrAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

		List<AccountFinancialReportDTO> listOfrecords = new ArrayList<>();
		Iterator<VoucherDetailViewEntity> iterator = records.iterator();
		while (iterator.hasNext()) {
			VoucherDetailViewEntity report = iterator.next();
			AccountFinancialReportDTO journelreportset = new AccountFinancialReportDTO();
			if (report.getVouDate() != null) {
				journelreportset.setVoucherDate(
						new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(report.getVoPostingDate()));
			}
			journelreportset.setVoucherNo(report.getVouNo());
			journelreportset.setAccountHead(report.getAcHeadCode());
			journelreportset.setParticular(report.getParticulars());
			if (report.getDrAmount() != null && !report.getDrAmount()
					.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
				journelreportset.setDrAmount((report.getDrAmount()).setScale(2, RoundingMode.HALF_EVEN));
				sumOfDrAmount = sumOfDrAmount.add(report.getDrAmount());
			}
			if (report.getCrAmount() != null && !report.getCrAmount()
					.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
				journelreportset.setCrAmount((report.getCrAmount()).setScale(2, RoundingMode.HALF_EVEN));
				SumOfCrAmount = SumOfCrAmount.add(report.getCrAmount());
			}
			listOfrecords.add(journelreportset);

		}
		journalRegisterList.setSumOfCrAmount(SumOfCrAmount);
		journalRegisterList.setSumOfDrAmount(sumOfDrAmount);
		journalRegisterList.setListOfSum(listOfrecords);

		return journalRegisterList;
	}

	@Override
	@Transactional(readOnly = true)
	public void processRegisterOfDeposit(ModelMap model, String fromDate, String toDate, Long orgId,
			String reportTypeCode, Long accountHeadId, Long registerDepTypeId) {

		BigDecimal totalDepositAmount = new BigDecimal(0.00);
		BigDecimal totalBalance = new BigDecimal(0.00);
		BigDecimal totalVoucherAmount = new BigDecimal(0.00);
		BigDecimal totalRecoveredAmount = new BigDecimal(0.00);
		BigDecimal totalAdjustmentForfeitureAmount = new BigDecimal(0.00);
		BigDecimal voucherAmount= new BigDecimal(0.00);
		BigDecimal deductionAmount= new BigDecimal(0.00);
		BigDecimal voucherDetails= new BigDecimal(0.00);
		

		List<Object[]> resiterDepositrecord = queryReportDataFromViewRegisterOfdeposit(fromDate, toDate,
				orgId, registerDepTypeId);
		if (resiterDepositrecord == null || resiterDepositrecord.isEmpty()) {
			model.addAttribute(VALIDATION_ERROR, AccountConstants.Y.getValue());
			LOGGER.error("No Records found for report from TB_AC_DEPOSITS input[fromDate=" + fromDate + " todate="
					+ toDate + " orgid=" + orgId + " depositTypeId=" + registerDepTypeId);
			
			AccountFinancialReportDTO dto = new AccountFinancialReportDTO();
			dto.setFromDate(fromDate);
			dto.setToDate(toDate);
			model.addAttribute(REPORT_DATA, dto);
		} else {
			AccountFinancialReportDTO dto1 = new AccountFinancialReportDTO();
			List<AccountFinancialReportDTO> listOfDepositBalance = new ArrayList<>();

			for (Object[] records : resiterDepositrecord) {

				AccountFinancialReportDTO dto = new AccountFinancialReportDTO();
				if (records[0] != null) {
					dto.setReportTypeId(Long.valueOf(records[0].toString()));
					if(records[29]!=null)
					 voucherAmount = new BigDecimal(records[29].toString());
					if(records[30]!=null)
					 deductionAmount = new BigDecimal(records[30].toString());
					if(records[31]!=null)
					 voucherDetails = new BigDecimal(records[31].toString());

					// defect no :29360 by srikanth. To get paid details instead of getting from payment master, getting from details(because in case of partial payment system is showing wrong calculation
					if (voucherAmount != null) {
							dto.setVoucherAmount(voucherAmount);
							totalVoucherAmount = totalVoucherAmount.add(voucherAmount);
							dto.setVoucherAmountIndianCurrency(
									CommonMasterUtility.getAmountInIndianCurrency(voucherAmount));

					}

					if (deductionAmount != null) {
						dto.setRecoveredDeposit(deductionAmount);
						totalRecoveredAmount = totalRecoveredAmount.add(deductionAmount);
						dto.setRecoveredDepositAmountIndianCurrency(
								CommonMasterUtility.getAmountInIndianCurrency(deductionAmount));
					}

					if (voucherDetails != null) {
						dto.setVoucherDetailAmt(voucherDetails);
						totalAdjustmentForfeitureAmount = totalAdjustmentForfeitureAmount.add(voucherDetails);
						dto.setAdjustmentForfeitureAmount(
								CommonMasterUtility.getAmountInIndianCurrency(voucherDetails));
					}
				}
				if ((records[11])!= null) {
					dto.setDepositNumber(records[11].toString());

				}
				if (records[10] != null) {
					dto.setDepositNarration(records[10].toString());
				}
				if (records[28] != null) {
					//String acHeadCode = secondaryheadMasterService.findByAccountHead(records.getSacHeadId());
					dto.setAccountCode(records[28].toString());
				}
				if (records[27] != null) {
					dto.setPayerPayee(records[27].toString());
				}
				if (records[8] != null) {
					dto.setDepositAmount(new BigDecimal( records[8].toString()));
					totalDepositAmount = totalDepositAmount.add(new BigDecimal( records[8].toString()));
					dto.setDepositAmountIndianCurrency(
							CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(records[8].toString())));
				}
				if (records[14] != null) {
					dto.setDepositRecieptNumber(records[14].toString());
				}

				if (records[24] != null) {
					dto.setTypeOfDeposit(records[24].toString());
				}

				if (records[12] != null) {
					dto.setDepositDate(records[12].toString());
				}
				if (dto.getDepositAmount() != null && dto.getVoucherAmount() != null
						&& dto.getRecoveredDeposit() != null && dto.getVoucherDetailAmt() != null) {
					dto.setDepositBalanceIndianCurrency(CommonMasterUtility
							.getAmountInIndianCurrency(dto.getDepositAmount().subtract(dto.getRecoveredDeposit()
									.add(dto.getVoucherAmount().add(dto.getVoucherDetailAmt())))));
					totalBalance = totalBalance.add(dto.getDepositAmount().subtract(
							dto.getVoucherAmount().add(dto.getRecoveredDeposit().add(dto.getVoucherDetailAmt()))));
				} else if (dto.getDepositAmount() != null && dto.getRecoveredDeposit() != null
						&& dto.getVoucherDetailAmt() != null) {
					dto.setDepositBalanceIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(
							dto.getDepositAmount().subtract(dto.getRecoveredDeposit().add(dto.getVoucherDetailAmt()))));
					totalBalance = totalBalance.add(
							dto.getDepositAmount().subtract(dto.getRecoveredDeposit().add(dto.getVoucherDetailAmt())));
				} else if (dto.getDepositAmount() != null && dto.getVoucherAmount() != null
						&& dto.getVoucherDetailAmt() != null) {
					dto.setDepositBalanceIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(
							dto.getDepositAmount().subtract(dto.getVoucherAmount().add(dto.getVoucherDetailAmt()))));
					totalBalance = totalBalance.add(
							dto.getDepositAmount().subtract(dto.getVoucherAmount().add(dto.getVoucherDetailAmt())));
				} else if (dto.getDepositAmount() != null && dto.getVoucherAmount() != null
						&& dto.getRecoveredDeposit() != null) {
					dto.setDepositBalanceIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(
							dto.getDepositAmount().subtract(dto.getVoucherAmount().add(dto.getRecoveredDeposit()))));
					totalBalance = totalBalance.add(
							dto.getDepositAmount().subtract(dto.getVoucherAmount().add(dto.getRecoveredDeposit())));
				} else if (dto.getDepositAmount() != null && dto.getRecoveredDeposit() != null) {
					dto.setDepositBalanceIndianCurrency(CommonMasterUtility
							.getAmountInIndianCurrency(dto.getDepositAmount().subtract(dto.getRecoveredDeposit())));
					totalBalance = totalBalance.add(dto.getDepositAmount().subtract(dto.getRecoveredDeposit()));
				} else if (dto.getDepositAmount() != null && dto.getVoucherDetailAmt() != null) {
					dto.setDepositBalanceIndianCurrency(CommonMasterUtility
							.getAmountInIndianCurrency(dto.getDepositAmount().subtract(dto.getVoucherDetailAmt())));
					totalBalance = totalBalance.add(dto.getDepositAmount().subtract(dto.getVoucherDetailAmt()));
				} else if (dto.getDepositAmount() != null && dto.getVoucherAmount() != null) {
					dto.setDepositBalanceIndianCurrency(CommonMasterUtility
							.getAmountInIndianCurrency(dto.getDepositAmount().subtract(dto.getVoucherAmount())));
					totalBalance = totalBalance.add(dto.getDepositAmount().subtract(dto.getVoucherAmount()));
				} else {
					dto.setDepositBalanceIndianCurrency(
							CommonMasterUtility.getAmountInIndianCurrency(dto.getDepositAmount()));
					totalBalance = totalBalance.add(dto.getDepositAmount());
				}
				listOfDepositBalance.add(dto);
			}
			dto1.setListOfDeposit(listOfDepositBalance);
			dto1.setFromDate(fromDate);
			dto1.setToDate(toDate);
			dto1.setTotalDepositIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(totalDepositAmount));
			dto1.setTotalBalanceIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(totalBalance));
			dto1.setTotalRecoveredDepositAmountIndianCurrency(
					CommonMasterUtility.getAmountInIndianCurrency(totalRecoveredAmount));
			dto1.setVoucherAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(totalVoucherAmount));
			dto1.setTotalAdjustmentForfeitureAmount(
					CommonMasterUtility.getAmountInIndianCurrency(totalAdjustmentForfeitureAmount));
			model.addAttribute(REPORT_DATA, dto1);
		}

	}

	private List<Object[]> queryReportDataFromViewRegisterOfdeposit(String fromDate, String toDate,
			Long orgId, Long registerDepTypeId) {

		final Date fromDates = Utility.stringToDate(fromDate);
		final Date toDates = Utility.stringToDate(toDate);

		return accountFinancialReportRepository.queryReportDataFromViewRegisterOfdeposits(fromDates, toDates, orgId,
				registerDepTypeId);

	}

	@Override
	@Transactional(readOnly = true)
	public void processIncomeAndExpenditure(ModelMap model, String fromDate, String toDate, Long orgId,
			String reportTypeCode, Long accountHeadId, int langId) {

		String financialYear = findFinancialYear(fromDate);
		List<Object[]> incomeRecord =new ArrayList<>();
		List<Object[]> previousYearIncomeAmount = null;
		List<Object[]> ExpenditureRecord = new ArrayList<>();
		List<Object[]> previousYearExp = null;
		final Long param1 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.AHP.getValue(),
				PrefixConstants.CMD, orgId);
		final Long param2 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.AHS.getValue(),
				PrefixConstants.CMD, orgId);
		List<LookUp> levelHead = acCodingstructureMasService.queryAccountHeadByChartOfAccount(param1, param2, orgId);
		if (levelHead != null && !levelHead.isEmpty()) {

			/*
			 * if (levelHead.get(0).getLookUpId() == accountHeadId) { // detail head
			 * incomeRecord = queryReportDataFromViewDetailHeadIncome(fromDate, toDate,
			 * orgId, langId); previousYearIncomeAmount =
			 * findPreviousYearDetailHeadIncome(fromDate, toDate, orgId, langId);
			 * ExpenditureRecord = queryReportDataFromViewDetailHeadExpenditure(fromDate,
			 * toDate, orgId, langId); previousYearExp =
			 * findPreviousYearDetailHeadexpenditure(fromDate, toDate, orgId, langId); }
			 * else if (levelHead.get(1).getLookUpId() == accountHeadId) {// major head
			 * incomeRecord = queryReportDataFromViewMajorHeadIncome(fromDate, toDate,
			 * orgId, langId); previousYearIncomeAmount =
			 * findPreviousYearMajorHeadIncome(fromDate, toDate, orgId, langId);
			 * ExpenditureRecord = queryReportDataFromViewMajorHeadExpenditure(fromDate,
			 * toDate, orgId, langId); previousYearExp =
			 * findPreviousYearMajorHeadexpenditure(fromDate, toDate, orgId, langId); } else
			 * if (levelHead.get(2).getLookUpId() == accountHeadId) {// minor head
			 * incomeRecord = queryReportDataFromViewMinorHeadIncome(fromDate, toDate,
			 * orgId, langId); previousYearIncomeAmount =
			 * findPreviousYearMinorHeadIncome(fromDate, toDate, orgId, langId);
			 * ExpenditureRecord = queryReportDataFromViewMinorHeadExpenditure(fromDate,
			 * toDate, orgId, langId); previousYearExp =
			 * findPreviousYearMinorHeadexpenditure(fromDate, toDate, orgId, langId); } else
			 * if (levelHead.get(3).getLookUpId() == accountHeadId) {// object class
			 * incomeRecord = queryReportDataFromViewObjectClassIncome(fromDate, toDate,
			 * orgId, langId); previousYearIncomeAmount =
			 * findPreviousYearObjectClassIncome(fromDate, toDate, orgId, langId);
			 * ExpenditureRecord = queryReportDataFromViewObjectClassExpenditure(fromDate,
			 * toDate, orgId, langId); previousYearExp =
			 * findPreviousYearObjectClassexpenditure(fromDate, toDate, orgId, langId); }
			 * else if (levelHead.get(4).getLookUpId() == accountHeadId) {// secondary head
			 * incomeRecord = queryReportDataFromViewIncome(fromDate, toDate, orgId,
			 * langId); previousYearIncomeAmount = findPreviousYearIncome(fromDate, toDate,
			 * orgId, langId); ExpenditureRecord =
			 * queryReportDataFromViewExpenditure(fromDate, toDate, orgId, langId);
			 * previousYearExp = findPreviousYearexpenditure(fromDate, toDate, orgId,
			 * langId); }
			 */
			if (levelHead.get(0).getLookUpId() == accountHeadId) { // Major head
				incomeRecord = queryReportDataFromViewMajorHeadIncome(fromDate, toDate, orgId, langId,accountHeadId);
				previousYearIncomeAmount = findPreviousYearMajorHeadIncome(fromDate, toDate, orgId, langId,accountHeadId);
				ExpenditureRecord = queryReportDataFromViewMajorHeadExpenditure(fromDate, toDate, orgId, langId,accountHeadId,null);
				previousYearExp = findPreviousYearMajorHeadexpenditure(fromDate, toDate, orgId, langId,accountHeadId,null);
			} else if (levelHead.get(1).getLookUpId() == accountHeadId) {// minor head
				incomeRecord = queryReportDataFromViewMinorHeadIncome(fromDate, toDate, orgId, langId,accountHeadId);
				previousYearIncomeAmount = findPreviousYearMinorHeadIncome(fromDate, toDate, orgId, langId,accountHeadId);
				ExpenditureRecord = queryReportDataFromViewMinorHeadExpenditure(fromDate, toDate, orgId, langId,accountHeadId);
				previousYearExp = findPreviousYearMinorHeadexpenditure(fromDate, toDate, orgId, langId,accountHeadId);
			} else if (levelHead.size()>3?levelHead.get(3).getLookUpId() == accountHeadId:levelHead.get(2).getLookUpId() == accountHeadId) {// secondary head
				incomeRecord = queryReportDataFromViewIncome(fromDate, toDate, orgId, langId);
				previousYearIncomeAmount = findPreviousYearIncome(fromDate, toDate, orgId, langId);
				ExpenditureRecord = queryReportDataFromViewExpenditure(fromDate, toDate, orgId, langId);
				previousYearExp = findPreviousYearexpenditure(fromDate, toDate, orgId, langId);
			}
		}

		BigDecimal totalOfIncome = new BigDecimal(0.00);
		BigDecimal totalOfExpenditure = new BigDecimal(0.00);
		BigDecimal previousYearIncomeTotal = new BigDecimal(0.00);
		BigDecimal previousYearExpTotal = new BigDecimal(0.00);
		BigDecimal finalSumIncomeCurrentAmt = new BigDecimal(0.00);
		BigDecimal finalSumIncomePreviousAmt = new BigDecimal(0.00);
		final AccountFinancialReportDTO dto = new AccountFinancialReportDTO();
		if (financialYear != null) {
			dto.setFinancialYear(financialYear);
		}
		List<AccountFinancialReportDTO> listOfIncome = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(incomeRecord)) {
			/*
			 * model.addAttribute(VALIDATION_ERROR, AccountConstants.Y.getValue()); LOGGER.
			 * error("No Records found for report from VW_VOUCHER_DETAIL input[fromDate=" +
			 * fromDate + "and todate=" + toDate + "orgId=" + orgId);
			 */

			for (final Object[] viewEntity : incomeRecord) {
				final AccountFinancialReportDTO dto1 = new AccountFinancialReportDTO();
				for (final Object[] previousYearIncomeAmounts : previousYearIncomeAmount) {

					if (previousYearIncomeAmounts[0].equals(viewEntity[0])) {
						if (previousYearIncomeAmounts[2] != null && previousYearIncomeAmounts[3] != null) {
							dto1.setPreviousIncome(new BigDecimal(previousYearIncomeAmounts[2].toString())
									.subtract(new BigDecimal(previousYearIncomeAmounts[3].toString()).setScale(2,
											RoundingMode.HALF_EVEN)));//CREDIT-DEBIT
							/*
							 * dto1.setPreviousIncome(BigDecimal.valueOf((double)
							 * previousYearIncomeAmounts[2]) .subtract(BigDecimal.valueOf((double)
							 * previousYearIncomeAmounts[3])) .setScale(2, RoundingMode.HALF_EVEN));
							 */
						}
						if(previousYearIncomeTotal!=null && dto1.getPreviousIncome()!=null)
						previousYearIncomeTotal = previousYearIncomeTotal.add(dto1.getPreviousIncome());
					}
				}
				dto1.setAccountCode((String) viewEntity[1]);
				if(viewEntity.length>4 && viewEntity[4]!=null)
				dto1.setScheduleNo((String) viewEntity[4]);
				if (viewEntity[2] != null && viewEntity[3] != null) {
					dto1.setCurrentYearAmount(new BigDecimal(viewEntity[2].toString())
							.subtract(new BigDecimal(viewEntity[3].toString()).setScale(2, RoundingMode.HALF_EVEN)));
					/*
					 * dto1.setCurrentYearAmount(BigDecimal.valueOf((double) viewEntity[2])
					 * .subtract(BigDecimal.valueOf((double) viewEntity[3])).setScale(2,
					 * RoundingMode.HALF_EVEN));
					 */
				}
				totalOfIncome = totalOfIncome.add(dto1.getCurrentYearAmount());
				listOfIncome.add(dto1);
			}
		}
		List<AccountFinancialReportDTO> listOfExpenditure = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(ExpenditureRecord)) {
			for (final Object[] viewEntity : ExpenditureRecord) {
				final AccountFinancialReportDTO dto1 = new AccountFinancialReportDTO();

				for (final Object[] previousYearExps : previousYearExp) {

					if (previousYearExps[0].equals(viewEntity[0])) {
						if (previousYearExps[2] != null && previousYearExps[3] != null) {
							dto1.setPriviousExp(new BigDecimal(previousYearExps[3].toString())
									.subtract(new BigDecimal(previousYearExps[2].toString()).setScale(2,
											RoundingMode.HALF_EVEN)));
							/*
							 * dto1.setPriviousExp(BigDecimal.valueOf((double) previousYearExps[3])
							 * .subtract(BigDecimal.valueOf((double) previousYearExps[2])) .setScale(2,
							 * RoundingMode.HALF_EVEN));
							 */
						}
						previousYearExpTotal = previousYearExpTotal.add(dto1.getPriviousExp());
					}
				}

				dto1.setAccountCode((String) viewEntity[1]);
				if(viewEntity.length>4 && viewEntity[4]!=null)
					dto1.setScheduleNo((String) viewEntity[4]);
				if (viewEntity[2] != null && viewEntity[3] != null) {//DEBIT-CREDIT
					dto1.setCurrentYearAmount(new BigDecimal(viewEntity[3].toString())
							.subtract(new BigDecimal(viewEntity[2].toString()).setScale(2, RoundingMode.HALF_EVEN)));
					/*
					 * dto1.setCurrentYearAmount(BigDecimal.valueOf((double) viewEntity[3])
					 * .subtract(BigDecimal.valueOf((double) viewEntity[2])).setScale(2,
					 * RoundingMode.HALF_EVEN));
					 */
				}

				totalOfExpenditure = totalOfExpenditure.add(dto1.getCurrentYearAmount());
				listOfExpenditure.add(dto1);
			}
		}
		List<Object[]> ExpenditureRecordNmam = new ArrayList<>();
		List<Object[]> previousYearExpNmAm = new ArrayList<>();
		List<Object[]> currYr272List = new ArrayList<>();
		List<Object[]> prevYr272List = new ArrayList<>();
		if (levelHead!=null) { // Code for 280/290 head data
			accountHeadId=levelHead.get(0).getLookUpId();
			ExpenditureRecordNmam = queryReportDataFromViewMajorHeadExpenditure(fromDate, toDate, orgId, langId,accountHeadId,MainetConstants.Y_FLAG);
			previousYearExpNmAm = findPreviousYearMajorHeadexpenditure(fromDate, toDate, orgId, langId,accountHeadId,MainetConstants.Y_FLAG);
		}
		if (levelHead!=null) { //Code for  Less Depreciation Transfer to Capital Contribution
			accountHeadId=levelHead.get(0).getLookUpId();
			currYr272List=queryReportDataFromViewMajorHeadExpenditure320(fromDate, toDate, orgId);
			prevYr272List=findPreviousYearMajorHeadexpenditure320(fromDate, toDate, orgId);
			if (CollectionUtils.isNotEmpty(currYr272List)) {
				Object[] currArr=currYr272List.get(0);
				if(currArr!=null && currArr.length>1) {
					dto.setCurrAm272(new BigDecimal(currArr[0].toString()));
					dto.setCurrAm312(new BigDecimal(currArr[1].toString()));
				}
			}else {
				dto.setCurrAm272(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
				dto.setCurrAm312(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
			}
			if (CollectionUtils.isNotEmpty(prevYr272List)) {
				Object[] prevArr=prevYr272List.get(0);
				if(prevArr!=null && prevArr.length>1) {
					dto.setPrevAm272(new BigDecimal(prevArr[0].toString()));
					dto.setPrevAm312(new BigDecimal(prevArr[1].toString()));
				}
			}else {
				dto.setPrevAm272(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
				dto.setPrevAm312(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
			}
		}
		BigDecimal totalOfExpenditureNMAMcr280 = new BigDecimal(0.00);
		BigDecimal totalOfExpenditureNMAMdr280 = new BigDecimal(0.00);
		BigDecimal totalOfExpenditureNMAMcr290 = new BigDecimal(0.00);
		BigDecimal totalOfExpenditureNMAMdr290 = new BigDecimal(0.00);
		BigDecimal totalOfExpenditureNMAMcrPrev280 = new BigDecimal(0.00);
		BigDecimal totalOfExpenditureNMAMdrPrev280 = new BigDecimal(0.00);
		BigDecimal totalOfExpenditureNMAMcrPrev290 = new BigDecimal(0.00);
		BigDecimal totalOfExpenditureNMAMdrPrev290 = new BigDecimal(0.00);
		if (!CollectionUtils.isEmpty(ExpenditureRecordNmam)) {
         for(Object[] objArr:ExpenditureRecordNmam) {
        	if(objArr[0]!=null && StringUtils.equals(objArr[0].toString(), "280")) {
        		totalOfExpenditureNMAMcr280=totalOfExpenditureNMAMcr280.add(new BigDecimal(objArr[2].toString()));
        		totalOfExpenditureNMAMdr280=totalOfExpenditureNMAMdr280.add(new BigDecimal(objArr[3].toString()));
        	}else {
        		totalOfExpenditureNMAMcr290=totalOfExpenditureNMAMcr290.add(new BigDecimal(objArr[2].toString()));
        		totalOfExpenditureNMAMdr290=totalOfExpenditureNMAMdr290.add(new BigDecimal(objArr[3].toString()));
			}
         }
		}
		if (!CollectionUtils.isEmpty(previousYearExpNmAm)) {
			for(Object[] objArr:previousYearExpNmAm) {

	        	if(objArr[0]!=null && StringUtils.equals(objArr[0].toString(), "280")) {
	        		totalOfExpenditureNMAMcrPrev280=totalOfExpenditureNMAMcrPrev280.add(new BigDecimal(objArr[2].toString()));
	        		totalOfExpenditureNMAMdrPrev280=totalOfExpenditureNMAMdrPrev280.add(new BigDecimal(objArr[3].toString()));
	        	}else {
	        		totalOfExpenditureNMAMcrPrev290=totalOfExpenditureNMAMcrPrev290.add(new BigDecimal(objArr[2].toString()));
	        		totalOfExpenditureNMAMdrPrev290=totalOfExpenditureNMAMdrPrev290.add(new BigDecimal(objArr[3].toString()));
				}
	          
	         }
		}
		dto.setCurrAm280(totalOfExpenditureNMAMdr280.subtract(totalOfExpenditureNMAMcr280));
		dto.setCurrAm290(totalOfExpenditureNMAMdr290.subtract(totalOfExpenditureNMAMcr290));
		dto.setPrevAm280(totalOfExpenditureNMAMdrPrev280.subtract(totalOfExpenditureNMAMcrPrev280));
		dto.setPrevAm290(totalOfExpenditureNMAMdrPrev290.subtract(totalOfExpenditureNMAMcrPrev290));
		dto.setPreviousExpTotal(previousYearExpTotal.setScale(2, RoundingMode.HALF_EVEN));
		dto.setTotalDrAmount(totalOfExpenditure.setScale(2, RoundingMode.HALF_EVEN));
		dto.setListOfExpenditure(listOfExpenditure);

		dto.setPreviousInTotal(previousYearIncomeTotal.setScale(2, RoundingMode.HALF_EVEN));
		dto.setTotalCrAmount(totalOfIncome.setScale(2, RoundingMode.HALF_EVEN));
		dto.setListOfIncome(listOfIncome);

		if (dto.getTotalCrAmount() != null && dto.getTotalDrAmount() != null) {
			finalSumIncomeCurrentAmt = dto.getTotalCrAmount().subtract(dto.getTotalDrAmount());
		}
		if (dto.getPreviousInTotal() != null && dto.getPreviousExpTotal() != null) {
			finalSumIncomePreviousAmt = dto.getPreviousInTotal().subtract(dto.getPreviousExpTotal());
		}
		dto.setTotalcurrentAmt(finalSumIncomeCurrentAmt);
		dto.setTotaltransferAmts(finalSumIncomePreviousAmt);
		if(finalSumIncomeCurrentAmt!=null)
		dto.setCurrYrCD(finalSumIncomeCurrentAmt.subtract(dto.getCurrAm280()));
		if(finalSumIncomePreviousAmt!=null)
		dto.setPrevYrCD(finalSumIncomePreviousAmt.subtract(dto.getPrevAm280()));
		/*
		 * if(totalOfIncome.compareTo(totalOfExpenditure) < 0) { totalOfIncome =
		 * totalOfIncome.add(finalSumIncomeCurrentAmt.abs());
		 * dto.setTotalCrAmount(totalOfIncome.setScale(2, RoundingMode.HALF_EVEN));
		 * }else { dto.setTotalCrAmount(totalOfIncome.setScale(2,
		 * RoundingMode.HALF_EVEN)); } if(totalOfExpenditure.compareTo(totalOfIncome) <
		 * 0) { totalOfExpenditure =
		 * totalOfExpenditure.subtract(finalSumIncomeCurrentAmt.abs());
		 * dto.setTotalDrAmount(totalOfExpenditure.setScale(2, RoundingMode.HALF_EVEN));
		 * }else { dto.setTotalDrAmount(totalOfExpenditure.setScale(2,
		 * RoundingMode.HALF_EVEN)); }
		 * if(previousYearIncomeTotal.compareTo(previousYearExpTotal) < 0) {
		 * previousYearIncomeTotal =
		 * previousYearIncomeTotal.add(finalSumIncomePreviousAmt.abs());
		 * dto.setPreviousInTotal(previousYearIncomeTotal.setScale(2,
		 * RoundingMode.HALF_EVEN)); }else {
		 * dto.setPreviousInTotal(previousYearIncomeTotal.setScale(2,
		 * RoundingMode.HALF_EVEN)); }
		 * if(previousYearExpTotal.compareTo(previousYearIncomeTotal) < 0) {
		 * previousYearExpTotal =
		 * previousYearExpTotal.subtract(finalSumIncomePreviousAmt.abs());
		 * dto.setPreviousExpTotal(previousYearExpTotal.setScale(2,
		 * RoundingMode.HALF_EVEN)); }else {
		 * dto.setPreviousExpTotal(previousYearExpTotal.setScale(2,
		 * RoundingMode.HALF_EVEN)); }
		 */

		/*
		 * if(finalSumIncomeCurrentAmt.signum() == -1) { totalOfExpenditure =
		 * totalOfExpenditure.subtract(finalSumIncomeCurrentAmt.abs());
		 * dto.setTotalDrAmount(totalOfExpenditure.setScale(2, RoundingMode.HALF_EVEN));
		 * }else { totalOfIncome = totalOfIncome.add(finalSumIncomeCurrentAmt.abs());
		 * dto.setTotalCrAmount(totalOfIncome.setScale(2, RoundingMode.HALF_EVEN)); }
		 * if(finalSumIncomePreviousAmt.signum() == -1) { previousYearExpTotal =
		 * previousYearExpTotal.subtract(finalSumIncomePreviousAmt.abs());
		 * dto.setPreviousExpTotal(previousYearExpTotal.setScale(2,
		 * RoundingMode.HALF_EVEN)); }else { previousYearIncomeTotal =
		 * previousYearIncomeTotal.add(finalSumIncomePreviousAmt.abs());
		 * dto.setPreviousInTotal(previousYearIncomeTotal.setScale(2,
		 * RoundingMode.HALF_EVEN)); }
		 */

		dto.setToDate(toDate);
		dto.setFromDate(fromDate);

		model.addAttribute(REPORT_DATA, dto);

	}

	private List<Object[]> findPreviousYearMajorHeadexpenditure320(String fromDate, String toDate, Long orgId) {

		final FinancialYear financialYear = tbFinancialyearJpaRepository
				.getFinanciaYearId(Utility.stringToDate(fromDate));
		final Long finYearId = financialYear.getFaYear();

		Date frmDate = null;
		final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(finYearId);
		for (Object[] objects : faYearFromDate) {
			frmDate = (Date) objects[0];
		}
		String myDate = Utility.dateToString(frmDate);
		int year1 = getYearFromDate(frmDate);
		String fromDate1 = myDate.substring(0, 6) + (year1 - 1);
		Long newlyFaYearId = tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(Utility.stringToDate(fromDate1));
		List<Object[]> previousYearFrmToDates = tbFinancialyearJpaRepository.getFinanceYearFrmDate(newlyFaYearId);

		Date fromDates = null;
		Date toDates = null;
		for (Object[] objects : previousYearFrmToDates) {
			if (objects[0] != null && objects[1] != null) {
				fromDates = (Date) objects[0];
				toDates = (Date) objects[1];
			}
		}
	

		return accountFinancialReportRepository.queryReportDataFromViewMajorHeadExpenditure320NM(fromDates,toDates,
				orgId,newlyFaYearId);

	}

	private List<Object[]> queryReportDataFromViewMajorHeadExpenditure320(String fromDate, String toDate, Long orgId) {
		final Date fromDates = Utility.stringToDate(fromDate);
		final Date toDates = Utility.stringToDate(toDate);

		Long fId= tbFinancialyearJpaRepository.getFinanceYearIds(fromDates)	;	
				return accountFinancialReportRepository.queryReportDataFromViewMajorHeadExpenditure320NM(fromDates, toDates, orgId,fId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> queryReportDataFromViewIncome(String fromDate, String toDate, Long orgId, int langId) {

		final Date fromDates = Utility.stringToDate(fromDate);
		final Date toDates = Utility.stringToDate(toDate);

		Organisation org = new Organisation();
		org.setOrgid(orgId);
		final LookUp coaLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.LookUp.INDIVIDUAL,
				PrefixConstants.PREFIX_VALUE_IELA, langId, org);

		return accountFinancialReportRepository.queryReportDataFromViewIncome(fromDates, toDates, orgId,
				coaLookup.getLookUpId());
	}

	private List<Object[]> queryReportDataFromViewExpenditure(String fromDate, String toDate, Long orgId, int langId) {
		final Date fromDates = Utility.stringToDate(fromDate);
		final Date toDates = Utility.stringToDate(toDate);

		Organisation org = new Organisation();
		org.setOrgid(orgId);
		final LookUp coaLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.NEC.EMPLOYEE,
				PrefixConstants.PREFIX_VALUE_IELA, langId, org);
		return accountFinancialReportRepository.queryReportDataFromViewExpenditure(fromDates, toDates, orgId,
				coaLookup.getLookUpId());

	}

	public List<Object[]> queryReportDataFromViewMinorHeadIncome(String fromDate, String toDate, Long orgId,
			int langId, Long accountHeadId) {

		final Date fromDates = Utility.stringToDate(fromDate);
		final Date toDates = Utility.stringToDate(toDate);

		Organisation org = new Organisation();
		org.setOrgid(orgId);
		final LookUp coaLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.LookUp.INDIVIDUAL,
				PrefixConstants.PREFIX_VALUE_IELA, langId, org);

		return accountFinancialReportRepository.queryReportDataFromViewMinorHeadIncomeAndExpenditure(fromDates, toDates,
				orgId, coaLookup.getLookUpId(),accountHeadId);
	}

	private List<Object[]> queryReportDataFromViewMinorHeadExpenditure(String fromDate, String toDate, Long orgId,
			int langId, Long accountHeadId) {
		final Date fromDates = Utility.stringToDate(fromDate);
		final Date toDates = Utility.stringToDate(toDate);

		Organisation org = new Organisation();
		org.setOrgid(orgId);
		final LookUp coaLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.NEC.EMPLOYEE,
				PrefixConstants.PREFIX_VALUE_IELA, langId, org);
		return accountFinancialReportRepository.queryReportDataFromViewMinorHeadIncomeAndExpenditure(fromDates, toDates,
				orgId, coaLookup.getLookUpId(),accountHeadId);

	}

	public List<Object[]> queryReportDataFromViewObjectClassIncome(String fromDate, String toDate, Long orgId,
			int langId) {

		final Date fromDates = Utility.stringToDate(fromDate);
		final Date toDates = Utility.stringToDate(toDate);

		Organisation org = new Organisation();
		org.setOrgid(orgId);
		final LookUp coaLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.LookUp.INDIVIDUAL,
				PrefixConstants.PREFIX_VALUE_IELA, langId, org);

		return accountFinancialReportRepository.queryReportDataFromViewObjectClassIncomeAndExpenditure(fromDates,
				toDates, orgId, coaLookup.getLookUpId());
	}

	private List<Object[]> queryReportDataFromViewObjectClassExpenditure(String fromDate, String toDate, Long orgId,
			int langId) {
		final Date fromDates = Utility.stringToDate(fromDate);
		final Date toDates = Utility.stringToDate(toDate);

		Organisation org = new Organisation();
		org.setOrgid(orgId);
		final LookUp coaLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.NEC.EMPLOYEE,
				PrefixConstants.PREFIX_VALUE_IELA, langId, org);
		return accountFinancialReportRepository.queryReportDataFromViewObjectClassIncomeAndExpenditure(fromDates,
				toDates, orgId, coaLookup.getLookUpId());

	}

	public List<Object[]> queryReportDataFromViewDetailHeadIncome(String fromDate, String toDate, Long orgId,
			int langId) {

		final Date fromDates = Utility.stringToDate(fromDate);
		final Date toDates = Utility.stringToDate(toDate);

		Organisation org = new Organisation();
		org.setOrgid(orgId);
		final LookUp coaLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.LookUp.INDIVIDUAL,
				PrefixConstants.PREFIX_VALUE_IELA, langId, org);

		return accountFinancialReportRepository.queryReportDataFromViewDeatilHeadIncomeAndExpenditure(fromDates,
				toDates, orgId, coaLookup.getLookUpId());
	}

	private List<Object[]> queryReportDataFromViewDetailHeadExpenditure(String fromDate, String toDate, Long orgId,
			int langId) {
		final Date fromDates = Utility.stringToDate(fromDate);
		final Date toDates = Utility.stringToDate(toDate);

		Organisation org = new Organisation();
		org.setOrgid(orgId);
		final LookUp coaLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.NEC.EMPLOYEE,
				PrefixConstants.PREFIX_VALUE_IELA, langId, org);
		return accountFinancialReportRepository.queryReportDataFromViewDeatilHeadIncomeAndExpenditure(fromDates,
				toDates, orgId, coaLookup.getLookUpId());

	}

	public List<Object[]> queryReportDataFromViewMajorHeadIncome(String fromDate, String toDate, Long orgId,
			int langId, Long accountHeadId) {

		final Date fromDates = Utility.stringToDate(fromDate);
		final Date toDates = Utility.stringToDate(toDate);

		Organisation org = new Organisation();
		org.setOrgid(orgId);
		final LookUp coaLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.LookUp.INDIVIDUAL,
				PrefixConstants.PREFIX_VALUE_IELA, langId, org);

		return accountFinancialReportRepository.queryReportDataFromViewMajorHeadIncomeAndExpenditure(fromDates, toDates,
				orgId, coaLookup.getLookUpId(),accountHeadId);
	}

	private List<Object[]> queryReportDataFromViewMajorHeadExpenditure(String fromDate, String toDate, Long orgId,
			int langId, Long accountHeadId,String namnFormat) {
		final Date fromDates = Utility.stringToDate(fromDate);
		final Date toDates = Utility.stringToDate(toDate);

		Organisation org = new Organisation();
		org.setOrgid(orgId);
		final LookUp coaLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.NEC.EMPLOYEE,
				PrefixConstants.PREFIX_VALUE_IELA, langId, org);
		List<Object[]> objList=new ArrayList<>();
		if(StringUtils.isEmpty(namnFormat)) {
      //Defect #185953
		return accountFinancialReportRepository.queryReportDataFromViewMajorHeadExpenditure(fromDates, toDates,
				orgId, coaLookup.getLookUpId(),accountHeadId);}
		else {
			return accountFinancialReportRepository.queryReportDataFromViewMajorHeadIncomeAndExpenditureNMAM(fromDates, toDates,
					orgId, coaLookup.getLookUpId(),accountHeadId);
		}
		}

	

	/**
	 * @param model
	 * @param transactionDate
	 * @param orgId
	 * @param reportTypeCode
	 * @param bankAccountId
	 * @param langId
	 *            Date :-22/02/2019 Changeg By Ajay Kumar Set Bank Name without
	 *            HeadCode , Change Query of findChequeIssuedButNotPresent and
	 *            findCheckDeposited
	 * 
	 */
	private void processBankReconciliationReport(ModelMap model, String transactionDate, long orgId,
			String reportTypeCode, Long bankAccountId, int langId) {
		BigDecimal totalOpeningBalAndChequeAm = new BigDecimal(0.00);
		BigDecimal amount = new BigDecimal(0.00);
		final Long accountHeadId = getSacHeadIdByBankAccountId(bankAccountId, orgId);
		Map<Long, String> bankAccountlist = new LinkedHashMap<>();
		bankAccountlist = accountChequeDishonourService.getBankAccountData(orgId);
		String collect = bankAccountlist.entrySet().stream().filter(map -> map.getKey().equals(bankAccountId))
				.map(map -> map.getValue()).collect(Collectors.joining());
		final AccountFinancialReportDTO dto = new AccountFinancialReportDTO();
		dto.setOpeningBalance(calculateOpeningBalanceAsOnDateForGeneralBankBook(accountHeadId, orgId, transactionDate));
		if (dto.getOpeningBalance() != null) {
			dto.setOpeningBalanceIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(dto.getOpeningBalance()));
		} else {
			dto.setOpeningBalance(new BigDecimal(0.00));
		}
		dto.setTransactionDate(transactionDate);
		String acHeadCode = secondaryheadMasterService.findByAccountHead(accountHeadId);

		if (StringUtils.isEmpty(acHeadCode)) {
			model.addAttribute(VALIDATION_ERROR, AccountConstants.Y.getValue());
			LOGGER.error("account head code  found for report from TB_AC_SECONDARYHEAD_MASTER input[ transactionDate="
					+ transactionDate + "orgId=" + orgId);

		} else {
			dto.setAccountCode(collect);
			BigDecimal chequeDpositAmount = accountFinancialReportRepository.findChequeIssuedButNotPresent(orgId,
					accountHeadId, Utility.stringToDate(transactionDate));
			if (chequeDpositAmount == null)
				chequeDpositAmount = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN);
			if (chequeDpositAmount != null) {
				dto.setChequeAmounts(chequeDpositAmount.setScale(2));
				dto.setChequeAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(chequeDpositAmount));
				totalOpeningBalAndChequeAm = dto.getOpeningBalance().add(chequeDpositAmount);
			}
			final LookUp coaLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue("STP","CLR", langId,UserSession.getCurrent().getOrganisation());
			if(coaLookup!=null)
		    amount = accountFinancialReportRepository.findChequeIssuedAndPaymentStop(orgId,accountHeadId, Utility.stringToDate(transactionDate),coaLookup.getLookUpId());
			if (amount == null)
				amount = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN);
			if (amount != null) {
				dto.setBalanceAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(amount));
			}

			
			BigDecimal chequeDepositedAmount = accountFinancialReportRepository.findCheckDeposited(orgId, accountHeadId,
					Utility.stringToDate(transactionDate));
			if (chequeDepositedAmount == null)
				chequeDepositedAmount = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN);
			if (chequeDepositedAmount != null) {
				dto.setChequeDeposit(chequeDepositedAmount.setScale(2));
				dto.setChequeDepositIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(chequeDepositedAmount));
			}
			BigDecimal chequeDishonouredAmount = accountFinancialReportRepository.findChequeDepositButDishonoured(orgId,
					accountHeadId, Utility.stringToDate(transactionDate));
			if (chequeDishonouredAmount == null)
				chequeDishonouredAmount = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN);
			if (chequeDishonouredAmount != null) {
				dto.setChequeDishonoured(chequeDishonouredAmount.setScale(2));
				dto.setChequeDishonouredIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(chequeDishonouredAmount));
			}

			dto.setSubTotalAmount(totalOpeningBalAndChequeAm.setScale(2));
			dto.setSubTotalAmountIndianCurrency(
					CommonMasterUtility.getAmountInIndianCurrency(totalOpeningBalAndChequeAm.setScale(2)));
			if (dto.getChequeDeposit() != null && chequeDishonouredAmount != null) {
				BigDecimal totalDepositAndDishonoured = dto.getChequeDeposit().add(chequeDishonouredAmount);
				dto.setTotalDepositAndDishonoured(totalDepositAndDishonoured);
				dto.setTotalDepositAndDishonouredIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(totalDepositAndDishonoured));
				BigDecimal subTractionTatal = dto.getSubTotalAmount().subtract(dto.getTotalDepositAndDishonoured());
				dto.setSubTractionTatal(subTractionTatal);
				dto.setSubTractionTatalIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(subTractionTatal));
			}
			model.addAttribute(REPORT_DATA, dto);
		}

	}

	/*
	 * Object: Method for BudgetEstimation Report(Story-161) Author By:- Ajay Kumar
	 * Date: 14-03-2018
	 */
	private void processGeneralBudgetEstimateReport(ModelMap model, long orgId, long financialId) {
		String financialYears = findFinancialYearByFinancialYearId(financialId); // Please use type here.
		String toDate = null;
		String fromDate = null;
		String lastYrFromDate = null;
		String prevYrFromDate = null;
		String nextYearFromDate = null;
		String nextYearTodate = null;
		long EmpId = 0;

		// Current FinancialYearId for FromDate and ToDate
		List<Object[]> frmdateTodate = accountFinancialReportRepository.getAllFinincialFromDate(financialId);

		for (Object[] dateEntity : frmdateTodate) {
			fromDate = dateEntity[1].toString();
			toDate = dateEntity[2].toString();
		}
		// Next Year FromDate and to ToDate
		Date newFromDate = null;
		final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(financialId);
		for (Object[] objects : faYearFromDate) {
			newFromDate = (Date) objects[0];
		}
		int year1 = getYearFromDate(newFromDate);
		int newOneYear = (year1 + 1);
		int newTwoYear = (year1 + 2);
		nextYearFromDate = (String.valueOf(newOneYear) + " - " + String.valueOf(newTwoYear));

		String myDate = Utility.dateToString(newFromDate);
		String fromDate1 = myDate.substring(0, 6) + (year1 + 1);
		Long nextFaYearId = tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(Utility.stringToDate(fromDate1));
		// Covert Last 3Years From Current FromDate
		LocalDate localDate = LocalDate.parse(fromDate);
		LocalDate last3Year = localDate.minusYears(3);
		lastYrFromDate = last3Year.toString();// 2014-04-01

		LocalDate localDat = LocalDate.parse(toDate);
		LocalDate yearLate = localDat.minusYears(1);
		toDate = yearLate.toString(); // 2017-03-31

		LocalDate oneyrback = LocalDate.parse(fromDate);
		LocalDate oneyearago = oneyrback.minusYears(1);
		prevYrFromDate = oneyearago.toString(); // 2016-04-01
		// Converting Date According To utility Class FormateDate
		SimpleDateFormat fdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat fdf2 = new SimpleDateFormat("dd/MM/yyyy");
		try {
			lastYrFromDate = fdf2.format(fdf1.parse(lastYrFromDate));// 2014-04-01
			toDate = fdf2.format(fdf1.parse(toDate));// 2017-03-31
			prevYrFromDate = fdf2.format(fdf1.parse(prevYrFromDate));// 2016-04-01
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date lastYrFromDates = Utility.stringToDate(lastYrFromDate);
		Date toDates = Utility.stringToDate(toDate);
		Date prevYrFromDates = Utility.stringToDate(prevYrFromDate);
		AccountFinancialReportDTO receiptdto = null;
		List<AccountFinancialReportDTO> receiptList = new ArrayList<AccountFinancialReportDTO>();
		List<AccountFinancialReportDTO> expenditureList = new ArrayList<AccountFinancialReportDTO>();
		AccountFinancialReportDTO receipt = new AccountFinancialReportDTO();
		Long prvYrFinancialyr = accountFinancialReportRepository.getAllFinincialPrevDate(prevYrFromDates);
		if (prvYrFinancialyr != null) {
			LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
					PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(),
					UserSession.getCurrent().getOrganisation());
			Long cpdBugtypeid = revenueLookup.getLookUpId();
			List<Object[]> receiptBudgetEntity = accountFinancialReportRepository
					.getAllBudgetEnstimationOfReceiptReportData(lastYrFromDates, toDates, prevYrFromDates, financialId,
							prvYrFinancialyr, orgId, cpdBugtypeid, nextFaYearId);
			// ReceiptBudgetEstimation Data Populate
			if (receiptBudgetEntity != null && !receiptBudgetEntity.isEmpty()) {
				for (Object[] receiptBudgetEstimationEntity : receiptBudgetEntity) {
					receiptdto = new AccountFinancialReportDTO();
					// Splitting Two distinct String For Display Separately.
					if (receiptBudgetEstimationEntity[1] != null) {
						int i;
						for (i = 0; i < receiptBudgetEstimationEntity[1].toString().length(); i++) {
							char c = receiptBudgetEstimationEntity[1].toString().charAt(i);
							if (c >= '0' && c > '9')
								break;
						}
						String acounthead = receiptBudgetEstimationEntity[1].toString().substring(0, i);
						acounthead = acounthead.substring(0, acounthead.lastIndexOf(" - "));
						String accountCode = receiptBudgetEstimationEntity[1].toString().substring(i);
						receiptdto.setAccountHead(accountCode);
						receiptdto.setAccountCode(acounthead);
					}

					// BudgetEstimation of last 3 years Average.
					if (receiptBudgetEstimationEntity[2] != null) {
						receiptdto.setLastThreeYrAmt(CommonMasterUtility.getAmountInIndianCurrency(
								new BigDecimal(receiptBudgetEstimationEntity[2].toString())));
					} else {
						receiptdto.setLastThreeYrAmt(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

					}

					// BudgetEstimation collection amount of last year
					if (receiptBudgetEstimationEntity[3] != null) {
						receiptdto.setLastYrAmt(CommonMasterUtility.getAmountInIndianCurrency(
								new BigDecimal(receiptBudgetEstimationEntity[3].toString())));
					} else {
						receiptdto.setLastYrAmt(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

					}

					// BudgetEstimation revised amount of current Year
					if (receiptBudgetEstimationEntity[4] != null) {
						receiptdto.setRevisedEstimation(CommonMasterUtility.getAmountInIndianCurrency(
								new BigDecimal(receiptBudgetEstimationEntity[4].toString())));
					} else {
						receiptdto.setRevisedEstimation(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

					}

					// BudgetEstimation current amount of current Year
					if (receiptBudgetEstimationEntity[5] != null) {
						receiptdto.setOriginalEst(CommonMasterUtility.getAmountInIndianCurrency(
								new BigDecimal(receiptBudgetEstimationEntity[5].toString())));
					} else {
						receiptdto.setOriginalEst(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

					}

					// BudgetEstimation of next year amount
					if (receiptBudgetEstimationEntity[6] != null) {
						receiptdto.setNxtYrOEstimation(CommonMasterUtility.getAmountInIndianCurrency(
								new BigDecimal(receiptBudgetEstimationEntity[6].toString())));
					} else {
						receiptdto.setNxtYrOEstimation(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

					}

					// BudgetEstimation created by .
					if (receiptBudgetEstimationEntity[7] != null) {
						EmpId = Long.valueOf(receiptBudgetEstimationEntity[7].toString());
						String userName = accountFinancialReportRepository.queryReAppropriationReportForUser(EmpId);
						receiptdto.setUserId(userName);
					} else {
						receiptdto.setUserId(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

					}
					
					if (receiptBudgetEstimationEntity[8] != null) {
						receiptdto.setRemarks(receiptBudgetEstimationEntity[8].toString());
					} else {
						receiptdto.setRemarks("");

					}

					receiptList.add(receiptdto);

				}
			}
		}

		// BudgetEnstimationOfExpenditure Data Populate
		if (prvYrFinancialyr != null) {

			LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
					PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(),
					UserSession.getCurrent().getOrganisation());
			Long cpdBugtypeid = expLookup.getLookUpId();

			List<Object[]> expenditureBudgetEntity = accountFinancialReportRepository
					.getAllBudgetEnstimationOfExpenditureReportData(lastYrFromDates, toDates, prevYrFromDates,
							financialId, prvYrFinancialyr, orgId, cpdBugtypeid, nextFaYearId);

			if (expenditureBudgetEntity != null && !expenditureBudgetEntity.isEmpty()) {

				for (Object[] expenditeBudgetEstimationEntity : expenditureBudgetEntity) {
					receiptdto = new AccountFinancialReportDTO();

					// Splitting Two distinct String For Display Separately.
					if (expenditeBudgetEstimationEntity[1] != null) {
						int i;
						for (i = 0; i < expenditeBudgetEstimationEntity[1].toString().length(); i++) {
							char c = expenditeBudgetEstimationEntity[1].toString().charAt(i);
							if (c >= '0' && c > '9')
								break;
						}
						String acounthead = expenditeBudgetEstimationEntity[1].toString().substring(0, i);
						acounthead = acounthead.substring(0, acounthead.lastIndexOf(" - "));
						String accountCode = expenditeBudgetEstimationEntity[1].toString().substring(i);
						receiptdto.setAccountHead(accountCode);
						receiptdto.setAccountCode(acounthead);
					}

					// BudgetEstimation of last 3 years Average.
					if (expenditeBudgetEstimationEntity[2] != null) {
						receiptdto.setLastThreeYrAmt(CommonMasterUtility.getAmountInIndianCurrency(
								new BigDecimal(expenditeBudgetEstimationEntity[2].toString())));
					} else {
						receiptdto.setLastThreeYrAmt(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

					}

					// BudgetEstimation collection amount of last year
					if (expenditeBudgetEstimationEntity[3] != null) {
						receiptdto.setLastYrAmt(CommonMasterUtility.getAmountInIndianCurrency(
								new BigDecimal(expenditeBudgetEstimationEntity[3].toString())));
					} else {
						receiptdto.setLastYrAmt(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

					}

					// BudgetEstimation revised amount of current Year
					if (expenditeBudgetEstimationEntity[4] != null) {
						receiptdto.setRevisedEstimation(CommonMasterUtility.getAmountInIndianCurrency(
								new BigDecimal(expenditeBudgetEstimationEntity[4].toString())));
					} else {
						receiptdto.setRevisedEstimation(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

					}

					// BudgetEstimation current amount of current Year
					if (expenditeBudgetEstimationEntity[5] != null) {
						receiptdto.setOriginalEst(CommonMasterUtility.getAmountInIndianCurrency(
								new BigDecimal(expenditeBudgetEstimationEntity[5].toString())));
					} else {
						receiptdto.setOriginalEst(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

					}

					// BudgetEstimation amount of next year amount
					if (expenditeBudgetEstimationEntity[6] != null) {
						receiptdto.setNxtYrOEstimation(CommonMasterUtility.getAmountInIndianCurrency(
								new BigDecimal(expenditeBudgetEstimationEntity[6].toString())));
					} else {
						receiptdto.setNxtYrOEstimation(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

					}

					// BudgetEstimation created by
					if (expenditeBudgetEstimationEntity[7] != null) {
						EmpId = Long.valueOf(expenditeBudgetEstimationEntity[7].toString());
						String userName = accountFinancialReportRepository.queryReAppropriationReportForUser(EmpId);
						receiptdto.setUserId(userName);
					} else {
						receiptdto.setUserId(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

					}
					if (expenditeBudgetEstimationEntity[8] != null) {
						receiptdto.setRemarks(expenditeBudgetEstimationEntity[8].toString());
					} else {
						receiptdto.setRemarks("");

					}

					expenditureList.add(receiptdto);

				}
			}
		}

		receipt.setListOfBudgetEstimation(receiptList);
		receipt.setListOfExpenditure(expenditureList);
		receipt.setFinancialYear(financialYears);
		receipt.setFromDate(nextYearFromDate);
		receipt.setToDate(nextYearTodate);
		model.addAttribute(REPORT_DATA, receipt);

	}

	private void cashAndCashEquivalentReport(ModelMap model, long orgId, String reportTypeCode, String transactionDate,
			int langId) {
		Date toDate = Utility.stringToDate(transactionDate);
		BigDecimal totalCrAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal defaultValue = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal totalDrAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal totalAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal CashInHandCr = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal CashInHandDr = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

		final AccountFinancialReportDTO cashequivalent = new AccountFinancialReportDTO();

		final long cpdIdPayMode = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CASH.getValue(),
				AccountPrefix.PAY.toString(), orgId);

		if (cpdIdPayMode == 0l) {
			throw new NullPointerException("Cash Mode is not configured in Prefix[PAY] for orgId=" + orgId);
		}

		Organisation org = new Organisation();
		org.setOrgid(orgId);
		final LookUp lookUpSacStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
				PrefixConstants.ACN, langId, org);
		final Long activeStatusId = lookUpSacStatus.getLookUpId();
		final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.AccountJournalVoucherEntry.DR, PrefixConstants.DCR, orgId);
		final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.AccountJournalVoucherEntry.CR, PrefixConstants.DCR, orgId);
		Long sacHeadId = accountFinancialReportRepository.findSacHeadId(cpdIdPayMode, orgId, activeStatusId);
		Date fromDates = tbFinancialyearJpaRepository.getFromDateFromFinancialYearIdByPassingDate(toDate);

		List<Object[]> cashinhand = accountFinancialReportRepository.findCashAmountHeadId(sacHeadId, orgId, toDate,
				fromDates);
		for (Object[] cash : cashinhand) {
			if (cash[0] != null) {
				CashInHandCr = new BigDecimal(cash[0].toString());
			}
			if (cash[1] != null) {
				CashInHandDr = new BigDecimal(cash[1].toString());
			}
			BigDecimal totlDrAmount = CashInHandDr.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			BigDecimal totlCrAmount = CashInHandCr.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			cashequivalent.setClosingBalance(totlDrAmount.subtract(totlCrAmount));
		}
		String acountheadcode = secondaryheadMasterService.findByAccountHead(sacHeadId);
		Long financialYearId = tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(toDate);

		List<Object[]> OpeningBalanceList = null;

		if (financialYearId != null) {
			OpeningBalanceList = findOpeningBalanceAmount(sacHeadId, orgId, financialYearId);
		}

		if (OpeningBalanceList != null && !OpeningBalanceList.isEmpty()) {

			for (Object[] OpeningBalanceLists : OpeningBalanceList) {

				if (OpeningBalanceLists[1].equals(drId)) {

					if (OpeningBalanceLists[0] != null && CashInHandCr.compareTo(defaultValue) > 0
							&& CashInHandCr != null && CashInHandDr != null) {

						BigDecimal closingBalance = (new BigDecimal(OpeningBalanceLists[0].toString()))
								.add(CashInHandDr).subtract(CashInHandCr);

						cashequivalent.setClosingBalance(closingBalance.setScale(2, RoundingMode.HALF_EVEN));

					}
				} else if (OpeningBalanceLists[1].equals(crId)) {
					if (OpeningBalanceLists[0] != null && CashInHandCr != null && CashInHandDr != null) {
						BigDecimal closingBalance = (new BigDecimal(OpeningBalanceLists[0].toString()))
								.add(CashInHandCr).subtract(CashInHandDr);
						cashequivalent.setClosingBalance(closingBalance.setScale(2, RoundingMode.HALF_EVEN));
					}
				}
			}
		}
		List<AccountFinancialReportDTO> list = new ArrayList<>();
		cashequivalent.setTransactionDate(transactionDate);
		cashequivalent.setAccountHead(acountheadcode);

		AccountFinancialReportDTO dto = null;
		long accidcHeadId = 00;

		List<LookUp> cashAccountHead = secondaryheadMasterService.findAccountHeadsByOrgIdBankBook(orgId);

		for (LookUp cashid : cashAccountHead) {
			accidcHeadId = cashid.getLookUpId();
			Date fromDatess = tbFinancialyearJpaRepository
					.getFromDateFromFinancialYearIdByPassingDate(Utility.stringToDate(transactionDate));
			List<Object[]> records = queryReportDataFromCashView(transactionDate, orgId, accidcHeadId, fromDatess);
			List<Object[]> OpeningBalanceBankList = findOpeningBalanceAmount(accidcHeadId, orgId, financialYearId);

			for (Object[] voucherentity : records) {

				if (voucherentity[0] != null) {
					dto = new AccountFinancialReportDTO();
					dto.setAccountCode(voucherentity[0].toString());

					BigDecimal drAnumber = new BigDecimal(voucherentity[1].toString(), MathContext.DECIMAL64);
					BigDecimal crAnumber = new BigDecimal(voucherentity[2].toString(), MathContext.DECIMAL64);
					totalDrAmount = drAnumber;
					totalCrAmount = crAnumber;
					BigDecimal totlDrAmount = totalDrAmount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
					BigDecimal totlCrAmount = totalCrAmount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
					dto.setClosingBalance(totlDrAmount.subtract(totlCrAmount));

					for (Object[] OpeningBalanceBankLists : OpeningBalanceBankList) {
						if (OpeningBalanceBankLists[1].equals(drId)) {
							if (OpeningBalanceBankLists[0] != null && !totalCrAmount.equals(new BigDecimal(0.00))
									&& !totalDrAmount.equals(new BigDecimal(0.00))) {
								BigDecimal closingBalance = (new BigDecimal((String) OpeningBalanceBankLists[0]))
										.add(totalDrAmount).subtract(totalCrAmount);
								dto.setClosingBalance(closingBalance.setScale(2, RoundingMode.HALF_EVEN));
							} else if (OpeningBalanceBankLists[0] != null && !totalCrAmount.equals(new BigDecimal(0.00))
									&& totalDrAmount.equals(new BigDecimal(0.00))) {
								BigDecimal closingBalance = (new BigDecimal((String) OpeningBalanceBankLists[0]))
										.subtract(totalCrAmount);
								dto.setClosingBalance(closingBalance.setScale(2, RoundingMode.HALF_EVEN));

							} else if (OpeningBalanceBankLists[0] != null && totalCrAmount.equals(new BigDecimal(0.00))
									&& !totalDrAmount.equals(new BigDecimal(0.00))) {
								BigDecimal closingBalance = (new BigDecimal((String) OpeningBalanceBankLists[0]))
										.add(totalDrAmount);
								BigDecimal amount = closingBalance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
								dto.setClosingBalance(amount.setScale(2, RoundingMode.HALF_EVEN));
							}
						}

						else if (OpeningBalanceBankLists[1].equals(crId)) {
							if (OpeningBalanceBankLists[0] != null && !totalCrAmount.equals(new BigDecimal(0.00))
									&& !totalDrAmount.equals(new BigDecimal(0.00))) {
								BigDecimal closingBalance = (new BigDecimal((String) OpeningBalanceBankLists[0]))
										.add(totalCrAmount).subtract(totalDrAmount);
								dto.setClosingBalance(closingBalance.setScale(2, RoundingMode.HALF_EVEN));
							} else if (OpeningBalanceBankLists[0] != null && !totalCrAmount.equals(new BigDecimal(0.00))
									&& totalDrAmount.equals(new BigDecimal(0.00))) {
								BigDecimal closingBalance = (new BigDecimal((String) OpeningBalanceBankLists[0]))
										.subtract(totalCrAmount);
								dto.setClosingBalance(closingBalance.setScale(2, RoundingMode.HALF_EVEN));
							} else if (OpeningBalanceBankLists[0] != null && totalCrAmount.equals(new BigDecimal(0.00))
									&& !totalDrAmount.equals(new BigDecimal(0.00))) {
								BigDecimal closingBalance = (new BigDecimal((String) OpeningBalanceBankLists[0]))
										.add(totalDrAmount);
								BigDecimal amount = closingBalance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
								dto.setClosingBalance(amount.setScale(2, RoundingMode.HALF_EVEN));
							}
						}

					}
					totalAmount = totalAmount.add(dto.getClosingBalance());
					list.add(dto);
				}

			}

		}
		if (cashequivalent.getClosingBalance() != null) {
			cashequivalent.setTotalbankAmts(totalAmount.add(cashequivalent.getClosingBalance()));
		} else {
			cashequivalent.setTotalbankAmts(totalAmount);
		}
		cashequivalent.setTransactionDate(transactionDate);
		cashequivalent.setListCashEquvalent(list);
		model.addAttribute(REPORT_DATA, cashequivalent);

	}

	private void approvedReAppropriationReport(ModelMap model, long orgId, long financialId) {
		// TODO Auto-generated method stub
		final List<Object[]> ReAppropriationDec = accountFinancialReportRepository.queryReAppReptExpIncrement(orgId,
				financialId);
		String fromDate = null;
		String toDate = null;
		List<Object[]> frmdateTodate = accountFinancialReportRepository.getAllFinincialFromDate(financialId);
		for (Object[] dateEntity : frmdateTodate) {
			fromDate = dateEntity[1].toString();
			toDate = dateEntity[2].toString();
		}
		SimpleDateFormat fdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat fdf2 = new SimpleDateFormat("dd/MM/yyyy");
		try {
			fromDate = fdf2.format(fdf1.parse(fromDate));// 2014-04-01
			toDate = fdf2.format(fdf1.parse(toDate));// 2017-03-31

		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date utilFromDates = Utility.stringToDate(fromDate);
		Date utiltoDates = Utility.stringToDate(toDate);
		long financialYearId = financialId;
		String financialYears = findFinancialYearByFinancialYearId(financialYearId);
		AccountFinancialReportDTO dto = new AccountFinancialReportDTO();
		List<AccountFinancialReportDTO> list = new ArrayList<>();
		final AccountFinancialReportDTO appropriation = new AccountFinancialReportDTO();
		BigDecimal totalBudgetAmount = BigDecimal.ZERO;
		BigDecimal totalTransAmt = BigDecimal.ZERO;

		BigDecimal totalDecAmt = BigDecimal.ZERO;
		List<Object[]> ReAppropriationUtiliAmt = accountFinancialReportRepository.queryReAppReptExpUtilizationAmt(orgId,
				financialId, utilFromDates, utiltoDates);

		for (Object[] Entity : ReAppropriationDec) {
			dto = new AccountFinancialReportDTO();
			BigDecimal totalSanctionAmt = BigDecimal.ZERO;
			BigDecimal BalanceAvl = BigDecimal.ZERO;
			BigDecimal utilValue = BigDecimal.ZERO;
			BigDecimal totalBudSacAmt = BigDecimal.ZERO;
			long budgetCodeId = Long.valueOf(Entity[0].toString());

			if (ReAppropriationUtiliAmt != null && !ReAppropriationUtiliAmt.isEmpty()) {

				for (Object[] reApprUtiliAmt : ReAppropriationUtiliAmt) {
					long newbudgetCodeId = Long.valueOf(reApprUtiliAmt[0].toString());

					if (budgetCodeId == newbudgetCodeId) {
						dto.setTotalUtilamt(CommonMasterUtility.getAmountInIndianCurrency(
								new BigDecimal(reApprUtiliAmt[2].toString()).setScale(2, RoundingMode.HALF_EVEN)));
						utilValue = (new BigDecimal(reApprUtiliAmt[2].toString())).setScale(2, RoundingMode.HALF_EVEN);
					}
				}
			} else {
				dto.setTotalUtilamt(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
			}

			if (dto.getTotalUtilamt() == null || dto.getTotalUtilamt().isEmpty()) {
				dto.setTotalUtilamt(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
			}

			if (Entity[1] != null) {
				int i;
				for (i = 0; i < Entity[1].toString().length(); i++) {
					char c = Entity[1].toString().charAt(i);
					if (c >= '0' && c > '9')
						break;
				}
				String acounthead = Entity[1].toString().substring(0, i);
				acounthead = acounthead.substring(0, acounthead.lastIndexOf(" - "));
				String accountCode = Entity[1].toString().substring(i);
				dto.setAccountHead(accountCode);
				dto.setAccountCode(acounthead);
			}
			dto.setOriginalEst(CommonMasterUtility.getAmountInIndianCurrency(
					new BigDecimal(Entity[2].toString()).setScale(2, RoundingMode.HALF_EVEN)));

			if (Entity[2] != null) {
				totalBudgetAmount = totalBudgetAmount
						.add(new BigDecimal(Entity[2].toString()).setScale(2, RoundingMode.HALF_EVEN));
			}

			dto.setTransferAmt(((BigDecimal) Entity[3]).setScale(2, RoundingMode.HALF_EVEN));

			if (Entity[3] != null) {
				totalTransAmt = totalTransAmt
						.add(new BigDecimal(Entity[3].toString()).setScale(2, RoundingMode.HALF_EVEN));
				totalDecAmt = totalDecAmt.add(new BigDecimal(Entity[3].toString()).setScale(2, RoundingMode.HALF_EVEN));
			}

			if (Entity[2] != null || Entity[3] != null) {
				totalSanctionAmt = totalSanctionAmt.add(new BigDecimal(Entity[2].toString())
						.add(new BigDecimal(Entity[3].toString())).setScale(2, RoundingMode.HALF_EVEN));

				totalBudSacAmt = totalBudSacAmt.add(totalSanctionAmt.setScale(2, RoundingMode.HALF_EVEN));
			}

			if (dto.getTotalUtilamt() != null) {

				BalanceAvl = BalanceAvl.add(new BigDecimal(totalSanctionAmt.toString())
						.subtract(new BigDecimal(utilValue.toString())).setScale(2, RoundingMode.HALF_EVEN));
				dto.setTotalBalance(CommonMasterUtility.getAmountInIndianCurrency(
						new BigDecimal(BalanceAvl.toString()).setScale(2, RoundingMode.HALF_EVEN)));

			} else {
				dto.setTotalBalance(CommonMasterUtility
						.getAmountInIndianCurrency(totalBudSacAmt.setScale(2, RoundingMode.HALF_EVEN)));
			}
			dto.setBudgetSactionAmt(CommonMasterUtility
					.getAmountInIndianCurrency(totalSanctionAmt.setScale(2, RoundingMode.HALF_EVEN)));

			dto.setLmodate(Utility.dateToString((Date) Entity[4]));

			if (Entity[5] != null) {
				dto.setUserId(Entity[5].toString());
				Employee employeeEntity = employeeJpaRepository.findOne(Long.valueOf(Entity[5].toString()));
				String empName = "";
				if (employeeEntity != null) {
					if ((employeeEntity.getEmpmname() != null) && !employeeEntity.getEmpmname().isEmpty()) {
						empName = employeeEntity.getEmpname() + " " + employeeEntity.getEmpmname() + " "
								+ employeeEntity.getEmplname();
					} else {
						empName = employeeEntity.getEmpname() + " " + employeeEntity.getEmplname();
					}
					if (empName != null && !empName.isEmpty()) {
						dto.setAuthorizedBy(empName);
					}
				}
			}

			if (Entity[6] != null) {
				dto.setRemarks(Entity[6].toString());
			}
			list.add(dto);
		}

		AccountFinancialReportDTO reappropritiondto = null;
		List<AccountFinancialReportDTO> decreaseList = new ArrayList<>();
		final List<Object[]> ReAppropriationInc = accountFinancialReportRepository.queryReAppReptExpDecrement(orgId,
				financialId);

		for (Object[] Entity : ReAppropriationInc) {
			reappropritiondto = new AccountFinancialReportDTO();
			BigDecimal totalDecSanctionAmt = BigDecimal.ZERO;
			BigDecimal totalIncreamentAmt = BigDecimal.ZERO;
			BigDecimal BalanceAvlDeec = BigDecimal.ZERO;
			BigDecimal utilValueDec = BigDecimal.ZERO;
			BigDecimal totalBudSacAmt = BigDecimal.ZERO;
			long budgetCodeId = Long.valueOf(Entity[0].toString());
			if (ReAppropriationUtiliAmt != null && !ReAppropriationUtiliAmt.isEmpty()) {

				for (Object[] reApprUtiliAmt : ReAppropriationUtiliAmt) {
					long newbudgetCodeId = Long.valueOf(reApprUtiliAmt[0].toString());

					if (budgetCodeId == newbudgetCodeId) {
						reappropritiondto.setTotalUtilamt(CommonMasterUtility.getAmountInIndianCurrency(
								new BigDecimal(reApprUtiliAmt[2].toString()).setScale(2, RoundingMode.HALF_EVEN)));
						utilValueDec = (new BigDecimal(reApprUtiliAmt[2].toString()).setScale(2,
								RoundingMode.HALF_EVEN));
					}
				}
			} else {
				reappropritiondto.setTotalUtilamt(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
			}

			if (reappropritiondto.getTotalUtilamt() == null || reappropritiondto.getTotalUtilamt().isEmpty()) {
				reappropritiondto.setTotalUtilamt(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
			}

			if (Entity[1] != null) {
				int i;
				for (i = 0; i < Entity[1].toString().length(); i++) {
					char c = Entity[1].toString().charAt(i);
					if (c >= '0' && c > '9')
						break;
				}
				String acounthead = Entity[1].toString().substring(0, i);
				acounthead = acounthead.substring(0, acounthead.lastIndexOf(" - "));
				String accountCode = Entity[1].toString().substring(i);
				reappropritiondto.setAccountHead(accountCode);
				reappropritiondto.setAccountCode(acounthead);
			}
			reappropritiondto.setOriginalEst(CommonMasterUtility.getAmountInIndianCurrency(
					new BigDecimal(Entity[2].toString()).setScale(2, RoundingMode.HALF_EVEN)));

			if (Entity[2] != null) {
				totalBudgetAmount = totalBudgetAmount
						.add(new BigDecimal(Entity[2].toString()).setScale(2, RoundingMode.HALF_EVEN));
			}

			reappropritiondto.setTransferAmt(((BigDecimal) Entity[3]).setScale(2, RoundingMode.HALF_EVEN));

			if (Entity[3] != null) {
				totalTransAmt = totalTransAmt
						.add(new BigDecimal(Entity[3].toString()).setScale(2, RoundingMode.HALF_EVEN));
				totalIncreamentAmt = totalIncreamentAmt
						.add(new BigDecimal(Entity[3].toString()).setScale(2, RoundingMode.HALF_EVEN));
			}

			if (Entity[2] != null || Entity[3] != null) {
				totalDecSanctionAmt = totalDecSanctionAmt.add(new BigDecimal(Entity[2].toString())
						.subtract(new BigDecimal(Entity[3].toString())).setScale(2, RoundingMode.HALF_EVEN));

				totalBudSacAmt = totalBudSacAmt.add(totalDecSanctionAmt);
			}

			if (reappropritiondto.getTotalUtilamt() != null) {
				BalanceAvlDeec = BalanceAvlDeec.add(new BigDecimal(totalDecSanctionAmt.toString())
						.subtract(new BigDecimal(utilValueDec.toString())).setScale(2, RoundingMode.HALF_EVEN));
				reappropritiondto.setTotalBalance(CommonMasterUtility.getAmountInIndianCurrency(
						new BigDecimal(BalanceAvlDeec.toString()).setScale(2, RoundingMode.HALF_EVEN)));
			} else {
				reappropritiondto.setTotalBalance(CommonMasterUtility
						.getAmountInIndianCurrency(totalDecSanctionAmt.setScale(2, RoundingMode.HALF_EVEN)));
			}

			reappropritiondto.setBudgetSactionAmt(CommonMasterUtility
					.getAmountInIndianCurrency(totalDecSanctionAmt.setScale(2, RoundingMode.HALF_EVEN)));

			reappropritiondto.setLmodate(Utility.dateToString((Date) Entity[4]));

			if (Entity[5] != null) {
				reappropritiondto.setUserId(Entity[5].toString());
				Employee employeeEntity = employeeJpaRepository.findOne(Long.valueOf(Entity[5].toString()));
				String empName = "";
				if (employeeEntity != null) {
					if ((employeeEntity.getEmpmname() != null) && !employeeEntity.getEmpmname().isEmpty()) {
						empName = employeeEntity.getEmpname() + " " + employeeEntity.getEmpmname() + " "
								+ employeeEntity.getEmplname();
					} else {
						empName = employeeEntity.getEmpname() + " " + employeeEntity.getEmplname();
					}
					if (empName != null && !empName.isEmpty()) {
						reappropritiondto.setAuthorizedBy(empName);
					}
				}
			}
			if (Entity[6] != null) {
				reappropritiondto.setRemarks(Entity[6].toString());
			}
			decreaseList.add(reappropritiondto);
		}

		final List<Object[]> reAppropriationReceiptDec = accountFinancialReportRepository
				.queryReAppReptReceiptDecrement(orgId, financialId);

		List<Object[]> ReAppropriationUtiliAmtofPayment = accountFinancialReportRepository
				.queryReAppropriationReportUtilTotalAmtPayment(orgId, financialId, utilFromDates, utiltoDates);
		AccountFinancialReportDTO receiptdto = null;
		List<AccountFinancialReportDTO> receiptdtoList = new ArrayList<>();
		if (reAppropriationReceiptDec != null && !reAppropriationReceiptDec.isEmpty()) {

			for (Object[] receiptEntity : reAppropriationReceiptDec) {
				receiptdto = new AccountFinancialReportDTO();
				BigDecimal totalIncreamentAmts = BigDecimal.ZERO;
				BigDecimal utilValue = BigDecimal.ZERO;
				BigDecimal BalanceAvl = BigDecimal.ZERO;
				long budgetCodeId = Long.valueOf(receiptEntity[0].toString());

				if (ReAppropriationUtiliAmtofPayment != null && !ReAppropriationUtiliAmtofPayment.isEmpty()) {

					for (Object[] reApprUtiliAmt : ReAppropriationUtiliAmtofPayment) {
						long newbudgetCodeId = Long.valueOf(reApprUtiliAmt[0].toString());
						if (budgetCodeId == newbudgetCodeId) {
							receiptdto.setTotalUtilamt(CommonMasterUtility.getAmountInIndianCurrency(
									new BigDecimal(reApprUtiliAmt[2].toString()).setScale(2, RoundingMode.HALF_EVEN)));
							utilValue = (new BigDecimal(reApprUtiliAmt[2].toString()).setScale(2,
									RoundingMode.HALF_EVEN));
						}
					}
				} else {
					receiptdto.setTotalUtilamt(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
				}

				if (receiptdto.getTotalUtilamt() == null || receiptdto.getTotalUtilamt().isEmpty()) {
					receiptdto.setTotalUtilamt(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
				}

				if (receiptEntity[1] != null) {
					int i;
					for (i = 0; i < receiptEntity[1].toString().length(); i++) {
						char c = receiptEntity[1].toString().charAt(i);
						if (c >= '0' && c > '9')
							break;
					}
					String acounthead = receiptEntity[1].toString().substring(0, i);
					acounthead = acounthead.substring(0, acounthead.lastIndexOf(" - "));
					String accountCode = receiptEntity[1].toString().substring(i);
					receiptdto.setAccountHead(accountCode);
					receiptdto.setAccountCode(acounthead);
				}

				if (receiptEntity[2] != null) {
					receiptdto.setOriginalEst(CommonMasterUtility.getAmountInIndianCurrency(
							new BigDecimal(receiptEntity[2].toString()).setScale(2, RoundingMode.HALF_EVEN)));
				}

				if (receiptEntity[3] != null) {
					receiptdto.setTransferAmt(((BigDecimal) receiptEntity[3]).setScale(2, RoundingMode.HALF_EVEN));

				}

				if (receiptEntity[2] != null && receiptEntity[3] != null) {
					totalIncreamentAmts = totalIncreamentAmts.add(new BigDecimal(receiptEntity[2].toString())
							.subtract(new BigDecimal(receiptEntity[3].toString())));
					receiptdto.setTotalbudgetSacAmt(totalIncreamentAmts.setScale(2, RoundingMode.HALF_EVEN).toString());

				}

				if (receiptdto.getTotalUtilamt() != null) {

					BalanceAvl = BalanceAvl.add(new BigDecimal(totalIncreamentAmts.toString())
							.subtract(new BigDecimal(utilValue.toString()).setScale(2, RoundingMode.HALF_EVEN)));
					receiptdto.setTotalBalance(CommonMasterUtility.getAmountInIndianCurrency(
							(new BigDecimal(BalanceAvl.toString())).setScale(2, RoundingMode.HALF_EVEN)));

				} else {
					receiptdto.setTotalBalance(CommonMasterUtility
							.getAmountInIndianCurrency(totalIncreamentAmts.setScale(2, RoundingMode.HALF_EVEN)));
				}

				if (receiptEntity[4] != null) {
					receiptdto.setLmodate(Utility.dateToString((Date) receiptEntity[4]));
				}

				if (receiptEntity[5] != null) {
					receiptdto.setUserId(receiptEntity[5].toString());
					Employee employeeEntity = employeeJpaRepository.findOne(Long.valueOf(receiptEntity[5].toString()));
					String empName = "";
					if (employeeEntity != null) {
						if ((employeeEntity.getEmpmname() != null) && !employeeEntity.getEmpmname().isEmpty()) {
							empName = employeeEntity.getEmpname() + " " + employeeEntity.getEmpmname() + " "
									+ employeeEntity.getEmplname();
						} else {
							empName = employeeEntity.getEmpname() + " " + employeeEntity.getEmplname();
						}
						if (empName != null && !empName.isEmpty()) {
							receiptdto.setAuthorizedBy(empName);
						}
					}
				}
				if (receiptEntity[6] != null) {
					receiptdto.setRemarks(receiptEntity[6].toString());
				}
				receiptdtoList.add(receiptdto);
			}
		}

		final List<Object[]> reAppropriationReceiptInc = accountFinancialReportRepository
				.queryReAppReptReceiptIncrement(orgId, financialId);
		AccountFinancialReportDTO receiptIncdto = null;
		List<AccountFinancialReportDTO> receiptIncList = new ArrayList<>();
		if (reAppropriationReceiptInc != null && !reAppropriationReceiptInc.isEmpty()) {

			for (Object[] receiptIncEntity : reAppropriationReceiptInc) {
				receiptIncdto = new AccountFinancialReportDTO();
				BigDecimal totalIncreamentAmts = BigDecimal.ZERO;
				BigDecimal utilValueInc = BigDecimal.ZERO;
				BigDecimal BalanceAvl = BigDecimal.ZERO;
				long budgetCodeId = Long.valueOf(receiptIncEntity[0].toString());

				if (ReAppropriationUtiliAmtofPayment != null && !ReAppropriationUtiliAmtofPayment.isEmpty()) {
					for (Object[] reApprUtiliAmt : ReAppropriationUtiliAmtofPayment) {
						long newbudgetCodeId = Long.valueOf(reApprUtiliAmt[0].toString());
						if (budgetCodeId == newbudgetCodeId) {
							receiptIncdto.setTotalUtilamt(CommonMasterUtility.getAmountInIndianCurrency(
									new BigDecimal(reApprUtiliAmt[2].toString()).setScale(2, RoundingMode.HALF_EVEN)));
							utilValueInc = (new BigDecimal(reApprUtiliAmt[2].toString()).setScale(2,
									RoundingMode.HALF_EVEN));
						}
					}
				} else {
					receiptIncdto.setTotalUtilamt(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
				}

				if (receiptIncdto.getTotalUtilamt() == null || receiptIncdto.getTotalUtilamt().isEmpty()) {
					receiptIncdto.setTotalUtilamt(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
				}

				if (receiptIncEntity[1] != null) {
					int i;
					for (i = 0; i < receiptIncEntity[1].toString().length(); i++) {
						char c = receiptIncEntity[1].toString().charAt(i);
						if (c >= '0' && c > '9')
							break;
					}
					String acounthead = receiptIncEntity[1].toString().substring(0, i);
					acounthead = acounthead.substring(0, acounthead.lastIndexOf(" - "));
					String accountCode = receiptIncEntity[1].toString().substring(i);
					receiptIncdto.setAccountHead(accountCode);
					receiptIncdto.setAccountCode(acounthead);
				}

				if (receiptIncEntity[2] != null) {
					receiptIncdto.setOriginalEst(CommonMasterUtility.getAmountInIndianCurrency(
							new BigDecimal(receiptIncEntity[2].toString()).setScale(2, RoundingMode.HALF_EVEN)));
				}

				if (receiptIncEntity[3] != null) {
					receiptIncdto
							.setTransferAmt(((BigDecimal) receiptIncEntity[3]).setScale(2, RoundingMode.HALF_EVEN));

				}

				if (receiptIncEntity[2] != null && receiptIncEntity[3] != null) {
					totalIncreamentAmts = totalIncreamentAmts.add(new BigDecimal(receiptIncEntity[2].toString())
							.add(new BigDecimal(receiptIncEntity[3].toString())));
					receiptIncdto.setTotalbudgetSacAmt(CommonMasterUtility
							.getAmountInIndianCurrency(totalIncreamentAmts.setScale(2, RoundingMode.HALF_EVEN)));

				}

				if (receiptIncdto.getTotalUtilamt() != null) {
					BalanceAvl = BalanceAvl.add(new BigDecimal(totalIncreamentAmts.toString())
							.subtract(new BigDecimal(utilValueInc.toString())));
					receiptIncdto.setTotalBalance(CommonMasterUtility
							.getAmountInIndianCurrency(BalanceAvl.setScale(2, RoundingMode.HALF_EVEN)));
				} else {
					receiptIncdto.setTotalBalance(CommonMasterUtility
							.getAmountInIndianCurrency(totalIncreamentAmts.setScale(2, RoundingMode.HALF_EVEN)));
				}

				if (receiptIncEntity[4] != null) {
					receiptIncdto.setLmodate(Utility.dateToString((Date) receiptIncEntity[4]));
				}

				if (receiptIncEntity[5] != null) {
					receiptIncdto.setUserId(receiptIncEntity[5].toString());
					Employee employeeEntity = employeeJpaRepository
							.findOne(Long.valueOf(receiptIncEntity[5].toString()));
					String empName = "";
					if ((employeeEntity.getEmpmname() != null) && !employeeEntity.getEmpmname().isEmpty()) {
						empName = employeeEntity.getEmpname() + " " + employeeEntity.getEmpmname() + " "
								+ employeeEntity.getEmplname();
					} else {
						empName = employeeEntity.getEmpname() + " " + employeeEntity.getEmplname();
					}
					receiptIncdto.setAuthorizedBy(empName);
				}
				if (receiptIncEntity[6] != null) {
					receiptIncdto.setRemarks(receiptIncEntity[6].toString());
				}
				receiptIncList.add(receiptIncdto);
			}

		}

		appropriation.setListofreceiptinc(receiptIncList);
		appropriation.setListofreceiptdec(receiptdtoList);
		appropriation.setListDecReAppropriation(decreaseList);
		appropriation.setFinancialYear(financialYears);
		appropriation.setListReAppropriation(list);
		model.addAttribute(REPORT_DATA, appropriation);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> queryReportDataFromCashView(String transactionDate, Long org, Long accidcHeadId,
			Date fromDatess) {
		if ((transactionDate == null) || transactionDate.isEmpty()) {
			throw new IllegalArgumentException("todate cannot be null or empty [from date=" + transactionDate + "]");
		}
		final Date transactionsDate = Utility.stringToDate(transactionDate);
		return accountFinancialReportRepository.queryReportDataCashEquivalent(transactionsDate, org, accidcHeadId,
				fromDatess);
	}

	private void dayBookReport(ModelMap model, String fromDate, String toDate, long orgId) {
		BigDecimal totalCrAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal totalDrAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

		List<Object[]> recordsOfDayBook = findDayBookFromVoucherDetailViewEntity(fromDate, toDate, orgId);

		List<AccountFinancialReportDTO> listOfDto = new ArrayList<>();
		AccountFinancialReportDTO finalAccountFinancialReportDTO = new AccountFinancialReportDTO();

		if (recordsOfDayBook.isEmpty() || recordsOfDayBook == null) {

			model.addAttribute(VALIDATION_ERROR, AccountConstants.Y.getValue());
			LOGGER.error("No Records found for report from voucherDetailViewEntity input[fromDate=" + fromDate
					+ " todate=" + toDate + " orgid=" + orgId);
		} else {

			for (Object[] voucherDetailViewEntity : recordsOfDayBook) {

				AccountFinancialReportDTO accountFinancialReportDTO = new AccountFinancialReportDTO();
				if (voucherDetailViewEntity[0] != null) {
					accountFinancialReportDTO.setVoucherDate(Utility.dateToString((Date) voucherDetailViewEntity[0]));
				}
				if (voucherDetailViewEntity[1] != null) {
					accountFinancialReportDTO.setVoucherType(CommonMasterUtility
							.findLookUpDesc(AccountPrefix.VOT.toString(), orgId, (long) voucherDetailViewEntity[1]));
				}
				if (voucherDetailViewEntity[2] != null) {
					accountFinancialReportDTO.setVoucherNo((String) voucherDetailViewEntity[2]);
				}
				if (voucherDetailViewEntity[3] != null) {
					accountFinancialReportDTO.setVoucherSubType(CommonMasterUtility
							.findLookUpDesc(AccountPrefix.TDP.toString(), orgId, (long) voucherDetailViewEntity[3]));
				}
				if (voucherDetailViewEntity[4] != null) {
					accountFinancialReportDTO.setReceiptNumber((String) voucherDetailViewEntity[4]);
				}
				if (voucherDetailViewEntity[5] != null) {
					accountFinancialReportDTO.setReceiptDate(Utility.dateToString((Date) voucherDetailViewEntity[5]));
				}
				if (voucherDetailViewEntity[6] != null) {
					accountFinancialReportDTO.setParticular((String) voucherDetailViewEntity[6]);
				}

				if (voucherDetailViewEntity[7] != null) {
					final String drcrCpdId = CommonMasterUtility.findLookUpCode(PrefixConstants.DCR, orgId,
							(long) voucherDetailViewEntity[8]);
					if (drcrCpdId.equals(PrefixConstants.AccountJournalVoucherEntry.CR)) {
						accountFinancialReportDTO.setCrAmountIndianCurrency(
								CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) voucherDetailViewEntity[7]));
						totalCrAmount = totalCrAmount
								.add(((BigDecimal) voucherDetailViewEntity[7]).setScale(2, RoundingMode.HALF_EVEN));
					} else {
						accountFinancialReportDTO.setDrAmountIndianCurrency(
								CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) voucherDetailViewEntity[7]));
						totalDrAmount = totalDrAmount
								.add(((BigDecimal) voucherDetailViewEntity[7]).setScale(2, RoundingMode.HALF_EVEN));
					}
				}

				if (voucherDetailViewEntity[9] != null) {
					String acountheadcode = secondaryheadMasterService
							.findByAccountHead((Long) voucherDetailViewEntity[9]);
					accountFinancialReportDTO.setAccountHead(acountheadcode);
				}

				listOfDto.add(accountFinancialReportDTO);

			}

			finalAccountFinancialReportDTO.setListOfDayBook(listOfDto);
			finalAccountFinancialReportDTO
					.setCrAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(totalCrAmount));
			finalAccountFinancialReportDTO
					.setDrAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(totalDrAmount));
			finalAccountFinancialReportDTO.setFromDate(fromDate);
			finalAccountFinancialReportDTO.setToDate(toDate);
			model.addAttribute(REPORT_DATA, finalAccountFinancialReportDTO);

		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findDayBookFromVoucherDetailViewEntity(String fromDate, String toDate, long orgId) {

		final Date fromDates = Utility.stringToDate(fromDate);
		final Date toDates = Utility.stringToDate(toDate);

		return accountFinancialReportRepository.findDayBookFromVoucherDetailViewEntity(fromDates, toDates, orgId);
	}

	private BigDecimal findVoucherNumberAndPaymentAmount(String toDate, String fromDate, Long orgId, Long depId) {

		final Date toDates = Utility.stringToDate(toDate);
		final Date fromDates = Utility.stringToDate(fromDate);
		return accountFinancialReportRepository.findVoucherNumberAndPaymentAmount(toDates, fromDates, orgId, depId);

	}

	private BigDecimal findDeductionAmount(String toDate, String fromDate, Long orgId, Long depId) {
		final Date toDates = Utility.stringToDate(toDate);
		final Date fromDates = Utility.stringToDate(fromDate);
		return accountFinancialReportRepository.findDeductionAmount(toDates, fromDates, orgId, depId);
	}

	private BigDecimal findVoucherNumber(String toDate, String fromDate, Long orgId, Long depId) {
		final Date toDates = Utility.stringToDate(toDate);
		final Date fromDates = Utility.stringToDate(fromDate);
		return accountFinancialReportRepository.findVoucherNumber(toDates, fromDates, orgId, depId);
	}

	private void processOpeningBalanceReport(ModelMap model, Long financialYearId, Long orgId) {
		BigDecimal totalCrAmount = new BigDecimal(0.00);
		BigDecimal totalDrAmount = new BigDecimal(0.00);
		String financialYears = findFinancialYearByFinancialYearId(financialYearId);
		AccountFinancialReportDTO openingBalanceaccountFinancialReportDTO = new AccountFinancialReportDTO();
		List<AccountFinancialReportDTO> listOfOpeningBalance = new ArrayList<>();
		List<Object[]> openingBalanceDetails = findOpeningBalanceReport(financialYearId, orgId);
		if (openingBalanceDetails.isEmpty() || openingBalanceDetails == null) {
			model.addAttribute(VALIDATION_ERROR, AccountConstants.Y.getValue());
			LOGGER.error("No Records found for report from AccountBudgetOpenBalanceEntity input[financialYearId="
					+ financialYearId + " orgid=" + orgId);
		} else {
			for (Object[] openingBalanceDetail : openingBalanceDetails) {

				AccountFinancialReportDTO accountFinancialReportDTO = new AccountFinancialReportDTO();

				if (openingBalanceDetail[0] != null) {
					accountFinancialReportDTO.setAccountHeadId((Long) openingBalanceDetail[0]);
				}
				if (openingBalanceDetail[1] != null) {
					accountFinancialReportDTO.setAccountCode((String) openingBalanceDetail[1]);
				}
				if (openingBalanceDetail[2] != null) {
					accountFinancialReportDTO.setOpeningBalance(new BigDecimal((String) openingBalanceDetail[2]));
					accountFinancialReportDTO.setOpeningBalanceIndianCurrency(CommonMasterUtility
							.getAmountInIndianCurrency(accountFinancialReportDTO.getOpeningBalance()));
					final String drcrCpdId1 = CommonMasterUtility.findLookUpCode(PrefixConstants.DCR, orgId,
							(long) openingBalanceDetail[3]);
					if (drcrCpdId1.equals(PrefixConstants.AccountJournalVoucherEntry.CR)) {
						totalCrAmount = totalCrAmount.add(accountFinancialReportDTO.getOpeningBalance());
					} else {
						totalDrAmount = totalDrAmount.add(accountFinancialReportDTO.getOpeningBalance());
					}
				}
				if (openingBalanceDetail[3] != null) {
					final String drcrCpdId = CommonMasterUtility.findLookUpCode(PrefixConstants.DCR, orgId,
							(long) openingBalanceDetail[3]);
					accountFinancialReportDTO.setBalanceType(drcrCpdId);
				}
				if (openingBalanceDetail[4] != null) {
					accountFinancialReportDTO.setFinalizedFlag((String) openingBalanceDetail[4]);
				}

				if (accountFinancialReportDTO.getOpeningBalance() != null
						&& !(accountFinancialReportDTO.getOpeningBalance().compareTo(BigDecimal.ZERO) == 0)) {
					listOfOpeningBalance.add(accountFinancialReportDTO);
				}
			}
		}
		if (StringUtils.isNotEmpty(financialYears))
			openingBalanceaccountFinancialReportDTO.setFinancialYear(financialYears);
		openingBalanceaccountFinancialReportDTO
				.setTotalDrAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(totalDrAmount));
		openingBalanceaccountFinancialReportDTO
				.setTotalCrAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(totalCrAmount));
		openingBalanceaccountFinancialReportDTO.setListOfOpeningBalance(listOfOpeningBalance);

		model.addAttribute(REPORT_DATA, openingBalanceaccountFinancialReportDTO);

	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findOpeningBalanceReport(Long financialYearId, Long orgId) {

		return accountFinancialReportRepository.findOpeningBalanceReport(orgId, financialYearId);
	}

	private void paymentChequeRegister(final ModelMap model, long orgId, final String fromDate, String toDate,Long accountHeadId,Long fieldId) {
		BigDecimal totalPaymentAmt = new BigDecimal(0.00);
		int index = 0;
		AccountFinancialReportDTO paymentdto = null;
		List<AccountFinancialReportDTO> chequepaymentregister = new ArrayList<>();
		final AccountFinancialReportDTO chequepayment = new AccountFinancialReportDTO();

		final List<Object[]> paymentrecords = accountFinanceReportDAO
				.querypaymentChequeReport(Utility.stringToDate(fromDate), orgId, Utility.stringToDate(toDate),accountHeadId,fieldId);
		long EmpId = 0;
		if (paymentrecords != null && !paymentrecords.isEmpty()) {
			for (Object[] paymententity : paymentrecords) {
				paymentdto = new AccountFinancialReportDTO();

				paymentdto.setPaymentNo(paymententity[0].toString());

				paymentdto.setPaymentDates(Utility.dateToString((Date) paymententity[1]));

				if (paymententity[2] != null) {
					paymentdto.setBillNo(paymententity[2].toString());
				}

				if (paymententity[6] != null) {
					paymentdto.setBillEntryDate(Utility.dateToString((Date) paymententity[6]));
				}

				if (paymententity[3] != null) {
					paymentdto.setVendorName(paymententity[3].toString());
				}

				if (paymententity[4] != null) {
					paymentdto.setParticular(paymententity[4].toString());
				}
				if (paymententity[5] != null) {
					paymentdto.setChequeNo(paymententity[5].toString());
				}

				if (paymententity[7] != null) {
					paymentdto.setPaymentAmnt(
							CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(paymententity[7].toString())));
					totalPaymentAmt = totalPaymentAmt.add(new BigDecimal(paymententity[7].toString()));
				}

				if (paymententity[8] != null) {
					paymentdto.setIssuanceDate(Utility.dateToString((Date) paymententity[8]));
				}

				if (paymententity[9] != null) {
					paymentdto.setChequeIssueDate(Utility.dateToString((Date) paymententity[9]));
				}

				if (paymententity[10] != null) {
					EmpId = Long.valueOf(paymententity[10].toString());
					String userName = accountFinancialReportRepository.queryReAppropriationReportForUser(EmpId);
					paymentdto.setApprovedBy(userName);
				}
				if(paymentdto.getVendorName()==null && paymententity[11] != null) {
					paymentdto.setVendorName(vendorMasterService.getVendorNameById(Long.valueOf(paymententity[11].toString()), orgId));
				}
				index = index + 1;
				paymentdto.setIndex(index);
				chequepaymentregister.add(paymentdto);
			}
		}
		chequepayment.setListofchequepayment(chequepaymentregister);
		chequepayment.setTotalPaymentAmnt(CommonMasterUtility.getAmountInIndianCurrency(totalPaymentAmt));
		chequepayment.setFromDate(fromDate);
		chequepayment.setToDate(toDate);
		model.addAttribute("bankAccountHead", accountChequeDishonourService.getBankAccountData(orgId).get(accountHeadId));
		model.addAttribute("fieldCode", tbAcFieldMasterService.getFieldMasterLastLevels(UserSession.getCurrent().getOrganisation().getOrgid()).get(fieldId));
		model.addAttribute(REPORT_DATA, chequepayment);
		

	}

	@Override
	@Transactional(readOnly = true)
	public String findFinancialYearByFinancialYearId(Long financialYearId) {
		List<Object[]> financialYear = tbFinancialyearJpaRepository.getFinanceYearFrmDate(financialYearId);
		String fromDate = null;
		String toDate = null;
		String financialYears = null;
		if (!financialYear.isEmpty() && financialYear != null) {
			for (Object[] year : financialYear) {
				fromDate = Utility.dateToString((Date) year[0]);
				toDate = Utility.dateToString((Date) year[1]);
			}
		}

		if (StringUtils.isNotEmpty(toDate) && StringUtils.isNotEmpty(fromDate)) {
			financialYears = fromDate.substring(6, 10) + "-" + toDate.substring(6, 10);
			return financialYears;
		}

		return null;
	}

	private void processDishonorChequeRegister(ModelMap model, long orgId, String fromDate, String toDate) {
		int index = 0;
		
		LookUp lookUp=null;
		try {
		 lookUp = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.AccountConstants.DISHONORED.getValue(),
				PrefixConstants.LookUpPrefix.CLR, new Organisation(orgId));
		}catch(Exception e) {
			LOGGER.error("Error while getting prefix value", e);	
		}
		AccountFinancialReportDTO dto = null;
		List<AccountFinancialReportDTO> dishonorChequeList = new ArrayList<>();
		final AccountFinancialReportDTO dishonorCheque = new AccountFinancialReportDTO();
		final List<Object[]> dishonorChequeReport = accountFinancialReportRepository
				.queryChequeDishonorReport(Utility.stringToDate(fromDate), orgId, Utility.stringToDate(toDate),lookUp.getLookUpId());
		if (dishonorChequeReport != null && !dishonorChequeReport.isEmpty()) {
			for (Object[] dishonorEntity : dishonorChequeReport) {
				dto = new AccountFinancialReportDTO();
				dto.setReceivedFrom(dishonorEntity[0].toString());
				dto.setReceiptNumber(dishonorEntity[1].toString());
				dto.setChequeNo(dishonorEntity[2].toString());
				dto.setRamt(((BigDecimal) dishonorEntity[3]).setScale(2, RoundingMode.HALF_EVEN));
				dto.setDischarge(new BigDecimal((Double) dishonorEntity[4]).setScale(2, RoundingMode.HALF_EVEN));
				dto.setChkDate(Utility.dateToString((Date) dishonorEntity[5]));
				index = index + 1;
				dto.setIndex(index);
				dishonorChequeList.add(dto);
			}
		}
		dishonorCheque.setListofchequeDishonor(dishonorChequeList);
		dishonorCheque.setFromDate(fromDate);
		dishonorCheque.setToDate(toDate);
		model.addAttribute(REPORT_DATA, dishonorCheque);
	}

	private String findFinancialYear(String fromDate) {
		String financialYear = null;
		Date myDate = Utility.stringToDate(fromDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.add(Calendar.DATE, -1);
		Date beforeDate = cal.getTime();
		Date afterDate = null;

		int year = getYearFromDate(myDate);
		String year1 = year + "-" + (year + 1);
		int month = getMonthFromDate(myDate);
		String beforDate = "";
		if (month < 4) {
			// if (month >= 3) {
			String year2 = year1.substring(0, year1.lastIndexOf("-"));
			int finalYear = Integer.valueOf(year2) - 1;
			financialYear = finalYear + "-" + (finalYear + 1);
			return financialYear;
		}
		return year1;

	}

	private List<Object[]> findPreviousYearIncome(String fromDate, String toDate, Long orgId, int langId) {

		final FinancialYear financialYear = tbFinancialyearJpaRepository
				.getFinanciaYearId(Utility.stringToDate(fromDate));
		final Long finYearId = financialYear.getFaYear();

		Date frmDate = null;
		final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(finYearId);
		for (Object[] objects : faYearFromDate) {
			frmDate = (Date) objects[0];
		}
		String myDate = Utility.dateToString(frmDate);
		int year1 = getYearFromDate(frmDate);
		String fromDate1 = myDate.substring(0, 6) + (year1 - 1);
		Long newlyFaYearId = tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(Utility.stringToDate(fromDate1));
		List<Object[]> previousYearFrmToDates = tbFinancialyearJpaRepository.getFinanceYearFrmDate(newlyFaYearId);

		Date fromDates = null;
		Date toDates = null;
		for (Object[] objects : previousYearFrmToDates) {
			if (objects[0] != null && objects[1] != null) {
				fromDates = (Date) objects[0];
				toDates = (Date) objects[1];
			}
		}

		/*
		 * Date myDate = Utility.stringToDate(fromDate); int year1 =
		 * getYearFromDate(myDate); Date myDate1 = Utility.stringToDate(toDate); int
		 * year2 = getYearFromDate(myDate1); String fromDate1 = fromDate.substring(0, 6)
		 * + (year1 - 1); String toDate1 = toDate.substring(0, 6) + (year2 - 1); Date
		 * fromDates = Utility.stringToDate(fromDate1); Date toDates =
		 * Utility.stringToDate(toDate1);
		 */

		Organisation org = new Organisation();
		org.setOrgid(orgId);
		final LookUp coaLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.LookUp.INDIVIDUAL,
				PrefixConstants.PREFIX_VALUE_IELA, langId, org);

		return accountFinancialReportRepository.queryReportDataFromViewIncome(fromDates, toDates, orgId,
				coaLookup.getLookUpId());

	}

	private List<Object[]> findPreviousYearMajorHeadIncome(String fromDate, String toDate, Long orgId, int langId, Long accountHeadId) {

		final FinancialYear financialYear = tbFinancialyearJpaRepository
				.getFinanciaYearId(Utility.stringToDate(fromDate));
		final Long finYearId = financialYear.getFaYear();

		Date frmDate = null;
		final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(finYearId);
		for (Object[] objects : faYearFromDate) {
			frmDate = (Date) objects[0];
		}
		String myDate = Utility.dateToString(frmDate);
		int year1 = getYearFromDate(frmDate);
		String fromDate1 = myDate.substring(0, 6) + (year1 - 1);
		Long newlyFaYearId = tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(Utility.stringToDate(fromDate1));
		List<Object[]> previousYearFrmToDates = tbFinancialyearJpaRepository.getFinanceYearFrmDate(newlyFaYearId);

		Date fromDates = null;
		Date toDates = null;
		for (Object[] objects : previousYearFrmToDates) {
			if (objects[0] != null && objects[1] != null) {
				fromDates = (Date) objects[0];
				toDates = (Date) objects[1];
			}
		}
		Organisation org = new Organisation();
		org.setOrgid(orgId);
		final LookUp coaLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.LookUp.INDIVIDUAL,
				PrefixConstants.PREFIX_VALUE_IELA, langId, org);

		return accountFinancialReportRepository.queryReportDataFromViewMajorHeadIncomeAndExpenditure(fromDates, toDates,
				orgId, coaLookup.getLookUpId(),accountHeadId);

	}

	private List<Object[]> findPreviousYearMinorHeadIncome(String fromDate, String toDate, Long orgId, int langId, Long accountHeadId) {

		final FinancialYear financialYear = tbFinancialyearJpaRepository
				.getFinanciaYearId(Utility.stringToDate(fromDate));
		final Long finYearId = financialYear.getFaYear();

		Date frmDate = null;
		final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(finYearId);
		for (Object[] objects : faYearFromDate) {
			frmDate = (Date) objects[0];
		}
		String myDate = Utility.dateToString(frmDate);
		int year1 = getYearFromDate(frmDate);
		String fromDate1 = myDate.substring(0, 6) + (year1 - 1);
		Long newlyFaYearId = tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(Utility.stringToDate(fromDate1));
		List<Object[]> previousYearFrmToDates = tbFinancialyearJpaRepository.getFinanceYearFrmDate(newlyFaYearId);

		Date fromDates = null;
		Date toDates = null;
		for (Object[] objects : previousYearFrmToDates) {
			if (objects[0] != null && objects[1] != null) {
				fromDates = (Date) objects[0];
				toDates = (Date) objects[1];
			}
		}
		Organisation org = new Organisation();
		org.setOrgid(orgId);
		final LookUp coaLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.LookUp.INDIVIDUAL,
				PrefixConstants.PREFIX_VALUE_IELA, langId, org);

		return accountFinancialReportRepository.queryReportDataFromViewMinorHeadIncomeAndExpenditure(fromDates, toDates,
				orgId, coaLookup.getLookUpId(),accountHeadId);

	}

	private List<Object[]> findPreviousYearObjectClassIncome(String fromDate, String toDate, Long orgId, int langId) {

		final FinancialYear financialYear = tbFinancialyearJpaRepository
				.getFinanciaYearId(Utility.stringToDate(fromDate));
		final Long finYearId = financialYear.getFaYear();

		Date frmDate = null;
		final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(finYearId);
		for (Object[] objects : faYearFromDate) {
			frmDate = (Date) objects[0];
		}
		String myDate = Utility.dateToString(frmDate);
		int year1 = getYearFromDate(frmDate);
		String fromDate1 = myDate.substring(0, 6) + (year1 - 1);
		Long newlyFaYearId = tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(Utility.stringToDate(fromDate1));
		List<Object[]> previousYearFrmToDates = tbFinancialyearJpaRepository.getFinanceYearFrmDate(newlyFaYearId);

		Date fromDates = null;
		Date toDates = null;
		for (Object[] objects : previousYearFrmToDates) {
			if (objects[0] != null && objects[1] != null) {
				fromDates = (Date) objects[0];
				toDates = (Date) objects[1];
			}
		}
		Organisation org = new Organisation();
		org.setOrgid(orgId);
		final LookUp coaLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.LookUp.INDIVIDUAL,
				PrefixConstants.PREFIX_VALUE_IELA, langId, org);

		return accountFinancialReportRepository.queryReportDataFromViewObjectClassIncomeAndExpenditure(fromDates,
				toDates, orgId, coaLookup.getLookUpId());

	}

	private List<Object[]> findPreviousYearDetailHeadIncome(String fromDate, String toDate, Long orgId, int langId) {

		final FinancialYear financialYear = tbFinancialyearJpaRepository
				.getFinanciaYearId(Utility.stringToDate(fromDate));
		final Long finYearId = financialYear.getFaYear();

		Date frmDate = null;
		final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(finYearId);
		for (Object[] objects : faYearFromDate) {
			frmDate = (Date) objects[0];
		}
		String myDate = Utility.dateToString(frmDate);
		int year1 = getYearFromDate(frmDate);
		String fromDate1 = myDate.substring(0, 6) + (year1 - 1);
		Long newlyFaYearId = tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(Utility.stringToDate(fromDate1));
		List<Object[]> previousYearFrmToDates = tbFinancialyearJpaRepository.getFinanceYearFrmDate(newlyFaYearId);

		Date fromDates = null;
		Date toDates = null;
		for (Object[] objects : previousYearFrmToDates) {
			if (objects[0] != null && objects[1] != null) {
				fromDates = (Date) objects[0];
				toDates = (Date) objects[1];
			}
		}
		Organisation org = new Organisation();
		org.setOrgid(orgId);
		final LookUp coaLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.LookUp.INDIVIDUAL,
				PrefixConstants.PREFIX_VALUE_IELA, langId, org);

		return accountFinancialReportRepository.queryReportDataFromViewDeatilHeadIncomeAndExpenditure(fromDates,
				toDates, orgId, coaLookup.getLookUpId());

	}

	private List<Object[]> findPreviousYearexpenditure(String fromDate, String toDate, Long orgId, int langId) {
		/*
		 * Date myDate = Utility.stringToDate(fromDate); int year1 =
		 * getYearFromDate(myDate); Date myDate1 = Utility.stringToDate(toDate); int
		 * year2 = getYearFromDate(myDate1); String fromDate1 = fromDate.substring(0, 6)
		 * + (year1 - 1); String toDate1 = toDate.substring(0, 6) + (year2 - 1); Date
		 * fromDates = Utility.stringToDate(fromDate1); Date toDates =
		 * Utility.stringToDate(toDate1);
		 */

		final FinancialYear financialYear = tbFinancialyearJpaRepository
				.getFinanciaYearId(Utility.stringToDate(fromDate));
		final Long finYearId = financialYear.getFaYear();

		Date frmDate = null;
		final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(finYearId);
		for (Object[] objects : faYearFromDate) {
			frmDate = (Date) objects[0];
		}
		String myDate = Utility.dateToString(frmDate);
		int year1 = getYearFromDate(frmDate);
		String fromDate1 = myDate.substring(0, 6) + (year1 - 1);
		Long newlyFaYearId = tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(Utility.stringToDate(fromDate1));
		List<Object[]> previousYearFrmToDates = tbFinancialyearJpaRepository.getFinanceYearFrmDate(newlyFaYearId);

		Date fromDates = null;
		Date toDates = null;
		for (Object[] objects : previousYearFrmToDates) {
			if (objects[0] != null && objects[1] != null) {
				fromDates = (Date) objects[0];
				toDates = (Date) objects[1];
			}
		}
		Organisation org = new Organisation();
		org.setOrgid(orgId);
		final LookUp coaLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.NEC.EMPLOYEE,
				PrefixConstants.PREFIX_VALUE_IELA, langId, org);
		return accountFinancialReportRepository.queryReportDataFromViewExpenditure(fromDates, toDates, orgId,
				coaLookup.getLookUpId());

	}

	private List<Object[]> findPreviousYearMajorHeadexpenditure(String fromDate, String toDate, Long orgId,
			int langId, Long accountHeadId,String NMAMFormat) {

		final FinancialYear financialYear = tbFinancialyearJpaRepository
				.getFinanciaYearId(Utility.stringToDate(fromDate));
		final Long finYearId = financialYear.getFaYear();

		Date frmDate = null;
		final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(finYearId);
		for (Object[] objects : faYearFromDate) {
			frmDate = (Date) objects[0];
		}
		String myDate = Utility.dateToString(frmDate);
		int year1 = getYearFromDate(frmDate);
		String fromDate1 = myDate.substring(0, 6) + (year1 - 1);
		Long newlyFaYearId = tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(Utility.stringToDate(fromDate1));
		List<Object[]> previousYearFrmToDates = tbFinancialyearJpaRepository.getFinanceYearFrmDate(newlyFaYearId);

		Date fromDates = null;
		Date toDates = null;
		for (Object[] objects : previousYearFrmToDates) {
			if (objects[0] != null && objects[1] != null) {
				fromDates = (Date) objects[0];
				toDates = (Date) objects[1];
			}
		}
		Organisation org = new Organisation();
		org.setOrgid(orgId);
		final LookUp coaLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.NEC.EMPLOYEE,
				PrefixConstants.PREFIX_VALUE_IELA, langId, org);
		if (StringUtils.isEmpty(NMAMFormat)) {
        //Defect #185953
			return accountFinancialReportRepository.queryReportDataFromViewMajorHeadExpenditure(fromDates,
					toDates, orgId, coaLookup.getLookUpId(), accountHeadId);
		} else {
			return accountFinancialReportRepository.queryReportDataFromViewMajorHeadIncomeAndExpenditureNMAM(fromDates,
					toDates, orgId, coaLookup.getLookUpId(), accountHeadId);
		}

	}

	private List<Object[]> findPreviousYearMinorHeadexpenditure(String fromDate, String toDate, Long orgId,
			int langId, Long accountHeadId) {

		final FinancialYear financialYear = tbFinancialyearJpaRepository
				.getFinanciaYearId(Utility.stringToDate(fromDate));
		final Long finYearId = financialYear.getFaYear();

		Date frmDate = null;
		final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(finYearId);
		for (Object[] objects : faYearFromDate) {
			frmDate = (Date) objects[0];
		}
		String myDate = Utility.dateToString(frmDate);
		int year1 = getYearFromDate(frmDate);
		String fromDate1 = myDate.substring(0, 6) + (year1 - 1);
		Long newlyFaYearId = tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(Utility.stringToDate(fromDate1));
		List<Object[]> previousYearFrmToDates = tbFinancialyearJpaRepository.getFinanceYearFrmDate(newlyFaYearId);

		Date fromDates = null;
		Date toDates = null;
		for (Object[] objects : previousYearFrmToDates) {
			if (objects[0] != null && objects[1] != null) {
				fromDates = (Date) objects[0];
				toDates = (Date) objects[1];
			}
		}
		Organisation org = new Organisation();
		org.setOrgid(orgId);
		final LookUp coaLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.NEC.EMPLOYEE,
				PrefixConstants.PREFIX_VALUE_IELA, langId, org);
		return accountFinancialReportRepository.queryReportDataFromViewMinorHeadIncomeAndExpenditure(fromDates, toDates,
				orgId, coaLookup.getLookUpId(),accountHeadId);

	}

	private List<Object[]> findPreviousYearObjectClassexpenditure(String fromDate, String toDate, Long orgId,
			int langId) {

		final FinancialYear financialYear = tbFinancialyearJpaRepository
				.getFinanciaYearId(Utility.stringToDate(fromDate));
		final Long finYearId = financialYear.getFaYear();

		Date frmDate = null;
		final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(finYearId);
		for (Object[] objects : faYearFromDate) {
			frmDate = (Date) objects[0];
		}
		String myDate = Utility.dateToString(frmDate);
		int year1 = getYearFromDate(frmDate);
		String fromDate1 = myDate.substring(0, 6) + (year1 - 1);
		Long newlyFaYearId = tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(Utility.stringToDate(fromDate1));
		List<Object[]> previousYearFrmToDates = tbFinancialyearJpaRepository.getFinanceYearFrmDate(newlyFaYearId);

		Date fromDates = null;
		Date toDates = null;
		for (Object[] objects : previousYearFrmToDates) {
			if (objects[0] != null && objects[1] != null) {
				fromDates = (Date) objects[0];
				toDates = (Date) objects[1];
			}
		}
		Organisation org = new Organisation();
		org.setOrgid(orgId);
		final LookUp coaLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.NEC.EMPLOYEE,
				PrefixConstants.PREFIX_VALUE_IELA, langId, org);
		return accountFinancialReportRepository.queryReportDataFromViewObjectClassIncomeAndExpenditure(fromDates,
				toDates, orgId, coaLookup.getLookUpId());

	}

	private List<Object[]> findPreviousYearDetailHeadexpenditure(String fromDate, String toDate, Long orgId,
			int langId) {

		final FinancialYear financialYear = tbFinancialyearJpaRepository
				.getFinanciaYearId(Utility.stringToDate(fromDate));
		final Long finYearId = financialYear.getFaYear();

		Date frmDate = null;
		final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(finYearId);
		for (Object[] objects : faYearFromDate) {
			frmDate = (Date) objects[0];
		}
		String myDate = Utility.dateToString(frmDate);
		int year1 = getYearFromDate(frmDate);
		String fromDate1 = myDate.substring(0, 6) + (year1 - 1);
		Long newlyFaYearId = tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(Utility.stringToDate(fromDate1));
		List<Object[]> previousYearFrmToDates = tbFinancialyearJpaRepository.getFinanceYearFrmDate(newlyFaYearId);

		Date fromDates = null;
		Date toDates = null;
		for (Object[] objects : previousYearFrmToDates) {
			if (objects[0] != null && objects[1] != null) {
				fromDates = (Date) objects[0];
				toDates = (Date) objects[1];
			}
		}
		Organisation org = new Organisation();
		org.setOrgid(orgId);
		final LookUp coaLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.NEC.EMPLOYEE,
				PrefixConstants.PREFIX_VALUE_IELA, langId, org);
		return accountFinancialReportRepository.queryReportDataFromViewDeatilHeadIncomeAndExpenditure(fromDates,
				toDates, orgId, coaLookup.getLookUpId());

	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findOpeningBalanceAmount(Long sacHeadId, Long orgId, Long financialYearId) {

		return accountFinancialReportRepository.findOpeningBalanceAmount(sacHeadId, orgId, financialYearId);
	}

	private void processBankAccountsSummaryReport(ModelMap model, long orgId, String fromDate, String toDate,Long fieldId) {
		AccountFinancialReportDTO bankdto = null;
		List<AccountFinancialReportDTO> bankAccountsList = new ArrayList<>();
		final AccountFinancialReportDTO BankAccount = new AccountFinancialReportDTO();
		BigDecimal totalOpeningBalance = new BigDecimal(0.00);
		BigDecimal totalReceiptAmt = new BigDecimal(0.00);
		BigDecimal totalPaymentAmnt = new BigDecimal(0.00);
		BigDecimal totalClosingAmt = new BigDecimal(0.00);

		Long financialYearId = tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(Utility.stringToDate(fromDate));
		final List<Object[]> bankAccountSummary = accountFinanceReportDAO.queryBankAccountsSummaryReport(
				Utility.stringToDate(fromDate), orgId, Utility.stringToDate(toDate), financialYearId,fieldId);
		if (bankAccountSummary != null && !bankAccountSummary.isEmpty()) {
			for (Object[] bankaccountEntity : bankAccountSummary) {
				bankdto = new AccountFinancialReportDTO();

				BigDecimal totalOpeningReceiptAmt = new BigDecimal(0.00);

				if (bankaccountEntity[0] != null) {
					bankdto.setBankname(bankaccountEntity[0].toString());
				}
				if (bankaccountEntity[1] != null) {
					bankdto.setBankAcNo(bankaccountEntity[1].toString());
				}
				if (bankaccountEntity[2] != null) {
					bankdto.setAccountType(bankaccountEntity[2].toString());
				}

				if (bankaccountEntity[3] != null) {
					bankdto.setOpeningBalancers(CommonMasterUtility
							.getAmountInIndianCurrency(new BigDecimal(bankaccountEntity[3].toString())));
					totalOpeningBalance = totalOpeningBalance.add(new BigDecimal(bankaccountEntity[3].toString()));
				} else {
					bankdto.setOpeningBalancers(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
				}
				if (bankaccountEntity[4] != null) {
					bankdto.setReceiptAmt(CommonMasterUtility
							.getAmountInIndianCurrency(new BigDecimal(bankaccountEntity[4].toString())));
					totalReceiptAmt = totalReceiptAmt.add(new BigDecimal(bankaccountEntity[4].toString()));
				} else {
					bankdto.setReceiptAmt(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
				}

				if (bankaccountEntity[5] != null) {
					bankdto.setPaymentAmnt(CommonMasterUtility
							.getAmountInIndianCurrency(new BigDecimal(bankaccountEntity[5].toString())));
					totalPaymentAmnt = totalPaymentAmnt.add(new BigDecimal(bankaccountEntity[5].toString()));
				} else {
					bankdto.setPaymentAmnt(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
				}

				if (bankaccountEntity[3] != null && bankaccountEntity[4] != null) {
					totalOpeningReceiptAmt = totalOpeningReceiptAmt.add(new BigDecimal(bankaccountEntity[3].toString()))
							.add(new BigDecimal(bankaccountEntity[4].toString()));
				} else if (bankaccountEntity[3] != null) {
					totalOpeningReceiptAmt = totalOpeningReceiptAmt
							.add(new BigDecimal(bankaccountEntity[3].toString()));
				} else if (bankaccountEntity[4] != null) {
					totalOpeningReceiptAmt = totalOpeningReceiptAmt
							.add(new BigDecimal(bankaccountEntity[4].toString()));
				}

				if (bankaccountEntity[5] != null) {
					totalOpeningReceiptAmt = totalOpeningReceiptAmt
							.subtract(new BigDecimal(bankaccountEntity[5].toString()));
					bankdto.setClosingAmt(CommonMasterUtility
							.getAmountInIndianCurrency(new BigDecimal(totalOpeningReceiptAmt.toString())));
					totalClosingAmt = totalClosingAmt.add(new BigDecimal(totalOpeningReceiptAmt.toString()));
				} else {
					bankdto.setClosingAmt(CommonMasterUtility
							.getAmountInIndianCurrency(new BigDecimal(totalOpeningReceiptAmt.toString())));

				}
				if (bankaccountEntity[6] != null) {
					bankdto.setBaBankAcName(bankaccountEntity[6].toString());
				}

				bankAccountsList.add(bankdto);
			}
		}
		BankAccount.setBankAccountSummary(bankAccountsList);
		BankAccount.setTotalOpeningBalancers(
				CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(totalOpeningBalance.toString())));
		BankAccount.setTotalReceiptAmt(
				CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(totalReceiptAmt.toString())));
		BankAccount.setTotalPaymentAmt(
				CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(totalPaymentAmnt.toString())));
		BankAccount.setTotalClosingAmt(
				CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(totalClosingAmt.toString())));
		BankAccount.setFromDate(fromDate);
		BankAccount.setToDate(toDate);
		model.addAttribute(REPORT_DATA, BankAccount);
		if(fieldId != null && fieldId != -1L) {
			model.addAttribute("fieldName", tbAcFieldMasterService.getFieldDesc(fieldId));
		}
	}

	private void processGeneratebBalanceSheet(ModelMap model, long orgId, String transactionDate, int langId,
			Long accountHeadId, Long superOrgId) {

		BigDecimal totalClosingAsset = new BigDecimal(0.00);
		BigDecimal totalClosingAnOnAsset = new BigDecimal(0.00);
		BigDecimal totalClosingliability = new BigDecimal(0.00);
		BigDecimal totalClosingAnOnliability = new BigDecimal(0.00);

		Long financialYearId = tbFinancialyearJpaRepository
				.getFinanciaYearIdByFromDate(Utility.stringToDate(transactionDate));
		Date fromDates = tbFinancialyearJpaRepository
				.getFromDateFromFinancialYearIdByPassingDate(Utility.stringToDate(transactionDate));
		List<AccountFinancialReportDTO> balanceSheetList = new ArrayList<>();
		List<AccountFinancialReportDTO> balanceSheetListLiability = new ArrayList<>();
		List<AccountFinancialReportDTO> accountBalanceAssetList = new ArrayList<>();
		List<AccountFinancialReportDTO> accountBalanceLiabilityList = new ArrayList<>();

		List<Object[]> balanceSheetReport = null;
		List<Object[]> previousBalanceList = null;
		List<Object[]> listofliabilityHead = null;
		List<Object[]> listofAssetHead = null;
		final Long param1 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.AHP.getValue(),
				PrefixConstants.CMD, orgId);
		final Long param2 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.AHS.getValue(),
				PrefixConstants.CMD, orgId);
		List<LookUp> levelHead = acCodingstructureMasService.queryAccountHeadByChartOfAccount(param1, param2, orgId);
		if (levelHead != null && !levelHead.isEmpty()) {

			/*
			 * if (levelHead.get(0).getLookUpId() == accountHeadId) { // detail head } else
			 * if (levelHead.get(1).getLookUpId() == accountHeadId) {// major head
			 * balanceSheetReport =
			 * accountFinancialReportRepository.findBalanceSheetMajorHeadReport(orgId,
			 * fromDates, Utility.stringToDate(transactionDate), financialYearId);
			 * previousBalanceList = findPreviousYearMajorHeadBalanceSheet(orgId, fromDates,
			 * Utility.stringToDate(transactionDate), financialYearId); listofliabilityHead
			 * = findliabilityMajorHead(superOrgId); listofAssetHead =
			 * findAssetMajorHead(superOrgId); } else if (levelHead.get(2).getLookUpId() ==
			 * accountHeadId) {// miner head } else if (levelHead.get(3).getLookUpId() ==
			 * accountHeadId) {// object class } else if (levelHead.get(4).getLookUpId() ==
			 * accountHeadId) {// secondary head balanceSheetReport =
			 * accountFinancialReportRepository.findBalanceSheetReport(orgId, fromDates,
			 * Utility.stringToDate(transactionDate), financialYearId); previousBalanceList
			 * = findPreviousYearBalanceSheet(orgId, fromDates,
			 * Utility.stringToDate(transactionDate), financialYearId); listofliabilityHead
			 * = findliabilityHead(orgId); listofAssetHead = findAssetHead(orgId); }
			 */
			if (levelHead.get(0).getLookUpId() == accountHeadId) { // Major Head
				balanceSheetReport = accountFinancialReportRepository.findBalanceSheetMajorHeadReport(orgId, fromDates,
						Utility.stringToDate(transactionDate), financialYearId);
				previousBalanceList = findPreviousYearMajorHeadBalanceSheet(orgId, fromDates,
						Utility.stringToDate(transactionDate), financialYearId);

				listofliabilityHead = findliabilityMajorHead(superOrgId);
				listofAssetHead = findAssetMajorHead(superOrgId);
			} else if (levelHead.get(1).getLookUpId() == accountHeadId) {// Minor head
				balanceSheetReport = accountFinancialReportRepository.findBalanceSheetMinorHeadReport(orgId, fromDates,
						Utility.stringToDate(transactionDate), financialYearId);
				previousBalanceList = findPreviousYearMinorHeadBalanceSheet(orgId, fromDates,
						Utility.stringToDate(transactionDate), financialYearId);

				listofliabilityHead = findliabilityMajorHead(superOrgId);
				listofAssetHead = findAssetMajorHead(superOrgId);
			} else if (levelHead.get(2).getLookUpId() == accountHeadId) {// secondary head
				balanceSheetReport = accountFinancialReportRepository.findBalanceSheetReport(orgId, fromDates,
						Utility.stringToDate(transactionDate), financialYearId);
				previousBalanceList = findPreviousYearBalanceSheet(orgId, fromDates,
						Utility.stringToDate(transactionDate), financialYearId);

				listofliabilityHead = findliabilityHead(orgId);
				listofAssetHead = findAssetHead(orgId);
			}
		}

		if (balanceSheetReport != null && !balanceSheetReport.isEmpty()) {

			for (Object[] balanceSheetReports : balanceSheetReport) {

				AccountFinancialReportDTO accountFinancialReportDTO = new AccountFinancialReportDTO();
				if (previousBalanceList != null && !previousBalanceList.isEmpty()) {
					for (final Object[] previousBalanceLists : previousBalanceList) {
						if (balanceSheetReports[2].equals(previousBalanceLists[2])) {
							if (previousBalanceLists[3] != null && !previousBalanceLists[3].toString().equals("0.00")
									&& !previousBalanceLists[3].toString().equals("0")) {
								accountFinancialReportDTO.setClosingBalanceAsOn(
										calculateClosingBalAsOnDate((BigDecimal) previousBalanceLists[3], null, null));
							} else if (previousBalanceLists[4] != null
									&& !previousBalanceLists[4].toString().equals("0.00")
									&& !previousBalanceLists[4].toString().equals("0")) {
								accountFinancialReportDTO.setClosingBalanceAsOn(
										calculateClosingBalAsOnDate((BigDecimal) previousBalanceLists[4], null, null));
							} else {
								accountFinancialReportDTO.setClosingBalanceAsOn(
										calculateClosingBalAsOnDate((BigDecimal) previousBalanceLists[4], null, null));
							}
						}
					}
				}
				if (balanceSheetReports[0] != null) {
					accountFinancialReportDTO.setAccountHead((String) balanceSheetReports[0]);
				}
				if (balanceSheetReports[1] != null) {
					accountFinancialReportDTO.setAccountHeadDesc((String) balanceSheetReports[1]);
				}
				if (balanceSheetReports[2] != null) {
					accountFinancialReportDTO.setAccountCode((String) balanceSheetReports[2].toString());
				}

				if (balanceSheetReports[3] != null) {
					accountFinancialReportDTO.setOpeningDrAmount(new BigDecimal(balanceSheetReports[3].toString()));
				}

				if (balanceSheetReports[4] != null) {
					accountFinancialReportDTO.setOpeningCrAmount(new BigDecimal(balanceSheetReports[4].toString()));
				}

				if (balanceSheetReports[3] != null && !balanceSheetReports[3].toString().equals("0.00")
						&& !balanceSheetReports[3].toString().equals("0")) {
					accountFinancialReportDTO
							.setClosingBalance(calculateClosingBalAsOnDate((BigDecimal) balanceSheetReports[3],
									(BigDecimal) balanceSheetReports[6], (BigDecimal) balanceSheetReports[5]));
				} else if (balanceSheetReports[4] != null && !balanceSheetReports[4].toString().equals("0.00")
						&& !balanceSheetReports[4].toString().equals("0")) {
					accountFinancialReportDTO
							.setClosingBalance(calculateClosingBalAsOnDate((BigDecimal) balanceSheetReports[4],
									(BigDecimal) balanceSheetReports[5], (BigDecimal) balanceSheetReports[6]));
				} else {
					accountFinancialReportDTO
							.setClosingBalance(calculateClosingBalAsOnDate((BigDecimal) balanceSheetReports[4],
									(BigDecimal) balanceSheetReports[6], (BigDecimal) balanceSheetReports[5]));
				}

				balanceSheetList.add(accountFinancialReportDTO);

			}

		}

		else if (previousBalanceList != null && !previousBalanceList.isEmpty()) {
			for (final Object[] previousBalanceLists : previousBalanceList) {
				AccountFinancialReportDTO accountFinancialReportDTO = new AccountFinancialReportDTO();
				if (previousBalanceLists[0] != null) {
					accountFinancialReportDTO.setAccountHead((String) previousBalanceLists[0]);
				}
				if (previousBalanceLists[1] != null) {
					accountFinancialReportDTO.setAccountHeadDesc((String) previousBalanceLists[1]);
				}
				if (previousBalanceLists[2] != null) {
					accountFinancialReportDTO.setAccountCode((String) previousBalanceLists[2].toString());
				}
				if (previousBalanceLists[3] != null) {
					accountFinancialReportDTO.setOpeningDrAmount(new BigDecimal(previousBalanceLists[3].toString()));
				}
				if (previousBalanceLists[4] != null) {
					accountFinancialReportDTO.setOpeningCrAmount(new BigDecimal(previousBalanceLists[4].toString()));
				}
				if (previousBalanceLists[3] != null && !previousBalanceLists[3].toString().equals("0.00")
						&& !previousBalanceLists[3].toString().equals("0")) {
					accountFinancialReportDTO.setClosingBalanceAsOn(
							calculateClosingBalAsOnDate((BigDecimal) previousBalanceLists[3], null, null));
				} else if (previousBalanceLists[4] != null && !previousBalanceLists[4].toString().equals("0.00")
						&& !previousBalanceLists[4].toString().equals("0")) {
					accountFinancialReportDTO.setClosingBalanceAsOn(
							calculateClosingBalAsOnDate((BigDecimal) previousBalanceLists[4], null, null));
				} else {
					accountFinancialReportDTO.setClosingBalanceAsOn(
							calculateClosingBalAsOnDate((BigDecimal) previousBalanceLists[4], null, null));
				}
				balanceSheetList.add(accountFinancialReportDTO);
			}
		}

		if (balanceSheetReport != null && !balanceSheetReport.isEmpty()) {

			for (Object[] balanceSheetReports : balanceSheetReport) {

				AccountFinancialReportDTO accountFinancialReportDTO = new AccountFinancialReportDTO();
				if (previousBalanceList != null && !previousBalanceList.isEmpty()) {
					for (final Object[] previousBalanceLists : previousBalanceList) {
						if (balanceSheetReports[2].equals(previousBalanceLists[2])) {
							if (previousBalanceLists[3] != null && !previousBalanceLists[3].toString().equals("0.00")
									&& !previousBalanceLists[3].toString().equals("0")) {
								accountFinancialReportDTO.setClosingBalanceAsOn(
										calculateClosingBalAsOnDate((BigDecimal) previousBalanceLists[3], null, null));
							} else if (previousBalanceLists[4] != null
									&& !previousBalanceLists[4].toString().equals("0.00")
									&& !previousBalanceLists[4].toString().equals("0")) {
								accountFinancialReportDTO.setClosingBalanceAsOn(
										calculateClosingBalAsOnDate((BigDecimal) previousBalanceLists[4], null, null));
							} else {
								accountFinancialReportDTO.setClosingBalanceAsOn(calculateClosingBalTranAsOnDate(
										(BigDecimal) previousBalanceLists[4], null, null));
							}
						}
					}
				}
				if (balanceSheetReports[0] != null) {
					accountFinancialReportDTO.setAccountHead((String) balanceSheetReports[0]);
				}
				if (balanceSheetReports[1] != null) {
					accountFinancialReportDTO.setAccountHeadDesc((String) balanceSheetReports[1]);
				}
				if (balanceSheetReports[2] != null) {
					accountFinancialReportDTO.setAccountCode((String) balanceSheetReports[2].toString());
				}

				if (balanceSheetReports[3] != null) {
					accountFinancialReportDTO.setOpeningDrAmount(new BigDecimal(balanceSheetReports[3].toString()));
				}

				if (balanceSheetReports[4] != null) {
					accountFinancialReportDTO.setOpeningCrAmount(new BigDecimal(balanceSheetReports[4].toString()));
				}

				if (balanceSheetReports[3] != null && !balanceSheetReports[3].toString().equals("0.00")
						&& !balanceSheetReports[3].toString().equals("0")) {
					accountFinancialReportDTO
							.setClosingBalance(calculateClosingBalAsOnDate((BigDecimal) balanceSheetReports[3],
									(BigDecimal) balanceSheetReports[6], (BigDecimal) balanceSheetReports[5]));
				} else if (balanceSheetReports[4] != null && !balanceSheetReports[4].toString().equals("0.00")
						&& !balanceSheetReports[4].toString().equals("0")) {
					accountFinancialReportDTO
							.setClosingBalance(calculateClosingBalAsOnDate((BigDecimal) balanceSheetReports[4],
									(BigDecimal) balanceSheetReports[5], (BigDecimal) balanceSheetReports[6]));
				} else {
					accountFinancialReportDTO
							.setClosingBalance(calculateClosingBalTranAsOnDate((BigDecimal) balanceSheetReports[4],
									(BigDecimal) balanceSheetReports[6], (BigDecimal) balanceSheetReports[5]));
				}

				balanceSheetListLiability.add(accountFinancialReportDTO);

			}

		}

		else if (previousBalanceList != null && !previousBalanceList.isEmpty()) {
			for (final Object[] previousBalanceLists : previousBalanceList) {
				AccountFinancialReportDTO accountFinancialReportDTO = new AccountFinancialReportDTO();
				if (previousBalanceLists[0] != null) {
					accountFinancialReportDTO.setAccountHead((String) previousBalanceLists[0]);
				}
				if (previousBalanceLists[1] != null) {
					accountFinancialReportDTO.setAccountHeadDesc((String) previousBalanceLists[1]);
				}
				if (previousBalanceLists[2] != null) {
					accountFinancialReportDTO.setAccountCode((String) previousBalanceLists[2].toString());
				}
				if (previousBalanceLists[3] != null) {
					accountFinancialReportDTO.setOpeningDrAmount(new BigDecimal(previousBalanceLists[3].toString()));
				}
				if (previousBalanceLists[4] != null) {
					accountFinancialReportDTO.setOpeningCrAmount(new BigDecimal(previousBalanceLists[4].toString()));
				}
				if (previousBalanceLists[3] != null && !previousBalanceLists[3].toString().equals("0.00")
						&& !previousBalanceLists[3].toString().equals("0")) {
					accountFinancialReportDTO.setClosingBalanceAsOn(
							calculateClosingBalAsOnDate((BigDecimal) previousBalanceLists[3], null, null));
				} else if (previousBalanceLists[4] != null && !previousBalanceLists[4].toString().equals("0.00")
						&& !previousBalanceLists[4].toString().equals("0")) {
					accountFinancialReportDTO.setClosingBalanceAsOn(
							calculateClosingBalAsOnDate((BigDecimal) previousBalanceLists[4], null, null));
				} else {
					accountFinancialReportDTO.setClosingBalanceAsOn(
							calculateClosingBalTranAsOnDate((BigDecimal) previousBalanceLists[4], null, null));
				}
				balanceSheetListLiability.add(accountFinancialReportDTO);
			}
		}

		if (listofliabilityHead != null && !listofliabilityHead.isEmpty() && balanceSheetListLiability != null
				&& !balanceSheetListLiability.isEmpty()) {

			for (AccountFinancialReportDTO AccountFinancialReportDTO1 : balanceSheetListLiability) {
				AccountFinancialReportDTO accountFinancialReportDTO2 = new AccountFinancialReportDTO();

				for (Object[] listofliabilityHeads : listofliabilityHead) {
					if (AccountFinancialReportDTO1.getAccountCode().equals(listofliabilityHeads[0].toString())) {
						if (AccountFinancialReportDTO1.getAccountHead() != null)
							accountFinancialReportDTO2.setAccountHead(AccountFinancialReportDTO1.getAccountHead());
						accountFinancialReportDTO2.setAccountHeadDesc(AccountFinancialReportDTO1.getAccountHeadDesc());

						if (AccountFinancialReportDTO1.getOpeningDrAmount() != null
								&& !(AccountFinancialReportDTO1.getOpeningDrAmount().compareTo(BigDecimal.ZERO) == 0)) {
							if (AccountFinancialReportDTO1.getClosingBalance() != null && !(AccountFinancialReportDTO1
									.getClosingBalance().compareTo(BigDecimal.ZERO) == 0)) {
								if (AccountFinancialReportDTO1.getClosingBalance().signum() == -1) {
									accountFinancialReportDTO2
											.setClosingBalance(AccountFinancialReportDTO1.getClosingBalance());
								} else {
									accountFinancialReportDTO2
											.setClosingBalance(AccountFinancialReportDTO1.getClosingBalance().negate());
								}
							}
							if (AccountFinancialReportDTO1.getClosingBalanceAsOn() != null
									&& !(AccountFinancialReportDTO1.getClosingBalanceAsOn()
											.compareTo(BigDecimal.ZERO) == 0)) {
								if (AccountFinancialReportDTO1.getClosingBalanceAsOn().signum() == -1) {
									accountFinancialReportDTO2
											.setClosingBalanceAsOn(AccountFinancialReportDTO1.getClosingBalanceAsOn());
								} else {
									accountFinancialReportDTO2.setClosingBalanceAsOn(
											AccountFinancialReportDTO1.getClosingBalanceAsOn().negate());
								}
							}
						} else {
							if (AccountFinancialReportDTO1.getClosingBalance() != null && !(AccountFinancialReportDTO1
									.getClosingBalance().compareTo(BigDecimal.ZERO) == 0))
								if (AccountFinancialReportDTO1.getClosingBalance().signum() == -1) {
									accountFinancialReportDTO2
											.setClosingBalance(AccountFinancialReportDTO1.getClosingBalance());
								} else {
									accountFinancialReportDTO2
											.setClosingBalance(AccountFinancialReportDTO1.getClosingBalance().abs());
								}
							if (AccountFinancialReportDTO1.getClosingBalanceAsOn() != null
									&& !(AccountFinancialReportDTO1.getClosingBalanceAsOn()
											.compareTo(BigDecimal.ZERO) == 0))
								if (AccountFinancialReportDTO1.getClosingBalanceAsOn().signum() == -1) {
									accountFinancialReportDTO2
											.setClosingBalanceAsOn(AccountFinancialReportDTO1.getClosingBalanceAsOn());
								} else {
									accountFinancialReportDTO2.setClosingBalanceAsOn(
											AccountFinancialReportDTO1.getClosingBalanceAsOn().abs());
								}
						}
						if (accountFinancialReportDTO2.getClosingBalance() != null) {
							accountBalanceLiabilityList.add(accountFinancialReportDTO2);
						} else if (accountFinancialReportDTO2.getClosingBalanceAsOn() != null) {
							accountBalanceLiabilityList.add(accountFinancialReportDTO2);
						}
						if (AccountFinancialReportDTO1.getOpeningDrAmount() != null
								&& !(AccountFinancialReportDTO1.getOpeningDrAmount().compareTo(BigDecimal.ZERO) == 0)) {
							if (AccountFinancialReportDTO1.getClosingBalance() != null) {
								if (AccountFinancialReportDTO1.getClosingBalance().signum() == -1) {
									totalClosingAsset = totalClosingAsset
											.add(AccountFinancialReportDTO1.getClosingBalance());
								} else {
									totalClosingAsset = totalClosingAsset
											.subtract(AccountFinancialReportDTO1.getClosingBalance());
								}
							}
							if (AccountFinancialReportDTO1.getClosingBalanceAsOn() != null) {
								if (AccountFinancialReportDTO1.getClosingBalanceAsOn().signum() == -1) {
									totalClosingAnOnAsset = totalClosingAnOnAsset
											.add(AccountFinancialReportDTO1.getClosingBalanceAsOn());
								} else {
									totalClosingAnOnAsset = totalClosingAnOnAsset
											.subtract(AccountFinancialReportDTO1.getClosingBalanceAsOn());
								}
							}
						} else {
							if (AccountFinancialReportDTO1.getClosingBalance() != null && !(AccountFinancialReportDTO1
									.getClosingBalance().compareTo(BigDecimal.ZERO) == 0)) {
								if (AccountFinancialReportDTO1.getClosingBalance().signum() == -1) {
									totalClosingAsset = totalClosingAsset
											.add(AccountFinancialReportDTO1.getClosingBalance());
								} else {
									totalClosingAsset = totalClosingAsset
											.add(AccountFinancialReportDTO1.getClosingBalance());
								}
							}
							if (AccountFinancialReportDTO1.getClosingBalanceAsOn() != null
									&& !(AccountFinancialReportDTO1.getClosingBalanceAsOn()
											.compareTo(BigDecimal.ZERO) == 0)) {
								if (AccountFinancialReportDTO1.getClosingBalanceAsOn().signum() == -1) {
									totalClosingAnOnAsset = totalClosingAnOnAsset
											.add(AccountFinancialReportDTO1.getClosingBalanceAsOn());
								} else {
									totalClosingAnOnAsset = totalClosingAnOnAsset
											.add(AccountFinancialReportDTO1.getClosingBalanceAsOn());
								}
							}
						}
					}

				}

			}

		}

		if (listofAssetHead != null && !listofAssetHead.isEmpty() && balanceSheetList != null
				&& !balanceSheetList.isEmpty()) {

			for (AccountFinancialReportDTO accountFinancialReportDTO1 : balanceSheetList) {
				AccountFinancialReportDTO accountFinancialReportDTO3 = new AccountFinancialReportDTO();

				for (Object[] listofAssetHeads : listofAssetHead) {
					if (accountFinancialReportDTO1.getAccountCode().equals(listofAssetHeads[0].toString())) {

						if (accountFinancialReportDTO1.getAccountHead() != null)
							accountFinancialReportDTO3.setAccountHead(accountFinancialReportDTO1.getAccountHead());
						accountFinancialReportDTO3.setAccountHeadDesc(accountFinancialReportDTO1.getAccountHeadDesc());

						if (accountFinancialReportDTO1.getOpeningCrAmount() != null
								&& !(accountFinancialReportDTO1.getOpeningCrAmount().compareTo(BigDecimal.ZERO) == 0)) {
							if (accountFinancialReportDTO1.getClosingBalance() != null && !(accountFinancialReportDTO1
									.getClosingBalance().compareTo(BigDecimal.ZERO) == 0)) {
								if (accountFinancialReportDTO1.getClosingBalance().signum() == -1) {
									accountFinancialReportDTO3
											.setClosingBalance(accountFinancialReportDTO1.getClosingBalance());
								} else {
									accountFinancialReportDTO3
											.setClosingBalance(accountFinancialReportDTO1.getClosingBalance().negate());
								}
							}
							if (accountFinancialReportDTO1.getClosingBalanceAsOn() != null
									&& !(accountFinancialReportDTO1.getClosingBalanceAsOn()
											.compareTo(BigDecimal.ZERO) == 0)) {
								if (accountFinancialReportDTO1.getClosingBalanceAsOn().signum() == -1) {
									accountFinancialReportDTO3
											.setClosingBalanceAsOn(accountFinancialReportDTO1.getClosingBalanceAsOn());
								} else {
									accountFinancialReportDTO3.setClosingBalanceAsOn(
											accountFinancialReportDTO1.getClosingBalanceAsOn().negate());
								}
							}
						} else {
							if (accountFinancialReportDTO1.getClosingBalance() != null && !(accountFinancialReportDTO1
									.getClosingBalance().compareTo(BigDecimal.ZERO) == 0))
								accountFinancialReportDTO3
										.setClosingBalance(accountFinancialReportDTO1.getClosingBalance());
							if (accountFinancialReportDTO1.getClosingBalanceAsOn() != null
									&& !(accountFinancialReportDTO1.getClosingBalanceAsOn()
											.compareTo(BigDecimal.ZERO) == 0))
								accountFinancialReportDTO3
										.setClosingBalanceAsOn(accountFinancialReportDTO1.getClosingBalanceAsOn());
						}
						if (accountFinancialReportDTO3.getClosingBalance() != null) {
							accountBalanceAssetList.add(accountFinancialReportDTO3);
						} else if (accountFinancialReportDTO3.getClosingBalanceAsOn() != null) {
							accountBalanceAssetList.add(accountFinancialReportDTO3);
						}

						if (accountFinancialReportDTO1.getOpeningCrAmount() != null
								&& !(accountFinancialReportDTO1.getOpeningCrAmount().compareTo(BigDecimal.ZERO) == 0)) {
							if (accountFinancialReportDTO1.getClosingBalance() != null) {
								if (accountFinancialReportDTO1.getClosingBalance().signum() == -1) {
									totalClosingliability = totalClosingliability
											.add(accountFinancialReportDTO1.getClosingBalance());
								} else {
									totalClosingliability = totalClosingliability
											.subtract(accountFinancialReportDTO1.getClosingBalance());
								}
							}
							if (accountFinancialReportDTO1.getClosingBalanceAsOn() != null) {
								if (accountFinancialReportDTO1.getClosingBalanceAsOn().signum() == -1) {
									totalClosingAnOnliability = totalClosingAnOnliability
											.add(accountFinancialReportDTO1.getClosingBalanceAsOn());
								} else {
									totalClosingAnOnliability = totalClosingAnOnliability
											.subtract(accountFinancialReportDTO1.getClosingBalanceAsOn());
								}
							}
						} else {
							if (accountFinancialReportDTO1.getClosingBalance() != null) {
								totalClosingliability = totalClosingliability
										.add(accountFinancialReportDTO1.getClosingBalance());
							}
							if (accountFinancialReportDTO1.getClosingBalanceAsOn() != null) {
								totalClosingAnOnliability = totalClosingAnOnliability
										.add(accountFinancialReportDTO1.getClosingBalanceAsOn());
							}
						}
					}

				}

			}

		}

		BigDecimal finalSumIncomeDr = BigDecimal.ZERO;
		BigDecimal finalSumIncomeCr = BigDecimal.ZERO;
		BigDecimal finalSumExpenditureDr = BigDecimal.ZERO;
		BigDecimal finalSumExpenditureCr = BigDecimal.ZERO;

		List<Object[]> incomeRecord = queryReportDataFromViewIncome(Utility.dateToString(fromDates), transactionDate,
				orgId, langId);
		for (Object[] objects : incomeRecord) {
			if (objects[2] != null) {
				finalSumIncomeCr = finalSumIncomeCr.add(new BigDecimal(objects[2].toString()));
			}
			if (objects[3] != null) {
				finalSumIncomeDr = finalSumIncomeDr.add(new BigDecimal(objects[3].toString()));
			}
		}
		List<Object[]> ExpenditureRecord = queryReportDataFromViewExpenditure(Utility.dateToString(fromDates),
				transactionDate, orgId, langId);
		for (Object[] objects : ExpenditureRecord) {
			if (objects[2] != null) {
				finalSumExpenditureCr = finalSumExpenditureCr.add(new BigDecimal(objects[2].toString()));
			}
			if (objects[3] != null) {
				finalSumExpenditureDr = finalSumExpenditureDr.add(new BigDecimal(objects[3].toString()));
			}
		}

		BigDecimal finalIncomeTotalAmt = finalSumIncomeCr.subtract(finalSumIncomeDr);
		BigDecimal finalExpenditureTotalAmt = finalSumExpenditureDr.subtract(finalSumExpenditureCr);
		BigDecimal reverveAndSurpresAmount = finalIncomeTotalAmt.subtract(finalExpenditureTotalAmt);

		BigDecimal finalPreviousSumIncomeDr = BigDecimal.ZERO;
		BigDecimal finalPreviousSumIncomeCr = BigDecimal.ZERO;
		BigDecimal finalPreviousSumExpenditureDr = BigDecimal.ZERO;
		BigDecimal finalPreviousSumExpenditureCr = BigDecimal.ZERO;

		List<Object[]> previousYearIncomeAmount = findPreviousYearIncome(Utility.dateToString(fromDates),
				transactionDate, orgId, langId);
		List<Object[]> previousYearExp = findPreviousYearexpenditure(Utility.dateToString(fromDates), transactionDate,
				orgId, langId);

		for (Object[] objects : previousYearIncomeAmount) {
			if (objects[2] != null) {
				finalPreviousSumIncomeCr = finalPreviousSumIncomeCr.add(new BigDecimal(objects[2].toString()));
			}
			if (objects[3] != null) {
				finalPreviousSumIncomeDr = finalPreviousSumIncomeDr.add(new BigDecimal(objects[3].toString()));
			}
		}
		for (Object[] objects : previousYearExp) {
			if (objects[2] != null) {
				finalPreviousSumExpenditureCr = finalPreviousSumExpenditureCr
						.add(new BigDecimal(objects[2].toString()));
			}
			if (objects[3] != null) {
				finalPreviousSumExpenditureDr = finalPreviousSumExpenditureDr
						.add(new BigDecimal(objects[3].toString()));
			}
		}

		BigDecimal finalPreviousIncomeTotalAmt = finalPreviousSumIncomeCr.subtract(finalPreviousSumIncomeDr);
		BigDecimal finalPreviousExpenditureTotalAmt = finalPreviousSumExpenditureDr
				.subtract(finalPreviousSumExpenditureCr);
		BigDecimal reverveAndSurpresPreviousAmount = finalPreviousIncomeTotalAmt
				.subtract(finalPreviousExpenditureTotalAmt);
		Long deptId = deparmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString());
		Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.SAD.toString(),
				AccountPrefix.TDP.toString(), orgId);
		final Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.CommonConstants.ACTIVE,
				AccountPrefix.ACN.toString(), orgId);
		VoucherTemplateMasterEntity template = voucherTemplateRepository.queryDefinedTemplate(voucherSubTypeId, deptId,
				orgId, status, null);
		Long newSacHeadId = null;
		int count = 0;
		if (template != null) {
			List<VoucherTemplateDetailEntity> listDetDetails = voucherTemplateRepository
					.queryDefinedTemplateDet(template.getTemplateId(), template.getOrgid());
			for (final VoucherTemplateDetailEntity detailTemplate : listDetDetails) {
				if (detailTemplate.getSacHeadId() != null && detailTemplate.getSacHeadId().longValue() != 0L) {
					newSacHeadId = detailTemplate.getSacHeadId();
					count++;
					break;
				}
			}
		} else {
			throw new NullPointerException(
					"VoucherTemplate not found in template For : " + voucherSubTypeId + " orgid : " + orgId);
		}
		if (count == 0) {
			throw new IllegalArgumentException(
					ApplicationSession.getInstance().getMessage("account.voucher.service.cpdid.paymode") + orgId);
		}
		String acHeadCode = "";
		if (newSacHeadId != null) {
			acHeadCode = secondaryheadMasterService.findByAccountHead(newSacHeadId);
		}

		AccountFinancialReportDTO accountFinancialReportDTO = new AccountFinancialReportDTO();

		accountFinancialReportDTO.setAccountCode(acHeadCode);
		accountFinancialReportDTO.setPreviousInTotal(reverveAndSurpresAmount);
		accountFinancialReportDTO.setPreviousExpTotal(reverveAndSurpresPreviousAmount);

		accountFinancialReportDTO.setAssetList(accountBalanceAssetList);
		accountFinancialReportDTO.setLiabilityList(accountBalanceLiabilityList);
		if (totalClosingAsset.compareTo(totalClosingliability) < 0) {
			accountFinancialReportDTO.setSumTransactionCR(totalClosingAsset.add(reverveAndSurpresAmount.abs()));
		} else {
			accountFinancialReportDTO.setSumTransactionCR(totalClosingAsset);
		}
		if (totalClosingliability.compareTo(totalClosingAsset) < 0) {
			accountFinancialReportDTO.setSumClosingCR(totalClosingliability.add(reverveAndSurpresAmount.abs()));
		} else {
			accountFinancialReportDTO.setSumClosingCR(totalClosingliability);
		}
		if (totalClosingAnOnAsset.compareTo(totalClosingAnOnliability) < 0) {
			accountFinancialReportDTO
					.setSumTransactionDR(totalClosingAnOnAsset.add(reverveAndSurpresPreviousAmount.abs()));
		} else {
			accountFinancialReportDTO.setSumTransactionDR(totalClosingAnOnAsset);
		}
		if (totalClosingAnOnliability.compareTo(totalClosingAnOnAsset) < 0) {
			accountFinancialReportDTO
					.setSumClosingDR(totalClosingAnOnliability.add(reverveAndSurpresPreviousAmount.abs()));
		} else {
			accountFinancialReportDTO.setSumClosingDR(totalClosingAnOnliability);
		}
		accountFinancialReportDTO.setFromDate(transactionDate);
		model.addAttribute(REPORT_LIST, accountFinancialReportDTO);
	}

	private List<Object[]> findAssetHead(long orgId) {
		return accountFinancialReportRepository.findAssetHead(orgId);
	}

	private List<Object[]> findliabilityHead(long orgId) {

		return accountFinancialReportRepository.findliabilityHead(orgId);
	}

	private List<Object[]> findAssetMajorHead(Long orgId) {
		return accountFinancialReportRepository.findAssetMajorHead(orgId);
	}

	private List<Object[]> findliabilityMajorHead(Long orgId) {

		return accountFinancialReportRepository.findliabilityMajorHead(orgId);
	}

	private List<Object[]> findPreviousYearBalanceSheet(long orgId, Date fromDates, Date stringToDate,
			Long financialYearId) {

		int year1 = getYearFromDate(fromDates);
		String fromDate1 = Utility.dateToString(fromDates).substring(0, 6) + (year1 - 1);
		Date fromDate = Utility.stringToDate(fromDate1);
		List<Object[]> yearlist = tbFinancialyearJpaRepository.getAllSLIPrefixDateFinincialYear(fromDates);
		if (yearlist != null && !yearlist.isEmpty()) {
			for (Object[] yearlists : yearlist)
				if (yearlists[0] != null && yearlists[2] != null)
					return accountFinancialReportRepository.findBalanceSheetPreviousReport(orgId, (Long) yearlists[0]);

		}
		return null;
	}

	private List<Object[]> findPreviousYearMajorHeadBalanceSheet(long orgId, Date fromDates, Date stringToDate,
			Long financialYearId) {

		int year1 = getYearFromDate(fromDates);
		String fromDate1 = Utility.dateToString(fromDates).substring(0, 6) + (year1 - 1);
		Date fromDate = Utility.stringToDate(fromDate1);
		List<Object[]> yearlist = tbFinancialyearJpaRepository.getAllSLIPrefixDateFinincialYear(fromDates);
		if (yearlist != null && !yearlist.isEmpty()) {
			for (Object[] yearlists : yearlist)
				if (yearlists[0] != null && yearlists[2] != null)
					return accountFinancialReportRepository.findBalanceSheetMajorHeadPreviousReport(orgId,
							(Long) yearlists[0]);
		}
		return null;
	}

	private List<Object[]> findPreviousYearMinorHeadBalanceSheet(long orgId, Date fromDates, Date stringToDate,
			Long financialYearId) {

		int year1 = getYearFromDate(fromDates);
		String fromDate1 = Utility.dateToString(fromDates).substring(0, 6) + (year1 - 1);
		Date fromDate = Utility.stringToDate(fromDate1);
		List<Object[]> yearlist = tbFinancialyearJpaRepository.getAllSLIPrefixDateFinincialYear(fromDates);
		if (yearlist != null && !yearlist.isEmpty()) {
			for (Object[] yearlists : yearlist)
				if (yearlists[0] != null && yearlists[2] != null)
					return accountFinancialReportRepository.findBalanceSheetMinorHeadPreviousReport(orgId,
							(Long) yearlists[0]);

		}
		return null;
	}
	
	private List<Object[]> findPreviousYearActualMajorHeadBalanceSheet(long orgId, Date fromDates, Date stringToDate,
			Long financialYearId) {

		int year1 = getYearFromDate(fromDates);
		String fromDate1 = Utility.dateToString(fromDates).substring(0, 6) + (year1 - 1);
		Date fromDate = Utility.stringToDate(fromDate1);
		List<Object[]> yearlist = tbFinancialyearJpaRepository.getAllSLIPrefixDateFinincialYear(fromDates);
		if (yearlist != null && !yearlist.isEmpty()) {
			for (Object[] yearlists : yearlist)
				if (yearlists[0] != null && yearlists[2] != null)
					return accountFinancialReportRepository.findBalanceSheetActualMajorHeadPreviousReport(orgId,
							(Long) yearlists[0]);

		}
		return null;
	}

	private void processChequeBookControlRegisterReport(ModelMap model, long orgId, Long accountHeadId) {
		AccountFinancialReportDTO chequeBookDto = null;
		List<AccountFinancialReportDTO> listofchequepayment = new ArrayList<>();

		final AccountFinancialReportDTO chequeBook = new AccountFinancialReportDTO();
		long EmpId = 0;
		String bankAcntId = null;
		String accountDesc = null;

		List<Object[]> bankacheadid = accountFinancialReportRepository.findBankAccIdByBank(orgId, accountHeadId);

		for (Object[] achead : bankacheadid) {

			bankAcntId = achead[0].toString();
			accountDesc = achead[1].toString();
		}

		final List<Object[]> ChequeBookControl = accountFinancialReportRepository
				.chequeBookControlRegisterbyAccount(orgId, bankAcntId);

		if (ChequeBookControl != null && !ChequeBookControl.isEmpty()) {
			for (Object[] chequeBookEntity : ChequeBookControl) {
				chequeBookDto = new AccountFinancialReportDTO();

				if (chequeBookEntity[0] != null) {
					String receiptdate = chequeBookEntity[0].toString();
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
					try {
						receiptdate = sdf2.format(sdf1.parse(receiptdate));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					chequeBookDto.setReceiptDate(receiptdate);
				}

				if (chequeBookEntity[1] != null) {
					chequeBookDto.setBankname(chequeBookEntity[1].toString());
				} else {
					chequeBookDto.setBankname(" ");
				}

				if (chequeBookEntity[2] != null) {
					chequeBookDto.setBranchName(chequeBookEntity[2].toString());
				} else {
					chequeBookDto.setBranchName(" ");
				}

				if (chequeBookEntity[3] != null) {
					chequeBookDto.setNoFirstLeave(chequeBookEntity[3].toString());
				}

				if (chequeBookEntity[4] != null) {
					chequeBookDto.setNolastLeave(chequeBookEntity[4].toString());
				} else {
					chequeBookDto.setNolastLeave(" ");
				}

				if (chequeBookEntity[5] != null) {

					String issuedate = chequeBookEntity[5].toString();
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
					try {
						issuedate = sdf2.format(sdf1.parse(issuedate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					chequeBookDto.setDoIssue(issuedate);

				} else {
					chequeBookDto.setDoIssue(" ");
				}

				if (chequeBookEntity[6] != null) {
					EmpId = Long.valueOf(chequeBookEntity[6].toString());
					String userName = accountFinancialReportRepository.queryReAppropriationReportForUser(EmpId);
					chequeBookDto.setIssueToWhom(userName);
				}
				listofchequepayment.add(chequeBookDto);
			}
		}
		chequeBook.setAccountCode(accountDesc);
		chequeBook.setListofchequepayment(listofchequepayment);
		model.addAttribute(REPORT_DATA, chequeBook);
	}

	private void processTransactionReversalReport(ModelMap model, long orgId, String fromDate, String toDate,
			String transactionTypeCode, Long fieldId) {
		// TODO Auto-generated method stub
		AccountFinancialReportDTO transactionReversaldto = null;
		List<AccountFinancialReportDTO> listTransactionReversal = new ArrayList<>();

		final AccountFinancialReportDTO transactionReversal = new AccountFinancialReportDTO();
		long EmpId = 0;
		Date transactionFromDate = Utility.stringToDate(fromDate);
		Date transactionToDate = Utility.stringToDate(toDate);
		List<Object[]> transactionReversalReceipt = null;

		if (transactionTypeCode.equals(MainetConstants.RP)) {
			if(fieldId != null && fieldId != -1L) {
				transactionReversalReceipt = accountFinancialReportRepository.transactionReversalReportReceiptByDateAndFieldId(orgId,
						transactionFromDate, transactionToDate, fieldId);
			}else {
				transactionReversalReceipt = accountFinancialReportRepository.transactionReversalReportReceiptByDate(orgId,
						transactionFromDate, transactionToDate);
			}
			List<Department> deptList = deptService.getDepartments(MainetConstants.STATUS.ACTIVE);
			if (CollectionUtils.isNotEmpty(transactionReversalReceipt)) {
				if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
						MainetConstants.ENV_SKDCL)) {
					transactionReversalReceipt=transactionReversalReceipt.stream().map(m->{
						for(Department d:deptList) {
							if(d.getDpDeptid()==Long.valueOf(m[9].toString())) {
								m[0]=d.getDpDeptcode().concat(m[0].toString());
							}
						}
						return m;
					}).collect(Collectors.toList());
				}
			}
			transactionReversal.setAccountHead("Receipt Entry");
		}

		if (transactionTypeCode.equals(MainetConstants.BP)) {
			transactionReversalReceipt = accountFinancialReportRepository.transactionReversalReportBillByDate(orgId,
					transactionFromDate, transactionToDate);
			transactionReversal.setAccountHead("Bill / Invoice Entry");
		}
		if (transactionTypeCode.equals(MainetConstants.DSE)) {
			transactionReversalReceipt = accountFinancialReportRepository
					.transactionReversalReportDepositeSlipEntryByDate(orgId, transactionFromDate, transactionToDate);
			transactionReversal.setAccountHead("Deposit Slip Entry");
		}

		if (transactionTypeCode.equals(MainetConstants.DPE) || transactionTypeCode.equals(MainetConstants.BPE)) {
			transactionReversalReceipt = accountFinancialReportRepository
					.transactionReversalReportDirectPaymentByDate(orgId, transactionFromDate, transactionToDate);
			transactionReversal.setAccountHead("Payment Entry");
		}

		if (transactionReversalReceipt != null && !transactionReversalReceipt.isEmpty()) {

			for (Object[] receiptentity : transactionReversalReceipt) {
				transactionReversaldto = new AccountFinancialReportDTO();

				if (receiptentity[0] != null) {
					transactionReversaldto.setTransactionNo(receiptentity[0].toString());
				}

				if (receiptentity[1] != null) {
					String transactiondate = receiptentity[1].toString();
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
					try {
						transactiondate = sdf2.format(sdf1.parse(transactiondate));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					transactionReversaldto.setTransactionDate(transactiondate);
				}

				if (receiptentity[2] != null) {
					transactionReversaldto.setAmount(receiptentity[2].toString());
				}

				if (receiptentity[3] != null) {
					transactionReversaldto.setNarration(receiptentity[3].toString());
				}

				if (receiptentity[4] != null) {
					transactionReversaldto.setReceivePayname(receiptentity[4].toString());
				}

				if (receiptentity[5] != null) {
					EmpId = Long.valueOf(receiptentity[5].toString());
					String userName = accountFinancialReportRepository.queryReAppropriationReportForUser(EmpId);
					transactionReversaldto.setReversedBy(userName);
				}

				if (receiptentity[6] != null) {
					String reversaldate = receiptentity[6].toString();
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
					try {
						reversaldate = sdf2.format(sdf1.parse(reversaldate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					transactionReversaldto.setReversedDate(reversaldate);
				}

				if (receiptentity[7] != null) {
					transactionReversaldto.setReversalReason(receiptentity[7].toString());
				}

				if (receiptentity[8] != null) {
					EmpId = Long.valueOf(receiptentity[8].toString());
					String userName = accountFinancialReportRepository.queryReAppropriationReportForUser(EmpId);
					transactionReversaldto.setAuthorizedBy(userName);
				}

				listTransactionReversal.add(transactionReversaldto);
			}
		}
		transactionReversal.setTransactionReversal(listTransactionReversal);
		transactionReversal.setFromDate(fromDate);
		transactionReversal.setToDate(toDate);
		model.addAttribute(REPORT_DATA, transactionReversal);

	}

	private void processReceiptsBudgetStatusReportt(ModelMap model, long orgId, Long financialId,Long deptId,Long FunctionId) {
		AccountFinancialReportDTO receiptsBudgetdto = null;
		List<AccountFinancialReportDTO> listReceiptsBudget = new ArrayList<>();
		final AccountFinancialReportDTO ReceiptsBudget = new AccountFinancialReportDTO();
		long financialYearId = financialId;
		String financialYears = findFinancialYearByFinancialYearId(financialYearId);

		BigDecimal totalrevenue = new BigDecimal(0.00);
		BigDecimal totalcollected = new BigDecimal(0.00);
		BigDecimal sumoftotalbalance = new BigDecimal(0.00);

		String fromDate = null;
		String toDate = null;
		List<Object[]> frmdateTodate = accountFinancialReportRepository.getAllFinincialFromDate(financialId);
		for (Object[] dateEntity : frmdateTodate) {
			fromDate = dateEntity[1].toString();
			toDate = dateEntity[2].toString();
		}
		String ds1 = fromDate;
		String ds2 = toDate;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
		try {
			fromDate = sdf2.format(sdf1.parse(ds1));
			toDate = sdf2.format(sdf1.parse(ds2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date fromDates = Utility.stringToDate(fromDate);
		Date todates = Utility.stringToDate(toDate);
		final List<Object[]> receiptsBudgetStatus = accountFinancialReportRepository
				.ReceiptsBudgetStatusReport(fromDates, todates, financialId, orgId,deptId,FunctionId);

		if (receiptsBudgetStatus != null && !receiptsBudgetStatus.isEmpty()) {

			for (Object[] receiptsBudgetEntity : receiptsBudgetStatus) {
				receiptsBudgetdto = new AccountFinancialReportDTO();
				BigDecimal revenue = new BigDecimal(0.00);
				BigDecimal collected = new BigDecimal(0.00);
				BigDecimal totalbalance = new BigDecimal(0.00);

				if (receiptsBudgetEntity[0] != null) {
					int i;
					for (i = 0; i < receiptsBudgetEntity[0].toString().length(); i++) {
						char c = receiptsBudgetEntity[0].toString().charAt(i);
						if (c >= '0' && c > '9')
							break;
					}
					String acounthead = receiptsBudgetEntity[0].toString().substring(0, i);
					acounthead = acounthead.substring(0, acounthead.lastIndexOf(" - "));
					String accountCode = receiptsBudgetEntity[0].toString().substring(i);
					receiptsBudgetdto.setAccountHead(accountCode);
					receiptsBudgetdto.setAccountCode(acounthead);
				}
				if (receiptsBudgetEntity[2] != null) {
					receiptsBudgetdto.setOriginalEst(CommonMasterUtility
							.getAmountInIndianCurrency(new BigDecimal(receiptsBudgetEntity[2].toString())));
					revenue = revenue.add(new BigDecimal(receiptsBudgetEntity[2].toString()));
					totalrevenue = totalrevenue.add(new BigDecimal(receiptsBudgetEntity[2].toString()));

				} else if (receiptsBudgetEntity[1] != null) {
					receiptsBudgetdto.setOriginalEst(CommonMasterUtility
							.getAmountInIndianCurrency(new BigDecimal(receiptsBudgetEntity[1].toString())));
					revenue = revenue.add(new BigDecimal(receiptsBudgetEntity[1].toString()));
					totalrevenue = totalrevenue.add(new BigDecimal(receiptsBudgetEntity[1].toString()));

				} else {
					receiptsBudgetdto.setOriginalEst(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
				}

				if (receiptsBudgetEntity[3] != null) {
					receiptsBudgetdto.setTotalOrgEsmtAmt(CommonMasterUtility
							.getAmountInIndianCurrency(new BigDecimal(receiptsBudgetEntity[3].toString())));
					collected = collected.add(new BigDecimal(receiptsBudgetEntity[3].toString()));
					totalcollected = totalcollected.add(new BigDecimal(receiptsBudgetEntity[3].toString()));
				} else {
					receiptsBudgetdto.setTotalOrgEsmtAmt(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
				}

				totalbalance = revenue.subtract(collected);
				if (totalbalance != null) {
					receiptsBudgetdto.setTotalBalance(
							CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(totalbalance.toString())));
					sumoftotalbalance = sumoftotalbalance.add(new BigDecimal(totalbalance.toString()));
				} else {
					receiptsBudgetdto.setTotalBalance(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
				}

				listReceiptsBudget.add(receiptsBudgetdto);
			}
		}
		ReceiptsBudget.setListOfBudgetEstimation(listReceiptsBudget);
		ReceiptsBudget.setTotalrevenue(
				CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(totalrevenue.toString())));
		ReceiptsBudget.setTotalCollected(
				CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(totalcollected.toString())));
		ReceiptsBudget.setSumofTotalBalance(
				CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(sumoftotalbalance.toString())));
		ReceiptsBudget.setFinancialYear(financialYears);
		model.addAttribute(REPORT_DATA, ReceiptsBudget);

	}

	private void processExpenditureBudgetStatusReport(ModelMap model, long orgId, Long financialId) {
		BigDecimal totalrevenue = new BigDecimal(0.00);
		BigDecimal totalcollected = new BigDecimal(0.00);
		BigDecimal sumoftotalbalance = new BigDecimal(0.00);
		BigDecimal sumofAccrualAmt = new BigDecimal(0.00);
		AccountFinancialReportDTO expenditureBudgetdto = null;
		List<AccountFinancialReportDTO> listexpenditureBudge = new ArrayList<>();
		final AccountFinancialReportDTO expenditure = new AccountFinancialReportDTO();
		long financialYearId = financialId;
		String financialYears = findFinancialYearByFinancialYearId(financialYearId);
		String fromDate = null;
		String toDate = null;
		List<Object[]> frmdateTodate = accountFinancialReportRepository.getAllFinincialFromDate(financialId);
		for (Object[] dateEntity : frmdateTodate) {
			fromDate = dateEntity[1].toString();
			toDate = dateEntity[2].toString();
		}
		String ds1 = fromDate;
		String ds2 = toDate;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
		try {
			fromDate = sdf2.format(sdf1.parse(ds1));
			toDate = sdf2.format(sdf1.parse(ds2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date fromDates = Utility.stringToDate(fromDate);
		Date todates = Utility.stringToDate(toDate);

		/*final List<Object[]> expenditureBudget = accountFinancialReportRepository
				.expenditureBudgetStatusReport(fromDates, todates, financialId, orgId);*/
		final List<Object[]> expenditureBudget = accountFinancialReportRepository.expenditureBudgetStatusReportBasedOnDeptId(fromDates, todates, financialId, orgId);
		 List<Department> deptList = deptService.getDepartments(MainetConstants.STATUS.ACTIVE);
		 
		if (expenditureBudget != null && !expenditureBudget.isEmpty()) {
			for (Object[] expenditureBudgetEntity : expenditureBudget) {
				expenditureBudgetdto = new AccountFinancialReportDTO();
				BigDecimal revenue = new BigDecimal(0.00);
				BigDecimal collected = new BigDecimal(0.00);
				BigDecimal totalbalance = new BigDecimal(0.00);

				if (expenditureBudgetEntity[0] != null) {
					int i;
					for (i = 0; i < expenditureBudgetEntity[0].toString().length(); i++) {
						char c = expenditureBudgetEntity[0].toString().charAt(i);
						if (c >= '0' && c > '9')
							break;
					}
					
					if(expenditureBudgetEntity[4]!=null) {
						for(Department d :deptList) {
							if(d.getDpDeptid()!=null&&(d.getDpDeptid().equals(Long.valueOf(expenditureBudgetEntity[4].toString())))) {
								expenditureBudgetdto.setDeptName(d.getDpDeptdesc());
							}
						}
					}
					String acounthead = expenditureBudgetEntity[0].toString().substring(0, i);
					acounthead = acounthead.substring(0, acounthead.lastIndexOf(" - "));
					String accountCode = expenditureBudgetEntity[0].toString().substring(i);
					expenditureBudgetdto.setAccountHead(accountCode);
					expenditureBudgetdto.setAccountCode(acounthead);
				}

				if (expenditureBudgetEntity[2] != null) {
					expenditureBudgetdto.setOriginalEst(CommonMasterUtility
							.getAmountInIndianCurrency(new BigDecimal(expenditureBudgetEntity[2].toString())));
					revenue = revenue.add(new BigDecimal(expenditureBudgetEntity[2].toString()));
					totalrevenue = totalrevenue.add(new BigDecimal(expenditureBudgetEntity[2].toString()));

				} else if (expenditureBudgetEntity[1] != null) {
					expenditureBudgetdto.setOriginalEst(CommonMasterUtility
							.getAmountInIndianCurrency(new BigDecimal(expenditureBudgetEntity[1].toString())));
					revenue = revenue.add(new BigDecimal(expenditureBudgetEntity[1].toString()));
					totalrevenue = totalrevenue.add(new BigDecimal(expenditureBudgetEntity[1].toString()));

				} else {
					expenditureBudgetdto.setOriginalEst(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
				}

				if (expenditureBudgetEntity[3] != null) {
					expenditureBudgetdto.setTotalOrgEsmtAmt(CommonMasterUtility
							.getAmountInIndianCurrency(new BigDecimal(expenditureBudgetEntity[3].toString())));
					collected = collected.add(new BigDecimal(expenditureBudgetEntity[3].toString()));
					totalcollected = totalcollected.add(new BigDecimal(expenditureBudgetEntity[3].toString()));
				} else {
					expenditureBudgetdto.setTotalOrgEsmtAmt(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
				}

				totalbalance = revenue.subtract(collected);
				if (totalbalance != null) {
					expenditureBudgetdto.setTotalBalance(
							CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(totalbalance.toString())));
					sumoftotalbalance = sumoftotalbalance.add(new BigDecimal(totalbalance.toString()));
				} else {
					expenditureBudgetdto.setTotalBalance(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
				}
               if(expenditureBudgetEntity[4]!=null && expenditureBudgetEntity[5]!=null) {
            	   String accrualAmt=null;
            	   if(expenditureBudgetEntity[6]!=null) {
            		    accrualAmt = accountBudgetProjectedExpenditureService.getAllAcrualExpenditureAmountFieldId(financialId,Long.valueOf(expenditureBudgetEntity[5].toString()),
       	                    orgId,Long.valueOf(expenditureBudgetEntity[4].toString()),Long.valueOf(expenditureBudgetEntity[6].toString()));
            	   }
            	   else {
            		    accrualAmt = accountBudgetProjectedExpenditureService.getAllAcrualExpenditureAmount(financialId,Long.valueOf(expenditureBudgetEntity[5].toString()),
       	                    orgId,Long.valueOf(expenditureBudgetEntity[4].toString()));
            	   }
				
				expenditureBudgetdto.setAmount(CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(accrualAmt)));
				sumofAccrualAmt=sumofAccrualAmt.add(new BigDecimal(accrualAmt));
               }
               if(expenditureBudgetEntity[6]!=null) {
            	   expenditureBudgetdto.setFieldId( tbAcFieldMasterService.getFieldDesc(Long.valueOf(expenditureBudgetEntity[6].toString())));         	  
               }
				listexpenditureBudge.add(expenditureBudgetdto);
			}
		}
		expenditure.setListOfBudgetEstimation(listexpenditureBudge);
		expenditure.setTotalrevenue(
				CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(totalrevenue.toString())));
		expenditure.setTotalCollected(
				CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(totalcollected.toString())));
		expenditure.setSumofTotalBalance(
				CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(sumoftotalbalance.toString())));
		expenditure.setSubTotalAmountIndianCurrency(sumofAccrualAmt.toString());
		expenditure.setFinancialYear(financialYears);
		model.addAttribute(REPORT_DATA, expenditure);
	}

	private void processchequeReceivedReportt(ModelMap model, long orgId, String fromDate, String toDate,
			String categoryId) {
		AccountFinancialReportDTO chequeReceiveddto = null;
		List<AccountFinancialReportDTO> listchequeReceived = new ArrayList<>();
		final AccountFinancialReportDTO chequeReceived = new AccountFinancialReportDTO();
		final List<Object[]> chequeReceivedReport = accountFinancialReportRepository.chequeReceivedStatusReport(
				Utility.stringToDate(fromDate), Utility.stringToDate(toDate), orgId, categoryId);
		 List<Department> deptList = deptService.getDepartments(MainetConstants.STATUS.ACTIVE);
			
		if (chequeReceivedReport != null && !chequeReceivedReport.isEmpty()) {
			for (Object[] chequeReceivedEntity : chequeReceivedReport) {
				chequeReceiveddto = new AccountFinancialReportDTO();

				if (chequeReceivedEntity[2] != null) {
					chequeReceiveddto.setReceiptNo(chequeReceivedEntity[2].toString());
					if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
						List<Department> deptFilterList = deptList.stream().filter(f->f.getDpDeptid()==Long.valueOf(chequeReceivedEntity[11].toString())).collect(Collectors.toList());
						chequeReceiveddto.setReceiptNo(deptFilterList.get(0).getDpDeptcode().concat(chequeReceivedEntity[2].toString()));
					}
				}

				if (chequeReceivedEntity[3] != null) {
					String receivedate = chequeReceivedEntity[3].toString();
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
					try {
						receivedate = sdf2.format(sdf1.parse(receivedate));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					chequeReceiveddto.setReceiptDate(receivedate);
				}
				if (chequeReceivedEntity[4] != null) {
					chequeReceiveddto.setReceivedFrom(chequeReceivedEntity[4].toString());
				}
				if (chequeReceivedEntity[5] != null) {
					chequeReceiveddto.setChequeNo(chequeReceivedEntity[5].toString());
				}

				if (chequeReceivedEntity[6] != null) {
					String chequedate = chequeReceivedEntity[6].toString();
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
					try {
						chequedate = sdf2.format(sdf1.parse(chequedate));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					chequeReceiveddto.setChequeIssueDate(chequedate);
				}

				if (chequeReceivedEntity[8] != null) {
					chequeReceiveddto.setRdAmount(CommonMasterUtility
							.getAmountInIndianCurrency(new BigDecimal(chequeReceivedEntity[8].toString())));
				}

				if (chequeReceivedEntity[9] != null) {
					chequeReceiveddto.setBankname(chequeReceivedEntity[9].toString());
				}
				if (chequeReceivedEntity[10] != null) {
					chequeReceiveddto.setRemarks(chequeReceivedEntity[10].toString());
				}

				listchequeReceived.add(chequeReceiveddto);
			}
		}
		chequeReceived.setListOfBudgetEstimation(listchequeReceived);
		chequeReceived.setFromDate(fromDate);
		chequeReceived.setToDate(toDate);
		model.addAttribute(REPORT_DATA, chequeReceived);
	}

	private void processChequeCancellationReport(ModelMap model, long orgId, String fromDate, String toDate) {
		// TODO Auto-generated method stub
		AccountFinancialReportDTO chequeCancellationdto = null;
		List<AccountFinancialReportDTO> listChequeCancellation = new ArrayList<>();
		final AccountFinancialReportDTO chequeCancellation = new AccountFinancialReportDTO();
		final List<Object[]> chequeCancellationData = accountFinancialReportRepository
				.chequeCancellationReportByDate(Utility.stringToDate(fromDate), Utility.stringToDate(toDate), orgId);

		if (chequeCancellationData != null && !chequeCancellationData.isEmpty()) {

			for (Object[] chequeCancellationEntity : chequeCancellationData) {

				chequeCancellationdto = new AccountFinancialReportDTO();

				if (chequeCancellationEntity[0] != null) {
					chequeCancellationdto.setChequeNo(chequeCancellationEntity[0].toString());
				}

				if (chequeCancellationEntity[1] != null) {
					chequeCancellationdto.setChequeIssueDate(Utility.dateToString((Date) chequeCancellationEntity[1]));
				}
				if (chequeCancellationEntity[2] != null) {
					chequeCancellationdto.setPaymentAmnt(chequeCancellationEntity[2].toString());
				}

				if (chequeCancellationEntity[3] != null) {
					chequeCancellationdto.setCancellationDate(Utility.dateToString((Date) chequeCancellationEntity[3]));
				}
				if (chequeCancellationEntity[4] != null) {
					chequeCancellationdto.setRemarks(chequeCancellationEntity[4].toString());
				}
				if (chequeCancellationEntity[5] != null) {
					chequeCancellationdto.setNewChequeNo(chequeCancellationEntity[5].toString());
				}
				if (chequeCancellationEntity[6] != null) {
					chequeCancellationdto.setPaymentNo(chequeCancellationEntity[6].toString());
				}
				if (chequeCancellationEntity[8] != null) {
					chequeCancellationdto.setBankname(chequeCancellationEntity[8].toString());
				}
				if (chequeCancellationEntity[9] != null) {
					chequeCancellationdto.setBankAcNo(chequeCancellationEntity[9].toString());
				}

				listChequeCancellation.add(chequeCancellationdto);
			}
		}
		chequeCancellation.setListofchequepayment(listChequeCancellation);
		chequeCancellation.setFromDate(fromDate);
		chequeCancellation.setToDate(toDate);
		model.addAttribute(REPORT_DATA, chequeCancellation);
	}

	private void processPaymentAndReceiptReport(ModelMap model, String fromDate, String toDate, String reportTypeCode,
			long orgId, Long accountHeadId) {
		BigDecimal cashamount1 = new BigDecimal(0.00);
		BigDecimal cashamount2 = new BigDecimal(0.00);
		BigDecimal cashamount3 = new BigDecimal(0.00);
		BigDecimal cashamount4 = new BigDecimal(0.00);
		BigDecimal combineOpenBalGet = new BigDecimal(0.00);
		BigDecimal previousCombineOpenBalGet = new BigDecimal(0.00);
		BigDecimal combineOpenBal = new BigDecimal(0.00);
		BigDecimal previousCombineOpenBal = new BigDecimal(0.00);
		BigDecimal openingCash = new BigDecimal(0.00);
		BigDecimal previousOpeningCash = new BigDecimal(0.00);
		BigDecimal closingOfBankGet = new BigDecimal(0.00);
		BigDecimal previousClosingOfBankGet = new BigDecimal(0.00);
		BigDecimal closingOfBank = new BigDecimal(0.00);
		BigDecimal previousClosingOfBank = new BigDecimal(0.00);
		BigDecimal closingCashAmt = new BigDecimal(0.00);
		BigDecimal previousClosingCashAmt = new BigDecimal(0.00);
		BigDecimal sumBudgetAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumAcutualOpenReceiptAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumAcutualNonOpenReceiptAmount = new BigDecimal(
				MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumBudgetAmountPayment = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumAcutualAmountOpenPayment = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumAcutualOpenPreviousReceiptAmount = new BigDecimal(
				MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumAcutualNonOpenPreviousReceiptAmount = new BigDecimal(
				MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumBudgetAmountPreviousPayment = new BigDecimal(
				MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumAcutualAmountOpenPreviousPayment = new BigDecimal(
				MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumAcutualAmountNonOpenPayment = new BigDecimal(
				MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumAcutualAmountPaymentBalance = new BigDecimal(
				MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumbalanceRecoverable = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumbalanceOfTillDate = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumbalanceOfPayTilDate = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		List<Object[]> openingPaymentSideLists = null;
		List<Object[]> nonOpeningPaymentSideLists = null;
		List<Object[]> operatingReceiptSideLists = null;
		List<Object[]> nonOperatingReceiptSideLists = null;
		List<Object[]> openingPreviousPaymentSideLists = null;
		List<Object[]> nonOpeningPreviousPaymentSideLists = null;
		List<Object[]> operatingPreviousReceiptSideLists = null;
		List<Object[]> nonOperatingPreviousReceiptSideLists = null;
		Map<String,BigDecimal> rprPrevAmtMap=new HashMap<>();
		final Date frmDate = Utility.stringToDate(fromDate);
		final Date tDate = Utility.stringToDate(toDate);
		final long cpdIdPayMode = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CASH.getValue(),
				AccountPrefix.PAY.toString(), orgId);
		List<LookUp> accountHeadList = secondaryheadMasterService.findAccountHeadsByOrgIdBankBook(orgId);
		if (accountHeadList != null && !accountHeadList.isEmpty()) {
			List<Long> headList = accountHeadList.stream().map(LookUp::getLookUpId).collect(Collectors.toList());
				combineOpenBalGet = calculateOpeningBalanceAsOnDateAndAccHeads(headList, orgId, fromDate);
				if (combineOpenBalGet != null) {
					combineOpenBal = combineOpenBal.add(combineOpenBalGet);
				}
				closingOfBankGet = calculateClosingBalanceAsOnDateAndAccHeads(headList, orgId, toDate);
				if (closingOfBankGet != null) {
					closingOfBank = closingOfBank.add(closingOfBankGet);
				}
				previousCombineOpenBalGet = calculateOpeningBalanceAsOnPreviousDateAndAccHeads(headList,
						orgId, fromDate);
				if (previousCombineOpenBalGet != null) {
					previousCombineOpenBal = previousCombineOpenBal.add(previousCombineOpenBalGet);
				}
				previousClosingOfBankGet = calculateClosingBalanceAsOnPreviousDateAndAccHeads(headList,
						orgId, toDate);
				if (previousClosingOfBankGet != null) {
					previousClosingOfBank = previousClosingOfBank.add(previousClosingOfBankGet);
				
			}
		}
		
		
		long finanacialYearId = queryToFindFinanacialYearID(frmDate);
		String isSecoundary="N";
		Object[] results =null;
		Long value=null;
        Long id=null;
        results = tbAcCodingstructureMasJpaRepository.queryAccountHeadById(accountHeadId,orgId);
        if (results == null || (results.length == 0)) {
        	results=tbAcCodingstructureMasJpaRepository.queryAccountHeadById(accountHeadId,ApplicationSession.getInstance().getSuperUserOrganization().getOrgid());
        }
        Object[] app = (Object[]) results[0];
        value=((long) app[1]); 
        id=((long) app[3]);;
       
		LookUp perfix= CommonMasterUtility.getNonHierarchicalLookUpObject(id,UserSession.getCurrent().getOrganisation());
		if(perfix.getLookUpCode().equals(AccountConstants.AHP.getValue())) {
					
		//final Long param1 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.AHP.getValue(),
				//PrefixConstants.CMD, orgId);
		//final Long param2 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.AHS.getValue(),
				//PrefixConstants.CMD, orgId);
		//List<LookUp> levelHead = acCodingstructureMasService.queryAccountHeadByChartOfAccount(param1, param2, orgId);
		//if (levelHead != null && !levelHead.isEmpty()) {
		if(value==2) { // Minor head  
				operatingReceiptSideLists = queryClassifiedBudgetReceiptSideOpeningReportDataL3(frmDate, tDate, orgId,
						accountHeadId);
				nonOperatingReceiptSideLists = queryClassifiedBudgetReceiptSideNonOpeningReportDataL3(frmDate, tDate,
						orgId, accountHeadId);
				openingPaymentSideLists = queryClassifiedBudgetPaymentSideOpeningReportDataL3(frmDate, tDate, orgId,
						accountHeadId);
				nonOpeningPaymentSideLists = queryClassifiedBudgetPaymentSideNonOpeningReportDataL3(frmDate, tDate,
						orgId, accountHeadId);
				operatingPreviousReceiptSideLists = queryClassifiedBudgetReceiptSideOpeningPreviousReportDataL3(frmDate,
						tDate, orgId, accountHeadId);
				nonOperatingPreviousReceiptSideLists = queryClassifiedBudgetReceiptSideNonOpeningPreviousReportDataL3(
						frmDate, tDate, orgId, accountHeadId);
				openingPreviousPaymentSideLists = queryClassifiedBudgetPaymentSideOpeningPreviousReportDataL3(frmDate,
						tDate, orgId, accountHeadId);
				nonOpeningPreviousPaymentSideLists = queryClassifiedBudgetPaymentSideNonOpeningPreviousReportDataL3(
						frmDate, tDate, orgId, accountHeadId);

			}else if(value==1) {//major head
				operatingReceiptSideLists = queryClassifiedBudgetReceiptSideOpeningReportDataLevel3(frmDate, tDate, orgId,
						accountHeadId);
				nonOperatingReceiptSideLists = queryClassifiedBudgetReceiptSideNonOpeningReportDataLevel3(frmDate, tDate, orgId,
						accountHeadId);
				openingPaymentSideLists = queryClassifiedBudgetPaymentSideOpeningReportDataLevel3(frmDate, tDate, orgId,
						accountHeadId);
				nonOpeningPaymentSideLists = queryClassifiedBudgetPaymentSideNonOpeningReportDataLevel3(frmDate, tDate, orgId,
						accountHeadId);
				operatingPreviousReceiptSideLists=queryClassifiedBudgetReceiptSideOpeningPreviousReportDataLevel3(frmDate, tDate, orgId,
						accountHeadId);
				nonOperatingPreviousReceiptSideLists=queryClassifiedBudgetReceiptSideNonOpeningPreviousReportDataLevel3(frmDate, tDate, orgId,
						accountHeadId);
				openingPreviousPaymentSideLists=queryClassifiedBudgetPaymentSideOpeningPreviousReportDataLevel3(frmDate, tDate, orgId,
						accountHeadId);
				nonOpeningPreviousPaymentSideLists=queryClassifiedBudgetPaymentSideNonOpeningPreviousReportDataLevel3(frmDate, tDate, orgId,
						accountHeadId);
			} 
			else {// detail head,
				operatingReceiptSideLists = queryClassifiedBudgetReceiptSideOpeningReportDataL1(frmDate, tDate, orgId,
						accountHeadId);
				nonOperatingReceiptSideLists = queryClassifiedBudgetReceiptSideNonOpeningReportDataL1(frmDate, tDate,
						orgId, accountHeadId);
				openingPaymentSideLists = queryClassifiedBudgetPaymentSideOpeningReportDataL1(frmDate, tDate, orgId,
						accountHeadId);
				nonOpeningPaymentSideLists = queryClassifiedBudgetPaymentSideNonOpeningReportDataL1(frmDate, tDate,
						orgId, accountHeadId);
				operatingPreviousReceiptSideLists = queryClassifiedBudgetReceiptSideOpeningPreviousReportDataL2(frmDate,
						tDate, orgId, accountHeadId);
				nonOperatingPreviousReceiptSideLists = queryClassifiedBudgetReceiptSideNonOpeningPreviousReportDataL2(
						frmDate, tDate, orgId, accountHeadId);
				openingPreviousPaymentSideLists = queryClassifiedBudgetPaymentSideOpeningPreviousReportDataL2(frmDate,
						tDate, orgId, accountHeadId);
				nonOpeningPreviousPaymentSideLists = queryClassifiedBudgetPaymentSideNonOpeningPreviousReportDataL2(
						frmDate, tDate, orgId, accountHeadId);
			}
			
	}
			else  {//secondary head
				isSecoundary="Y";
				operatingReceiptSideLists = queryClassifiedBudgetReceiptSideOpeningData(frmDate, tDate, orgId,
						accountHeadId);
				nonOperatingReceiptSideLists = queryClassifiedBudgetReceiptSideNonOpeningData(frmDate, tDate, orgId,
						accountHeadId);
				openingPaymentSideLists = queryClassifiedBudgetPaymentSideOpeningReportData(frmDate, tDate, orgId,
						accountHeadId);
				nonOpeningPaymentSideLists = queryClassifiedBudgetPaymentSideNonOpeningReportData(frmDate, tDate, orgId,
						accountHeadId);
				operatingPreviousReceiptSideLists = queryClassifiedBudgetPreviousReceiptSideReportDataL3(frmDate, tDate,
						orgId, accountHeadId);
				nonOperatingPreviousReceiptSideLists = queryClassifiedBudgetnonOperatingPreviousReceiptSideReportDataL3(
						frmDate, tDate, orgId, accountHeadId);
				openingPreviousPaymentSideLists = queryClassifiedBudgetopeningPreviousPaymentReportDataL3(frmDate,
						tDate, orgId, accountHeadId);
				nonOpeningPreviousPaymentSideLists = queryClassifiedBudgetnonOpeningPreviousPaymentSideReportDataL3(
						frmDate, tDate, orgId, accountHeadId);
			}
	//	}
		
		final AccountFinancialReportDTO openingReceiptBean = new AccountFinancialReportDTO();
		final AccountFinancialReportDTO nonOpeningReceiptBean = new AccountFinancialReportDTO();
		final AccountFinancialReportDTO openingPaymentBean = new AccountFinancialReportDTO();
		final AccountFinancialReportDTO nonOpeningPaymentBean = new AccountFinancialReportDTO();
		if (isReceiptPaymentRecordsFound(operatingReceiptSideLists, nonOperatingReceiptSideLists,
				openingPaymentSideLists, nonOpeningPaymentSideLists)) {
			final AccountFinancialReportDTO dto = new AccountFinancialReportDTO();
			dto.setFromDate(fromDate);
			dto.setToDate(toDate);
			// opening balance of cash mode and bank also start
			/*
			 * Long sacHeadId =
			 * accountFinancialReportRepository.findSacHeadIdByPayModeId(cpdIdPayMode,
			 * orgId); // opening balance cash and bank start openingCash =
			 * calculateOpeningBalanceAsOnDate(sacHeadId, orgId, fromDate); closingCashAmt =
			 * calculateClosingBalanceAsOnDate(sacHeadId, orgId, toDate);
			 * previousOpeningCash = calculateOpeningBalanceAsOnPreviousDate(sacHeadId,
			 * orgId, fromDate); previousClosingCashAmt =
			 * calculateClosingBalanceAsOnPreviousDate(sacHeadId, orgId, toDate);
			 */
			List<Object[]> cashSacHeadIdList = (List<Object[]>) accountFinancialReportRepository
					.findSacHeadIdByCashPayModeId(orgId);
			if (cashSacHeadIdList != null && !cashSacHeadIdList.isEmpty()) {
				for (Object accountHeadLists : cashSacHeadIdList) {
					Long sacHeadId1 = Long.valueOf(accountHeadLists.toString());
					if (sacHeadId1 != null) {
						cashamount1 = calculateOpeningBalanceAsOnDate(sacHeadId1, orgId, fromDate);
						if (cashamount1 != null) {
							openingCash = openingCash.add(cashamount1);
						}
						cashamount2 = calculateClosingBalanceAsOnDate(sacHeadId1, orgId, toDate);
						if (cashamount2 != null) {
							closingCashAmt = closingCashAmt.add(cashamount2);
						}

						cashamount3 = calculateOpeningBalanceAsOnPreviousDate(sacHeadId1, orgId, fromDate);
						if (cashamount3 != null) {
							previousOpeningCash = previousOpeningCash.add(cashamount3);
						}
						cashamount4 = calculateClosingBalanceAsOnPreviousDate(sacHeadId1, orgId, toDate);
						if (cashamount4 != null) {
							previousClosingCashAmt = previousClosingCashAmt.add(cashamount4);
						}

					}
				}
			}
			if (combineOpenBal != null) {
				dto.setIndCurrBankBalance(CommonMasterUtility.getAmountInIndianCurrency(combineOpenBal));
			}
			if (closingOfBank != null) {
				dto.setIndCurrClosingBankAmount(CommonMasterUtility.getAmountInIndianCurrency(closingOfBank));
			}

			if (openingCash != null) {
				dto.setIndCurrOpeningBalance(CommonMasterUtility.getAmountInIndianCurrency(openingCash));
			}
			if (closingCashAmt != null) {
				dto.setIndCurrClosingBalance(CommonMasterUtility.getAmountInIndianCurrency(closingCashAmt));
			}

			if (previousCombineOpenBal != null) {
				dto.setIndCurrPreviousOpeningBankAmt(CommonMasterUtility.getAmountInIndianCurrency(previousCombineOpenBal));
			} else {
				dto.setIndCurrPreviousOpeningBankAmt(CommonMasterUtility.getAmountInIndianCurrency(previousCombineOpenBal));
			}
			if (previousOpeningCash != null) {
				dto.setIndCurrPreviousOpeningCashAmt(CommonMasterUtility.getAmountInIndianCurrency(previousOpeningCash));
			} else {
				dto.setIndCurrPreviousOpeningCashAmt(CommonMasterUtility.getAmountInIndianCurrency(previousOpeningCash));
			}
			if (previousClosingOfBank != null) {
				dto.setIndCurrPreviousClosingBankAmt(CommonMasterUtility.getAmountInIndianCurrency(previousClosingOfBank));
			} else {
				dto.setIndCurrPreviousClosingBankAmt(CommonMasterUtility.getAmountInIndianCurrency(previousClosingOfBank));
			}
			if (previousClosingCashAmt != null) {
				dto.setIndCurrPreviousClosingCashAmt(CommonMasterUtility.getAmountInIndianCurrency(previousClosingCashAmt));
			} else {
				dto.setIndCurrPreviousClosingCashAmt(CommonMasterUtility.getAmountInIndianCurrency(previousClosingCashAmt));
			}

			BigDecimal totalOpenCashBankAmount = new BigDecimal(0.00);
			if (openingCash != null && combineOpenBal != null) {
				totalOpenCashBankAmount = openingCash.add(combineOpenBal);
				dto.setOpeningBalanceIndianCurrency(
						(CommonMasterUtility.getAmountInIndianCurrency(totalOpenCashBankAmount)));
			}
			BigDecimal totalOpenPreviousCashBankAmount = new BigDecimal(0.00);
			if (previousOpeningCash != null && previousCombineOpenBal != null) {
				totalOpenPreviousCashBankAmount = previousOpeningCash.add(previousCombineOpenBal);
				dto.setTotalBalance(CommonMasterUtility.getAmountInIndianCurrency(totalOpenPreviousCashBankAmount));
			}
			BigDecimal totalClosingCashAmt = new BigDecimal(0.00);
			if (closingCashAmt != null && closingOfBank != null) {
				totalClosingCashAmt = closingCashAmt.add(closingOfBank);
				dto.setClosingCash(CommonMasterUtility.getAmountInIndianCurrency(totalClosingCashAmt));
			}
			BigDecimal totalClosingPreviousCashAmt = new BigDecimal(0.00);
			if (previousClosingCashAmt != null && previousClosingOfBank != null) {
				totalClosingPreviousCashAmt = previousClosingCashAmt.add(previousClosingOfBank);
				dto.setClosingAmt(CommonMasterUtility.getAmountInIndianCurrency(totalClosingPreviousCashAmt));
			}
			/*
			 * if (closingCashAmt != null) { if (closingCashAmt.signum() == -1) {
			 * dto.setClosingCash(CommonMasterUtility.getAmountInIndianCurrency(
			 * closingCashAmt.abs()) + " Cr."); } else {
			 * dto.setClosingCash(CommonMasterUtility.getAmountInIndianCurrency(
			 * closingCashAmt) + " Dr."); } } if (closingOfBank != null) { if
			 * (closingOfBank.signum() == -1) {
			 * dto.setClosingAmt(CommonMasterUtility.getAmountInIndianCurrency(closingOfBank
			 * .abs()) + " Cr."); } else {
			 * dto.setClosingAmt(CommonMasterUtility.getAmountInIndianCurrency(
			 * closingOfBank) + " Dr."); } }
			 */
			/*
			 * if (combineOpenBal != null) { if (combineOpenBal.signum() == -1) {
			 * dto.setTotalBalance(CommonMasterUtility.getAmountInIndianCurrency(
			 * combineOpenBal.abs()) + " Cr."); } else {
			 * dto.setTotalBalance(CommonMasterUtility.getAmountInIndianCurrency(
			 * combineOpenBal) + " Dr."); } }
			 */

			BigDecimal sumOfReceiptsGrandTotalAmt = BigDecimal.ZERO;
			BigDecimal sumOfPaymentsGrandTotalAmt = BigDecimal.ZERO;
			BigDecimal sumOfReceiptsPreviousGrandTotalAmt = BigDecimal.ZERO;
			BigDecimal sumOfPaymentsPreviousGrandTotalAmt = BigDecimal.ZERO;
			// opening balance receipt of cash mode and bank also end
			final List<AccountFinancialReportDTO> openinglist = new ArrayList<>();
			for (Object[] objectListTillYear : operatingReceiptSideLists) {
				final AccountFinancialReportDTO openingDTO = new AccountFinancialReportDTO();
				if (objectListTillYear[0] != null) {
					if(isSecoundary.equalsIgnoreCase("N"))
					openingDTO.setAccountCode(objectListTillYear[0].toString());
					if (operatingPreviousReceiptSideLists != null) {
						for (Object[] objectListPreviousYear : operatingPreviousReceiptSideLists) {
							if (objectListPreviousYear[0] != null) {
								if (objectListTillYear[0].equals(objectListPreviousYear[0])) {
									if (objectListPreviousYear[2] != null) {
										sumAcutualOpenPreviousReceiptAmount = sumAcutualOpenPreviousReceiptAmount
												.add(new BigDecimal(objectListPreviousYear[2].toString()));
										openingDTO.setBalanceRecoverableIndianCurrency(
												CommonMasterUtility.getAmountInIndianCurrency(
														new BigDecimal(objectListPreviousYear[2].toString())));
									}
								}
							}
						}
					}
				}
				if (objectListTillYear[1] != null) {
					openingDTO.setAccountHead(objectListTillYear[1].toString());
				}
				if (objectListTillYear[2] == null) {
					openingDTO.setActualAmountReceived(new BigDecimal(0.00));
					openingDTO.setActualAmountReceivedIndianCurrency(
							CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(0.00)));
					sumAcutualOpenReceiptAmount = sumAcutualOpenReceiptAmount.add(new BigDecimal(0.00));
				} else {
					openingDTO.setActualAmountReceived(new BigDecimal(objectListTillYear[2].toString()));
					openingDTO.setActualAmountReceivedIndianCurrency(CommonMasterUtility
							.getAmountInIndianCurrency(new BigDecimal(objectListTillYear[2].toString())));
					sumAcutualOpenReceiptAmount = sumAcutualOpenReceiptAmount
							.add(new BigDecimal(objectListTillYear[2].toString()));
				}
				openinglist.add(openingDTO);
			}
			if(CollectionUtils.isNotEmpty(operatingPreviousReceiptSideLists)) {
				openinglist.addAll(setPreviousRPRReportDate(operatingPreviousReceiptSideLists, openinglist, rprPrevAmtMap, isSecoundary));
			}
			if (sumbalanceOfTillDate != null) {
				openingReceiptBean.setBalanceAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumbalanceOfTillDate));
			}
			if (sumbalanceRecoverable != null) {
				openingReceiptBean.setSumbalanceRecoverableIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumbalanceRecoverable));
			}
			if (sumBudgetAmount != null) {
				openingReceiptBean.setSumBudgetAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumBudgetAmount));
			}
			if (sumAcutualOpenReceiptAmount != null) {
				sumOfReceiptsGrandTotalAmt = sumOfReceiptsGrandTotalAmt.add(sumAcutualOpenReceiptAmount);
				openingReceiptBean.setSumAcutualAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumAcutualOpenReceiptAmount));
			}
			if (sumAcutualOpenPreviousReceiptAmount != null) {
				sumOfReceiptsPreviousGrandTotalAmt = sumOfReceiptsPreviousGrandTotalAmt
						.add(sumAcutualOpenPreviousReceiptAmount);
				// openingReceiptBean.setSubTotalAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(sumAcutualOpenPreviousReceiptAmount));
			}
			openingReceiptBean.setListOfSum(openinglist);

			// non opening receipt balance of cash mode and bank also end
			final List<AccountFinancialReportDTO> nonOpeningList = new ArrayList<>();
			for (Object[] objectListTillYear : nonOperatingReceiptSideLists) {
				final AccountFinancialReportDTO nonOpeningDTO = new AccountFinancialReportDTO();
				if (objectListTillYear[0] != null) {
					if(isSecoundary.equalsIgnoreCase("N"))
					nonOpeningDTO.setAccountCode(objectListTillYear[0].toString());
					if (nonOperatingPreviousReceiptSideLists != null) {
						for (Object[] objectListnonOperatingPreviousYear : nonOperatingPreviousReceiptSideLists) {
							if (objectListnonOperatingPreviousYear[0] != null) {
								if (objectListTillYear[0].equals(objectListnonOperatingPreviousYear[0])) {
									if (objectListnonOperatingPreviousYear[2] != null) {
										sumAcutualNonOpenPreviousReceiptAmount = sumAcutualNonOpenPreviousReceiptAmount
												.add(new BigDecimal(objectListnonOperatingPreviousYear[2].toString()));
										nonOpeningDTO.setBalanceRecoverableIndianCurrency(
												CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(
														objectListnonOperatingPreviousYear[2].toString())));
									}
								}
							}
						}
					}
				}
				if (objectListTillYear[1] != null) {
					nonOpeningDTO.setAccountHead(objectListTillYear[1].toString());
				}
				if (objectListTillYear[2] == null) {
					nonOpeningDTO.setActualAmountReceived(new BigDecimal(0.00));
					nonOpeningDTO.setActualAmountReceivedIndianCurrency(
							CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(0.00)));
					sumAcutualNonOpenReceiptAmount = sumAcutualNonOpenReceiptAmount.add(new BigDecimal(0.00));
				} else {
					nonOpeningDTO.setActualAmountReceived(new BigDecimal(objectListTillYear[2].toString()));
					nonOpeningDTO.setActualAmountReceivedIndianCurrency(CommonMasterUtility
							.getAmountInIndianCurrency(new BigDecimal(objectListTillYear[2].toString())));
					sumAcutualNonOpenReceiptAmount = sumAcutualNonOpenReceiptAmount
							.add(new BigDecimal(objectListTillYear[2].toString()));
				}
				nonOpeningList.add(nonOpeningDTO);
			}
			if(CollectionUtils.isNotEmpty(nonOperatingPreviousReceiptSideLists)) {
				nonOpeningList.addAll(setPreviousRPRReportDate(nonOperatingPreviousReceiptSideLists, nonOpeningList, rprPrevAmtMap, isSecoundary));
			}
			if (sumbalanceOfTillDate != null) {
				nonOpeningReceiptBean.setBalanceAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumbalanceOfTillDate));
			}
			if (sumbalanceRecoverable != null) {
				nonOpeningReceiptBean.setSumbalanceRecoverableIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumbalanceRecoverable));
			}
			if (sumBudgetAmount != null) {
				nonOpeningReceiptBean.setSumBudgetAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumBudgetAmount));
			}
			if (sumAcutualNonOpenReceiptAmount != null) {
				sumOfReceiptsGrandTotalAmt = sumOfReceiptsGrandTotalAmt.add(sumAcutualNonOpenReceiptAmount);
				nonOpeningReceiptBean.setSumAcutualAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumAcutualNonOpenReceiptAmount));
			}
			if (sumAcutualNonOpenPreviousReceiptAmount != null) {
				sumOfReceiptsPreviousGrandTotalAmt = sumOfReceiptsPreviousGrandTotalAmt
						.add(sumAcutualNonOpenPreviousReceiptAmount);
				// openingReceiptBean.setSubTotalAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(sumAcutualOpenPreviousReceiptAmount));
			}
			nonOpeningReceiptBean.setListOfSum(nonOpeningList);
			
			BigDecimal sumOfReceiptsGrandTotalAmount = sumOfReceiptsGrandTotalAmt.add(totalOpenCashBankAmount);
			BigDecimal sumOfReceiptsPreviousGrandTotalAmount = sumOfReceiptsPreviousGrandTotalAmt
					.add(totalOpenPreviousCashBankAmount);
			if(rprPrevAmtMap!=null&&rprPrevAmtMap.get(MainetConstants.AccountBillEntry.AMOUNT)!=null) {
				sumOfReceiptsPreviousGrandTotalAmount = sumOfReceiptsPreviousGrandTotalAmount
						.add(rprPrevAmtMap.get(MainetConstants.AccountBillEntry.AMOUNT));
			}
			dto.setActualAmountReceivedIndianCurrency(
					CommonMasterUtility.getAmountInIndianCurrency(sumOfReceiptsGrandTotalAmount));
			dto.setChequeAmountIndianCurrency(
					CommonMasterUtility.getAmountInIndianCurrency(sumOfReceiptsPreviousGrandTotalAmount));

			// opening balance payment of cash mode and bank also end
			rprPrevAmtMap=new HashMap<>();
			final List<AccountFinancialReportDTO> openingPaymentlist = new ArrayList<>();
			if (openingPaymentSideLists != null && !openingPaymentSideLists.isEmpty()) {
				for (Object[] objectListPaymentTillDate : openingPaymentSideLists) {
					final AccountFinancialReportDTO openingPayDTO = new AccountFinancialReportDTO();
					if (objectListPaymentTillDate[0] != null) {
						if(isSecoundary.equalsIgnoreCase("N"))
						openingPayDTO.setAccountCode(objectListPaymentTillDate[0].toString());
						if (openingPreviousPaymentSideLists != null) {
							for (Object[] objectListPaymentPreviousYear : openingPreviousPaymentSideLists) {
								if (objectListPaymentPreviousYear[0] != null) {
									if (objectListPaymentTillDate[0].equals(objectListPaymentPreviousYear[0])) {
										if (objectListPaymentPreviousYear[2] != null) {
											sumBudgetAmountPreviousPayment = sumBudgetAmountPreviousPayment
													.add(new BigDecimal(objectListPaymentPreviousYear[2].toString()));
											openingPayDTO.setBalanceRecoverableIndianCurrency(
													CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(
															objectListPaymentPreviousYear[2].toString())));
										}
									}
								}
							}
						}
					}
					if (objectListPaymentTillDate[1] != null) {
						openingPayDTO.setAccountHead(objectListPaymentTillDate[1].toString());
					}
					if (objectListPaymentTillDate[2] == null) {
						openingPayDTO.setActualAmountReceived(new BigDecimal(0.00));
						openingPayDTO.setActualAmountReceivedIndianCurrency(
								CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(0.00)));
						sumAcutualAmountOpenPayment = sumAcutualAmountOpenPayment.add(new BigDecimal(0.00));
					} else {
						openingPayDTO.setActualAmountReceived((BigDecimal) objectListPaymentTillDate[2]);
						openingPayDTO.setActualAmountReceivedIndianCurrency(CommonMasterUtility
								.getAmountInIndianCurrency((BigDecimal) objectListPaymentTillDate[2]));
						sumAcutualAmountOpenPayment = sumAcutualAmountOpenPayment
								.add((BigDecimal) objectListPaymentTillDate[2]);
					}
					openingPaymentlist.add(openingPayDTO);

				}
			}
			if(CollectionUtils.isNotEmpty(openingPreviousPaymentSideLists)) {
				openingPaymentlist.addAll(setPreviousRPRReportDate(openingPreviousPaymentSideLists, openingPaymentlist, rprPrevAmtMap, isSecoundary));
			}
			if (sumBudgetAmountPayment != null) {
				openingPaymentBean.setSumBudgetAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumBudgetAmountPayment));
			}
			if (sumAcutualAmountOpenPayment != null) {
				sumOfPaymentsGrandTotalAmt = sumOfPaymentsGrandTotalAmt.add(sumAcutualAmountOpenPayment);
				openingPaymentBean.setSumAcutualAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumAcutualAmountOpenPayment));
			}
			if (sumAcutualAmountPaymentBalance != null) {
				openingPaymentBean.setSubTotalAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumAcutualAmountPaymentBalance));
			}
			if (sumbalanceOfPayTilDate != null) {
				openingPaymentBean.setTotalDepositIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumbalanceOfPayTilDate));
			}
			if (sumBudgetAmountPreviousPayment != null) {
				sumOfPaymentsPreviousGrandTotalAmt = sumOfPaymentsPreviousGrandTotalAmt
						.add(sumBudgetAmountPreviousPayment);
				// openingPaymentBean.setSumAcutualAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(sumAcutualAmountOpenPayment));
			}
			openingPaymentBean.setListOfSum(openingPaymentlist);

			// non opening balance payment of cash mode and bank also end
			final List<AccountFinancialReportDTO> nonOpenPaylist = new ArrayList<>();
			if (nonOpeningPaymentSideLists != null && !nonOpeningPaymentSideLists.isEmpty()) {
				for (Object[] objectListPaymentTillDate : nonOpeningPaymentSideLists) {
					final AccountFinancialReportDTO nonOpenPayDTO = new AccountFinancialReportDTO();
					if (objectListPaymentTillDate[0] != null) {
						if(isSecoundary.equalsIgnoreCase("N"))
						nonOpenPayDTO.setAccountCode(objectListPaymentTillDate[0].toString());
						if (nonOpeningPreviousPaymentSideLists != null) {
							for (Object[] objectListNonPaymentPreviousYear : nonOpeningPreviousPaymentSideLists) {
								if (objectListNonPaymentPreviousYear[0] != null) {
									if (objectListPaymentTillDate[0].equals(objectListNonPaymentPreviousYear[0])) {
										if (objectListNonPaymentPreviousYear[2] != null) {
											sumAcutualAmountOpenPreviousPayment = sumAcutualAmountOpenPreviousPayment
													.add(new BigDecimal(
															objectListNonPaymentPreviousYear[2].toString()));
											nonOpenPayDTO.setBalanceRecoverableIndianCurrency(
													CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(
															objectListNonPaymentPreviousYear[2].toString())));
										}
									}
								}
							}
						}
					}
					if (objectListPaymentTillDate[1] != null) {
						nonOpenPayDTO.setAccountHead(objectListPaymentTillDate[1].toString());
					}
					if (objectListPaymentTillDate[2] == null) {
						nonOpenPayDTO.setActualAmountReceived(new BigDecimal(0.00));
						nonOpenPayDTO.setActualAmountReceivedIndianCurrency(
								CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(0.00)));
						sumAcutualAmountNonOpenPayment = sumAcutualAmountNonOpenPayment.add(new BigDecimal(0.00));
					} else {
						nonOpenPayDTO.setActualAmountReceived((BigDecimal) objectListPaymentTillDate[2]);
						nonOpenPayDTO.setActualAmountReceivedIndianCurrency(CommonMasterUtility
								.getAmountInIndianCurrency((BigDecimal) objectListPaymentTillDate[2]));
						sumAcutualAmountNonOpenPayment = sumAcutualAmountNonOpenPayment
								.add((BigDecimal) objectListPaymentTillDate[2]);
					}
					nonOpenPaylist.add(nonOpenPayDTO);
				}
			}if(CollectionUtils.isNotEmpty(nonOpeningPreviousPaymentSideLists)) {
				nonOpenPaylist.addAll(setPreviousRPRReportDate(nonOpeningPreviousPaymentSideLists, nonOpenPaylist, rprPrevAmtMap, isSecoundary));
			}
			if (sumBudgetAmountPayment != null) {
				nonOpeningPaymentBean.setSumBudgetAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumBudgetAmountPayment));
			}
			if (sumAcutualAmountNonOpenPayment != null) {
				sumOfPaymentsGrandTotalAmt = sumOfPaymentsGrandTotalAmt.add(sumAcutualAmountNonOpenPayment);
				nonOpeningPaymentBean.setSumAcutualAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumAcutualAmountNonOpenPayment));
			}
			if (sumAcutualAmountPaymentBalance != null) {
				nonOpeningPaymentBean.setSubTotalAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumAcutualAmountPaymentBalance));
			}
			if (sumbalanceOfPayTilDate != null) {
				nonOpeningPaymentBean.setTotalDepositIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumbalanceOfPayTilDate));
			}
			if (sumAcutualAmountOpenPreviousPayment != null) {
				sumOfPaymentsPreviousGrandTotalAmt = sumOfPaymentsPreviousGrandTotalAmt
						.add(sumAcutualAmountOpenPreviousPayment);
				// nonOpeningPaymentBean.setSumAcutualAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(sumAcutualAmountNonOpenPayment));
			}
			nonOpeningPaymentBean.setListOfSum(nonOpenPaylist);

			BigDecimal sumOfPaymentsGrandTotalAmount = sumOfPaymentsGrandTotalAmt.add(totalClosingCashAmt);
			BigDecimal sumOfPaymentsPreviousGrandTotalAmount = sumOfPaymentsPreviousGrandTotalAmt
					.add(totalClosingPreviousCashAmt);
			if(rprPrevAmtMap!=null&&rprPrevAmtMap.get(MainetConstants.AccountBillEntry.AMOUNT)!=null) {
				sumOfPaymentsPreviousGrandTotalAmount = sumOfPaymentsPreviousGrandTotalAmount
						.add(rprPrevAmtMap.get(MainetConstants.AccountBillEntry.AMOUNT));
			}
			dto.setSumbalanceRecoverableIndianCurrency(
					CommonMasterUtility.getAmountInIndianCurrency(sumOfPaymentsGrandTotalAmount));
			dto.setChequeDepositIndianCurrency(
					CommonMasterUtility.getAmountInIndianCurrency(sumOfPaymentsPreviousGrandTotalAmount));

			model.addAttribute(MainetConstants.AccountFinancialReport.OPENING_RECEIPT_SIDE_LIST, openingReceiptBean);
			model.addAttribute(AccountFinancialReport.NON_OPENING_RECEIPT_SIDE_LIST, nonOpeningReceiptBean);
			model.addAttribute(MainetConstants.AccountFinancialReport.OPENING_PAYMENT_SIDE_LIST, openingPaymentBean);
			model.addAttribute(AccountFinancialReport.NON_OPENING_PAYMENT_SIDE_LIST, nonOpeningPaymentBean);
			model.addAttribute(REPORT_DATA, dto);
		}

		else

		{
			model.addAttribute(VALIDATION_ERROR, AccountConstants.Y.getValue());
			LOGGER.error("No Records found for Classified Budget Report for [frmDate=" + frmDate + ",tDate=" + tDate
					+ "orgId=" + orgId);
		}

	}

	private BigDecimal calculateClosingBalanceAsOnPreviousDateAndAccHeads(List<Long> headList, long orgId,
		final 	String toDate) {
		
		Date reptToDate = Utility.stringToDate(toDate);
		
		int toDay = getDayFromDate(reptToDate);
		int toMonth = getMonthFromDate(reptToDate);
		int toYear = getYearFromDate(reptToDate);
		String toDates = toDay + "/" + toMonth + "/" + (toYear - 1);

		List<Object[]> result = null;
		Long financialYearId = tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(Utility.stringToDate(toDates));
		if (financialYearId != null)
			result = accountFinancialReportRepository.queryOpenBalanceAmountAndCrDrTypeAndAccHeads(headList, orgId,
					financialYearId);
		List<Object[]> drCrAmtList = null;
		Date financialYFromDate = tbFinancialyearJpaRepository
				.getFromDateFromFinancialYearIdByPassingDate(Utility.stringToDate(toDates));
		drCrAmtList = accountFinancialReportRepository.getSumOfCreditAndSumOfDebitFromVouchersAndAccHeads(financialYFromDate,
				Utility.stringToDate(toDates), headList, orgId);

		return closingBalanceForMultAccHeads(result, drCrAmtList, orgId);
	}

	private BigDecimal calculateOpeningBalanceAsOnPreviousDateAndAccHeads(List<Long> headList, long orgId,
			String fromDate) {

		Date reptFromDate = Utility.stringToDate(fromDate);
		int fromDay = getDayFromDate(reptFromDate);
		int fromMonth = getMonthFromDate(reptFromDate);
		int fromYear = getYearFromDate(reptFromDate);
		String fromDates = fromDay + "/" + fromMonth + "/" + (fromYear - 1);

		List<Object[]> result = null;
		Long financialYearId = tbFinancialyearJpaRepository
				.getFinanciaYearIdByFromDate(Utility.stringToDate(fromDates));
		if (financialYearId != null)
			result = accountFinancialReportRepository.queryOpenBalanceAmountAndCrDrTypeAndAccHeads(headList, orgId,
					financialYearId);
		Date reportFromDate = Utility.stringToDate(fromDates);// intialize your date to any date
		int year1 = getYearFromDate(reportFromDate);
		String isDateFinancail = "01/04/" + (year1);
		Date finYearStartDate = Utility.stringToDate(isDateFinancail);// intialize your date to any date
		List<Object[]> drCrAmtList = null;
		if (!reportFromDate.equals(finYearStartDate)) {
			Date dateBefore = new Date(reportFromDate.getTime() - 1 * 24 * 3600 * 1000);
			Date financialYFromDate = tbFinancialyearJpaRepository
					.getFromDateFromFinancialYearIdByPassingDate(dateBefore);
			drCrAmtList = accountFinancialReportRepository.getSumOfCreditAndSumOfDebitFromVouchersAndAccHeads(financialYFromDate,
					dateBefore, headList, orgId);
		}
		return openingBalanceForMultAccHeads(result, drCrAmtList, orgId);
	}

	private BigDecimal calculateClosingBalanceAsOnDateAndAccHeads(List<Long> headList, long orgId, String toDate) {
		List<Object[]> result = null;
		Long financialYearId = tbFinancialyearJpaRepository
				.getFinanciaYearIdByFromDate(Utility.stringToDate(toDate));
		if (financialYearId != null)
			result = accountFinancialReportRepository.queryOpenBalanceAmountAndCrDrTypeAndAccHeads(headList, orgId,
					financialYearId);
		List<Object[]> drCrAmtList = null;
		Date financialYFromDate = tbFinancialyearJpaRepository
				.getFromDateFromFinancialYearIdByPassingDate(Utility.stringToDate(toDate));
		drCrAmtList = accountFinancialReportRepository.getSumOfCreditAndSumOfDebitFromVouchersAndAccHeads(financialYFromDate,
				Utility.stringToDate(toDate), headList, orgId);

		return closingBalanceForMultAccHeads(result, drCrAmtList, orgId);
	}

	private BigDecimal calculateOpeningBalanceAsOnDateAndAccHeads(List<Long> headList, long orgId, String fromDate) {
		List<Object[]> result = null;
		Long financialYearId = tbFinancialyearJpaRepository
				.getFinanciaYearIdByFromDate(Utility.stringToDate(fromDate));
		if (financialYearId != null)
			result = accountFinancialReportRepository.queryOpenBalanceAmountAndCrDrTypeAndAccHeads(headList, orgId,
					financialYearId);
		Date reportFromDate = Utility.stringToDate(fromDate);// intialize your date to any date
		int year1 = getYearFromDate(reportFromDate);
		String isDateFinancail = "01/04/" + (year1);
		Date finYearStartDate = Utility.stringToDate(isDateFinancail);// intialize your date to any date
		List<Object[]> drCrAmtList = null;
		if (!reportFromDate.equals(finYearStartDate)) {
			Date dateBefore = new Date(reportFromDate.getTime() - 1 * 24 * 3600 * 1000);
			Date financialYFromDate = tbFinancialyearJpaRepository
					.getFromDateFromFinancialYearIdByPassingDate(dateBefore);
			drCrAmtList = accountFinancialReportRepository.getSumOfCreditAndSumOfDebitFromVouchersAndAccHeads(financialYFromDate,
					dateBefore, headList, orgId);
		}
		return openingBalanceForMultAccHeads(result, drCrAmtList, orgId);
	}

	private BigDecimal openingBalanceForMultAccHeads(final List<Object[]> result, final List<Object[]> drCrAmtList,
			Long orgId) {
		BigDecimal crAmt = new BigDecimal(0.00);
		BigDecimal drAmt = new BigDecimal(0.00);
		BigDecimal vouAmt = new BigDecimal(0.00);
		BigDecimal finalAmt = new BigDecimal(0.00);
		if (CollectionUtils.isNotEmpty(result)) {
			for (Object[] arr : result) {
				if (arr[1] != null && arr[1].toString().equalsIgnoreCase("cr")) {
					BigDecimal crAmt1 = new BigDecimal(arr[0].toString());
					crAmt = crAmt.add(crAmt1);

				} else {
					BigDecimal drAmt1 = new BigDecimal(arr[0].toString());
					drAmt = drAmt.add(drAmt1);
				}
			}
		}
		if (CollectionUtils.isNotEmpty(drCrAmtList)
				&& (drCrAmtList.get(0)[0] != null && drCrAmtList.get(0)[1] != null)) {
			vouAmt = new BigDecimal(drCrAmtList.get(0)[0].toString())
					.subtract(new BigDecimal(drCrAmtList.get(0)[1].toString()));
		}
		finalAmt = (drAmt.subtract(crAmt)).subtract(vouAmt);
		return finalAmt;
	}
	private BigDecimal closingBalanceForMultAccHeads(final List<Object[]> result, final List<Object[]> drCrAmtList, Long orgId) {
		BigDecimal crAmt= new BigDecimal(0.00);
		BigDecimal drAmt= new BigDecimal(0.00);
		BigDecimal vouAmt= new BigDecimal(0.00);
		BigDecimal finalAmt= new BigDecimal(0.00);
		if(CollectionUtils.isNotEmpty(result)) {
		for(Object[] arr:result)	{
			if(arr[1]!=null && arr[1].toString().equalsIgnoreCase("cr")) {
				BigDecimal crAmt1= new BigDecimal(arr[0].toString());
				crAmt=crAmt.add(crAmt1);
				
			}else {
				BigDecimal drAmt1= new BigDecimal(arr[0].toString());
				drAmt=drAmt.add(drAmt1);
			}
		}
		}
		if(CollectionUtils.isNotEmpty(drCrAmtList)&&(drCrAmtList.get(0)[0]!=null &&drCrAmtList.get(0)[1]!=null)) {
			vouAmt=new BigDecimal(drCrAmtList.get(0)[0].toString()).subtract(new BigDecimal(drCrAmtList.get(0)[1].toString()));
		}
		int drCheck=drAmt.compareTo(BigDecimal.ZERO);
		int crCheck=crAmt.compareTo(BigDecimal.ZERO);
		if(drCheck!=0||crCheck!=0) {
				finalAmt = (drAmt.subtract(crAmt)).add(vouAmt);
		}
		else
			finalAmt=vouAmt;
		return finalAmt;
	}

	private List<Object[]> queryClassifiedBudgetnonOpeningPreviousPaymentSideReportDataL3(Date frmDate, Date tDate,
			long orgId, Long accountHeadId) {
		// TODO Auto-generated method stub
		int fromDay = getDayFromDate(frmDate);
		int fromMonth = getMonthFromDate(frmDate);
		int fromYear = getYearFromDate(frmDate);
		String fromDates = fromDay + "/" + fromMonth + "/" + (fromYear - 1);
		Date fromDate = Utility.stringToDate(fromDates);
		int toDay = getDayFromDate(tDate);
		int toMonth = getMonthFromDate(tDate);
		int toYear = getYearFromDate(tDate);
		String toDates = toDay + "/" + toMonth + "/" + (toYear - 1);
		Date toDate = Utility.stringToDate(toDates);
		final Long coaReceiptLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.NEC.EMPLOYEE, PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetnonOpeningPreviousPaymentSideReportDataL3(fromDate,
				toDate, orgId, coaReceiptLookupId);
	}

	private List<Object[]> queryClassifiedBudgetopeningPreviousPaymentReportDataL3(Date frmDate, Date tDate, long orgId,
			Long accountHeadId) {
		// TODO Auto-generated method stub
		int fromDay = getDayFromDate(frmDate);
		int fromMonth = getMonthFromDate(frmDate);
		int fromYear = getYearFromDate(frmDate);
		String fromDates = fromDay + "/" + fromMonth + "/" + (fromYear - 1);
		Date fromDate = Utility.stringToDate(fromDates);

		int toDay = getDayFromDate(tDate);
		int toMonth = getMonthFromDate(tDate);
		int toYear = getYearFromDate(tDate);
		String toDates = toDay + "/" + toMonth + "/" + (toYear - 1);
		Date toDate = Utility.stringToDate(toDates);
		final Long coaReceiptLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.NEC.EMPLOYEE, PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetopeningPreviousPaymentReportDataL3(fromDate,
				toDate, orgId, coaReceiptLookupId);
	}

	private List<Object[]> queryClassifiedBudgetnonOperatingPreviousReceiptSideReportDataL3(Date frmDate, Date tDate,
			long orgId, Long accountHeadId) {
		// TODO Auto-generated method stub
		int fromDay = getDayFromDate(frmDate);
		int fromMonth = getMonthFromDate(frmDate);
		int fromYear = getYearFromDate(frmDate);
		String fromDates = fromDay + "/" + fromMonth + "/" + (fromYear - 1);
		Date fromDate = Utility.stringToDate(fromDates);

		int toDay = getDayFromDate(tDate);
		int toMonth = getMonthFromDate(tDate);
		int toYear = getYearFromDate(tDate);
		String toDates = toDay + "/" + toMonth + "/" + (toYear - 1);
		Date toDate = Utility.stringToDate(toDates);
		final Long coaReceiptLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.LookUp.INDIVIDUAL, PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetnonOperatingPreviousReceiptSideReportDataL3(
				fromDate, toDate, orgId, coaReceiptLookupId);

	}

	private List<Object[]> queryClassifiedBudgetPreviousReceiptSideReportDataL3(Date frmDate, Date tDate, long orgId,
			Long accountHeadId) {
		// TODO Auto-generated method stub
		int fromDay = getDayFromDate(frmDate);
		int fromMonth = getMonthFromDate(frmDate);
		int fromYear = getYearFromDate(frmDate);
		String fromDates = fromDay + "/" + fromMonth + "/" + (fromYear - 1);
		Date fromDate = Utility.stringToDate(fromDates);

		int toDay = getDayFromDate(tDate);
		int toMonth = getMonthFromDate(tDate);
		int toYear = getYearFromDate(tDate);
		String toDates = toDay + "/" + toMonth + "/" + (toYear - 1);
		Date toDate = Utility.stringToDate(toDates);
		final Long coaReceiptLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.LookUp.INDIVIDUAL, PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetPreviousReceiptSideReportDataL3(fromDate, toDate,
				orgId, coaReceiptLookupId);

	}

	private List<Object[]> queryClassifiedBudgetPaymentSideNonOpeningReportData(Date frmDate, Date tDate, long orgId,
			Long accountHeadId) {
		// TODO Auto-generated method stub
		final Long coaReceiptLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.NEC.EMPLOYEE, PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetPaymentSideNonOpeningReport(frmDate, tDate, orgId,
				coaReceiptLookupId);
	}

	private List<Object[]> queryClassifiedBudgetPaymentSideOpeningReportData(Date frmDate, Date tDate, long orgId,
			Long accountHeadId) {
		// TODO Auto-generated method stub
		final Long coaReceiptLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.NEC.EMPLOYEE, PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetPaymentSideOpeningReport(frmDate, tDate, orgId,
				coaReceiptLookupId);

	}

	private List<Object[]> queryClassifiedBudgetReceiptSideNonOpeningData(Date frmDate, Date tDate, long orgId,
			Long accountHeadId) {
		// TODO Auto-generated method stub
		final Long coaReceiptLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.LookUp.INDIVIDUAL, PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetReceiptSideNonOpeningReport(frmDate, tDate, orgId,
				coaReceiptLookupId);

	}

	private List<Object[]> queryClassifiedBudgetReceiptSideOpeningData(Date frmDate, Date tDate, long orgId,
			Long accountHeadId) {
		// TODO Auto-generated method stub
		final Long coaReceiptLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.LookUp.INDIVIDUAL, PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetReceiptSideOpeningReport(frmDate, tDate, orgId,
				coaReceiptLookupId);

	}

	private List<Object[]> queryClassifiedBudgetPaymentSideNonOpeningReportDataL1(Date frmDate, Date tDate, long orgId,
			Long accountHeadId) {
		// TODO Auto-generated method stub
		final Long coaPaymentLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.NEC.EMPLOYEE,
				PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetPaymentSideNonOpeningReportDataL1(frmDate, tDate,
				orgId, coaPaymentLookupId);

	}

	private List<Object[]> queryClassifiedBudgetPaymentSideOpeningReportDataL1(Date frmDate, Date tDate, long orgId,
			Long accountHeadId) {
		// TODO Auto-generated method stub
		final Long coaPaymentLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.NEC.EMPLOYEE,
				PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetPaymentSideOpeningReportDataL1(frmDate, tDate,
				orgId, coaPaymentLookupId);

	}

	private List<Object[]> queryClassifiedBudgetReceiptSideNonOpeningReportDataL1(Date frmDate, Date tDate, long orgId,
			Long accountHeadId) {
		// TODO Auto-generated method stub
		final Long coaReceiptLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.LookUp.INDIVIDUAL, PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetReceiptSideNonOpeningReportDataL1(frmDate, tDate,
				orgId, coaReceiptLookupId);

	}

	private List<Object[]> queryClassifiedBudgetReceiptSideOpeningReportDataL1(Date frmDate, Date tDate, long orgId,
			Long accountHeadId) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		final Long coaReceiptLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.LookUp.INDIVIDUAL, PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetReceiptSideOpeningReportDataL1(frmDate, tDate,
				orgId, coaReceiptLookupId);

	}

	private List<Object[]> queryClassifiedBudgetPaymentSideNonOpeningPreviousReportDataL2(Date frmDate, Date tDate,
			long orgId, Long accountHeadId) {
		// TODO Auto-generated method stub

		int fromDay = getDayFromDate(frmDate);
		int fromMonth = getMonthFromDate(frmDate);
		int fromYear = getYearFromDate(frmDate);
		String fromDates = fromDay + "/" + fromMonth + "/" + (fromYear - 1);
		Date fromDate = Utility.stringToDate(fromDates);

		int toDay = getDayFromDate(tDate);
		int toMonth = getMonthFromDate(tDate);
		int toYear = getYearFromDate(tDate);
		String toDates = toDay + "/" + toMonth + "/" + (toYear - 1);
		Date toDate = Utility.stringToDate(toDates);

		final Long coaPaymentLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.NEC.EMPLOYEE,
				PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetPaymentSideNonOpeningReportDataL2(fromDate, toDate,
				orgId, coaPaymentLookupId);

	}

	private List<Object[]> queryClassifiedBudgetPaymentSideOpeningPreviousReportDataL2(Date frmDate, Date tDate,
			long orgId, Long accountHeadId) {
		// TODO Auto-generated method stub
		int fromDay = getDayFromDate(frmDate);
		int fromMonth = getMonthFromDate(frmDate);
		int fromYear = getYearFromDate(frmDate);
		String fromDates = fromDay + "/" + fromMonth + "/" + (fromYear - 1);
		Date fromDate = Utility.stringToDate(fromDates);
		int toDay = getDayFromDate(tDate);
		int toMonth = getMonthFromDate(tDate);
		int toYear = getYearFromDate(tDate);
		String toDates = toDay + "/" + toMonth + "/" + (toYear - 1);
		Date toDate = Utility.stringToDate(toDates);
		final Long coaPaymentLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.NEC.EMPLOYEE,
				PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetPaymentSideOpeningReportDataL2(fromDate, toDate,
				orgId, coaPaymentLookupId);
	}

	private List<Object[]> queryClassifiedBudgetReceiptSideNonOpeningPreviousReportDataL2(Date frmDate, Date tDate,
			long orgId, Long accountHeadId) {

		int fromDay = getDayFromDate(frmDate);
		int fromMonth = getMonthFromDate(frmDate);
		int fromYear = getYearFromDate(frmDate);
		String fromDates = fromDay + "/" + fromMonth + "/" + (fromYear - 1);
		Date fromDate = Utility.stringToDate(fromDates);

		int toDay = getDayFromDate(tDate);
		int toMonth = getMonthFromDate(tDate);
		int toYear = getYearFromDate(tDate);
		String toDates = toDay + "/" + toMonth + "/" + (toYear - 1);
		Date toDate = Utility.stringToDate(toDates);
		final Long coaReceiptLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.LookUp.INDIVIDUAL, PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetReceiptSideNonOpeningReportDataL2(fromDate, toDate,
				orgId, coaReceiptLookupId);

	}

	private List<Object[]> queryClassifiedBudgetReceiptSideOpeningPreviousReportDataL2(Date frmDate, Date tDate,
			long orgId, Long accountHeadId) {
		// TODO Auto-generated method stub
		int fromDay = getDayFromDate(frmDate);
		int fromMonth = getMonthFromDate(frmDate);
		int fromYear = getYearFromDate(frmDate);
		String fromDates = fromDay + "/" + fromMonth + "/" + (fromYear - 1);
		Date fromDate = Utility.stringToDate(fromDates);

		int toDay = getDayFromDate(tDate);
		int toMonth = getMonthFromDate(tDate);
		int toYear = getYearFromDate(tDate);
		String toDates = toDay + "/" + toMonth + "/" + (toYear - 1);
		Date toDate = Utility.stringToDate(toDates);

		final Long coaReceiptLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.LookUp.INDIVIDUAL, PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetReceiptSideOpeningReportDataL2(fromDate, toDate,
				orgId, coaReceiptLookupId);
	}

	private List<Object[]> queryClassifiedBudgetPaymentSideNonOpeningPreviousReportDataL3(Date frmDate, Date tDate,
			long orgId, Long accountHeadId) {

		int fromDay = getDayFromDate(frmDate);
		int fromMonth = getMonthFromDate(frmDate);
		int fromYear = getYearFromDate(frmDate);
		String fromDates = fromDay + "/" + fromMonth + "/" + (fromYear - 1);
		Date fromDate = Utility.stringToDate(fromDates);

		int toDay = getDayFromDate(tDate);
		int toMonth = getMonthFromDate(tDate);
		int toYear = getYearFromDate(tDate);
		String toDates = toDay + "/" + toMonth + "/" + (toYear - 1);
		Date toDate = Utility.stringToDate(toDates);

		final Long coaPaymentLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.NEC.EMPLOYEE,
				PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetPaymentSideNonOpeningReportDataL3(fromDate, toDate,
				orgId, coaPaymentLookupId);
	}

	private List<Object[]> queryClassifiedBudgetPaymentSideOpeningPreviousReportDataL3(Date frmDate, Date tDate,
			long orgId, Long accountHeadId) {

		int fromDay = getDayFromDate(frmDate);
		int fromMonth = getMonthFromDate(frmDate);
		int fromYear = getYearFromDate(frmDate);
		String fromDates = fromDay + "/" + fromMonth + "/" + (fromYear - 1);
		Date fromDate = Utility.stringToDate(fromDates);

		int toDay = getDayFromDate(tDate);
		int toMonth = getMonthFromDate(tDate);
		int toYear = getYearFromDate(tDate);
		String toDates = toDay + "/" + toMonth + "/" + (toYear - 1);
		Date toDate = Utility.stringToDate(toDates);

		final Long coaPaymentLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.NEC.EMPLOYEE,
				PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetPaymentSideOpeningReportDataL3(fromDate, toDate,
				orgId, coaPaymentLookupId);
	}

	private List<Object[]> queryClassifiedBudgetReceiptSideNonOpeningPreviousReportDataL3(Date frmDate, Date tDate,
			long orgId, Long accountHeadId) {

		int fromDay = getDayFromDate(frmDate);
		int fromMonth = getMonthFromDate(frmDate);
		int fromYear = getYearFromDate(frmDate);
		String fromDates = fromDay + "/" + fromMonth + "/" + (fromYear - 1);
		Date fromDate = Utility.stringToDate(fromDates);

		int toDay = getDayFromDate(tDate);
		int toMonth = getMonthFromDate(tDate);
		int toYear = getYearFromDate(tDate);
		String toDates = toDay + "/" + toMonth + "/" + (toYear - 1);
		Date toDate = Utility.stringToDate(toDates);

		final Long coaReceiptLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.LookUp.INDIVIDUAL, PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetReceiptSideNonOpeningReportDataL3(fromDate, toDate,
				orgId, coaReceiptLookupId);
	}

	private List<Object[]> queryClassifiedBudgetReceiptSideOpeningPreviousReportDataL3(Date frmDate, Date tDate,
			long orgId, Long accountHeadId) {
		int fromDay = getDayFromDate(frmDate);
		int fromMonth = getMonthFromDate(frmDate);
		int fromYear = getYearFromDate(frmDate);
		String fromDates = fromDay + "/" + fromMonth + "/" + (fromYear - 1);
		Date fromDate = Utility.stringToDate(fromDates);
		int toDay = getDayFromDate(tDate);
		int toMonth = getMonthFromDate(tDate);
		int toYear = getYearFromDate(tDate);
		String toDates = toDay + "/" + toMonth + "/" + (toYear - 1);
		Date toDate = Utility.stringToDate(toDates);
		final Long coaReceiptLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.LookUp.INDIVIDUAL, PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetReceiptSideOpeningReportDataL3(fromDate, toDate,
				orgId, coaReceiptLookupId);
	}

	private BigDecimal calculateClosingBalanceAsOnDate(final Long sacHeadId, final Long orgId,
			final String voPostingDate) {
		List<Object[]> result = null;
		Long financialYearId = tbFinancialyearJpaRepository
				.getFinanciaYearIdByFromDate(Utility.stringToDate(voPostingDate));
		if (financialYearId != null)
			result = accountFinancialReportRepository.queryOpenBalanceAmountAndCrDrType(sacHeadId, orgId,
					financialYearId);
		List<Object[]> drCrAmtList = null;
		Date financialYFromDate = tbFinancialyearJpaRepository
				.getFromDateFromFinancialYearIdByPassingDate(Utility.stringToDate(voPostingDate));
		drCrAmtList = accountFinancialReportRepository.getSumOfCreditAndSumOfDebitFromVouchers(financialYFromDate,
				Utility.stringToDate(voPostingDate), sacHeadId, orgId);

		return openingBalance(result, drCrAmtList, orgId);
	}

	private BigDecimal calculateClosingBalanceAsOnPreviousDate(final Long sacHeadId, final Long orgId,
			final String reportToDate) {

		Date reptToDate = Utility.stringToDate(reportToDate);
		int toDay = getDayFromDate(reptToDate);
		int toMonth = getMonthFromDate(reptToDate);
		int toYear = getYearFromDate(reptToDate);
		String toDates = toDay + "/" + toMonth + "/" + (toYear - 1);

		List<Object[]> result = null;
		Long financialYearId = tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(Utility.stringToDate(toDates));
		if (financialYearId != null)
			result = accountFinancialReportRepository.queryOpenBalanceAmountAndCrDrType(sacHeadId, orgId,
					financialYearId);
		List<Object[]> drCrAmtList = null;
		Date financialYFromDate = tbFinancialyearJpaRepository
				.getFromDateFromFinancialYearIdByPassingDate(Utility.stringToDate(toDates));
		drCrAmtList = accountFinancialReportRepository.getSumOfCreditAndSumOfDebitFromVouchers(financialYFromDate,
				Utility.stringToDate(toDates), sacHeadId, orgId);

		return openingBalance(result, drCrAmtList, orgId);
	}

	public void processCollectionSummaryReport(long orgId, String fromDate, String toDate, String reportTypeCode,
			ModelMap model) {
		List<Object[]> collectionSummaryReport = findCollectionSummaryReportByTodateAndFromDateAndOrgId(toDate,
				fromDate, orgId);
		AccountCollectionSummaryDTO finalaccountCollectionSummaryDTO = new AccountCollectionSummaryDTO();
		List<AccountCollectionSummaryDTO> summaryRecordList = new ArrayList<>();
		BigDecimal cashAmountTotal = new BigDecimal(0.00);
		BigDecimal chequeDDTotal = new BigDecimal(0.00);
		BigDecimal bankAmountTotal = new BigDecimal(0.00);
		BigDecimal grandTotal = new BigDecimal(0.00);
		if (collectionSummaryReport == null || collectionSummaryReport.isEmpty()) {
			finalaccountCollectionSummaryDTO.setFromDate(fromDate);
			finalaccountCollectionSummaryDTO.setToDate(toDate);
			LOGGER.error("No Records found for report from  input[fromDate=" + fromDate + " todate=" + toDate
					+ " orgid=" + orgId);
		} else {
			for (final Object obj[] : collectionSummaryReport) {
				final AccountCollectionSummaryDTO accountCollectionSummaryDTO = new AccountCollectionSummaryDTO();
				if (obj[0] != null) {
					accountCollectionSummaryDTO.setNameOfDepartment((String) obj[0]);
				}
				if (obj[2] != null) {
					accountCollectionSummaryDTO.setNameOfTheRevenueHead((String) obj[2]);
				}
				if (obj[3] != null) {
					accountCollectionSummaryDTO.setCashAmountIndianCurrency(
							CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) obj[3]));
					cashAmountTotal = cashAmountTotal.add((BigDecimal) obj[3]);
				}
				if (obj[4] != null) {
					accountCollectionSummaryDTO.setChequeDDAmountIndianCurrency(
							CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) obj[4]));
					chequeDDTotal = chequeDDTotal.add((BigDecimal) obj[4]);
				}
				if (obj[5] != null) {
					accountCollectionSummaryDTO.setBankAmountIndianCurrency(
							CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) obj[5]));
					bankAmountTotal = bankAmountTotal.add((BigDecimal) obj[5]);

				}
				summaryRecordList.add(accountCollectionSummaryDTO);
			}

			finalaccountCollectionSummaryDTO.setCollectionSummaryRecordList(summaryRecordList);

			if (!cashAmountTotal.equals(new BigDecimal(0.00))) {
				finalaccountCollectionSummaryDTO.setCashAmountTotalIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(cashAmountTotal));
			}
			if (!chequeDDTotal.equals(new BigDecimal(0.00))) {
				finalaccountCollectionSummaryDTO
						.setChequeDDTotalIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(chequeDDTotal));
			}
			if (!bankAmountTotal.equals(new BigDecimal(0.00))) {
				finalaccountCollectionSummaryDTO
						.setBankTotalIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(bankAmountTotal));
			}
			grandTotal = bankAmountTotal.add(chequeDDTotal).add(cashAmountTotal);
			finalaccountCollectionSummaryDTO.setGrandTotal(CommonMasterUtility.getAmountInIndianCurrency(grandTotal));
			final String totalAmountInWords = Utility.convertBigNumberToWord(grandTotal);
			finalaccountCollectionSummaryDTO.setTotalAmountInWords(totalAmountInWords);
			finalaccountCollectionSummaryDTO.setFromDate(fromDate);
			finalaccountCollectionSummaryDTO.setToDate(toDate);

			model.addAttribute(REPORT_DATA, finalaccountCollectionSummaryDTO);
		}

	}

	public List<Object[]> findCollectionSummaryReportByTodateAndFromDateAndOrgId(String toDate, String fromDate,
			long orgId) {

		Date toDates = null;
		Date fromDates = null;
		if (StringUtils.isNotEmpty(toDate) && StringUtils.isNotEmpty(fromDate)) {
			toDates = Utility.stringToDate(toDate);
			fromDates = Utility.stringToDate(fromDate);
		}

		return accountCollectionSummaryReportJpaRepository
				.findCollectionSummaryReportByTodateAndFromDateAndOrgId(toDates, fromDates, orgId);

	}

	private void processCollectionDetailReport(long orgId, String fromDate, String toDate, String reportTypeCode,
			Long superOrgId, ModelMap model, Long registerDepTypeId,Long fieldId) {
		BigDecimal totalBankAmount = new BigDecimal(0.00);
		BigDecimal totalCashAmount = new BigDecimal(0.00);
		List<Object[]> collectionDetailList = findCollectionDetailReportbyByTodateAndFromDateAndOrgIdAndCreatedById(
				toDate, fromDate, orgId, superOrgId,registerDepTypeId,fieldId);
		  List<Department> deptList = deptService.getDepartments(MainetConstants.STATUS.ACTIVE);
		//#120771
		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.AccountConstants.DISHONORED.getValue(),
				PrefixConstants.LookUpPrefix.CLR, new Organisation(orgId));
		AccountCollectionSummaryDTO finalaccountCollectionDetailDTO = new AccountCollectionSummaryDTO();
		List<AccountCollectionSummaryDTO> listOfCollectionDetail = new ArrayList<>();
		if (collectionDetailList == null || collectionDetailList.isEmpty()) {
			LOGGER.error("No Records found for report from  input[fromDate=" + fromDate + " todate=" + toDate
					+ " orgid=" + orgId);
		} else {
			for (final Object obj[] : collectionDetailList) {
				final AccountCollectionSummaryDTO accountCollectionDetailDTO = new AccountCollectionSummaryDTO();

				if (obj[0] != null) {
					String collectionMode = CommonMasterUtility.findLookUpDesc(AccountPrefix.PAY.name(), orgId,
							Long.valueOf(obj[3].toString()));
					accountCollectionDetailDTO.setReceiptMode(collectionMode);
					List<Object[]> bankDetails = findBankDetailByReceiptHeadIdAndOrgId(Long.valueOf(obj[0].toString()),
							orgId);
					if (bankDetails != null && !bankDetails.isEmpty()) {
						for (Object[] bank : bankDetails) {
							if (bank[1] != null) {
								accountCollectionDetailDTO.setBankAccountNumber((String) bank[1]);
							}
							if (bank[2] != null) {
								accountCollectionDetailDTO.setDateOfDeposit(Utility.dateToString((Date) bank[2]));
							}
							if (bank[3] != null) {
								accountCollectionDetailDTO.setDateOfRealisation(Utility.dateToString((Date) bank[3]));
							}

							String flag = "";
							Long chequeStatus=0L;
							if (bank[4] != null)
								chequeStatus = (Long) bank[4];

							if (chequeStatus.equals(lookUp.getLookUpId())) //MainetConstants.Common_Constant.NUMBER.FOUR  #120771
								flag = "Y";
							else
								flag = "N";
							accountCollectionDetailDTO.setWhetherReturned(flag);

						}
					}

				}
				if (obj[1] != null) {
					accountCollectionDetailDTO.setReceiptNumber(Long.valueOf(obj[1].toString()));
					if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
						List<Department> deptFilterList = deptList.stream().filter(f->f.getDpDeptid()==Long.valueOf(obj[8].toString())).collect(Collectors.toList());
						accountCollectionDetailDTO.setRmReceiptNo(deptFilterList.get(0).getDpDeptcode().concat(obj[1].toString()));
					}
					
				}
				if (obj[2] != null) {
					accountCollectionDetailDTO.setReceiptDate(Utility.dateToString((Date) obj[2]));

				}
				if (obj[6] != null) {
					accountCollectionDetailDTO.setCashAmountIndianCurrency(
							CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) obj[6]));
					totalCashAmount = totalCashAmount.add((BigDecimal) obj[6]);
				}

				if (obj[5] != null) {
					accountCollectionDetailDTO.setChequeNumber(Long.valueOf(obj[5].toString()));
				}
				if (obj[4] != null) {
					accountCollectionDetailDTO.setNameOfDepositer((String) obj[4]);
				}
				if (obj[7] != null) {
					accountCollectionDetailDTO.setRemarks((String) obj[7]);
				}

				listOfCollectionDetail.add(accountCollectionDetailDTO);

			}
			finalaccountCollectionDetailDTO.setFromDate(fromDate);
			finalaccountCollectionDetailDTO.setToDate(toDate);
			finalaccountCollectionDetailDTO
					.setBankTotalIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(totalBankAmount));
			finalaccountCollectionDetailDTO
					.setCashAmountTotalIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(totalCashAmount));
			finalaccountCollectionDetailDTO.setListOfCollectionDetail(listOfCollectionDetail);
			if(fieldId != null && fieldId != -1L) {
				model.addAttribute("fieldName", tbAcFieldMasterService.getFieldDesc(fieldId));
			}
			if(registerDepTypeId  != null && registerDepTypeId != -1L) {
				model.addAttribute("deptName", deptService.fetchDepartmentDescById(registerDepTypeId));
			}
			model.addAttribute(REPORT_DATA, finalaccountCollectionDetailDTO);
		}
	}

	public List<Object[]> findCollectionDetailReportbyByTodateAndFromDateAndOrgIdAndCreatedById(String toDate,
			String fromDate, Long orgId, Long superOrgId, Long registerDepTypeId, Long fieldId) {

		Date toDates = null;
		Date fromDates = null;
		if (StringUtils.isNotEmpty(toDate) && StringUtils.isNotEmpty(fromDate)) {
			toDates = Utility.stringToDate(toDate);
			fromDates = Utility.stringToDate(fromDate);
		}

		return accountFinanceReportDAO
				.findCollectionDetailReportbyByTodateAndFromDateAndOrgIdAndCreatedById(toDates, fromDates, orgId,registerDepTypeId,fieldId);

	}

	public List<Object[]> findBankDetailByReceiptHeadIdAndOrgId(Long receiptId, Long OrgId) {

		if (receiptId != null && OrgId != null) {
			return accountCollectionSummaryReportJpaRepository.findBankDetailByReceiptHeadIdAndOrgId(receiptId, OrgId);
		}
		return null;

	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> queryClassifiedBudgetReceiptSideReportDataL1(Date fromDate, Date toDate, Long orgId,
			Long finanacialYearId, Long codcofdetId) {
		// TODO Auto-generated method stub
		return null;
		// accountFinancialReportRepository.queryClassifiedBudgetReceiptSideReportDataL1(fromDate,
		// toDate, orgId,codcofdetId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> queryClassifiedBudgetPaymentSideReportDataL1(Date fromDate, Date toDate, Long orgId,
			Long finanacialYearId, Long codcofdetId) {

		return accountFinancialReportRepository.queryClassifiedBudgetPaymentSideReportDataL1(fromDate, toDate, orgId,
				finanacialYearId, codcofdetId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> queryClassifiedBudgetReceiptSideOpeningReportDataL3(Date fromDate, Date toDate, Long orgId,
			Long codcofdetId) {

		// TODO Auto-generated method stub
		final Long coaReceiptLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.LookUp.INDIVIDUAL, PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetReceiptSideOpeningReportDataL3(fromDate, toDate,
				orgId, coaReceiptLookupId);

	}

	@Override
	public List<Object[]> queryClassifiedBudgetReceiptSideNonOpeningReportDataL3(Date fromDate, Date toDate, Long orgId,
			Long codcofdetId) {
		// TODO Auto-generated method stub
		final Long coaReceiptLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.LookUp.INDIVIDUAL, PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetReceiptSideNonOpeningReportDataL3(fromDate, toDate,
				orgId, coaReceiptLookupId);

	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> queryClassifiedBudgetPaymentSideOpeningReportDataL3(Date fromDate, Date toDate, Long orgId,
			Long codcofdetId) {

		// TODO Auto-generated method stub
		final Long coaPaymentLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.NEC.EMPLOYEE,
				PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetPaymentSideOpeningReportDataL3(fromDate, toDate,
				orgId, coaPaymentLookupId);
	}

	@Override
	public List<Object[]> queryClassifiedBudgetPaymentSideNonOpeningReportDataL3(Date fromDate, Date toDate, Long orgId,
			Long codcofdetId) {
		// TODO Auto-generated method stub
		final Long coaPaymentLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.NEC.EMPLOYEE,
				PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetPaymentSideNonOpeningReportDataL3(fromDate, toDate,
				orgId, coaPaymentLookupId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> queryClassifiedBudgetReceiptSideReportDataL2(Date fromDate, Date toDate, Long orgId,
			Long finanacialYearId, Long codcofdetId) {
		// TODO Auto-generated method stub
		return accountFinancialReportRepository.queryClassifiedBudgetReceiptSideReportDataL2(fromDate, toDate, orgId,
				finanacialYearId, codcofdetId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> queryClassifiedBudgetPaymentSideReportDataL2(Date fromDate, Date toDate, Long orgId,
			Long finanacialYearId, Long codcofdetId) {
		// TODO Auto-generated method stub
		return accountFinancialReportRepository.queryClassifiedBudgetPaymentSideReportDataL2(fromDate, toDate, orgId,
				finanacialYearId, codcofdetId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> queryClassifiedBudgetReceiptSideReportDataL4(Date fromDate, Date toDate, Long orgId,
			Long finanacialYearId, Long codcofdetId) {
		// TODO Auto-generated method stub
		return accountFinancialReportRepository.queryClassifiedBudgetReceiptSideReportDataL4(fromDate, toDate, orgId,
				finanacialYearId, codcofdetId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> queryClassifiedBudgetPaymentSideReportDataL4(Date fromDate, Date toDate, Long orgId,
			Long finanacialYearId, Long codcofdetId) {
		// TODO Auto-generated method stub
		return accountFinancialReportRepository.queryClassifiedBudgetPaymentSideReportDataL4(fromDate, toDate, orgId,
				finanacialYearId, codcofdetId);
	}

	@Override
	@Transactional
	public void processGeneralLedgerReport(final ModelMap model, AccountFinancialReportDTO dto, Long orgId) {
		switch (dto.getReportType()) {
		case GLR:
			processGeneralLedgerRegisterReport(model, dto, orgId);
			break;
		default:
			break;
		}
	}

	private void processAuditTrailReport(long orgId, String fromDate, String toDate, ModelMap model) {
		AccountBillEntryMasterHistDto aBillMasHisDto = null;
		List<AccountBillEntryMasterHistDto> auditTrailList = new ArrayList<>();
		AccountBillEntryMasterHistDto auditTrail = new AccountBillEntryMasterHistDto();
		List<Object[]> auditTrailHisData = accountFinancialReportRepository.queryForHistDetails(orgId,
				Utility.stringToDate(fromDate), Utility.stringToDate(toDate));
		Long bmId = null;
		if (auditTrailHisData != null && !auditTrailHisData.isEmpty()) {
			for (Object[] auditTrailEntity : auditTrailHisData) {

				if (auditTrailEntity[15] != null) {

					if (auditTrailEntity[15].equals('C')) {

						bmId = Long.valueOf(auditTrailEntity[0].toString());
						long EmpId = 0;
						aBillMasHisDto = new AccountBillEntryMasterHistDto();

						if (auditTrailEntity[15] != null) {
							aBillMasHisDto.sethStatus((Character) auditTrailEntity[15]);
						}

						if (auditTrailEntity[0] != null) {
							aBillMasHisDto.sethBmId(Long.valueOf(auditTrailEntity[0].toString()));
						}

						if (auditTrailEntity[1] != null) {
							aBillMasHisDto.setBillNo(auditTrailEntity[1].toString());
						}

						if (auditTrailEntity[2] != null) {
							aBillMasHisDto.setBillEntryDate(Utility.dateToString((Date) auditTrailEntity[2]));
						}

						if (auditTrailEntity[3] != null) {
							aBillMasHisDto.setVendorName(auditTrailEntity[3].toString());
						}

						if (auditTrailEntity[4] != null) {

							String accountCodeDesc = budgetCodeService.findAccountHeadCodeBySacHeadId(
									Long.valueOf(auditTrailEntity[4].toString()), orgId);
							aBillMasHisDto.setSecHeadId(accountCodeDesc.toString());
						}

						if (auditTrailEntity[5] != null) {
							aBillMasHisDto.setBillChargeAmt(auditTrailEntity[5].toString());
						}

						if (auditTrailEntity[6] != null) {
							aBillMasHisDto.setBillTotalAmount(new BigDecimal(auditTrailEntity[6].toString()));
						}

						if (auditTrailEntity[16] != null) {
							String accountCodeDesc = budgetCodeService.findAccountHeadCodeBySacHeadId(
									Long.valueOf(auditTrailEntity[16].toString()), orgId);
							aBillMasHisDto.setSecHeadId1(accountCodeDesc.toString());
						}

						if (auditTrailEntity[7] != null) {
							aBillMasHisDto.setDeductionAmt(auditTrailEntity[7].toString());
						}

						if (auditTrailEntity[8] != null) {
							aBillMasHisDto.setNarration(auditTrailEntity[8].toString());
						}

						if (auditTrailEntity[9] != null) {
							aBillMasHisDto.setLgIpMacAddress(auditTrailEntity[9].toString());
						}

						if (auditTrailEntity[10] != null) {
							EmpId = Long.valueOf(auditTrailEntity[10].toString());
							String userName = accountFinancialReportRepository.queryReAppropriationReportForUser(EmpId);
							aBillMasHisDto.setCreatedBy(userName);
						}

						if (auditTrailEntity[11] != null) {
							aBillMasHisDto.setCreatedDate(Utility.dateToString((Date) auditTrailEntity[11]));
						}

						if (auditTrailEntity[12] != null) {
							EmpId = Long.valueOf(auditTrailEntity[12].toString());
							String userName = accountFinancialReportRepository.queryReAppropriationReportForUser(EmpId);
							aBillMasHisDto.setUpdatedBy(userName);
						}

						if (auditTrailEntity[13] != null) {
							aBillMasHisDto.setUpdatedDate(Utility.dateToString((Date) auditTrailEntity[13]));
						}

						if (auditTrailEntity[14] != null) {
							aBillMasHisDto.setLgIpMacAddressUpdated((String) auditTrailEntity[14]);
						}
						auditTrailList.add(aBillMasHisDto);

					}

					if (aBillMasHisDto.gethBmId() != null) {
						if (aBillMasHisDto.gethBmId().equals(Long.valueOf(auditTrailEntity[0].toString()))
								&& auditTrailEntity[15].equals('U')) {
							long EmpId = 0;
							aBillMasHisDto = new AccountBillEntryMasterHistDto();

							if (auditTrailEntity[1] != null) {
								aBillMasHisDto.setBillNo(auditTrailEntity[1].toString());
							}

							if (auditTrailEntity[2] != null) {
								aBillMasHisDto.setBillEntryDate(Utility.dateToString((Date) auditTrailEntity[2]));
							}

							if (auditTrailEntity[3] != null) {
								aBillMasHisDto.setVendorName(auditTrailEntity[3].toString());
							}

							if (auditTrailEntity[4] != null) {
								String accountCodeDesc = budgetCodeService.findAccountHeadCodeBySacHeadId(
										Long.valueOf(auditTrailEntity[4].toString()), orgId);
								aBillMasHisDto.setSecHeadId(accountCodeDesc.toString());
							}

							if (auditTrailEntity[5] != null) {
								aBillMasHisDto.setBillChargeAmt(auditTrailEntity[5].toString());
							}

							if (auditTrailEntity[6] != null) {
								aBillMasHisDto.setBillTotalAmount(new BigDecimal(auditTrailEntity[6].toString()));
							}

							if (auditTrailEntity[16] != null) {
								String accountCodeDesc = budgetCodeService.findAccountHeadCodeBySacHeadId(
										Long.valueOf(auditTrailEntity[16].toString()), orgId);
								aBillMasHisDto.setSecHeadId1(accountCodeDesc.toString());
							}

							if (auditTrailEntity[7] != null) {
								aBillMasHisDto.setDeductionAmt(auditTrailEntity[7].toString());
							}

							if (auditTrailEntity[8] != null) {
								aBillMasHisDto.setNarration(auditTrailEntity[8].toString());
							}

							if (auditTrailEntity[9] != null) {
								aBillMasHisDto.setLgIpMacAddress(auditTrailEntity[9].toString());
							}

							if (auditTrailEntity[10] != null) {
								EmpId = Long.valueOf(auditTrailEntity[10].toString());
								String userName = accountFinancialReportRepository
										.queryReAppropriationReportForUser(EmpId);
								aBillMasHisDto.setCreatedBy(userName);
							}
							if (auditTrailEntity[11] != null) {
								aBillMasHisDto.setCreatedDate(Utility.dateToString((Date) auditTrailEntity[11]));
							}

							if (auditTrailEntity[12] != null) {

								EmpId = Long.valueOf(auditTrailEntity[12].toString());
								String userName = accountFinancialReportRepository
										.queryReAppropriationReportForUser(EmpId);
								aBillMasHisDto.setUpdatedBy(userName);
							}

							if (auditTrailEntity[13] != null) {
								aBillMasHisDto.setUpdatedDate(Utility.dateToString((Date) auditTrailEntity[13]));
							}

							if (auditTrailEntity[14] != null) {
								aBillMasHisDto.setLgIpMacAddressUpdated((String) auditTrailEntity[14]);
							}
							auditTrailList.add(aBillMasHisDto);
						}
					}
				}
			}
		}
		auditTrail.setFromDate(fromDate);
		auditTrail.setToDate(toDate);
		auditTrail.setListOfmasterData(auditTrailList);
		model.addAttribute(REPORT_DATA, auditTrail);
	}

	private void processTdsCertificateReport(long orgId, Long financialId, Long vendorId, ModelMap model) {
		AccountFinancialReportDTO tdsCertificatedto = new AccountFinancialReportDTO();
		List<AccountFinancialReportDTO> tdsCertificateList = new ArrayList<>();
		final AccountFinancialReportDTO tdsCertificated = new AccountFinancialReportDTO();

		final List<Object[]> tdsCertificatedata = accountFinancialReportRepository.queryForTdsCertificate(orgId,
				vendorId);
		if (tdsCertificatedata != null && !tdsCertificatedata.isEmpty()) {
			for (Object[] tdsCertificateEntity : tdsCertificatedata) {

				if (tdsCertificateEntity[1] != null) {
					tdsCertificatedto.setVendorName(tdsCertificateEntity[1].toString());
				}

				if (tdsCertificateEntity[2] != null) {
					tdsCertificatedto.setVendorAdd(tdsCertificateEntity[2].toString());
				}

				if (tdsCertificateEntity[3] != null) {
					tdsCertificatedto.setVendorPanNo(tdsCertificateEntity[3].toString());
				}

				if (tdsCertificateEntity[4] != null) {
					tdsCertificatedto.setOrgName(tdsCertificateEntity[4].toString());
				}

				if (tdsCertificateEntity[5] != null) {
					tdsCertificatedto.setOrgAdd(tdsCertificateEntity[5].toString());
				}
				if (tdsCertificateEntity[6] != null) {
					tdsCertificatedto.setOrgPanNo(tdsCertificateEntity[6].toString());
				}
			}
		}
		tdsCertificatedto.setVendorName(vendorId.toString());
		tdsCertificatedto.setFinancialYear(financialId.toString());
		model.addAttribute(REPORT_DATA, tdsCertificatedto);

	}

	@Override
	@Transactional(readOnly = true)
	public AccountFinancialReportDTO getAccountDashBoardReportDetails() {

		BigDecimal totalVendors = BigDecimal.ZERO;
		BigDecimal totalAccountHeads = BigDecimal.ZERO;
		BigDecimal totalDeposits = BigDecimal.ZERO;
		BigDecimal totalReceipts = BigDecimal.ZERO;
		BigDecimal totalDepositSlips = BigDecimal.ZERO;
		BigDecimal totalBills = BigDecimal.ZERO;
		BigDecimal totalPayments = BigDecimal.ZERO;
		BigDecimal totalVouchers = BigDecimal.ZERO;

		List<Object[]> recordsOfDashBoard = accountFinancialReportRepository.findAccountDashBoardAllRecords();

		List<AccountFinancialReportDTO> listOfDto = new ArrayList<>();
		AccountFinancialReportDTO finalAccountFinancialReportDTO = new AccountFinancialReportDTO();
		if (recordsOfDashBoard.isEmpty() || recordsOfDashBoard == null) {
			return finalAccountFinancialReportDTO;
		} else {
			for (Object[] dashBoardDetails : recordsOfDashBoard) {
				AccountFinancialReportDTO accountFinancialReportDTO = new AccountFinancialReportDTO();
				if (dashBoardDetails[1] != null) {
					accountFinancialReportDTO.setOrgName(dashBoardDetails[1].toString());
				}
				if (dashBoardDetails[2] != null) {
					accountFinancialReportDTO.setVendorName(dashBoardDetails[2].toString());
					totalVendors = totalVendors.add(new BigDecimal(dashBoardDetails[2].toString()));
				}
				if (dashBoardDetails[3] != null) {
					accountFinancialReportDTO.setAccountHead(dashBoardDetails[3].toString());
					totalAccountHeads = totalAccountHeads.add(new BigDecimal(dashBoardDetails[3].toString()));
				}
				if (dashBoardDetails[4] != null) {
					accountFinancialReportDTO.setDepositNarration(dashBoardDetails[4].toString());
					totalDeposits = totalDeposits.add(new BigDecimal(dashBoardDetails[4].toString()));
				}
				if (dashBoardDetails[5] != null) {
					accountFinancialReportDTO.setReceiptNumber(dashBoardDetails[5].toString());
					totalReceipts = totalReceipts.add(new BigDecimal(dashBoardDetails[5].toString()));
				}
				if (dashBoardDetails[6] != null) {
					accountFinancialReportDTO.setTypeOfDeposit(dashBoardDetails[6].toString());
					totalDepositSlips = totalDepositSlips.add(new BigDecimal(dashBoardDetails[6].toString()));
				}
				if (dashBoardDetails[7] != null) {
					accountFinancialReportDTO.setBillNo(dashBoardDetails[7].toString());
					totalBills = totalBills.add(new BigDecimal(dashBoardDetails[7].toString()));
				}
				if (dashBoardDetails[8] != null) {
					accountFinancialReportDTO.setPaymentNo(dashBoardDetails[8].toString());
					totalPayments = totalPayments.add(new BigDecimal(dashBoardDetails[8].toString()));
				}
				if (dashBoardDetails[9] != null) {
					accountFinancialReportDTO.setVoucherNo(dashBoardDetails[9].toString());
					totalVouchers = totalVouchers.add(new BigDecimal(dashBoardDetails[9].toString()));
				}
				listOfDto.add(accountFinancialReportDTO);
			}
			finalAccountFinancialReportDTO.setListOfDayBook(listOfDto);
			finalAccountFinancialReportDTO.setVendorAdd(totalVendors.toString());
			finalAccountFinancialReportDTO.setBalanceAmountIndianCurrency(totalAccountHeads.toString());
			finalAccountFinancialReportDTO.setTotalDepositIndianCurrency(totalDeposits.toString());
			finalAccountFinancialReportDTO.setReceiptAmt(totalReceipts.toString());
			finalAccountFinancialReportDTO.setDepositAmountIndianCurrency(totalDepositSlips.toString());
			finalAccountFinancialReportDTO.setBillAmt(totalBills.toString());
			finalAccountFinancialReportDTO.setPaymentAmnt(totalPayments.toString());
			finalAccountFinancialReportDTO.setVoucherAmountIndianCurrency(totalVouchers.toString());
			// model.addAttribute(REPORT_DATA, finalAccountFinancialReportDTO);
			return finalAccountFinancialReportDTO;
		}
	}

	private void processGeneratebBalanceSheet1(ModelMap model, long orgId, String transactionDate, int langId,
			Long accountHeadId, Long superOrgId) {
		BigDecimal totalClosingAsset = new BigDecimal(0.00);
		BigDecimal totalClosingAnOnAsset = new BigDecimal(0.00);
		BigDecimal totalClosingliability = new BigDecimal(0.00);
		BigDecimal totalClosingAnOnliability = new BigDecimal(0.00);
		Long financialYearId = tbFinancialyearJpaRepository
				.getFinanciaYearIdByFromDate(Utility.stringToDate(transactionDate));
		Date fromDates = tbFinancialyearJpaRepository
				.getFromDateFromFinancialYearIdByPassingDate(Utility.stringToDate(transactionDate));
		List<AccountFinancialReportDTO> balanceSheetList = new ArrayList<>();
		List<AccountFinancialReportDTO> balanceSheetListLiability = new ArrayList<>();
		List<AccountFinancialReportDTO> accountBalanceAssetList = new ArrayList<>();
		List<AccountFinancialReportDTO> accountBalanceLiabilityList = new ArrayList<>();
		List<Object[]> balanceSheetReport = null;
		List<Object[]> previousBalanceList = null;
		List<Object[]> listofliabilityHead = null;
		List<Object[]> listofAssetHead = null;
		final Long param1 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.AHP.getValue(),
				PrefixConstants.CMD, orgId);
		final Long param2 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.AHS.getValue(),
				PrefixConstants.CMD, orgId);
		List<LookUp> levelHead = acCodingstructureMasService.queryAccountHeadByChartOfAccount(param1, param2, orgId);
		if (levelHead != null && !levelHead.isEmpty()) {
			if (levelHead.get(0).getLookUpId() == accountHeadId) { //Detail from list 
				balanceSheetReport = accountFinancialReportRepository.findBalanceSheetMinorHeadReport(orgId, fromDates,
						Utility.stringToDate(transactionDate), financialYearId);
				previousBalanceList = findPreviousYearMinorHeadBalanceSheet(orgId, fromDates,
						Utility.stringToDate(transactionDate), financialYearId);
				listofliabilityHead = findliabilityMajorHead(superOrgId);
				listofAssetHead = findAssetMajorHead(superOrgId);
			} else if (levelHead.get(1).getLookUpId() == accountHeadId) {//Major from list   
				balanceSheetReport = accountFinancialReportRepository.findBalanceSheetActualMajorHeadReport(orgId, fromDates,
					Utility.stringToDate(transactionDate), financialYearId);
				previousBalanceList = findPreviousYearActualMajorHeadBalanceSheet(orgId, fromDates,
					Utility.stringToDate(transactionDate), financialYearId);
				listofliabilityHead = findliabilityMajorHead(superOrgId);
				listofAssetHead = findAssetMajorHead(superOrgId);
			} else if (levelHead.get(2).getLookUpId() == accountHeadId) { //minor from list 
				balanceSheetReport = accountFinancialReportRepository.findBalanceSheetMajorHeadReport(orgId, fromDates,
						Utility.stringToDate(transactionDate), financialYearId);
				previousBalanceList = findPreviousYearMajorHeadBalanceSheet(orgId, fromDates,
						Utility.stringToDate(transactionDate), financialYearId);
				listofliabilityHead = findliabilityMajorHead(superOrgId);
				listofAssetHead = findAssetMajorHead(superOrgId);
			}else if (levelHead.get(3).getLookUpId() == accountHeadId) {// secondary head 
				balanceSheetReport = accountFinancialReportRepository.findBalanceSheetReport(orgId, fromDates,
						Utility.stringToDate(transactionDate), financialYearId);
				previousBalanceList = findPreviousYearBalanceSheet(orgId, fromDates,
						Utility.stringToDate(transactionDate), financialYearId);
				listofliabilityHead = findliabilityHead(orgId);
				listofAssetHead = findAssetHead(orgId);
			}
		}
		// Liabilitity
		if (balanceSheetReport != null && !balanceSheetReport.isEmpty()) {
			for (Object[] balanceSheetReports : balanceSheetReport) {
				AccountFinancialReportDTO accountFinancialReportDTO = new AccountFinancialReportDTO();
				if (previousBalanceList != null && !previousBalanceList.isEmpty()) {
					for (final Object[] previousBalanceLists : previousBalanceList) {
						if (balanceSheetReports[2].equals(previousBalanceLists[2])) {
							if (previousBalanceLists[3] != null && !previousBalanceLists[3].toString().equals("0.00")
									&& !previousBalanceLists[3].toString().equals("0")) {
								accountFinancialReportDTO.setClosingBalanceAsOn(
										calculateClosingBalAsOnDate((BigDecimal) previousBalanceLists[4], null,
												(BigDecimal) previousBalanceLists[3]));
							} else if (previousBalanceLists[4] != null
									&& !previousBalanceLists[4].toString().equals("0.00")
									&& !previousBalanceLists[4].toString().equals("0")) {
								accountFinancialReportDTO.setClosingBalanceAsOn(
										calculateClosingBalAsOnDate((BigDecimal) previousBalanceLists[4], null, null));
							} else {
								accountFinancialReportDTO.setClosingBalanceAsOn(calculateClosingBalTranAsOnDate(
										(BigDecimal) previousBalanceLists[4], null, null));
							}
						}
					}
				}
				if (balanceSheetReports[0] != null) {
					accountFinancialReportDTO.setAccountHead((String) balanceSheetReports[0]);
				}
				if (balanceSheetReports[1] != null) {
					accountFinancialReportDTO.setAccountHeadDesc((String) balanceSheetReports[1]);
				}
				if (balanceSheetReports[2] != null) {
					accountFinancialReportDTO.setAccountCode((String) balanceSheetReports[2].toString());
				}

				if (balanceSheetReports[3] != null) {
					accountFinancialReportDTO.setOpeningDrAmount(new BigDecimal(balanceSheetReports[3].toString()));
				}

				if (balanceSheetReports[4] != null) {
					accountFinancialReportDTO.setOpeningCrAmount(new BigDecimal(balanceSheetReports[4].toString()));
				}
				if (balanceSheetReports[3] != null && !balanceSheetReports[3].toString().equals("0.00")
						&& !balanceSheetReports[3].toString().equals("0")) {
					accountFinancialReportDTO.setClosingBalance(
							calculateClosingBalAsOnDate(accountFinancialReportDTO.getClosingBalanceAsOn(),
									(BigDecimal) balanceSheetReports[6], (BigDecimal) balanceSheetReports[5]));
				} else if (balanceSheetReports[4] != null && !balanceSheetReports[4].toString().equals("0.00")
						&& !balanceSheetReports[4].toString().equals("0")) {
					accountFinancialReportDTO.setClosingBalance(
							calculateClosingBalAsOnDate(accountFinancialReportDTO.getClosingBalanceAsOn(),
									(BigDecimal) balanceSheetReports[6], (BigDecimal) balanceSheetReports[5]));
				} else if (balanceSheetReports[5] != null && !balanceSheetReports[5].toString().equals("0.00")
						&& !balanceSheetReports[5].toString().equals("0")) {
					accountFinancialReportDTO.setClosingBalance(
							calculateClosingBalAsOnDate(accountFinancialReportDTO.getClosingBalanceAsOn(),
									(BigDecimal) balanceSheetReports[6], (BigDecimal) balanceSheetReports[5]));
				} else {
					accountFinancialReportDTO.setClosingBalance(
							calculateClosingBalTranAsOnDate(accountFinancialReportDTO.getClosingBalanceAsOn(),
									(BigDecimal) balanceSheetReports[6], (BigDecimal) balanceSheetReports[5]));
				}
				balanceSheetListLiability.add(accountFinancialReportDTO);
			}
		} else if (previousBalanceList != null && !previousBalanceList.isEmpty()) {
			for (final Object[] previousBalanceLists : previousBalanceList) {
				AccountFinancialReportDTO accountFinancialReportDTO = new AccountFinancialReportDTO();
				if (previousBalanceLists[0] != null) {
					accountFinancialReportDTO.setAccountHead((String) previousBalanceLists[0]);
				}
				if (previousBalanceLists[1] != null) {
					accountFinancialReportDTO.setAccountHeadDesc((String) previousBalanceLists[1]);
				}
				if (previousBalanceLists[2] != null) {
					accountFinancialReportDTO.setAccountCode((String) previousBalanceLists[2].toString());
				}
				if (previousBalanceLists[3] != null) {
					accountFinancialReportDTO.setOpeningDrAmount(new BigDecimal(previousBalanceLists[3].toString()));
				}
				if (previousBalanceLists[4] != null) {
					accountFinancialReportDTO.setOpeningCrAmount(new BigDecimal(previousBalanceLists[4].toString()));
				}
				if (previousBalanceLists[3] != null && !previousBalanceLists[3].toString().equals("0.00")
						&& !previousBalanceLists[3].toString().equals("0")) {
					accountFinancialReportDTO.setClosingBalanceAsOn(calculateClosingBalAsOnDate(
							(BigDecimal) previousBalanceLists[4], null, (BigDecimal) previousBalanceLists[3]));
				} else if (previousBalanceLists[4] != null && !previousBalanceLists[4].toString().equals("0.00")
						&& !previousBalanceLists[4].toString().equals("0")) {
					accountFinancialReportDTO.setClosingBalanceAsOn(
							calculateClosingBalAsOnDate((BigDecimal) previousBalanceLists[4], null, null));
				} else {
					accountFinancialReportDTO.setClosingBalanceAsOn(
							calculateClosingBalTranAsOnDate((BigDecimal) previousBalanceLists[4], null, null));
				}
				balanceSheetListLiability.add(accountFinancialReportDTO);
			}
		}
		if (listofliabilityHead != null && !listofliabilityHead.isEmpty() && balanceSheetListLiability != null
				&& !balanceSheetListLiability.isEmpty()) {
			for (AccountFinancialReportDTO AccountFinancialReportDTO1 : balanceSheetListLiability) {
				AccountFinancialReportDTO accountFinancialReportDTO2 = new AccountFinancialReportDTO();
				for (Object[] listofliabilityHeads : listofliabilityHead) {
					if (AccountFinancialReportDTO1.getAccountCode().equals(listofliabilityHeads[0].toString())) {
						if (AccountFinancialReportDTO1.getAccountHead() != null)
							accountFinancialReportDTO2.setAccountHead(AccountFinancialReportDTO1.getAccountHead());
						accountFinancialReportDTO2.setAccountHeadDesc(AccountFinancialReportDTO1.getAccountHeadDesc());
						if (AccountFinancialReportDTO1.getOpeningDrAmount() != null
								&& !(AccountFinancialReportDTO1.getOpeningDrAmount().compareTo(BigDecimal.ZERO) == 0)) {
							if (AccountFinancialReportDTO1.getClosingBalance() != null && !(AccountFinancialReportDTO1
									.getClosingBalance().compareTo(BigDecimal.ZERO) == 0)) {
								accountFinancialReportDTO2
										.setClosingBalance(AccountFinancialReportDTO1.getClosingBalance());
							}
							if (AccountFinancialReportDTO1.getClosingBalanceAsOn() != null
									&& !(AccountFinancialReportDTO1.getClosingBalanceAsOn()
											.compareTo(BigDecimal.ZERO) == 0)) {
								accountFinancialReportDTO2
										.setClosingBalanceAsOn(AccountFinancialReportDTO1.getClosingBalanceAsOn());
							}
						} else {
							if (AccountFinancialReportDTO1.getClosingBalance() != null && !(AccountFinancialReportDTO1
									.getClosingBalance().compareTo(BigDecimal.ZERO) == 0))
								if (AccountFinancialReportDTO1.getClosingBalance().signum() == -1) {
									accountFinancialReportDTO2
											.setClosingBalance(AccountFinancialReportDTO1.getClosingBalance());
								} else {
									accountFinancialReportDTO2
											.setClosingBalance(AccountFinancialReportDTO1.getClosingBalance().abs());
								}
							if (AccountFinancialReportDTO1.getClosingBalanceAsOn() != null
									&& !(AccountFinancialReportDTO1.getClosingBalanceAsOn()
											.compareTo(BigDecimal.ZERO) == 0))
								accountFinancialReportDTO2
										.setClosingBalanceAsOn(AccountFinancialReportDTO1.getClosingBalanceAsOn());

						}
						if (accountFinancialReportDTO2.getClosingBalance() != null) {
							accountBalanceLiabilityList.add(accountFinancialReportDTO2);
						} else if (accountFinancialReportDTO2.getClosingBalanceAsOn() != null) {
							accountBalanceLiabilityList.add(accountFinancialReportDTO2);
						}
						if (AccountFinancialReportDTO1.getOpeningDrAmount() != null
								&& !(AccountFinancialReportDTO1.getOpeningDrAmount().compareTo(BigDecimal.ZERO) == 0)) {
							if (AccountFinancialReportDTO1.getClosingBalance() != null) {
								if (AccountFinancialReportDTO1.getClosingBalance().signum() == -1) {
									totalClosingliability = totalClosingliability
											.add(AccountFinancialReportDTO1.getClosingBalance());
								} else {
									totalClosingliability = totalClosingliability
											.add(AccountFinancialReportDTO1.getClosingBalance());
								}
							}
							if (AccountFinancialReportDTO1.getClosingBalanceAsOn() != null) {
								if (AccountFinancialReportDTO1.getClosingBalanceAsOn().signum() == -1) {
									totalClosingAnOnliability = totalClosingAnOnliability
											.add(AccountFinancialReportDTO1.getClosingBalanceAsOn());
								} else {
									totalClosingAnOnliability = totalClosingAnOnliability
											.add(AccountFinancialReportDTO1.getClosingBalanceAsOn());
								}
							}
						} else {
							if (AccountFinancialReportDTO1.getClosingBalance() != null && !(AccountFinancialReportDTO1
									.getClosingBalance().compareTo(BigDecimal.ZERO) == 0)) {
								if (AccountFinancialReportDTO1.getClosingBalance().signum() == -1) {
									totalClosingliability = totalClosingliability
											.add(AccountFinancialReportDTO1.getClosingBalance());
								} else {
									totalClosingliability = totalClosingliability
											.add(AccountFinancialReportDTO1.getClosingBalance());
								}
							}
							if (AccountFinancialReportDTO1.getClosingBalanceAsOn() != null
									&& !(AccountFinancialReportDTO1.getClosingBalanceAsOn()
											.compareTo(BigDecimal.ZERO) == 0)) {
								if (AccountFinancialReportDTO1.getClosingBalanceAsOn().signum() == -1) {
									totalClosingAnOnliability = totalClosingAnOnliability
											.add(AccountFinancialReportDTO1.getClosingBalanceAsOn());
								} else {
									totalClosingAnOnliability = totalClosingAnOnliability
											.add(AccountFinancialReportDTO1.getClosingBalanceAsOn());
								}
							}
						}
					}

				}

			}

		}
		// Assets
		if (balanceSheetReport != null && !balanceSheetReport.isEmpty()) {
			for (Object[] balanceSheetReports : balanceSheetReport) {
				AccountFinancialReportDTO accountFinancialReportDTO = new AccountFinancialReportDTO();
				if (previousBalanceList != null && !previousBalanceList.isEmpty()) {
					for (final Object[] previousBalanceLists : previousBalanceList) {
						if (balanceSheetReports[2].equals(previousBalanceLists[2])) {
							if (previousBalanceLists[3] != null && !previousBalanceLists[3].toString().equals("0.00")
									&& !previousBalanceLists[3].toString().equals("0")) {
								accountFinancialReportDTO.setClosingBalanceAsOn(
										calculateClosingBalAsOnDate((BigDecimal) previousBalanceLists[3], null,
												(BigDecimal) previousBalanceLists[4]));
							} else if (previousBalanceLists[4] != null
									&& !previousBalanceLists[4].toString().equals("0.00")
									&& !previousBalanceLists[4].toString().equals("0")) {
								accountFinancialReportDTO.setClosingBalanceAsOn(
										calculateClosingBalAsOnDate(null, null, (BigDecimal) previousBalanceLists[4]));
							} else {
								accountFinancialReportDTO.setClosingBalanceAsOn(
										calculateClosingBalAsOnDate(null, null, (BigDecimal) previousBalanceLists[4]));
							}
						}
					}
				}
				if (balanceSheetReports[0] != null) {
					accountFinancialReportDTO.setAccountHead((String) balanceSheetReports[0]);
				}
				if (balanceSheetReports[1] != null) {
					accountFinancialReportDTO.setAccountHeadDesc((String) balanceSheetReports[1]);
				}
				if (balanceSheetReports[2] != null) {
					accountFinancialReportDTO.setAccountCode((String) balanceSheetReports[2].toString());
				}

				if (balanceSheetReports[3] != null) {
					accountFinancialReportDTO.setOpeningDrAmount(new BigDecimal(balanceSheetReports[3].toString()));
				}

				if (balanceSheetReports[4] != null) {
					accountFinancialReportDTO.setOpeningCrAmount(new BigDecimal(balanceSheetReports[4].toString()));
				}

				if (balanceSheetReports[3] != null && !balanceSheetReports[3].toString().equals("0.00")
						&& !balanceSheetReports[3].toString().equals("0")) {
					accountFinancialReportDTO.setClosingBalance(
							calculateClosingBalAsOnDate(accountFinancialReportDTO.getClosingBalanceAsOn(),
									(BigDecimal) balanceSheetReports[5], (BigDecimal) balanceSheetReports[6]));
				} else if (balanceSheetReports[4] != null && !balanceSheetReports[4].toString().equals("0.00")
						&& !balanceSheetReports[4].toString().equals("0")) {
					accountFinancialReportDTO.setClosingBalance(
							calculateClosingBalAsOnDate(accountFinancialReportDTO.getClosingBalanceAsOn(),
									(BigDecimal) balanceSheetReports[5], (BigDecimal) balanceSheetReports[6]));
				} else if (balanceSheetReports[5] != null && !balanceSheetReports[5].toString().equals("0.00")
						&& !balanceSheetReports[5].toString().equals("0")) {
					accountFinancialReportDTO.setClosingBalance(
							calculateClosingBalAsOnDate(accountFinancialReportDTO.getClosingBalanceAsOn(),
									(BigDecimal) balanceSheetReports[5], (BigDecimal) balanceSheetReports[6]));
				} else {
					accountFinancialReportDTO.setClosingBalance(
							calculateClosingBalAsOnDate(accountFinancialReportDTO.getClosingBalanceAsOn(),
									(BigDecimal) balanceSheetReports[5], (BigDecimal) balanceSheetReports[6]));
				}
				balanceSheetList.add(accountFinancialReportDTO);

			}

		} else if (previousBalanceList != null && !previousBalanceList.isEmpty()) {
			for (final Object[] previousBalanceLists : previousBalanceList) {
				AccountFinancialReportDTO accountFinancialReportDTO = new AccountFinancialReportDTO();
				if (previousBalanceLists[0] != null) {
					accountFinancialReportDTO.setAccountHead((String) previousBalanceLists[0]);
				}
				if (previousBalanceLists[1] != null) {
					accountFinancialReportDTO.setAccountHeadDesc((String) previousBalanceLists[1]);
				}
				if (previousBalanceLists[2] != null) {
					accountFinancialReportDTO.setAccountCode((String) previousBalanceLists[2].toString());
				}
				if (previousBalanceLists[3] != null) {
					accountFinancialReportDTO.setOpeningDrAmount(new BigDecimal(previousBalanceLists[3].toString()));
				}
				if (previousBalanceLists[4] != null) {
					accountFinancialReportDTO.setOpeningCrAmount(new BigDecimal(previousBalanceLists[4].toString()));
				}
				if (previousBalanceLists[3] != null && !previousBalanceLists[3].toString().equals("0.00")
						&& !previousBalanceLists[3].toString().equals("0")) {
					accountFinancialReportDTO.setClosingBalanceAsOn(calculateClosingBalAsOnDate(
							(BigDecimal) previousBalanceLists[3], null, (BigDecimal) previousBalanceLists[4]));
				} else if (previousBalanceLists[4] != null && !previousBalanceLists[4].toString().equals("0.00")
						&& !previousBalanceLists[4].toString().equals("0")) {
					accountFinancialReportDTO.setClosingBalanceAsOn(
							calculateClosingBalAsOnDate((BigDecimal) previousBalanceLists[4], null, null));
				} else {
					accountFinancialReportDTO.setClosingBalanceAsOn(
							calculateClosingBalAsOnDate((BigDecimal) previousBalanceLists[4], null, null));
				}
				balanceSheetList.add(accountFinancialReportDTO);
			}
		}

		if (listofAssetHead != null && !listofAssetHead.isEmpty() && balanceSheetList != null
				&& !balanceSheetList.isEmpty()) {
			for (AccountFinancialReportDTO accountFinancialReportDTO1 : balanceSheetList) {
				AccountFinancialReportDTO accountFinancialReportDTO3 = new AccountFinancialReportDTO();
				for (Object[] listofAssetHeads : listofAssetHead) {
					if (accountFinancialReportDTO1.getAccountCode().equals(listofAssetHeads[0].toString())) {
						if (accountFinancialReportDTO1.getAccountHead() != null)
							accountFinancialReportDTO3.setAccountHead(accountFinancialReportDTO1.getAccountHead());
						accountFinancialReportDTO3.setAccountHeadDesc(accountFinancialReportDTO1.getAccountHeadDesc());
						if (accountFinancialReportDTO1.getOpeningCrAmount() != null
								&& !(accountFinancialReportDTO1.getOpeningCrAmount().compareTo(BigDecimal.ZERO) == 0)) {
							if (accountFinancialReportDTO1.getClosingBalance() != null && !(accountFinancialReportDTO1
									.getClosingBalance().compareTo(BigDecimal.ZERO) == 0)) {
								accountFinancialReportDTO3
										.setClosingBalance(accountFinancialReportDTO1.getClosingBalance());
							}
							if (accountFinancialReportDTO1.getClosingBalanceAsOn() != null
									&& !(accountFinancialReportDTO1.getClosingBalanceAsOn()
											.compareTo(BigDecimal.ZERO) == 0)) {
								accountFinancialReportDTO3
										.setClosingBalanceAsOn(accountFinancialReportDTO1.getClosingBalanceAsOn());

							}
						} else {
							if (accountFinancialReportDTO1.getClosingBalance() != null && !(accountFinancialReportDTO1
									.getClosingBalance().compareTo(BigDecimal.ZERO) == 0))
								accountFinancialReportDTO3
										.setClosingBalance(accountFinancialReportDTO1.getClosingBalance());
							if (accountFinancialReportDTO1.getClosingBalanceAsOn() != null
									&& !(accountFinancialReportDTO1.getClosingBalanceAsOn()
											.compareTo(BigDecimal.ZERO) == 0))
								accountFinancialReportDTO3
										.setClosingBalanceAsOn(accountFinancialReportDTO1.getClosingBalanceAsOn());
						}
						if (accountFinancialReportDTO3.getClosingBalance() != null) {
							accountBalanceAssetList.add(accountFinancialReportDTO3);
						} else if (accountFinancialReportDTO3.getClosingBalanceAsOn() != null) {
							accountBalanceAssetList.add(accountFinancialReportDTO3);
						}
						// Total
						if (accountFinancialReportDTO1.getOpeningCrAmount() != null
								&& !(accountFinancialReportDTO1.getOpeningCrAmount().compareTo(BigDecimal.ZERO) == 0)) {
							if (accountFinancialReportDTO1.getClosingBalance() != null) {
								if (accountFinancialReportDTO1.getClosingBalance().signum() == -1) {
									totalClosingAsset = totalClosingAsset
											.add(accountFinancialReportDTO1.getClosingBalance());
								} else {
									totalClosingAsset = totalClosingAsset
											.add(accountFinancialReportDTO1.getClosingBalance());
								}
							}
							if (accountFinancialReportDTO1.getClosingBalanceAsOn() != null) {
								if (accountFinancialReportDTO1.getClosingBalanceAsOn().signum() == -1) {
									totalClosingAnOnAsset = totalClosingAnOnAsset
											.add(accountFinancialReportDTO1.getClosingBalanceAsOn());
								} else {
									totalClosingAnOnAsset = totalClosingAnOnAsset
											.add(accountFinancialReportDTO1.getClosingBalanceAsOn());
								}
							}
						} else {
							if (accountFinancialReportDTO1.getClosingBalance() != null) {
								totalClosingAsset = totalClosingAsset
										.add(accountFinancialReportDTO1.getClosingBalance());
							}
							if (accountFinancialReportDTO1.getClosingBalanceAsOn() != null) {
								totalClosingAnOnAsset = totalClosingAnOnAsset
										.add(accountFinancialReportDTO1.getClosingBalanceAsOn());
							}
						}

					}

				}

			}
		}
		BigDecimal finalSumIncomeDr = BigDecimal.ZERO;
		BigDecimal finalSumIncomeCr = BigDecimal.ZERO;
		BigDecimal finalSumExpenditureDr = BigDecimal.ZERO;
		BigDecimal finalSumExpenditureCr = BigDecimal.ZERO;
		List<Object[]> incomeRecord = queryReportDataFromViewIncome(Utility.dateToString(fromDates), transactionDate,
				orgId, langId);
		for (Object[] objects : incomeRecord) {
			if (objects[2] != null) {
				finalSumIncomeCr = finalSumIncomeCr.add(new BigDecimal(objects[2].toString()));
			}
			if (objects[3] != null) {
				finalSumIncomeDr = finalSumIncomeDr.add(new BigDecimal(objects[3].toString()));
			}
		}
		List<Object[]> ExpenditureRecord = queryReportDataFromViewExpenditure(Utility.dateToString(fromDates),
				transactionDate, orgId, langId);
		for (Object[] objects : ExpenditureRecord) {
			if (objects[2] != null) {
				finalSumExpenditureCr = finalSumExpenditureCr.add(new BigDecimal(objects[2].toString()));
			}
			if (objects[3] != null) {
				finalSumExpenditureDr = finalSumExpenditureDr.add(new BigDecimal(objects[3].toString()));
			}
		}
		BigDecimal finalIncomeTotalAmt = finalSumIncomeCr.subtract(finalSumIncomeDr);
		BigDecimal finalExpenditureTotalAmt = finalSumExpenditureDr.subtract(finalSumExpenditureCr);
		BigDecimal reverveAndSurpresAmount = finalIncomeTotalAmt.subtract(finalExpenditureTotalAmt);
		BigDecimal finalPreviousSumIncomeDr = BigDecimal.ZERO;
		BigDecimal finalPreviousSumIncomeCr = BigDecimal.ZERO;
		BigDecimal finalPreviousSumExpenditureDr = BigDecimal.ZERO;
		BigDecimal finalPreviousSumExpenditureCr = BigDecimal.ZERO;
		BigDecimal totalClsBalance = BigDecimal.ZERO;
		BigDecimal sumOfDeficitAmount = BigDecimal.ZERO;

		List<Object[]> previousYearIncomeAmount = findPreviousYearIncome(Utility.dateToString(fromDates),
				transactionDate, orgId, langId);
		List<Object[]> previousYearExp = findPreviousYearexpenditure(Utility.dateToString(fromDates), transactionDate,
				orgId, langId);

		for (Object[] objects : previousYearIncomeAmount) {
			if (objects[2] != null) {
				finalPreviousSumIncomeCr = finalPreviousSumIncomeCr.add(new BigDecimal(objects[2].toString()));
			}
			if (objects[3] != null) {
				finalPreviousSumIncomeDr = finalPreviousSumIncomeDr.add(new BigDecimal(objects[3].toString()));
			}
		}
		for (Object[] objects : previousYearExp) {
			if (objects[2] != null) {
				finalPreviousSumExpenditureCr = finalPreviousSumExpenditureCr
						.add(new BigDecimal(objects[2].toString()));
			}
			if (objects[3] != null) {
				finalPreviousSumExpenditureDr = finalPreviousSumExpenditureDr
						.add(new BigDecimal(objects[3].toString()));
			}
		}

		BigDecimal finalPreviousIncomeTotalAmt = finalPreviousSumIncomeCr.subtract(finalPreviousSumIncomeDr);
		BigDecimal finalPreviousExpenditureTotalAmt = finalPreviousSumExpenditureDr
				.subtract(finalPreviousSumExpenditureCr);
		BigDecimal reverveAndSurpresPreviousAmount = finalPreviousIncomeTotalAmt
				.subtract(finalPreviousExpenditureTotalAmt);
		Long deptId = deparmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString());
		Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.SAD.toString(),
				AccountPrefix.TDP.toString(), orgId);
		final Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.CommonConstants.ACTIVE,
				AccountPrefix.ACN.toString(), orgId);
		VoucherTemplateMasterEntity template = voucherTemplateRepository.queryDefinedTemplate(voucherSubTypeId, deptId,
				orgId, status, null);
		Long newSacHeadId = null;
		int count = 0;
		if (template != null) {
			List<VoucherTemplateDetailEntity> listDetDetails = voucherTemplateRepository
					.queryDefinedTemplateDet(template.getTemplateId(), template.getOrgid());
			for (final VoucherTemplateDetailEntity detailTemplate : listDetDetails) {
				if (detailTemplate.getSacHeadId() != null && detailTemplate.getSacHeadId().longValue() != 0L) {
					newSacHeadId = detailTemplate.getSacHeadId();
					count++;
					break;
				}
			}
		} else {
			throw new NullPointerException(
					"VoucherTemplate not found in template For : " + voucherSubTypeId + " orgid : " + orgId);
		}
		if (count == 0) {
			throw new IllegalArgumentException(
					ApplicationSession.getInstance().getMessage("account.voucher.service.cpdid.paymode") + orgId);
		}
		String acHeadCode = "";
		if (newSacHeadId != null) {
			acHeadCode = secondaryheadMasterService.findByAccountHead(newSacHeadId);
		}

		// New Modification of BalanceSheet
		List<Object[]> listOfSum = accountFinancialReportRepository.getSumOfCreditAndSumOfDebitFromVoucher(fromDates,
				Utility.stringToDate(transactionDate), Long.valueOf(newSacHeadId), orgId);
		if (listOfSum != null && !listOfSum.isEmpty()) {
			for (Object[] listOfSums : listOfSum) {
				totalClsBalance = totalClsBalance.add(
						new BigDecimal(listOfSums[0].toString()).subtract(new BigDecimal(listOfSums[1].toString())));
			}
		}

		// totalClsBalance Is Negative
		if (totalClsBalance.signum() == -1) {
			sumOfDeficitAmount = sumOfDeficitAmount.add(reverveAndSurpresAmount).add(totalClsBalance);
		} else if (totalClsBalance.signum() == 1) {
			sumOfDeficitAmount = sumOfDeficitAmount.add(reverveAndSurpresAmount).add(totalClsBalance);
		}

		AccountFinancialReportDTO accountFinancialReportDTO = new AccountFinancialReportDTO();
		accountFinancialReportDTO.setAccountCode(acHeadCode);

		if (sumOfDeficitAmount != null) {
			accountFinancialReportDTO.setPreviousInTotal(sumOfDeficitAmount);
		}

		accountFinancialReportDTO.setPreviousExpTotal(reverveAndSurpresPreviousAmount);
		accountFinancialReportDTO.setAssetList(accountBalanceAssetList);
		accountFinancialReportDTO.setLiabilityList(accountBalanceLiabilityList);

		if (totalClosingAsset != null) {
			accountFinancialReportDTO.setSumTransactionCR(totalClosingAsset);
		}
		if (totalClosingAnOnAsset != null) {
			accountFinancialReportDTO.setSumTransactionDR(totalClosingAnOnAsset);
		}

		if (totalClosingliability != null) {
			accountFinancialReportDTO.setSumClosingCR(totalClosingliability.add(reverveAndSurpresAmount));
		}
		if (totalClosingAnOnliability != null) {
			accountFinancialReportDTO.setSumClosingDR(totalClosingAnOnliability);
		}
		accountFinancialReportDTO.setFromDate(transactionDate);
		model.addAttribute(REPORT_LIST, accountFinancialReportDTO);
	}

	private Long getSacHeadIdByBankAccountId(final Long bankAccountId, final Long orgId) {
		Long sacHeadId = null;
		try {
			sacHeadId = contraEntryVoucherRepository.getSacHeadIdByBankAccountId(bankAccountId, orgId);
		} catch (final NonUniqueResultException ex) {
			LOGGER.error("duplicate Account Head mapped against Bank Account[bankAccountId=" + bankAccountId + ",orgId="
					+ orgId + "]", ex);
			throw new IllegalArgumentException("duplicate Account Head mapped against Bank Account[bankAccountId="
					+ bankAccountId + ",orgId=" + orgId + "]", ex);
		}
		return sacHeadId;
	}

	/**
	 * this method is use for the find the cash flow report and following is the
	 * inputs
	 * 
	 * @param orgId
	 * @param toDate
	 * @param model
	 */
	/*As Per Samadhan Sir this is no functioning code */
	private void processCashFlowReport(long orgId, int langId, String toDate, ModelMap model) {
		// this is for current year report of cash flow start
		String accountHeads = ApplicationSession.getInstance().getMessage("account.head.code");
		String[] headList = accountHeads.split(",");
		AccountFinancialReportDTO reportDTO = new AccountFinancialReportDTO();
		Date toDates = Utility.stringToDate(toDate);
		Long financialYearId = tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(toDates);
		Date financialYFromDate = tbFinancialyearJpaRepository.getFromDateFromFinancialYearIdByPassingDate(toDates);
		String fromDate = Utility.dateToString(financialYFromDate);
		List<Object[]> incomeRecord = queryReportDataFromViewIncome(fromDate, toDate, orgId, langId);
		List<Object[]> ExpenditureRecord = queryReportDataFromViewExpenditure(fromDate, toDate, orgId, langId);
		BigDecimal totalIncome = BigDecimal.ZERO;
		if (incomeRecord != null && !incomeRecord.isEmpty()) {
			totalIncome = incomeRecord.stream().filter(r -> r != null)
					.map(r -> new BigDecimal(r[2].toString()).subtract(new BigDecimal(r[3].toString())))
					.reduce(BigDecimal::add).get();
		}
		BigDecimal totalExpance = BigDecimal.ZERO;
		if (ExpenditureRecord != null && !ExpenditureRecord.isEmpty()) {
			totalExpance = ExpenditureRecord.stream().filter(r -> r != null)
					.map(r -> new BigDecimal(r[3].toString()).subtract(new BigDecimal(r[2].toString())))
					.reduce(BigDecimal::add).get();
		}
		reportDTO.setCurrentYearAmount(totalIncome.subtract(totalExpance).setScale(2, RoundingMode.CEILING));
		List<BigDecimal> amounts = new ArrayList<>();
		int checks = -1;
		for (String head : headList) {
			head = head.replace("|", ",");
			List<Object[]> cashFlowList = accountFinancialReportRepository.getCashFlowReport(orgId, financialYFromDate,
					toDates, head, financialYearId);
			checks++;
			if (checks <= 6) {
				BigDecimal amount = calculateAmountOpeningblnc(cashFlowList);
				amounts.add(amount);
			} else if (checks > 6 && checks <= 12) {
				BigDecimal amount = calculateAmountClosingBlnc(cashFlowList);
				amounts.add(amount);
			} else {
				cashFlowList.stream().forEach(l -> {
					amounts.add((BigDecimal) l[3]);// TRN_TOTAL_CR
					amounts.add((BigDecimal) l[2]);// TRN_TOTAL_DR
				});
			}
		}
		final long cpdIdPayMode = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CASH.getValue(),
				AccountPrefix.PAY.toString(), orgId);
		Long sacHeadId = accountFinancialReportRepository.findSacHeadIdByPayModeId(cpdIdPayMode, orgId);
		// opening balance cash and bank start
		BigDecimal openingCash = calculateOpeningBalanceAsOnDate(sacHeadId, orgId, fromDate);
		List<LookUp> accountHeadList = secondaryheadMasterService.findAccountHeadsByOrgIdBankBook(orgId);
		List<Long> headLists = accountHeadList.stream().map(LookUp::getLookUpId).collect(Collectors.toList());
		BigDecimal bankAmount  = calculateOpeningBalanceAsOnDateAndAccHeads(headLists, orgId, fromDate);
	
		reportDTO.setOpeningBalance(bankAmount.add(openingCash));
		BigDecimal closingbnkamount =  calculateClosingBalanceAsOnDateAndAccHeads(headLists, orgId, toDate);
			
		reportDTO.setBankBalance(closingbnkamount);
		BigDecimal closingCashAmt = calculateClosingBalanceAsOnDate(sacHeadId, orgId, toDate);
		reportDTO.setCashBalance(closingCashAmt);
		reportDTO.setAmounts(amounts);
		// this is for current year report of cash flow end

		// this is for previous year report of cash flow start
		Date previousYDate = getPreviousYearfromDateAndTodate(toDate);
		String pTodate = Utility.dateToString(previousYDate);
		Long financialPYearId = tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(previousYDate);
		Date financialPYFromDate = tbFinancialyearJpaRepository
				.getFromDateFromFinancialYearIdByPassingDate(previousYDate);
		String fromPDate = Utility.dateToString(financialPYFromDate);

		List<Object[]> incomeRecordP = queryReportDataFromViewIncome(fromPDate, pTodate, orgId, langId);
		List<Object[]> ExpenditureRecordP = queryReportDataFromViewExpenditure(fromPDate, pTodate, orgId, langId);
		BigDecimal totalIncomeP = BigDecimal.ZERO;
		if (incomeRecordP != null && !incomeRecordP.isEmpty()) {
			totalIncomeP = incomeRecordP.stream().filter(r -> r != null)
					.map(r -> new BigDecimal(r[2].toString()).subtract(new BigDecimal(r[3].toString())))
					.reduce(BigDecimal::add).get();
		}
		BigDecimal totalExpanceP = BigDecimal.ZERO;
		if (ExpenditureRecordP != null && !ExpenditureRecordP.isEmpty()) {
			totalExpanceP = ExpenditureRecordP.stream().filter(r -> r != null)
					.map(r -> new BigDecimal(r[3].toString()).subtract(new BigDecimal(r[2].toString())))
					.reduce(BigDecimal::add).get();
		}
		reportDTO.setPreviousIncome(totalIncomeP.subtract(totalExpanceP).setScale(2, RoundingMode.CEILING));

		List<BigDecimal> amountsP = new ArrayList<>();
		int checksP = -1;
		for (String head : headList) {
			head = head.replace("|", ",");
			List<Object[]> cashFlowList = accountFinancialReportRepository.getCashFlowReport(orgId, financialPYFromDate,
					previousYDate, head, financialPYearId);
			checksP++;
			if (checksP <= 6) {
				BigDecimal amount = calculateAmountOpeningblnc(cashFlowList);
				amountsP.add(amount);
			} else if (checksP > 6 && checksP <= 12) {
				BigDecimal amount = calculateAmountClosingBlnc(cashFlowList);
				amountsP.add(amount);
			} else {
				cashFlowList.stream().forEach(l -> {
					amountsP.add((BigDecimal) l[3]);// TRN_TOTAL_CR
					amountsP.add((BigDecimal) l[2]);// TRN_TOTAL_DR
				});
			}
		}
		// opening balance cash and bank start
		BigDecimal openingCashP = calculateOpeningBalanceAsOnDate(sacHeadId, orgId, fromPDate);
		BigDecimal bankAmountP =  calculateOpeningBalanceAsOnDateAndAccHeads(headLists, orgId, fromDate);
		
		reportDTO.setOpenblancePreous(bankAmountP.add(openingCashP));
		BigDecimal closingbnkamountP = calculateClosingBalanceAsOnDateAndAccHeads(headLists, orgId, pTodate);
		reportDTO.setBankBalanceP(closingbnkamountP);
		BigDecimal closingCashAmtP = calculateClosingBalanceAsOnDate(sacHeadId, orgId, pTodate);
		reportDTO.setCashBalanceP(closingCashAmtP);
		reportDTO.setPrevusAmt(amountsP);
		// this is for previous year report of cash flow end
		//reportDTO.setReceipstDate(financialYFromDate);
		model.addAttribute(REPORT_LIST, reportDTO);

	}

	private BigDecimal calculateAmountOpeningblnc(List<Object[]> cashFlowList) {
		BigDecimal openingblanc = cashFlowList.stream()
				.map(l -> (new BigDecimal(l[0].toString()).subtract(new BigDecimal(l[1].toString())))
						.add(new BigDecimal(l[2].toString()).subtract(new BigDecimal(l[3].toString()))))
				.reduce(BigDecimal::add).get();
		return openingblanc;
	}

	private BigDecimal calculateAmountClosingBlnc(List<Object[]> cashFlowList) {
		BigDecimal closingblanc = cashFlowList.stream()
				.map(l -> (new BigDecimal(l[1].toString()).subtract(new BigDecimal(l[0].toString())))
						.add(new BigDecimal(l[3].toString()).subtract(new BigDecimal(l[2].toString()))))
				.reduce(BigDecimal::add).get();
		return closingblanc;
	}

	/**
	 * this method gives the previous year date
	 * 
	 * @param date
	 * @return
	 */
	private Date getPreviousYearfromDateAndTodate(String date) {
		Date toDate = Utility.stringToDate(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(toDate);
		cal.add(Calendar.YEAR, -1);
		Date previousYearDate = cal.getTime();
		return previousYearDate;

	}

	/***********************************************
	 * Receipt And Payment
	 *****************************************************************/

	private void processPaymentAndReceiptReport1(ModelMap model, String fromDate, String toDate, String reportTypeCode,
			long orgId, Long accountHeadId) {
		BigDecimal cashamount1 = new BigDecimal(0.00);
		BigDecimal cashamount2 = new BigDecimal(0.00);
		BigDecimal cashamount3 = new BigDecimal(0.00);
		BigDecimal cashamount4 = new BigDecimal(0.00);
		BigDecimal combineOpenBalGet = new BigDecimal(0.00);
		BigDecimal previousCombineOpenBalGet = new BigDecimal(0.00);
		BigDecimal combineOpenBal = new BigDecimal(0.00);
		BigDecimal previousCombineOpenBal = new BigDecimal(0.00);
		BigDecimal openingCash = new BigDecimal(0.00);
		BigDecimal previousOpeningCash = new BigDecimal(0.00);
		BigDecimal closingOfBankGet = new BigDecimal(0.00);
		BigDecimal previousClosingOfBankGet = new BigDecimal(0.00);
		BigDecimal closingOfBank = new BigDecimal(0.00);
		BigDecimal previousClosingOfBank = new BigDecimal(0.00);
		BigDecimal closingCashAmt = new BigDecimal(0.00);
		BigDecimal previousClosingCashAmt = new BigDecimal(0.00);
		BigDecimal sumBudgetAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumAcutualOpenReceiptAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumAcutualNonOpenReceiptAmount = new BigDecimal(
				MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumBudgetAmountPayment = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumAcutualAmountOpenPayment = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumAcutualOpenPreviousReceiptAmount = new BigDecimal(
				MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumAcutualNonOpenPreviousReceiptAmount = new BigDecimal(
				MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumBudgetAmountPreviousPayment = new BigDecimal(
				MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumAcutualAmountOpenPreviousPayment = new BigDecimal(
				MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumAcutualAmountNonOpenPayment = new BigDecimal(
				MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumAcutualAmountPaymentBalance = new BigDecimal(
				MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumbalanceRecoverable = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumbalanceOfTillDate = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumbalanceOfPayTilDate = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		List<Object[]> openingPaymentSideLists = null;
		List<Object[]> nonOpeningPaymentSideLists = null;
		List<Object[]> operatingReceiptSideLists = null;
		List<Object[]> nonOperatingReceiptSideLists = null;
		List<Object[]> openingPreviousPaymentSideLists = null;
		List<Object[]> nonOpeningPreviousPaymentSideLists = null;
		List<Object[]> operatingPreviousReceiptSideLists = null;
		List<Object[]> nonOperatingPreviousReceiptSideLists = null;
		final Date frmDate = Utility.stringToDate(fromDate);
		final Date tDate = Utility.stringToDate(toDate);
		final long cpdIdPayMode = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CASH.getValue(),
				AccountPrefix.PAY.toString(), orgId);
		List<LookUp> accountHeadList = secondaryheadMasterService.findAccountHeadsByOrgIdBankBook(orgId);
		if (accountHeadList != null && !accountHeadList.isEmpty()) {
			for (LookUp accountHeadLists : accountHeadList) {
				combineOpenBalGet = calculateOpeningBalanceAsOnDate(accountHeadLists.getLookUpId(), orgId, fromDate);
				if (combineOpenBalGet != null) {
					combineOpenBal = combineOpenBal.add(combineOpenBalGet);
				}
				closingOfBankGet = calculateClosingBalanceAsOnDate(accountHeadLists.getLookUpId(), orgId, toDate);
				if (closingOfBankGet != null) {
					closingOfBank = closingOfBank.add(closingOfBankGet);
				}
				previousCombineOpenBalGet = calculateOpeningBalanceAsOnPreviousDate(accountHeadLists.getLookUpId(),
						orgId, fromDate);
				if (previousCombineOpenBalGet != null) {
					previousCombineOpenBal = previousCombineOpenBal.add(previousCombineOpenBalGet);
				}
				previousClosingOfBankGet = calculateClosingBalanceAsOnPreviousDate(accountHeadLists.getLookUpId(),
						orgId, toDate);
				if (previousClosingOfBankGet != null) {
					previousClosingOfBank = previousClosingOfBank.add(previousClosingOfBankGet);
				}
			}
		}
		long finanacialYearId = queryToFindFinanacialYearID(frmDate);
		final Long param1 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.AHP.getValue(),
				PrefixConstants.CMD, orgId);
		final Long param2 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.AHS.getValue(),
				PrefixConstants.CMD, orgId);
		List<LookUp> levelHead = acCodingstructureMasService.queryAccountHeadByChartOfAccount(param1, param2, orgId);
		if (levelHead != null && !levelHead.isEmpty()) {
			if (levelHead.get(0).getLookUpId() == accountHeadId) { // major head
				operatingReceiptSideLists = queryClassifiedBudgetReceiptSideOpeningReportDataL3(frmDate, tDate, orgId,
						accountHeadId);
				nonOperatingReceiptSideLists = queryClassifiedBudgetReceiptSideNonOpeningReportDataL3(frmDate, tDate,
						orgId, accountHeadId);
				openingPaymentSideLists = queryClassifiedBudgetPaymentSideOpeningReportDataL3(frmDate, tDate, orgId,
						accountHeadId);
				nonOpeningPaymentSideLists = queryClassifiedBudgetPaymentSideNonOpeningReportDataL3(frmDate, tDate,
						orgId, accountHeadId);

				operatingPreviousReceiptSideLists = queryClassifiedBudgetReceiptSideOpeningPreviousReportDataL3(frmDate,
						tDate, orgId, accountHeadId);
				nonOperatingPreviousReceiptSideLists = queryClassifiedBudgetReceiptSideNonOpeningPreviousReportDataL3(
						frmDate, tDate, orgId, accountHeadId);
				openingPreviousPaymentSideLists = queryClassifiedBudgetPaymentSideOpeningPreviousReportDataL3(frmDate,
						tDate, orgId, accountHeadId);
				nonOpeningPreviousPaymentSideLists = queryClassifiedBudgetPaymentSideNonOpeningPreviousReportDataL3(
						frmDate, tDate, orgId, accountHeadId);

			} else if (levelHead.get(1).getLookUpId() == accountHeadId) { // minor head,
				operatingReceiptSideLists = queryClassifiedBudgetReceiptSideOpeningReportDataL1(frmDate, tDate, orgId,
						accountHeadId);
				nonOperatingReceiptSideLists = queryClassifiedBudgetReceiptSideNonOpeningReportDataL1(frmDate, tDate,
						orgId, accountHeadId);
				openingPaymentSideLists = queryClassifiedBudgetPaymentSideOpeningReportDataL1(frmDate, tDate, orgId,
						accountHeadId);
				nonOpeningPaymentSideLists = queryClassifiedBudgetPaymentSideNonOpeningReportDataL1(frmDate, tDate,
						orgId, accountHeadId);
				operatingPreviousReceiptSideLists = queryClassifiedBudgetReceiptSideOpeningPreviousReportDataL2(frmDate,
						tDate, orgId, accountHeadId);
				nonOperatingPreviousReceiptSideLists = queryClassifiedBudgetReceiptSideNonOpeningPreviousReportDataL2(
						frmDate, tDate, orgId, accountHeadId);
				openingPreviousPaymentSideLists = queryClassifiedBudgetPaymentSideOpeningPreviousReportDataL2(frmDate,
						tDate, orgId, accountHeadId);
				nonOpeningPreviousPaymentSideLists = queryClassifiedBudgetPaymentSideNonOpeningPreviousReportDataL2(
						frmDate, tDate, orgId, accountHeadId);

			} else if (levelHead.get(2).getLookUpId() == accountHeadId) {// secondary head
				operatingReceiptSideLists = queryClassifiedBudgetReceiptSideOpeningData(frmDate, tDate, orgId,
						accountHeadId);
				nonOperatingReceiptSideLists = queryClassifiedBudgetReceiptSideNonOpeningData(frmDate, tDate, orgId,
						accountHeadId);
				openingPaymentSideLists = queryClassifiedBudgetPaymentSideOpeningReportData(frmDate, tDate, orgId,
						accountHeadId);
				nonOpeningPaymentSideLists = queryClassifiedBudgetPaymentSideNonOpeningReportData(frmDate, tDate, orgId,
						accountHeadId);
				operatingPreviousReceiptSideLists = queryClassifiedBudgetReceiptSideOpeningData(frmDate, tDate, orgId,
						accountHeadId);
				nonOperatingPreviousReceiptSideLists = queryClassifiedBudgetReceiptSideNonOpeningData(frmDate, tDate,
						orgId, accountHeadId);
				openingPreviousPaymentSideLists = queryClassifiedBudgetPaymentSideOpeningReportData(frmDate, tDate,
						orgId, accountHeadId);
				nonOpeningPreviousPaymentSideLists = queryClassifiedBudgetPaymentSideNonOpeningReportData(frmDate,
						tDate, orgId, accountHeadId);
			}
		}

		final AccountFinancialReportDTO openingReceiptBean = new AccountFinancialReportDTO();
		final AccountFinancialReportDTO nonOpeningReceiptBean = new AccountFinancialReportDTO();
		final AccountFinancialReportDTO openingPaymentBean = new AccountFinancialReportDTO();
		final AccountFinancialReportDTO nonOpeningPaymentBean = new AccountFinancialReportDTO();
		if (isReceiptPaymentRecordsFound(operatingReceiptSideLists, nonOperatingReceiptSideLists,
				openingPaymentSideLists, nonOpeningPaymentSideLists)) {
			final AccountFinancialReportDTO dto = new AccountFinancialReportDTO();
			dto.setFromDate(fromDate);
			dto.setToDate(toDate);
			List<Object[]> cashSacHeadIdList = (List<Object[]>) accountFinancialReportRepository
					.findSacHeadIdByCashPayModeId(orgId);
			if (cashSacHeadIdList != null && !cashSacHeadIdList.isEmpty()) {
				for (Object accountHeadLists : cashSacHeadIdList) {
					Long sacHeadId1 = Long.valueOf(accountHeadLists.toString());
					if (sacHeadId1 != null) {
						cashamount1 = calculateOpeningBalanceAsOnDate(sacHeadId1, orgId, fromDate);
						if (cashamount1 != null) {
							openingCash = openingCash.add(cashamount1);
						}
						cashamount2 = calculateClosingBalanceAsOnDate(sacHeadId1, orgId, toDate);
						if (cashamount2 != null) {
							closingCashAmt = closingCashAmt.add(cashamount2);
						}

						cashamount3 = calculateOpeningBalanceAsOnPreviousDate(sacHeadId1, orgId, fromDate);
						if (cashamount3 != null) {
							previousOpeningCash = previousOpeningCash.add(cashamount3);
						}
						cashamount4 = calculateClosingBalanceAsOnPreviousDate(sacHeadId1, orgId, toDate);
						if (cashamount4 != null) {
							previousClosingCashAmt = previousClosingCashAmt.add(cashamount4);
						}

					}
				}
			}
			if (combineOpenBal != null) {
				dto.setBankBalance(combineOpenBal);
			}
			if (closingOfBank != null) {
				dto.setClosingBankAmount(closingOfBank.toString());
			}

			if (openingCash != null) {
				dto.setOpeningBalance(openingCash);
			}
			if (closingCashAmt != null) {
				dto.setClosingBalance(closingCashAmt);
			}

			if (previousCombineOpenBal != null) {
				dto.setPreviousOpeningBankAmt(previousCombineOpenBal);
			} else {
				dto.setPreviousOpeningBankAmt(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT));
			}
			if (previousOpeningCash != null) {
				dto.setPreviousOpeningCashAmt(previousOpeningCash);
			} else {
				dto.setPreviousOpeningCashAmt(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT));
			}
			if (previousClosingOfBank != null) {
				dto.setPreviousClosingBankAmt(previousClosingOfBank);
			} else {
				dto.setPreviousClosingBankAmt(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT));
			}
			if (previousClosingCashAmt != null) {
				dto.setPreviousClosingCashAmt(previousClosingCashAmt);
			} else {
				dto.setPreviousClosingCashAmt(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT));
			}

			BigDecimal totalOpenCashBankAmount = new BigDecimal(0.00);
			if (openingCash != null && combineOpenBal != null) {
				totalOpenCashBankAmount = openingCash.add(combineOpenBal);
				dto.setOpeningBalanceIndianCurrency(
						(totalOpenCashBankAmount.setScale(2, RoundingMode.HALF_EVEN)).toString());
			}
			BigDecimal totalOpenPreviousCashBankAmount = new BigDecimal(0.00);
			if (previousOpeningCash != null && previousCombineOpenBal != null) {
				totalOpenPreviousCashBankAmount = previousOpeningCash.add(previousCombineOpenBal);
				dto.setTotalBalance((totalOpenPreviousCashBankAmount.setScale(2, RoundingMode.HALF_EVEN)).toString());
			}
			BigDecimal totalClosingCashAmt = new BigDecimal(0.00);
			if (closingCashAmt != null && closingOfBank != null) {
				totalClosingCashAmt = closingCashAmt.add(closingOfBank);
				dto.setClosingCash((totalClosingCashAmt.setScale(2, RoundingMode.HALF_EVEN)).toString());
			}
			BigDecimal totalClosingPreviousCashAmt = new BigDecimal(0.00);
			if (previousClosingCashAmt != null && previousClosingOfBank != null) {
				totalClosingPreviousCashAmt = previousClosingCashAmt.add(previousClosingOfBank);
				dto.setClosingAmt((totalClosingPreviousCashAmt.setScale(2, RoundingMode.HALF_EVEN)).toString());
			}
			BigDecimal sumOfReceiptsGrandTotalAmt = BigDecimal.ZERO;
			BigDecimal sumOfPaymentsGrandTotalAmt = BigDecimal.ZERO;
			BigDecimal sumOfReceiptsPreviousGrandTotalAmt = BigDecimal.ZERO;
			BigDecimal sumOfPaymentsPreviousGrandTotalAmt = BigDecimal.ZERO;
			// opening balance receipt of cash mode and bank also end
			final List<AccountFinancialReportDTO> openinglist = new ArrayList<>();
			for (Object[] objectListTillYear : operatingReceiptSideLists) {
				final AccountFinancialReportDTO openingDTO = new AccountFinancialReportDTO();
				if (objectListTillYear[0] != null) {
					openingDTO.setAccountCode(objectListTillYear[0].toString());
					if (operatingPreviousReceiptSideLists != null) {
						for (Object[] objectListPreviousYear : operatingPreviousReceiptSideLists) {
							if (objectListPreviousYear[0] != null) {
								if (objectListTillYear[0].equals(objectListPreviousYear[0])) {
									if (objectListPreviousYear[2] != null) {
										sumAcutualOpenPreviousReceiptAmount = sumAcutualOpenPreviousReceiptAmount
												.add(new BigDecimal(objectListPreviousYear[2].toString()));
										openingDTO.setBalanceRecoverableIndianCurrency(
												CommonMasterUtility.getAmountInIndianCurrency(
														new BigDecimal(objectListPreviousYear[2].toString())));
									}
								}
							}
						}
					}
				}
				if (objectListTillYear[1] != null) {
					openingDTO.setAccountHead(objectListTillYear[1].toString());
				}
				if (objectListTillYear[2] == null) {
					openingDTO.setActualAmountReceived(new BigDecimal(0.00));
					openingDTO.setActualAmountReceivedIndianCurrency(
							CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(0.00)));
					sumAcutualOpenReceiptAmount = sumAcutualOpenReceiptAmount.add(new BigDecimal(0.00));
				} else {
					openingDTO.setActualAmountReceived(new BigDecimal(objectListTillYear[2].toString()));
					openingDTO.setActualAmountReceivedIndianCurrency(CommonMasterUtility
							.getAmountInIndianCurrency(new BigDecimal(objectListTillYear[2].toString())));
					sumAcutualOpenReceiptAmount = sumAcutualOpenReceiptAmount
							.add(new BigDecimal(objectListTillYear[2].toString()));
				}
				openinglist.add(openingDTO);
			}
			if (sumbalanceOfTillDate != null) {
				openingReceiptBean.setBalanceAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumbalanceOfTillDate));
			}
			if (sumbalanceRecoverable != null) {
				openingReceiptBean.setSumbalanceRecoverableIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumbalanceRecoverable));
			}
			if (sumBudgetAmount != null) {
				openingReceiptBean.setSumBudgetAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumBudgetAmount));
			}
			if (sumAcutualOpenReceiptAmount != null) {
				sumOfReceiptsGrandTotalAmt = sumOfReceiptsGrandTotalAmt.add(sumAcutualOpenReceiptAmount);
				openingReceiptBean.setSumAcutualAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumAcutualOpenReceiptAmount));
			}
			if (sumAcutualOpenPreviousReceiptAmount != null) {
				sumOfReceiptsPreviousGrandTotalAmt = sumOfReceiptsPreviousGrandTotalAmt
						.add(sumAcutualOpenPreviousReceiptAmount);
				// openingReceiptBean.setSubTotalAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(sumAcutualOpenPreviousReceiptAmount));
			}
			openingReceiptBean.setListOfSum(openinglist);

			// non opening receipt balance of cash mode and bank also end
			final List<AccountFinancialReportDTO> nonOpeningList = new ArrayList<>();
			for (Object[] objectListTillYear : nonOperatingReceiptSideLists) {
				final AccountFinancialReportDTO nonOpeningDTO = new AccountFinancialReportDTO();

				if (objectListTillYear[0] != null) {
					nonOpeningDTO.setAccountCode(objectListTillYear[0].toString());
					if (nonOperatingPreviousReceiptSideLists != null) {
						for (Object[] objectListnonOperatingPreviousYear : nonOperatingPreviousReceiptSideLists) {
							if (objectListnonOperatingPreviousYear[0] != null) {
								if (objectListTillYear[0].equals(objectListnonOperatingPreviousYear[0])) {
									if (objectListnonOperatingPreviousYear[2] != null) {
										sumAcutualNonOpenPreviousReceiptAmount = sumAcutualNonOpenPreviousReceiptAmount
												.add(new BigDecimal(objectListnonOperatingPreviousYear[2].toString()));
										nonOpeningDTO.setBalanceRecoverableIndianCurrency(
												CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(
														objectListnonOperatingPreviousYear[2].toString())));
									}
								}
							}
						}
					}
				}
				if (objectListTillYear[1] != null) {
					nonOpeningDTO.setAccountHead(objectListTillYear[1].toString());
				}
				if (objectListTillYear[2] == null) {
					nonOpeningDTO.setActualAmountReceived(new BigDecimal(0.00));
					nonOpeningDTO.setActualAmountReceivedIndianCurrency(
							CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(0.00)));
					sumAcutualNonOpenReceiptAmount = sumAcutualNonOpenReceiptAmount.add(new BigDecimal(0.00));
				} else {
					nonOpeningDTO.setActualAmountReceived(new BigDecimal(objectListTillYear[2].toString()));
					nonOpeningDTO.setActualAmountReceivedIndianCurrency(CommonMasterUtility
							.getAmountInIndianCurrency(new BigDecimal(objectListTillYear[2].toString())));
					sumAcutualNonOpenReceiptAmount = sumAcutualNonOpenReceiptAmount
							.add(new BigDecimal(objectListTillYear[2].toString()));
				}
				nonOpeningList.add(nonOpeningDTO);
			}
			if (sumbalanceOfTillDate != null) {
				nonOpeningReceiptBean.setBalanceAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumbalanceOfTillDate));
			}
			if (sumbalanceRecoverable != null) {
				nonOpeningReceiptBean.setSumbalanceRecoverableIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumbalanceRecoverable));
			}
			if (sumBudgetAmount != null) {
				nonOpeningReceiptBean.setSumBudgetAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumBudgetAmount));
			}
			if (sumAcutualNonOpenReceiptAmount != null) {
				sumOfReceiptsGrandTotalAmt = sumOfReceiptsGrandTotalAmt.add(sumAcutualNonOpenReceiptAmount);
				nonOpeningReceiptBean.setSumAcutualAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumAcutualNonOpenReceiptAmount));
			}
			if (sumAcutualNonOpenPreviousReceiptAmount != null) {
				sumOfReceiptsPreviousGrandTotalAmt = sumOfReceiptsPreviousGrandTotalAmt
						.add(sumAcutualNonOpenPreviousReceiptAmount);
				// openingReceiptBean.setSubTotalAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(sumAcutualOpenPreviousReceiptAmount));
			}
			nonOpeningReceiptBean.setListOfSum(nonOpeningList);

			BigDecimal sumOfReceiptsGrandTotalAmount = sumOfReceiptsGrandTotalAmt.add(totalOpenCashBankAmount);
			BigDecimal sumOfReceiptsPreviousGrandTotalAmount = sumOfReceiptsPreviousGrandTotalAmt
					.add(totalOpenPreviousCashBankAmount);
			dto.setActualAmountReceivedIndianCurrency(
					CommonMasterUtility.getAmountInIndianCurrency(sumOfReceiptsGrandTotalAmount));
			dto.setChequeAmountIndianCurrency(
					CommonMasterUtility.getAmountInIndianCurrency(sumOfReceiptsPreviousGrandTotalAmount));

			// opening balance payment of cash mode and bank also end
			final List<AccountFinancialReportDTO> openingPaymentlist = new ArrayList<>();
			if (openingPaymentSideLists != null && !openingPaymentSideLists.isEmpty()) {
				for (Object[] objectListPaymentTillDate : openingPaymentSideLists) {
					final AccountFinancialReportDTO openingPayDTO = new AccountFinancialReportDTO();
					if (objectListPaymentTillDate[0] != null) {
						openingPayDTO.setAccountCode(objectListPaymentTillDate[0].toString());
						if (openingPreviousPaymentSideLists != null) {
							for (Object[] objectListPaymentPreviousYear : openingPreviousPaymentSideLists) {
								if (objectListPaymentPreviousYear[0] != null) {
									if (objectListPaymentTillDate[0].equals(objectListPaymentPreviousYear[0])) {
										if (objectListPaymentPreviousYear[2] != null) {
											sumBudgetAmountPreviousPayment = sumBudgetAmountPreviousPayment
													.add(new BigDecimal(objectListPaymentPreviousYear[2].toString()));
											openingPayDTO.setBalanceRecoverableIndianCurrency(
													CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(
															objectListPaymentPreviousYear[2].toString())));
										}
									}
								}
							}
						}
					}
					if (objectListPaymentTillDate[1] != null) {
						openingPayDTO.setAccountHead(objectListPaymentTillDate[1].toString());
					}
					if (objectListPaymentTillDate[2] == null) {
						openingPayDTO.setActualAmountReceived(new BigDecimal(0.00));
						openingPayDTO.setActualAmountReceivedIndianCurrency(
								CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(0.00)));
						sumAcutualAmountOpenPayment = sumAcutualAmountOpenPayment.add(new BigDecimal(0.00));
					} else {
						openingPayDTO.setActualAmountReceived((BigDecimal) objectListPaymentTillDate[2]);
						openingPayDTO.setActualAmountReceivedIndianCurrency(CommonMasterUtility
								.getAmountInIndianCurrency((BigDecimal) objectListPaymentTillDate[2]));
						sumAcutualAmountOpenPayment = sumAcutualAmountOpenPayment
								.add((BigDecimal) objectListPaymentTillDate[2]);
					}
					openingPaymentlist.add(openingPayDTO);

				}
			}
			if (sumBudgetAmountPayment != null) {
				openingPaymentBean.setSumBudgetAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumBudgetAmountPayment));
			}
			if (sumAcutualAmountOpenPayment != null) {
				sumOfPaymentsGrandTotalAmt = sumOfPaymentsGrandTotalAmt.add(sumAcutualAmountOpenPayment);
				openingPaymentBean.setSumAcutualAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumAcutualAmountOpenPayment));
			}
			if (sumAcutualAmountPaymentBalance != null) {
				openingPaymentBean.setSubTotalAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumAcutualAmountPaymentBalance));
			}
			if (sumbalanceOfPayTilDate != null) {
				openingPaymentBean.setTotalDepositIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumbalanceOfPayTilDate));
			}
			if (sumBudgetAmountPreviousPayment != null) {
				sumOfPaymentsPreviousGrandTotalAmt = sumOfPaymentsPreviousGrandTotalAmt
						.add(sumBudgetAmountPreviousPayment);
				// openingPaymentBean.setSumAcutualAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(sumAcutualAmountOpenPayment));
			}
			openingPaymentBean.setListOfSum(openingPaymentlist);

			// non opening balance payment of cash mode and bank also end
			final List<AccountFinancialReportDTO> nonOpenPaylist = new ArrayList<>();
			if (nonOpeningPaymentSideLists != null && !nonOpeningPaymentSideLists.isEmpty()) {
				for (Object[] objectListPaymentTillDate : nonOpeningPaymentSideLists) {
					final AccountFinancialReportDTO nonOpenPayDTO = new AccountFinancialReportDTO();
					if (objectListPaymentTillDate[0] != null) {
						nonOpenPayDTO.setAccountCode(objectListPaymentTillDate[0].toString());
						if (nonOpeningPreviousPaymentSideLists != null) {
							for (Object[] objectListNonPaymentPreviousYear : nonOpeningPreviousPaymentSideLists) {
								if (objectListNonPaymentPreviousYear[0] != null) {
									if (objectListPaymentTillDate[0].equals(objectListNonPaymentPreviousYear[0])) {
										if (objectListNonPaymentPreviousYear[2] != null) {
											sumAcutualAmountOpenPreviousPayment = sumAcutualAmountOpenPreviousPayment
													.add(new BigDecimal(
															objectListNonPaymentPreviousYear[2].toString()));
											nonOpenPayDTO.setBalanceRecoverableIndianCurrency(
													CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(
															objectListNonPaymentPreviousYear[2].toString())));
										}
									}
								}
							}
						}
					}
					if (objectListPaymentTillDate[1] != null) {
						nonOpenPayDTO.setAccountHead(objectListPaymentTillDate[1].toString());
					}
					if (objectListPaymentTillDate[2] == null) {
						nonOpenPayDTO.setActualAmountReceived(new BigDecimal(0.00));
						nonOpenPayDTO.setActualAmountReceivedIndianCurrency(
								CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(0.00)));
						sumAcutualAmountNonOpenPayment = sumAcutualAmountNonOpenPayment.add(new BigDecimal(0.00));
					} else {
						nonOpenPayDTO.setActualAmountReceived((BigDecimal) objectListPaymentTillDate[2]);
						nonOpenPayDTO.setActualAmountReceivedIndianCurrency(CommonMasterUtility
								.getAmountInIndianCurrency((BigDecimal) objectListPaymentTillDate[2]));
						sumAcutualAmountNonOpenPayment = sumAcutualAmountNonOpenPayment
								.add((BigDecimal) objectListPaymentTillDate[2]);
					}
					nonOpenPaylist.add(nonOpenPayDTO);
				}
			}
			if (sumBudgetAmountPayment != null) {
				nonOpeningPaymentBean.setSumBudgetAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumBudgetAmountPayment));
			}
			if (sumAcutualAmountNonOpenPayment != null) {
				sumOfPaymentsGrandTotalAmt = sumOfPaymentsGrandTotalAmt.add(sumAcutualAmountNonOpenPayment);
				nonOpeningPaymentBean.setSumAcutualAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumAcutualAmountNonOpenPayment));
			}
			if (sumAcutualAmountPaymentBalance != null) {
				nonOpeningPaymentBean.setSubTotalAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumAcutualAmountPaymentBalance));
			}
			if (sumbalanceOfPayTilDate != null) {
				nonOpeningPaymentBean.setTotalDepositIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(sumbalanceOfPayTilDate));
			}
			if (sumAcutualAmountOpenPreviousPayment != null) {
				sumOfPaymentsPreviousGrandTotalAmt = sumOfPaymentsPreviousGrandTotalAmt
						.add(sumAcutualAmountOpenPreviousPayment);
				// nonOpeningPaymentBean.setSumAcutualAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(sumAcutualAmountNonOpenPayment));
			}
			nonOpeningPaymentBean.setListOfSum(nonOpenPaylist);

			BigDecimal sumOfPaymentsGrandTotalAmount = sumOfPaymentsGrandTotalAmt.add(totalClosingCashAmt);
			BigDecimal sumOfPaymentsPreviousGrandTotalAmount = sumOfPaymentsPreviousGrandTotalAmt
					.add(totalClosingPreviousCashAmt);
			dto.setSumbalanceRecoverableIndianCurrency(
					CommonMasterUtility.getAmountInIndianCurrency(sumOfPaymentsGrandTotalAmount));
			dto.setChequeDepositIndianCurrency(
					CommonMasterUtility.getAmountInIndianCurrency(sumOfPaymentsPreviousGrandTotalAmount));

			model.addAttribute(MainetConstants.AccountFinancialReport.OPENING_RECEIPT_SIDE_LIST, openingReceiptBean);
			model.addAttribute(AccountFinancialReport.NON_OPENING_RECEIPT_SIDE_LIST, nonOpeningReceiptBean);
			model.addAttribute(MainetConstants.AccountFinancialReport.OPENING_PAYMENT_SIDE_LIST, openingPaymentBean);
			model.addAttribute(AccountFinancialReport.NON_OPENING_PAYMENT_SIDE_LIST, nonOpeningPaymentBean);
			model.addAttribute(REPORT_DATA, dto);
		}

		else

		{
			model.addAttribute(VALIDATION_ERROR, AccountConstants.Y.getValue());
			LOGGER.error("No Records found for Classified Budget Report for [frmDate=" + frmDate + ",tDate=" + tDate
					+ "orgId=" + orgId);
		}

	}

	@Override
	public AccountFinancialReportDTO getInterBankTransactionsReportData(String fromDateId, String toDateId,
			Long orgId) {
		List<Object[]> listOfInterBankData = accountFinancialReportRepository
				.getInterBankReportDataBy(Utility.stringToDate(fromDateId), Utility.stringToDate(toDateId), orgId);
		final Map<Long, String> bankAccountlistdto = accountChequeDishonourService.getBankAccountData(orgId);
		final AccountFinancialReportDTO interBankReportData = new AccountFinancialReportDTO();
		List<AccountFinancialReportDTO> listofinterBankReportdata = listOfInterBankData.stream()
				.filter(obj -> obj != null).map(obj -> {
					final AccountFinancialReportDTO obj2 = new AccountFinancialReportDTO();
					obj2.setTransactionDate(Utility.dateToString((Date) obj[0]));
					obj2.setTransactionNo(obj[1].toString());
					obj2.setBankAcNo(getAccNo(bankAccountlistdto, Long.valueOf(obj[2].toString()), orgId));
					obj2.setReceiptNo(getAccNo(bankAccountlistdto, Long.valueOf(obj[3].toString()), orgId));
					obj2.setPaymentAmnt(obj[4].toString());
					obj2.setNarration(obj[5].toString());
					return obj2;
				}).collect(Collectors.toList());
		listofinterBankReportdata.sort((AccountFinancialReportDTO h1, AccountFinancialReportDTO h2) -> h1
				.getTransactionDate().compareTo(h2.getTransactionDate()));
		interBankReportData.setListOfDeposit(listofinterBankReportdata);
		return interBankReportData;
	}

	private String getAccNo(Map<Long, String> bankAccountlist, Long bankAccountId, Long orgId) {
		String collect = bankAccountlist.entrySet().stream().filter(map -> map.getKey().equals(bankAccountId))
				.map(map -> map.getValue()).collect(Collectors.joining());
		return collect;
	}

	@Override
	public AccountFinancialReportDTO getQuarterlyBudgetVarianceReportData(Long financialYearId, Long orgId) {
		// TODO Auto-generated method stub
		String fromDate = null;
		String toDate = null;
		List<Object[]> frmdateTodate = accountFinancialReportRepository.getAllFinincialFromDate(financialYearId);
		for (Object[] dateEntity : frmdateTodate) {
			fromDate = dateEntity[1].toString();
			toDate = dateEntity[2].toString();
		}
		String ds1 = fromDate;
		String ds2 = toDate;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
		try {
			fromDate = sdf2.format(sdf1.parse(ds1));
			toDate = sdf2.format(sdf1.parse(ds2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date fromDates = Utility.stringToDate(fromDate);
		Date todates = Utility.stringToDate(toDate);
		final List<LookUp> paymentModeList = CommonMasterUtility.getLookUps(AccountPrefix.COA.name(),
				UserSession.getCurrent().getOrganisation());
		AccountFinancialReportDTO budgetReportData = new AccountFinancialReportDTO();
		for (final LookUp lookUp : paymentModeList) {

			if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)){
			// REVENUE RECEIPTS
			if (lookUp.getLookUpCode().equals("I")) {
				List<Object[]> listOfIncomeQuarterlyData = accountFinancialReportRepository
						.getQuaterlyRevenueReCeiptsReportDataTSCL(orgId, fromDates, todates, financialYearId,
								lookUp.getLookUpCode());

				List<AccountFinancialReportDTO> listOfIncomeQuarterlyDto = getQuaterlyBudgetVarianceReportData(
						listOfIncomeQuarterlyData);
				budgetReportData.setListOfBudgetEstimation(listOfIncomeQuarterlyDto);
			}
			// CAPITAL RECEIPTS
			if (lookUp.getLookUpCode().equals("I")) {
				List<Object[]> listOfExpenditureQuarterlyData = accountFinancialReportRepository
						.getCapitalReceiptsDataByTSCL(orgId, fromDates, todates, financialYearId);
				List<AccountFinancialReportDTO> listOfExpenditureQuarterlyDto = getQuaterlyBudgetVarianceReportData(
						listOfExpenditureQuarterlyData);
				budgetReportData.setLiabilityList(listOfExpenditureQuarterlyDto);
			}

			// REVENUE EXPENDITURE
			if (lookUp.getLookUpCode().equals("E")) {
				List<Object[]> listOfLiabilitiesQuarterlyData = accountFinancialReportRepository
						.getRevenueExpenditureReportDataTSCL(orgId, fromDates, todates, financialYearId,
								lookUp.getLookUpCode());
				List<AccountFinancialReportDTO> listOfliabilities = getQuaterlyBudgetVarianceReportData(
						listOfLiabilitiesQuarterlyData);
				budgetReportData.setListOfExpenditure(listOfliabilities);
			}
			// CAPITAL EXPENDITURES
			if (lookUp.getLookUpCode().equals("E")) {
				List<Object[]> listOfAssetsQuarterlyData = accountFinancialReportRepository
						.getCapitalExpenditureReportDataTSCL(orgId, fromDates, todates, financialYearId);
				List<AccountFinancialReportDTO> listOfAssets = getQuaterlyBudgetVarianceReportData(
						listOfAssetsQuarterlyData);
				budgetReportData.setAssetList(listOfAssets);
			}
		}else {
			// REVENUE RECEIPTS
						if (lookUp.getLookUpCode().equals("I")) {
							List<Object[]> listOfIncomeQuarterlyData = accountFinancialReportRepository
									.getQuaterlyRevenueReCeiptsReportData(orgId, fromDates, todates, financialYearId,
											lookUp.getLookUpCode());

							List<AccountFinancialReportDTO> listOfIncomeQuarterlyDto = getQuaterlyBudgetVarianceReportData(
									listOfIncomeQuarterlyData);
							budgetReportData.setListOfBudgetEstimation(listOfIncomeQuarterlyDto);
						}
						// CAPITAL RECEIPTS
						if (lookUp.getLookUpCode().equals("I")) {
							List<Object[]> listOfExpenditureQuarterlyData = accountFinancialReportRepository
									.getCapitalReceiptsDataBy(orgId, fromDates, todates, financialYearId);
							List<AccountFinancialReportDTO> listOfExpenditureQuarterlyDto = getQuaterlyBudgetVarianceReportData(
									listOfExpenditureQuarterlyData);
							budgetReportData.setLiabilityList(listOfExpenditureQuarterlyDto);
						}

						// REVENUE EXPENDITURE
						if (lookUp.getLookUpCode().equals("E")) {
							List<Object[]> listOfLiabilitiesQuarterlyData = accountFinancialReportRepository
									.getRevenueExpenditureReportData(orgId, fromDates, todates, financialYearId,
											lookUp.getLookUpCode());
							List<AccountFinancialReportDTO> listOfliabilities = getQuaterlyBudgetVarianceReportData(
									listOfLiabilitiesQuarterlyData);
							budgetReportData.setListOfExpenditure(listOfliabilities);
						}
						// CAPITAL EXPENDITURES
						if (lookUp.getLookUpCode().equals("E")) {
							List<Object[]> listOfAssetsQuarterlyData = accountFinancialReportRepository
									.getCapitalExpenditureReportData(orgId, fromDates, todates, financialYearId);
							List<AccountFinancialReportDTO> listOfAssets = getQuaterlyBudgetVarianceReportData(
									listOfAssetsQuarterlyData);
							budgetReportData.setAssetList(listOfAssets);
						}
		}

		}
		return budgetReportData;
	}

	private List<AccountFinancialReportDTO> getQuaterlyBudgetVarianceReportData(List<Object[]> listOfQuarterlyData) {
		List<AccountFinancialReportDTO> listOfQuaterlyData = new ArrayList<>();
		if (listOfQuarterlyData != null && !listOfQuarterlyData.isEmpty()) {
			AccountFinancialReportDTO newdto1 = null;
			BigDecimal totalBudget = BigDecimal.ZERO;
			BigDecimal totalQuater1Amt = BigDecimal.ZERO;
			BigDecimal totalQuater2Amt = BigDecimal.ZERO;
			BigDecimal totalQuater3Amt = BigDecimal.ZERO;
			BigDecimal sumOftotalAmt = BigDecimal.ZERO;

			for (Object[] obj : listOfQuarterlyData) {
				BigDecimal percentage = BigDecimal.ZERO;
				newdto1 = new AccountFinancialReportDTO();
				newdto1.setAccountCode(obj[0].toString());

				newdto1.setAccountHeadDesc(obj[1].toString());

				newdto1.setAccountHeadId(Long.valueOf(obj[2].toString()));

				if (obj[3] != null) {
					newdto1.setOriginalEst(obj[3].toString());
					totalBudget = totalBudget.add(new BigDecimal(obj[3].toString()));
				} else {
					newdto1.setOriginalEst(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

				}

				if (obj[5] != null) {
					newdto1.setQuarter1Amount(obj[5].toString());
					totalQuater1Amt = totalQuater1Amt.add(new BigDecimal(obj[5].toString()));
				} else {
					newdto1.setQuarter1Amount(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
				}
				if (obj[6] != null) {
					newdto1.setQuarter2Amount(obj[6].toString());
					totalQuater2Amt = totalQuater2Amt.add(new BigDecimal(obj[6].toString()));
				} else {
					newdto1.setQuarter2Amount(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
				}

				if (obj[7] != null) {
					newdto1.setQuarter3Amount(obj[7].toString());
					totalQuater3Amt = totalQuater3Amt.add(new BigDecimal(obj[7].toString()));
				} else {
					newdto1.setQuarter2Amount(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
				}

				if (obj[8] != null) {
					newdto1.setTotalCollected(obj[8].toString());
					sumOftotalAmt = sumOftotalAmt.add(new BigDecimal(obj[8].toString()));
				} else {
					newdto1.setTotalCollected(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
				}
				if (obj[3] != null && obj[8] != null) {
					percentage = sumOftotalAmt.divide(totalBudget, 2, RoundingMode.HALF_EVEN).multiply(new BigDecimal(100));
					
					
				}
				if (percentage != null) {
					newdto1.setPercentage(percentage);
				} else {
					newdto1.setPercentage(new BigDecimal(0.00));
				}
				listOfQuaterlyData.add(newdto1);
			}
			newdto1 = new AccountFinancialReportDTO();
			newdto1.setTotalBudget(CommonMasterUtility.getAmountInIndianCurrency(totalBudget));
			newdto1.setTotalQuater1Amt(CommonMasterUtility.getAmountInIndianCurrency(totalQuater1Amt));
			newdto1.setTotalQuater2Amt(CommonMasterUtility.getAmountInIndianCurrency(totalQuater2Amt));
			newdto1.setTotalQuater3Amt(CommonMasterUtility.getAmountInIndianCurrency(totalQuater3Amt));
			newdto1.setSumOftotalAmt(CommonMasterUtility.getAmountInIndianCurrency(sumOftotalAmt));
			listOfQuaterlyData.add(newdto1);
		}
		return listOfQuaterlyData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.account.service.AccountFinancialReportService#
	 * getPaymentAndReceiptReportData(java.lang.String, java.lang.Long)
	 */
	@Override
	public AccountFinancialReportDTO getPaymentAndReceiptReportData(String fromDateId,String toDateId,Long orgid) {
		// TODO Auto-generated method stub
		final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.AccountJournalVoucherEntry.DR, PrefixConstants.DCR, orgid);
		final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.AccountJournalVoucherEntry.CR, PrefixConstants.DCR, orgid);
		final Long rvCpId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.REV_SUB_CPD_VALUE,
				PrefixConstants.VOT, orgid);
		final Long pvCpId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.PAY_SUB_CPD_VALUE,
				PrefixConstants.VOT, orgid);
		AccountFinancialReportDTO listOfReceiptDto = new AccountFinancialReportDTO();

		Long headIdOfRvDr = accountFinancialReportRepository.drcrSacHeadId(orgid, PrefixConstants.REV_SUB_CPD_VALUE);
		Long headIdOfPvDr = accountFinancialReportRepository.drcrSacHeadId(orgid, PrefixConstants.PAY_SUB_CPD_VALUE);

		List<Object[]> listOfReceiptBookData = accountFinancialReportRepository.getReceiptBookReportData(orgid,
				Utility.stringToDate(fromDateId),Utility.stringToDate(toDateId), drId, crId);
		List<Object[]> listOfPaymentBookData = accountFinancialReportRepository.getPaymentBookReportData(orgid,
				Utility.stringToDate(fromDateId), Utility.stringToDate(toDateId), drId, crId);
		LOGGER.info("count0--"+listOfReceiptBookData.size());
		LOGGER.info("count1--"+listOfPaymentBookData.size());
        LOGGER.info("data----------------------------->"+orgid+"---"+fromDateId+"---"+toDateId+"--"+drId+"--"+crId);
		// ReceiptBook Data
		List<AccountFinancialReportDTO> liistofReceiptData = new ArrayList<>();
		if (listOfReceiptBookData != null && !listOfReceiptBookData.isEmpty()) {
			listOfReceiptBookData.parallelStream().forEach(obj -> {
				if (Long.valueOf(obj[6].toString()).equals(rvCpId)) {
					final AccountFinancialReportDTO newdto1 = new AccountFinancialReportDTO();
					newdto1.setReceiptvouId(Long.valueOf(obj[0].toString()));
					newdto1.setVoucherDate(Utility.dateToString((Date) obj[1]));
					newdto1.setVoucherNo(obj[2].toString());
					newdto1.setAccountCode(obj[3].toString());
					newdto1.setNarration(obj[4].toString());
					if (Long.valueOf(obj[8].toString()).equals(headIdOfRvDr)) {
						newdto1.setDrAmount(new BigDecimal(obj[5].toString()));
					} else {
						newdto1.setCrAmount(new BigDecimal(obj[5].toString()));
					}
					liistofReceiptData.add(newdto1);
				}
			});
			
			liistofReceiptData.stream().filter(f -> f != null && f.getVoucherNo() != null).collect(Collectors.toList())
					.sort((AccountFinancialReportDTO h1, AccountFinancialReportDTO h2) -> h1.getVoucherNo()
							.compareTo(h2.getVoucherNo()));
		}
		// PaymentBook Data
		List<AccountFinancialReportDTO> liistofPaymentData = new ArrayList<>();
		if (listOfPaymentBookData != null && !listOfPaymentBookData.isEmpty()) {
			listOfPaymentBookData.parallelStream().forEach(obj -> {
				if (Long.valueOf(obj[6].toString()).equals(pvCpId)) {
					final AccountFinancialReportDTO newdto2 = new AccountFinancialReportDTO();
					newdto2.setPaymentvouId(Long.valueOf(obj[0].toString()));
					newdto2.setPaymentDates(Utility.dateToString((Date) obj[1]));
					newdto2.setPaymentNo(obj[2].toString());
					newdto2.setPaymentHeadcode(obj[3].toString());
					newdto2.setPaymentNarration(obj[4].toString());
					if (Long.valueOf(obj[8].toString()).equals(headIdOfPvDr)) {
						newdto2.setPaymentDrAmount(new BigDecimal(obj[5].toString()));
					} else {
						newdto2.setPaymentCrAmount(new BigDecimal(obj[5].toString()));
					}
					liistofPaymentData.add(newdto2);
				}
			});
		}

		liistofPaymentData.stream().filter(f->f!=null && f.getPaymentNo()!=null && f.getPaymentvouId()!=null).collect(Collectors.toList()).sort((AccountFinancialReportDTO h1, AccountFinancialReportDTO h2) -> h1.getPaymentNo()
				.compareTo(h2.getPaymentNo()));
		// ReceiptBook Data And PaymentBook Data Combined
		
		AccountFinancialReportDTO newdto3 = new AccountFinancialReportDTO();
		int count = 0;
		if (liistofReceiptData.size() >= liistofPaymentData.size()) {
			for (AccountFinancialReportDTO obj : liistofPaymentData) {
				newdto3 = liistofReceiptData.get(count);
				if(obj==null || newdto3==null)
				LOGGER.info("OBJECT------------->"+count +"NEW DTO----------"+newdto3);
				if(obj!=null && newdto3!=null ) {
				newdto3.setPaymentvouId(obj.getPaymentvouId());
				newdto3.setPaymentDates(obj.getPaymentDates());
				newdto3.setPaymentNo(obj.getPaymentNo());
				newdto3.setPaymentHeadcode(obj.getPaymentHeadcode());
				newdto3.setPaymentNarration(obj.getPaymentNarration());
				newdto3.setPaymentDrAmount(obj.getPaymentDrAmount());
				newdto3.setPaymentCrAmount(obj.getPaymentCrAmount());
				count = count + 1;
				}
			}
		} else if (liistofPaymentData.size() > liistofReceiptData.size()) {
			for (AccountFinancialReportDTO obj : liistofPaymentData) {
				if(obj!=null && newdto3!=null ) {
				if (liistofReceiptData.size() > count) {
					newdto3 = liistofReceiptData.get(count);
					newdto3.setPaymentvouId(obj.getPaymentvouId());
					newdto3.setPaymentDates(obj.getPaymentDates());
					newdto3.setPaymentNo(obj.getPaymentNo());
					newdto3.setPaymentHeadcode(obj.getPaymentHeadcode());
					newdto3.setPaymentNarration(obj.getPaymentNarration());
					newdto3.setPaymentDrAmount(obj.getPaymentDrAmount());
					newdto3.setPaymentCrAmount(obj.getPaymentCrAmount());
					count = count + 1;
				} else {
					newdto3 = new AccountFinancialReportDTO();
					newdto3.setPaymentvouId(obj.getPaymentvouId());
					newdto3.setPaymentDates(obj.getPaymentDates());
					newdto3.setPaymentNo(obj.getPaymentNo());
					newdto3.setPaymentHeadcode(obj.getPaymentHeadcode());
					newdto3.setPaymentNarration(obj.getPaymentNarration());
					newdto3.setPaymentDrAmount(obj.getPaymentDrAmount());
					newdto3.setPaymentCrAmount(obj.getPaymentCrAmount());
					liistofReceiptData.add(newdto3);
					count = count + 1;
				}
			}
			}
		}
		listOfReceiptDto.setListofreceiptinc(liistofReceiptData);
		return listOfReceiptDto;
	}

	@Override
	public AccountFinancialReportDTO getbudgetEstimationSheetsReport(Long financialYearId, Long deptId, Long functionId,
			String reportType,Long orgId) {
		List<Object[]> listOfBudgetsheetReport = null;
		BigDecimal sumOfpreviousYearAmt=BigDecimal.ZERO;
		BigDecimal sumOfCurrentYearAmt=BigDecimal.ZERO;
		BigDecimal sumOfRevisedYearAmt=BigDecimal.ZERO;
		BigDecimal sumOfNextYearAmt=BigDecimal.ZERO;
		AccountFinancialReportDTO budgetSheet = null;
		AccountFinancialReportDTO listOfReceiptDto = new AccountFinancialReportDTO();
		List<AccountFinancialReportDTO> liistofReceiptData = new ArrayList<>();
		
		if (reportType.equals(MainetConstants.ReceiptForm.RECEIPT_TYPE)) {
			//listOfBudgetsheetReport = accountFinancialReportRepository
				//	.getbudgetEstimationSheetsReportData(financialYearId, deptId, functionId);
			listOfBudgetsheetReport = accountFinancialReportRepository.getbudgetEstimationSheetsFormateReportReceiptData(financialYearId, deptId, functionId,orgId);
		} else if (reportType.equals(MainetConstants.ReceiptForm.PAYMENT_TYPE)) {
			//listOfBudgetsheetReport = accountFinancialReportRepository
			//		.getbudgetEstimationSheetsReportData(financialYearId, deptId, functionId);
			listOfBudgetsheetReport = accountFinancialReportRepository.getbudgetEstimationSheetsFormateReportPaymentData(financialYearId, deptId, functionId,orgId);
		}
		
		if (listOfBudgetsheetReport != null && !listOfBudgetsheetReport.isEmpty()) {
			for (Object[] obj : listOfBudgetsheetReport) {
				budgetSheet = new AccountFinancialReportDTO();

				if (obj[0] != null) {
					budgetSheet.setFieldId(obj[0].toString());
				}
				if (obj[1] != null) {
					budgetSheet.setAccountHeadDesc(obj[1].toString());
				}
				if (obj[2] != null) {
					
					budgetSheet.setPrevYramount(CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(obj[2].toString())));
					sumOfpreviousYearAmt=sumOfpreviousYearAmt.add(new BigDecimal(obj[2].toString()));
				}
				if (obj[3] != null) {
					budgetSheet.setCurrentYrAmount(CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(obj[3].toString())));
					sumOfCurrentYearAmt=sumOfCurrentYearAmt.add(new BigDecimal(obj[3].toString()));
				}

				if (obj[4] != null) {
					budgetSheet.setRevisedofCurrentYrAmount(CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(obj[4].toString())));
					sumOfRevisedYearAmt=sumOfRevisedYearAmt.add(new BigDecimal(obj[4].toString()));
				}

				if (obj[5] != null) {
					budgetSheet.setNextYrAmout(CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(obj[5].toString())));
					sumOfNextYearAmt=sumOfNextYearAmt.add(new BigDecimal(obj[5].toString()));
				}

				liistofReceiptData.add(budgetSheet);
			}
			listOfReceiptDto.setListOfIncome(liistofReceiptData);
			listOfReceiptDto.setPrevYramount(CommonMasterUtility.getAmountInIndianCurrency(sumOfpreviousYearAmt));
			listOfReceiptDto.setCurrentYrAmount(CommonMasterUtility.getAmountInIndianCurrency(sumOfCurrentYearAmt));
			listOfReceiptDto.setRevisedofCurrentYrAmount(CommonMasterUtility.getAmountInIndianCurrency(sumOfRevisedYearAmt));
			listOfReceiptDto.setNextYrAmout(CommonMasterUtility.getAmountInIndianCurrency(sumOfNextYearAmt));
		}
		return listOfReceiptDto;
	}

	@Override
	public AccountFinancialReportDTO getAccountBudgetReceivableReportData(Long financialYear, Long deptId, Long monthId,
			Long orgId) {
		// TODO Auto-generated method stub
		Long voucherType = 672L;
		Long cpdIdStatusFlag = 39L;
		Date dateBefore = null;
		Date tempdateBefore =null;
		BigDecimal totalClosingDr = new BigDecimal(0.00);
		BigDecimal totalClosingCr = new BigDecimal(0.00);
		List<Object[]> OpeningBalanceList = null;
		AccountFinancialReportDTO tempDTO;
		AccountFinancialReportDTO bean = new AccountFinancialReportDTO();
		List<AccountFinancialReportDTO> liisofAccountHead = new ArrayList<>();
		List<Object[]> acHeadCodeList = accountFinancialReportRepository.findAccountHeadsByOrgIdandDeptId(voucherType,
				orgId, deptId, cpdIdStatusFlag);
		for (Object[] accountFinReportDTO : acHeadCodeList) {
			tempDTO = new AccountFinancialReportDTO();
			tempDTO.setAccountCode(accountFinReportDTO[0].toString());
			tempDTO.setAccountHeadId(Long.valueOf(accountFinReportDTO[1].toString()));
			liisofAccountHead.add(tempDTO);
		}
		String fromDate = null;
		String toDate = null;
		List<Object[]> frmdateTodate = accountFinancialReportRepository.getAllFinincialFromDate(financialYear);
		for (Object[] dateEntity : frmdateTodate) {
			fromDate = dateEntity[1].toString();
			toDate = dateEntity[2].toString();
		}
		String ds1 = fromDate;
		String ds2 = toDate;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
		try {
			fromDate = sdf2.format(sdf1.parse(ds1));
			toDate = sdf2.format(sdf1.parse(ds2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (monthId >= 4) {
			 
			toDate = fromDate.replace("04", "0"+monthId.toString()); //01/06/2019
			
	
			
		} else {
			toDate = toDate.replace("31/03", "01/0"+monthId.toString());
			/*tempdateBefore = new Date(Utility.stringToDate(toDate).getTime() - 1 * 24 * 3600 * 1000);*/
		}
		
		bean.setFromDate(fromDate);
		bean.setToDate(toDate);
		AccountFinancialReportDTO finalDTO = new AccountFinancialReportDTO();
		List<AccountFinancialReportDTO> finalResultDTO = new ArrayList<AccountFinancialReportDTO>();
		for (AccountFinancialReportDTO accountFinReportDTO : liisofAccountHead) {
			final List<VoucherDetailViewEntity> records = findReportDataForGeneralLedgerRegister(bean.getFromDate(),
					bean.getToDate(), accountFinReportDTO.getAccountHeadId(), orgId);
			AccountFinancialReportDTO accountFinancialReportDTO = new AccountFinancialReportDTO();
			mapGeneralLedgerDataToDTO(records, accountFinancialReportDTO);
			Date d = Utility.stringToDate(bean.getFromDate());// intialize your date to any date
			int year1 = getYearFromDate(d);
			String isDateFinancail = "01/04/" + (year1);
			Date d1 = Utility.stringToDate(bean.getToDate());// intialize your date to any date
			int year2 = getYearFromDate(d);
			String isDateFinancail1 = "31/03/" + (year2);
			if (!d.equals(Utility.stringToDate(isDateFinancail))) { // &&
				dateBefore = new Date(d.getTime() - 1 * 24 * 3600 * 1000);
				Date financialYFromDate = tbFinancialyearJpaRepository
						.getFromDateFromFinancialYearIdByPassingDate(dateBefore);
				financialYFromDate = Utility.stringToDate((Utility.dateToString(financialYFromDate)));
				List<Object[]> listOfSum = accountFinancialReportRepository.getSumOfCreditAndSumOfDebitFromVoucher(
						financialYFromDate, dateBefore, accountFinReportDTO.getAccountHeadId(), orgId);
				Long financialYearId = tbFinancialyearJpaRepository
						.getFinanciaYearIdByFromDate(Utility.stringToDate(bean.getFromDate()));
				if (financialYearId != null)
					OpeningBalanceList = findOpeningBalanceAmount(accountFinReportDTO.getAccountHeadId(), orgId,
							financialYearId);

				final AccountFinancialReportDTO dto = new AccountFinancialReportDTO();
				final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
						PrefixConstants.AccountJournalVoucherEntry.DR, PrefixConstants.DCR, orgId);
				final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
						PrefixConstants.AccountJournalVoucherEntry.CR, PrefixConstants.DCR, orgId);
				if ((OpeningBalanceList != null) && !OpeningBalanceList.isEmpty()) {
					for (Object[] OpeningBalanceLists : OpeningBalanceList) {
						if (OpeningBalanceLists[1].equals(drId)) {
							if (OpeningBalanceLists[0] != null) {
								dto.setOpeningDrAmount(new BigDecimal((String) OpeningBalanceLists[0]));
								dto.setClosingDrAmount(new BigDecimal((String) OpeningBalanceLists[0]));
							}
						} else if (OpeningBalanceLists[1].equals(crId)) {
							if (OpeningBalanceLists[0] != null) {
								dto.setOpeningCrAmount(new BigDecimal((String) OpeningBalanceLists[0]));
								dto.setClosingCrAmount(new BigDecimal((String) OpeningBalanceLists[0]));
							}
						}

					}
				}

				if (listOfSum != null && !listOfSum.isEmpty()) {
					for (Object[] listOfSums : listOfSum) {

						if (dto.getOpeningDrAmount() != null && listOfSums[0] != null && listOfSums[1] != null) {
							BigDecimal openingBalance = dto.getOpeningDrAmount()
									.add(new BigDecimal(listOfSums[1].toString()))
									.subtract(new BigDecimal(listOfSums[0].toString()));
							if (openingBalance.signum() == -1) {
								dto.setOpeningCrAmount(openingBalance.abs());
							} else {
								dto.setOpeningDrAmount(openingBalance);
							}
						} else if (dto.getOpeningCrAmount() != null && listOfSums[0] != null && listOfSums[1] != null) {
							BigDecimal openingBalance = dto.getOpeningCrAmount()
									.add(new BigDecimal(listOfSums[0].toString())
											.subtract(new BigDecimal(listOfSums[1].toString())));
							if (openingBalance.signum() == -1) {
								dto.setOpeningDrAmount(openingBalance.abs());
							} else {
								dto.setOpeningCrAmount(openingBalance);
							}
						} else if (dto.getOpeningCrAmount() == null && dto.getOpeningDrAmount() == null
								&& listOfSums[0] != null && listOfSums[1] == null) {
							BigDecimal openingBalance = new BigDecimal(listOfSums[0].toString());
							dto.setOpeningCrAmount(openingBalance);

						} else if (dto.getOpeningCrAmount() == null && dto.getOpeningDrAmount() == null
								&& listOfSums[0] == null && listOfSums[1] != null) {
							BigDecimal openingBalance = new BigDecimal(listOfSums[1].toString());
							dto.setOpeningDrAmount(openingBalance);

						}

						else if (dto.getOpeningCrAmount() == null && dto.getOpeningDrAmount() == null
								&& listOfSums[0] != null && listOfSums[1] != null) {
							BigDecimal openingBalance = new BigDecimal(listOfSums[0].toString())
									.subtract(new BigDecimal(listOfSums[1].toString()));
							if (openingBalance.signum() == -1) {
								dto.setOpeningDrAmount(openingBalance.abs());
							} else {
								dto.setOpeningCrAmount(openingBalance);
							}
						} else if (dto.getOpeningCrAmount() == null && dto.getOpeningDrAmount() == null
								&& listOfSums[0] == null && listOfSums[1] == null) {
							dto.setOpeningCrAmount(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
							dto.setOpeningDrAmount(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
						}

					}
				}

				if (dto.getOpeningCrAmount() != null && accountFinancialReportDTO.getSumOfCrAmount() != null
						&& accountFinancialReportDTO.getSumOfDrAmount() != null) {
					BigDecimal closingBalance = dto.getOpeningCrAmount()
							.add(accountFinancialReportDTO.getSumOfCrAmount())
							.subtract(accountFinancialReportDTO.getSumOfDrAmount());
					if (closingBalance.signum() == -1) {
						dto.setClosingDrAmount(closingBalance.abs());
						dto.setClosingCrAmount(null);
					} else {
						dto.setClosingCrAmount(closingBalance);
						dto.setClosingDrAmount(null);

						// Changed by @ajay Kumar
					}
				}

				else if (dto.getOpeningDrAmount() != null && accountFinancialReportDTO.getSumOfCrAmount() != null
						&& accountFinancialReportDTO.getSumOfDrAmount() != null) {
					BigDecimal closingBalance = dto.getOpeningDrAmount()
							.add(accountFinancialReportDTO.getSumOfDrAmount())
							.subtract(accountFinancialReportDTO.getSumOfCrAmount());
					if (closingBalance.signum() == -1) {
						dto.setClosingCrAmount(closingBalance.abs());
					} else {
						dto.setClosingDrAmount(closingBalance);
					}
				} else if (accountFinancialReportDTO.getSumOfCrAmount() != null
						&& accountFinancialReportDTO.getSumOfDrAmount() != null) {
					BigDecimal closingBalance = accountFinancialReportDTO.getSumOfCrAmount()
							.subtract(accountFinancialReportDTO.getSumOfDrAmount());
					if (closingBalance.signum() == -1) {
						dto.setClosingCrAmount(closingBalance.abs());
					} else {
						dto.setClosingDrAmount(closingBalance);
					}

				}
				dto.setFromDate(bean.getFromDate());
				dto.setToDate(bean.getToDate());
				dto.setAccountCode(accountFinReportDTO.getAccountCode());
				mapGeneralLedgerDataToDTO(records, dto);
				finalResultDTO.add(dto);
			} else {
				List<Object[]> listOfSum = accountFinancialReportRepository.getSumOfCreditAndSumOfDebitFromVoucher(
						Utility.stringToDate(bean.getFromDate()), Utility.stringToDate(bean.getToDate()),
						accountFinReportDTO.getAccountHeadId(), orgId);
				Long financialYearId = tbFinancialyearJpaRepository
						.getFinanciaYearIdByFromDate(Utility.stringToDate(bean.getFromDate()));
				if (financialYearId != null)
					OpeningBalanceList = findOpeningBalanceAmount(accountFinReportDTO.getAccountHeadId(), orgId,
							financialYearId);
				final AccountFinancialReportDTO dto = new AccountFinancialReportDTO();
				final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
						PrefixConstants.AccountJournalVoucherEntry.DR, PrefixConstants.DCR, orgId);
				final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
						PrefixConstants.AccountJournalVoucherEntry.CR, PrefixConstants.DCR, orgId);
				if ((OpeningBalanceList != null) && !OpeningBalanceList.isEmpty()) {
					for (Object[] OpeningBalanceLists : OpeningBalanceList) {
						if (OpeningBalanceLists[1].equals(drId)) {
							if (OpeningBalanceLists[0] != null) {
								dto.setOpeningDrAmount(new BigDecimal((String) OpeningBalanceLists[0]).setScale(2,
										RoundingMode.HALF_EVEN));
								dto.setClosingDrAmount(new BigDecimal((String) OpeningBalanceLists[0]).setScale(2,
										RoundingMode.HALF_EVEN));

							}
						} else if (OpeningBalanceLists[1].equals(crId)) {
							if (OpeningBalanceLists[0] != null) {
								dto.setOpeningCrAmount(new BigDecimal((String) OpeningBalanceLists[0]).setScale(2,
										RoundingMode.HALF_EVEN));
								dto.setClosingCrAmount(new BigDecimal((String) OpeningBalanceLists[0]).setScale(2,
										RoundingMode.HALF_EVEN));

							}
						}

					}
				}
				if (listOfSum != null && !listOfSum.isEmpty()) {
					for (Object[] listOfSums : listOfSum) {
						if (dto.getOpeningCrAmount() != null
								&& !dto.getOpeningCrAmount()
										.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))
								&& listOfSums[0] != null && listOfSums[1] != null) {
							/*
							 * dto.setClosingCrAmount(calculateClosingBalAsOnDate(dto.getOpeningCrAmount(),
							 * BigDecimal.valueOf((Double) listOfSums[0]), BigDecimal.valueOf((Double)
							 * listOfSums[1])));
							 */

							BigDecimal closingBalance = calculateClosingBalAsOnDate(dto.getOpeningCrAmount(),
									new BigDecimal(listOfSums[0].toString()), new BigDecimal(listOfSums[1].toString()));

							if (closingBalance.signum() == -1) {
								dto.setClosingDrAmount(closingBalance.abs());
								dto.setClosingCrAmount(null);
							} else {
								dto.setClosingCrAmount(closingBalance);
								dto.setClosingDrAmount(null);
							}

						} else if (dto.getOpeningDrAmount() != null
								&& !dto.getOpeningDrAmount()
										.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))
								&& listOfSums[0] != null && listOfSums[1] != null) {
							/*
							 * dto.setClosingDrAmount(calculateClosingBalAsOnDate(dto.getOpeningDrAmount(),
							 * BigDecimal.valueOf((Double) listOfSums[1]), BigDecimal.valueOf((Double)
							 * listOfSums[0])));
							 */
							BigDecimal closingBalance = calculateClosingBalAsOnDate(dto.getOpeningDrAmount(),
									new BigDecimal(listOfSums[1].toString()), new BigDecimal(listOfSums[0].toString()));

							if (closingBalance.signum() == -1) {
								dto.setClosingCrAmount(closingBalance.abs());
								dto.setClosingDrAmount(null);
							} else {
								dto.setClosingCrAmount(null);
								dto.setClosingDrAmount(closingBalance);
							}

						} else if (listOfSums[0] != null && listOfSums[1] != null) {

							BigDecimal closingBlance = new BigDecimal(0.00);
							if (listOfSums[0] != null && listOfSums[1] != null) {

								closingBlance = calculateClosingBalAsOnDate(dto.getOpeningDrAmount(),
										new BigDecimal(listOfSums[0].toString()),
										new BigDecimal(listOfSums[1].toString()));
							}
							if (closingBlance.signum() == -1) {
								dto.setClosingDrAmount(closingBlance.abs());
							} else {
								dto.setClosingCrAmount(closingBlance);
							}
						}
					}
					if (dto.getOpeningCrAmount() == null) {
						dto.setOpeningCrAmount(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
					}
					if (dto.getOpeningDrAmount() == null) {
						dto.setOpeningDrAmount(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
					}
				}
				dto.setFromDate(bean.getFromDate());
				dto.setToDate(bean.getToDate());
				dto.setAccountCode(accountFinReportDTO.getAccountCode());
				mapGeneralLedgerDataToDTO(records, dto);
				finalResultDTO.add(dto);
			}
		}
		finalDTO.setFromDate(bean.getFromDate());
		finalDTO.setToDate(bean.getToDate());
		finalDTO.setGeneralLedgerList(finalResultDTO);
		return finalDTO;
	}

	@Override
	public AccountLoanReportDTO  loanReportData(String loanCode, Long orgId)
	{

		List<Object[]> loanRegisterData = null;
		loanRegisterData = accountFinancialReportRepository.loanReportData(loanCode, orgId);
		List<AccountLoanReportDTO> reportList = new ArrayList<AccountLoanReportDTO>();
		AccountLoanReportDTO ListOfReportDto = new AccountLoanReportDTO();//final object 
		AccountLoanReportDTO reportDtoTemp; //for iterating 
		
		if(loanRegisterData != null && !loanRegisterData.isEmpty())
		{
			for(Object[] obj : loanRegisterData)
			{
				reportDtoTemp = new AccountLoanReportDTO();
				if(obj[0] != null)
				{
					reportDtoTemp.setLoanId(Long.valueOf(obj[0].toString()));
				}
				if(obj[1] != null)
				{
					reportDtoTemp.setLnDeptname((obj[1].toString()));
				}
				if(obj[2] != null)
				{
					reportDtoTemp.setLnPurpose(obj[2].toString());
				}
				if(obj[3] != null)
				{
					reportDtoTemp.setSanctionDate(Utility.converObjectToDate(obj[3]));
				}
				if(obj[4] != null)
				{
					reportDtoTemp.setSantionAmount(new BigDecimal(obj[4].toString()));
				}
				if(obj[5] != null)
				{
					reportDtoTemp.setLnInrate(new BigDecimal(obj[5].toString()));
				}
				if(obj[6] != null)
				{
					//reportDtoTemp.setInstAmt(Long.valueOf((obj[6].toString())));
				//	reportDtoTemp.setInstAmt(new BigDecimal(obj[6].toString()));
				}
				if(obj[7] == null)
				{
					//reportDtoTemp.setNoOfInstallments(0L);
					//reportDtoTemp.setNoOfInstallments(Long.valueOf(obj[7].toString()));
				}
				else
				{
					//reportDtoTemp.setNoOfInstallments(Long.valueOf(obj[7].toString()));
				}
				if(obj[8] != null)
				{
					reportDtoTemp.setLmRemark(obj[8].toString());}
				if(obj[9] != null)
				{
					reportDtoTemp.setRmDate(Utility.converObjectToDate(obj[9]));
				}
				if(obj[10] != null)
				{
					//reportDtoTemp.setLoanPeriodUnit(obj[10].toString());
				}
				if(obj[11] != null) //setAmountReceived
				{
					reportDtoTemp.setRmAmount(new BigDecimal(obj[11].toString()));
				}
				if(obj[12] != null)
				{
					reportDtoTemp.setInstDueDate(Utility.converObjectToDate(obj[12]));
				}
				if(obj[13] != null)
				{
					reportDtoTemp.setPrnpalAmount(new BigDecimal(obj[13].toString()));
				}
				if(obj[14] != null)
				{
					reportDtoTemp.setIntAmount(new BigDecimal(obj[14].toString()));
				}
				if(obj[15] != null)
				{
					//Long sum =Long.valueOf(obj[14].toString())+ Long.valueOf(obj[13].toString()) ;
					reportDtoTemp.setBalIntAmt(new BigDecimal(obj[15].toString()));
				}
				//new BigDecimal(obj[14].toString()) + new BigDecimal(obj[13].toString())
				if(obj[16] != null)
				{
				reportDtoTemp.setResNo(Long.valueOf(obj[16].toString()));
				}
				if(obj[17] != null)
				{
					reportDtoTemp.setResDate(Utility.converObjectToDate(obj[17]));
				}
				if(obj[17] != null)
				{
					reportDtoTemp.setResDate(Utility.converObjectToDate(obj[17]));
				}
				if(obj[18] != null)
				{
					reportDtoTemp.setBalPrnpalamt(new BigDecimal(obj[18].toString()));
				}
				if(obj[19] != null)
				{
					//reportDtoTemp.setSanctionNo(obj[19].toString());
				}
				reportList.add(reportDtoTemp);
				
				
			}
			//ListOfReportDto.setAccountLoanReportDtoList(reportList);
		}
		return null;
	}

	@Override
	public AccountInvestmentMasterDto getRegisterData(String invstNo, Long orgId) {
		
		
		List<AccountInvestmentMasterEntity> entityData = accountFinancialReportRepository.getRegisterData(invstNo, orgId);
		AccountInvestmentMasterDto dtoData = new AccountInvestmentMasterDto();
		BeanUtils.copyProperties(entityData.get(0), dtoData);
		dtoData.setInvdate(Utility.dateToString(dtoData.getInvstDate(), "dd/MM/yyyy"));
		dtoData.setResdate(Utility.dateToString(dtoData.getResDate(), "dd/MM/yyyy"));
		dtoData.setInvDueDate(Utility.dateToString(dtoData.getInvstDueDate(), "dd/MM/yyyy"));
		return dtoData;
	}

	@Override
	public List<String> getInvestmentId(Long orgId) {
		List<String> investmentNos = accountFinancialReportRepository.getInvestmentId(orgId);
		List<String> investId = new ArrayList<String>() ;
		
		investmentNos.forEach(investmentno->{
			investId.add(investmentno);
		});
		
		return investId;
	}
	
	@Override
	public List<String> getInvestmentIdFromFundId(Long orgId,Long fundId) {
		List<String> investmentNos = accountFinancialReportRepository.getInvestmentIdFromFundId(orgId,fundId);
		List<String> investId = new ArrayList<String>() ;
		
		investmentNos.forEach(investmentno->{
			investId.add(investmentno);
		});
		
		return investId;
	}

	@Override
	public Map<String, String> getGrantName(Long orgId) {
		List<Object[]> grantName = accountFinancialReportRepository.getGrantName(orgId);
		final Map<String, String> NameAndNoMap = new LinkedHashMap<>(0);
		
		for (Object[] obj : grantName) {
			if (obj[0]!= null) {
				NameAndNoMap.put((String) (obj[0]), (String) obj[1]);
			// 	//NameAndNoMap.put((String) (obj.getGrtNo()), (String) obj.getGrtName());
			}
	}
		return NameAndNoMap;
	
	}	
		
		
		
		
	@Override
	public List<String> getLoanCode(Long orgId) {
		List<String> loanId = accountFinancialReportRepository.getLoanCode(orgId);
		List<String> loanCode = new ArrayList<String>();
		
		loanId.forEach(id->{
			loanCode.add(id);
		});
		
		return loanCode;
	}

	@Override
	public List<AccountGrantMasterDto> getGrantRegisterData(String grtName, Long faYearId, Long orgId) {
		
		Date fromDate	 = null;
		Date toDate = null;
		
		List <AccountGrantMasterDto> grantMasterDtoList = null;
		List<Object[]> frmdateTodate = accountFinancialReportRepository.getAllFinincialFromDate(faYearId);
		
		for (Object[] dateEntity : frmdateTodate) {
			
			fromDate =  Utility.converObjectToDate(dateEntity[1]);
			toDate = Utility.converObjectToDate(dateEntity[2]);
		}
		
		List<AccountGrantMasterEntity> entityData = accountFinancialReportRepository.getGrantRegisterData(grtName,fromDate,toDate, orgId);
		//List<AccountGrantMasterEntity> entityData = accountFinancialReportRepository.getGrantRegisterDataName(grtName, orgId);
		
		for (int i = 0; i < entityData.size(); i++) {
		     BeanUtils.copyProperties(entityData.get(i), grantMasterDtoList.get(i));
		}
		return grantMasterDtoList;
	}

	@Override
	public AccountLoanReportDTO dataForRegister(Long orgid, String loanCode) {
		AccountLoanReportDTO dto = new AccountLoanReportDTO();
		
		
		// call the method and get array of objects
		
		AccountReceiptDTO receiptDto ;
		AccountLoanDetDto detDto;
		AccountBillEntryMasterBean billDto;
		
		
		List<AccountReceiptDTO> tempReceiptList = new ArrayList<AccountReceiptDTO>();
		List<AccountLoanDetDto> tempDetList = new ArrayList<AccountLoanDetDto>();
		List<AccountBillEntryMasterBean> tempBillList = new ArrayList<AccountBillEntryMasterBean>();
		
		
		/*
		 * List<Object[]> listOfData =
		 * accountFinancialReportRepository.receiptsForRegister(orgid, loanCode);
		 * 
		 * if(listOfData != null && !listOfData.isEmpty()) { for(Object [] object
		 * :listOfData) { receiptDto = new AccountReceiptDTO(); if(object[0]!= null) {
		 * // set date receiptDto.setReceiptAmount(object[0].toString()); }
		 * if(object[1]!= null) { //set amount
		 * receiptDto.setCreatedDate(Utility.converObjectToDate(object[1]));
		 * receiptDto.setReceiptDate(Utility.converObjectToDate(object[1]).toString());
		 * } tempReceiptList.add(receiptDto); } }
		 * 
		 * //dto.setAccountReceiptList(tempReceiptList);
		 * 
		 * 
		 * List<Object[]> amountDueForPayment =
		 * accountFinancialReportRepository.dueForPaymentData(orgid, loanCode);
		 * if(amountDueForPayment != null && !amountDueForPayment.isEmpty()) {
		 * for(Object [] object :amountDueForPayment) { detDto = new
		 * AccountLoanDetDto(); if(object[0]!= null) { detDto.setPrnpalAmount(new
		 * BigDecimal(object[0].toString())); } if(object[1]!= null) {
		 * detDto.setIntAmount(new BigDecimal(object[1].toString())); } if(object[2]!=
		 * null) { detDto.setBalIntAmt((new BigDecimal(object[2].toString()))); }
		 * if(object[3]!= null) {
		 * detDto.setInstDueDate(Utility.converObjectToDate(object[3])); }
		 * tempDetList.add(detDto); } }
		 * 
		 * 
		 * List<Object[]> amountPaid =
		 * accountFinancialReportRepository.amountPaidData(orgid, loanCode);
		 * if(amountPaid != null && !amountPaid.isEmpty()) { for(Object [] object
		 * :amountPaid) { billDto = new AccountBillEntryMasterBean(); if(object[0]!=
		 * null) { billDto.setBillDate(object[0].toString()); } if(object[1]!= null) {
		 * billDto.setBillAmt(object[1].toString()); }
		 * 
		 * tempBillList.add(billDto); } }
		 * //dto.setAccountBillEntryMasterList(tempBillList);
		 * 
		 * List<AccountLoanMasterEntity> lnmst =
		 * accountFinancialReportRepository.getLoanMasterData(orgid, loanCode);
		 * 
		 * lnmst.get(0).getAccountLoanDetList();
		 */
		
		
		return dto;
	}

	@Override
	public AccountBillEntryMasterEnitity amountPaidData(Long orgid, Long lndetId) {
		// TODO Auto-generated method stub
		AccountBillEntryMasterEnitity billEntity = accountFinancialReportRepository.amountPaidData(orgid, lndetId);
		return billEntity;
	}

	@Override
	public List<TbServiceReceiptMasEntity> receiptsForRegister(Long orgid, Long refId, String receiptTypeFlag) {
		List<TbServiceReceiptMasEntity> receiptEntity = new ArrayList<TbServiceReceiptMasEntity>();
		
		receiptEntity = accountFinancialReportRepository.receiptsForRegister(orgid, refId,receiptTypeFlag);
		return receiptEntity;
	}

	@Override
	public AccountFinancialReportDTO getbudgetEstimationConsolidationFormat(Long financialYearId, Long deptId,
			String reportType, Long orgId) {
		AccountFinancialReportDTO bean=null;
		BigDecimal sumOfpreviousYearAmt=BigDecimal.ZERO;
		BigDecimal sumOfCurrentYearAmt=BigDecimal.ZERO;
		BigDecimal sumOfRevisedYearAmt=BigDecimal.ZERO;
		BigDecimal sumOfNextYearAmt=BigDecimal.ZERO;
		AccountFinancialReportDTO mainBean=new AccountFinancialReportDTO();
		List<AccountFinancialReportDTO> liistofReceiptData = new ArrayList<>();
		List<Object[]> reportData=null;
		if(reportType.equals(MainetConstants.ReceiptForm.RECEIPT_TYPE)) {
			reportData=accountFinancialReportRepository.getbudgetEstimationConsolidationFormatReportReceiptData(financialYearId, orgId);
		}else if(reportType.equals(MainetConstants.ReceiptForm.PAYMENT_TYPE)) {
			reportData=accountFinancialReportRepository.getbudgetEstimationConsolidationFormatReportPaymentData(financialYearId, orgId);
		}
	 if(CollectionUtils.isNotEmpty(reportData)) {
			for(Object [] obj:reportData) {
				bean=new AccountFinancialReportDTO();	
				if(obj[0]!=null) {
					bean.setFieldId(obj[0].toString());
				}
				if(obj[1]!=null) {
					bean.setFunctionName(obj[1].toString());
				}
				if(obj[2]!=null) {
					//column name field used for Functionary
			         bean.setColumnName(obj[2].toString());
				}
				if(obj[3]!=null) {
					bean.setAccountHeadDesc(obj[3].toString());
				}
				if(obj[4]!=null) {
					bean.setPrevYramount(CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(obj[4].toString())));
					sumOfpreviousYearAmt=sumOfpreviousYearAmt.add(new BigDecimal(obj[4].toString()));
				}
				if(obj[5]!=null) {
					bean.setCurrentYrAmount(CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(obj[5].toString())));
					sumOfCurrentYearAmt=sumOfCurrentYearAmt.add(new BigDecimal(obj[5].toString()));
				}
				if(obj[6]!=null) {
					bean.setRevisedEstimation(CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(obj[6].toString())));
					sumOfRevisedYearAmt=sumOfRevisedYearAmt.add(new BigDecimal(obj[6].toString()));
				}
				
				if(obj[7]!=null) {
					bean.setNextYrAmout(CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(obj[7].toString())));
					sumOfNextYearAmt=sumOfNextYearAmt.add(new BigDecimal(obj[7].toString()));
				}
				liistofReceiptData.add(bean);
			}
			mainBean.setListOfIncome(liistofReceiptData);
			mainBean.setPrevYramount(CommonMasterUtility.getAmountInIndianCurrency(sumOfpreviousYearAmt));
			mainBean.setCurrentYrAmount(CommonMasterUtility.getAmountInIndianCurrency(sumOfCurrentYearAmt));
			mainBean.setRevisedofCurrentYrAmount(CommonMasterUtility.getAmountInIndianCurrency(sumOfRevisedYearAmt));
			mainBean.setNextYrAmout(CommonMasterUtility.getAmountInIndianCurrency(sumOfNextYearAmt));
		}
		return mainBean;
	}

	@Override
	public List<AccountFunctionWiseBudgetReportDto> getFunctionWiseBugetReport(Long financialYearId, Long orgID) {
       List<Object[]> list = accountFinancialReportRepository.getFunctionWiseBudgetData(financialYearId, orgID);
       List<AccountFunctionWiseBudgetReportDto>  budgetList=new ArrayList<AccountFunctionWiseBudgetReportDto>();
       if(CollectionUtils.isNotEmpty(list)) {
            budgetList = list.stream().map(obj->{
    	    AccountFunctionWiseBudgetReportDto  dto=new AccountFunctionWiseBudgetReportDto(); 
    	    dto.setFunctionDesc(obj[0].toString());
      	    dto.setFunctionCode(obj[1].toString());
      	    dto.setRevenuReceiptAmt(CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(obj[3].toString())));
      	    dto.setCapitalReceiptAmt(CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(obj[4].toString())));
      	    dto.setSubTotalReceiptAmt(CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(obj[5].toString())));
      	    dto.setRevenueExpensesAmt(CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(obj[6].toString())));
      	    dto.setCapitalExpensesAmt(CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(obj[7].toString())));
      	    dto.setSubtotalExpenseAmt(CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(obj[8].toString())));
      	    dto.setOutFlow(CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(obj[9].toString())));
      	    return dto;
        }).collect(Collectors.toList());
      }
		return budgetList;
	}
	
	//operating Receipt major head
	private List<Object[]> queryClassifiedBudgetReceiptSideOpeningReportDataLevel3(Date frmDate, Date tDate, long orgId,
			Long accountHeadId) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		final Long coaReceiptLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.LookUp.INDIVIDUAL, PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetReceiptSideOpeningReportDataLevel3(frmDate, tDate,
				orgId, coaReceiptLookupId);

	}
	
	//non operating Receipt major heads
	private List<Object[]> queryClassifiedBudgetReceiptSideNonOpeningReportDataLevel3(Date fromDate, Date toDate, Long orgId,
			Long codcofdetId) {
		// TODO Auto-generated method stub
		Organisation org=new Organisation();
		List<Long> lookupId=new ArrayList<>();
		org.setOrgid(orgId);
		final Long coaReceiptLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.LookUp.INDIVIDUAL, PrefixConstants.PREFIX_VALUE_IELA, orgId);
		List<LookUp> coaReceiptLookupIds=CommonMasterUtility.getListLookup(PrefixConstants.PREFIX_VALUE_IELA, org).stream().filter(coa->coa.getLookUpCode().equals(PrefixConstants.LookUp.INDIVIDUAL)||coa.getLookUpCode().equals(MainetConstants.FlagE)).collect(Collectors.toList());
		for(LookUp lkp:coaReceiptLookupIds) {
			lookupId.add(lkp.getLookUpId());
		}
		return accountFinancialReportRepository.queryClassifiedBudgetReceiptSideNonOperatingOpeningReportDataLevel3(fromDate, toDate,
				orgId, lookupId);

	}
	
	//operating Payment major heads
	private List<Object[]> queryClassifiedBudgetPaymentSideOpeningReportDataLevel3(Date fromDate, Date toDate, Long orgId,
			Long codcofdetId) {

		// TODO Auto-generated method stub
		final Long coaPaymentLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.NEC.EMPLOYEE,
				PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetPaymentSideOperatingOpeningReportDataLevel3(fromDate, toDate,
				orgId, coaPaymentLookupId);
	}
	
	//non operating payment
	private List<Object[]> queryClassifiedBudgetPaymentSideNonOpeningReportDataLevel3(Date fromDate, Date toDate, Long orgId,
			Long codcofdetId) {

		Organisation org=new Organisation();
		List<Long> lookupId=new ArrayList<>();
		org.setOrgid(orgId);
		List<LookUp> coaReceiptLookupIds=CommonMasterUtility.getListLookup(PrefixConstants.PREFIX_VALUE_IELA, org).stream().filter(coa->coa.getLookUpCode().equals(PrefixConstants.LookUp.INDIVIDUAL)||coa.getLookUpCode().equals(MainetConstants.FlagE)).collect(Collectors.toList());
		for(LookUp lkp:coaReceiptLookupIds) {
			lookupId.add(lkp.getLookUpId());
		}
		return accountFinancialReportRepository.queryClassifiedBudgetPaymentSideNonOperatingOpeningReportDataLevel3(fromDate, toDate,
				orgId, lookupId);
	}

	
	
	//prevous year
	//operating Receipt major head
	private List<Object[]> queryClassifiedBudgetReceiptSideOpeningPreviousReportDataLevel3(Date frmDate, Date tDate, long orgId,
			Long accountHeadId) {
		int fromDay = getDayFromDate(frmDate);
		int fromMonth = getMonthFromDate(frmDate);
		int fromYear = getYearFromDate(frmDate);
		String fromDates = fromDay + "/" + fromMonth + "/" + (fromYear - 1);
		Date fromDate = Utility.stringToDate(fromDates);
		int toDay = getDayFromDate(tDate);
		int toMonth = getMonthFromDate(tDate);
		int toYear = getYearFromDate(tDate);
		String toDates = toDay + "/" + toMonth + "/" + (toYear - 1);
		Date toDate = Utility.stringToDate(toDates);
		final Long coaReceiptLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.LookUp.INDIVIDUAL, PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetReceiptSideOpeningReportDataLevel3(fromDate, toDate,
				orgId, coaReceiptLookupId);

	}
	
	//non operating Receipt major heads
	private List<Object[]> queryClassifiedBudgetReceiptSideNonOpeningPreviousReportDataLevel3(Date frmDate, Date tDate, Long orgId,
			Long codcofdetId) {
		// TODO Auto-generated method stub
		int fromDay = getDayFromDate(frmDate);
		int fromMonth = getMonthFromDate(frmDate);
		int fromYear = getYearFromDate(frmDate);
		String fromDates = fromDay + "/" + fromMonth + "/" + (fromYear - 1);
		Date fromDate = Utility.stringToDate(fromDates);
		int toDay = getDayFromDate(tDate);
		int toMonth = getMonthFromDate(tDate);
		int toYear = getYearFromDate(tDate);
		String toDates = toDay + "/" + toMonth + "/" + (toYear - 1);
		Date toDate = Utility.stringToDate(toDates);
		Organisation org=new Organisation();
		List<Long> lookupId=new ArrayList<>();
		org.setOrgid(orgId);
		final Long coaReceiptLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.LookUp.INDIVIDUAL, PrefixConstants.PREFIX_VALUE_IELA, orgId);
		List<LookUp> coaReceiptLookupIds=CommonMasterUtility.getListLookup(PrefixConstants.PREFIX_VALUE_IELA, org).stream().filter(coa->coa.getLookUpCode().equals(PrefixConstants.LookUp.INDIVIDUAL)||coa.getLookUpCode().equals(MainetConstants.FlagE)).collect(Collectors.toList());
		for(LookUp lkp:coaReceiptLookupIds) {
			lookupId.add(lkp.getLookUpId());
		}
		return accountFinancialReportRepository.queryClassifiedBudgetReceiptSideNonOperatingOpeningReportDataLevel3(fromDate, toDate,
				orgId, lookupId);

	}
	
	//operating Payment major heads
	private List<Object[]> queryClassifiedBudgetPaymentSideOpeningPreviousReportDataLevel3(Date frmDate, Date tDate, Long orgId,
			Long codcofdetId) {
     	// TODO Auto-generated method stub
		int fromDay = getDayFromDate(frmDate);
		int fromMonth = getMonthFromDate(frmDate);
		int fromYear = getYearFromDate(frmDate);
		String fromDates = fromDay + "/" + fromMonth + "/" + (fromYear - 1);
		Date fromDate = Utility.stringToDate(fromDates);
		int toDay = getDayFromDate(tDate);
		int toMonth = getMonthFromDate(tDate);
		int toYear = getYearFromDate(tDate);
		String toDates = toDay + "/" + toMonth + "/" + (toYear - 1);
		Date toDate = Utility.stringToDate(toDates);
		final Long coaPaymentLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.NEC.EMPLOYEE,
				PrefixConstants.PREFIX_VALUE_IELA, orgId);
		return accountFinancialReportRepository.queryClassifiedBudgetPaymentSideOperatingOpeningReportDataLevel3(fromDate, toDate,
				orgId, coaPaymentLookupId);
	}
	
	//non operating payment
	private List<Object[]> queryClassifiedBudgetPaymentSideNonOpeningPreviousReportDataLevel3(Date frmDate, Date tDate, Long orgId,
			Long codcofdetId) {
		// TODO Auto-generated method stub
		int fromDay = getDayFromDate(frmDate);
		int fromMonth = getMonthFromDate(frmDate);
		int fromYear = getYearFromDate(frmDate);
		String fromDates = fromDay + "/" + fromMonth + "/" + (fromYear - 1);
		Date fromDate = Utility.stringToDate(fromDates);
		int toDay = getDayFromDate(tDate);
		int toMonth = getMonthFromDate(tDate);
		int toYear = getYearFromDate(tDate);
		String toDates = toDay + "/" + toMonth + "/" + (toYear - 1);
		Date toDate = Utility.stringToDate(toDates);
		Organisation org=new Organisation();
		List<Long> lookupId=new ArrayList<>();
		org.setOrgid(orgId);
		final Long coaReceiptLookupId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.LookUp.INDIVIDUAL, PrefixConstants.PREFIX_VALUE_IELA, orgId);
		List<LookUp> coaReceiptLookupIds=CommonMasterUtility.getListLookup(PrefixConstants.PREFIX_VALUE_IELA, org).stream().filter(coa->coa.getLookUpCode().equals(PrefixConstants.LookUp.INDIVIDUAL)||coa.getLookUpCode().equals(MainetConstants.FlagE)).collect(Collectors.toList());
		for(LookUp lkp:coaReceiptLookupIds) {
			lookupId.add(lkp.getLookUpId());
		}
		return accountFinancialReportRepository.queryClassifiedBudgetPaymentSideNonOperatingOpeningReportDataLevel3(fromDate, toDate,
				orgId, lookupId);
	}
	
	private Map<Long, String> getFunctionList(){
		final Organisation superUserOrganization = ApplicationSession.getInstance().getSuperUserOrganization();
		final int langId = UserSession.getCurrent().getLanguageId();
		final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
		Long defaultOrgId = null;
		if (isDafaultOrgExist) {
			defaultOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
		} else {
			defaultOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		}
		try {
		return tbAcFunctionMasterService.getFunctionMasterStatusLastLevels(defaultOrgId,
				superUserOrganization, langId);
		}catch(Exception e) {
			LOGGER.error("Error while fetching data for function",e);
			return null;
		}
	}
	 private Map<Long, String> getDepartmentList() {
		final Map<Long, String> deptMap = new LinkedHashMap<>(0);
		List<Object[]> department = null;
		department = deptService.getAllDeptTypeNames();
		for (final Object[] dep : department) {
			if (dep[0] != null) {
				deptMap.put((Long) (dep[0]), (String) dep[1]);
			}
		}
	  return deptMap;
	}
	 
	public void getSecondaryHeadCodeDetail(final ModelMap model, final long orgId) {
		SecondarySecondaryHeadCodeDTO dto = null;
		SecondarySecondaryHeadCodeDTO ReportDto = new SecondarySecondaryHeadCodeDTO();
		List<SecondarySecondaryHeadCodeDTO> ListData = new ArrayList<>();
		List<Object[]> reportData = accountFinancialReportRepository.getScondaryCodeDetails(orgId);
		if (CollectionUtils.isNotEmpty(reportData)) {
			for (Object[] obj : reportData) {
				dto = new SecondarySecondaryHeadCodeDTO();
				dto.setFunction(obj[3] + "-" + obj[4]);
				dto.setPrimaryHeadCode(obj[5] + "-" + obj[6]);
				dto.setSecondaryHeadCode((String) obj[9]);
				dto.setStatus((String) obj[10]);
				ListData.add(dto);

			}
		}
		ReportDto.setSecondList(ListData);
		model.addAttribute(REPORT_LIST, ReportDto);

	}
	private List<AccountFinancialReportDTO> setPreviousRPRReportDate(List<Object[]> rprRecieptPaymentList,List<AccountFinancialReportDTO> finDtoList,Map<String,BigDecimal> rprMap,String isSecoundary) {
		final List<AccountFinancialReportDTO> openNonopenpayRecieptList = new ArrayList<>();
		BigDecimal sumBudgetAmountRecieptPaymentPrevious = new BigDecimal(
				MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		if(CollectionUtils.isNotEmpty(rprRecieptPaymentList)) {
			for(Object[] opnPrevList:rprRecieptPaymentList) {
				boolean flag =true;
				Map<String,String> map=new HashMap<String, String>();
				for(AccountFinancialReportDTO openPrevDto:finDtoList){
					map.put(openPrevDto.getAccountCode(), openPrevDto.getBalanceRecoverableIndianCurrency());
					if(openPrevDto.getBalanceRecoverableIndianCurrency()==null&&(openPrevDto.getAccountCode()!=null&&openPrevDto.getAccountCode().equals(opnPrevList[0]))) {
						
						sumBudgetAmountRecieptPaymentPrevious = sumBudgetAmountRecieptPaymentPrevious
								.add(new BigDecimal(opnPrevList[2].toString()));
						openPrevDto.setBalanceRecoverableIndianCurrency(
								CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(
										opnPrevList[2].toString())));
						flag=false;
					}
				}
				 if(StringUtils.isBlank(map.get(opnPrevList[0]))&&(isSecoundary.equalsIgnoreCase("N")&&flag)){
					final AccountFinancialReportDTO openingPayDTO = new AccountFinancialReportDTO();
					openingPayDTO.setAccountCode(opnPrevList[0].toString());
					sumBudgetAmountRecieptPaymentPrevious = sumBudgetAmountRecieptPaymentPrevious
							.add(new BigDecimal(opnPrevList[2].toString()));
					openingPayDTO.setAccountHead(opnPrevList[1].toString());
					openingPayDTO.setBalanceRecoverableIndianCurrency(
							CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(
									opnPrevList[2].toString())));
					openNonopenpayRecieptList.add(openingPayDTO);
				}
			}
		}
		if(rprMap!=null&&rprMap.get(MainetConstants.AccountBillEntry.AMOUNT)!=null) {
			BigDecimal bdAmt=rprMap.get(MainetConstants.AccountBillEntry.AMOUNT);
			rprMap.put(MainetConstants.AccountBillEntry.AMOUNT, bdAmt.add(sumBudgetAmountRecieptPaymentPrevious));	
		}else {
			rprMap.put(MainetConstants.AccountBillEntry.AMOUNT, sumBudgetAmountRecieptPaymentPrevious);		
		}
		return openNonopenpayRecieptList;
	}
}
