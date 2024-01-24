package com.abm.mainet.property.ui.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.dto.ChallanReportDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.dto.CFCSchedulingCounterDet;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.CommonChallanPayModeDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptModesDetEntity;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.domain.ProvisionalBillMasEntity;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPaymentDetailDto;
import com.abm.mainet.property.dto.PropertyBillPaymentDto;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.service.AsExecssAmtService;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.PropertyBillPaymentService;
import com.abm.mainet.property.service.PropertyMainBillService;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.validator.BillPaymentValidator;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.google.common.util.concurrent.AtomicDouble;

@Component
@Scope("session")
public class PropertyBillPaymentModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyBillPaymentModel.class);

    @Autowired
    private ServiceMasterService serviceMaster;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    @Autowired
    private TbCfcApplicationMstService tbCfcservice;

    @Autowired
    private IChallanService iChallanService;

    @Resource
    private IFileUploadService fileUpload;

    @Resource
    private AssesmentMastService assesmentMastService;

    @Autowired
    private IReceiptEntryService receiptEntryService;
    
    @Autowired
	private BillMasterCommonService billMasterCommonService;
    
    @Autowired
    private PropertyMainBillService propertyMainBillService;
    
    @Resource
    private SelfAssessmentService selfAssessmentService;
    
    @Autowired
    private AsExecssAmtService asExecssAmtService;

    private Map<String, List<BillDisplayDto>> displayMap = new HashMap<>();

    private BillPaymentDetailDto billPayDto = new BillPaymentDetailDto();

    private PropertyBillPaymentDto propBillPaymentDto = new PropertyBillPaymentDto();

    private String receiptType;// used for manual receipt entry--Set 'M' for manual receipt

    private String manualReceiptNo; // used for manual receipt entry

    private Date manualReeiptDate; // used for manual receipt entry

    private ProperySearchDto searchDto = new ProperySearchDto();

    private String deptCode;

    private Double totalRebate;

    private String billingMethod;

    private String billingMethodConfigure;

    private String flatNo;

    private List<String> flatNoList;

    private String payeeName;

    private double halfPaymentRebate;

    private String rebateApplFlag;

    private ProvisionalAssesmentOwnerDtlDto ownerDtlDto;

    private ProvisionalAssesmentMstDto assmtDto;

    private double receivedAmount;

    private List<BillPaymentDetailDto> billPaymentDetailDtoList = new ArrayList<>();

    private String parentGrpFlag;

    private List<LookUp> parentPropLookupList = new ArrayList<>(0);
    
    private String occupierName;
    
    private String skdclEnv;
    
    private String illegal;

    @Override
    public boolean saveForm() {
        LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " saveForm() method");
        setCustomViewName("propertyBillPayment");
        final CommonChallanDTO offline = getOfflineDTO();
        final BillPaymentDetailDto billPayDto = getBillPayDto();
        List<DocumentDetailsVO> docs = null;
        final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
        offline.setOfflinePaymentText(modeDesc);
        offline.setManualReceiptNo(getManualReceiptNo());
        offline.setManualReeiptDate(getManualReeiptDate());
        offline.setFlatNo(propBillPaymentDto.getFlatNo());
        offline.setOccupierName(getOccupierName());
		/*
		 * if (CollectionUtils.isNotEmpty(offline.getMultiModeList())) { if
		 * (offline.getOnlineOfflineCheck() == null ||
		 * offline.getOnlineOfflineCheck().isEmpty()) {
		 * addValidationError("Please select the Collection Type"); } AtomicDouble
		 * totalPaidAmt = new AtomicDouble(0);
		 * offline.getMultiModeList().forEach(modeList -> { if (modeList.getAmount() !=
		 * null) totalPaidAmt.addAndGet(modeList.getAmount()); }); if
		 * (totalPaidAmt.doubleValue() > billPayDto.getTotalPaidAmt() ||
		 * totalPaidAmt.doubleValue() < billPayDto.getTotalPaidAmt()) {
		 * addValidationError("Receipt amount and sum of amount entered by modes should be equal"
		 * ); }
		 * 
		 * List<CommonChallanPayModeDTO> multiMode =
		 * offline.getMultiModeList().stream().flatMap(i -> { final AtomicInteger count
		 * = new AtomicInteger(); Long duplicateCount =
		 * receiptEntryService.getDuplicateChequeNoCount(i.getCbBankid(),
		 * i.getBmChqDDNo()); if (duplicateCount != null && duplicateCount > 0) {
		 * count.getAndIncrement(); }
		 * 
		 * final List<CommonChallanPayModeDTO> duplicatedCheque = new ArrayList<>();
		 * offline.getMultiModeList().forEach(p -> { String payMode = "";
		 * List<BankMasterEntity> iBank =
		 * ApplicationSession.getInstance().getBanks().stream() .filter(bank ->
		 * bank.getBankId().equals(i.getCbBankid())).collect(Collectors.toList());
		 * List<BankMasterEntity> pBank =
		 * ApplicationSession.getInstance().getBanks().stream() .filter(bank ->
		 * bank.getBankId().equals(p.getCbBankid())).collect(Collectors.toList());
		 * payMode =
		 * CommonMasterUtility.getNonHierarchicalLookUpObject(p.getPayModeIn(),
		 * UserSession.getCurrent().getOrganisation()).getLookUpCode(); if (payMode !=
		 * null && !payMode.isEmpty() && !payMode.equalsIgnoreCase("C") &&
		 * !payMode.equalsIgnoreCase("POS")) { if
		 * (p.getPayModeIn().equals(i.getPayModeIn()) &&
		 * p.getCbBankid().equals(i.getCbBankid()) &&
		 * p.getBmChqDDNo().equals(i.getBmChqDDNo()) &&
		 * (pBank.get(0).getIfsc().equals(iBank.get(0).getIfsc()))) {
		 * count.getAndIncrement(); } if (count.get() == 2) { duplicatedCheque.add(i); }
		 * } }); return duplicatedCheque.stream(); }).collect(Collectors.toList()); if
		 * (multiMode.size() > 0) {
		 * addValidationError(getAppSession().getMessage("Duplicate Cheque Number")); }
		 * 
		 * } else {
		 */
            validateBean(offline, CommonOfflineMasterValidator.class);
        /*}*/
        validateBean(billPayDto, BillPaymentValidator.class);
        if ("M".equals(getReceiptType())) {
            if (StringUtils.isEmpty(getManualReceiptNo())) {
                addValidationError("Manual Receipt No must not be empty");
            }
            if (MainetConstants.PAYMENT.OFFLINE.equals(offline.getOnlineOfflineCheck())) {
                addValidationError("Offline mode is not allowed please select another mode");
            }
            docs = new ArrayList<>(0);
            if ((FileUploadUtility.getCurrent().getFileMap() != null)
                    && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
                for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                    final List<File> list = new ArrayList<>(entry.getValue());
                    for (final File file : list) {
                        try {
                            DocumentDetailsVO d = new DocumentDetailsVO();
                            final Base64 base64 = new Base64();
                            final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
                            d.setDocumentByteCode(bytestring);
                            d.setDocumentName(file.getName());
                            docs.add(d);
                        } catch (final IOException e) {
                        }
                    }
                }
            } else {
                addValidationError("Please upload manual receipt");
            }
        }
        if (hasValidationErrors()) {
            return false;
        }

        offline.setOfflinePaymentText(modeDesc);
        Map<Long, Double> details = new HashMap<>(0);
        final Map<Long, Long> billDetails = new HashMap<>(0);
        

  		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), PrefixConstants.ENV.ACT) && billPayDto.getAdvanceAmt() > 0 && CollectionUtils.isNotEmpty(billPayDto.getBillMasList())) {
			List<ProvisionalBillMasEntity> provBillList = new ArrayList<>();
			BillDisplayDto advanceAmt = asExecssAmtService.getBillDisplayDtoWithAdvanceAmt(billPayDto.getPropNo(),
					UserSession.getCurrent().getOrganisation().getOrgid(), billPayDto.getAssmtDto().getFlatNo());
			List<BillReceiptPostingDTO> billRecePstingDto = null;
			double ajustedAmt = 0;
			List<TbBillMas> billMasList = billPayDto.getBillMasList();
			if (advanceAmt != null && advanceAmt.getTotalTaxAmt() != null) {

				billRecePstingDto = selfAssessmentService.knowkOffAdvanceAmt(billMasList, advanceAmt,
						UserSession.getCurrent().getOrganisation(), new Date(), null);
				if (billMasList.get(billMasList.size() - 1).getExcessAmount() > 0) {
					ajustedAmt = advanceAmt.getCurrentYearTaxAmt().doubleValue()
							- billMasList.get(billMasList.size() - 1).getExcessAmount();
				} else {
					ajustedAmt = advanceAmt.getCurrentYearTaxAmt().doubleValue();
				}
			}
			selfAssessmentService.saveAdvanceAmt(billPayDto.getAssmtDto(),
					UserSession.getCurrent().getEmployee().getEmpId(), billPayDto.getDeptId(), advanceAmt,
					UserSession.getCurrent().getOrganisation(), billMasList, getClientIpAddress(), provBillList,
					billRecePstingDto, ajustedAmt, billPayDto.getAssmtDto().getFlatNo(),
					UserSession.getCurrent().getLanguageId());

			billMasterCommonService.updateArrearInCurrentBills(billMasList);
			propertyMainBillService.saveAndUpdateMainBill(billMasList,
					UserSession.getCurrent().getOrganisation().getOrgid(),
					UserSession.getCurrent().getEmployee().getEmpId(), MainetConstants.Property.AuthStatus.AUTH,
					Utility.getMacAddress());
		}
  		
        setChallanDToandSaveChallanData(offline, details, billDetails, billPayDto);
        if ("M".equals(getReceiptType())) {
            RequestDTO dto = new RequestDTO();
            dto.setDeptId(offline.getDeptId());
            dto.setServiceId(offline.getServiceId());
            dto.setReferenceId(getReceiptDTO().getReceiptId().toString());
            dto.setOrgId(offline.getOrgId());
            dto.setUserId(offline.getUserId());
            dto.setLangId(Long.valueOf(offline.getLangId()));
            fileUpload.doFileUpload(docs, dto);
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(getBillingMethod())
                && org.apache.commons.lang.StringUtils.equals(getBillingMethod(), "I")) {
            assesmentMastService.updateMobileNoAndEmailInDetail(billPayDto.getMobileNo(), billPayDto.getEmailId(),
                    billPayDto.getAssmtDto().getProvisionalAssesmentDetailDtoList().get(0).getProAssdId());
        } else {
        	if(ownerDtlDto != null) {
        		assesmentMastService.updateMobileNoAndEmailInOwnerDti(billPayDto.getMobileNo(), billPayDto.getEmailId(),
                        ownerDtlDto.getProAssoId());	
        	}
        }
        sendSmsAndMail(billPayDto.getAssmtDto(), UserSession.getCurrent().getOrganisation(),
                UserSession.getCurrent().getLanguageId(), billPayDto.getTotalPaidAmt(),
                UserSession.getCurrent().getEmployee().getEmpId(), getReceiptDTO().getReceiptNo(),
                billPayDto.getMobileNo());
        LOGGER.info("End--> " + this.getClass().getSimpleName() + " saveForm() method");
        return true;
    }

    private void setChallanDToandSaveChallanData(final CommonChallanDTO offline, final Map<Long, Double> details,
            final Map<Long, Long> billDetails, BillPaymentDetailDto billPayDto) {
        LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " setChallanDToandSaveChallanData() method");
        final UserSession session = UserSession.getCurrent();

        LookUp printBillPaymentDescOnBill = null;
        try {
            printBillPaymentDescOnBill = CommonMasterUtility.getValueFromPrefixLookUp("PPB", "ENV",
                    UserSession.getCurrent().getOrganisation());
        } catch (Exception e) {
            LOGGER.error("No prefix found for ENV(PPB)");
        }
        LookUp printManualReceiptYear = null;
        try {
            printManualReceiptYear = CommonMasterUtility.getValueFromPrefixLookUp("PMY", "ENV",
                    UserSession.getCurrent().getOrganisation());
        } catch (Exception e) {
            LOGGER.error("No prefix found for ENV(PMY)");
        }
        try {
            CFCSchedulingCounterDet counterDet = null;
            if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "SKDCL")) {
                counterDet = tbCfcservice.getCounterDetByEmpId(UserSession.getCurrent().getEmployee().getEmpId(),
                        UserSession.getCurrent().getOrganisation().getOrgid());
                if (counterDet != null) {
                	//#147528->to get counter details on receipt
					tbCfcservice.setRecieptCfcAndCounterCount(counterDet);
                    offline.setCfcCenter(counterDet.getCollcntrno());
                    offline.setCfcCounterNo(counterDet.getCounterno());
                }

            }
        } catch (Exception e) {
            logger.info("Exception occure while seting the Counter scheduling info:" + e);
        }

        offline.setAmountToPay(Double.toString(billPayDto.getTotalPaidAmt()));
        offline.setUserId(session.getEmployee().getEmpId());
        offline.setOrgId(session.getOrganisation()
                .getOrgid());
        offline.setLangId(session.getLanguageId());
        offline.setLgIpMac(session.getEmployee().getEmppiservername());
        offline.setNewHouseNo(billPayDto.getNewHouseNo());
        if ((details != null) && !details.isEmpty()) {
            offline.setFeeIds(details);
        }
        if ((billDetails != null) && !billDetails.isEmpty()) {
            offline.setBillDetIds(billDetails);
        }
        offline.setPropNoConnNoEstateNoL(getAppSession().getMessage("propertydetails.PropertyNo."));
        offline.setPropNoConnNoEstateNoV(billPayDto.getPropNo());
        offline.setFaYearId(UserSession.getCurrent().getFinYearId());
        if (getManualReeiptDate() != null && printManualReceiptYear != null
                && org.apache.commons.lang.StringUtils.equals(MainetConstants.FlagY, printManualReceiptYear.getOtherField())) {
            Long manualYearId = ApplicationContextProvider.getApplicationContext().getBean(IFinancialYearService.class)
                    .getFinanceYearId(getManualReeiptDate());
            offline.setFaYearId(String.valueOf(manualYearId));
        }
        offline.setPaymentCategory(MainetConstants.NewWaterServiceConstants.BILL_SCHEDULE_DATE);
        offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
        offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
        offline.setParentPropNo(billPayDto.getParentPropNo());
        // offline.setEmailId(billPayDto.get);
        offline.setApplicantName(billPayDto.getOwnerFullName());
        offline.setApplicantFullName(billPayDto.getOwnerFullName());
        offline.setPayeeName(billPayDto.getOwnerFullName());
        if (org.apache.commons.lang.StringUtils.isNotBlank(payeeName)) {
            offline.setPayeeName(payeeName);
        }
        offline.setApplNo(billPayDto.getApplNo());
        offline.setApplicantAddress(billPayDto.getAddress());
        offline.setUniquePrimaryId(billPayDto.getPropNo());
        if(org.apache.commons.lang.StringUtils.isNotBlank(billPayDto.getParentPropNo())) {
        	offline.setUniquePrimaryId(billPayDto.getParentPropNo());
        }
        offline.setMobileNumber(billPayDto.getPrimaryOwnerMobNo());
        offline.setServiceId(billPayDto.getServiceId());
        if ((printBillPaymentDescOnBill != null && org.apache.commons.lang.StringUtils.equals(MainetConstants.FlagY,
                printBillPaymentDescOnBill.getOtherField())) && (offline.getApplNo() == null || offline.getApplNo() == 0)) {
            try {
                final ServiceMaster service = serviceMaster.getServiceByShortName("PBP",
                        UserSession.getCurrent().getOrganisation().getOrgid());
                if (service != null) {
                    offline.setServiceId(service.getSmServiceId());
                }
            } catch (Exception exception) {
                LOGGER.error("No service available for service short code PBP");
            }
        }
        offline.setDeptId(billPayDto.getDeptId());
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED);
        offline.setDocumentUploaded(false);
        offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        offline.setPlotNo(billPayDto.getPlotNo());
        offline.setOfflinePaymentText(CommonMasterUtility
                .getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
                .getLookUpCode());
        offline.setManualReceiptNo(getManualReceiptNo());
        offline.setManualReeiptDate(getManualReeiptDate());
        offline.setUsageType(billPayDto.getUsageType1());
        if (billPayDto.getWard1() != null) {
            offline.getDwzDTO().setAreaDivision1(billPayDto.getWard1());
        }
        if (billPayDto.getWard2() != null) {
            offline.getDwzDTO().setAreaDivision2(billPayDto.getWard2());
        }
        if (billPayDto.getWard3() != null) {
            offline.getDwzDTO().setAreaDivision3(billPayDto.getWard3());
        }
        if (billPayDto.getWard4() != null) {
            offline.getDwzDTO().setAreaDivision4(billPayDto.getWard4());
        }
        if (billPayDto.getWard5() != null) {
            offline.getDwzDTO().setAreaDivision5(billPayDto.getWard5());
        }
        // offline.setBillDetails(billPayDto.getBillDetails());
        offline.setReferenceNo(billPayDto.getOldpropno());
        offline.setPdRv(billPayDto.getPdRv());
        offline.setParentPropNo(billPayDto.getParentPropNo());

        //setting parshad ward zone details
        if ((billPayDto.getAssmtDto().getAssParshadWard1() != null) && (billPayDto.getAssmtDto().getAssParshadWard1() > 0)) {
        	offline.setParshadWard1(CommonMasterUtility.getHierarchicalLookUp(billPayDto.getAssmtDto().getAssParshadWard1(), UserSession.getCurrent().getOrganisation())
                    .getLookUpDesc());
        }
        if ((billPayDto.getAssmtDto().getAssParshadWard2() != null) && (billPayDto.getAssmtDto().getAssParshadWard2() > 0)) {
        	offline.setParshadWard2(CommonMasterUtility.getHierarchicalLookUp(billPayDto.getAssmtDto().getAssParshadWard2(), UserSession.getCurrent().getOrganisation())
                    .getLookUpDesc());
        }
        if ((billPayDto.getAssmtDto().getAssParshadWard3() != null) && (billPayDto.getAssmtDto().getAssParshadWard3() > 0)) {
        	offline.setParshadWard3(CommonMasterUtility.getHierarchicalLookUp(billPayDto.getAssmtDto().getAssParshadWard3(), UserSession.getCurrent().getOrganisation())
                    .getLookUpDesc());
        }
        if ((billPayDto.getAssmtDto().getAssParshadWard4() != null) && (billPayDto.getAssmtDto().getAssParshadWard4() > 0)) {
        	offline.setParshadWard4(CommonMasterUtility.getHierarchicalLookUp(billPayDto.getAssmtDto().getAssParshadWard4(), UserSession.getCurrent().getOrganisation())
                    .getLookUpDesc());
        }
        if ((billPayDto.getAssmtDto().getAssParshadWard5() != null) && (billPayDto.getAssmtDto().getAssParshadWard5() > 0)) {
        	offline.setParshadWard5(CommonMasterUtility.getHierarchicalLookUp(billPayDto.getAssmtDto().getAssParshadWard5(), UserSession.getCurrent().getOrganisation())
                    .getLookUpDesc());
        }
        
        
        if (billPayDto.getAssmtDto() != null) {
            offline.setUniquePropertyId(billPayDto.getAssmtDto().getUniquePropertyId());
        }
        
        if ((offline.getOnlineOfflineCheck() != null)
                && offline.getOnlineOfflineCheck().equals(
                        MainetConstants.PAYMENT_TYPE.OFFLINE)) {
            final ChallanMaster master = iChallanService
                    .InvokeGenerateChallan(offline);
            offline.setChallanValidDate(master
                    .getChallanValiDate());
            offline.setChallanNo(master.getChallanNo());
            // setSuccessMessage(getAppSession().getMessage("prop.save.self") + asseMstDto.getApmApplicationId());
        } else if ((offline.getOnlineOfflineCheck() != null)
                && offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
            final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline,
                    getServiceName());
            // Defect#114092 - After submitting the form sequence of Tax Description is
            // getting changed on generated Receipt
            if (printDto.getPaymentList() != null && !printDto.getPaymentList().isEmpty()) {
            	//#149921
            	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)){
            		printDto.getPaymentList().sort(Comparator.comparing(
    						ChallanReportDTO::getDisplaySeq, Comparator.nullsLast(Comparator.naturalOrder())));
            	}else {
            		printDto.getPaymentList().sort(Comparator.comparing(ChallanReportDTO::getDetails));
            	}
            	
            }
                
            setReceiptDTO(printDto);

            // US#102200 // pushing document to DMS
            /*
             * String URL = ServiceEndpoints.BIRT_REPORT_DMS_URL + "=" +
             * ApplicationSession.getInstance().getMessage("property.birtName.billPayment") + "&__format=pdf&RP_ORGID=" +
             * offline.getOrgId() + "&RP_RCPTID=" + printDto.getReceiptId(); Utility.pushDocumentToDms(URL,
             * billPayDto.getPropNo(), MainetConstants.Property.PROP_DEPT_SHORT_CODE,fileUpload);
             */

            setSuccessMessage(getAppSession().getMessage("prop.bill.paid"));
        }
        setOfflineDTO(offline);
        LOGGER.info("End--> " + this.getClass().getSimpleName() + " setChallanDToandSaveChallanData() method");
    }

    private void sendSmsAndMail(final ProvisionalAssesmentMstDto provAsseMstDto, Organisation organisation, int langId,
            double receivedAmount, Long userId, String receiptNo, String mobileNo) {
        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setUserId(userId);
        if(provAsseMstDto != null) {
        	 dto.setEmail(provAsseMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0).geteMail());
        	 dto.setPropertyNo(provAsseMstDto.getAssNo());
        }
        dto.setMobnumber(mobileNo);
        dto.setReferenceNo(receiptNo);
        dto.setOwnerName(billPayDto.getOwnerFullName());
        dto.setAmount(receivedAmount);
        dto.setDate(new Date());
        if (provAsseMstDto != null && provAsseMstDto.getApmApplicationId() != null) {
            dto.setAppNo(provAsseMstDto.getApmApplicationId().toString());
        }
        ismsAndEmailService.sendEmailSMS(MainetConstants.Property.PROP_DEPT_SHORT_CODE, "PropertyBillPayment.html",
                PrefixConstants.SMS_EMAIL_ALERT_TYPE.COMPLETED_MESSAGE, dto, organisation, langId);
    }

    public Map<String, List<BillDisplayDto>> getDisplayMap() {
        return displayMap;
    }

    public void setDisplayMap(Map<String, List<BillDisplayDto>> displayMap) {
        this.displayMap = displayMap;
    }

    public PropertyBillPaymentDto getPropBillPaymentDto() {
        return propBillPaymentDto;
    }

    public void setPropBillPaymentDto(PropertyBillPaymentDto propBillPaymentDto) {
        this.propBillPaymentDto = propBillPaymentDto;
    }

    public BillPaymentDetailDto getBillPayDto() {
        return billPayDto;
    }

    public void setBillPayDto(BillPaymentDetailDto billPayDto) {
        this.billPayDto = billPayDto;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
    }

    public String getManualReceiptNo() {
        return manualReceiptNo;
    }

    public void setManualReceiptNo(String manualReceiptNo) {
        this.manualReceiptNo = manualReceiptNo;
    }

    public Date getManualReeiptDate() {
        return manualReeiptDate;
    }

    public void setManualReeiptDate(Date manualReeiptDate) {
        this.manualReeiptDate = manualReeiptDate;
    }

    public ProperySearchDto getSearchDto() {
        return searchDto;
    }

    public void setSearchDto(ProperySearchDto searchDto) {
        this.searchDto = searchDto;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public Double getTotalRebate() {
        return totalRebate;
    }

    public void setTotalRebate(Double totalRebate) {
        this.totalRebate = totalRebate;
    }

    public String getBillingMethod() {
        return billingMethod;
    }

    public void setBillingMethod(String billingMethod) {
        this.billingMethod = billingMethod;
    }

    public String getBillingMethodConfigure() {
        return billingMethodConfigure;
    }

    public void setBillingMethodConfigure(String billingMethodConfigure) {
        this.billingMethodConfigure = billingMethodConfigure;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public List<String> getFlatNoList() {
        return flatNoList;
    }

    public void setFlatNoList(List<String> flatNoList) {
        this.flatNoList = flatNoList;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public ProvisionalAssesmentOwnerDtlDto getOwnerDtlDto() {
        return ownerDtlDto;
    }

    public void setOwnerDtlDto(ProvisionalAssesmentOwnerDtlDto ownerDtlDto) {
        this.ownerDtlDto = ownerDtlDto;
    }

    public ProvisionalAssesmentMstDto getAssmtDto() {
        return assmtDto;
    }

    public void setAssmtDto(ProvisionalAssesmentMstDto assmtDto) {
        this.assmtDto = assmtDto;
    }

    public double getHalfPaymentRebate() {
        return halfPaymentRebate;
    }

    public void setHalfPaymentRebate(double halfPaymentRebate) {
        this.halfPaymentRebate = halfPaymentRebate;
    }

    public String getRebateApplFlag() {
        return rebateApplFlag;
    }

    public void setRebateApplFlag(String rebateApplFlag) {
        this.rebateApplFlag = rebateApplFlag;
    }

    public double getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(double receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public List<BillPaymentDetailDto> getBillPaymentDetailDtoList() {
        return billPaymentDetailDtoList;
    }

    public void setBillPaymentDetailDtoList(List<BillPaymentDetailDto> billPaymentDetailDtoList) {
        this.billPaymentDetailDtoList = billPaymentDetailDtoList;
    }

    public String getParentGrpFlag() {
        return parentGrpFlag;
    }

    public void setParentGrpFlag(String parentGrpFlag) {
        this.parentGrpFlag = parentGrpFlag;
    }

    public List<LookUp> getParentPropLookupList() {
        return parentPropLookupList;
    }

    public void setParentPropLookupList(List<LookUp> parentPropLookupList) {
        this.parentPropLookupList = parentPropLookupList;
    }

	public String getOccupierName() {
		return occupierName;
	}

	public void setOccupierName(String occupierName) {
		this.occupierName = occupierName;
	}

	public String getSkdclEnv() {
		return skdclEnv;
	}

	public void setSkdclEnv(String skdclEnv) {
		this.skdclEnv = skdclEnv;
	}

	public String getIllegal() {
		return illegal;
	}

	public void setIllegal(String illegal) {
		this.illegal = illegal;
	}

}
