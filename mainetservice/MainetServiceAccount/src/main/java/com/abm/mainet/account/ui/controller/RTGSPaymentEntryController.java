package com.abm.mainet.account.ui.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
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

import com.abm.mainet.account.domain.AccountBillEntryDeductionDetEntity;
import com.abm.mainet.account.domain.AccountBillEntryExpenditureDetEntity;
import com.abm.mainet.account.domain.AccountBillEntryMasterEnitity;
import com.abm.mainet.account.domain.AccountBudgetProjectedExpenditureEntity;
import com.abm.mainet.account.domain.AccountPaymentDetEntity;
import com.abm.mainet.account.domain.AccountPaymentMasterEntity;
import com.abm.mainet.account.dto.AccountBillEntryExpenditureDetBean;
import com.abm.mainet.account.dto.AccountBillEntryMasterBean;
import com.abm.mainet.account.dto.PaymentEntryDto;
import com.abm.mainet.account.dto.PaymentReportDto;
import com.abm.mainet.account.dto.RTGSPaymentEntryDTO;
import com.abm.mainet.account.dto.VoucherTemplateDTO;
import com.abm.mainet.account.service.AccountBillEntryService;
import com.abm.mainet.account.service.AccountBudgetProjectedExpenditureService;
import com.abm.mainet.account.service.PaymentEntrySrevice;
import com.abm.mainet.account.service.VoucherTemplateService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.dto.TbBankmaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.PaymentDetailsDto;
import com.abm.mainet.common.integration.acccount.dto.RTGSPaymentDetailsDTO;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
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
@RequestMapping("/RTGSPaymentEntry.html")
public class RTGSPaymentEntryController extends AbstractController {

    private final static String RTGS_PAYMENT_ENTRY_DTO = "RTGSPaymentEntryDto";
    private static final String BILL_TYPE_LIST = "billTypeList";
    private static final String BILL_TYPE_LIST_P = "billTypeListP";
    private static final String PAYMENT_TYPE_LIST = "paymentTypeList";
    private static final String VENDOR_LIST = "vendorList";
    private static final String BILL_DETAIL_LIST = "billDetailList";
    private static final String PAYMENT_MODE = "paymentMode";
    private static final String BANK_AC_MAP = "bankAccountMap";
    private final String JSP_FORM = "RTGSPaymentEntry/Form";
    private static final String NO_RECORD_FOUND = "No record found for bill containing id : ";
    private static final String TEMPLATE_EXIST_FLAG = "templateExistFlag";
    private final static String PAYMENT_REPORT_DTO = "oPaymentReportDto";
    private final String Report_FORM = "RTGSPaymentEntry/Report";
    private final String JSP_VIEW_FORM = "RTGSPaymentEntry/view";
    private final String JSP_RTGS_BILL_VIEW_FORM = "RTGSPaymentEntry/viewRTGSBillForm";
    private final String BUDGET_DETAILS = "budgetDetails";
    private static final String ACCOUNT_BILL_EXPENDITURE_DETAILS = "AccountRTGSBillExpenditureDetails";
    private static final Logger LOGGER = Logger.getLogger(RTGSPaymentEntryController.class);

    @Resource
    private TbAcVendormasterService vendorMasterService;
    @Resource
    private TbBankmasterService banksMasterService;
    @Resource
    private SecondaryheadMasterService secondaryheadMasterService;
    @Resource
    private AccountBillEntryService billEntryService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private VoucherTemplateService voucherTemplateService;
    @Resource
    private ILocationMasService locMasService;
    @Resource
    PaymentEntrySrevice paymentEntryService;
    @Resource
    private TbFinancialyearService financialyearService;
    @Resource
    private AccountBudgetProjectedExpenditureService accountBudgetProjectedExpenditureService;
    @Resource
   	private IAttachDocsService attachDocsService;
    @Autowired
   	private IFileUploadService accountFileUpload;

    public RTGSPaymentEntryController() {
        super(RTGSPaymentEntryController.class, RTGS_PAYMENT_ENTRY_DTO);
        LOGGER.info("RTGS Payment entry controller created");
    }

    @RequestMapping()
    public String index(final Model model, final HttpServletRequest httpServletRequest) {
        log("Action 'form'");
        final RTGSPaymentEntryDTO rtgsPaymentEntryDTO = new RTGSPaymentEntryDTO();
        httpServletRequest.getSession().setAttribute(MainetConstants.RTGSPaymentEntry.GRID_ITEM, null);
        populateModel(model, rtgsPaymentEntryDTO, MODE_VIEW);
        return MainetConstants.RTGSPaymentEntry.RTGS_PAYMENT_ENTRY;
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

    @RequestMapping(params = "createPage", method = RequestMethod.POST)
    public String populateGridList(final Model model, final HttpServletRequest httpServletRequest) {
        log("Action 'form'");
        accountFileUpload.sessionCleanUpForFileUpload();
        final RTGSPaymentEntryDTO rtgsPaymentEntryDTO = new RTGSPaymentEntryDTO();
        populateModel(model, rtgsPaymentEntryDTO, MODE_CREATE);
        return JSP_FORM;
    }

    @RequestMapping(params = "showData", method = RequestMethod.POST)
    public String populateGridList(final Model model, final HttpServletRequest httpServletRequest, final HttpSession session) {
        log("Action 'form'");
        final RTGSPaymentEntryDTO rtgsPaymentEntryDTO = (RTGSPaymentEntryDTO) session.getAttribute("rtgsPaymentEntry");
        populateModel(model, rtgsPaymentEntryDTO, MODE_UPDATE);
        generateBillNumbers(model, rtgsPaymentEntryDTO);
        session.removeAttribute("rtgsPaymentEntry");
        return JSP_FORM;
    }

    private void generateBillNumbers(Model model, RTGSPaymentEntryDTO rtgsPaymentEntryDTO) {
        Date payDate = UtilityService.convertStringDateToDateFormat(rtgsPaymentEntryDTO.getTransactionDate());
        Long vendorId = rtgsPaymentEntryDTO.getDupVendorId();
        final List<Object[]> billNumbers = billEntryService
                .getBillNumbers(UserSession.getCurrent().getOrganisation().getOrgid(),
                        vendorId, payDate);
        final Map<Long, String> billNumberMap = new HashMap<>();
        if ((billNumbers != null) && !billNumbers.isEmpty()) {
            for (final Object[] obj : billNumbers) {
                billNumberMap.put((Long) obj[0], (String) obj[1]);
            }
        } else {
            LOGGER.error(NO_RECORD_FOUND + rtgsPaymentEntryDTO.getBillTypeId() + " and " + vendorId);
        }
    }

    @RequestMapping(params = "searchForRTGSPaymentEntry", method = RequestMethod.POST)
    public @ResponseBody String searchRecords(@Valid final RTGSPaymentEntryDTO dto, final BindingResult bindingResult,
            final HttpServletRequest request, final ModelMap model) {
        String resultMsg = StringUtils.EMPTY;
        final ResponseEntity<?> responseEntity = paymentEntryService.searchRecordsForRTGSPaymentEntry(dto,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (responseEntity.getStatusCode() == HttpStatus.FOUND) {
            model.addAttribute(MainetConstants.PaymentEntry.GRID_RECORD, responseEntity.getBody());
            request.getSession().setAttribute(MainetConstants.PaymentEntry.GRID_ITEM, responseEntity.getBody());
        } else {
            resultMsg = (String) responseEntity.getBody();
            model.addAttribute(MainetConstants.PaymentEntry.GRID_RECORD, null);
            request.getSession().setAttribute(MainetConstants.PaymentEntry.GRID_ITEM, null);
            LOGGER.error("Problem while searching for RTGS Payment Entry No Record found:" + dto);
        }
        return resultMsg;
    }

    public void populateModel(final Model model, final RTGSPaymentEntryDTO rtgsPaymentEntryDTO, final String formMode) {
        model.addAttribute(RTGS_PAYMENT_ENTRY_DTO, rtgsPaymentEntryDTO);
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
                .filter(look -> look.getLookUpCode() != null
                        && !look.getLookUpCode().equals(MainetConstants.AccountBillEntry.BILL_TYPE_ESB))
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

    public void populateBillDetails(final Model model) {

        final PaymentEntryDto paymentDto = new PaymentEntryDto();
        final PaymentDetailsDto billDetailDto = new PaymentDetailsDto();
        final List<PaymentDetailsDto> billDetailList = new ArrayList<>();
        billDetailList.add(billDetailDto);
        paymentDto.setPaymentDetailsDto(billDetailList);
        model.addAttribute(BILL_DETAIL_LIST, billDetailList);
    }

    public void populatePaymentModeList(final Model model) {
        final List<LookUp> paymentModeList = CommonMasterUtility.getLookUps(AccountPrefix.PAY.name(),
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> paymentMode = new ArrayList<>();
        for (final LookUp payList : paymentModeList) {
            if (payList.getLookUpCode().equals(AccountConstants.CHEQUE.getValue())
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

    @RequestMapping(params = "checkPaymentDateisExists", method = RequestMethod.POST)
    public @ResponseBody boolean findBudgetOpenBalDuplicateCombination(RTGSPaymentEntryDTO rtgsPaymentEntryDTO,
            final HttpServletRequest request, final Model model, final BindingResult bindingResult) {
        Date payDate = UtilityService.convertStringDateToDateFormat(rtgsPaymentEntryDTO.getTransactionDate());
        boolean isValidationError = false;
        if (billEntryService.isPaymentDateisExists(UserSession.getCurrent().getOrganisation().getOrgid(), payDate)) {
            bindingResult.addError(new org.springframework.validation.FieldError(RTGS_PAYMENT_ENTRY_DTO,
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
                .getBillNumbers(UserSession.getCurrent().getOrganisation().getOrgid(),vendorId, payDate);
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

    @RequestMapping(params = "viewRTGSPaymentEntryDetail", method = RequestMethod.GET)
    public String viewPaymentEntryDetailForReversal(@RequestParam("gridId") final long gridId,
            final HttpServletRequest request, final Model model) {
        final int langId = UserSession.getCurrent().getLanguageId();
        final RTGSPaymentEntryDTO rtgsPpaymentEntryDto = paymentEntryService.getRTGSRecordForView(gridId,
                UserSession.getCurrent().getOrganisation().getOrgid(), langId);
      //file upload start
        final String accountIds = MainetConstants.RECEIPT_MASTER.Module + MainetConstants.operator.FORWARD_SLACE +gridId;
		final List<AttachDocs> attachDocsList = attachDocsService
				.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), accountIds);
		rtgsPpaymentEntryDto.setAttachDocsList(attachDocsList);
		//file upload end
        model.addAttribute(MainetConstants.PaymentEntry.PAYMENT_DETAIL, rtgsPpaymentEntryDto.getRtgsPaymentDetailsDto());
        populateModel(model, rtgsPpaymentEntryDto, MODE_VIEW);
        return JSP_VIEW_FORM;
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
                masterDto.setBillTypeId(list.getBillTypeId().getCpdId());
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

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public @ResponseBody String createBillEntry(@Valid RTGSPaymentEntryDTO rtgsPaymentEntryDTO, final Model model,
            final HttpServletRequest request) throws ParseException {

        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final int langsId = UserSession.getCurrent().getLanguageId();
        rtgsPaymentEntryDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        final LookUp depLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.AccountBillEntry.DE,
                AccountPrefix.ABT.toString(), langsId, organisation);
        log("Action 'create payment form'");
        Long depLookUpId = depLookup.getLookUpId();
        if (depLookUpId.equals(rtgsPaymentEntryDTO.getDupBillTypeId())) {
            final List<RTGSPaymentDetailsDTO> billDetails = rtgsPaymentEntryDTO.getRtgsPaymentDetailsDto();
            if ((billDetails != null) && !billDetails.isEmpty()) {
                for (final RTGSPaymentDetailsDTO billDetail : billDetails) {
                    BigDecimal oldDepDefundAmt = paymentEntryService.getDepDefundAmountDetailsCheck(billDetail.getId(),
                            rtgsPaymentEntryDTO.getOrgId());
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

        rtgsPaymentEntryDTO.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);
        rtgsPaymentEntryDTO.setSuccessfulFlag(MainetConstants.MASTER.Y);

        if (rtgsPaymentEntryDTO.getDupBillTypeId() != null) {
            rtgsPaymentEntryDTO.setBillTypeId(rtgsPaymentEntryDTO.getDupBillTypeId());
        }
        if (rtgsPaymentEntryDTO.getDupTransactionDate() != null && !rtgsPaymentEntryDTO.getDupTransactionDate().isEmpty()) {
            rtgsPaymentEntryDTO.setTransactionDate(rtgsPaymentEntryDTO.getDupTransactionDate());
        }
        rtgsPaymentEntryDTO.setPaymentDate(Utility.stringToDate(rtgsPaymentEntryDTO.getTransactionDate()));
        rtgsPaymentEntryDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
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
        rtgsPaymentEntryDTO.setFieldId(fieldId);
        rtgsPaymentEntryDTO.setCreatedDate(new Date());
        rtgsPaymentEntryDTO.setLanguageId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
        rtgsPaymentEntryDTO.setLgIpMacAddress(Utility.getClientIpAddress(request));
        rtgsPaymentEntryDTO.setOrganisation(organisation);
        rtgsPaymentEntryDTO.setRtgsPaymentDetailsDto(rtgsPaymentEntryDTO.getRtgsPaymentDetailsDto());
        rtgsPaymentEntryDTO = paymentEntryService.createRTGSPaymentEntryFormData(rtgsPaymentEntryDTO);
        //file upload start
        if (rtgsPaymentEntryDTO.getAttachments() != null && rtgsPaymentEntryDTO.getAttachments().size() > 0) {
			prepareFileUpload(rtgsPaymentEntryDTO);
			
			String documentName = rtgsPaymentEntryDTO.getAttachments().get(0).getDocumentName();
			if (documentName != null && !documentName.isEmpty()) {

				FileUploadDTO fileUploadDTO = new FileUploadDTO();
				if (rtgsPaymentEntryDTO.getOrgId() != null) {
					fileUploadDTO.setOrgId(rtgsPaymentEntryDTO.getOrgId());
				}
				if (rtgsPaymentEntryDTO.getCreatedBy() != null) {
					fileUploadDTO.setUserId(rtgsPaymentEntryDTO.getCreatedBy());
				}
				fileUploadDTO.setStatus(MainetConstants.FlagA);
				fileUploadDTO.setDepartmentName(MainetConstants.RECEIPT_MASTER.Module);
				final String accountIds = MainetConstants.RECEIPT_MASTER.Module
						+ MainetConstants.operator.FORWARD_SLACE + rtgsPaymentEntryDTO.getPaymentId();
				fileUploadDTO.setIdfId(accountIds);
				boolean fileuploadStatus = accountFileUpload.doMasterFileUpload(rtgsPaymentEntryDTO.getAttachments(),
						fileUploadDTO);
				if (!fileuploadStatus) {
					throw new FrameworkException("Invoice upload is failed, do to upload file into filenet path");
				}
			}
		}
        
        List<Long> removeFileById = null;
		String fileId = rtgsPaymentEntryDTO.getRemoveFileById();
		if (fileId != null && !fileId.isEmpty()) {
			removeFileById = new ArrayList<>();
			String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
			for (String fields : fileArray) {
				removeFileById.add(Long.valueOf(fields));
			}
		}
		if (removeFileById != null && !removeFileById.isEmpty()) {
			paymentEntryService.updateUploadPaymentDeletedRecords(removeFileById,rtgsPaymentEntryDTO.getUpdatedBy());
		}
        //file upload end
        request.getSession().setAttribute(MainetConstants.DirectPaymentEntry.PAYMENT_LIST, rtgsPaymentEntryDTO);
        return rtgsPaymentEntryDTO.getPaymentNo();
    }

    public void prepareFileUpload(RTGSPaymentEntryDTO rtgsPaymentEntryDto) {
  		List<DocumentDetailsVO> documentDetailsVOList = rtgsPaymentEntryDto.getAttachments();
  		rtgsPaymentEntryDto.setAttachments(accountFileUpload.prepareFileUpload(documentDetailsVOList));
  	}
    
    @RequestMapping(params = "paymentReportForm", method = RequestMethod.POST)
    public String paymentReportForm(@Valid final RTGSPaymentEntryDTO rtgsPaymentEntryDTO, final Model model,
            final HttpServletRequest request) throws ParseException {
        BigDecimal totalPaymentAmt = BigDecimal.ZERO;
        final RTGSPaymentEntryDTO oRTGSPaymentEntryDto = (RTGSPaymentEntryDTO) request.getSession()
                .getAttribute(MainetConstants.DirectPaymentEntry.PAYMENT_LIST);
        final PaymentReportDto oPaymentReportDto = new PaymentReportDto();
        final List<PaymentReportDto> paymentDetailsDto = new ArrayList<>();
        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        final Organisation org = UserSession.getCurrent().getOrganisation();
        final LookUp lkp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.DirectPaymentEntry.PV,
                PrefixConstants.ContraVoucherEntry.VOT, org);
        oPaymentReportDto.setVoucherType(lkp.getDescLangFirst());
        oPaymentReportDto.setVoucherNo(oRTGSPaymentEntryDto.getPaymentNo());
        oPaymentReportDto.setOrganisationName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
        oPaymentReportDto.setVoucherAmount(
                CommonMasterUtility.getAmountInIndianCurrency(oRTGSPaymentEntryDto.getTotalPaymentAmount()));

        oPaymentReportDto.setNarration(oRTGSPaymentEntryDto.getNarration());

        oPaymentReportDto.setPaymentDate(oRTGSPaymentEntryDto.getPaymentDate());

        oPaymentReportDto.setPayDate(oRTGSPaymentEntryDto.getTransactionDate());

        String paymentModeDesc = CommonMasterUtility.findLookUpDesc(AccountPrefix.PAY.name(),
                oRTGSPaymentEntryDto.getOrgId(), oRTGSPaymentEntryDto.getPaymentMode());

        if (paymentModeDesc != null) {
            oPaymentReportDto.setPaymentMode(paymentModeDesc);
        }

        List<Object[]> bankAccountList = new ArrayList<>();
        bankAccountList = banksMasterService.getBankAccountPayment(orgid, oRTGSPaymentEntryDto.getBankAcId());
        if ((bankAccountList != null) && !bankAccountList.isEmpty()) {
            for (final Object obj[] : bankAccountList) {
                oPaymentReportDto.setBankName(obj[3].toString());
                oPaymentReportDto.setBankNumber(obj[1].toString());
                oPaymentReportDto.setAccountCode(obj[2].toString() + MainetConstants.HYPHEN + obj[1].toString());
                oPaymentReportDto.setBranchName(obj[5].toString());
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
        	log("AIC Prefix not found ");
        }
        if(isChequeIssanceRequired!=null && isChequeIssanceRequired.getOtherField().equals(MainetConstants.Y_FLAG)) {
  		     lkpStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.READY_FOR_ISSUE.getValue(),
                   AccountPrefix.CLR.toString(), langId, org);
       }else {
        lkpStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.ISSUED.getValue(),
                AccountPrefix.CLR.toString(), langId, org);
       }
        final Long cpdIdStatus = lkpStatus.getLookUpId();
        if (oRTGSPaymentEntryDto.getInstrumentNo() != null) {
            final String chequno = paymentEntryService.getInstrumentChequeNo(cpdIdStatus,
                    oRTGSPaymentEntryDto.getInstrumentNo());
            oPaymentReportDto.setChequeNo(chequno);
        }
        final LookUp lookUpBank = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MENU.B,
                AccountPrefix.PAY.toString(), oRTGSPaymentEntryDto.getLanguageId().intValue(),
                oRTGSPaymentEntryDto.getOrganisation());
        Long lookUpIdBank = lookUpBank.getLookUpId();
        if (oRTGSPaymentEntryDto.getPaymentMode().equals(lookUpIdBank)) {
            oPaymentReportDto.setChequeNo(oRTGSPaymentEntryDto.getUtrNo());
        }
        oPaymentReportDto.setInstrumentDate(oRTGSPaymentEntryDto.getInstrumentDate());

        AccountPaymentMasterEntity entity = paymentEntryService.findByPaymentNumber(oRTGSPaymentEntryDto.getPaymentNo(),
                orgid, oRTGSPaymentEntryDto.getPaymentDate());

        if (entity != null && entity.getPaymentDetailList() != null && !entity.getPaymentDetailList().isEmpty()) {
            List<AccountPaymentDetEntity> detList = entity.getPaymentDetailList();
            List<PaymentDetailsDto> detDTOList = new ArrayList<PaymentDetailsDto>();
            Map<String, BigDecimal> vendorHeadIdMap = new HashMap<String, BigDecimal>();
            for (AccountPaymentDetEntity paymentDetailsDto2 : detList) {
                BigDecimal mapVendorPaymentAmount = vendorHeadIdMap.get(paymentDetailsDto2.getFi04V2());
                if (mapVendorPaymentAmount == null) {
                    vendorHeadIdMap.put(paymentDetailsDto2.getFi04V2(), paymentDetailsDto2.getPaymentAmt());
                } else {
                    mapVendorPaymentAmount = mapVendorPaymentAmount.add(paymentDetailsDto2.getPaymentAmt());
                    vendorHeadIdMap.put(paymentDetailsDto2.getFi04V2(), mapVendorPaymentAmount);
                }
            }
            for (Entry<String, BigDecimal> rtgsPaymentDetailsDTO : vendorHeadIdMap.entrySet()) {
                PaymentDetailsDto dto = new PaymentDetailsDto();
                if (rtgsPaymentDetailsDTO.getValue() != null) {
                    dto.setPaymentAmountDesc(
                            CommonMasterUtility.getAmountInIndianCurrency(rtgsPaymentDetailsDTO.getValue()));
                    totalPaymentAmt = totalPaymentAmt
                            .add(new BigDecimal(rtgsPaymentDetailsDTO.getValue().toString()));
                }
                TbAcVendormaster tbAcVendormaster = null;
                if (rtgsPaymentDetailsDTO.getKey() != null) {
                    tbAcVendormaster = vendorMasterService.findById(Long.valueOf(rtgsPaymentDetailsDTO.getKey()), orgid);
                    dto.setVendorName(tbAcVendormaster.getVmVendorname());
                    if (tbAcVendormaster.getBankId() != null) {
                        TbBankmaster bankMaster = banksMasterService.findById(tbAcVendormaster.getBankId(), orgid);
                        dto.setBankName(bankMaster.getBmBankname());
                        dto.setBranchName(bankMaster.getBmBankbranch());
                        dto.setIfscCode(bankMaster.getBmBankcode());
                    }
                    if (tbAcVendormaster.getBankaccountnumber() != null && !tbAcVendormaster.getBankaccountnumber().isEmpty()) {
                        dto.setBankAccountNumber(tbAcVendormaster.getBankaccountnumber());
                    }
                }
                detDTOList.add(dto);
            }
            oPaymentReportDto.setPaymentDetailsDto(detDTOList);
        }

        final String amountInWords = Utility.convertBigNumberToWord(oRTGSPaymentEntryDto.getTotalPaymentAmount());
        oPaymentReportDto.setAmountInWords(amountInWords);
        oPaymentReportDto.setListOfTbPaymentRepor(paymentDetailsDto);

        populateReportModel(model, oPaymentReportDto, MODE_CREATE);
        return Report_FORM;
    }

    public void populateReportModel(final Model model, final PaymentReportDto paymentReportDto,
            final String formMode) {
        model.addAttribute(PAYMENT_REPORT_DTO, paymentReportDto);
        populateListOfPaymentType(model);
        populateListOfBillType(model);
        populateVendorList(model);
        populateBillDetails(model);
        populatePaymentModeList(model);
        populateListOfBanks(model);
        checkTemplate(model);
    }

    @RequestMapping(params = "RTGSPaymentGridPrintForm", method = RequestMethod.GET)
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
        oPaymentReportDto.setVoucherType(lkp.getDescLangFirst());
        oPaymentReportDto.setVoucherNo(entity.getPaymentNo());
        oPaymentReportDto.setOrganisationName(UserSession.getCurrent().getOrganisation().getONlsOrgname());

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
                oPaymentReportDto.setBankName(obj[3].toString());
                oPaymentReportDto.setBankNumber(obj[1].toString());
                oPaymentReportDto.setAccountCode(obj[2].toString() + MainetConstants.HYPHEN + obj[1].toString());
                oPaymentReportDto.setBranchName(obj[5].toString());
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

        if (entity != null && entity.getPaymentDetailList() != null && !entity.getPaymentDetailList().isEmpty()) {
            List<AccountPaymentDetEntity> detList = entity.getPaymentDetailList();
            List<PaymentDetailsDto> detDTOList = new ArrayList<PaymentDetailsDto>();
            Map<String, BigDecimal> vendorHeadIdMap = new HashMap<String, BigDecimal>();
            for (AccountPaymentDetEntity paymentDetailsDto2 : detList) {
                BigDecimal mapVendorPaymentAmount = vendorHeadIdMap.get(paymentDetailsDto2.getFi04V2());
                if (mapVendorPaymentAmount == null) {
                    vendorHeadIdMap.put(paymentDetailsDto2.getFi04V2(), paymentDetailsDto2.getPaymentAmt());
                } else {
                    mapVendorPaymentAmount = mapVendorPaymentAmount.add(paymentDetailsDto2.getPaymentAmt());
                    vendorHeadIdMap.put(paymentDetailsDto2.getFi04V2(), mapVendorPaymentAmount);
                }
            }
            for (Entry<String, BigDecimal> rtgsPaymentDetailsDTO : vendorHeadIdMap.entrySet()) {

                PaymentDetailsDto dto = new PaymentDetailsDto();

                if (rtgsPaymentDetailsDTO.getValue() != null) {
                    dto.setPaymentAmountDesc(
                            CommonMasterUtility.getAmountInIndianCurrency(rtgsPaymentDetailsDTO.getValue()));
                    totalPaymentAmt = totalPaymentAmt
                            .add(new BigDecimal(rtgsPaymentDetailsDTO.getValue().toString()));
                }
                TbAcVendormaster tbAcVendormaster = null;
                if (rtgsPaymentDetailsDTO.getKey() != null) {
                    tbAcVendormaster = vendorMasterService.findById(Long.valueOf(rtgsPaymentDetailsDTO.getKey()), orgid);
                    dto.setVendorName(tbAcVendormaster.getVmVendorname());
                    if (tbAcVendormaster.getBankId() != null) {
                        TbBankmaster bankMaster = banksMasterService.findById(tbAcVendormaster.getBankId(), orgid);
                        dto.setBankName(bankMaster.getBmBankname());
                        dto.setBranchName(bankMaster.getBmBankbranch());
                        dto.setIfscCode(bankMaster.getBmBankcode());
                    }
                    if (tbAcVendormaster.getBankaccountnumber() != null && !tbAcVendormaster.getBankaccountnumber().isEmpty()) {
                        dto.setBankAccountNumber(tbAcVendormaster.getBankaccountnumber());
                    }
                }
                detDTOList.add(dto);
            }
            oPaymentReportDto.setPaymentDetailsDto(detDTOList);
        }

        oPaymentReportDto.setVoucherAmount(CommonMasterUtility.getAmountInIndianCurrency(totalPaymentAmt));
        final String amountInWords = Utility.convertBigNumberToWord(entity.getPaymentAmount());
        oPaymentReportDto.setAmountInWords(amountInWords);
        oPaymentReportDto.setListOfTbPaymentRepor(paymentDetailsDto);
        populateReportModel(model, oPaymentReportDto, MODE_CREATE);
        return Report_FORM;
    }

    @RequestMapping(params = "getRTGSBillEntryViewData")
    public String displayDepositeSlipData(final Model model, final HttpServletRequest request,
            @RequestParam(value = "billId") final Long billId) {

        log("Action getRTGSBillEntryViewData");
        String viewmode = "VIEW";
        AccountBillEntryMasterBean accountBillEntryBean = new AccountBillEntryMasterBean();
        if (viewmode.equals(MainetConstants.VIEW)) {
            model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.SUCCESS);
        } else if (viewmode.equals(MainetConstants.AccountBillEntry.PAYMENT_REVIEW)) {
            model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.SUCCESS);
            accountBillEntryBean.setPaymentEntryFlag(MainetConstants.AccountBillEntry.PAYMENT);
        } else {
            model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
        }
        populateBillModel(model, accountBillEntryBean, MODE_VIEW);
        if (!request.getRequestURI().contains(MainetConstants.AccountBillEntry.ENTRY)) {
            accountBillEntryBean.setAuthorizationMode(MainetConstants.AccountBillEntry.AUTH);
        }
        accountBillEntryBean = billEntryService.populateBillEntryViewData(accountBillEntryBean, billId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        Map<Long, String> dedutionExpHeadMap = new LinkedHashMap<>();
        List<AccountBillEntryExpenditureDetBean> expDetList = accountBillEntryBean.getExpenditureDetailList();
        for (AccountBillEntryExpenditureDetBean accountBillEntryExpenditureDetBean : expDetList) {
            Long sacheadid = accountBillEntryExpenditureDetBean.getSacHeadId();
            String accountHeadCode = "";
            if (sacheadid != null) {
                accountHeadCode = secondaryheadMasterService.findByAccountHead(sacheadid);
                dedutionExpHeadMap.put(sacheadid, accountHeadCode);
            }
        }
        model.addAttribute("dedutionExpHeadMap", dedutionExpHeadMap);
        accountBillEntryBean.setRtgsPaymentIdFlag(MainetConstants.Y_FLAG);
        model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.VIEW_DATA, accountBillEntryBean);
        RTGSPaymentEntryDTO rtgsPaymentEntryDTO = new RTGSPaymentEntryDTO();
        BeanUtils.copyProperties(accountBillEntryBean, rtgsPaymentEntryDTO);
        model.addAttribute(RTGS_PAYMENT_ENTRY_DTO, rtgsPaymentEntryDTO);
        model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.MODE_FLAG, MainetConstants.RnLCommon.MODE_VIEW);
        return JSP_RTGS_BILL_VIEW_FORM;
    }

    public void populateBillModel(final Model model, final AccountBillEntryMasterBean accountBillEntryBean,
            final String formMode) {
        if (accountBillEntryBean.getAdvanceFlag() != null && !accountBillEntryBean.getAdvanceFlag().isEmpty()) {
            if (accountBillEntryBean.getAdvanceFlag().equals(MainetConstants.Y_FLAG)) {
            }
        }
        if (formMode == MODE_VIEW) {
            isMakerChecker(model, accountBillEntryBean);
        }
    }

    private void isMakerChecker(final Model model, final AccountBillEntryMasterBean accountBillEntryBean) {

        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final String isMakerChecker = billEntryService.isMakerChecker(organisation);
        accountBillEntryBean.setIsMakerChecker(isMakerChecker);
    }

    /*
     * This method gets the expenditure budget data based on the account heads and the department
     */
    @RequestMapping(params = "viewBillExpDetails")
    public String viewExpenditureDetails(@RequestParam("sacHeadId") final Long sacHeadId,
            @RequestParam("entryDate") final String entryDate,
            @RequestParam("bchChargesAmt") final BigDecimal sanctionedAmt, @RequestParam("count") final Long count,
            final HttpServletRequest request, final Model model) {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final FinancialYear financialYear = financialyearService.getFinanciaYearByDate(Utility.stringToDate(entryDate));
        final Long finYearId = financialYear.getFaYear();
        final List<AccountBudgetProjectedExpenditureEntity> projectedExpList = accountBudgetProjectedExpenditureService
                .getExpenditureDetailsForBillEntryFormView(orgId, finYearId, sacHeadId);

        RTGSPaymentEntryDTO rtgsPaymentEntryDTO = new RTGSPaymentEntryDTO();
        AccountBillEntryExpenditureDetBean billExpDetBean = null;
        final List<AccountBillEntryExpenditureDetBean> billExpBudgetDetBeanList = new ArrayList<>();
        BigDecimal balanceProvisionAmt = null;
        BigDecimal newBalanceAmount = null;
        BigDecimal expenditureAmt = null;
        if ((projectedExpList != null) && !projectedExpList.isEmpty()) {
            for (final AccountBudgetProjectedExpenditureEntity prExpDet : projectedExpList) {
                billExpDetBean = new AccountBillEntryExpenditureDetBean();
                billExpDetBean.setProjectedExpenditureId(prExpDet.getPrExpenditureid());
                if (prExpDet.getRevisedEstamt() == null) {
                    billExpDetBean.setOriginalEstimate(prExpDet.getOrginalEstamt());
                } else {
                    billExpDetBean.setOriginalEstimate(new BigDecimal(prExpDet.getRevisedEstamt()));
                }
                if (prExpDet.getExpenditureAmt() == null) {
                    expenditureAmt = BigDecimal.ZERO;
                    prExpDet.setExpenditureAmt(BigDecimal.ZERO);
                    billExpDetBean.setBalanceAmount(expenditureAmt);
                    if (prExpDet.getRevisedEstamt() == null) {
                        balanceProvisionAmt = prExpDet.getOrginalEstamt().subtract(expenditureAmt.add(sanctionedAmt));
                    } else {
                        balanceProvisionAmt = new BigDecimal(prExpDet.getRevisedEstamt())
                                .subtract(expenditureAmt.add(sanctionedAmt));
                    }
                    newBalanceAmount = expenditureAmt.add(sanctionedAmt);
                    if (prExpDet.getRevisedEstamt() == null) {
                        prExpDet.getExpenditureAmt().add(sanctionedAmt).compareTo(prExpDet.getOrginalEstamt());
                    } else {
                        prExpDet.getExpenditureAmt().add(sanctionedAmt)
                                .compareTo(new BigDecimal(prExpDet.getRevisedEstamt()));
                    }
                } else {
                    billExpDetBean.setBalanceAmount(prExpDet.getExpenditureAmt());
                    if (prExpDet.getRevisedEstamt() == null) {
                        balanceProvisionAmt = prExpDet.getOrginalEstamt()
                                .subtract((prExpDet.getExpenditureAmt().add(sanctionedAmt)));
                    } else {
                        balanceProvisionAmt = new BigDecimal(prExpDet.getRevisedEstamt())
                                .subtract((prExpDet.getExpenditureAmt().add(sanctionedAmt)));
                    }
                    newBalanceAmount = prExpDet.getExpenditureAmt().add(sanctionedAmt);
                    if (prExpDet.getRevisedEstamt() == null) {
                        prExpDet.getExpenditureAmt().add(sanctionedAmt).compareTo(prExpDet.getOrginalEstamt());
                    } else {
                        prExpDet.getExpenditureAmt().add(sanctionedAmt)
                                .compareTo(new BigDecimal(prExpDet.getRevisedEstamt()));
                    }
                }

                billExpDetBean.setNewBalanceAmount(newBalanceAmount);
                billExpDetBean.setActualAmount(sanctionedAmt.setScale(2, RoundingMode.CEILING));
                billExpDetBean.setRowCount(count);
                billExpDetBean.setBalProvisionAmount(balanceProvisionAmt);
                billExpBudgetDetBeanList.add(billExpDetBean);
                rtgsPaymentEntryDTO.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);

            }
        } else {
            model.addAttribute(MainetConstants.AccountBillEntry.ERROR_MSG,
                    ApplicationSession.getInstance().getMessage("account.bill.entry.controller.budget.data"));
        }
        rtgsPaymentEntryDTO.setExpenditureDetailList(billExpBudgetDetBeanList);
        model.addAttribute(BUDGET_DETAILS, billExpBudgetDetBeanList);
        model.addAttribute(RTGS_PAYMENT_ENTRY_DTO, rtgsPaymentEntryDTO);

        return ACCOUNT_BILL_EXPENDITURE_DETAILS;
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
