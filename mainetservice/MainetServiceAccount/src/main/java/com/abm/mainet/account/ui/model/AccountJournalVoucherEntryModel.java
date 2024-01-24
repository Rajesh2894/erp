
package com.abm.mainet.account.ui.model;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.domain.AccountDepositEntity;
import com.abm.mainet.account.dto.AccountJournalVoucherEntryBean;
import com.abm.mainet.account.dto.AccountJournalVoucherEntryDetailsBean;
import com.abm.mainet.account.service.AccountBudgetProjectedRevenueEntryService;
import com.abm.mainet.account.service.AccountContraVoucherEntryService;
import com.abm.mainet.account.service.AccountDepositService;
import com.abm.mainet.account.service.AccountJournalVoucherService;
import com.abm.mainet.account.service.BudgetCodeService;
import com.abm.mainet.account.ui.validator.AccountJournalVoucherValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.dto.SecondaryheadMaster;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.DepartmentLookUp;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author deepika.pimpale
 *
 */
@Component
@Scope("session")
public class AccountJournalVoucherEntryModel extends AbstractEntryFormModel<AccountJournalVoucherEntryBean> {

	private static final long serialVersionUID = 1960563305386337134L;

	@Resource
	private AccountJournalVoucherService journalVoucherService;

	@Resource
	private AccountBudgetProjectedRevenueEntryService budgetProjectedRevenue;

	@Resource
	private BudgetCodeService budgetCodeService;

	@Resource
	private ILocationMasService locMasService;

	@Resource
	private AccountDepositService accountDepositService;

	@Resource
	private BudgetCodeService accountBudgetCodeService;

	@Resource
	private DepartmentService departmentService;
	
	@Resource
	private SecondaryheadMasterService secondaryheadMasterService;

	private String mode;

	private String modeView;

	private String isAuthApplication;

	private List<AccountJournalVoucherEntryBean> searchData = new ArrayList<>();

	private AccountDepositEntity accountDepositEntity = new AccountDepositEntity();

	private String voucherNo;

	private String accountHead;
	private String voucherDates;
	private String fromDate;
	private String toDate;
	private Long faYearId;
	private BigDecimal openDr;
	private BigDecimal openCr;
	private Map<Long, String> errorMap=new HashMap<>();
	@Transient
	private String uploadFileName;
	
	private Map<Long, String> accountHeadCodeList = new HashMap<>();

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public Long getFaYearId() {
		return faYearId;
	}

	public void setFaYearId(Long faYearId) {
		this.faYearId = faYearId;
	}

	private AccountJournalVoucherEntryBean oAccountJournalVoucherEntryBean;
	private AccountJournalVoucherEntryDetailsBean oAccountJournalVoucherEntryDetailsBean;
	private List<LookUp> voucherSubTypeList;

	@Resource
	private AccountContraVoucherEntryService contraVoucherService;

	@Resource
	private ILocationMasService locationMasService;

	public List<AccountJournalVoucherEntryBean> getSearchVoucherData(

			final Long voucherType, final Date fromDate, final Date toDate, final String dateType,
			final String authStatus, final BigDecimal amount, final String refNo, final Long orgId,
			String urlIdentifyFlag) {
		final List<AccountJournalVoucherEntryBean> data = journalVoucherService.getSearchVoucherData(voucherType,
				fromDate, toDate, dateType, authStatus, amount, orgId, refNo, urlIdentifyFlag);

		return data;
	}

	public List<AccountJournalVoucherEntryBean> getGridVoucherData(final Long orgId) {
		final List<AccountJournalVoucherEntryBean> data = journalVoucherService.getGridVoucherData(orgId);

		return data;
	}

	@Override
	public void editForm(final long rowId) {

		final AccountJournalVoucherEntryBean master = journalVoucherService.getAccountVoucherDataBeanById(rowId);
		
		if (master != null) {
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
				LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(master.getVouTypeCpdId(),
						UserSession.getCurrent().getOrganisation());
				if ("RV".equalsIgnoreCase(lookup.getLookUpCode()))
					master.setVouReferenceNo(
							departmentService.getDeptCode(master.getDpDeptid()).concat(master.getVouReferenceNo()));
			}
		}
		final Long depositSubType = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				MainetConstants.AccountJournalVoucherEntry.VOU_SUB_TYPE_DFA, MainetConstants.AccountBillEntry.TDP,
				master.getOrg());
		//Disabled the form when entry type is not manual #132548
		final Long checkIfManualVoucher = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				 PrefixConstants.AccountJournalVoucherEntry.MAN,AccountPrefix.VET.toString(),
				master.getOrg());
		
		if (depositSubType.equals(master.getVouSubtypeCpdId()) || !(checkIfManualVoucher.equals(master.getEntryType()))){
			master.setDepSubTypeFlag(MainetConstants.CommonConstants.CHAR_Y);

		}

		if ((master.getAuthoFlg() != null) && MainetConstants.MENU.Y.equals(master.getAuthoFlg())) {
			setIsAuthApplication(MainetConstants.MENU.Y);
		}
		if ((master.getDetails() != null) && !master.getDetails().isEmpty()) {
			Collections.sort(master.getDetails(), AccountJournalVoucherEntryDetailsBean.compareOnvoutDetId);
		}
		String voucherType = null;
		String transType = null;
		Map<Long, String> budgetCode = new HashMap<>();
		for (final AccountJournalVoucherEntryDetailsBean detail : master.getDetails()) {
			voucherType = CommonMasterUtility.getNonHierarchicalLookUpObject(master.getVouTypeCpdId(),
					UserSession.getCurrent().getOrganisation()).getLookUpCode();
			transType = CommonMasterUtility
					.getNonHierarchicalLookUpObject(detail.getDrcrCpdId(), UserSession.getCurrent().getOrganisation())
					.getLookUpCode();
			budgetCode = populateAccountHead(voucherType, transType);

			BigDecimal amount = detail.getVoudetAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN);
			detail.setVoudetAmt(amount);

			detail.setAccountHeadCodeList(budgetCode);
		}
		setEntity(master);

	}

	@Override
	public void viewForm(final long rowId) {

		final AccountJournalVoucherEntryBean master = journalVoucherService.getAccountVoucherDataBeanById(rowId);

		if (master != null) {
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
				LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(master.getVouTypeCpdId(),
						UserSession.getCurrent().getOrganisation());
				if ("RV".equalsIgnoreCase(lookup.getLookUpCode()))
					master.setVouReferenceNo(
							departmentService.getDeptCode(master.getDpDeptid()).concat(master.getVouReferenceNo()));
			}
		}
		if ((master.getAuthoFlg() != null) && MainetConstants.MENU.Y.equals(master.getAuthoFlg())) {
			setIsAuthApplication(MainetConstants.MENU.Y);
		}
		if ((master.getDetails() != null) && !master.getDetails().isEmpty()) {
			Collections.sort(master.getDetails(), AccountJournalVoucherEntryDetailsBean.compareOnvoutDetId);
		}
		String voucherType = null;
		String transType = null;
		Map<Long, String> budgetCode = new HashMap<>();
		for (final AccountJournalVoucherEntryDetailsBean detail : master.getDetails()) {
			/*
			 * voucherType =
			 * CommonMasterUtility.getNonHierarchicalLookUpObject(master.getVouTypeCpdId(),
			 * UserSession.getCurrent().getOrganisation()).getLookUpCode(); transType =
			 * CommonMasterUtility .getNonHierarchicalLookUpObject(detail.getDrcrCpdId(),
			 * UserSession.getCurrent().getOrganisation()) .getLookUpCode();
			 */
			detail.setAcHeadCode(secondaryheadMasterService.findSacHeadCodeBySacHeadId(detail.getSacHeadId()));
			BigDecimal amount = detail.getVoudetAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN);
			detail.setVoudetAmt(amount);

			//detail.setAccountHeadCodeList(budgetCode);
		}
		//budgetCode = populateBudgetHead(voucherType, transType);
		//setAccountHeadCodeList(budgetCode);
		setEntity(master);
	}

	public Map<Long, String> populateBudgetHead(final String voucherType, final String transType) {
		final Map<Long, String> budgetCode = new HashMap<>();

		final List<SecondaryheadMaster> chList = accountBudgetCodeService
				.findByAllGridData(UserSession.getCurrent().getOrganisation().getOrgid());

		if (chList != null) {
			for (final SecondaryheadMaster bean : chList) {
				budgetCode.put(bean.getSacHeadId(), bean.getAcHeadCode());
			}
		}
		return budgetCode;
	}

	public void depCreateForm(final Long depId) {

		final AccountJournalVoucherEntryBean master = new AccountJournalVoucherEntryBean();

		final AccountDepositEntity depositList = accountDepositService.fidbyId(depId);
		setAccountDepositEntity(depositList);

		if ((master.getAuthoFlg() != null) && MainetConstants.MENU.Y.equals(master.getAuthoFlg())) {
			setIsAuthApplication(MainetConstants.MENU.Y);
		}

		master.setDepId(depositList.getDepId());
		master.setBalAmount(depositList.getDepRefundBal());
		master.setDepositFlag(MainetConstants.MENU.Y);

		if ((master.getDetails() != null) && !master.getDetails().isEmpty()) {
			Collections.sort(master.getDetails(), AccountJournalVoucherEntryDetailsBean.compareOnvoutDetId);
		}

		Long dfalookUpId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				MainetConstants.AccountJournalVoucherEntry.VOU_SUB_TYPE_DFA, MainetConstants.AccountBillEntry.TDP,
				depositList.getOrgid());
		master.setVoucherSubType(dfalookUpId);

		final Long voucherTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.AccountJournalVoucherEntry.JV, AccountPrefix.VOT.toString(), depositList.getOrgid());
		master.setVouTypeCpdId(voucherTypeId);
		master.setVouReferenceNo(depositList.getDepNo().toString());

		master.setNarration("Vendor code : " + depositList.getTbVendormaster().getVmVendorcode() + ","
				+ " Vendor Name : " + depositList.getTbVendormaster().getVmVendorname() + "," + "Deposit No. : "
				+ depositList.getDepNo() + "," + " Deposit Date : "
				+ Utility.dateToString(depositList.getDepReceiptdt()));
		master.setTransactionDate(Utility.dateToString(depositList.getDepReceiptdt()));
		final List<AccountJournalVoucherEntryDetailsBean> beanList = new ArrayList<>();
		final AccountJournalVoucherEntryDetailsBean bean = new AccountJournalVoucherEntryDetailsBean();

		final Long drCrId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.AccountJournalVoucherEntry.DR, PrefixConstants.DCR, depositList.getOrgid());
		bean.setDrcrCpdId(drCrId);
		// final AccountBudgetCodeDto budgetCodeDto = new AccountBudgetCodeDto();
		// budgetCodeDto.setPrBudgetCodeid(depositList.getSacHeadId());
		// bean.setBudgetCode(budgetCodeDto);
		bean.setSacHeadId(depositList.getSacHeadId());
		master.setDepSacHeadId(depositList.getSacHeadId());
		bean.setVoudetAmt(depositList.getDepRefundBal());
		beanList.add(bean);
		master.setDetails(beanList);

		final AccountJournalVoucherEntryDetailsBean bean1 = new AccountJournalVoucherEntryDetailsBean();
		final Long drCrId1 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				PrefixConstants.AccountJournalVoucherEntry.CR, PrefixConstants.DCR, depositList.getOrgid());
		bean1.setDrcrCpdId(drCrId1);
		// final AccountBudgetCodeDto budgetCodeDto1 = new AccountBudgetCodeDto();
		// budgetCodeDto1.setPrBudgetCodeid(depositList.getSacHeadId());
		// bean1.setBudgetCode(budgetCodeDto1);
		// bean1.setSacHeadId(depositList.getSacHeadId());
		bean1.setVoudetAmt(depositList.getDepRefundBal());
		beanList.add(bean1);
		master.setDetails(beanList);

		String voucherType = null;
		String transType = null;
		Map<Long, String> budgetCode = new HashMap<>();
		for (final AccountJournalVoucherEntryDetailsBean detail : master.getDetails()) {
			voucherType = CommonMasterUtility.getNonHierarchicalLookUpObject(master.getVouTypeCpdId(),
					UserSession.getCurrent().getOrganisation()).getLookUpCode();
			transType = CommonMasterUtility
					.getNonHierarchicalLookUpObject(detail.getDrcrCpdId(), UserSession.getCurrent().getOrganisation())
					.getLookUpCode();
			budgetCode = populateAccountHead(voucherType, transType);
			detail.setAccountHeadCodeList(budgetCode);
		}
		setEntity(master);
	}

	public void addForm(final Long orgId) {

		final AccountJournalVoucherEntryBean master = new AccountJournalVoucherEntryBean();
		Long dfalookUpId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				MainetConstants.AccountJournalVoucherEntry.VOU_SUB_TYPE_TEC, MainetConstants.AccountBillEntry.TDP,
				orgId);
		master.setVoucherSubType(dfalookUpId);
		setEntity(master);
	}

	public List<DepartmentLookUp> getDepartmentList() {
		return getDepartmentLookUp();
	}

	public List<LookUp> getVoucherType(Organisation org) {
		final LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(
				PrefixConstants.AccountJournalVoucherEntry.JV, PrefixConstants.ContraVoucherEntry.VOT, org);
		final List<LookUp> lookUpList = new ArrayList<>();
		if (lookup != null) {
			lookUpList.add(lookup);
		}
		return lookUpList;
	}

	@Override
	public boolean saveForm() {
		final AccountJournalVoucherValidator validator = new AccountJournalVoucherValidator();
		validator.validate(getEntity(), getBindingResult());

		final AccountJournalVoucherEntryBean dto = getEntity();
		Map<Long, String> accountHeadMap = null;
		boolean found;
		boolean errorStatus = false;
		final String voucherType = CommonMasterUtility
				.getNonHierarchicalLookUpObject(dto.getVouTypeCpdId(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode();

		for (final AccountJournalVoucherEntryDetailsBean det : dto.getDetails()) {
			found = checkIfExist(det.getSacHeadId(), dto.getDetails());
			if (found) {
				if (!errorStatus) {
					getBindingResult().addError(new org.springframework.validation.FieldError(
							MainetConstants.CommonConstants.COMMAND, MainetConstants.BLANK, null, false,
							new String[] { MainetConstants.ERRORS }, null,
							ApplicationSession.getInstance().getMessage("account.journal.voucher.saveform.maessage")));
					errorStatus = true;
				}
			}
		}
		for (final AccountJournalVoucherEntryDetailsBean det : dto.getDetails()) {
			accountHeadMap = new HashMap<Long, String>(0);
			final String transType = CommonMasterUtility
					.getNonHierarchicalLookUpObject(det.getDrcrCpdId(), UserSession.getCurrent().getOrganisation())
					.getLookUpCode();
			accountHeadMap = populateAccountHead(voucherType, transType);
			det.setAccountHeadCodeList(accountHeadMap);
		}

		if(dto.getTransactionDate() != null && getAccountDepositEntity().getDepReceiptdt() != null) {
			final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE,
					MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
					UserSession.getCurrent().getOrganisation());
			
			String transactDate = dto.getTransactionDate();
			String sliDate = lookUp.getOtherField();
			Date transactionalDate = null;
			Date SLIDate = null;
			try {
				transactionalDate = new SimpleDateFormat("dd/MM/yyyy").parse(transactDate);
				SLIDate = new SimpleDateFormat("dd/MM/yyyy").parse(sliDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (Utility.compareDate(transactionalDate,getAccountDepositEntity().getDepReceiptdt())) {
				addValidationError(
						ApplicationSession.getInstance().getMessage("Transactional Date cannot be less than deposit date"));
			}
			if(Utility.compareDate(transactionalDate,SLIDate)) {
				addValidationError(
						ApplicationSession.getInstance().getMessage("Transactional Date cannot be less than SLI Date ")+" SLI Date is: "+" "+sliDate);
			}
		}
		
		
		if (hasValidationErrors()) {
			return false;
		}

		final List<AccountJournalVoucherEntryDetailsBean> finalList = new ArrayList<>();
		for (final AccountJournalVoucherEntryDetailsBean detail : dto.getDetails()) {
			if (MainetConstants.MENU.D.equals(detail.getDeleted())) {
				journalVoucherService.deleteRow(detail.getVoudetId());
			} else {
				finalList.add(detail);
			}
		}
		dto.setDetails(finalList);
		setUpdateAuditFields(dto, getMode());
		if ((dto.getAuthoFlg() != null) && MainetConstants.MENU.Y.equals(dto.getAuthoFlg())) {
			String voucherNo = MainetConstants.BLANK;
			if (dto.getVouTypeCpdId() != null
					&& (dto.getTransactionAuthDate() != null && !dto.getTransactionAuthDate().isEmpty())) {
				voucherNo = journalVoucherService.generateVoucherNumber(voucherType, dto.getOrg(),
						dto.getTransactionAuthDate());
			} else {
				voucherNo = journalVoucherService.generateVoucherNumber(voucherType, dto.getOrg(),
						dto.getTransactionDate());
			}
			dto.setVouNo(voucherNo);
		}

		setoAccountJournalVoucherEntryBean(dto);

		if (getMode().equals(MainetConstants.MENU.A)) {
			boolean temp = journalVoucherService.saveAccountJournalVoucherEntry(dto);
			if (!temp) {
				this.addValidationError("This financial year is already closed!");
				return temp;
			}
		} else {
			boolean temp = journalVoucherService.updateAccountJournalVoucherEntry(dto);
			if (!temp) {
				this.addValidationError(
						"Deposite transfer amount should be equal or less than deposit balance amount.");
				return temp;
			}
		}
		if (dto.getAuthoFlg().equals(AccountConstants.Y.getValue())) {
			// Task #7144
			Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
					MainetConstants.AccountJournalVoucherEntry.VOU_SUB_TYPE_DFA, AccountPrefix.TDP.toString(),
					dto.getOrg());
			if (dto.getVoucherSubType().equals(voucherSubTypeId)) {
				journalVoucherService.updateVoucherIdInDeposit(Long.valueOf(dto.getVouReferenceNo()), dto.getVouId(),
						dto.getOrg());
			}

		}

		setEntity(dto);
		return true;
	}

	/**
	 * @param list
	 * @param det
	 * @return
	 */
	private boolean checkIfExist(final Long sacHeadId, final List<AccountJournalVoucherEntryDetailsBean> list) {

		int cnt = 0;
		for (final AccountJournalVoucherEntryDetailsBean details : list) {
			if (!MainetConstants.MENU.D.equals(details.getDeleted())) {
				if (details.getSacHeadId() == sacHeadId) {
					cnt++;
				}
			}
		}
		if (cnt > 1) {
			return true;
		}

		return false;
	}

	@Override
	public void delete(final long rowId) {
		journalVoucherService.delete(rowId);
	}

	/**
	 * @param dto
	 * @param mode2
	 */
	private void setUpdateAuditFields(final AccountJournalVoucherEntryBean dto, final String updateMode) {
		final Employee emp = UserSession.getCurrent().getEmployee();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final int langId = UserSession.getCurrent().getLanguageId();
		if (dto.getVouId() == 0L) {
			dto.setOrg(orgId);
			dto.setLangId(langId);
			dto.setCreatedBy(emp.getEmpId());
			dto.setLmodDate(new Date());
			Organisation org = new Organisation();
			org.setOrgid(orgId);
			final LookUp lkp = CommonMasterUtility.getValueFromPrefixLookUp(
					PrefixConstants.AccountJournalVoucherEntry.VT, PrefixConstants.AccountJournalVoucherEntry.AUT, org);
			if (lkp == null) {
				dto.setAuthoDate(new Date());
				dto.setAuthoId(emp.getEmpId());
				dto.setAuthoFlg(MainetConstants.MENU.Y);
				dto.setVouPostingDate(new Date());
			} else {
				dto.setAuthoFlg(MainetConstants.MENU.N);
			}
			dto.setLgIpMac(dto.getLgIpMac());
			final Long departmentId = departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString());
			dto.setDpDeptid(departmentId);
			if (emp.getTbLocationMas() != null) {
				final Long fieldId = locationMasService.getcodIdRevLevel1ByLocId(emp.getTbLocationMas().getLocId(),
						orgId);
				dto.setFieldId(fieldId);
			}
		} else {
			dto.setUpdatedby(emp.getEmpId());
			dto.setUpdatedDate(new Date());
			dto.setLgIpMacUpd(dto.getLgIpMac());

		}

		for (final AccountJournalVoucherEntryDetailsBean det : dto.getDetails()) {
			if (det.getVoudetId() == 0L) {
				det.setOrgId(orgId);
				det.setLangId(UserSession.getCurrent().getLanguageId());
				det.setCreatedBy(emp.getEmpId());
				det.setLmodDate(new Date());
				det.setLgIpMac(dto.getLgIpMac());
				det.setMaster(dto);
			} else {
				det.setUpdatedBy(emp.getEmpId());
				det.setUpdatedDate(new Date());
				det.setLgIpMacUpd(dto.getLgIpMac());
			}
		}

	}

	/**
	 * @return the voucherNo
	 */
	public String getVoucherNo() {
		return voucherNo;
	}

	/**
	 * @param voucherNo
	 *            the voucherNo to set
	 */
	public void setVoucherNo(final String voucherNo) {
		this.voucherNo = voucherNo;
	}

	/**
	 * @return the searchData
	 */
	public List<AccountJournalVoucherEntryBean> getSearchData() {
		return searchData;
	}

	/**
	 * @param searchData
	 *            the searchData to set
	 */
	public void setSearchData(final List<AccountJournalVoucherEntryBean> searchData) {
		this.searchData = searchData;
	}

	public AccountDepositEntity getAccountDepositEntity() {
		return accountDepositEntity;
	}

	public void setAccountDepositEntity(AccountDepositEntity accountDepositEntity) {
		this.accountDepositEntity = accountDepositEntity;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(final String mode) {
		this.mode = mode;
	}

	public String getModeView() {
		return modeView;
	}

	public void setModeView(final String modeView) {
		this.modeView = modeView;
	}

	/**
	 * @return the isAuthApplication
	 */
	public String getIsAuthApplication() {
		return isAuthApplication;
	}

	/**
	 * @param isAuthApplication
	 *            the isAuthApplication to set
	 */
	public void setIsAuthApplication(final String isAuthApplication) {
		this.isAuthApplication = isAuthApplication;
	}

	public Map<Long, String> populateAccountHead(final String voucherType, final String transType) {
		final Map<Long, String> budgetCode = new HashMap<>();
		List<Object[]> budgetList = new ArrayList<>();
		final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
				PrefixConstants.LookUp.ACN, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		final Long activeStatusId = lookUpFieldStatus.getLookUpId();
		switch (voucherType) {
		case MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV:
			if ((PrefixConstants.AccountJournalVoucherEntry.CR).equalsIgnoreCase(transType)) {
				budgetList = budgetProjectedRevenue
						.getAccountHeadCodeInRevenue(UserSession.getCurrent().getOrganisation().getOrgid(),activeStatusId);
			} else {
				budgetList = budgetProjectedRevenue
						.getAccountHeadCodeInRevenue(UserSession.getCurrent().getOrganisation().getOrgid(),activeStatusId);
			}
			break;

		case PrefixConstants.AccountJournalVoucherEntry.JV:
			budgetList = budgetCodeService.getVoucherAccountHead(UserSession.getCurrent().getOrganisation().getOrgid(),
					CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.TbAcVendormaster.VD,
							PrefixConstants.ACCOUNT_MASTERS.SECONDARY_MASTER.SECONDARY_LOOKUPCODE,
							ApplicationSession.getInstance().getSuperUserOrganization().getOrgid()),
					CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
							PrefixConstants.ACCOUNT_MASTERS.SECONDARY_MASTER.SECONDARY_OTHER_CPD_VALUE,
							PrefixConstants.ACCOUNT_MASTERS.SECONDARY_MASTER.SECONDARY_LOOKUPCODE,
							ApplicationSession.getInstance().getSuperUserOrganization().getOrgid()),activeStatusId);

			break;

		case MainetConstants.AccountJournalVoucherEntry.CV:
			budgetList = budgetCodeService.getVoucherAccountHead(UserSession.getCurrent().getOrganisation().getOrgid(),
					CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.VoucherTemplate.CA,
							PrefixConstants.ACCOUNT_MASTERS.SECONDARY_MASTER.SECONDARY_LOOKUPCODE,
							ApplicationSession.getInstance().getSuperUserOrganization().getOrgid()),
					CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.AccountJournalVoucherEntry.BK,
							PrefixConstants.ACCOUNT_MASTERS.SECONDARY_MASTER.SECONDARY_LOOKUPCODE,
							ApplicationSession.getInstance().getSuperUserOrganization().getOrgid()),activeStatusId);
			break;

		case MainetConstants.AccountJournalVoucherEntry.PV:
			if ((PrefixConstants.AccountJournalVoucherEntry.CR).equalsIgnoreCase(transType)) {
				budgetList = budgetProjectedRevenue
						.getAccountHeadCodeInRevenue(UserSession.getCurrent().getOrganisation().getOrgid(),activeStatusId);
			} else {
				budgetList = budgetProjectedRevenue
						.getAccountHeadCodeInRevenue(UserSession.getCurrent().getOrganisation().getOrgid(),activeStatusId);
			}
			break;
		default:
			break;
		}
		for (final Object[] budget : budgetList) {
			budgetCode.put(Long.valueOf(budget[0].toString()), budget[1].toString());
		}

		return budgetCode;
	}


	public AccountJournalVoucherEntryBean getoAccountJournalVoucherEntryBean() {
		return oAccountJournalVoucherEntryBean;
	}

	public void setoAccountJournalVoucherEntryBean(
			final AccountJournalVoucherEntryBean oAccountJournalVoucherEntryBean) {
		this.oAccountJournalVoucherEntryBean = oAccountJournalVoucherEntryBean;
	}

	public AccountJournalVoucherEntryDetailsBean getoAccountJournalVoucherEntryDetailsBean() {
		return oAccountJournalVoucherEntryDetailsBean;
	}

	public void setoAccountJournalVoucherEntryDetailsBean(
			final AccountJournalVoucherEntryDetailsBean oAccountJournalVoucherEntryDetailsBean) {
		this.oAccountJournalVoucherEntryDetailsBean = oAccountJournalVoucherEntryDetailsBean;
	}

	public List<LookUp> getVoucherSubTypeList() {
		return voucherSubTypeList;
	}

	public void setVoucherSubTypeList(final List<LookUp> voucherSubTypeList) {
		this.voucherSubTypeList = voucherSubTypeList;
	}

	public void setAccountJournalVoucherEntryBean(List<LookUp> voucherSubTypeList2) {
		this.voucherSubTypeList = voucherSubTypeList2;
	}

	public String getAccountHead() {
		return accountHead;
	}

	public void setAccountHead(String accountHead) {
		this.accountHead = accountHead;
	}

	public String getVoucherDates() {
		return voucherDates;
	}

	public void setVoucherDates(String voucherDates) {
		this.voucherDates = voucherDates;
	}

	public BigDecimal getOpenDr() {
		return openDr;
	}

	public void setOpenDr(BigDecimal openDr) {
		this.openDr = openDr;
	}

	public BigDecimal getOpenCr() {
		return openCr;
	}

	public void setOpenCr(BigDecimal openCr) {
		this.openCr = openCr;
	}

	public Map<Long, String> getErrorMap() {
		return errorMap;
	}

	public void setErrorMap(Map<Long, String> errorMap) {
		this.errorMap = errorMap;
	}

	public Map<Long, String> getAccountHeadCodeList() {
		return accountHeadCodeList;
	}

	public void setAccountHeadCodeList(Map<Long, String> accountHeadCodeList) {
		this.accountHeadCodeList = accountHeadCodeList;
	}

}
