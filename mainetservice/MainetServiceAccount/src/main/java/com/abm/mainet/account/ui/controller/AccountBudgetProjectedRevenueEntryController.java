package com.abm.mainet.account.ui.controller;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.domain.AccountBudgetProjectedRevenueEntryEntity;
import com.abm.mainet.account.dto.AccountBudgetProjectedRevenueEntryBean;
import com.abm.mainet.account.dto.AccountBudgetProjectedRevenueEntryUploadDto;
import com.abm.mainet.account.dto.ReadExcelData;
import com.abm.mainet.account.service.AccountBudgetProjectedRevenueEntryService;
import com.abm.mainet.account.service.BudgetCodeService;
import com.abm.mainet.account.ui.validator.AccountBudgetProjectedRevenueEntryExcelValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.AccountFieldMasterBean;
import com.abm.mainet.common.integration.acccount.dto.TbAcCodingstructureMas;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountFunctionMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.upload.excel.WriteExcelData;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Message;
import com.abm.mainet.common.utility.MessageType;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("/AccountBudgetProjectedRevenueEntry.html")
public class AccountBudgetProjectedRevenueEntryController extends AbstractController {

    private static final String MAIN_ENTITY_NAME = "tbAcBudgetProjectedRevenueEntry";
    private static final String MAIN_LIST_NAME = "list";
    private static final String JSP_FORM = "tbAcBudgetProjectedRevenueEntry/form";
    private static final String JSP_VIEW = "tbAcBudgetProjectedRevenueEntry/view";
    private static final String JSP_LIST = "tbAcBudgetProjectedRevenueEntry/list";
    private static final String JSP_EXCELUPLOAD = "tbAcBudgetProjectedRevenueEntry/excelupload";
    private static final String AC_Financialyr_LOOKUPS = "aFinancialYr";
    private String modeView = MainetConstants.BLANK;

    @Resource
    private AccountBudgetProjectedRevenueEntryService accountBudgetProjectedRevenueEntryService;
    @Resource
    private BudgetCodeService accountBudgetCodeService;
    @Resource
    private TbFinancialyearService tbFinancialyearService;
    @Autowired
    private DepartmentService departmentService;
    @Resource
    private SecondaryheadMasterService secondaryheadMasterService;
    @Resource
    private TbAcCodingstructureMasService tbAcCodingstructureMasService;
    @Resource
    private TbOrganisationService tbOrganisationService;
    @Resource
    private AccountFunctionMasterService tbAcFunctionMasterService;
    @Resource
    private AccountFieldMasterService tbAcFieldMasterService;
    @Autowired
    private IFileUploadService fileUpload;
    private List<AccountBudgetProjectedRevenueEntryBean> chList = null;

    public AccountBudgetProjectedRevenueEntryController() {
        super(AccountBudgetProjectedRevenueEntryController.class, MAIN_ENTITY_NAME);
        log("AccountBudgetProjectedRevenueEntryController created.");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(final HttpServletRequest request,
            final Model model) {
        log("AccountBudgetProjectedRevenueEntry-'gridData' : 'Get grid Data'");
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
            @RequestParam("cpdBugsubtypeId") final Long cpdBugsubtypeId, @RequestParam("dpDeptid") final Long dpDeptid,
            @RequestParam("prBudgetCodeid") Long prBudgetCodeid, @RequestParam("fundId") final Long fundId,
            @RequestParam("functionId") final Long functionId, @RequestParam("fieldId") final Long fieldId ) {
        log("AccountBudgetProjectedRevenueEntry-'getjqGridsearch' : 'get jqGrid search data'");

        chList = new ArrayList<>();
        chList.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        chList = accountBudgetProjectedRevenueEntryService.findByGridAllData(faYearid, fundId, functionId, cpdBugsubtypeId,
                dpDeptid, prBudgetCodeid, fieldId,orgId);
        if (chList != null) {
            String amount = null;
            String amount1 = null;
            String amount2 = null;
            String amount3 = null;
            for (final AccountBudgetProjectedRevenueEntryBean bean : chList) {
                if ((bean.getPrCollected() != null) && !bean.getPrCollected().isEmpty()) {
                    amount1 = CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(bean.getPrCollected()));
                    bean.setFormattedCurrency1(amount1);
                }
                if ((bean.getOrginalEstamt() != null) && !bean.getOrginalEstamt().isEmpty()) {
                    amount = CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(bean.getOrginalEstamt()));
                    bean.setFormattedCurrency(amount);
                }
                if ((bean.getRevisedEstamt() != null) && !bean.getRevisedEstamt().isEmpty()) {
                    amount3 = CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(bean.getRevisedEstamt()));
                    bean.setFormattedCurrency3(amount3);
                }
                BigDecimal balAmt = null;
                if ((bean.getRevisedEstamt() != null && !bean.getRevisedEstamt().isEmpty())
                        && (bean.getPrCollected() != null && !bean.getPrCollected().isEmpty())) {
                    balAmt = new BigDecimal(bean.getRevisedEstamt()).subtract((new BigDecimal(bean.getPrCollected())));
                } else {
                    balAmt = new BigDecimal(bean.getOrginalEstamt()).subtract((new BigDecimal(bean.getPrCollected())));
                }
                amount2 = CommonMasterUtility.getAmountInIndianCurrency(balAmt);
                bean.setFormattedCurrency2(amount2);
            }
        }
        if(CollectionUtils.isNotEmpty(chList)) {
			if (getSelfDeparmentConfiguration() != null
					&& MainetConstants.Y_FLAG.equalsIgnoreCase(getSelfDeparmentConfiguration())) {
				Long deptId = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid();
				String deptCode = departmentService.getDeptCode(deptId);
				if (!deptCode.equalsIgnoreCase(MainetConstants.AccountConstants.AC.getValue())) {
					chList = chList.stream().filter(p -> p.getDpDeptid() == deptId)
							.collect(Collectors.toList());
				}
			}
        } 
        
        return chList;
    }

    @RequestMapping(params = "getjqGridload", method = RequestMethod.POST)
    public String loadBudgetReappropriationData(final AccountBudgetProjectedRevenueEntryBean bean, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest request, final BindingResult bindingResult)
            throws Exception {
        log("AccountReappropriationMaster-'getjqGridsearch' : 'get jqGridload data'");
        String result = MainetConstants.CommonConstant.BLANK;
        final List<LookUp> fundTypeLevel = CommonMasterUtility.getListLookup(
                MainetConstants.BUDGET_CODE.FUND_FIELD_DEPT_STATUS_PREFIX,
                UserSession.getCurrent().getOrganisation());
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final int langId = UserSession.getCurrent().getLanguageId();
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final Long faYearid = bean.getFaYearid();
        final Long cpdBugtypeid = revenueLookup.getLookUpId();
        final Long cpdBugsubtypeId = bean.getCpdBugsubtypeId();
        final Long dpDeptid = bean.getDpDeptid();
        if (dpDeptid != null) {
            Map<Long, String> budgetCode = null;
            for (final LookUp lookUp : fundTypeLevel) {
                if (MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    budgetCode = accountBudgetCodeService.findByAllRevBudgetHeads(faYearid, cpdBugtypeid, cpdBugsubtypeId,
                            dpDeptid, organisation, langId);
                }
                if (MainetConstants.BUDGET_CODE.OBJECT_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    budgetCode = accountBudgetCodeService.findByAllRevObjectTypeBudgetHeads(faYearid, cpdBugtypeid,
                            cpdBugsubtypeId, dpDeptid, organisation, langId);
                }
            }
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.ACCOUNT_BUDGET_CODE_MAP, budgetCode);
        }
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        result = JSP_FORM;
        return result;
    }

    @RequestMapping(params = "getjqGridFiledload", method = RequestMethod.POST)
    public String loadBudgetExpenditureFieldData(final AccountBudgetProjectedRevenueEntryBean bean, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest request, final BindingResult bindingResult)
            throws Exception {
        log("AccountReappropriationMaster-'getjqGridFiledload' : 'get search Field data'");
        String result = MainetConstants.CommonConstant.BLANK;
        final List<LookUp> fundTypeLevel = CommonMasterUtility.getListLookup(
                MainetConstants.BUDGET_CODE.FUND_FIELD_DEPT_STATUS_PREFIX,
                UserSession.getCurrent().getOrganisation());
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final int langId = UserSession.getCurrent().getLanguageId();
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final Long faYearid = bean.getFaYearid();
        final Long cpdBugtypeid = revenueLookup.getLookUpId();
        final Long cpdBugsubtypeId = bean.getCpdBugsubtypeId();
        final Long dpDeptid = bean.getDpDeptid();
        final Long fieldId = bean.getFieldId();
        if (dpDeptid != null && fieldId != null) {
            Map<Long, String> budgetCode = null;
            for (final LookUp lookUp : fundTypeLevel) {
                if (MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    budgetCode = accountBudgetCodeService.findByAllRevFieldBudgetHeads(faYearid, cpdBugtypeid, cpdBugsubtypeId,
                            dpDeptid, organisation, langId, fieldId);
                }
                if (MainetConstants.BUDGET_CODE.OBJECT_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    budgetCode = accountBudgetCodeService.findByAllRevObjectTypeFieldBudgetHeads(faYearid, cpdBugtypeid,
                            cpdBugsubtypeId, dpDeptid, organisation, langId, fieldId);
                }
            }
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.ACCOUNT_BUDGET_CODE_MAP, budgetCode);
        }
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        result = JSP_FORM;
        return result;
    }

    @RequestMapping(params = "getjqGridFunctionload", method = RequestMethod.POST)
    public String loadBudgetExpenditureFunctionData(final AccountBudgetProjectedRevenueEntryBean bean, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest request, final BindingResult bindingResult)
            throws Exception {
        log("AccountReappropriationMaster-'getjqGridFunctionload' : 'get search Function data'");
        String result = MainetConstants.CommonConstant.BLANK;
        final List<LookUp> fundTypeLevel = CommonMasterUtility.getListLookup(
                MainetConstants.BUDGET_CODE.FUND_FIELD_DEPT_STATUS_PREFIX,
                UserSession.getCurrent().getOrganisation());
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final int langId = UserSession.getCurrent().getLanguageId();
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final Long faYearid = bean.getFaYearid();
        final Long cpdBugtypeid = revenueLookup.getLookUpId();
        final Long cpdBugsubtypeId = bean.getCpdBugsubtypeId();
        final Long dpDeptid = bean.getDpDeptid();
        final Long functionId = bean.getFunctionId();
        if (dpDeptid != null && functionId != null) {
            Map<Long, String> budgetCode = null;
            for (final LookUp lookUp : fundTypeLevel) {
                if (MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    budgetCode = accountBudgetCodeService.findByAllRevFunctionBudgetHeads(faYearid, cpdBugtypeid, cpdBugsubtypeId,
                            dpDeptid, organisation, langId, functionId);
                }
                if (MainetConstants.BUDGET_CODE.OBJECT_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    budgetCode = accountBudgetCodeService.findByAllRevObjectTypeFunctionBudgetHeads(faYearid, cpdBugtypeid,
                            cpdBugsubtypeId, dpDeptid, organisation, langId, functionId);
                }
            }
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.ACCOUNT_BUDGET_CODE_MAP, budgetCode);
        }
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        result = JSP_FORM;
        return result;
    }

    @RequestMapping()
    public String getList(final Model model) throws Exception {
        log("AccountBudgetProjectedRevenueEntry-'list' :'list'");
        String result = MainetConstants.CommonConstant.BLANK;
        chList = new ArrayList<>();
        chList.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final String faYearid = UserSession.getCurrent().getFinYearId();
        final Map<Long, String> fundMap = new LinkedHashMap<>();
        final Map<Long, String> functionMap = new LinkedHashMap<>();
        if ((faYearid != null) && !faYearid.isEmpty()) {
           // chList = accountBudgetProjectedRevenueEntryService.findByFinancialId(Long.valueOf(faYearid), orgId);
            if (CollectionUtils.isNotEmpty(chList)) {
                String amount = null;
                String amount1 = null;
                String amount2 = null;
                String amount3 = null;
                for (final AccountBudgetProjectedRevenueEntryBean bean : chList) {
                    if ((bean.getOrginalEstamt() != null) || (bean.getRevisedEstamt() == null)) {
                        amount = CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(bean.getOrginalEstamt()));
                        bean.setFormattedCurrency(amount);
                    }
                    if ((bean.getRevisedEstamt() != null) && !bean.getRevisedEstamt().isEmpty()) {
                        amount3 = CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(bean.getRevisedEstamt()));
                        bean.setFormattedCurrency3(amount3);
                    }
                    if ((bean.getPrCollected() != null) && !bean.getPrCollected().isEmpty()) {
                        amount1 = CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(bean.getPrCollected()));
                        bean.setFormattedCurrency1(amount1);
                    }
                    BigDecimal balAmt = null;
                    if ((bean.getRevisedEstamt() != null && !bean.getRevisedEstamt().isEmpty())
                            && (bean.getPrCollected() != null && !bean.getPrCollected().isEmpty())) {
                        balAmt = new BigDecimal(bean.getRevisedEstamt()).subtract((new BigDecimal(bean.getPrCollected())));
                    } else {
                        balAmt = new BigDecimal(bean.getOrginalEstamt()).subtract((new BigDecimal(bean.getPrCollected())));
                    }
                    amount2 = CommonMasterUtility.getAmountInIndianCurrency(balAmt);
                    bean.setFormattedCurrency2(amount2);
                }
            }
        }
        final Map<Long, String> budrevMap = new LinkedHashMap<>();
        final List<AccountBudgetProjectedRevenueEntryBean> list = accountBudgetProjectedRevenueEntryService
                .findBudgetProjectedRevenueEntrysByOrgId(orgId);
        final List<LookUp> fundTypeLevel = CommonMasterUtility.getListLookup(
                MainetConstants.BUDGET_CODE.FUND_FIELD_DEPT_STATUS_PREFIX,
                UserSession.getCurrent().getOrganisation());
        for (final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean : list) {
            final Long prBudgetCodeid = accountBudgetProjectedRevenueEntryBean.getPrBudgetCodeid();
            String budgetCode=null;
            for (final LookUp lookUp : fundTypeLevel) {
                if (MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                	budgetCode = accountBudgetCodeService.getBudgetCode(prBudgetCodeid, orgId);
                }
                if (MainetConstants.BUDGET_CODE.OBJECT_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                	budgetCode = accountBudgetCodeService.getBudgetCodes(prBudgetCodeid, orgId);
                }
              }
            budrevMap.put(prBudgetCodeid, budgetCode);

            final List<Object[]> fundCode = accountBudgetCodeService.getFundCode(prBudgetCodeid, orgId);
            if (fundCode != null) {
                for (final Object[] objects : fundCode) {
                    if ((objects[0] != null) && (objects[1] != null) && (objects[2] != null)) {
                        fundMap.put((Long) objects[0], objects[1].toString() + MainetConstants.SEPARATOR + objects[2].toString());
                    }
                }
            }
            final List<Object[]> functionCode = accountBudgetCodeService.getFunctionCode(prBudgetCodeid, orgId);
            for (final Object[] objects : functionCode) {
                if ((objects[0] != null) && (objects[1] != null) && (objects[2] != null)) {
                    functionMap.put((Long) objects[0], objects[1].toString() + MainetConstants.SEPARATOR + objects[2].toString());
                }
            }
        }
        
        if(CollectionUtils.isNotEmpty(chList)) {
			if (getSelfDeparmentConfiguration() != null
					&& MainetConstants.Y_FLAG.equalsIgnoreCase(getSelfDeparmentConfiguration())) {
				Long deptId = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid();
				String deptCode = departmentService.getDeptCode(deptId);
				if (!deptCode.equalsIgnoreCase(MainetConstants.AccountConstants.AC.getValue())) {
					chList = chList.stream().filter(p -> p.getDpDeptid() == deptId)
							.collect(Collectors.toList());
				}
			}
        }
        model.addAttribute(MainetConstants.AccountBudgetProjectedRevenueEntry.BUDGET_REVENUE_MAP, budrevMap);

        final AccountBudgetProjectedRevenueEntryBean bean = new AccountBudgetProjectedRevenueEntryBean();
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FUND_MAP, fundMap);
        model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FUNCTION_MAP, functionMap);
        model.addAttribute(MAIN_LIST_NAME, chList);
if (getSelfDeparmentConfiguration() != null
				&& MainetConstants.Y_FLAG.equalsIgnoreCase(getSelfDeparmentConfiguration())) {
			if (UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptcode().equalsIgnoreCase("AC")) {
				model.addAttribute("deptCheck", MainetConstants.Y_FLAG);
			}
			else {
				model.addAttribute("deptCheck", MainetConstants.N_FLAG);
			}
		}
        else {
        	model.addAttribute("deptCheck", MainetConstants.Y_FLAG);
        }  
           
      result = JSP_LIST;
        return result;
    }

    @RequestMapping(params = "form")
    public String formForCreate(final Model model) throws Exception {
        log("AccountBudgetProjectedRevenueEntry-'formForCreate' : 'formForCreate'");
        String result = MainetConstants.CommonConstant.BLANK;
        final AccountBudgetProjectedRevenueEntryBean bean = new AccountBudgetProjectedRevenueEntryBean();
        populateModel(model, bean, FormMode.CREATE);
        result = JSP_FORM;
        return result;
    }

    private void populateModel(final Model model, final AccountBudgetProjectedRevenueEntryBean bean, final FormMode formMode)
            throws Exception {
        log("AccountBudgetProjectedRevenueEntry-'populateModel' : populate model");
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        int langId = UserSession.getCurrent().getLanguageId();

        final List<LookUp> bugSubTypelevelMap = CommonMasterUtility.getListLookup(PrefixConstants.BUG_SUB_PREFIX,
                UserSession.getCurrent().getOrganisation());
        if (bugSubTypelevelMap != null) {
            for (final LookUp lookUp : bugSubTypelevelMap) {
                if (lookUp != null) {
                    if (lookUp.getLookUpCode().equals(PrefixConstants.REV_SUB_CPD_VALUE)
                            || lookUp.getLookUpCode().equals(PrefixConstants.EXP_SUB_CPD_VALUE)) {
                        model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.BUG_SUB_TYPE_LEVEL_MAP,
                                bugSubTypelevelMap);
                        model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.BUDGET_SUBTYPE_STATUS,
                                MainetConstants.MASTER.Y);
                    }
                }
            }
        }

        // final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
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
                MainetConstants.BUDGET_CODE.FUND_FIELD_DEPT_STATUS_PREFIX,
                UserSession.getCurrent().getOrganisation());
        for (final LookUp lookUp : fundTypeLevel) {
            if (MainetConstants.BUDGET_CODE.FUND_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                model.addAttribute(MainetConstants.FUND_MASTER.FUND_STATUS, MainetConstants.MASTER.Y);
            }
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
                if (formMode.equals(FormMode.VIEW)) {
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
                                            tbAcFieldMasterService.getFieldMasterLastLevels(defaultOrgId));
                                } else {
                                    model.addAttribute(
                                            MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
                                            tbAcFieldMasterService.getFieldMasterLastLevels(orgId));
                                }
                            }
                        }
                    }
                }
                model.addAttribute(MainetConstants.FIELD_MASTER.FIELD_STATUS, MainetConstants.MASTER.Y);
            }
            if (MainetConstants.BUDGET_CODE.FUNCTION_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                if (formMode.equals(FormMode.CREATE) || formMode.equals(FormMode.UPDATE)) {

                    final LookUp cmdFunctionPrefix = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                            PrefixConstants.FUNCTION_CPD_VALUE, PrefixConstants.CMD,
                            UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
                    if ((tbCodingList != null) && !tbCodingList.isEmpty()) {
                        for (final TbAcCodingstructureMas master : tbCodingList) {
                            if (cmdFunctionPrefix.getLookUpId() == master.getComCpdId()) {
                                defaultFunctionFlag = master.getDefineOnflag();
                                if (defaultFunctionFlag.equals(MainetConstants.MASTER.Y) && isDafaultOrgExist) {
                                    model.addAttribute(
                                            MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FUNCTION_MASTER_ITEMS,
                                            tbAcFunctionMasterService.getFunctionMasterStatusLastLevels(defaultOrgId,
                                                    superUserOrganization, langId));
                                } else {
                                    model.addAttribute(
                                            MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FUNCTION_MASTER_ITEMS,
                                            tbAcFunctionMasterService.getFunctionMasterStatusLastLevels(orgId,
                                                    organisation, langId));
                                }
                            }
                        }
                    }
                }
                if (formMode.equals(FormMode.VIEW)) {

                    final LookUp cmdFunctionPrefix = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                            PrefixConstants.FUNCTION_CPD_VALUE, PrefixConstants.CMD,
                            UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
                    if ((tbCodingList != null) && !tbCodingList.isEmpty()) {
                        for (final TbAcCodingstructureMas master : tbCodingList) {
                            if (cmdFunctionPrefix.getLookUpId() == master.getComCpdId()) {
                                defaultFunctionFlag = master.getDefineOnflag();
                                if (defaultFunctionFlag.equals(MainetConstants.MASTER.Y) && isDafaultOrgExist) {
                                    model.addAttribute(
                                            MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FUNCTION_MASTER_ITEMS,
                                            tbAcFunctionMasterService.getFunctionMasterLastLevels(defaultOrgId));
                                } else {
                                    model.addAttribute(
                                            MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FUNCTION_MASTER_ITEMS,
                                            tbAcFunctionMasterService.getFunctionMasterLastLevels(orgId));
                                }
                            }
                        }
                    }
                }
                model.addAttribute(MainetConstants.FUNCTION_MASTER.FUNCTION_STATUS, MainetConstants.MASTER.Y);
            }

        }

        final Map<Long, String> deptMap = new LinkedHashMap<>(0);
        List<Object[]> department = null;
        department = departmentService.getAllDeptTypeNames();
        if(CollectionUtils.isNotEmpty(department)) {
			if (getSelfDeparmentConfiguration() != null
					&& MainetConstants.Y_FLAG.equalsIgnoreCase(getSelfDeparmentConfiguration())) {
				department = populateDepartmentBasedOnConfiguration(department);
			}
         }
        for (final Object[] dep : department) {
            if (dep[0] != null) {
                deptMap.put((Long) (dep[0]), (String) dep[1]);
            }
        }
        
        
        model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.DEPT_MAP, deptMap);

        model.addAttribute(AC_Financialyr_LOOKUPS, secondaryheadMasterService.getAllFinincialYear(orgId, langId));
        bean.setSuccessFlag(MainetConstants.FlagY);

        model.addAttribute(MAIN_ENTITY_NAME, bean);
        if (formMode == FormMode.CREATE) {

            final Map<Long, String> financeMap = tbFinancialyearService.getAllFinincialYearByStatusWise(orgId);
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FINANCE_MAP, financeMap);

            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE,
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE_CREATE);
            model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, false);
        } else if (formMode == FormMode.UPDATE) {

            final Map<Long, String> financeMap = tbFinancialyearService.getAllFinincialYear();
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FINANCE_MAP, financeMap);

            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE,
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE_UPDATE);
        }
    }

    @RequestMapping(params = "getBudgetRevDuplicateGridloadData", method = RequestMethod.POST)
    public @ResponseBody boolean findBudgetOpenBalDuplicateCombination(final AccountBudgetProjectedRevenueEntryBean bean,
            final HttpServletRequest request, final Model model, final BindingResult bindingResult,
            @RequestParam("cnt") final int cnt) {
        boolean isValidationError = false;
        final Long faYearid = bean.getFaYearid();
        final Long prBudgetCodeid = bean.getBugRevenueMasterDtoList().get(cnt).getPrBudgetCodeid();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long deptId=bean.getDpDeptid();
         final Long fieldId=bean.getFieldId();
        if (accountBudgetProjectedRevenueEntryService.isCombinationExists(faYearid, prBudgetCodeid, orgId,deptId,fieldId)) {
                     bindingResult.addError(new org.springframework.validation.FieldError(
                    MainetConstants.AccountBudgetProjectedRevenueEntry.BUDGET_PROJ_REVENUE_ENTRY,
                    MainetConstants.CommonConstant.BLANK, null, false, new String[] {
                            MainetConstants.ERRORS },
                    null, ApplicationSession.getInstance().getMessage(MainetConstants.CommonConstant.BLANK)));
            isValidationError = true;
        }
        return isValidationError;
    }

    @RequestMapping(params = "checkTransactions", method = RequestMethod.POST)
    public @ResponseBody boolean checkTransactionsCombination(@RequestParam("prProjectionid") final Long prProjectionid,
            @RequestParam("faYearid") final Long faYearid, final HttpServletRequest request) {
        log("AccountBudgetProjectedRevenueEntry-'checkTransactions' : check Transactions");
        boolean isValidationError = false;
        final Long prProjectionId = prProjectionid;
        final Long faYearId = faYearid;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        if (accountBudgetProjectedRevenueEntryService.isCombinationCheckTransactions(prProjectionId, faYearId, orgId)) {
            isValidationError = true;
        }
        return isValidationError;
    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String create(final AccountBudgetProjectedRevenueEntryBean tbAcBudgetProjectedRevenueEntry,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {
        log("AccountBudgetProjectedRevenueEntryBean-'create' : 'create'");
        String result = MainetConstants.CommonConstant.BLANK;

        if (!bindingResult.hasErrors()) {
            tbAcBudgetProjectedRevenueEntry.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
            final UserSession userSession = UserSession.getCurrent();
            tbAcBudgetProjectedRevenueEntry.setOrgid(userSession.getOrganisation().getOrgid());
            tbAcBudgetProjectedRevenueEntry.setLangId(userSession.getLanguageId());
            tbAcBudgetProjectedRevenueEntry.setUserId(userSession.getEmployee().getEmpId());
            tbAcBudgetProjectedRevenueEntry.setLmoddate(new Date());
            tbAcBudgetProjectedRevenueEntry.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
            populateModel(model, tbAcBudgetProjectedRevenueEntry, FormMode.CREATE);
            final Organisation orgid = UserSession.getCurrent().getOrganisation();
            final int langId = UserSession.getCurrent().getLanguageId();
            AccountBudgetProjectedRevenueEntryBean tbAcBudgetProjectedRevenueEntryCreated = accountBudgetProjectedRevenueEntryService
                    .saveBudgetProjectedRevenueEntryFormData(tbAcBudgetProjectedRevenueEntry, orgid, langId);
            if (tbAcBudgetProjectedRevenueEntryCreated == null) {
                tbAcBudgetProjectedRevenueEntryCreated = new AccountBudgetProjectedRevenueEntryBean();
            }
            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetProjectedRevenueEntryCreated);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            if (tbAcBudgetProjectedRevenueEntry.getPrProjectionid() == null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        MainetConstants.AccountBudgetAdditionalSupplemental.RECORD_SAVE_SUCCESS);
            }
            if (tbAcBudgetProjectedRevenueEntry.getPrProjectionid() != null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        MainetConstants.AccountBudgetAdditionalSupplemental.RECORD_UPDATE_SUCCESS);
            }
            result = JSP_FORM;
        } else {
            tbAcBudgetProjectedRevenueEntry.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            populateModel(model, tbAcBudgetProjectedRevenueEntry, FormMode.CREATE);
            result = JSP_FORM;
        }
        return result;
    }

    @RequestMapping(params = "update", method = RequestMethod.POST)
    public String update(AccountBudgetProjectedRevenueEntryBean tbAcBudgetProjectedRevenueEntry,
            @RequestParam("prProjectionid") final Long prProjectionid,
            @RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {
        log("tbAcBudgetProjectedRevenueEntry-'gridData' : 'update'");
        String result = MainetConstants.CommonConstant.BLANK;
        final List<LookUp> fundTypeLevel = CommonMasterUtility.getListLookup(
                MainetConstants.BUDGET_CODE.FUND_FIELD_DEPT_STATUS_PREFIX,
                UserSession.getCurrent().getOrganisation());
        if (!bindingResult.hasErrors()) {
            if (viewmode.equals(MainetConstants.EDIT)) {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.EDIT);
            } else {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
            }
            setModeView(viewmode);
            final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            tbAcBudgetProjectedRevenueEntry.setPrProjectionid(prProjectionid);
            tbAcBudgetProjectedRevenueEntry = accountBudgetProjectedRevenueEntryService
                    .getDetailsUsingProjectionId(tbAcBudgetProjectedRevenueEntry, orgId);
            final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                    PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
            final Long faYearid = tbAcBudgetProjectedRevenueEntry.getFaYearid();
            final Long cpdBugtypeid = revenueLookup.getLookUpId();
            final Long cpdBugsubtypeId = tbAcBudgetProjectedRevenueEntry.getCpdBugsubtypeId();
            final Long dpDeptid = tbAcBudgetProjectedRevenueEntry.getDpDeptid();
            final Organisation organisation = UserSession.getCurrent().getOrganisation();
            final int langId = UserSession.getCurrent().getLanguageId();

            Map<Long, String> budgetCode = null;
            for (final LookUp lookUp : fundTypeLevel) {
                if (MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    budgetCode = accountBudgetCodeService.findByAllRevBudgetHeads(faYearid, cpdBugtypeid, cpdBugsubtypeId,
                            dpDeptid, organisation, langId);
                }
                if (MainetConstants.BUDGET_CODE.OBJECT_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    budgetCode = accountBudgetCodeService.findByAllRevObjectTypeBudgetHeads(faYearid, cpdBugtypeid,
                            cpdBugsubtypeId, dpDeptid, organisation, langId);
                }
            }
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.ACCOUNT_BUDGET_CODE_MAP, budgetCode);

            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetProjectedRevenueEntry);
            populateModel(model, tbAcBudgetProjectedRevenueEntry, FormMode.UPDATE);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            log("BudgetopenBalanceMaster 'update' : update done - redirect");
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            log("BudgetopenBalanceMaster 'update' : update done - redirect");
            result = JSP_FORM;
        } else {
            log("BudgetopenBalanceMaster 'update' : binding errors");
            populateModel(model, tbAcBudgetProjectedRevenueEntry, FormMode.UPDATE);
            result = JSP_FORM;
        }
        return result;
    }

    @RequestMapping(params = "formForView", method = RequestMethod.POST)
    public String formForView(AccountBudgetProjectedRevenueEntryBean tbAcBudgetProjectedRevenueEntry,
            @RequestParam("prProjectionid") final Long prProjectionid,
            @RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {
        log("tbAcBudgetProjectedRevenueEntry-'gridData' : 'view'");
        String result = MainetConstants.CommonConstant.BLANK;

        if (!bindingResult.hasErrors()) {
            if (viewmode.equals(MainetConstants.VIEW)) {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.VIEW);
            } else {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
            }
            setModeView(viewmode);
            final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            tbAcBudgetProjectedRevenueEntry.setPrProjectionid(prProjectionid);
            tbAcBudgetProjectedRevenueEntry = accountBudgetProjectedRevenueEntryService
                    .getDetailsUsingProjectionId(tbAcBudgetProjectedRevenueEntry, orgId);
            if (tbAcBudgetProjectedRevenueEntry.getFaYearid() != null) {
                tbAcBudgetProjectedRevenueEntry.setFinancialYearDesc(
                        tbFinancialyearService.findFinancialYearDesc(tbAcBudgetProjectedRevenueEntry.getFaYearid()));
            }

            final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                    PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
            final Long cpdBugtypeid = revenueLookup.getLookUpId();
            final Long cpdBugsubtypeId = tbAcBudgetProjectedRevenueEntry.getCpdBugsubtypeId();
            final Long dpDeptid = tbAcBudgetProjectedRevenueEntry.getDpDeptid();

            final Map<Long, String> budgetCode = accountBudgetCodeService.finAllBudgetCodeByOrg(orgId);
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.ACCOUNT_BUDGET_CODE_MAP, budgetCode);

            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetProjectedRevenueEntry);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            log("BudgetopenBalanceMaster 'view' : view done - redirect");
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            log("BudgetopenBalanceMaster 'view' : view done - redirect");
            result = JSP_VIEW;
        } else {
            log("BudgetopenBalanceMaster 'view' : binding errors");
            populateModel(model, tbAcBudgetProjectedRevenueEntry, FormMode.UPDATE);
            result = JSP_VIEW;
        }
        return result;
    }

    @RequestMapping(params = "exportTemplateData")
    public String exportImportExcelTemplate(final Model model) throws Exception {
        String result = MainetConstants.CommonConstant.BLANK;
        final AccountBudgetProjectedRevenueEntryBean bean = new AccountBudgetProjectedRevenueEntryBean();
        populateModel(model, bean, FormMode.CREATE);
        result = JSP_EXCELUPLOAD;
        fileUpload.sessionCleanUpForFileUpload();
        return result;
    }

    @RequestMapping(params = "ExcelTemplateData")
    public void exportAccountBudgetProjectedRevenueEntryExcelData(final HttpServletResponse response,
            final HttpServletRequest request) {

        try {
            WriteExcelData<AccountBudgetProjectedRevenueEntryUploadDto> data = new WriteExcelData<>(
                    MainetConstants.ACCOUNTBUDGETPROJECTEDREVENUEENTRYUPLOADDTO
                            + MainetConstants.XLSX_EXT,
                    request, response);

            data.getExpotedExcelSheet(new ArrayList<AccountBudgetProjectedRevenueEntryUploadDto>(),
                    AccountBudgetProjectedRevenueEntryUploadDto.class);
        } catch (Exception ex) {
            throw new FrameworkException(ex.getMessage());
        }
    }

    @RequestMapping(params = "loadExcelData", method = RequestMethod.POST)
    public String loadValidateAndLoadExcelData(AccountBudgetProjectedRevenueEntryBean bean,
            final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
            final HttpServletRequest httpServletRequest) throws Exception {
        log("Action 'loadExcelData'");

        final ApplicationSession session = ApplicationSession.getInstance();
        final UserSession userSession = UserSession.getCurrent();
        final long orgId = userSession.getOrganisation().getOrgid();
        final int langId = userSession.getLanguageId();
        final Long userId = userSession.getEmployee().getEmpId();
        final String filePath = getUploadedFinePath();
        ReadExcelData<AccountBudgetProjectedRevenueEntryUploadDto> data = new ReadExcelData<>(filePath,
                AccountBudgetProjectedRevenueEntryUploadDto.class);
        data.parseExcelList();
        List<String> errlist = data.getErrorList();
        if (!errlist.isEmpty()) {
            bindingResult.addError(new org.springframework.validation.FieldError(
                    MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.MAIN_ENTITY_NAME,
                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                    session.getMessage("accounts.empty.excel")));
        } else {
            final List<AccountBudgetProjectedRevenueEntryUploadDto> accountBudgetProjectedRevenueEntryUploadDtos = data
                    .getParseData();
            final Map<Long, String> financeMap = tbFinancialyearService.getAllFinincialYearByStatusWise(orgId);

            final Map<Long, String> deptMap = new LinkedHashMap<>(0);
            List<Object[]> department = null;
            department = departmentService.getAllDeptTypeNames();
            if (department != null) {
                for (final Object[] dep : department) {
                    if (dep[0] != null) {
                        deptMap.put((Long) (dep[0]), (String) dep[1]);
                    }
                }
            }
            List<LookUp> budgSubTypelist = null;
            budgSubTypelist = CommonMasterUtility.getLookUps(PrefixConstants.BUG_SUB_PREFIX,
                    UserSession.getCurrent().getOrganisation());
            final Map<Long, String> budgetCode = accountBudgetCodeService.finAllBudgetCodeByOrganization(orgId);
            List<AccountBudgetProjectedRevenueEntryEntity> budgetProjectedRevenue = accountBudgetProjectedRevenueEntryService
                    .getBudgetProjectedRevenueEntry(orgId);
            List<AccountFieldMasterBean> filedlist = tbAcFieldMasterService.findAllByOrgId(orgId);
            AccountBudgetProjectedRevenueEntryExcelValidator validator = new AccountBudgetProjectedRevenueEntryExcelValidator();
            List<AccountBudgetProjectedRevenueEntryUploadDto> accountBudgetProjectedRevenueUploadList = validator
                    .excelValidation(accountBudgetProjectedRevenueEntryUploadDtos, bindingResult, financeMap, deptMap,
                            budgetCode, budgSubTypelist,filedlist);
            if (validator.count == 0) {
                validator.budgetExpenditureComb(accountBudgetProjectedRevenueUploadList, bindingResult,
                        budgetProjectedRevenue);
            }

            if (!bindingResult.hasErrors()) {
                for (AccountBudgetProjectedRevenueEntryUploadDto accountBudgetProjectedRevenueEntryUploadDto : accountBudgetProjectedRevenueUploadList) {

                    accountBudgetProjectedRevenueEntryUploadDto
                            .setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                    accountBudgetProjectedRevenueEntryUploadDto.setLangId(Long.valueOf(langId));
                    accountBudgetProjectedRevenueEntryUploadDto.setUserId(userId);
                    accountBudgetProjectedRevenueEntryUploadDto.setLmoddate(new Date());
                    accountBudgetProjectedRevenueEntryUploadDto
                            .setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
                    accountBudgetProjectedRevenueEntryService.saveBudgetProjectedRevenueEntryExportData(
                            accountBudgetProjectedRevenueEntryUploadDto, orgId,
                            langId);
                }

                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        session.getMessage("accounts.success.excel"));
            }
        }
        model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MAIN_ENTITY_NAME, bindingResult);
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
        fileUpload.sessionCleanUpForFileUpload();
        return JSP_EXCELUPLOAD;
    }

    private String getUploadedFinePath() {
        String filePath = null;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            Set<File> list = entry.getValue();
            for (final File file : list) {
                filePath = file.toString();
                break;
            }
        }
        return filePath;
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

    public String getModeView() {
        return modeView;
    }

    public void setModeView(final String modeView) {
        this.modeView = modeView;
    }
    private List<Object[]> populateDepartmentBasedOnConfiguration(List<Object[]> department){
 		Long deptId = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid();
 		String deptCode = departmentService.getDeptCode(deptId);
 		//other than Account and Audit Department Employee should not have the filter
 		if (!deptCode.equalsIgnoreCase(MainetConstants.AccountConstants.AC.getValue()) && !deptCode.equalsIgnoreCase(MainetConstants.AccountConstants.AD.getValue()))
 			department = department.stream().filter(obj -> obj[0] == deptId).collect(Collectors.toList());
     	return department;
     }
    private String getSelfDeparmentConfiguration() {
    	LookUp lookup=null;
    	Organisation org = UserSession.getCurrent().getOrganisation();
    	int langId = UserSession.getCurrent().getLanguageId();
        try {
        	 lookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.ASD.getValue(),
                    AccountPrefix.AIC.toString(),langId,org);
        }catch(Exception e) {
        	return null;
        }
        return lookup.getOtherField();
    }
}
