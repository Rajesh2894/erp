package com.abm.mainet.account.ui.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.domain.AccountPaymentDetEntity;
import com.abm.mainet.account.domain.AccountPaymentMasterEntity;
import com.abm.mainet.account.dto.AccountBillEntryExpenditureDetBean;
import com.abm.mainet.account.dto.PaymentEntryDto;
import com.abm.mainet.account.dto.PaymentReportDto;
import com.abm.mainet.account.dto.VoucherTemplateDTO;
import com.abm.mainet.account.service.AccountBillEntryService;
import com.abm.mainet.account.service.AccountContraVoucherEntryService;
import com.abm.mainet.account.service.BudgetCodeService;
import com.abm.mainet.account.service.PaymentEntrySrevice;
import com.abm.mainet.account.service.VoucherTemplateService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.integration.acccount.dto.PaymentDetailsDto;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountFundMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.TbBankmasterService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;

/**
 * @author tejas.kotekar
 *
 */
@Controller
@RequestMapping("/DirectPaymentEntry.html")
public class DirectPaymentEntryController extends AbstractController {

    private static final String JSP_LIST = "DirectPaymentEntry/list";
    private static final String JSP_FORM = "DirectPaymentEntry/form";
    private static final String JSP_PAY_FORM = "DirectPaymentEntry/payForm";
    private static final String JSP_VIEW_PAY_FORM = "DirectPaymentEntry/viewPayForm";
    private final static String PAYMENT_ENTRY_DTO = "paymentEntryDto";
    private static final String BILL_TYPE_LIST = "billTypeList";
    private static final String VENDOR_LIST = "vendorList";
    private static final String BANK_AC_MAP = "bankAccountMap";
    private static final String PROJECTED_EXPENDITURE_LIST = "projectedExpenditureList";
    private static final String EXP_BUDGETCODE_MAP = "expBudgetCodeMap";
    private static final String PAYMENT_MODE = "paymentMode";
    private static final String TEMPLATE_EXIST_FLAG = "templateExistFlag";
    private static final Logger LOGGER = Logger.getLogger(DirectPaymentEntryController.class);
    private final static String PAYMENT_REPORT_DTO = "oPaymentReportDto";
    private final String Report_FORM = "directPaymentEntry/Report";
    private final static String ACCOUNT_BILL_TYPE_FOR_DIRECT_PAYMENT = "MI,AD";
    private final static String BILL_TYPE_CODE = "billTypeCode";
    @Resource
    private TbAcVendormasterService vendorMasterService;
    @Resource
    private TbBankmasterService banksMasterService;
    @Resource
    private AccountBillEntryService billEntryService;
    @Resource
    private BudgetCodeService budgetCodeService;
    @Resource
    private PaymentEntrySrevice paymentEntryService;
    @Resource
    private AccountContraVoucherEntryService accountContraVoucherEntryService;
    @Resource
    private VoucherTemplateService voucherTemplateService;
    @Resource
    private SecondaryheadMasterService secondaryheadMasterService;
    @Resource
    private ILocationMasService locMasService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private SecondaryheadMasterService tbAcSecondaryheadMasterService;
    @Resource
    private AccountFundMasterService accountFundMasterService;
    @Autowired
    private IEmployeeService employeeService;
    @Resource
  	private AccountFieldMasterService tbAcFieldMasterService;
    

    List<PaymentEntryDto> masterDtoList = null;

    public DirectPaymentEntryController() {
        super(PaymentEntryController.class, PAYMENT_ENTRY_DTO);
        LOGGER.info("Direct payment entry controller created");
    }

    public void populateModel(final Model model, final PaymentEntryDto paymentEntryDto, final String formMode) {
        model.addAttribute(PAYMENT_ENTRY_DTO, paymentEntryDto);
        model.addAttribute(BILL_TYPE_CODE, ACCOUNT_BILL_TYPE_FOR_DIRECT_PAYMENT);
        if (formMode == MODE_CREATE) {
            model.addAttribute(MODE, formMode);
            populateListOfBillType(model, paymentEntryDto);
            populateVendorList(model);
            getExpenditureDetailsByFinyearId(paymentEntryDto, model);
            populatePaymentModeList(model);
            populateListOfBanks(model);
            checkTemplate(model);
            checkingBudgetDefParameter(model);
        }
        if (formMode == MODE_VIEW) {
            populateListOfBanks(model);
            model.addAttribute(MODE, formMode);
            getExpenditureDetailsByFinyearIdForView(paymentEntryDto, model);
        }

    }
    private void populateField(final Model model) {
		model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
				tbAcFieldMasterService.getFieldMasterLastLevels(UserSession.getCurrent().getOrganisation().getOrgid()));
	}

    @RequestMapping()
    public String populateGridList(final Model model, final HttpServletRequest httpServletRequest) {

        masterDtoList = new ArrayList<>();
        final PaymentEntryDto paymentEntryDto = new PaymentEntryDto();
        populateModel(model, paymentEntryDto, MODE_CREATE);
        masterDtoList.clear();
        return JSP_LIST;
    }

    @RequestMapping(params = "getGridData")
    public @ResponseBody JQGridResponse<PaymentEntryDto> gridData(final HttpServletRequest request, final Model model) {

        final int page = Integer.parseInt(request.getParameter(AccountConstants.PAGE.getValue()));
        final JQGridResponse<PaymentEntryDto> response = new JQGridResponse<>();
        response.setRows(masterDtoList);
        response.setTotal(masterDtoList.size());
        response.setRecords(masterDtoList.size());
        response.setPage(page);
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, masterDtoList);
        return response;
    }

    @RequestMapping(params = "searchDirectPayData")
    public @ResponseBody List<PaymentEntryDto> searchDirectPayData(
            @RequestParam("paymentEntryDate") final String paymentEntryDate,
            @RequestParam("paymentAmount") final BigDecimal paymentAmount,
            @RequestParam("vendorId") final Long vendorId, @RequestParam("budgetCodeId") final Long budgetCodeId,
            @RequestParam("paymentNo") final String paymentNo, @RequestParam("baAccountid") final Long baAccountid,
            final Model model) {
        masterDtoList = new ArrayList<>();
        masterDtoList.clear();
        PaymentEntryDto masterDto = null;
        List<AccountPaymentMasterEntity> directPaymentEntryList = null;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long paymentTypeFlag = 1L;
        if ((paymentEntryDate != MainetConstants.BLANK) || (paymentAmount != null) || (vendorId != null) || (budgetCodeId != null)
                || (paymentNo != MainetConstants.BLANK) || (baAccountid != null)) {
            directPaymentEntryList = paymentEntryService.getPaymentDetails(orgId, paymentEntryDate, paymentAmount, vendorId,
                    budgetCodeId, paymentNo, baAccountid, paymentTypeFlag);
        }
        if ((directPaymentEntryList != null) && !directPaymentEntryList.isEmpty()) {
            for (final AccountPaymentMasterEntity paymentMaster : directPaymentEntryList) {
                masterDto = new PaymentEntryDto();
                masterDto.setId(paymentMaster.getPaymentId());
                masterDto.setPaymentNo(paymentMaster.getPaymentNo());
                masterDto.setPaymentEntryDate(UtilityService.convertDateToDDMMYYYY(paymentMaster.getPaymentDate()));
                if (paymentMaster.getVmVendorId() != null && paymentMaster.getVmVendorId().getVmVendorid() != null) {
                    final String vendorDescription = vendorMasterService.getVendorNameById(
                            paymentMaster.getVmVendorId().getVmVendorid(),
                            paymentMaster.getOrgId());
                    masterDto.setVendorDesc(vendorDescription);
                } else {
                    if (paymentMaster.getNarration() != null && !paymentMaster.getNarration().isEmpty()) {
                        String[] arr = paymentMaster.getNarration().split(Pattern.quote("$"));
                        masterDto.setVendorDesc(arr[0]);
                    }
                }
                masterDto.setBillAmountStr(CommonMasterUtility.getAmountInIndianCurrency(paymentMaster.getPaymentAmount()));
                masterDtoList.add(masterDto);
            }
        }
        return masterDtoList;
    }

    @RequestMapping(params = "formForView", method = RequestMethod.POST)
    public String formForView(final Model model, final HttpServletRequest request, @RequestParam("id") final Long id) {
        final int langId = UserSession.getCurrent().getLanguageId();
        log("Action 'formForView'");
        final PaymentEntryDto paymentEntryDto = paymentEntryService.findDirectPaymentEntryDataById(id,
                UserSession.getCurrent().getOrganisation().getOrgid(), langId);
        populateField(model);
        model.addAttribute(MainetConstants.DirectPaymentEntry.PAY_DETAIL_LIST, paymentEntryDto.getPaymentDetailsDto());
        populateModel(model, paymentEntryDto, MODE_VIEW);
        return JSP_VIEW_PAY_FORM;
    }

    @RequestMapping(params = "formForPrint", method = RequestMethod.POST)
    public String formForPrint(final Model model, final HttpServletRequest request, @RequestParam("id") final Long id) {
        log("Action 'formForPrint'");
        AccountPaymentMasterEntity entity = paymentEntryService.findById(id,
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
        oPaymentReportDto.setVoucherNo(entity.getPaymentNo());
        oPaymentReportDto.setOrganisationName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
        oPaymentReportDto
                .setVoucherAmount(CommonMasterUtility.getAmountInIndianCurrency(entity.getPaymentAmount()));

        if (entity.getVmVendorId().getVmVendorid() != null) {
            oPaymentReportDto.setVendorCodeDescription(entity.getVmVendorId().getVmVendorname());
        }
        oPaymentReportDto.setNarration(
                oPaymentReportDto.getVendorCodeDescription() + MainetConstants.HYPHEN
                        + entity.getNarration());

        oPaymentReportDto.setPaymentDate(entity.getPaymentDate());

        oPaymentReportDto.setPayDate(Utility.dateToString(entity.getPaymentDate()));

        if (entity.getPaymentModeId() != null
                && (entity.getPaymentModeId().getCpdDesc() != null && !entity.getPaymentModeId().getCpdDesc().isEmpty())) {
            oPaymentReportDto.setPaymentMode(entity.getPaymentModeId().getCpdDesc());
        }

        List<Object[]> bankAccountList = new ArrayList<>();
        if (entity.getBaBankAccountId() != null && entity.getBaBankAccountId().getBaAccountId() != null) {
            bankAccountList = banksMasterService.getBankAccountPayment(orgid, entity.getBaBankAccountId().getBaAccountId());
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

        List<PaymentDetailsDto> detList = new ArrayList<PaymentDetailsDto>();

        List<AccountPaymentDetEntity> detDTOList = entity.getPaymentDetailList();

        for (final AccountPaymentDetEntity det : detDTOList) {

            final PaymentDetailsDto detDTo = new PaymentDetailsDto();

            String accountCodeDesc = budgetCodeService.findAccountHeadCodeBySacHeadId(Long.valueOf(det.getBudgetCodeId()),
                    entity.getOrgId());

            detDTo.setAccountCode(accountCodeDesc);

            AccountBudgetCodeEntity accountHeadEntity = budgetCodeService
                    .findBudgetHeadIdBySacHeadId(Long.valueOf(det.getBudgetCodeId().toString()), entity.getOrgId());
            if (accountHeadEntity != null) {
                if (accountHeadEntity.getTbAcFunctionMaster() != null
                        && (accountHeadEntity.getTbAcFunctionMaster().getFunctionDesc() != null
                                && !accountHeadEntity.getTbAcFunctionMaster().getFunctionDesc().isEmpty())) {
                    detDTo.setFunctionDesc(accountHeadEntity.getTbAcFunctionMaster().getFunctionDesc());
                }

                String departmentDesc = departmentService.getDepartmentDescByDeptCode(AccountConstants.AC.toString());
                detDTo.setFunctionaryDesc(departmentDesc);
            }
            detDTo.setPaymentAmountDesc(det.getPaymentAmt().toString());

            detList.add(detDTo);
        }
        oPaymentReportDto.setPaymentDetailsDto(detList);

        final String amountInWords = Utility
                .convertBigNumberToWord(entity.getPaymentAmount());
        oPaymentReportDto.setAmountInWords(amountInWords);
        oPaymentReportDto.setListOfTbPaymentRepor(paymentDetailsDto);

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
        populateReportModel(model, oPaymentReportDto, MODE_CREATE);
        return Report_FORM;
    }

    public void populateListOfBillType(final Model model, PaymentEntryDto paymentEntryDto) {
        final List<LookUp> billTypeLookupList = CommonMasterUtility.getLookUps(AccountPrefix.ABT.toString(),
                UserSession.getCurrent().getOrganisation());
        for (final LookUp lookUp : billTypeLookupList) {
            if (lookUp.getDefaultVal() != null && !lookUp.getDefaultVal().isEmpty()) {
                if (lookUp.getDefaultVal().equals("Y")) {
                    paymentEntryDto.setBillTypeId(lookUp.getLookUpId());
                }
            }
        }
        List<LookUp> billTypeList = new ArrayList<LookUp>();
        for (final LookUp payList : billTypeLookupList) {
            if (payList.getLookUpCode().equals("DE") ||
                    payList.getLookUpCode().equals("AD")
                    || payList.getLookUpCode().equals("MI")) {
                LookUp lookup = new LookUp();
                lookup.setLookUpId(payList.getLookUpId());
                lookup.setLookUpCode(payList.getLookUpCode());
                lookup.setLookUpDesc(payList.getLookUpDesc());
                lookup.setLookUpType(payList.getLookUpType());
                lookup.setDescLangFirst(payList.getDescLangFirst());
                lookup.setDescLangSecond(payList.getDescLangSecond());
                billTypeList.add(lookup);
            }
        }
        model.addAttribute(BILL_TYPE_LIST, billTypeList);
    }

    // Populate the list of bill types e.g. Miscellaneous,Liability,Deposit,etc - View Time
    public void populateListOfBillTypeView(final Model model) {
        final List<LookUp> billTypeLookupList = CommonMasterUtility.getLookUps(AccountPrefix.ABT.toString(),
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(BILL_TYPE_LIST, billTypeLookupList);
    }

    // Populates the list of vendors
    public void populateVendorList(final Model model) {

        final Organisation org = UserSession.getCurrent().getOrganisation();
        final Integer languageId = UserSession.getCurrent().getLanguageId();
        final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.AC.getValue(),
                PrefixConstants.VSS, languageId, org);
        final Long vendorStatus = lookUpVendorStatus.getLookUpId();
        final List<TbAcVendormaster> vendorList = vendorMasterService.getActiveVendors(org.getOrgid(), vendorStatus);
        List<TbAcVendormaster> newVendorList = new ArrayList<>();
        newVendorList = vendorList.stream()
                .filter(look -> look.getRtgsvendorflag() != null && look.getRtgsvendorflag().equals(MainetConstants.Y_FLAG))
                .collect(Collectors.toList());
        model.addAttribute(VENDOR_LIST, newVendorList);

    }

    // Populates the list of banks
    public void populateListOfBanks(final Model model) {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<Object[]> bankAccountList = new ArrayList<>();
        final Map<Long, String> bankAccountMap = new HashMap<>();
        final Long statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.U.toString(),
                PrefixConstants.BAS,
                orgId);
        bankAccountList = banksMasterService.getActiveBankAccountList(orgId, statusId);
        if ((bankAccountList != null) && !bankAccountList.isEmpty()) {
            for (final Object[] bankAc : bankAccountList) {
                bankAccountMap.put((Long) bankAc[0],
                        bankAc[3] + MainetConstants.SEPARATOR + bankAc[1] + MainetConstants.SEPARATOR + bankAc[2]);
            }
        }
        model.addAttribute(BANK_AC_MAP, bankAccountMap);
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

    private void getExpenditureDetailsByFinyearId(final PaymentEntryDto paymentEntryDto, final Model model) {

        List<AccountBillEntryExpenditureDetBean> billExpDetBeanList = null;
        AccountBillEntryExpenditureDetBean billExpDetBean = null;

        billExpDetBeanList = new ArrayList<>();
        billExpDetBean = new AccountBillEntryExpenditureDetBean();
        billExpDetBeanList.add(billExpDetBean);

        model.addAttribute(PROJECTED_EXPENDITURE_LIST, billExpDetBeanList);
        model.addAttribute(EXP_BUDGETCODE_MAP,
                secondaryheadMasterService.findExpenditureHeadMap(UserSession.getCurrent().getOrganisation().getOrgid()));
    }

    private void getExpenditureDetailsByFinyearIdForView(final PaymentEntryDto paymentEntryDto, final Model model) {

        List<AccountBillEntryExpenditureDetBean> billExpDetBeanList = null;
        AccountBillEntryExpenditureDetBean billExpDetBean = null;

        billExpDetBeanList = new ArrayList<>();
        billExpDetBean = new AccountBillEntryExpenditureDetBean();
        billExpDetBeanList.add(billExpDetBean);

        model.addAttribute(PROJECTED_EXPENDITURE_LIST, billExpDetBeanList);
        model.addAttribute(EXP_BUDGETCODE_MAP,
                secondaryheadMasterService.findExpenditureHeadMapForView(UserSession.getCurrent().getOrganisation().getOrgid()));
    }

    @RequestMapping(params = "formForCreate", method = RequestMethod.POST)
    public String formForCreate(final Model model) {
        final PaymentEntryDto paymentEntryDto = new PaymentEntryDto();
        populateField(model);
        populateModel(model, paymentEntryDto, MODE_CREATE);
        return JSP_FORM;
    }

    @RequestMapping(params = "proceedPayment", method = RequestMethod.POST)
    public String formForPayment(final PaymentEntryDto paymentEntryDto, final Model model, final HttpServletRequest request) {

        log("Action 'proceed payment form'");
        final List<PaymentDetailsDto> paymentDetails = paymentEntryDto.getPaymentDetailsDto();
        request.getSession().setAttribute(MainetConstants.DirectPaymentEntry.PAY_DETAIL, paymentDetails);
        populateModel(model, paymentEntryDto, MODE_CREATE);
        return JSP_PAY_FORM;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "create", method = RequestMethod.POST)
    public @ResponseBody String createBillEntry(@Valid final PaymentEntryDto paymentEntrydto, final Model model,
            final HttpServletRequest request)
            throws ParseException {
        log("Action 'create payment form'");
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        paymentEntrydto.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);
        paymentEntrydto.setSuccessfulFlag(MainetConstants.MASTER.Y);
        paymentEntrydto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
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
       // paymentEntrydto.setFieldId(fieldId);
        paymentEntrydto.setCreatedDate(new Date());
        paymentEntrydto.setLanguageId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
        paymentEntrydto.setLgIpMacAddress(Utility.getClientIpAddress(request));
        paymentEntrydto.setOrganisation(organisation);
        paymentEntrydto.setPaymentDetailsDto(paymentEntrydto.getPaymentDetailsDto());
        AccountPaymentMasterEntity directPaymentEntity = paymentEntryService.createDirectPaymentEntry(paymentEntrydto);
        request.getSession().setAttribute(MainetConstants.DirectPaymentEntry.PAYMENT_LIST, paymentEntrydto);
        return directPaymentEntity.getPaymentNo();
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

    @RequestMapping(params = "viewDirectPaymentDetail")
    public String viewBillInvoiceDetailsForReversal(@RequestParam("gridId") final long gridId, final HttpServletRequest request,
            final Model model) {

        log("Action 'formForView'");
        int langId = UserSession.getCurrent().getLanguageId();
        final PaymentEntryDto paymentEntryDto = paymentEntryService.findDirectPaymentEntryDataById(gridId,
                UserSession.getCurrent().getOrganisation().getOrgid(), langId);
        model.addAttribute(MainetConstants.DirectPaymentEntry.PAY_DETAIL_LIST, paymentEntryDto.getPaymentDetailsDto());
        populateModel(model, paymentEntryDto, MODE_VIEW);
        model.addAttribute(MainetConstants.PRIMARYCODEMASTER.MODE_FLAG, MainetConstants.MODE_VIEW);
        request.getSession().setAttribute(MainetConstants.REQUIRED_PG_PARAM.MODE, MainetConstants.MODE_VIEW);
        return JSP_VIEW_PAY_FORM;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "paymentReportForm", method = RequestMethod.POST)
    public String paymentReportForm(@Valid final PaymentEntryDto paymentEntrydto, final Model model,
            final HttpServletRequest request)
            throws ParseException {
        final PaymentEntryDto opaymentEntryDto = (PaymentEntryDto) request.getSession()
                .getAttribute(MainetConstants.DirectPaymentEntry.PAYMENT_LIST);
        final PaymentReportDto oPaymentReportDto = new PaymentReportDto();
        final List<PaymentReportDto> paymentDetailsDto = new ArrayList<>();
        new AccountPaymentMasterEntity();
        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();

        final Organisation org = UserSession.getCurrent().getOrganisation();
        final LookUp lkp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.DirectPaymentEntry.PV,
                PrefixConstants.ContraVoucherEntry.VOT, org);
        String voucheNo = voucherTemplateService.getVoucherNoBy(lkp.getLookUpId(), opaymentEntryDto.getPaymentNo(),
                opaymentEntryDto.getPaymentDate(), UserSession.getCurrent().getOrganisation().getOrgid());
        oPaymentReportDto.setVoucherType(lkp.getDescLangFirst());
        oPaymentReportDto.setVoucherNo(opaymentEntryDto.getPaymentNo());

        oPaymentReportDto.setPaymentEntryDate(opaymentEntryDto.getPaymentEntryDate());

        oPaymentReportDto.setOrganisationName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
        oPaymentReportDto
                .setVoucherAmount(CommonMasterUtility.getAmountInIndianCurrency(opaymentEntryDto.getTotalPaymentAmount()));

        oPaymentReportDto.setNarration(opaymentEntryDto.getPayeeName() + "-" + opaymentEntryDto.getNarration());
        // oPaymentReportDto.setNarration(opaymentEntryDto.getNarration());
        oPaymentReportDto.setPaymentDate(opaymentEntryDto.getPaymentDate());

        if (opaymentEntryDto.getPayeeName() != null && !opaymentEntryDto.getPayeeName().isEmpty()) {
            oPaymentReportDto.setVendorCodeDescription(opaymentEntryDto.getPayeeName());
        }
        oPaymentReportDto.setPayDate(opaymentEntryDto.getTransactionDate());
        List<Object[]> bankAccountList = new ArrayList<>();
        bankAccountList = banksMasterService.getBankAccountPayment(orgid, opaymentEntryDto.getBankAcId());
        if ((bankAccountList != null) && !bankAccountList.isEmpty()) {
            for (final Object obj[] : bankAccountList) {
                oPaymentReportDto.setBankName(obj[3].toString() + " - " + obj[1].toString() + " - " + obj[2].toString());
                oPaymentReportDto.setBankNumber(voucheNo);
                if (obj[4] != null) {
                    oPaymentReportDto.setNameOfTheFund(accountFundMasterService.getFundCodeDesc(Long.valueOf(obj[4].toString())));
                }
                paymentDetailsDto.add(oPaymentReportDto);
            }

        }
        final int langId = UserSession.getCurrent().getLanguageId();
        final LookUp lkpStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.ISSUED.getValue(),
                AccountPrefix.CLR.toString(), langId, org);
        final Long cpdIdStatus = lkpStatus.getLookUpId();
        if (opaymentEntryDto.getInstrumentNo() != null) {
            final String chequno = paymentEntryService.getCheque(opaymentEntryDto.getInstrumentNo());
            // final String chequno = paymentEntryService.getCheque(cpdIdStatus, opaymentEntryDto.getPaymentId());
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

        List<PaymentDetailsDto> detList = new ArrayList<PaymentDetailsDto>();

        final List<PaymentDetailsDto> receiptFeeDetail = opaymentEntryDto.getPaymentDetailsDto();

        for (final PaymentDetailsDto det : receiptFeeDetail) {
            final PaymentDetailsDto detDTo = new PaymentDetailsDto();

            String accountCodeDesc = budgetCodeService.findAccountHeadCodeBySacHeadId(Long.valueOf(det.getId()),
                    opaymentEntryDto.getOrgId());
            detDTo.setAccountCode(accountCodeDesc);
            AccountBudgetCodeEntity accountHeadEntity = budgetCodeService
                    .findBudgetHeadIdBySacHeadId(Long.valueOf(det.getId().toString()), opaymentEntryDto.getOrgId());
            if (accountHeadEntity != null) {
                if (accountHeadEntity.getTbAcFunctionMaster() != null
                        && (accountHeadEntity.getTbAcFunctionMaster().getFunctionDesc() != null
                                && !accountHeadEntity.getTbAcFunctionMaster().getFunctionDesc().isEmpty())) {
                    detDTo.setFunctionDesc(accountHeadEntity.getTbAcFunctionMaster().getFunctionDesc());
                }

                String departmentDesc = departmentService.getDepartmentDescByDeptCode(AccountConstants.AC.toString());
                detDTo.setFunctionaryDesc(departmentDesc);
            }

            detDTo.setPaymentAmountDesc(CommonMasterUtility.getAmountInIndianCurrency(det.getPaymentAmount()));

            detList.add(detDTo);
        }
        oPaymentReportDto.setPaymentDetailsDto(detList);

        String paymentModeDesc = CommonMasterUtility.findLookUpDesc(AccountPrefix.PAY.name(), opaymentEntryDto.getOrgId(),
                opaymentEntryDto.getPaymentMode());
        oPaymentReportDto.setPaymentMode(paymentModeDesc);

        final String amountInWords = Utility
                .convertBigNumberToWord(opaymentEntryDto.getTotalPaymentAmount());
        oPaymentReportDto.setAmountInWords(amountInWords);

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

        oPaymentReportDto.setListOfTbPaymentRepor(paymentDetailsDto);
        populateReportModel(model, oPaymentReportDto, MODE_CREATE);
        return Report_FORM;
    }

    public void populateReportModel(final Model model, final PaymentReportDto oPaymentReportDto, final String formMode) {
        model.addAttribute(PAYMENT_REPORT_DTO, oPaymentReportDto);
        populateListOfBillTypeView(model);
        populateVendorList(model);
        populatePaymentModeList(model);
        populateListOfBanks(model);
        checkTemplate(model);
    }

    @RequestMapping(params = "getPaymentAccountHeadData", method = RequestMethod.POST)
    public String getPaymentAccountHeadAllData(final PaymentEntryDto paymentEntryDto, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest request,
            final BindingResult bindingResult) {
        log("DirectPaymentEntry-'getPaymentAccountHeadData' : 'get PaymentAccountHead Data'");
        String result = MainetConstants.CommonConstant.BLANK;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        String recCategoryType = CommonMasterUtility.findLookUpCode(PrefixConstants.AccountPrefix.ABT.toString(),
                UserSession.getCurrent().getOrganisation().getOrgid(), paymentEntryDto.getBillTypeId());
        Long updatedBillTypeId = paymentEntryDto.getBillTypeId();
        Map<Long, String> exBudgetAcHeadMap = null;
        populateField(model);
        if (recCategoryType.equals("AD")) {
            final LookUp lookUpStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
                    PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(),
                    UserSession.getCurrent().getOrganisation());
            final Long activeStatusId = lookUpStatus.getLookUpId();
            recCategoryType = "A";
            exBudgetAcHeadMap = tbAcSecondaryheadMasterService.getAcHeadCodeInReceieptCategoryTypeEntry(activeStatusId,
                    orgId, recCategoryType);
            populateModel(model, paymentEntryDto, MODE_CREATE);
            model.addAttribute(EXP_BUDGETCODE_MAP, exBudgetAcHeadMap);
        } else {
            populateModel(model, paymentEntryDto, MODE_CREATE);

        }

        paymentEntryDto.setBillTypeId(updatedBillTypeId);
        model.addAttribute(PAYMENT_ENTRY_DTO, paymentEntryDto);
        return JSP_FORM;

    }
}
