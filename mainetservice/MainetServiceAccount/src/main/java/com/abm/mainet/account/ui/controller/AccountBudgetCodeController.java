
package com.abm.mainet.account.ui.controller;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.abm.mainet.account.dto.AccountBudgetCodeUploadDto;
import com.abm.mainet.account.dto.ReadExcelData;
import com.abm.mainet.account.service.BudgetCodeService;
import com.abm.mainet.account.ui.validator.AccountBudgetHeadExcelValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountBudgetCodeBean;
import com.abm.mainet.common.integration.acccount.dto.TbAcCodingstructureMas;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountFunctionMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountFundMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountHeadPrimaryAccountCodeMasterService;
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

/**
 * @author prasad.kancharla
 *
 */
@Controller
@RequestMapping("/AccountBudgetCode.html")
public class AccountBudgetCodeController extends AbstractController {

    private static final String MAIN_ENTITY_NAME = "tbAcBudgetCode";
    private static final String MAIN_LIST_NAME = "list";
    private static final String JSP_FORM = "tbAcBudgetCode/form";
    private static final String JSP_VIEW = "tbAcBudgetCode/view";
    private static final String JSP_LIST = "tbAcBudgetCode/list";
    private static final String JSP_EXCELUPLOAD = "tbAcBudgetCode/excelupload";
    private String modeView = MainetConstants.BLANK;

    @Resource
    private BudgetCodeService accountBudgetCodeService;
    @Resource
    private AccountFunctionMasterService tbAcFunctionMasterService;
    @Resource
    private AccountFieldMasterService tbAcFieldMasterService;
    @Resource
    private AccountFundMasterService tbAcFundMasterService;
    @Resource
    private SecondaryheadMasterService tbAcSecondaryheadMasterService;
    @Resource
    private TbFinancialyearService tbFinancialyearService;
    @Autowired
    private DepartmentService departmentService;
    @Resource
    private TbAcCodingstructureMasService tbAcCodingstructureMasService;
    @Resource
    private TbOrganisationService tbOrganisationService;
    @Resource
    private AccountHeadPrimaryAccountCodeMasterService accountHeadPrimaryAccountCodeMasterService;
    @Autowired
    private IFileUploadService fileUpload;
    private List<AccountBudgetCodeBean> chList = null;

    public AccountBudgetCodeController() {
        super(AccountBudgetCodeController.class, MAIN_ENTITY_NAME);
        log("AccountBudgetCodeController created.");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(final HttpServletRequest request,
            final Model model) {
        log("AccountBudgetCode-'gridData' : 'Get grid Data'");
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
            @RequestParam("dpDeptid") final Long dpDeptid, @RequestParam("fundId") final Long fundId,
            @RequestParam("fieldId") final Long fieldId, @RequestParam("functionId") final Long functionId,
            @RequestParam("sacHeadId") final Long sacHeadId,
            @RequestParam("cpdIdStatusFlag") final String cpdIdStatusFlag,
            @RequestParam("objectHeadType") final String objectHeadType) {
        log("AccountBudgetCode-'getjqGridsearch' : 'get jqGrid search data'");

        Long defaultOrgId = null;
        defaultOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

        final List<LookUp> activeDeActiveMap = CommonMasterUtility.getListLookup(
                MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_STATUS_PREFIX, UserSession.getCurrent().getOrganisation());
        chList = new ArrayList<>();
        chList.clear();
        chList = accountBudgetCodeService.findByAllGridSearchData(dpDeptid, fundId, fieldId, functionId, sacHeadId,cpdIdStatusFlag, defaultOrgId, objectHeadType);
        Long saHeadId = null;
        String pacHeadCompositeCode = null;
        String sacHeadCode = null;
        if (chList != null) {
            for (final AccountBudgetCodeBean bean : chList) {
                final String cpdIdStatsFlag = bean.getCpdIdStatusFlag();
                String lookUpCode = null;
                for (final LookUp lookUp : activeDeActiveMap) {
                    lookUpCode = lookUp.getLookUpCode();
                    if (lookUpCode.equals(cpdIdStatsFlag)) {
                        bean.setCpdIdStatusFlagDup(lookUp.getLookUpDesc());
                    }
                }
                if (objectHeadType.equals(MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS)) {
                    if (bean.getPacHeadId() != null) {
                        bean.setAccountHeads(bean.getPacHeadCode());
                    }
                } else {
                    if (bean.getSacHeadId() != null) {
                        saHeadId = bean.getSacHeadId();
                        pacHeadCompositeCode = tbAcSecondaryheadMasterService.findByPacHeadId(saHeadId, defaultOrgId);
                        sacHeadCode = bean.getSacHeadCode();
                        bean.setAccountHeads(pacHeadCompositeCode + MainetConstants.SEPARATOR_AC_HEAD + sacHeadCode);
                    }
                }
            }
        }
        return chList;
    }

    @RequestMapping(params = "getjqGridload", method = RequestMethod.POST)
    public String loadBudgetReappropriationData(final AccountBudgetCodeBean bean, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest request,
            final BindingResult bindingResult) throws Exception {
        log("AccountReappropriationMaster-'getjqGridload' : 'get jqGridload data'");
        String result = MainetConstants.CommonConstant.BLANK;
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        result = JSP_FORM;
        return result;
    }

    @RequestMapping()
    public String getList(final Model model) throws Exception {
        log("AccountBudgetCode-'list' :'list'");
        String result = MainetConstants.CommonConstant.BLANK;
        final List<LookUp> activeDeActiveMap = CommonMasterUtility.getListLookup(
                MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_STATUS_PREFIX, UserSession.getCurrent().getOrganisation());

        final LookUp primaryStatusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS,
                MainetConstants.BUDGET_CODE.FUND_FIELD_DEPT_STATUS_PREFIX, UserSession.getCurrent().getLanguageId(),
                UserSession.getCurrent().getOrganisation());
        final String primaryStatusLookUpCode = primaryStatusLookup.getLookUpCode();

        chList = new ArrayList<>();
        chList.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        if (chList != null) {
            Long sacHeadId = null;
            String pacHeadCompositeCode = null;
            String sacHeadCode = null;
            for (final AccountBudgetCodeBean bean : chList) {
                final String cpdIdStatusFlag = bean.getCpdIdStatusFlag();
                String lookUpCode = null;
                for (final LookUp lookUp : activeDeActiveMap) {
                    lookUpCode = lookUp.getLookUpCode();
                    if (lookUpCode.equals(cpdIdStatusFlag)) {
                        bean.setCpdIdStatusFlagDup(lookUp.getLookUpDesc());
                    }
                }
                if (primaryStatusLookUpCode != null) {
                    if (primaryStatusLookUpCode.equals(MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS)) {
                        if (bean.getPacHeadId() != null) {
                            bean.setAccountHeads(bean.getPacHeadCode());
                            bean.setObjectHeadType(MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS);
                        }
                    } else {
                        if (bean.getSacHeadId() != null) {
                            sacHeadId = bean.getSacHeadId();
                            pacHeadCompositeCode = tbAcSecondaryheadMasterService.findByPacHeadId(sacHeadId, orgId);
                            sacHeadCode = bean.getSacHeadCode();
                            bean.setAccountHeads(
                                    pacHeadCompositeCode + MainetConstants.SEPARATOR_AC_HEAD + sacHeadCode);
                        }
                    }
                }
            }
        }

        final AccountBudgetCodeBean bean = new AccountBudgetCodeBean();
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        model.addAttribute(MAIN_LIST_NAME, chList);
        result = JSP_LIST;
        return result;
    }

    @RequestMapping(params = "form")
    public String formForCreate(final Model model) throws Exception {
        log("AccountBudgetCode-'formForCreate' : 'formForCreate'");
        String result = MainetConstants.CommonConstant.BLANK;
        final AccountBudgetCodeBean bean = new AccountBudgetCodeBean();
        populateModel(model, bean, FormMode.CREATE);
        result = JSP_FORM;
        return result;
    }

    private void populateModel(final Model model, final AccountBudgetCodeBean bean, final FormMode formMode)
            throws Exception {
        log("AccountBudgetCode-'populateModel' : populate model");
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final Organisation superUserOrganization = ApplicationSession.getInstance().getSuperUserOrganization();
        final int langId = UserSession.getCurrent().getLanguageId();
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);

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
                        model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.BUG_SUB_TYPE_LEVEL_MAP,
                                bugSubTypelevelMap);
                        model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.BUDGET_SUBTYPE_STATUS,
                                MainetConstants.MASTER.Y);
                    }
                }
            }
        }

        final List<LookUp> activeDeActiveMap = CommonMasterUtility.getListLookup(
                MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_STATUS_PREFIX, UserSession.getCurrent().getOrganisation());
        model.addAttribute(MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_MAP, activeDeActiveMap);

        final List<LookUp> fundTypeLevel = CommonMasterUtility.getListLookup(
                MainetConstants.BUDGET_CODE.FUND_FIELD_DEPT_STATUS_PREFIX, UserSession.getCurrent().getOrganisation());

        String defaultFundFlag = MainetConstants.CommonConstant.BLANK;
        String defaultFieldFlag = MainetConstants.CommonConstant.BLANK;
        String defaultFunctionFlag = MainetConstants.CommonConstant.BLANK;
        String defaultPrimaryHeadFlag = MainetConstants.CommonConstant.BLANK;
        String defaultSecondaryFlag = MainetConstants.CommonConstant.BLANK;

        Long defaultOrgId = null;
        if (isDafaultOrgExist) {
            defaultOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
        } else {
            defaultOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
        }

        final List<TbAcCodingstructureMas> tbCodingList = tbAcCodingstructureMasService.findAllWithOrgId(defaultOrgId);

        for (final LookUp lookUp : fundTypeLevel) {

            /*
             * For getting the fund data based on default configuration either from default ULB or child ULB
             */
            if (MainetConstants.BUDGET_CODE.FUND_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                if (formMode.equals(FormMode.CREATE) || formMode.equals(FormMode.UPDATE)) {
                    final LookUp cmdFundPrefix = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                            PrefixConstants.FUND_CPD_VALUE, PrefixConstants.CMD,
                            UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
                    if ((tbCodingList != null) && !tbCodingList.isEmpty()) {
                        for (final TbAcCodingstructureMas master : tbCodingList) {
                            if (cmdFundPrefix.getLookUpId() == master.getComCpdId()) {
                                defaultFundFlag = master.getDefineOnflag();
                                if (defaultFundFlag.equals(MainetConstants.MASTER.Y) && isDafaultOrgExist) {
                                    model.addAttribute(
                                            MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FUND_MASTER_ITEMS,
                                            tbAcFundMasterService.getFundMasterStatusLastLevels(defaultOrgId,
                                                    superUserOrganization, langId));
                                } else {
                                    model.addAttribute(
                                            MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FUND_MASTER_ITEMS,
                                            tbAcFundMasterService.getFundMasterStatusLastLevels(orgId, organisation,
                                                    langId));

                                }
                            }
                        }
                    }
                }
                if (formMode.equals(FormMode.VIEW)) {

                    final LookUp cmdFundPrefix = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                            PrefixConstants.FUND_CPD_VALUE, PrefixConstants.CMD,
                            UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
                    if ((tbCodingList != null) && !tbCodingList.isEmpty()) {
                        for (final TbAcCodingstructureMas master : tbCodingList) {
                            if (cmdFundPrefix.getLookUpId() == master.getComCpdId()) {
                                defaultFundFlag = master.getDefineOnflag();
                                if (defaultFundFlag.equals(MainetConstants.MASTER.Y) && isDafaultOrgExist) {
                                    model.addAttribute(
                                            MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FUND_MASTER_ITEMS,
                                            tbAcFundMasterService.getFundMasterLastLevels(defaultOrgId));
                                } else {
                                    model.addAttribute(
                                            MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FUND_MASTER_ITEMS,
                                            tbAcFundMasterService.getFundMasterLastLevels(orgId));
                                }
                            }
                        }
                    }
                }
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
            if (MainetConstants.BUDGET_CODE.DEPT_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                final Map<Long, String> deptMap = new LinkedHashMap<>(0);
                List<Object[]> department = null;
                department = departmentService.getAllDeptTypeNames();
                for (final Object[] dep : department) {
                    if (dep[0] != null) {
                        deptMap.put((Long) (dep[0]), (String) dep[1]);
                    }
                }
                model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.DEPT_MAP, deptMap);
                model.addAttribute(MainetConstants.AccountBudgetCode.DEPT_STATUS, MainetConstants.MASTER.Y);
            }
             //not required for configuration based as samadhan sir told
            //if (MainetConstants.BUDGET_CODE.FUNCTION_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
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
          //  }

            if (MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                if (formMode.equals(FormMode.CREATE) || formMode.equals(FormMode.UPDATE)) {

                    final LookUp cmdPrimaryHeadPrefix = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                            PrefixConstants.AHP, PrefixConstants.CMD, UserSession.getCurrent().getLanguageId(),
                            UserSession.getCurrent().getOrganisation());
                    if ((tbCodingList != null) && !tbCodingList.isEmpty()) {
                        for (final TbAcCodingstructureMas master : tbCodingList) {
                            if (cmdPrimaryHeadPrefix.getLookUpId() == master.getComCpdId()) {
                                defaultPrimaryHeadFlag = master.getDefineOnflag();
                                final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                                        MainetConstants.MASTER.A, PrefixConstants.ACN,
                                        UserSession.getCurrent().getLanguageId(),
                                        UserSession.getCurrent().getOrganisation());
                                final Long activeStatusId = lookUpFieldStatus.getLookUpId();
                                if (defaultPrimaryHeadFlag.equals(MainetConstants.MASTER.Y) && isDafaultOrgExist) {
                                    model.addAttribute(
                                            MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.PAC_HEAD_MASTER_ITEMS,
                                            accountHeadPrimaryAccountCodeMasterService
                                                    .getPrimaryMasterStatusLastLevels(defaultOrgId, activeStatusId));
                                } else {
                                    model.addAttribute(
                                            MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.PAC_HEAD_MASTER_ITEMS,
                                            accountHeadPrimaryAccountCodeMasterService
                                                    .getPrimaryMasterStatusLastLevels(orgId, activeStatusId));
                                }
                            }
                        }
                    }
                }
                if (formMode.equals(FormMode.VIEW)) {

                    final LookUp cmdPrimaryHeadPrefix = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                            PrefixConstants.AHP, PrefixConstants.CMD, UserSession.getCurrent().getLanguageId(),
                            UserSession.getCurrent().getOrganisation());
                    if ((tbCodingList != null) && !tbCodingList.isEmpty()) {
                        for (final TbAcCodingstructureMas master : tbCodingList) {
                            if (cmdPrimaryHeadPrefix.getLookUpId() == master.getComCpdId()) {
                                defaultPrimaryHeadFlag = master.getDefineOnflag();
                                if (defaultPrimaryHeadFlag.equals(MainetConstants.MASTER.Y) && isDafaultOrgExist) {
                                    model.addAttribute(
                                            MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.PAC_HEAD_MASTER_ITEMS,
                                            accountHeadPrimaryAccountCodeMasterService
                                                    .getPrimaryMasterLastLevels(defaultOrgId));
                                } else {
                                    model.addAttribute(
                                            MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.PAC_HEAD_MASTER_ITEMS,
                                            accountHeadPrimaryAccountCodeMasterService
                                                    .getPrimaryMasterLastLevels(orgId));
                                }
                            }
                        }
                    }
                }
                model.addAttribute(MainetConstants.AccountBudgetCode.PRIMARY_STATUS, MainetConstants.MASTER.Y);
                bean.setObjectHeadType(MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS);
            }

            if (MainetConstants.BUDGET_CODE.OBJECT_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                if (formMode.equals(FormMode.CREATE) || formMode.equals(FormMode.UPDATE)) {

                    final LookUp cmdSecondaryHeadPrefix = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                            PrefixConstants.SECONDARY_CPD_VALUE, PrefixConstants.CMD,
                            UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
                    if ((tbCodingList != null) && !tbCodingList.isEmpty()) {
                        for (final TbAcCodingstructureMas master : tbCodingList) {
                            if (cmdSecondaryHeadPrefix.getLookUpId() == master.getComCpdId()) {
                                defaultSecondaryFlag = master.getDefineOnflag();
                                if (defaultSecondaryFlag.equals(MainetConstants.MASTER.Y) && isDafaultOrgExist) {
                                    model.addAttribute(
                                            MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.PAC_HEAD_MASTER_ITEMS,
                                            tbAcSecondaryheadMasterService.findStatusPrimarySecondaryHeadData(
                                                    defaultOrgId, superUserOrganization, langId));
                                } else {
                                    model.addAttribute(
                                            MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.PAC_HEAD_MASTER_ITEMS,
                                            tbAcSecondaryheadMasterService.findStatusPrimarySecondaryHeadData(orgId,
                                                    organisation, langId));
                                }
                            }
                        }
                    }
                }
                if (formMode.equals(FormMode.VIEW)) {

                    final LookUp cmdSecondaryHeadPrefix = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                            PrefixConstants.SECONDARY_CPD_VALUE, PrefixConstants.CMD,
                            UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
                    if ((tbCodingList != null) && !tbCodingList.isEmpty()) {
                        for (final TbAcCodingstructureMas master : tbCodingList) {
                            if (cmdSecondaryHeadPrefix.getLookUpId() == master.getComCpdId()) {
                                defaultSecondaryFlag = master.getDefineOnflag();
                                if (defaultSecondaryFlag.equals(MainetConstants.MASTER.Y) && isDafaultOrgExist) {
                                    model.addAttribute(
                                            MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.PAC_HEAD_MASTER_ITEMS,
                                            tbAcSecondaryheadMasterService.findPrimarySecondaryHead(defaultOrgId));
                                } else {
                                    model.addAttribute(
                                            MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.PAC_HEAD_MASTER_ITEMS,
                                            tbAcSecondaryheadMasterService.findPrimarySecondaryHead(orgId));
                                }
                            }
                        }
                    }
                }
                model.addAttribute(MainetConstants.AccountBudgetCode.OBJECTWISE_STATUS, MainetConstants.MASTER.Y);
                bean.setObjectHeadType(MainetConstants.BUDGET_CODE.OBJECT_WISE_STATUS);
            }
        }

        model.addAttribute(MAIN_ENTITY_NAME, bean);
        if (formMode == FormMode.CREATE) {
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE,
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE_CREATE);
            model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, false);
        } else if (formMode == FormMode.UPDATE) {
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE,
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE_UPDATE);
        }
    }

    @RequestMapping(params = "getBudgetHeadDescDetails", method = RequestMethod.POST)
    public @ResponseBody Map<StringBuilder, StringBuilder> getBudgetHeadDescDetails(final AccountBudgetCodeBean bean,
            final HttpServletRequest request, final Model model, @RequestParam("cnt") final int cnt) {
        final StringBuilder builder = generateBudgetHeadDesc(bean, cnt);
        final Map<StringBuilder, StringBuilder> revBalMap = new HashMap<>();
        revBalMap.put(builder, builder);
        return revBalMap;
    }

    private StringBuilder generateBudgetHeadDesc(final AccountBudgetCodeBean bean, final int cnt) {

        final Long dpDeptid = bean.getDpDeptid();
        Long fundId = null;
        if (bean.getFundId() != null) {
            fundId = bean.getFundId();
        }
        final Long functionId = bean.getBudgCodeMasterDtoList().get(cnt).getFunctionId();
        Long fieldId = null;
        if (bean.getFieldId() != null) {
            fieldId = bean.getFieldId();
        }
        final Long sacHeadId = bean.getBudgCodeMasterDtoList().get(cnt).getSacHeadId();
        /*
         * orgId not Required for this condition wise related all areas.
         */
        final String deptCode = departmentService.getDeptCode(dpDeptid);
        final String fundCode = tbAcFundMasterService.getFundCode(fundId);
        final String fieldCode = tbAcFieldMasterService.getFieldCode(fieldId);
        String functionCode = tbAcFunctionMasterService.getFunctionCode(functionId);
        String pacHeadsacHeadCodeDesc = MainetConstants.CommonConstant.BLANK;
        if (bean.getObjectHeadType().equals(MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS)) {
            pacHeadsacHeadCodeDesc = accountHeadPrimaryAccountCodeMasterService.findByPrimaryHeadCodeDesc(sacHeadId);
        } else {
        	functionCode=functionCode.replace(MainetConstants.HYPHEN, MainetConstants.BLANK);
            pacHeadsacHeadCodeDesc = tbAcSecondaryheadMasterService.findByPrimarySacHeadCodeDesc(sacHeadId);
        }
        final StringBuilder builder = new StringBuilder();
        builder.append(deptCode == null ? MainetConstants.CommonConstant.BLANK : deptCode + MainetConstants.SEPARATOR)
                .append(fundCode == null ? MainetConstants.CommonConstant.BLANK : fundCode + MainetConstants.SEPARATOR)
                .append(fieldCode == null ? MainetConstants.CommonConstant.BLANK
                        : fieldCode + MainetConstants.SEPARATOR)
                .append(functionCode == null ? MainetConstants.CommonConstant.BLANK
                        : functionCode + MainetConstants.SEPARATOR)
                .append(pacHeadsacHeadCodeDesc == null ? MainetConstants.CommonConstant.BLANK : pacHeadsacHeadCodeDesc);
        return builder;
    }

    @RequestMapping(params = "getBudgetRevDuplicateGridloadData", method = RequestMethod.POST)
    public @ResponseBody boolean findBudgetOpenBalDuplicateCombination(final AccountBudgetCodeBean bean,
            final HttpServletRequest request, final Model model, final BindingResult bindingResult,
            @RequestParam("cnt") final int cnt) {
        boolean isValidationError = false;

        final Long defaultOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

        final Long dpDeptid = bean.getDpDeptid();
        Long fundId = null;
        if (bean.getFundId() != null) {
            fundId = bean.getFundId();
        }
        final Long functionId = bean.getBudgCodeMasterDtoList().get(cnt).getFunctionId();
        Long fieldId = null;
        if (bean.getFieldId() != null) {
            fieldId = bean.getFieldId();
        }
        final Long sacHeadId = bean.getBudgCodeMasterDtoList().get(cnt).getSacHeadId();
        final String objectHeadType = bean.getObjectHeadType();
        if (accountBudgetCodeService.isCombinationExists(dpDeptid, fundId, functionId, fieldId, sacHeadId, defaultOrgId,
                objectHeadType)) {
            bindingResult.addError(
                    new org.springframework.validation.FieldError(MainetConstants.AccountBudgetCode.BUDGET_CODE,
                            MainetConstants.CommonConstant.BLANK, null, false, new String[] { MainetConstants.ERRORS },
                            null, ApplicationSession.getInstance().getMessage(MainetConstants.CommonConstant.BLANK)));
            isValidationError = true;
        }
        return isValidationError;
    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String create(final AccountBudgetCodeBean tbAcBudgetCode, final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {
        log("AccountBudgetCodeBean-'create' : 'create'");
        String result = MainetConstants.CommonConstant.BLANK;
        if (!bindingResult.hasErrors()) {
            tbAcBudgetCode.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
            final UserSession userSession = UserSession.getCurrent();
            tbAcBudgetCode.setOrgid(userSession.getOrganisation().getOrgid());
            tbAcBudgetCode.setUserId(userSession.getEmployee().getEmpId());
            tbAcBudgetCode.setLmoddate(new Date());
            tbAcBudgetCode.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
            populateModel(model, tbAcBudgetCode, FormMode.CREATE);
            final Organisation orgid = UserSession.getCurrent().getOrganisation();
            final int langId = UserSession.getCurrent().getLanguageId();
            AccountBudgetCodeBean tbAcBudgetCodeCreated = accountBudgetCodeService
                    .saveBudgetCodeFormData(tbAcBudgetCode, orgid, langId);
            if (tbAcBudgetCodeCreated == null) {
                tbAcBudgetCodeCreated = new AccountBudgetCodeBean();
            }
            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetCodeCreated);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            if (tbAcBudgetCode.getPrBudgetCodeid() == null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        MainetConstants.AccountBudgetAdditionalSupplemental.RECORD_SAVE_SUCCESS);
            }
            if (tbAcBudgetCode.getPrBudgetCodeid() != null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        MainetConstants.AccountBudgetAdditionalSupplemental.RECORD_UPDATE_SUCCESS);
            }
            tbAcBudgetCode.setSuccessFlag(MainetConstants.MASTER.Y);
            result = JSP_FORM;
        } else {
            tbAcBudgetCode.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            populateModel(model, tbAcBudgetCode, FormMode.CREATE);
            tbAcBudgetCode.setSuccessFlag(MainetConstants.MASTER.N);
            result = JSP_FORM;
        }
        return result;
    }

    @RequestMapping(params = "update", method = RequestMethod.POST)
    public String update(AccountBudgetCodeBean tbAcBudgetCode,
            @RequestParam("prBudgetCodeid") final Long prBudgetCodeid,
            @RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
            final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
            final HttpServletRequest httpServletRequest) throws Exception {
        log("tbAcBudgetCode-'gridData' : 'update'");
        String result = MainetConstants.CommonConstant.BLANK;

        final LookUp primaryStatusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS,
                MainetConstants.BUDGET_CODE.FUND_FIELD_DEPT_STATUS_PREFIX, UserSession.getCurrent().getLanguageId(),
                UserSession.getCurrent().getOrganisation());
        final String primaryStatusLookUpCode = primaryStatusLookup.getLookUpCode();

        if (!bindingResult.hasErrors()) {
            if (viewmode.equals(MainetConstants.EDIT)) {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.EDIT);
            } else {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
            }
            setModeView(viewmode);
            final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            UserSession.getCurrent().getOrganisation();
            UserSession.getCurrent().getLanguageId();
            tbAcBudgetCode.setPrBudgetCodeid(prBudgetCodeid);
            tbAcBudgetCode = accountBudgetCodeService.getDetailsUsingPrBudgetCodeId(tbAcBudgetCode, orgId,
                    primaryStatusLookUpCode);
            final List<LookUp> activeDeActiveMap = CommonMasterUtility.getListLookup(
                    MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_STATUS_PREFIX,
                    UserSession.getCurrent().getOrganisation());
            final String cpdIdStatusFlag = tbAcBudgetCode.getCpdIdStatusFlag();
            String lookUpCode = null;
            for (final LookUp lookUp : activeDeActiveMap) {
                lookUpCode = lookUp.getLookUpCode();
                if (lookUpCode.equals(cpdIdStatusFlag)) {
                    tbAcBudgetCode.setCpdIdStatusFlagDup(lookUp.getLookUpCode());
                }
            }

            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetCode);
            populateModel(model, tbAcBudgetCode, FormMode.UPDATE);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            log("BudgetCodeMaster 'update' : update done - redirect");
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            log("BudgetCodeMaster 'update' : update done - redirect");
            result = JSP_FORM;
        } else {
            log("BudgetCodeMaster 'update' : binding errors");
            populateModel(model, tbAcBudgetCode, FormMode.UPDATE);
            result = JSP_FORM;
        }
        return result;
    }

    @RequestMapping(params = "formForView", method = RequestMethod.POST)
    public String formForView(AccountBudgetCodeBean tbAcBudgetCode,
            @RequestParam("prBudgetCodeid") final Long prBudgetCodeid,
            @RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
            final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
            final HttpServletRequest httpServletRequest) throws Exception {
        log("tbAcBudgetCode-'gridData' : 'view'");
        String result = MainetConstants.CommonConstant.BLANK;

        final LookUp primaryStatusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS,
                MainetConstants.BUDGET_CODE.FUND_FIELD_DEPT_STATUS_PREFIX, UserSession.getCurrent().getLanguageId(),
                UserSession.getCurrent().getOrganisation());
        final String primaryStatusLookUpCode = primaryStatusLookup.getLookUpCode();

        if (!bindingResult.hasErrors()) {
            if (viewmode.equals(MainetConstants.VIEW)) {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.VIEW);
            } else {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
            }
            setModeView(viewmode);
            final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            tbAcBudgetCode.setPrBudgetCodeid(prBudgetCodeid);
            tbAcBudgetCode = accountBudgetCodeService.getDetailsUsingPrBudgetCodeId(tbAcBudgetCode, orgId,
                    primaryStatusLookUpCode);
            final List<LookUp> activeDeActiveMap = CommonMasterUtility.getListLookup(
                    MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_STATUS_PREFIX,
                    UserSession.getCurrent().getOrganisation());
            final String cpdIdStatusFlag = tbAcBudgetCode.getCpdIdStatusFlag();
            String lookUpCode = null;
            for (final LookUp lookUp : activeDeActiveMap) {
                lookUpCode = lookUp.getLookUpCode();
                if (lookUpCode.equals(cpdIdStatusFlag)) {
                    tbAcBudgetCode.setCpdIdStatusFlagDup(lookUp.getLookUpCode());
                }
            }

            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetCode);
            populateModel(model, tbAcBudgetCode, FormMode.VIEW);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            log("BudgetCodeMaster 'view' : view done - redirect");
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            log("BudgetCodeMaster 'view' : view done - redirect");
            result = JSP_VIEW;
        } else {
            log("BudgetCodeMaster 'view' : binding errors");
            populateModel(model, tbAcBudgetCode, FormMode.VIEW);
            result = JSP_VIEW;
        }
        return result;
    }

    @RequestMapping(params = "importExportExcelTemplateData", method = RequestMethod.POST)
    public String exportImportExcelTemplate(final Model model) throws Exception {
        log("AccountBudgetCode-'exportImportExcelTemplate' : 'exportImportExcelTemplate'");
        String result = MainetConstants.CommonConstant.BLANK;
        final AccountBudgetCodeBean bean = new AccountBudgetCodeBean();
        populateModel(model, bean, FormMode.CREATE);
        fileUpload.sessionCleanUpForFileUpload();
        result = JSP_EXCELUPLOAD;
        return result;
    }

    @RequestMapping(params = "exportExcelTemplateData")
    public void exportAccountBudgetCodeMasterExcelData(final HttpServletResponse response,
            final HttpServletRequest request) {

        try {
            WriteExcelData<AccountBudgetCodeUploadDto> data = new WriteExcelData<>(
                    MainetConstants.ACCOUNTBUDGETCODEUPLOADDTO + MainetConstants.XLSX_EXT, request, response);

            data.getExpotedExcelSheet(new ArrayList<AccountBudgetCodeUploadDto>(), AccountBudgetCodeUploadDto.class);
        } catch (Exception ex) {
            throw new FrameworkException(ex.getMessage());
        }
    }

    @RequestMapping(params = "loadExcelData", method = RequestMethod.POST)
    public String loadValidateAndLoadExcelData(AccountBudgetCodeBean tbAcBudgetCode, final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {
        log("Action 'loadExcelData'");
        final ApplicationSession session = ApplicationSession.getInstance();
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        Organisation defaultOrg = null;
        if (isDafaultOrgExist) {
            defaultOrg = session.getSuperUserOrganization();
        } else {
            defaultOrg = UserSession.getCurrent().getOrganisation();
        }
        final int langId = UserSession.getCurrent().getLanguageId();
        final Long userId = UserSession.getCurrent().getEmployee().getEmpId();
        final Organisation orgid = UserSession.getCurrent().getOrganisation();
        final String filePath = getUploadedFinePath();
        ReadExcelData<AccountBudgetCodeUploadDto> data = new ReadExcelData<>(filePath,
                AccountBudgetCodeUploadDto.class);
        data.parseExcelList();
        List<String> errlist = data.getErrorList();
        if (!errlist.isEmpty()) {
            bindingResult.addError(
                    new org.springframework.validation.FieldError(MainetConstants.BUDGET_CODE.MAIN_ENTITY_NAME,
                            MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("accounts.empty.excel")));
        } else {
            final List<AccountBudgetCodeUploadDto> accountBudgetCodeUploadDtos = data.getParseData();
            boolean primaryDefaultFlag = false;
            if (isDafaultOrgExist) {
                primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                        ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(),
                        MainetConstants.MASTER.Y);
            } else {
                primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                        UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
            }
            boolean objectHeadDefaultFlag = false;
            if (isDafaultOrgExist) {
                objectHeadDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(
                        PrefixConstants.SECONDARY_CPD_VALUE,
                        ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(),
                        MainetConstants.MASTER.Y);
            } else {
                objectHeadDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(
                        PrefixConstants.SECONDARY_CPD_VALUE,
                        UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
            }
            boolean functionDefaultFlag = false;
            if (isDafaultOrgExist) {
                functionDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(
                        PrefixConstants.FUNCTION_CPD_VALUE,
                        ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(),
                        MainetConstants.MASTER.Y);
            } else {
                functionDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(
                        PrefixConstants.FUNCTION_CPD_VALUE, UserSession.getCurrent().getOrganisation().getOrgid(),
                        MainetConstants.MASTER.Y);
            }

            Map<Long, String> primaryHead = null;
            Map<String, String> primaryCompositeCodeAndDesc = null;
            if (isDafaultOrgExist && primaryDefaultFlag) {
                primaryHead = accountHeadPrimaryAccountCodeMasterService
                        .getPrimaryCompositeCode(session.getSuperUserOrganization(), langId);
                primaryCompositeCodeAndDesc = accountHeadPrimaryAccountCodeMasterService
                        .getPrimaryCompositeCodeAndDesc(defaultOrg, langId);
            } else if (isDafaultOrgExist && (primaryDefaultFlag == false)) {
                primaryHead = accountHeadPrimaryAccountCodeMasterService
                        .getPrimaryCompositeCode(UserSession.getCurrent().getOrganisation(), langId);
                primaryCompositeCodeAndDesc = accountHeadPrimaryAccountCodeMasterService
                        .getPrimaryCompositeCodeAndDesc(defaultOrg, langId);
            } else {
                primaryHead = accountHeadPrimaryAccountCodeMasterService
                        .getPrimaryCompositeCode(UserSession.getCurrent().getOrganisation(), langId);
                primaryCompositeCodeAndDesc = accountHeadPrimaryAccountCodeMasterService
                        .getPrimaryCompositeCodeAndDesc(defaultOrg, langId);
            }

            Map<Long, String> objectHead = null;
            if (isDafaultOrgExist && objectHeadDefaultFlag) {
                objectHead = tbAcSecondaryheadMasterService.findStatusWiseObjectHeadData(
                        session.getSuperUserOrganization(), langId);
            } else if (isDafaultOrgExist && (primaryDefaultFlag == false)) {
                objectHead = tbAcSecondaryheadMasterService.findStatusWiseObjectHeadData(
                        UserSession.getCurrent().getOrganisation(), langId);
            } else {
                objectHead = tbAcSecondaryheadMasterService.findStatusWiseObjectHeadData(
                        UserSession.getCurrent().getOrganisation(), langId);
            }

            Map<Long, String> functionHead = null;
            if (isDafaultOrgExist && functionDefaultFlag) {
                functionHead = tbAcFunctionMasterService.getFunctionCompositeCode(session.getSuperUserOrganization(),
                        langId);
            } else if (isDafaultOrgExist && (functionDefaultFlag == false)) {
                functionHead = tbAcFunctionMasterService
                        .getFunctionCompositeCode(UserSession.getCurrent().getOrganisation(), langId);
            } else {
                functionHead = tbAcFunctionMasterService
                        .getFunctionCompositeCode(UserSession.getCurrent().getOrganisation(), langId);
            }

            String cpdIdStatusFlag = MainetConstants.BUDGET_CODE.ACTIVE_VALUE;

            List<AccountBudgetCodeEntity> budgetHeadCodes = accountBudgetCodeService
                    .getBudgetHeadCodes(UserSession.getCurrent().getOrganisation().getOrgid(), cpdIdStatusFlag);

            final List<LookUp> fundTypeLevel = CommonMasterUtility.getListLookup(
                    MainetConstants.BUDGET_CODE.FUND_FIELD_DEPT_STATUS_PREFIX, UserSession.getCurrent().getOrganisation());
            String primaryHeadFlag = null;
            String objectHeadFlag = null;
            for (final LookUp lookUp : fundTypeLevel) {

                if (MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    primaryHeadFlag = MainetConstants.Y_FLAG;
                }
                if (MainetConstants.BUDGET_CODE.OBJECT_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    objectHeadFlag = MainetConstants.Y_FLAG;
                }
            }

            AccountBudgetHeadExcelValidator validator = new AccountBudgetHeadExcelValidator();
            final List<AccountBudgetCodeUploadDto> accountBudgetCodeUploadList = validator.excelValidation(
                    accountBudgetCodeUploadDtos, bindingResult, primaryHead, functionHead, primaryCompositeCodeAndDesc,
                    objectHead, orgid, primaryHeadFlag, objectHeadFlag);
            if (validator.count == 0) {
                validator.priFunCombValdn(accountBudgetCodeUploadList, bindingResult, budgetHeadCodes);
            }

            if (!bindingResult.hasErrors()) {
                for (AccountBudgetCodeUploadDto accountBudgetCodeUploadDto : accountBudgetCodeUploadList) {

                    accountBudgetCodeUploadDto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                    accountBudgetCodeUploadDto.setUserId(userId);
                    accountBudgetCodeUploadDto.setLangId(Long.valueOf(langId));
                    accountBudgetCodeUploadDto.setLmoddate(new Date());
                    accountBudgetCodeUploadDto.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
                    accountBudgetCodeUploadDto.setCpdIdStatusFlag(cpdIdStatusFlag);
                    accountBudgetCodeService.saveBudgetHeadExportData(accountBudgetCodeUploadDto, orgid, langId, primaryHeadFlag,
                            objectHeadFlag);
                }
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        session.getMessage("accounts.success.excel"));
            }
        }
        model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MAIN_ENTITY_NAME, bindingResult);
        populateModel(model, tbAcBudgetCode, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetCode);
        messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
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
    
    @RequestMapping(method = RequestMethod.POST, params = "getBudgetHead")
    public @ResponseBody Map<Long, String> getBudget(@RequestParam("functionId") final Long functionId,
    		final HttpServletRequest request) {
        Map<Long, String> budgetMap = new HashMap<>();
        budgetMap=  tbAcSecondaryheadMasterService.findStatusPrimarySecondaryHeadDataFuntId(UserSession.getCurrent().getOrganisation().getOrgid(),
        		UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId(),functionId);
       return budgetMap;
        
    }
    
    
}