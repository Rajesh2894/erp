package com.abm.mainet.account.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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

import com.abm.mainet.account.domain.TbAcChequebookleafDetEntity;
import com.abm.mainet.account.dto.AccountContraVoucherCashDepBean;
import com.abm.mainet.account.dto.AccountContraVoucherEntryBean;
import com.abm.mainet.account.dto.AccountContraVoucherReportAccountheadBean;
import com.abm.mainet.account.dto.AccountContraVoucherReportBean;
import com.abm.mainet.account.dto.VoucherTemplateDTO;
import com.abm.mainet.account.service.AccountContraVoucherEntryService;
import com.abm.mainet.account.service.BudgetCodeService;
import com.abm.mainet.account.service.TbAcChequebookleafMasService;
import com.abm.mainet.account.service.VoucherTemplateService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.TbBankmasterService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;

/** @author tejas.kotekar */
@Controller
@RequestMapping("/ContraVoucherEntry.html")
public class AccountContraVoucherEntryController extends AbstractController {

    private final String JSP_FORM = "ContraVoucherEntry/form";
    private final String JSP_VIEW_FORM = "ContraVoucherEntry/viewform";
    private final String JSP_LIST = "ContraVoucherEntry/list";
    private final String SAVE_ACTION_CREATE = "ContraVoucherEntry?create";
    private final String JSP_REPORT = "ContraVoucherEntry/Report";

    private final static String CONTRA_VOUCHER_BEAN = "contraVoucherBean";

    private final String BANK_AC_MAP = "bankAccountMap";

    private final String CHEQUEBOOK_MAP = "chequeBookMap";

    private final String PAY_MODE_MAP = "payModeMap";

    private final String PAY_MAP_CHEQUE_WITH = "payMapChequeWith";

    private final String PAY_MAP_CASH = "payMapCash";

    private final String DEN_LOOKUP_LIST = "denLookupList";

    private final String ERR_TRUE = "errTrue";

    private final String ERR_FALSE = "errFalse";

    private final String CHEQUE_NO_MAP = "chequeNoMap";

    List<AccountContraVoucherEntryBean> masterBeanList = null;

    @Resource
    AccountContraVoucherEntryService accountContraVoucherEntryService;
    @Resource
    private VoucherTemplateService voucherTemplateService;
    @Resource
    private TbBankmasterService banksMasterService;
    @Resource
    private BudgetCodeService budgetCodeService;

    @Resource
    private ILocationMasService locMasService;

    @Resource
    private TbAcChequebookleafMasService chequebookleafMasService;

    @Resource
    private DepartmentService departmentService;

    public AccountContraVoucherEntryController() {
        super(AccountContraVoucherEntryController.class, CONTRA_VOUCHER_BEAN);
        log("AccountContraVoucherEntryController created.");
    }

    @RequestMapping()
    public String populateGridList(final Model model) {
        log("Action 'form'");
        masterBeanList = new ArrayList<>();
        final AccountContraVoucherEntryBean contraVoucherBean = new AccountContraVoucherEntryBean();
        populateModel(model, contraVoucherBean, MODE_CREATE);
        masterBeanList.clear();
        return JSP_LIST;
    }

    @RequestMapping(params = "formForCreate", method = RequestMethod.POST)
    public String formForCreateContraVoucher(final Model model) {
        log("Action 'formForCreate'");
        final AccountContraVoucherEntryBean contraVoucherBean = new AccountContraVoucherEntryBean();
        populateModel(model, contraVoucherBean, MODE_CREATE);
        return JSP_FORM;
    }

    private void populateModel(final Model model, final AccountContraVoucherEntryBean contraVoucherBean,
            final String formMode) {

        model.addAttribute(CONTRA_VOUCHER_BEAN, contraVoucherBean);
        if (formMode.equals(MODE_CREATE)) {
            model.addAttribute(MODE, MODE_CREATE);
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
            populateListOfBanks(model);
            populatePaymentMode(model);
            populateDenominations(model, contraVoucherBean);
        } else {
            model.addAttribute(MODE, MODE_UPDATE);
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
            populateListOfBanks(model);
            populatePaymentMode(model);
            populateDenominations(model, contraVoucherBean);
        }
    }

    @SuppressWarnings("unlikely-arg-type")
    @RequestMapping(params = "searchContraEntry")
    public @ResponseBody List<AccountContraVoucherEntryBean> getBillEntryData(
            @RequestParam("fromDate") final String fromDate, @RequestParam("toDate") final String toDate,
            @RequestParam("transactionType") final Character transactionType,
            @RequestParam("transactionNo") final Long transactionNo, final HttpServletRequest request,
            final Model model) {

        masterBeanList = new ArrayList<>();
        masterBeanList.clear();
        AccountContraVoucherEntryBean masterBean = null;
        Set<Object[]> contraEntryList = null;
        Character contraType = null;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        if ((fromDate != MainetConstants.BLANK) || (toDate != MainetConstants.BLANK) || (transactionNo != null)
                || (transactionType != null)) {
            contraEntryList = accountContraVoucherEntryService.getContraEntryDetails(orgId, fromDate, toDate,
                    transactionNo, transactionType);
        } else {
            contraEntryList = accountContraVoucherEntryService.getAllContraEntryData(orgId);
        }
        for (final Object[] contraList : contraEntryList) {
            masterBean = new AccountContraVoucherEntryBean();
            masterBean.setCoTranId((Long) contraList[0]);
            masterBean.setCoVouchernumber(contraList[1].toString());
            final Date date = (Date) contraList[2];
            masterBean.setCoEntryDateStr(UtilityService.convertDateToDDMMYYYY(date));
            // masterBean.setCpdModePay((Long) contraList[3]);
            final String amount = CommonMasterUtility
                    .getAmountInIndianCurrency(new BigDecimal(contraList[3].toString()));
            masterBean.setAmountStr(amount);
            if (transactionType != null) {
                if (transactionType.equals(MainetConstants.AccountContraVoucherEntry.W)) {
                    contraType = MainetConstants.AccountContraVoucherEntry.W;
                } else {
                    contraType = (Character) contraList[4];
                }
            }
            if (contraType.equals(MainetConstants.AccountContraVoucherEntry.T)) {
                masterBean.setContraType(MainetConstants.AccountContraVoucherEntry.TRANSFER_ENTRY);
            }
            if (contraType.equals(MainetConstants.AccountContraVoucherEntry.W)) {
                masterBean.setContraType(MainetConstants.AccountContraVoucherEntry.CASH_WITHDRAW_ENTRY);
            }
            if (contraType.equals(MainetConstants.AccountContraVoucherEntry.D)) {
                masterBean.setContraType(MainetConstants.AccountContraVoucherEntry.CASH_DEPOSIT_ENTRY);
            }
            masterBeanList.add(masterBean);
        }
        return masterBeanList;
    }

    @RequestMapping(params = "getGridData")
    public @ResponseBody JQGridResponse<AccountContraVoucherEntryBean> gridData(final HttpServletRequest request,
            final Model model) {

        final int page = Integer.parseInt(request.getParameter(AccountConstants.PAGE.getValue()));
        final JQGridResponse<AccountContraVoucherEntryBean> response = new JQGridResponse<>();
        response.setRows(masterBeanList);
        response.setTotal(masterBeanList.size());
        response.setRecords(masterBeanList.size());
        response.setPage(page);
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, masterBeanList);
        return response;
    }

    private void populateListOfBanks(final Model model) {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<Object[]> bankAccountList = new ArrayList<>();
        final Map<Long, String> bankAccountMap = new HashMap<>();
        final Long statusId = CommonMasterUtility
                .lookUpIdByLookUpCodeAndPrefix(PrefixConstants.PaymentMode.USER_ADUSTMENT, PrefixConstants.BAS, orgId);
        bankAccountList = banksMasterService.getActiveBankAccountList(orgId, statusId);
        if ((bankAccountList != null) && !bankAccountList.isEmpty()) {
            for (final Object[] bankAc : bankAccountList) {
                bankAccountMap.put((Long) bankAc[0],
                        bankAc[3] + MainetConstants.SEPARATOR + bankAc[1] + MainetConstants.SEPARATOR + bankAc[2]);
            }
        }
        model.addAttribute(BANK_AC_MAP, bankAccountMap);
    }

    @RequestMapping(params = "getChequeNumbers")
    public @ResponseBody Map<Long, String> getChequeNumbers(final Model model,
            @RequestParam("bankAcId") final Long bankAcId) {

        Map<Long, String> chequeBookMap = null;
        final LookUp lkpStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                AccountConstants.NOT_ISSUED.getValue(), AccountPrefix.CLR.toString(),
                UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final Long statusId = lkpStatus.getLookUpId();
        final List<TbAcChequebookleafDetEntity> chequeList = chequebookleafMasService.getIssuedChequeNumbers(bankAcId,
                statusId);
        chequeBookMap = new HashMap<>();
        if ((chequeList != null) && !chequeList.isEmpty()) {
            for (final TbAcChequebookleafDetEntity en : chequeList) {
                chequeBookMap.put(en.getChequebookDetid(), en.getChequeNo());
            }
        }
        model.addAttribute(CHEQUEBOOK_MAP, chequeBookMap);
        return chequeBookMap;
    }

    public @ResponseBody Map<Long, String> getChequeNumbersForUpdateOrView(final Model model,
            @RequestParam("bankAcId") final Long bankAcId) {

        Map<Long, String> chequeBookMap = null;
        final List<TbAcChequebookleafDetEntity> chequeList = chequebookleafMasService.getChequeNumbers(bankAcId);
        chequeBookMap = new HashMap<>();
        if ((chequeList != null) && !chequeList.isEmpty()) {
            for (final TbAcChequebookleafDetEntity en : chequeList) {
                chequeBookMap.put(en.getChequebookDetid(), en.getChequeNo());
            }
        }
        model.addAttribute(CHEQUEBOOK_MAP, chequeBookMap);
        return chequeBookMap;
    }

    @RequestMapping(params = "getBankBalance")
    public @ResponseBody BigDecimal getBankBalance(final Model model, final Long bankId,
            @RequestParam("bankAcId") final Long bankAcId,
            @RequestParam("transactionDate") final String transactionDate) {
        BigDecimal bankBalance = null;
        if (bankAcId != null && (transactionDate != null && !transactionDate.isEmpty())) {
            Date date = Utility.stringToDate(transactionDate);
            Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
            bankBalance = accountContraVoucherEntryService.getBankBalance(bankAcId, date, orgid);
        }
        return bankBalance;
    }

    @RequestMapping(params = "getPettyCashAmount")
    public @ResponseBody BigDecimal getPettyCashAmountExist(final Model model,
            @RequestParam("transactionDate") final String transactionDate) {
        BigDecimal pettyCashBalance = null;
        if ((transactionDate != null && !transactionDate.isEmpty())) {
            Date date = Utility.stringToDate(transactionDate);
            Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
            Long cpdIdPayMode = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PCA.toString(),
                    AccountPrefix.PAY.toString(), orgid);
            Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DS.toString(),
                    AccountPrefix.TDP.toString(), orgid);
            Long voucherSubTypeId1 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CWE.toString(),
                    AccountPrefix.TDP.toString(), orgid);
            Long voucherSubTypeId2 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PVE.toString(),
                    AccountPrefix.TDP.toString(), orgid);
            Long voucherSubTypeId3 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PCD.toString(),
                    AccountPrefix.TDP.toString(), orgid);
            Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.CommonConstants.ACTIVE,
                    AccountPrefix.ACN.toString(), orgid);
            Long deptId = departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString());
            pettyCashBalance = accountContraVoucherEntryService.getPettyCashAmount(date, orgid, cpdIdPayMode,
                    voucherSubTypeId, voucherSubTypeId1, voucherSubTypeId2, voucherSubTypeId3, status, deptId);
            if (pettyCashBalance == null) {
                pettyCashBalance = new BigDecimal(0.00);
            }
        }
        return pettyCashBalance;
    }

    @RequestMapping(params = "getCashAmount")
    public @ResponseBody BigDecimal getCashAmountExist(final Model model,
            @RequestParam("transactionDate") final String transactionDate) {
        BigDecimal cashBalance = null;
        if ((transactionDate != null && !transactionDate.isEmpty())) {
            Date date = Utility.stringToDate(transactionDate);
            Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
            Long cpdIdPayMode = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PCA.toString(),
                    AccountPrefix.PAY.toString(), orgid);
            Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DS.toString(),
                    AccountPrefix.TDP.toString(), orgid);
            Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.CommonConstants.ACTIVE,
                    AccountPrefix.ACN.toString(), orgid);
            Long deptId = departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString());
            cashBalance = accountContraVoucherEntryService.getCashAmount(date, orgid, cpdIdPayMode, voucherSubTypeId,
                    status, deptId);
            if (cashBalance == null) {
                cashBalance = new BigDecimal(0.00);
            }
        }
        return cashBalance;
    }

    public void populatePaymentMode(final Model model) {

        final Organisation orgnaisation = UserSession.getCurrent().getOrganisation();
        final LookUp lkpBank = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.BANK.getValue(),
                AccountPrefix.PAY.toString(), UserSession.getCurrent().getLanguageId(), orgnaisation);
        final LookUp lkpCheque = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.CHEQUE.getValue(),
                AccountPrefix.PAY.toString(), UserSession.getCurrent().getLanguageId(), orgnaisation);
        final LookUp lkpCash = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.CASH.getValue(),
                AccountPrefix.PAY.toString(), UserSession.getCurrent().getLanguageId(), orgnaisation);
        final Map<Long, String> payMap = new HashMap<>();
        payMap.put(lkpBank.getLookUpId(), lkpBank.getDescLangFirst());
        payMap.put(lkpCheque.getLookUpId(), lkpCheque.getDescLangFirst());
        model.addAttribute(PAY_MODE_MAP, payMap);
        final Map<Long, String> payMapChequeWith = new HashMap<>();
        payMapChequeWith.put(lkpCheque.getLookUpId(), lkpCheque.getDescLangFirst());
        model.addAttribute(PAY_MAP_CHEQUE_WITH, payMapChequeWith);
        final Map<Long, String> payMapCash = new HashMap<>();
        payMapCash.put(lkpCash.getLookUpId(), lkpCash.getDescLangFirst());
        model.addAttribute(PAY_MAP_CASH, payMapCash);
    }

    public void populateDenominations(final Model model, final AccountContraVoucherEntryBean contraBean) {

        final List<LookUp> denLookupList = CommonMasterUtility.getLookUps(AccountPrefix.DEN.toString(),
                UserSession.getCurrent().getOrganisation());
        if ((contraBean.getDenominationList() != null) && !contraBean.getDenominationList().isEmpty()) {
            for (final LookUp lookUp : denLookupList) {
                lookUp.setLookUpExtraLongOne(0);
                for (final Object[] objArr : contraBean.getDenominationList()) {
                    if (lookUp.getLookUpId() == (long) objArr[0]) {
                        lookUp.setLookUpExtraLongOne((long) objArr[1]);
                    }
                }
            }
        }
        model.addAttribute(DEN_LOOKUP_LIST, denLookupList);
        final List<AccountContraVoucherCashDepBean> listOfBean = new ArrayList<>();
        if ((denLookupList != null) & !denLookupList.isEmpty()) {
            AccountContraVoucherCashDepBean cashDep = null;
            for (final LookUp lookup : denLookupList) {
                cashDep = new AccountContraVoucherCashDepBean();
                cashDep.setTbComparamDet(lookup.getLookUpId());
                cashDep.setDenomDesc(lookup.getLookUpCode());
                listOfBean.add(cashDep);
            }
            contraBean.setCashDep(listOfBean);
        }
    }

    @SuppressWarnings({ "unchecked", "unused" })
    @RequestMapping(params = "formForUpdateOrView", method = RequestMethod.POST)
    public String formForUpdate(final Model model, final HttpServletRequest request,
            @RequestParam("contraId") final Long contraId,
            @RequestParam(MainetConstants.TAX_HEAD_MAPPING_MASTER.MODE_DATA) final String viewmode) {
        log("Action 'formForUpdate'");
        if (viewmode.equals(MainetConstants.VIEW)) {
            model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.SUCCESS);
        } else {
            model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
        }
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final AccountContraVoucherEntryBean contraVoucherBean = new AccountContraVoucherEntryBean();
        final Object[] contraList = accountContraVoucherEntryService.getContraEntryDataById(contraId, orgId);
        final List<Object[]> arrList = (List<Object[]>) contraList[0];
        final List<Object[]> listTwo = (List<Object[]>) contraList[1];

        if (((arrList != null) && !arrList.isEmpty()) && (listTwo != null) && !listTwo.isEmpty()) {
            final Object[] objMasterArr = arrList.get(0);
            String amount = null;
            if (Arrays.asList(objMasterArr).contains(MainetConstants.AccountContraVoucherEntry.T)) {
                final Object[] masterData = arrList.get(0);
                final Object[] payAndRecieptArr = listTwo.get(0);

                contraVoucherBean.setCoVouchernumber( masterData[0].toString()); // Transaction
                                                                                           // Number
                final Date entryDate = (Date) masterData[1];
                contraVoucherBean.setCoDateStr(UtilityService.convertDateToDDMMYYYY(entryDate)); // Transaction
                                                                                                 // Date
                final String paymentModeDesc = CommonMasterUtility.findLookUpDesc(AccountPrefix.PAY.toString(),
                        UserSession.getCurrent().getOrganisation().getOrgid(), (long) masterData[2]);
                contraVoucherBean.setPaymentModeDesc(paymentModeDesc);// Mode
                // // Recpt amount
                amount = CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(masterData[3].toString()));
                contraVoucherBean.setAmountRecStr(amount); // Recpt amount
                contraVoucherBean.setBaAccountidRec((Long) masterData[4]); // Recpt
                                                                           // bank
                                                                           // acc
                contraVoucherBean.setCoTypeFlag((Character) masterData[6]); // Type
                                                                            // flag
                if ((masterData[5] != null) && !masterData[5].equals(MainetConstants.CommonConstant.BLANK)) {
                    contraVoucherBean.setPayTo((String) masterData[5]); // Pay
                                                                        // to
                }
                contraVoucherBean.setCoRemarkPay((String) payAndRecieptArr[0]); // Description
                contraVoucherBean.setBaAccountidPay((Long) payAndRecieptArr[1]); // Payment
                                                                                 // Bank
                                                                                 // acc
                contraVoucherBean.setChequebookDetid((Long) payAndRecieptArr[2]); // instrument
                                                                                  // no
                final Date chequeDate = (Date) payAndRecieptArr[3];
                amount = CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(masterData[3].toString()));
                // Payment Amount
                contraVoucherBean.setAmountPayStr(amount); // Payment Amount
                contraVoucherBean.setCoChequedateStr(UtilityService.convertDateToDDMMYYYY(chequeDate)); // instrument
                                                                                                        // date

                // Cheque nos
                final Map<Long, String> chequeNoMap = getChequeNumbersForUpdateOrView(model,
                        (Long) payAndRecieptArr[1]);
                model.addAttribute(CHEQUE_NO_MAP, chequeNoMap);
                populateModel(model, contraVoucherBean, MODE_UPDATE);

            }
            if (Arrays.asList(objMasterArr).contains(MainetConstants.AccountContraVoucherEntry.W)) {
                final Object[] masterData = arrList.get(0);
                final Object[] payAndRecieptArr = listTwo.get(0);
                contraVoucherBean.setCoVouchernumber(Long.toString((long) masterData[0])); // Transaction
                                                                                           // Number
                final Date entryDate = (Date) masterData[1];
                contraVoucherBean.setCoDateStr(UtilityService.convertDateToDDMMYYYY(entryDate)); // Transaction
                                                                                                 // Date
                amount = CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(masterData[2].toString()));
                contraVoucherBean.setAmountPayStr(amount); // Payment Amount
                // Payment Amount

                contraVoucherBean.setCoRemarkPay((String) payAndRecieptArr[0]); // Description
                contraVoucherBean.setBaAccountidPay((Long) payAndRecieptArr[1]); // Payment
                                                                                 // Bank
                                                                                 // acc
                contraVoucherBean.setChequebookDetid((Long) payAndRecieptArr[2]); // instrument
                                                                                  // no
                final Map<Long, String> chequeNoMap = getChequeNumbersForUpdateOrView(model,
                        (Long) payAndRecieptArr[1]);
                model.addAttribute(CHEQUE_NO_MAP, chequeNoMap);
                final Date chequeDate = (Date) payAndRecieptArr[3];
                contraVoucherBean.setCoChequedateStr(UtilityService.convertDateToDDMMYYYY(chequeDate)); // instrument
                                                                                                        // date
                final String paymentModeDesc = CommonMasterUtility.findLookUpDesc(AccountPrefix.PAY.toString(),
                        UserSession.getCurrent().getOrganisation().getOrgid(), (long) payAndRecieptArr[4]);
                // // Mode
                contraVoucherBean.setPaymentModeDesc(paymentModeDesc);// Mode
                contraVoucherBean.setCoTypeFlag((Character) masterData[3]);
                populateModel(model, contraVoucherBean, MODE_UPDATE);
            }
            if (Arrays.asList(objMasterArr).contains(MainetConstants.AccountContraVoucherEntry.D)) {
                final Object[] masterData = arrList.get(0);
                contraVoucherBean.setCoVouchernumber(Long.toString((long) masterData[0])); // Transaction
                                                                                           // Number
                final Date entryDate = (Date) masterData[1];
                contraVoucherBean.setCoDateStr(UtilityService.convertDateToDDMMYYYY(entryDate)); // Transaction
                                                                                                 // Date
                amount = CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(masterData[2].toString()));
                contraVoucherBean.setAmountRecStr(amount); // Recpt Amount

                contraVoucherBean.setCoRemarkRec((String) masterData[3]);
                contraVoucherBean.setBaAccountidRec((Long) masterData[4]);
                contraVoucherBean.setCoTypeFlag((Character) masterData[5]);
                // set denomination
                final List<AccountContraVoucherCashDepBean> denominationList = new ArrayList<>();
                contraVoucherBean.setDenominationList(listTwo);
                for (final Object[] objArr : listTwo) {
                    new AccountContraVoucherCashDepBean();
                }
                contraVoucherBean.setCashDep(denominationList);
                contraVoucherBean.setTotal(Double.valueOf(amount));
                populateModel(model, contraVoucherBean, MODE_UPDATE);
            }
        }
        return JSP_VIEW_FORM;
    }

    @RequestMapping(params = "checkTemplate")
    public @ResponseBody String checkTemplate(@RequestParam("contraType") final Character contraType) {

        String templateExistFlag = null;
        final VoucherTemplateDTO postDTO = new VoucherTemplateDTO();
        postDTO.setTemplateType(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.ContraVoucherEntry.PN,
                PrefixConstants.ContraVoucherEntry.MTP, UserSession.getCurrent().getOrganisation().getOrgid()));

        postDTO.setVoucherType(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.ContraVoucherEntry.CV,
                MainetConstants.AccountBillEntry.VOT, UserSession.getCurrent().getOrganisation().getOrgid()));

        postDTO.setDepartment(departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString()));

        if (contraType.equals(MainetConstants.AccountContraVoucherEntry.T)) {
            postDTO.setTemplateFor(
                    CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.ContraVoucherEntry.BTE,
                            PrefixConstants.REV_TYPE_CPD_VALUE, UserSession.getCurrent().getOrganisation().getOrgid()));
        }
        if (contraType.equals(MainetConstants.AccountContraVoucherEntry.W)) {
            postDTO.setTemplateFor(
                    CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.ContraVoucherEntry.CWE,
                            PrefixConstants.REV_TYPE_CPD_VALUE, UserSession.getCurrent().getOrganisation().getOrgid()));
        }
        if (contraType.equals(MainetConstants.AccountContraVoucherEntry.D)) {
            postDTO.setTemplateFor(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.ChequeDishonour.DS,
                    PrefixConstants.REV_TYPE_CPD_VALUE, UserSession.getCurrent().getOrganisation().getOrgid()));
        }
        final boolean existTempalte = voucherTemplateService.isTemplateExist(postDTO,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (!existTempalte) {
            templateExistFlag = MainetConstants.MENU.N;
        } else {
            templateExistFlag = MainetConstants.MENU.Y;
        }
        return templateExistFlag;
    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String createContraVoucher(@Valid final AccountContraVoucherEntryBean contraVoucherBean,
            final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
            final HttpServletRequest httpServletRequest) {
        log("Action 'create transfer entry'");
        final Organisation org = UserSession.getCurrent().getOrganisation();
        final Integer languageId = UserSession.getCurrent().getLanguageId();
        final LookUp lookUpCheque = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                AccountConstants.CHEQUE.getValue(), AccountPrefix.PAY.toString(), languageId, org);
         Long payModLookupId =null;
        if(contraVoucherBean.getCpdModePay()!=null) {
        	payModLookupId=	contraVoucherBean.getCpdModePay();
        }
        else {
        payModLookupId = lookUpCheque.getLookUpId();
        }
        contraVoucherBean.setSuccessfulFlag(MainetConstants.MASTER.Y);
        contraVoucherBean.setCoTypeFlag(contraVoucherBean.getCoTypeFlag());
        contraVoucherBean.setOrgId(org.getOrgid());
        contraVoucherBean.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        contraVoucherBean.setCreatedDate(new Date());
        if (contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.T)) {
            String transDate = contraVoucherBean.getTransactionDate().substring(0,
                    contraVoucherBean.getTransactionDate().lastIndexOf(","));
            contraVoucherBean.setCoEntryDate(Utility.stringToDate(transDate));
        } else {
            contraVoucherBean.setCoEntryDate(Utility.stringToDate(contraVoucherBean.getTransactionDate()));
        }
        contraVoucherBean.setLangId(Long.valueOf(languageId.toString()));
        contraVoucherBean.setLgIpMac(Utility.getMacAddress());
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
        contraVoucherBean.setFieldId(fieldId);
        AccountContraVoucherEntryBean bean = accountContraVoucherEntryService.createContraEntry(contraVoucherBean,
                payModLookupId, org);
        httpServletRequest.getSession().setAttribute(MainetConstants.AccountContraVoucherEntry.ACCOUNT_CONTRA_LIST,
                bean);
        model.addAttribute(ERR_FALSE, false);
        model.addAttribute(ERR_TRUE, true);
        populateModel(model, contraVoucherBean, MODE_CREATE);
        return JSP_FORM;
    }

    @RequestMapping(params = "contraVoucherReport", method = RequestMethod.POST)
    public String ReportContraVoucher(@Valid final AccountContraVoucherEntryBean contraVoucherBean,
            final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
            final HttpServletRequest httpServletRequest) {
        log("Action 'create transfer entry'");
        final AccountContraVoucherReportBean oaccountBean = new AccountContraVoucherReportBean();
        final Organisation org = UserSession.getCurrent().getOrganisation();
        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        final Organisation org1 = new Organisation();
        org1.setOrgid(orgid);

        final AccountContraVoucherEntryBean contraVoucherEntryBean = (AccountContraVoucherEntryBean) httpServletRequest
                .getSession().getAttribute(MainetConstants.AccountContraVoucherEntry.ACCOUNT_CONTRA_LIST);

        oaccountBean.setBankBalance(contraVoucherEntryBean.getBankBalance());
        oaccountBean.setCoVouchernumber(contraVoucherEntryBean.getVoucherNo());
        oaccountBean.setNarration(contraVoucherBean.getCoRemarkPay());

        final LookUp lkp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.ContraVoucherEntry.CV,
                MainetConstants.AccountBillEntry.VOT, org);
        oaccountBean.setVoucherType(lkp.getDescLangFirst());
        final Character contraId = contraVoucherEntryBean.getCoTypeFlag();
        LookUp voucherSubTypeLookUp = null;
        if (contraId.equals(MainetConstants.AccountContraVoucherEntry.T)) {
            voucherSubTypeLookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.ContraVoucherEntry.BTE,
                    PrefixConstants.REV_TYPE_CPD_VALUE, org);
        } else if (contraId.equals(MainetConstants.AccountContraVoucherEntry.W)) {
            voucherSubTypeLookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.ContraVoucherEntry.CWE,
                    PrefixConstants.REV_TYPE_CPD_VALUE, org);
        } else if (contraId.equals(MainetConstants.AccountContraVoucherEntry.D)) {
            voucherSubTypeLookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.ChequeDishonour.DS,
                    PrefixConstants.REV_TYPE_CPD_VALUE, org);
        }
        oaccountBean.setVouchersubType(voucherSubTypeLookUp.getDescLangFirst());
        oaccountBean.setCoAmountPay(contraVoucherEntryBean.getCoAmountPay());
        final String deptDesc = departmentService
                .fetchDepartmentDescById(departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString()));
        oaccountBean.setDepartmentDesc(deptDesc);

        final List<AccountContraVoucherReportAccountheadBean> acList = new ArrayList<>();
        final AccountContraVoucherReportAccountheadBean acBean = new AccountContraVoucherReportAccountheadBean();
        Long budgetCodeIdCr = null;
        Long budgetCodeIdDr = null;
        if (contraId.equals(MainetConstants.AccountContraVoucherEntry.T)) {

            oaccountBean.setCoEntryDateStr(Utility.dateToString(contraVoucherEntryBean.getCoEntryDate()));
            acBean.setAccountPaymentHead(contraVoucherEntryBean.getPayee());
            acBean.setAmountPaymentCredit(contraVoucherEntryBean.getCoAmountPay());
            acBean.setAmountPaymentDebit(new BigDecimal(MainetConstants.ZERO));

            acBean.setAccountRecieptHead(contraVoucherEntryBean.getPayTo());
            acBean.setAmountRecieptDebit(contraVoucherEntryBean.getCoAmountPay());
            acBean.setAmountRecieptCredit(new BigDecimal(MainetConstants.ZERO));

            budgetCodeIdCr = accountContraVoucherEntryService.getSacHeadId(contraVoucherEntryBean.getBaAccountidPay(),
                    contraVoucherEntryBean.getOrgId());
            String accountCodeDesc = budgetCodeService.findAccountHeadCodeBySacHeadId(Long.valueOf(budgetCodeIdCr),
                    orgid);

            if (accountCodeDesc != null) {
                acBean.setBudgetCodeId(accountCodeDesc);
            }
            Long sacHeadId = accountContraVoucherEntryService.getSacHeadId(contraVoucherEntryBean.getBaAccountidRec(),
                    contraVoucherEntryBean.getOrgId());
            String accountHeadid = "";
            if (sacHeadId != null) {
                accountHeadid = budgetCodeService.findAccountHeadCodeBySacHeadId(sacHeadId, orgid);
            }

            if (accountHeadid != null) {
                acBean.setBudgetCode(accountHeadid.toString());
            }

            oaccountBean.setNarration(contraVoucherEntryBean.getCoRemarkPay());
            acList.add(acBean);
        }

        if (contraId.equals(MainetConstants.AccountContraVoucherEntry.W)) {

            oaccountBean.setCoEntryDateStr(contraVoucherEntryBean.getCoChequedateStr());

            acBean.setAccountPaymentHead(contraVoucherEntryBean.getPayTo());
            acBean.setAmountPaymentCredit(contraVoucherEntryBean.getCoAmountPay());
            acBean.setAmountPaymentDebit(new BigDecimal(MainetConstants.ZERO));

            acBean.setAccountRecieptHead(contraVoucherEntryBean.getPayTo());
            acBean.setAmountRecieptDebit(contraVoucherEntryBean.getCoAmountPay());
            acBean.setAmountRecieptCredit(new BigDecimal(MainetConstants.ZERO));

            budgetCodeIdCr = accountContraVoucherEntryService.getSacHeadId(contraVoucherEntryBean.getBaAccountidPay(),
                    contraVoucherEntryBean.getOrgId());

            String accountCodeDesc = budgetCodeService.findAccountHeadCodeBySacHeadId(Long.valueOf(budgetCodeIdCr),
                    orgid);

            if (accountCodeDesc != null) {
                acBean.setBudgetCodeId(accountCodeDesc);
            }

            final LookUp lkp1 = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.PCA.toString(),
                    AccountPrefix.PAY.toString(), Integer.parseInt(contraVoucherEntryBean.getLangId().toString()), org);
            final Long cpdIdPayMode = lkp1.getLookUpId();

            budgetCodeIdDr = accountContraVoucherEntryService.getBudgetCodeIdForPettyChash(
                    contraVoucherEntryBean.getBaAccountidPay(), contraVoucherEntryBean.getOrgId());

            String accountHeadDesc = budgetCodeService.findAccountHeadCodeBySacHeadId(Long.valueOf(budgetCodeIdCr),
                    orgid);

            Long modeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PCA.toString(),
                    AccountPrefix.PAY.toString(), contraVoucherEntryBean.getOrgId());
            Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CWE.toString(),
                    AccountPrefix.TDP.toString(), contraVoucherEntryBean.getOrgId());
            Long deptId = contraVoucherEntryBean.getDepartmentId();
            final Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    MainetConstants.CommonConstants.ACTIVE, AccountPrefix.ACN.toString(),
                    contraVoucherEntryBean.getOrgId());

            Long sacHeadId = voucherTemplateService.getSacHeadIdForVoucherTemplate(voucherSubTypeId, modeId, deptId,
                    status, contraVoucherEntryBean.getOrgId());
            String accountHeadid = "";
            if (sacHeadId != null) {
                accountHeadid = budgetCodeService.findAccountHeadCodeBySacHeadId(sacHeadId, orgid);
            }

            if (accountHeadid != null) {
                acBean.setBudgetCode(accountHeadid.toString());
            }

            oaccountBean.setNarration(contraVoucherEntryBean.getCoRemarkPay());
            acList.add(acBean);
        }

        if (contraId.equals(MainetConstants.AccountContraVoucherEntry.D)) {

            oaccountBean.setCoEntryDateStr(contraVoucherEntryBean.getTransactionDate());
            acBean.setAccountPaymentHead(contraVoucherEntryBean.getPayTo());
            acBean.setAmountPaymentCredit(contraVoucherEntryBean.getCoAmountRec());
            acBean.setAmountPaymentDebit(new BigDecimal(MainetConstants.ZERO));
            acBean.setAccountRecieptHead(contraVoucherEntryBean.getPayTo());
            acBean.setAmountRecieptDebit(contraVoucherEntryBean.getCoAmountRec());
            acBean.setAmountRecieptCredit(new BigDecimal(MainetConstants.ZERO));
            Long modeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PCA.toString(),
                    AccountPrefix.PAY.toString(), contraVoucherEntryBean.getOrgId());
            Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DS.toString(),
                    AccountPrefix.TDP.toString(), contraVoucherEntryBean.getOrgId());
            Long deptId = contraVoucherEntryBean.getDepartmentId();
            final Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    MainetConstants.CommonConstants.ACTIVE, AccountPrefix.ACN.toString(),
                    contraVoucherEntryBean.getOrgId());

            budgetCodeIdCr = voucherTemplateService.getSacHeadIdForVoucherTemplate(voucherSubTypeId, modeId, deptId,
                    status, contraVoucherEntryBean.getOrgId());
            String accountCodeDesc = "";
            if (budgetCodeIdCr != null) {
                accountCodeDesc = budgetCodeService.findAccountHeadCodeBySacHeadId(budgetCodeIdCr, orgid);
            }

            if (accountCodeDesc != null) {
                acBean.setBudgetCodeId(accountCodeDesc);
            }

            budgetCodeIdDr = accountContraVoucherEntryService.getSacHeadId(contraVoucherEntryBean.getBaAccountidRec(),
                    contraVoucherEntryBean.getOrgId());
            String accountcodeNew = "";
            if (budgetCodeIdDr != null) {
                accountcodeNew = budgetCodeService.findAccountHeadCodeBySacHeadId(budgetCodeIdDr, orgid);
            }

            if (accountcodeNew != null) {
                acBean.setBudgetCode(accountcodeNew.toString());
            }

            if (contraVoucherEntryBean.getCoRemarkPay() != null) {
                oaccountBean.setNarration(contraVoucherEntryBean.getCoRemarkPay());
            } else {
                oaccountBean.setNarration(contraVoucherEntryBean.getCoRemarkRec());
            }
            acList.add(acBean);
        }

        oaccountBean.setOacBeanList(acList);
        oaccountBean.setOrganizationNameReg(UserSession.getCurrent().getOrganisation().getONlsOrgnameMar());
        oaccountBean.setOrganizationName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
        if (contraVoucherEntryBean.getCoAmountPay() != null) {
            oaccountBean.setTotalAmount(contraVoucherEntryBean.getCoAmountPay());
        } else {
            oaccountBean.setTotalAmount(contraVoucherEntryBean.getCoAmountRec());
        }

        model.addAttribute(ERR_FALSE, false);
        model.addAttribute(ERR_TRUE, true);

        populateModelReport(model, oaccountBean, MODE_CREATE);
        return JSP_REPORT;
    }

    private void populateModelReport(final Model model, final AccountContraVoucherReportBean contraVoucherReportBean,
            final String formMode) {

        model.addAttribute(CONTRA_VOUCHER_BEAN, contraVoucherReportBean);
        if (formMode.equals(MODE_CREATE)) {
            model.addAttribute(MODE, MODE_CREATE);
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
            populateListOfBanks(model);
            populatePaymentMode(model);
        } else {
            model.addAttribute(MODE, MODE_UPDATE);
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
            populateListOfBanks(model);
            populatePaymentMode(model);
        }
    }

}
