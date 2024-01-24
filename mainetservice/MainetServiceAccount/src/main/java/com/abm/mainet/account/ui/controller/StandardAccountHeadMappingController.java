package com.abm.mainet.account.ui.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.dto.StandardAccountHeadDto;
import com.abm.mainet.account.service.StandardAccountHeadMappingService;
import com.abm.mainet.account.ui.model.response.StandardAccountResponse;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.MASTER;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.service.AccountHeadPrimaryAccountCodeMasterService;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 * Spring MVC controller for 'StandardAccountHeadMappping' management.
 */
@Controller
@RequestMapping("StandardAccountHeadMappping.html")
public class StandardAccountHeadMappingController extends AbstractController {

    @Resource
    private StandardAccountHeadMappingService headMappingService;

    @Autowired
    private AccountHeadPrimaryAccountCodeMasterService tbAcPrimaryheadMasterService;

    @Resource
    private TbOrganisationService tbOrganisationService;

    @Resource
    private TbAcCodingstructureMasService tbAcCodingstructureMasService;

    private static final String JSP_FORM = "standardHeadMapping/form";

    private static final String JSP_LIST = "standardHeadMapping/list";
    private static final String JSP_VIEW = "standardHeadMapping/view";

    private static final String SAVE_ACTION_CREATE = "StandardAccountHeadMappping.html?create";

    private static final String SAVE_ACTION_UPDATE = "StandardAccountHeadMappping.html?update";

    private static List<StandardAccountHeadDto> accountlist = new ArrayList<>();
    private static final String ENTITY = "standardAccountHeadDto";

    // --------------------------------------------------------------------------------------
    /**
     * Default constructor
     */
    public StandardAccountHeadMappingController() {
        super(StandardAccountHeadMappingController.class, ENTITY);
    }

    /**
     * Populates the Spring MVC model with the given entity and eventually other useful data
     * @param model
     * @param standardAccountHeadDto
     * @throws Exception
     */
    private void populateModel(final Model model, final StandardAccountHeadDto standardAccountHeadDto, final String formMode)
            throws Exception {
        accountlist.clear();

        boolean primaryDefaultFlag = false;
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);

        if (isDafaultOrgExist) {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        Organisation defaultorg1 = null;
        if (isDafaultOrgExist && primaryDefaultFlag) {
            defaultorg1 = ApplicationSession.getInstance().getSuperUserOrganization();
        } else if (isDafaultOrgExist && (primaryDefaultFlag == false)) {
            defaultorg1 = UserSession.getCurrent().getOrganisation();
        } else {
            defaultorg1 = UserSession.getCurrent().getOrganisation();
        }

        final List<LookUp> investmentType = CommonMasterUtility.getListLookup(MainetConstants.StandardAccountHeadMapping.IVT,
                defaultorg1);
        final List<LookUp> advanceType = CommonMasterUtility.getListLookup(MainetConstants.AdvanceEntry.ATY, defaultorg1);
        final List<LookUp> asset = CommonMasterUtility.getListLookup(MainetConstants.StandardAccountHeadMapping.AST, defaultorg1);
        final List<LookUp> loans = CommonMasterUtility.getListLookup(MainetConstants.StandardAccountHeadMapping.LNT, defaultorg1);
        final List<LookUp> statutoryDeductions = CommonMasterUtility.getListLookup(MainetConstants.StandardAccountHeadMapping.TDS,
                defaultorg1);

        final List<LookUp> accountType = CommonMasterUtility.getListLookup(AccountPrefix.SAM.name(), defaultorg1);
        model.addAttribute(MainetConstants.BANK_MASTER.ACCOUNT_TYPE, accountType);
        final List<LookUp> payMode = CommonMasterUtility.getListLookup(AccountPrefix.PAY.name(), defaultorg1);
        model.addAttribute(MainetConstants.StandardAccountHeadMapping.PAY_MODE, payMode);
        final List<LookUp> bankType = CommonMasterUtility.getListLookup(PrefixConstants.BAT, defaultorg1);
        model.addAttribute(MainetConstants.BANK_MASTER.BANK_TYPE, bankType);
        final List<LookUp> bankAccountIntType = CommonMasterUtility.getListLookup(PrefixConstants.ITP, defaultorg1);
        model.addAttribute(MainetConstants.BANK_MASTER.BANK_ACCOUNT_INT_TYPE, bankAccountIntType);
        final List<LookUp> status = CommonMasterUtility.getListLookup(AccountPrefix.ACN.name(), defaultorg1);
        model.addAttribute(MainetConstants.REQUIRED_PG_PARAM.STATUS, status);
        if (formMode.equals(MainetConstants.StandardAccountHeadMapping.UPDATE)) {
            final int langId = UserSession.getCurrent().getLanguageId();

            if (isDafaultOrgExist) {
                primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                        ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
            } else {
                primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                        UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
            }
            Organisation defaultorg = null;
            if (isDafaultOrgExist && primaryDefaultFlag) {
                ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
                defaultorg = ApplicationSession.getInstance().getSuperUserOrganization();

                if ((UserSession.getCurrent().getOrganisation().getOrgid()) == (ApplicationSession.getInstance()
                        .getSuperUserOrganization().getOrgid())) {
                    model.addAttribute(MainetConstants.FIELD_MASTER.PARENT_FLAG_STATUS, MainetConstants.MASTER.Y);
                } else {
                    model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.N);
                }
            } else if (isDafaultOrgExist && (primaryDefaultFlag == false)) {
                UserSession.getCurrent().getOrganisation().getOrgid();
                defaultorg = UserSession.getCurrent().getOrganisation();
                if ((UserSession.getCurrent().getOrganisation().getOrgid()) == (ApplicationSession.getInstance()
                        .getSuperUserOrganization().getOrgid())) {
                    model.addAttribute(MainetConstants.FIELD_MASTER.PARENT_FLAG_STATUS, MainetConstants.MASTER.Y);
                } else {
                    model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.Y);
                }
            } else {
                UserSession.getCurrent().getOrganisation().getOrgid();
                defaultorg = UserSession.getCurrent().getOrganisation();
                model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.Y);
            }

            final Map<Long, String> primaryHead = tbAcPrimaryheadMasterService.getPrimaryHeadCodeStatusLastLevels(defaultorg,
                    langId);
            model.addAttribute(MainetConstants.StandardAccountHeadMapping.PRIMARY_HEAD, primaryHead);
        }
        if (formMode.equals(MainetConstants.VIEW)) {

            if (isDafaultOrgExist) {
                primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                        ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
            } else {
                primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                        UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
            }
            Long defaultOrgId = null;
            if (isDafaultOrgExist && primaryDefaultFlag) {
                defaultOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
            } else if (isDafaultOrgExist && (primaryDefaultFlag == false)) {
                defaultOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
            } else {
                defaultOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
            }
            final Map<Long, String> primaryHead = tbAcPrimaryheadMasterService.getPrimaryHeadCodeLastLevels(defaultOrgId);
            model.addAttribute(MainetConstants.StandardAccountHeadMapping.PRIMARY_HEAD, primaryHead);
        }
        model.addAttribute(ENTITY, standardAccountHeadDto);
        final List<LookUp> vendorType = CommonMasterUtility.getListLookup(MainetConstants.StandardAccountHeadMapping.VNT,
                defaultorg1);
        model.addAttribute(MainetConstants.StandardAccountHeadMapping.VENDOR_TYPE, vendorType);
        final List<LookUp> depositType = CommonMasterUtility.getListLookup(MainetConstants.StandardAccountHeadMapping.DTY,
                defaultorg1);
        model.addAttribute(MainetConstants.StandardAccountHeadMapping.DEPOSIT_TYPE, depositType);
        model.addAttribute(MainetConstants.StandardAccountHeadMapping.INVEST_TYPE, investmentType);
        model.addAttribute(MainetConstants.StandardAccountHeadMapping.ADVANCE_TYPE, advanceType);
        model.addAttribute(MainetConstants.StandardAccountHeadMapping.ASSET, asset);
        model.addAttribute(MainetConstants.StandardAccountHeadMapping.LOAN, loans);
        model.addAttribute(MainetConstants.StandardAccountHeadMapping.STATUTORY_DEDUCTION, statutoryDeductions);

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
     * Shows a list with all the occurrences of StandardAccountHeadMappping found in the database
     * @param model Spring MVC model
     * @return
     * @throws Exception
     */
    @RequestMapping()
    public String list(final Model model) throws Exception {
        String result = StringUtils.EMPTY;

        boolean primaryDefaultFlag = false;
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);

        if (isDafaultOrgExist) {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }

        if (isDafaultOrgExist && primaryDefaultFlag) {
            if ((UserSession.getCurrent().getOrganisation().getOrgid()) == (ApplicationSession.getInstance()
                    .getSuperUserOrganization().getOrgid())) {
                model.addAttribute(MainetConstants.FIELD_MASTER.PARENT_FLAG_STATUS, MainetConstants.MASTER.Y);
            } else {
                model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.N);
            }
        } else if (isDafaultOrgExist && (primaryDefaultFlag == false)) {
            if ((UserSession.getCurrent().getOrganisation().getOrgid()) == (ApplicationSession.getInstance()
                    .getSuperUserOrganization().getOrgid())) {
                model.addAttribute(MainetConstants.FIELD_MASTER.PARENT_FLAG_STATUS, MainetConstants.MASTER.Y);
            } else {
                model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.Y);
            }
        } else {
            model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.Y);
        }

        final StandardAccountHeadDto standardDto = new StandardAccountHeadDto();
        populateModel(model, standardDto, MODE_CREATE);
        result = JSP_LIST;
        return result;
    }

    @RequestMapping(params = "getGridData")
    public @ResponseBody StandardAccountResponse gridData(final HttpServletRequest request, final Model model) {

        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        final StandardAccountResponse response = new StandardAccountResponse();
        response.setRows(accountlist);
        response.setTotal(accountlist.size());
        response.setRecords(accountlist.size());
        response.setPage(page);
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, accountlist);
        return response;
    }

    @RequestMapping(params = "searchAccountType")
    public @ResponseBody List getAccountData(@RequestParam("accountType") final Long accountType,
            @RequestParam("accountSubType") final Long accountSubType, final HttpServletRequest request, final Model model) {
        accountlist.clear();

        boolean primaryDefaultFlag = false;
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);

        if (isDafaultOrgExist) {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        Organisation defaultorg = null;
        if (isDafaultOrgExist && primaryDefaultFlag) {
            defaultorg = ApplicationSession.getInstance().getSuperUserOrganization();
        } else if (isDafaultOrgExist && (primaryDefaultFlag == false)) {
            defaultorg = UserSession.getCurrent().getOrganisation();
        } else {
            defaultorg = UserSession.getCurrent().getOrganisation();
        }
        final List<StandardAccountHeadDto> accountDetails = headMappingService.getAccountHead(accountType, accountSubType,
                defaultorg);
        if (accountDetails != null) {
            for (final StandardAccountHeadDto standardAccountHeadDto : accountDetails) {
                if (isDafaultOrgExist && primaryDefaultFlag) {
                    if ((UserSession.getCurrent().getOrganisation().getOrgid()) == (ApplicationSession.getInstance()
                            .getSuperUserOrganization().getOrgid())) {
                        standardAccountHeadDto.setDefaultOrgFlag(MainetConstants.MASTER.Y);
                    }
                } else {
                    standardAccountHeadDto.setDefaultOrgFlag(MainetConstants.MASTER.Y);
                }
            }
            accountlist.addAll(accountDetails);
        }
        return accountlist;
    }

    /**
     * Shows a form page in order to create a new StandardAccountHeadMappping
     * @param model Spring MVC model
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "form")
    public String formForCreate(final Model model) throws Exception {
        final StandardAccountHeadDto standardHeadDto = new StandardAccountHeadDto();
        standardHeadDto.setMode(MainetConstants.StandardAccountHeadMapping.CREATE);
        populateModel(model, standardHeadDto, MODE_CREATE);
        return JSP_FORM;
    }

    /**
     * Shows a form page in order to update an existing Bank Account
     * @param model Spring MVC model
     * @param baAccountId primary key element
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "formForUpdate")
    public String formForUpdate(final Model model, @RequestParam("accountType") final String accountType,
            @RequestParam("primaryHeadId") final String primaryHeadId, @RequestParam("payMode") final String payMode,
            @RequestParam("status") final String status, @RequestParam(MainetConstants.MODE) final String viewmode)
            throws Exception {
        String result = StringUtils.EMPTY;
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final int langId = UserSession.getCurrent().getLanguageId();
        StandardAccountHeadDto dtoWithList = new StandardAccountHeadDto();
        Long primaryHedId = null;
        if ((primaryHeadId != null) && !primaryHeadId.isEmpty()) {
            primaryHedId = Long.valueOf(primaryHeadId);
        }
        dtoWithList = headMappingService.getDetailsUsingprimaryHeadId(primaryHedId, organisation, langId);
        populateModel(model, dtoWithList, viewmode);
        result = JSP_FORM;
        return result;
    }

    /**
     * Shows a form page in order to update an existing Bank Account
     * @param model Spring MVC model
     * @param baAccountId primary key element
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "formForView")
    public String formForView(final Model model, @RequestParam("accountType") final String accountType,
            @RequestParam("primaryHeadId") final String primaryHeadId, @RequestParam("payMode") final String payMode,
            @RequestParam("status") final String status, @RequestParam(MainetConstants.MODE) final String viewmode)
            throws Exception {
        String result = StringUtils.EMPTY;
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final int langId = UserSession.getCurrent().getLanguageId();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        StandardAccountHeadDto dto = new StandardAccountHeadDto();
        Long primaryHedId = null;
        if ((primaryHeadId != null) && !primaryHeadId.isEmpty()) {
            primaryHedId = Long.valueOf(primaryHeadId);
        }
        dto = headMappingService.getDetailsUsingprimaryHeadId(primaryHedId, organisation, langId);
        dto.setViewAccountTypeDesc(
                CommonMasterUtility.findLookUpDesc(AccountPrefix.SAM.name().toString(), orgId, Long.valueOf(accountType)));
        if (!status.equals(MainetConstants.StandardAccountHeadMapping.NULL)) {
            dto.setStatusDescription(
                    CommonMasterUtility.findLookUpDesc(AccountPrefix.ACN.name().toString(), orgId, Long.valueOf(status)));
        }
        dto.setPrimaryHeadId(Long.valueOf(primaryHeadId));
        populateModel(model, dto, viewmode);
        result = JSP_VIEW;
        return result;
    }

    /**
     * 'CREATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * @param tbBankmaster entity to be created
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "create") // GET or POST
    public String createMapping(@Valid final StandardAccountHeadDto mappingDto, final BindingResult bindingResult,
            final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) {
        String result = StringUtils.EMPTY;
        if (!bindingResult.hasErrors()) {
            final Long accountType = mappingDto.getAccountType();
            final List<LookUp> acountType = CommonMasterUtility.getListLookup(AccountPrefix.SAM.name(),
                    UserSession.getCurrent().getOrganisation());
            for (final LookUp lookUp : acountType) {
                if (lookUp.getLookUpId() == accountType) {
                    if (lookUp.getLookUpCode().equals(MainetConstants.CHEQUE_DISHONOUR.PAY)) {
                        mappingDto.setAccountSubType(mappingDto.getPayMode());
                    }
                }
                if (lookUp.getLookUpId() == accountType) {
                    if (lookUp.getLookUpCode().equals(MainetConstants.MASTER.BK)) {
                        mappingDto.setAccountSubType(mappingDto.getBankType());
                    }
                }
                if (lookUp.getLookUpId() == accountType) {
                    if (lookUp.getLookUpCode().equals(MainetConstants.StandardAccountHeadMapping.VN)) {
                        mappingDto.setAccountSubType(mappingDto.getVendorType());
                    }
                }
                if (lookUp.getLookUpId() == accountType) {
                    if (lookUp.getLookUpCode().equals(MainetConstants.StandardAccountHeadMapping.IN)) {
                        mappingDto.setAccountSubType(mappingDto.getInvestmentType());
                    }
                }
                if (lookUp.getLookUpId() == accountType) {
                    if (lookUp.getLookUpCode().equals(MainetConstants.AccountDeposit.DP)) {
                        mappingDto.setAccountSubType(mappingDto.getDepositType());
                    }
                }
                if (lookUp.getLookUpId() == accountType) {
                    if (lookUp.getLookUpCode().equals(MainetConstants.AccountBillEntry.AD)) {
                        mappingDto.setAccountSubType(mappingDto.getAdvanceType());
                    }
                }
                if (lookUp.getLookUpId() == accountType) {
                    if (lookUp.getLookUpCode().equals(MainetConstants.StandardAccountHeadMapping.AS)) {
                        mappingDto.setAccountSubType(mappingDto.getAsset());
                    }
                }
                if (lookUp.getLookUpId() == accountType) {
                    if (lookUp.getLookUpCode().equals(MainetConstants.StandardAccountHeadMapping.SD)) {
                        mappingDto.setAccountSubType(mappingDto.getStatutoryDeduction());
                    }
                }
                if (lookUp.getLookUpId() == accountType) {
                    if (lookUp.getLookUpCode().equals(MainetConstants.StandardAccountHeadMapping.LO)) {
                        mappingDto.setAccountSubType(mappingDto.getLoans());
                    }
                }
                if (lookUp.getLookUpId() == accountType) {
                    if (lookUp.getLookUpCode().equals("BAI")) {
                        mappingDto.setAccountSubType(mappingDto.getBankAccountIntType());
                    }
                }
            }
            final Long activeStatusId = getDefaultAccountStatus();
            for (final StandardAccountHeadDto dto : mappingDto.getPrimaryHeadMappingList()) {
                if (dto.getStatus() == null) {
                    dto.setStatus(activeStatusId);
                }
            }

            final StandardAccountHeadDto mappingCreated = headMappingService.createMapping(mappingDto);
            mappingCreated.setSuccessFlag(MainetConstants.MASTER.Y);
            model.addAttribute(ENTITY, mappingCreated);
            result = JSP_FORM;
        } else {
            mappingDto.setSuccessFlag(MainetConstants.MASTER.N);
            result = MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW;
        }

        return result;
    }

    @RequestMapping(params = "getPrimaryHeadCodeDesc", method = RequestMethod.POST)
    public String getPrimaryHeadCodeDesc(final StandardAccountHeadDto dto, final Model model,
            final RedirectAttributes redirectAttributes,
            final HttpServletRequest request, final BindingResult bindingResult) throws Exception {
        log("StandardAccountHeadMappingController-'getPrimaryHeadCodeDesc' : 'get PrimaryHeadCodeDesc'");
        String result = MainetConstants.CommonConstant.BLANK;

        boolean primaryDefaultFlag = false;
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);

        if (isDafaultOrgExist) {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        Organisation defaultorg = null;
        if (isDafaultOrgExist && primaryDefaultFlag) {
            defaultorg = ApplicationSession.getInstance().getSuperUserOrganization();
        } else if (isDafaultOrgExist && (primaryDefaultFlag == false)) {
            defaultorg = UserSession.getCurrent().getOrganisation();
        } else {
            defaultorg = UserSession.getCurrent().getOrganisation();
        }
        final int langId = UserSession.getCurrent().getLanguageId();
        final Long acountType = dto.getAccountType();
        final List<LookUp> acountType1 = CommonMasterUtility.getListLookup(AccountPrefix.SAM.name(),
                UserSession.getCurrent().getOrganisation());
        final Map<Long, String> primaryJHead = new LinkedHashMap<>();
        for (final LookUp lookUp : acountType1) {
            if (lookUp.getLookUpId() == acountType) {
                final String acountType2 = lookUp.getLookUpCode();
                final LookUp liabilityLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                        MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.LIABILITY_OTHER_FIELD,
                        PrefixConstants.PREFIX_VALUE_IELA, UserSession.getCurrent().getLanguageId(),
                        UserSession.getCurrent().getOrganisation());
                final LookUp assetLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                        MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.ASSET_OTHER_FIELD,
                        PrefixConstants.PREFIX_VALUE_IELA, UserSession.getCurrent().getLanguageId(),
                        UserSession.getCurrent().getOrganisation());
                final LookUp expeditureLookUp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                        MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.EXPENDITURE_OTHER_FIELD,
                        PrefixConstants.PREFIX_VALUE_IELA, UserSession.getCurrent().getLanguageId(),
                        UserSession.getCurrent().getOrganisation());
                final Map<Long, String> primaryHead = tbAcPrimaryheadMasterService
                        .getPrimaryHeadStatusWiseAndAcTypeSubTypeLastLevels(defaultorg, langId);
                if ((acountType2 != null) && !acountType2.isEmpty()) {
                	
                	 for (final Map.Entry<Long, String> entry : primaryHead.entrySet()) {
                         final Long key = entry.getKey();
                         final String value = entry.getValue();
                             primaryJHead.put(key, value);
                     }

                	
                	
					/*
					 * if (acountType2.equals(MainetConstants.CHEQUE_DISHONOUR.PAY) ||
					 * acountType2.equals(MainetConstants.MASTER.BK) ||
					 * acountType2.equals(MainetConstants.AccountBillEntry.AD) ||
					 * acountType2.equals(MainetConstants.StandardAccountHeadMapping.LO) ||
					 * acountType2.equals(MainetConstants.StandardAccountHeadMapping.AS) ||
					 * acountType2.equals(MainetConstants.StandardAccountHeadMapping.IN) ||
					 * acountType2.equals(MainetConstants.StandardAccountHeadMapping.VN) ||
					 * acountType2.equals(MainetConstants.StandardAccountHeadMapping.ADP)) { for
					 * (final Map.Entry<Long, String> entry : primaryHead.entrySet()) { final Long
					 * key = entry.getKey(); final String value = entry.getValue(); int
					 * strPacCodeValue; strPacCodeValue =
					 * Integer.parseInt(String.valueOf(value.charAt(0))); if
					 * ((Integer.parseInt(assetLookup.getOtherField()) == strPacCodeValue)) {
					 * primaryJHead.put(key, value); } } }
					 * 
					 * if (acountType2.equals(MainetConstants.StandardAccountHeadMapping.VN) ||
					 * acountType2.equals(MainetConstants.AccountDeposit.DP) ||
					 * acountType2.equals(MainetConstants.StandardAccountHeadMapping.SD)) { for
					 * (final Map.Entry<Long, String> entry : primaryHead.entrySet()) { final Long
					 * key = entry.getKey(); final String value = entry.getValue(); int
					 * strPacCodeValue; strPacCodeValue =
					 * Integer.parseInt(String.valueOf(value.charAt(0))); if
					 * ((Integer.parseInt(liabilityLookup.getOtherField()) == strPacCodeValue)) {
					 * primaryJHead.put(key, value); } } } if
					 * (acountType2.equals(MainetConstants.StandardAccountHeadMapping.FAD)) { for
					 * (final Map.Entry<Long, String> entry : primaryHead.entrySet()) { final Long
					 * key = entry.getKey(); final String value = entry.getValue(); int
					 * strPacCodeValue; strPacCodeValue =
					 * Integer.parseInt(String.valueOf(value.charAt(0))); if
					 * ((Integer.parseInt(expeditureLookUp.getOtherField()) == strPacCodeValue)) {
					 * primaryJHead.put(key, value); } }
					 }*/
                }
            }
        }
        model.addAttribute(MainetConstants.StandardAccountHeadMapping.PRIMARY_HEAD, primaryJHead);
        final String viewmode = MainetConstants.Actions.CREATE;
        populateModel(model, dto, viewmode);
        model.addAttribute(ENTITY, dto);
        result = JSP_FORM;
        return result;
    }

    @RequestMapping(params = "validateAccountCodeDesc", method = RequestMethod.POST)
    public @ResponseBody boolean validateAccountCode(final Model model, @RequestParam("primaryHeadId") final Long primaryHeadId,
            @RequestParam("accountType") final Long accountType, @RequestParam("accountSubType") final Long accountSubType) {
        log("Action 'validate account code'");
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final int langId = UserSession.getCurrent().getLanguageId();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.IsLookUp.ACTIVE,
                PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX, langId, organisation);
        final Long lookUpStatusId = statusLookup.getLookUpId();
        final boolean isDuplicate = headMappingService.findAccountSubType(primaryHeadId, accountType, accountSubType,
                lookUpStatusId,
                orgId);
        return isDuplicate;
    }

    @RequestMapping(params = "checkingStatusPaymode", method = RequestMethod.POST)
    public @ResponseBody boolean checkingStatusPaymode(final Model model, @RequestParam("accountType") final Long accountType,
            @RequestParam("payModeId") final Long payModeId) {
        log("Action 'checking Status in Paymode'");
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final int langId = UserSession.getCurrent().getLanguageId();
        final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.IsLookUp.ACTIVE,
                PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX, langId, organisation);
        final Long lookUpStatusId = statusLookup.getLookUpId();
        final boolean isDuplicate = headMappingService.findStatusPaymode(accountType, payModeId, lookUpStatusId, orgId);
        return isDuplicate;
    }

    /**
     * 'CREATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * @param tbBankmaster entity to be created
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "update") // GET or POST
    public String update(@Valid final StandardAccountHeadDto accountDto, final BindingResult bindingResult, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) {
        String result = StringUtils.EMPTY;
        if (!bindingResult.hasErrors()) {
            final StandardAccountHeadDto mappingCreated = headMappingService.updateMapping(accountDto);
            mappingCreated.setSuccessFlag(MASTER.U);
            model.addAttribute(ENTITY, mappingCreated);
            result = JSP_FORM;
        } else {
            accountDto.setSuccessFlag(MainetConstants.MASTER.N);
            result = MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW;
        }

        return result;
    }

    public Long getDefaultAccountStatus() {
        final List<LookUp> accountStatus = CommonMasterUtility.getListLookup(AccountPrefix.ACN.name(),
                UserSession.getCurrent().getOrganisation());
        Long activeStatusId = 0l;

        for (final LookUp lkUpObj : accountStatus) {
            if (lkUpObj.getLookUpCode().equalsIgnoreCase(MainetConstants.MASTER.A)) {
                activeStatusId = lkUpObj.getLookUpId();
                break;
            }
        }
        return activeStatusId;
    }
}
