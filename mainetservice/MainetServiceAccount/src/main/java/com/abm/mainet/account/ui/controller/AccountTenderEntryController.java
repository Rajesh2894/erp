package com.abm.mainet.account.ui.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.domain.AccountBudgetProjectedExpenditureEntity;
import com.abm.mainet.account.domain.AccountTenderDetEntity;
import com.abm.mainet.account.domain.AccountTenderEntryEntity;
import com.abm.mainet.account.dto.AccountTenderDetBean;
import com.abm.mainet.account.dto.AccountTenderEntryBean;
import com.abm.mainet.account.service.AccountBudgetProjectedExpenditureService;
import com.abm.mainet.account.service.AccountTenderEntryService;
import com.abm.mainet.account.service.BudgetCodeService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountFunctionMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountFundMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountHeadPrimaryAccountCodeMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
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
import com.abm.mainet.common.utility.UtilityService;

@Controller
@RequestMapping("/AccountTenderEntry.html")
public class AccountTenderEntryController extends AbstractController {

    /**
     * @param controllerClass
     * @param entityName
     */
    public AccountTenderEntryController() {
        super(AccountTenderEntryController.class, MainetConstants.CommonConstant.BLANK);

    }

    private final String JSP_FORM = "AccountTenderEntry/form";
    private final String JSP_LIST = "AccountTenderEntry/list";
    private final String SAVE_ACTION_CREATE = "/AccountTenderEntry/create";
    private final String SAVE_ACTION_UPDATE = "/AccountTenderEntry/create";
    private static Logger logger = Logger.getLogger(AccountTenderEntryController.class);

    @Autowired
    private DepartmentService departmentService;

    @Resource
    private AccountFieldMasterService tbAcFieldMasterService;

    @Resource
    private AccountHeadPrimaryAccountCodeMasterService tbAcPrimaryheadMasterService;

    @Resource
    private SecondaryheadMasterService tbAcSecondaryheadMasterService;

    @Resource
    private AccountFundMasterService tbAcFundMasterService;

    @Resource
    private AccountFunctionMasterService tbAcFunctionMasterService;

    @Resource
    private AccountBudgetProjectedExpenditureService tbAcProjectedExpenditureService;

    @Resource
    private AccountTenderEntryService tbAcTenderEntryService;

    @Resource
    private BudgetCodeService accountBudgetCodeService;

    @Resource
    private TbAcVendormasterService tbAcVendormasterService;

    @Resource
    // private TbFinancialyearJpaRepository tbFinancialyearJpaRepository;
    private TbFinancialyearService tbFinancialyearService;

    private final String TENDER_TYPE = "tenderType";

    private final String VENDOR_MAP = "vendorMap";

    private List<AccountTenderEntryBean> chList = null;

    @RequestMapping()
    public String populateGridList(final Model model) throws Exception {
        log("Action 'list'");
        String result = MainetConstants.CommonConstant.BLANK;
        chList = new ArrayList<>();
        chList.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        chList = tbAcTenderEntryService.findAll(orgId);
        final Map<Long, String> accountHeadsMap = new LinkedHashMap<>();
        for (final AccountTenderEntryBean accountTenderEntryBean : chList) {
            final List<AccountTenderDetBean> prBudgetCodeIdList = accountTenderEntryBean.getTenderDetList();
            Long prBudgetCodeId = null;
            for (final AccountTenderDetBean accountTenderDetBean : prBudgetCodeIdList) {
                prBudgetCodeId = accountTenderDetBean.getSacHeadId();
                if (prBudgetCodeId != null) {
                    final String budgetCode = tbAcSecondaryheadMasterService.getAccountHeadCodeInReceieptDetEntry(prBudgetCodeId);
                    accountHeadsMap.put(accountTenderDetBean.getSacHeadId(), budgetCode);
                }
            }
        }
        final AccountTenderEntryBean bean = new AccountTenderEntryBean();
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MainetConstants.AccountDeposit.ACCOUNT_HEAD_MAP, accountHeadsMap);
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, chList);
        result = JSP_LIST;
        return result;
    }

    @RequestMapping(params = "form", method = RequestMethod.POST)
    public String formForCreate(final Model model) throws Exception {
        log("Action 'formForCreate'");

        final AccountTenderEntryBean tenderDetBean = new AccountTenderEntryBean();
        populateModel(model, tenderDetBean, FormMode.CREATE);
        final List<AccountTenderEntryBean> list = new ArrayList<>();
        final List<AccountTenderDetBean> detList = new ArrayList<>();
        tenderDetBean.setTenderDetList(detList);
        list.add(tenderDetBean);
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, list);

        return JSP_FORM;

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(final HttpServletRequest request,
            final Model model) {
        log("AccountTenderEntry-'gridData' : 'Get grid Data'");
        final JQGridResponse response = new JQGridResponse<>();
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        response.setRows(chList);
        response.setTotal(chList.size());
        response.setRecords(chList.size());
        response.setPage(page);
        model.addAttribute(MainetConstants.TENDER_ENTRY.MAIN_LIST_NAME, chList);
        return response;
    }

    @RequestMapping(params = "getjqGridsearch")
    public @ResponseBody List getCheqData(final HttpServletRequest request, final Model model,
            @RequestParam("trTenderNo") final String trTenderNo,
            @RequestParam("vmVendorid") final Long vmVendorid, @RequestParam("trTypeCpdId") final Long trTypeCpdId,
            @RequestParam("sacHeadId") final Long sacHeadId, @RequestParam("trTenderAmount") final String trTenderAmount,
            @RequestParam("statusId") final String statusId) {
        log("AccountTenderEntry-'getjqGridsearch' : 'get jqGrid search data'");

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        chList = new ArrayList<>();
        chList.clear();
        chList = tbAcTenderEntryService.findByAllGridSearchData(trTenderNo, vmVendorid, trTypeCpdId, sacHeadId, trTenderAmount,
                statusId, orgId);
        return chList;
    }

    public void populateListOfTenderType(final Model model) {

        final List<LookUp> tenderTypeLookUp = CommonMasterUtility.getListLookup(
                AccountPrefix.LTY.toString(), UserSession.getCurrent()
                        .getOrganisation());

        model.addAttribute(TENDER_TYPE, tenderTypeLookUp);

    }

    @RequestMapping(params = "getTenderDetailsForEdit")
    public String formForUpdate(
            final Model model,
            final AccountTenderEntryBean tenderEntryBean,
            final HttpServletRequest request,
            @RequestParam(MainetConstants.TENDER_ENTRY.TENDERID) final Long trTenderId,
            @RequestParam(MainetConstants.TAX_HEAD_MAPPING_MASTER.MODE_DATA) final String viewmode) throws Exception {

        log("Action 'formForUpdate'");

        if (viewmode.equals(MainetConstants.VIEW)) {
            model.addAttribute(MainetConstants.VIEW_MODE,
                    MainetConstants.SUCCESS);
        } else {
            model.addAttribute(MainetConstants.VIEW_MODE,
                    MainetConstants.FAILED);
        }

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

        final AccountTenderEntryEntity tenderEntryList = tbAcTenderEntryService
                .findById(trTenderId);
        populateModel(model, tenderEntryBean, FormMode.UPDATE);
        tenderEntryBean.setTrTenderId(tenderEntryList.getTrTenderId());
        tenderEntryBean.setTrTenderNo(tenderEntryList.getTrTenderNo());
        tenderEntryBean.setTrTenderDate(UtilityService.convertDateToDDMMYYYY(tenderEntryList.getTrTenderDate()));
        tenderEntryBean.setDpDeptid(tenderEntryList.getTbDepartment()
                .getDpDeptid());
        tenderEntryBean.setTrTypeCpdId(tenderEntryList.getTbComparamDet().getCpdId());
        tenderEntryBean.setTrTenderAmount(tenderEntryList
                .getTrTenderAmount().toString());
        if (tenderEntryList.getTrEmdAmt() != null) {
            tenderEntryBean.setTrEmdAmt(tenderEntryList.getTrEmdAmt().toString());
        }
        tenderEntryBean.setVmVendorid(tenderEntryList.getTbVendormaster()
                .getVmVendorid());
        tenderEntryBean.setTrNameofwork(tenderEntryList.getTrNameofwork());
        tenderEntryBean.setTrProposalNo(tenderEntryList.getTrProposalNo());
        tenderEntryBean.setTrProposalDate(UtilityService.convertDateToDDMMYYYY(tenderEntryList.getTrProposalDate()));
        tenderEntryBean.setSpecialconditions(tenderEntryList
                .getSpecialconditions());

        final List<AccountTenderDetBean> listDetDto = new ArrayList<>();

        for (final AccountTenderDetEntity tenderList : tenderEntryList.getListOfTbAcTenderDet()) {
            final AccountTenderDetBean detDto = new AccountTenderDetBean();
            detDto.setTrTenderidDet(tenderList.getTrTenderidDet());
            detDto.setSacHeadId(tenderList.getSacHeadId());
            detDto.setBudgetaryProv(tenderList.getBudgetaryProv());
            detDto.setBalanceProv(tenderList.getBalanceProv());
            detDto.setTrTenderAmount(tenderList.getTenderDetailAmt().toString());
            listDetDto.add(detDto);
        }
        // final Long dpDeptid = tenderEntryList.getTbDepartment().getDpDeptid();
        final Map<Long, String> budgetCode = tbAcSecondaryheadMasterService.findExpenditureHeadMapAccountTypeIsOthers(orgId);
        model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.ACCOUNT_BUDGET_CODE_MAP, budgetCode);

        final Long vmVendorid = tenderEntryList.getTbVendormaster().getVmVendorid();
        final Long emdId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.TenderEntryAuthorization.EM,
                AccountPrefix.DTY.toString(), orgId);
        final Map<Long, Long> depositCode = tbAcTenderEntryService.findDepositTypeEmdData(vmVendorid, emdId, orgId);
        model.addAttribute(MainetConstants.AccountTenderEntryAuthorization.DEPOSIT_CODE_MAP, depositCode);

        tenderEntryBean.setTenderDetList(listDetDto);
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, listDetDto);

        return JSP_FORM;

    }

    /**
     * Populates the combo-box "items" for the referenced entity "TbAcSecondaryh eadMaster"
     *
     * @param model
     */

    @RequestMapping(params = "sacHeadItemsList")
    public @ResponseBody Map<Long, String> sacHeadData(
            @RequestParam("pacHeadId") final String primaryCode,
            final HttpServletRequest request, final Model model) {
        Map<Long, String> lookup = new HashMap<>(0);

        lookup = tbAcSecondaryheadMasterService.findAllById(Long
                .valueOf(primaryCode));

        return lookup;

    }

    @RequestMapping(params = "create")
    public String create(@Valid final AccountTenderEntryBean tenderEntryBean,
            final BindingResult bindingResult, final Model model,
            final RedirectAttributes redirectAttributes,
            final HttpServletRequest httpRequestMapping) throws Exception {
        log("Action,'create'");
        if (!bindingResult.hasErrors()) {
            tenderEntryBean
                    .setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);

            tenderEntryBean.setOrgid(UserSession.getCurrent()
                    .getOrganisation().getOrgid());
            tenderEntryBean.setLangId((long) UserSession.getCurrent().getLanguageId());
            tenderEntryBean.setCreatedBy(UserSession.getCurrent()
                    .getEmployee().getEmpId());
            tenderEntryBean.setOrgShortName(UserSession.getCurrent().getOrganisation().getOrgShortNm());
            tenderEntryBean.setAuthorisedBy(UserSession.getCurrent().getEmployee().getEmpId());
            tenderEntryBean.setAuthDate((new Date()));

            tenderEntryBean.setCreatedDate((new Date()));
            tenderEntryBean.setLgIpMac(Utility.getClientIpAddress(httpRequestMapping));

            tbAcTenderEntryService.create(tenderEntryBean);
            model.addAttribute(
                    MainetConstants.TENDER_ENTRY.TENDER_ENTRY_BEAN,
                    tbAcTenderEntryService);
            messageHelper.addMessage(redirectAttributes, new Message(
                    MessageType.SUCCESS, MainetConstants.SAVE));
            if (tenderEntryBean.getTrTenderId() == null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        ApplicationSession.getInstance().getMessage("accounts.fieldmaster.success"));
            }
            if (tenderEntryBean.getTrTenderId() != null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        ApplicationSession.getInstance().getMessage("accounts.fieldmaster.update"));
            }
            return JSP_FORM;
        } else {
            tenderEntryBean
                    .setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
            final List<AccountTenderDetBean> list = keepDataAfterValidationError(
                    tenderEntryBean, bindingResult);
            model.addAttribute(
                    MainetConstants.FIELD_MASTER.MAIN_LIST_NAME,
                    list);
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX
                    + MainetConstants.FORM_NAME, bindingResult);
            populateModel(model, tenderEntryBean, FormMode.CREATE);
            return JSP_FORM;
        }
    }

    /**
     * @param tenderEntryBean
     * @param bindingResult
     * @return
     */
    private List<AccountTenderDetBean> keepDataAfterValidationError(
            final AccountTenderEntryBean tenderEntryBean, final BindingResult bindingResult) {
        List<AccountTenderDetBean> list = null;

        list = tenderEntryBean.getTenderDetList();

        if (list == null) {
            list = new ArrayList<>();
        }
        if (list.isEmpty()) {
            list.add(new AccountTenderDetBean());
            tenderEntryBean.setTenderDetList(list);
        }
        return list;
    }

    private void populateModel(final Model model,
            final AccountTenderEntryBean tenderEntryBean, final FormMode formMode) throws Exception {
        model.addAttribute(
                MainetConstants.TENDER_ENTRY.ACCOUNT_TENDER_ENTRY,
                tenderEntryBean);
        if (formMode == FormMode.CREATE) {
            model.addAttribute(MODE, MODE_CREATE);
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
            populateListOfTenderType(model);
            populateAddVendorList(model);
            final Map<Long, String> deptMap = new HashMap<>(0);
            List<Object[]> department = null;
            department = departmentService.getAllDeptTypeNames();
            for (final Object[] dep : department) {
                if (dep[0] != null) {
                    deptMap.put((Long) (dep[0]), (String) dep[1]);
                }
            }
            model.addAttribute(
                    MainetConstants.TENDER_ENTRY.DEPT_MAP,
                    deptMap);
            final List<LookUp> activeDeActiveMap = CommonMasterUtility.getListLookup(
                    MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_STATUS_PREFIX, UserSession.getCurrent().getOrganisation());
            model.addAttribute(MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_MAP, activeDeActiveMap);
            checkingBudgetDefParameter(model);
        } else if (formMode == FormMode.UPDATE) {
            model.addAttribute(MODE, MODE_UPDATE);
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);
            populateListOfTenderType(model);
            populateVendorList(model);
            final Map<Long, String> deptMap = new HashMap<>(0);
            List<Object[]> department = null;
            department = departmentService.getAllDeptTypeNames();
            for (final Object[] dep : department) {
                if (dep[0] != null) {
                    deptMap.put((Long) (dep[0]), (String) dep[1]);
                }
            }
            model.addAttribute(
                    MainetConstants.TENDER_ENTRY.DEPT_MAP,
                    deptMap);
            final List<LookUp> activeDeActiveMap = CommonMasterUtility.getListLookup(
                    MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_STATUS_PREFIX, UserSession.getCurrent().getOrganisation());
            model.addAttribute(MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_MAP, activeDeActiveMap);
            checkingBudgetDefParameter(model);
        }
    }

    @RequestMapping(params = "getBudgetExpenditureData", method = RequestMethod.POST)
    public String loadBudgetExpenditureData(final AccountTenderEntryBean dto, final Model model,
            final RedirectAttributes redirectAttributes,
            final HttpServletRequest request, final BindingResult bindingResult) throws Exception {
        log("AccountReappropriationMaster-'getBudgetExpenditureData' : 'get BudgetExpenditureData data'");
        String result = MainetConstants.CommonConstant.BLANK;

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        // final Long dpDeptid = dto.getDpDeptid();
        final Map<Long, String> budgetCode = tbAcSecondaryheadMasterService.findExpenditureHeadMapAccountTypeIsOthers(orgId);
        model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.ACCOUNT_BUDGET_CODE_MAP, budgetCode);
        populateModel(model, dto, FormMode.CREATE);
        final List<AccountTenderEntryBean> list = new ArrayList<>();
        list.add(dto);
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, list);
        result = JSP_FORM;
        return result;
    }

    @RequestMapping(params = "getDepositTypeEmdData", method = RequestMethod.POST)
    public String loadDepositTypeEmdData(final AccountTenderEntryBean dto, final Model model,
            final RedirectAttributes redirectAttributes,
            final HttpServletRequest request, final BindingResult bindingResult) throws Exception {
        log("AccountReappropriationMaster-'getDepositTypeEmdData' : 'get DepositTypeEmdData data'");
        String result = MainetConstants.CommonConstant.BLANK;

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long vmVendorid = dto.getVmVendorid();
        final Long emdId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.TenderEntryAuthorization.EM,
                AccountPrefix.DTY.toString(), orgId);
        final Map<Long, Long> depositCode = tbAcTenderEntryService.findDepositTypeEmdData(vmVendorid, emdId, orgId);
        model.addAttribute(MainetConstants.AccountTenderEntryAuthorization.DEPOSIT_CODE_MAP, depositCode);

        // final Long dpDeptid = dto.getDpDeptid();
        final Map<Long, String> budgetCode = tbAcSecondaryheadMasterService.findExpenditureHeadMapAccountTypeIsOthers(orgId);
        model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.ACCOUNT_BUDGET_CODE_MAP, budgetCode);

        populateModel(model, dto, FormMode.CREATE);
        final List<AccountTenderEntryBean> list = new ArrayList<>();
        list.add(dto);
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, list);
        result = JSP_FORM;
        return result;
    }

    @RequestMapping(params = "getOrgBalGridload", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> findBudgetExpAmountData(final AccountTenderEntryBean bean,
            final HttpServletRequest request,
            final Model model, @RequestParam("cnt") final int cnt) {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long budgCodeid = bean.getTenderDetList().get(cnt).getSacHeadId();
        Long yearId = tbFinancialyearService.getFinanciaYearIdByFromDate(Utility.stringToDate(bean.getTrTenderDate()));

        final Map<String, String> opBalMap = new HashMap<>();

        final List<AccountBudgetProjectedExpenditureEntity> projectedExpList = tbAcProjectedExpenditureService
                .getExpenditureDetailsForBillEntryFormView(orgId, yearId, budgCodeid);
        for (AccountBudgetProjectedExpenditureEntity accountBudgetProjectedExpenditureEntity : projectedExpList) {
            if (accountBudgetProjectedExpenditureEntity.getRevisedEstamt() == null
                    || accountBudgetProjectedExpenditureEntity.getRevisedEstamt().isEmpty()) {
                BigDecimal originalEstAmount = accountBudgetProjectedExpenditureEntity.getOrginalEstamt();
                originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
                opBalMap.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_EXP_ORIGINAL_ESTIMATE,
                        originalEstAmount.toString());
                if (accountBudgetProjectedExpenditureEntity.getExpenditureAmt() == null) {
                    opBalMap.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_EXP_BALANCE_PROVISION,
                            originalEstAmount.toString());
                } else {
                    BigDecimal expAmt = new BigDecimal(accountBudgetProjectedExpenditureEntity.getExpenditureAmt().toString());
                    expAmt = expAmt.setScale(2, RoundingMode.CEILING);
                    opBalMap.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_EXP_BALANCE_PROVISION,
                            originalEstAmount.subtract(expAmt).toString());
                }
            } else {
                BigDecimal revEsmtAmt = new BigDecimal(accountBudgetProjectedExpenditureEntity.getRevisedEstamt());
                revEsmtAmt = revEsmtAmt.setScale(2, RoundingMode.CEILING);
                opBalMap.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_EXP_ORIGINAL_ESTIMATE,
                        revEsmtAmt.toString());
                if (accountBudgetProjectedExpenditureEntity.getExpenditureAmt() == null) {
                    opBalMap.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_EXP_BALANCE_PROVISION,
                            revEsmtAmt.toString());
                } else {
                    BigDecimal expAmt = new BigDecimal(accountBudgetProjectedExpenditureEntity.getExpenditureAmt().toString());
                    expAmt = expAmt.setScale(2, RoundingMode.CEILING);
                    opBalMap.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_EXP_BALANCE_PROVISION,
                            revEsmtAmt.subtract(expAmt).toString());
                }
            }
        }
        return opBalMap;
    }

    /**
     * @param model
     */
    @RequestMapping(params = "getDetails", method = RequestMethod.POST)
    private @ResponseBody List<AccountTenderDetBean> populateListOfAccountBudgetgetProjectedExpenditureItems(
            @RequestParam("tenderEntryDate") final String tenderEntryDate,
            @RequestParam("pacHeadId") final Long pacHeadId,
            @RequestParam("sacHeadId") final Long sacHeadid,
            @RequestParam("fundId") final Long fundId,
            @RequestParam("fieldId") final Long feildId,
            @RequestParam("functionId") final Long functionId, final Model model) {

        final List<AccountTenderDetBean> tenderDeatilBeanList = new ArrayList<>();
        UserSession.getCurrent().getOrganisation().getOrgid();
        final Date date = UtilityService
                .convertStringDateToDateFormat(tenderEntryDate);
        return tenderDeatilBeanList;
    }

    private void populateAddVendorList(final Model model) {
        final Map<Long, String> vendorMap = new HashMap<>();
        String vendorName;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

        final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                AccountConstants.AC.getValue(), PrefixConstants.VSS, UserSession.getCurrent().getLanguageId(),
                UserSession.getCurrent().getOrganisation());
        final Long vendorStatus = lookUpVendorStatus.getLookUpId();
        final List<TbAcVendormaster> vendorList = tbAcVendormasterService.getActiveVendors(orgId, vendorStatus);
        // final List<TbAcVendormaster> vendorList = VendormService.findAll(orgId);
        if ((vendorList != null) && !vendorList.isEmpty()) {
            for (final TbAcVendormaster vendor : vendorList) {

                vendorName = vendor.getVmVendorcode()
                        + MainetConstants.SEPARATOR
                        + vendor.getVmVendorname();
                vendorMap.put(vendor.getVmVendorid(), vendorName);
            }
        }
        model.addAttribute(VENDOR_MAP, vendorMap);
    }

    private void populateVendorList(final Model model) {

        final Map<Long, String> vendorMap = new HashMap<>();
        String vendorName;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<TbAcVendormaster> vendorList = tbAcVendormasterService.findAll(orgId);
        if ((vendorList != null) && !vendorList.isEmpty()) {

            for (final TbAcVendormaster vendor : vendorList) {

                vendorName = vendor.getVmVendorcode()
                        + MainetConstants.SEPARATOR
                        + vendor.getVmVendorname();
                vendorMap.put(vendor.getVmVendorid(), vendorName);
            }
        }
        model.addAttribute(VENDOR_MAP, vendorMap);

    }

    private void checkingBudgetDefParameter(Model model) {
        final List<LookUp> budgetParametersLookUpList = CommonMasterUtility.getListLookup(PrefixConstants.BDP,
                UserSession.getCurrent().getOrganisation());
        if (budgetParametersLookUpList != null) {
            for (final LookUp lookUp : budgetParametersLookUpList) {
                if (lookUp != null) {
                    if (lookUp.getLookUpCode().equals(PrefixConstants.WORK_ORDER_ENTRY_CPD_VALUE)) {
                        model.addAttribute(MainetConstants.AccountBillEntry.BUDGET_PARAMETERS_STATUS,
                                MainetConstants.MASTER.Y);
                    }
                }
            }
        }
    }
}
