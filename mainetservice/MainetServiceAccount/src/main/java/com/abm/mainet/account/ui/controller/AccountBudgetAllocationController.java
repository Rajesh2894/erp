/**
 * @author prasad.kancharla
 *
 */
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.dto.AccountBudgetAllocationBean;
import com.abm.mainet.account.service.AccountBudgetAllocationService;
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
@RequestMapping("/AccountBudgetAllocation.html")
public class AccountBudgetAllocationController extends AbstractController {

    private static final String MAIN_ENTITY_NAME = "tbAcBudgetAllocation";
    private static final String MAIN_LIST_NAME = "list";
    private static final String JSP_FORM = "tbAcBudgetAllocation/form";
    private static final String JSP_VIEW = "tbAcBudgetAllocation/view";
    private static final String JSP_LIST = "tbAcBudgetAllocation/list";
    private static final String AC_Financialyr_LOOKUPS = "aFinancialYr";
    private String modeView = MainetConstants.BLANK;

    @Resource
    private AccountBudgetProjectedRevenueEntryService accountBudgetProjectedRevenueEntryService;
    @Resource
    private AccountBudgetProjectedExpenditureService accountBudgetProjectedExpenditureService;
    @Resource
    private AccountBudgetAllocationService accountBudgetAllocationService;
    @Resource
    private BudgetCodeService accountBudgetCodeService;
    @Resource
    private TbFinancialyearService tbFinancialyearService;
    @Autowired
    private DepartmentService departmentService;
    @Resource
    private SecondaryheadMasterService secondaryheadMasterService;

    private List<AccountBudgetAllocationBean> chList = null;

    public AccountBudgetAllocationController() {
        super(AccountBudgetAllocationController.class, MAIN_ENTITY_NAME);
        log("AccountBudgetAllocationController created.");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(final HttpServletRequest request,
            final Model model) {
        log("AccountBudgetAllocationController-'gridData' : 'Get grid Data'");
        final JQGridResponse response = new JQGridResponse<>();
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        response.setRows(chList);
        response.setTotal(chList.size());
        response.setRecords(chList.size());
        response.setPage(page);
        model.addAttribute(MAIN_LIST_NAME, chList);
        return response;
    }

    @RequestMapping()
    public String getList(final Model model) {
        log("AccountBudgetAllocationController-'list' :'list'");
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
            chList = accountBudgetAllocationService.findBudgetAllocationByFinYearId(Long.valueOf(faYearid), orgId);
            for (final AccountBudgetAllocationBean bean : chList) {
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

                final Long cpdBugtypeid = bean.getCpdBugtypeId();
                if (cpdBugtypeid.equals(revenueLookup.getLookUpId())) {
                    final List<Object[]> orgEstimateAmtDetails = accountBudgetProjectedRevenueEntryService
                            .findByRenueOrgAmount(Long.valueOf(faYearid), prBudgetCodeid, orgId);
                    if (!orgEstimateAmtDetails.isEmpty()) {
                        for (final Object[] objects : orgEstimateAmtDetails) {
                            if (objects[0] == null) {
                                BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
                                originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
                                final String displayoriginalEstAmount = CommonMasterUtility
                                        .getAmountInIndianCurrency(originalEstAmount);
                                bean.setOrginalEstamt(displayoriginalEstAmount.toString());
                            }
                            if (objects[0] != null) {
                                BigDecimal revisedEsmtAmount = new BigDecimal(objects[0].toString());
                                revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);
                                final String displayrevisedEsmtAmount = CommonMasterUtility
                                        .getAmountInIndianCurrency(revisedEsmtAmount);
                                bean.setOrginalEstamt(displayrevisedEsmtAmount.toString());
                            }
                        }
                    }
                }
                if (cpdBugtypeid.equals(expLookup.getLookUpId())) {
                    final List<Object[]> orgEstimateAmtDetails = accountBudgetProjectedExpenditureService
                            .findByExpOrgAmount(Long.valueOf(faYearid), prBudgetCodeid, orgId);
                    if (!orgEstimateAmtDetails.isEmpty()) {
                        for (final Object[] objects : orgEstimateAmtDetails) {
                            if (objects[0] == null) {
                                BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
                                originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
                                final String displayoriginalEstAmount = CommonMasterUtility
                                        .getAmountInIndianCurrency(originalEstAmount);
                                bean.setOrginalEstamt(displayoriginalEstAmount.toString());
                            }
                            if (objects[0] != null) {
                                BigDecimal revisedEsmtAmount = new BigDecimal(objects[0].toString());
                                revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);
                                final String displayrevisedEsmtAmount = CommonMasterUtility
                                        .getAmountInIndianCurrency(revisedEsmtAmount);
                                bean.setOrginalEstamt(displayrevisedEsmtAmount.toString());
                            }
                        }
                    }
                }

                final BigDecimal allocaionPer = bean.getReleasePer();
                BigDecimal orgEsmtAmt = new BigDecimal(
                        bean.getOrginalEstamt().replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK));
                orgEsmtAmt = orgEsmtAmt.setScale(2, RoundingMode.CEILING);
                final BigDecimal divDefValue = new BigDecimal(100);
                final BigDecimal amount = ((orgEsmtAmt.multiply(allocaionPer)).divide(divDefValue));
                final String displayAmount = CommonMasterUtility.getAmountInIndianCurrency(amount);
                bean.setAmount(displayAmount);
            }
        }
        final AccountBudgetAllocationBean bean = new AccountBudgetAllocationBean();
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FUND_MAP, fundMap);
        model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FUNCTION_MAP, functionMap);
        model.addAttribute(MAIN_LIST_NAME, chList);
        result = JSP_LIST;
        return result;
    }

    @RequestMapping(params = "form")
    public String formForCreate(final Model model) {
        log("AccountBudgetAllocationController-'formForCreate' : 'formForCreate'");
        String result = MainetConstants.CommonConstant.BLANK;

        final AccountBudgetAllocationBean bean = new AccountBudgetAllocationBean();
        bean.setTempDate(Utility.dateToString(new Date()));
        populateModel(model, bean, FormMode.CREATE);
        result = JSP_FORM;
        return result;
    }

    @RequestMapping(params = "getjqGridsearch")
    public @ResponseBody List getCheqAllocationData(final HttpServletRequest request, final Model model,
            @RequestParam("faYearid") final Long faYearid, @RequestParam("cpdBugtypeId") final Long cpdBugtypeId,
            @RequestParam("dpDeptid") final Long dpDeptid, @RequestParam("prBudgetCodeid") Long prBudgetCodeid,
            @RequestParam("fundId") final Long fundId, @RequestParam("functionId") final Long functionId) {
        log("AccountBudgetAllocationController-'getjqGridsearch' : 'get jqGrid search data'");

        chList = new ArrayList<>();
        chList.clear();
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

        Map<String, String> primaryIdAllData = new LinkedHashMap<>();
        primaryIdAllData = accountBudgetAllocationService.findBudgetIdCodeFromBudgetAllocation(faYearid, fundId, functionId,
                cpdBugtypeId,
                prBudgetCodeid, dpDeptid, orgId);
        for (final String key : primaryIdAllData.keySet()) {
            prBudgetCodeid = Long.valueOf(key);
            final List<AccountBudgetAllocationBean> chListA = accountBudgetAllocationService.findByGridAllData(faYearid,
                    cpdBugtypeId,
                    dpDeptid, prBudgetCodeid, orgId);
            chList.addAll(chListA);
            for (final AccountBudgetAllocationBean bean : chList) {
                final Long prBudgetCodeId = bean.getPrBudgetCodeid();
                final String budgetCode = accountBudgetCodeService.getBudgetCode(prBudgetCodeId, orgId);
                bean.setPrBudgetCode(budgetCode);

                final Long cpdBugtypeid = bean.getCpdBugtypeId();
                if (cpdBugtypeid.equals(revenueLookup.getLookUpId())) {
                    final List<Object[]> orgEstimateAmtDetails = accountBudgetProjectedRevenueEntryService
                            .findByRenueOrgAmount(Long.valueOf(faYearid), prBudgetCodeid, orgId);
                    if (!orgEstimateAmtDetails.isEmpty()) {
                        for (final Object[] objects : orgEstimateAmtDetails) {
                            if (objects[0] == null) {
                                BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
                                originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
                                final String displayoriginalEstAmount = CommonMasterUtility
                                        .getAmountInIndianCurrency(originalEstAmount);
                                bean.setOrginalEstamt(displayoriginalEstAmount.toString());
                            }
                            if (objects[0] != null) {
                                BigDecimal revisedEsmtAmount = new BigDecimal(objects[0].toString());
                                revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);
                                final String displayrevisedEsmtAmount = CommonMasterUtility
                                        .getAmountInIndianCurrency(revisedEsmtAmount);
                                bean.setOrginalEstamt(displayrevisedEsmtAmount.toString());
                            }
                        }
                    }
                }
                if (cpdBugtypeid.equals(expLookup.getLookUpId())) {
                    final List<Object[]> orgEstimateAmtDetails = accountBudgetProjectedExpenditureService
                            .findByExpOrgAmount(Long.valueOf(faYearid), prBudgetCodeid, orgId);
                    if (!orgEstimateAmtDetails.isEmpty()) {
                        for (final Object[] objects : orgEstimateAmtDetails) {
                            if (objects[0] == null) {
                                BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
                                originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
                                final String displayoriginalEstAmount = CommonMasterUtility
                                        .getAmountInIndianCurrency(originalEstAmount);
                                bean.setOrginalEstamt(displayoriginalEstAmount.toString());
                            }
                            if (objects[0] != null) {
                                BigDecimal revisedEsmtAmount = new BigDecimal(objects[0].toString());
                                revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);
                                final String displayrevisedEsmtAmount = CommonMasterUtility
                                        .getAmountInIndianCurrency(revisedEsmtAmount);
                                bean.setOrginalEstamt(displayrevisedEsmtAmount.toString());
                            }
                        }
                    }
                }

                final BigDecimal allocaionPer = bean.getReleasePer();
                BigDecimal orgEsmtAmt = new BigDecimal(
                        bean.getOrginalEstamt().replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK));
                orgEsmtAmt = orgEsmtAmt.setScale(2, RoundingMode.CEILING);
                final BigDecimal divDefValue = new BigDecimal(100);
                final BigDecimal amount = ((orgEsmtAmt.multiply(allocaionPer)).divide(divDefValue));
                final String displayAmount = CommonMasterUtility.getAmountInIndianCurrency(amount);
                bean.setAmount(displayAmount);
            }
        }
        return chList;
    }

    @RequestMapping(params = "getjqGridAllocationload", method = RequestMethod.POST)
    public String loadBudgetAllocationData(final AccountBudgetAllocationBean bean, final Model model,
            final RedirectAttributes redirectAttributes,
            final HttpServletRequest request, final BindingResult bindingResult) throws Exception {
        String result = MainetConstants.CommonConstant.BLANK;

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        if (bean.getFaYearid() != null) {
            final Long faYearid = bean.getFaYearid();
            final Long cpdBugtypeid = bean.getCpdBugtypeId();
            final Long cpdBugsubtypeId = bean.getCpdBugsubtypeId();
            final Long dpDeptid = bean.getDpDeptid();
            if (cpdBugtypeid != null) {
                if (revenueLookup.getLookUpId() == cpdBugtypeid) {
                    final List<Object[]> prBudgetCodeids = accountBudgetProjectedRevenueEntryService
                            .findBudgetCodeIdFromBudgetProjectedRevenueEntry(faYearid,
                                    cpdBugsubtypeId, dpDeptid, orgId);
                    if (!prBudgetCodeids.isEmpty()) {
                        final Map<String, String> budgetCode = new LinkedHashMap<>();
                        for (final Object objects : prBudgetCodeids) {
                            final Long prBudgetCodeid = Long.valueOf(objects.toString());
                            budgetCode.putAll(accountBudgetCodeService.getAllBudgetCodes(prBudgetCodeid, orgId));
                        }
                        model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.REV_BUDGET_CODE_MAP, budgetCode);
                    }
                }
                if (expLookup.getLookUpId() == cpdBugtypeid) {
                    final List<Object[]> prBudgetCodeids = accountBudgetProjectedExpenditureService.findBudgetProjExpBudgetCodes(
                            faYearid,
                            cpdBugsubtypeId, dpDeptid, orgId);
                    if (!prBudgetCodeids.isEmpty()) {
                        final Map<String, String> budgetCode = new LinkedHashMap<>();
                        for (final Object objects : prBudgetCodeids) {
                            final Long prBudgetCodeid = Long.valueOf(objects.toString());
                            budgetCode.putAll(accountBudgetCodeService.getAllBudgetCodes(prBudgetCodeid, orgId));
                        }
                        model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.EXP_BUDGET_CODE_MAP, budgetCode);
                    }
                }
            }
        }
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        result = JSP_FORM;
        return result;
    }

    @RequestMapping(params = "getOrgBalGridload", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> findBudgetReappropriationAmountData1(final AccountBudgetAllocationBean bean,
            final HttpServletRequest request, final Model model, @RequestParam("cnt") final int cnt) {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long faYearid = bean.getFaYearid();
        final String budgCodeid = bean.getBugprojRevBeanList().get(cnt).getPrRevBudgetCode().replace(
                MainetConstants.operator.COMMA,
                MainetConstants.CommonConstant.BLANK);
        final Map<String, String> opBalMap = new HashMap<>();
        final List<Object[]> OrgEsmtAmount = accountBudgetProjectedRevenueEntryService.findByRenueOrgAmount(faYearid,
                Long.valueOf(budgCodeid), orgId);
        if (!OrgEsmtAmount.isEmpty()) {
            for (final Object[] objects : OrgEsmtAmount) {
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
        }
        return opBalMap;
    }

    @RequestMapping(params = "getOrgBalExpGridload", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> findBudgetReappropriationExpAmountData(final AccountBudgetAllocationBean bean,
            final HttpServletRequest request, final Model model, @RequestParam("cont") final int cont) {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long faYearid = bean.getFaYearid();
        final String budgCodeid = bean.getBugprojExpBeanList().get(cont).getPrExpBudgetCode().replace(
                MainetConstants.operator.COMMA,
                MainetConstants.CommonConstant.BLANK);
        final Map<String, String> opnExpBalMap = new HashMap<>();
        final List<Object[]> OrgEsmtAmount = accountBudgetProjectedExpenditureService.findByExpOrgAmount(faYearid,
                Long.valueOf(budgCodeid), orgId);
        if (!OrgEsmtAmount.isEmpty()) {
            for (final Object[] objects : OrgEsmtAmount) {
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
        }
        return opnExpBalMap;
    }

    @RequestMapping(params = "getReappDynamicRevPrimarykeyIdDetails", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> findBudgetReappDynamicRevenueAllocationPrimaryIdData(
            final AccountBudgetAllocationBean bean,
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
                    final Long PrimaryKeyId = Long.valueOf(objects[3].toString());
                    revBalMap.put(PrimaryKeyId, objects[3].toString());
                }
            }
        }
        return revBalMap;
    }

    @RequestMapping(params = "getExpPrimarykeyIdDetails", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> findBudgetExpenditureAllocationPrimaryIdData(final AccountBudgetAllocationBean bean,
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
                    final Long PrimaryKeyId = Long.valueOf(objects[3].toString());
                    expBalMap.put(PrimaryKeyId, objects[3].toString());
                }
            }
        }
        return expBalMap;
    }

    @RequestMapping(params = "getOrgBalRevDuplicateGridloadData", method = RequestMethod.POST)
    public @ResponseBody boolean findBudgetAllocationRevDuplicateCombination(final AccountBudgetAllocationBean bean,
            final HttpServletRequest request, final Model model, final BindingResult bindingResult,
            @RequestParam("cnt") final int cnt) {
        boolean isValidationError = false;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long faYearid = bean.getFaYearid();
        final String prRevBudgetCode = bean.getBugprojRevBeanList().get(cnt).getPrRevBudgetCode()
                .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK);
        if (accountBudgetAllocationService.isBudgetAllocationEntryExists(faYearid, Long.valueOf(prRevBudgetCode), orgId)) {
            bindingResult.addError(new org.springframework.validation.FieldError(
                    MainetConstants.BUDGET_ALLOCATION_MASTER.BUDGET_ALLOCATION_MASTER, MainetConstants.CommonConstant.BLANK, null,
                    false, new String[] { MainetConstants.ERRORS }, null,
                    ApplicationSession.getInstance().getMessage(MainetConstants.CommonConstant.BLANK)));
            isValidationError = true;
        }
        return isValidationError;
    }

    @RequestMapping(params = "getOrgBalExpDuplicateGridloadData", method = RequestMethod.POST)
    public @ResponseBody boolean findBudgetAllocationExpDuplicateCombination(final AccountBudgetAllocationBean bean,
            final HttpServletRequest request, final Model model, final BindingResult bindingResult,
            @RequestParam("cont") final int cont) {
        boolean isValidationError = false;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long faYearid = bean.getFaYearid();
        final String prExpBudgetCode = bean.getBugprojExpBeanList().get(cont).getPrExpBudgetCode()
                .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK);
        if (accountBudgetAllocationService.isBudgetAllocationEntryExists(faYearid, Long.valueOf(prExpBudgetCode), orgId)) {
            bindingResult.addError(new org.springframework.validation.FieldError(
                    MainetConstants.BUDGET_ALLOCATION_MASTER.BUDGET_ALLOCATION_MASTER, MainetConstants.CommonConstant.BLANK, null,
                    false, new String[] { MainetConstants.ERRORS }, null,
                    ApplicationSession.getInstance().getMessage(MainetConstants.CommonConstant.BLANK)));
            isValidationError = true;
        }
        return isValidationError;
    }

    private void populateModel(final Model model, final AccountBudgetAllocationBean bean, final FormMode formMode) {
        log("AccountBudgetAllocationController-'populateModel' : populate model");
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        int langId = UserSession.getCurrent().getLanguageId();
        final List<LookUp> levelMap = CommonMasterUtility.getListLookup(PrefixConstants.PREFIX,
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.LEVELS_MAP, levelMap);
        final List<LookUp> bugSubTypelevelMap = CommonMasterUtility.getListLookup(PrefixConstants.BUG_SUB_PREFIX,
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.BUG_SUB_TYPE_LEVEL_MAP, bugSubTypelevelMap);
        final Map<Long, String> deptMap = new HashMap<>(0);
        List<Object[]> department = null;
        department = departmentService.getAllDeptTypeNames();
        for (final Object[] dep : department) {
            if (dep[0] != null) {
                deptMap.put((Long) (dep[0]), (String) dep[1]);
            }
        }
        model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.DEPT_MAP, deptMap);
        model.addAttribute(MAIN_ENTITY_NAME, bean);

        model.addAttribute(AC_Financialyr_LOOKUPS, secondaryheadMasterService.getAllFinincialYear(orgId, langId));

        if (formMode == FormMode.CREATE) {

            final Map<Long, String> financeMap = tbFinancialyearService.getAllFinincialYearByStatusWise(orgId);
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FINANCE_MAP, financeMap);

            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE,
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE_CREATE);
            model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, false);
        } else if (formMode == FormMode.UPDATE) {

            final Map<Long, String> financeMap = tbFinancialyearService.getAllFinincialYear();
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FINANCE_MAP, financeMap);

            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE,
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE_UPDATE);
        }
    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String create(final AccountBudgetAllocationBean tbAcBudgetAllocation, final BindingResult bindingResult,
            final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws ParseException {
        log("AccountBudgetAllocationBean-'create' : 'create'");
        String result = MainetConstants.CommonConstant.BLANK;

        if (!bindingResult.hasErrors()) {
            tbAcBudgetAllocation.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
            final UserSession userSession = UserSession.getCurrent();
            final int LanguageId = userSession.getLanguageId();
            final Organisation Organisation = UserSession.getCurrent().getOrganisation();
            tbAcBudgetAllocation.setOrgid(userSession.getOrganisation().getOrgid());
            tbAcBudgetAllocation.setLangId(userSession.getLanguageId());
            tbAcBudgetAllocation.setCreatedBy(userSession.getEmployee().getEmpId());
            tbAcBudgetAllocation.setCreatedDate(new Date());
            tbAcBudgetAllocation.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
            AccountBudgetAllocationBean tbAcBudgetAllocationCreated = accountBudgetAllocationService
                    .saveBudgetAllocationFormData(tbAcBudgetAllocation, LanguageId, Organisation);
            if (tbAcBudgetAllocationCreated == null) {
                tbAcBudgetAllocationCreated = new AccountBudgetAllocationBean();
            }
            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetAllocationCreated);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            if (tbAcBudgetAllocation.getBaId() == null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        MainetConstants.AccountBudgetAdditionalSupplemental.RECORD_SAVE_SUCCESS);
            }
            if (tbAcBudgetAllocation.getBaId() != null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        MainetConstants.AccountBudgetAdditionalSupplemental.RECORD_UPDATE_SUCCESS);
            }
            result = JSP_FORM;
        } else {
            tbAcBudgetAllocation.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            result = JSP_FORM;
        }
        return result;
    }

    @RequestMapping(params = "formForUpdate", method = RequestMethod.POST)
    public String formForUpdate(AccountBudgetAllocationBean tbAcBudgetAllocation, @RequestParam("baId") final Long baId,
            @RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) {
        log("tbAcBudgetAllocation-'gridData' : 'formforupdate'");
        String result = MainetConstants.CommonConstant.BLANK;

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
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
            tbAcBudgetAllocation.setBaId(baId);
            final UserSession userSession = UserSession.getCurrent();
            final int LanguageId = userSession.getLanguageId();
            final Organisation Organisation = UserSession.getCurrent().getOrganisation();
            tbAcBudgetAllocation = accountBudgetAllocationService.getDetailsUsingBudgetAllocationId(tbAcBudgetAllocation,
                    LanguageId, Organisation);
            if (tbAcBudgetAllocation.getFaYearid() != null) {
                final Long faYearid = tbAcBudgetAllocation.getFaYearid();
                final Long cpdBugtypeid = tbAcBudgetAllocation.getCpdBugtypeId();
                final Long cpdBugsubtypeId = tbAcBudgetAllocation.getCpdBugsubtypeId();
                final Long dpDeptid = tbAcBudgetAllocation.getDpDeptid();
                if (revenueLookup.getLookUpId() == cpdBugtypeid) {
                    final List<Object[]> prBudgetCodeids = accountBudgetProjectedRevenueEntryService
                            .findBudgetCodeIdFromBudgetProjectedRevenueEntry(faYearid,
                                    cpdBugsubtypeId, dpDeptid, orgId);
                    if (!prBudgetCodeids.isEmpty()) {
                        final Map<String, String> budgetCode = new LinkedHashMap<>();
                        for (final Object objects : prBudgetCodeids) {
                            final Long prBudgetCodeid = Long.valueOf(objects.toString());
                            budgetCode.putAll(accountBudgetCodeService.getAllBudgetCodes(prBudgetCodeid, orgId));
                        }
                        model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.REV_BUDGET_CODE_MAP, budgetCode);
                    }
                }
                if (expLookup.getLookUpId() == cpdBugtypeid) {
                    final List<Object[]> prBudgetCodeids = accountBudgetProjectedExpenditureService.findBudgetProjExpBudgetCodes(
                            faYearid,
                            cpdBugsubtypeId, dpDeptid, orgId);
                    if (!prBudgetCodeids.isEmpty()) {
                        final Map<String, String> budgetCode = new LinkedHashMap<>();
                        for (final Object objects : prBudgetCodeids) {
                            final Long prBudgetCodeid = Long.valueOf(objects.toString());
                            budgetCode.putAll(accountBudgetCodeService.getAllBudgetCodes(prBudgetCodeid, orgId));
                        }
                        model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.EXP_BUDGET_CODE_MAP, budgetCode);
                    }
                }
            }
            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetAllocation);
            populateModel(model, tbAcBudgetAllocation, FormMode.UPDATE);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            log("tbAcBudgetAllocation 'formforupdate' : update done - redirect");
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            log("tbAcBudgetAllocation 'formforupdate' : update done - redirect");
            result = JSP_FORM;
        } else {
            log("tbAcBudgetAllocation 'formforupdate' : binding errors");
            populateModel(model, tbAcBudgetAllocation, FormMode.UPDATE);
            result = JSP_FORM;
        }
        return result;
    }

    @RequestMapping(params = "formForView", method = RequestMethod.POST)
    public String formForView(AccountBudgetAllocationBean tbAcBudgetAllocation, @RequestParam("baId") final Long baId,
            @RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) {
        log("tbAcBudgetAllocation-'gridData' : 'view'");
        String result = MainetConstants.CommonConstant.BLANK;

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
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
            tbAcBudgetAllocation.setBaId(baId);
            final UserSession userSession = UserSession.getCurrent();
            final int LanguageId = userSession.getLanguageId();
            final Organisation Organisation = UserSession.getCurrent().getOrganisation();
            tbAcBudgetAllocation = accountBudgetAllocationService.getDetailsUsingBudgetAllocationId(tbAcBudgetAllocation,
                    LanguageId, Organisation);
            if (tbAcBudgetAllocation.getFaYearid() != null) {
                tbAcBudgetAllocation
                        .setFinancialYearDesc(tbFinancialyearService.findFinancialYearDesc(tbAcBudgetAllocation.getFaYearid()));
            }
            if (tbAcBudgetAllocation.getFaYearid() != null) {
                final Long faYearid = tbAcBudgetAllocation.getFaYearid();
                final Long cpdBugtypeid = tbAcBudgetAllocation.getCpdBugtypeId();
                final Long cpdBugsubtypeId = tbAcBudgetAllocation.getCpdBugsubtypeId();
                final Long dpDeptid = tbAcBudgetAllocation.getDpDeptid();
                if (revenueLookup.getLookUpId() == cpdBugtypeid) {
                    final List<Object[]> prBudgetCodeids = accountBudgetProjectedRevenueEntryService
                            .findBudgetCodeIdFromBudgetProjectedRevenueEntry(faYearid,
                                    cpdBugsubtypeId, dpDeptid, orgId);
                    if (!prBudgetCodeids.isEmpty()) {
                        final Map<String, String> budgetCode = new LinkedHashMap<>();
                        for (final Object objects : prBudgetCodeids) {
                            final Long prBudgetCodeid = Long.valueOf(objects.toString());
                            budgetCode.putAll(accountBudgetCodeService.getAllBudgetCodes(prBudgetCodeid, orgId));
                        }
                        model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.REV_BUDGET_CODE_MAP, budgetCode);
                    }
                }
                if (expLookup.getLookUpId() == cpdBugtypeid) {
                    final List<Object[]> prBudgetCodeids = accountBudgetProjectedExpenditureService.findBudgetProjExpBudgetCodes(
                            faYearid,
                            cpdBugsubtypeId, dpDeptid, orgId);
                    if (!prBudgetCodeids.isEmpty()) {
                        final Map<String, String> budgetCode = new LinkedHashMap<>();
                        for (final Object objects : prBudgetCodeids) {
                            final Long prBudgetCodeid = Long.valueOf(objects.toString());
                            budgetCode.putAll(accountBudgetCodeService.getAllBudgetCodes(prBudgetCodeid, orgId));
                        }
                        model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.EXP_BUDGET_CODE_MAP, budgetCode);
                    }
                }
            }
            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetAllocation);
            populateModel(model, tbAcBudgetAllocation, FormMode.UPDATE);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            log("tbAcBudgetAllocation 'view' : view done - redirect");
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            log("tbAcBudgetAllocation 'view' : view done - redirect");
            result = JSP_VIEW;
        } else {
            log("tbAcBudgetAllocation 'view' : binding errors");
            populateModel(model, tbAcBudgetAllocation, FormMode.UPDATE);
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
