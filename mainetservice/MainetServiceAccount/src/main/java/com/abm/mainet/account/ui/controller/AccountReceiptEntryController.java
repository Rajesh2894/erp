
package com.abm.mainet.account.ui.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.account.dto.AccountDepositBean;
import com.abm.mainet.account.dto.AccountInvestmentMasterDto;
import com.abm.mainet.account.dto.AccountReceiptReportMasDto;
import com.abm.mainet.account.dto.BankMasterDto;
import com.abm.mainet.account.dto.ReceiptReversalViewDTO;
import com.abm.mainet.account.dto.VoucherTemplateDTO;
import com.abm.mainet.account.service.AccountBudgetProjectedRevenueEntryService;
import com.abm.mainet.account.service.AccountContraVoucherEntryService;
import com.abm.mainet.account.service.AccountInvestmentService;
import com.abm.mainet.account.service.AccountReceiptEntryService;
import com.abm.mainet.account.service.AccountVoucherPostService;
import com.abm.mainet.account.service.AccountVoucherReversalService;
import com.abm.mainet.account.service.BankAccountService;
import com.abm.mainet.account.service.BudgetCodeService;
import com.abm.mainet.account.service.VoucherTemplateService;
import com.abm.mainet.account.ui.model.response.AccountReceiptEntryResponse;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CFCSchedulingCounterDet;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.acccount.dto.AccountFieldMasterBean;
import com.abm.mainet.common.integration.acccount.dto.BankAccountMasterDto;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptFeesDetBean;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountFundMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.mapper.TbDepartmentServiceMapper;
import com.abm.mainet.common.master.service.BankMasterService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.master.service.TbCustbanksService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.TbBankmasterService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.aspose.slides.pa2137a2a.tb;

/**
 * @author dharmendra.chouhan
 *
 */
@Controller
@RequestMapping("/AccountReceiptEntry.html")
public class AccountReceiptEntryController extends AbstractController {

	private static final String MAIN_ENTITY_NAME = "tbServiceReceiptMas";
	private static final String DEPARTMENT_LIST = "departmentlist";
	private static final String BANKACCOUNT_LIST = "bankaccountlist";
	private static final String PAYMENTMODE = "paymentMode";
	private static final String CUSTOMERBANKLIST = "customerBankList";
	private static final String JSP_FORM = "accountReceiptEntry/form";
	private static final String JSP_LIST = "accountReceiptEntry/list";
	private static final String SAVE_ACTION_CREATE = "AccountReceiptEntry.html?create";
	private List<TbServiceReceiptMasBean> listOfTaxBean;
	private static final String View = "View";
	private static final String PostFlag = "PostFlag";
	private static final String ReceiptReportJsp_Form = "ReceiptReport";
	private static final String PAYEE_LIST = "payeeList";
	private static final String TB_SERVICE_RECEIPTMAS_BEAN = "tbServiceReceiptMasBean";
	private static final String RECEIPT_VOU_TYPE = "recieptVouType";

	private static final Logger LOGGER = Logger.getLogger(AccountReceiptEntryController.class);

	@Resource
	private TbAcVendormasterService tbAcVendormasterService;

	@Resource
	private AccountReceiptEntryService accountReceiptEntryService;

	@Resource
	private BudgetCodeService budgetCodeService;

	@Resource
	private AccountFieldMasterService tbAcFieldMasterService;

	@Resource
	private TbCustbanksService tbCustbanksService;

	@Resource
	private ILocationMasService locMasService;

	@Resource
	private BankMasterService bankMasterService;

	@Resource
	private BankAccountService bankAccountService;

	@Resource
	private AccountBudgetProjectedRevenueEntryService accountBudgetProjectedRevenueEntryService;

	@Resource
	private AccountContraVoucherEntryService accountContraVoucherEntryService;

	@Resource
	private VoucherTemplateService voucherTemplateService;

	@Resource
	private TbBankmasterService banksMasterService;

	@Resource
	private TbDepartmentService tbDepartmentService;

	@Resource
	private TbTaxMasService tbTaxMasService;

	@Resource
	private AccountVoucherReversalService accountVoucherReversalService;

	@Resource
	private SecondaryheadMasterService tbAcSecondaryheadMasterService;

	@Resource
	private TbOrganisationService tbOrganisationService;

	@Resource
	private DepartmentService departmentService;

	@Resource
	private TbDepartmentServiceMapper tbDepartmentServiceMapper;

	@Resource
	private AccountVoucherPostService accountVoucherPostService;

	@Resource
	private AccountInvestmentService accountInvestmentService;
	
	@Resource
	private IReceiptEntryService iReceiptEntryService;
	
	@Resource
	private AccountFundMasterService tbAcFundMasterService;

	@Autowired
	private TbCfcApplicationMstService tbCfcservice;
	public AccountReceiptEntryController() {
		super(AccountReceiptEntryController.class, MAIN_ENTITY_NAME);
		log("AccountReceiptEntryController created.");
	}

	private void populateModel(final Model model, final TbServiceReceiptMasBean tbServiceReceiptMasBean,
			final FormMode formMode) {
		model.addAttribute(TB_SERVICE_RECEIPTMAS_BEAN, tbServiceReceiptMasBean);
		model.addAttribute("SudaEnv", Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA));
		final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				AccountConstants.AC.getValue(), PrefixConstants.VSS, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		final Long vendorStatus = lookUpVendorStatus.getLookUpId();
		final List<TbAcVendormaster> list = tbAcVendormasterService.getActiveVendors(orgid, vendorStatus);
		model.addAttribute(MainetConstants.VENDOR_MASTER.MAIN_LIST_NAME, list);
		List<TbDepartment>  departmentlist = new ArrayList<>();
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
			List<Department> entities = departmentService.getDepartments(orgid, MainetConstants.CommonConstants.ACTIVE);
			if (entities != null) {
				for (final Department dept : entities) {
					departmentlist.add(tbDepartmentServiceMapper.mapTbDepartmentEntityToTbDepartment(dept));
				}
			}
		} else {
			Department entities = departmentService.getDepartment(AccountConstants.AC.getValue(),
					MainetConstants.CommonConstants.ACTIVE);
			if (entities != null) {
				departmentlist.add(tbDepartmentServiceMapper.mapTbDepartmentEntityToTbDepartment(entities));
			}
		}

		tbServiceReceiptMasBean.setTemplateExistsFlag(checkTemplate());
		model.addAttribute(DEPARTMENT_LIST, departmentlist);
		List<Object[]> bankAccountList;
		final Map<Long, String> bankAccountMap = new HashMap<>();
		final Long statusId = CommonMasterUtility
				.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.PaymentMode.USER_ADUSTMENT, PrefixConstants.BAS, orgid);
		bankAccountList = banksMasterService.getActiveBankAccountList(orgid, statusId);
		for (final Object[] bankAc : bankAccountList) {
			bankAccountMap.put((Long) bankAc[0],
					bankAc[3] + MainetConstants.SEPARATOR + bankAc[1] + MainetConstants.SEPARATOR + bankAc[2]);
		}
		model.addAttribute(BANKACCOUNT_LIST, bankAccountMap);
		final List<LookUp> paymentModeList = CommonMasterUtility.getLookUps(AccountPrefix.PAY.name(),
				UserSession.getCurrent().getOrganisation());
		final List<LookUp> paymentMode1 = new ArrayList<>();
		for (final LookUp looUp : paymentModeList) {
			if (looUp.getOtherField() != null) {
				if (MainetConstants.MENU.Y.equals(looUp.getOtherField())) {
					paymentMode1.add(looUp);
				}
			}
		}
		final List<String> payeeList = accountReceiptEntryService.getPayeeNames(orgid);
		model.addAttribute(PAYEE_LIST, payeeList);
		model.addAttribute(PAYMENTMODE, paymentMode1);
		final List<BankMasterEntity> customerBankList = bankMasterService.getBankList();
		model.addAttribute(CUSTOMERBANKLIST, customerBankList);

		final List<LookUp> recieptVouType = CommonMasterUtility.getListLookup(
				MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV, UserSession.getCurrent().getOrganisation());
		List<LookUp> newReciptVouTypeList = new ArrayList<LookUp>();
		if(formMode.equals(FormMode.VIEW)) {
			newReciptVouTypeList.addAll(recieptVouType);
		}else {
		for (LookUp lookUp : recieptVouType) {
			LookUp newReceiptCategory = new LookUp();
			if (lookUp.getLookUpCode().equals("M") || lookUp.getLookUpCode().equals("A")
					|| lookUp.getLookUpCode().equals("P") || lookUp.getLookUpCode().equals("GRT")
					|| lookUp.getLookUpCode().equals("INV") || lookUp.getLookUpCode().equals("LNR")) {
				newReceiptCategory.setLookUpId(lookUp.getLookUpId());
				newReceiptCategory.setLookUpCode(lookUp.getLookUpCode());
				newReceiptCategory.setLookUpDesc(lookUp.getLookUpDesc());
				newReceiptCategory.setDescLangFirst(lookUp.getDescLangFirst());
				if (formMode == FormMode.CREATE) {
					if (lookUp.getDefaultVal() != null && !lookUp.getDefaultVal().isEmpty()) {
						if (lookUp.getDefaultVal().equals("Y")) {
							if (tbServiceReceiptMasBean.getRecCategoryType() == null
									|| tbServiceReceiptMasBean.getRecCategoryType().isEmpty()) {
								tbServiceReceiptMasBean.setRecCategoryTypeId(lookUp.getLookUpId());
							} else if (tbServiceReceiptMasBean.getRecCategoryType().equals("M")) {
								tbServiceReceiptMasBean.setRecCategoryTypeId(lookUp.getLookUpId());
							}
						}
					}
				}
				newReciptVouTypeList.add(newReceiptCategory);
			}
		 }
		}
		model.addAttribute(RECEIPT_VOU_TYPE, newReciptVouTypeList);

		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if (formMode == FormMode.CREATE) {
			model.addAttribute(MODE, MODE_CREATE);
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
			if (tbServiceReceiptMasBean.getRecCategoryType() == null
					|| tbServiceReceiptMasBean.getRecCategoryType().isEmpty()) {
				populateBudgetCodes(model);
			} else if (tbServiceReceiptMasBean.getRecCategoryType().equals("M")) {
				populateBudgetCodes(model);
			}
			populateListOfTbAcFieldMasterItems(model, orgId);
		} else {
			model.addAttribute(MODE, MODE_VIEW);
			populateViewBudgetCodes(model);
			populateListOfTbAcFieldMasterItems(model, orgId);
		}
	}

	private void populateViewBudgetCodes(Model model) {
		// TODO Auto-generated method stub
		final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		final Map<Long, String> budgetAcHeadMap = tbAcSecondaryheadMasterService.getDirectAcHeadCodeByOrgId(orgid);
		model.addAttribute(MainetConstants.AccountReceiptEntry.HEAD_CODE_MAP, budgetAcHeadMap);
	}

	/**
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping()
	public String list(final Model model) {
		log("Action 'list'");
		// for REST Webservice testing -Debugging

		listOfTaxBean = new ArrayList<>();
		final TbServiceReceiptMasBean tbServiceReceiptMasBean = new TbServiceReceiptMasBean();
		populateModel(model, tbServiceReceiptMasBean, FormMode.CREATE);
		return JSP_LIST;
	}

	@RequestMapping(params = "checkBudgetCodeForFeeMode")
	public @ResponseBody String checkBudgetCodeIdForFeeMode(final Model model,
			@RequestParam("cpdFeemode") final Long cpdFeemode) {

		final String budgetCodeStatus = MainetConstants.MENU.Y;
		return budgetCodeStatus;
	}

	@RequestMapping(params = "getVendorPhoneNoAndEmailId", method = RequestMethod.POST)
	public @ResponseBody List<String> getVendorPhoneNoAndEmailId(final TbServiceReceiptMasBean bean,
			final HttpServletRequest request, final Model model) {
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final Long vendorId = bean.getVmVendorId();
		final List<String> vendrList = new ArrayList<>();
		final List<Object[]> vendorlist = tbAcVendormasterService.getVendorPhoneNoAndEmailId(vendorId, orgId);
		if (!vendorlist.isEmpty()) {
			for (final Object[] objects : vendorlist) {
				if (objects[0] != null) {
					vendrList.add(objects[0].toString());
				} else {
					vendrList.add(MainetConstants.CommonConstant.BLANK);
				}
				if (objects[1] != null) {
					vendrList.add(objects[1].toString());
				} else {
					vendrList.add(MainetConstants.CommonConstant.BLANK);
				}
			}
		}
		return vendrList;
	}

	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String formForCreate(final Model model) {
		log("Action 'formForCreate'");
		final TbServiceReceiptMasBean tbServiceReceiptMasBean = new TbServiceReceiptMasBean();
		TbSrcptFeesDetBean bean = new TbSrcptFeesDetBean();
		List<TbSrcptFeesDetBean> beanList = new ArrayList<TbSrcptFeesDetBean>();
		beanList.add(bean);
		tbServiceReceiptMasBean.setReceiptFeeDetail(beanList);
		populateModel(model, tbServiceReceiptMasBean, FormMode.CREATE);
		model.addAttribute(MAIN_ENTITY_NAME, tbServiceReceiptMasBean);
		return JSP_FORM;
	}

	@RequestMapping(params = "createformFromAdvance", method = RequestMethod.POST)
	public String formForCreateFromAdvance(final HttpServletRequest request, final Model model,
			@RequestParam("prAdvEntryId") final Long prAdvEntryId,
			@RequestParam("advanceNumber") final Long advanceNumber,
			@RequestParam("advanceDate") final String advanceDate, @RequestParam("vendorId") final Long vendorId,
			@RequestParam("balanceAmount") final String balanceAmount, @RequestParam("pacHeadId") final Long pacHeadId,
			@RequestParam("categoryTypeId") final Long categoryTypeId) {
		log("Action 'createformFromAdvance'");
		final TbServiceReceiptMasBean tbServiceReceiptMasBean = new TbServiceReceiptMasBean();
		// get passed data and set to bean
		// set a flag for back button
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		tbServiceReceiptMasBean.setPrAdvEntryId(prAdvEntryId);
		if (vendorId != null) {
			tbServiceReceiptMasBean.setVmVendorId(vendorId);
			final TbAcVendormaster tbAcVendormaster = tbAcVendormasterService.findById(vendorId, orgId);
			if (tbAcVendormaster != null) {
				tbServiceReceiptMasBean.setRmReceivedfrom(tbAcVendormaster.getVmVendorname());
				tbServiceReceiptMasBean.setMobileNumber(tbAcVendormaster.getMobileNo());
				tbServiceReceiptMasBean.setEmailId(tbAcVendormaster.getEmailId());
			}
		}
		tbServiceReceiptMasBean.setRmNarration(ApplicationSession.getInstance().getMessage("account.receipt.advance")
				+ advanceNumber + MainetConstants.SEPARATOR
				+ ApplicationSession.getInstance().getMessage("account.receipt.advancedate") + advanceDate);

		final List<TbSrcptFeesDetBean> bean = new ArrayList<>();
		final TbSrcptFeesDetBean srcptFeesDetBean = new TbSrcptFeesDetBean();
		srcptFeesDetBean.setSacHeadId(pacHeadId);
		// srcptFeesDetBean.setRfFeeamount(new BigDecimal(balanceAmount));
		bean.add(srcptFeesDetBean);
		tbServiceReceiptMasBean.setReceiptFeeDetail(bean);
		tbServiceReceiptMasBean.setBalanceAmount(
				balanceAmount.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK));
		tbServiceReceiptMasBean.setAdvanceFlag(MainetConstants.MENU.Y);
		populateModel(model, tbServiceReceiptMasBean, FormMode.CREATE);
		String recCategoryType = CommonMasterUtility.findLookUpCode(MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV,
				UserSession.getCurrent().getOrganisation().getOrgid(), categoryTypeId);
		Map<Long, String> budgetAcHeadMap = null;
		if (recCategoryType.equals("A") || recCategoryType.equals("P")) {
			final LookUp lookUpStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
					PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(),
					UserSession.getCurrent().getOrganisation());
			final Long activeStatusId = lookUpStatus.getLookUpId();
			budgetAcHeadMap = tbAcSecondaryheadMasterService.getAcHeadCodeInReceieptCategoryTypeEntry(activeStatusId,
					orgId, recCategoryType);
			model.addAttribute(MainetConstants.AccountReceiptEntry.HEAD_CODE_MAP, budgetAcHeadMap);
			tbServiceReceiptMasBean.setRecCategoryTypeId(categoryTypeId);
		}

		model.addAttribute(MAIN_ENTITY_NAME, tbServiceReceiptMasBean);
		return JSP_FORM;
	}

	@RequestMapping(params = "getGridData")
	public @ResponseBody AccountReceiptEntryResponse gridData(final HttpServletRequest request, final Model model) {
		log("Action 'Get grid Data'");
		final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
		final AccountReceiptEntryResponse accountReceiptEntryResponse = new AccountReceiptEntryResponse();
		accountReceiptEntryResponse.setRows(listOfTaxBean);
		accountReceiptEntryResponse.setTotal(listOfTaxBean.size());
		accountReceiptEntryResponse.setRecords(listOfTaxBean.size());
		accountReceiptEntryResponse.setPage(page);
		model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, model);
		return accountReceiptEntryResponse;
	}

	// search
	@RequestMapping(params = "getjqGridsearch")
	public @ResponseBody String gridData(final HttpServletRequest request, final Model model,
			@RequestParam("rmAmount") String rmAmount, @RequestParam("rmRcptno") String rmRcptno,
			@RequestParam("rm_Receivedfrom") String rm_Receivedfrom, @RequestParam("rmDate") final String rmDate)
			throws ParseException {
		log("Action getjqGridsearch");
		List<TbServiceReceiptMasBean> list = null;
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		String message = null;
		Date date = null;
		if ((rmDate != null) && !rmDate.isEmpty()) {
			date = Utility.stringToDate(rmDate);
		}

		Long receiptNo = null;
		
		if (StringUtils.isNotEmpty(rmRcptno)) {
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
				receiptNo = Utility.getReceiptIdFromCustomRcptNO(rmRcptno);
			}
		} else {
			if ((rmRcptno == null) || rmRcptno.isEmpty()) {
				receiptNo = 0L;
			} else {
				receiptNo = Long.valueOf(rmRcptno);
			}
		}
		BigDecimal rmReptAmount;
		if ((rmAmount == null) || rmAmount.isEmpty()) {
			rmReptAmount = BigDecimal.ZERO;
		} else {
			rmReptAmount = new BigDecimal(rmAmount);
		}
		if (rm_Receivedfrom == null) {
			rm_Receivedfrom = MainetConstants.CommonConstant.BLANK;
		}

		list = accountReceiptEntryService.findAll(orgId, rmReptAmount, receiptNo, rm_Receivedfrom, date);
		listOfTaxBean = new ArrayList<>();
		if ((list != null) && !list.isEmpty()) {
			for (final TbServiceReceiptMasBean bean : list) {
				// final Format df = new DecimalFormat("###,###,###.00");
				bean.setFormattedCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(bean.getRmAmount())));
				if (bean != null) {
					if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
							MainetConstants.ENV_SKDCL)) {
						bean.setRmReceiptNo(
								iReceiptEntryService.getCustomReceiptNo(bean.getDpDeptId(), bean.getRmRcptno()));
					}
				}
				if(bean.getReceiptDelFlag()==null)
			 	  listOfTaxBean.add(bean);
			}
			message = MainetConstants.MENU.Y;
		} else {
			message = MainetConstants.MENU.N;
		}
		//Receipt already reverse logic
		if(listOfTaxBean.isEmpty() && CollectionUtils.isNotEmpty(list) && rmRcptno!=null && !rmRcptno.isEmpty() && rmDate!=null && !rmDate.isEmpty()) {
			if(list.get(0).getReceiptDelFlag()!=null && MainetConstants.Y_FLAG.equals(list.get(0).getReceiptDelFlag())) {
				message=MainetConstants.MENU.R;
			}
		}else if(listOfTaxBean.isEmpty() && CollectionUtils.isNotEmpty(list) && rmRcptno!=null && !rmRcptno.isEmpty()) {
			for(TbServiceReceiptMasBean bean:list) {
				if(bean.getReceiptDelFlag()!=null && MainetConstants.Y_FLAG.equals(bean.getReceiptDelFlag())) {
					message=MainetConstants.MENU.R;
				}
			}
		}
		
		return message;
	}

	@RequestMapping(params = "validateChequeDate", method = RequestMethod.POST)
	public @ResponseBody boolean validateChequeDate(TbServiceReceiptMasBean tbServiceReceiptMasBean,
			final HttpServletRequest request, final Model model, final BindingResult bindingResult) {

		boolean isValidationError = false;
		if (!MainetConstants.CommonConstant.BLANK
				.equals(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdChequedddatetemp())) {
			final Date date = Utility.stringToDate(tbServiceReceiptMasBean.getTransactionDate());
			final Date rdchequedddate = Utility
					.stringToDate(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdChequedddatetemp());
			if (rdchequedddate.compareTo(date) > 0) {
				bindingResult.addError(new org.springframework.validation.FieldError(TB_SERVICE_RECEIPTMAS_BEAN,
						MainetConstants.CommonConstant.BLANK, null, false, new String[] { MainetConstants.ERRORS },
						null, ApplicationSession.getInstance().getMessage(MainetConstants.CommonConstant.BLANK)));
				isValidationError = true;
			}
			final Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.MONTH, -1 * MainetConstants.CHEQUEDATEVALIDATION_MONTHS);
			if (rdchequedddate.before(c.getTime())) {
				bindingResult.addError(new org.springframework.validation.FieldError(TB_SERVICE_RECEIPTMAS_BEAN,
						MainetConstants.CommonConstant.BLANK, null, false, new String[] { MainetConstants.ERRORS },
						null, ApplicationSession.getInstance().getMessage(MainetConstants.CommonConstant.BLANK)));
				isValidationError = true;
			}
		}
		return isValidationError;
	}

	@RequestMapping(params = "create")
	public ModelAndView create(@Valid final TbServiceReceiptMasBean tbServiceReceiptMasBean,
			final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
			final HttpServletRequest httpServletRequest) throws Exception {
		log("Action 'create'");
		final Long superOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
		final long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		final Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		// final String macAddress = Utility.getMacAddress();
		String macAddress = Utility.getClientIpAddress(httpServletRequest);
		final int languageId = UserSession.getCurrent().getLanguageId();

		if (!bindingResult.hasErrors()) {
			tbServiceReceiptMasBean.setSuperOrgId(superOrgId);
			tbServiceReceiptMasBean.setOrgId(orgid);
			tbServiceReceiptMasBean.setCreatedDate(new Date());
			tbServiceReceiptMasBean.setLangId(languageId);
			tbServiceReceiptMasBean.setLgIpMac(macAddress);
			tbServiceReceiptMasBean.setCreatedBy(empId);

			long fieldId = 0;
			if (UserSession.getCurrent().getLoggedLocId() != null) {
				final TbLocationMas locMas = locMasService.findById(UserSession.getCurrent().getLoggedLocId());
				if ((locMas.getLocRevenueWZMappingDto() != null) && !locMas.getLocRevenueWZMappingDto().isEmpty()) {
					fieldId = locMas.getLocRevenueWZMappingDto().get(0).getCodIdRevLevel1();
				}
			}
			if (fieldId == 0) {
				throw new NullPointerException("fieldId is not linked with Location Master for[locId="
						+ UserSession.getCurrent().getEmployee().getTbLocationMas().getLocId() + ",locName="
						+ UserSession.getCurrent().getEmployee().getTbLocationMas().getLocNameEng() + "]");
			}

			final TbServiceReceiptMasBean TbServiceReceiptMasBeanCreated = accountReceiptEntryService
					.create(tbServiceReceiptMasBean, fieldId);
			// you need to prepare & check very data to show on report.
			if ((tbServiceReceiptMasBean.getAdvanceFlag() != null)
					&& !tbServiceReceiptMasBean.getAdvanceFlag().isEmpty()) {
				TbServiceReceiptMasBeanCreated.setAdvanceFlag(tbServiceReceiptMasBean.getAdvanceFlag());
			}
			model.addAttribute(MAIN_ENTITY_NAME, TbServiceReceiptMasBeanCreated);
			httpServletRequest.getSession().setAttribute(MainetConstants.AccountReceiptEntry.RECEIPT_LIST,
					TbServiceReceiptMasBeanCreated);

			// Code Added By Rahul S Chaubey
			// Changing the Status from Active to Close of Investement whenever a Receipt
			// Entry is made against that particular investment id.
			if (StringUtils.isNotBlank(tbServiceReceiptMasBean.getFlag())
					&& tbServiceReceiptMasBean.getFlag().equalsIgnoreCase("INV")) {
				AccountInvestmentMasterDto accountInvestmentMasterDto = new AccountInvestmentMasterDto();
				accountInvestmentMasterDto.setInvstId(tbServiceReceiptMasBean.getPrAdvEntryId());
				List<AccountInvestmentMasterDto> data = accountInvestmentService.findByBankIdInvestmentData(null,
						accountInvestmentMasterDto.getInvstId(), null, null, null, null, null,
						UserSession.getCurrent().getOrganisation().getOrgid());

				BeanUtils.copyProperties(data.get(0), accountInvestmentMasterDto);
				accountInvestmentMasterDto.setStatus(MainetConstants.WorksManagement.CLOSED);
				accountInvestmentService.saveInvestMentMaster(accountInvestmentMasterDto);
			}

			return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME,
					MainetConstants.SUCCESS_MSG);

		} else {
			populateModel(model, tbServiceReceiptMasBean, FormMode.CREATE);
			model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
			model.addAttribute(MAIN_ENTITY_NAME, tbServiceReceiptMasBean);
			return new ModelAndView(JSP_FORM);
		}

	}

	@RequestMapping(params = "viewMode")
	public String formForView(final Model model, @RequestParam("rmRcptid") final Long rmRcptid,
			@RequestParam("postFlag") final String postFlag) {

		final String feeAmountStr = null;
		TbServiceReceiptMasBean tbServiceReceiptMasBean = null;
		tbServiceReceiptMasBean = accountReceiptEntryService.findById(rmRcptid,
				UserSession.getCurrent().getOrganisation().getOrgid());
        tbServiceReceiptMasBean.setRecCategoryTypeId(tbServiceReceiptMasBean.getRmReceiptcategoryId());
        
		if (tbServiceReceiptMasBean != null) {
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
				tbServiceReceiptMasBean.setRmReceiptNo(iReceiptEntryService.getCustomReceiptNo(
						tbServiceReceiptMasBean.getDpDeptId(), tbServiceReceiptMasBean.getRmRcptno()));
			}
		}
		// final List<TbSrcptFeesDetBean> recDetDetails =
		// tbServiceReceiptMasBean.getReceiptFeeDetail();
		List<Object[]> receiptDetList = accountReceiptEntryService.findAllDataByReceiptId(
				tbServiceReceiptMasBean.getRmRcptid(), UserSession.getCurrent().getOrganisation().getOrgid());
		List<TbSrcptFeesDetBean> recDetDetails1 = new ArrayList<>();
		String acHeadCode = MainetConstants.CommonConstant.BLANK;

		for (final Object[] tbSrcptFeesDetBean : receiptDetList) {
			final TbSrcptFeesDetBean bean = new TbSrcptFeesDetBean();
			if ((tbSrcptFeesDetBean[0] != null) && ((Long) tbSrcptFeesDetBean[0] != 0)) {
				acHeadCode = tbAcSecondaryheadMasterService
						.getAccountHeadCodeInReceieptDetEntry((Long) tbSrcptFeesDetBean[0]);
			}
			bean.setAcHeadCode(acHeadCode);
			bean.setRfFeeamount((BigDecimal) tbSrcptFeesDetBean[1]);
			recDetDetails1.add(bean);
		}
		tbServiceReceiptMasBean.setReceiptFeeDetail(recDetDetails1);

		tbServiceReceiptMasBean.setFeeAmountStr(feeAmountStr);
		TbAcVendormaster tbAcVendormaster = null;
		if (tbServiceReceiptMasBean.getVmVendorId() == null) {
			tbAcVendormaster = new TbAcVendormaster();
		} else {
			tbAcVendormaster = tbAcVendormasterService.findById(tbServiceReceiptMasBean.getVmVendorId(),
					UserSession.getCurrent().getOrganisation().getOrgid());
			tbServiceReceiptMasBean.setVmVendorIdDesc(tbAcVendormaster.getVmVendorname());
		}
		if (!tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemode()
				.equals(MainetConstants.CommonConstant.BLANK)) {
			LookUp lookUp = new LookUp();

			lookUp = CommonMasterUtility
					.getNonHierarchicalLookUpObject(tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemode());

			tbServiceReceiptMasBean.getReceiptModeDetailList().setCpdFeemodeDesc(lookUp.getLookUpDesc());
			tbServiceReceiptMasBean.getReceiptModeDetailList().setCpdFeemodeCode(lookUp.getLookUpCode());
		}

		

		BankMasterDto bankMasterDto = null;
		if (tbServiceReceiptMasBean.getReceiptModeDetailList().getCbBankid() == null) {
			bankMasterDto = new BankMasterDto();
		} else {
			bankMasterDto = bankAccountService
					.getBankbyBranchId(tbServiceReceiptMasBean.getReceiptModeDetailList().getCbBankid());
			tbServiceReceiptMasBean.getReceiptModeDetailList()
					.setCbBankidDesc(bankMasterDto.getBank() + MainetConstants.SEPARATOR + bankMasterDto.getBranch()
							+ MainetConstants.SEPARATOR + bankMasterDto.getIfsc());
		}
		BankAccountMasterDto bankAccountMasterDto = null;
		if (tbServiceReceiptMasBean.getReceiptModeDetailList().getBaAccountid() == null) {
			bankAccountMasterDto = new BankAccountMasterDto();
		} else {
			bankAccountMasterDto = bankAccountService
					.findAccountByAccountId(tbServiceReceiptMasBean.getReceiptModeDetailList().getBaAccountid());
			String bank="";
			if(bankAccountMasterDto.getBankId()!=null)
				bank=bankMasterService.getBankById(bankAccountMasterDto.getBankId());
			tbServiceReceiptMasBean.getReceiptModeDetailList().setCbBankidDesc(bank+MainetConstants.HYPHEN+bankAccountMasterDto.getBaAccountNo()
					+ MainetConstants.HYPHEN + bankAccountMasterDto.getBaAccountName());
		}
		model.addAttribute(MODE, View);
		model.addAttribute(PostFlag, postFlag);

		if (tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
				.equals(MainetConstants.AccountReceiptEntry.RT)
				|| tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode().equals(MainetConstants.MENU.N)
				|| tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
						.equals(MainetConstants.NewWaterServiceConstants.BILL_SCHEDULE_DATE)) {
			tbServiceReceiptMasBean.getReceiptModeDetailList()
					.setTranRefNumber(tbServiceReceiptMasBean.getReceiptModeDetailList().getTranRefNumber());
		} else {
			if (tbServiceReceiptMasBean.getReceiptModeDetailList() != null
					&& (tbServiceReceiptMasBean.getReceiptModeDetailList().getRdChequeddno() != null)) {
				tbServiceReceiptMasBean.getReceiptModeDetailList()
						.setTranRefNumber(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdChequeddno().toString());
			}
		}

		if (tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
				.equals(MainetConstants.AccountReceiptEntry.RT)
				|| tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode().equals(MainetConstants.MENU.N)
				|| tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
						.equals(MainetConstants.NewWaterServiceConstants.BILL_SCHEDULE_DATE)) {
			final String tranRefDate = Utility
					.dateToString(tbServiceReceiptMasBean.getReceiptModeDetailList().getTranRefDate());
			tbServiceReceiptMasBean.getReceiptModeDetailList().setRdChequedddatetemp(tranRefDate);
		} else {
			final String chkDate = Utility
					.dateToString(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdChequedddate());
			tbServiceReceiptMasBean.getReceiptModeDetailList().setRdChequedddatetemp(chkDate);
		}

		populateModel(model, tbServiceReceiptMasBean, FormMode.VIEW);
		model.addAttribute(MAIN_ENTITY_NAME, tbServiceReceiptMasBean);
		return JSP_FORM;
	}

	private void populateListOfTbAcFieldMasterItems(final Model model, final Long orgId) {
		model.addAttribute(MainetConstants.ACCOUNT_RECEIPT_ENTRY_MASTER.FIELD_MASTER_ITEMS,
				tbAcFieldMasterService.getFieldMasterLastLevels(orgId));
	}

	private void populateBudgetCodes(final Model model) {

		ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
		final Organisation org = UserSession.getCurrent().getOrganisation();
		// final int langId = UserSession.getCurrent().getLanguageId();
		AccountPrefix.COA.toString();
		final LookUp lookUpStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
				PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		final Long activeStatusId = lookUpStatus.getLookUpId();
		final Map<Long, String> budgetAcHeadMap = tbAcSecondaryheadMasterService
				.getAcHeadCodeInReceieptEntry(activeStatusId, org.getOrgid());
		model.addAttribute(MainetConstants.AccountReceiptEntry.HEAD_CODE_MAP, budgetAcHeadMap);
	}

	private String checkTemplate() {

		final VoucherTemplateDTO postDTO = new VoucherTemplateDTO();
		String templateExistFlag = null;
		postDTO.setTemplateType(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.ContraVoucherEntry.PN,
				PrefixConstants.ContraVoucherEntry.MTP, UserSession.getCurrent().getOrganisation().getOrgid()));
		postDTO.setVoucherType(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.REV_SUB_CPD_VALUE,
				PrefixConstants.ContraVoucherEntry.VOT, UserSession.getCurrent().getOrganisation().getOrgid()));
		postDTO.setDepartment(departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString()));
		postDTO.setTemplateFor(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.REV_SUB_CPD_VALUE,
				PrefixConstants.REV_TYPE_CPD_VALUE, UserSession.getCurrent().getOrganisation().getOrgid()));
		final boolean existTempalte = voucherTemplateService.isTemplateExist(postDTO,
				UserSession.getCurrent().getOrganisation().getOrgid());
		if (!existTempalte) {
			templateExistFlag = MainetConstants.MENU.N;
		} else {
			templateExistFlag = MainetConstants.MENU.Y;
		}
		return templateExistFlag;
	}

	@RequestMapping(params = "reciptPrintForm", method = RequestMethod.POST)
	public String ReceiptPrintForm(final Model model, final HttpServletRequest request) {
		log("Action 'reciptPrintForm'");

		final TbServiceReceiptMasBean tbServiceReceiptMasBean = (TbServiceReceiptMasBean) request.getSession()
				.getAttribute(MainetConstants.AccountReceiptEntry.RECEIPT_LIST);

		final AccountReceiptReportMasDto oAccountReceiptReportMasDto = new AccountReceiptReportMasDto();
		tbServiceReceiptMasBean.getReceiptFeeDetail();
		tbServiceReceiptMasBean.getDeptName();

		oAccountReceiptReportMasDto.setRmRcptno(tbServiceReceiptMasBean.getRmRcptno());
         //#149536
		if  (StringUtils.isNotEmpty(tbServiceReceiptMasBean.getReceiptModeDetailList().getBaAccountidDesc()))
		oAccountReceiptReportMasDto.setBaAccountNo(tbServiceReceiptMasBean.getReceiptModeDetailList().getBaAccountidDesc());
		if (oAccountReceiptReportMasDto != null) {
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
				oAccountReceiptReportMasDto.setRmReceiptNo(iReceiptEntryService.getCustomReceiptNo(
						tbServiceReceiptMasBean.getDpDeptId(), tbServiceReceiptMasBean.getRmRcptno()));
			}
		}
		long fieldId = 0;

		if (UserSession.getCurrent().getLoggedLocId() != null) {
			final TbLocationMas locMas = locMasService.findById(UserSession.getCurrent().getLoggedLocId());
			if ((locMas.getLocRevenueWZMappingDto() != null) && !locMas.getLocRevenueWZMappingDto().isEmpty()) {
				fieldId = locMas.getLocRevenueWZMappingDto().get(0).getCodIdRevLevel1();
			}
		}
		if (fieldId == 0) {
			throw new NullPointerException("fieldId is not linked with Location Master for[locId="
					+ UserSession.getCurrent().getEmployee().getTbLocationMas().getLocId() + ",locName="
					+ UserSession.getCurrent().getEmployee().getTbLocationMas().getLocNameEng() + "]");
		}
		final AccountFieldMasterBean fieldDto = tbAcFieldMasterService.findById(fieldId);
		if (fieldDto != null) {
				CFCSchedulingCounterDet counterDet = null;
	            if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
	                
						
						oAccountReceiptReportMasDto.setCashCollectNo(tbServiceReceiptMasBean.getCfcColCenterNo());
						oAccountReceiptReportMasDto.setCounterNo(tbServiceReceiptMasBean.getCfcColCounterNo());
	                
	                if(UserSession.getCurrent().getEmployee().getEmpname()!= null && UserSession.getCurrent().getEmployee().getEmplname() != null)
	                oAccountReceiptReportMasDto.setEmpName(UserSession.getCurrent().getEmployee().getEmpname()+ MainetConstants.WHITE_SPACE + UserSession.getCurrent().getEmployee().getEmplname());
	            }
	            else{
	            	oAccountReceiptReportMasDto.setCashCollectNo(
	    					fieldDto.getFieldCompcode().replace(MainetConstants.HYPHEN, MainetConstants.operator.EMPTY)
	    							+ MainetConstants.SEPARATOR + fieldDto.getFieldDesc());
	            }	
		}
		/*
		 * if (tbServiceReceiptMasBean.getCreatedBy() != null) {
		 * oAccountReceiptReportMasDto.setCounterNo(tbServiceReceiptMasBean.getCreatedBy
		 * ().toString()); }
		 */

		if (tbServiceReceiptMasBean.getManualReceiptNo() != null) {
			oAccountReceiptReportMasDto.setManualReceiptNo(tbServiceReceiptMasBean.getManualReceiptNo());
		}

		if (tbServiceReceiptMasBean.getRmNarration() != null) {
			oAccountReceiptReportMasDto.setRmNarration(tbServiceReceiptMasBean.getRmNarration());
		}

		final String reciptdate = Utility.dateToString(tbServiceReceiptMasBean.getRmDate());
		final SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
		final String time = localDateFormat.format(new Date());
		
		oAccountReceiptReportMasDto.setReceiptTime(time);

		oAccountReceiptReportMasDto.setRmDate(reciptdate);
		final String fYear = Utility.getCurrentFinancialYear();
		oAccountReceiptReportMasDto.setfYear(fYear);

		if (!tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemode()
				.equals(MainetConstants.CommonConstant.BLANK)) {
			LookUp lookUp = new LookUp();

			lookUp = CommonMasterUtility
					.getNonHierarchicalLookUpObject(tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemode());
			oAccountReceiptReportMasDto.setCpdFeemodeDesc(
					MainetConstants.AccountReceiptEntry.BY + MainetConstants.WHITE_SPACE + lookUp.getLookUpDesc());
			tbServiceReceiptMasBean.getReceiptModeDetailList().setCpdFeemodeCode(lookUp.getLookUpCode());
		}

		TbAcVendormaster tbAcVendormaster = null;
		if (tbServiceReceiptMasBean.getVmVendorId() == null) {
			oAccountReceiptReportMasDto.setVmVendorIdDesc(tbServiceReceiptMasBean.getRmReceivedfrom());
		} else {
			tbAcVendormaster = tbAcVendormasterService.findById(tbServiceReceiptMasBean.getVmVendorId(),
					UserSession.getCurrent().getOrganisation().getOrgid());
			oAccountReceiptReportMasDto.setVmVendorIdDesc(tbAcVendormaster.getVmVendorname());
		}
		oAccountReceiptReportMasDto.setRdAmount(CommonMasterUtility
				.getAmountInIndianCurrency(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdAmount()));
		oAccountReceiptReportMasDto.setRmAmount(
				CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(tbServiceReceiptMasBean.getRmAmount())));
		oAccountReceiptReportMasDto.setApmApplicationId(tbServiceReceiptMasBean.getApmApplicationId());
		if (!tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
				.equals(MainetConstants.CommonConstants.C)) {
			if (tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
					.equals(MainetConstants.AccountReceiptEntry.RT)
					|| tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode().equals(MainetConstants.MENU.N)
					|| tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
							.equals(MainetConstants.NewWaterServiceConstants.BILL_SCHEDULE_DATE)) {

				oAccountReceiptReportMasDto
						.setRdChequeddno(tbServiceReceiptMasBean.getReceiptModeDetailList().getTranRefNumber());

			} else if (tbServiceReceiptMasBean.getReceiptModeDetailList().getRdChequeddno() != null) {
				oAccountReceiptReportMasDto
						.setRdChequeddno(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdChequeddno().toString());
			}
		}
		oAccountReceiptReportMasDto.setCpdFeemodeCode(tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode());
		if (tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
				.equals(MainetConstants.AccountReceiptEntry.RT)
				|| tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode().equals(MainetConstants.MENU.N)
				|| tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
						.equals(MainetConstants.NewWaterServiceConstants.BILL_SCHEDULE_DATE)) {
			final String tranRefDate = Utility
					.dateToString(tbServiceReceiptMasBean.getReceiptModeDetailList().getTranRefDate());
			oAccountReceiptReportMasDto.setRdChequedddatetemp(tranRefDate);
		} else {
			final String chkDate = Utility
					.dateToString(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdChequedddate());
			oAccountReceiptReportMasDto.setRdChequedddatetemp(chkDate);
		}

		BankAccountMasterDto bankAccountMasterDto = null;
		if (tbServiceReceiptMasBean.getReceiptModeDetailList().getBaAccountid() == null) {
			bankAccountMasterDto = new BankAccountMasterDto();
		} else {
			bankAccountMasterDto = bankAccountService
					.findAccountByAccountId(tbServiceReceiptMasBean.getReceiptModeDetailList().getBaAccountid());
			BankMasterDto bankMasterDto = bankAccountService.getBankbyBranchId(bankAccountMasterDto.getBankId());
			oAccountReceiptReportMasDto.setCbBankidDesc(
					bankMasterDto.getBank() + MainetConstants.HYPHEN + bankAccountMasterDto.getBaAccountNo()
							+ MainetConstants.HYPHEN + bankAccountMasterDto.getBaAccountName());
			oAccountReceiptReportMasDto.setBranch(bankMasterDto.getBranch());
		}

		oAccountReceiptReportMasDto.setOrganizationName(UserSession.getCurrent().getOrganisation().getONlsOrgname());

		BankMasterDto bankMasterDto = null;
		if (tbServiceReceiptMasBean.getReceiptModeDetailList().getCbBankid() == null) {
			bankMasterDto = new BankMasterDto();
		} else {
			bankMasterDto = bankAccountService
					.getBankbyBranchId(tbServiceReceiptMasBean.getReceiptModeDetailList().getCbBankid());
			oAccountReceiptReportMasDto.setCbBankidDesc(bankMasterDto.getBank());
			oAccountReceiptReportMasDto.setBranch(bankMasterDto.getBranch());
		}

		String amountInWords = Utility
				.convertBiggerNumberToWord(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdAmount());
		oAccountReceiptReportMasDto.setAmountInWords(amountInWords);

		if ((tbServiceReceiptMasBean.getAdvanceFlag() != null) && !tbServiceReceiptMasBean.getAdvanceFlag().isEmpty()) {
			oAccountReceiptReportMasDto.setAdvanceFlag(tbServiceReceiptMasBean.getAdvanceFlag());
		}
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		List<Object[]> receiptDetList = accountReceiptEntryService
				.findAllDataByReceiptId(tbServiceReceiptMasBean.getRmRcptid(), orgId);
		final List<TbSrcptFeesDetBean> receiptFeeDetail = new ArrayList<>();
		for (final Object[] det : receiptDetList) {

			final TbSrcptFeesDetBean bean = new TbSrcptFeesDetBean();
			if ((det[0] != null) && ((Long) det[0] != 0)) {
				bean.setReceiptHead(budgetCodeService.findAccountHeadCodeBySacHeadId((Long) det[0], orgId));
			}
			bean.setReceiptAmount(CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) det[1]));
			receiptFeeDetail.add(bean);
			oAccountReceiptReportMasDto.setReceiptFeeDetail(receiptFeeDetail);
		}
		/*Defect #85098*/
		String flag = request.getParameter("flag");
		model.addAttribute("flag",flag);
		
		model.addAttribute(MainetConstants.AccountReceiptEntry.ACC_RECEIPT_REPORT, oAccountReceiptReportMasDto);
		return ReceiptReportJsp_Form;
	}

	/**
	 * being used to render view page for receipt details from Account Voucher
	 * Reversal page
	 * 
	 * @param gridId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(params = "viewReceiptDetail")
	public String viewVoucherForm(@RequestParam("gridId") final long gridId, final HttpServletRequest request,
			final ModelMap model) {
		String page = StringUtils.EMPTY;
		try {
			final ReceiptReversalViewDTO viewData = accountVoucherReversalService.veiwData(gridId,
					UserSession.getCurrent().getOrganisation().getOrgid());
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
				viewData.setRmReceiptNo(iReceiptEntryService.getCustomReceiptNo(
					  viewData.getDeptId(), viewData.getReceiptNo()));
			}

			model.addAttribute(MainetConstants.AccountReceiptEntry.RECEIPT_VIEW_DATA, viewData);
			page = MainetConstants.AccountReceiptEntry.RECEIPT_VIEW_PAGE;
			model.addAttribute(MainetConstants.PRIMARYCODEMASTER.MODE_FLAG, MainetConstants.MODE_VIEW);
			request.getSession().setAttribute(MainetConstants.FIELD_MASTER.MODE_DATA, MainetConstants.MODE_VIEW);
		} catch (final Exception ex) {
			LOGGER.error("Error while view Template", ex);
		}
		return page;
	}

	@RequestMapping(params = "onTransactionDate", method = RequestMethod.GET)
	public @ResponseBody String validateTransactionDate(@RequestParam("transactionDate") final String transactionDate,
			final HttpServletRequest request, final ModelMap model) {
		String response;
		try {
			final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE,
					MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
					UserSession.getCurrent().getOrganisation());
			final String date = lookUp.getOtherField();
			Objects.requireNonNull(date, ApplicationSession.getInstance().getMessage("account.depositslip.livedate"));
			final Date sliDate = Utility.stringToDate(date);
			final Date transDate = Utility.stringToDate(transactionDate);
			if ((transDate.getTime() >= sliDate.getTime()) && (transDate.getTime() <= new Date().getTime())) {
				response = ApplicationSession.getInstance().getMessage("accounts.depositslip.response.ok");
			} else {
				response = ApplicationSession.getInstance().getMessage("accounts.depositslip.response.receiptdate")
						+ date
						+ ApplicationSession.getInstance().getMessage("accounts.depositslip.response.currentdate");
			}

		} catch (final Exception ex) {
			response = ApplicationSession.getInstance().getMessage("account.depositslip.response.servererror");
			LOGGER.error("Error while validating Receipt date from SLI Prefix", ex);
		}
		return response;
	}

	@RequestMapping(params = "SLIDate", method = RequestMethod.GET)
	public @ResponseBody Object[] findSLIDate(final HttpServletRequest request, final ModelMap model) {
		final Object[] dateArray = new Object[3];
		try {
			final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE,
					MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
					UserSession.getCurrent().getOrganisation());
			Objects.requireNonNull(lookUp, ApplicationSession.getInstance().getMessage("account.receipt.sli"));
			final String date = lookUp.getOtherField();
			// Objects.requireNonNull(date,
			// ApplicationSession.getInstance().getMessage("account.depositslip.livedate"));
			if (date != null && !date.isEmpty()) {
				final Date sliDate = Utility.stringToDate(date);
				final Calendar calendar = Calendar.getInstance();
				calendar.setTime(sliDate);
				dateArray[0] = calendar.get(Calendar.YEAR);
				dateArray[1] = calendar.get(Calendar.MONTH);
				dateArray[2] = calendar.get(Calendar.DATE);
			}
		} catch (final Exception ex) {
			LOGGER.error("Error while finding SLI Date from SLI Prefix:", ex);
		}
		return dateArray;
	}

	@RequestMapping(params = "formForPrint")
	public String formForPrint(final Model model, @RequestParam("rmRcptid") final Long rmRcptid,
			@RequestParam("postFlag") final String postFlag) {

		TbServiceReceiptMasBean tbServiceReceiptMasBean = null;

		tbServiceReceiptMasBean = accountReceiptEntryService.findById(rmRcptid,
				UserSession.getCurrent().getOrganisation().getOrgid());

		final AccountReceiptReportMasDto oAccountReceiptReportMasDto = new AccountReceiptReportMasDto();

		oAccountReceiptReportMasDto.setRmRcptno(tbServiceReceiptMasBean.getRmRcptno());

		if (oAccountReceiptReportMasDto != null) {
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
				oAccountReceiptReportMasDto.setRmReceiptNo(iReceiptEntryService.getCustomReceiptNo(
						tbServiceReceiptMasBean.getDpDeptId(), tbServiceReceiptMasBean.getRmRcptno()));
			}
		}
		
		/*
		 * long fieldId = 0;
		 * 
		 * if (UserSession.getCurrent().getLoggedLocId() != null) { final TbLocationMas
		 * locMas = locMasService.findById(UserSession.getCurrent().getLoggedLocId());
		 * if ((locMas.getLocRevenueWZMappingDto() != null) &&
		 * !locMas.getLocRevenueWZMappingDto().isEmpty()) { fieldId =
		 * locMas.getLocRevenueWZMappingDto().get(0).getCodIdRevLevel1(); } } if
		 * (fieldId == 0) { throw new
		 * NullPointerException("fieldId is not linked with Location Master for[locId="
		 * + UserSession.getCurrent().getEmployee().getTbLocationMas().getLocId() +
		 * ",locName=" +
		 * UserSession.getCurrent().getEmployee().getTbLocationMas().getLocNameEng() +
		 * "]"); }
		 */
		final AccountFieldMasterBean fieldDto = tbAcFieldMasterService.findById(tbServiceReceiptMasBean.getFieldId());
		if (fieldDto != null) {
			oAccountReceiptReportMasDto.setCashCollectNo(
					fieldDto.getFieldCompcode().replace(MainetConstants.HYPHEN, MainetConstants.operator.EMPTY)
							+ MainetConstants.SEPARATOR + fieldDto.getFieldDesc());
		}
		if (tbServiceReceiptMasBean.getCreatedBy() != null) {
			oAccountReceiptReportMasDto.setCounterNo(tbServiceReceiptMasBean.getCreatedBy().toString());
		}

		if (tbServiceReceiptMasBean.getManualReceiptNo() != null) {
			oAccountReceiptReportMasDto.setManualReceiptNo(tbServiceReceiptMasBean.getManualReceiptNo());
		}

		if (tbServiceReceiptMasBean.getRmNarration() != null) {
			oAccountReceiptReportMasDto.setRmNarration(tbServiceReceiptMasBean.getRmNarration());
		}
		
		if(UserSession.getCurrent().getEmployee().getEmpname()!= null && UserSession.getCurrent().getEmployee().getEmplname() != null)
            oAccountReceiptReportMasDto.setEmpName(UserSession.getCurrent().getEmployee().getEmpname()+ MainetConstants.WHITE_SPACE + UserSession.getCurrent().getEmployee().getEmplname());
		final SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
		final String time = localDateFormat.format(new Date());
		oAccountReceiptReportMasDto.setReceiptTime(time);
		final String reciptdate = Utility.dateToString(tbServiceReceiptMasBean.getRmDate());
		oAccountReceiptReportMasDto.setRmDate(reciptdate);
		final String fYear = Utility.getCurrentFinancialYear();
		oAccountReceiptReportMasDto.setfYear(fYear);

		if (!tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemode()
				.equals(MainetConstants.CommonConstant.BLANK)) {
			LookUp lookUp = new LookUp();

			lookUp = CommonMasterUtility
					.getNonHierarchicalLookUpObject(tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemode());
			oAccountReceiptReportMasDto.setCpdFeemodeDesc(
					MainetConstants.AccountReceiptEntry.BY + MainetConstants.WHITE_SPACE + lookUp.getLookUpDesc());
			tbServiceReceiptMasBean.getReceiptModeDetailList().setCpdFeemodeCode(lookUp.getLookUpCode());
		}

		// Defect #153928
		oAccountReceiptReportMasDto.setCpdFeemodeCode(tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode());
		if(tbServiceReceiptMasBean.getReceiptModeDetailList().getBaAccountidDesc()!= null){
		oAccountReceiptReportMasDto.setBaAccountNo(tbServiceReceiptMasBean.getReceiptModeDetailList().getBaAccountidDesc());
		}
		
		TbAcVendormaster tbAcVendormaster = null;
		if (tbServiceReceiptMasBean.getVmVendorId() == null) {
			oAccountReceiptReportMasDto.setVmVendorIdDesc(tbServiceReceiptMasBean.getRmReceivedfrom());
		} else {
			tbAcVendormaster = tbAcVendormasterService.findById(tbServiceReceiptMasBean.getVmVendorId(),
					UserSession.getCurrent().getOrganisation().getOrgid());
			oAccountReceiptReportMasDto.setVmVendorIdDesc(tbAcVendormaster.getVmVendorname());
		}
		oAccountReceiptReportMasDto.setRdAmount(CommonMasterUtility
				.getAmountInIndianCurrency(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdAmount()));
		oAccountReceiptReportMasDto.setRmAmount(
				CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(tbServiceReceiptMasBean.getRmAmount())));
		oAccountReceiptReportMasDto.setApmApplicationId(tbServiceReceiptMasBean.getApmApplicationId());
		if (!tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
				.equals(MainetConstants.CommonConstants.C)) {
			if (tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
					.equals(MainetConstants.AccountReceiptEntry.RT)
					|| tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode().equals(MainetConstants.MENU.N)
					|| tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
							.equals(MainetConstants.NewWaterServiceConstants.BILL_SCHEDULE_DATE)) {

				oAccountReceiptReportMasDto
						.setRdChequeddno(tbServiceReceiptMasBean.getReceiptModeDetailList().getTranRefNumber());

			} else {
				oAccountReceiptReportMasDto
						.setRdChequeddno(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdChequeddno().toString());
			}
		}

		if (tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
				.equals(MainetConstants.AccountReceiptEntry.RT)
				|| tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode().equals(MainetConstants.MENU.N)
				|| tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
						.equals(MainetConstants.NewWaterServiceConstants.BILL_SCHEDULE_DATE)) {
			final String tranRefDate = Utility
					.dateToString(tbServiceReceiptMasBean.getReceiptModeDetailList().getTranRefDate());
			oAccountReceiptReportMasDto.setRdChequedddatetemp(tranRefDate);
		} else {
			final String chkDate = Utility
					.dateToString(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdChequedddate());
			oAccountReceiptReportMasDto.setRdChequedddatetemp(chkDate);
		}

		BankAccountMasterDto bankAccountMasterDto = null;
		if (tbServiceReceiptMasBean.getReceiptModeDetailList().getBaAccountid() == null) {
			bankAccountMasterDto = new BankAccountMasterDto();
		} else {
			bankAccountMasterDto = bankAccountService
					.findAccountByAccountId(tbServiceReceiptMasBean.getReceiptModeDetailList().getBaAccountid());
			oAccountReceiptReportMasDto.setCbBankidDesc(bankAccountMasterDto.getBaAccountNo() + MainetConstants.HYPHEN
					+ bankAccountMasterDto.getBaAccountName());

		}

		oAccountReceiptReportMasDto.setOrganizationName(UserSession.getCurrent().getOrganisation().getONlsOrgname());

		BankMasterDto bankMasterDto = null;
		if (tbServiceReceiptMasBean.getReceiptModeDetailList().getCbBankid() == null) {
			bankMasterDto = new BankMasterDto();
		} else {
			bankMasterDto = bankAccountService
					.getBankbyBranchId(tbServiceReceiptMasBean.getReceiptModeDetailList().getCbBankid());
			oAccountReceiptReportMasDto.setCbBankidDesc(bankMasterDto.getBank());
			oAccountReceiptReportMasDto.setBranch(bankMasterDto.getBranch());
		}

		final String amountInWords = Utility
				.convertBiggerNumberToWord(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdAmount());
		oAccountReceiptReportMasDto.setAmountInWords(amountInWords);

		if ((tbServiceReceiptMasBean.getAdvanceFlag() != null) && !tbServiceReceiptMasBean.getAdvanceFlag().isEmpty()) {
			oAccountReceiptReportMasDto.setAdvanceFlag(tbServiceReceiptMasBean.getAdvanceFlag());
		}
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		List<Object[]> receiptDetList = accountReceiptEntryService
				.findAllDataByReceiptId(tbServiceReceiptMasBean.getRmRcptid(), orgId);
		final List<TbSrcptFeesDetBean> receiptFeeDetail = new ArrayList<>();
		for (final Object[] det : receiptDetList) {

			final TbSrcptFeesDetBean bean = new TbSrcptFeesDetBean();
			if ((det[0] != null) && ((Long) det[0] != 0)) {
				bean.setReceiptHead(budgetCodeService.findAccountHeadCodeBySacHeadId((Long) det[0], orgId));
			}
			bean.setReceiptAmount(CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) det[1]));
			receiptFeeDetail.add(bean);
			oAccountReceiptReportMasDto.setReceiptFeeDetail(receiptFeeDetail);
		}
		if(oAccountReceiptReportMasDto.getCbBankidDesc()==null && tbServiceReceiptMasBean.getReceiptModeDetailList().getRdDrawnon()!=null)
			oAccountReceiptReportMasDto.setCbBankidDesc(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdDrawnon());
		model.addAttribute(MainetConstants.AccountReceiptEntry.ACC_RECEIPT_REPORT, oAccountReceiptReportMasDto);
		return ReceiptReportJsp_Form;
	}

	@RequestMapping(params = "getReceiptAccountHeadData", method = RequestMethod.POST)
	public String getReceiptAccountHeadAllData(final TbServiceReceiptMasBean tbServiceReceiptMasBean, final Model model,
			final RedirectAttributes redirectAttributes, final HttpServletRequest request,
			final BindingResult bindingResult) {
		log("AccountReceiptEntry-'getReceiptAccountHeadData' : 'get ReceiptAccountHead data'");
		String result = MainetConstants.CommonConstant.BLANK;
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		String recCategoryType = CommonMasterUtility.findLookUpCode(MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV,
				UserSession.getCurrent().getOrganisation().getOrgid(), tbServiceReceiptMasBean.getRecCategoryTypeId());
		Map<Long, String> budgetAcHeadMap = null;
		if (recCategoryType.equals("A") || recCategoryType.equals("P")) {
			final LookUp lookUpStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
					PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(),
					UserSession.getCurrent().getOrganisation());
			final Long activeStatusId = lookUpStatus.getLookUpId();
			budgetAcHeadMap = tbAcSecondaryheadMasterService.getAcHeadCodeInReceieptCategoryTypeEntry(activeStatusId,
					orgId, recCategoryType);
			model.addAttribute(MainetConstants.AccountReceiptEntry.HEAD_CODE_MAP, budgetAcHeadMap);
		} else {
			populateBudgetCodes(model);
		}
		tbServiceReceiptMasBean.setTransactionDateDup(tbServiceReceiptMasBean.getTransactionDate());
		tbServiceReceiptMasBean.setRecCategoryType(recCategoryType);
		//model.addAttribute(MainetConstants.AccountReceiptEntry.HEAD_CODE_MAP, budgetAcHeadMap);
		populateModel(model, tbServiceReceiptMasBean, FormMode.CREATE);
		model.addAttribute(MAIN_ENTITY_NAME, tbServiceReceiptMasBean);
		result = JSP_FORM;
		return result;
	}

	@RequestMapping(params = "ActualSLIDate", method = RequestMethod.GET)
	public @ResponseBody String findActualSLIDate(final HttpServletRequest request, final ModelMap model) {
		String date = "";
		try {
			final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE,
					MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
					UserSession.getCurrent().getOrganisation());
			Objects.requireNonNull(lookUp, ApplicationSession.getInstance().getMessage("account.receipt.sli"));
			date = lookUp.getOtherField();
			Objects.requireNonNull(date, ApplicationSession.getInstance().getMessage("account.depositslip.livedate"));
		} catch (final Exception ex) {
			LOGGER.error("Error while finding SLI Date from SLI Prefix:", ex);
			return date = MainetConstants.Y_FLAG;
		}
		return date;
	}

	@RequestMapping(params = "createformFromGrant", method = RequestMethod.POST)
	public String formForCreateFromGrant(final HttpServletRequest request, final Model model,
			@RequestParam("grantId") final Long grantId, @RequestParam("categoryTypeId") final Long categoryTypeId,
			@RequestParam("grtNature") final String grtNature, @RequestParam("grtNo") final String grtNo,
			@RequestParam("sanctionAmt") final String sanctionAmt) {
		log("Action 'createformFromGrant'");
		String result = MainetConstants.CommonConstant.BLANK;
		final TbServiceReceiptMasBean tbServiceReceiptMasBean = new TbServiceReceiptMasBean();
		TbSrcptFeesDetBean bean = new TbSrcptFeesDetBean();
		List<TbSrcptFeesDetBean> beanList = new ArrayList<TbSrcptFeesDetBean>();
		beanList.add(bean);
		tbServiceReceiptMasBean.setReceiptFeeDetail(beanList);
		tbServiceReceiptMasBean.setAdvanceFlag(MainetConstants.MENU.N);
		populateModel(model, tbServiceReceiptMasBean, FormMode.CREATE);
		tbServiceReceiptMasBean.setRecCategoryTypeId(categoryTypeId);
		tbServiceReceiptMasBean.setPrAdvEntryId(grantId);
		tbServiceReceiptMasBean.setBalanceAmount(
				sanctionAmt.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK));
		tbServiceReceiptMasBean.setRmNarration(grtNo + MainetConstants.SEPARATOR + grtNature);
		// tbServiceReceiptMasBean.setFlag("grant");
		LookUp lookUp =CommonMasterUtility.getNonHierarchicalLookUpObject(categoryTypeId);
		  BigDecimal receiptAmount = accountReceiptEntryService.getReceiptsAmount(grantId,
		  UserSession.getCurrent().getOrganisation().getOrgid(),lookUp.getLookUpCode());
		 
				 
			 tbServiceReceiptMasBean.setTotalAmount (receiptAmount);
		
		tbServiceReceiptMasBean.setFlag("GRT");
		model.addAttribute(MAIN_ENTITY_NAME, tbServiceReceiptMasBean);
		result = JSP_FORM;
		return result;
	}

	@RequestMapping(params = "createformFromLoan", method = RequestMethod.POST)
	public String formForCreateFromLoan(final HttpServletRequest request, final Model model,
			@RequestParam("loanId") final Long loanId, @RequestParam("categoryTypeId") final Long categoryTypeId,
			@RequestParam("loanremarks") final String loanremarks,
			@RequestParam("loanPurpose") final String loanPurpose,
			@RequestParam("santionamount") final String santionamount) {
		log("Action 'createformFromLoan'");
		String result = MainetConstants.CommonConstant.BLANK;
		final TbServiceReceiptMasBean tbServiceReceiptMasBean = new TbServiceReceiptMasBean();
		TbSrcptFeesDetBean bean = new TbSrcptFeesDetBean();
		List<TbSrcptFeesDetBean> beanList = new ArrayList<TbSrcptFeesDetBean>();
		beanList.add(bean);
		tbServiceReceiptMasBean.setReceiptFeeDetail(beanList);
		tbServiceReceiptMasBean.setAdvanceFlag(MainetConstants.MENU.N);
		populateModel(model, tbServiceReceiptMasBean, FormMode.CREATE);
		tbServiceReceiptMasBean.setRecCategoryTypeId(categoryTypeId);
		tbServiceReceiptMasBean.setPrAdvEntryId(loanId);
		tbServiceReceiptMasBean.setBalanceAmount(
				santionamount.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK));
		tbServiceReceiptMasBean.setRmNarration(loanremarks + MainetConstants.SEPARATOR + loanPurpose);
		tbServiceReceiptMasBean.setFlag("LNR");
		//code added by rahul.chaubey
		//restricting the receipt amount from exceeding the sanction amount
		
		LookUp lookUp =CommonMasterUtility.getNonHierarchicalLookUpObject(categoryTypeId);
		  BigDecimal receiptAmount = accountReceiptEntryService.getReceiptsAmount(loanId,
		  UserSession.getCurrent().getOrganisation().getOrgid(),lookUp.getLookUpCode());
		 
		  tbServiceReceiptMasBean.setTotalAmount(receiptAmount);
		
		model.addAttribute(MAIN_ENTITY_NAME, tbServiceReceiptMasBean);
		result = JSP_FORM;
		return result;
	}

	@RequestMapping(params = "createformFromInvest", method = RequestMethod.POST)
	public String formForCreateFromInvest(final HttpServletRequest request, final Model model,
			@RequestParam("invstId") final Long invstId, @RequestParam("categoryTypeId") final Long categoryTypeId,
			@RequestParam("invstNo") final String invstNo, @RequestParam("status") final String status,
			@RequestParam("invstAmount") final String invstAmount,@RequestParam("fundId") final Long fundId,@RequestParam("inFdrNo") final String inFdrNo,@RequestParam("bankName") final String bankName) {
		log("Action 'createformFromInvest'");
		String result = MainetConstants.CommonConstant.BLANK;
		final TbServiceReceiptMasBean tbServiceReceiptMasBean = new TbServiceReceiptMasBean();
		TbSrcptFeesDetBean bean = new TbSrcptFeesDetBean();
		List<TbSrcptFeesDetBean> beanList = new ArrayList<TbSrcptFeesDetBean>();
		beanList.add(bean);
		tbServiceReceiptMasBean.setReceiptFeeDetail(beanList);
		tbServiceReceiptMasBean.setAdvanceFlag(MainetConstants.MENU.N);
		populateModel(model, tbServiceReceiptMasBean, FormMode.CREATE);
		tbServiceReceiptMasBean.setRecCategoryTypeId(categoryTypeId);
		tbServiceReceiptMasBean.setPrAdvEntryId(invstId);
		tbServiceReceiptMasBean.setBalanceAmount(
				invstAmount.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK));
		final String fundCode = tbAcFundMasterService.getFundCodeDesc(fundId);
		Department entities = departmentService.getDepartment(AccountConstants.AC.getValue(),
				MainetConstants.CommonConstants.ACTIVE);
		if (entities != null) {
			tbServiceReceiptMasBean.setDpDeptId(entities.getDpDeptid());
		}
		if(status.equals(MainetConstants.STATUS.ACTIVE))
				tbServiceReceiptMasBean.setRmNarration(inFdrNo + MainetConstants.SEPARATOR + fundCode);
		else tbServiceReceiptMasBean.setRmNarration(inFdrNo + MainetConstants.SEPARATOR + fundCode);
		tbServiceReceiptMasBean.setFlag("INV");
		tbServiceReceiptMasBean.setRmReceivedfrom(bankName);
		model.addAttribute(MAIN_ENTITY_NAME, tbServiceReceiptMasBean);
		result = JSP_FORM;
		return result;
	}
	  
}
