package com.abm.mainet.account.ui.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.dto.AccountCashDepositeBean;
import com.abm.mainet.account.dto.AccountChequeDishonourDTO;
import com.abm.mainet.account.dto.AccountChequeOrCashDepositeBean;
import com.abm.mainet.account.service.AccountChequeDishonourService;
import com.abm.mainet.account.service.AccountChequeOrCashDepositeService;
import com.abm.mainet.account.ui.model.AccountChequeAndDepositeModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.dto.BankAccountMasterDto;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.TbBankmasterService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.DepartmentLookUp;
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
@RequestMapping("/AccountChequeDishonour.html")
public class AccountChequeDishonourController extends AbstractController {

    private static final String MAIN_ENTITY_NAME = "tbAccountChequeDishonour";
    private static final String MAIN_LIST_NAME = "list";
    private static final String JSP_FORM = "tbAccountChequeDishonour/form";
    private static final String JSP_VIEW = "tbAccountChequeDishonour/view";
    private static final String JSP_LIST = "tbAccountChequeDishonour/list";
    private static final String MAIN_LIST_NAME_DEPOSIT_SLIP = "accountChequeOrCashDepositeBean";
    private String modeView = MainetConstants.CommonConstant.BLANK;

    @Resource
    private AccountChequeDishonourService accountChequeDishonourService;
    @Resource
    private AccountChequeOrCashDepositeService chequeOrCashService;
    @Resource
    private AccountFieldMasterService accountFieldMasterService;
    @Resource
    private TbBankmasterService bankAccountService;
    @Resource
    private ILocationMasService locMasService;

    private List<AccountChequeDishonourDTO> chList = null;

    public AccountChequeDishonourController() {
        super(AccountChequeDishonourController.class, MAIN_ENTITY_NAME);
        log("AccountChequeDishonourController created.");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(final HttpServletRequest request,
            final Model model) {
        log("AccountChequeDishonourController-'gridData' : 'Get grid Data'");
        final JQGridResponse response = new JQGridResponse<>();
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        response.setRows(chList);
        response.setTotal(chList.size());
        response.setRecords(chList.size());
        response.setPage(page);
        model.addAttribute(MAIN_LIST_NAME, chList);
        return response;
    }

    @RequestMapping(params = "getjqGridPayInSlipChequeDDNoSearch", method = RequestMethod.POST)
    public @ResponseBody List getCheqData(final HttpServletRequest request, final Model model,
            @RequestParam("number") final String number,
            @RequestParam("date") final String date, @RequestParam("amount") final String amount,
            @RequestParam("bankAccount") final String bankAccount, @RequestParam("serchType") final String serchType) {
        log("AccountChequeDishonourController-'getjqGridsearch' : 'get jqGrid search data'");
        chList = new ArrayList<>();
        chList.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        if (serchType.equals(MainetConstants.MENU.P)) {
            Long bankAccountId = null;
            Date depositSlipDate = null;
            if ((bankAccount != null) && !bankAccount.isEmpty()) {
                bankAccountId = Long.valueOf(bankAccount);
            }
            if ((date != null) && !date.isEmpty()) {
                depositSlipDate = Utility.stringToDate(date);
            }
            BigDecimal depositSlipAmt = null;
            if ((amount != null) && !amount.isEmpty()) {
                depositSlipAmt = new BigDecimal(amount);
            }
            chList = accountChequeDishonourService.findByAllGridPayInSlipSearchData(number, depositSlipDate, depositSlipAmt,
                    bankAccountId, orgId);
        } else {
            if (serchType.equals(MainetConstants.MENU.D)) {
                Long bankAccountId = null;
                Date depositSlipDate = null;
                if ((bankAccount != null) && !bankAccount.isEmpty()) {
                    bankAccountId = Long.valueOf(bankAccount);
                }
                if ((date != null) && !date.isEmpty()) {
                    depositSlipDate = Utility.stringToDate(date);
                }
                BigDecimal receiptAmt = null;
                if ((amount != null) && !amount.isEmpty()) {
                    receiptAmt = new BigDecimal(amount);
                }
                chList = accountChequeDishonourService.findByAllGridChequeDDNoSearchData(Long.valueOf(number), depositSlipDate, receiptAmt,
                        bankAccountId, orgId);
            }
        }
        return chList;
    }

    @RequestMapping()
    public String getList(final Model model) throws Exception {
        log("AccountChequeDishonourController-'list' :'list'");
        String result = MainetConstants.CommonConstant.BLANK;
        chList = new ArrayList<>();
        chList.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Map<Long, String> bankAccountlist = new LinkedHashMap<>();
        bankAccountlist = accountChequeDishonourService.getBankAccountData(orgId);
        final AccountChequeDishonourDTO bean = new AccountChequeDishonourDTO();
        bean.setSerchType(MainetConstants.MENU.P);
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.BANK_AC_LIST, bankAccountlist);
        result = JSP_LIST;
        return result;
    }

    @RequestMapping(params = "form")
    public String formForCreate(final HttpServletRequest request, final Model model) throws Exception {
        log("AccountChequeDishonourController-'formForCreate' : 'formForCreate'");
        String result = MainetConstants.CommonConstant.BLANK;
        @SuppressWarnings("unchecked")
        final List<AccountChequeDishonourDTO> chList = (List<AccountChequeDishonourDTO>) request.getSession()
                .getAttribute(MainetConstants.BankReconciliation.CH_LIST);
        final AccountChequeDishonourDTO bean = new AccountChequeDishonourDTO();
        bean.setChequeDishonourDtoList(chList);
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        result = JSP_FORM;
        return result;
    }

    private void populateModel(final Model model, final AccountChequeDishonourDTO bean, final FormMode formMode) {
        log("AccountChequeDishonourController-'populateModel' : populate model");
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        if (formMode == FormMode.CREATE) {
            model.addAttribute(MainetConstants.TAX_HEAD_MAPPING_MASTER.MODE_DATA, MainetConstants.Actions.CREATE);
            model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, false);
        } else if (formMode == FormMode.UPDATE) {
            model.addAttribute(MainetConstants.TAX_HEAD_MAPPING_MASTER.MODE_DATA, MainetConstants.Actions.UPDATE);
        }
    }

    public AccountChequeAndDepositeModel getModel() {
        return ApplicationContextProvider.getApplicationContext().getBean(AccountChequeAndDepositeModel.class);
    }

    @RequestMapping(params = "viewData")
    public String displayDepositeSlipData(final Model model, final HttpServletRequest request,
            @RequestParam(value = "chequeDishonourId") final Long chequeDishonourId) {
        log("AccountChequeOrCashDepositeController 'create Data form'");
        getModel().setViewMode(true);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.IS_VIEW_MODE, true);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.IS_VIEW_MODE, getModel().isViewMode());
        String page = null;

        final AccountChequeOrCashDepositeBean chequeOrCashBean = chequeOrCashService
                .getSlipDetailsUsingDepSlipId(chequeDishonourId);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.CHEQUE_OR_CASH, chequeOrCashBean.getDepositeType());

        final List<TbServiceReceiptMasBean> listOfDraweeBank = chequeOrCashService.getDraweeBankDetailsView(
                chequeOrCashBean.getDepositeSlipId(), UserSession.getCurrent().getOrganisation().getOrgid(),
                chequeOrCashBean.getCoTypeFlag(), chequeOrCashBean.getDepositeType());
        chequeOrCashBean.setListOfChequeDDPoDetails(listOfDraweeBank);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.CHEQUE_OR_DD, true);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.RECEIPT_DETAIL, listOfDraweeBank);

        if (chequeOrCashBean.getSfeeMode() != null) {
            chequeOrCashBean.setDepositeType(String.valueOf(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(Long.valueOf(chequeOrCashBean.getSfeeMode())).getLookUpDesc()));
        }
        populateDenominations(model, chequeOrCashBean);
        model.addAttribute(MAIN_LIST_NAME_DEPOSIT_SLIP, chequeOrCashBean);
        page = JSP_VIEW;
        return page;
    }

    public void populateDenominations(final Model model, final AccountChequeOrCashDepositeBean chequeOrCashDepBean) {
        final UserSession userSession = UserSession.getCurrent();
        final Long statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.PaymentMode.USER_ADUSTMENT,
                PrefixConstants.BAS, userSession.getOrganisation().getOrgid());
        final List<Object[]> accountsList = bankAccountService.getActiveBankAccountList(userSession.getOrganisation().getOrgid(),
                statusId);
        final List<BankAccountMasterDto> accountList = new ArrayList<>();
        BankAccountMasterDto account = null;
        for (final Object[] obj : accountsList) {
            account = new BankAccountMasterDto();
            account.setBaAccountId((Long) obj[0]);
            account.setBaAccountName(obj[2].toString());
            account.setFundId((Long) obj[4]);
            account.setBaAccountNo(obj[1].toString());
            accountList.add(account);
        }
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.BANK_LIST, accountList);
        final List<LookUp> permanentPayList = CommonMasterUtility.getLookUps(MainetConstants.CHEQUE_DISHONOUR.PAY,
                userSession.getOrganisation());
        final List<LookUp> payList = new ArrayList<>();
        for (final LookUp payLookup : permanentPayList) {
            if (MainetConstants.MENU.D.equals(payLookup.getLookUpCode())
                    || MainetConstants.MODE_CREATE.equals(payLookup.getLookUpCode())
                    || PrefixConstants.CHEQUE_DISHONOUR.PREFIX_CODE_Q.equals(payLookup.getLookUpCode())
                    || MainetConstants.MENU.P.equals(payLookup.getLookUpCode())) {
                payList.add(payLookup);
            }
        }
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.PERMANENT_PAY_LIST, payList);

        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final int langId = UserSession.getCurrent().getLanguageId();
        final LookUp statusDeptLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.ChequeDishonour.DWR,
                PrefixConstants.ChequeDishonour.CFD, langId, organisation);
        if (statusDeptLookup != null && StringUtils.isNotBlank(statusDeptLookup.getOtherField())) {
            if (statusDeptLookup.getOtherField().equals(MainetConstants.MENU.Y)) {
                final List<DepartmentLookUp> deptList = CommonMasterUtility
                        .getDepartmentForWS(UserSession.getCurrent().getOrganisation());

                model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.DEPT_LIST, deptList);
                model.addAttribute(MainetConstants.AccountBudgetCode.DEPT_STATUS, MainetConstants.MENU.Y);
            }
        }
        final LookUp statusFieldLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.ChequeDishonour.FLR,
                PrefixConstants.ChequeDishonour.CFD, langId, organisation);
        if (statusFieldLookup != null && StringUtils.isNotBlank(statusFieldLookup.getOtherField())) {
            if (statusFieldLookup.getOtherField().equals(MainetConstants.MENU.Y)) {
                model.addAttribute(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.FIELD_MAS_LAST_LVL,
                        accountFieldMasterService.getFieldMasterStatusLastLevels(
                                UserSession.getCurrent().getOrganisation().getOrgid(), UserSession.getCurrent().getOrganisation(),
                                UserSession.getCurrent().getLanguageId()));
                model.addAttribute(MainetConstants.FIELD_MASTER.FIELD_STATUS, MainetConstants.MENU.Y);
            }
        }
        if (!getModel().isViewMode()) {
            final List<LookUp> denLookupList = CommonMasterUtility.getLookUps(AccountPrefix.DEN.toString(),
                    userSession.getOrganisation());
            model.addAttribute(MainetConstants.CONTRA_VOUCHER_ENTRY.DEN_LOOKUP_LIST, denLookupList);
            final Iterator<LookUp> denListItr = denLookupList.iterator();
            Iterator<AccountCashDepositeBean> CashDep = null;
            if (chequeOrCashDepBean.getCashDep() != null) {
                CashDep = chequeOrCashDepBean.getCashDep().iterator();
            }
            final List<AccountCashDepositeBean> listOfBean = new ArrayList<>();
            AccountCashDepositeBean bean = null;
            if ((denListItr != null) && (CashDep != null)) {
                while (denListItr.hasNext()) {
                    while (CashDep.hasNext()) {
                        final AccountCashDepositeBean CashDep2 = CashDep.next();
                        final LookUp lkp = denListItr.next();
                        bean = new AccountCashDepositeBean();
                        bean.setDenomination(CashDep2.getDenomination());
                        bean.setTbComparamDet(lkp.getLookUpId());
                        bean.setDenomDesc(lkp.getLookUpCode());
                        listOfBean.add(bean);
                    }
                }
            } else {
                if (denListItr != null) {
                    while (denListItr.hasNext()) {
                        final LookUp lkp = denListItr.next();
                        bean = new AccountCashDepositeBean();
                        bean.setTbComparamDet(lkp.getLookUpId());
                        bean.setDenomDesc(lkp.getLookUpCode());
                        listOfBean.add(bean);
                    }
                }
            }
            chequeOrCashDepBean.setCashDep(listOfBean);
        }
        model.addAttribute(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.FIELD_MAS_LAST_LVL,
                accountFieldMasterService.getFieldMasterStatusLastLevels(userSession.getOrganisation().getOrgid(),
                        userSession.getOrganisation(), userSession.getLanguageId()));
        ;

    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String create(final AccountChequeDishonourDTO tbAccountChequeDishonour, final BindingResult bindingResult,
            final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws Exception {
        log("AccountChequeDishonourDTO-'create' : 'create'");
        String result = MainetConstants.CommonConstant.BLANK;
        if (!bindingResult.hasErrors()) {

            tbAccountChequeDishonour.setHasError(MainetConstants.MENU.FALSE);
            final UserSession userSession = UserSession.getCurrent();
            tbAccountChequeDishonour.setOrgid(userSession.getOrganisation().getOrgid());
            tbAccountChequeDishonour.setLangId(userSession.getLanguageId());
            tbAccountChequeDishonour.setUserId(userSession.getEmployee().getEmpId());
            tbAccountChequeDishonour.setLmoddate(new Date());
            tbAccountChequeDishonour.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
            final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            populateModel(model, tbAccountChequeDishonour, FormMode.CREATE);
            long fieldId = 0;
            if (UserSession.getCurrent().getLoggedLocId() != null) {
                final TbLocationMas locMas = locMasService
                        .findById(UserSession.getCurrent().getLoggedLocId());
                if ((locMas.getLocRevenueWZMappingDto() != null) && !locMas.getLocRevenueWZMappingDto().isEmpty()) {
                    fieldId = locMas.getLocRevenueWZMappingDto().get(0).getCodIdRevLevel1();
                }
            }
            if (fieldId == 0) {
                throw new NullPointerException("fieldId is not linked with Location Master for[locId="
                        + UserSession.getCurrent().getEmployee().getTbLocationMas().getLocId() + ",locName="
                        + UserSession.getCurrent().getEmployee().getTbLocationMas().getLocNameEng() + "]");
            }
            final int langId = userSession.getLanguageId();
            final Long empId = userSession.getEmployee().getEmpId();
            AccountChequeDishonourDTO tbAccountChequeDishonourCreated = accountChequeDishonourService
                    .saveAccountChequeDishonourFormData(tbAccountChequeDishonour, orgId, fieldId, langId, empId,
                            Utility.getClientIpAddress(httpServletRequest));
            if (tbAccountChequeDishonourCreated == null) {
                tbAccountChequeDishonourCreated = new AccountChequeDishonourDTO();
            }
            model.addAttribute(MAIN_ENTITY_NAME, tbAccountChequeDishonourCreated);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
            ApplicationSession.getInstance().getMessage("accounts.fieldmaster.update"));
            result = JSP_FORM;
        } else {
            tbAccountChequeDishonour.setHasError(MainetConstants.MENU.TRUE);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.VOUCHER_TEMPLATE_ENTRY.COMMAND, bindingResult);
            populateModel(model, tbAccountChequeDishonour, FormMode.CREATE);
            result = JSP_FORM;
        }
        return result;
    }

    @RequestMapping(params = "update", method = RequestMethod.POST)
    public String update(AccountChequeDishonourDTO tbAccountChequeDishonour,
            @RequestParam("chequeDishonourId") final Long chequeDishonourId,
            @RequestParam("chequedddate") final String chequedddate, @RequestParam("id") final Long id,
            @RequestParam("bankAccount") final String bankAccount,
            @RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {
        log("AccountChequeDishonourDTO-'gridData' : 'update'");
        String result = MainetConstants.CommonConstant.BLANK;
        if (!bindingResult.hasErrors()) {
            if (viewmode.equals(MainetConstants.EDIT)) {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.EDIT);
            } else {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
            }
            setModeView(viewmode);
            tbAccountChequeDishonour.setChequeDishonourId(chequeDishonourId);
            tbAccountChequeDishonour.setBankAccount(bankAccount);
            tbAccountChequeDishonour.setDishonourIds(id.toString());
            tbAccountChequeDishonour.setChequedddate(chequedddate);
            model.addAttribute(MAIN_ENTITY_NAME, tbAccountChequeDishonour);
            populateModel(model, tbAccountChequeDishonour, FormMode.UPDATE);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            log("AccountChequeDishonourDTO 'update' : update done - redirect");
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            log("AccountChequeDishonourDTO 'update' : update done - redirect");
            result = JSP_FORM;
        } else {
            log("AccountChequeDishonourDTO 'update' : binding errors");
            populateModel(model, tbAccountChequeDishonour, FormMode.UPDATE);
            result = JSP_FORM;
        }
        return result;
    }

    public String getModeView() {
        return modeView;
    }

    public void setModeView(final String modeView) {
        this.modeView = modeView;
    }
}