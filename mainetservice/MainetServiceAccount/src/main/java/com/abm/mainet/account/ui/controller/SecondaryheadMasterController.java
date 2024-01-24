package com.abm.mainet.account.ui.controller;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.abm.mainet.account.dto.ReadExcelData;
import com.abm.mainet.account.service.AccountFinancialReportService;
import com.abm.mainet.account.service.BankAccountService;
import com.abm.mainet.account.service.TbAcChequebookleafMasService;
import com.abm.mainet.account.ui.validator.SecondaryHeadMasterExcelValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.TbBankaccount;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSacheadAccMapEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountSecondaryHeadMasterExportDto;
import com.abm.mainet.common.integration.acccount.dto.BankAccountMasterDto;
import com.abm.mainet.common.integration.acccount.dto.SecondaryheadMaster;
import com.abm.mainet.common.integration.acccount.dto.TbSacheadAccMapDTO;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountFunctionMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountFundMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountHeadPrimaryAccountCodeMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.TbBankaccountService;
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
 * Spring MVC controller for 'TbAcSecondaryheadMaster' management.
 */
@Controller
@RequestMapping("/tbAcSecondaryheadMaster.html")
public class SecondaryheadMasterController extends AbstractController {

    // --- Variables names ( to be used in JSP with Expression Language )
    private static final String MAIN_ENTITY_NAME = "secondaryheadMaster";
    private static final String MAIN_LIST_NAME = "list";
    private static final String listOfTbAcFunctionMasterItems = "listOfTbAcFunctionMasterItems";
    private static final String listOfTbAcPrimaryMasterItems = "listOfTbAcPrimaryMasterItems";
    private static final String listOfTbAcSecondaryMasterItems = "listOfTbAcSecondaryMasterItems";
    private static final String VendorList = "VendorList";
    private static final String legerTypeList = "legerTypeList";
    private static final String SacHeadId = "sacHeadId";
    // --- JSP pages names ( View name in the MVC model )
    private static final String JSP_FORM = "SecondaryheadMaster/form";
    private static final String JSP_VIEW = "SecondaryheadMaster/view";
    private static final String JSP_LIST = "SecondaryheadMaster/list";
    private static final String JSP_EXCELUPLOAD = "SecondaryheadMaster/excelupload";

    private static final String SAVE_ACTION_CREATE = "tbAcSecondaryheadMaster?create";
    private static final String SAVE_ACTION_UPDATE = "/tbAcSecondaryheadMaster/update";
    private static final String JSP_SEC_REPORT_VIEW="SECONDARY_HEADCODE";

    @Autowired
    private AccountHeadPrimaryAccountCodeMasterService tbAcPrimaryheadMasterService;
    @Autowired
    private SecondaryheadMasterService tbAcSecondaryheadMasterService;
    @Resource
    private TbAcVendormasterService tbVendormasterService;
    @Resource
    private TbBankaccountService tbBankaccountService;

    @Resource
    private TbAcChequebookleafMasService acChequebookleafMasService;
    @Resource
    private TbOrganisationService tbOrganisationService;
    @Resource
    private TbAcCodingstructureMasService tbAcCodingstructureMasService;
    @Resource
    private AccountFunctionMasterService tbAcFunctionMasterService;
    @Resource
    private AccountFieldMasterService tbAcFieldMasterService;
    @Resource
    private AccountFundMasterService tbAcFundMasterService;
    @Autowired
    private IFileUploadService fileUpload;
    @Resource
    private AccountFinancialReportService  accountfianService;
    
    @Resource
    private BankAccountService bankaccountService;
    

    private List<SecondaryheadMaster> list = null;

    public SecondaryheadMasterController() {
        super(SecondaryheadMasterController.class, MAIN_ENTITY_NAME);
        log("TbAcSecondaryheadMasterController created.");
    }

    /**
     * Populates the Spring MVC model with the given entity and eventually other useful data
     * 
     * @param model
     * @param tbAcSecondaryheadMaster
     */
    private void populateModel(final Model model, final SecondaryheadMaster secondaryheadMaster,
            final FormMode formMode) {
        model.addAttribute(MAIN_ENTITY_NAME, secondaryheadMaster);
        if (formMode == FormMode.CREATE) {
            model.addAttribute(MODE, MODE_CREATE); // The form is in "create" mode
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
        } else if (formMode == FormMode.UPDATE) {
            final List<LookUp> activeDeActiveMap = CommonMasterUtility.getListLookup(PrefixConstants.ACN,
                    UserSession.getCurrent().getOrganisation());
            model.addAttribute(MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_MAP, activeDeActiveMap);
            model.addAttribute(MODE, MODE_UPDATE); // The form is in "update" mode
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);

        } else if (formMode == FormMode.VIEW) {
            final List<LookUp> activeDeActiveMap = CommonMasterUtility.getListLookup(PrefixConstants.ACN,
                    UserSession.getCurrent().getOrganisation());
            model.addAttribute(MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_MAP, activeDeActiveMap);
            model.addAttribute(MODE, MODE_VIEW); // The form is in "VIEW" mode
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);

        }else if (formMode == FormMode.BULKEDITSAVE) {
        	model.addAttribute(MODE, "BULKEDITSAVE"); // The form is in "create" mode
            model.addAttribute(SAVE_ACTION, "BULKEDITSAVE");
        }
	   
    }

    /**
     * Shows a list with all the occurrences of TbAcSecondaryheadMaster found in the database
     * 
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping()
    public String list(final Model model) {
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        boolean secondaryDefaultFlag = false;
        boolean primaryDefaultFlag = false;

        final List<LookUp> activeDeActiveMap = CommonMasterUtility.getListLookup(
                MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_STATUS_PREFIX, UserSession.getCurrent().getOrganisation());

        if (isDafaultOrgExist) {
            secondaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(
                    PrefixConstants.SECONDARY_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            secondaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(
                    PrefixConstants.SECONDARY_CPD_VALUE, UserSession.getCurrent().getOrganisation().getOrgid(),
                    MainetConstants.MASTER.Y);
        }
        if (isDafaultOrgExist) {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        if (isDafaultOrgExist && secondaryDefaultFlag && primaryDefaultFlag) {
            list = tbAcSecondaryheadMasterService
                    .findAll(ApplicationSession.getInstance().getSuperUserOrganization().getOrgid());
            if ((UserSession.getCurrent().getOrganisation().getOrgid()) == (ApplicationSession.getInstance()
                    .getSuperUserOrganization().getOrgid())) {
                model.addAttribute(MainetConstants.FIELD_MASTER.PARENT_FLAG_STATUS, MainetConstants.MASTER.Y);
            } else {
                model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.N);
            }
        } else if (isDafaultOrgExist && primaryDefaultFlag && (secondaryDefaultFlag == false)) {
            list = tbAcSecondaryheadMasterService.findAll(UserSession.getCurrent().getOrganisation().getOrgid());
            if ((UserSession.getCurrent().getOrganisation().getOrgid()) == (ApplicationSession.getInstance()
                    .getSuperUserOrganization().getOrgid())) {
                model.addAttribute(MainetConstants.FIELD_MASTER.PARENT_FLAG_STATUS, MainetConstants.MASTER.Y);
            } else {
                model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.Y);
            }
        } else {
            list = tbAcSecondaryheadMasterService.findAll(UserSession.getCurrent().getOrganisation().getOrgid());
            model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.Y);
        }
        for (final SecondaryheadMaster secondaryheadMaster : list) {

            final List<LookUp> accountTypeLevel = CommonMasterUtility.getListLookup(PrefixConstants.CMD_PREFIX,
                    UserSession.getCurrent().getOrganisation());
            for (final LookUp lookUp : accountTypeLevel) {
                if (lookUp != null) {
                    if (PrefixConstants.AHP.equalsIgnoreCase(lookUp.getLookUpCode())) {
                        if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                            if (lookUp.getOtherField().equals(MainetConstants.MENU.Y)) {
                                model.addAttribute(MainetConstants.AccountBudgetCode.PRIMARY_STATUS,
                                        MainetConstants.MASTER.Y);
                            }
                        }
                    }

                    if (PrefixConstants.FUNCTION_CPD_VALUE.equalsIgnoreCase(lookUp.getLookUpCode())) {
                        if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                            if (lookUp.getOtherField().equals(MainetConstants.MENU.Y)) {
                                model.addAttribute(MainetConstants.FUNCTION_MASTER.FUNCTION_STATUS,
                                        MainetConstants.MASTER.Y);
                            }
                        }
                    }

                    if (PrefixConstants.FUND_CPD_VALUE.equalsIgnoreCase(lookUp.getLookUpCode())) {
                        if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                            if (lookUp.getOtherField().equals(MainetConstants.MENU.Y)) {
                                model.addAttribute(MainetConstants.FUND_MASTER.FUND_STATUS, MainetConstants.MASTER.Y);
                            }
                        }
                    }

                    if (PrefixConstants.FIELD_CPD_VALUE.equalsIgnoreCase(lookUp.getLookUpCode())) {
                        if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                            if (lookUp.getOtherField().equals(MainetConstants.MENU.Y)) {
                                model.addAttribute(MainetConstants.FIELD_MASTER.FIELD_STATUS, MainetConstants.MASTER.Y);
                            }
                        }
                    }

                    if (PrefixConstants.SECONDARY_CPD_VALUE.equalsIgnoreCase(lookUp.getLookUpCode())) {
                        if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                            if (lookUp.getOtherField().equals(MainetConstants.MENU.Y)) {
                                model.addAttribute(MainetConstants.SecondaryheadMaster.SECONDARY_STATUS,
                                        MainetConstants.MASTER.Y);
                            }
                        }
                    }
                }
            }
            secondaryheadMaster.setSacHeadCodeDesc(secondaryheadMaster.getSacHeadCode() + MainetConstants.SEPARATOR
                    + secondaryheadMaster.getSacHeadDesc());
            secondaryheadMaster.setPacHeadDesc(secondaryheadMaster.getPacHeadCode() + MainetConstants.SEPARATOR
                    + secondaryheadMaster.getPacHeadDesc());
            if ((secondaryheadMaster.getFundCode() != null) && !secondaryheadMaster.getFundCode().isEmpty()) {
                secondaryheadMaster.setFundCode(secondaryheadMaster.getFundCode());
            }
            if ((secondaryheadMaster.getFieldCode() != null) && !secondaryheadMaster.getFieldCode().isEmpty()) {
                secondaryheadMaster.setFieldCode(secondaryheadMaster.getFieldCode());
            }
            if ((secondaryheadMaster.getFunctionCode() != null) && !secondaryheadMaster.getFunctionCode().isEmpty()) {
                secondaryheadMaster.setFunctionCode(secondaryheadMaster.getFunctionCode());
            }

            final Long cpdIdStatsFlag = secondaryheadMaster.getSacStatusCpdId();
            Long lookUpId = null;
            for (final LookUp lookUp : activeDeActiveMap) {
                lookUpId = lookUp.getLookUpId();
                if (lookUpId.equals(cpdIdStatsFlag)) {
                    secondaryheadMaster.setCpdIdStatusFlag(lookUp.getLookUpDesc());
                }
            }
            if (isDafaultOrgExist && secondaryDefaultFlag) {
                if ((UserSession.getCurrent().getOrganisation().getOrgid()) == (ApplicationSession.getInstance()
                        .getSuperUserOrganization().getOrgid())) {
                    secondaryheadMaster.setDefaultOrgFlag(MainetConstants.MASTER.Y);
                }
            } else {
                secondaryheadMaster.setDefaultOrgFlag(MainetConstants.MASTER.Y);
            }
        }
        List<LookUp> ledgerTypeList = null;
        if (isDafaultOrgExist) {
            ledgerTypeList = CommonMasterUtility.getLookUps(PrefixConstants.SECONDARY_LOOKUPCODE,
                    ApplicationSession.getInstance().getSuperUserOrganization());
        } else {
            ledgerTypeList = CommonMasterUtility.getLookUps(PrefixConstants.SECONDARY_LOOKUPCODE,
                    UserSession.getCurrent().getOrganisation());
        }
        model.addAttribute(legerTypeList, ledgerTypeList);

        Map<Long, String> fundMap = new LinkedHashMap<Long, String>();
        Map<Long, String> fieldMap = new LinkedHashMap<Long, String>();
        Map<Long, String> functionMap = new LinkedHashMap<Long, String>();
        Map<Long, String> pacHeadMap = new LinkedHashMap<Long, String>();
        for (SecondaryheadMaster sacHeadList : list) {
            fundMap.put(sacHeadList.getFundId(), sacHeadList.getFundCode());
            fieldMap.put(sacHeadList.getFieldId(), sacHeadList.getFieldCode());
            functionMap.put(sacHeadList.getFunctionId(), sacHeadList.getFunctionCode());
            pacHeadMap.put(sacHeadList.getPacHeadId(), sacHeadList.getPacHeadDesc());
        }
        model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FUND_MASTER_ITEMS, fundMap);
        model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS, fieldMap);
        model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FUNCTION_MASTER_ITEMS, functionMap);
        model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.PAC_HEAD_MASTER_ITEMS, pacHeadMap);

        final SecondaryheadMaster tbAcSecondaryheadMaster = new SecondaryheadMaster();
        model.addAttribute(MAIN_ENTITY_NAME, tbAcSecondaryheadMaster);
        model.addAttribute(MAIN_LIST_NAME, list);
        return JSP_LIST;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> gridData(final HttpServletRequest request,
            final Model model) {
        log("SecondaryheadMasterController -'gridData' : 'Get grid Data'");
        final JQGridResponse response = new JQGridResponse<>();
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        response.setRows(list);
        response.setTotal(list.size());
        response.setRecords(list.size());
        response.setPage(page);
        model.addAttribute(MAIN_LIST_NAME, list);
        return response;
    }

    /**
     * Shows a form page in order to getjqGridsearch a Data.
     * 
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping(params = "getjqGridsearch")
    public @ResponseBody List getCheqData(final HttpServletRequest request, final Model model,
            @RequestParam("fundId") final Long fundId, @RequestParam("fieldId") final Long fieldId,
            @RequestParam("pacHeadId") final Long pacHeadId, @RequestParam("functionId") final Long functionId,
            @RequestParam("sacHeadId") final Long sacHeadId, @RequestParam("ledgerTypeId") final Long ledgerTypeId) {
        log("AccountBudgetCode-'getjqGridsearch' : 'get jqGrid search data'");
        final List<LookUp> activeDeActiveMap = CommonMasterUtility.getListLookup(
                MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_STATUS_PREFIX, UserSession.getCurrent().getOrganisation());
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        boolean primaryDefaultFlag = false;
        boolean secondaryDefaultFlag = false;

        if (isDafaultOrgExist) {
            secondaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(
                    PrefixConstants.SECONDARY_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            secondaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(
                    PrefixConstants.SECONDARY_CPD_VALUE, UserSession.getCurrent().getOrganisation().getOrgid(),
                    MainetConstants.MASTER.Y);
        }
        if (isDafaultOrgExist) {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        Long defaultOrgId = null;
        if (isDafaultOrgExist && secondaryDefaultFlag && primaryDefaultFlag) {
            defaultOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
        } else if (isDafaultOrgExist && primaryDefaultFlag && (secondaryDefaultFlag == false)) {
            defaultOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
        } else {
            defaultOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
        }

        list = new ArrayList<>();
        list.clear();
        list = tbAcSecondaryheadMasterService.findByAllGridSearchData(fundId, fieldId, pacHeadId, functionId, sacHeadId,
                ledgerTypeId, defaultOrgId);
        if (list != null) {
            for (final SecondaryheadMaster secondaryheadMaster : list) {

                secondaryheadMaster.setSacHeadCodeDesc(secondaryheadMaster.getSacHeadCode() + MainetConstants.SEPARATOR
                        + secondaryheadMaster.getSacHeadDesc());
                secondaryheadMaster.setPacHeadDesc(secondaryheadMaster.getPacHeadCode() + MainetConstants.SEPARATOR
                        + secondaryheadMaster.getPacHeadDesc());
                if ((secondaryheadMaster.getFundCode() != null) && !secondaryheadMaster.getFundCode().isEmpty()) {
                    secondaryheadMaster.setFundCode(secondaryheadMaster.getFundCode());
                }
                if ((secondaryheadMaster.getFieldCode() != null) && !secondaryheadMaster.getFieldCode().isEmpty()) {
                    secondaryheadMaster.setFieldCode(secondaryheadMaster.getFieldCode());
                }
                if ((secondaryheadMaster.getFunctionCode() != null)
                        && !secondaryheadMaster.getFunctionCode().isEmpty()) {
                    secondaryheadMaster.setFunctionCode(secondaryheadMaster.getFunctionCode());
                }
                final Long cpdIdStatsFlag = secondaryheadMaster.getSacStatusCpdId();
                Long lookUpId = null;
                for (final LookUp lookUp : activeDeActiveMap) {
                    lookUpId = lookUp.getLookUpId();
                    if (lookUpId.equals(cpdIdStatsFlag)) {
                        secondaryheadMaster.setCpdIdStatusFlag(lookUp.getLookUpDesc());
                    }
                }
                if (isDafaultOrgExist && secondaryDefaultFlag) {
                    if ((UserSession.getCurrent().getOrganisation().getOrgid()) == (ApplicationSession.getInstance()
                            .getSuperUserOrganization().getOrgid())) {
                        secondaryheadMaster.setDefaultOrgFlag(MainetConstants.MASTER.Y);
                    }
                } else {
                    secondaryheadMaster.setDefaultOrgFlag(MainetConstants.MASTER.Y);
                }
                if (secondaryheadMaster.getSacLeddgerTypeCpdId() != null) {
                    secondaryheadMaster.setSacLeddgerTypeCpdCode(CommonMasterUtility.findLookUpCode(PrefixConstants.FTY,
                            secondaryheadMaster.getOrgid(), secondaryheadMaster.getSacLeddgerTypeCpdId()));
                }
            }
        }
        return list;
    }

    /**
     * Shows a form page in order to create a new TbAcSecondaryheadMaster
     * 
     * @param model Spring MVC model
     * @return
     */

    @RequestMapping(params = "bankaccountList", method = {
            RequestMethod.POST }, headers = "Accept=*/*", produces = "application/json")
    public @ResponseBody List<TbBankaccount> getBankaccountData(final HttpServletRequest request, final Model model,
            @RequestParam("bmBankid") final Long bmBankid) {

        List<TbBankaccount> tbBankaccount = new ArrayList<TbBankaccount>(0);

        return tbBankaccount;

    }

    @RequestMapping(params = "add")
    public String formForCreate(final Model model) throws Exception {
        log("Action 'formForCreate'");

        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        boolean primaryDefaultFlag = false;
        boolean secondaryDefaultFlag = false;
        boolean functionDefaultFlag = false;
        boolean fundDefaultFlag = false;
        boolean fieldDefaultFlag = false;

        if (isDafaultOrgExist) {
            secondaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(
                    PrefixConstants.SECONDARY_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            secondaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(
                    PrefixConstants.SECONDARY_CPD_VALUE, UserSession.getCurrent().getOrganisation().getOrgid(),
                    MainetConstants.MASTER.Y);
        }
        if (isDafaultOrgExist) {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        if (isDafaultOrgExist) {
            functionDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(
                    PrefixConstants.FUNCTION_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            functionDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(
                    PrefixConstants.FUNCTION_CPD_VALUE, UserSession.getCurrent().getOrganisation().getOrgid(),
                    MainetConstants.MASTER.Y);
        }
        if (isDafaultOrgExist) {
            fundDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            fundDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        if (isDafaultOrgExist) {
            fieldDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FIELD_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            fieldDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FIELD_CPD_VALUE,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        // --- Populates the model with a new instance
        final SecondaryheadMaster tbAcSecondaryheadMaster = new SecondaryheadMaster();
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final Long orgId = organisation.getOrgid();
        final int langId = UserSession.getCurrent().getLanguageId();
        List<LookUp> list = null;
        if (isDafaultOrgExist) {
            list = CommonMasterUtility.getLookUps(PrefixConstants.SECONDARY_LOOKUPCODE,
                    ApplicationSession.getInstance().getSuperUserOrganization());
        } else {
            list = CommonMasterUtility.getLookUps(PrefixConstants.SECONDARY_LOOKUPCODE,
                    UserSession.getCurrent().getOrganisation());
        }

        final List<LookUp> accountTypeLevel = CommonMasterUtility.getListLookup(PrefixConstants.CMD_PREFIX,
                UserSession.getCurrent().getOrganisation());
        Collections.sort(list);
        for (final LookUp lookUp : accountTypeLevel) {
            if (lookUp != null) {
                if (PrefixConstants.AHP.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                        if (lookUp.getOtherField().equals(MainetConstants.MENU.Y)) {
                            if (isDafaultOrgExist && secondaryDefaultFlag && primaryDefaultFlag) {
                                model.addAttribute(listOfTbAcFunctionMasterItems,
                                        tbAcPrimaryheadMasterService.getSecondaryPrimaryHeadCodeStatusLastLevels(
                                                ApplicationSession.getInstance().getSuperUserOrganization(), langId));
                            } else if (isDafaultOrgExist && primaryDefaultFlag && (secondaryDefaultFlag == false)) {
                                model.addAttribute(listOfTbAcFunctionMasterItems,
                                        tbAcPrimaryheadMasterService.getSecondaryPrimaryHeadCodeStatusLastLevels(
                                                ApplicationSession.getInstance().getSuperUserOrganization(), langId));
                            } else {
                                model.addAttribute(listOfTbAcFunctionMasterItems, tbAcPrimaryheadMasterService
                                        .getSecondaryPrimaryHeadCodeStatusLastLevels(organisation, langId));
                            }
                            model.addAttribute(MainetConstants.AccountBudgetCode.PRIMARY_STATUS,
                                    MainetConstants.MASTER.Y);
                        }
                    }
                }

                if (PrefixConstants.FUNCTION_CPD_VALUE.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                        if (lookUp.getOtherField().equals(MainetConstants.MENU.Y)) {
                            if (isDafaultOrgExist && functionDefaultFlag) {
                                model.addAttribute(listOfTbAcPrimaryMasterItems,
                                        tbAcFunctionMasterService.getFunctionMasterStatusLastLevels(
                                                ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(),
                                                ApplicationSession.getInstance().getSuperUserOrganization(), langId));
                            } else if (isDafaultOrgExist && (functionDefaultFlag == false)) {
                                model.addAttribute(listOfTbAcPrimaryMasterItems, tbAcFunctionMasterService
                                        .getFunctionMasterStatusLastLevels(orgId, organisation, langId));
                            } else {
                                model.addAttribute(listOfTbAcPrimaryMasterItems, tbAcFunctionMasterService
                                        .getFunctionMasterStatusLastLevels(orgId, organisation, langId));
                            }
                            model.addAttribute(MainetConstants.FUNCTION_MASTER.FUNCTION_STATUS,
                                    MainetConstants.MASTER.Y);
                        }
                    }
                }

                if (PrefixConstants.FUND_CPD_VALUE.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                        if (lookUp.getOtherField().equals(MainetConstants.MENU.Y)) {
                            if (isDafaultOrgExist && fundDefaultFlag) {
                                model.addAttribute(
                                        MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FUND_MASTER_ITEMS,
                                        tbAcFundMasterService.getFundMasterStatusLastLevels(
                                                ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(),
                                                ApplicationSession.getInstance().getSuperUserOrganization(), langId));
                            } else {
                                model.addAttribute(
                                        MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FUND_MASTER_ITEMS,
                                        tbAcFundMasterService.getFundMasterStatusLastLevels(orgId, organisation,
                                                langId));
                            }
                            model.addAttribute(MainetConstants.FUND_MASTER.FUND_STATUS, MainetConstants.MASTER.Y);
                        }
                    }
                }

                if (PrefixConstants.FIELD_CPD_VALUE.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                        if (lookUp.getOtherField().equals(MainetConstants.MENU.Y)) {
                            if (isDafaultOrgExist && fieldDefaultFlag) {
                                model.addAttribute(
                                        MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
                                        tbAcFieldMasterService.getFieldMasterStatusLastLevels(
                                                ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(),
                                                ApplicationSession.getInstance().getSuperUserOrganization(), langId));
                            } else {
                                model.addAttribute(
                                        MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
                                        tbAcFieldMasterService.getFieldMasterStatusLastLevels(orgId, organisation,
                                                langId));
                            }
                            model.addAttribute(MainetConstants.FIELD_MASTER.FIELD_STATUS, MainetConstants.MASTER.Y);
                        }
                    }
                }

                if (PrefixConstants.SECONDARY_CPD_VALUE.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                        if (lookUp.getOtherField().equals(MainetConstants.MENU.Y)) {
                            model.addAttribute(MainetConstants.SecondaryheadMaster.SECONDARY_STATUS,
                                    MainetConstants.MASTER.Y);
                        }
                    }
                }
            }
        }
        model.addAttribute(VendorList, tbVendormasterService.findAllSechead(orgId));
        model.addAttribute(legerTypeList, list);
        if (list != null) {
            for (final LookUp ledgerTypeLookUp : list) {
                if ((ledgerTypeLookUp.getDefaultVal() != null) && !ledgerTypeLookUp.getDefaultVal().isEmpty()) {
                    if (ledgerTypeLookUp.getDefaultVal().equals(MainetConstants.MENU.Y)) {
                        tbAcSecondaryheadMaster.setSacLeddgerTypeCpdId(ledgerTypeLookUp.getLookUpId());
                    }
                }
            }
        }
        populateModel(model, tbAcSecondaryheadMaster, FormMode.CREATE);
        return JSP_FORM;
    }

    /**
     * Shows a form page in order to update an existing TbAcSecondaryheadMaster
     * 
     * @param model Spring MVC model
     * @param sacHeadId primary key element
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "editGr", method = RequestMethod.POST)
    public String formForUpdate(final Model model, @RequestParam("sacHeadId") final Long sacHeadId,
            @RequestParam("MODE") final String MODE) throws Exception {

        log("Action 'formForUpdate'");

        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        boolean primaryDefaultFlag = false;
        boolean functionDefaultFlag = false;
        boolean fundDefaultFlag = false;
        boolean fieldDefaultFlag = false;

        if (isDafaultOrgExist) {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        if (isDafaultOrgExist) {
            functionDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(
                    PrefixConstants.FUNCTION_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            functionDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(
                    PrefixConstants.FUNCTION_CPD_VALUE, UserSession.getCurrent().getOrganisation().getOrgid(),
                    MainetConstants.MASTER.Y);
        }
        if (isDafaultOrgExist) {
            fundDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            fundDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        if (isDafaultOrgExist) {
            fieldDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FIELD_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            fieldDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FIELD_CPD_VALUE,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        // --- Search the entity by its primary key and stores it in the model
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long defaultOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
        final SecondaryheadMaster tbAcSecondaryheadMaster = tbAcSecondaryheadMasterService.findBySacHeadId(sacHeadId,
                orgId);
        if (tbAcSecondaryheadMaster.getSacLeddgerTypeCpdId() != null) {
            if (isDafaultOrgExist) {
                tbAcSecondaryheadMaster.setSacLeddgerTypeCpdCode(
                        CommonMasterUtility.findLookUpCode(PrefixConstants.SECONDARY_LOOKUPCODE, defaultOrgId,
                                tbAcSecondaryheadMaster.getSacLeddgerTypeCpdId()));
            } else {
                tbAcSecondaryheadMaster.setSacLeddgerTypeCpdCode(CommonMasterUtility.findLookUpCode(
                        PrefixConstants.SECONDARY_LOOKUPCODE, orgId, tbAcSecondaryheadMaster.getSacLeddgerTypeCpdId()));
            }
        }
        if ((tbAcSecondaryheadMaster.getSacHeadCode() != null) && ((tbAcSecondaryheadMaster.getSacHeadDesc() != null)
                && !tbAcSecondaryheadMaster.getSacHeadDesc().isEmpty())) {
            tbAcSecondaryheadMaster.setSacHeadCodeDesc(tbAcSecondaryheadMaster.getSacHeadDesc());
        }
        if (tbAcSecondaryheadMaster.getBaAccountid() != null) {
            final TbBankaccount tbBankaccount = tbBankaccountService.findById(tbAcSecondaryheadMaster.getBaAccountid(),
                    orgId);
            if (tbBankaccount != null) {
                tbAcSecondaryheadMaster.getBankName();
                tbBankaccount.getTbBankmaster();
                tbAcSecondaryheadMaster.setBankName(tbBankaccount.getTbBankmaster());
            }
        }
        if (tbAcSecondaryheadMaster.getFunctionId() != null) {
            tbAcSecondaryheadMaster.setFunctionId(tbAcSecondaryheadMaster.getFunctionId());
        }
        if (tbAcSecondaryheadMaster.getFundId() != null) {
            tbAcSecondaryheadMaster.setFundId(tbAcSecondaryheadMaster.getFundId());
        }
        if (tbAcSecondaryheadMaster.getFieldId() != null) {
            tbAcSecondaryheadMaster.setFieldId(tbAcSecondaryheadMaster.getFieldId());
        }
        List<LookUp> list = null;
        if (isDafaultOrgExist) {
            list = CommonMasterUtility.getLookUps(PrefixConstants.SECONDARY_LOOKUPCODE,
                    ApplicationSession.getInstance().getSuperUserOrganization());
        } else {
            list = CommonMasterUtility.getLookUps(PrefixConstants.SECONDARY_LOOKUPCODE,
                    UserSession.getCurrent().getOrganisation());
        }
        Collections.sort(list);
        final List<LookUp> accountTypeLevel = CommonMasterUtility.getListLookup(PrefixConstants.CMD_PREFIX,
                UserSession.getCurrent().getOrganisation());
        for (final LookUp lookUp : accountTypeLevel) {
            if (lookUp != null) {
                if (PrefixConstants.AHP.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                        if (lookUp.getOtherField().equals(MainetConstants.MENU.Y)) {
                            model.addAttribute(MainetConstants.AccountBudgetCode.PRIMARY_STATUS,
                                    MainetConstants.MASTER.Y);
                        }
                    }
                }

                if (PrefixConstants.FUNCTION_CPD_VALUE.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                        if (lookUp.getOtherField().equals(MainetConstants.MENU.Y)) {
                            model.addAttribute(MainetConstants.FUNCTION_MASTER.FUNCTION_STATUS,
                                    MainetConstants.MASTER.Y);
                        }
                    }
                }

                if (PrefixConstants.FUND_CPD_VALUE.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                        if (lookUp.getOtherField().equals(MainetConstants.MENU.Y)) {
                            model.addAttribute(MainetConstants.FUND_MASTER.FUND_STATUS, MainetConstants.MASTER.Y);
                        }
                    }
                }

                if (PrefixConstants.FIELD_CPD_VALUE.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                        if (lookUp.getOtherField().equals(MainetConstants.MENU.Y)) {
                            model.addAttribute(MainetConstants.FIELD_MASTER.FIELD_STATUS, MainetConstants.MASTER.Y);
                        }
                    }
                }

                if (PrefixConstants.SECONDARY_CPD_VALUE.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                        if (lookUp.getOtherField().equals(MainetConstants.MENU.Y)) {
                            model.addAttribute(MainetConstants.SecondaryheadMaster.SECONDARY_STATUS,
                                    MainetConstants.MASTER.Y);
                        }
                    }
                }
            }
        }

        if (isDafaultOrgExist && primaryDefaultFlag) {
            model.addAttribute(listOfTbAcFunctionMasterItems, tbAcPrimaryheadMasterService.getPrimaryHeadCodeLastLevels(
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid()));
        } else {
            model.addAttribute(listOfTbAcFunctionMasterItems, tbAcPrimaryheadMasterService
                    .getPrimaryHeadCodeLastLevels(UserSession.getCurrent().getOrganisation().getOrgid()));
        }
        if (isDafaultOrgExist && functionDefaultFlag) {
            model.addAttribute(listOfTbAcPrimaryMasterItems, tbAcFunctionMasterService.getFunctionMasterLastLevels(
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid()));
        } else {
            model.addAttribute(listOfTbAcPrimaryMasterItems, tbAcFunctionMasterService
                    .getFunctionMasterLastLevels(UserSession.getCurrent().getOrganisation().getOrgid()));
        }
        if (isDafaultOrgExist && fundDefaultFlag) {
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FUND_MASTER_ITEMS,
                    tbAcFundMasterService.getFundMasterLastLevels(
                            ApplicationSession.getInstance().getSuperUserOrganization().getOrgid()));
        } else {
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FUND_MASTER_ITEMS,
                    tbAcFundMasterService
                            .getFundMasterLastLevels(UserSession.getCurrent().getOrganisation().getOrgid()));
        }
        if (isDafaultOrgExist && fieldDefaultFlag) {
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
                    tbAcFieldMasterService.getFieldMasterLastLevels(
                            ApplicationSession.getInstance().getSuperUserOrganization().getOrgid()));
        } else {
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
                    tbAcFieldMasterService
                            .getFieldMasterLastLevels(UserSession.getCurrent().getOrganisation().getOrgid()));
        }
        model.addAttribute(listOfTbAcSecondaryMasterItems,
                tbAcSecondaryheadMasterService.getSecondaryHeadCodeOnlyLastLevels(orgId));
        model.addAttribute(VendorList, tbVendormasterService.findAll(orgId));
        model.addAttribute(legerTypeList, list);
        model.addAttribute(SacHeadId, sacHeadId);
        tbAcSecondaryheadMaster.setSacHeadId(Long.valueOf(sacHeadId + MainetConstants.CommonConstant.BLANK));
        if (MODE.equals(MainetConstants.VIEW)) {
            populateModel(model, tbAcSecondaryheadMaster, FormMode.VIEW);
        } else {
            populateModel(model, tbAcSecondaryheadMaster, FormMode.UPDATE);
        }

        return JSP_FORM;
    }

    @RequestMapping(params = "viewGr", method = RequestMethod.POST)
    public String formForView(final Model model, @RequestParam("sacHeadId") final Long sacHeadId,
            @RequestParam("MODE") final String MODE) throws Exception {

        log("Action 'formForView'");

        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);

        boolean primaryDefaultFlag = false;
        boolean functionDefaultFlag = false;
        boolean fundDefaultFlag = false;
        boolean fieldDefaultFlag = false;

        if (isDafaultOrgExist) {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        if (isDafaultOrgExist) {
            functionDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(
                    PrefixConstants.FUNCTION_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            functionDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(
                    PrefixConstants.FUNCTION_CPD_VALUE, UserSession.getCurrent().getOrganisation().getOrgid(),
                    MainetConstants.MASTER.Y);
        }
        if (isDafaultOrgExist) {
            fundDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            fundDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        if (isDafaultOrgExist) {
            fieldDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FIELD_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            fieldDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FIELD_CPD_VALUE,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }

        // --- Search the entity by its primary key and stores it in the model
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long defaultOrgid = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
        final SecondaryheadMaster tbAcSecondaryheadMaster = tbAcSecondaryheadMasterService.findBySacHeadId(sacHeadId,
                orgId);
        if (tbAcSecondaryheadMaster.getSacLeddgerTypeCpdId() != null) {
            if (isDafaultOrgExist) {
                tbAcSecondaryheadMaster.setSacLeddgerTypeCpdCode(
                        CommonMasterUtility.findLookUpCode(PrefixConstants.SECONDARY_LOOKUPCODE, defaultOrgid,
                                tbAcSecondaryheadMaster.getSacLeddgerTypeCpdId()));
                tbAcSecondaryheadMaster.setSacLeddgerTypeCpdDesc(
                        CommonMasterUtility.findLookUpDesc(PrefixConstants.SECONDARY_LOOKUPCODE, defaultOrgid,
                                tbAcSecondaryheadMaster.getSacLeddgerTypeCpdId()));
            } else {
                tbAcSecondaryheadMaster.setSacLeddgerTypeCpdCode(CommonMasterUtility.findLookUpCode(
                        PrefixConstants.SECONDARY_LOOKUPCODE, orgId, tbAcSecondaryheadMaster.getSacLeddgerTypeCpdId()));
                tbAcSecondaryheadMaster.setSacLeddgerTypeCpdDesc(CommonMasterUtility.findLookUpDesc(
                        PrefixConstants.SECONDARY_LOOKUPCODE, orgId, tbAcSecondaryheadMaster.getSacLeddgerTypeCpdId()));
            }
            tbAcSecondaryheadMaster.setSacStatusCpdDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.ACN, orgId,
                    tbAcSecondaryheadMaster.getSacStatusCpdId()));
            if ((tbAcSecondaryheadMaster.getSacHeadCode() != null)
                    && ((tbAcSecondaryheadMaster.getSacHeadDesc() != null)
                            && !tbAcSecondaryheadMaster.getSacHeadDesc().isEmpty())) {
                tbAcSecondaryheadMaster.setSacHeadCodeDesc(tbAcSecondaryheadMaster.getSacHeadCode()
                        + MainetConstants.SEPARATOR + tbAcSecondaryheadMaster.getSacHeadDesc());
            }
        }
        if (tbAcSecondaryheadMaster.getBaAccountid() != null) {
            final TbBankaccount tbBankaccount = tbBankaccountService.findById(tbAcSecondaryheadMaster.getBaAccountid(),
                    orgId);
            if (tbBankaccount != null) {
                tbAcSecondaryheadMaster.getBankName();
                tbBankaccount.getTbBankmaster();
                tbAcSecondaryheadMaster.setBankName(tbBankaccount.getTbBankmaster());
            }
        }
        if (tbAcSecondaryheadMaster.getFunctionId() != null) {
            tbAcSecondaryheadMaster.setFunctionId(tbAcSecondaryheadMaster.getFunctionId());
        }
        if (tbAcSecondaryheadMaster.getFundId() != null) {
            tbAcSecondaryheadMaster.setFundId(tbAcSecondaryheadMaster.getFundId());
        }
        if (tbAcSecondaryheadMaster.getFieldId() != null) {
            tbAcSecondaryheadMaster.setFieldId(tbAcSecondaryheadMaster.getFieldId());
        }
        List<LookUp> list = null;
        if (isDafaultOrgExist) {
            list = CommonMasterUtility.getLookUps(PrefixConstants.SECONDARY_LOOKUPCODE,
                    ApplicationSession.getInstance().getSuperUserOrganization());
        } else {
            list = CommonMasterUtility.getLookUps(PrefixConstants.SECONDARY_LOOKUPCODE,
                    UserSession.getCurrent().getOrganisation());
        }
        Collections.sort(list);
        final List<LookUp> accountTypeLevel = CommonMasterUtility.getListLookup(PrefixConstants.CMD_PREFIX,
                UserSession.getCurrent().getOrganisation());
        for (final LookUp lookUp : accountTypeLevel) {
            if (lookUp != null) {
                if (PrefixConstants.AHP.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                        if (lookUp.getOtherField().equals(MainetConstants.MENU.Y)) {
                            model.addAttribute(MainetConstants.AccountBudgetCode.PRIMARY_STATUS,
                                    MainetConstants.MASTER.Y);
                        }
                    }
                }

                if (PrefixConstants.FUNCTION_CPD_VALUE.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                        if (lookUp.getOtherField().equals(MainetConstants.MENU.Y)) {
                            model.addAttribute(MainetConstants.FUNCTION_MASTER.FUNCTION_STATUS,
                                    MainetConstants.MASTER.Y);
                        }
                    }
                }

                if (PrefixConstants.FUND_CPD_VALUE.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                        if (lookUp.getOtherField().equals(MainetConstants.MENU.Y)) {
                            model.addAttribute(MainetConstants.FUND_MASTER.FUND_STATUS, MainetConstants.MASTER.Y);
                        }
                    }
                }

                if (PrefixConstants.FIELD_CPD_VALUE.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                        if (lookUp.getOtherField().equals(MainetConstants.MENU.Y)) {
                            model.addAttribute(MainetConstants.FIELD_MASTER.FIELD_STATUS, MainetConstants.MASTER.Y);
                        }
                    }
                }

                if (PrefixConstants.SECONDARY_CPD_VALUE.equalsIgnoreCase(lookUp.getLookUpCode())) {
                    if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                        if (lookUp.getOtherField().equals(MainetConstants.MENU.Y)) {
                            model.addAttribute(MainetConstants.SecondaryheadMaster.SECONDARY_STATUS,
                                    MainetConstants.MASTER.Y);
                        }
                    }
                }
            }
        }

        if (isDafaultOrgExist && primaryDefaultFlag) {
            model.addAttribute(listOfTbAcFunctionMasterItems, tbAcPrimaryheadMasterService.getPrimaryHeadCodeLastLevels(
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid()));
        } else {
            model.addAttribute(listOfTbAcFunctionMasterItems, tbAcPrimaryheadMasterService
                    .getPrimaryHeadCodeLastLevels(UserSession.getCurrent().getOrganisation().getOrgid()));
        }
        if (isDafaultOrgExist && functionDefaultFlag) {
            model.addAttribute(listOfTbAcPrimaryMasterItems, tbAcFunctionMasterService.getFunctionMasterLastLevels(
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid()));
        } else {
            model.addAttribute(listOfTbAcPrimaryMasterItems, tbAcFunctionMasterService
                    .getFunctionMasterLastLevels(UserSession.getCurrent().getOrganisation().getOrgid()));
        }
        if (isDafaultOrgExist && fundDefaultFlag) {
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FUND_MASTER_ITEMS,
                    tbAcFundMasterService.getFundMasterLastLevels(
                            ApplicationSession.getInstance().getSuperUserOrganization().getOrgid()));
        } else {
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FUND_MASTER_ITEMS,
                    tbAcFundMasterService
                            .getFundMasterLastLevels(UserSession.getCurrent().getOrganisation().getOrgid()));
        }
        if (isDafaultOrgExist && fieldDefaultFlag) {
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
                    tbAcFieldMasterService.getFieldMasterLastLevels(
                            ApplicationSession.getInstance().getSuperUserOrganization().getOrgid()));
        } else {
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
                    tbAcFieldMasterService
                            .getFieldMasterLastLevels(UserSession.getCurrent().getOrganisation().getOrgid()));
        }
        model.addAttribute(VendorList, tbVendormasterService.findAll(orgId));
        model.addAttribute(legerTypeList, list);
        model.addAttribute(SacHeadId, sacHeadId);
        tbAcSecondaryheadMaster.setSacHeadId(Long.valueOf(sacHeadId + MainetConstants.CommonConstant.BLANK));
        if (MODE.equals(MainetConstants.VIEW)) {
            populateModel(model, tbAcSecondaryheadMaster, FormMode.VIEW);
        } else {
            populateModel(model, tbAcSecondaryheadMaster, FormMode.UPDATE);
        }

        return JSP_VIEW;
    }

    /**
     * 'CREATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * 
     * @param tbAcSecondaryheadMaster entity to be created
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "create") // GET or POST Updaet and create
    public String create(@Valid SecondaryheadMaster secondaryheadMaster, final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {
        log("Action 'create'");

        final ApplicationSession session = ApplicationSession.getInstance();
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        Organisation defaultOrg = null;
        if (isDafaultOrgExist) {
            defaultOrg = ApplicationSession.getInstance().getSuperUserOrganization();
        } else {
            defaultOrg = UserSession.getCurrent().getOrganisation();
        }
        final int langId = UserSession.getCurrent().getLanguageId();
        secondaryheadMaster.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
        secondaryheadMaster.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        secondaryheadMaster.setLmoddate(new Date());
        secondaryheadMaster.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
        secondaryheadMaster = tbAcSecondaryheadMasterService.saveSecondaryHeadData(secondaryheadMaster, defaultOrg,
                langId);
        populateModel(model, secondaryheadMaster, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, secondaryheadMaster);
        messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
        if (secondaryheadMaster.getSacHeadId() == null) {
            model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                    session.getMessage("accounts.fieldmaster.success"));
        }
        if (secondaryheadMaster.getSacHeadId() != null) {
            model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                    session.getMessage("accounts.fieldmaster.update"));
        }
        return JSP_FORM;
    }

    /**
     * 'UPDATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * 
     * @param tbAcSecondaryheadMaster entity to be updated
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/update")
    public String update(@Valid final SecondaryheadMaster tbAcSecondaryheadMaster, final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes,
            final HttpServletRequest httpServletRequest) {
        log("Action 'update'");
        if (!bindingResult.hasErrors()) {
            // --- Perform database operations
            final SecondaryheadMaster tbAcSecondaryheadMasterSaved = tbAcSecondaryheadMasterService
                    .update(tbAcSecondaryheadMaster);
            model.addAttribute(MAIN_ENTITY_NAME, tbAcSecondaryheadMasterSaved);
            log("Action 'update' : update done - redirect");
            return redirectToForm(httpServletRequest, tbAcSecondaryheadMaster.getSacHeadId());
        } else {
            log("Action 'update' : binding errors");
            populateModel(model, tbAcSecondaryheadMaster, FormMode.UPDATE);
            return JSP_FORM;
        }
    }

    /**
     * 'DELETE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * 
     * @param redirectAttributes
     * @param sacHeadId primary key element
     * @return
     */
    @RequestMapping(value = "/delete/{sacHeadId}") // GET or POST
    public String delete(final RedirectAttributes redirectAttributes, @PathVariable("sacHeadId") final Long sacHeadId) {
        log("Action 'delete'");
        tbAcSecondaryheadMasterService.delete(sacHeadId);
        return redirectToList();
    }

    @RequestMapping(params = "exportExcelTemplateData")
    public void exportAccountSecondaryHeadMasterExcelData(final HttpServletResponse response,
            final HttpServletRequest request) {

        try {
            WriteExcelData<AccountSecondaryHeadMasterExportDto> data = new WriteExcelData<>(
                    MainetConstants.ACCOUNTSECONDARYHEADMASTEREXPORTDTO
                            + MainetConstants.XLSX_EXT,
                    request, response);

            data.getExpotedExcelSheet(new ArrayList<AccountSecondaryHeadMasterExportDto>(),
                    AccountSecondaryHeadMasterExportDto.class);
        } catch (Exception ex) {
            throw new FrameworkException(ex.getMessage());
        }
    }
    
    @RequestMapping(params = "exportAccountSacHeadBankExcelData")
    public void exportAccountSacHeadBankExcelData(final HttpServletResponse response,
            final HttpServletRequest request) {

        try {
            WriteExcelData<TbSacheadAccMapDTO> data = new WriteExcelData<>(
                    "SecondaryHeadMapAcc"
                            + MainetConstants.XLSX_EXT,
                    request, response);

            data.getExpotedExcelSheet(new ArrayList<TbSacheadAccMapDTO>(),
            		TbSacheadAccMapDTO.class);
        } catch (Exception ex) {
            throw new FrameworkException(ex.getMessage());
        }
    }
    
    @RequestMapping(params = "importExportExcelTemplateData")
    public String exportImportExcelTemplate(final Model model) throws Exception {
        log("Action 'exportTemplate'");
        SecondaryheadMaster dto = new SecondaryheadMaster();
        populateModel(model, dto, FormMode.CREATE);
        fileUpload.sessionCleanUpForFileUpload();
        return JSP_EXCELUPLOAD;
    }
    
    @RequestMapping(params = "exportImportExcelMapBank")
    public String exportImportExcelMapBank(final Model model) throws Exception {
        log("Action 'exportTemplate'");
        SecondaryheadMaster dto = new SecondaryheadMaster();
        populateModel(model, dto, FormMode.BULKEDITSAVE);
        fileUpload.sessionCleanUpForFileUpload();
        return "SecondaryheadMaster/exceluploadBankMap";
    }
    
    @RequestMapping(params = "loadExcelData", method = RequestMethod.POST)
    public String loadValidateAndLoadExcelData(@Valid SecondaryheadMaster secondaryheadMaster, final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {
        log("Action 'loadExcelData'");

        ApplicationSession session = null;
        session = ApplicationSession.getInstance();
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        Organisation defaultOrg = null;
        if (isDafaultOrgExist) {
            defaultOrg = session.getSuperUserOrganization();
        } else {
            defaultOrg = UserSession.getCurrent().getOrganisation();
        }
        final int langId = UserSession.getCurrent().getLanguageId();
        final Long userId = UserSession.getCurrent().getEmployee().getEmpId();

        final String filePath = getUploadedFinePath();
        ReadExcelData<AccountSecondaryHeadMasterExportDto> data = new ReadExcelData<>(filePath,
                AccountSecondaryHeadMasterExportDto.class);
        data.parseExcelList();
        List<String> errlist = data.getErrorList();
        if (!errlist.isEmpty()) {
            bindingResult.addError(new org.springframework.validation.FieldError(
                    MainetConstants.SecondaryheadMaster.SECONDARY_HEAD, MainetConstants.BLANK, null, false,
                    new String[] { MainetConstants.ERRORS }, null, session.getMessage("accounts.Secondaryhead.empty.excel")));
        } else {
            final List<AccountSecondaryHeadMasterExportDto> secondaryHeadMasterExportDtos = data.getParseData();
            boolean primaryDefaultFlag = false;
            if (isDafaultOrgExist) {
                primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                        ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
            } else {
                primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                        UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
            }
            boolean functionDefaultFlag = false;
            if (isDafaultOrgExist) {
                functionDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(
                        PrefixConstants.FUNCTION_CPD_VALUE,
                        ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
            } else {
                functionDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(
                        PrefixConstants.FUNCTION_CPD_VALUE, UserSession.getCurrent().getOrganisation().getOrgid(),
                        MainetConstants.MASTER.Y);
            }

            Map<Long, String> primaryHead = null;
            if (isDafaultOrgExist && primaryDefaultFlag) {
                primaryHead = tbAcPrimaryheadMasterService.getPrimaryCompositeCode(session.getSuperUserOrganization(),
                        langId);
            } else if (isDafaultOrgExist && (primaryDefaultFlag == false)) {
                primaryHead = tbAcPrimaryheadMasterService.getPrimaryCompositeCode(UserSession.getCurrent().getOrganisation(),
                        langId);
            } else {
                primaryHead = tbAcPrimaryheadMasterService.getPrimaryCompositeCode(UserSession.getCurrent().getOrganisation(),
                        langId);
            }

            Map<Long, String> functionHead = null;
            if (isDafaultOrgExist && functionDefaultFlag) {
                functionHead = tbAcFunctionMasterService.getFunctionCompositeCode(session.getSuperUserOrganization(), langId);
            } else if (isDafaultOrgExist && (functionDefaultFlag == false)) {
                functionHead = tbAcFunctionMasterService.getFunctionCompositeCode(UserSession.getCurrent().getOrganisation(),
                        langId);
            } else {
                functionHead = tbAcFunctionMasterService.getFunctionCompositeCode(UserSession.getCurrent().getOrganisation(),
                        langId);
            }

            List<LookUp> list = null;
            list = CommonMasterUtility.getLookUps(PrefixConstants.SECONDARY_LOOKUPCODE,
                    UserSession.getCurrent().getOrganisation());

            final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
                    PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(),
                    UserSession.getCurrent().getOrganisation());
            Long activeStatusId = lookUpFieldStatus.getLookUpId();
            List<AccountHeadSecondaryAccountCodeMasterEntity> accountHeadList = tbAcSecondaryheadMasterService
                    .getActiveSacHeadCodeDeatails(UserSession.getCurrent().getOrganisation().getOrgid(), activeStatusId);
            SecondaryHeadMasterExcelValidator validator = new SecondaryHeadMasterExcelValidator();
            final List<AccountSecondaryHeadMasterExportDto> secondaryHeadMasterExportList = validator
                    .excelValidation(secondaryHeadMasterExportDtos, bindingResult, primaryHead, functionHead, list);
            if (validator.count == 0) {
                validator.primaryFuncLedgTypeDbCombinationExistsValid(secondaryHeadMasterExportList, bindingResult,
                        accountHeadList);
            }
            if (!bindingResult.hasErrors()) {
                for (AccountSecondaryHeadMasterExportDto accountSecondaryHeadMasterExportDto : secondaryHeadMasterExportList) {

                    accountSecondaryHeadMasterExportDto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                    accountSecondaryHeadMasterExportDto.setUserId(userId);
                    accountSecondaryHeadMasterExportDto.setLangId(Long.valueOf(langId));
                    accountSecondaryHeadMasterExportDto.setLmoddate(new Date());
                    accountSecondaryHeadMasterExportDto.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
                    accountSecondaryHeadMasterExportDto.setSecondaryStatus(MainetConstants.Y_FLAG);
                    tbAcSecondaryheadMasterService.saveSecondaryHeadExportData(accountSecondaryHeadMasterExportDto, defaultOrg,
                            langId);
                }
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        session.getMessage("accounts.success.excel"));
            }
        }
        populateModel(model, secondaryheadMaster, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, secondaryheadMaster);
        messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
        fileUpload.sessionCleanUpForFileUpload();
        return JSP_EXCELUPLOAD;
    }
    
    @RequestMapping(params = "loadMapExcelData", method = RequestMethod.POST)
    public String loadValidateAndLoadMapExcelData(@Valid TbSacheadAccMapDTO tbSacheadAccMapDTO, final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {
        log("Action 'loadExcelData'");
        ApplicationSession session = null;
        session = ApplicationSession.getInstance();
        final Long userId = UserSession.getCurrent().getEmployee().getEmpId();

        final String filePath = getUploadedFinePath();
        ReadExcelData<TbSacheadAccMapDTO> data = new ReadExcelData<>(filePath,
        		TbSacheadAccMapDTO.class);
        data.parseExcelList();
        List<String> errlist = data.getErrorList();
        //List<BankAccountMasterDto> bankList = bankaccountService.findAll(UserSession.getCurrent().getOrganisation().getOrgid());
        List<AccountHeadSecondaryAccountCodeMasterEntity> secondaryAccountHeadList = tbAcSecondaryheadMasterService.getSacHeadCode(UserSession.getCurrent().getOrganisation().getOrgid());
        // Filter the list where baAccountId is null
        List<AccountHeadSecondaryAccountCodeMasterEntity> withNullBaAccountId = secondaryAccountHeadList.stream()
            .filter(entity -> entity.getTbBankaccount() == null)
            .collect(Collectors.toList());

        // Filter the list where baAccountId is not null
        List<AccountHeadSecondaryAccountCodeMasterEntity> withNotNullBaAccountId = secondaryAccountHeadList.stream()
            .filter(entity -> entity.getTbBankaccount() != null)
            .collect(Collectors.toList());
        if (!errlist.isEmpty()) {
            bindingResult.addError(new org.springframework.validation.FieldError(
                    MainetConstants.SecondaryheadMaster.SECONDARY_HEAD, MainetConstants.BLANK, null, false,
                    new String[] { MainetConstants.ERRORS }, null, session.getMessage("accounts.Secondaryhead.empty.excel")));
        } else {
        	 final List<TbSacheadAccMapDTO> sacheadAccMapDTO = data.getParseData();
        	 List<TbSacheadAccMapDTO> recordsToSave = new ArrayList<>();
        	 for (TbSacheadAccMapDTO tbSacheadAccMapDto : sacheadAccMapDTO) {
        		 Long sacHeadId = getSacHeadIdByName(withNullBaAccountId, tbSacheadAccMapDto.getSacHeadName());
        		 Long accountId = getAccountIdByName(withNotNullBaAccountId, tbSacheadAccMapDto.getBaAccountName());

                 if (sacHeadId != 0L && accountId != 0L) {
                     boolean combinationExist = tbAcSecondaryheadMasterService.doesCombinationExist(sacHeadId, accountId);

                     if (!combinationExist) {

                		 tbSacheadAccMapDto.setSacHeadId(sacHeadId);
                		 tbSacheadAccMapDto.setBaAccountId(accountId);
                    	 tbSacheadAccMapDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                		 tbSacheadAccMapDto.setCreatedBy(userId);
                		 tbSacheadAccMapDto.setUpdatedBy(userId);
                		 tbSacheadAccMapDto.setCreatedDate(new Date());
                		 tbSacheadAccMapDto.setUpdatedDate(new Date());
                		 tbSacheadAccMapDto.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
                		 tbSacheadAccMapDto.setLgIpMacUpd(Utility.getClientIpAddress(httpServletRequest));
                         recordsToSave.add(tbSacheadAccMapDto);
                     }
                 }
             }

             // Save all records in a batch
             if (!recordsToSave.isEmpty()) {
                 tbAcSecondaryheadMasterService.saveSecondaryHeadBankMapDataBatch(recordsToSave);
                 model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                         session.getMessage("accounts.success.excel"));
             }else {
            	 model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                         session.getMessage("accounts.unsuccess.excel"));
             }
        	 
        }
        SecondaryheadMaster dto = new SecondaryheadMaster();
        populateModel(model, dto, FormMode.BULKEDITSAVE);
        messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
        fileUpload.sessionCleanUpForFileUpload();
        return "SecondaryheadMaster/exceluploadBankMap";
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

    @RequestMapping(params = "checkDupFunPriDescExist", method = RequestMethod.POST)
    public @ResponseBody boolean findBudgetOpenBalDuplicateCombination(final SecondaryheadMaster bean,
            final HttpServletRequest request, final Model model, final BindingResult bindingResult) {
        // boolean isValidationError = false;
        bean.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
        final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
                PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(),
                UserSession.getCurrent().getOrganisation());
        Long activeStatusId = lookUpFieldStatus.getLookUpId();
        boolean checkDupFunPriDescExist = tbAcSecondaryheadMasterService.checkDupFunPriDescExist(bean, activeStatusId);
        return checkDupFunPriDescExist;
    }
    
	@RequestMapping( params = "printreport" ,method = RequestMethod.POST)
	public String findReportForSecondaryHead(final ModelMap model) {
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		accountfianService.getSecondaryHeadCodeDetail(model, orgId);
		return JSP_SEC_REPORT_VIEW;
	}
	
	
	public Long getSacHeadIdByName(List<AccountHeadSecondaryAccountCodeMasterEntity> secondaryHeadList, String bankName) {
		for (AccountHeadSecondaryAccountCodeMasterEntity secondaryHead : secondaryHeadList) {
			String normalizedBankName  = bankName.replaceAll("[^a-zA-Z0-9\\s]", " ");
			String normalizedSecondaryHead  = secondaryHead.getAcHeadCode().replaceAll("[^a-zA-Z0-9\\s]", " ");
			if (normalizedSecondaryHead.trim().contains(normalizedBankName.trim()))  {
				return secondaryHead.getSacHeadId();
			}
		}
		return 0L; // Bank name not found in the list
	}
	
	public Long getAccountIdByName(List<AccountHeadSecondaryAccountCodeMasterEntity> secondaryHeadList, String bankName) {
		for (AccountHeadSecondaryAccountCodeMasterEntity secondaryHead : secondaryHeadList) {
			String normalizedBankName  = bankName.replaceAll("[^a-zA-Z0-9\\s]", " ");
			String normalizedSecondaryHead  = secondaryHead.getAcHeadCode().replaceAll("[^a-zA-Z0-9\\s]", " ");
			if (normalizedSecondaryHead.trim().contains(normalizedBankName.trim()))  {
				return secondaryHead.getTbBankaccount().getBaAccountId();
			}
		}
		return 0L; // Bank name not found in the list
	}
	 
}