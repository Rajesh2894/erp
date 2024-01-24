package com.abm.mainet.account.ui.controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.domain.AccountBillEntryDeductionDetEntity;
import com.abm.mainet.account.domain.AccountBillEntryExpenditureDetEntity;
import com.abm.mainet.account.domain.AccountBillEntryMasterEnitity;
import com.abm.mainet.account.domain.AccountPaymentDetEntity;
import com.abm.mainet.account.domain.AccountPaymentMasterEntity;
import com.abm.mainet.account.dto.PaymentEntryDto;
import com.abm.mainet.account.dto.PaymentReportDto;
import com.abm.mainet.account.dto.VoucherReversalDTO;
import com.abm.mainet.account.dto.VoucherTemplateDTO;
import com.abm.mainet.account.service.AccountBillEntryService;
import com.abm.mainet.account.service.AccountChequeOrCashDepositeService;
import com.abm.mainet.account.service.AccountContraVoucherEntryService;
import com.abm.mainet.account.service.BudgetCodeService;
import com.abm.mainet.account.service.PaymentEntrySrevice;
import com.abm.mainet.account.service.VoucherTemplateService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.integration.acccount.dto.PaymentDetailsDto;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountFundMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.TbBankmasterService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;

@Controller
@RequestMapping("/PaymentEntry.html")
public class PaymentEntryController extends AbstractController {

    private final String JSP_LIST = "PaymentEntry/list";
    private final String NEW_JSP_LIST = "NewPaymentEntry/list";
    private final String JSP_FORM = "PaymentEntry/form";
    private final String JSP_VIEW_FORM = "PaymentEntry/view";
    private final static String PAYMENT_ENTRY_DTO = "paymentEntryDto";
    private static final String BILL_TYPE_LIST = "billTypeList";
    private static final String BILL_TYPE_LIST_P = "billTypeListP";
    private static final String PAYMENT_TYPE_LIST = "paymentTypeList";
    private static final String VENDOR_LIST = "vendorList";
    private static final Logger LOGGER = Logger.getLogger(PaymentEntryController.class);
    private static final String PAYMENT_MODE = "paymentMode";
    private static final String BANK_AC_MAP = "bankAccountMap";
    private static final String TEMPLATE_EXIST_FLAG = "templateExistFlag";
    private static final String NO_RECORD_FOUND = "No record found for bill containing id : ";
    private static final String BILL_DETAIL_LIST = "billDetailList";
    private final String Report_FORM = "PaymentEntry/Report";
    private final static String PAYMENT_REPORT_DTO = "oPaymentReportDto";
    @Resource
    private AccountBillEntryService billEntryService;
    @Resource
    private TbAcVendormasterService vendorMasterService;
    @Resource
    private TbBankmasterService banksMasterService;
    @Resource
    AccountContraVoucherEntryService accountContraVoucherEntryService;
    @Resource
    PaymentEntrySrevice paymentEntryService;
    @Resource
    private VoucherTemplateService voucherTemplateService;
    @Resource
    private BudgetCodeService budgetCodeService;
    @Resource
    private ILocationMasService locMasService;
    @Resource
    private AccountContraVoucherEntryService contraVoucherEntryService;
    @Resource
    private AccountChequeOrCashDepositeService accountChequeOrCashDepositeService;
    @Resource
    private SecondaryheadMasterService secondaryheadMasterService;

    @Resource
    private ILocationMasService locationMasService;

    @Resource
    private DepartmentService departmentService;
    @Resource
    private AccountFundMasterService accountFundMasterService;
    @Autowired
    private IEmployeeService employeeService;
    
    @Resource
  	private AccountFieldMasterService tbAcFieldMasterService;
    @Resource
   	private IAttachDocsService attachDocsService;
    @Autowired
   	private IFileUploadService accountFileUpload;
    
    /**
     * @param controllerClass
     * @param entityName
     */
    public PaymentEntryController() {
        super(PaymentEntryController.class, PAYMENT_ENTRY_DTO);
        LOGGER.info("Payment entry controller created");
    }

    @RequestMapping()
    public String index(final Model model, final HttpServletRequest httpServletRequest) {
        log("Action 'form'");
        final PaymentEntryDto paymentEntryDto = new PaymentEntryDto();
        httpServletRequest.getSession().setAttribute(MainetConstants.PaymentEntry.GRID_ITEM, null);
        populateModel(model, paymentEntryDto, MODE_VIEW);
        return MainetConstants.PaymentEntry.PAYMENT_ENTRY;
    }

    private void populateField(final Model model) {
		model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
				tbAcFieldMasterService.getFieldMasterLastLevels(UserSession.getCurrent().getOrganisation().getOrgid()));
	}
    
    
    @RequestMapping(params = "createPage", method = RequestMethod.POST)
    public String populateGridList(final Model model, final HttpServletRequest httpServletRequest) {
        log("Action 'form'");
        accountFileUpload.sessionCleanUpForFileUpload();
        final PaymentEntryDto paymentEntryDto = new PaymentEntryDto();
        populateField(model);
        populateModel(model, paymentEntryDto, MODE_CREATE);
        return JSP_LIST;
    }
    
    @RequestMapping(method = RequestMethod.GET, params = "home")
    public String directPaymentHome(final Model model, final HttpServletRequest httpServletRequest) {
        log("Action 'form'");
        final PaymentEntryDto paymentEntryDto = new PaymentEntryDto();
        populateModel(model, paymentEntryDto, MODE_CREATE);
        return NEW_JSP_LIST;
    }
    public void populateModel(final Model model, final PaymentEntryDto paymentEntryDto, final String formMode) {
        model.addAttribute(PAYMENT_ENTRY_DTO, paymentEntryDto);
        populateListOfPaymentType(model);
        populateListOfBillType(model);
        populateVendorList(model);
        populateBillDetails(model);
        populatePaymentModeList(model);
        populateListOfBanks(model);
        checkingBudgetDefParameter(model);
        if (formMode.equals(MODE_VIEW)) {
            populateListOfBanks(model);
            model.addAttribute(MainetConstants.AccountBillEntry.EXPENDITURE_HEAD_MAP, secondaryheadMasterService
                    .findExpenditureHeadMapForView(UserSession.getCurrent().getOrganisation().getOrgid()));
        }
        checkTemplate(model);
    }

    public void populateListOfBillType(final Model model) {
        final List<LookUp> billTypeLookupList = CommonMasterUtility.getLookUps(AccountPrefix.ABT.toString(),
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(BILL_TYPE_LIST, billTypeLookupList);

        List<LookUp> newbillTypeLookupListP = new ArrayList<>();
        List<LookUp> billTypeLookupListP = CommonMasterUtility.getLookUps(AccountPrefix.ABT.toString(),
                UserSession.getCurrent().getOrganisation());
        newbillTypeLookupListP = billTypeLookupListP.stream()
                .filter(look -> look.getLookUpCode() != null)
                .collect(Collectors.toList());
        model.addAttribute(BILL_TYPE_LIST_P, newbillTypeLookupListP);
    }

    public void populateListOfPaymentType(final Model model) {
        final List<LookUp> paymentTypeLookupList = CommonMasterUtility.getLookUps(AccountPrefix.PDM.toString(),
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(PAYMENT_TYPE_LIST, paymentTypeLookupList);

    }

    public void populateVendorList(final Model model) {

        final Organisation org = UserSession.getCurrent().getOrganisation();
        final Integer languageId = UserSession.getCurrent().getLanguageId();
        final LookUp lookUpVendorStatus = CommonMasterUtility
                .getLookUpFromPrefixLookUpValue(AccountConstants.AC.getValue(), PrefixConstants.VSS, languageId, org);
        final Long vendorStatus = lookUpVendorStatus.getLookUpId();
        final List<TbAcVendormaster> vendorList = vendorMasterService.getActiveVendors(org.getOrgid(), vendorStatus);
        model.addAttribute(VENDOR_LIST, vendorList);

    }

    @RequestMapping(params = "searchBillData")
    public @ResponseBody List<PaymentEntryDto> getBillEntryData(@RequestParam("billId") final Long billId,
            @RequestParam("bchId") Long bchId) {

        List<PaymentEntryDto> masterBeanList = null;
        masterBeanList = new ArrayList<>();
        PaymentEntryDto masterDto = null;
        List<AccountBillEntryMasterEnitity> billEntryList = null;
        String billAmountStr = null;
        String deductionsStr = null;
        String netPayableStr = null;
        String newNetPayableAmt = "";
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        if (billId != null) {
            billEntryList = billEntryService.getBillDataByBillId(billId, orgId);
        }
        if ((billEntryList != null) && !billEntryList.isEmpty()) {
            for (final AccountBillEntryMasterEnitity list : billEntryList) {
                BigDecimal totalDeductionAmount = BigDecimal.ZERO;
                masterDto = new PaymentEntryDto();
                masterDto.setId(list.getId());
                masterDto.setBillNo(list.getBillNo());
                masterDto.setBillDate(UtilityService.convertDateToDDMMYYYY(list.getBillEntryDate()));
                masterDto.setBillTotalAmount(list.getBillTotalAmount());

                String billType = CommonMasterUtility.findLookUpCode(AccountPrefix.ABT.toString(), orgId,
                        list.getBillTypeId().getCpdId());

                if (billType.equals(MainetConstants.AccountBillEntry.BILL_TYPE_ESB)) {

                    BigDecimal ratio = BigDecimal.ZERO;
                    BigDecimal sumOfBillBalanceAmount = BigDecimal.ZERO;
                    BigDecimal sumOfBillDeductionAmount = BigDecimal.ZERO;

                    List<AccountBillEntryExpenditureDetEntity> expList = list.getExpenditureDetailList();
                    for (AccountBillEntryExpenditureDetEntity expBillEntryDetails : expList) {
                        sumOfBillBalanceAmount = sumOfBillBalanceAmount.add(expBillEntryDetails.getBillChargesAmount());
                    }
                    List<AccountBillEntryDeductionDetEntity> dedList = list.getDeductionDetailList();
                    if (dedList != null && !dedList.isEmpty()) {
                        for (AccountBillEntryDeductionDetEntity dedBillEntryDetails : dedList) {
                            sumOfBillDeductionAmount = sumOfBillDeductionAmount
                                    .add(dedBillEntryDetails.getDeductionAmount());
                        }
                    }

                    BigDecimal sumOfBillProRataBalanceAmount = sumOfBillBalanceAmount.setScale(2,
                            BigDecimal.ROUND_HALF_EVEN);
                    BigDecimal sumOfBillProRataDeductionAmount = sumOfBillDeductionAmount.setScale(2,
                            BigDecimal.ROUND_HALF_EVEN);
                    ratio = sumOfBillProRataDeductionAmount.divide(sumOfBillProRataBalanceAmount,
                            MathContext.DECIMAL128);
                    // BigDecimal proRataRatio = ratio.setScale(2, BigDecimal.ROUND_HALF_EVEN);

                    List<AccountBillEntryExpenditureDetEntity> expListProRata = list.getExpenditureDetailList();
                    for (AccountBillEntryExpenditureDetEntity expBillEntryProRataDetails : expListProRata) {

                        if (bchId.equals(expBillEntryProRataDetails.getSacHeadId())) {

                            BigDecimal proRataDeductionAmt = BigDecimal.ZERO;
                            BigDecimal proRataPayableAmt = BigDecimal.ZERO;

                            proRataDeductionAmt = expBillEntryProRataDetails.getBillChargesAmount().multiply(ratio)
                                    .setScale(0, BigDecimal.ROUND_HALF_EVEN);
                            proRataPayableAmt = expBillEntryProRataDetails.getBillChargesAmount()
                                    .subtract(proRataDeductionAmt);
                            BigDecimal amount = proRataPayableAmt.setScale(0, BigDecimal.ROUND_HALF_EVEN);

                            newNetPayableAmt = expBillEntryProRataDetails.getFi04V1();
                            billAmountStr = CommonMasterUtility
                                    .getAmountInIndianCurrency(expBillEntryProRataDetails.getBillChargesAmount());
                            masterDto.setBillAmountStr(billAmountStr);

                            deductionsStr = CommonMasterUtility.getAmountInIndianCurrency(proRataDeductionAmt);
                            masterDto.setDeductionsStr(deductionsStr);
                            if (newNetPayableAmt != null && !newNetPayableAmt.isEmpty()) {
                                masterDto.setNetPayable(new BigDecimal(newNetPayableAmt).setScale(0, BigDecimal.ROUND_HALF_EVEN));
                                masterDto.setNetPayableStr(CommonMasterUtility
                                        .getAmountInIndianCurrency(
                                                new BigDecimal(newNetPayableAmt).setScale(0, BigDecimal.ROUND_HALF_EVEN)));
                            } else {
                                masterDto.setNetPayable(amount);
                                masterDto.setNetPayableStr(CommonMasterUtility
                                        .getAmountInIndianCurrency(amount));
                            }
                            masterBeanList.add(masterDto);
                        }
                    }
                } else {

                    final List<AccountBillEntryExpenditureDetEntity> expDetList = list.getExpenditureDetailList();
                    if ((expDetList != null) && !expDetList.isEmpty()) {
                        for (final AccountBillEntryExpenditureDetEntity expDetEntity : expDetList) {
                            if (expDetEntity.getSacHeadId().equals(bchId)) {
                                newNetPayableAmt = expDetEntity.getFi04V1();
                                billAmountStr = CommonMasterUtility
                                        .getAmountInIndianCurrency(expDetEntity.getBillChargesAmount());
                                masterDto.setBillAmountStr(billAmountStr);
                            }
                        }
                    } else {
                        LOGGER.error("Expenditure data not found");
                    }
                    final List<AccountBillEntryDeductionDetEntity> dedDetList = list.getDeductionDetailList();
                    if ((dedDetList != null) && !dedDetList.isEmpty()) {
                        for (final AccountBillEntryDeductionDetEntity dedDetEntity : dedDetList) {
                        	//This condition is not required in case of deposit. Because no expenditure head is required under deposit case-32127
                           
                        	/*if (dedDetEntity.getBchId() != null && dedDetEntity.getBchId().equals(bchId)) {*/
                                if (dedDetEntity.getDeductionAmount() != null) {
                                    totalDeductionAmount = totalDeductionAmount.add(dedDetEntity.getDeductionAmount());
                                }
                            /*}*/
                        }
                    }
                    masterDto.setTotalDeductions(totalDeductionAmount);
                    deductionsStr = CommonMasterUtility.getAmountInIndianCurrency(totalDeductionAmount);
                    masterDto.setDeductionsStr(deductionsStr);
                    if (newNetPayableAmt != null && !newNetPayableAmt.isEmpty()) {
                        masterDto.setNetPayable(new BigDecimal(newNetPayableAmt));
                        masterDto.setNetPayableStr(
                                CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(newNetPayableAmt)));
                    } else {
                        masterDto.setNetPayable(
                                new BigDecimal(billAmountStr.replaceAll(",", "")).subtract(totalDeductionAmount));
                        netPayableStr = CommonMasterUtility.getAmountInIndianCurrency(
                                new BigDecimal(billAmountStr.replaceAll(",", "")).subtract(totalDeductionAmount));
                        masterDto.setNetPayableStr(netPayableStr);
                    }
                    masterBeanList.add(masterDto);
                }
            }
        } else {
            LOGGER.error(NO_RECORD_FOUND + billId);
        }
        return masterBeanList;
    }

    public void populateBillDetails(final Model model) {

        final PaymentEntryDto paymentDto = new PaymentEntryDto();
        final PaymentDetailsDto billDetailDto = new PaymentDetailsDto();
        final List<PaymentDetailsDto> billDetailList = new ArrayList<>();
        billDetailList.add(billDetailDto);
        paymentDto.setPaymentDetailsDto(billDetailList);
        model.addAttribute(BILL_DETAIL_LIST, billDetailList);
    }

    @RequestMapping(params = "checkPaymentDateisExists", method = RequestMethod.POST)
    public @ResponseBody boolean findBudgetOpenBalDuplicateCombination(PaymentEntryDto paymentEntryDto,
            final HttpServletRequest request, final Model model, final BindingResult bindingResult) {
        Date payDate = UtilityService.convertStringDateToDateFormat(paymentEntryDto.getTransactionDate());
        boolean isValidationError = false;
        if (billEntryService.isPaymentDateisExists(UserSession.getCurrent().getOrganisation().getOrgid(), payDate)) {
            bindingResult.addError(new org.springframework.validation.FieldError(PAYMENT_ENTRY_DTO,
                    MainetConstants.CommonConstant.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                    ApplicationSession.getInstance().getMessage(MainetConstants.CommonConstant.BLANK)));
            isValidationError = true;
        }
        return isValidationError;
    }

    @RequestMapping(params = "getBillNumbers", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> getBillNumbers(@RequestParam("billTypeId") final Long billTypeId,
            @RequestParam("vendorId") final Long vendorId, @RequestParam("paymentDate") final String paymentDate) {
        Date payDate = UtilityService.convertStringDateToDateFormat(paymentDate);
        final List<Object[]> billNumbers = billEntryService
                .getBillNumbers(UserSession.getCurrent().getOrganisation().getOrgid(), billTypeId, vendorId, payDate);
        final Map<Long, String> billNumberMap = new HashMap<>();
        if ((billNumbers != null) && !billNumbers.isEmpty()) {
            for (final Object[] obj : billNumbers) {
                billNumberMap.put(Long.valueOf(obj[0].toString()), (String) obj[1]);
            }
        } else {
            LOGGER.error(NO_RECORD_FOUND + billTypeId + " and " + vendorId);
        }
        return billNumberMap;
    }
    
    @RequestMapping(params = "getBillNumbersWithFieldId", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> getBillNumbersWithFieldId(@RequestParam("billTypeId") final Long billTypeId,
            @RequestParam("vendorId") final Long vendorId, @RequestParam("paymentDate") final String paymentDate,@RequestParam("fieldId") final Long fieldId) {
        Date payDate = UtilityService.convertStringDateToDateFormat(paymentDate);
        final List<Object[]> billNumbers = billEntryService
                .getBillNumbersWithFieldId(UserSession.getCurrent().getOrganisation().getOrgid(), billTypeId, vendorId, payDate,fieldId);
        final Map<Long, String> billNumberMap = new HashMap<>();
        if ((billNumbers != null) && !billNumbers.isEmpty()) {
            for (final Object[] obj : billNumbers) {
                billNumberMap.put(Long.valueOf(obj[0].toString()), (String) obj[1]);
            }
        } else {
            LOGGER.error(NO_RECORD_FOUND + billTypeId + " and " + vendorId);
        }
        return billNumberMap;
    }

    @RequestMapping(params = "proceedPayment", method = RequestMethod.POST)
    public String formForCreate(final Model model, final PaymentEntryDto paymentEntryDto,
            final HttpServletRequest request) {

        log("Action 'proceed payment form'");
        final List<PaymentDetailsDto> billDetails = paymentEntryDto.getPaymentDetailsDto();
        request.getSession().setAttribute(MainetConstants.PaymentEntry.BILL_DETAIL, billDetails);
        populateModel(model, paymentEntryDto, MODE_CREATE);
        return JSP_FORM;
    }

    public void populatePaymentModeList(final Model model) {
        final List<LookUp> paymentModeList = CommonMasterUtility.getLookUps(AccountPrefix.PAY.name(),
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> paymentMode = new ArrayList<>();
        for (final LookUp payList : paymentModeList) {
            if (/* payList.getLookUpCode().equals(AccountConstants.CASH.getValue()) || */
            payList.getLookUpCode().equals(AccountConstants.CHEQUE.getValue())
                    || payList.getLookUpCode().equals(AccountConstants.PCA.getValue())
                    || payList.getLookUpCode().equals(AccountConstants.BANK.getValue())
                    || payList.getLookUpCode().equals(AccountConstants.RTGS.getValue())
                    || payList.getLookUpCode().equals(AccountConstants.A.getValue())) {
                paymentMode.add(payList);
            }
        }
        model.addAttribute(PAYMENT_MODE, paymentMode);
    }

    private void populateListOfBanks(final Model model) {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<Object[]> bankAccountList = new ArrayList<>();
        final Map<Long, String> bankAccountMap = new HashMap<>();
        final Long statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.U.toString(),
                PrefixConstants.BAS, orgId);
        bankAccountList = banksMasterService.getActiveBankAccountList(orgId, statusId);
        if ((bankAccountList != null) && !bankAccountList.isEmpty()) {
            for (final Object[] bankAc : bankAccountList) {
                bankAccountMap.put((Long) bankAc[0],
                        bankAc[3] + MainetConstants.SEPARATOR + bankAc[1] + MainetConstants.SEPARATOR + bankAc[2]);
            }
        }
        model.addAttribute(BANK_AC_MAP, bankAccountMap);
    }

    private void checkingBudgetDefParameter(Model model) {
        final List<LookUp> budgetParametersLookUpList = CommonMasterUtility.getListLookup(PrefixConstants.BDP,
                UserSession.getCurrent().getOrganisation());
        if (budgetParametersLookUpList != null) {
            for (final LookUp lookUp : budgetParametersLookUpList) {
                if (lookUp != null) {
                    if (lookUp.getLookUpCode().equals(PrefixConstants.PAYMENT_ENTRY_CPD_VALUE)) {
                        model.addAttribute(MainetConstants.AccountBillEntry.BUDGET_PARAMETERS_STATUS,
                                MainetConstants.MASTER.Y);
                    }
                }
            }
        }
    }

    @RequestMapping(params = "formForView", method = RequestMethod.POST)
    public String formForView(final Model model, final HttpServletRequest request,
            @RequestParam("id") final Long paymentId) {
        final int langId = UserSession.getCurrent().getLanguageId();
        final PaymentEntryDto paymentEntryDto = paymentEntryService.getRecordForView(paymentId,
                UserSession.getCurrent().getOrganisation().getOrgid(), langId);
        
        //file upload start
        final String accountIds = MainetConstants.RECEIPT_MASTER.Module + MainetConstants.operator.FORWARD_SLACE +paymentId;
		final List<AttachDocs> attachDocsList = attachDocsService
				.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), accountIds);
		paymentEntryDto.setAttachDocsList(attachDocsList);
		//file upload end
		model.addAttribute(MainetConstants.PaymentEntry.PAYMENT_DETAIL, paymentEntryDto.getPaymentDetailsDto());
		populateField(model);
        populateModel(model, paymentEntryDto, MODE_VIEW);
        return JSP_VIEW_FORM;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "create", method = RequestMethod.POST)
    public @ResponseBody String createBillEntry(@Valid PaymentEntryDto paymentEntrydto, final Model model,
            final HttpServletRequest request) throws ParseException {
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final int langsId = UserSession.getCurrent().getLanguageId();
        paymentEntrydto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        final LookUp depLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.AccountBillEntry.DE,
                AccountPrefix.ABT.toString(), langsId, organisation);
        log("Action 'create payment form'");
        Long depLookUpId = depLookup.getLookUpId();
        if (depLookUpId.equals(paymentEntrydto.getDupBillTypeId())) {
            final List<PaymentDetailsDto> billDetails = paymentEntrydto.getPaymentDetailsDto();
            if ((billDetails != null) && !billDetails.isEmpty()) {
                for (final PaymentDetailsDto billDetail : billDetails) {
                    BigDecimal oldDepDefundAmt = paymentEntryService.getDepDefundAmountDetailsCheck(billDetail.getId(),
                            paymentEntrydto.getOrgId());
                    if (oldDepDefundAmt != null && !oldDepDefundAmt.toString().equals("0.00")
                            && !oldDepDefundAmt.toString().equals("0")) {
                        BigDecimal newDepDefundAmt = oldDepDefundAmt.subtract(billDetail.getPaymentAmount());
                        if (newDepDefundAmt.signum() == -1) {
                            return "deposit balance not available for payment for this deposit";
                        }
                    } else {
                        return "deposit balance not available for payment for this deposit";
                    }
                }
            }
        }

        paymentEntrydto.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);
        paymentEntrydto.setSuccessfulFlag(MainetConstants.MASTER.Y);
        if (paymentEntrydto.getDupVendorId() != null) {
            paymentEntrydto.setVendorId(paymentEntrydto.getDupVendorId());
        }
        if (paymentEntrydto.getDupBillTypeId() != null) {
            paymentEntrydto.setBillTypeId(paymentEntrydto.getDupBillTypeId());
        }
        if (paymentEntrydto.getDupTransactionDate() != null && !paymentEntrydto.getDupTransactionDate().isEmpty()) {
            paymentEntrydto.setTransactionDate(paymentEntrydto.getDupTransactionDate());
        }
        if (paymentEntrydto.getFieldId() != null) {
            paymentEntrydto.setFieldId(paymentEntrydto.getFieldId());
        }
        paymentEntrydto.setPaymentDate(Utility.stringToDate(paymentEntrydto.getTransactionDate()));
        paymentEntrydto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
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
        //paymentEntrydto.setFieldId(fieldId);
        paymentEntrydto.setCreatedDate(new Date());
        paymentEntrydto.setLanguageId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
        paymentEntrydto.setLgIpMacAddress(Utility.getClientIpAddress(request));
        paymentEntrydto.setOrganisation(organisation);
        paymentEntrydto.setPaymentDetailsDto(paymentEntrydto.getPaymentDetailsDto());
        paymentEntrydto = paymentEntryService.createPaymentEntry(paymentEntrydto);
        request.getSession().setAttribute(MainetConstants.DirectPaymentEntry.PAYMENT_LIST, paymentEntrydto);
        // this is for the the update of the deposit table in deposit it requires payment_Id to update Task #7144
        if (paymentEntrydto.getBillTypeId().equals(depLookup.getLookUpId())) {
        	for (PaymentDetailsDto paymentDto : paymentEntrydto.getPaymentDetailsDto()) {
        		paymentEntryService.updateDepositsByPaymentId(paymentDto.getId(),
                        paymentEntrydto.getPaymentId(), organisation.getOrgid());
			}
            
        }
        
      //file upload start
        if (paymentEntrydto.getAttachments() != null && paymentEntrydto.getAttachments().size() > 0) {
			prepareFileUpload(paymentEntrydto);
			String documentName = paymentEntrydto.getAttachments().get(0).getDocumentName();
			if (documentName != null && !documentName.isEmpty()) {
				FileUploadDTO fileUploadDTO = new FileUploadDTO();
				if (paymentEntrydto.getOrgId() != null) {
					fileUploadDTO.setOrgId(paymentEntrydto.getOrgId());
				}
				if (paymentEntrydto.getCreatedBy() != null) {
					fileUploadDTO.setUserId(paymentEntrydto.getCreatedBy());
				}
				fileUploadDTO.setStatus(MainetConstants.FlagA);
				fileUploadDTO.setDepartmentName(MainetConstants.RECEIPT_MASTER.Module);
				final String accountIds = MainetConstants.RECEIPT_MASTER.Module
						+ MainetConstants.operator.FORWARD_SLACE + paymentEntrydto.getPaymentId();
				fileUploadDTO.setIdfId(accountIds);
				boolean fileuploadStatus = accountFileUpload.doMasterFileUpload(paymentEntrydto.getAttachments(),
						fileUploadDTO);
				if (!fileuploadStatus) {
					throw new FrameworkException("Invoice upload is failed, do to upload file into filenet path");
				}
			}
		}
        List<Long> removeFileById = null;
		String fileId = paymentEntrydto.getRemoveFileById();
		if (fileId != null && !fileId.isEmpty()) {
			removeFileById = new ArrayList<>();
			String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
			for (String fields : fileArray) {
				removeFileById.add(Long.valueOf(fields));
			}
		}
		if (removeFileById != null && !removeFileById.isEmpty()) {
			paymentEntryService.updateUploadPaymentDeletedRecords(removeFileById,paymentEntrydto.getUpdatedBy());
		}
        //file upload end
        return paymentEntrydto.getPaymentNo();
    }

    public void prepareFileUpload(PaymentEntryDto paymentEntryDto) {
		List<DocumentDetailsVO> documentDetailsVOList = paymentEntryDto.getAttachments();
		paymentEntryDto.setAttachments(accountFileUpload.prepareFileUpload(documentDetailsVOList));
	}
    
    public void checkTemplate(final Model model) {
        final VoucherTemplateDTO postDTO = new VoucherTemplateDTO();
        postDTO.setTemplateType(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PN.toString(),
                AccountPrefix.MTP.toString(), UserSession.getCurrent().getOrganisation().getOrgid()));
        postDTO.setVoucherType(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PV.toString(),
                AccountPrefix.VOT.toString(), UserSession.getCurrent().getOrganisation().getOrgid()));
        postDTO.setDepartment(departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString()));
        postDTO.setTemplateFor(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PVE.toString(),
                AccountPrefix.TDP.toString(), UserSession.getCurrent().getOrganisation().getOrgid()));
        final boolean existTempalte = voucherTemplateService.isTemplateExist(postDTO,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (!existTempalte) {
            model.addAttribute(TEMPLATE_EXIST_FLAG, AccountConstants.N.toString());
        } else {
            model.addAttribute(TEMPLATE_EXIST_FLAG, AccountConstants.Y.toString());
        }
    }

    @RequestMapping(params = "viewPaymentEntryDetail")
    public String viewPaymentEntryDetailForReversal(@RequestParam("gridId") final long gridId,
            final HttpServletRequest request, final Model model) {
        final int langId = UserSession.getCurrent().getLanguageId();
        final PaymentEntryDto paymentEntryDto = paymentEntryService.getRecordForView(gridId,
                UserSession.getCurrent().getOrganisation().getOrgid(), langId);
      //file upload start
        final String accountIds = MainetConstants.RECEIPT_MASTER.Module + MainetConstants.operator.FORWARD_SLACE +gridId;
		final List<AttachDocs> attachDocsList = attachDocsService
				.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), accountIds);
		paymentEntryDto.setAttachDocsList(attachDocsList);
		//file upload end
        model.addAttribute(MainetConstants.PaymentEntry.PAYMENT_DETAIL, paymentEntryDto.getPaymentDetailsDto());
        populateField(model);
        populateModel(model, paymentEntryDto, MODE_VIEW);
        return JSP_VIEW_FORM;
    }

    @RequestMapping(params = "paymentGridPrintForm", method = RequestMethod.GET)
    public String paymentGridPrintForm(@RequestParam("gridId") final long gridId, final HttpServletRequest request,
            final Model model) {
        BigDecimal totalPaymentAmt = BigDecimal.ZERO;
        AccountPaymentMasterEntity entity = paymentEntryService.findById(gridId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        final PaymentReportDto oPaymentReportDto = new PaymentReportDto();
        final List<PaymentReportDto> paymentDetailsDto = new ArrayList<>();
        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        final Organisation org = UserSession.getCurrent().getOrganisation();
        final LookUp lkp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.DirectPaymentEntry.PV,
                PrefixConstants.ContraVoucherEntry.VOT, org);

        String voucheNo = voucherTemplateService.getVoucherNoBy(lkp.getLookUpId(), entity.getPaymentNo(), entity.getPaymentDate(),
                UserSession.getCurrent().getOrganisation().getOrgid());
        oPaymentReportDto.setVoucherType(lkp.getDescLangFirst());
        oPaymentReportDto.setVoucherTypeReg(lkp.getDescLangSecond());
        oPaymentReportDto.setVoucherNo(entity.getPaymentNo());
        oPaymentReportDto.setOrganisationName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
        /*
         * oPaymentReportDto.setVoucherAmount(CommonMasterUtility. getAmountInIndianCurrency(entity.getPaymentAmount()));
         */
        if (entity.getVmVendorId().getVmVendorid() != null) {
            oPaymentReportDto.setVendorCodeDescription(entity.getVmVendorId().getVmVendorname());
        }

        final Long vendorSacHeadIdDr = billEntryService.getVendorSacHeadIdByVendorId(
                entity.getVmVendorId().getVmVendorid(), UserSession.getCurrent().getOrganisation().getOrgid());
        String vendorAcHeadCode = "";
        if (vendorSacHeadIdDr != null) {
            vendorAcHeadCode = secondaryheadMasterService.findByAccountHead(vendorSacHeadIdDr);
        }
        oPaymentReportDto.setVendorAccountHead(vendorAcHeadCode);

        oPaymentReportDto.setNarration(entity.getNarration());

        oPaymentReportDto.setPaymentDate(entity.getPaymentDate());

        oPaymentReportDto.setPayDate(Utility.dateToString(entity.getPaymentDate()));

        if (entity.getPaymentModeId() != null && (entity.getPaymentModeId().getCpdDesc() != null
                && !entity.getPaymentModeId().getCpdDesc().isEmpty())) {
            oPaymentReportDto.setPaymentMode(entity.getPaymentModeId().getCpdDesc());
        }
        List<Object[]> bankAccountList = new ArrayList<>();

        if (entity.getBaBankAccountId() != null && entity.getBaBankAccountId().getBaAccountId() != null) {
            bankAccountList = banksMasterService.getBankAccountPayment(orgid,
                    entity.getBaBankAccountId().getBaAccountId());
        }

        if ((bankAccountList != null) && !bankAccountList.isEmpty()) {
            for (final Object obj[] : bankAccountList) {
                oPaymentReportDto.setBankName(obj[3].toString() + " - " + obj[1].toString() + " - " + obj[2].toString());
                oPaymentReportDto.setBankNumber(voucheNo);
                oPaymentReportDto.setAccountCode(obj[2].toString() + MainetConstants.HYPHEN + obj[1].toString());
                if (obj[4] != null) {
                    oPaymentReportDto.setNameOfTheFund(accountFundMasterService.getFundCodeDesc(Long.valueOf(obj[4].toString())));
                }
                paymentDetailsDto.add(oPaymentReportDto);
            }

        }

        if (entity.getInstrumentNumber() != null) {
            final String chequno = paymentEntryService.getCheque(entity.getInstrumentNumber());
            oPaymentReportDto.setChequeNo(chequno);
        }
        if (entity.getUtrNo() != null && !entity.getUtrNo().isEmpty()) {
            oPaymentReportDto.setChequeNo(entity.getUtrNo());
        }
        if (entity.getInstrumentDate() != null) {
            oPaymentReportDto.setInstrumentDate(entity.getInstrumentDate().toString());
        }

        List<AccountPaymentDetEntity> detList = entity.getPaymentDetailList();
        List<PaymentDetailsDto> detDTOList = new ArrayList<PaymentDetailsDto>();

        for (AccountPaymentDetEntity detDto : detList) {
            PaymentDetailsDto dto = new PaymentDetailsDto();
            final List<String> expenditures = getExpenditureDetailHead(detDto.getBillId(), detDto.getOrgId());
            if ((expenditures != null) && !expenditures.isEmpty()) {
                int j = 0;
                for (int i = j; i <= expenditures.size();) {

                    int index = expenditures.get(i).lastIndexOf("-");
                    // String headCode = expenditures.get(i).substring(0, index);
                    dto.setAccountCode(expenditures.get(i));
                    dto.setAccountHead(expenditures.get(i).substring(index + 1));
                    break;

                }

                dto.setPaymentAmountDesc(CommonMasterUtility.getAmountInIndianCurrency(detDto.getPaymentAmt()));

                totalPaymentAmt = totalPaymentAmt.add(new BigDecimal(detDto.getPaymentAmt().toString()));
                j++;
            }
            AccountBudgetCodeEntity accountHeadEntity = budgetCodeService
                    .findBudgetHeadIdBySacHeadId(Long.valueOf(detDto.getBudgetCodeId().toString()), detDto.getOrgId());
            if (accountHeadEntity != null) {
                if (accountHeadEntity.getTbAcFunctionMaster() != null
                        && (accountHeadEntity.getTbAcFunctionMaster().getFunctionDesc() != null
                                && !accountHeadEntity.getTbAcFunctionMaster().getFunctionDesc().isEmpty())) {
                    dto.setFunctionDesc(accountHeadEntity.getTbAcFunctionMaster().getFunctionDesc());
                }

                String departmentDesc = departmentService.getDepartmentDescByDeptCode(AccountConstants.AC.toString());
                dto.setFunctionaryDesc(departmentDesc);
            }
            detDTOList.add(dto);
        }
        String departmentDesc = departmentService.getDepartmentDescByDeptCode(AccountConstants.AC.toString());
        oPaymentReportDto.setDepatmentDesc(departmentDesc);

        String empName = "";
        final EmployeeBean bean = employeeService.findById(entity.getCreatedBy());
        if ((bean.getEmpmname() != null) && !bean.getEmpmname().isEmpty()) {
            empName = bean.getEmpname() + " " + bean.getEmpmname() + " " + bean.getEmplname();
        } else {
            if (bean.getEmplname() != null && !bean.getEmplname().isEmpty()) {
                empName = bean.getEmpname() + " " + bean.getEmplname();
            } else {
                empName = bean.getEmpname();
            }
        }
        oPaymentReportDto.setPreparedBy(empName);
        oPaymentReportDto.setPreparedDate(Utility.dateToString(entity.getCreatedDate()));
        oPaymentReportDto.setVoucherAmount(CommonMasterUtility.getAmountInIndianCurrency(totalPaymentAmt));
        oPaymentReportDto.setPaymentDetailsDto(detDTOList);
        final String amountInWords = Utility.convertBigNumberToWord(entity.getPaymentAmount());
        oPaymentReportDto.setAmountInWords(amountInWords);
        oPaymentReportDto.setListOfTbPaymentRepor(paymentDetailsDto);
        populateReportModel(model, oPaymentReportDto, MODE_CREATE);
        return Report_FORM;
    }

    @RequestMapping(params = "paymentReportForm", method = RequestMethod.POST)
    public String paymentReportForm(@Valid final PaymentEntryDto paymentEntrydto, final Model model,
            final HttpServletRequest request) throws ParseException {
        BigDecimal totalPaymentAmt = BigDecimal.ZERO;
        final PaymentEntryDto opaymentEntryDto = (PaymentEntryDto) request.getSession()
                .getAttribute(MainetConstants.DirectPaymentEntry.PAYMENT_LIST);
        final PaymentReportDto oPaymentReportDto = new PaymentReportDto();
        final List<PaymentReportDto> paymentDetailsDto = new ArrayList<>();
        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        final Organisation org = UserSession.getCurrent().getOrganisation();
        final LookUp lkp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.DirectPaymentEntry.PV,
                PrefixConstants.ContraVoucherEntry.VOT, org);
        String voucheNo = voucherTemplateService.getVoucherNoBy(lkp.getLookUpId(), opaymentEntryDto.getPaymentNo(),
                opaymentEntryDto.getPaymentDate(), UserSession.getCurrent().getOrganisation().getOrgid());
        oPaymentReportDto.setVoucherType(lkp.getDescLangFirst());
        oPaymentReportDto.setVoucherNo(opaymentEntryDto.getPaymentNo());
        oPaymentReportDto.setOrganisationName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
        oPaymentReportDto.setVoucherAmount(
                CommonMasterUtility.getAmountInIndianCurrency(opaymentEntryDto.getTotalPaymentAmount()));

        TbAcVendormaster tbAcVendormaster = null;
        if (opaymentEntryDto.getVendorId() != null) {
            tbAcVendormaster = vendorMasterService.findById(opaymentEntryDto.getVendorId(),
                    UserSession.getCurrent().getOrganisation().getOrgid());
            oPaymentReportDto.setVendorCodeDescription(tbAcVendormaster.getVmVendorname());
        }

        final Long vendorSacHeadIdDr = billEntryService.getVendorSacHeadIdByVendorId(opaymentEntryDto.getVendorId(),
                UserSession.getCurrent().getOrganisation().getOrgid());
        String vendorAcHeadCode = "";
        if (vendorSacHeadIdDr != null) {
            vendorAcHeadCode = secondaryheadMasterService.findByAccountHead(vendorSacHeadIdDr);
        }
        oPaymentReportDto.setVendorAccountHead(vendorAcHeadCode);

        oPaymentReportDto.setNarration(opaymentEntryDto.getNarration());

        oPaymentReportDto.setPaymentDate(opaymentEntryDto.getPaymentDate());

        oPaymentReportDto.setPayDate(opaymentEntryDto.getTransactionDate());

        String paymentModeDesc = CommonMasterUtility.findLookUpDesc(AccountPrefix.PAY.name(),
                opaymentEntryDto.getOrgId(), opaymentEntryDto.getPaymentMode());

        if (paymentModeDesc != null) {
            oPaymentReportDto.setPaymentMode(paymentModeDesc);
        }

        List<Object[]> bankAccountList = new ArrayList<>();
        bankAccountList = banksMasterService.getBankAccountPayment(orgid, opaymentEntryDto.getBankAcId());
        if ((bankAccountList != null) && !bankAccountList.isEmpty()) {
            for (final Object obj[] : bankAccountList) {

                oPaymentReportDto.setBankName(obj[3].toString() + " - " + obj[1].toString() + " - " + obj[2].toString());
                oPaymentReportDto.setBankNumber(voucheNo);
                oPaymentReportDto.setAccountCode(obj[2].toString() + MainetConstants.HYPHEN + obj[1].toString());
                if (obj[4] != null) {
                    oPaymentReportDto.setNameOfTheFund(accountFundMasterService.getFundCodeDesc(Long.valueOf(obj[4].toString())));
                }
                paymentDetailsDto.add(oPaymentReportDto);
            }

        }

        final int langId = UserSession.getCurrent().getLanguageId();
        LookUp isChequeIssanceRequired=null;
        LookUp lkpStatus = null;
        try {
          isChequeIssanceRequired = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.CIR.getValue(),
                AccountPrefix.AIC.toString(), langId, org);
        }catch(Exception e) {
        	LOGGER.error("AIC Prefix not found ", e);
        }
        if(isChequeIssanceRequired!=null && isChequeIssanceRequired.getOtherField().equals(MainetConstants.Y_FLAG)) {
   		     lkpStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.READY_FOR_ISSUE.getValue(),
                    AccountPrefix.CLR.toString(), langId, org);
        }else {
        	 lkpStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.ISSUED.getValue(),
                     AccountPrefix.CLR.toString(), langId, org);
        }
        
        final Long cpdIdStatus = lkpStatus.getLookUpId();
        if (opaymentEntryDto.getInstrumentNo() != null) {
            final String chequno = paymentEntryService.getInstrumentChequeNo(cpdIdStatus,
                    opaymentEntryDto.getInstrumentNo());
            oPaymentReportDto.setChequeNo(chequno);
        }
        final LookUp lookUpBank = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MENU.B,
                AccountPrefix.PAY.toString(), opaymentEntryDto.getLanguageId().intValue(),
                opaymentEntryDto.getOrganisation());
        Long lookUpIdBank = lookUpBank.getLookUpId();
        if (opaymentEntryDto.getPaymentMode().equals(lookUpIdBank)) {
            oPaymentReportDto.setChequeNo(opaymentEntryDto.getUtrNo());
        }
        oPaymentReportDto.setInstrumentDate(opaymentEntryDto.getInstrumentDate());

        AccountPaymentMasterEntity entity = paymentEntryService.findByPaymentNumber(opaymentEntryDto.getPaymentNo(),
                UserSession.getCurrent().getOrganisation().getOrgid(), opaymentEntryDto.getPaymentDate());

        if (entity != null && entity.getPaymentDetailList() != null && !entity.getPaymentDetailList().isEmpty()) {
            List<AccountPaymentDetEntity> detList = entity.getPaymentDetailList();
            List<PaymentDetailsDto> detDTOList = new ArrayList<PaymentDetailsDto>();
            for (AccountPaymentDetEntity paymentDetailsDto2 : detList) {
                String acHeadCode = secondaryheadMasterService.findByAccountHead(paymentDetailsDto2.getBudgetCodeId());
                PaymentDetailsDto dto = new PaymentDetailsDto();
                dto.setAccountCode(acHeadCode);
                AccountBudgetCodeEntity accountHeadEntity = budgetCodeService.findBudgetHeadIdBySacHeadId(
                        Long.valueOf(paymentDetailsDto2.getBudgetCodeId().toString()), paymentDetailsDto2.getOrgId());
                if (accountHeadEntity != null) {
                    if (accountHeadEntity.getTbAcFunctionMaster() != null
                            && (accountHeadEntity.getTbAcFunctionMaster().getFunctionDesc() != null
                                    && !accountHeadEntity.getTbAcFunctionMaster().getFunctionDesc().isEmpty())) {
                        dto.setFunctionDesc(accountHeadEntity.getTbAcFunctionMaster().getFunctionDesc());
                    }

                    String departmentDesc = departmentService.getDepartmentDescByDeptCode(AccountConstants.AC.toString());
                    dto.setFunctionaryDesc(departmentDesc);
                }
                if (paymentDetailsDto2.getPaymentAmt() != null) {
                    dto.setPaymentAmountDesc(
                            CommonMasterUtility.getAmountInIndianCurrency(paymentDetailsDto2.getPaymentAmt()));

                    totalPaymentAmt = totalPaymentAmt
                            .add(new BigDecimal(paymentDetailsDto2.getPaymentAmt().toString()));
                }
                detDTOList.add(dto);
            }

            oPaymentReportDto.setPaymentDetailsDto(detDTOList);
        }

        String departmentDesc = departmentService.getDepartmentDescByDeptCode(AccountConstants.AC.toString());
        oPaymentReportDto.setDepatmentDesc(departmentDesc);

        String empName = "";
        final EmployeeBean bean = employeeService.findById(opaymentEntryDto.getCreatedBy());
        if ((bean.getEmpmname() != null) && !bean.getEmpmname().isEmpty()) {
            empName = bean.getEmpname() + " " + bean.getEmpmname() + " " + bean.getEmplname();
        } else {
            if (bean.getEmplname() != null && !bean.getEmplname().isEmpty()) {
                empName = bean.getEmpname() + " " + bean.getEmplname();
            } else {
                empName = bean.getEmpname();
            }
        }
        oPaymentReportDto.setPreparedBy(empName);
        oPaymentReportDto.setPreparedDate(Utility.dateToString(opaymentEntryDto.getCreatedDate()));

        final String amountInWords = Utility.convertBigNumberToWord(opaymentEntryDto.getTotalPaymentAmount());
        oPaymentReportDto.setAmountInWords(amountInWords);
        oPaymentReportDto.setListOfTbPaymentRepor(paymentDetailsDto);

        populateReportModel(model, oPaymentReportDto, MODE_CREATE);
        return Report_FORM;
    }

    private List<Long> getExpenditureDetails(final Long billId, final Long orgId) {
        return paymentEntryService.getExpenditureDetDetails(billId, orgId);
    }

    public void populateReportModel(final Model model, final PaymentReportDto oPaymentReportDto,
            final String formMode) {
        model.addAttribute(PAYMENT_REPORT_DTO, oPaymentReportDto);
        populateListOfPaymentType(model);
        populateListOfBillType(model);
        populateVendorList(model);
        populateBillDetails(model);
        populatePaymentModeList(model);
        populateListOfBanks(model);
        checkTemplate(model);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "reverse", method = RequestMethod.POST)
    public @ResponseBody String saveReversal(@Valid final VoucherReversalDTO dto, final HttpServletRequest request) {

        final List<VoucherReversalDTO> sessionData = (List<VoucherReversalDTO>) request.getSession()
                .getAttribute(MainetConstants.AccountBillEntry.REVERSAL_GRID_DATA);
        String result = StringUtils.EMPTY;
        final ResponseEntity<?> response = accountChequeOrCashDepositeService.validateDataForReversal(sessionData, dto,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (response.getStatusCode() == HttpStatus.OK) {
            dto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            dto.setLangId(UserSession.getCurrent().getLanguageId());
            Long fieldId = null;
            if (UserSession.getCurrent().getEmployee().getTbLocationMas() != null) {
                fieldId = locationMasService.getcodIdRevLevel1ByLocId(
                        UserSession.getCurrent().getEmployee().getTbLocationMas().getLocId(),
                        UserSession.getCurrent().getOrganisation().getOrgid());
            }
            if ((fieldId == null) || (fieldId == 0)) {
                throw new NullPointerException("fieldId is not linked with Location Master for[locId="
                        + UserSession.getCurrent().getEmployee().getTbLocationMas().getLocId() + ",locName="
                        + UserSession.getCurrent().getEmployee().getTbLocationMas().getLocNameEng() + "]");
            }

            paymentEntryService.reversePaymentEntry((List<String>) response.getBody(), dto, fieldId,
                    UserSession.getCurrent().getOrganisation().getOrgid(), Utility.getClientIpAddress(request));
            result = ApplicationSession.getInstance().getMessage("account.depositslip.result.success");
        } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            result = (String) response.getBody();
        }

        return result;

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "getGridData", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse geGridResults(final HttpServletRequest request,
            @RequestParam final String rows) {
        final JQGridResponse response = new JQGridResponse<>();
        List<PaymentEntryDto> gridRecords = new ArrayList<>();
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        if (request.getSession().getAttribute(MainetConstants.PaymentEntry.GRID_ITEM) != null) {
            gridRecords = (List<PaymentEntryDto>) request.getSession()
                    .getAttribute(MainetConstants.PaymentEntry.GRID_ITEM);
        }
        response.setRows(gridRecords);
        response.setTotal(gridRecords.size());
        response.setRecords(gridRecords.size());
        response.setPage(page);

        return response;
    }

    @RequestMapping(params = "searchForPaymentEntry", method = RequestMethod.POST)
    public @ResponseBody String searchRecords(@Valid final PaymentEntryDto dto, final BindingResult bindingResult,
            final HttpServletRequest request, final ModelMap model) {
        String resultMsg = StringUtils.EMPTY;
        final ResponseEntity<?> responseEntity = paymentEntryService.searchRecordsForPaymentEntry(dto,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (responseEntity.getStatusCode() == HttpStatus.FOUND) {
            model.addAttribute(MainetConstants.PaymentEntry.GRID_RECORD, responseEntity.getBody());
            request.getSession().setAttribute(MainetConstants.PaymentEntry.GRID_ITEM, responseEntity.getBody());
        } else {
            resultMsg = (String) responseEntity.getBody();
            model.addAttribute(MainetConstants.PaymentEntry.GRID_RECORD, null);
            request.getSession().setAttribute(MainetConstants.PaymentEntry.GRID_ITEM, null);
            LOGGER.error("Problem while searching for Payment Entry No Record found:" + dto);
        }
        return resultMsg;
    }

    private List<String> getExpenditureDetailHead(Long billId, Long orgId) {

        return paymentEntryService.getExpenditureDetailHead(billId, orgId);
    }

    @RequestMapping(params = "getAllExpenditureHeadData", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> getAllBudgetHeadExpDesc(final PaymentEntryDto dto, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest request,
            final BindingResult bindingResult, @RequestParam("billIdNo") final Long billIdNo) {
        log("Action 'getAllExpenditureHeadData' : 'get getAllExpenditureHeadData data'");
        Map<Long, String> dedutionExpHeadMap = new LinkedHashMap<>();
        List<AccountBillEntryMasterEnitity> billEntryData = billEntryService.getBillDataByBillId(billIdNo,
                UserSession.getCurrent().getOrganisation().getOrgid());
        for (AccountBillEntryMasterEnitity accountBillEntryMasterEnitity : billEntryData) {
            List<AccountBillEntryExpenditureDetEntity> expDetList = accountBillEntryMasterEnitity
                    .getExpenditureDetailList();
            for (AccountBillEntryExpenditureDetEntity accountBillEntryExpenditureDetBean : expDetList) {
                Long sacheadid = accountBillEntryExpenditureDetBean.getSacHeadId();
                String accountHeadCode = "";
                if (sacheadid != null) {
                    accountHeadCode = secondaryheadMasterService.findByAccountHead(sacheadid);
                    dedutionExpHeadMap.put(sacheadid, accountHeadCode);
                }
            }
        }
        return dedutionExpHeadMap;
    }

    
	@RequestMapping(params = "getNarretionFromBill", method = RequestMethod.POST)
	public @ResponseBody String getNarretionFromBill(final PaymentEntryDto dto, final Model model,
			final RedirectAttributes redirectAttributes, final HttpServletRequest request,
			final BindingResult bindingResult, @RequestParam("billIdNo") final Long billIdNo) {
		log("Action 'getNarretionFromBill' : 'get getNarretionFromBill data'");
		String narration = "";
		List<AccountBillEntryMasterEnitity> billEntryData = billEntryService.getBillDataByBillId(billIdNo,
				UserSession.getCurrent().getOrganisation().getOrgid());
		if (CollectionUtils.isNotEmpty(billEntryData))
			narration = billEntryData.get(0).getNarration();
		return narration;
	}    
    
    @RequestMapping(params = "searchBillSumaryWiseData")
    public @ResponseBody List<PaymentEntryDto> searchBillSumaryWiseData(@RequestParam("billId") final Long billId) {

        List<PaymentEntryDto> masterBeanList = null;
        masterBeanList = new ArrayList<>();
        PaymentEntryDto masterDto = null;
        List<AccountBillEntryMasterEnitity> billEntryList = null;
        BigDecimal billAmountStr = BigDecimal.ZERO;
        String deductionsStr = null;
        String netPayableStr = null;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        if (billId != null) {
            billEntryList = billEntryService.getBillDataByBillId(billId, orgId);
        }
        if ((billEntryList != null) && !billEntryList.isEmpty()) {
            for (final AccountBillEntryMasterEnitity list : billEntryList) {
                BigDecimal newNetPayableAmt = BigDecimal.ZERO;
                BigDecimal newNetBalPayableAmt = BigDecimal.ZERO;
                BigDecimal totalDeductionAmount = BigDecimal.ZERO;
                BigDecimal totalDisallowedAmount = BigDecimal.ZERO;
                BigDecimal netPayableAmount = BigDecimal.ZERO;
                masterDto = new PaymentEntryDto();
                masterDto.setId(list.getId());
                masterDto.setBillNo(list.getBillNo());
                masterDto.setBillDate(UtilityService.convertDateToDDMMYYYY(list.getBillEntryDate()));
                masterDto.setBillTotalAmount(list.getBillTotalAmount());
                final List<AccountBillEntryExpenditureDetEntity> expDetList = list.getExpenditureDetailList();
                if ((expDetList != null) && !expDetList.isEmpty()) {
                    for (final AccountBillEntryExpenditureDetEntity expDetEntity : expDetList) {
                        if (expDetEntity.getFi04V1() != null && !expDetEntity.getFi04V1().isEmpty()) {
                            newNetBalPayableAmt = newNetBalPayableAmt.add(new BigDecimal(expDetEntity.getFi04V1()));
                        } else {
                            newNetPayableAmt = newNetPayableAmt.add(expDetEntity.getBillChargesAmount());
                        }
                        if (expDetEntity.getBillChargesAmount() != null) {
                            billAmountStr = billAmountStr.add(expDetEntity.getBillChargesAmount());
                        }
                        if ((expDetEntity.getDisallowedAmount() != null)
                                && !expDetEntity.getDisallowedAmount().equals(MainetConstants.BLANK)) {
                            totalDisallowedAmount = totalDisallowedAmount.add(expDetEntity.getDisallowedAmount());
                            if (expDetEntity.getBudgetCodeId() != null) {
                                masterDto.setBudgetCodeId(expDetEntity.getBudgetCodeId().getprBudgetCodeid());
                            }
                        }
                    }
                } else {
                    LOGGER.error("Expenditure data not found");
                }
                final List<AccountBillEntryDeductionDetEntity> dedDetList = list.getDeductionDetailList();
                if ((dedDetList != null) && !dedDetList.isEmpty()) {
                    for (final AccountBillEntryDeductionDetEntity dedDetEntity : dedDetList) {
                        if (dedDetEntity.getDeductionAmount() != null) {
                            totalDeductionAmount = totalDeductionAmount.add(dedDetEntity.getDeductionAmount());
                            masterDto.setTotalDeductions(totalDeductionAmount);
                            deductionsStr = CommonMasterUtility.getAmountInIndianCurrency(totalDeductionAmount);
                            masterDto.setDeductionsStr(deductionsStr);
                        }
                    }
                }
                netPayableAmount = masterDto.getBillTotalAmount()
                        .subtract(totalDisallowedAmount.add(totalDeductionAmount));
                final BigDecimal netPayable = netPayableAmount.setScale(2, RoundingMode.CEILING);
                if (newNetBalPayableAmt != null && newNetBalPayableAmt != BigDecimal.ZERO) {
                    BigDecimal sumPaymentAmount = billEntryService.findPaymentAmount(billId, orgId);
                    if (sumPaymentAmount != null) {
                        newNetBalPayableAmt = newNetBalPayableAmt.add(sumPaymentAmount);
                    }
                    if (newNetBalPayableAmt.compareTo(netPayableAmount) == 1) {
                        newNetBalPayableAmt = newNetBalPayableAmt.subtract(new BigDecimal(1));
                    } else if (newNetBalPayableAmt.compareTo(netPayableAmount) == -1) {
                        BigDecimal subPaymentAmt = netPayableAmount.subtract(newNetBalPayableAmt);
                        if (subPaymentAmt.compareTo(new BigDecimal(1)) == 0) {
                            newNetBalPayableAmt = newNetBalPayableAmt.add(new BigDecimal(1));
                        } else {
                            newNetBalPayableAmt = newNetBalPayableAmt.add(subPaymentAmt);
                        }
                    }
                    newNetBalPayableAmt = newNetBalPayableAmt.subtract(sumPaymentAmount);
                    masterDto.setNetPayable(newNetBalPayableAmt);
                    netPayableStr = CommonMasterUtility
                            .getAmountInIndianCurrency(newNetBalPayableAmt);
                    masterDto.setNetPayableStr(netPayableStr);
                } else if (newNetPayableAmt != null && newNetPayableAmt != BigDecimal.ZERO) {
                    masterDto.setNetPayable(newNetPayableAmt.subtract(totalDeductionAmount));
                    netPayableStr = CommonMasterUtility
                            .getAmountInIndianCurrency(newNetPayableAmt.subtract(totalDeductionAmount));
                    masterDto.setNetPayableStr(netPayableStr);
                } else {
                    masterDto.setNetPayable(list.getBalanceAmount());
                    netPayableStr = CommonMasterUtility.getAmountInIndianCurrency(list.getBalanceAmount());
                    masterDto.setNetPayableStr(netPayableStr);
                }
                masterDto.setBillAmountStr(CommonMasterUtility.getAmountInIndianCurrency(billAmountStr));
                masterBeanList.add(masterDto);
            }
        } else {
            LOGGER.error(NO_RECORD_FOUND + billId);
        }
        return masterBeanList;
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
