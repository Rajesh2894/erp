package com.abm.mainet.account.ui.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import com.abm.mainet.account.dto.TbAcPayToBank;
import com.abm.mainet.account.service.BankAccountService;
import com.abm.mainet.account.service.TbAcPayToBankService;
import com.abm.mainet.account.ui.validator.PayToBankValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Message;
import com.abm.mainet.common.utility.MessageType;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @RequestMapping("/AccountPayToBankTDS.html") Spring MVC controller for 'TbAcPayToBank' management.
 */
@Controller
@RequestMapping("/AccountPayToBankTDS.html")
public class TbAcPayToBankController extends AbstractController {
    private static final String SAC_HEAD_MAP = "sacHeadMap";
    private static final String TDS_TYPE_LOOK_UP = "tdsTypeLookUp";
    // --- Variables names ( to be used in JSP with Expression Language )
    // --- JSP pages names ( View name in the MVC model )
    private static final String JSP_FORM = "tbAcPayToBank/form";
    private static final String JSP_LIST = "tbAcPayToBank/list";

    // --- SAVE ACTION ( in the HTML form )
    private static final String SAVE_ACTION_CREATE = "AccountPayToBankTDS.html?create";
    private static final String SAVE_ACTION_UPDATE = "AccountPayToBankTDS.html?update";
    private static final String View = "View";
    // --- Main entity service
    @Resource
    private TbAcPayToBankService tbAcPayToBankService;

    private static Logger logger = Logger.getLogger(TbAcPayToBankController.class);

    // --- Other service(s)

    @Autowired
    private TbAcPayToBankService acPayToBankService;
    @Resource
    private TbOrganisationService tbOrganisationService;
    @Resource
    private TbAcVendormasterService tbAcVendormasterService;
    @Resource
    private BankAccountService bankAccountService;
    @Resource
    private SecondaryheadMasterService secondaryheadmasterservice;

    List<TbAcPayToBank> list = null;

    public TbAcPayToBankController() {
        super(TbAcPayToBankController.class, MainetConstants.BANK_MASTER_PAY_TDS.MAIN_ENTITY_NAME);
        log("TbAcPayToBankController created.");
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(MainetConstants.CommonConstants.D_FORMATE);
        dateFormat.setLenient(false);

        // true passed to CustomDateEditor constructor means convert empty
        // String to null

        binder.registerCustomEditor(Date.class, MainetConstants.TbAcPayToBank.TB_ENTRY_DATE,
                new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(Date.class, MainetConstants.TbAcChequebookleafMas.L_MOD_DATE,
                new CustomDateEditor(dateFormat, true));
    }

    /**
     * Populates the Spring MVC model with the given entity and eventually other useful data
     *
     * @param model
     * @param tbAcPayToBank
     */
    private void populateModel(final Model model, final TbAcPayToBank tbAcPayToBank, final FormMode formMode) {
        // --- Main entity
        model.addAttribute(MainetConstants.BANK_MASTER_PAY_TDS.MAIN_ENTITY_NAME, tbAcPayToBank);

        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        Organisation defaultOrg = null;
        if (isDafaultOrgExist) {
            defaultOrg = ApplicationSession.getInstance().getSuperUserOrganization();
        } else {
            defaultOrg = UserSession.getCurrent().getOrganisation();
        }
        final List<LookUp> tdsType = CommonMasterUtility.getLookUps(PrefixConstants.TDS, defaultOrg);
        model.addAttribute(TDS_TYPE_LOOK_UP, tdsType);
        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                AccountConstants.AC.getValue(), PrefixConstants.VSS, UserSession.getCurrent().getLanguageId(),
                defaultOrg);
        final Long vendorStatus = lookUpVendorStatus.getLookUpId();
        final List<TbAcVendormaster> list = tbAcVendormasterService.getActiveVendors(orgid, vendorStatus);
        model.addAttribute(MainetConstants.AdvanceEntry.VENDOR_LIST, list);

        final String status = MainetConstants.STATUS.ACTIVE;
        final List<String> bankNameList = bankAccountService.getBankNameStatusList(status);
        model.addAttribute(MainetConstants.BANK_MASTER.BANKNAME_LIST, bankNameList);
        final List<LookUp> activeDeActiveMap = CommonMasterUtility.getListLookup(PrefixConstants.ACN,
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_MAP, activeDeActiveMap);
        if (formMode == FormMode.CREATE) {
            final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            final Map<Long, String> sacHeadMap = new LinkedHashMap<>();
            final Long depTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    PrefixConstants.StandardAccountHeadMapping.SD, AccountPrefix.SAM.toString(), orgId);
            final Long depSubtypeId = tbAcPayToBank.getPtbTdsType();
            final Long statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.STATUS.ACTIVE,
                    AccountPrefix.ACN.toString(), orgId);
            final List<AccountHeadSecondaryAccountCodeMasterEntity> secHeadDescList = secondaryheadmasterservice
                    .getSecondaryHeadcodesForTax(orgId);
            for (AccountHeadSecondaryAccountCodeMasterEntity secHeadMasterEntity : secHeadDescList) {
                if (secHeadMasterEntity.getSacHeadId() != null && secHeadMasterEntity.getSacHeadDesc() != null) {
                    sacHeadMap.put(secHeadMasterEntity.getSacHeadId(), secHeadMasterEntity.getAcHeadCode());
                }
            }
            /*
             * sacHeadMap.putAll(secondaryheadmasterservice.findAccountHeadDepTypes( depTypeId, depSubtypeId, statusId, orgId));
             */
            model.addAttribute(SAC_HEAD_MAP, sacHeadMap);
            model.addAttribute(MODE, MODE_CREATE); // The form is in "create"
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
        } else if (formMode == FormMode.UPDATE) {
            final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            final Map<Long, String> sacHeadMap = new LinkedHashMap<>();
            final Long depTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    PrefixConstants.StandardAccountHeadMapping.SD, AccountPrefix.SAM.toString(), orgId);
            final Long depSubtypeId = tbAcPayToBank.getPtbTdsType();
            final Long statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.STATUS.ACTIVE,
                    AccountPrefix.ACN.toString(), orgId);
            final List<AccountHeadSecondaryAccountCodeMasterEntity> secHeadDescList = secondaryheadmasterservice
                    .getSecondaryHeadcodesForTax(orgId);
            for (AccountHeadSecondaryAccountCodeMasterEntity secHeadMasterEntity : secHeadDescList) {
                if (secHeadMasterEntity.getSacHeadId() != null && secHeadMasterEntity.getSacHeadDesc() != null) {
                    sacHeadMap.put(secHeadMasterEntity.getSacHeadId(), secHeadMasterEntity.getAcHeadCode());
                }
            }
            model.addAttribute(SAC_HEAD_MAP, sacHeadMap);
            model.addAttribute(MODE, MODE_UPDATE); // The form is in "update"
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);
        }
    }

    @RequestMapping(params = "getChequeData")
    public @ResponseBody List getCheqData(final HttpServletRequest request, final Model model,
            @RequestParam("tdsType") final Long tdsType) {
        log("Action-'getChequeData' : 'get getChequeData search data'");

        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        Organisation defaultOrg = null;
        if (isDafaultOrgExist) {
            defaultOrg = ApplicationSession.getInstance().getSuperUserOrganization();
        } else {
            defaultOrg = UserSession.getCurrent().getOrganisation();
        }
        list = new ArrayList<>();
        list.clear();
        list = acPayToBankService.getTdsTypeData(tdsType, UserSession.getCurrent().getOrganisation().getOrgid(),
                defaultOrg.getOrgid());
        return list;
    }

    /**
     * Shows a list with all the occurrences of TbAcPayToBank found in the database
     *
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping()
    public String list(final Model model) {
        log("Action 'list'");
        list = new ArrayList<>();
        list.clear();
        final TbAcPayToBank tbAcPayToBank = new TbAcPayToBank();
        model.addAttribute(MainetConstants.BANK_MASTER_PAY_TDS.MAIN_ENTITY_NAME, tbAcPayToBank);
        populateModel(model, tbAcPayToBank, FormMode.CREATE);
        return JSP_LIST;
    }

    /**
     * Shows a form page in order to create a new TbAcPayToBank
     *
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping(params = "form")
    public String formForCreate(final Model model) {
        log("Action 'formForCreate'");
        // --- Populates the model with a new instance
        final TbAcPayToBank tbAcPayToBank = new TbAcPayToBank();
        populateModel(model, tbAcPayToBank, FormMode.CREATE);
        return JSP_FORM;
    }

    /**
     * Shows a form page in order to update an existing TbAcPayToBank
     *
     * @param model Spring MVC model
     * @param ptbId primary key element
     * @return
     */

    @RequestMapping(params = "formForUpdate")
    public String formForUpdate(@RequestParam("ptbId") final Long ptbId, final Model model) {
        log("Action 'formForUpdate'");

        TbAcPayToBank tbAcPayToBank = null;
        if (ptbId != null) {
            tbAcPayToBank = tbAcPayToBankService.findById(ptbId);
        } else {
            tbAcPayToBank = new TbAcPayToBank();
        }

        final String bankName = tbAcPayToBank.getPtbBankbranch();
        List<LookUp> branchDetails = null;
        branchDetails = new ArrayList<>();
        LookUp lookUp = null;
        final List<Object[]> branchList = bankAccountService.getBranchNamesByBank(bankName);
        for (final Object[] obj : branchList) {
            lookUp = new LookUp();
            if ((obj[0] != null) && (obj[1] != null)) {
                lookUp.setLookUpId(Long.valueOf(obj[0].toString()));
                lookUp.setDescLangFirst(obj[1].toString());
            }
            branchDetails.add(lookUp);
        }
        populateModel(model, tbAcPayToBank, FormMode.UPDATE);
        model.addAttribute(MainetConstants.BankAccountMaster.BRANCH_LOOKUP, branchDetails);
        return JSP_FORM;
    }

    @RequestMapping(params = "viewMode")
    public String update(@Valid final TbAcPayToBank tbAcPayToBankrBeen, final BindingResult bindingResult,
            final Model model, final HttpServletRequest httpServletRequest,
            @RequestParam("MODE1") final String viewmode) {
        log("Action 'update'");
        String viewReturned = MainetConstants.CommonConstant.BLANK;
        if (tbAcPayToBankrBeen.getPtbId() != null) {
            if (viewmode.endsWith(View)) {
                model.addAttribute(MODE, View);
                final TbAcPayToBank acPayToBank = tbAcPayToBankService.findById(tbAcPayToBankrBeen.getPtbId());
                log("Action 'update' : update done - redirect");
                final String bankName = acPayToBank.getPtbBankbranch();
                List<LookUp> branchDetails = null;
                branchDetails = new ArrayList<>();
                LookUp lookUp = null;
                final List<Object[]> branchList = bankAccountService.getBranchNamesByBank(bankName);
                for (final Object[] obj : branchList) {
                    lookUp = new LookUp();
                    if ((obj[0] != null) && (obj[1] != null)) {
                        lookUp.setLookUpId(Long.valueOf(obj[0].toString()));
                        lookUp.setDescLangFirst(obj[1].toString());
                    }
                    branchDetails.add(lookUp);
                }
                populateModel(model, acPayToBank, FormMode.UPDATE);
                model.addAttribute(MODE, viewmode);
                model.addAttribute(MainetConstants.BankAccountMaster.BRANCH_LOOKUP, branchDetails);
                viewReturned = JSP_FORM;
            } else {
                model.addAttribute(MODE, MODE_CREATE);
            }
        } else {
            log("Action 'list'");
            final List<TbAcPayToBank> list = tbAcPayToBankService.findAll();
            model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, list);
        }

        return viewReturned;

    }

    @RequestMapping(params = "getptbTdsTypeDuplicateData", method = RequestMethod.POST)
    public @ResponseBody boolean findTdsTypDuplicateCombination(final TbAcPayToBank tbAcPayToBank,
            final HttpServletRequest request, final Model model, final BindingResult bindingResult) {
        boolean isValidationError = false;
        final Long ptbTdsType = tbAcPayToBank.getPtbTdsType();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final String status = MainetConstants.STATUS.ACTIVE;
        if (tbAcPayToBankService.isCombinationExists(ptbTdsType, orgId, status)) {
            bindingResult.addError(
                    new org.springframework.validation.FieldError(MainetConstants.BANK_MASTER_PAY_TDS.MAIN_ENTITY_NAME,
                            MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                            ApplicationSession.getInstance().getMessage(MainetConstants.BLANK)));
            isValidationError = true;
        }
        return isValidationError;
    }

    @RequestMapping(params = "getptbBSRCodeDuplicateData", method = RequestMethod.POST)
    public @ResponseBody boolean findBSRCodeDuplicateCombination(final TbAcPayToBank tbAcPayToBank,
            final HttpServletRequest request, final Model model, final BindingResult bindingResult) {
        boolean isValidationError = false;
        final Long bankId = tbAcPayToBank.getBankId();
        final String ptbBsrcode = tbAcPayToBank.getPtbBsrcode();
        if (tbAcPayToBankService.isBSRCodeCombinationExists(bankId, ptbBsrcode)) {
            bindingResult.addError(
                    new org.springframework.validation.FieldError(MainetConstants.BANK_MASTER_PAY_TDS.MAIN_ENTITY_NAME,
                            MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                            ApplicationSession.getInstance().getMessage(MainetConstants.BLANK)));
            isValidationError = true;
        }
        return isValidationError;
    }

    @RequestMapping(params = "getDepTypeWiseAccountHead", method = RequestMethod.POST)
    public String getDepTypeWiseAccountHead(final TbAcPayToBank tbAcPayToBank, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest request,
            final BindingResult bindingResult) throws Exception {
        log("TbAcPayToBank-'getDepTypeWiseAccountHead' : 'getDepTypeWiseAccountHead data'");
        String result = MainetConstants.BLANK;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Map<Long, String> sacHeadMap = new LinkedHashMap<>();
        final Long depTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                PrefixConstants.StandardAccountHeadMapping.SD, AccountPrefix.SAM.toString(), orgId);
        final Long depSubtypeId = tbAcPayToBank.getPtbTdsType();
        final Long statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.STATUS.ACTIVE,
                AccountPrefix.ACN.toString(), orgId);
        final List<AccountHeadSecondaryAccountCodeMasterEntity> secHeadDescList = secondaryheadmasterservice
                .getSecondaryHeadcodesForTax(orgId);

        for (AccountHeadSecondaryAccountCodeMasterEntity secHeadMasterEntity : secHeadDescList) {
            if (secHeadMasterEntity.getSacHeadId() != null && secHeadMasterEntity.getSacHeadDesc() != null) {
                sacHeadMap.put(secHeadMasterEntity.getSacHeadId(), secHeadMasterEntity.getAcHeadCode());
            }
        }
        /*
         * sacHeadMap.putAll(secondaryheadmasterservice.findAccountHeadDepTypes( depTypeId, depSubtypeId, statusId, orgId));
         */
        model.addAttribute(SAC_HEAD_MAP, sacHeadMap);
        populateModel(model, tbAcPayToBank, FormMode.CREATE);
        model.addAttribute(MainetConstants.BANK_MASTER_PAY_TDS.MAIN_ENTITY_NAME, tbAcPayToBank);
        result = JSP_FORM;
        return result;
    }

    @RequestMapping(params = "create")
    // GET or POST
    public ModelAndView create(@Valid final TbAcPayToBank tbAcPayToBank, final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes,
            final HttpServletRequest httpServletRequest) {
        log("Action 'create'");

        new PayToBankValidator();

        if (!bindingResult.hasErrors()) {

            tbAcPayToBank.setLmoddate(new Date());

            tbAcPayToBank.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            tbAcPayToBank.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            tbAcPayToBank.setLgIpMac(Utility.getMacAddress());
            tbAcPayToBank.setLangId((long) UserSession.getCurrent().getLanguageId());

            final TbAcPayToBank tbAcPayToBankrCreated = tbAcPayToBankService.create(tbAcPayToBank);

            model.addAttribute(MainetConstants.BANK_MASTER_PAY_TDS.MAIN_ENTITY_NAME, tbAcPayToBankrCreated);

            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME,
                    MainetConstants.SUCCESS_MSG);

        } else {
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            populateModel(model, tbAcPayToBank, FormMode.CREATE);
            return new ModelAndView(JSP_FORM);
        }

    }

    /**
     * 'UPDATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     *
     * @param tbAcPayToBank entity to be updated
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "update")
    // GET or POST
    public ModelAndView update(@Valid final TbAcPayToBank tbAcPayToBank, final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes,
            final HttpServletRequest httpServletRequest) {
        log("Action 'update'");
        if (!bindingResult.hasErrors()) {

            tbAcPayToBank.setUserId(tbAcPayToBank.getUserId());
            tbAcPayToBank.setLmoddate(tbAcPayToBank.getLmoddate());
            tbAcPayToBank.setLgIpMac(tbAcPayToBank.getLgIpMac());

            tbAcPayToBank.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            tbAcPayToBank.setUpdatedDate(new Date());
            tbAcPayToBank.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            tbAcPayToBank.setLgIpMacUpd(Utility.getMacAddress());

            // --- Perform database operations
            final TbAcPayToBank tbAcPayToBankSaved = tbAcPayToBankService.update(tbAcPayToBank);

            model.addAttribute(MainetConstants.BANK_MASTER_PAY_TDS.MAIN_ENTITY_NAME, tbAcPayToBankSaved);
            // --- Set the result message
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            log("Action 'update' : update done - redirect");
            return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME,
                    MainetConstants.SUCCESS_MSG);

        } else {
            log("Action 'update' : binding errors");
            populateModel(model, tbAcPayToBank, FormMode.UPDATE);
            return new ModelAndView(JSP_FORM);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(final HttpServletRequest request,
            final Model model) {
        log("Action-'gridData' : 'Get grid Data'");
        final JQGridResponse response = new JQGridResponse<>();
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        response.setRows(list);
        response.setTotal(list.size());
        response.setRecords(list.size());
        response.setPage(page);
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, list);
        return response;
    }

}
