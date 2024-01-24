package com.abm.mainet.account.ui.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.domain.AccountBillEntryDeductionDetEntity;
import com.abm.mainet.account.domain.AccountBillEntryExpenditureDetEntity;
import com.abm.mainet.account.domain.AccountBillEntryMasterEnitity;
import com.abm.mainet.account.domain.AccountBillEntryMeasurementDetEntity;
import com.abm.mainet.account.domain.AccountBudgetProjectedExpenditureEntity;
import com.abm.mainet.account.domain.AccountDepositEntity;
import com.abm.mainet.account.domain.AccountTenderDetEntity;
import com.abm.mainet.account.domain.AccountTenderEntryEntity;
import com.abm.mainet.account.dto.AccountBillEntryDeductionDetBean;
import com.abm.mainet.account.dto.AccountBillEntryExpenditureDetBean;
import com.abm.mainet.account.dto.AccountBillEntryMasterBean;
import com.abm.mainet.account.dto.AccountBillEntryMeasurementDetBean;
import com.abm.mainet.account.dto.AccountBudgetProjectedExpenditureBean;
import com.abm.mainet.account.dto.AccountGrantMasterDto;
import com.abm.mainet.account.dto.AccountLoanMasterDto;
import com.abm.mainet.account.dto.DocumentDto;
import com.abm.mainet.account.dto.VoucherReversalDTO;
import com.abm.mainet.account.dto.VoucherTemplateDTO;
import com.abm.mainet.account.service.AccountBillEntryService;
import com.abm.mainet.account.service.AccountBudgetProjectedExpenditureService;
import com.abm.mainet.account.service.AccountChequeOrCashDepositeService;
import com.abm.mainet.account.service.AccountContraVoucherEntryService;
import com.abm.mainet.account.service.AccountDepositService;
import com.abm.mainet.account.service.AccountGrantMasterService;
import com.abm.mainet.account.service.AccountLoanMasterService;
import com.abm.mainet.account.service.AccountTenderEntryService;
import com.abm.mainet.account.service.BudgetCodeService;
import com.abm.mainet.account.service.VoucherTemplateService;
import com.abm.mainet.account.ui.model.response.AccountBillEntryResponse;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbAcVendormasterEntity;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountFundMasterBean;
import com.abm.mainet.common.integration.acccount.dto.TaxDefinationDto;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountFundMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.LocOperationWZMappingDto;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.ITaxDefinationService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.DepartmentLookUp;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.common.workflow.dao.IWorkflowTypeDAO;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;

/** @author tejas.kotekar */
@Controller
@RequestMapping(value = { "/AccountBillEntry.html", "AccountBillAuthorization.html" })
public class AccountBillEntryController extends AbstractController {

	private static final String ACCOUNT_VOUCHER_REVERSAL_HTML = "AccountVoucherReversal.html?back";
	private final String JSP_FORM = "AccountBillEntry/form";
	private final String JSP_VIEW_FORM = "AccountBillEntry/viewForm";
	private final String JSP_RTGS_VIEW_FORM = "AccountBillEntry/viewRTGSForm";
	private final String JSP_LIST = "AccountBillEntry/list";
	private final String SAVE_ACTION_CREATE = "AccountBillEntry?create";
	private static final String ACCOUNT_EXPENDITURE_DETAILS = "AccountExpenditureDetails";
	private final static String BILL_ENTRY_BEAN = "accountBillEntryBean";
	private final static String BILL_TYPE_LIST = "billTypeList";
	private final static String VENDOR_LIST = "vendorList";
	private final static String ACCOUNT_BILL_TYPE = "MI,ESB";
	private final static String BILL_TYPE_CODE = "billTypeCode";
	private final static String ACCOUNT_BILL_TYPE_DEPOSITE = "DE";

	private final String PROJECTED_EXPENDITURE_LIST = "projectedExpenditureList";
	private final String PAC_MAP = "pacMap";
	private final String BUDGET_CODE_LIST = "budgetCodeList";
	private final String DED_DET_LIST = "deductionDetailList";
	private final String MB_DET_LIST = "measurementDetailList";
	private final String DED_PAC_MAP = "dedPacMap";
	private final String BUDGET_DETAILS = "budgetDetails";
	private final String BILL_NO_LIST = "billnoList";
	private final String JSP_OF_ACCOUNTBILL_PRINT = "AccountBillEntry/formPrint";
	private final String ACCOUNT_BILL_ENTRY_VIEW = "AccountBillEntryMasterBean";
	private final String DED_EXP_HEAD_MAP = "dedutionExpHeadMap";
	private final String WORKFLOW_FORM = "AccountBillEntryWorkflow/form";
	private final String WORKFLOW_VIEW_FORM = "AccountBillEntryWorkflow/viewform";
	private final String WORKFLOW_VIEW_FORM_Detail = "AccountBillEntryWorkflow/viewformDetails";
	private final String REFUND_FORM = "AccountRefundBillEntry/form";
	private final static String VALUE_TYPE_LIST = "valueTypeList";

	

	private static final Logger LOGGER = Logger.getLogger(AccountBillEntryController.class);
	List<AccountBillEntryMasterBean> masterBeanList = null;

	@Resource
	private AccountBillEntryService billEntryService;

	@Resource
	private AccountBudgetProjectedExpenditureService accountBudgetProjectedExpenditureService;

	@Resource
	AccountContraVoucherEntryService accountContraVoucherEntryService;

	@Resource
	private BudgetCodeService budgetCodeService;

	@Resource
	private IEmployeeService employeeService;

	@Resource
	private VoucherTemplateService voucherTemplateService;

	@Resource
	private TbAcVendormasterService tbAcVendormasterService;

	@Resource
	private AccountChequeOrCashDepositeService accountChequeOrCashDepositeService;

	@Resource
	private AccountDepositService accountDepositService;

	@Resource
	private ILocationMasService locMasService;

	@Resource
	private SecondaryheadMasterService secondaryheadMasterService;

	@Resource
	private TbDepartmentService tbDepartmentService;

	@Resource
	private TbFinancialyearService financialyearService;

	@Resource
	private DepartmentService departmentService;

	@Resource
	private AccountTenderEntryService tbAcTenderEntryService;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private IFileUploadService accountFileUpload;

	@Resource
	private IAttachDocsService attachDocsService;

	@Autowired
	private ITaxDefinationService taxDefinationService;

	@Resource
	private AccountFieldMasterService tbAcFieldMasterService;

	@Resource
	private AccountGrantMasterService accountGrantMasterService;

	@Autowired
	private ServiceMasterService serviceMasterService;
	
	@Autowired
	AccountLoanMasterService accountLoanMasterService;
	
	@Autowired
	private TbOrganisationService tbOrganisationService;
	
	@Resource
	private TbAcCodingstructureMasService tbAcCodingstructureMasService;
	
	@Autowired
	private IWorkflowTyepResolverService workflowTyepResolverService;
	
	
	@Autowired
	private IWorkflowTypeDAO iWorkflowTypeDAO;
	
	@Autowired
	private IWorkFlowTypeService workFlowTypeService;
	
	/**
	 * @param controllerClass
	 * @param entityName
	 */
	public AccountBillEntryController() {
		super(AccountBillEntryController.class, BILL_ENTRY_BEAN);
		log("AccountBillEntryController created.");
	}

	public void populateModel(final Model model, final AccountBillEntryMasterBean accountBillEntryBean,
			final String formMode) {
		if (accountBillEntryBean.getAdvanceFlag() != null && !accountBillEntryBean.getAdvanceFlag().isEmpty()) {
			if (accountBillEntryBean.getAdvanceFlag().equals(MainetConstants.Y_FLAG)) {
			}
		} else if (accountBillEntryBean.getDepositFlag() == null || accountBillEntryBean.getDepositFlag().isEmpty()) {
			if (formMode == MODE_CREATE) {
				final List<LookUp> billTypeLookupList = CommonMasterUtility.getLookUps(AccountPrefix.ABT.toString(),
						UserSession.getCurrent().getOrganisation());
				for (final LookUp lookUp : billTypeLookupList) {
					if (lookUp.getDefaultVal() != null && !lookUp.getDefaultVal().isEmpty()) {
						if (lookUp.getDefaultVal().equals("Y")) {
							accountBillEntryBean.setBillTypeId(lookUp.getLookUpId());
						}
					}
				}
			}
		}
		model.addAttribute(BILL_ENTRY_BEAN, accountBillEntryBean);
		
		model.addAttribute(BILL_TYPE_CODE, ACCOUNT_BILL_TYPE);
		if (formMode == MODE_CREATE) {
			model.addAttribute(MODE, MODE_CREATE);
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
			populateListOfBillType(model);
			populateVendorActiveStatusList(model);
			populateExpenditureDetailsAtStartup(model, accountBillEntryBean);
			populateDeductionDetails(model);
			checkingBudgetDefParameter(model);
			isMakerChecker(model, accountBillEntryBean);
		} else if (formMode == MODE_UPDATE) {
			model.addAttribute(MODE, MODE_UPDATE);
			populateListOfBillType(model);
			populateVendorList(model);
			checkingBudgetDefParameter(model);
			isMakerChecker(model, accountBillEntryBean);
		} else if (formMode == MODE_VIEW) {
			isMakerChecker(model, accountBillEntryBean);
		} else if (formMode == MODE_EDIT) {
			isMakerChecker(model, accountBillEntryBean);
		}
	}

	
	
	
	
	private void checkingBudgetDefParameter(Model model) {
		final List<LookUp> budgetParametersLookUpList = CommonMasterUtility.getListLookup(PrefixConstants.BDP,
				UserSession.getCurrent().getOrganisation());
		if (budgetParametersLookUpList != null) {
			for (final LookUp lookUp : budgetParametersLookUpList) {
				if (lookUp != null) {
					if (lookUp.getLookUpCode().equals(PrefixConstants.BILL_ENTRY_CPD_VALUE)) {
						model.addAttribute(MainetConstants.AccountBillEntry.BUDGET_PARAMETERS_STATUS,
								MainetConstants.MASTER.Y);
					}
				}
			}
		}
	}

	@RequestMapping()
	public String populateGridList(final Model model, final HttpServletRequest httpServletRequest) {
		log("Action 'form'");
		masterBeanList = new ArrayList<>();
		final AccountBillEntryMasterBean accountBillEntryBean = new AccountBillEntryMasterBean();
		populateVendorList(model);
		populateDepartments(model);
		if (!httpServletRequest.getRequestURI().contains(MainetConstants.AccountBillEntry.ENTRY)) {
			accountBillEntryBean.setAuthorizationMode(MainetConstants.AccountBillEntry.AUTH);
			model.addAttribute(MainetConstants.AccountBillEntry.MODEL_CHK, MainetConstants.AccountBillEntry.AUTH);
		} else {
			model.addAttribute(MainetConstants.AccountBillEntry.MODEL_CHK, MainetConstants.Actions.CREATE);
		}
		populateModel(model, accountBillEntryBean, MODE_CREATE);
		isWorkflowDefine(model, accountBillEntryBean);
		masterBeanList.clear();
		return JSP_LIST;
	}

	private void populateDepartments(final Model model) {
	     List<DepartmentLookUp> deptList =null; /*CommonMasterUtility
				.getDepartmentForWS(UserSession.getCurrent().getOrganisation());*/
		 List<Department> department = departmentService.getDepartments(UserSession.getCurrent().getOrganisation().getOrgid(),"A");
		 deptList =department.stream().map(m->{
			 DepartmentLookUp lookup=new DepartmentLookUp();
			 lookup.setDefaultVal(m.getDpDeptdesc());
			 lookup.setLookUpId(m.getDpDeptid());
			 lookup.setLookUpCode(m.getDpDeptcode());
			 return lookup; 
		 }).collect(Collectors.toList());
		model.addAttribute(MainetConstants.AccountBillEntry.DEPT_LIST, deptList);
	}

	private void isMakerChecker(final Model model, final AccountBillEntryMasterBean accountBillEntryBean) {
		final Organisation organisation = UserSession.getCurrent().getOrganisation();
		final String isMakerChecker = billEntryService.isMakerChecker(organisation);
		accountBillEntryBean.setIsMakerChecker(isMakerChecker);
	}
	private void populateField(final Model model) {
		model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
				tbAcFieldMasterService.getFieldMasterLastLevels(UserSession.getCurrent().getOrganisation().getOrgid()));
	}
	
	private void isWorkflowDefine(final Model model, final AccountBillEntryMasterBean accountBillEntryBean) {
		 final int langId = UserSession.getCurrent().getLanguageId();
	     final Organisation org = UserSession.getCurrent().getOrganisation();
		 final LookUp activenessLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.IsLookUp.ACTIVE,PrefixConstants.LookUp.ACN, langId, org);
		 ServiceMaster service = serviceMasterService.getServiceByShortName("VB", UserSession.getCurrent().getOrganisation().getOrgid());
		    if(Long.valueOf(activenessLookup.getLookUpId())==service.getSmServActive()) {
		    	accountBillEntryBean.setIsServiceActive("active");
		    }else {
		    	accountBillEntryBean.setIsServiceActive("inActive");
		    }
	}
	

	@RequestMapping(params = "getGridData")
	public @ResponseBody AccountBillEntryResponse gridData(final HttpServletRequest request, final Model model) {
		final int page = Integer.parseInt(request.getParameter(MainetConstants.AccountConstants.PAGE.getValue()));
		final AccountBillEntryResponse response = new AccountBillEntryResponse();
		response.setRows(masterBeanList);
		response.setTotal(masterBeanList.size());
		response.setRecords(masterBeanList.size());
		response.setPage(page);
		model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, masterBeanList);
		return response;
	}

	@RequestMapping(params = "searchBillData")
	public @ResponseBody List<AccountBillEntryMasterBean> getBillEntryData(
			@RequestParam("fromDate") final String fromDate, @RequestParam("toDate") final String toDate,
			@RequestParam("bmBilltypeCpdId") final Long billType, @RequestParam("billNo") final String billNo,
			@RequestParam("vendorId") final Long vendorId, @RequestParam("departmentId") final Long departmentId,
			final HttpServletRequest request, final Model model) {
		masterBeanList = new ArrayList<>();
		masterBeanList.clear();
		AccountBillEntryMasterBean masterBean = null;
		List<AccountBillEntryMasterEnitity> billEntryList = null;
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if ((fromDate != MainetConstants.BLANK) || (toDate != MainetConstants.BLANK)
				|| (billNo != MainetConstants.BLANK) || (billType != null) || (vendorId != null)
				|| (departmentId != null)) {
			billEntryList = billEntryService.getBillEntryDetails(orgId, fromDate, toDate, billNo, billType, vendorId,
					departmentId);
		} else {
			billEntryList = billEntryService.getAllBillEntryData(orgId);
		}
		for (final AccountBillEntryMasterEnitity list : billEntryList) {
			BigDecimal totalDeductionAmount = BigDecimal.ZERO;
			BigDecimal totalDisallowedAmount = BigDecimal.ZERO;
			BigDecimal netPayableAmount = BigDecimal.ZERO;
			masterBean = new AccountBillEntryMasterBean();
			masterBean.setId(list.getId());
			masterBean.setBillNo(list.getBillNo());
			String billAmountStr = "";
			if (list.getBillTotalAmount() != null) {
				masterBean.setBillTotalAmount(list.getBillTotalAmount());
				billAmountStr = CommonMasterUtility.getAmountInIndianCurrency(list.getBillTotalAmount());
				masterBean.setBillAmountStr(billAmountStr);
			}
			masterBean.setVendorDesc(list.getVendorName());
			if (list.getCheckerAuthorization().equals(MainetConstants.RnLCommon.N)) {
				masterBean.setAuthorizationStatus(MainetConstants.AccountConstants.UNAUTHORIZED.getValue());
			}
			if (list.getCheckerAuthorization().equals(MainetConstants.RnLCommon.Y)) {
				masterBean.setAuthorizationStatus(MainetConstants.AccountConstants.AUTHORIZED.getValue());
			}
			if (list.getCheckerAuthorization().equals(MainetConstants.AccountBillEntry.R)) {
				masterBean.setAuthorizationStatus(MainetConstants.AccountConstants.REJECTED.getValue());
			}
			final List<AccountBillEntryExpenditureDetEntity> expDetList = list.getExpenditureDetailList();
			for (final AccountBillEntryExpenditureDetEntity expDetEntity : expDetList) {
				if ((expDetEntity.getDisallowedAmount() != null)
						&& !expDetEntity.getDisallowedAmount().equals(MainetConstants.BLANK)) {
					totalDisallowedAmount = totalDisallowedAmount.add(expDetEntity.getDisallowedAmount());
				}
			}
			final List<AccountBillEntryDeductionDetEntity> dedDetList = list.getDeductionDetailList();
			if ((dedDetList != null) && !dedDetList.isEmpty()) {
				for (final AccountBillEntryDeductionDetEntity dedDetEntity : dedDetList) {
					if (dedDetEntity.getDeductionAmount() == null) {
						dedDetEntity.setDeductionAmount(new BigDecimal(0.00));
					}
					totalDeductionAmount = totalDeductionAmount.add(dedDetEntity.getDeductionAmount());
					masterBean.setTotalDeductions(totalDeductionAmount);
					final String deductionsStr = CommonMasterUtility.getAmountInIndianCurrency(totalDeductionAmount);
					masterBean.setDeductionsStr(deductionsStr);
					netPayableAmount = masterBean.getBillTotalAmount()
							.subtract(totalDisallowedAmount.add(totalDeductionAmount));
					masterBean.setNetPayable(netPayableAmount);
					final String netPayableStr = CommonMasterUtility.getAmountInIndianCurrency(netPayableAmount);
					masterBean.setNetPayableStr(netPayableStr);
				}
			}
			if (masterBean.getDeductionsStr() == null || masterBean.getDeductionsStr().isEmpty()) {
				masterBean.setDeductionsStr("0.00");
			}
			if (masterBean.getNetPayableStr() == null || masterBean.getNetPayableStr().isEmpty()) {
				netPayableAmount = masterBean.getBillTotalAmount()
						.subtract(totalDisallowedAmount.add(totalDeductionAmount));
				masterBean.setNetPayableStr(CommonMasterUtility.getAmountInIndianCurrency(netPayableAmount).toString());
			}
			masterBeanList.add(masterBean);
		}
		return masterBeanList;
	}

	private void setTransactionHeadPrefix(final AccountBillEntryMasterBean accountBillEntryBean) {
		final Organisation organisation = UserSession.getCurrent().getOrganisation();
		final LookUp transactionHeadBudgetCode = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				MainetConstants.AccountConstants.FNC.getValue(), PrefixConstants.AccountPrefix.TSH.toString(),
				UserSession.getCurrent().getLanguageId(), organisation);
		if (transactionHeadBudgetCode.getDefaultVal().equals(MainetConstants.MENU.Y)) {
			accountBillEntryBean.setTransHeadFlagBudgetCode(MainetConstants.AccountConstants.TRUE.getValue());
		}
	}

	@RequestMapping(params = "formForCreate", method = RequestMethod.POST)
	public String formForCreate(final Model model) {
		log("Action 'form'");
		fileUpload.sessionCleanUpForFileUpload();
		final AccountBillEntryMasterBean accountBillEntryBean = new AccountBillEntryMasterBean();
		long fieldId = 0;
		accountBillEntryBean.setBillEntryDate(UtilityService.convertDateToDDMMYYYY(new Date()));
		accountBillEntryBean.setBillDate(UtilityService.convertDateToDDMMYYYY(new Date()));
		accountBillEntryBean.setDepartmentId(UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid());
		TbLocationMas locMas = locMasService.findById(UserSession.getCurrent().getEmployee().getTbLocationMas().getLocId());
		if (locMas!=null && (locMas.getLocRevenueWZMappingDto() != null) && !locMas.getLocRevenueWZMappingDto().isEmpty()) {
			fieldId = locMas.getLocRevenueWZMappingDto().get(0).getCodIdRevLevel1();
			accountBillEntryBean.setFieldId(fieldId);
		}
		model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
		model.addAttribute(MainetConstants.AccountBillEntry.MODEL_CHK, MainetConstants.Actions.CREATE);
		
		populateModel(model, accountBillEntryBean, MODE_CREATE);
		populateDepartments(model);
		populateField(model);
		populateFund(model);
		
		List<AccountBillEntryMeasurementDetBean> measurementDetailList = new ArrayList<>();
		AccountBillEntryMeasurementDetBean billMeasureDetBean = null;
		measurementDetailList.add(billMeasureDetBean);
		model.addAttribute(MB_DET_LIST, measurementDetailList);
		// setTransactionHeadPrefix(accountBillEntryBean);
		List<LookUp> billTypeLookupList = CommonMasterUtility.getLookUps(PrefixConstants.AccountPrefix.ABT.toString(),
				UserSession.getCurrent().getOrganisation());
		billTypeLookupList = billTypeLookupList.stream().parallel()
				.filter(tr -> tr.getLookUpCode().equalsIgnoreCase("MI") || tr.getLookUpCode().equalsIgnoreCase("ESB")|| tr.getLookUpCode().equalsIgnoreCase("AD")|| tr.getLookUpCode().equalsIgnoreCase("S") || tr.getLookUpCode().equalsIgnoreCase("W")||tr.getLookUpCode().equalsIgnoreCase("GFD"))
				.collect(Collectors.toList());
		model.addAttribute(VALUE_TYPE_LIST,(CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.VTY,
				UserSession.getCurrent().getOrganisation())));
		model.addAttribute(BILL_TYPE_LIST, billTypeLookupList);
		return JSP_FORM;
	}

	@RequestMapping(params = "createformFromAdvance", method = RequestMethod.POST)
	public String formForCreateFromAdvance(final HttpServletRequest request, final Model model,
			@RequestParam("prAdvEntryId") final Long prAdvEntryId,
			@RequestParam("advanceNumber") final Long advanceNumber,
			@RequestParam("advanceDate") final String advanceDate, @RequestParam("vendorId") final Long vendorId,
			@RequestParam("balanceAmount") final String balanceAmount,
			@RequestParam("pacHeadId") final Long pacHeadId) {
		log("Action 'createformFromAdvance'");
		final AccountBillEntryMasterBean accountBillEntryBean = new AccountBillEntryMasterBean();
		// get passed data and set to bean
		// set a flag for back button
		populateFund(model);
		accountBillEntryBean.setPrAdvEntryId(prAdvEntryId);
		 
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final Organisation org = UserSession.getCurrent().getOrganisation();
		if (vendorId != null) {
			accountBillEntryBean.setVendorId(vendorId);
		}
		final Long advBillTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				MainetConstants.AccountBillEntry.AD, PrefixConstants.AccountPrefix.ABT.toString(), orgId);
		accountBillEntryBean.setBillTypeId(advBillTypeId);
		accountBillEntryBean.setNarration(MainetConstants.AccountBillEntry.INVOICE_ADVANCE_NO + advanceNumber
				+ MainetConstants.SEPARATOR + MainetConstants.AccountBillEntry.ADVANCE_DATE + advanceDate);
		accountBillEntryBean.setBalAmount(new BigDecimal(
				balanceAmount.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK)));
		accountBillEntryBean.setInvoiceValue(new BigDecimal(
				balanceAmount.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK)));
		accountBillEntryBean.setBillTotalAmount(new BigDecimal(
				balanceAmount.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK)));
		accountBillEntryBean.setBalAmount(new BigDecimal(
				balanceAmount.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK)));
		accountBillEntryBean.setBmTaxableValue(new BigDecimal(
				balanceAmount.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK)));
		accountBillEntryBean.setBillNo(advanceNumber.toString());
		accountBillEntryBean.setInvoiceDate(advanceDate);
		accountBillEntryBean.setTransactionDate(advanceDate);

		final List<AccountBillEntryExpenditureDetBean> expenditureDetailList = accountBillEntryBean
				.getExpenditureDetailList();
		final AccountBillEntryExpenditureDetBean expBean = new AccountBillEntryExpenditureDetBean();
		expBean.setBudgetCodeId(pacHeadId);
		expBean.setActualAmount(new BigDecimal(
				balanceAmount.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK)));
		expBean.setBillChargesAmount(new BigDecimal(
				balanceAmount.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK)));
		expenditureDetailList.add(expBean);
		accountBillEntryBean.setExpenditureDetailList(expenditureDetailList);

		accountBillEntryBean.setBudgetCodeId(pacHeadId);
		accountBillEntryBean.setAdvanceFlag(MainetConstants.MENU.Y);
		accountBillEntryBean.setWorkOrderFlag(MainetConstants.MENU.N);
		model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
		populateModel(model, accountBillEntryBean, MODE_CREATE);
		populateField(model);
		populateDepartments(model);
		// Task #6674 11. Set Bill type as Advance Adjustment by default as well as keep
		// it disabled so a user will not change it.
		List<LookUp> billTypeLookupList = CommonMasterUtility.getLookUps(PrefixConstants.AccountPrefix.ABT.toString(),
				UserSession.getCurrent().getOrganisation());
		billTypeLookupList = billTypeLookupList.stream().parallel()
				.filter(l -> l.getLookUpCode().equalsIgnoreCase("ADJ")).collect(Collectors.toList());
		model.addAttribute(BILL_TYPE_LIST, billTypeLookupList);
		populateListOfBillType(model);
		if(CollectionUtils.isNotEmpty(billTypeLookupList)) {
			accountBillEntryBean.setBillTypeId(billTypeLookupList.get(0).getLookUpId());
		}
		
		Map<Long, String> acHeadLookUp = secondaryheadMasterService.getDeductionHeadForAdvAdjustment(orgId,
				prAdvEntryId);
		model.addAttribute(DED_PAC_MAP, acHeadLookUp);
		final List<AccountBillEntryDeductionDetBean> billDeductionDetBeanList = acHeadLookUp.entrySet().stream()
				.map(e -> {
					final AccountBillEntryDeductionDetBean bean = new AccountBillEntryDeductionDetBean();
					bean.setBudgetCodeId(e.getKey());
					return bean;
				}).collect(Collectors.toList());
		accountBillEntryBean.setDeductionDetailList(billDeductionDetBeanList);
		model.addAttribute(DED_DET_LIST, billDeductionDetBeanList);
		/* setTransactionHeadPrefix(accountBillEntryBean); */
		model.addAttribute(VALUE_TYPE_LIST,(CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.VTY,
				UserSession.getCurrent().getOrganisation())));
		return JSP_FORM;
	}

	@RequestMapping(params = "createformFromDeposit", method = RequestMethod.POST)
	public String formForCreateFromDeposit(final HttpServletRequest request, final Model model,
			@RequestParam("depId") final Long depId) {
		log("Action 'createformFromDeposit'");
		populateFund(model);
		final AccountBillEntryMasterBean accountBillEntryBean = new AccountBillEntryMasterBean();
		final AccountDepositEntity depositList = accountDepositService.fidbyId(depId);
		accountBillEntryBean.setDepId(depId);
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if (depositList.getTbVendormaster().getVmVendorid() != null) {
			accountBillEntryBean.setVendorId(depositList.getTbVendormaster().getVmVendorid());
		}
		if (depositList.getTbDepartment()!= null) {
			accountBillEntryBean.setDepartmentId(depositList.getTbDepartment().getDpDeptid());
		}
		
		final Long depBillTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				MainetConstants.AccountBillEntry.DE, PrefixConstants.AccountPrefix.ABT.toString(), orgId);

		accountBillEntryBean.setBillTypeId(depBillTypeId);
		accountBillEntryBean.setBillNo(depositList.getDepNo().toString());
		String date = MainetConstants.CommonConstant.BLANK;
		if (depositList.getDepReceiptdt() != null) {
			date = UtilityService.convertDateToDDMMYYYY(depositList.getDepReceiptdt());
		}
		accountBillEntryBean.setInvoiceDate(date);
		accountBillEntryBean.setTransactionDate(
				Utility.dateToString(depositList.getDepReceiptdt())/*
																	 * Utility.dateToString(new Date())
																	 */);
		accountBillEntryBean.setInvoiceNumber(depositList.getDepNo().toString());
		accountBillEntryBean.setNarration(MainetConstants.AccountBillEntry.INVOICE_DEPOSIT_NO + depositList.getDepNo()
				+ MainetConstants.SEPARATOR + MainetConstants.AccountBillEntry.DEPOSIT_DATE + date);
		accountBillEntryBean.setBalAmount(depositList.getDepRefundBal());
		accountBillEntryBean.setBmTaxableValue(depositList.getDepRefundBal());
		accountBillEntryBean.setTotalDedcutionsAmountStr("0.00");
		accountBillEntryBean.setTotalDisallowedAmount(new BigDecimal(0.00));
		accountBillEntryBean.setInvoiceValue(depositList.getDepRefundBal());
		accountBillEntryBean.setBillTotalAmount(depositList.getDepRefundBal());
		accountBillEntryBean.setVendorDesc(depositList.getTbVendormaster().getVmVendorcode() + " - "
				+ depositList.getTbVendormaster().getVmVendorname());

		final List<AccountBillEntryExpenditureDetBean> expenditureDetailList = accountBillEntryBean
				.getExpenditureDetailList();
		final AccountBillEntryExpenditureDetBean expBean = new AccountBillEntryExpenditureDetBean();
		expBean.setBudgetCodeId(depositList.getSacHeadId());
		expBean.setActualAmount(depositList.getDepRefundBal());
		expBean.setBillChargesAmount(depositList.getDepRefundBal());
		expenditureDetailList.add(expBean);
		accountBillEntryBean.setExpenditureDetailList(expenditureDetailList);
		accountBillEntryBean.setBudgetCodeId(depositList.getSacHeadId());
		accountBillEntryBean.setDepositFlag(MainetConstants.MENU.Y);
		accountBillEntryBean.setWorkOrderFlag(MainetConstants.MENU.N);
		model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
		populateModel(model, accountBillEntryBean, MODE_CREATE);
		populateDepartments(model);
		populateField(model);
		// setTransactionHeadPrefix(accountBillEntryBean);
		model.addAttribute(BILL_TYPE_CODE, ACCOUNT_BILL_TYPE_DEPOSITE);
		List<LookUp> billTypeLookupList = CommonMasterUtility.getLookUps(PrefixConstants.AccountPrefix.ABT.toString(),
				UserSession.getCurrent().getOrganisation());
		billTypeLookupList = billTypeLookupList.stream().parallel()
				.filter(tr -> tr.getLookUpCode().equalsIgnoreCase("DE")).collect(Collectors.toList());
		model.addAttribute(BILL_TYPE_LIST, billTypeLookupList);
		model.addAttribute("depositDate", date);
		model.addAttribute("defectLiablityDate", Utility.dateToString(depositList.getDefectLiabilityDate()));
		return JSP_FORM;
	}

	@RequestMapping(params = "createformFromWorkOrder", method = RequestMethod.POST)
	public String createformFromWorkOrder(final HttpServletRequest request, final Model model,
			@RequestParam("trTenderId") final Long trTenderId) {
		log("Action 'createformFromWorkOrder'");
		AccountBillEntryMasterBean accountBillEntryBean = new AccountBillEntryMasterBean();
		// get passed data and set to bean
		// set a flag for back button
		AccountTenderEntryEntity tenderEntryList = tbAcTenderEntryService.findById(trTenderId);
		accountBillEntryBean.setTrTenderId(trTenderId);
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if (tenderEntryList.getTbVendormaster().getVmVendorid() != null) {
			accountBillEntryBean.setVendorId(tenderEntryList.getTbVendormaster().getVmVendorid());
		}
		final Long depBillTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
				MainetConstants.AccountBillEntry.LI, PrefixConstants.AccountPrefix.ABT.toString(), orgId);
		accountBillEntryBean.setBillTypeId(depBillTypeId);
		accountBillEntryBean.setBillNo(tenderEntryList.getTrTenderNo().toString());
		String date = MainetConstants.CommonConstant.BLANK;
		if (tenderEntryList.getTrTenderDate() != null) {
			date = UtilityService.convertDateToDDMMYYYY(tenderEntryList.getTrTenderDate());
		}
		accountBillEntryBean.setInvoiceDate(date);
		accountBillEntryBean.setTransactionDate(
				Utility.dateToString(tenderEntryList.getTrTenderDate())/*
																		 * Utility.dateToString(new Date())
																		 */);
		accountBillEntryBean.setInvoiceNumber(tenderEntryList.getTrTenderNo().toString());
		accountBillEntryBean
				.setNarration(MainetConstants.AccountBillEntry.INVOICE_WORKORDER_NO + tenderEntryList.getTrTenderNo()
						+ MainetConstants.SEPARATOR + MainetConstants.AccountBillEntry.WORKORDER_DATE + date);
		accountBillEntryBean.setBalAmount(tenderEntryList.getTrTenderAmount());
		accountBillEntryBean.setTotalDedcutionsAmountStr("0.00");
		accountBillEntryBean.setTotalDisallowedAmount(new BigDecimal(0.00));
		accountBillEntryBean.setInvoiceValue(tenderEntryList.getTrTenderAmount());
		accountBillEntryBean.setBillTotalAmount(tenderEntryList.getTrTenderAmount());
		accountBillEntryBean.setVendorDesc(tenderEntryList.getTbVendormaster().getVmVendorcode() + " - "
				+ tenderEntryList.getTbVendormaster().getVmVendorname());

		final List<AccountBillEntryExpenditureDetBean> expenditureDetailList = accountBillEntryBean
				.getExpenditureDetailList();

		List<AccountTenderDetEntity> listOfTbAcTenderDet = tenderEntryList.getListOfTbAcTenderDet();
		for (AccountTenderDetEntity accountTenderDetEntity : listOfTbAcTenderDet) {
			final AccountBillEntryExpenditureDetBean expBean = new AccountBillEntryExpenditureDetBean();
			expBean.setBudgetCodeId(accountTenderDetEntity.getSacHeadId());
			accountBillEntryBean.setBudgetCodeId(accountTenderDetEntity.getSacHeadId());
			expBean.setActualAmount(accountTenderDetEntity.getTenderDetailAmt());
			expBean.setBillChargesAmount(accountTenderDetEntity.getTenderDetailAmt());
			expenditureDetailList.add(expBean);
		}
		accountBillEntryBean.setExpenditureDetailList(expenditureDetailList);
		accountBillEntryBean.setDepositFlag(MainetConstants.MENU.Y);
		model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
		populateModel(model, accountBillEntryBean, MODE_CREATE);
		setTransactionHeadPrefix(accountBillEntryBean);
		return JSP_FORM;
	}

	@RequestMapping(params = "formForUpdate", method = RequestMethod.POST)
	public String formForUpdate(final Model model, final HttpServletRequest request,
			@RequestParam("bmId") final Long bmId,
			@RequestParam(MainetConstants.TAX_HEAD_MAPPING_MASTER.MODE_DATA) final String viewmode) {
		log("Action 'formForUpdate'");
		fileUpload.sessionCleanUpForFileUpload();
		if (viewmode.equals(MainetConstants.VIEW)) {
			model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.SUCCESS);
		} else {
			model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
		}
		AccountBillEntryMasterBean accountBillEntryBean = new AccountBillEntryMasterBean();
		populateModel(model, accountBillEntryBean, MODE_UPDATE);
		populateDepartments(model);
		populateField(model);
		populateFund(model);
		if (!request.getRequestURI().contains(MainetConstants.AccountBillEntry.ENTRY)) {
			accountBillEntryBean.setAuthorizationMode(MainetConstants.AccountBillEntry.AUTH);
			model.addAttribute(MainetConstants.AccountBillEntry.MODEL_CHK, MainetConstants.AccountBillEntry.AUTH);
		} else {
			model.addAttribute(MainetConstants.AccountBillEntry.MODEL_CHK, MainetConstants.Complaint.MODE_EDIT);
		}
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		accountBillEntryBean = billEntryService.populateBillEntryEditData(accountBillEntryBean, bmId, orgId);
		
		Map<Long, String> dedutionExpHeadMap = new LinkedHashMap<>();
		List<AccountBillEntryExpenditureDetBean> expDetList = accountBillEntryBean.getExpenditureDetailList();
		for (AccountBillEntryExpenditureDetBean accountBillEntryExpenditureDetBean : expDetList) {
			Long sacheadid = accountBillEntryExpenditureDetBean.getSacHeadId();
			String accountHeadCode = "";
			if (sacheadid != null) {
				accountHeadCode = secondaryheadMasterService.findByAccountHead(sacheadid);
				dedutionExpHeadMap.put(sacheadid, accountHeadCode);
			}
		}
		model.addAttribute("dedutionExpHeadMap", dedutionExpHeadMap);
		if (accountBillEntryBean.getMeasurementDetailList() != null && !accountBillEntryBean.getMeasurementDetailList().isEmpty()) {
			model.addAttribute(MB_DET_LIST, accountBillEntryBean.getMeasurementDetailList());
		} else {
			List<AccountBillEntryMeasurementDetBean> measurementDetailList = new ArrayList<>();
			AccountBillEntryMeasurementDetBean billMeasureDetBean = null;
			measurementDetailList.add(billMeasureDetBean);
			model.addAttribute(MB_DET_LIST, measurementDetailList);
		}

		final String accountIds = MainetConstants.RECEIPT_MASTER.Module + MainetConstants.operator.FORWARD_SLACE + bmId;
		final List<AttachDocs> attachDocsList = attachDocsService
				.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), accountIds);
		accountBillEntryBean.setAttachDocsList(attachDocsList);
		model.addAttribute(MainetConstants.AccountBillEntry.EDIT_DATA, accountBillEntryBean);
		model.addAttribute(MainetConstants.AccountBillEntry.EXPENDITURE_HEAD_MAP, secondaryheadMasterService
				.findExpenditureHeadMapAccountTypeIsOthers(UserSession.getCurrent().getOrganisation().getOrgid()));
		final Organisation org = UserSession.getCurrent().getOrganisation();
		Long taxMasLookUpId = null;
		List<LookUp> taxMaslookUpList = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC, 1, org);
		for (LookUp lookUp : taxMaslookUpList) {
			if (lookUp.getLookUpCode().equals(MainetConstants.StandardAccountHeadMapping.TDS)) {
				taxMasLookUpId = lookUp.getLookUpId();
			}
		}
		model.addAttribute(VALUE_TYPE_LIST,(CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.VTY,
				UserSession.getCurrent().getOrganisation())));
		// tax maser to bill deduction account heads
		model.addAttribute(MainetConstants.AccountBillEntry.DEDUCT_HEAD_MAP,
				secondaryheadMasterService.getTaxMasBillDeductionAcHeadAllDetails(org.getOrgid(), taxMasLookUpId));
		return JSP_FORM;
	}

	@RequestMapping(params = "formForView", method = RequestMethod.POST)
	public String formForView(final Model model, final HttpServletRequest request,
			@RequestParam("bmId") final Long bmId,
			@RequestParam(MainetConstants.TAX_HEAD_MAPPING_MASTER.MODE_DATA) final String viewmode) {

		log("Action formForView");
		fileUpload.sessionCleanUpForFileUpload();
		AccountBillEntryMasterBean accountBillEntryBean = new AccountBillEntryMasterBean();
		if (viewmode.equals(MainetConstants.VIEW)) {
			model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.SUCCESS);
		} else if (viewmode.equals(MainetConstants.AccountBillEntry.PAYMENT_REVIEW)) {
			model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.SUCCESS);
			accountBillEntryBean.setPaymentEntryFlag(MainetConstants.AccountBillEntry.PAYMENT);
		} else {
			model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
		}
		populateModel(model, accountBillEntryBean, MODE_VIEW);
		if (!request.getRequestURI().contains(MainetConstants.AccountBillEntry.ENTRY)) {
			accountBillEntryBean.setAuthorizationMode(MainetConstants.AccountBillEntry.AUTH);
		}
		accountBillEntryBean = billEntryService.populateBillEntryViewData(accountBillEntryBean, bmId,
				UserSession.getCurrent().getOrganisation().getOrgid());

		Map<Long, String> dedutionExpHeadMap = new LinkedHashMap<>();
		List<AccountBillEntryExpenditureDetBean> expDetList = accountBillEntryBean.getExpenditureDetailList();
		for (AccountBillEntryExpenditureDetBean accountBillEntryExpenditureDetBean : expDetList) {
			Long sacheadid = accountBillEntryExpenditureDetBean.getSacHeadId();
			String accountHeadCode = "";
			if (sacheadid != null) {
				accountHeadCode = secondaryheadMasterService.findByAccountHead(sacheadid);
				dedutionExpHeadMap.put(sacheadid, accountHeadCode);
			}
		}
		model.addAttribute("dedutionExpHeadMap", dedutionExpHeadMap);

		final String accountIds = MainetConstants.RECEIPT_MASTER.Module + MainetConstants.operator.FORWARD_SLACE + bmId;
		final List<AttachDocs> attachDocsList = attachDocsService
				.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), accountIds);
		accountBillEntryBean.setAttachDocsList(attachDocsList);
		populateDepartments(model);
		populateField(model);
		populateFund(model);
		model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.VIEW_DATA, accountBillEntryBean);
		model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.MODE_FLAG, MainetConstants.RnLCommon.MODE_VIEW);
		return JSP_VIEW_FORM;
	}

	public List<AccountBillEntryMasterBean> populateListOfBillNo(final Model model) {

		final List<AccountBillEntryMasterBean> billEntryMasterBeanList = new ArrayList<>();
		AccountBillEntryMasterBean billMasterBean = null;
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final List<AccountBillEntryMasterEnitity> listOfBillEntry = billEntryService.getBillEntryDetailsByOrgId(orgId);
		if ((listOfBillEntry != null) && !listOfBillEntry.isEmpty()) {
			for (final AccountBillEntryMasterEnitity listOfBill : listOfBillEntry) {
				billMasterBean = new AccountBillEntryMasterBean();
				billMasterBean.setBillNo(listOfBill.getBillNo());
				billEntryMasterBeanList.add(billMasterBean);
			}
			model.addAttribute(BILL_NO_LIST, billEntryMasterBeanList);
		}
		return billEntryMasterBeanList;
	}

	public void populateListOfBillType(final Model model) {

		final List<LookUp> billTypeLookupList = CommonMasterUtility
				.getLookUps(PrefixConstants.AccountPrefix.ABT.toString(), UserSession.getCurrent().getOrganisation());
		model.addAttribute(BILL_TYPE_LIST, billTypeLookupList);
	}

	public void populateVendorActiveStatusList(final Model model) {
		final Organisation org = UserSession.getCurrent().getOrganisation();

		final Integer languageId = UserSession.getCurrent().getLanguageId();
		final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				MainetConstants.AccountConstants.AC.getValue(), PrefixConstants.VSS, languageId, org);
		final Long vendorStatus = lookUpVendorStatus.getLookUpId();
		final LookUp lookUpSacStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
				PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		final Long activeStatusId = lookUpSacStatus.getLookUpId();
		final List<TbAcVendormaster> vendorList = tbAcVendormasterService
				.getActiveStatusVendorsAndSacAcHead(org.getOrgid(), vendorStatus, activeStatusId);
		model.addAttribute(VENDOR_LIST, vendorList);
	}

	public void populateVendorList(final Model model) {
		final Organisation org = UserSession.getCurrent().getOrganisation();

		final Integer languageId = UserSession.getCurrent().getLanguageId();
		final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				MainetConstants.AccountConstants.AC.getValue(), PrefixConstants.VSS, languageId, org);
		final Long vendorStatus = lookUpVendorStatus.getLookUpId();
		final List<TbAcVendormaster> vendorList = tbAcVendormasterService.getActiveVendors(org.getOrgid(),
				vendorStatus);
		model.addAttribute(VENDOR_LIST, vendorList);
	}

	@RequestMapping(params = "getVendorCode")
	public @ResponseBody Map<Long, String> getVendorCode(final Model model,
			@RequestParam("vendorId") final Long vendorId) {

		Map<Long, String> vendorCodeMap = null;
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final List<TbAcVendormasterEntity> vendorList = tbAcVendormasterService.getVendorCodeByVendorId(orgId,
				vendorId);
		vendorCodeMap = new HashMap<>();
		if ((vendorList != null) && !vendorList.isEmpty()) {
			for (final TbAcVendormasterEntity vendor : vendorList) {
				vendorCodeMap.put(vendor.getVmVendorid(), vendor.getVmVendorcode());
			}
		}
		return vendorCodeMap;
	}

	/*
	 * This method gets the expenditure data based on the date provided and the
	 * department selected
	 */
	@RequestMapping(params = "getExpenditureDetails", method = RequestMethod.POST)
	public String populateExpenditureDetails(final Model model, final AccountBillEntryMasterBean billEntryBean) {

		getExpenditureDetailsByFinyearId(billEntryBean, model);
		setTransactionHeadPrefix(billEntryBean);
		model.addAttribute(BILL_ENTRY_BEAN, billEntryBean);
		populateListOfBillType(model);
		populateVendorList(model);
		populateDeductionDetails(model);
		model.addAttribute(MODE, MODE_CREATE);
		return JSP_FORM;
	}

	private void getExpenditureDetailsByFinyearId(final AccountBillEntryMasterBean billEntryBean, final Model model) {

		List<AccountBudgetProjectedExpenditureBean> projectedExpList = null;
		List<AccountBillEntryExpenditureDetBean> billExpDetBeanList = null;
		AccountBillEntryExpenditureDetBean billExpDetBean = null;
		Map<Long, String> expPacHeadMap = null;

		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final FinancialYear financialYear = financialyearService.getFinanciaYearByDate(new Date());
		if (financialYear != null) {
			final Long finYearId = financialYear.getFaYear();
			final List<String> budgetCodeList = new ArrayList<>();
			projectedExpList = accountBudgetProjectedExpenditureService.findExpenditureDataByFinYearId(orgId,
					finYearId);
			billExpDetBeanList = new ArrayList<>();
			expPacHeadMap = new LinkedHashMap<>();
			if ((projectedExpList != null) && !projectedExpList.isEmpty()) {
				for (final AccountBudgetProjectedExpenditureBean expPac : projectedExpList) {
					billExpDetBean = new AccountBillEntryExpenditureDetBean();
					final List<Object[]> expenditureList = budgetCodeService.getExpenditutreBudgetHeads(orgId,
							expPac.getPrBudgetCodeid());
					if ((expenditureList != null) && !expenditureList.isEmpty()) {
						for (final Object[] expArray : expenditureList) {
							/*
							 * final LookUp transactionHeadBudgetCode =
							 * CommonMasterUtility.getLookUpFromPrefixLookUpValue(
							 * MainetConstants.AccountConstants.FNC.getValue(),
							 * PrefixConstants.AccountPrefix.TSH.toString(),
							 * UserSession.getCurrent().getLanguageId(),
							 * UserSession.getCurrent().getOrganisation());
							 */
							expPacHeadMap.put((Long) expArray[0], expArray[1].toString());

						}
					}
					budgetCodeList.add(expPac.getPrExpBudgetCode());
				}
				if (billEntryBean.getDepositFlag() != null && !billEntryBean.getDepositFlag().isEmpty()) {
					if (billEntryBean.getDepositFlag().equals(MainetConstants.MENU.Y)) {
						billExpDetBean.setBudgetCodeId(billEntryBean.getBudgetCodeId());
					}
				}
				billExpDetBeanList.add(billExpDetBean);
			} else {
				billEntryBean.setExpenditureExistsFlag(MainetConstants.MENU.N);
				billExpDetBean = new AccountBillEntryExpenditureDetBean();
				if (billEntryBean.getDepositFlag() != null && !billEntryBean.getDepositFlag().isEmpty()) {
					if (billEntryBean.getDepositFlag().equals(MainetConstants.MENU.Y)) {
						billExpDetBean.setBudgetCodeId(billEntryBean.getBudgetCodeId());
					}
				}
				billExpDetBeanList.add(billExpDetBean);
			}
			billEntryBean.setExpenditureDetailList(billExpDetBeanList);
			model.addAttribute(PROJECTED_EXPENDITURE_LIST, billExpDetBeanList);
			model.addAttribute(PAC_MAP, secondaryheadMasterService
					.findExpenditureHeadMapAccountTypeIsOthers(UserSession.getCurrent().getOrganisation().getOrgid()));
			model.addAttribute(BUDGET_CODE_LIST, budgetCodeList);
		} else {
			billEntryBean.setDataFlag(MainetConstants.Complaint.MODE_EDIT);
			populateExpenditureDetailsAtStartup(model, billEntryBean);
		}
	}

	public void populateExpenditureDetailsAtStartup(final Model model, AccountBillEntryMasterBean billEntryBean) {

		final String flag = billEntryBean.getDataFlag();
		if (flag == null) {
			String finYearDate;
			final Date currentDate = new Date();
			finYearDate = UtilityService.convertDateToDDMMYYYY(currentDate);
			billEntryBean.setBillEntryDate(finYearDate);
			getExpenditureDetailsByFinyearId(billEntryBean, model);
		} else {
			billEntryBean = new AccountBillEntryMasterBean();
			final AccountBillEntryExpenditureDetBean expDetBean = new AccountBillEntryExpenditureDetBean();
			final List<AccountBillEntryExpenditureDetBean> expDetBeanList = new ArrayList<>();
			expDetBeanList.add(expDetBean);
			billEntryBean.setExpenditureDetailList(expDetBeanList);
			model.addAttribute(PROJECTED_EXPENDITURE_LIST, expDetBeanList);
			setTransactionHeadPrefix(billEntryBean);
		}
	}

	public void populateDeductionDetails(final Model model) {

		final Organisation org = UserSession.getCurrent().getOrganisation();
		Long taxMasLookUpId = null;
		Long taxMasLookUpSubId = null;
		List<LookUp> taxMaslookUpList = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC, 1, org);
		for (LookUp lookUp : taxMaslookUpList) {
			if (lookUp.getLookUpCode().equals(MainetConstants.StandardAccountHeadMapping.TDS)) {
				taxMasLookUpId = lookUp.getLookUpId();
			}
		}
		List<LookUp> taxMaslookUpSubList = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC, 2, org);
		for (LookUp lookUp : taxMaslookUpSubList) {
			if (lookUp.getLookUpCode().equals("BDS")) {
				taxMasLookUpSubId = lookUp.getLookUpId();
			}
		}
		AccountBillEntryDeductionDetBean billDeductionDetBean =  new AccountBillEntryDeductionDetBean();
		final List<AccountBillEntryDeductionDetBean> billDeductionDetBeanList = new ArrayList<>();
		final AccountBillEntryMasterBean billEntryBean = new AccountBillEntryMasterBean();
		billDeductionDetBeanList.add(billDeductionDetBean);
		billEntryBean.setDeductionDetailList(billDeductionDetBeanList);
		model.addAttribute(DED_DET_LIST, billDeductionDetBeanList);
		model.addAttribute(DED_PAC_MAP,
				secondaryheadMasterService.getTaxMasBillDeductionAcHeadAllDetails(org.getOrgid(), taxMasLookUpId,taxMasLookUpSubId));

	}

	/*
	 * This method gets the expenditure budget data based on the account heads and
	 * the department
	 */
	@RequestMapping(params = "viewExpDetails")
	public String viewExpenditureDetails(@RequestParam("sacHeadId") final Long sacHeadId,
			@RequestParam("entryDate") final String entryDate,
			@RequestParam("bchChargesAmt") final BigDecimal sanctionedAmt, @RequestParam("count") final Long count,
			@RequestParam("deptId") final Long deptId,@RequestParam("fieldId") final Long fieldId,
			final HttpServletRequest request, final Model model) {

		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
	
		final FinancialYear financialYear = financialyearService.getFinanciaYearByDate(Utility.stringToDate(entryDate));
		final Long finYearId = financialYear.getFaYear();
		final List<AccountBudgetProjectedExpenditureEntity> projectedExpList = accountBudgetProjectedExpenditureService
				.getExpenditureDetailsForBillEntryFormViewWithFieldId(orgId, finYearId, sacHeadId,fieldId);
		final AccountBillEntryMasterBean billEntryBean = new AccountBillEntryMasterBean();
		AccountBillEntryExpenditureDetBean billExpDetBean = null;
		final List<AccountBillEntryExpenditureDetBean> billExpBudgetDetBeanList = new ArrayList<>();
		BigDecimal balanceProvisionAmt = null;
		BigDecimal newBalanceAmount = null;
		BigDecimal expenditureAmt = null;
		if ((projectedExpList != null) && !projectedExpList.isEmpty()) {
			for (final AccountBudgetProjectedExpenditureEntity prExpDet : projectedExpList) {
			  if(deptId.equals(prExpDet.getTbDepartment().getDpDeptid())) {
				billExpDetBean = new AccountBillEntryExpenditureDetBean();
				billExpDetBean.setProjectedExpenditureId(prExpDet.getPrExpenditureid());
				if (prExpDet.getRevisedEstamt() == null) {
					billExpDetBean.setOriginalEstimate(prExpDet.getOrginalEstamt());
				} else {
					billExpDetBean.setOriginalEstimate(new BigDecimal(prExpDet.getRevisedEstamt()));
				}
				if (prExpDet.getExpenditureAmt() == null) {
					expenditureAmt = BigDecimal.ZERO;
					prExpDet.setExpenditureAmt(BigDecimal.ZERO);
					billExpDetBean.setBalanceAmount(expenditureAmt);
					if (prExpDet.getRevisedEstamt() == null) {
						balanceProvisionAmt = prExpDet.getOrginalEstamt().subtract(expenditureAmt.add(sanctionedAmt));
					} else {
						balanceProvisionAmt = new BigDecimal(prExpDet.getRevisedEstamt())
								.subtract(expenditureAmt.add(sanctionedAmt));
					}
					newBalanceAmount = expenditureAmt.add(sanctionedAmt);
					if (prExpDet.getRevisedEstamt() == null) {
						prExpDet.getExpenditureAmt().add(sanctionedAmt).compareTo(prExpDet.getOrginalEstamt());
					} else {
						prExpDet.getExpenditureAmt().add(sanctionedAmt)
								.compareTo(new BigDecimal(prExpDet.getRevisedEstamt()));
					}
				} else {
					billExpDetBean.setBalanceAmount(prExpDet.getExpenditureAmt());
					if (prExpDet.getRevisedEstamt() == null) {
						balanceProvisionAmt = prExpDet.getOrginalEstamt()
								.subtract((prExpDet.getExpenditureAmt().add(sanctionedAmt)));
					} else {
						balanceProvisionAmt = new BigDecimal(prExpDet.getRevisedEstamt())
								.subtract((prExpDet.getExpenditureAmt().add(sanctionedAmt)));
					}
					newBalanceAmount = prExpDet.getExpenditureAmt().add(sanctionedAmt);
					if (prExpDet.getRevisedEstamt() == null) {
						prExpDet.getExpenditureAmt().add(sanctionedAmt).compareTo(prExpDet.getOrginalEstamt());
					} else {
						prExpDet.getExpenditureAmt().add(sanctionedAmt)
								.compareTo(new BigDecimal(prExpDet.getRevisedEstamt()));
					}
				}
			
				billExpDetBean.setNewBalanceAmount(newBalanceAmount);
				billExpDetBean.setRowCount(count);
				billExpDetBean.setBalProvisionAmount(balanceProvisionAmt);
				billExpBudgetDetBeanList.add(billExpDetBean);
				billEntryBean.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
				
				} 
			}
		} else {
			model.addAttribute(MainetConstants.AccountBillEntry.ERROR_MSG,
					ApplicationSession.getInstance().getMessage("account.bill.entry.controller.budget.data"));
		}
		if(billExpDetBean==null) {
			model.addAttribute(MainetConstants.AccountBillEntry.ERROR_MSG,
					ApplicationSession.getInstance().getMessage("account.bill.entry.controller.budget.data"));
		}
		billEntryBean.setExpenditureDetailList(billExpBudgetDetBeanList);
		model.addAttribute(BUDGET_DETAILS, billExpBudgetDetBeanList);
		model.addAttribute(BILL_ENTRY_BEAN, billEntryBean);

		return ACCOUNT_EXPENDITURE_DETAILS;
	}
	
	
	@RequestMapping(params = "viewExpDetailsAuth")
	public String viewExpenditureDetailsAuth(@RequestParam("sacHeadId") final Long sacHeadId,
			@RequestParam("entryDate") final String entryDate,
			@RequestParam("bchChargesAmt") final BigDecimal sanctionedAmt, @RequestParam("count") final Long count,
			@RequestParam("deptId") final Long deptId,@RequestParam("fieldId") final Long fieldId,
			final HttpServletRequest request, final Model model) {

		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		String flag=request.getAuthType();
		final FinancialYear financialYear = financialyearService.getFinanciaYearByDate(Utility.stringToDate(entryDate));
		final Long finYearId = financialYear.getFaYear();
		final List<AccountBudgetProjectedExpenditureEntity> projectedExpList = accountBudgetProjectedExpenditureService
				.getExpenditureDetailsForBillEntryFormViewWithFieldId(orgId, finYearId, sacHeadId,fieldId);
		final AccountBillEntryMasterBean billEntryBean = new AccountBillEntryMasterBean();
		AccountBillEntryExpenditureDetBean billExpDetBean = null;
		final List<AccountBillEntryExpenditureDetBean> billExpBudgetDetBeanList = new ArrayList<>();
		BigDecimal balanceProvisionAmt = null;
		BigDecimal newBalanceAmount = null;
		BigDecimal expenditureAmt = null;
		if ((projectedExpList != null) && !projectedExpList.isEmpty()) {
			for (final AccountBudgetProjectedExpenditureEntity prExpDet : projectedExpList) {
			  if(deptId.equals(prExpDet.getTbDepartment().getDpDeptid())) {
				billExpDetBean = new AccountBillEntryExpenditureDetBean();
				billExpDetBean.setProjectedExpenditureId(prExpDet.getPrExpenditureid());
				if (prExpDet.getRevisedEstamt() == null) {
					billExpDetBean.setOriginalEstimate(prExpDet.getOrginalEstamt());
				} else {
					billExpDetBean.setOriginalEstimate(new BigDecimal(prExpDet.getRevisedEstamt()));
				}
				if (prExpDet.getExpenditureAmt() == null) {
					expenditureAmt = BigDecimal.ZERO;
					prExpDet.setExpenditureAmt(BigDecimal.ZERO);
					billExpDetBean.setBalanceAmount(expenditureAmt);
					if (prExpDet.getRevisedEstamt() == null) {
						balanceProvisionAmt = prExpDet.getOrginalEstamt().subtract(expenditureAmt);
					} else {
						balanceProvisionAmt = new BigDecimal(prExpDet.getRevisedEstamt())
								.subtract(expenditureAmt);
					}
					newBalanceAmount = expenditureAmt.add(sanctionedAmt);
					if (prExpDet.getRevisedEstamt() == null) {
						prExpDet.getExpenditureAmt().add(sanctionedAmt).compareTo(prExpDet.getOrginalEstamt());
					} else {
						prExpDet.getExpenditureAmt().add(sanctionedAmt)
								.compareTo(new BigDecimal(prExpDet.getRevisedEstamt()));
					}
				} else {
					billExpDetBean.setBalanceAmount(prExpDet.getExpenditureAmt().subtract(sanctionedAmt));
					if (prExpDet.getRevisedEstamt() == null) {
						balanceProvisionAmt = prExpDet.getOrginalEstamt()
								.subtract((prExpDet.getExpenditureAmt()));
					} else {
						balanceProvisionAmt = new BigDecimal(prExpDet.getRevisedEstamt())
								.subtract((prExpDet.getExpenditureAmt()));
					}
					newBalanceAmount = prExpDet.getExpenditureAmt().add(sanctionedAmt);
					if (prExpDet.getRevisedEstamt() == null) {
						prExpDet.getExpenditureAmt().add(sanctionedAmt).compareTo(prExpDet.getOrginalEstamt());
					} else {
						prExpDet.getExpenditureAmt().add(sanctionedAmt)
								.compareTo(new BigDecimal(prExpDet.getRevisedEstamt()));
					}
				}
			
				billExpDetBean.setNewBalanceAmount(newBalanceAmount);
				billExpDetBean.setRowCount(count);
				billExpDetBean.setBalProvisionAmount(balanceProvisionAmt);
				billExpBudgetDetBeanList.add(billExpDetBean);
				billEntryBean.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
				
				} 
			}
		} else {
			model.addAttribute(MainetConstants.AccountBillEntry.ERROR_MSG,
					ApplicationSession.getInstance().getMessage("account.bill.entry.controller.budget.data"));
		}
		if(billExpDetBean==null) {
			model.addAttribute(MainetConstants.AccountBillEntry.ERROR_MSG,
					ApplicationSession.getInstance().getMessage("account.bill.entry.controller.budget.data"));
		}
		billEntryBean.setExpenditureDetailList(billExpBudgetDetBeanList);
		model.addAttribute(BUDGET_DETAILS, billExpBudgetDetBeanList);
		model.addAttribute(BILL_ENTRY_BEAN, billEntryBean);

		return ACCOUNT_EXPENDITURE_DETAILS;
	}

	/*
	 * This method gets the expenditure budget data based on the account heads and
	 * the department
	 */
	@RequestMapping(params = "viewExpDetailsInViewMode")
	public String viewExpDetailsInViewMode(@RequestParam("sacHeadId") final Long sacHeadId,
			@RequestParam("entryDate") final String entryDate,
			@RequestParam("bchChargesAmt") final BigDecimal sanctionedAmt, @RequestParam("count") final Long count,
			final HttpServletRequest request, final Model model) {

		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final FinancialYear financialYear = financialyearService.getFinanciaYearByDate(Utility.stringToDate(entryDate));
		final Long finYearId = financialYear.getFaYear();
		final List<AccountBudgetProjectedExpenditureEntity> projectedExpList = accountBudgetProjectedExpenditureService
				.getExpenditureDetailsForBillEntryFormView(orgId, finYearId, sacHeadId);
		final AccountBillEntryMasterBean billEntryBean = new AccountBillEntryMasterBean();
		AccountBillEntryExpenditureDetBean billExpDetBean = null;
		final List<AccountBillEntryExpenditureDetBean> billExpBudgetDetBeanList = new ArrayList<>();
		BigDecimal balanceProvisionAmt = null;
		BigDecimal newBalanceAmount = null;
		BigDecimal expenditureAmt = null;
		if ((projectedExpList != null) && !projectedExpList.isEmpty()) {
			for (final AccountBudgetProjectedExpenditureEntity prExpDet : projectedExpList) {
				billExpDetBean = new AccountBillEntryExpenditureDetBean();
				billExpDetBean.setProjectedExpenditureId(prExpDet.getPrExpenditureid());
				if (prExpDet.getRevisedEstamt() == null) {
					billExpDetBean.setOriginalEstimate(prExpDet.getOrginalEstamt());
				} else {
					billExpDetBean.setOriginalEstimate(new BigDecimal(prExpDet.getRevisedEstamt()));
				}
				if (prExpDet.getExpenditureAmt() == null) {
					expenditureAmt = BigDecimal.ZERO;
					prExpDet.setExpenditureAmt(BigDecimal.ZERO);
					billExpDetBean.setBalanceAmount(expenditureAmt);
					if (prExpDet.getRevisedEstamt() == null) {
						balanceProvisionAmt = prExpDet.getOrginalEstamt();
					} else {
						balanceProvisionAmt = new BigDecimal(prExpDet.getRevisedEstamt());
					}
					newBalanceAmount = expenditureAmt;
					if (prExpDet.getRevisedEstamt() == null) {
						prExpDet.getExpenditureAmt().compareTo(prExpDet.getOrginalEstamt());
					} else {
						prExpDet.getExpenditureAmt().compareTo(new BigDecimal(prExpDet.getRevisedEstamt()));
					}
				} else {
					billExpDetBean.setBalanceAmount(prExpDet.getExpenditureAmt());
					if (prExpDet.getRevisedEstamt() == null) {
						balanceProvisionAmt = prExpDet.getOrginalEstamt().subtract((prExpDet.getExpenditureAmt()));
					} else {
						balanceProvisionAmt = new BigDecimal(prExpDet.getRevisedEstamt())
								.subtract((prExpDet.getExpenditureAmt()));
					}
					newBalanceAmount = prExpDet.getExpenditureAmt();
					if (prExpDet.getRevisedEstamt() == null) {
						prExpDet.getExpenditureAmt().compareTo(prExpDet.getOrginalEstamt());
					} else {
						prExpDet.getExpenditureAmt().compareTo(new BigDecimal(prExpDet.getRevisedEstamt()));
					}
				}

				billExpDetBean.setNewBalanceAmount(newBalanceAmount);
				billExpDetBean.setRowCount(count);
				billExpDetBean.setBalProvisionAmount(balanceProvisionAmt);
				billExpBudgetDetBeanList.add(billExpDetBean);
				billEntryBean.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);

			}
		} else {
			model.addAttribute(MainetConstants.AccountBillEntry.ERROR_MSG,
					ApplicationSession.getInstance().getMessage("account.bill.entry.controller.budget.data"));
		}
		billEntryBean.setExpenditureDetailList(billExpBudgetDetBeanList);
		model.addAttribute(BUDGET_DETAILS, billExpBudgetDetBeanList);
		model.addAttribute(BILL_ENTRY_BEAN, billEntryBean);

		return ACCOUNT_EXPENDITURE_DETAILS;
	}

	/*
	 * This method gets the expenditure budget data based on the account heads and
	 * the department
	 */
	@RequestMapping(params = "viewPaymentExpDetails")
	public String viewPaymentExpenditureDetails(@RequestParam("billId") final Long billId,
			@RequestParam("entryDate") final String entryDate, @RequestParam("paymentAmt") final Double paymentAmt,
			@RequestParam("count") final Long count,@RequestParam("bmId") final Long bmId,@RequestParam("fieldId") final Long fieldId,
			final HttpServletRequest request, final Model model) {

		BigDecimal paymentAmount = new BigDecimal(paymentAmt);
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final FinancialYear financialYear = financialyearService.getFinanciaYearByDate(Utility.stringToDate(entryDate));
		final Long finYearId = financialYear.getFaYear();
		final List<AccountBudgetProjectedExpenditureEntity> projectedExpList = accountBudgetProjectedExpenditureService
				.getExpenditureDetailsForPaymentEntryFormViewWithFieldId(orgId, finYearId, billId,bmId,fieldId);
		final AccountBillEntryMasterBean billEntryBean = new AccountBillEntryMasterBean();
		AccountBillEntryExpenditureDetBean billExpDetBean = null;
		final List<AccountBillEntryExpenditureDetBean> billExpBudgetDetBeanList = new ArrayList<>();
		BigDecimal balanceProvisionAmt = null;
		BigDecimal newBalanceAmount = null;
		BigDecimal expenditureAmt = null;
		if ((projectedExpList != null) && !projectedExpList.isEmpty()) {
			for (final AccountBudgetProjectedExpenditureEntity prExpDet : projectedExpList) {
				billExpDetBean = new AccountBillEntryExpenditureDetBean();
				billExpDetBean.setProjectedExpenditureId(prExpDet.getPrExpenditureid());
				if (prExpDet.getRevisedEstamt() == null || prExpDet.getRevisedEstamt().equals("0")) {
					billExpDetBean.setOriginalEstimate(prExpDet.getOrginalEstamt());
				} else {
					billExpDetBean.setOriginalEstimate(new BigDecimal(prExpDet.getRevisedEstamt()));
				}
				if (prExpDet.getExpenditureAmt() == null) {
					expenditureAmt = BigDecimal.ZERO;
					prExpDet.setExpenditureAmt(BigDecimal.ZERO);
					billExpDetBean.setBalanceAmount(expenditureAmt);
					if (prExpDet.getRevisedEstamt() == null || prExpDet.getRevisedEstamt().equals("0")) {
						balanceProvisionAmt = prExpDet.getOrginalEstamt().subtract(expenditureAmt.add(paymentAmount));
					} else {
						balanceProvisionAmt = new BigDecimal(prExpDet.getRevisedEstamt())
								.subtract(expenditureAmt.add(paymentAmount));
					}
					newBalanceAmount = expenditureAmt.add(paymentAmount);
					if (prExpDet.getRevisedEstamt() == null || prExpDet.getRevisedEstamt().equals("0")) {
						prExpDet.getExpenditureAmt().add(paymentAmount).compareTo(prExpDet.getOrginalEstamt());
					} else {
						prExpDet.getExpenditureAmt().add(paymentAmount)
								.compareTo(new BigDecimal(prExpDet.getRevisedEstamt()));
					}
				} else {
					billExpDetBean.setBalanceAmount(prExpDet.getExpenditureAmt());
					if (prExpDet.getRevisedEstamt() == null || prExpDet.getRevisedEstamt().equals("0")) {
						balanceProvisionAmt = prExpDet.getOrginalEstamt()
								.subtract((prExpDet.getExpenditureAmt().add(paymentAmount)));
					} else {
						balanceProvisionAmt = new BigDecimal(prExpDet.getRevisedEstamt())
								.subtract((prExpDet.getExpenditureAmt().add(paymentAmount)));
					}
					newBalanceAmount = prExpDet.getExpenditureAmt().add(paymentAmount);
					if (prExpDet.getRevisedEstamt() == null || prExpDet.getRevisedEstamt().equals("0")) {
						prExpDet.getExpenditureAmt().add(paymentAmount).compareTo(prExpDet.getOrginalEstamt());
					} else {
						prExpDet.getExpenditureAmt().add(paymentAmount)
								.compareTo(new BigDecimal(prExpDet.getRevisedEstamt()));
					}
				}

				if ((billExpDetBean.getOriginalEstimate().subtract(billExpDetBean.getBalanceAmount()))
						.compareTo(new BigDecimal(paymentAmt)) < 0) {
					model.addAttribute(MainetConstants.AccountBillEntry.ERROR_MSG, ApplicationSession.getInstance()
							.getMessage("account.bill.payment.entry.budget.balance.data"));
				}

				billExpDetBean.setNewBalanceAmount(newBalanceAmount);
				billExpDetBean.setRowCount(count);
				billExpDetBean.setBalProvisionAmount(balanceProvisionAmt);
				billExpBudgetDetBeanList.add(billExpDetBean);
				billEntryBean.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);

			}
		} else {
			model.addAttribute(MainetConstants.AccountBillEntry.ERROR_MSG,
					ApplicationSession.getInstance().getMessage("account.bill.entry.controller.budget.payment.data"));
		}
		billEntryBean.setExpenditureDetailList(billExpBudgetDetBeanList);
		model.addAttribute(BUDGET_DETAILS, billExpBudgetDetBeanList);
		model.addAttribute(BILL_ENTRY_BEAN, billEntryBean);

		return ACCOUNT_EXPENDITURE_DETAILS;
	}

	/*
	 * This method gets the expenditure budget data based on the account heads and
	 * the department
	 */
	@RequestMapping(params = "viewDirectPaymentExpDetails")
	public String viewDirectPaymentExpenditureDetails(@RequestParam("budgetCodeId") final Long budgetCodeId,
			@RequestParam("entryDate") final String entryDate, @RequestParam("paymentAmt") final Double paymentAmt,
			@RequestParam("count") final Long count, final HttpServletRequest request, final Model model) {

		BigDecimal paymentAmount = new BigDecimal(paymentAmt);
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final FinancialYear financialYear = financialyearService.getFinanciaYearByDate(Utility.stringToDate(entryDate));
		final Long finYearId = financialYear.getFaYear();
		final List<AccountBudgetProjectedExpenditureEntity> projectedExpList = accountBudgetProjectedExpenditureService
				.getExpenditureDetailsForDirectPaymentEntryFormView(orgId, finYearId, budgetCodeId);
		final AccountBillEntryMasterBean billEntryBean = new AccountBillEntryMasterBean();
		AccountBillEntryExpenditureDetBean billExpDetBean = null;
		final List<AccountBillEntryExpenditureDetBean> billExpBudgetDetBeanList = new ArrayList<>();
		BigDecimal balanceProvisionAmt = null;
		BigDecimal newBalanceAmount = null;
		BigDecimal expenditureAmt = null;
		if ((projectedExpList != null) && !projectedExpList.isEmpty()) {
			for (final AccountBudgetProjectedExpenditureEntity prExpDet : projectedExpList) {
				billExpDetBean = new AccountBillEntryExpenditureDetBean();
				billExpDetBean.setProjectedExpenditureId(prExpDet.getPrExpenditureid());
				if (prExpDet.getRevisedEstamt() == null) {
					billExpDetBean.setOriginalEstimate(prExpDet.getOrginalEstamt());
				} else {
					billExpDetBean.setOriginalEstimate(new BigDecimal(prExpDet.getRevisedEstamt()));
				}
				if (prExpDet.getExpenditureAmt() == null) {
					expenditureAmt = BigDecimal.ZERO;
					prExpDet.setExpenditureAmt(BigDecimal.ZERO);
					billExpDetBean.setBalanceAmount(expenditureAmt);
					if (prExpDet.getRevisedEstamt() == null) {
						balanceProvisionAmt = prExpDet.getOrginalEstamt().subtract(expenditureAmt.add(paymentAmount));
					} else {
						balanceProvisionAmt = new BigDecimal(prExpDet.getRevisedEstamt())
								.subtract(expenditureAmt.add(paymentAmount));
					}
					newBalanceAmount = expenditureAmt.add(paymentAmount);
					if (prExpDet.getRevisedEstamt() == null) {
						prExpDet.getExpenditureAmt().add(paymentAmount).compareTo(prExpDet.getOrginalEstamt());
					} else {
						prExpDet.getExpenditureAmt().add(paymentAmount)
								.compareTo(new BigDecimal(prExpDet.getRevisedEstamt()));
					}
				} else {
					billExpDetBean.setBalanceAmount(prExpDet.getExpenditureAmt());
					if (prExpDet.getRevisedEstamt() == null) {
						balanceProvisionAmt = prExpDet.getOrginalEstamt()
								.subtract((prExpDet.getExpenditureAmt().add(paymentAmount)));
					} else {
						balanceProvisionAmt = new BigDecimal(prExpDet.getRevisedEstamt())
								.subtract((prExpDet.getExpenditureAmt().add(paymentAmount)));
					}
					newBalanceAmount = prExpDet.getExpenditureAmt().add(paymentAmount);
					if (prExpDet.getRevisedEstamt() == null) {
						prExpDet.getExpenditureAmt().add(paymentAmount).compareTo(prExpDet.getOrginalEstamt());
					} else {
						prExpDet.getExpenditureAmt().add(paymentAmount)
								.compareTo(new BigDecimal(prExpDet.getRevisedEstamt()));
					}
				}

				if ((billExpDetBean.getOriginalEstimate().subtract(billExpDetBean.getBalanceAmount()))
						.compareTo(new BigDecimal(paymentAmt)) < 0) {
					model.addAttribute(MainetConstants.AccountBillEntry.ERROR_MSG, ApplicationSession.getInstance()
							.getMessage("account.bill.payment.entry.budget.balance.data"));
				}

				billExpDetBean.setNewBalanceAmount(newBalanceAmount);
				billExpDetBean.setRowCount(count);
				billExpDetBean.setBalProvisionAmount(balanceProvisionAmt);
				billExpBudgetDetBeanList.add(billExpDetBean);
				billEntryBean.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);

			}
		} else {
			model.addAttribute(MainetConstants.AccountBillEntry.ERROR_MSG,
					ApplicationSession.getInstance().getMessage("account.bill.entry.controller.budget.data"));
		}
		billEntryBean.setExpenditureDetailList(billExpBudgetDetBeanList);
		model.addAttribute(BUDGET_DETAILS, billExpBudgetDetBeanList);
		model.addAttribute(BILL_ENTRY_BEAN, billEntryBean);

		return ACCOUNT_EXPENDITURE_DETAILS;
	}

	/*
	 * This method gets the expenditure budget data based on the account heads and
	 * the department
	 */
	@RequestMapping(params = "getExpDetails")
	public @ResponseBody List<AccountBillEntryExpenditureDetBean> getExpenditureDetails(
			@RequestParam("budgetCodeId") final Long budgetCodeId, @RequestParam("entryDate") final String entryDate,
			@RequestParam("bchChargesAmt") final BigDecimal sanctionedAmt, @RequestParam("count") final Long count,
			final HttpServletRequest request, final Model model) {

		List<AccountBillEntryExpenditureDetBean> billExpBudgetDetBeanList = null;
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final FinancialYear financialYear = financialyearService.getFinanciaYearByDate(Utility.stringToDate(entryDate));
		final Long finYearId = financialYear.getFaYear();
		final List<AccountBudgetProjectedExpenditureEntity> projectedExpList = accountBudgetProjectedExpenditureService
				.getExpenditureDetailsForBillEntryFormView(orgId, finYearId, budgetCodeId);
		final AccountBillEntryMasterBean billEntryBean = new AccountBillEntryMasterBean();
		AccountBillEntryExpenditureDetBean billExpDetBean = null;
		billExpBudgetDetBeanList = new ArrayList<>();
		BigDecimal balanceProvisionAmt = null;
		BigDecimal newBalanceAmount = null;
		BigDecimal expenditureAmt = null;
		int compare = 0;
		if ((projectedExpList != null) && !projectedExpList.isEmpty()) {
			for (final AccountBudgetProjectedExpenditureEntity prExpDet : projectedExpList) {
				billExpDetBean = new AccountBillEntryExpenditureDetBean();
				billExpDetBean.setProjectedExpenditureId(prExpDet.getPrExpenditureid());
				if (prExpDet.getRevisedEstamt() == null) {
					billExpDetBean.setOriginalEstimate(prExpDet.getOrginalEstamt());
				} else {
					billExpDetBean.setOriginalEstimate(new BigDecimal(prExpDet.getRevisedEstamt()));
				}
				if (prExpDet.getExpenditureAmt() == null) {
					expenditureAmt = BigDecimal.ZERO;
					prExpDet.setExpenditureAmt(BigDecimal.ZERO);
					billExpDetBean.setBalanceAmount(expenditureAmt);
					billExpDetBean.setBillBudIdfyFlag(expenditureAmt.toString());
					if (prExpDet.getRevisedEstamt() == null) {
						balanceProvisionAmt = prExpDet.getOrginalEstamt().subtract(expenditureAmt.add(sanctionedAmt));
					} else {
						balanceProvisionAmt = new BigDecimal(prExpDet.getRevisedEstamt())
								.subtract(expenditureAmt.add(sanctionedAmt));
					}
					newBalanceAmount = expenditureAmt.add(sanctionedAmt);
					if (prExpDet.getRevisedEstamt() == null) {
						compare = prExpDet.getExpenditureAmt().add(sanctionedAmt)
								.compareTo(prExpDet.getOrginalEstamt());
					} else {
						compare = prExpDet.getExpenditureAmt().add(sanctionedAmt)
								.compareTo(new BigDecimal(prExpDet.getRevisedEstamt()));
					}
				} else {
					billExpDetBean.setBalanceAmount(prExpDet.getExpenditureAmt());
					billExpDetBean.setBillBudIdfyFlag(prExpDet.getExpenditureAmt().toString());
					if (prExpDet.getRevisedEstamt() == null) {
						balanceProvisionAmt = prExpDet.getOrginalEstamt()
								.subtract((prExpDet.getExpenditureAmt().add(sanctionedAmt)));
					} else {
						balanceProvisionAmt = new BigDecimal(prExpDet.getRevisedEstamt())
								.subtract((prExpDet.getExpenditureAmt().add(sanctionedAmt)));
					}
					newBalanceAmount = prExpDet.getExpenditureAmt().add(sanctionedAmt);
					if (prExpDet.getRevisedEstamt() == null) {
						compare = prExpDet.getExpenditureAmt().add(sanctionedAmt)
								.compareTo(prExpDet.getOrginalEstamt());
					} else {
						compare = prExpDet.getExpenditureAmt().add(sanctionedAmt)
								.compareTo(new BigDecimal(prExpDet.getRevisedEstamt()));
					}
				}
				if (compare != 1) {
					billExpDetBean.setNewBalanceAmount(newBalanceAmount);
					billExpDetBean.setRowCount(count);
					billExpDetBean.setBalProvisionAmount(balanceProvisionAmt);
					billExpBudgetDetBeanList.add(billExpDetBean);
					billExpDetBean.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
				} else {
					billExpBudgetDetBeanList.add(billExpDetBean);
					billExpDetBean.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);
				}
			}
		}
		billEntryBean.setExpenditureDetailList(billExpBudgetDetBeanList);
		model.addAttribute(BUDGET_DETAILS, billExpBudgetDetBeanList);
		model.addAttribute(BILL_ENTRY_BEAN, billEntryBean);
    
	    final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.BILL_ENTRY_CPD_VALUE,
	    		PrefixConstants.BCE,UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
	    billExpDetBean = new AccountBillEntryExpenditureDetBean();
		if(expLookup!=null && expLookup.getOtherField()!=null) {
		if(expLookup.getOtherField().equals(MainetConstants.Y_FLAG)){
			billExpDetBean.setBudgetCheckFlag(MainetConstants.Y_FLAG);
		 }
		}
		if (billExpBudgetDetBeanList == null || billExpBudgetDetBeanList.isEmpty()) {
			billExpDetBean.setBillBudIdfyFlag(MainetConstants.Y_FLAG);
			billExpBudgetDetBeanList.add(billExpDetBean);
		}
		return billExpBudgetDetBeanList;
	}

	@RequestMapping(params = "getDepartmentIdByBudgetCode")
	public @ResponseBody Long getDepartmentIdByBudgetCode(@RequestParam("budgetCode") final Long budgetCode,
			@RequestParam("entryDate") final String entryDate, final Model model) {

		Long departmentId = null;
		final FinancialYear financialYear = financialyearService.getFinanciaYearByDate(new Date());
		if (financialYear != null) {
			final Long finYearId = financialYear.getFaYear();
			departmentId = accountBudgetProjectedExpenditureService
					.getDepartmentFromBudgetProjectedExpenditureByBudgetCode(
							UserSession.getCurrent().getOrganisation().getOrgid(), budgetCode, finYearId);

		}
		return departmentId;
	}
	

	@RequestMapping(params = "create", method = RequestMethod.POST)
	public String createBillEntry(@Valid final AccountBillEntryMasterBean billEntryBean,
			final BindingResult bindingResult, final Model model, final HttpServletRequest httpServletRequest)
			throws ParseException {
		
		final ApplicationSession session = ApplicationSession.getInstance();
		final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE,
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
				UserSession.getCurrent().getOrganisation());
		Date transactionalDate = null;
		Date SLIDate = null;
		if(StringUtils.isNotBlank(billEntryBean.getTransactionDate()) && StringUtils.isNotBlank(lookUp.getOtherField())) {
			String transactDate = billEntryBean.getTransactionDate();
			String sliDate = lookUp.getOtherField();
			String depDate = billEntryBean.getInvoiceDate();
			
			Date depositDate = null;
			try {
				transactionalDate = new SimpleDateFormat("dd/MM/yyyy").parse(transactDate);
				SLIDate = new SimpleDateFormat("dd/MM/yyyy").parse(sliDate);
				depositDate = new SimpleDateFormat("dd/MM/yyyy").parse(depDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if(StringUtils.isNotBlank(billEntryBean.getInvoiceDate())) {
				if (Utility.compareDate(transactionalDate,depositDate)) {
					bindingResult.addError(new org.springframework.validation.FieldError(MainetConstants.FUNCTION_MASTER.FUNC_MASTER,
			                MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
			                session.getMessage("Transactional Date cannot be less than deposit date")));
				}
			}
			
			/*
			 * if(Utility.compareDate(transactionalDate,SLIDate)) {
			 * bindingResult.addError(new
			 * org.springframework.validation.FieldError(MainetConstants.FUNCTION_MASTER.
			 * FUNC_MASTER, MainetConstants.BLANK, null, false, new String[] {
			 * MainetConstants.ERRORS }, null,
			 * session.getMessage("Transactional Date cannot be less than SLI Date "
			 * )+" SLI Date is: "+" "+sliDate)); }
			 */
		}
		
		if(StringUtils.isNotBlank(billEntryBean.getGrantFlag()) && StringUtils.equals(billEntryBean.getGrantFlag(), MainetConstants.FlagY)) {
			int payableAmountFlag = billEntryBean.getInvoiceValue().compareTo(billEntryBean.getGrantPayableAmount());
			if(payableAmountFlag == 1) {
				bindingResult.addError(new org.springframework.validation.FieldError(MainetConstants.FUNCTION_MASTER.FUNC_MASTER,
		                MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
		                session.getMessage("Bill invoice amount cannot be greater than payable amount ")+" Payable amount is: "+" "+billEntryBean.getGrantPayableAmount()));
			}
		}
		
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
			if(StringUtils.isBlank(billEntryBean.getInvoiceNumber())) {
				bindingResult.addError(new org.springframework.validation.FieldError(MainetConstants.FUNCTION_MASTER.FUNC_MASTER,
		                MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
		                session.getMessage("account.validation.enter.invoice.bill.no")));
			}
			if(StringUtils.isBlank(billEntryBean.getNarration())) {
				bindingResult.addError(new org.springframework.validation.FieldError(MainetConstants.FUNCTION_MASTER.FUNC_MASTER,
		                MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
		                session.getMessage("account.narration")));
			}
		}
		
		if (!bindingResult.hasErrors()) {
			AccountBillEntryMasterEnitity entity;
			final Organisation organisation = UserSession.getCurrent().getOrganisation();
			final Long superOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
			billEntryBean.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);
			billEntryBean.setSuccessfulFlag(MainetConstants.MASTER.Y);
			billEntryBean.setSuperOrgId(superOrgId);
			billEntryBean.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			billEntryBean.setOrgShortName(UserSession.getCurrent().getOrganisation().getOrgShortNm());
			billEntryBean.setBillEntryDateDt(Utility.stringToDate(billEntryBean.getTransactionDate()));
			/*long fieldId = 0;*/
			Long workFlowLevel1 = null;
			Long workFlowLevel2 = null;
			Long workFlowLevel3 = null;
			Long workFlowLevel4 = null;
			Long workFlowLevel5 = null;
            /*if (UserSession.getCurrent().getLoggedLocId() != null) {
				//TbLocationMas locMas = locMasService.findById(UserSession.getCurrent().getLoggedLocId());
				/*if ((locMas.getLocRevenueWZMappingDto() != null) && !locMas.getLocRevenueWZMappingDto().isEmpty()) {
					fieldId = locMas.getLocRevenueWZMappingDto().get(0).getCodIdRevLevel1();
				}*/
			
			//Long locId = locMasService.getRevenueLocationId(billEntryBean.getFieldId(),UserSession.getCurrent().getOrganisation().getOrgid());			
			List<Long> locId = locMasService.getlocationListByFieldIdAndOrgId(billEntryBean.getFieldId(),UserSession.getCurrent().getOrganisation().getOrgid());
			
			LOGGER.info("Loc List based on Field Id: " + billEntryBean.getFieldId() + " List: " +locId);
			Department dept = ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
	                .findDepartmentByCode(MainetConstants.DEPT_SHORT_NAME.ACCOUNT);
			
				Long finalLocId = null;
				ServiceMaster serviceToFindWF = serviceMasterService.getServiceByShortName("VB", billEntryBean.getOrgId());
				WorkflowMas workflowMas = null;
				
				if(!locId.isEmpty())
				{
					for(final Long locId1 : locId){
						List<Long> locIdBasedOnDeptOrgIdList = locMasService.getOperLocationIdBasedOnLocIdDeptIdOrgId(locId1,dept.getDpDeptid(),UserSession.getCurrent().getOrganisation().getOrgid());
			
						if(!locIdBasedOnDeptOrgIdList.isEmpty()){
							//finalLocId = locIdBasedOnDeptOrgIdList.get(0);
							
							LocOperationWZMappingDto operLocationAndDeptId = locMasService.findOperLocationAndDeptId(locIdBasedOnDeptOrgIdList.get(0),dept.getDpDeptid());
							
							LookUp lookup= null;
							try {
								lookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue("BTW", "AIC", 1,
										UserSession.getCurrent().getOrganisation());
							} catch (FrameworkException e) {
								LOGGER.info("Bill Type perfix not define in AIC perfix");
							}
							if (lookup != null && lookup.getOtherField() != null && lookup.getOtherField().equals("Y")) {
								List<WorkflowMas> worKFlowList = iWorkflowTypeDAO.getDefinedActiveWorkFlows(UserSession.getCurrent().getOrganisation().getOrgid(),
										dept.getDpDeptid(), serviceToFindWF.getSmServiceId(), null,
										billEntryBean.getBillTypeId(), null);
								if (CollectionUtils.isNotEmpty(worKFlowList)) {
									for (WorkflowMas mas : worKFlowList) {
										if (mas.getStatus().equalsIgnoreCase("Y")) {
											if (mas.getToAmount() != null) {
												workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(UserSession.getCurrent().getOrganisation().getOrgid(),
														dept.getDpDeptid(), serviceToFindWF.getSmServiceId(),
														billEntryBean.getTotalSanctionedAmount(), null, billEntryBean.getBillTypeId(),
														operLocationAndDeptId.getCodIdOperLevel1(), operLocationAndDeptId.getCodIdOperLevel2(),
														operLocationAndDeptId.getCodIdOperLevel3(), operLocationAndDeptId.getCodIdOperLevel4(),
														operLocationAndDeptId.getCodIdOperLevel5());
												break;
											} else {
												workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(UserSession.getCurrent().getOrganisation().getOrgid(),
														dept.getDpDeptid(), serviceToFindWF.getSmServiceId(), null, null,
														billEntryBean.getBillTypeId(), operLocationAndDeptId.getCodIdOperLevel1(), operLocationAndDeptId.getCodIdOperLevel2(),
														operLocationAndDeptId.getCodIdOperLevel3(), operLocationAndDeptId.getCodIdOperLevel4(),
														operLocationAndDeptId.getCodIdOperLevel5());
												break;
											}
										}
									}
								} 
							}
							
							else {
								
						workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(UserSession.getCurrent().getOrganisation().getOrgid(),
									dept.getDpDeptid(), serviceToFindWF.getSmServiceId(),
									operLocationAndDeptId.getCodIdOperLevel1(), operLocationAndDeptId.getCodIdOperLevel2(),
									operLocationAndDeptId.getCodIdOperLevel3(), operLocationAndDeptId.getCodIdOperLevel4(),
									operLocationAndDeptId.getCodIdOperLevel5());}
								
						}
						if(null != workflowMas){
							finalLocId = locIdBasedOnDeptOrgIdList.get(0);
							LOGGER.info("Workflow Id: " + workflowMas.getWfId() + "based on: " + workflowMas.getCodIdOperLevel1() + workflowMas.getCodIdOperLevel2());
							LOGGER.info("Final LocId based on location Id: " + finalLocId + " DeptId: " +dept.getDpDeptid() + " ordId" + UserSession.getCurrent().getOrganisation().getOrgid());
							break;
						}
					}
					
				}
			
			
			LOGGER.info("Fetch Workflow from tb_location_oper_wardzone based on deptID, locId, OrgId : " + dept.getDpDeptid() + finalLocId + UserSession.getCurrent().getOrganisation().getOrgid());
		    
			final LocOperationWZMappingDto locMas = locMasService.findOperLocationAndDeptId(finalLocId,dept.getDpDeptid());
			
			
			// In workflow, events wise Location Operation WZMapping is required.
			if (locMas != null) {
				locMas.getCodIdOperLevel1();
				workFlowLevel1 = locMas.getCodIdOperLevel1();
				workFlowLevel2 = locMas.getCodIdOperLevel2();
				workFlowLevel3 = locMas.getCodIdOperLevel3();
				workFlowLevel4 = locMas.getCodIdOperLevel4();
				workFlowLevel5 = locMas.getCodIdOperLevel5();
			}
			
			/*}
		if (fieldId == 0) {
			throw new NullPointerException("fieldId is not linked with Location Master for[locId="
					+ UserSession.getCurrent().getEmployee().getTbLocationMas().getLocId() + ",locName="
					+ UserSession.getCurrent().getEmployee().getTbLocationMas().getLocNameEng() + "]");
		}*/
			
				
			billEntryBean.setWorkFlowLevel1(workFlowLevel1);
			billEntryBean.setWorkFlowLevel2(workFlowLevel2);
			billEntryBean.setWorkFlowLevel3(workFlowLevel3);
			billEntryBean.setWorkFlowLevel4(workFlowLevel4);
			billEntryBean.setWorkFlowLevel5(workFlowLevel5);

			// billEntryBean.setFieldId(fieldId);
			if (billEntryBean.getId() == null) {
				billEntryBean.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				billEntryBean.setCreatedDate(new Date());
				billEntryBean.setLanguageId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
				billEntryBean.setLgIpMacAddress(Utility.getClientIpAddress(httpServletRequest));
			}
			final UserSession userSession = UserSession.getCurrent();
			if (billEntryBean.getId() != null) {
				billEntryBean.setUpdatedBy(userSession.getEmployee().getEmpId());
				billEntryBean.setUpdatedDate(new Date());
				billEntryBean.setLgIpMacAddressUpdated(Utility.getClientIpAddress(httpServletRequest));
				billEntryBean.setCheckerUser(UserSession.getCurrent().getEmployee().getEmpId().toString());
				if (billEntryBean.getDupCreatedDate() != null && !billEntryBean.getDupCreatedDate().isEmpty()) {
					billEntryBean.setCreatedDate(Utility.stringToDate(billEntryBean.getDupCreatedDate()));
				}
			}
			billEntryBean.setOrganisation(organisation);
			// billEntryBean.setDepartmentId(tbDepartmentService.getDepartmentIdByDeptCode(MainetConstants.RECEIPT_MASTER.Module));
			final String makerChecker = billEntryService.isMakerChecker(billEntryBean.getOrganisation());
			if (billEntryBean.getNetPayable() == null) {
				billEntryBean.setNetPayable(billEntryBean.getInvoiceValue());
			}
			
			ServiceMaster service = serviceMasterService.getServiceByShortName("VB", billEntryBean.getOrgId());
			LookUp serviceActiveLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(service.getSmServActive(), UserSession.getCurrent().getOrganisation());
			// workflow is not applicable.
			if (Utility.compareDate(transactionalDate, SLIDate)) {
				billEntryBean.setIsMakerChecker(MainetConstants.Y_FLAG);
				entity = billEntryService.createBillEntry(billEntryBean);
				billEntryService.forUpdateBillIdInToDepositEntryTable(billEntryBean, entity);
			} else if ((StringUtils.equals(serviceActiveLookUp.getLookUpCode(), MainetConstants.FlagI)) && (makerChecker.equals(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE) || makerChecker.equals(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE))) {
				billEntryBean.setIsMakerChecker(MainetConstants.Y_FLAG);
				entity = billEntryService.createBillEntry(billEntryBean);
				billEntryService.forUpdateBillIdInToDepositEntryTable(billEntryBean, entity);
			} else {
				entity = billEntryService.createBillEntryWithMakerChecker(billEntryBean,
						Utility.getClientIpAddress(httpServletRequest));
				billEntryService.forUpdateBillIdInToDepositEntryTable(billEntryBean, entity);
			}

			if (billEntryBean.getAttachments() != null && billEntryBean.getAttachments().size() > 0) {

				prepareFileUpload(billEntryBean);

				String documentName = billEntryBean.getAttachments().get(0).getDocumentName();
				if (documentName != null && !documentName.isEmpty()) {

					FileUploadDTO fileUploadDTO = new FileUploadDTO();
					if (billEntryBean.getOrgId() != null) {
						fileUploadDTO.setOrgId(billEntryBean.getOrgId());
					}
					if (billEntryBean.getCreatedBy() != null) {
						fileUploadDTO.setUserId(billEntryBean.getCreatedBy());
					}
					fileUploadDTO.setStatus(MainetConstants.FlagA);
					fileUploadDTO.setDepartmentName(MainetConstants.RECEIPT_MASTER.Module);
					final String accountIds = MainetConstants.RECEIPT_MASTER.Module
							+ MainetConstants.operator.FORWARD_SLACE + entity.getId();
					fileUploadDTO.setIdfId(accountIds);
					boolean fileuploadStatus = accountFileUpload.doMasterFileUpload(billEntryBean.getAttachments(),
							fileUploadDTO);
					if (!fileuploadStatus) {
						throw new FrameworkException("Invoice upload is failed, do to upload file into filenet path");
					}
				}
			}

			List<Long> removeFileById = null;
			String fileId = billEntryBean.getRemoveFileById();
			if (fileId != null && !fileId.isEmpty()) {
				removeFileById = new ArrayList<>();
				String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
				for (String fields : fileArray) {
					removeFileById.add(Long.valueOf(fields));
				}
			}
			if (removeFileById != null && !removeFileById.isEmpty()) {
				billEntryService.updateUploadInvoiceDeletedRecords(removeFileById, billEntryBean.getUpdatedBy());
			}

			populateModel(model, billEntryBean, MODE_CREATE);
			httpServletRequest.getSession().setAttribute(MainetConstants.AccountReceiptEntry.RECEIPT_LIST, entity);
			httpServletRequest.getSession().setAttribute("BillENtryBean", billEntryBean);
			model.addAttribute("displayBillNo", billEntryBean.getBillNo());
			if(!StringUtils.isEmpty(billEntryBean.getGrantFlag()))
			model.addAttribute("grantFlag",billEntryBean.getGrantFlag());
			if(!StringUtils.isEmpty(billEntryBean.getDepositFlag()))
				model.addAttribute("depositFlag",billEntryBean.getDepositFlag());
		} else {
			billEntryBean.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
			billEntryBean.setSuccessfulFlag(MainetConstants.MASTER.N);
			final List<AccountBillEntryExpenditureDetBean> expDetailList = keepExpenditureDataAfterValidationError(
					model, billEntryBean, bindingResult);
			model.addAttribute(PROJECTED_EXPENDITURE_LIST, expDetailList);
			final List<AccountBillEntryDeductionDetBean> dedDetailList = keepDeductionDataAfterValidationError(model,
					billEntryBean, bindingResult);
			model.addAttribute(DED_DET_LIST, dedDetailList);
			model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
			model.addAttribute(BILL_ENTRY_BEAN, billEntryBean);
			populateListOfBillType(model);
			populateVendorList(model);
			populateDepartments(model);
			populateField(model);
			model.addAttribute(MODE, MODE_CREATE);
			model.addAttribute(PAC_MAP, secondaryheadMasterService
					.findExpenditureHeadMapAccountTypeIsOthers(UserSession.getCurrent().getOrganisation().getOrgid()));
		}
		return JSP_FORM;
	}

	public void prepareFileUpload(AccountBillEntryMasterBean billEntryBean) {
		List<DocumentDetailsVO> documentDetailsVOList = billEntryBean.getAttachments();
		billEntryBean.setAttachments(accountFileUpload.prepareFileUpload(documentDetailsVOList));
	}

	/**
	 * @param billEntryBean
	 * @param bindingResult
	 * @return
	 */
	private List<AccountBillEntryExpenditureDetBean> keepExpenditureDataAfterValidationError(final Model model,
			final AccountBillEntryMasterBean billEntryBean, final BindingResult bindingResult) {

		List<AccountBillEntryExpenditureDetBean> list = null;

		list = billEntryBean.getExpenditureDetailList();
		if (list == null) {
			list = new ArrayList<>();
		}
		if (list.isEmpty()) {
			list.add(new AccountBillEntryExpenditureDetBean());
			billEntryBean.setExpenditureDetailList(list);
		}
		List<AccountBudgetProjectedExpenditureBean> projectedExpList = null;
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final FinancialYear financialYear = financialyearService.getFinanciaYearByDate(new Date());
		final Long finYearId = financialYear.getFaYear();
		projectedExpList = accountBudgetProjectedExpenditureService.findExpenditureDataByFinYearId(orgId, finYearId);
		final Map<Long, String> expPacHeadMap = new LinkedHashMap<>();
		final List<String> budgetCodeList = new ArrayList<>();
		if ((projectedExpList != null) && !projectedExpList.isEmpty()) {
			for (final AccountBudgetProjectedExpenditureBean expPac : projectedExpList) {
				budgetCodeList.add(expPac.getPrExpBudgetCode());
			}
		}
		/*setTransactionHeadPrefix(billEntryBean);*/
		billEntryBean.setExpenditureDetailList(list);
		model.addAttribute(PROJECTED_EXPENDITURE_LIST, list);
		model.addAttribute(PAC_MAP, expPacHeadMap);
		model.addAttribute(BUDGET_CODE_LIST, budgetCodeList);
		return list;

	}

	/**
	 * @param billEntryBean
	 * @param bindingResult
	 * @return
	 */
	private List<AccountBillEntryDeductionDetBean> keepDeductionDataAfterValidationError(final Model model,
			final AccountBillEntryMasterBean billEntryBean, final BindingResult bindingResult) {

		List<AccountBillEntryDeductionDetBean> list = null;

		list = billEntryBean.getDeductionDetailList();
		if (list == null) {
			list = new ArrayList<>();
		}
		if (list.isEmpty()) {
			list.add(new AccountBillEntryDeductionDetBean());
			billEntryBean.setDeductionDetailList(list);
		}
		final Map<Long, String> dedPacMap = new LinkedHashMap<>();
		billEntryBean.setDeductionDetailList(list);
		model.addAttribute(DED_DET_LIST, list);
		model.addAttribute(DED_PAC_MAP, dedPacMap);

		return list;
	}

	@RequestMapping(params = "checkTemplate")
	public @ResponseBody String checkTemplate() {
		final VoucherTemplateDTO postDTO = new VoucherTemplateDTO();
		String templateExistFlag = null;
		postDTO.setTemplateType(
				CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.AccountConstants.PN.toString(),
						MainetConstants.VOUCHER_TEMPLATE_ENTRY.TEMPLATE_TYPE_PREFIX,
						UserSession.getCurrent().getOrganisation().getOrgid()));
		postDTO.setVoucherType(
				CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.AccountConstants.JV.toString(),
						MainetConstants.AccountBillEntry.VOT, UserSession.getCurrent().getOrganisation().getOrgid()));
		postDTO.setDepartment(departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString()));
		postDTO.setTemplateFor(
				CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.AccountConstants.BI.toString(),
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

	/**
	 * this method being used in the case of Bill/Invoice Reversal
	 *
	 * @param gridId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(params = "viewBillInvoiceDetail")
	public String viewBillInvoiceDetailsForReversal(@RequestParam("gridId") final long gridId,
			final HttpServletRequest request, final Model model) {

		log("Action formForView");
		AccountBillEntryMasterBean accountBillEntryBean = new AccountBillEntryMasterBean();
		model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.SUCCESS);
		populateModel(model, accountBillEntryBean, MODE_VIEW);
		if (!request.getRequestURI().contains(MainetConstants.AccountBillEntry.ENTRY)) {
			accountBillEntryBean.setAuthorizationMode(MainetConstants.AccountBillEntry.AUTH);
		}
		accountBillEntryBean = billEntryService.populateBillEntryViewData(accountBillEntryBean, gridId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		populateDepartments(model);
		populateField(model);
		populateFund(model);
		model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.VIEW_DATA, accountBillEntryBean);
		model.addAttribute(MainetConstants.AccountBillEntry.ON_BACK_REVARSAL_URL, ACCOUNT_VOUCHER_REVERSAL_HTML);

		return JSP_VIEW_FORM;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "reverse", method = RequestMethod.POST)
	public @ResponseBody String saveReversal(@Valid final VoucherReversalDTO dto, final HttpServletRequest request) {

		final List<VoucherReversalDTO> sessionData = (List<VoucherReversalDTO>) request.getSession()
				.getAttribute(MainetConstants.AccountBillEntry.REVERSAL_GRID_DATA);
		String result = StringUtils.EMPTY;
		final ResponseEntity<?> response = accountChequeOrCashDepositeService.validateDataForReversal(sessionData, dto,
				UserSession.getCurrent().getOrganisation().getOrgid());
		if (response.getStatusCode() == HttpStatus.OK) {
			dto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			dto.setLangId(UserSession.getCurrent().getLanguageId());
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
			billEntryService.reverseBillOrInvoice((List<String>) response.getBody(), dto, fieldId, // ip address method
																									// change
					UserSession.getCurrent().getOrganisation().getOrgid(), UserSession.getCurrent().getLanguageId(),
					Utility.getClientIpAddress(request));
			result = MainetConstants.AccountBillEntry.SUCCESS;
		} else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
			result = (String) response.getBody();
		}

		return result;

	}

	/**
	 * used to validate Transaction date selected Transaction must be either greater
	 * or equal to date defined in other field in SLI prefix
	 * 
	 * @param transactionDate
	 *            :
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(params = "onTransactionDate", method = RequestMethod.GET)
	public @ResponseBody String validateTransactionDate(@RequestParam("transactionDate") final String transactionDate,
			final HttpServletRequest request, final ModelMap model) {
		String response;
		try {
			final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(
					MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_LIVE_VALUE,
					MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
					UserSession.getCurrent().getOrganisation());
			final String date = lookUp.getOtherField();
			Objects.requireNonNull(date, MainetConstants.AccountBillEntry.LIVE_DATE_IN_SLI);
			final Date sliDate = Utility.stringToDate(date);
			final Date transDate = Utility.stringToDate(transactionDate);
			if (transDate.getTime() >= sliDate.getTime()) {
				response = MainetConstants.AccountBillEntry.OK;
			} else {
				response = MainetConstants.AccountBillEntry.VALIDATE_TRANSACTION_DATE + date
						+ MainetConstants.AccountBillEntry.VALIDATE_CURRENT_DATE;
			}

		} catch (final Exception ex) {
			response = MainetConstants.AccountBillEntry.INTERNAL_SERVER_ERROR;
			LOGGER.error("Error while validating Transaction date from SLI Prefix", ex);
		}
		return response;
	}

	@RequestMapping(params = "onAuthorizationDate", method = RequestMethod.GET)
	public @ResponseBody String validateAuthorizationDate(
			@RequestParam("authorizationDate") final String authorizationDate,
			@RequestParam("entryDate") final String entryDate) {
		String response;
		try {
			Objects.requireNonNull(authorizationDate, MainetConstants.AccountBillEntry.VALIDATE_AUTH_DATE_NULL);
			Objects.requireNonNull(entryDate, MainetConstants.AccountBillEntry.VALIDATE_ENTRY_DATE);
			final Date entrDate = Utility.stringToDate(entryDate);
			final Date authDate = Utility.stringToDate(authorizationDate);
			if ((authDate.getTime() >= entrDate.getTime()) && (authDate.getTime() <= new Date().getTime())) {
				response = MainetConstants.AccountBillEntry.OK;
			} else {
				response = ApplicationSession.getInstance().getMessage(MainetConstants.AccountBillEntry.VALIDATE_AUTH_DATE);
			}
		} catch (final Exception ex) {
			response = MainetConstants.AccountBillEntry.INTERNAL_SERVER_ERROR;
			LOGGER.error("Error while validating Authorization date with entry date", ex);
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "accountBillEntryFormPrint", method = RequestMethod.POST)
	public String accountBillEntryFormPrint(final Model model, final HttpServletRequest request) {

		AccountBillEntryMasterBean recordListbean = new AccountBillEntryMasterBean();
		BigDecimal totalDedution = new BigDecimal(0.00);
		BigDecimal totalExpenditure = new BigDecimal(0.00);
		List expendituRerecords = new ArrayList();
		List deductionList = new ArrayList();
		final AccountBillEntryMasterEnitity entitySession = (AccountBillEntryMasterEnitity) request.getSession()
				.getAttribute(MainetConstants.AccountReceiptEntry.RECEIPT_LIST);
		AccountBillEntryMasterEnitity entity = billEntryService.findBillEntryById(entitySession.getOrgId(),
				entitySession.getId());
		AccountBillEntryMasterBean editBean = (AccountBillEntryMasterBean) request.getSession()
				.getAttribute("BillENtryBean");
		recordListbean.setAuthorizationMode(editBean.getAuthorizationMode());
		BigDecimal totalBudgetAmt = new BigDecimal(0.00);
		BigDecimal totalExpenditureAmt = new BigDecimal(0.00);
		BigDecimal totalRequestAmt = new BigDecimal(0.00);
		BigDecimal totalNewBalance = new BigDecimal(0.00);
		BigDecimal totExpAndRequestAmt = new BigDecimal(0.00);

		for (AccountBillEntryExpenditureDetEntity recordListExpenditure : entity.getExpenditureDetailList())

		{
			AccountBillEntryMasterBean recordListbeansdeductionExpenditures = new AccountBillEntryMasterBean();

			recordListbeansdeductionExpenditures.setVendorDesc(billEntryService
					.findAccountHeadCodeBySacHeadId(recordListExpenditure.getSacHeadId(), entity.getOrgId()));
			recordListbeansdeductionExpenditures.setActualAmountStr(
					recordListExpenditure.getBillChargesAmount().setScale(2, RoundingMode.HALF_EVEN).toString());
			totalExpenditure = totalExpenditure
					.add(new BigDecimal(recordListbeansdeductionExpenditures.getActualAmountStr()));
			final FinancialYear financialYear = financialyearService.getFinanciaYearByDate(entity.getBillEntryDate());
			final Long finYearId = financialYear.getFaYear();
			final List<AccountBudgetProjectedExpenditureEntity> projectedExpList = accountBudgetProjectedExpenditureService
					.getExpenditureDetailsForBillEntryFormViewWithFieldId(entity.getOrgId(), finYearId, recordListExpenditure.getSacHeadId(),entity.getFieldId());

			BigDecimal balanceProvisionAmt = null;
			BigDecimal expenditureAmt = null;
			BigDecimal sanctionedAmt = recordListExpenditure.getBillChargesAmount();
			totalRequestAmt=totalRequestAmt.add(sanctionedAmt);
			if ((projectedExpList != null) && !projectedExpList.isEmpty()) {
				for (final AccountBudgetProjectedExpenditureEntity prExpDet : projectedExpList) {
				  if(entity.getDepartmentId().getDpDeptid().equals(prExpDet.getTbDepartment().getDpDeptid())) {
					if (prExpDet.getRevisedEstamt() == null) {
						totalBudgetAmt=totalBudgetAmt.add(prExpDet.getOrginalEstamt());
					} else {
						totalBudgetAmt=totalBudgetAmt.add(new BigDecimal(prExpDet.getRevisedEstamt()));
					}
					if (prExpDet.getExpenditureAmt() == null) {
						expenditureAmt = BigDecimal.ZERO;
						prExpDet.setExpenditureAmt(BigDecimal.ZERO);
						totalExpenditureAmt=totalExpenditureAmt.add(expenditureAmt);
						totExpAndRequestAmt=totExpAndRequestAmt.add(totalExpenditureAmt.add(sanctionedAmt));
						if (prExpDet.getRevisedEstamt() == null) {
							balanceProvisionAmt = prExpDet.getOrginalEstamt().subtract(expenditureAmt.add(sanctionedAmt));
						} else {
							balanceProvisionAmt = new BigDecimal(prExpDet.getRevisedEstamt()).subtract(expenditureAmt.add(sanctionedAmt));
						}
						if (prExpDet.getRevisedEstamt() == null) {
							prExpDet.getExpenditureAmt().add(sanctionedAmt).compareTo(prExpDet.getOrginalEstamt());
						} else {
							prExpDet.getExpenditureAmt().add(sanctionedAmt).compareTo(new BigDecimal(prExpDet.getRevisedEstamt()));
						}
					} else {
						totalExpenditureAmt=totalExpenditureAmt.add(prExpDet.getExpenditureAmt().subtract(sanctionedAmt));
						totExpAndRequestAmt=totExpAndRequestAmt.add(totalExpenditureAmt.add(sanctionedAmt));
						if (prExpDet.getRevisedEstamt() == null) {
							balanceProvisionAmt = prExpDet.getOrginalEstamt().subtract((prExpDet.getExpenditureAmt()));
						} else {
							balanceProvisionAmt = new BigDecimal(prExpDet.getRevisedEstamt()).subtract((prExpDet.getExpenditureAmt()));
						}
						if (prExpDet.getRevisedEstamt() == null) {
							prExpDet.getExpenditureAmt().add(sanctionedAmt).compareTo(prExpDet.getOrginalEstamt());
						} else {
							prExpDet.getExpenditureAmt().add(sanctionedAmt).compareTo(new BigDecimal(prExpDet.getRevisedEstamt()));
						}
					}
					totalNewBalance=totalNewBalance.add(balanceProvisionAmt);
					} 
				}
			}
			expendituRerecords.add(recordListbeansdeductionExpenditures);
		}
		recordListbean.setNewBalanceAmount(totalBudgetAmt);
		recordListbean.setBalanceAmount(totalExpenditureAmt);
		recordListbean.setBillTotalAmount(totalRequestAmt);
		recordListbean.setTotalSanctionedAmount(totalNewBalance);
		recordListbean.setBalAmount(totExpAndRequestAmt);

		for (AccountBillEntryDeductionDetEntity recordList : entity.getDeductionDetailList()) {
			if (recordList != null) {
				AccountBillEntryMasterBean recordListbeansdeduction = new AccountBillEntryMasterBean();

				recordListbeansdeduction.setVendorCodeDescription(
						billEntryService.findAccountHeadCodeBySacHeadId(recordList.getSacHeadId(), entity.getOrgId()));
				if (recordList.getDeductionAmount() != null) {
					recordListbeansdeduction.setDedcutionAmountStr(
							recordList.getDeductionAmount().setScale(2, RoundingMode.HALF_EVEN).toString());
				}
				totalDedution = totalDedution.add(new BigDecimal(recordListbeansdeduction.getDedcutionAmountStr()));
				deductionList.add(recordListbeansdeduction);
			}
		}
		
		final List<AccountBillEntryMeasurementDetBean> measurementList = new ArrayList<>();
		BigDecimal totalMbAmt = BigDecimal.ZERO;
		for (AccountBillEntryMeasurementDetEntity mbList : entity.getMeasuremetDetailList()) {
			if (mbList != null) {
					final AccountBillEntryMeasurementDetBean dto = new AccountBillEntryMeasurementDetBean();
					BeanUtils.copyProperties(mbList, dto);
					dto.setOrgId(mbList.getOrgid());
					measurementList.add(dto);
					totalMbAmt=totalMbAmt.add(dto.getMbItemAmt());
			}
		}
		recordListbean.setMbTotalAmount(totalMbAmt);
		recordListbean.setMeasurementDetailList(measurementList);
		recordListbean.setBillNo(entity.getBillNo());
		recordListbean.setBillDate(Utility.dateToString(entity.getBillEntryDate()));
		if (entity.getVendorId() != null && entity.getVendorId().getVmVendorid() != null) {
			recordListbean.setVendorName(
					tbAcVendormasterService.getVendorNameById(entity.getVendorId().getVmVendorid(), entity.getOrgId()));
		}
		recordListbean.setNarration(entity.getNarration());
		recordListbean.setTotalDeductions(totalDedution.setScale(2, RoundingMode.HALF_EVEN));
		recordListbean.setTotalActualamt(totalExpenditure);
		if (totalDedution != null) {
			recordListbean.setNetPayable(totalExpenditure.subtract(totalDedution));
		}

		if (recordListbean.getNetPayable() != null) {
			final String totalAmountInWords = Utility.convertBigDecimalToWord(recordListbean.getNetPayable());
			recordListbean.setTotalSanctionedAmtStr(totalAmountInWords);
		}
		recordListbean.setExpenditureDetailList(expendituRerecords);
		recordListbean.setDeductionDetailList(deductionList);
		/*For Back Button -> going back to GrantMaster Summarry Page*/ 
		String grantFlag = request.getParameter("grantFlag");
		model.addAttribute("grantFlag",grantFlag);
		
		String depositFlag=request.getParameter("depositFlag");
		model.addAttribute("depositFlag",depositFlag);
		
		model.addAttribute(ACCOUNT_BILL_ENTRY_VIEW, recordListbean);

		return JSP_OF_ACCOUNTBILL_PRINT;

	}

	@RequestMapping(params = "getdeductionExpHeadMapDetails", method = RequestMethod.POST)
	public @ResponseBody Map<Long, String> getAllBudgetHeadDesc(final AccountBillEntryMasterBean bean,
			final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest request,
			final BindingResult bindingResult) {
		log("Action 'form': 'getExistBillEnryFormAllDetails'");
		Map<Long, String> dedutionExpHeadMap = new LinkedHashMap<>();
		List<AccountBillEntryExpenditureDetBean> expDetList = bean.getExpenditureDetailList();
		for (AccountBillEntryExpenditureDetBean accountBillEntryExpenditureDetBean : expDetList) {
			Long sacheadid = accountBillEntryExpenditureDetBean.getBudgetCodeId();
			String accountHeadCode = "";
			if (sacheadid != null) {
				accountHeadCode = secondaryheadMasterService.findByAccountHead(sacheadid);
				dedutionExpHeadMap.put(sacheadid, accountHeadCode);
			}
		}
		return dedutionExpHeadMap;
	}

	@RequestMapping(params = "checkExpHeadDedExpheadExists")
	public @ResponseBody boolean checkExpHeadDedExpheadExists(AccountBillEntryMasterBean bean,
			final RedirectAttributes redirectAttributes, final HttpServletRequest request, final Model model) {
		log("Action 'form': 'checkExpHeadDedExpheadExists'");
		boolean expHeadExists = false;
		Map<String, String> expHeadMap = new LinkedHashMap<>();
		Map<String, String> dedExpHeadMap = new LinkedHashMap<>();
		List<AccountBillEntryExpenditureDetBean> expList = bean.getExpenditureDetailList();
		List<AccountBillEntryDeductionDetBean> deductionList = bean.getDeductionDetailList();
		if (deductionList != null) {
			for (AccountBillEntryDeductionDetBean accountBillEntryDeductionDetBean : deductionList) {
				if (accountBillEntryDeductionDetBean.getBchId() != null && accountBillEntryDeductionDetBean.getBchId() >0) {
					dedExpHeadMap.put(accountBillEntryDeductionDetBean.getBchId().toString(),
							accountBillEntryDeductionDetBean.getBchId().toString());
				}
			}
		}
		if (expList != null) {
			for (AccountBillEntryExpenditureDetBean accountBillEntryExpenditureDetBean : expList) {
				if (accountBillEntryExpenditureDetBean.getBudgetCodeId() != null) {
					expHeadMap.put(accountBillEntryExpenditureDetBean.getBudgetCodeId().toString(),
							accountBillEntryExpenditureDetBean.getBudgetCodeId().toString());
				}
			}
		}
		if (dedExpHeadMap != null && expHeadMap != null) {
			expHeadExists = mapsAreEqual(dedExpHeadMap, expHeadMap);
		}
		return expHeadExists;
	}

	public boolean mapsAreEqual(Map<String, String> dedExpHeadMap, Map<String, String> expHeadMap) {
		try {
			for (String k : dedExpHeadMap.keySet()) {
				if (!expHeadMap.get(k).equals(dedExpHeadMap.get(k))) {
					return true;
				}
			}
			/*
			 * for (String y : dedExpHeadMap.keySet()) { if (!expHeadMap.containsKey(y)) {
			 * return true; } }
			 */
		} catch (NullPointerException np) {
			return true;
		}
		return false;
	}

	/*
	 * This method gets the expenditure budget data based on the account heads and
	 * the department
	 */
	@RequestMapping(params = "checkBudgetBillSumaryWiseData")
	public String checkBudgetBillSumaryWiseData(@RequestParam("billId") final Long billId,
			@RequestParam("entryDate") final String entryDate, @RequestParam("paymentAmt") final String paymentAmt,
			final HttpServletRequest request, final Model model) {

		BigDecimal paymentAmount = new BigDecimal(paymentAmt);
		BigDecimal finalBalAmt = BigDecimal.ZERO;
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final FinancialYear financialYear = financialyearService.getFinanciaYearByDate(Utility.stringToDate(entryDate));
		final Long finYearId = financialYear.getFaYear();
		List<AccountBillEntryMasterEnitity> billEntryList = null;
		int count = 0;
		if (billId != null) {
			billEntryList = billEntryService.getBillDataByBillId(billId, orgId);
		}
		final AccountBillEntryMasterBean billEntryBean = new AccountBillEntryMasterBean();
		final List<AccountBillEntryExpenditureDetBean> billExpBudgetDetBeanList = new ArrayList<>();
		if ((billEntryList != null) && !billEntryList.isEmpty()) {
			for (final AccountBillEntryMasterEnitity list : billEntryList) {
				List<AccountBillEntryExpenditureDetEntity> billExpList = list.getExpenditureDetailList();
				for (AccountBillEntryExpenditureDetEntity accountBillEntryExpenditureDetEntity : billExpList) {
					AccountBudgetCodeEntity budgetCode = null;
					budgetCode = billEntryService.findBudgetHeadIdByUsingSacHeadId(
							accountBillEntryExpenditureDetEntity.getSacHeadId(),
							accountBillEntryExpenditureDetEntity.getOrgid());
					if (budgetCode != null && budgetCode.getprBudgetCodeid() != null) {
						Long bchId = accountBillEntryExpenditureDetEntity.getSacHeadId();
						final List<AccountBudgetProjectedExpenditureEntity> projectedExpList = accountBudgetProjectedExpenditureService
								.getExpenditureDetailsForPaymentEntryFormView(orgId, finYearId, bchId,billId);
						AccountBillEntryExpenditureDetBean billExpDetBean = null;
						BigDecimal balanceProvisionAmt = null;
						BigDecimal newBalanceAmount = null;
						BigDecimal expenditureAmt = null;
						if ((projectedExpList != null) && !projectedExpList.isEmpty()) {
							for (final AccountBudgetProjectedExpenditureEntity prExpDet : projectedExpList) {
								billExpDetBean = new AccountBillEntryExpenditureDetBean();
								billExpDetBean.setProjectedExpenditureId(prExpDet.getPrExpenditureid());
								if (prExpDet.getRevisedEstamt() == null || prExpDet.getRevisedEstamt().equals("0")) {
									billExpDetBean.setOriginalEstimate(prExpDet.getOrginalEstamt());
								} else {
									billExpDetBean.setOriginalEstimate(new BigDecimal(prExpDet.getRevisedEstamt()));
								}
								if (prExpDet.getExpenditureAmt() == null) {
									expenditureAmt = BigDecimal.ZERO;
									prExpDet.setExpenditureAmt(BigDecimal.ZERO);
									billExpDetBean.setBalanceAmount(expenditureAmt);
									if (prExpDet.getRevisedEstamt() == null
											|| prExpDet.getRevisedEstamt().equals("0")) {
										balanceProvisionAmt = prExpDet.getOrginalEstamt()
												.subtract(expenditureAmt.add(paymentAmount));
									} else {
										balanceProvisionAmt = new BigDecimal(prExpDet.getRevisedEstamt())
												.subtract(expenditureAmt.add(paymentAmount));
									}
									newBalanceAmount = expenditureAmt.add(paymentAmount);
									if (prExpDet.getRevisedEstamt() == null
											|| prExpDet.getRevisedEstamt().equals("0")) {
										prExpDet.getExpenditureAmt().add(paymentAmount)
												.compareTo(prExpDet.getOrginalEstamt());
									} else {
										prExpDet.getExpenditureAmt().add(paymentAmount)
												.compareTo(new BigDecimal(prExpDet.getRevisedEstamt()));
									}
								} else {
									billExpDetBean.setBalanceAmount(prExpDet.getExpenditureAmt());
									if (prExpDet.getRevisedEstamt() == null
											|| prExpDet.getRevisedEstamt().equals("0")) {
										balanceProvisionAmt = prExpDet.getOrginalEstamt()
												.subtract((prExpDet.getExpenditureAmt().add(paymentAmount)));
									} else {
										balanceProvisionAmt = new BigDecimal(prExpDet.getRevisedEstamt())
												.subtract((prExpDet.getExpenditureAmt().add(paymentAmount)));
									}
									newBalanceAmount = prExpDet.getExpenditureAmt().add(paymentAmount);
									if (prExpDet.getRevisedEstamt() == null
											|| prExpDet.getRevisedEstamt().equals("0")) {
										prExpDet.getExpenditureAmt().add(paymentAmount)
												.compareTo(prExpDet.getOrginalEstamt());
									} else {
										prExpDet.getExpenditureAmt().add(paymentAmount)
												.compareTo(new BigDecimal(prExpDet.getRevisedEstamt()));
									}
								}
								finalBalAmt = finalBalAmt.add(billExpDetBean.getOriginalEstimate()
										.subtract(billExpDetBean.getBalanceAmount()));
							}
						}
					} else {
						count++;
					}
				}
			}
		}
		if (count != 0) {
			model.addAttribute(MainetConstants.AccountBillEntry.ERROR_MSG,
					ApplicationSession.getInstance().getMessage("account.bill.entry.controller.budget.payment.data"));
		} else if (finalBalAmt.compareTo(new BigDecimal(paymentAmt)) < 0) {
			model.addAttribute(MainetConstants.AccountBillEntry.ERROR_MSG,
					ApplicationSession.getInstance().getMessage("account.bill.payment.entry.budget.balance.data"));
		}
		billEntryBean.setExpenditureDetailList(billExpBudgetDetBeanList);
		model.addAttribute(BUDGET_DETAILS, billExpBudgetDetBeanList);
		model.addAttribute(BILL_ENTRY_BEAN, billEntryBean);

		return ACCOUNT_EXPENDITURE_DETAILS;
	}

	@RequestMapping(params = "showDetails")
	public String showDetails(final Model model, final HttpServletRequest request,
			AccountBillEntryMasterBean accountBillEntryBean, @RequestParam("appNo") final String appNo,
			@RequestParam("actualTaskId") final long actualTaskId) throws Exception {
		log("Action 'formForUpdate'");
		String viewmode = "EDIT";
		LookUp isEditablelookUp=null;
		try {
		   isEditablelookUp = CommonMasterUtility.getValueFromPrefixLookUp(AccountConstants.BRO.getValue(),PrefixConstants.AccountPrefix.AIC.toString(),UserSession.getCurrent().getOrganisation());
		}catch(Exception e) {
			LOGGER.error("Prefix not found ",e);
		}
		if (isEditablelookUp != null && isEditablelookUp.getOtherField().equals(MainetConstants.Y_FLAG))
			viewmode = MainetConstants.VIEW;		
		// AccountBillEntryMasterBean accountBillEntryBean = new
		// AccountBillEntryMasterBean();
		populateModel(model, accountBillEntryBean, MODE_UPDATE);
		populateDepartments(model);
		populateField(model);
		populateFund(model);
		if (!request.getRequestURI().contains(MainetConstants.AccountBillEntry.ENTRY)) {
			accountBillEntryBean.setAuthorizationMode(MainetConstants.AccountBillEntry.AUTH);
			model.addAttribute(MainetConstants.AccountBillEntry.MODEL_CHK, MainetConstants.AccountBillEntry.AUTH);
		} else {
			model.addAttribute(MainetConstants.AccountBillEntry.MODEL_CHK, MainetConstants.Complaint.MODE_EDIT);
		}
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		accountBillEntryBean = billEntryService.populateBillEntryWorkFlowData(accountBillEntryBean, appNo, orgId,
				actualTaskId);
		if (accountBillEntryBean.getBillTypeCode().equals("W")) {
			viewmode = MainetConstants.VIEW;
		}
		if (viewmode.equals(MainetConstants.VIEW)) {
			model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.SUCCESS);
		} else {
			model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
		}
		Map<Long, String> dedutionExpHeadMap = new LinkedHashMap<>();
		List<AccountBillEntryExpenditureDetBean> expDetList = accountBillEntryBean.getExpenditureDetailList();
		for (AccountBillEntryExpenditureDetBean accountBillEntryExpenditureDetBean : expDetList) {
			Long sacheadid = accountBillEntryExpenditureDetBean.getSacHeadId();
			String accountHeadCode = "";
			if (sacheadid != null) {
				accountHeadCode = secondaryheadMasterService.findByAccountHead(sacheadid);
				dedutionExpHeadMap.put(sacheadid, accountHeadCode);
			}
		}
		model.addAttribute("dedutionExpHeadMap", dedutionExpHeadMap);
		model.addAttribute(MainetConstants.AccountBillEntry.EDIT_DATA, accountBillEntryBean);
		model.addAttribute(MainetConstants.AccountBillEntry.EXPENDITURE_HEAD_MAP, secondaryheadMasterService
				.findExpenditureHeadMapAccountTypeIsOthers(UserSession.getCurrent().getOrganisation().getOrgid()));
		// model.addAttribute(MainetConstants.AccountBillEntry.DEDUCT_HEAD_MAP,secondaryheadMasterService.findDeductionHeadMap(UserSession.getCurrent().getOrganisation().getOrgid()));
		final Organisation org = UserSession.getCurrent().getOrganisation();
		Long taxMasLookUpId = null;
		List<LookUp> taxMaslookUpList = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC, 1, org);
		for (LookUp lookUp : taxMaslookUpList) {
			if (lookUp.getLookUpCode().equals(MainetConstants.StandardAccountHeadMapping.TDS)) {
				taxMasLookUpId = lookUp.getLookUpId();
			}
		}
		//deduction head against advanced adjustment
		Map<Long, String> dedutiondMap = new LinkedHashMap<>();
		   List<AccountBillEntryDeductionDetBean> deductionList = accountBillEntryBean.getDeductionDetailList();
		   for (AccountBillEntryDeductionDetBean accountBillEntryDeductionDetBean : deductionList) {
				Long sacheadid = accountBillEntryDeductionDetBean.getSacHeadId();
				String accountHeadCode = "";
				if (sacheadid != null) {
					accountHeadCode = secondaryheadMasterService.findByAccountHead(sacheadid);
					dedutiondMap.put(sacheadid, accountHeadCode);
				}
			}
		   model.addAttribute(MainetConstants.AccountBillEntry.DEDUCT_HEAD_MAP,dedutiondMap);
		// tax maser to bill deduction account heads
		model.addAttribute(MainetConstants.AccountBillEntry.DEDUCT_HEAD_MAP,
				secondaryheadMasterService.getTaxMasBillDeductionAcHeadAllDetails(org.getOrgid(), taxMasLookUpId));
		// get attached document
	        final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
	                .getBean(IAttachDocsService.class).findByCode(orgId,
	                		MainetConstants.RECEIPT_MASTER.Module + MainetConstants.operator.FORWARD_SLACE
	                                + accountBillEntryBean.getId());
	        List<DocumentDto> documentDtos = new ArrayList<>();
	        // iterate and set document details
	        attachDocs.forEach(doc -> {
	            DocumentDto docDto = new DocumentDto();
	            // get employee name who attach this image
	            Employee emp = employeeService.findEmployeeById(doc.getUserId());
	            docDto.setAttBy(emp.getFullName());
	            docDto.setAttFname(doc.getAttFname());
	            docDto.setAttId(doc.getAttId());
	            docDto.setAttPath(doc.getAttPath());
	            documentDtos.add(docDto);
	        });
	        boolean lastApproval = workFlowTypeService
					.isLastTaskInCheckerTaskList(accountBillEntryBean.getActualTaskId());
	        accountBillEntryBean.setLastCheck(lastApproval);
	        model.addAttribute("attachDocs",attachDocs);
	        model.addAttribute("documentDtos",documentDtos);
	     if(viewmode.equals(MainetConstants.VIEW))   
	      return  WORKFLOW_VIEW_FORM;
	     
	     
		  return WORKFLOW_FORM;
	}

	@RequestMapping(params = "billFormForViewRTGS", method = RequestMethod.POST)
	public String billFormForViewRTGS(final Model model, final HttpServletRequest request,
			@RequestParam("bmId") final Long bmId, @RequestParam("paymentId") final Long paymentId,
			@RequestParam(MainetConstants.TAX_HEAD_MAPPING_MASTER.MODE_DATA) final String viewmode) {

		log("Action billFormForViewRTGS");
		AccountBillEntryMasterBean accountBillEntryBean = new AccountBillEntryMasterBean();
		if (viewmode.equals(MainetConstants.VIEW)) {
			model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.SUCCESS);
		} else if (viewmode.equals(MainetConstants.AccountBillEntry.PAYMENT_REVIEW)) {
			model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.SUCCESS);
			accountBillEntryBean.setPaymentEntryFlag(MainetConstants.AccountBillEntry.PAYMENT);
		} else {
			model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
		}
		populateModel(model, accountBillEntryBean, MODE_VIEW);
		if (!request.getRequestURI().contains(MainetConstants.AccountBillEntry.ENTRY)) {
			accountBillEntryBean.setAuthorizationMode(MainetConstants.AccountBillEntry.AUTH);
		}
		accountBillEntryBean = billEntryService.populateBillEntryViewData(accountBillEntryBean, bmId,
				UserSession.getCurrent().getOrganisation().getOrgid());

		Map<Long, String> dedutionExpHeadMap = new LinkedHashMap<>();
		List<AccountBillEntryExpenditureDetBean> expDetList = accountBillEntryBean.getExpenditureDetailList();
		for (AccountBillEntryExpenditureDetBean accountBillEntryExpenditureDetBean : expDetList) {
			Long sacheadid = accountBillEntryExpenditureDetBean.getSacHeadId();
			String accountHeadCode = "";
			if (sacheadid != null) {
				accountHeadCode = secondaryheadMasterService.findByAccountHead(sacheadid);
				dedutionExpHeadMap.put(sacheadid, accountHeadCode);
			}
		}
		model.addAttribute("dedutionExpHeadMap", dedutionExpHeadMap);

		accountBillEntryBean.setPaymentId(paymentId);

		model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.VIEW_DATA, accountBillEntryBean);
		model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.MODE_FLAG, MainetConstants.RnLCommon.MODE_VIEW);
		return JSP_RTGS_VIEW_FORM;
	}

	@RequestMapping(method = RequestMethod.POST, params = "doFileUpload")
	public @ResponseBody JsonViewObject uploadDocument(final HttpServletRequest httpServletRequest,
			final HttpServletResponse response, final String fileCode, @RequestParam final String browserType) {
		UserSession.getCurrent().setBrowserType(browserType);
		final MultipartHttpServletRequest request = (MultipartHttpServletRequest) httpServletRequest;
		final JsonViewObject jsonViewObject = FileUploadUtility.getCurrent().doFileUpload(request, fileCode,
				browserType);
		return jsonViewObject;
	}

	@RequestMapping(method = RequestMethod.POST, params = "doFileUploadValidatn")
	public @ResponseBody List<JsonViewObject> doFileUploadValidatn(final HttpServletRequest httpServletRequest,
			@RequestParam final String browserType) {
		UserSession.getCurrent().setBrowserType(browserType);
		final List<JsonViewObject> result = FileUploadUtility.getCurrent().getFileUploadList();
		return result;
	}

	@RequestMapping(method = RequestMethod.POST, params = "doFileDeletion")
	public @ResponseBody JsonViewObject doFileDeletion(@RequestParam final String fileId,
			final HttpServletRequest httpServletRequest, @RequestParam final String browserType,
			@RequestParam(name = "uniqueId", required = false) final Long uniqueId) {
		UserSession.getCurrent().setBrowserType(browserType);
		JsonViewObject jsonViewObject = JsonViewObject.successResult();
		jsonViewObject = FileUploadUtility.getCurrent().deleteFile(fileId);
		return jsonViewObject;
	}

	@RequestMapping(params = "taxDeduction", method = RequestMethod.POST)
	public @ResponseBody String getTaxDeductionamount(final AccountBillEntryMasterBean bean, final Model model,
			final RedirectAttributes redirectAttributes, final HttpServletRequest request,
			final BindingResult bindingResult, @RequestParam final String bmBilltypeCpdId,
			@RequestParam final int cnt) {
		LOGGER.info("Tax Deduction method started--------------------------->");
		final List<LookUp> billTypeLookupList = CommonMasterUtility
				.getLookUps(PrefixConstants.AccountPrefix.ABT.toString(), UserSession.getCurrent().getOrganisation());
		final BigDecimal ONE_HUNDRED = new BigDecimal(100);
		String lookupothervalue = null;
		String response = null;
		String panNo = null;
		Long taxMasLookUpId = null;
		Long taxId = null;
		// checking Otherfield value available or not
		for (LookUp lookUp : billTypeLookupList) {
			if (Long.valueOf(lookUp.getLookUpId()).equals(bean.getBillTypeId())) {
				lookupothervalue = lookUp.getOtherField();
			}
		}
		try {
			LOGGER.info("ABT Perfix Value --------------------------->"+lookupothervalue);
			if (lookupothervalue.equals("Y")) {
				// getting vendor Details for pancard no
				List<Object[]> vendorList = tbAcVendormasterService
						.getVendorDetails(UserSession.getCurrent().getOrganisation().getOrgid(), bean.getVendorId());
				for (Object[] vendor : vendorList) {
					panNo = vendor[2].toString();
				}
				LOGGER.info("PAN NO --------------------------->"+panNo);
				// Checking taxId is available in tax details or not.
				List<LookUp> taxMaslookUpList = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC, 1,
						UserSession.getCurrent().getOrganisation());
				for (LookUp lookUp : taxMaslookUpList) {
					if (lookUp.getLookUpCode().equals(MainetConstants.StandardAccountHeadMapping.TDS)) {
						taxMasLookUpId = lookUp.getLookUpId();
					}
				}
				Map<Long, String> taxMasBillDedDetailsMap = secondaryheadMasterService
						.getTaxMasBillDeductionTaxId(UserSession.getCurrent().getOrganisation().getOrgid(), taxMasLookUpId);
				for (Map.Entry<Long, String> entry : taxMasBillDedDetailsMap.entrySet()) {
					if (entry.getValue().equals(bean.getDeductionDetailList().get(cnt).getBudgetCodeId().toString())) {
						taxId = entry.getKey();
					}
				}
				LOGGER.info("TAX ID  --------------------------->"+taxId);
				// Checking panNo is available in deduction table if available the populate this
				// case.
				List<TaxDefinationDto> taxDefinationDtos = taxDefinationService
						.getTaxDefinationList(UserSession.getCurrent().getOrganisation().getOrgid());
				for (TaxDefinationDto taxDefinationDto : taxDefinationDtos) {
					if (taxDefinationDto.getTaxId().equals(taxId)) {
						LOGGER.info("TAX ID  MATCH--------------------------->"+taxDefinationDto.getTaxId());
						if (panNo == null || panNo.isEmpty()) {
							LOGGER.info("PAN No NULL--------------------------->"+panNo);
							if (taxDefinationDto.getTaxPanApp().equals("N") || taxDefinationDto.getTaxPanApp().isEmpty()) {
								LOGGER.info("PAN APP EQUAL N--------------------------->"+taxDefinationDto.getTaxPanApp());
								if (taxDefinationDto.getTaxThreshold() != null) {
									LOGGER.info("Threshold not null--------------------------->"+taxDefinationDto.getTaxThreshold());
									/*if (taxDefinationDto.getTaxThreshold().compareTo(
											bean.getExpenditureDetailList().get(0).getBillChargesAmount()) == -1) {*/
									LOGGER.info("TaxableValue--------------------------->"+bean.getBmTaxableValue());
									if (taxDefinationDto.getTaxThreshold().compareTo(
											bean.getBmTaxableValue()) == -1) {
										BigDecimal salesTax = bean.getBmTaxableValue()
												.multiply(taxDefinationDto.getRaTaxPercent()).divide(ONE_HUNDRED);
										salesTax = salesTax.setScale(2, RoundingMode.HALF_UP);
										response = salesTax.toString();
										break;
									}
								} else {
									LOGGER.info("Threshold null--------------------------->"+taxDefinationDto.getTaxThreshold());
									BigDecimal salesTax = bean.getBmTaxableValue()
											.multiply(taxDefinationDto.getRaTaxPercent()).divide(ONE_HUNDRED);
									salesTax = salesTax.setScale(2, RoundingMode.HALF_UP);
									response = salesTax.toString();
								}

							}
						} else if (panNo != null) {
							LOGGER.info("PAN No NOT NULL--------------------------->"+panNo);
							if (taxDefinationDto.getTaxPanApp().equals("Y")) {
								LOGGER.info("PAN APP EQUAL Y--------------------------->"+taxDefinationDto.getTaxPanApp());
								if (taxDefinationDto.getTaxThreshold() != null) {
									LOGGER.info("Threshold not null--------------------------->"+taxDefinationDto.getTaxThreshold());
									LOGGER.info("TaxableValue--------------------------->"+bean.getBmTaxableValue());
									if (taxDefinationDto.getTaxThreshold().compareTo(
											bean.getBmTaxableValue()) == -1) {
										BigDecimal salesTax = bean.getBmTaxableValue()
												.multiply(taxDefinationDto.getRaTaxPercent()).divide(ONE_HUNDRED);
										salesTax = salesTax.setScale(2, RoundingMode.HALF_UP);
										response = salesTax.toString();
										break;
									}
								} else {
									LOGGER.info("Threshold null--------------------------->"+taxDefinationDto.getTaxThreshold());
									BigDecimal salesTax = bean.getBmTaxableValue()
											.multiply(taxDefinationDto.getRaTaxPercent()).divide(ONE_HUNDRED);
									salesTax = salesTax.setScale(2, RoundingMode.HALF_UP);
									response = salesTax.toString();
								}
							}
						}

					}
					
				}
				
				try {
				final LookUp roundingStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.CommonMasterUi.RTD,
						MainetConstants.CommonMasterUi.TRP, 1, UserSession.getCurrent().getOrganisation());
				LOGGER.info("Rounding Status--------------------------->");
				if(roundingStatus!=null && roundingStatus.getOtherField()!=null) {
					LOGGER.info("Rounding Status not null--------------------------->"+roundingStatus.getOtherField());
					if(response!=null) {
						LOGGER.info("Response--------------------------->"+response);
					if(roundingStatus.getOtherField().equals(String.valueOf(MainetConstants.NUMBERS.ZERO))) {
						response=(new BigDecimal(response).setScale(0, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP).toString();
					}else if(roundingStatus.getOtherField().equals(String.valueOf(MainetConstants.NUMBERS.TWO))) {
						response=getAmountInRoundOff(Double.valueOf(response));
					}
				  }
				}
			  }catch(Exception e) {
				  LOGGER.error("No Prefix found "+ e);
				}
			}
		}
		catch(Exception ex)
		{
			response = null;
		}
		return response;
	}

	@RequestMapping(params = "refund", method = RequestMethod.POST)
	public String formForCreateFromGrant(final HttpServletRequest request, final Model model,
			@RequestParam("categoryTypeId") final String categoryType,
			@RequestParam("id") final Long id) {
		log("Action 'createformforRefund'");
		
		//dto
		final AccountBillEntryMasterBean accountBillEntryBean = new AccountBillEntryMasterBean();
		// final Long depBillTypeId =
		// CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("GFD",
		// PrefixConstants.AccountPrefix.ABT.toString(),
		// UserSession.getCurrent().getOrganisation().getOrgid());
		
		//accountBillEntryBean.setBillTypeDesc("Grant Bill Entry");
		
		final List<LookUp> recieptVouType = CommonMasterUtility.getListLookup(
				MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV, UserSession.getCurrent().getOrganisation());
		for (LookUp lookUps : recieptVouType) {
			if (lookUps.getLookUpId() == 42676 ) {
				
				 AccountLoanMasterDto loanDataList = accountLoanMasterService.findLoanMasterData
						(id, null, null, UserSession.getCurrent().getOrganisation().getOrgid(),null).get(0);
				 
				 accountBillEntryBean.setDepartment(loanDataList.getLnDeptname());
				 accountBillEntryBean.setVendorId(loanDataList.getVendorId());
				 accountBillEntryBean.setSanctionedAmountStr(loanDataList.getSantionAmount().toString());
		
			}
		}
		
		
		model.addAttribute(BILL_ENTRY_BEAN, accountBillEntryBean);
		return REFUND_FORM;
	}
	
	@RequestMapping(params="createFormForGrantUtilization" , method= {RequestMethod.POST})
	public String formForCreateForGrantUtilzation(final HttpServletRequest request, final Model model,
			@RequestParam("grantId") final Long grantId, @RequestParam("payableAmount") Long payableAmount,@RequestParam("flag") String flag) {
		
		log("Action 'createformForGrantUtilization'");
		final AccountBillEntryMasterBean accountBillEntryBean = new AccountBillEntryMasterBean();
		AccountGrantMasterDto grantMasterDto = ApplicationContextProvider.getApplicationContext().getBean(AccountGrantMasterService.class).getGrantDetailsByGrntIdAndOrgId(grantId, UserSession.getCurrent().getOrganisation().getOrgid());
		accountBillEntryBean.setGrantFlag(MainetConstants.FlagY);
		accountBillEntryBean.setGrantNo(grantMasterDto.getGrtNo());
		accountBillEntryBean.setGrantId(grantMasterDto.getGrntId());
		accountBillEntryBean.setGrantPayableAmount(BigDecimal.valueOf(payableAmount));
		model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
		populateModel(model, accountBillEntryBean, MODE_CREATE);
		populateField(model);
		populateDepartments(model);
		populateFund(model);
		model.addAttribute(MainetConstants.AccountBillEntry.MODEL_CHK, MainetConstants.Actions.CREATE);
		List<LookUp> billTypeLookupList = CommonMasterUtility.getLookUps(PrefixConstants.AccountPrefix.ABT.toString(),
				UserSession.getCurrent().getOrganisation());
		
		if(flag.equalsIgnoreCase(MainetConstants.FlagU))
		{billTypeLookupList = billTypeLookupList.stream().parallel()
				.filter(tr -> tr.getLookUpCode().equalsIgnoreCase("GRT"))
				.collect(Collectors.toList());
		}
		else {
		billTypeLookupList = billTypeLookupList.stream().parallel()
				.filter(tr -> tr.getLookUpCode().equalsIgnoreCase("GFD"))
				.collect(Collectors.toList());
		}
		
		model.addAttribute(BILL_TYPE_LIST, billTypeLookupList);
		accountBillEntryBean.setBillTypeId(billTypeLookupList.get(0).getLookUpId());
		accountBillEntryBean.setGrantFund(ApplicationContextProvider.getApplicationContext().getBean(AccountFundMasterService.class).getFundCodeDesc(grantMasterDto.getFundId()));
		accountBillEntryBean.setFundId(grantMasterDto.getFundId());
		return JSP_FORM;
	}
	
	@RequestMapping(params="createFormForLoan" , method= {RequestMethod.POST})
	public String formForCreateForLoan(final HttpServletRequest request, final Model model,
			@RequestParam("loanId") final Long loanId,@RequestParam("loanRepaymentId") final Long loanRepaymentId, @RequestParam("loanPayAmount") Long loanPayAmount) {
		
		log("Action 'createformForGrantUtilization'");
		final AccountBillEntryMasterBean accountBillEntryBean = new AccountBillEntryMasterBean();
		accountBillEntryBean.setLoanFlag(MainetConstants.FlagY);
		accountBillEntryBean.setLoanPayableAmount(loanPayAmount);
		accountBillEntryBean.setLoanId(loanId);
		accountBillEntryBean.setLoanRepaymentId(loanRepaymentId);
		accountBillEntryBean.setInvoiceValue(BigDecimal.valueOf(loanPayAmount));
		model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
		populateModel(model, accountBillEntryBean, MODE_CREATE);
		populateField(model);
		populateDepartments(model);
		model.addAttribute(MainetConstants.AccountBillEntry.MODEL_CHK, MainetConstants.Actions.CREATE);
		List<LookUp> billTypeLookupList = CommonMasterUtility.getLookUps(PrefixConstants.AccountPrefix.ABT.toString(),
				UserSession.getCurrent().getOrganisation());
		billTypeLookupList = billTypeLookupList.stream().parallel()
				.filter(tr -> tr.getLookUpCode().equalsIgnoreCase("LNS"))
				.collect(Collectors.toList());
		model.addAttribute(BILL_TYPE_LIST, billTypeLookupList);
		accountBillEntryBean.setBillTypeId(billTypeLookupList.get(0).getLookUpId());
		return JSP_FORM;
	}
	
	//added by rahul.chaubey User Story #40685
	public void populateFund(final Model model)
	{
		boolean fieldDefaultFlag = false;
		final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
		boolean fundDefaultFlag = false;
		if (isDafaultOrgExist) {
			fundDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
					ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
		} else {
			fundDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
					UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
		}
		Organisation defultorg = null;
		Long defultorgId = null;
		if (isDafaultOrgExist && fundDefaultFlag) {
			defultorg = ApplicationSession.getInstance().getSuperUserOrganization();
			defultorgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
		} else if (isDafaultOrgExist && (fundDefaultFlag == false)) {
			defultorg = UserSession.getCurrent().getOrganisation();
			defultorgId = UserSession.getCurrent().getOrganisation().getOrgid();
		} else {
			defultorg = UserSession.getCurrent().getOrganisation();
			defultorgId = UserSession.getCurrent().getOrganisation().getOrgid();
		}
		final LookUp fundLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.FUND_CPD_VALUE,
				PrefixConstants.CMD, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		List<AccountFundMasterBean> fundList = tbAcCodingstructureMasService.getFundMasterActiveStatusList(defultorgId, defultorg,
				fundLookup.getLookUpId(), UserSession.getCurrent().getLanguageId());
		
		model.addAttribute("fundList", fundList);
	}
	
	
	public String getAmountInRoundOff(double amount) {
		double number=2;
		String fmt="%.2f";
		amount*=number;
		amount = Math.ceil(amount);
		amount=amount/2;
		return String.format(fmt,amount);
	}
	

    @RequestMapping(params = "getjqGridFiledload", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> getBudgetCode(final AccountBillEntryMasterBean billEntryBean, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest request, final BindingResult bindingResult)
            throws Exception {
        
        final Map<Long, String> budgetMap = new HashMap<>();
		final Long dpDeptid = billEntryBean.getDepartmentId();
        final Long fieldId = billEntryBean.getFieldId();
        final FinancialYear financialYear = financialyearService.getFinanciaYearByDate(new Date());
		if (financialYear != null) {
			final Long finYearId = financialYear.getFaYear();
			 final List<Object[]> budgetCodeList = budgetCodeService
                .findByAllObjectBudgetHeadId(UserSession.getCurrent().getOrganisation().getOrgid(), finYearId, dpDeptid,fieldId);
        if ((budgetCodeList != null) && !budgetCodeList.isEmpty()) {
            for (final Object[] obj : budgetCodeList) {
            	budgetMap.put(Long.valueOf(obj[0].toString()), (String) obj[1]);
            }
        } else {
            LOGGER.error("NO_RECORD_FOUND ");
        }
		
		}
       return budgetMap;
    
    }
    
    @RequestMapping(params = "viewRefNoDetails")
	public String viewRefNoDetails(final Model model, final HttpServletRequest request,
			AccountBillEntryMasterBean accountBillEntryBean, @RequestParam("appNo") final String appNo,
			@RequestParam("actualTaskId") final long actualTaskId) throws Exception {
		log("Action 'formForUpdate'");
		String viewmode = "EDIT";
		LookUp isEditablelookUp=null;
		try {
		   isEditablelookUp = CommonMasterUtility.getValueFromPrefixLookUp(AccountConstants.BRO.getValue(),PrefixConstants.AccountPrefix.AIC.toString(),UserSession.getCurrent().getOrganisation());
		}catch(Exception e) {
			LOGGER.error("Prefix not found ",e);
		}
		if (isEditablelookUp != null && isEditablelookUp.getOtherField().equals(MainetConstants.Y_FLAG))
			viewmode = MainetConstants.VIEW;		
		populateModel(model, accountBillEntryBean, MODE_UPDATE);
		populateDepartments(model);
		populateField(model);
		populateFund(model);
			accountBillEntryBean.setAuthorizationMode(MainetConstants.AccountBillEntry.AUTH);
			model.addAttribute(MainetConstants.AccountBillEntry.MODEL_CHK, MainetConstants.AccountBillEntry.AUTH);
	
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		accountBillEntryBean = billEntryService.populateBillEntryWorkFlowData(accountBillEntryBean, appNo, orgId,
				actualTaskId);
			viewmode = MainetConstants.VIEW;
		if (viewmode.equals(MainetConstants.VIEW)) {
			model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.SUCCESS);
		} else {
			model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
		}
		Map<Long, String> dedutionExpHeadMap = new LinkedHashMap<>();
		List<AccountBillEntryExpenditureDetBean> expDetList = accountBillEntryBean.getExpenditureDetailList();
		for (AccountBillEntryExpenditureDetBean accountBillEntryExpenditureDetBean : expDetList) {
			Long sacheadid = accountBillEntryExpenditureDetBean.getSacHeadId();
			String accountHeadCode = "";
			if (sacheadid != null) {
				accountHeadCode = secondaryheadMasterService.findByAccountHead(sacheadid);
				dedutionExpHeadMap.put(sacheadid, accountHeadCode);
			}
		}
		model.addAttribute("dedutionExpHeadMap", dedutionExpHeadMap);
		model.addAttribute(MainetConstants.AccountBillEntry.EDIT_DATA, accountBillEntryBean);
		model.addAttribute(MainetConstants.AccountBillEntry.EXPENDITURE_HEAD_MAP, secondaryheadMasterService
				.findExpenditureHeadMapAccountTypeIsOthers(UserSession.getCurrent().getOrganisation().getOrgid()));
		// model.addAttribute(MainetConstants.AccountBillEntry.DEDUCT_HEAD_MAP,secondaryheadMasterService.findDeductionHeadMap(UserSession.getCurrent().getOrganisation().getOrgid()));
		final Organisation org = UserSession.getCurrent().getOrganisation();
		Long taxMasLookUpId = null;
		List<LookUp> taxMaslookUpList = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC, 1, org);
		for (LookUp lookUp : taxMaslookUpList) {
			if (lookUp.getLookUpCode().equals(MainetConstants.StandardAccountHeadMapping.TDS)) {
				taxMasLookUpId = lookUp.getLookUpId();
			}
		}
		//deduction head against advanced adjustment
		Map<Long, String> dedutiondMap = new LinkedHashMap<>();
		   List<AccountBillEntryDeductionDetBean> deductionList = accountBillEntryBean.getDeductionDetailList();
		   for (AccountBillEntryDeductionDetBean accountBillEntryDeductionDetBean : deductionList) {
				Long sacheadid = accountBillEntryDeductionDetBean.getSacHeadId();
				String accountHeadCode = "";
				if (sacheadid != null) {
					accountHeadCode = secondaryheadMasterService.findByAccountHead(sacheadid);
					dedutiondMap.put(sacheadid, accountHeadCode);
				}
			}
		   model.addAttribute(MainetConstants.AccountBillEntry.DEDUCT_HEAD_MAP,dedutiondMap);
		// tax maser to bill deduction account heads
		// get attached document
	        final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
	                .getBean(IAttachDocsService.class).findByCode(orgId,
	                		MainetConstants.RECEIPT_MASTER.Module + MainetConstants.operator.FORWARD_SLACE
	                                + accountBillEntryBean.getId());
	        List<DocumentDto> documentDtos = new ArrayList<>();
	        // iterate and set document details
	        attachDocs.forEach(doc -> {
	            DocumentDto docDto = new DocumentDto();
	            // get employee name who attach this image
	            Employee emp = employeeService.findEmployeeById(doc.getUserId());
	            docDto.setAttBy(emp.getFullName());
	            docDto.setAttFname(doc.getAttFname());
	            docDto.setAttId(doc.getAttId());
	            docDto.setAttPath(doc.getAttPath());
	            documentDtos.add(docDto);
	        });
	        model.addAttribute("attachDocs",attachDocs);
	        model.addAttribute("documentDtos",documentDtos);
	      return  WORKFLOW_VIEW_FORM_Detail;
	}
    
    @ResponseBody
	@RequestMapping(params = "getTaxDeductionDet", method = RequestMethod.POST)
	public Map<String, Object> getTaxDeductionDet(final AccountBillEntryMasterBean bean, final Model model,
			final RedirectAttributes redirectAttributes, final HttpServletRequest request,
			final BindingResult bindingResult, @RequestParam final String bmBilltypeCpdId,
			@RequestParam final int cnt)
    {
    	Map<String, Object> object = new LinkedHashMap<String, Object>();
    	LOGGER.info("Tax Deduction method started--------------------------->");
		final List<LookUp> billTypeLookupList = CommonMasterUtility
				.getLookUps(PrefixConstants.AccountPrefix.ABT.toString(), UserSession.getCurrent().getOrganisation());
		final BigDecimal ONE_HUNDRED = new BigDecimal(100);
		String lookupothervalue = null;
		String response = null;
		String panNo = null;
		Long taxMasLookUpId = null;
		Long taxId = null;
		// checking Otherfield value available or not
		for (LookUp lookUp : billTypeLookupList) {
			if (Long.valueOf(lookUp.getLookUpId()).equals(bean.getBillTypeId())) {
				lookupothervalue = lookUp.getOtherField();
			}
		}
		try {
			LOGGER.info("ABT Perfix Value --------------------------->"+lookupothervalue);
			if (lookupothervalue.equals("Y")) {
				// getting vendor Details for pancard no
				List<Object[]> vendorList = tbAcVendormasterService
						.getVendorDetails(UserSession.getCurrent().getOrganisation().getOrgid(), bean.getVendorId());
				for (Object[] vendor : vendorList) {
					panNo = vendor[2].toString();
				}
				LOGGER.info("PAN NO --------------------------->"+panNo);
				// Checking taxId is available in tax details or not.
				List<LookUp> taxMaslookUpList = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC, 1,
						UserSession.getCurrent().getOrganisation());
				for (LookUp lookUp : taxMaslookUpList) {
					if (lookUp.getLookUpCode().equals(MainetConstants.StandardAccountHeadMapping.TDS)) {
						taxMasLookUpId = lookUp.getLookUpId();
					}
				}
				Map<Long, String> taxMasBillDedDetailsMap = secondaryheadMasterService
						.getTaxMasBillDeductionTaxId(UserSession.getCurrent().getOrganisation().getOrgid(), taxMasLookUpId);
				for (Map.Entry<Long, String> entry : taxMasBillDedDetailsMap.entrySet()) {
					if (entry.getValue().equals(bean.getDeductionDetailList().get(cnt).getBudgetCodeId().toString())) {
						taxId = entry.getKey();
					}
				}
				LOGGER.info("TAX ID  --------------------------->"+taxId);
				// Checking panNo is available in deduction table if available the populate this
				// case.
				List<TaxDefinationDto> taxDefinationDtos = taxDefinationService
						.getTaxDefinationList(UserSession.getCurrent().getOrganisation().getOrgid());
				for (TaxDefinationDto taxDefinationDto : taxDefinationDtos) {
					if (taxDefinationDto.getTaxId().equals(taxId)) {
						object.put("P", taxDefinationDto.getRaTaxFact());
						object.put("V",taxDefinationDto.getRaTaxPercent());
					}
					
				}
			}
		}
		catch(Exception ex)
		{
			response = null;
		}
		return object;
   }

}