package com.abm.mainet.account.ui.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.dto.ChequeIssueanceDTO;
import com.abm.mainet.account.service.AccountChequeDishonourService;
import com.abm.mainet.account.service.AccountChequeIssueanceService;
import com.abm.mainet.account.service.AccountContraVoucherEntryService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Organisation;
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
 * 
 * @author vishwanath.s
 *
 */

@Controller
@RequestMapping("/AccountChequeIssue.html")
public class AccountChequeIssueController extends AbstractController {

    private static final String MAIN_ENTITY_NAME = "tbBankReconciliation";
    private static final String MAIN_LIST_NAME = "list";
    private static final String JSP_FORM = "chequeIssue/form";
    private static final String JSP_LIST = "chequeIssue/list";
    private static final String JSP_SUMMARY = "chequeIssue/summary";
    private static final String JSP_SUMM = "chequeIssueCon/summary";
    private String modeView = MainetConstants.CommonConstant.BLANK;


    @Resource
    private AccountChequeDishonourService accountChequeDishonourService;
  
    @Resource
    AccountContraVoucherEntryService accountContraVoucherEntryService;
    
    @Resource
    private AccountChequeIssueanceService accountChequeIssueanceService;

   
    
    private List<ChequeIssueanceDTO> chList1 = null;

    public AccountChequeIssueController() {
        super(AccountChequeIssueController.class, MAIN_ENTITY_NAME);
        log("ChequeIssuanceControllar created.");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(final HttpServletRequest request,
            final Model model) {
        log("ChequeIssuanceControllar-'gridData' : 'Get grid Data'");
        final JQGridResponse response = new JQGridResponse<>();
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        response.setRows(chList1);
        response.setTotal(chList1.size());
        response.setRecords(chList1.size());
        response.setPage(page);
        model.addAttribute(MAIN_LIST_NAME, chList1);
        return response;
    }

    @RequestMapping(params = "getjqGridReceiptPaymentBothSearch", method = RequestMethod.POST)
    public ModelAndView getLedgerDetails(final ChequeIssueanceDTO bean, final BindingResult bindingResult,
            final Model model) {
        log("ChequeIssuanceControllar-'getjqGridsearch' : 'get jqGrid search data'");
        String result = MainetConstants.CommonConstant.BLANK;
        chList1 = new ArrayList<>();
        chList1.clear();
        BigDecimal bankBalance = new BigDecimal(0.00);
        BigDecimal totalBankBalance = new BigDecimal(0.00);
        BigDecimal totalPayment = new BigDecimal(0.00);
        BigDecimal totalreceipt = new BigDecimal(0.00);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long bankAccountId = Long.valueOf(bean.getBankAccount());
        Map<Long, String> bankAccountlist = new LinkedHashMap<>();
        bankAccountlist = accountChequeDishonourService.getBankAccountData(orgId);
        String collect = bankAccountlist.entrySet().stream().filter(map -> map.getKey().equals(bankAccountId))
                .map(map -> map.getValue()).collect(Collectors.joining());

        Date fromDte = null;
        Date toDte = null;
        if ((bean.getFormDate() != null) && !bean.getFormDate().isEmpty()) {
            fromDte = Utility.stringToDate(bean.getFormDate());
        }
        if ((bean.getToDate() != null) && !bean.getToDate().isEmpty()) {
            toDte = Utility.stringToDate(bean.getToDate());
        }
        final Long lkpStatus = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
        		AccountConstants.READY_FOR_ISSUE.getValue(), AccountPrefix.CLR.toString(),
        		orgId);
           chList1 = accountChequeIssueanceService.findByAllGridChequeNotIssuanceData(Long.valueOf(bean.getBankAccount()), fromDte, toDte, orgId,lkpStatus);
            // closing balanace
            bankBalance = accountContraVoucherEntryService.getBankBalance(Long.valueOf(bean.getBankAccount()), toDte,
                    UserSession.getCurrent().getOrganisation().getOrgid());

            if ((!chList1.isEmpty() && chList1 != null) && bankBalance != null) {

                if (chList1.get(chList1.size() - 1).getTotalPaymentAmount() != null) {
                    if (chList1.get(chList1.size() - 1).getTotalPaymentAmount() != null) {
                        totalPayment = chList1.get(chList1.size() - 1).getTotalPaymentAmount();
                    }
                    if (chList1.get(chList1.size() - 1).getTotalReceiptAmount() != null) {
                        totalreceipt = chList1.get(chList1.size() - 1).getTotalReceiptAmount();
                    }
                    totalBankBalance = bankBalance.add(totalPayment).subtract(totalreceipt);

                } else if (chList1.get(chList1.size() - 1).getTotalReceiptAmount() != null) {
                    if (chList1.get(chList1.size() - 1).getTotalPaymentAmount() != null) {
                        totalPayment = chList1.get(chList1.size() - 1).getTotalPaymentAmount();
                    }
                    if (chList1.get(chList1.size() - 1).getTotalReceiptAmount() != null) {
                        totalreceipt = chList1.get(chList1.size() - 1).getTotalReceiptAmount();
                    }
                    totalBankBalance = bankBalance.add(totalPayment).subtract(totalreceipt);

                } else if ((chList1.get(chList1.size() - 1).getTotalReceiptAmount() == null)
                        && (chList1.get(chList1.size() - 1).getTotalPaymentAmount() == null)) {
                    totalBankBalance = bankBalance.add(totalPayment).subtract(totalreceipt);
                }

            }

        final ChequeIssueanceDTO dto = new ChequeIssueanceDTO();
        if (chList1 == null || chList1.isEmpty()) {
            dto.setSuccessfulFlag(MainetConstants.MASTER.Y);
            result = JSP_LIST;
        } else {
            model.addAttribute("bankTypeDesc", collect);
            dto.setSerchType(bean.getSerchType());
            dto.setBankReconciliationDTO(chList1);
            result = JSP_FORM;
        }
        if (bankBalance != null) {
            dto.setBalanceAsUlb(CommonMasterUtility.getAmountInIndianCurrency(bankBalance));
            dto.setBankBalanceAsperStatement(CommonMasterUtility.getAmountInIndianCurrency(totalBankBalance));
        } else {
            dto.setBalanceAsUlb("0.00");
            dto.setBankBalanceAsperStatement("0.00");
        }
        // dto.setBankReconIds(collect);
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, dto);
        return new ModelAndView(result, MainetConstants.FORM_NAME, model);
    }

    @RequestMapping()
    public String getList(final Model model) throws Exception {
        log("Cheque Issueance-'list' :'list'");
        String result = MainetConstants.CommonConstant.BLANK;
        final ChequeIssueanceDTO dto = new ChequeIssueanceDTO();
        chList1 = new ArrayList<>();
        chList1.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Map<Long, String> bankAccountlist = new LinkedHashMap<>();
        bankAccountlist = accountChequeDishonourService.getBankAccountData(orgId);
        final Long lkpStatus = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
        		AccountConstants.ISSUED.getValue(), AccountPrefix.CLR.toString(),
        		orgId);
         Date fromDate = UserSession.getCurrent().getFinStartDate();
         Date toDate = UserSession.getCurrent().getFinEndDate();
        
        chList1 = accountChequeIssueanceService.findByAllGridChequeNotIssuanceData(null, fromDate, toDate, orgId,lkpStatus); 
        dto.setBankReconciliationDTO(chList1);
        //dto.setListofStatusId(listofStatusId);
        model.addAttribute(MainetConstants.BankReconciliation.BANK_LIST, bankAccountlist);
        model.addAttribute(MAIN_ENTITY_NAME, dto);
        result = JSP_SUMMARY;
        return result;
    }

    @RequestMapping(params = "form")
    public String formForCreate(final HttpServletRequest request, final Model model) throws Exception {
        log("BankReconciliationController-'formForCreate' : 'formForCreate'");
        String result = MainetConstants.CommonConstant.BLANK;
        @SuppressWarnings("unchecked")
        final List<ChequeIssueanceDTO> chList = (List<ChequeIssueanceDTO>) request.getSession()
                .getAttribute(MainetConstants.BankReconciliation.CH_LIST);
        final ChequeIssueanceDTO bean = new ChequeIssueanceDTO();
        bean.setBankReconciliationDTO(chList);
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        result = JSP_FORM;
        return result;
    }

    private void populateModel(final Model model, final ChequeIssueanceDTO bean, final FormMode formMode) {
        log("ChequeIssueanceControllar-'populateModel' : populate model");
        final List<LookUp> paymentModeList = CommonMasterUtility.getLookUps(AccountPrefix.PAY.name(),
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> paymentMode = new ArrayList<>();
        for (final LookUp payList : paymentModeList) {
            if (payList.getLookUpCode().equals(AccountConstants.CHEQUE.getValue())
                    || payList.getLookUpCode().equals(AccountConstants.CONTRA_DEPOSIT.getValue())) {
                paymentMode.add(payList);
            }
        }
        model.addAttribute(MainetConstants.TABLE_COLUMN.PAYMENT_MODE, paymentMode);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        if (formMode == FormMode.CREATE) {
            model.addAttribute(MainetConstants.FIELD_MASTER.MODE, MainetConstants.Actions.CREATE);
            model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, false);
        } else if (formMode == FormMode.UPDATE) {
            model.addAttribute(MainetConstants.FIELD_MASTER.MODE, MainetConstants.Actions.UPDATE);
        }
    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String create(@ModelAttribute final ChequeIssueanceDTO chequeIssueanceDTO,
            final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
            final HttpServletRequest httpServletRequest) throws Exception {
        log("ChequeIssueanceDTO-'create' : 'create'");
        String result = MainetConstants.CommonConstant.BLANK;
        if (!bindingResult.hasErrors()) {
        	chequeIssueanceDTO.setHasError(MainetConstants.MENU.FALSE);
            final UserSession userSession = UserSession.getCurrent();
            chequeIssueanceDTO.setOrgid(userSession.getOrganisation().getOrgid());
            chequeIssueanceDTO.setLangId(userSession.getLanguageId());
            chequeIssueanceDTO.setUserId(userSession.getEmployee().getEmpId());
            chequeIssueanceDTO.setLmoddate(new Date());
            chequeIssueanceDTO.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
            populateModel(model, chequeIssueanceDTO, FormMode.CREATE);
            ChequeIssueanceDTO tbBankReconciliationCreated = accountChequeIssueanceService
                    .saveChequeIssueanceFormData(chequeIssueanceDTO);
            if (tbBankReconciliationCreated == null) {
               tbBankReconciliationCreated = new ChequeIssueanceDTO();
           }
            model.addAttribute(MAIN_ENTITY_NAME, tbBankReconciliationCreated);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                    ApplicationSession.getInstance().getMessage("accounts.fieldmaster.update"));
            result = JSP_FORM;
        } else {
        	chequeIssueanceDTO.setHasError(MainetConstants.MENU.TRUE);
            model.addAttribute(MainetConstants.FUND_MASTER.MODE_VIEW, getModeView());
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.CommonConstants.COMMAND, bindingResult);
            populateModel(model, chequeIssueanceDTO, FormMode.CREATE);
            result = JSP_FORM;
        }
        return result;
    }

    @RequestMapping(params = "AddForm", method = RequestMethod.POST)
    public String AddForm(final ChequeIssueanceDTO bean1, final BindingResult bindingResult, final Model model) {
        log("ChequeIssuanceControllar-'list' :'list'");
        String result = MainetConstants.CommonConstant.BLANK;
        chList1 = new ArrayList<>();
        chList1.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Map<Long, String> bankAccountlist = new LinkedHashMap<>();
        bankAccountlist = accountChequeDishonourService.getBankAccountData(orgId);
        final ChequeIssueanceDTO bean = new ChequeIssueanceDTO();
        bean.setSerchType(MainetConstants.MENU.R);
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        model.addAttribute(MainetConstants.BankReconciliation.BANK_LIST, bankAccountlist);
        result = JSP_LIST;
        return result;
    }

    @RequestMapping(params = "searchChequeIssuanceData", method = RequestMethod.POST)
    public String createPopulationForm(final HttpServletRequest request, final Model model,
            @RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
            @RequestParam(required = false) Long bankAccount) {
        log("ChequeIssuanceControllar-'list' :'list'");
        String result = MainetConstants.CommonConstant.BLANK;
        final ChequeIssueanceDTO dto = new ChequeIssueanceDTO();
        chList1 = new ArrayList<>();
        chList1.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Map<Long, String> bankAccountlist = new LinkedHashMap<>();
       Date fDate=Utility.stringToDate(fromDate);
       Date tDate=Utility.stringToDate(toDate);
       final Long lkpStatus = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
        		AccountConstants.ISSUED.getValue(), AccountPrefix.CLR.toString(),
        		orgId);
        chList1 = accountChequeIssueanceService.findByAllGridChequeNotIssuanceData(bankAccount, fDate, tDate, orgId,lkpStatus); 
        bankAccountlist = accountChequeDishonourService.getBankAccountData(orgId);
        dto.setFormDate(fromDate);
        dto.setToDate(toDate);
        dto.setBankAccount(bankAccount.toString());
        dto.setBankReconciliationDTO(chList1);
        model.addAttribute(MainetConstants.BankReconciliation.BANK_LIST, bankAccountlist);
        model.addAttribute(MAIN_ENTITY_NAME, dto);
        result = JSP_SUMM;
        return result;
    }

    /*@RequestMapping(method = { RequestMethod.POST }, params = "searchStatusId")
    public Map<Long, String> serchStatusId(@RequestParam("bankAccountId") Long bankAccountId,
            final HttpServletRequest httpServletRequest) {
        List<BankReconciliationDTO> result = bankReconciliationService
                .getAllStatusId(UserSession.getCurrent().getOrganisation().getOrgid());
        Map<Long, String> data = new HashMap<>();
        if (result != null && !result.isEmpty()) {
            result.forEach(vdata -> {
                data.put(vdata.getId(), vdata.getBankReconIds());
            });
        }
        return data;
    }*/

    public String getModeView() {
        return modeView;
    }

    public void setModeView(final String modeView) {
        this.modeView = modeView;
    }
    
    @RequestMapping(method = { RequestMethod.POST }, params = "isChequeIssuanceRequired")
    @ResponseBody
    public String isChequeIssuanceRequired( final HttpServletRequest httpServletRequest) {
    	 Organisation org = UserSession.getCurrent().getOrganisation();
    	 int langId = UserSession.getCurrent().getLanguageId();
    	 final LookUp isChequeIssanceRequired = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.CIR.getValue(),
                 AccountPrefix.AIC.toString(), langId, org);
    	 return isChequeIssanceRequired.getOtherField();
    }
}