
package com.abm.mainet.account.ui.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abm.mainet.account.dto.BudgetHeadDTO;
import com.abm.mainet.account.dto.VoucherTemplateDTO;
import com.abm.mainet.account.service.BudgetCodeService;
import com.abm.mainet.account.service.BudgetHeadProvider;
import com.abm.mainet.account.service.VoucherTemplateService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author Vivek.Kumar
 * @since 13-JAN-2017
 */
@Controller
@RequestMapping("/VoucherTemplate.html")
public class VoucherTemplateController {

    private static final String VOUCHER_TEMPLATE_LIST_JSP = "voucherTemplate/list";
    private static final String VOUCHER_TEMPLATE_CREATE_JSP = "voucherTemplate/create";
    private static final String VOUCHER_TEMPLATE_ERROR_JSP = "voucherTemplateError/list";

    @Resource
    private VoucherTemplateService voucherTemplateService;
    @Resource
    private BudgetHeadProvider budgetHeadProvider;
    @Resource
    private BudgetCodeService accountBudgetCodeService;
    @Resource
    private SecondaryheadMasterService tbAcSecondaryheadMasterService;
    @Resource
    private TbOrganisationService tbOrganisationService;
    @Resource
    private TbAcCodingstructureMasService tbAcCodingstructureMasService;

    private static final Logger LOGGER = Logger.getLogger(VoucherTemplateController.class);

    /**
     * render grid home page
     * @return
     */
    @RequestMapping()
    public String gridPage(final HttpServletRequest request, final ModelMap model) {

        request.getSession().setAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.GRID_DATA, null);
        model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.COMMAND, model);
        model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.FORM_DTO, new VoucherTemplateDTO());
        final VoucherTemplateDTO cachedData = (VoucherTemplateDTO) request.getSession()
                .getAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.VOUCHER_TEMPLATE);
        String viewName = StringUtils.EMPTY;
        try {
            if (cachedData == null) {
                final VoucherTemplateDTO templatedto = voucherTemplateService
                        .populateModel(UserSession.getCurrent().getOrganisation().getOrgid());
                if (UserSession.getCurrent().getFinYearId() == null) {
                    model.addAttribute("curFinYearIdExistFlag", MainetConstants.Y_FLAG);
                } else {
                    templatedto.setCurrentFYearId(Long.parseLong(UserSession.getCurrent().getFinYearId()));
                }
                model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.DTO, templatedto);
                request.getSession().setAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.VOUCHER_TEMPLATE, templatedto);
            } else {
                final List<VoucherTemplateDTO> list = voucherTemplateService
                        .searchByAllTemplateData(UserSession.getCurrent().getOrganisation().getOrgid());
                request.getSession().setAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.GRID_DATA, list);
                model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.DTO, cachedData);
            }
            model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.FORM_DTO, new VoucherTemplateDTO());
            viewName = VOUCHER_TEMPLATE_LIST_JSP;
        } catch (final Exception ex) {
            throw new FrameworkException(ex.getMessage());
        }
        return viewName;
    }

    /**
     *
     * @param request
     * @param rows
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "getGridData", produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(final HttpServletRequest request,
            @RequestParam final String rows) {
        final JQGridResponse response = new JQGridResponse<>();
        try {
            List<VoucherTemplateDTO> templateDTOs = new ArrayList<>();
            final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
            if (request.getSession().getAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.GRID_DATA) != null) {
                templateDTOs = (List<VoucherTemplateDTO>) request.getSession()
                        .getAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.GRID_DATA);
            }
            response.setRows(templateDTOs);
            response.setTotal(templateDTOs.size());
            response.setRecords(templateDTOs.size());
            response.setPage(page);
        } catch (final Exception ex) {
            LOGGER.error("Error occurred", ex);
        }
        return response;
    }

    /*
     * findVoucherDuplicateCombination() is to find the voucher duplicate combination files in static area
     */
    @RequestMapping(params = "getVoucherDuplicateData", method = RequestMethod.POST)
    public @ResponseBody boolean findVoucherDuplicateCombination(final VoucherTemplateDTO templateType,
            final HttpServletRequest request,
            final Model model, final BindingResult bindingResult) {
        boolean isValidationError = false;
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        try {
            if (voucherTemplateService.isTemplateExist(templateType, orgId)) {
                bindingResult.addError(
                        new org.springframework.validation.FieldError(MainetConstants.VOUCHER_TEMPLATE_ENTRY.VOUCHER_TEMPLATE,
                                MainetConstants.CommonConstant.BLANK, null, false, new String[] {
                                        MainetConstants.ERRORS },
                                null, ApplicationSession.getInstance().getMessage(MainetConstants.CommonConstant.BLANK)));
                isValidationError = true;
            }
        } catch (final Exception ex) {
            LOGGER.error("Error while getVoucherDuplicateData select", ex);
        }
        return isValidationError;
    }

    /*
     * getVoucherPacSacHeadData() is to find the voucher Primary Secondary Head All Required Data Based on Prefix And OT.
     */
    @RequestMapping(params = "getVoucherPacSacHeadData", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> getVoucherPacSacHeadData(final VoucherTemplateDTO templateType,
            final HttpServletRequest request,
            final Model model, @RequestParam("cnt") final int cnt) {

        final int langId = UserSession.getCurrent().getLanguageId();
        final LookUp incomeLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.INCOME_OTHER_FIELD,
                PrefixConstants.PREFIX_VALUE_IELA, UserSession.getCurrent().getLanguageId(),
                UserSession.getCurrent().getOrganisation());
        final LookUp expenditureLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.EXPENDITURE_OTHER_FIELD,
                PrefixConstants.PREFIX_VALUE_IELA, UserSession.getCurrent().getLanguageId(),
                UserSession.getCurrent().getOrganisation());
        final LookUp liabilityLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.LIABILITY_OTHER_FIELD,
                PrefixConstants.PREFIX_VALUE_IELA, UserSession.getCurrent().getLanguageId(),
                UserSession.getCurrent().getOrganisation());
        final LookUp assetLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.ASSET_OTHER_FIELD,
                PrefixConstants.PREFIX_VALUE_IELA, UserSession.getCurrent().getLanguageId(),
                UserSession.getCurrent().getOrganisation());

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

        final LookUp oTLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ACCOUNT_MASTERS.SECONDARY_MASTER.SECONDARY_OTHER_CPD_VALUE, PrefixConstants.SECONDARY_LOOKUPCODE,
                langId, defaultorg);
        final LookUp cALookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.VoucherTemplate.CA,
                PrefixConstants.SECONDARY_LOOKUPCODE, langId, defaultorg);

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long accountType = templateType.getMappingDetails().get(cnt).getAccountType();
        final Long statusId = getActiveStatusId();
        final Map<Long, String> pacSacHeadMap = new LinkedHashMap<>();

        final List<Object[]> accountHeadList = tbAcSecondaryheadMasterService.findSacHeadIdDescAllData(orgId, statusId);

        if ((accountType != null) && (accountType != 0)) {
            final Long lookUpIdI = incomeLookup.getLookUpId();
            final Long lookUpOTId = oTLookup.getLookUpId();
            final Long lookUpCAId = cALookup.getLookUpId();
            if (lookUpIdI.equals(accountType)) {
                final String otherFieldI = incomeLookup.getOtherField();
                for (final Object[] objects : accountHeadList) {
                    int strPacCodeValue;
                    strPacCodeValue = Integer.parseInt(String.valueOf(objects[2].toString().charAt(0)));
                    Long statusOT = null;
                    if (objects[3] != null) {
                        statusOT = Long.valueOf(objects[3].toString());
                        if (statusOT.equals(lookUpOTId) || statusOT.equals(lookUpCAId)) {
                            if ((otherFieldI != null) && !otherFieldI.isEmpty()) {
                                if ((Integer.parseInt(otherFieldI) == strPacCodeValue)) {
                                    if ((objects[0] != null) && ((objects[1] != null) && !objects[1].toString().isEmpty())) {
                                        pacSacHeadMap.put((Long) objects[0], objects[1].toString());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            final Long lookUpIdE = expenditureLookup.getLookUpId();
            if (lookUpIdE.equals(accountType)) {
                final String otherFieldE = expenditureLookup.getOtherField();
                for (final Object[] objects : accountHeadList) {
                    int strPacCodeValue;
                    strPacCodeValue = Integer.parseInt(String.valueOf(objects[2].toString().charAt(0)));
                    Long statusOT = null;
                    if (objects[3] != null) {
                        statusOT = Long.valueOf(objects[3].toString());
                        if (statusOT.equals(lookUpOTId) || statusOT.equals(lookUpCAId)) {
                            if ((otherFieldE != null) && !otherFieldE.isEmpty()) {
                                if ((Integer.parseInt(otherFieldE) == strPacCodeValue)) {
                                    if ((objects[0] != null) && ((objects[1] != null) && !objects[1].toString().isEmpty())) {
                                        pacSacHeadMap.put((Long) objects[0], objects[1].toString());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            final Long lookUpIdL = liabilityLookup.getLookUpId();
            if (lookUpIdL.equals(accountType)) {
                final String otherFieldL = liabilityLookup.getOtherField();
                for (final Object[] objects : accountHeadList) {
                    int strPacCodeValue;
                    strPacCodeValue = Integer.parseInt(String.valueOf(objects[2].toString().charAt(0)));
                    Long statusOT = null;
                    if (objects[3] != null) {
                        statusOT = Long.valueOf(objects[3].toString());
                        if (statusOT.equals(lookUpOTId) || statusOT.equals(lookUpCAId)) {
                            if ((otherFieldL != null) && !otherFieldL.isEmpty()) {
                                if ((Integer.parseInt(otherFieldL) == strPacCodeValue)) {
                                    if ((objects[0] != null) && ((objects[1] != null) && !objects[1].toString().isEmpty())) {
                                        pacSacHeadMap.put((Long) objects[0], objects[1].toString());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            final Long lookUpIdA = assetLookup.getLookUpId();
            if (lookUpIdA.equals(accountType)) {
                final String otherFieldA = assetLookup.getOtherField();
                for (final Object[] objects : accountHeadList) {
                    int strPacCodeValue;
                    strPacCodeValue = Integer.parseInt(String.valueOf(objects[2].toString().charAt(0)));
                    Long statusOT = null;
                    if (objects[3] != null) {
                        statusOT = Long.valueOf(objects[3].toString());
                        if (statusOT.equals(lookUpOTId) || statusOT.equals(lookUpCAId)) {
                            if ((otherFieldA != null) && !otherFieldA.isEmpty()) {
                                if ((Integer.parseInt(otherFieldA) == strPacCodeValue)) {
                                    if ((objects[0] != null) && ((objects[1] != null) && !objects[1].toString().isEmpty())) {
                                        pacSacHeadMap.put((Long) objects[0], objects[1].toString());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return pacSacHeadMap;
    }

    /**
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(params = "createPage", method = RequestMethod.GET)
    public String openVoucherTemplatePage(final HttpServletRequest request, final ModelMap model) {

        model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.COMMAND, model);
        final VoucherTemplateDTO cachedData = (VoucherTemplateDTO) request.getSession()
                .getAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.VOUCHER_TEMPLATE);
        String viewName = StringUtils.EMPTY;
        try {
            if (cachedData == null) {
                final VoucherTemplateDTO dto = voucherTemplateService
                        .populateModel(UserSession.getCurrent().getOrganisation().getOrgid());
                model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.DTO, dto);
                request.getSession().setAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.VOUCHER_TEMPLATE, dto);

            } else {
                model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.DTO, cachedData);
            }
            model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.NO_DATA_FOUND, MainetConstants.MENU.N);
            model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.FORM_DTO, new VoucherTemplateDTO());
            model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.MODE_FLAG, MainetConstants.MENU.A);
            viewName = VOUCHER_TEMPLATE_CREATE_JSP;
        } catch (final Exception ex) {
            final String msg = ex.getMessage();
            model.get(MainetConstants.VOUCHER_TEMPLATE_ENTRY.NO_DATA_FOUND);
            model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.NO_DATA_FOUND, msg);
            model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.FORM_DTO, new VoucherTemplateDTO());
            viewName = VOUCHER_TEMPLATE_ERROR_JSP;
            LOGGER.error("Error while populateModel:", ex);
        }
        return viewName;
    }

    /**
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(params = "saveForm", method = RequestMethod.POST)
    public @ResponseBody String saveForm(@Valid final VoucherTemplateDTO voucherTemplate, final BindingResult bindingResult,
            final HttpServletRequest request, final ModelMap model) {

        final ApplicationSession session = ApplicationSession.getInstance();
        model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.COMMAND, model);
        String successMessage = StringUtils.EMPTY;
        try {
            final long voucherTempId = voucherTemplate.getTemplateId();
            voucherTemplate.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            voucherTemplate.setLangId((long) UserSession.getCurrent().getLanguageId());
            voucherTemplate.setIpMacAddress(Utility.getClientIpAddress(request));
            final UserSession userSession = UserSession.getCurrent();
            if ((Long) voucherTemplate.getTemplateId() != null && (Long) voucherTemplate.getTemplateId() > 0) {
                voucherTemplate.setUpdatedBy(userSession.getEmployee().getEmpId());
                voucherTemplate.setUpdatedDate(new Date());
                voucherTemplate.setLgIpMacUpd(Utility.getClientIpAddress(request));
            }

            if (voucherTemplate.getStatus() == null) {
                voucherTemplate.setStatus(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.STATUS_ACTIVE_PREFIX,
                        PrefixConstants.STATUS_PREFIX, UserSession.getCurrent().getOrganisation().getOrgid()));
            }

            if (voucherTemplateService.createVoucherTemplate(voucherTemplate,
                    UserSession.getCurrent().getOrganisation().getOrgid())) {
                if (Long.valueOf(voucherTempId) == 0) {
                    successMessage = session.getMessage("accounts.fieldmaster.success");
                }
                if (Long.valueOf(voucherTempId) != 0) {
                    successMessage = session.getMessage("accounts.fieldmaster.update");
                }
            } else {
                successMessage = session.getMessage("voucher.template.success");
            }
        } catch (final Exception ex) {
            LOGGER.error("Error while populateModel:", ex);
            successMessage = session.getMessage("voucher.template.search.error");
        }
        return successMessage;
    }

    /**
     *
     * @param mode
     * @param request
     * @param model
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "onModeSelect", method = RequestMethod.POST)
    public @ResponseBody List<BudgetHeadDTO> budgetCodeOnModeSelect(@RequestParam("mode") final long mode,
            final HttpServletRequest request, final ModelMap model) {
        List<BudgetHeadDTO> list = Collections.EMPTY_LIST;
        try {
            list = budgetHeadProvider.findBudgetHeadsByMode(mode, UserSession.getCurrent().getOrganisation().getOrgid());
        } catch (final Exception ex) {
            LOGGER.error("Error while Mode select", ex);
        }
        return list;
    }

    /**
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(params = "errorPage", method = RequestMethod.GET)
    public String renderErrorPage(final HttpServletRequest request, final ModelMap model) {

        return "defaultExceptionFormView";
    }

    /**
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(params = "searchTemplate", method = RequestMethod.GET)
    public @ResponseBody String searchTemplateList(@Valid final VoucherTemplateDTO dto, final BindingResult bindingResult,
            final HttpServletRequest request, final ModelMap model) {
        String message = StringUtils.EMPTY;
        final ApplicationSession session = ApplicationSession.getInstance();
        try {
            final List<VoucherTemplateDTO> list = voucherTemplateService.searchTemplate(dto,
                    UserSession.getCurrent().getOrganisation().getOrgid());
            if (list.isEmpty()) {
                message = session.getMessage("voucher.template.search.message");
            }
            request.getSession().setAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.GRID_DATA, list);
        } catch (final Exception ex) {
            message = session.getMessage("voucher.template.search.error");
            LOGGER.error("Error while searching template", ex);
        }
        return message;
    }

    /**
     *
     * @param gridId
     * @param type
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(params = "view", method = RequestMethod.GET)
    public String viewTemplateForm(@RequestParam("gridId") final long gridId, final HttpServletRequest request,
            final ModelMap model) {
        String page = StringUtils.EMPTY;
        VoucherTemplateDTO viewData = null;
        try {
            viewData = voucherTemplateService.viewTemplate(gridId);
            model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.VIEW_DATA, viewData);
            page = MainetConstants.VOUCHER_TEMPLATE_ENTRY.VIEW_PAGE;
            model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.MODE_FLAG, MainetConstants.MODE_VIEW);
        } catch (final Exception ex) {
            LOGGER.error("Error while view Template", ex);
        }
        return page;
    }

    /**
     *
     * @param gridId
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(params = "edit", method = RequestMethod.GET)
    public String editTemplateForm(@RequestParam("gridId") final long gridId, final HttpServletRequest request,
            final ModelMap model) {
        String page = StringUtils.EMPTY;
        VoucherTemplateDTO editData = null;
        final List<LookUp> lookUps = CommonMasterUtility.getListLookup(
                MainetConstants.VOUCHER_TEMPLATE_ENTRY.TEMPLATE_TYPE_PREFIX,
                UserSession.getCurrent().getOrganisation());
        try {
            editData = voucherTemplateService.editTemplate(gridId);
            final Long templatetype = editData.getTemplateType();
            Long voucherLookUpId = null;
            for (final LookUp lookUp : lookUps) {
                voucherLookUpId = lookUp.getLookUpId();
                if (voucherLookUpId.equals(templatetype)) {
                    editData.setTemplateTypeCode(lookUp.getLookUpCode());
                }
            }
            UserSession.getCurrent().getOrganisation();
            final int langId = UserSession.getCurrent().getLanguageId();
            final LookUp incomeLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.INCOME_OTHER_FIELD,
                    PrefixConstants.PREFIX_VALUE_IELA, UserSession.getCurrent().getLanguageId(),
                    UserSession.getCurrent().getOrganisation());
            final LookUp expenditureLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.EXPENDITURE_OTHER_FIELD,
                    PrefixConstants.PREFIX_VALUE_IELA, UserSession.getCurrent().getLanguageId(),
                    UserSession.getCurrent().getOrganisation());
            final LookUp liabilityLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.LIABILITY_OTHER_FIELD,
                    PrefixConstants.PREFIX_VALUE_IELA, UserSession.getCurrent().getLanguageId(),
                    UserSession.getCurrent().getOrganisation());
            final LookUp assetLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.ASSET_OTHER_FIELD,
                    PrefixConstants.PREFIX_VALUE_IELA, UserSession.getCurrent().getLanguageId(),
                    UserSession.getCurrent().getOrganisation());

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

            final LookUp oTLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    PrefixConstants.ACCOUNT_MASTERS.SECONDARY_MASTER.SECONDARY_OTHER_CPD_VALUE,
                    PrefixConstants.SECONDARY_LOOKUPCODE, langId, defaultorg);
            final LookUp cALookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.VoucherTemplate.CA,
                    PrefixConstants.SECONDARY_LOOKUPCODE, langId, defaultorg);
            final Long statusId = getActiveStatusId();
            final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

            final List<VoucherTemplateDTO> getMappingDetails = editData.getMappingDetails();

            for (final VoucherTemplateDTO voucherTemplateDTO : getMappingDetails) {
                final Long accountType = voucherTemplateDTO.getAccountType();

                final List<Object[]> accountHeadList = tbAcSecondaryheadMasterService.findSacHeadIdDescAllData(orgId, statusId);

                final Map<Long, String> pacSacHeadMapI = new LinkedHashMap<>();
                final Map<Long, String> pacSacHeadMapE = new LinkedHashMap<>();
                final Map<Long, String> pacSacHeadMapL = new LinkedHashMap<>();
                final Map<Long, String> pacSacHeadMapA = new LinkedHashMap<>();
                if ((accountType != null) && (accountType != 0)) {
                    final Long lookUpIdI = incomeLookup.getLookUpId();
                    final Long lookUpOTId = oTLookup.getLookUpId();
                    final Long lookUpCAId = cALookup.getLookUpId();
                    if (lookUpIdI.equals(accountType)) {
                        final String otherFieldI = incomeLookup.getOtherField();
                        for (final Object[] objects : accountHeadList) {
                            int strPacCodeValue;
                            strPacCodeValue = Integer.parseInt(String.valueOf(objects[2].toString().charAt(0)));
                            Long statusOT = null;
                            if (objects[3] != null) {
                                statusOT = Long.valueOf(objects[3].toString());
                                if (statusOT.equals(lookUpOTId) || statusOT.equals(lookUpCAId)) {
                                    if ((otherFieldI != null) && !otherFieldI.isEmpty()) {
                                        if ((Integer.parseInt(otherFieldI) == strPacCodeValue)) {
                                            if ((objects[0] != null)
                                                    && ((objects[1] != null) && !objects[1].toString().isEmpty())) {
                                                pacSacHeadMapI.put((Long) objects[0], objects[1].toString());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        voucherTemplateDTO.setLookupdesc(incomeLookup.getLookUpCode());
                        model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.PAC_HEAD_MAP_I, pacSacHeadMapI);
                    }
                    final Long lookUpIdE = expenditureLookup.getLookUpId();
                    if (lookUpIdE.equals(accountType)) {
                        final String otherFieldE = expenditureLookup.getOtherField();
                        for (final Object[] objects : accountHeadList) {
                            int strPacCodeValue;
                            strPacCodeValue = Integer.parseInt(String.valueOf(objects[2].toString().charAt(0)));
                            Long statusOT = null;
                            if (objects[3] != null) {
                                statusOT = Long.valueOf(objects[3].toString());
                                if (statusOT.equals(lookUpOTId) || statusOT.equals(lookUpCAId)) {
                                    if ((otherFieldE != null) && !otherFieldE.isEmpty()) {
                                        if ((Integer.parseInt(otherFieldE) == strPacCodeValue)) {
                                            if ((objects[0] != null)
                                                    && ((objects[1] != null) && !objects[1].toString().isEmpty())) {
                                                pacSacHeadMapE.put((Long) objects[0], objects[1].toString());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        voucherTemplateDTO.setLookupdesc(expenditureLookup.getLookUpCode());
                        model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.PAC_HEAD_MAP_E, pacSacHeadMapE);
                    }
                    final Long lookUpIdL = liabilityLookup.getLookUpId();
                    if (lookUpIdL.equals(accountType)) {
                        final String otherFieldL = liabilityLookup.getOtherField();
                        for (final Object[] objects : accountHeadList) {
                            int strPacCodeValue;
                            strPacCodeValue = Integer.parseInt(String.valueOf(objects[2].toString().charAt(0)));
                            Long statusOT = null;
                            if (objects[3] != null) {
                                statusOT = Long.valueOf(objects[3].toString());
                                if (statusOT.equals(lookUpOTId) || statusOT.equals(lookUpCAId)) {
                                    if ((otherFieldL != null) && !otherFieldL.isEmpty()) {
                                        if ((Integer.parseInt(otherFieldL) == strPacCodeValue)) {
                                            if ((objects[0] != null)
                                                    && ((objects[1] != null) && !objects[1].toString().isEmpty())) {
                                                pacSacHeadMapL.put((Long) objects[0], objects[1].toString());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        voucherTemplateDTO.setLookupdesc(liabilityLookup.getLookUpCode());
                        model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.PAC_HEAD_MAP_L, pacSacHeadMapL);
                    }
                    final Long lookUpIdA = assetLookup.getLookUpId();
                    if (lookUpIdA.equals(accountType)) {
                        final String otherFieldA = assetLookup.getOtherField();
                        for (final Object[] objects : accountHeadList) {
                            int strPacCodeValue;
                            strPacCodeValue = Integer.parseInt(String.valueOf(objects[2].toString().charAt(0)));
                            Long statusOT = null;
                            if (objects[3] != null) {
                                statusOT = Long.valueOf(objects[3].toString());
                                if (statusOT.equals(lookUpOTId) || statusOT.equals(lookUpCAId)) {
                                    if ((otherFieldA != null) && !otherFieldA.isEmpty()) {
                                        if ((Integer.parseInt(otherFieldA) == strPacCodeValue)) {
                                            if ((objects[0] != null)
                                                    && ((objects[1] != null) && !objects[1].toString().isEmpty())) {
                                                pacSacHeadMapA.put((Long) objects[0], objects[1].toString());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        voucherTemplateDTO.setLookupdesc(assetLookup.getLookUpCode());
                        model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.PAC_HEAD_MAP_A, pacSacHeadMapA);
                    }
                }
            }
            model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.FORM_DTO, editData);
            model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.MODE_FLAG, MainetConstants.MENU.E);
            final VoucherTemplateDTO dto = voucherTemplateService
                    .populateModel(UserSession.getCurrent().getOrganisation().getOrgid());
            model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.DTO, dto);
            page = MainetConstants.VOUCHER_TEMPLATE_ENTRY.EDIT_PAGE;
        } catch (final Exception ex) {
            LOGGER.error("Error while view Template", ex);
        }
        return page;
    }

    public Long getActiveStatusId() {
        final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
                PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(),
                UserSession.getCurrent().getOrganisation());
        final Long activeStatusId = lookUpFieldStatus.getLookUpId();
        return activeStatusId;
    }

}
