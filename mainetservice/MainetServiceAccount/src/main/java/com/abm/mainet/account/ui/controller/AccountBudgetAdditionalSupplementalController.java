
package com.abm.mainet.account.ui.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.dto.AccountBudgetAdditionalSupplementalBean;
import com.abm.mainet.account.service.AccountBudgetAdditionalSupplementalService;
import com.abm.mainet.account.service.AccountBudgetProjectedExpenditureService;
import com.abm.mainet.account.service.AccountBudgetProjectedRevenueEntryService;
import com.abm.mainet.account.service.BudgetCodeService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Message;
import com.abm.mainet.common.utility.MessageType;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("/AccountBudgetAdditionalSupplemental.html")
public class AccountBudgetAdditionalSupplementalController extends AbstractController {

    private static final String ADD_SUP_MAP = "addSupMap";
    private static final String MAIN_ENTITY_NAME = "tbAcBudgetAdditionalSupplemental";
    private static final String MAIN_LIST_NAME = "list";
    private static final String JSP_FORM = "tbAcBudgetAdditionalSupplemental/form";
    private static final String JSP_VIEW = "tbAcBudgetAdditionalSupplemental/view";
    private static final String JSP_LIST = "tbAcBudgetAdditionalSupplemental/list";
    private static final String AC_Financialyr_LOOKUPS = "aFinancialYr";
    private String modeView = MainetConstants.BLANK;
    @Resource
    private AccountBudgetAdditionalSupplementalService accountBudgetAdditionalSupplementalService;
    @Resource
    private AccountBudgetProjectedRevenueEntryService accountBudgetProjectedRevenueEntryService;
    @Resource
    private BudgetCodeService accountBudgetCodeService;
    @Resource
    private AccountBudgetProjectedExpenditureService accountBudgetProjectedExpenditureService;
    @Resource
    private TbFinancialyearService tbFinancialyearService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;
    @Resource
    private SecondaryheadMasterService secondaryheadMasterService;

    private List<AccountBudgetAdditionalSupplementalBean> chList = null;

    public AccountBudgetAdditionalSupplementalController() {
        super(AccountBudgetAdditionalSupplementalController.class, MAIN_ENTITY_NAME);
        log("AccountAdditionalSupplementalController created.");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(final HttpServletRequest request,
            final Model model) {
        log("AccountAdditionalSupplemental-'gridData' : 'Get grid Data'");
        final JQGridResponse response = new JQGridResponse<>();
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        response.setRows(chList);
        response.setTotal(chList.size());
        response.setRecords(chList.size());
        response.setPage(page);
        model.addAttribute(MAIN_LIST_NAME, chList);
        return response;
    }

    @RequestMapping(params = "getjqGridsearch")
    public @ResponseBody List getCheqData(final HttpServletRequest request, final Model model,
            @RequestParam("faYearid") final Long faYearid,
            @RequestParam("cpdBugtypeId") final Long cpdBugtypeId, @RequestParam("dpDeptid") final Long dpDeptid,
            @RequestParam("prBudgetCodeid") final Long prBudgetCodeid) {
        log("AccountAdditionalSupplemental-'getjqGridsearch' : 'get jqGrid search data'");
        chList = new ArrayList<>();
        chList.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final String budgIdentifyFlag = MainetConstants.BUDGET_ADDITIONALSUPPLEMENTAL.BUDGET_IDENTIFY_FLAG;
        chList = accountBudgetAdditionalSupplementalService.findByGridAllData(faYearid, cpdBugtypeId, dpDeptid, prBudgetCodeid,
                budgIdentifyFlag, orgId);
        String amount = null;
        String tansAmount = null;
        String finalAmount = null;
        for (final AccountBudgetAdditionalSupplementalBean bean : chList) {
            final Long prBudgetCodeId = bean.getPrBudgetCodeid();
            final String budgetCode = accountBudgetCodeService.getBudgetCode(prBudgetCodeId, orgId);
            bean.setPrBudgetCode(budgetCode);

            if (bean.getOrgRevBalamt() != null) {
                amount = CommonMasterUtility.getAmountInIndianCurrency(bean.getOrgRevBalamt());
                bean.setFormattedCurrency(amount);
            }
            if (bean.getTransferAmount() != null) {
                tansAmount = CommonMasterUtility.getAmountInIndianCurrency(bean.getTransferAmount());
                bean.setTransAmountAddSupBug(tansAmount);
            }
            if (bean.getNewOrgRevAmount() != null) {
                finalAmount = CommonMasterUtility.getAmountInIndianCurrency(bean.getNewOrgRevAmount());
                bean.setFinalAmountAddSupBug(finalAmount);
            }
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
    public String loadBudgetAdditionalSupplementalData(final AccountBudgetAdditionalSupplementalBean bean, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest request, final BindingResult bindingResult)
            throws Exception {
        String result = MainetConstants.CommonConstant.BLANK;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final List<LookUp> bugTypelevelMap = CommonMasterUtility.getListLookup(PrefixConstants.PREFIX,
                UserSession.getCurrent().getOrganisation());
        if (bean.getFaYearid() != null) {
            final Long cpdBugtypeid = bean.getCpdBugtypeId();
            final Long dpDeptid = bean.getDpDeptid();
            Map<Long, String> budgetCode = null;
            if (dpDeptid != null && cpdBugtypeid != null) {
                if (bugTypelevelMap != null) {
                    for (final LookUp lookUp : bugTypelevelMap) {
                        if (lookUp != null) {
                            if (lookUp.getLookUpCode().equals(PrefixConstants.REV_CPD_VALUE)) {
                                if (cpdBugtypeid.equals(lookUp.getLookUpId())) {
                                    budgetCode = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeads(orgId);
                                }
                            }
                            if (lookUp.getLookUpCode().equals(PrefixConstants.EXP_CPD_VALUE)) {
                                if (cpdBugtypeid.equals(lookUp.getLookUpId())) {
                                    budgetCode = accountBudgetProjectedExpenditureService.findByAllExpBudgetHeads(orgId);
                                }
                            }
                        }
                    }
                }
                model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.ACCOUNT_BUDGET_CODE_MAP, budgetCode);
            }
        }
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        result = JSP_FORM;
        return result;
    }

    @RequestMapping(params = "getOrgBalGridload", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> findBudgetAdditionalSupplementalAmountData(
            final AccountBudgetAdditionalSupplementalBean bean, final HttpServletRequest request, final Model model,
            @RequestParam("cnt") final int cnt) {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long faYearid = bean.getFaYearid();
        final String budgCodeid = bean.getBugprojRevBeanList().get(cnt).getPrRevBudgetCode().replace(
                MainetConstants.operator.COMMA,
                MainetConstants.CommonConstant.BLANK);
        final Map<String, String> opBalMap = new HashMap<>();
        final List<Object[]> orgEsmtAmount = accountBudgetProjectedRevenueEntryService.findByRenueOrgAmount(faYearid,
                Long.valueOf(budgCodeid), orgId);
        if (!orgEsmtAmount.isEmpty()) {
            for (final Object[] objects : orgEsmtAmount) {
                if (objects[0] == null) {
                    BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
                    originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
                    opBalMap.put(MainetConstants.BUDGET_ALLOCATION_MASTER.REV_ORG_ESMT_VALUE, originalEstAmount.toString());
                }
                if (objects[0] != null) {
                    BigDecimal revisedEsmtAmount = new BigDecimal(objects[0].toString());
                    revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);
                    opBalMap.put(MainetConstants.BUDGET_ALLOCATION_MASTER.REV_ORG_ESMT_VALUE, revisedEsmtAmount.toString());
                }
            }
        } else {
            opBalMap.put(MainetConstants.BUDGET_ALLOCATION_MASTER.REV_ORG_ESMT_VALUE, MainetConstants.IsDeleted.ZERO);
        }
        return opBalMap;
    }

    @RequestMapping(params = "getOrgBalExpGridload", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> findBudgetReappropriationExpAmountData(
            final AccountBudgetAdditionalSupplementalBean bean,
            final HttpServletRequest request, final Model model, @RequestParam("cont") final int cont) {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long faYearid = bean.getFaYearid();
        final String budgCodeid = bean.getBugprojExpBeanList().get(cont).getPrExpBudgetCode().replace(
                MainetConstants.operator.COMMA,
                MainetConstants.CommonConstant.BLANK);
        final Map<String, String> opnExpBalMap = new HashMap<>();
        final List<Object[]> orgEsmtAmount = accountBudgetProjectedExpenditureService.findByExpOrgAmount(faYearid,
                Long.valueOf(budgCodeid), orgId);
        if (!orgEsmtAmount.isEmpty()) {
            for (final Object[] objects : orgEsmtAmount) {
                if (objects[0] == null) {
                    BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
                    originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
                    opnExpBalMap.put(MainetConstants.BUDGET_ALLOCATION_MASTER.EXP_ORG_ESMT_VALUE, originalEstAmount.toString());
                }
                if (objects[0] != null) {
                    BigDecimal revisedEsmtAmount = new BigDecimal(objects[0].toString());
                    revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);
                    opnExpBalMap.put(MainetConstants.BUDGET_ALLOCATION_MASTER.EXP_ORG_ESMT_VALUE, revisedEsmtAmount.toString());
                }
            }
        } else {
            opnExpBalMap.put(MainetConstants.BUDGET_ALLOCATION_MASTER.EXP_ORG_ESMT_VALUE, MainetConstants.IsDeleted.ZERO);
        }
        return opnExpBalMap;
    }

    @RequestMapping(params = "getReappDynamicRevPrimarykeyIdDetails", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> findBudgetReappDynamicRevenueAllocationPrimaryIdData(
            final AccountBudgetAdditionalSupplementalBean bean, final HttpServletRequest request, final Model model,
            @RequestParam("cnt") final int cnt) {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long faYearid = bean.getFaYearid();
        final String prRevBudgetCode = bean.getBugprojRevBeanList().get(cnt).getPrRevBudgetCode()
                .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK);
        final List<Object[]> primarykeyIdDetails = accountBudgetProjectedRevenueEntryService.findByRenueOrgAmount(faYearid,
                Long.valueOf(prRevBudgetCode), orgId);
        final Map<Long, String> revBalMap = new HashMap<>();
        if (!primarykeyIdDetails.isEmpty()) {
            for (final Object[] objects : primarykeyIdDetails) {
                if (objects[3] != null) {
                    final Long primaryKeyId = Long.valueOf(objects[3].toString());
                    revBalMap.put(primaryKeyId, objects[3].toString());
                }
            }
        }
        return revBalMap;
    }

    @RequestMapping(params = "getExpPrimarykeyIdDetails", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> findBudgetExpenditureAllocationPrimaryIdData(
            final AccountBudgetAdditionalSupplementalBean bean, final HttpServletRequest request, final Model model,
            @RequestParam("cont") final int cont) {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long faYearid = bean.getFaYearid();
        final String prExpBudgetCode = bean.getBugprojExpBeanList().get(cont).getPrExpBudgetCode()
                .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK);
        final List<Object[]> primarykeyIdDetails = accountBudgetProjectedExpenditureService.findByExpOrgAmount(faYearid,
                Long.valueOf(prExpBudgetCode), orgId);
        final Map<Long, String> expBalMap = new HashMap<>();
        if (!primarykeyIdDetails.isEmpty()) {
            for (final Object[] objects : primarykeyIdDetails) {
                if (objects[3] != null) {
                    final Long primaryKeyId = Long.valueOf(objects[3].toString());
                    expBalMap.put(primaryKeyId, objects[3].toString());
                }
            }
        }
        return expBalMap;
    }

    @RequestMapping()
    public String getList(final Model model) {
        log("AccountAdditionalSupplemental-'list' :'list'");
        String result = MainetConstants.CommonConstant.BLANK;
        chList = new ArrayList<>();
        chList.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final String budgIdentifyFlag = MainetConstants.BUDGET_ADDITIONALSUPPLEMENTAL.BUDGET_IDENTIFY_FLAG;
        final String faYearid = UserSession.getCurrent().getFinYearId();
        if ((faYearid != null) && !faYearid.isEmpty()) {
            chList = accountBudgetAdditionalSupplementalService.findBudgetAdditionalSupplementalByFinancialId(
                    Long.valueOf(faYearid), budgIdentifyFlag,
                    orgId);
            String amount = null;
            String tansAmount = null;
            String finalAmount = null;
            for (final AccountBudgetAdditionalSupplementalBean bean : chList) {
                final Long prBudgetCodeid = bean.getPrBudgetCodeid();
                final String budgetCode = accountBudgetCodeService.getBudgetCode(prBudgetCodeid, orgId);
                bean.setPrBudgetCode(budgetCode);

                if (bean.getOrgRevBalamt() != null) {
                    amount = CommonMasterUtility.getAmountInIndianCurrency(bean.getOrgRevBalamt());
                    bean.setFormattedCurrency(amount);
                }
                if (bean.getTransferAmount() != null) {
                    tansAmount = CommonMasterUtility.getAmountInIndianCurrency(bean.getTransferAmount());
                    bean.setTransAmountAddSupBug(tansAmount);
                }
                if (bean.getNewOrgRevAmount() != null) {
                    finalAmount = CommonMasterUtility.getAmountInIndianCurrency(bean.getNewOrgRevAmount());
                    bean.setFinalAmountAddSupBug(finalAmount);
                }
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
        }
        final Map<Long, String> addSupMap = new HashMap<>();
        final List<AccountBudgetAdditionalSupplementalBean> list = accountBudgetAdditionalSupplementalService
                .findBudgetAdditionalSupplementalByOrg(
                        orgId,
                        budgIdentifyFlag);
        for (final AccountBudgetAdditionalSupplementalBean accountBudgetAdditionalSupplementalBean : list) {
            final Long prBudgetCodeid = accountBudgetAdditionalSupplementalBean.getPrBudgetCodeid();
            final String budgetCode = accountBudgetCodeService.getBudgetCode(prBudgetCodeid, orgId);
            addSupMap.put(prBudgetCodeid, budgetCode);
        }
        model.addAttribute(ADD_SUP_MAP, addSupMap);
        final AccountBudgetAdditionalSupplementalBean bean = new AccountBudgetAdditionalSupplementalBean();
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        model.addAttribute(MAIN_LIST_NAME, chList);
        result = JSP_LIST;
        return result;
    }

    @RequestMapping(params = "form")
    public String formForCreate(final Model model) {
        log("AccountAdditionalSupplemental-'formForCreate' : 'formForCreate'");
        String result = MainetConstants.BLANK;
        final AccountBudgetAdditionalSupplementalBean bean = new AccountBudgetAdditionalSupplementalBean();
        populateModel(model, bean, FormMode.CREATE);
        result = JSP_FORM;
        return result;
    }

    private void populateModel(final Model model, final AccountBudgetAdditionalSupplementalBean bean, final FormMode formMode) {
        log("AccountAdditionalSupplemental-'populateModel' : populate model");
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

        final Map<Long, String> deptMap = new HashMap<>(0);
        List<Object[]> department = null;
        department = departmentService.getAllDeptTypeNames();
        for (final Object[] dep : department) {
            if (dep[0] != null) {
                deptMap.put((Long) (dep[0]), (String) dep[1]);
            }
        }
        model.addAttribute(MainetConstants.BUDGET_ADDITIONALSUPPLEMENTAL.DEPT_MAP, deptMap);
        final Map<Long, String> employeeMap = new HashMap<>(0);
        List<Object[]> employee = null;
        employee = employeeService.getAllEmployeeNames(orgId);
        for (final Object[] emp : employee) {
            if (emp[0] != null) {
                employeeMap.put((Long) (emp[0]), (String) emp[1]);
            }
        }
        model.addAttribute(MainetConstants.BUDGET_ADDITIONALSUPPLEMENTAL.EMPLOYEE_MAP, employeeMap);
        model.addAttribute(MAIN_ENTITY_NAME, bean);

        model.addAttribute(AC_Financialyr_LOOKUPS, secondaryheadMasterService.getAllFinincialYear(orgId, langId));

        if (formMode == FormMode.CREATE) {

            final Map<Long, String> financeMap = tbFinancialyearService.getAllFinincialYearByStatusWise(orgId);
            model.addAttribute(MainetConstants.BUDGET_ADDITIONALSUPPLEMENTAL.FINANCE_MAP, financeMap);

            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE,
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE_CREATE);
            model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, false);
        } else if (formMode == FormMode.UPDATE) {

            final Map<Long, String> financeMap = tbFinancialyearService.getAllFinincialYear();
            model.addAttribute(MainetConstants.BUDGET_ADDITIONALSUPPLEMENTAL.FINANCE_MAP, financeMap);

            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE,
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE_UPDATE);
        }
    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String create(final AccountBudgetAdditionalSupplementalBean tbAcBudgetAdditionalSupplemental,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) {
        log("AccountBudgetAdditionalSupplementalBean-'create' : 'create'");
        String result = MainetConstants.CommonConstant.BLANK;
        if (!bindingResult.hasErrors()) {
            tbAcBudgetAdditionalSupplemental.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
            final UserSession userSession = UserSession.getCurrent();
            final int langId = userSession.getLanguageId();
            final Organisation orgId = UserSession.getCurrent().getOrganisation();
            tbAcBudgetAdditionalSupplemental.setOrgid(userSession.getOrganisation().getOrgid());
            tbAcBudgetAdditionalSupplemental.setLangId(userSession.getLanguageId());
            tbAcBudgetAdditionalSupplemental.setUserId(userSession.getEmployee().getEmpId());
            tbAcBudgetAdditionalSupplemental.setLmoddate(new Date());
            tbAcBudgetAdditionalSupplemental.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
            if (tbAcBudgetAdditionalSupplemental.getPaAdjid() != null) {
                tbAcBudgetAdditionalSupplemental.setApprovedBy(userSession.getEmployee().getEmpId());
                tbAcBudgetAdditionalSupplemental.setUpdatedBy(userSession.getEmployee().getEmpId());
                tbAcBudgetAdditionalSupplemental.setUpdatedDate(new Date());
            }
            populateModel(model, tbAcBudgetAdditionalSupplemental, FormMode.CREATE);
            AccountBudgetAdditionalSupplementalBean tbAcBudgetAdditionalSupplementalCreated = accountBudgetAdditionalSupplementalService
                    .saveBudgetAdditionalSupplementalFormData(tbAcBudgetAdditionalSupplemental, langId, orgId);
            if (tbAcBudgetAdditionalSupplementalCreated == null) {
                tbAcBudgetAdditionalSupplementalCreated = new AccountBudgetAdditionalSupplementalBean();
            }
            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetAdditionalSupplementalCreated);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            if (tbAcBudgetAdditionalSupplemental.getPaAdjid() == null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        MainetConstants.AccountBudgetAdditionalSupplemental.RECORD_SAVE_SUCCESS);
            }
            if (tbAcBudgetAdditionalSupplemental.getPaAdjid() != null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        MainetConstants.AccountBudgetAdditionalSupplemental.RECORD_UPDATE_SUCCESS);
            }
            result = JSP_FORM;
        } else {
            tbAcBudgetAdditionalSupplemental.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            populateModel(model, tbAcBudgetAdditionalSupplemental, FormMode.CREATE);
            result = JSP_FORM;
        }
        return result;
    }

    @RequestMapping(params = "update", method = RequestMethod.POST)
    public String update(AccountBudgetAdditionalSupplementalBean tbAcBudgetAdditionalSupplemental,
            @RequestParam("paAdjid") final Long paAdjid,
            @RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) {
        log("tbAcBudgetAdditionalSupplemental-'gridData' : 'update'");
        String result = MainetConstants.CommonConstant.BLANK;
        final List<LookUp> bugTypelevelMap = CommonMasterUtility.getListLookup(PrefixConstants.PREFIX,
                UserSession.getCurrent().getOrganisation());
        if (!bindingResult.hasErrors()) {
            if (viewmode.equals(MainetConstants.EDIT)) {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.EDIT);
            } else {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
            }
            setModeView(viewmode);
            final UserSession userSession = UserSession.getCurrent();
            final int langId = userSession.getLanguageId();
            final Organisation org = UserSession.getCurrent().getOrganisation();
            tbAcBudgetAdditionalSupplemental.setPaAdjid(paAdjid);
            tbAcBudgetAdditionalSupplemental = accountBudgetAdditionalSupplementalService
                    .getDetailsUsingBudgetAdditionalSupplementalId(tbAcBudgetAdditionalSupplemental, langId, org);
            if (tbAcBudgetAdditionalSupplemental.getFaYearid() != null) {
                tbAcBudgetAdditionalSupplemental.setFinancialYearDesc(
                        tbFinancialyearService.findFinancialYearDesc(tbAcBudgetAdditionalSupplemental.getFaYearid()));
            }
            final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            if (tbAcBudgetAdditionalSupplemental.getFaYearid() != null) {
                final Long cpdBugtypeid = tbAcBudgetAdditionalSupplemental.getCpdBugtypeId();
                Map<Long, String> budgetCode = null;
                if (bugTypelevelMap != null) {
                    for (final LookUp lookUp : bugTypelevelMap) {
                        if (lookUp != null) {
                            if (lookUp.getLookUpCode().equals(PrefixConstants.REV_CPD_VALUE)) {
                                if (cpdBugtypeid.equals(lookUp.getLookUpId())) {
                                    budgetCode = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeads(orgId);
                                }
                            }
                            if (lookUp.getLookUpCode().equals(PrefixConstants.EXP_CPD_VALUE)) {
                                if (cpdBugtypeid.equals(lookUp.getLookUpId())) {
                                    budgetCode = accountBudgetProjectedExpenditureService.findByAllExpBudgetHeads(orgId);
                                }
                            }
                        }
                    }
                }
                model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.ACCOUNT_BUDGET_CODE_MAP, budgetCode);
            }
            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetAdditionalSupplemental);
            populateModel(model, tbAcBudgetAdditionalSupplemental, FormMode.UPDATE);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            log("tbAcBudgetAdditionalSupplemental 'update' : update done - redirect");
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            log("tbAcBudgetAdditionalSupplemental 'update' : update done - redirect");
            result = JSP_FORM;
        } else {
            log("tbAcBudgetAdditionalSupplemental 'update' : binding errors");
            populateModel(model, tbAcBudgetAdditionalSupplemental, FormMode.UPDATE);
            result = JSP_FORM;
        }
        return result;
    }

    @RequestMapping(params = "formForView", method = RequestMethod.POST)
    public String formForView(AccountBudgetAdditionalSupplementalBean tbAcBudgetAdditionalSupplemental,
            @RequestParam("paAdjid") final Long paAdjid,
            @RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) {
        log("tbAcBudgetAdditionalSupplemental-'gridData' : 'view'");
        String result = MainetConstants.CommonConstant.BLANK;
        if (!bindingResult.hasErrors()) {
            if (viewmode.equals(MainetConstants.VIEW)) {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.VIEW);
            } else {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
            }
            setModeView(viewmode);
            final UserSession userSession = UserSession.getCurrent();
            final int langId = userSession.getLanguageId();
            final Organisation org = UserSession.getCurrent().getOrganisation();
            tbAcBudgetAdditionalSupplemental.setPaAdjid(paAdjid);
            tbAcBudgetAdditionalSupplemental = accountBudgetAdditionalSupplementalService
                    .getDetailsUsingBudgetAdditionalSupplementalId(tbAcBudgetAdditionalSupplemental, langId, org);
            if (tbAcBudgetAdditionalSupplemental.getFaYearid() != null) {
                tbAcBudgetAdditionalSupplemental.setFinancialYearDesc(
                        tbFinancialyearService.findFinancialYearDesc(tbAcBudgetAdditionalSupplemental.getFaYearid()));
            }
            final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            if (tbAcBudgetAdditionalSupplemental.getFaYearid() != null) {
                final Long cpdBugtypeid = tbAcBudgetAdditionalSupplemental.getCpdBugtypeId();
                final Long cpdBugsubtypeId = tbAcBudgetAdditionalSupplemental.getCpdBugSubTypeId();
                final Long dpDeptid = tbAcBudgetAdditionalSupplemental.getDpDeptid();
                final Map<Long, String> budgetCode = accountBudgetCodeService.finAllBudgetCodeByOrg(orgId);
                model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.ACCOUNT_BUDGET_CODE_MAP, budgetCode);
            }
            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetAdditionalSupplemental);
            populateModel(model, tbAcBudgetAdditionalSupplemental, FormMode.UPDATE);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            log("tbAcBudgetAdditionalSupplemental 'view' : view done - redirect");
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            log("tbAcBudgetAdditionalSupplemental 'view' : view done - redirect");
            result = JSP_VIEW;
        } else {
            log("tbAcBudgetAdditionalSupplemental 'view' : binding errors");
            populateModel(model, tbAcBudgetAdditionalSupplemental, FormMode.UPDATE);
            result = JSP_VIEW;
        }
        return result;
    }

    public String getModeView() {
        return modeView;
    }

    public void setModeView(final String modeView) {
        this.modeView = modeView;
    }
}
