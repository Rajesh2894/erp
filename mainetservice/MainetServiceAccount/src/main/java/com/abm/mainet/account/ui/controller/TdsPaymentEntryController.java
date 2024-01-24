package com.abm.mainet.account.ui.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.abm.mainet.account.domain.AccountPaymentDetEntity;
import com.abm.mainet.account.domain.AccountPaymentMasterEntity;
import com.abm.mainet.account.dto.AccountBillEntryExpenditureDetBean;
import com.abm.mainet.account.dto.AccountBudgetReappropriationMasterBean;
import com.abm.mainet.account.dto.PaymentEntryDto;
import com.abm.mainet.account.dto.PaymentReportDto;
import com.abm.mainet.account.dto.TbAcPayToBank;
import com.abm.mainet.account.dto.VoucherTemplateDTO;
import com.abm.mainet.account.service.AccountBillEntryService;
import com.abm.mainet.account.service.BudgetCodeService;
import com.abm.mainet.account.service.DeductionRegisterService;
import com.abm.mainet.account.service.PaymentEntrySrevice;
import com.abm.mainet.account.service.TbAcPayToBankService;
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
import com.abm.mainet.common.master.service.TbOrganisationService;
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

/*
 * Object: TdsPaymentEntry(Story-155)
 *  Author By:- Ajay Kumar
 * Date: 16-02-2018
 */
@Controller
@RequestMapping("/TdsPaymentEntry.html")
public class TdsPaymentEntryController extends AbstractController {
    private static final String Tds_Payment = "tdsPayment_list";
    private final static String PAYMENT_ENTRY_DTO = "tdsPaymentEntryDto";
    private final String JSP_FORM = "tdsPaymentEntry/form";
    private static final String BILL_TYPE_LIST = "billTypeList";
    private static final String VENDOR_LIST = "vendorList";
    private static final String TDS_LIST = "tdsList";
    private static final String BILL_DETAIL_LIST = "billDetailList";
    private static final String PAYMENT_MODE = "paymentMode";
    private static final String BANK_AC_MAP = "bankAccountMap";
    private static final String TEMPLATE_EXIST_FLAG = "templateExistFlag";
    private final String Report_FORM = "tdsPaymentEntry/Report";
    private static final String JSP_VIEW_PAY_FORM = "TdsPaymentEntry/viewPayForm";
    private final static String PAYMENT_REPORT_DTO = "oPaymentReportDto";
    private static final String PROJECTED_EXPENDITURE_LIST = "projectedExpenditureList";
    private static final String EXP_BUDGETCODE_MAP = "expBudgetCodeMap";
    @Resource
    private DepartmentService departmentService;
    @Resource
    private TbBankmasterService banksMasterService;
    @Resource
    private SecondaryheadMasterService secondaryheadMasterService;
    @Resource
    private TbAcVendormasterService vendorMasterService;
    @Resource
    private VoucherTemplateService voucherTemplateService;
    @Resource
    private ILocationMasService locMasService;
    @Resource
    private PaymentEntrySrevice paymentEntryService;
    @Resource
    private AccountBillEntryService billEntryService;
    @Resource
    private BudgetCodeService budgetCodeService;
    @Resource
    private DeductionRegisterService deductionRegisterService;
    @Resource
    private AccountFundMasterService accountFundMasterService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private TbAcPayToBankService acPayToBankService;
    @Resource
    private TbOrganisationService tbOrganisationService;
    @Resource
   	private IAttachDocsService attachDocsService;
    @Autowired
   	private IFileUploadService accountFileUpload;

    List<PaymentEntryDto> masterDtoList = null;

    public TdsPaymentEntryController() {
        super(AccountBillRegistrationController.class, Tds_Payment);
        log("AccountBillRegistrationController created.");
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

    @RequestMapping(params = "formForCreate", method = RequestMethod.POST)
    public String formForCreate(final Model model) {
    	accountFileUpload.sessionCleanUpForFileUpload();
        final PaymentEntryDto paymentEntryDto = new PaymentEntryDto();
        populateModel(model, paymentEntryDto, MODE_CREATE);
        return JSP_FORM;
    }

    private void populateModel(final Model model, final PaymentEntryDto paymentEntryDto, final String formMode) {
        // TODO Auto-generated method stub
        model.addAttribute(PAYMENT_ENTRY_DTO, paymentEntryDto);
        populateListOfBillType(model, paymentEntryDto);
        populateVendorList(model);
        populateTdsTypeList(model);
        populateBillDetails(model);
        populatePaymentModeList(model);
        populateListOfBanks(model);
        getExpenditureDetailsByFinyearId(paymentEntryDto, model);
        model.addAttribute("SudaEnv", Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA));
        if (formMode.equals(MODE_VIEW)) {
            populateListOfBanks(model);
            model.addAttribute(MainetConstants.AccountBillEntry.EXPENDITURE_HEAD_MAP, secondaryheadMasterService
                    .findExpenditureHeadMapForView(UserSession.getCurrent().getOrganisation().getOrgid()));
        }
        checkTemplate(model);
    }

    // populate for Bill Type
    public void populateListOfBillType(final Model model, PaymentEntryDto paymentEntryDto) {
        final List<LookUp> billTypeLookupList = CommonMasterUtility.getLookUps(AccountPrefix.ABT.toString(),
                UserSession.getCurrent().getOrganisation());
        for (LookUp list : billTypeLookupList) {
            if (list.getDefaultVal().equals(MainetConstants.CommonConstants.Y)) {
                paymentEntryDto.setBillTypeId(list.getLookUpId());
            }
        }
        model.addAttribute(BILL_TYPE_LIST, billTypeLookupList);
    }

    // populate for vendorList
    public void populateVendorList(final Model model) {
        final Organisation org = UserSession.getCurrent().getOrganisation();
        final Integer languageId = UserSession.getCurrent().getLanguageId();
        final LookUp lookUpVendorStatus = CommonMasterUtility
                .getLookUpFromPrefixLookUpValue(AccountConstants.AC.getValue(), PrefixConstants.VSS, languageId, org);
        final Long vendorStatus = lookUpVendorStatus.getLookUpId();
        final List<TbAcVendormaster> vendorList = vendorMasterService.getActiveVendors(org.getOrgid(), vendorStatus);
        model.addAttribute(VENDOR_LIST, vendorList);
    }

    // populate for tdstype list
    public void populateTdsTypeList(final Model model) {
        final List<LookUp> tdsLookUpList = CommonMasterUtility.getListLookup(PrefixConstants.TDS,
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(TDS_LIST, tdsLookUpList);
    }

    // populate for bill details
    public void populateBillDetails(final Model model) {
        final PaymentEntryDto paymentDto = new PaymentEntryDto();
        final PaymentDetailsDto billDetailDto = new PaymentDetailsDto();
        final List<PaymentDetailsDto> billDetailList = new ArrayList<>();
        billDetailList.add(billDetailDto);
        paymentDto.setPaymentDetailsDto(billDetailList);
        model.addAttribute(BILL_DETAIL_LIST, billDetailList);
    }

    // populate for payment mode list
    public void populatePaymentModeList(final Model model) {
        final List<LookUp> paymentModeList = CommonMasterUtility.getLookUps(AccountPrefix.PAY.name(),
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> paymentMode = new ArrayList<>();
        for (final LookUp payList : paymentModeList) {
            if (payList.getLookUpCode().equals(AccountConstants.CHEQUE.getValue())
                    || payList.getLookUpCode().equals(AccountConstants.BANK.getValue())
                    || payList.getLookUpCode().equals(AccountConstants.RTGS.getValue())) {
                paymentMode.add(payList);
            }
        }
        model.addAttribute(PAYMENT_MODE, paymentMode);
    }

    // populate for expenditure details list.
    private void getExpenditureDetailsByFinyearId(final PaymentEntryDto paymentEntryDto, final Model model) {
        List<AccountBillEntryExpenditureDetBean> billExpDetBeanList = null;
        AccountBillEntryExpenditureDetBean billExpDetBean = null;
        billExpDetBeanList = new ArrayList<>();
        billExpDetBean = new AccountBillEntryExpenditureDetBean();
        billExpDetBeanList.add(billExpDetBean);
        model.addAttribute(PROJECTED_EXPENDITURE_LIST, billExpDetBeanList);
        model.addAttribute(EXP_BUDGETCODE_MAP, secondaryheadMasterService
                .findExpenditureHeadMap(UserSession.getCurrent().getOrganisation().getOrgid()));
    }

    // populate for list of bank
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

    // populate for check template
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

    // populate for grid data
    @RequestMapping()
    public String populateGridList(final Model model, final HttpServletRequest httpServletRequest) {
        masterDtoList = new ArrayList<>();
        final PaymentEntryDto paymentEntryDto = new PaymentEntryDto();
        populateModel(model, paymentEntryDto, MODE_CREATE);
        masterDtoList.clear();
        return Tds_Payment;
    }

    @RequestMapping(params = "vendordetails", method = RequestMethod.POST)
    public String getvendorDetails(final PaymentEntryDto bean, final BindingResult bindingResult, final Model model) {
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        String fromDate = bean.getFromDate();
        String toDate = bean.getToDate();
        Long tdsTypeId = bean.getTdsTypeId();
        final List<PaymentDetailsDto> vendorDetails = deductionRegisterService.getActiveDetails(orgId,
                Utility.stringToDate(fromDate), Utility.stringToDate(toDate), tdsTypeId);
        if (vendorDetails == null || vendorDetails.isEmpty()) {
            bean.setSuccessfulFlag(MainetConstants.MASTER.Y);
        } else {
            bean.setPaymentDetailsDto(vendorDetails);
        }
        model.addAttribute(PAYMENT_ENTRY_DTO, bean);
        populateModel(model, bean, MODE_CREATE);
        return JSP_FORM;
    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public @ResponseBody String createBillEntry(@Valid final PaymentEntryDto paymentEntrydto, final Model model,
            final HttpServletRequest request) throws ParseException {
        log("Action 'create payment form'");
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        paymentEntrydto.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);
        paymentEntrydto.setSuccessfulFlag(MainetConstants.MASTER.Y);
        paymentEntrydto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        paymentEntrydto.setPaymentDate(Utility.stringToDate(paymentEntrydto.getTransactionDate()));
        paymentEntrydto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        long fieldId = 0;
        if (UserSession.getCurrent().getLoggedLocId() != null) {
            final TbLocationMas locMas = locMasService.findById(UserSession.getCurrent().getLoggedLocId());
            if ((locMas.getLocRevenueWZMappingDto() != null) && !locMas.getLocRevenueWZMappingDto().isEmpty()) {
                fieldId = locMas.getLocRevenueWZMappingDto().get(0).getCodIdRevLevel1();
            }
        }
        if (fieldId == 0) {
            throw new NullPointerException("fieldId is not linked with Location Master for[locId="
                    + UserSession.getCurrent().getEmployee().getTbLocationMas().getLocId() + ",locName="
                    + UserSession.getCurrent().getEmployee().getTbLocationMas().getLocNameEng() + "]");
        }
        paymentEntrydto.setFieldId(fieldId);
        paymentEntrydto.setCreatedDate(new Date());
        paymentEntrydto.setLanguageId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
        paymentEntrydto.setLgIpMacAddress(Utility.getClientIpAddress(request));
        paymentEntrydto.setOrganisation(organisation);
        paymentEntrydto.setPaymentDetailsDto(paymentEntrydto.getPaymentDetailsDto());
        AccountPaymentMasterEntity paymentEntity = paymentEntryService.createTDSPaymentEntry(paymentEntrydto);
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
						+ MainetConstants.operator.FORWARD_SLACE + paymentEntity.getPaymentId();
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
        request.getSession().setAttribute(MainetConstants.DirectPaymentEntry.PAYMENT_LIST, paymentEntrydto);
        return paymentEntrydto.getPaymentNo();
    }

    public void prepareFileUpload(PaymentEntryDto paymentEntryDto) {
		List<DocumentDetailsVO> documentDetailsVOList = paymentEntryDto.getAttachments();
		paymentEntryDto.setAttachments(accountFileUpload.prepareFileUpload(documentDetailsVOList));
	}
    
    @RequestMapping(params = "searchTdsPayData")
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
        final Long paymentTypeFlag = 2L;
        if ((paymentEntryDate != MainetConstants.BLANK) || (paymentAmount != null) || (vendorId != null)
                || (budgetCodeId != null) || (paymentNo != MainetConstants.BLANK) || (baAccountid != null)) {
            directPaymentEntryList = paymentEntryService.getTdsPaymentDetails(orgId, paymentEntryDate, paymentAmount,
                    vendorId, budgetCodeId, paymentNo, baAccountid, paymentTypeFlag);
        }
        if ((directPaymentEntryList != null) && !directPaymentEntryList.isEmpty()) {
            for (final AccountPaymentMasterEntity paymentMaster : directPaymentEntryList) {
                masterDto = new PaymentEntryDto();
                masterDto.setId(paymentMaster.getPaymentId());
                masterDto.setPaymentNo(paymentMaster.getPaymentNo());
                masterDto.setPaymentEntryDate(UtilityService.convertDateToDDMMYYYY(paymentMaster.getPaymentDate()));
                if (paymentMaster.getVmVendorId() != null && paymentMaster.getVmVendorId().getVmVendorid() != null) {
                    final String vendorDescription = vendorMasterService
                            .getVendorNameById(paymentMaster.getVmVendorId().getVmVendorid(), paymentMaster.getOrgId());
                    masterDto.setVendorDesc(vendorDescription);
                } else {
                    if (paymentMaster.getNarration() != null && !paymentMaster.getNarration().isEmpty()) {
                        String[] arr = paymentMaster.getNarration().split(Pattern.quote("$"));
                        masterDto.setVendorDesc(arr[0]);
                    }
                }
                masterDto.setBillAmountStr(
                        CommonMasterUtility.getAmountInIndianCurrency(paymentMaster.getPaymentAmount()));
                masterDtoList.add(masterDto);
            }
        }
        return masterDtoList;
    }

    //
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
        oPaymentReportDto.setVoucherAmount(CommonMasterUtility.getAmountInIndianCurrency(entity.getPaymentAmount()));
        if (entity.getVmVendorId().getVmVendorid() != null) {
            oPaymentReportDto.setVendorCodeDescription(entity.getVmVendorId().getVmVendorname());
        }
        oPaymentReportDto.setNarration(
                oPaymentReportDto.getVendorCodeDescription() + MainetConstants.HYPHEN + entity.getNarration());
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
            oPaymentReportDto.setInstrumentDate(Utility.dateToString(entity.getInstrumentDate()));
        }
        List<PaymentDetailsDto> detList = new ArrayList<PaymentDetailsDto>();
        List<AccountPaymentDetEntity> detDTOList = entity.getPaymentDetailList();
        for (final AccountPaymentDetEntity det : detDTOList) {
            final PaymentDetailsDto detDTo = new PaymentDetailsDto();
            String accountCodeDesc = budgetCodeService
                    .findAccountHeadCodeBySacHeadId(Long.valueOf(det.getBudgetCodeId()), entity.getOrgId());

            detDTo.setAccountCode(accountCodeDesc);
            String accountHead = budgetCodeService.findAccountHeadCodeBySacHeadId(Long.valueOf(det.getBudgetCodeId()),
                    entity.getOrgId());

            detDTo.setAccountHead(accountHead);
            detDTo.setPaymentAmountDesc(CommonMasterUtility.getAmountInIndianCurrency(entity.getPaymentAmount()));

            AccountBudgetCodeEntity accountHeadEntity = budgetCodeService
                    .findBudgetHeadIdBySacHeadId(Long.valueOf(det.getBudgetCodeId()), entity.getOrgId());
            if (accountHeadEntity != null) {
                if (accountHeadEntity.getTbAcFunctionMaster() != null
                        && (accountHeadEntity.getTbAcFunctionMaster().getFunctionDesc() != null
                                && !accountHeadEntity.getTbAcFunctionMaster().getFunctionDesc().isEmpty())) {
                    detDTo.setFunctionDesc(accountHeadEntity.getTbAcFunctionMaster().getFunctionDesc());
                }

                String departmentDesc = departmentService.getDepartmentDescByDeptCode(AccountConstants.AC.toString());
                detDTo.setFunctionaryDesc(departmentDesc);
            }
            detList.add(detDTo);
            break;
        }
        oPaymentReportDto.setPaymentDetailsDto(detList);
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
        final String amountInWords = Utility.convertBigNumberToWord(entity.getPaymentAmount());
        oPaymentReportDto.setAmountInWords(amountInWords);
        oPaymentReportDto.setListOfTbPaymentRepor(paymentDetailsDto);
        populateReportModel(model, oPaymentReportDto, MODE_CREATE);
        return Report_FORM;
    }

    public void populateReportModel(final Model model, final PaymentReportDto oPaymentReportDto,
            final String formMode) {
        model.addAttribute(PAYMENT_REPORT_DTO, oPaymentReportDto);
        populateVendorList(model);
        populatePaymentModeList(model);
        populateListOfBanks(model);
        checkTemplate(model);
    }

    @RequestMapping(params = "formForView", method = RequestMethod.POST)
    public String formForView(final Model model, final HttpServletRequest request, @RequestParam("id") final Long id) {
        final int langId = UserSession.getCurrent().getLanguageId();
        log("Action 'formForView'");
        final PaymentEntryDto paymentEntryDto = paymentEntryService.findPaymentEntryDataById(id,
                UserSession.getCurrent().getOrganisation().getOrgid(), langId);
        //file upload start
        final String accountIds = MainetConstants.RECEIPT_MASTER.Module + MainetConstants.operator.FORWARD_SLACE +id;
		final List<AttachDocs> attachDocsList = attachDocsService
				.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), accountIds);
		paymentEntryDto.setAttachDocsList(attachDocsList);
		//file upload end
        model.addAttribute(MainetConstants.DirectPaymentEntry.PAY_DETAIL_LIST, paymentEntryDto.getPaymentDetailsDto());
        populateModel(model, paymentEntryDto, MODE_VIEW);
        return JSP_VIEW_PAY_FORM;
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
        oPaymentReportDto.setPayDate(Utility.dateToString(opaymentEntryDto.getPaymentDate()));
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
                    oPaymentReportDto.setNameOfTheFund(
                            accountFundMasterService.getFundCodeDesc(Long.valueOf(obj[4].toString())));
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
                int index = acHeadCode.lastIndexOf("-");
                dto.setAccountCode(acHeadCode);
                dto.setAccountHead(acHeadCode.substring(index + 1));
                AccountBudgetCodeEntity accountHeadEntity = budgetCodeService
                        .findBudgetHeadIdBySacHeadId(paymentDetailsDto2.getBudgetCodeId(), opaymentEntryDto.getOrgId());
                if (accountHeadEntity != null) {
                    if (accountHeadEntity.getTbAcFunctionMaster() != null
                            && (accountHeadEntity.getTbAcFunctionMaster().getFunctionDesc() != null
                                    && !accountHeadEntity.getTbAcFunctionMaster().getFunctionDesc().isEmpty())) {
                        dto.setFunctionDesc(accountHeadEntity.getTbAcFunctionMaster().getFunctionDesc());
                    }

                    String departmentDesc = departmentService
                            .getDepartmentDescByDeptCode(AccountConstants.AC.toString());
                    dto.setFunctionaryDesc(departmentDesc);
                }
                if (paymentDetailsDto2.getPaymentAmt() != null) {
                    dto.setPaymentAmountDesc(
                            CommonMasterUtility.getAmountInIndianCurrency(opaymentEntryDto.getTotalPaymentAmount()));
                    totalPaymentAmt = totalPaymentAmt
                            .add(new BigDecimal(paymentDetailsDto2.getPaymentAmt().toString()));
                }
                detDTOList.add(dto);
                break;
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

    @SuppressWarnings({ "unlikely-arg-type", "null" })
    @RequestMapping(method = { RequestMethod.POST }, params = "vendorNameOfTdstype")
    public @ResponseBody Map<Long, String> serchVendorName(@RequestParam("tdsTypeId") Long tdsTypeId,
            final HttpServletRequest httpServletRequest) {
        // Get Vendorlist From TbAcPayToBank
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.N);
        Organisation defaultOrg = null;
        if (isDafaultOrgExist) {
            defaultOrg = ApplicationSession.getInstance().getSuperUserOrganization();
        } else {
            defaultOrg = UserSession.getCurrent().getOrganisation();
        }
        List<TbAcPayToBank> list = acPayToBankService.getTdsTypeData(tdsTypeId,
                UserSession.getCurrent().getOrganisation().getOrgid(), defaultOrg.getOrgid());
        final Integer languageId = UserSession.getCurrent().getLanguageId();
        final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                AccountConstants.AC.getValue(), PrefixConstants.VSS, languageId, defaultOrg);
        final Long vendorStatus = lookUpVendorStatus.getLookUpId();

        // Filter Vendor From TbAcPayToBank and store in vendorMasterList
        List<TbAcVendormaster> vendorList = vendorMasterService.getActiveVendors(defaultOrg.getOrgid(), vendorStatus);
        Map<Long, String> vendornameOfTdsType = new HashMap<Long, String>();
        if (list.size() > 0) {
            vendorList.parallelStream().forEach(curr -> {
                if (curr.getVmVendorid().equals(list.get(0).getVmVendorid())) {
                    vendornameOfTdsType.put(curr.getVmVendorid(),
                            curr.getVmVendorcode() + " - " + curr.getVmVendorname());
                }
            });
        }
        if (list.size() == 0) {
            vendorList.parallelStream().forEach(curr -> {
                vendornameOfTdsType.put(curr.getVmVendorid(), curr.getVmVendorcode() + " - " + curr.getVmVendorname());
            });
        }
        return vendornameOfTdsType;
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
