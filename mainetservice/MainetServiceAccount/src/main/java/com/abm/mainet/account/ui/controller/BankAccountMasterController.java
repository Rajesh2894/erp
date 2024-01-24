package com.abm.mainet.account.ui.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.dto.BankAccountMaster;
import com.abm.mainet.account.dto.BankAccountMasterUploadDTO;
import com.abm.mainet.account.service.BankAccountService;
import com.abm.mainet.account.ui.model.BankMasterResponse;
import com.abm.mainet.account.ui.validator.BankAccountMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.BankAccountMasterEntity;
import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.ReadExcelData;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.BankAccountMasterDto;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountFunctionMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountHeadPrimaryAccountCodeMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.BankMasterService;
import com.abm.mainet.common.master.service.TbCustbanksService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.upload.excel.WriteExcelData;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Message;
import com.abm.mainet.common.utility.MessageType;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * Spring MVC controller for 'TbBankmaster' management.
 */
@Controller
@RequestMapping("BankAccountMaster.html")
public class BankAccountMasterController extends AbstractController {

    private static final Logger LOGGER = Logger.getLogger(BankAccountMasterController.class);

    @Resource
    private BankAccountService bankAccountService;

    @Autowired
    private AccountHeadPrimaryAccountCodeMasterService tbAcPrimaryheadMasterService;

    @Resource
    private TbCustbanksService tbCustbanksService;

    @Resource
    private AccountFieldMasterService tbAcFieldMasterService;

    @Resource
    private SecondaryheadMasterService secondaryHeadMasterService;

    @Resource
    private TbAcCodingstructureMasService tbAcCodingstructureMasService;

    @Resource
    private TbOrganisationService tbOrganisationService;

    @Resource
    private AccountFunctionMasterService tbAcFunctionMasterService;

    @Resource
    private BankMasterService bankMasterService;
    @Autowired
    private IFileUploadService fileUpload;
    private static final String JSP_LIST = "bankAccountMaster/list";
    private static final String JSP_FORM = "bankAccountMaster/form";
    private static final String JSP_VIEW = "bankAccountMaster/view";
    private static final String JSP_EXCELUPLOAD = "bankAccountMaster/ExcelUpload";
    private static final String SAVE_ACTION_CREATE = "BankAccountMaster.html?create";

    private static final String SAVE_ACTION_UPDATE = "BankAccountMaster.html?update";

    private static final String ENTITY = "bankAccountMaster";

    private static final String FUND_LIST = "fundList";

    private static List<BankAccountMasterDto> banklist = new ArrayList<>();

    private static final String CUSTOMERBANKLIST = "customerBankList";

    public BankAccountMasterController() {
        super(BankAccountMasterController.class, ENTITY);
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
        binder.registerCustomEditor(Date.class, "listOfTbBankaccount.baCurrentdate",
                new CustomDateEditor(dateFormat, true));
    }

    /**
     * Populates the Spring MVC model with the given entity and eventually other useful data
     * 
     * @param model
     * @param tbBankmaster
     * @throws Exception
     */
    private void populateModel(final Model model, final BankAccountMaster bankAccountMaster, final String formMode)
            throws Exception {

        model.addAttribute(ENTITY, bankAccountMaster);
        final LookUp fundLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.FUND_CPD_VALUE,
                PrefixConstants.CMD, UserSession.getCurrent().getLanguageId(),
                UserSession.getCurrent().getOrganisation());
        ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
        final int langId = UserSession.getCurrent().getLanguageId();

        final List<BankMasterEntity> customerBankList = bankMasterService.getBankList();
        model.addAttribute(CUSTOMERBANKLIST, customerBankList);

        final List<LookUp> bankType = CommonMasterUtility.getListLookup(PrefixConstants.BAT,
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(MainetConstants.BANK_MASTER.BANK_TYPE, bankType);
        final List<LookUp> accountType = CommonMasterUtility.getListLookup(PrefixConstants.ACT,
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(MainetConstants.BANK_MASTER.ACCOUNT_TYPE, accountType);
        final List<LookUp> accountStatus = CommonMasterUtility.getListLookup(PrefixConstants.BAS,
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(MainetConstants.BANK_MASTER.ACCOUNT_STATUS, accountStatus);

        boolean fieldDefaultFlag = false;
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        if (isDafaultOrgExist) {
            fieldDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FIELD_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            fieldDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FIELD_CPD_VALUE,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        Organisation defaltorg = null;
        Long defaltorgId = null;
        if (isDafaultOrgExist && fieldDefaultFlag) {
            defaltorg = ApplicationSession.getInstance().getSuperUserOrganization();
            defaltorgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
        } else if (isDafaultOrgExist && (fieldDefaultFlag == false)) {
            defaltorg = UserSession.getCurrent().getOrganisation();
            defaltorgId = UserSession.getCurrent().getOrganisation().getOrgid();
        } else {
            defaltorg = UserSession.getCurrent().getOrganisation();
            defaltorgId = UserSession.getCurrent().getOrganisation().getOrgid();
        }
        model.addAttribute(MainetConstants.BANK_MASTER.FIELD_LIST,
                tbAcFieldMasterService.getFieldMasterStatusLastLevels(defaltorgId, defaltorg, langId));

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

        model.addAttribute(FUND_LIST, tbAcCodingstructureMasService.getFundMasterActiveStatusList(defultorgId,
                defultorg, fundLookup.getLookUpId(), langId));
        boolean primaryDefaultFlag = false;
        if (isDafaultOrgExist) {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }

        Long defaultorgId = null;
        if (isDafaultOrgExist && primaryDefaultFlag) {
            ApplicationSession.getInstance().getSuperUserOrganization();
            defaultorgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
        } else if (isDafaultOrgExist && (primaryDefaultFlag == false)) {
            UserSession.getCurrent().getOrganisation();
            defaultorgId = UserSession.getCurrent().getOrganisation().getOrgid();
        } else {
            UserSession.getCurrent().getOrganisation();
            defaultorgId = UserSession.getCurrent().getOrganisation().getOrgid();
        }

        final Map<Long, String> primaryHead = tbAcPrimaryheadMasterService
                .getPrimaryHeadCodeLastLevelsForBank(defaultorgId);

        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final Long orgid = organisation.getOrgid();
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
        final List<LookUp> accountTypeLevel = CommonMasterUtility.getListLookup(PrefixConstants.CMD_PREFIX,
                UserSession.getCurrent().getOrganisation());
        for (final LookUp lookUp : accountTypeLevel) {
            if (PrefixConstants.FUNCTION_CPD_VALUE.equalsIgnoreCase(lookUp.getLookUpCode())) {
                if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                    if (lookUp.getOtherField().equals(MainetConstants.MENU.Y)) {
                        if (isDafaultOrgExist && functionDefaultFlag) {
                            model.addAttribute(MainetConstants.BankAccountMaster.PRIMARY_MASTER_ITEM,
                                    tbAcFunctionMasterService.getFunctionMasterStatusLastLevels(
                                            ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(),
                                            ApplicationSession.getInstance().getSuperUserOrganization(), langId));
                        } else if (isDafaultOrgExist && (functionDefaultFlag == false)) {
                            model.addAttribute(MainetConstants.BankAccountMaster.PRIMARY_MASTER_ITEM,
                                    tbAcFunctionMasterService.getFunctionMasterStatusLastLevels(orgid, organisation,
                                            langId));
                        } else {
                            model.addAttribute(MainetConstants.BankAccountMaster.PRIMARY_MASTER_ITEM,
                                    tbAcFunctionMasterService.getFunctionMasterStatusLastLevels(orgid, organisation,
                                            langId));
                        }
                        model.addAttribute(MainetConstants.FUNCTION_MASTER.FUNCTION_STATUS, MainetConstants.MASTER.Y);
                    }
                }
            }
        }

        model.addAttribute(MainetConstants.BANK_MASTER.PRIMARY_LIST, primaryHead);
        if (formMode.equalsIgnoreCase(MODE_CREATE)) {
            model.addAttribute(MODE, MODE_CREATE);
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
        } else if (formMode.equalsIgnoreCase(MODE_UPDATE)) {
            model.addAttribute(MODE, MODE_UPDATE);
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);
        } else if (formMode.equalsIgnoreCase(MODE_VIEW)) {
            model.addAttribute(MODE, MODE_VIEW);
        }
    }

    /**
     * Shows a list with all the occurrences of TbBankmaster found in the database
     * 
     * @param model Spring MVC model
     * @return
     * @throws Exception
     */
    @RequestMapping()
    public String list(final HttpServletRequest request, final Model model) throws Exception {
        String result = StringUtils.EMPTY;

        banklist.clear();
        final List<BankAccountMasterDto> mainList = bankAccountService
                .findAll(UserSession.getCurrent().getOrganisation().getOrgid());
        Map<Long, String> bankAccountMap = new HashMap<Long, String>();
        if (mainList != null && !mainList.isEmpty()) {
            for (final BankAccountMasterDto obj : mainList) {
                bankAccountMap.put(obj.getBaAccountId(), obj.getBaAccountName());
            }
        }

        final List<Object[]> bankListOfObj = bankAccountService
                .findBankDeatilsInBankAccount(UserSession.getCurrent().getOrganisation().getOrgid());
        Map<Long, String> bankMap = new HashMap<Long, String>();
        if (bankListOfObj != null && !bankListOfObj.isEmpty()) {
            for (Object[] objects : bankListOfObj) {
                if (objects[0] != null && objects[1] != null && objects[2] != null && objects[3] != null) {
                    bankMap.put((Long) objects[0], objects[1].toString() + MainetConstants.SEPARATOR + objects[2].toString()
                            + MainetConstants.SEPARATOR + objects[3].toString());
                }
            }
        }
        model.addAttribute(MainetConstants.BANK_MASTER.BANK_MAP_LIST, bankMap);

        final BankAccountMaster bankAccountMaster = new BankAccountMaster();
        populateModel(model, bankAccountMaster, MODE_CREATE);
        model.addAttribute(MainetConstants.BANK_MASTER.BANK_ACCOUNT_MAP_LIST, bankAccountMap);
        result = JSP_LIST;
        return result;

    }

    @RequestMapping(params = "getGridData")
    public @ResponseBody BankMasterResponse gridData(final HttpServletRequest request, final Model model) {

        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        final BankMasterResponse response = new BankMasterResponse();
        response.setRows(banklist);
        response.setTotal(banklist.size());
        response.setRecords(banklist.size());
        response.setPage(page);
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, banklist);
        return response;
    }

    @RequestMapping(params = "searchBankAccount")
    public @ResponseBody List getBankData(@RequestParam("accountNo") final String accountNo,
            @RequestParam("accNameId") final Long accountNameId, @RequestParam("bankId") final Long bankId,
            final HttpServletRequest request, final Model model) {

        banklist = new ArrayList<>();
        banklist.clear();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final List<BankAccountMasterDto> bankAccountByBankId = bankAccountService.findByAllGridSearchData(accountNo,
                accountNameId, bankId, orgId);
        if (bankAccountByBankId != null && !bankAccountByBankId.isEmpty()) {
            for (final BankAccountMasterDto dto : bankAccountByBankId) {
                dto.setBankTypeDesc(getAccounttypeDesc(dto.getCpdAccountType()));
                banklist.add(dto);
            }
        }
        return banklist;
    }

    /**
     * @param cpdAccountType
     * @return
     */
    private String getAccounttypeDesc(final Long cpdAccountType) {
        String desc = MainetConstants.CommonConstant.BLANK;
        final List<LookUp> bankType = CommonMasterUtility.getListLookup(PrefixConstants.ACT,
                UserSession.getCurrent().getOrganisation());
        for (final LookUp lk : bankType) {
            if (lk.getLookUpId() == cpdAccountType) {
                desc = lk.getDescLangFirst();
                break;
            }
        }
        return desc;
    }

    /**
     * Shows a form page in order to create a new TbBankmaster
     * 
     * @param model Spring MVC model
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "form")
    public String formForCreate(final HttpServletRequest request, final Model model) throws Exception {

        final BankAccountMaster bankAccountMaster = new BankAccountMaster();
        populateModel(model, bankAccountMaster, MODE_CREATE);
        return JSP_FORM;
    }

    /**
     * 'CREATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * 
     * @param tbBankmaster entity to be created
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "create") // GET or POST
    public String create(@Valid final BankAccountMaster bankAccountMaster, final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {
        String result = StringUtils.EMPTY;
        if (!bindingResult.hasErrors()) {
            validateAccountsByMapping(bankAccountMaster, bindingResult, model, httpServletRequest);
            if (bindingResult.hasErrors()) {
                bankAccountMaster.setAlreadyExist(MainetConstants.MASTER.Y);
                bankAccountMaster.setSuccessFlag(MainetConstants.MASTER.N);
                populateModel(model, bankAccountMaster, MODE_CREATE);
                model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
                result = JSP_FORM;
            } else {
                setCommonFields(bankAccountMaster, httpServletRequest);
                final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
                Organisation defaultOrg = null;
                if (isDafaultOrgExist) {
                    defaultOrg = ApplicationSession.getInstance().getSuperUserOrganization();
                } else {
                    defaultOrg = UserSession.getCurrent().getOrganisation();
                }
                final Organisation org = UserSession.getCurrent().getOrganisation();
                bankAccountMaster.setOrgId(org.getOrgid());
                final int langId = UserSession.getCurrent().getLanguageId();
                final Long userId = UserSession.getCurrent().getEmployee().getEmpId();
                final Long accountStatus = getDefaultAccountStatus();
                bankAccountMaster.setIpMacAddress(UserSession.getCurrent().getEmployee().getEmppiservername());
                final BankAccountMaster bankAccountCreated = bankAccountService.create(bankAccountMaster, defaultOrg,
                        org, userId, langId, accountStatus);
                bankAccountCreated.setSuccessFlag(MainetConstants.MASTER.Y);
                model.addAttribute(ENTITY, bankAccountCreated);
                result = JSP_FORM;
            }
        } else {
            bankAccountMaster.setSuccessFlag(MainetConstants.MASTER.N);
            LOGGER.error("Exception occured in create method for binding result");
            result = MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW;
        }

        return result;
    }

    public Long getDefaultAccountStatus() {
        final Integer languageId = UserSession.getCurrent().getLanguageId();

        final LookUp activeLookUp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.PaymentMode.USER_ADUSTMENT, PrefixConstants.BAS, languageId,
                UserSession.getCurrent().getOrganisation());
        final Long activeLookupId = activeLookUp.getLookUpId();
        return activeLookupId;
    }

    /**
     * @param bindingResult
     * @param bankAccountMaster
     *
     */
    private void validateAccountsByMapping(final BankAccountMaster bankAccountMaster, final BindingResult bindingResult,
            final Model model, final HttpServletRequest httpServletRequest) {

        if ((bankAccountMaster.getListOfTbBankaccount() != null)
                && !bankAccountMaster.getListOfTbBankaccount().isEmpty()) {

            for (final BankAccountMasterDto account : bankAccountMaster.getListOfTbBankaccount()) {

                final boolean isPresent = bankAccountService.validateDuplicateCombination(account.getBaAccountNo(),
                        account.getFunctionId(), account.getPacHeadId(), account.getFieldId(), account.getFundId());

                if (isPresent) {
                    bindingResult.addError(new org.springframework.validation.FieldError(ENTITY, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null,
                            MainetConstants.CommonConstant.BLANK + " " + ApplicationSession.getInstance().getMessage(
                                    "Duplicate combination of Account Number, Account Head, Field and Fund")));
                }

            }
        }
    }

    /**
     * @param tbBankmaster
     */
    private void setCommonFields(final BankAccountMaster bankAccountMaster,
            final HttpServletRequest httpServletRequest) {
        final Long langId = (long) UserSession.getCurrent().getLanguageId();
        final Long userId = UserSession.getCurrent().getEmployee().getEmpId();

        for (final BankAccountMasterDto dto : bankAccountMaster.getListOfTbBankaccount()) {
            dto.setCreatedBy(userId);
            dto.setLangId(langId);
            dto.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
        }

    }

    @RequestMapping(params = "getBranchNames", method = RequestMethod.POST)
    public @ResponseBody List<LookUp> getBranchNames(final Model model,
            @RequestParam("bankName") final String bankName) {
        log("Action 'get branch name details'");

        List<LookUp> branchDetails = null;
        branchDetails = new ArrayList<>();
        LookUp lookUp = null;

        final List<Object[]> branchList = bankAccountService.getBranchNamesByBank(bankName);
        for (final Object[] obj : branchList) {
            lookUp = new LookUp();
            if ((obj[0] != null) && (obj[1] != null) && (obj[2] != null)) {
                lookUp.setLookUpId(Long.valueOf(obj[0].toString()));
                lookUp.setDescLangFirst(obj[1].toString() + MainetConstants.SEPARATOR + obj[2].toString());
            }
            branchDetails.add(lookUp);
        }
        return branchDetails;
    }

    /**
     * Shows a form page in order to update an existing Bank Account
     * 
     * @param model Spring MVC model
     * @param baAccountId primary key element
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "formForUpdate")
    public String formForUpdate(final Model model, @RequestParam("baAccountId") final Long baAccountId,
            @RequestParam(MainetConstants.MODE) final String viewmode) throws Exception {
        String result = StringUtils.EMPTY;

        final BankAccountMaster bankAccountMaster = new BankAccountMaster();
        final BankAccountMasterDto bankAccountDto = bankAccountService.findAccountByAccountId(baAccountId);
        bankAccountMaster.setAccountId(baAccountId);
        bankAccountMaster.getListOfTbBankaccount().add(bankAccountDto);
        bankAccountMaster.setBankType(Long.valueOf(bankAccountDto.getBankTypeDesc()));
        bankAccountMaster.setBankName(bankAccountDto.getBankId().toString());
        bankAccountMaster.setOrgId(bankAccountDto.getOrgId());
        populateModel(model, bankAccountMaster, viewmode);
        result = JSP_FORM;
        return result;
    }

    /**
     * Shows a form page in order to update an existing Bank Account
     * 
     * @param model Spring MVC model
     * @param baAccountId primary key element
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "formForView")
    public String formForView(final Model model, @RequestParam("baAccountId") final Long baAccountId,
            @RequestParam(MainetConstants.MODE) final String viewmode) throws Exception {
        String result = StringUtils.EMPTY;

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final BankAccountMaster bankAccountMaster = bankAccountService.viewBankAccountForm(baAccountId, orgId);
        populateModel(model, bankAccountMaster, viewmode);
        result = JSP_VIEW;

        return result;
    }

    /**
     * 'UPDATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * 
     * @param bankAccountMaster entity to be updated
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "update") // GET or POST
    public String update(@Valid final BankAccountMaster bankAccountMaster, final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes,
            final HttpServletRequest httpServletRequest) {
        String result = StringUtils.EMPTY;
        if (!bindingResult.hasErrors()) {
            setCommonFieldsForUpdate(bankAccountMaster, httpServletRequest);
            final Organisation org = UserSession.getCurrent().getOrganisation();
            final int langId = UserSession.getCurrent().getLanguageId();
            final BankAccountMaster tbBankmasterSaved = bankAccountService.update(bankAccountMaster, org, langId);
            tbBankmasterSaved.setSuccessFlag(MainetConstants.MASTER.Y);
            model.addAttribute(ENTITY, tbBankmasterSaved);
            result = JSP_FORM;
        } else {
            bankAccountMaster.setSuccessFlag(MainetConstants.MASTER.N);
            LOGGER.error("error in update method for binding result");
            result = MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW;
        }
        return result;
    }

    private void setCommonFieldsForUpdate(final BankAccountMaster bankAccountMas,
            final HttpServletRequest httpServletRequest) {
        final Long langId = (long) UserSession.getCurrent().getLanguageId();
        final Long userId = UserSession.getCurrent().getEmployee().getEmpId();

        for (final BankAccountMasterDto dto : bankAccountMas.getListOfTbBankaccount()) {
            dto.setUpdatedBy(userId);
            dto.setLangId(langId);
            dto.setLgIpMacUpd(Utility.getClientIpAddress(httpServletRequest));
            dto.setUpdatedDate(new Date());
        }
    }

    @RequestMapping(params = "getAccountNumberDuplicate", method = RequestMethod.POST)
    public @ResponseBody boolean findBudgetOpenBalDuplicateCombination(final BankAccountMaster bankAccountMaster,
            final HttpServletRequest request, final Model model, final BindingResult bindingResult,
            @RequestParam("cnt") final int cnt) {
        boolean isValidationError = false;

        final String bankName = bankAccountMaster.getBankName();
        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        final String baAccountNo = bankAccountMaster.getListOfTbBankaccount().get(cnt).getBaAccountNo();
        if ((baAccountNo != null) && !baAccountNo.isEmpty()) {
            if (bankAccountService.isCombinationExists(bankName, baAccountNo, orgid)) {
                bindingResult.addError(new org.springframework.validation.FieldError(
                        MainetConstants.BankAccountMaster.BANK_ACC_MASTER, MainetConstants.CommonConstant.BLANK, null,
                        false, new String[] { MainetConstants.ERRORS }, null,
                        ApplicationSession.getInstance().getMessage(MainetConstants.CommonConstant.BLANK)));
                isValidationError = true;
            }
        }
        return isValidationError;
    }

    @RequestMapping(params = "exportTemplateData", method = RequestMethod.POST)
    public String exportImportExcelTemplate(final Model model) throws Exception {
        log("BankAccountMaster-'exportImportExcelTemplate' : 'exportImportExcelTemplate'");
        String result = MainetConstants.CommonConstant.BLANK;
        final BankAccountMaster bean = new BankAccountMaster();
        populateModel(model, bean, MODE_CREATE);
        fileUpload.sessionCleanUpForFileUpload();
        result = JSP_EXCELUPLOAD;
        return result;
    }

    @RequestMapping(params = "ExcelTemplateData")
    public void exportAccountDepositExcelData(final HttpServletResponse response, final HttpServletRequest request) {

        try {
            WriteExcelData<BankAccountMasterUploadDTO> data = new WriteExcelData<>(
                    MainetConstants.BANKACCOUNTMASTERUPLOADDTO + MainetConstants.XLSX_EXT, request, response);

            data.getExpotedExcelSheet(new ArrayList<BankAccountMasterUploadDTO>(), BankAccountMasterUploadDTO.class);
        } catch (Exception ex) {
            throw new FrameworkException(ex.getMessage());
        }
    }

    @RequestMapping(params = "loadExcelData", method = RequestMethod.POST)
    public String loadValidateAndLoadExcelData(BankAccountMaster bean, final BindingResult bindingResult,
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
        final Organisation org = UserSession.getCurrent().getOrganisation();
        final UserSession userSession = UserSession.getCurrent();
        final Long orgId = userSession.getOrganisation().getOrgid();
        final int langId = userSession.getLanguageId();
        final Long userId = userSession.getEmployee().getEmpId();
        final Long accountStatus = getDefaultAccountStatus();
        final String filePath = getUploadedFinePath();
        ReadExcelData<BankAccountMasterUploadDTO> data = new ReadExcelData<>(filePath,
                BankAccountMasterUploadDTO.class);
        data.parseExcelList();
        List<String> errlist = data.getErrorList();
        if (!errlist.isEmpty()) {
            bindingResult.addError(new org.springframework.validation.FieldError(
                    MainetConstants.BankAccountMaster.BANK_ACC_MASTER, MainetConstants.BLANK, null,
                    false, new String[] { MainetConstants.ERRORS }, null,
                    session.getMessage("accounts.empty.excel")));
        } else {
            final List<BankAccountMasterUploadDTO> bankAccountMasterUploadDtos = data.getParseData();

            final List<BankMasterEntity> customerBankList = bankMasterService.getBankList();
            Map<Long, String> bankMap = new HashMap<>();
            for (BankMasterEntity bankMasterEntity : customerBankList) {
                bankMap.put(bankMasterEntity.getBankId(),
                        bankMasterEntity.getBank() + '-' + bankMasterEntity.getIfsc());
            }
            final List<LookUp> bankType = CommonMasterUtility.getListLookup(PrefixConstants.BAT,
                    UserSession.getCurrent().getOrganisation());

            final List<LookUp> accountType = CommonMasterUtility.getListLookup(PrefixConstants.ACT,
                    UserSession.getCurrent().getOrganisation());

            boolean primaryDefaultFlag = false;
            if (isDafaultOrgExist) {
                primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                        ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
            } else {
                primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                        UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
            }
            Long defaultorgId = null;
            if (isDafaultOrgExist && primaryDefaultFlag) {
                ApplicationSession.getInstance().getSuperUserOrganization();
                defaultorgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
            } else if (isDafaultOrgExist && (primaryDefaultFlag == false)) {
                UserSession.getCurrent().getOrganisation();
                defaultorgId = UserSession.getCurrent().getOrganisation().getOrgid();
            } else {
                UserSession.getCurrent().getOrganisation();
                defaultorgId = UserSession.getCurrent().getOrganisation().getOrgid();
            }
            final Map<Long, String> primaryHead = tbAcPrimaryheadMasterService
                    .getPrimaryHeadCodeLastLevelsForBankAccount(defaultorgId);
            final Map<Long, String> functionHead = tbAcFunctionMasterService.getFunctionCompositeCode(defaultOrg, langId);
            final List<BankAccountMasterEntity> mainList = bankAccountService
                    .getBankAccountMasterDet(UserSession.getCurrent().getOrganisation().getOrgid());
            BankAccountMasterValidator validator = new BankAccountMasterValidator();
            List<BankAccountMasterUploadDTO> bankAccountMasterUploadDtosUploadList = validator
                    .excelValidation(bankAccountMasterUploadDtos, bindingResult, bankMap, bankType, accountType, functionHead,
                            primaryHead);

            if (validator.count == 0) {
                validator.isAccNumExists(bankAccountMasterUploadDtosUploadList, bindingResult, mainList);

            }

            if (!bindingResult.hasErrors()) {
                for (BankAccountMasterUploadDTO bankAccountMasterUploadDto : bankAccountMasterUploadDtosUploadList) {
                    bankAccountMasterUploadDto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                    bankAccountMasterUploadDto.setLangId(Long.valueOf(langId));
                    bankAccountMasterUploadDto.setUserId(userId);
                    bankAccountMasterUploadDto.setLmoddate(new Date());
                    bankAccountMasterUploadDto.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
                    bankAccountMasterUploadDto.setAccStatus(accountStatus);
                    bankAccountService.saveBankAccountMasterExcelData(bankAccountMasterUploadDto, defaultOrg, org, userId, langId,
                            accountStatus);
                }

                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        session.getMessage("accounts.success.excel"));
            }
        }
        model.addAttribute(BindingResult.MODEL_KEY_PREFIX + ENTITY, bindingResult);
        populateModel(model, bean, MODE_CREATE);
        model.addAttribute(ENTITY, bean);
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
}
