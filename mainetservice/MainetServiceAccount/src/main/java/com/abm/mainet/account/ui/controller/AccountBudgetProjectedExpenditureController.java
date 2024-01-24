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

import com.abm.mainet.account.domain.AccountBudgetProjectedExpenditureEntity;
import com.abm.mainet.account.dto.AccountBillEntryExpenditureDetBean;
import com.abm.mainet.account.dto.AccountBillEntryMasterBean;
import com.abm.mainet.account.dto.AccountBudgetProjectedExpenditureBean;
import com.abm.mainet.account.dto.AccountBudgetProjectedExpenditureUploadDto;
import com.abm.mainet.account.service.AccountBudgetProjectedExpenditureService;
import com.abm.mainet.account.service.BudgetCodeService;
import com.abm.mainet.account.ui.validator.AccountBudgetProjectedExpenditureExcelValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.ReadExcelData;
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
@RequestMapping("/AccountBudgetProjectedExpenditure.html")
public class AccountBudgetProjectedExpenditureController extends AbstractController {

    private static final String MAIN_ENTITY_NAME = "tbAcBudgetProjectedExpenditure";
    private static final String MAIN_LIST_NAME = "list";
    private static final String JSP_FORM = "tbAcBudgetProjectedExpenditure/form";
    private static final String JSP_VIEW = "tbAcBudgetProjectedExpenditure/view";
    private static final String JSP_LIST = "tbAcBudgetProjectedExpenditure/list";
    private static final String AC_Financialyr_LOOKUPS = "aFinancialYr";
    private static final String JSP_EXCELUPLOAD = "tbAcBudgetProjectedExpenditure/ExcelUpload";
    private static final String ACCOUNT_EXPENDITURE_DETAILS = "AccountExpenditureBudgetDetails";
	private final static String BILL_ENTRY_BEAN = "accountBillEntryBean";
	private final String BUDGET_DETAILS = "budgetDetails"; 
    private String modeView = MainetConstants.BLANK;

    @Resource
    private AccountBudgetProjectedExpenditureService accountBudgetProjectedExpenditureService;
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
    @Resource
	private TbFinancialyearService financialyearService;
    private List<AccountBudgetProjectedExpenditureBean> chList = null;

    public AccountBudgetProjectedExpenditureController() {
        super(AccountBudgetProjectedExpenditureController.class, MAIN_ENTITY_NAME);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(final HttpServletRequest request,
            final Model model) {
        log("AccountBudgetProjectedExpenditureController-'gridData' : 'Get grid Data'");
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
            @RequestParam("functionId") final Long functionId, @RequestParam("fieldId") final Long fieldId) {

        chList = new ArrayList<>();
        chList.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        chList = accountBudgetProjectedExpenditureService
                .findByGridAllData(faYearid, fundId, functionId, cpdBugsubtypeId, dpDeptid, prBudgetCodeid,fieldId, orgId);
        if (chList != null) {
            String amount = null;
            String amount1 = null;
            String amount2 = null;
            String amount3 = null;
            for (final AccountBudgetProjectedExpenditureBean bean : chList) {
                if ((bean.getOrginalEstamt() != null) || (bean.getRevisedEstamt() == null)) {
                    amount = CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(bean.getOrginalEstamt()));
                    bean.setFormattedCurrency(amount);
                }
                if ((bean.getExpenditureAmt() != null) && !bean.getExpenditureAmt().isEmpty()) {
                    amount1 = CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(bean.getExpenditureAmt()));
                    bean.setFormattedCurrency1(amount1);
                }
                if ((bean.getRevisedEstamt() != null) && !bean.getRevisedEstamt().isEmpty()) {
                    amount3 = CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(bean.getRevisedEstamt()));
                    bean.setFormattedCurrency3(amount3);
                }
                BigDecimal balAmt = null;
                if ((bean.getRevisedEstamt() != null && !bean.getRevisedEstamt().isEmpty())
                        && (bean.getExpenditureAmt() != null && !bean.getExpenditureAmt().isEmpty())) {
                    balAmt = new BigDecimal(bean.getRevisedEstamt()).subtract((new BigDecimal(bean.getExpenditureAmt())));
                } else {
                    balAmt = new BigDecimal(bean.getOrginalEstamt()).subtract((new BigDecimal(bean.getExpenditureAmt())));
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
					chList = chList.stream().filter(p -> p.getDpDeptid().equals(deptId))
							.collect(Collectors.toList());
				}
			}
        }
        return chList;
    }

    @RequestMapping(params = "getjqGridload", method = RequestMethod.POST)
    public String loadBudgetExpenditureData(final AccountBudgetProjectedExpenditureBean bean, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest request, final BindingResult bindingResult)
            throws Exception {
        String result = MainetConstants.CommonConstant.BLANK;
        final List<LookUp> fundTypeLevel = CommonMasterUtility.getListLookup(
                MainetConstants.BUDGET_CODE.FUND_FIELD_DEPT_STATUS_PREFIX,
                UserSession.getCurrent().getOrganisation());
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final int langId = UserSession.getCurrent().getLanguageId();
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final Long faYearid = bean.getFaYearid();
        final Long cpdBugtypeid = expLookup.getLookUpId();
        final Long cpdBugsubtypeId = bean.getCpdBugsubtypeId();
        final Long dpDeptid = bean.getDpDeptid();
        if (dpDeptid != null) {
            Map<Long, String> budgetCode = null;
            for (final LookUp lookUp : fundTypeLevel) {
                if (MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    budgetCode = accountBudgetCodeService.findByAllExpBudgetHeads(faYearid, cpdBugtypeid, cpdBugsubtypeId,
                            dpDeptid, organisation, langId);
                }
                if (MainetConstants.BUDGET_CODE.OBJECT_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    budgetCode = accountBudgetCodeService.findByAllExpObjectTypeBudgetHeads(faYearid, cpdBugtypeid,
                            cpdBugsubtypeId, dpDeptid, organisation, langId);
                }
            }
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.ACCOUNT_BUDGET_CODE_MAP, budgetCode);
        }
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        result = JSP_FORM;
        return result;
    }

    @RequestMapping(params = "getjqGridFiledload", method = RequestMethod.POST)
    public String loadBudgetExpenditureFieldData(final AccountBudgetProjectedExpenditureBean bean, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest request, final BindingResult bindingResult)
            throws Exception {
        String result = MainetConstants.CommonConstant.BLANK;
        final List<LookUp> fundTypeLevel = CommonMasterUtility.getListLookup(
                MainetConstants.BUDGET_CODE.FUND_FIELD_DEPT_STATUS_PREFIX,
                UserSession.getCurrent().getOrganisation());
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final int langId = UserSession.getCurrent().getLanguageId();
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final Long faYearid = bean.getFaYearid();
        final Long cpdBugtypeid = expLookup.getLookUpId();
        final Long cpdBugsubtypeId = bean.getCpdBugsubtypeId();
        final Long dpDeptid = bean.getDpDeptid();
        final Long fieldId = bean.getFieldId();
        if (dpDeptid != null && fieldId != null) {
            Map<Long, String> budgetCode = null;
            for (final LookUp lookUp : fundTypeLevel) {
                if (MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    budgetCode = accountBudgetCodeService.findByAllExpFieldBudgetHeads(faYearid, cpdBugtypeid, cpdBugsubtypeId,
                            dpDeptid, organisation, langId, fieldId);
                }
                if (MainetConstants.BUDGET_CODE.OBJECT_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    budgetCode = accountBudgetCodeService.findByAllExpObjectTypeFieldBudgetHeads(faYearid, cpdBugtypeid,
                            cpdBugsubtypeId, dpDeptid, organisation, langId, fieldId);
                }
            }
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.ACCOUNT_BUDGET_CODE_MAP, budgetCode);
        }
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        result = JSP_FORM;
        return result;
    }

    @RequestMapping(params = "getjqGridFunctionload", method = RequestMethod.POST)
    public String loadBudgetExpenditureFunctionData(final AccountBudgetProjectedExpenditureBean bean, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest request, final BindingResult bindingResult)
            throws Exception {
        String result = MainetConstants.CommonConstant.BLANK;
        final List<LookUp> fundTypeLevel = CommonMasterUtility.getListLookup(
                MainetConstants.BUDGET_CODE.FUND_FIELD_DEPT_STATUS_PREFIX,
                UserSession.getCurrent().getOrganisation());
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final int langId = UserSession.getCurrent().getLanguageId();
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final Long faYearid = bean.getFaYearid();
        final Long cpdBugtypeid = expLookup.getLookUpId();
        final Long cpdBugsubtypeId = bean.getCpdBugsubtypeId();
        final Long dpDeptid = bean.getDpDeptid();
        final Long functionId = bean.getFunctionId();
        if (dpDeptid != null && functionId != null) {
            Map<Long, String> budgetCode = null;
            for (final LookUp lookUp : fundTypeLevel) {
                if (MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    budgetCode = accountBudgetCodeService.findByAllExpFunctionBudgetHeads(faYearid, cpdBugtypeid, cpdBugsubtypeId,
                            dpDeptid, organisation, langId, functionId);
                }
                if (MainetConstants.BUDGET_CODE.OBJECT_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    budgetCode = accountBudgetCodeService.findByAllExpObjectTypeFunctionBudgetHeads(faYearid, cpdBugtypeid,
                            cpdBugsubtypeId, dpDeptid, organisation, langId, functionId);
                }
            }
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.ACCOUNT_BUDGET_CODE_MAP, budgetCode);
        }
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        result = JSP_FORM;
        return result;
    }

    @RequestMapping(params = "getBudgetExpDuplicateGridloadData", method = RequestMethod.POST)
    public @ResponseBody boolean findBudgetOpenBalDuplicateCombination(final AccountBudgetProjectedExpenditureBean bean,
            final HttpServletRequest request, final Model model, final BindingResult bindingResult,
            @RequestParam("cnt") final int cnt) {
        boolean isValidationError = false;
        final Long faYearid = bean.getFaYearid();
        final Long prBudgetCodeid = bean.getBugExpenditureMasterDtoList().get(cnt).getPrBudgetCodeid();
        final Long deptId=bean.getDpDeptid();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
      final Long fieldId=bean.getFieldId();
        if (accountBudgetProjectedExpenditureService.isBudgeExpAlreadyEntered(faYearid, prBudgetCodeid, orgId,deptId,fieldId)) {
                       bindingResult.addError(new org.springframework.validation.FieldError(
                    MainetConstants.AccountBudgetProjectedExpenditure.BUDGET_PROJ_EXPENDITURE,
                    MainetConstants.CommonConstant.BLANK, null, false, new String[] {
                            MainetConstants.ERRORS },
                    null, ApplicationSession.getInstance().getMessage(MainetConstants.CommonConstant.BLANK)));
            isValidationError = true;
        }
        return isValidationError;
    }

    @RequestMapping(params = "checkTransactions", method = RequestMethod.POST)
    public @ResponseBody boolean checkTransactionsCombination(@RequestParam("prExpenditureid") final Long prExpenditureid,
            @RequestParam("faYearid") final Long faYearid, final HttpServletRequest request) {
        boolean isValidationError = false;
        final Long prExpenditureId = prExpenditureid;
        final Long faYearId = faYearid;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        if (accountBudgetProjectedExpenditureService.isCombinationCheckTransactions(prExpenditureId, faYearId, orgId)) {
            isValidationError = true;
        }
        return isValidationError;
    }

    @RequestMapping()
    public String getList(final Model model) throws Exception {
        String result = MainetConstants.CommonConstant.BLANK;
        chList = new ArrayList<>();
        chList.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final String faYearid = UserSession.getCurrent().getFinYearId();
        final Map<Long, String> fundMap = new LinkedHashMap<>();
        final Map<Long, String> functionMap = new LinkedHashMap<>();
        if ((faYearid != null) && !faYearid.isEmpty()) {
           // chList = accountBudgetProjectedExpenditureService.findBudgetProjectedExpenditureByFinId(Long.valueOf(faYearid),
                 //   orgId);
            if (CollectionUtils.isNotEmpty(chList)) {
                String amount = null;
                String amount1 = null;
                String amount2 = null;
                String amount3 = null;
                for (final AccountBudgetProjectedExpenditureBean bean : chList) {
                    if ((bean.getOrginalEstamt() != null) && !bean.getOrginalEstamt().isEmpty()) {
                        amount = CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(bean.getOrginalEstamt()));
                        bean.setFormattedCurrency(amount);
                    }
                    if ((bean.getExpenditureAmt() != null) && !bean.getExpenditureAmt().isEmpty()) {
                        amount1 = CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(bean.getExpenditureAmt()));
                        bean.setFormattedCurrency1(amount1);
                    }
                    if ((bean.getRevisedEstamt() != null) && !bean.getRevisedEstamt().isEmpty()) {
                        amount3 = CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(bean.getRevisedEstamt()));
                        bean.setFormattedCurrency3(amount3);
                    }
                    BigDecimal balAmt = null;
                    if ((bean.getRevisedEstamt() != null && !bean.getRevisedEstamt().isEmpty())
                            && (bean.getExpenditureAmt() != null && !bean.getExpenditureAmt().isEmpty())) {
                        balAmt = new BigDecimal(bean.getRevisedEstamt()).subtract((new BigDecimal(bean.getExpenditureAmt())));
                    } else if(bean.getOrginalEstamt()!=null){
                        balAmt = new BigDecimal(bean.getOrginalEstamt()).subtract((new BigDecimal(bean.getExpenditureAmt())));
                    }
                    if(balAmt!=null)
                    amount2 = CommonMasterUtility.getAmountInIndianCurrency(balAmt);
                    bean.setFormattedCurrency2(amount2);
                }
            }
        }
        final Map<Long, String> budrexpMap = new LinkedHashMap<>();
        final List<AccountBudgetProjectedExpenditureBean> list = accountBudgetProjectedExpenditureService
                .findAllBudgetProjectedExpenditureByOrgId(orgId);
        final List<LookUp> fundTypeLevel = CommonMasterUtility.getListLookup(
                MainetConstants.BUDGET_CODE.FUND_FIELD_DEPT_STATUS_PREFIX,
                UserSession.getCurrent().getOrganisation());
        for (final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean : list) {
            final Long prBudgetCodeid = accountBudgetProjectedExpenditureBean.getPrBudgetCodeid();
             String budgetCode=null;
            for (final LookUp lookUp : fundTypeLevel) {
            if (MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
            	budgetCode = accountBudgetCodeService.getBudgetCode(prBudgetCodeid, orgId);
            }
            if (MainetConstants.BUDGET_CODE.OBJECT_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
            	budgetCode = accountBudgetCodeService.getBudgetCodes(prBudgetCodeid, orgId);
            }
            }
            budrexpMap.put(prBudgetCodeid, budgetCode);

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
					chList = chList.stream().filter(p -> p.getDpDeptid().equals(deptId))
							.collect(Collectors.toList());
				}
			}
        } 
        model.addAttribute(MainetConstants.AccountBudgetProjectedExpenditure.BUDGET_EXPENDITURE_MAP, budrexpMap);

        final AccountBudgetProjectedExpenditureBean bean = new AccountBudgetProjectedExpenditureBean();
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FUND_MAP, fundMap);
        model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FUNCTION_MAP, functionMap);
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
        log("AccountBudgetProjectedExpenditureController-'formForCreate' : 'formForCreate'");
        String result = MainetConstants.CommonConstant.BLANK;

        final AccountBudgetProjectedExpenditureBean bean = new AccountBudgetProjectedExpenditureBean();
        populateModel(model, bean, FormMode.CREATE);
        result = JSP_FORM;
        return result;
    }

    private void populateModel(final Model model, final AccountBudgetProjectedExpenditureBean bean, final FormMode formMode)
            throws Exception {
        log("AccountBudgetProjectedExpenditureController-'populateModel' : populate model");

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

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        String defaultFieldFlag = MainetConstants.CommonConstant.BLANK;
        String defaultFunctionFlag = MainetConstants.CommonConstant.BLANK;
        final Organisation superUserOrganization = ApplicationSession.getInstance().getSuperUserOrganization();
        final int langId = UserSession.getCurrent().getLanguageId();

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
        if (department != null) {
            for (final Object[] dep : department) {
                if (dep[0] != null) {
                    deptMap.put((Long) (dep[0]), (String) dep[1]);
                }
            }
        }
        if(CollectionUtils.isNotEmpty(department)) {
			if (getSelfDeparmentConfiguration() != null
					&& MainetConstants.Y_FLAG.equalsIgnoreCase(getSelfDeparmentConfiguration())) {
				department = populateDepartmentBasedOnConfiguration(department);
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
    public String create(final AccountBudgetProjectedExpenditureBean tbAcBudgetProjectedExpenditure,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {
        log("AccountBudgetProjectedExpenditureBean-'create' : 'create'");
        String result = MainetConstants.CommonConstant.BLANK;

        if (!bindingResult.hasErrors()) {
            tbAcBudgetProjectedExpenditure.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
            final UserSession userSession = UserSession.getCurrent();
            tbAcBudgetProjectedExpenditure.setOrgid(userSession.getOrganisation().getOrgid());
            tbAcBudgetProjectedExpenditure.setLangId(userSession.getLanguageId());
            tbAcBudgetProjectedExpenditure.setUserId(userSession.getEmployee().getEmpId());
            tbAcBudgetProjectedExpenditure.setLmoddate(new Date());
            tbAcBudgetProjectedExpenditure.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
            final Organisation orgid = UserSession.getCurrent().getOrganisation();
            final int langId = UserSession.getCurrent().getLanguageId();
            populateModel(model, tbAcBudgetProjectedExpenditure, FormMode.CREATE);
            AccountBudgetProjectedExpenditureBean tbAcBudgetProjectedExpenditureCreated = accountBudgetProjectedExpenditureService
                    .saveBudgetProjectedExpenditureFormData(tbAcBudgetProjectedExpenditure, orgid, langId);
            if (tbAcBudgetProjectedExpenditureCreated == null) {
                tbAcBudgetProjectedExpenditureCreated = new AccountBudgetProjectedExpenditureBean();
            }
            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetProjectedExpenditureCreated);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            if (tbAcBudgetProjectedExpenditure.getPrExpenditureid() == null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        MainetConstants.AccountBudgetAdditionalSupplemental.RECORD_SAVE_SUCCESS);
            }
            if (tbAcBudgetProjectedExpenditure.getPrExpenditureid() != null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        MainetConstants.AccountBudgetAdditionalSupplemental.RECORD_UPDATE_SUCCESS);
            }
            tbAcBudgetProjectedExpenditure.setSuccessFlag(MainetConstants.FlagY);
            result = JSP_FORM;
        } else {
            tbAcBudgetProjectedExpenditure.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            populateModel(model, tbAcBudgetProjectedExpenditure, FormMode.CREATE);
            result = JSP_FORM;
        }
        return result;
    }

    @RequestMapping(params = "update", method = RequestMethod.POST)
    public String update(AccountBudgetProjectedExpenditureBean tbAcBudgetProjectedExpenditure,
            @RequestParam("prExpenditureid") final Long prExpenditureid,
            @RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {
        log("tbAcBudgetProjectedExpenditure-'gridData' : 'update'");
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
            tbAcBudgetProjectedExpenditure.setPrExpenditureid(prExpenditureid);
            tbAcBudgetProjectedExpenditure = accountBudgetProjectedExpenditureService
                    .getDetailsUsingProjectionId(tbAcBudgetProjectedExpenditure, orgId);
            final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                    PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
            final Long cpdBugtypeid = expLookup.getLookUpId();
            final Long cpdBugsubtypeId = tbAcBudgetProjectedExpenditure.getCpdBugsubtypeId();
            final Long dpDeptid = tbAcBudgetProjectedExpenditure.getDpDeptid();
            final Long faYearid = tbAcBudgetProjectedExpenditure.getFaYearid();
            final Organisation organisation = UserSession.getCurrent().getOrganisation();
            final int langId = UserSession.getCurrent().getLanguageId();

            Map<Long, String> budgetCode = null;
            for (final LookUp lookUp : fundTypeLevel) {
                if (MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    budgetCode = accountBudgetCodeService.findByAllExpBudgetHeads(faYearid, cpdBugtypeid, cpdBugsubtypeId,
                            dpDeptid, organisation, langId);
                }
                if (MainetConstants.BUDGET_CODE.OBJECT_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    budgetCode = accountBudgetCodeService.findByAllExpObjectTypeBudgetHeads(faYearid, cpdBugtypeid,
                            cpdBugsubtypeId, dpDeptid, organisation, langId);
                }
            }
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.ACCOUNT_BUDGET_CODE_MAP, budgetCode);
            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetProjectedExpenditure);
            populateModel(model, tbAcBudgetProjectedExpenditure, FormMode.UPDATE);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            log("BudgetopenBalanceMaster 'update' : update done - redirect");
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            log("BudgetopenBalanceMaster 'update' : update done - redirect");
            result = JSP_FORM;
        } else {
            log("BudgetopenBalanceMaster 'update' : binding errors");
            populateModel(model, tbAcBudgetProjectedExpenditure, FormMode.UPDATE);
            result = JSP_FORM;
        }
        return result;
    }

    @RequestMapping(params = "formForView", method = RequestMethod.POST)
    public String formForView(AccountBudgetProjectedExpenditureBean tbAcBudgetProjectedExpenditure,
            @RequestParam("prExpenditureid") final Long prExpenditureid,
            @RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {
        log("tbAcBudgetProjectedExpenditure-'gridData' : 'view'");
        String result = MainetConstants.CommonConstant.BLANK;

        if (!bindingResult.hasErrors()) {
            if (viewmode.equals(MainetConstants.VIEW)) {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.VIEW);
            } else {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
            }
            setModeView(viewmode);
            final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            tbAcBudgetProjectedExpenditure.setPrExpenditureid(prExpenditureid);
            tbAcBudgetProjectedExpenditure = accountBudgetProjectedExpenditureService
                    .getDetailsUsingProjectionId(tbAcBudgetProjectedExpenditure, orgId);
            if (tbAcBudgetProjectedExpenditure.getFaYearid() != null) {
                tbAcBudgetProjectedExpenditure.setFinancialYearDesc(
                        tbFinancialyearService.findFinancialYearDesc(tbAcBudgetProjectedExpenditure.getFaYearid()));
            }
            final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                    PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
            final Long cpdBugtypeid = expLookup.getLookUpId();
            final Long cpdBugsubtypeId = tbAcBudgetProjectedExpenditure.getCpdBugsubtypeId();
            final Long dpDeptid = tbAcBudgetProjectedExpenditure.getDpDeptid();
            final Map<Long, String> budgetCode = accountBudgetCodeService.finAllBudgetCodeByOrg(orgId);
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.ACCOUNT_BUDGET_CODE_MAP, budgetCode);
            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetProjectedExpenditure);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            log("BudgetopenBalanceMaster 'view' : view done - redirect");
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            log("BudgetopenBalanceMaster 'view' : view done - redirect");
            result = JSP_VIEW;
        } else {
            log("BudgetopenBalanceMaster 'view' : binding errors");
            populateModel(model, tbAcBudgetProjectedExpenditure, FormMode.UPDATE);
            result = JSP_VIEW;
        }
        return result;
    }

    @RequestMapping(params = "importExportExcelTemplateData")
    public String exportImportExcelTemplate(final Model model) throws Exception {
        String result = MainetConstants.CommonConstant.BLANK;
        final AccountBudgetProjectedExpenditureBean bean = new AccountBudgetProjectedExpenditureBean();
        populateModel(model, bean, FormMode.CREATE);
        fileUpload.sessionCleanUpForFileUpload();
        result = JSP_EXCELUPLOAD;
        return result;
    }

    @RequestMapping(params = "exportExcelTemplateData")
    public void exportAccountBudgetProjectedExpenditureExcelData(final HttpServletResponse response,
            final HttpServletRequest request) {

        try {
            WriteExcelData<AccountBudgetProjectedExpenditureUploadDto> data = new WriteExcelData<>(
                    MainetConstants.ACCOUNTBUDGETPROJECTEDEXPENDITUREUPLOADDTO
                            + MainetConstants.XLSX_EXT,
                    request, response);

            data.getExpotedExcelSheet(new ArrayList<AccountBudgetProjectedExpenditureUploadDto>(),
                    AccountBudgetProjectedExpenditureUploadDto.class);
        } catch (Exception ex) {
            throw new FrameworkException(ex.getMessage());
        }
    }

    @RequestMapping(params = "loadExcelData", method = RequestMethod.POST)
    public String loadValidateAndLoadExcelData(AccountBudgetProjectedExpenditureBean bean,
            final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
            final HttpServletRequest httpServletRequest) throws Exception {
        log("Action 'loadExcelData'");

        final ApplicationSession session = ApplicationSession.getInstance();
        final UserSession userSession = UserSession.getCurrent();
        final long orgId = userSession.getOrganisation().getOrgid();
        final int langId = userSession.getLanguageId();
        final Long userId = userSession.getEmployee().getEmpId();
        final String filePath = getUploadedFinePath();
        ReadExcelData<AccountBudgetProjectedExpenditureUploadDto> data = new ReadExcelData<>(filePath,
                AccountBudgetProjectedExpenditureUploadDto.class);
        data.parseExcelList();
        List<String> errlist = data.getErrorList();
        if (!errlist.isEmpty()) {
            bindingResult.addError(new org.springframework.validation.FieldError(
                    MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.MAIN_ENTITY_NAME,
                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                    session.getMessage("accounts.empty.excel")));
        } else {
            final List<AccountBudgetProjectedExpenditureUploadDto> accountBudgetProjectedExpenditureUploadDtos = data
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
            List<AccountBudgetProjectedExpenditureEntity> budgetProjectedExpenditure = accountBudgetProjectedExpenditureService
                    .getBudgetProjectedExpenditure(orgId);
            List<AccountFieldMasterBean> filedlist = tbAcFieldMasterService.findAllByOrgId(orgId);
            AccountBudgetProjectedExpenditureExcelValidator validator = new AccountBudgetProjectedExpenditureExcelValidator();
            List<AccountBudgetProjectedExpenditureUploadDto> accountBudgetProjectedExpenditureUploadList = validator
                    .excelValidation(accountBudgetProjectedExpenditureUploadDtos, bindingResult, financeMap, deptMap,
                            budgetCode, budgSubTypelist,filedlist);
            if (validator.count == 0) {
                validator.budgetExpenditureComb(accountBudgetProjectedExpenditureUploadList, bindingResult,
                        budgetProjectedExpenditure);
            }

            if (!bindingResult.hasErrors()) {
                for (AccountBudgetProjectedExpenditureUploadDto accountBudgetProjectedExpenditureUploadDto : accountBudgetProjectedExpenditureUploadList) {

                    accountBudgetProjectedExpenditureUploadDto
                            .setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                    accountBudgetProjectedExpenditureUploadDto.setLangId(Long.valueOf(langId));
                    accountBudgetProjectedExpenditureUploadDto.setUserId(userId);
                    accountBudgetProjectedExpenditureUploadDto.setLmoddate(new Date());
                    accountBudgetProjectedExpenditureUploadDto
                            .setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
                    accountBudgetProjectedExpenditureService.saveBudgetProjectedExpenditureExportData(
                            accountBudgetProjectedExpenditureUploadDto, orgId,
                            langId);
                }

                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        session.getMessage("accounts.success.excel"));
            }
        }
        model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MAIN_ENTITY_NAME, bindingResult);
        // populateModel(model, bean, FormMode.CREATE);
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
 
    /*
	 * This method gets the expenditure budget data based on the account heads and
	 * the department
	 */
	@RequestMapping(params = "viewExpDetails")
	public String viewExpenditureDetails(AccountBudgetProjectedExpenditureBean tbAcBudgetProjectedExpenditure,
            @RequestParam("prExpenditureid") final Long prExpenditureid,
            @RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {

		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        tbAcBudgetProjectedExpenditure.setPrExpenditureid(prExpenditureid);
        tbAcBudgetProjectedExpenditure = accountBudgetProjectedExpenditureService
                .getDetailsUsingProjectionId(tbAcBudgetProjectedExpenditure, orgId);
        final Long sacHeadId = secondaryheadMasterService.getSacHeadIdByBudgetId(tbAcBudgetProjectedExpenditure.getBugExpenditureMasterDtoList().get(0).getPrBudgetCodeid(), orgId);
		final List<Object[]> projectedExpList = accountBudgetProjectedExpenditureService
				.getExpenditureDetails(orgId, tbAcBudgetProjectedExpenditure.getFaYearid(),sacHeadId,tbAcBudgetProjectedExpenditure.getFieldId(),tbAcBudgetProjectedExpenditure.getDpDeptid());
		final AccountBillEntryMasterBean billEntryBean = new AccountBillEntryMasterBean();
		AccountBillEntryExpenditureDetBean billExpDetBean = null;
		final List<AccountBillEntryExpenditureDetBean> billExpBudgetDetBeanList = new ArrayList<>();
		BigDecimal appovedProposal = BigDecimal.ZERO;
		BigDecimal adminApproval = BigDecimal.ZERO;
		BigDecimal tenderApproval =  BigDecimal.ZERO;
		BigDecimal workOrderIssued = BigDecimal.ZERO;
		if ((projectedExpList != null) && !projectedExpList.isEmpty()) {
			for (final Object[] prExpDet : projectedExpList) {
				billExpDetBean = new AccountBillEntryExpenditureDetBean();
				 if (prExpDet[2] != null) {
					 appovedProposal=(new BigDecimal(prExpDet[2].toString()));
				} 
				 if (prExpDet[3] != null) {
					 adminApproval=(new BigDecimal(prExpDet[3].toString()));
				} 
				 if (prExpDet[4] != null) {
					 tenderApproval=(new BigDecimal(prExpDet[4].toString()));
				} 
				 if (prExpDet[5] != null) {
					 workOrderIssued=(new BigDecimal(prExpDet[5].toString()));
				} 
				billExpDetBean.setOriginalEstimate(appovedProposal);
				billExpDetBean.setBalProvisionAmount(adminApproval);
				billExpDetBean.setBalanceAmount(tenderApproval);
				billExpDetBean.setActualAmount(workOrderIssued);
				billExpBudgetDetBeanList.add(billExpDetBean);
				billEntryBean.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
				
				} 
			
		} else {
			billExpDetBean = new AccountBillEntryExpenditureDetBean();
			billExpDetBean.setOriginalEstimate(appovedProposal);
			billExpDetBean.setBalProvisionAmount(adminApproval);
			billExpDetBean.setBalanceAmount(tenderApproval);
			billExpDetBean.setActualAmount(workOrderIssued);
			billExpBudgetDetBeanList.add(billExpDetBean);
			billEntryBean.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
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

}
