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
import com.abm.mainet.account.dto.StopPaymentDTO;
import com.abm.mainet.account.service.AccountChequeDishonourService;
import com.abm.mainet.account.service.AccountContraVoucherEntryService;
import com.abm.mainet.account.service.AccountStopPaymentService;
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
@RequestMapping("/AccountStopPayment.html")
public class AccountStopPaymentController extends AbstractController {

    private static final String MAIN_ENTITY_NAME = "stopPayment";//tbBankReconciliation
    private static final String MAIN_LIST_NAME = "list";
    private static final String JSP_FORM = "stopPayment/form";
    private static final String JSP_LIST = "stopPayment/list";
    private static final String JSP_SUMMARY = "stopPayment/summary";
    private static final String JSP_SUMM = "stopPaymentcon/summary";
    private String modeView = MainetConstants.CommonConstant.BLANK;

   
    @Resource
    private AccountChequeDishonourService accountChequeDishonourService;

    @Resource
    private AccountContraVoucherEntryService accountContraVoucherEntryService;
  
    @Resource
    private AccountStopPaymentService accountStopPaymentService;


    private List<StopPaymentDTO> chList1 = null;

    public AccountStopPaymentController() {
        super(AccountStopPaymentController.class, MAIN_ENTITY_NAME);
        log("AccountStopPaymentController created.");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(final HttpServletRequest request,
            final Model model) {
        log("AccountStopPaymentController-'gridData' : 'Get grid Data'");
        final JQGridResponse response = new JQGridResponse<>();
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        response.setRows(chList1);
        response.setTotal(chList1.size());
        response.setRecords(chList1.size());
        response.setPage(page);
        model.addAttribute(MAIN_LIST_NAME, chList1);
        return response;
    }

    @RequestMapping(params = "getjqGridStopPaymentSearch", method = RequestMethod.POST)
    public ModelAndView getLedgerDetails(final StopPaymentDTO bean, final BindingResult bindingResult,
            final Model model) {
        log("StopPaymentControllar-'getjqGridsearch' : 'get jqGrid search data'");
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
           chList1 =accountStopPaymentService.findRecordsForStopPayment(Long.valueOf(bean.getBankAccount()), fromDte, toDte, orgId);
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

        final StopPaymentDTO dto = new StopPaymentDTO();
        if (chList1 == null || chList1.isEmpty()) {
            dto.setSuccessfulFlag(MainetConstants.MASTER.Y);
            result = JSP_LIST;
        } else {
            model.addAttribute("bankTypeDesc", collect);
            dto.setSerchType(bean.getSerchType());
            dto.setStopPaymentDto(chList1);
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
        log("stop Payment-'list' :'list'");
        String result = MainetConstants.CommonConstant.BLANK;
        final StopPaymentDTO dto = new StopPaymentDTO();
        chList1 = new ArrayList<>();
        chList1.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Map<Long, String> bankAccountlist = new LinkedHashMap<>();
        bankAccountlist = accountChequeDishonourService.getBankAccountData(orgId);
        final Long lkpStatus = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
        		AccountConstants.STOP_PAYMENT.getValue(), AccountPrefix.CLR.toString(),
        		orgId);
         Date fromDate = UserSession.getCurrent().getFinStartDate();
         Date toDate = UserSession.getCurrent().getFinEndDate();
        chList1 =accountStopPaymentService.findByAllGridstopPaymentData(null, fromDate, toDate, orgId,lkpStatus); 
        dto.setStopPaymentDto(chList1);
        model.addAttribute(MainetConstants.BankReconciliation.BANK_LIST, bankAccountlist);
        model.addAttribute(MAIN_ENTITY_NAME, dto);
        result = JSP_SUMMARY;
        return result;
    }

    @RequestMapping(params = "form")
    public String formForCreate(final HttpServletRequest request, final Model model) throws Exception {
        log("AccountStopPaymentController-'formForCreate' : 'formForCreate'");
        String result = MainetConstants.CommonConstant.BLANK;
        @SuppressWarnings("unchecked")
        final List<StopPaymentDTO> chList = (List<StopPaymentDTO>) request.getSession()
                .getAttribute(MainetConstants.BankReconciliation.CH_LIST);
        final StopPaymentDTO bean = new StopPaymentDTO();
        bean.setStopPaymentDto(chList);
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        result = JSP_FORM;
        return result;
    }

    private void populateModel(final Model model, final StopPaymentDTO bean, final FormMode formMode) {
        log("stop payment Controllar-'populateModel' : populate model");
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
    public String create(@ModelAttribute final StopPaymentDTO stopPaymentDTO,
            final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
            final HttpServletRequest httpServletRequest) throws Exception {
        log("StopPaymentDTO-'create' : 'create'");
        String result = MainetConstants.CommonConstant.BLANK;
        if (!bindingResult.hasErrors()) {
        	stopPaymentDTO.setHasError(MainetConstants.MENU.FALSE);
            final UserSession userSession = UserSession.getCurrent();
            stopPaymentDTO.setOrgid(userSession.getOrganisation().getOrgid());
            stopPaymentDTO.setLangId(userSession.getLanguageId());
            stopPaymentDTO.setUserId(userSession.getEmployee().getEmpId());
            stopPaymentDTO.setLmoddate(new Date());
            stopPaymentDTO.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
            populateModel(model, stopPaymentDTO, FormMode.CREATE);
            StopPaymentDTO stopPaymentDTOCreated =accountStopPaymentService
               .savestopPaymentFormData(stopPaymentDTO);
            if (stopPaymentDTOCreated == null) {
            	stopPaymentDTOCreated = new StopPaymentDTO();
           }
            model.addAttribute(MAIN_ENTITY_NAME, stopPaymentDTOCreated);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                    ApplicationSession.getInstance().getMessage("accounts.fieldmaster.update"));
            result = JSP_FORM;
        } else {
        	stopPaymentDTO.setHasError(MainetConstants.MENU.TRUE);
            model.addAttribute(MainetConstants.FUND_MASTER.MODE_VIEW, getModeView());
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.CommonConstants.COMMAND, bindingResult);
            populateModel(model, stopPaymentDTO, FormMode.CREATE);
            result = JSP_FORM;
        }
        return result;
    }

    @RequestMapping(params = "AddForm", method = RequestMethod.POST)
    public String AddForm(final StopPaymentDTO bean1, final BindingResult bindingResult, final Model model) {
        log("AccountStopPaymentController-'list' :'list'");
        String result = MainetConstants.CommonConstant.BLANK;
        chList1 = new ArrayList<>();
        chList1.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Map<Long, String> bankAccountlist = new LinkedHashMap<>();
        bankAccountlist = accountChequeDishonourService.getBankAccountData(orgId);
        final StopPaymentDTO bean = new StopPaymentDTO();
        bean.setSerchType(MainetConstants.MENU.R);
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        model.addAttribute(MainetConstants.BankReconciliation.BANK_LIST, bankAccountlist);
        result = JSP_LIST;
        return result;
    }

    @RequestMapping(params = "searchStopPaymentData", method = RequestMethod.POST)
    public String createPopulationForm(final HttpServletRequest request, final Model model,
            @RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
            @RequestParam(required = false) Long bankAccount) {
        log("Stop Payment Controllar-'list' :'list'");
        String result = MainetConstants.CommonConstant.BLANK;
        final StopPaymentDTO dto = new StopPaymentDTO();
        chList1 = new ArrayList<>();
        chList1.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Map<Long, String> bankAccountlist = new LinkedHashMap<>();
       Date fDate=Utility.stringToDate(fromDate);
       Date tDate=Utility.stringToDate(toDate);
       final Long lkpStatus = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
        		AccountConstants.STOP_PAYMENT.getValue(), AccountPrefix.CLR.toString(),
        		orgId);
        chList1 =accountStopPaymentService.findByAllGridstopPaymentData(bankAccount, fDate, tDate, orgId,lkpStatus); 
        bankAccountlist = accountChequeDishonourService.getBankAccountData(orgId);
        dto.setFormDate(fromDate);
        dto.setToDate(toDate);
        dto.setBankAccount(bankAccount.toString());
        dto.setStopPaymentDto(chList1);
        model.addAttribute(MainetConstants.BankReconciliation.BANK_LIST, bankAccountlist);
        model.addAttribute(MAIN_ENTITY_NAME, dto);
        result = JSP_SUMM;
        return result;
    }

    /*@RequestMapping(method = { RequestMethod.POST }, params = "searchStatusId")
    public Map<Long, String> serchStatusId(@RequestParam("bankAccountId") Long bankAccountId,
            final HttpServletRequest httpServletRequest) {
        //List<BankReconciliationDTO> result = bankReconciliationService
          //      .getAllStatusId(UserSession.getCurrent().getOrganisation().getOrgid());
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
    
    @RequestMapping(method = { RequestMethod.POST }, params = "isStopPaymentRequired")
    @ResponseBody
    public String isStopPaymentRequired( final HttpServletRequest httpServletRequest) {
    	 Organisation org = UserSession.getCurrent().getOrganisation();
    	 int langId = UserSession.getCurrent().getLanguageId();
    	 final LookUp isStopPaymentRequired = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.SPR.getValue(),
                 AccountPrefix.AIC.toString(), langId, org);
    	 return isStopPaymentRequired.getOtherField();
    }
}