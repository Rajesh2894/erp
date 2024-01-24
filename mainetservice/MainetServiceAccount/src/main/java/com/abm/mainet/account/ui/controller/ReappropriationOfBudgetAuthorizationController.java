package com.abm.mainet.account.ui.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.dto.AccountBillEntryMasterBean;
import com.abm.mainet.account.dto.AccountBudgetProjectedExpenditureBean;
import com.abm.mainet.account.dto.AccountBudgetProjectedRevenueEntryBean;
import com.abm.mainet.account.dto.DocumentDto;
import com.abm.mainet.account.dto.ReappropriationOfBudgetAuthorizationDTO;
import com.abm.mainet.account.service.AccountBudgetProjectedExpenditureService;
import com.abm.mainet.account.service.AccountBudgetProjectedRevenueEntryService;
import com.abm.mainet.account.service.BudgetCodeService;
import com.abm.mainet.account.service.ReappropriationOfBudgetAuthorizationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.acccount.dto.TbAcCodingstructureMas;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.CommonUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Message;
import com.abm.mainet.common.utility.MessageType;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("/ReappropriationOfBudgetAuthorization.html")
public class ReappropriationOfBudgetAuthorizationController extends AbstractController {

	private static final String MAIN_ENTITY_NAME = "tbAcBudgetReappOfAuthorization";
	private static final String MAIN_LIST_NAME = "list";
	private static final String JSP_FORM = "tbAcBudgetReappOfAuthorization/form";
	private static final String JSP_VIEW = "tbAcBudgetReappOfAuthorization/view";
	private static final String JSP_LIST = "tbAcBudgetReappOfAuthorization/list";
	private static final String WORKFLOW_FORM = "AccountBudgetReappWorkflow/form";
	private static final String JSP_VIEW_APP = "AccountBudgetReappWorkflow/form/view";
	private static final String AC_Financialyr_LOOKUPS = "aFinancialYr";
	private String modeView = MainetConstants.BLANK;
	@Resource
	private ReappropriationOfBudgetAuthorizationService reappropriationOfBudgetAuthorizationService;
	@Resource
	private AccountBudgetProjectedRevenueEntryService accountBudgetProjectedRevenueEntryService;
	@Resource
	private AccountBudgetProjectedExpenditureService accountBudgetProjectedExpenditureService;
	@Resource
	private TbFinancialyearService tbFinancialyearService;
	@Resource
	private BudgetCodeService accountBudgetCodeService;
	@Autowired
	private DepartmentService departmentService;
	@Resource
	private SecondaryheadMasterService secondaryheadMasterService;
	@Resource
	private ILocationMasService locMasService;
	@Resource
	private TbAcCodingstructureMasService tbAcCodingstructureMasService;
	@Resource
	private TbOrganisationService tbOrganisationService;
	@Resource
	private AccountFieldMasterService tbAcFieldMasterService;
	@Autowired
	private ServiceMasterService serviceMasterService;
	@Resource
	private IEmployeeService employeeService;

	private List<ReappropriationOfBudgetAuthorizationDTO> chList = null;

	public ReappropriationOfBudgetAuthorizationController() {
		super(ReappropriationOfBudgetAuthorizationController.class, MAIN_ENTITY_NAME);
		log("ReappropriationOfBudgetAuthorizationController created.");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(params = "getGridData", method = RequestMethod.POST)
	public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(final HttpServletRequest request,
			final Model model) {
		log("ReappropriationOfBudgetAuthorization-'gridData' : 'Get grid Data'");
		final JQGridResponse response = new JQGridResponse<>();
		final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
		response.setRows(chList);
		response.setTotal(chList.size());
		response.setRecords(chList.size());
		response.setPage(page);
		model.addAttribute(MAIN_LIST_NAME, chList);
		return response;
	}

	@RequestMapping(params = "getjqGridSearchData")
	public @ResponseBody List<?> getjqGridSearchData(final HttpServletRequest request, final Model model,
			@RequestParam("fromDate") final String fromDate, @RequestParam("toDate") final String toDate,
			@RequestParam("cpdBugtypeId") final Long cpdBugtypeId, @RequestParam("status") final String status) {
		log("ReappropriationOfBudgetAuthorization-'getjqGridSearchData' : 'get jqGrid search data'");
		chList = new ArrayList<>();
		chList.clear();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		String statusRRA = MainetConstants.CommonConstant.BLANK;
		if ((status != null) && !status.isEmpty()) {
			final String statusReapp = status;
			if (statusReapp.equals(MainetConstants.MENU.A)) {
				statusRRA = MainetConstants.MENU.N;
			}
			if (statusReapp.equals(MainetConstants.CommonConstants.INACTIVE)) {
				statusRRA = MainetConstants.MENU.Y;
			}
		}
		final Date frmDate = Utility.stringToDate(fromDate);
		final Date todate = Utility.stringToDate(toDate);
		final String budgIdentifyFlag = MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_IDENTIFY_FLAG;
		chList = reappropriationOfBudgetAuthorizationService.findByAuthorizationGridData(frmDate, todate, cpdBugtypeId,
				statusRRA, budgIdentifyFlag, orgId);
		String amount = null;
		for (final ReappropriationOfBudgetAuthorizationDTO bean : chList) {
			if (bean.getTransferAmount() != null) {
				amount = CommonMasterUtility.getAmountInIndianCurrency(bean.getTransferAmount());
				bean.setFormattedCurrency(amount);
			}
			final String tempDate = CommonUtility.dateToString(bean.getPaEntrydate());
			bean.setTempDate(tempDate);
			if ((bean.getAuthFlag() != null) && !bean.getAuthFlag().isEmpty()) {
				final String status1 = bean.getAuthFlag();
				if (status1.equals(MainetConstants.MENU.Y)) {
					bean.setStatus(MainetConstants.AccountBudgetAdditionalSupplemental.APPROVED);
				}
				if (status1.equals(MainetConstants.MENU.N)) {
					bean.setStatus(MainetConstants.AccountBudgetAdditionalSupplemental.UNAPPROVED);
				}
			}
		}
		return chList;
	}

	@RequestMapping(params = "getjqGridload", method = RequestMethod.POST)
	public String loadBudgetReappropriationData(final ReappropriationOfBudgetAuthorizationDTO bean, final Model model,
			final RedirectAttributes redirectAttributes, final HttpServletRequest request,
			final BindingResult bindingResult) {
		log("ReappropriationOfBudgetAuthorizationController-'getjqGridsearch' : 'get jqGridload data'");
		String result = MainetConstants.CommonConstant.BLANK;
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
				PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
				PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		final Long cpdBugtypeId = bean.getCpdBugtypeId();

		final Map<Long, String> deptMap = new LinkedHashMap<>(0);
		if (revenueLookup.getLookUpId() == cpdBugtypeId) {
			final List<Object[]> department = accountBudgetProjectedRevenueEntryService.getAllDepartmentIdsData(orgId);
			for (final Object[] dep : department) {
				if (dep[0] != null) {
					deptMap.put((Long) (dep[0]), (String) dep[1]);
				}
			}
		}
		if (expLookup.getLookUpId() == cpdBugtypeId) {
			final List<Object[]> department = accountBudgetProjectedExpenditureService.getBudgetProjExpDeptIds(orgId);
			for (final Object[] dep : department) {
				if (dep[0] != null) {
					deptMap.put((Long) (dep[0]), (String) dep[1]);
				}
			}
		}
		model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.DEPT_MAP, deptMap);
		populateModel(model, bean, FormMode.CREATE);
		model.addAttribute(MAIN_ENTITY_NAME, bean);
		result = JSP_FORM;
		return result;
	}

	@RequestMapping(params = "getAllBudgetHeadDesc", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> getAllBudgetHeadDesc(final ReappropriationOfBudgetAuthorizationDTO bean,
			final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest request,
			final BindingResult bindingResult, @RequestParam("cnt") final int cnt) {
		log("ReappropriationOfBudgetAuthorizationController-'getAllBudgetHeadDesc' : 'get getAllBudgetHeadDesc data'");
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
				PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		final Map<String, String> budgetHeadMap = new LinkedHashMap<>();
		if (bean.getFaYearid() != null) {
			final Long faYearid = bean.getFaYearid();
			final Long cpdBugtypeid = bean.getCpdBugtypeId();
			final Long cpdBugsubtypeId = bean.getCpdBugSubTypeId();
			final Long dpDeptid = bean.getBugprojRevBeanList1().get(cnt).getDpDeptid();
			if (dpDeptid != null) {
				if (revenueLookup.getLookUpId() == cpdBugtypeid) {
					final List<Object[]> prBudgetCodeids = accountBudgetProjectedRevenueEntryService
							.findBudgetCodeIdFromBudgetProjectedRevenueEntry(faYearid, cpdBugsubtypeId, dpDeptid,
									orgId);
					if (!prBudgetCodeids.isEmpty()) {
						for (final Object objects : prBudgetCodeids) {
							final Long prBudgetCodeid = Long.valueOf(objects.toString());
							budgetHeadMap.putAll(accountBudgetCodeService.getAllBudgetCodes(prBudgetCodeid, orgId));
						}
					}
				}
			}
		}
		return budgetHeadMap;
	}

	@RequestMapping(params = "getAllBudgetHeadExpDesc", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> getAllBudgetHeadExpDesc(final ReappropriationOfBudgetAuthorizationDTO bean,
			final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest request,
			final BindingResult bindingResult, @RequestParam("cont") final int cont) {
		log("ReappropriationOfBudgetAuthorizationController-'getAllBudgetHeadExpDesc' : 'get getAllBudgetHeadExpDesc data'");
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
				PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		final Map<String, String> budgetHeadExpMap = new LinkedHashMap<>();
		if (bean.getFaYearid() != null) {
			final Long faYearid = bean.getFaYearid();
			final Long cpdBugtypeid = bean.getCpdBugtypeId();
			final Long cpdBugsubtypeId = bean.getCpdBugSubTypeId();
			final Long dpDeptid = bean.getBugprojExpBeanList1().get(cont).getDpDeptid();
			if (dpDeptid != null) {
				if (expLookup.getLookUpId() == cpdBugtypeid) {
					final List<Object[]> prBudgetCodeids = accountBudgetProjectedExpenditureService
							.findBudgetProjExpBudgetCodes(faYearid, cpdBugsubtypeId, dpDeptid, orgId);
					if (!prBudgetCodeids.isEmpty()) {
						for (final Object objects : prBudgetCodeids) {
							final Long prBudgetCodeid = Long.valueOf(objects.toString());
							budgetHeadExpMap.putAll(accountBudgetCodeService.getAllBudgetCodes(prBudgetCodeid, orgId));
						}
					}
				}
			}
		}
		return budgetHeadExpMap;
	}

	@RequestMapping(params = "getAllBudgetHeadDetails", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> getAllBudgetHeadDetails(final ReappropriationOfBudgetAuthorizationDTO bean,
			final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest request,
			final BindingResult bindingResult) {
		log("ReappropriationOfBudgetAuthorizationController-'getAllBudgetHeadDetails' : 'get getAllBudgetHead Details'");
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
				PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		final Map<String, String> budgetHeadDescMap = new LinkedHashMap<>();
		if (bean.getFaYearid() != null) {
			final Long faYearid = bean.getFaYearid();
			final Long cpdBugtypeid = bean.getCpdBugtypeId();
			final Long cpdBugsubtypeId = bean.getCpdBugSubTypeId();
			final Long dpDeptid = bean.getBugprojRevBeanList().get(0).getDpDeptid();
			if (dpDeptid != null) {
				if (revenueLookup.getLookUpId() == cpdBugtypeid) {
					final List<Object[]> prBudgetCodeids = accountBudgetProjectedRevenueEntryService
							.findBudgetCodeIdFromBudgetProjectedRevenueEntry(faYearid, cpdBugsubtypeId, dpDeptid,
									orgId);
					if (!prBudgetCodeids.isEmpty()) {
						for (final Object objects : prBudgetCodeids) {
							final Long prBudgetCodeid = Long.valueOf(objects.toString());
							budgetHeadDescMap.putAll(accountBudgetCodeService.getAllBudgetCodes(prBudgetCodeid, orgId));
						}
					}
				}
			}
		}
		return budgetHeadDescMap;
	}

	@RequestMapping(params = "getAllBudgetHeadExpDetails", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> getAllBudgetHeadExpDetails(
			final ReappropriationOfBudgetAuthorizationDTO bean, final Model model,
			final RedirectAttributes redirectAttributes, final HttpServletRequest request,
			final BindingResult bindingResult) {
		log("ReappropriationOfBudgetAuthorizationController-'getAllBudgetHeadExpDetails' : 'get getAllBudgetHeadExp Details'");
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
				PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		final Map<String, String> budgetHeadExpDescMap = new LinkedHashMap<>();
		if (bean.getFaYearid() != null) {
			final Long faYearid = bean.getFaYearid();
			final Long cpdBugtypeid = bean.getCpdBugtypeId();
			final Long cpdBugsubtypeId = bean.getCpdBugSubTypeId();
			final Long dpDeptid = bean.getBugprojExpBeanList().get(0).getDpDeptid();
			if (dpDeptid != null) {
				if (expLookup.getLookUpId() == cpdBugtypeid) {
					final List<Object[]> prBudgetCodeids = accountBudgetProjectedExpenditureService
							.findBudgetProjExpBudgetCodes(faYearid, cpdBugsubtypeId, dpDeptid, orgId);
					if (!prBudgetCodeids.isEmpty()) {
						for (final Object objects : prBudgetCodeids) {
							final Long prBudgetCodeid = Long.valueOf(objects.toString());
							budgetHeadExpDescMap
									.putAll(accountBudgetCodeService.getAllBudgetCodes(prBudgetCodeid, orgId));
						}
					}
				}
			}
		}
		return budgetHeadExpDescMap;
	}

	@RequestMapping(params = "getOrgBalGridload", method = RequestMethod.POST)
	public @ResponseBody List<String> findBudgetReappropriationAmountData(
			final ReappropriationOfBudgetAuthorizationDTO bean, final HttpServletRequest request, final Model model) {
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final Long faYearid = bean.getFaYearid().longValue();
		final String budgCodeid = bean.getBugprojRevBeanList().get(0).getPrRevBudgetCode();
		final List<String> arrRevList = new ArrayList<>();
		final List<Object[]> OrgEsmtAmount = accountBudgetProjectedRevenueEntryService.findByRenueOrgAmount(faYearid,
				Long.valueOf(budgCodeid), orgId);
		if (!OrgEsmtAmount.isEmpty()) {
			for (final Object[] objects : OrgEsmtAmount) {
				if (objects[0] == null) {
					BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
					originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
					arrRevList.add(originalEstAmount.toString());
					if (objects[2] == null) {
						arrRevList.add(originalEstAmount.toString());
					} else {
						BigDecimal revAmt = new BigDecimal(objects[2].toString());
						revAmt = revAmt.setScale(2, RoundingMode.CEILING);
						arrRevList.add(originalEstAmount.subtract(revAmt).toString());
					}
				}
				if (objects[0] != null) {
					BigDecimal revisedEsmtAmount = new BigDecimal(objects[0].toString());
					revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);
					arrRevList.add(revisedEsmtAmount.toString());
					if (objects[2] == null) {
						arrRevList.add(revisedEsmtAmount.toString());
					} else {
						BigDecimal revAmt = new BigDecimal(objects[2].toString());
						revAmt = revAmt.setScale(2, RoundingMode.CEILING);
						arrRevList.add(revisedEsmtAmount.subtract(revAmt).toString());
					}
				}
			}
		}
		return arrRevList;
	}

	@RequestMapping(params = "getOrgBalGridload1", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> findBudgetReappropriationAmountData1(
			final ReappropriationOfBudgetAuthorizationDTO bean, final HttpServletRequest request, final Model model,
			@RequestParam("cnt") final int cnt) {
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final Long faYearid = bean.getFaYearid().longValue();
		final String budgCodeid = bean.getBugprojRevBeanList1().get(cnt).getPrRevBudgetCode()
				.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK);
		final Map<String, String> opBalMap1 = new HashMap<>();
		final List<Object[]> OrgEsmtAmount = accountBudgetProjectedRevenueEntryService.findByRenueOrgAmount(faYearid,
				Long.valueOf(budgCodeid), orgId);
		if (!OrgEsmtAmount.isEmpty()) {
			for (final Object[] objects : OrgEsmtAmount) {
				if (objects[0] == null) {
					BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
					originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
					opBalMap1.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_REV_ORIGINAL_ESTIMATE,
							originalEstAmount.toString());
					if (objects[2] == null) {
						opBalMap1.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_REV_BALANCE_PROVISION,
								originalEstAmount.toString());
					} else {
						BigDecimal revAmt = new BigDecimal(objects[2].toString());
						revAmt = revAmt.setScale(2, RoundingMode.CEILING);
						opBalMap1.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_REV_BALANCE_PROVISION,
								originalEstAmount.subtract(revAmt).toString());
					}
				}
				if (objects[0] != null) {
					BigDecimal revisedEsmtAmount = new BigDecimal(objects[0].toString());
					revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);
					opBalMap1.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_REV_ORIGINAL_ESTIMATE,
							revisedEsmtAmount.toString());
					if (objects[2] == null) {
						opBalMap1.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_REV_BALANCE_PROVISION,
								revisedEsmtAmount.toString());
					} else {
						BigDecimal revAmt = new BigDecimal(objects[2].toString());
						revAmt = revAmt.setScale(2, RoundingMode.CEILING);
						opBalMap1.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_REV_BALANCE_PROVISION,
								revisedEsmtAmount.subtract(revAmt).toString());
					}
				}
			}
		}
		return opBalMap1;
	}

	@RequestMapping(params = "getOrgBalExpGridload", method = RequestMethod.POST)
	public @ResponseBody List<String> findBudgetReappropriationExpAmountData(
			final ReappropriationOfBudgetAuthorizationDTO bean, final HttpServletRequest request, final Model model) {

		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final Long faYearid = bean.getFaYearid();
		final String budgCodeid = bean.getBugprojExpBeanList().get(0).getPrExpBudgetCode();
		final List<String> arrExpList = new ArrayList<>();
		final List<Object[]> OrgEsmtAmount = accountBudgetProjectedExpenditureService.findByExpOrgAmount(faYearid,
				Long.valueOf(budgCodeid), orgId);
		if (!OrgEsmtAmount.isEmpty()) {
			for (final Object[] objects : OrgEsmtAmount) {
				if (objects[0] == null) {
					BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
					originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
					arrExpList.add(originalEstAmount.toString());
					if (objects[2] == null) {
						arrExpList.add(originalEstAmount.toString());
					} else {
						BigDecimal expAmt = new BigDecimal(objects[2].toString());
						expAmt = expAmt.setScale(2, RoundingMode.CEILING);
						arrExpList.add(originalEstAmount.subtract(expAmt).toString());
					}
				} else {
					BigDecimal revEsmtAmt = new BigDecimal(objects[0].toString());
					revEsmtAmt = revEsmtAmt.setScale(2, RoundingMode.CEILING);
					arrExpList.add(revEsmtAmt.toString());
					if (objects[2] == null) {
						arrExpList.add(revEsmtAmt.toString());
					} else {
						BigDecimal expAmt = new BigDecimal(objects[2].toString());
						expAmt = expAmt.setScale(2, RoundingMode.CEILING);
						arrExpList.add(revEsmtAmt.subtract(expAmt).toString());
					}
				}
			}
		}
		return arrExpList;
	}

	@RequestMapping(params = "getOrgBalExpGridload1", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> findBudgetReappropriationExpAmountData1(
			final ReappropriationOfBudgetAuthorizationDTO bean, final HttpServletRequest request, final Model model,
			@RequestParam("cont") final int cont) {

		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final Long faYearid = bean.getFaYearid().longValue();
		final String budgCodeid = bean.getBugprojExpBeanList1().get(cont).getPrExpBudgetCode()
				.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK);
		final Map<String, String> opBalMap3 = new HashMap<>();
		final List<Object[]> OrgEsmtAmount = accountBudgetProjectedExpenditureService.findByExpOrgAmount(faYearid,
				Long.valueOf(budgCodeid), orgId);
		if (!OrgEsmtAmount.isEmpty()) {
			for (final Object[] objects : OrgEsmtAmount) {
				if (objects[0] == null) {
					BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
					originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
					opBalMap3.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_EXP_ORIGINAL_ESTIMATE,
							originalEstAmount.toString());
					if (objects[2] == null) {
						opBalMap3.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_EXP_BALANCE_PROVISION,
								originalEstAmount.toString());
					} else {
						BigDecimal expAmt = new BigDecimal(objects[2].toString());
						expAmt = expAmt.setScale(2, RoundingMode.CEILING);
						opBalMap3.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_EXP_BALANCE_PROVISION,
								originalEstAmount.subtract(expAmt).toString());
					}
				} else {
					BigDecimal revEsmtAmt = new BigDecimal(objects[0].toString());
					revEsmtAmt = revEsmtAmt.setScale(2, RoundingMode.CEILING);
					opBalMap3.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_EXP_ORIGINAL_ESTIMATE,
							revEsmtAmt.toString());
					if (objects[2] == null) {
						opBalMap3.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_EXP_BALANCE_PROVISION,
								revEsmtAmt.toString());
					} else {
						BigDecimal expAmt = new BigDecimal(objects[2].toString());
						expAmt = expAmt.setScale(2, RoundingMode.CEILING);
						opBalMap3.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_EXP_BALANCE_PROVISION,
								revEsmtAmt.subtract(expAmt).toString());
					}
				}
			}
		}
		return opBalMap3;
	}

	@RequestMapping(params = "getReappRevPrimarykeyIdDetails", method = RequestMethod.POST)
	public @ResponseBody Map<Long, String> findBudgetReappRevenueAllocationPrimaryIdData(
			final ReappropriationOfBudgetAuthorizationDTO bean, final HttpServletRequest request, final Model model) {

		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final Long faYearid = bean.getFaYearid();
		final String prRevBudgetCode = bean.getBugprojRevBeanList().get(0).getPrRevBudgetCode()
				.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK);
		final List<Object[]> primarykeyIdDetails = accountBudgetProjectedRevenueEntryService
				.findByRenueOrgAmount(faYearid, Long.valueOf(prRevBudgetCode), orgId);
		final Map<Long, String> revBalMap = new HashMap<>();
		if (!primarykeyIdDetails.isEmpty()) {
			for (final Object[] objects : primarykeyIdDetails) {
				if (objects[3] != null) {
					final Long PrimaryKeyId = Long.valueOf(objects[3].toString());
					revBalMap.put(PrimaryKeyId, objects[3].toString());
				}
			}
		}
		return revBalMap;
	}

	@RequestMapping(params = "getReappDynamicRevPrimarykeyIdDetails", method = RequestMethod.POST)
	public @ResponseBody Map<Long, String> findBudgetReappDynamicRevenueAllocationPrimaryIdData(
			final ReappropriationOfBudgetAuthorizationDTO bean, final HttpServletRequest request, final Model model,
			@RequestParam("cnt") final int cnt) {

		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final Long faYearid = bean.getFaYearid();
		final String prRevBudgetCode = bean.getBugprojRevBeanList1().get(cnt).getPrRevBudgetCode()
				.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK);
		final List<Object[]> primarykeyIdDetails = accountBudgetProjectedRevenueEntryService
				.findByRenueOrgAmount(faYearid, Long.valueOf(prRevBudgetCode), orgId);
		final Map<Long, String> revBalMap = new HashMap<>();
		if (!primarykeyIdDetails.isEmpty()) {
			for (final Object[] objects : primarykeyIdDetails) {
				if (objects[3] != null) {
					final Long PrimaryKeyId = Long.valueOf(objects[3].toString());
					revBalMap.put(PrimaryKeyId, objects[3].toString());
				}
			}
		}
		return revBalMap;
	}

	@RequestMapping(params = "getReappExpPrimarykeyIdDetails", method = RequestMethod.POST)
	public @ResponseBody Map<Long, String> findBudgetReappExpenditureAllocationPrimaryIdData(
			final ReappropriationOfBudgetAuthorizationDTO bean, final HttpServletRequest request, final Model model) {
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final Long faYearid = bean.getFaYearid();
		final String prExpBudgetCode = bean.getBugprojExpBeanList().get(0).getPrExpBudgetCode()
				.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK);
		final Long deptId=bean.getBugprojExpBeanList().get(0).getDpDeptid();
		final List<Object[]> primarykeyIdDetails = accountBudgetProjectedExpenditureService.findByExpOrgAmount(faYearid,
				Long.valueOf(prExpBudgetCode), orgId);
		final Map<Long, String> expBalMap = new HashMap<>();
		if (!primarykeyIdDetails.isEmpty()) {
			for (final Object[] objects : primarykeyIdDetails) {
				if (objects[3] != null && deptId==objects[4]) {
					final Long PrimaryKeyId = Long.valueOf(objects[3].toString());
					expBalMap.put(PrimaryKeyId, objects[3].toString());
				}
			}
		}
		return expBalMap;
	}

	@RequestMapping(params = "getReappDynamicExpPrimarykeyIdDetails", method = RequestMethod.POST)
	public @ResponseBody Map<Long, String> findBudgetReappDynamicExpenditureAllocationPrimaryIdData(
			final ReappropriationOfBudgetAuthorizationDTO bean, final HttpServletRequest request, final Model model,
			@RequestParam("cont") final int cont) {
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final Long faYearid = bean.getFaYearid();
		final String prExpBudgetCode = bean.getBugprojExpBeanList1().get(cont).getPrExpBudgetCode()
				.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK);
		final List<Object[]> primarykeyIdDetails = accountBudgetProjectedExpenditureService.findByExpOrgAmount(faYearid,
				Long.valueOf(prExpBudgetCode), orgId);
		final Map<Long, String> expBalMap = new HashMap<>();
		if (!primarykeyIdDetails.isEmpty()) {
			for (final Object[] objects : primarykeyIdDetails) {
				if (objects[3] != null) {
					final Long PrimaryKeyId = Long.valueOf(objects[3].toString());
					expBalMap.put(PrimaryKeyId, objects[3].toString());
				}
			}
		}
		return expBalMap;
	}

	@RequestMapping()
	public String getList(final Model model) {
		log("ReappropriationOfBudgetAuthorization-'list' :'list'");
		String result = MainetConstants.CommonConstant.BLANK;
		chList = new ArrayList<>();
		chList.clear();
		String amount = null;
		for (final ReappropriationOfBudgetAuthorizationDTO bean : chList) {
			if (bean.getTransferAmount() != null) {
				amount = CommonMasterUtility.getAmountInIndianCurrency(bean.getTransferAmount());
				bean.setFormattedCurrency(amount);
			}
			final String tempDate = CommonUtility.dateToString(bean.getPaEntrydate());
			bean.setTempDate(tempDate);
			if ((bean.getAuthFlag() != null) && !bean.getAuthFlag().isEmpty()) {
				final String status = bean.getAuthFlag();
				if (status.equals(MainetConstants.MENU.Y)) {
					bean.setStatus(MainetConstants.AccountBudgetAdditionalSupplemental.APPROVED);
				}
				if (status.equals(MainetConstants.MENU.N)) {
					bean.setStatus(MainetConstants.AccountBudgetAdditionalSupplemental.UNAPPROVED);
				}
			}

		}
		final ReappropriationOfBudgetAuthorizationDTO bean = new ReappropriationOfBudgetAuthorizationDTO();
		populateModel(model, bean, FormMode.CREATE);
		isWorkflowDefine(model,bean);
		model.addAttribute(MAIN_ENTITY_NAME, bean);
		model.addAttribute(MAIN_LIST_NAME, chList);
		result = JSP_LIST;
		return result;
	}

	@RequestMapping(params = "form")
	public String formForCreate(final Model model) {
		log("ReappropriationOfBudgetAuthorization-'formForCreate' : 'formForCreate'");
		String result = MainetConstants.CommonConstant.BLANK;
		final ReappropriationOfBudgetAuthorizationDTO bean = new ReappropriationOfBudgetAuthorizationDTO();
		bean.setTempDate(Utility.dateToString(new Date()));
		populateModel(model, bean, FormMode.CREATE);
		result = JSP_FORM;
		return result;
	}

	private void populateModel(final Model model, final ReappropriationOfBudgetAuthorizationDTO dto,
			final FormMode formMode) {
		log("ReappropriationOfBudgetAuthorization-'populateModel' : populate model");
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		final List<LookUp> bugTypelevelMap = CommonMasterUtility.getListLookup(PrefixConstants.PREFIX,
				UserSession.getCurrent().getOrganisation());
		if (bugTypelevelMap != null) {
			for (final LookUp lookUp : bugTypelevelMap) {
				if (lookUp != null) {
					if (lookUp.getLookUpCode().equals(PrefixConstants.REV_CPD_VALUE)
							|| lookUp.getLookUpCode().equals(PrefixConstants.EXP_CPD_VALUE)) {
						model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.LEVELS_MAP, bugTypelevelMap);
						model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.BUDGET_TYPE_STATUS,
								MainetConstants.MASTER.Y);
					}
				}
			}
		}

		final List<LookUp> bugSubTypelevelMap = CommonMasterUtility.getListLookup(PrefixConstants.BUG_SUB_PREFIX,
				UserSession.getCurrent().getOrganisation());
		if (bugSubTypelevelMap != null) {
			for (final LookUp lookUp : bugSubTypelevelMap) {
				if (lookUp != null) {
					if (lookUp.getLookUpCode().equals(PrefixConstants.REV_SUB_CPD_VALUE)
							|| lookUp.getLookUpCode().equals(PrefixConstants.EXP_SUB_CPD_VALUE)) {
						model.addAttribute(MainetConstants.BUDGET_ADDITIONALSUPPLEMENTAL.BUG_SUB_TYPE_LEVEL_MAP,
								bugSubTypelevelMap);
						model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.BUDGET_SUBTYPE_STATUS,
								MainetConstants.MASTER.Y);
					}
				}
			}
		}

		/* Defect #96460 */
		final Organisation organisation = UserSession.getCurrent().getOrganisation();
		String defaultFieldFlag = MainetConstants.CommonConstant.BLANK;
		String defaultFunctionFlag = MainetConstants.CommonConstant.BLANK;
		final Organisation superUserOrganization = ApplicationSession.getInstance().getSuperUserOrganization();
		// final int langId = UserSession.getCurrent().getLanguageId();

		final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
		Long defaultOrgId = null;
		if (isDafaultOrgExist) {
			defaultOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
		} else {
			defaultOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		}

		final List<TbAcCodingstructureMas> tbCodingList = tbAcCodingstructureMasService.findAllWithOrgId(defaultOrgId);

		final List<LookUp> fundTypeLevel = CommonMasterUtility.getListLookup(
				MainetConstants.BUDGET_CODE.FUND_FIELD_DEPT_STATUS_PREFIX, UserSession.getCurrent().getOrganisation());
		for (final LookUp lookUp : fundTypeLevel) {
			if (MainetConstants.BUDGET_CODE.FIELD_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
				if (formMode.equals(FormMode.CREATE) || formMode.equals(FormMode.UPDATE)) {
					final LookUp cmdFieldPrefix = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
							PrefixConstants.FIELD_CPD_VALUE, PrefixConstants.CMD,
							UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
					if ((tbCodingList != null) && !tbCodingList.isEmpty()) {
						for (final TbAcCodingstructureMas master : tbCodingList) {
							if (cmdFieldPrefix.getLookUpId() == master.getComCpdId()) {
								defaultFieldFlag = master.getDefineOnflag();
								if (defaultFieldFlag.equals(MainetConstants.MASTER.Y) && isDafaultOrgExist) {
									model.addAttribute(
											MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
											tbAcFieldMasterService.getFieldMasterStatusLastLevels(defaultOrgId,
													superUserOrganization, langId));
								} else {
									model.addAttribute(
											MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
											tbAcFieldMasterService.getFieldMasterStatusLastLevels(orgId, organisation,
													langId));
								}
							}
						}
					}
				}
			}
		}
		model.addAttribute(MainetConstants.FIELD_MASTER.FIELD_STATUS, MainetConstants.MASTER.Y);

		final Map<Long, String> deptMap = new HashMap<>(0);
		List<Object[]> department = null;
		department = departmentService.getAllDeptTypeNames();
		for (final Object[] dep : department) {
			if (dep[0] != null) {
				deptMap.put((Long) (dep[0]), (String) dep[1]);
			}
		}
		// model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.DEPT_MAP,
		// deptMap);
		model.addAttribute(MainetConstants.AccountBudgetReappropriationMaster.DEPT_MAP_DATA, deptMap);
		model.addAttribute(MAIN_ENTITY_NAME, dto);

		final List<LookUp> activeDeActiveMap = CommonMasterUtility
				.getListLookup(MainetConstants.BUDGET_CODE.STATUS_FLAG, UserSession.getCurrent().getOrganisation());
		model.addAttribute(MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_MAP, activeDeActiveMap);

		model.addAttribute(AC_Financialyr_LOOKUPS, secondaryheadMasterService.getAllFinincialYear(orgId, langId));

		if (formMode == FormMode.CREATE) {

			final Map<Long, String> financeMap = tbFinancialyearService.getAllFinincialYearByStatusWise(orgId);
			model.addAttribute(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.FINANCE_MAP, financeMap);

			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE,
					MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE_CREATE);
			model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, false);
		} else if (formMode == FormMode.UPDATE) {

			final Map<Long, String> financeMap = tbFinancialyearService.getAllFinincialYear();
			model.addAttribute(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.FINANCE_MAP, financeMap);

			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE,
					MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE_UPDATE);
		}
	}

	@RequestMapping(params = "create", method = RequestMethod.POST)
	public String create(final ReappropriationOfBudgetAuthorizationDTO tbAcBudgetReappOfAuthorization,
			final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
			final HttpServletRequest httpServletRequest) throws ParseException {
		log("ReappropriationOfBudgetAuthorizationDTO-'create' : 'create'");
		String result = MainetConstants.CommonConstant.BLANK;
		if (!bindingResult.hasErrors()) {
			final ApplicationSession session = ApplicationSession.getInstance();
			tbAcBudgetReappOfAuthorization.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
			final UserSession userSession = UserSession.getCurrent();
			final int LanguageId = userSession.getLanguageId();
			final Organisation Organisation = UserSession.getCurrent().getOrganisation();

			Long workFlowLevel1 = null;
			Long workFlowLevel2 = null;
			Long workFlowLevel3 = null;
			Long workFlowLevel4 = null;
			Long workFlowLevel5 = null;
			if (UserSession.getCurrent().getLoggedLocId() != null) {
				final TbLocationMas locMas = locMasService.findById(UserSession.getCurrent().getLoggedLocId());
				// In workflow, events wise Location Operation WZMapping is required.
				if ((locMas.getLocOperationWZMappingDto() != null) && !locMas.getLocOperationWZMappingDto().isEmpty()) {
					workFlowLevel1 = locMas.getLocOperationWZMappingDto().get(0).getCodIdOperLevel1();
					workFlowLevel2 = locMas.getLocOperationWZMappingDto().get(0).getCodIdOperLevel2();
					workFlowLevel3 = locMas.getLocOperationWZMappingDto().get(0).getCodIdOperLevel3();
					workFlowLevel4 = locMas.getLocOperationWZMappingDto().get(0).getCodIdOperLevel4();
					workFlowLevel5 = locMas.getLocOperationWZMappingDto().get(0).getCodIdOperLevel5();
				}
			}
			tbAcBudgetReappOfAuthorization.setWorkFlowLevel1(workFlowLevel1);
			tbAcBudgetReappOfAuthorization.setWorkFlowLevel2(workFlowLevel2);
			tbAcBudgetReappOfAuthorization.setWorkFlowLevel3(workFlowLevel3);
			tbAcBudgetReappOfAuthorization.setWorkFlowLevel4(workFlowLevel4);
			tbAcBudgetReappOfAuthorization.setWorkFlowLevel5(workFlowLevel5);

			if (tbAcBudgetReappOfAuthorization.getPaAdjid() == null) {
				tbAcBudgetReappOfAuthorization.setLangId((long) userSession.getLanguageId());
				tbAcBudgetReappOfAuthorization.setUserId(userSession.getEmployee().getEmpId());
				tbAcBudgetReappOfAuthorization.setLmoddate(new Date());
				tbAcBudgetReappOfAuthorization.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
			} else {
				tbAcBudgetReappOfAuthorization.setUpdatedBy(userSession.getEmployee().getEmpId());
				tbAcBudgetReappOfAuthorization.setApprovedBy(userSession.getEmployee().getEmpId());
				tbAcBudgetReappOfAuthorization.setUpdatedDate(new Date());
				tbAcBudgetReappOfAuthorization.setLgIpMacUpd(Utility.getClientIpAddress(httpServletRequest));
				if (tbAcBudgetReappOfAuthorization.getCreatedDate() != null
						&& !tbAcBudgetReappOfAuthorization.getCreatedDate().isEmpty()) {
					tbAcBudgetReappOfAuthorization
							.setLmoddate(Utility.stringToDate(tbAcBudgetReappOfAuthorization.getCreatedDate()));
				}
			}
			tbAcBudgetReappOfAuthorization.setOrgid(userSession.getOrganisation().getOrgid());
			populateModel(model, tbAcBudgetReappOfAuthorization, FormMode.CREATE);
			ReappropriationOfBudgetAuthorizationDTO tbAcBudgetReappOfAuthorizationCreated = reappropriationOfBudgetAuthorizationService
					.saveBudgetReappAuthorizationFormData(tbAcBudgetReappOfAuthorization, LanguageId, Organisation);
			reappropriationOfBudgetAuthorizationService.forUpdateRevisedEstmtDataInBudgetExpRevTable(
					tbAcBudgetReappOfAuthorizationCreated, LanguageId, Organisation);
			if (tbAcBudgetReappOfAuthorizationCreated == null) {
				tbAcBudgetReappOfAuthorizationCreated = new ReappropriationOfBudgetAuthorizationDTO();
			}
			model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetReappOfAuthorizationCreated);
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
			if (tbAcBudgetReappOfAuthorization.getPaAdjid() == null) {
				model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
						session.getMessage("accounts.fieldmaster.success"));
			}
			if (tbAcBudgetReappOfAuthorization.getPaAdjid() != null) {
				String msg = null;
				if (tbAcBudgetReappOfAuthorization.getApproved().equals("N"))
					msg = session.getMessage("accounts.reappropriation.budget.send.back");
				else
					msg = session.getMessage("accounts.reappropriation.budget.auth");

				model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
						msg + " " + tbAcBudgetReappOfAuthorization.getBudgetTranRefNo());
			}
			result = JSP_FORM;
		} else {
			tbAcBudgetReappOfAuthorization.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);
			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
			model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
			populateModel(model, tbAcBudgetReappOfAuthorization, FormMode.CREATE);
			result = JSP_FORM;
		}
		return result;
	}

	@RequestMapping(params = "update", method = RequestMethod.POST)
	public String update(ReappropriationOfBudgetAuthorizationDTO tbAcBudgetReappOfAuthorization,
			@RequestParam("paAdjid") final Long paAdjid,
			@RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
			final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
			final HttpServletRequest httpServletRequest) {
		log("tbAcBudgetReappOfAuthorization-'gridData' : 'update'");
		String result = MainetConstants.CommonConstant.BLANK;
		final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
				PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
				PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		if (!bindingResult.hasErrors()) {
			if (viewmode.equals(MainetConstants.EDIT)) {
				model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.EDIT);
			} else {
				model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
			}
			setModeView(viewmode);
			final UserSession userSession = UserSession.getCurrent();
			final int LanguageId = userSession.getLanguageId();
			final Organisation Organisation = UserSession.getCurrent().getOrganisation();
			tbAcBudgetReappOfAuthorization.setPaAdjid(paAdjid);
			tbAcBudgetReappOfAuthorization = reappropriationOfBudgetAuthorizationService
					.getDetailsUsingBudgetReappAuthorizationId(tbAcBudgetReappOfAuthorization, LanguageId,
							Organisation);
			if (tbAcBudgetReappOfAuthorization.getFaYearid() != null) {
				tbAcBudgetReappOfAuthorization.setFinancialYearDesc(
						tbFinancialyearService.findFinancialYearDesc(tbAcBudgetReappOfAuthorization.getFaYearid()));
			}
			final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			if (tbAcBudgetReappOfAuthorization.getFaYearid() != null) {
				final Long cpdBugtypeid = tbAcBudgetReappOfAuthorization.getCpdBugtypeId();

				final List<AccountBudgetProjectedRevenueEntryBean> revBeanDynamic = tbAcBudgetReappOfAuthorization
						.getBugprojRevBeanList1();
				for (final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean : revBeanDynamic) {
					accountBudgetProjectedRevenueEntryBean.getDpDeptid();
					if (revenueLookup.getLookUpId() == cpdBugtypeid) {
						Map<Long, String> budgetCode = new LinkedHashMap<>();
						budgetCode = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeads(orgId);
						accountBudgetProjectedRevenueEntryBean.setBudgetMapDynamic(budgetCode);
					}
				}
				final List<AccountBudgetProjectedRevenueEntryBean> revBean = tbAcBudgetReappOfAuthorization
						.getBugprojRevBeanList();
				for (final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean : revBean) {
					if (revenueLookup.getLookUpId() == cpdBugtypeid) {
						Map<Long, String> budgetCode = new LinkedHashMap<>();
						budgetCode = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeads(orgId);
						model.addAttribute(MainetConstants.AccountBudgetReappropriationMaster.BUDGET_CODE_MAP,
								budgetCode);
					}
				}
				final List<AccountBudgetProjectedExpenditureBean> expBeanDynamic = tbAcBudgetReappOfAuthorization
						.getBugprojExpBeanList1();
				for (final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean : expBeanDynamic) {
					if (expLookup.getLookUpId() == cpdBugtypeid) {
						Map<Long, String> budgetCodeExp = new LinkedHashMap<>();
						budgetCodeExp = accountBudgetProjectedExpenditureService.findByAllExpBudgetHeads(orgId);
						accountBudgetProjectedExpenditureBean.setBudgetMapDynamicExp(budgetCodeExp);
					}
				}
				final List<AccountBudgetProjectedExpenditureBean> expBean = tbAcBudgetReappOfAuthorization
						.getBugprojExpBeanList();
				for (final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean : expBean) {
					if (expLookup.getLookUpId() == cpdBugtypeid) {
						Map<Long, String> budgetCodeExpStatic = new LinkedHashMap<>();
						budgetCodeExpStatic = accountBudgetProjectedExpenditureService.findByAllExpBudgetHeads(orgId);
						model.addAttribute(MainetConstants.AccountBudgetReappropriationMaster.BUDGET_EXP_MAP,
								budgetCodeExpStatic);
					}
				}
			}
			model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetReappOfAuthorization);
			loadBudgetReappropriationData(tbAcBudgetReappOfAuthorization, model, redirectAttributes, httpServletRequest,
					bindingResult);
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
			log("tbAcBudgetReappOfAuthorization 'update' : view done - redirect");
			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
			log("tbAcBudgetReappOfAuthorization 'update' : view done - redirect");
			result = JSP_FORM;
		} else {
			log("tbAcBudgetReappOfAuthorization 'update' : binding errors");
			populateModel(model, tbAcBudgetReappOfAuthorization, FormMode.UPDATE);
			result = JSP_FORM;
		}
		return result;
	}

	@RequestMapping(params = "formForView", method = RequestMethod.POST)
	public String formForView(ReappropriationOfBudgetAuthorizationDTO tbAcBudgetReappOfAuthorization,
			@RequestParam("paAdjid") final Long paAdjid,
			@RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
			final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
			final HttpServletRequest httpServletRequest) {
		log("tbAcBudgetReappOfAuthorization-'gridData' : 'view'");
		String result = MainetConstants.CommonConstant.BLANK;
		final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
				PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
				PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		if (!bindingResult.hasErrors()) {
			if (viewmode.equals(MainetConstants.VIEW)) {
				model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.VIEW);
			} else {
				model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
			}
			setModeView(viewmode);
			final UserSession userSession = UserSession.getCurrent();
			final int LanguageId = userSession.getLanguageId();
			final Organisation Organisation = UserSession.getCurrent().getOrganisation();
			tbAcBudgetReappOfAuthorization.setPaAdjid(paAdjid);
			tbAcBudgetReappOfAuthorization = reappropriationOfBudgetAuthorizationService
					.getDetailsUsingBudgetReappAuthorizationId(tbAcBudgetReappOfAuthorization, LanguageId,
							Organisation);
			if (tbAcBudgetReappOfAuthorization.getFaYearid() != null) {
				tbAcBudgetReappOfAuthorization.setFinancialYearDesc(
						tbFinancialyearService.findFinancialYearDesc(tbAcBudgetReappOfAuthorization.getFaYearid()));
			}
			final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			if (tbAcBudgetReappOfAuthorization.getFaYearid() != null) {
				final Long cpdBugtypeid = tbAcBudgetReappOfAuthorization.getCpdBugtypeId();

				final List<AccountBudgetProjectedRevenueEntryBean> revBeanDynamic = tbAcBudgetReappOfAuthorization
						.getBugprojRevBeanList1();
				Map<Long, String> budgetCode = new LinkedHashMap<>();
				for (final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean : revBeanDynamic) {
					if (revenueLookup.getLookUpId() == cpdBugtypeid) {
						budgetCode = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeads(orgId);
					}
				}
				model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.REV_BUDGET_CODE_MAP, budgetCode);
				final List<AccountBudgetProjectedRevenueEntryBean> revBean = tbAcBudgetReappOfAuthorization
						.getBugprojRevBeanList();
				for (final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean : revBean) {
					Map<Long, String> budgetCodeStatic = new LinkedHashMap<>();
					if (revenueLookup.getLookUpId() == cpdBugtypeid) {
						budgetCodeStatic = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeads(orgId);
					}
					model.addAttribute(MainetConstants.AccountBudgetReappropriationMaster.BUDGET_CODE_MAP,
							budgetCodeStatic);
				}
				Map<Long, String> budgetCodeExp = new LinkedHashMap<>();
				final List<AccountBudgetProjectedExpenditureBean> expBeanDynamic = tbAcBudgetReappOfAuthorization
						.getBugprojExpBeanList1();
				for (final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean : expBeanDynamic) {
					if (expLookup.getLookUpId() == cpdBugtypeid) {
						budgetCodeExp = accountBudgetProjectedExpenditureService.findByAllExpBudgetHeads(orgId);
					}
				}
				model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.EXP_BUDGET_CODE_MAP, budgetCodeExp);
				final List<AccountBudgetProjectedExpenditureBean> expBean = tbAcBudgetReappOfAuthorization
						.getBugprojExpBeanList();
				for (final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean : expBean) {
					Map<Long, String> budgetCodeExpStatic = new LinkedHashMap<>();
					if (expLookup.getLookUpId() == cpdBugtypeid) {
						budgetCodeExpStatic = accountBudgetProjectedExpenditureService.findByAllExpBudgetHeads(orgId);
					}
					model.addAttribute(MainetConstants.AccountBudgetReappropriationMaster.BUDGET_EXP_MAP,
							budgetCodeExpStatic);
				}
			}
			model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetReappOfAuthorization);
			loadBudgetReappropriationData(tbAcBudgetReappOfAuthorization, model, redirectAttributes, httpServletRequest,
					bindingResult);
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
			log("tbAcBudgetReappOfAuthorization 'view' : view done - redirect");
			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
			log("tbAcBudgetReappOfAuthorization 'view' : view done - redirect");
			result = JSP_VIEW;
		} else {
			log("tbAcBudgetReappOfAuthorization 'view' : binding errors");
			populateModel(model, tbAcBudgetReappOfAuthorization, FormMode.UPDATE);
			result = JSP_VIEW;
		}
		return result;
	}

	@RequestMapping(params = "showDetails")
	public String showDetails(final Model model, final HttpServletRequest request,
			ReappropriationOfBudgetAuthorizationDTO tbAcBudgetReappOfAuthorization,
			final RedirectAttributes redirectAttributes, final BindingResult bindingResult,
			@RequestParam("appNo") final String appNo, @RequestParam("actualTaskId") final long actualTaskId)
			throws Exception {

		log("tbAcBudgetReappOfAuthorization-'gridData' : 'showDetails'");
		String result = MainetConstants.CommonConstant.BLANK;
		final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
				PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
				PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		// model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.EDIT);
		if (!bindingResult.hasErrors()) {

			model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.EDIT);
			setModeView("EDIT");
			final UserSession userSession = UserSession.getCurrent();
			final int LanguageId = userSession.getLanguageId();
			final Organisation Organisation = UserSession.getCurrent().getOrganisation();
			tbAcBudgetReappOfAuthorization.setOrgid(Organisation.getOrgid());
			tbAcBudgetReappOfAuthorization.setBudgetTranRefNo(appNo);
			tbAcBudgetReappOfAuthorization = reappropriationOfBudgetAuthorizationService
					.populateBudgetReappropriationWorkFlowData(tbAcBudgetReappOfAuthorization, LanguageId, Organisation,
							actualTaskId);
			if (tbAcBudgetReappOfAuthorization.getFaYearid() != null) {
				tbAcBudgetReappOfAuthorization.setFinancialYearDesc(
						tbFinancialyearService.findFinancialYearDesc(tbAcBudgetReappOfAuthorization.getFaYearid()));
			}
			final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			if (tbAcBudgetReappOfAuthorization.getFaYearid() != null) {
				final Long cpdBugtypeid = tbAcBudgetReappOfAuthorization.getCpdBugtypeId();

				final List<AccountBudgetProjectedRevenueEntryBean> revBeanDynamic = tbAcBudgetReappOfAuthorization
						.getBugprojRevBeanList1();
				for (final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean : revBeanDynamic) {
					accountBudgetProjectedRevenueEntryBean.getDpDeptid();
					if (revenueLookup.getLookUpId() == cpdBugtypeid) {
						Map<Long, String> budgetCode = new LinkedHashMap<>();
						budgetCode = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeads(orgId);
						accountBudgetProjectedRevenueEntryBean.setBudgetMapDynamic(budgetCode);
					}
				}
				final List<AccountBudgetProjectedRevenueEntryBean> revBean = tbAcBudgetReappOfAuthorization
						.getBugprojRevBeanList();
				for (final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean : revBean) {
					if (revenueLookup.getLookUpId() == cpdBugtypeid) {
						Map<Long, String> budgetCode = new LinkedHashMap<>();
						budgetCode = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeads(orgId);
						model.addAttribute(MainetConstants.AccountBudgetReappropriationMaster.BUDGET_CODE_MAP,
								budgetCode);
					}
				}
				final List<AccountBudgetProjectedExpenditureBean> expBeanDynamic = tbAcBudgetReappOfAuthorization
						.getBugprojExpBeanList1();
				for (final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean : expBeanDynamic) {
					if (expLookup.getLookUpId() == cpdBugtypeid) {
						Map<Long, String> budgetCodeExp = new LinkedHashMap<>();
						budgetCodeExp = accountBudgetProjectedExpenditureService.findByAllExpBudgetHeads(orgId);
						accountBudgetProjectedExpenditureBean.setBudgetMapDynamicExp(budgetCodeExp);
					}
				}
				final List<AccountBudgetProjectedExpenditureBean> expBean = tbAcBudgetReappOfAuthorization
						.getBugprojExpBeanList();
				for (final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean : expBean) {
					if (expLookup.getLookUpId() == cpdBugtypeid) {
						Map<Long, String> budgetCodeExpStatic = new LinkedHashMap<>();
						budgetCodeExpStatic = accountBudgetProjectedExpenditureService.findByAllExpBudgetHeads(orgId);
						model.addAttribute(MainetConstants.AccountBudgetReappropriationMaster.BUDGET_EXP_MAP,
								budgetCodeExpStatic);
					}
				}
			}
			
			
			final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
	                .getBean(IAttachDocsService.class).findByCode(orgId,
	                		MainetConstants.RECEIPT_MASTER.Module + MainetConstants.operator.FORWARD_SLACE
	                                + tbAcBudgetReappOfAuthorization.getPaAdjid());
	        List<DocumentDto> documentDtos = new ArrayList<>();
	        // iterate and set document details
	       if(CollectionUtils.isNotEmpty(attachDocs)) {
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
	        }
	        model.addAttribute("attachDocs",attachDocs);
	        model.addAttribute("documentDtos",documentDtos);
			
			model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetReappOfAuthorization);
			loadBudgetReappropriationData(tbAcBudgetReappOfAuthorization, model, redirectAttributes, request,
					bindingResult);
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
			log("tbAcBudgetReappOfAuthorization 'showDetails' : view done - redirect");
			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, "EDIT");
			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
			log("tbAcBudgetReappOfAuthorization 'showDetails' : view done - redirect");
			result = WORKFLOW_FORM;
		} else {
			log("tbAcBudgetReappOfAuthorization 'showDetails' : binding errors");
			populateModel(model, tbAcBudgetReappOfAuthorization, FormMode.UPDATE);
			result = WORKFLOW_FORM;
		}
		return result;
	}

	public String getModeView() {
		return modeView;
	}

	public void setModeView(final String modeView) {
		this.modeView = modeView;
	}

	@RequestMapping(params = "crossReappropriation", method = RequestMethod.POST)
	public @ResponseBody Boolean create(final ReappropriationOfBudgetAuthorizationDTO tbAcBudgetReappOfAuthorization,
			final BindingResult bindingResult, final HttpServletRequest httpServletRequest) {
		try {
			LookUp lookupl = CommonMasterUtility.getValueFromPrefixLookUp(
					MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BCR, PrefixConstants.BCE,
					UserSession.getCurrent().getOrganisation());
			Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
					PrefixConstants.REV_CPD_VALUE, PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(),
					UserSession.getCurrent().getOrganisation());
			final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
					PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(),
					UserSession.getCurrent().getOrganisation());
			if (lookupl != null) {
				if (lookupl.getOtherField().equals(MainetConstants.Y_FLAG)) {
					int count = 0;
					if (expLookup.getLookUpId() == tbAcBudgetReappOfAuthorization.getCpdBugtypeId()) {
						List<AccountBudgetProjectedExpenditureBean> explist1 = tbAcBudgetReappOfAuthorization
								.getBugprojExpBeanList1();
						List<AccountBudgetProjectedExpenditureBean> explist = tbAcBudgetReappOfAuthorization
								.getBugprojExpBeanList();
						Long accType1 = accountBudgetCodeService.getBudgetAccTypeByBudgetCodeId(orgId,
								Long.valueOf(explist1.get(0).getPrExpBudgetCode().replace(",", "")));
						Long accType = accountBudgetCodeService.getBudgetAccTypeByBudgetCodeId(orgId,
								Long.valueOf(explist.get(0).getPrExpBudgetCode().replace(",", "")));

						for (AccountBudgetProjectedExpenditureBean bean : explist1) {
							Long accTypeOthers = accountBudgetCodeService.getBudgetAccTypeByBudgetCodeId(orgId,
									Long.valueOf(bean.getPrExpBudgetCode().replace(",", "")));
							if (!accType1.equals(accTypeOthers) || !accType.equals(accTypeOthers)) {
								count++;
							}
						}
					} else if (revenueLookup.getLookUpId() == tbAcBudgetReappOfAuthorization.getCpdBugtypeId()) {
						List<AccountBudgetProjectedRevenueEntryBean> revList1 = tbAcBudgetReappOfAuthorization
								.getBugprojRevBeanList1();
						List<AccountBudgetProjectedRevenueEntryBean> revList = tbAcBudgetReappOfAuthorization
								.getBugprojRevBeanList();
						Long accType1 = accountBudgetCodeService.getBudgetAccTypeByBudgetCodeId(orgId,
								Long.valueOf(revList1.get(0).getPrRevBudgetCode().replace(",", "")));
						Long accType = accountBudgetCodeService.getBudgetAccTypeByBudgetCodeId(orgId,
								Long.valueOf(revList.get(0).getPrRevBudgetCode().replace(",", "")));
						for (AccountBudgetProjectedRevenueEntryBean bean : revList1) {
							Long accTypeothers = accountBudgetCodeService.getBudgetAccTypeByBudgetCodeId(orgId,
									Long.valueOf(bean.getPrRevBudgetCode().replace(",", "")));
							if (!accType1.equals(accTypeothers) || !accType.equals(accTypeothers)) {
								count++;
							}
						}
					}
					if (count > 0) {
						return true;
					}
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	private void isWorkflowDefine(final Model model, final ReappropriationOfBudgetAuthorizationDTO ReappropriationBudgetAuthorizationDTO) {
		 final int langId = UserSession.getCurrent().getLanguageId();
	     final Organisation org = UserSession.getCurrent().getOrganisation();
		 final LookUp activenessLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.IsLookUp.ACTIVE,PrefixConstants.LookUp.ACN, langId, org);
		 ServiceMaster service = serviceMasterService.getServiceByShortName("BR", UserSession.getCurrent().getOrganisation().getOrgid());
		    if(Long.valueOf(activenessLookup.getLookUpId())==service.getSmServActive()) {
		    	ReappropriationBudgetAuthorizationDTO.setIsServiceActive("active");
		    }else {
		    	ReappropriationBudgetAuthorizationDTO.setIsServiceActive("inActive");
		    }
	}
	
	
	@RequestMapping(params = "viewRefNoDetails")
	public String viewRefNoDetails(final Model model, final HttpServletRequest request,
			ReappropriationOfBudgetAuthorizationDTO tbAcBudgetReappOfAuthorization,
			final RedirectAttributes redirectAttributes, final BindingResult bindingResult,
			@RequestParam("appNo") final String appNo, @RequestParam("actualTaskId") final long actualTaskId)
			throws Exception {

		log("tbAcBudgetReappOfAuthorization-'gridData' : 'showDetails'");
		String result = MainetConstants.CommonConstant.BLANK;
		final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
				PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
				PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		// model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.EDIT);
		if (!bindingResult.hasErrors()) {

			model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.EDIT);
			setModeView("EDIT");
			final UserSession userSession = UserSession.getCurrent();
			final int LanguageId = userSession.getLanguageId();
			final Organisation Organisation = UserSession.getCurrent().getOrganisation();
			tbAcBudgetReappOfAuthorization.setOrgid(Organisation.getOrgid());
			tbAcBudgetReappOfAuthorization.setBudgetTranRefNo(appNo);
			tbAcBudgetReappOfAuthorization = reappropriationOfBudgetAuthorizationService
					.populateBudgetReappropriationWorkFlowData(tbAcBudgetReappOfAuthorization, LanguageId, Organisation,
							actualTaskId);
			if (tbAcBudgetReappOfAuthorization.getFaYearid() != null) {
				tbAcBudgetReappOfAuthorization.setFinancialYearDesc(
						tbFinancialyearService.findFinancialYearDesc(tbAcBudgetReappOfAuthorization.getFaYearid()));
			}
			final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			if (tbAcBudgetReappOfAuthorization.getFaYearid() != null) {
				final Long cpdBugtypeid = tbAcBudgetReappOfAuthorization.getCpdBugtypeId();

				final List<AccountBudgetProjectedRevenueEntryBean> revBeanDynamic = tbAcBudgetReappOfAuthorization
						.getBugprojRevBeanList1();
				for (final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean : revBeanDynamic) {
					accountBudgetProjectedRevenueEntryBean.getDpDeptid();
					if (revenueLookup.getLookUpId() == cpdBugtypeid) {
						Map<Long, String> budgetCode = new LinkedHashMap<>();
						budgetCode = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeads(orgId);
						accountBudgetProjectedRevenueEntryBean.setBudgetMapDynamic(budgetCode);
					}
				}
				final List<AccountBudgetProjectedRevenueEntryBean> revBean = tbAcBudgetReappOfAuthorization
						.getBugprojRevBeanList();
				for (final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean : revBean) {
					if (revenueLookup.getLookUpId() == cpdBugtypeid) {
						Map<Long, String> budgetCode = new LinkedHashMap<>();
						budgetCode = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeads(orgId);
						model.addAttribute(MainetConstants.AccountBudgetReappropriationMaster.BUDGET_CODE_MAP,
								budgetCode);
					}
				}
				final List<AccountBudgetProjectedExpenditureBean> expBeanDynamic = tbAcBudgetReappOfAuthorization
						.getBugprojExpBeanList1();
				for (final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean : expBeanDynamic) {
					if (expLookup.getLookUpId() == cpdBugtypeid) {
						Map<Long, String> budgetCodeExp = new LinkedHashMap<>();
						budgetCodeExp = accountBudgetProjectedExpenditureService.findByAllExpBudgetHeads(orgId);
						accountBudgetProjectedExpenditureBean.setBudgetMapDynamicExp(budgetCodeExp);
					}
				}
				final List<AccountBudgetProjectedExpenditureBean> expBean = tbAcBudgetReappOfAuthorization
						.getBugprojExpBeanList();
				for (final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean : expBean) {
					if (expLookup.getLookUpId() == cpdBugtypeid) {
						Map<Long, String> budgetCodeExpStatic = new LinkedHashMap<>();
						budgetCodeExpStatic = accountBudgetProjectedExpenditureService.findByAllExpBudgetHeads(orgId);
						model.addAttribute(MainetConstants.AccountBudgetReappropriationMaster.BUDGET_EXP_MAP,
								budgetCodeExpStatic);
					}
				}
			}
			
			
			final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
	                .getBean(IAttachDocsService.class).findByCode(orgId,
	                		MainetConstants.RECEIPT_MASTER.Module + MainetConstants.operator.FORWARD_SLACE
	                                + tbAcBudgetReappOfAuthorization.getPaAdjid());
	        List<DocumentDto> documentDtos = new ArrayList<>();
	        // iterate and set document details
	       if(CollectionUtils.isNotEmpty(attachDocs)) {
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
	        }
	        model.addAttribute("attachDocs",attachDocs);
	        model.addAttribute("documentDtos",documentDtos);
			
			model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetReappOfAuthorization);
			loadBudgetReappropriationData(tbAcBudgetReappOfAuthorization, model, redirectAttributes, request,
					bindingResult);
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
			log("tbAcBudgetReappOfAuthorization 'showDetails' : view done - redirect");
			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, "EDIT");
			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
			log("tbAcBudgetReappOfAuthorization 'showDetails' : view done - redirect");
			result = JSP_VIEW_APP;
		} else {
			log("tbAcBudgetReappOfAuthorization 'showDetails' : binding errors");
			populateModel(model, tbAcBudgetReappOfAuthorization, FormMode.UPDATE);
			result = JSP_VIEW_APP;
		}
		return result;
	}
}
