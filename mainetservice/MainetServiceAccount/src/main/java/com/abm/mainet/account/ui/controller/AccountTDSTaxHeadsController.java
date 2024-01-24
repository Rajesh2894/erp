package com.abm.mainet.account.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.dto.AccountTDSTaxHeadsBean;
import com.abm.mainet.account.dto.AccountTDSTaxHeadsDto;
import com.abm.mainet.account.service.AccountTDSTaxHeadsService;
import com.abm.mainet.account.service.BudgetCodeService;
import com.abm.mainet.account.ui.model.response.AccountTDSTaxHeadsResponse;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountFunctionMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountFundMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountHeadPrimaryAccountCodeMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Message;
import com.abm.mainet.common.utility.MessageType;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;

/**
 * Spring MVC controller for 'TbAcTdsTaxheads' management.
 */
@Controller
@RequestMapping("/AccountTDSTaxHeadsMaster.html")
public class AccountTDSTaxHeadsController extends AbstractController {

    private final String JSP_FORM = "AccountTDSTaxHeadsMaster/form";

    private final String JSP_VIEW_FORM = "AccountTDSTaxHeadsMaster/viewForm";

    private final String JSP_LIST = "AccountTDSTaxHeadsMaster/list";

    private final String SAVE_ACTION_CREATE = "/AccountTDSTaxHeadsMaster/create";

    private final String SAVE_ACTION_UPDATE = "/AccountTDSTaxHeadsMaster/update";

    private final String STATUS = "status";

    @Resource
    private AccountTDSTaxHeadsService tbAcTdsTaxheadsService;

    @Resource
    private AccountFundMasterService tbAcFundMasterService;

    @Resource
    private AccountFunctionMasterService tbAcFunctionMasterService;

    @Resource
    private AccountFieldMasterService tbAcFieldMasterService;

    @Resource
    private AccountHeadPrimaryAccountCodeMasterService tbAcPrimaryheadMasterService;

    @Resource
    private SecondaryheadMasterService tbAcSecondaryheadMasterService;

    @Resource
    private BudgetCodeService budgetCodeService;

    List<AccountTDSTaxHeadsBean> masterBeanList = null;

    public AccountTDSTaxHeadsController() {
        super(AccountTDSTaxHeadsController.class, MainetConstants.TAX_HEAD_MAPPING_MASTER.TAX_HEADS_MASTER_BEAN);
        log("TbAcTdsTaxheadsController created.");
    }

    /**
     * Shows a list with all the occurrences of TbAcTdsTaxheads found in the database
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping()
    public String populateGridList(final Model model) {
        masterBeanList = new ArrayList<>();
        final AccountTDSTaxHeadsBean tbAcTdsTaxheads = new AccountTDSTaxHeadsBean();
        populateBudgetCodes(model);
        populateStatus(model);
        populateModel(model, tbAcTdsTaxheads, FormMode.CREATE);
        masterBeanList.clear();
        return JSP_LIST;
    }

    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody AccountTDSTaxHeadsResponse gridData(final HttpServletRequest request, final Model model) {
        AccountTDSTaxHeadsResponse taxHeadsResponse = null;
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        final List<AccountTDSTaxHeadsBean> listOfTaxBean = new ArrayList<>();
        if ((masterBeanList != null) && !masterBeanList.isEmpty()) {
            for (final AccountTDSTaxHeadsBean bean : masterBeanList) {
                listOfTaxBean.add(bean);
            }
        }
        taxHeadsResponse = new AccountTDSTaxHeadsResponse();
        taxHeadsResponse.setRows(listOfTaxBean);
        taxHeadsResponse.setTotal(masterBeanList.size());
        taxHeadsResponse.setRecords(masterBeanList.size());
        taxHeadsResponse.setPage(page);
        model.addAttribute(MainetConstants.TAX_HEAD_MAPPING_MASTER.MAIN_LIST_NAME, masterBeanList);
        return taxHeadsResponse;
    }

    @RequestMapping(params = "searchTDSData", method = RequestMethod.POST)
    public @ResponseBody List<AccountTDSTaxHeadsBean> getTdsMappingData(@RequestParam("accountHead") final Long accountHead,
            @RequestParam("tdsType") final Long tdsType, @RequestParam("status") final Long status) {

        masterBeanList = new ArrayList<>();
        masterBeanList.clear();
        AccountTDSTaxHeadsBean masterBean = null;
        List<AccountTDSTaxHeadsBean> tdsList = null;
        LookUp statusDesc = null;
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final LookUp lkp = CommonMasterUtility.getNonHierarchicalLookUpObject(status, organisation);
        final String statusFlag = lkp.getLookUpCode();
        if ((accountHead != null) || (tdsType != null) || (status != null)) {
            tdsList = tbAcTdsTaxheadsService.getTdsMappingData(organisation.getOrgid(), accountHead, tdsType, statusFlag);
        } else {
            tdsList = tbAcTdsTaxheadsService.findAll(organisation.getOrgid());
        }
        if ((tdsList != null) && !tdsList.isEmpty()) {
            for (final AccountTDSTaxHeadsBean list : tdsList) {
                masterBean = new AccountTDSTaxHeadsBean();
                masterBean.setTdsId(list.getTdsId());
                masterBean.setTdsDescription(list.getTdsDescription());
                masterBean.setFundDesc(list.getFundDesc());
                masterBean.setFunctionDesc(list.getFunctionDesc());
                masterBean.setFieldDesc(list.getFieldDesc());
                masterBean.setPacHeadDesc(list.getPacHeadDesc());
                masterBean.setSacHeadDesc(list.getSacHeadDesc());
                statusDesc = CommonMasterUtility.getLookUpFromPrefixLookUpValue(list.getTdsStatusFlg(),
                        AccountPrefix.ACN.toString(), UserSession.getCurrent().getLanguageId(), organisation);
                masterBean.setStatusDescription(statusDesc.getDescLangFirst());
                masterBeanList.add(masterBean);
            }
        }
        return masterBeanList;
    }

    /**
     * Populates the combo-box "items" for the referenced entity "TbAcSecondaryheadMaster"
     * @param model
     */
    @RequestMapping(params = "sacHeadItemsList")
    public @ResponseBody Map<Long, String> sacHeadData(@RequestParam("pacHeadId") final String primaryCode,
            final HttpServletRequest request,
            final Model model) {
        Map<Long, String> lookup = new HashMap<>(0);
        lookup = tbAcSecondaryheadMasterService.findAllById(Long.valueOf(primaryCode));
        return lookup;

    }

    /**
     * Populates the combo-box "items" for the referenced entity "TbComparamDet"
     * @param model
     */
    private void populateListOfTbComparamDetItems(final Model model) {
        final List<LookUp> taxDesc = CommonMasterUtility.getListLookup(PrefixConstants.TDS,
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(MainetConstants.TAX_HEAD_MAPPING_MASTER.TAX_DESCRIPTION_ITEMS, taxDesc);
    }

    /**
     * @param model
     */
    private void populateStatus(final Model model) {
        final List<LookUp> status = CommonMasterUtility.getListLookup(AccountPrefix.ACN.toString(),
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(STATUS, status);
    }

    /**
     * Populates the Spring MVC model with the given entity and eventually other useful data
     * @param model
     * @param tdsTaxHeadsMasterBean
     */

    private void populateModel(final Model model, final AccountTDSTaxHeadsBean tdsTaxHeadsMasterBean, final FormMode formMode) {
        model.addAttribute(MainetConstants.TAX_HEAD_MAPPING_MASTER.TAX_HEADS_MASTER_BEAN, tdsTaxHeadsMasterBean);
        if (formMode == FormMode.CREATE) {
            model.addAttribute(MODE, MODE_CREATE);
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
            populateListOfTbComparamDetItems(model);
            populateBudgetCodes(model);
            populateStatus(model);
        } else if (formMode == FormMode.UPDATE) {
            model.addAttribute(MODE, MODE_UPDATE);
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);
            populateListOfTbComparamDetItems(model);
            populateBudgetCodes(model);
            populateStatus(model);
        }
    }

    private void populateBudgetCodes(final Model model) {

        final Long superOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
        final Organisation org = UserSession.getCurrent().getOrganisation();
        final int langId = UserSession.getCurrent().getLanguageId();
        final String coaPrefix = AccountPrefix.COA.toString();
        final LookUp lookUp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.NEC.ADVOCATE, coaPrefix, langId,
                org);
        final Long cpdIdHeadType = lookUp.getLookUpId();
        final List<Object[]> budgetHeadList = budgetCodeService.getBudgetHeads(superOrgId, org.getOrgid(), cpdIdHeadType);
        final Map<Long, String> headCodeMap = new HashMap<>();
        if ((budgetHeadList != null) && !budgetHeadList.isEmpty()) {
            for (final Object[] budgetArray : budgetHeadList) {
                headCodeMap.put((Long) budgetArray[0], budgetArray[1].toString());
            }
        }
        model.addAttribute(MainetConstants.AccountReceiptEntry.HEAD_CODE_MAP, headCodeMap);
    }

    /**
     * Shows a form page in order to create a new TbAcTdsTaxheads
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping(params = "form", method = RequestMethod.POST)
    public String formForCreate(final Model model) {

        log("Action 'formForCreate'");
        final AccountTDSTaxHeadsBean tbAcTdsTaxheads = new AccountTDSTaxHeadsBean();
        final Organisation orgnaisation = UserSession.getCurrent().getOrganisation();
        final LookUp active = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.CommonConstants.ACTIVE,
                AccountPrefix.ACN.toString(), UserSession.getCurrent().getLanguageId(), orgnaisation);
        final Long activeId = active.getLookUpId();
        final AccountTDSTaxHeadsDto taxHeadsDto = new AccountTDSTaxHeadsDto();
        taxHeadsDto.setStatus(activeId);
        populateModel(model, tbAcTdsTaxheads, FormMode.CREATE);
        final List<AccountTDSTaxHeadsBean> list = new ArrayList<>();
        final List<AccountTDSTaxHeadsDto> dtoList = new ArrayList<>();
        dtoList.add(taxHeadsDto);
        tbAcTdsTaxheads.setTaxHeadsDtoList((dtoList));
        list.add(tbAcTdsTaxheads);
        model.addAttribute(MainetConstants.TAX_HEAD_MAPPING_MASTER.MAIN_LIST_NAME, list);
        model.addAttribute(MainetConstants.AccountTDSTaxHeads.ACTIVE_ID, activeId);
        return JSP_FORM;
    }

    /**
     * Shows a form page in order to update an existing TbAcTdsTaxheads
     * @param model Spring MVC model
     * @param tdsId primary key element
     * @return
     */
    @RequestMapping(params = "formForUpdate", method = RequestMethod.POST)
    public String formForUpdate(final Model model, final HttpServletRequest request,
            @RequestParam(MainetConstants.TAX_HEAD_MAPPING_MASTER.TDS_ID) final Long tdsId,
            @RequestParam(MainetConstants.TAX_HEAD_MAPPING_MASTER.MODE_DATA) final String viewmode) {
        log("Action 'formForUpdate'");
        if (viewmode.equals(MainetConstants.VIEW)) {
            model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.SUCCESS);
        } else {
            model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
        }
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final AccountTDSTaxHeadsBean tbAcTdsTaxheads = tbAcTdsTaxheadsService.findById(tdsId, orgId);
        final AccountTDSTaxHeadsDto taxHeadsDto = new AccountTDSTaxHeadsDto();
        final List<AccountTDSTaxHeadsDto> dtoList = new ArrayList<>();
        populateModel(model, tbAcTdsTaxheads, FormMode.UPDATE);
        taxHeadsDto.setTdsId(tdsId);
        taxHeadsDto.setTdsEntrydate(UtilityService.convertDateToDDMMYYYY(tbAcTdsTaxheads.getTdsEntrydate()));
        taxHeadsDto.setFundId(tbAcTdsTaxheads.getFundId());
        taxHeadsDto.setFunctionId(tbAcTdsTaxheads.getFunctionId());
        taxHeadsDto.setFieldId(tbAcTdsTaxheads.getFieldId());
        taxHeadsDto.setPacHeadId(tbAcTdsTaxheads.getSacHeadId());
        taxHeadsDto.setCpdIdDeductionType(tbAcTdsTaxheads.getCpdIdDeductionType());
        taxHeadsDto.setTdsStatusFlg(tbAcTdsTaxheads.getTdsStatusFlg());
        taxHeadsDto.setOrgid(tbAcTdsTaxheads.getOrgid());
        final LookUp lkp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(tbAcTdsTaxheads.getTdsStatusFlg(),
                AccountPrefix.ACN.toString(), UserSession.getCurrent().getLanguageId(),
                UserSession.getCurrent().getOrganisation());
        taxHeadsDto.setStatus(lkp.getLookUpId());
        taxHeadsDto.setBudgetCodeId(tbAcTdsTaxheads.getBudgetCodeId());
        dtoList.add(taxHeadsDto);
        tbAcTdsTaxheads.setTaxHeadsDtoList(dtoList);
        final List<AccountTDSTaxHeadsBean> list = new ArrayList<>();
        list.add(tbAcTdsTaxheads);
        model.addAttribute(MainetConstants.TAX_HEAD_MAPPING_MASTER.MAIN_LIST_NAME, list);
        return JSP_FORM;
    }

    /**
     * Shows a form page in order to update an existing TbAcTdsTaxheads
     * @param model Spring MVC model
     * @param tdsId primary key element
     * @return
     */
    @RequestMapping(params = "formForView", method = RequestMethod.POST)
    public String formForView(final Model model, final HttpServletRequest request,
            @RequestParam(MainetConstants.TAX_HEAD_MAPPING_MASTER.TDS_ID) final Long tdsId,
            @RequestParam(MainetConstants.TAX_HEAD_MAPPING_MASTER.MODE_DATA) final String viewmode) {

        log("Action 'formForUpdate'");
        if (viewmode.equals(MainetConstants.VIEW)) {
            model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.SUCCESS);
        } else {
            model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
        }
        LookUp lkpActive = null;
        String statusDesc = null;
        String coaPrefix = null;
        Long cpdIdHeadType = null;
        List<AccountTDSTaxHeadsBean> taxHeadlist = null;
        final Long superOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
        final AccountTDSTaxHeadsBean tbAcTdsTaxheads = tbAcTdsTaxheadsService.findById(tdsId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        final AccountTDSTaxHeadsDto taxHeadsDto = new AccountTDSTaxHeadsDto();
        final List<AccountTDSTaxHeadsDto> dtoList = new ArrayList<>();
        populateModel(model, tbAcTdsTaxheads, FormMode.UPDATE);
        taxHeadsDto.setTdsId(tdsId);
        taxHeadsDto.setTdsEntrydate(UtilityService.convertDateToDDMMYYYY(tbAcTdsTaxheads.getTdsEntrydate()));
        taxHeadsDto.setPacHeadId(tbAcTdsTaxheads.getSacHeadId());
        final String tdsTypeDesc = CommonMasterUtility.findLookUpDesc(PrefixConstants.TDS,
                UserSession.getCurrent().getOrganisation().getOrgid(),
                tbAcTdsTaxheads.getCpdIdDeductionType());
        tbAcTdsTaxheads.setTdsDescription(tdsTypeDesc);
        taxHeadsDto.setTdsStatusFlg(tbAcTdsTaxheads.getTdsStatusFlg());
        taxHeadsDto.setOrgid(tbAcTdsTaxheads.getOrgid());
        lkpActive = CommonMasterUtility.getLookUpFromPrefixLookUpValue(tbAcTdsTaxheads.getTdsStatusFlg(),
                AccountPrefix.ACN.toString(), UserSession.getCurrent().getLanguageId(),
                UserSession.getCurrent().getOrganisation());
        statusDesc = CommonMasterUtility.findLookUpDesc(AccountPrefix.ACN.toString(),
                UserSession.getCurrent().getOrganisation().getOrgid(), lkpActive.getLookUpId());
        tbAcTdsTaxheads.setStatusDescription((statusDesc));
        taxHeadsDto.setBudgetCodeId(tbAcTdsTaxheads.getBudgetCodeId());
        coaPrefix = AccountPrefix.COA.toString();
        final LookUp lookUp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.NEC.ADVOCATE, coaPrefix,
                UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        cpdIdHeadType = lookUp.getLookUpId();
        final String budgetHeadDesc = tbAcTdsTaxheadsService.getBudgetHeadDescription(cpdIdHeadType,
                tbAcTdsTaxheads.getBudgetCodeId(),
                superOrgId, tbAcTdsTaxheads.getOrgid());
        tbAcTdsTaxheads.setBudgetHeadDesc(budgetHeadDesc);
        dtoList.add(taxHeadsDto);
        tbAcTdsTaxheads.setTaxHeadsDtoList(dtoList);
        taxHeadlist = new ArrayList<>();
        taxHeadlist.add(tbAcTdsTaxheads);
        model.addAttribute(MainetConstants.TAX_HEAD_MAPPING_MASTER.MAIN_LIST_NAME, taxHeadlist);
        return JSP_VIEW_FORM;
    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String create(@Valid final AccountTDSTaxHeadsBean tdsTaxHeadsMasterBean, final BindingResult bindingResult,
            final Model model,
            final RedirectAttributes redirectAttributes,
            final HttpServletRequest httpServletRequest) {
        log("Action 'create'");
        final List<AccountTDSTaxHeadsDto> accountTDSTaxHeadsList = tdsTaxHeadsMasterBean.getTaxHeadsDtoList();
        if ((accountTDSTaxHeadsList != null) && !accountTDSTaxHeadsList.isEmpty()) {
            final Set<AccountTDSTaxHeadsDto> accountTDSTaxHeadsSet = new HashSet<>(accountTDSTaxHeadsList);
            Long taxId = null;
            Long tdsCount = null;
            final ApplicationSession appSession = ApplicationSession.getInstance();
            for (final AccountTDSTaxHeadsDto taxDto : accountTDSTaxHeadsSet) {
                taxId = tdsTaxHeadsMasterBean.getCpdIdDeductionType();
                if (taxDto.getTdsId() == null) {
                    tdsCount = tbAcTdsTaxheadsService.getTdsTypeCount(taxId);
                    if (tdsCount > 0) {
                        bindingResult.addError(new org.springframework.validation.FieldError(
                                MainetConstants.TAX_HEAD_MAPPING_MASTER.TAX_HEADS_MASTER_BEAN,
                                MainetConstants.BLANK, null, false,
                                new String[] { MainetConstants.ERRORS }, null,
                                appSession.getMessage("tax.heads.tds.mapping")));
                    }
                }
            }
        }
        if (!bindingResult.hasErrors()) {
            tdsTaxHeadsMasterBean.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);
            final Organisation org = new Organisation();
            tdsTaxHeadsMasterBean.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            tdsTaxHeadsMasterBean.setLangId((long) UserSession.getCurrent().getLanguageId());
            tdsTaxHeadsMasterBean.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            tdsTaxHeadsMasterBean.setTdsEntrydate(new Date());
            tdsTaxHeadsMasterBean.setLmoddate(new Date());
            tdsTaxHeadsMasterBean.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
            final AccountTDSTaxHeadsBean tbAcTdsTaxheadsCreated = tbAcTdsTaxheadsService.create(tdsTaxHeadsMasterBean, org);
            tdsTaxHeadsMasterBean.setSuccessfulFlag(MainetConstants.MASTER.Y);
            model.addAttribute(MainetConstants.TAX_HEAD_MAPPING_MASTER.TAX_HEADS_MASTER_BEAN, tbAcTdsTaxheadsCreated);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            return JSP_FORM;
        } else {
            tdsTaxHeadsMasterBean.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
            tdsTaxHeadsMasterBean.setSuccessfulFlag(MainetConstants.MASTER.N);
            final List<AccountTDSTaxHeadsDto> list = keepDataAfterValidationError(tdsTaxHeadsMasterBean, bindingResult);
            model.addAttribute(MainetConstants.FIELD_MASTER.MAIN_LIST_NAME, list);
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            populateModel(model, tdsTaxHeadsMasterBean, FormMode.CREATE);
            return JSP_FORM;
        }
    }

    /**
     * @param tdsTaxHeadsMasterBean
     * @param bindingResult
     * @return
     */
    private List<AccountTDSTaxHeadsDto> keepDataAfterValidationError(
            final AccountTDSTaxHeadsBean tdsTaxHeadsMasterBean, final BindingResult bindingResult) {
        List<AccountTDSTaxHeadsDto> list = null;
        list = tdsTaxHeadsMasterBean.getTaxHeadsDtoList();
        if (list == null) {
            list = new ArrayList<>();
        }
        if (list.isEmpty()) {
            list.add(new AccountTDSTaxHeadsDto());
            tdsTaxHeadsMasterBean.setTaxHeadsDtoList(list);
        }
        return list;
    }

}
