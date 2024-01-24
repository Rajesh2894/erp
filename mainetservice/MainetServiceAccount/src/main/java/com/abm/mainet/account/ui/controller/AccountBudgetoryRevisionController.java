
package com.abm.mainet.account.ui.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

import com.abm.mainet.account.dto.AccountBudgetProjectedExpenditureBean;
import com.abm.mainet.account.dto.AccountBudgetProjectedRevenueEntryBean;
import com.abm.mainet.account.dto.AccountBudgetoryRevisionBean;
import com.abm.mainet.account.service.AccountBudgetProjectedExpenditureService;
import com.abm.mainet.account.service.AccountBudgetProjectedRevenueEntryService;
import com.abm.mainet.account.service.AccountBudgetoryRevisionService;
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
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Message;
import com.abm.mainet.common.utility.MessageType;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("/AccountBudgetoryRevision.html")
public class AccountBudgetoryRevisionController extends AbstractController {

    private static final String MAIN_ENTITY_NAME = "tbAcBudgetoryRevision";
    private static final String MAIN_LIST_NAME = "list";
    private static final String JSP_FORM = "tbAcBudgetoryRevision/form";
    private static final String JSP_VIEW = "tbAcBudgetoryRevision/view";
    private static final String JSP_LIST = "tbAcBudgetoryRevision/list";
    private static final String AC_Financialyr_LOOKUPS = "aFinancialYr";
    private String modeView = MainetConstants.BLANK;
    @Resource
    private AccountBudgetProjectedRevenueEntryService accountBudgetProjectedRevenueEntryService;
    @Resource
    private AccountBudgetProjectedExpenditureService accountBudgetProjectedExpenditureService;
    @Resource
    private AccountBudgetoryRevisionService accountBudgetoryRevisionService;
    @Resource
    private TbFinancialyearService tbFinancialyearService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;
    @Resource
    private BudgetCodeService accountBudgetCodeService;
    @Resource
    private SecondaryheadMasterService secondaryheadMasterService;

    private List<AccountBudgetoryRevisionBean> chList = null;

    public AccountBudgetoryRevisionController() {
        super(AccountBudgetoryRevisionController.class, MAIN_ENTITY_NAME);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(final HttpServletRequest request,
            final Model model) {
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
    public @ResponseBody List<?> getCheqData(final HttpServletRequest request, final Model model,
            @RequestParam("faYearid") final Long faYearid,
            @RequestParam("cpdBugtypeId") final Long cpdBugtypeId, @RequestParam("dpDeptid") final Long dpDeptid,
            @RequestParam("prBudgetCodeid") Long prBudgetCodeid, @RequestParam("fundId") final Long fundId,
            @RequestParam("functionId") final Long functionId) {
        chList = new ArrayList<>();
        chList.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        Map<String, String> primaryIdAllData = new LinkedHashMap<>();
        primaryIdAllData = accountBudgetoryRevisionService.findBudgetCodeIdCodeFromBudgetoryRevision(faYearid, fundId, functionId,
                cpdBugtypeId,
                prBudgetCodeid, dpDeptid, orgId);
        for (final String key : primaryIdAllData.keySet()) {
            prBudgetCodeid = Long.valueOf(key);
            final List<AccountBudgetoryRevisionBean> chListA = accountBudgetoryRevisionService.findByGridAllData(faYearid,
                    cpdBugtypeId,
                    dpDeptid, prBudgetCodeid, orgId);
            // chList.addAll(chListA);
            String tansAmount = null;
            for (final AccountBudgetoryRevisionBean bean : chListA) {
                AccountBudgetoryRevisionBean newBean = new AccountBudgetoryRevisionBean();
                newBean.setBugrevId(bean.getBugrevId());
                final Long prBudgetCodeId = bean.getPrBudgetCodeid();
                final String budgetCode = accountBudgetCodeService.getBudgetCode(prBudgetCodeId, orgId);
                newBean.setPrBudgetCode(budgetCode);

                if (bean.getRevisedAmount() != null) {
                    tansAmount = CommonMasterUtility.getAmountInIndianCurrency(bean.getRevisedAmount());
                    // bean.setRevisedAmountDup(tansAmount);
                }
                final Long cpdBugtypeid = bean.getCpdBugtypeId();
                if (cpdBugtypeid.equals(revenueLookup.getLookUpId())) {
                    final List<Object[]> orgEstimateAmtDetails = accountBudgetProjectedRevenueEntryService
                            .findByRenueOrgAmount(Long.valueOf(faYearid), prBudgetCodeid, orgId);
                    if (!orgEstimateAmtDetails.isEmpty()) {
                        for (final Object[] objects : orgEstimateAmtDetails) {
                            if (objects[0] != null) {
                                newBean.setRevisedAmountDup(CommonMasterUtility
                                        .getAmountInIndianCurrency(new BigDecimal(objects[0].toString())));
                            }
                            BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
                            originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
                            final String displayoriginalEstAmount = CommonMasterUtility
                                    .getAmountInIndianCurrency(originalEstAmount);
                            newBean.setOrginalEstamt(displayoriginalEstAmount.toString());
                        }
                    }
                }
                if (cpdBugtypeid.equals(expLookup.getLookUpId())) {
                    final List<Object[]> orgEstimateAmtDetails = accountBudgetProjectedExpenditureService
                            .findByExpOrgAmount(Long.valueOf(faYearid), prBudgetCodeid, orgId);
                    if (!orgEstimateAmtDetails.isEmpty()) {
                        for (final Object[] objects : orgEstimateAmtDetails) {
                            if (objects[0] != null) {
                                newBean.setRevisedAmountDup(CommonMasterUtility
                                        .getAmountInIndianCurrency(new BigDecimal(objects[0].toString())));
                            }
                            BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
                            originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
                            final String displayoriginalEstAmount = CommonMasterUtility
                                    .getAmountInIndianCurrency(originalEstAmount);
                            newBean.setOrginalEstamt(displayoriginalEstAmount.toString());
                        }
                    }
                }
                chList.add(newBean);
            }
        }
        return chList;
    }

    @RequestMapping(params = "getjqGridload", method = RequestMethod.POST)
    public String loadBudgetEstimationPreparationData(final AccountBudgetoryRevisionBean bean, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest request, final BindingResult bindingResult)
            throws Exception {
        String result = MainetConstants.CommonConstant.BLANK;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final Long faYearid = bean.getFaYearid().longValue();
        if ((bean.getFaYearid() != null && bean.getCpdBugtypeId() != null)
                && bean.getCpdBugtypeId().equals(revenueLookup.getLookUpId())) {
            final List<AccountBudgetProjectedRevenueEntryBean> listOfAllFinacialYearDates = accountBudgetProjectedRevenueEntryService
                    .getListOfAllFinacialYearDates(orgId, faYearid);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_PAC_SAC_HEAD_LIST, listOfAllFinacialYearDates);
        }
        if ((bean.getFaYearid() != null && bean.getCpdBugtypeId() != null)
                && bean.getCpdBugtypeId().equals(expLookup.getLookUpId())) {
            final List<AccountBudgetProjectedExpenditureBean> listOfAllFinacialYearDates = accountBudgetProjectedExpenditureService
                    .getListOfAllFinacialYearDates(orgId, faYearid);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_PAC_SAC_HEAD_LIST, listOfAllFinacialYearDates);
        }
        if (bean.getFaYearid() != null) {
            final Long cpdBugtypeid = bean.getCpdBugtypeId();
            final Long dpDeptid = bean.getDpDeptid();
            if (dpDeptid != null && cpdBugtypeid != null) {
                if (revenueLookup.getLookUpId() == cpdBugtypeid) {
                    Map<Long, String> budgetCode = new LinkedHashMap<>();
                    budgetCode = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeads(orgId);
                    model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.REV_BUDGET_CODE_MAP, budgetCode);
                }
                if (expLookup.getLookUpId() == cpdBugtypeid) {
                    Map<Long, String> budgetCode = new LinkedHashMap<>();
                    budgetCode = accountBudgetProjectedExpenditureService.findByAllExpBudgetHeads(orgId);
                    model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.EXP_BUDGET_CODE_MAP, budgetCode);
                }
            }
        }
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        result = JSP_FORM;
        return result;
    }

    @RequestMapping(params = "getOrgBalGridload", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> findBudgetAdditionalSupplementalAmountData(final AccountBudgetoryRevisionBean bean,
            final HttpServletRequest request, final Model model, @RequestParam("cnt") final int cnt) {

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
                    opBalMap.put(MainetConstants.BUDGET_ESTIMATION_PREPARATION.ORIGINAL_ESMT_AMT_REV,
                            originalEstAmount.toString());
                }
                if (objects[0] != null) {
                    BigDecimal revisedEsmtAmount = new BigDecimal(objects[0].toString());
                    revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);
                    opBalMap.put(MainetConstants.BUDGET_ESTIMATION_PREPARATION.ORIGINAL_ESMT_AMT_REV,
                            revisedEsmtAmount.toString());
                }
            }
        }
        return opBalMap;
    }

    @RequestMapping(params = "getOrgBalGridloadExp", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> findBudgetEstimationPreparationAmountDataExp(final AccountBudgetoryRevisionBean bean,
            final HttpServletRequest request, final Model model, @RequestParam("cont") final int cont) {
        final Long faYearid = bean.getFaYearid();
        final String budgCodeid = bean.getBugprojExpBeanList().get(cont).getPrExpBudgetCode().replace(
                MainetConstants.operator.COMMA,
                MainetConstants.CommonConstant.BLANK);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<Object[]> list = null;
        final Map<String, String> opBalMapExp = new HashMap<>();
        list = accountBudgetProjectedExpenditureService.findByExpOrgAmount(faYearid, Long.valueOf(budgCodeid), orgId);
        if (!list.isEmpty()) {
            for (final Object[] amount : list) {
                if (amount[0] == null) {
                    BigDecimal originalEstAmount = new BigDecimal(amount[1].toString());
                    originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
                    opBalMapExp.put(MainetConstants.BUDGET_ESTIMATION_PREPARATION.ORIGINAL_ESMT_AMT_EXP,
                            originalEstAmount.toString());
                }
                if (amount[0] != null) {
                    BigDecimal revisedEsmtAmount = new BigDecimal(amount[0].toString());
                    revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);
                    opBalMapExp.put(MainetConstants.BUDGET_ESTIMATION_PREPARATION.ORIGINAL_ESMT_AMT_EXP,
                            revisedEsmtAmount.toString());
                }
            }
        }
        return opBalMapExp;
    }

    @RequestMapping(params = "getActualTillNovFromDecAmount", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> findBudgetgetRevisionActualTillNovFromDecAmountData(
            final AccountBudgetoryRevisionBean bean, final HttpServletRequest request, final Model model,
            @RequestParam("cnt") final int cnt)
            throws Exception {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long faYearid = bean.getFaYearid();
        final String budgCodeid = bean.getBugprojRevBeanList().get(cnt).getPrRevBudgetCode().replace(
                MainetConstants.operator.COMMA,
                MainetConstants.CommonConstant.BLANK);
        BigDecimal actualTillNovFromDecAmount = accountBudgetoryRevisionService
                .findBudgetgetRevisionActualTillNovFromDecAmountData(faYearid, Long.valueOf(budgCodeid), orgId);
        actualTillNovFromDecAmount = actualTillNovFromDecAmount.setScale(2, RoundingMode.CEILING);
        List<Object[]> list = null;
        list = accountBudgetProjectedRevenueEntryService.findByRenueOrgAmount(faYearid, Long.valueOf(budgCodeid), orgId);
        final Map<String, String> opBalMap = new HashMap<>();
        if (list.size() > 0) {
            for (final Object[] amount : list) {
                if (amount[0] == null) {
                    final BigDecimal orgAmount = new BigDecimal(amount[1].toString());
                    BigDecimal budgFormDecTOMarAmount = orgAmount.subtract(actualTillNovFromDecAmount);
                    budgFormDecTOMarAmount = budgFormDecTOMarAmount.setScale(2, RoundingMode.CEILING);
                    opBalMap.put(MainetConstants.BUDGETARY_REVISION.BUDGET_AFTR_TILL_NOV_AMT,
                            actualTillNovFromDecAmount.toString());
                    opBalMap.put(MainetConstants.BUDGETARY_REVISION.BUDGET_DEC_TO_MAR_AMT, budgFormDecTOMarAmount.toString());
                }
                if (amount[0] != null) {
                    final BigDecimal orgAmount = new BigDecimal(amount[0].toString());
                    BigDecimal budgFormDecTOMarAmount = orgAmount.subtract(actualTillNovFromDecAmount);
                    budgFormDecTOMarAmount = budgFormDecTOMarAmount.setScale(2, RoundingMode.CEILING);
                    opBalMap.put(MainetConstants.BUDGETARY_REVISION.BUDGET_AFTR_TILL_NOV_AMT,
                            actualTillNovFromDecAmount.toString());
                    opBalMap.put(MainetConstants.BUDGETARY_REVISION.BUDGET_DEC_TO_MAR_AMT, budgFormDecTOMarAmount.toString());
                }
            }
        } else {
            BigDecimal defaultOrgvalue = new BigDecimal(0);
            defaultOrgvalue = defaultOrgvalue.setScale(2, RoundingMode.CEILING);
            BigDecimal budgFormDecTOMarAmount = defaultOrgvalue.subtract(actualTillNovFromDecAmount);
            budgFormDecTOMarAmount = budgFormDecTOMarAmount.setScale(2, RoundingMode.CEILING);
            opBalMap.put(MainetConstants.BUDGETARY_REVISION.BUDGET_AFTR_TILL_NOV_AMT, actualTillNovFromDecAmount.toString());
            opBalMap.put(MainetConstants.BUDGETARY_REVISION.BUDGET_DEC_TO_MAR_AMT, budgFormDecTOMarAmount.toString());
        }
        return opBalMap;
    }

    @RequestMapping(params = "getExpActualTillNovFromDecAmount", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> findBudgetgetRevisionActualTillNovFromDecAmountExpData(
            final AccountBudgetoryRevisionBean bean, final HttpServletRequest request, final Model model,
            @RequestParam("cont") final int cont)
            throws Exception {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long faYearid = bean.getFaYearid();
        final String budgCodeid = bean.getBugprojExpBeanList().get(cont).getPrExpBudgetCode().replace(
                MainetConstants.operator.COMMA,
                MainetConstants.CommonConstant.BLANK);
        BigDecimal actualTillNovFromDecAmountExp = accountBudgetoryRevisionService
                .findBudgetgetRevisionActualTillNovFromDecAmountExpData(faYearid, Long.valueOf(budgCodeid), orgId);
        actualTillNovFromDecAmountExp = actualTillNovFromDecAmountExp.setScale(2, RoundingMode.CEILING);
        List<Object[]> listExp = null;
        listExp = accountBudgetProjectedExpenditureService.findByExpOrgAmount(faYearid, Long.valueOf(budgCodeid), orgId);
        final Map<String, String> opBalMapExp = new HashMap<>();
        if (listExp.size() > 0) {
            for (final Object[] amountExp : listExp) {
                if (amountExp[0] == null) {
                    final BigDecimal orgAmountExp = new BigDecimal(amountExp[1].toString());
                    BigDecimal budgFormDecTOMarAmountExp = orgAmountExp.subtract(actualTillNovFromDecAmountExp);
                    budgFormDecTOMarAmountExp = budgFormDecTOMarAmountExp.setScale(2, RoundingMode.CEILING);
                    opBalMapExp.put(MainetConstants.BUDGETARY_REVISION.BUDGET_EXP_AFTR_TILL_NOV_AMT,
                            actualTillNovFromDecAmountExp.toString());
                    opBalMapExp.put(MainetConstants.BUDGETARY_REVISION.BUDGET_EXP_DEC_TO_MAR_AMT,
                            budgFormDecTOMarAmountExp.toString());
                }
                if (amountExp[0] != null) {
                    final BigDecimal orgAmountExp = new BigDecimal(amountExp[0].toString());
                    BigDecimal budgFormDecTOMarAmountExp = orgAmountExp.subtract(actualTillNovFromDecAmountExp);
                    budgFormDecTOMarAmountExp = budgFormDecTOMarAmountExp.setScale(2, RoundingMode.CEILING);
                    opBalMapExp.put(MainetConstants.BUDGETARY_REVISION.BUDGET_EXP_AFTR_TILL_NOV_AMT,
                            actualTillNovFromDecAmountExp.toString());
                    opBalMapExp.put(MainetConstants.BUDGETARY_REVISION.BUDGET_EXP_DEC_TO_MAR_AMT,
                            budgFormDecTOMarAmountExp.toString());
                }
            }
        } else {
            BigDecimal defaultOrgvalue = new BigDecimal(0);
            defaultOrgvalue = defaultOrgvalue.setScale(2, RoundingMode.CEILING);
            BigDecimal budgFormDecTOMarAmountExp = defaultOrgvalue.subtract(actualTillNovFromDecAmountExp);
            budgFormDecTOMarAmountExp = budgFormDecTOMarAmountExp.setScale(2, RoundingMode.CEILING);
            opBalMapExp.put(MainetConstants.BUDGETARY_REVISION.BUDGET_EXP_AFTR_TILL_NOV_AMT,
                    actualTillNovFromDecAmountExp.toString());
            opBalMapExp.put(MainetConstants.BUDGETARY_REVISION.BUDGET_EXP_DEC_TO_MAR_AMT, budgFormDecTOMarAmountExp.toString());
        }
        return opBalMapExp;
    }

    @RequestMapping(params = "getOrgBalRevDuplicateGridloadData", method = RequestMethod.POST)
    public @ResponseBody boolean findBudgetAllocationRevDuplicateCombination(final AccountBudgetoryRevisionBean bean,
            final HttpServletRequest request, final Model model, final BindingResult bindingResult,
            @RequestParam("cnt") final int cnt) {
        boolean isValidationError = false;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long faYearid = bean.getFaYearid();
        final String prRevBudgetCode = bean.getBugprojRevBeanList().get(cnt).getPrRevBudgetCode()
                .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK);
        if (accountBudgetoryRevisionService.isBudgetoryRevisionEntryExists(faYearid, Long.valueOf(prRevBudgetCode), orgId)) {
            bindingResult.addError(
                    new org.springframework.validation.FieldError(MainetConstants.BUDGETARY_REVISION.BUDGETORY_REVISION_MASTER,
                            MainetConstants.CommonConstant.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                            ApplicationSession.getInstance().getMessage(MainetConstants.CommonConstant.BLANK)));
            isValidationError = true;
        }
        return isValidationError;
    }

    @RequestMapping(params = "getOrgBalExpDuplicateGridloadData", method = RequestMethod.POST)
    public @ResponseBody boolean findBudgetAllocationExpDuplicateCombination(final AccountBudgetoryRevisionBean bean,
            final HttpServletRequest request, final Model model, final BindingResult bindingResult,
            @RequestParam("cont") final int cont) {
        boolean isValidationError = false;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long faYearid = bean.getFaYearid();
        final String prExpBudgetCode = bean.getBugprojExpBeanList().get(cont).getPrExpBudgetCode()
                .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK);
        if (accountBudgetoryRevisionService.isBudgetoryRevisionEntryExists(faYearid, Long.valueOf(prExpBudgetCode), orgId)) {
            bindingResult.addError(
                    new org.springframework.validation.FieldError(MainetConstants.BUDGETARY_REVISION.BUDGETORY_REVISION_MASTER,
                            MainetConstants.CommonConstant.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                            ApplicationSession.getInstance().getMessage(MainetConstants.CommonConstant.BLANK)));
            isValidationError = true;
        }
        return isValidationError;
    }

    @RequestMapping(params = "getReappDynamicRevPrimarykeyIdDetails", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> findBudgetReappDynamicRevenueAllocationPrimaryIdData(
            final AccountBudgetoryRevisionBean bean,
            final HttpServletRequest request, final Model model, @RequestParam("cnt") final int cnt) {
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
    public @ResponseBody Map<Long, String> findBudgetExpenditureAllocationPrimaryIdData(final AccountBudgetoryRevisionBean bean,
            final HttpServletRequest request, final Model model, @RequestParam("cont") final int cont) {
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
        String result = MainetConstants.CommonConstant.BLANK;
        chList = new ArrayList<>();
        chList.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final String faYearid = UserSession.getCurrent().getFinYearId();
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final Map<Long, String> fundMap = new LinkedHashMap<>();
        final Map<Long, String> functionMap = new LinkedHashMap<>();
        if ((faYearid != null) && !faYearid.isEmpty()) {
            chList = accountBudgetoryRevisionService.findByFinancialId(orgId);
            String tansAmount = null;
            for (final AccountBudgetoryRevisionBean bean : chList) {
                final Long prBudgetCodeId = bean.getPrBudgetCodeid();
                final List<Object[]> fundCode = accountBudgetCodeService.getFundCode(prBudgetCodeId, orgId);
                if (fundCode != null) {
                    for (final Object[] objects : fundCode) {
                        if ((objects[0] != null) && (objects[1] != null) && (objects[2] != null)) {
                            fundMap.put((Long) objects[0],
                                    objects[1].toString() + MainetConstants.SEPARATOR + objects[2].toString());
                        }
                    }
                }
                final List<Object[]> functionCode = accountBudgetCodeService.getFunctionCode(prBudgetCodeId, orgId);
                for (final Object[] objects : functionCode) {
                    if ((objects[0] != null) && (objects[1] != null) && (objects[2] != null)) {
                        functionMap.put((Long) objects[0],
                                objects[1].toString() + MainetConstants.SEPARATOR + objects[2].toString());
                    }
                }
                final Long prBudgetCodeid = bean.getPrBudgetCodeid();
                final String budgetCode = accountBudgetCodeService.getBudgetCode(prBudgetCodeid, orgId);
                bean.setPrBudgetCode(budgetCode);

                if (bean.getRevisedAmount() != null) {
                    tansAmount = CommonMasterUtility.getAmountInIndianCurrency(bean.getRevisedAmount());
                    // bean.setRevisedAmountDup(tansAmount);
                }
                final Long cpdBugtypeId = bean.getCpdBugtypeId();
                if (cpdBugtypeId.equals(revenueLookup.getLookUpId())) {
                    final List<Object[]> orgEstimateAmtDetails = accountBudgetProjectedRevenueEntryService
                            .findByRenueOrgAmount(Long.valueOf(faYearid), prBudgetCodeid, orgId);
                    if (!orgEstimateAmtDetails.isEmpty()) {
                        for (final Object[] objects : orgEstimateAmtDetails) {
                            if (objects[0] != null) {
                                bean.setRevisedAmountDup(CommonMasterUtility
                                        .getAmountInIndianCurrency(new BigDecimal(objects[0].toString())));
                            }
                            BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
                            originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
                            final String displayoriginalEstAmount = CommonMasterUtility
                                    .getAmountInIndianCurrency(originalEstAmount);
                            bean.setOrginalEstamt(displayoriginalEstAmount.toString());
                        }
                    }
                }
                if (cpdBugtypeId.equals(expLookup.getLookUpId())) {
                    final List<Object[]> orgEstimateAmtDetails = accountBudgetProjectedExpenditureService
                            .findByExpOrgAmount(Long.valueOf(faYearid), prBudgetCodeid, orgId);
                    if (!orgEstimateAmtDetails.isEmpty()) {
                        for (final Object[] objects : orgEstimateAmtDetails) {
                            if (objects[0] != null) {
                                bean.setRevisedAmountDup(CommonMasterUtility
                                        .getAmountInIndianCurrency(new BigDecimal(objects[0].toString())));
                            }
                            BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
                            originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
                            final String displayoriginalEstAmount = CommonMasterUtility
                                    .getAmountInIndianCurrency(originalEstAmount);
                            bean.setOrginalEstamt(displayoriginalEstAmount.toString());
                        }
                    }
                }
            }
        }
        final AccountBudgetoryRevisionBean bean = new AccountBudgetoryRevisionBean();
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        model.addAttribute(MainetConstants.BUDGETARY_REVISION.FUND_MAP, fundMap);
        model.addAttribute(MainetConstants.BUDGETARY_REVISION.FUNCTION_MAP, functionMap);
        model.addAttribute(MAIN_LIST_NAME, chList);
        result = JSP_LIST;
        return result;
    }

    @RequestMapping(params = "form")
    public String formForCreate(final Model model) {
        String result = MainetConstants.CommonConstant.BLANK;
        final AccountBudgetoryRevisionBean bean = new AccountBudgetoryRevisionBean();
        bean.setTempDate(Utility.dateToString(new Date()));
        populateModel(model, bean, FormMode.CREATE);
        result = JSP_FORM;
        return result;
    }

    private void populateModel(final Model model, final AccountBudgetoryRevisionBean bean, final FormMode formMode) {
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
    public String create(final AccountBudgetoryRevisionBean tbAcBudgetoryRevision, final BindingResult bindingResult,
            final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws Exception {
        String result = MainetConstants.CommonConstant.BLANK;
        if (!bindingResult.hasErrors()) {
            tbAcBudgetoryRevision.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
            final UserSession userSession = UserSession.getCurrent();
            final int languageId = userSession.getLanguageId();
            final Organisation org = UserSession.getCurrent().getOrganisation();
            tbAcBudgetoryRevision.setOrgid(userSession.getOrganisation().getOrgid());
            tbAcBudgetoryRevision.setLangId(userSession.getLanguageId());
            tbAcBudgetoryRevision.setCreatedBy(userSession.getEmployee().getEmpId());
            tbAcBudgetoryRevision.setCreatedDate(new Date());
            tbAcBudgetoryRevision.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
            populateModel(model, tbAcBudgetoryRevision, FormMode.CREATE);
            AccountBudgetoryRevisionBean tbAcBudgetoryRevisionCreated = accountBudgetoryRevisionService
                    .saveBudgetoryRevisionFormData(tbAcBudgetoryRevision, languageId, org);
            if (tbAcBudgetoryRevisionCreated == null) {
                tbAcBudgetoryRevisionCreated = new AccountBudgetoryRevisionBean();
            }
            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetoryRevisionCreated);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            if (tbAcBudgetoryRevision.getBugrevId() == null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                ApplicationSession.getInstance().getMessage("accounts.fieldmaster.success"));
            }
            if (tbAcBudgetoryRevision.getBugrevId() != null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                ApplicationSession.getInstance().getMessage("accounts.fieldmaster.update"));
            }
            result = JSP_FORM;
        } else {
            tbAcBudgetoryRevision.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            populateModel(model, tbAcBudgetoryRevision, FormMode.CREATE);
            result = JSP_FORM;
        }
        return result;
    }

    @RequestMapping(params = "formforupdate", method = RequestMethod.POST)
    public String formforupdate(AccountBudgetoryRevisionBean tbAcBudgetoryRevision, @RequestParam("bugrevId") final Long bugrevId,
            @RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {
        String result = MainetConstants.CommonConstant.BLANK;
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());

        if (!bindingResult.hasErrors()) {
            if (viewmode.equals(MainetConstants.EDIT)) {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.EDIT);
            } else {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
            }
            setModeView(viewmode);
            final UserSession userSession = UserSession.getCurrent();
            final int languageId = userSession.getLanguageId();
            final Organisation org = UserSession.getCurrent().getOrganisation();
            tbAcBudgetoryRevision = accountBudgetoryRevisionService.getDetailsUsingBudgetoryRevisionId(tbAcBudgetoryRevision,
                    languageId, org);
            final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            if (tbAcBudgetoryRevision.getFaYearid() != null) {
                final Long cpdBugtypeid = tbAcBudgetoryRevision.getCpdBugtypeId();
                final Long dpDeptid = tbAcBudgetoryRevision.getDpDeptid();
                if (dpDeptid != null && cpdBugtypeid != null) {
                    if (revenueLookup.getLookUpId() == cpdBugtypeid) {
                        Map<Long, String> budgetCode = new LinkedHashMap<>();
                        budgetCode = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeads(orgId);
                        model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.REV_BUDGET_CODE_MAP, budgetCode);
                    }
                    if (expLookup.getLookUpId() == cpdBugtypeid) {
                        Map<Long, String> budgetCode = new LinkedHashMap<>();
                        budgetCode = accountBudgetProjectedExpenditureService.findByAllExpBudgetHeads(orgId);
                        model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.EXP_BUDGET_CODE_MAP, budgetCode);
                    }
                }
            }
            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetoryRevision);
            populateModel(model, tbAcBudgetoryRevision, FormMode.UPDATE);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            result = JSP_FORM;
        } else {
            log("tbAcBudgetoryRevision 'formforupdate' : binding errors");
            populateModel(model, tbAcBudgetoryRevision, FormMode.UPDATE);
            result = JSP_FORM;
        }
        return result;
    }

    @RequestMapping(params = "update", method = RequestMethod.POST)
    public String update(AccountBudgetoryRevisionBean tbAcBudgetoryRevision, @RequestParam("bugrevId") final Long bugrevId,
            @RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {
        String result = MainetConstants.CommonConstant.BLANK;
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        if (!bindingResult.hasErrors()) {
            if (viewmode.equals(MainetConstants.VIEW)) {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.VIEW);
            } else {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
            }
            setModeView(viewmode);
            final UserSession userSession = UserSession.getCurrent();
            final int languageId = userSession.getLanguageId();
            final Organisation org = UserSession.getCurrent().getOrganisation();
            tbAcBudgetoryRevision.setBugrevId(bugrevId);
            tbAcBudgetoryRevision = accountBudgetoryRevisionService.getDetailsUsingBudgetoryRevisionId(tbAcBudgetoryRevision,
                    languageId, org);
            if (tbAcBudgetoryRevision.getFaYearid() != null) {
                tbAcBudgetoryRevision
                        .setFinancialYearDesc(tbFinancialyearService.findFinancialYearDesc(tbAcBudgetoryRevision.getFaYearid()));
            }
            final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            if (tbAcBudgetoryRevision.getFaYearid() != null) {
                final Long cpdBugtypeid = tbAcBudgetoryRevision.getCpdBugtypeId();
                final Long dpDeptid = tbAcBudgetoryRevision.getDpDeptid();
                if (dpDeptid != null && cpdBugtypeid != null) {
                    if (revenueLookup.getLookUpId() == cpdBugtypeid) {
                        Map<Long, String> budgetCode = new LinkedHashMap<>();
                        budgetCode = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeads(orgId);
                        model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.REV_BUDGET_CODE_MAP, budgetCode);
                    }
                    if (expLookup.getLookUpId() == cpdBugtypeid) {
                        Map<Long, String> budgetCode = new LinkedHashMap<>();
                        budgetCode = accountBudgetProjectedExpenditureService.findByAllExpBudgetHeads(orgId);
                        model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.EXP_BUDGET_CODE_MAP, budgetCode);
                    }
                }
            }
            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetoryRevision);
            populateModel(model, tbAcBudgetoryRevision, FormMode.UPDATE);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            log("tbAcBudgetoryRevision 'view' : view done - redirect");
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            log("tbAcBudgetoryRevision 'view' : view done - redirect");
            result = JSP_VIEW;
        } else {
            log("tbAcBudgetoryRevision 'view' : binding errors");
            populateModel(model, tbAcBudgetoryRevision, FormMode.UPDATE);
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
