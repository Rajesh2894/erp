package com.abm.mainet.water.ui.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CFCSchedulingCounterDet;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.CommonChallanPayModeDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.BillTaxDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.master.service.TbTaxMasService;
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
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.TbWtBillSchedule;
import com.abm.mainet.water.dto.WaterBillTaxDTO;
import com.abm.mainet.water.dto.WaterDataEntrySearchDTO;
import com.abm.mainet.water.repository.TbWtBillMasJpaRepository;
import com.abm.mainet.water.repository.TbWtExcessAmtJpaRepository;
import com.abm.mainet.water.rest.dto.WaterBillRequestDTO;
import com.abm.mainet.water.rest.dto.WaterBillResponseDTO;
import com.abm.mainet.water.service.BillMasterService;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.service.TbWtBillScheduleService;
import com.google.common.util.concurrent.AtomicDouble;
import com.ibm.icu.text.SimpleDateFormat;

/**
 * @author Rahul.Yadav
 *
 */
@Component
@Scope("session")
public class WaterBillPaymentModel extends AbstractFormModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(WaterBillPaymentModel.class);
	
	private static final long serialVersionUID = 7613524106924997028L;

	@Autowired
	private BillMasterCommonService billMasterCommonService;

	@Autowired
	private BillMasterService billGenerationService;

	@Autowired
	private IChallanService iChallanService;

	@Autowired
	private ServiceMasterService serviceMaster;

	@Resource
	private TbWtExcessAmtJpaRepository tbWtExcessAmtJpaRepository;

	@Resource
	private TbWtBillMasJpaRepository tbWtBillMasJpaRepository;

	@Resource
	private TbTaxMasService tbTaxMasService;
	
	@Autowired
	private IFileUploadService fileUploadService;
	
	@Autowired
	private NewWaterConnectionService newWaterConnectionService;
	
	@Autowired
	private TbCfcApplicationMstService tbCfcservice;
	
	private TbBillMas billMas = null;

	private Double payAmount;

	private ServiceMaster service = null;

	private String ccnNumber;
	
	private String oldccnNumber;

	private String message = null;

	private String advancePayment = null;

	private BillTaxDTO rebateAndAdvanceDto = null;

	private double rebateAmount = 0d;
	
	private double excessAmount = 0d;
	
	private double surchargeAmount = 0d;
	
	private String receiptType;
	
	private String manualReceiptNo;
	
	private Date manualReceiptdate;
	
	private String manualReceiptDateString;
	
	private String mobileNo;
	private String emailId;
	private String payeeName;

	private double curSurchargeAmount = 0d;
	
	 private String guardianName;
	
	 private List<DocumentDetailsVO> checkList = new ArrayList<>();

	public double getBalanceExcessAmount() {
		return balanceExcessAmount;
	}

	public void setBalanceExcessAmount(final double balanceExcessAmount) {
		this.balanceExcessAmount = balanceExcessAmount;
	}

	private List<WaterBillTaxDTO> taxes = null;

	private double balanceExcessAmount = 0d;

	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
	
	private WaterDataEntrySearchDTO searchDTO = new WaterDataEntrySearchDTO();
	
	private String mode;
	
	private String showMode;
	
	private String rebateMessage;
	
	private double adjustmentEntry;
	
	private String skdclEnv;

	public boolean getBillPaymentData() {
		setTaxes(new ArrayList<WaterBillTaxDTO>(0));
		setMessage(null);
		setBillMas(new TbBillMas());
		setRebateAmount(0d);
		setRebateAndAdvanceDto(null);
		setExcessAmount(0d);
		setBalanceExcessAmount(0d);
		
		final TbBillMas entity = getBillMas();
		if ((getCcnNumber() == null || getCcnNumber().isEmpty()) && (getOldccnNumber() == null || getOldccnNumber().isEmpty() )) {
			addValidationError(getAppSession().getMessage("water.billPayment.search"));
		}else if(StringUtils.isNotBlank(getReceiptType()) && StringUtils.equals(getReceiptType(), MainetConstants.FlagM)) {
			validateInput();
		}
	 
		if (hasValidationErrors()) {
			return false;
		}
		
		WaterBillRequestDTO payrequestDTO = new WaterBillRequestDTO();
		payrequestDTO.setCcnNumber(getCcnNumber());
		payrequestDTO.setOldccNumber(getOldccnNumber());		
		payrequestDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		payrequestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		payrequestDTO.setIpAddress(getClientIpAddress());
		payrequestDTO.setManualReceiptDate(getManualReceiptdate());
		
		WaterBillResponseDTO outPutData = billGenerationService.fetchBillPaymentData(payrequestDTO);
		if(outPutData.getCcnNumber()!=null && !outPutData.getCcnNumber().equals(MainetConstants.BLANK)) {
		setCcnNumber(outPutData.getCcnNumber());
		}
		if(outPutData.getOldccnNumber()!=null && !outPutData.getOldccnNumber().equals(MainetConstants.BLANK)) {
		setOldccnNumber(outPutData.getOldccnNumber());
		}
		if (StringUtils.isNotEmpty(outPutData.getGuardianName())) {
			setGuardianName(outPutData.getGuardianName());
		}
		if ((outPutData != null) && (outPutData.getTotalPayableAmount() > 0d)
				&& MainetConstants.FlagS.equals(outPutData.getStatus())) {
			entity.setOrgid(outPutData.getApplicantDto().getOrgId());
			entity.setCsIdn(outPutData.getCsIdn());
			entity.setBmTotalOutstanding(Math.floor(outPutData.getTotalPayableAmount() - outPutData.getRebateAmount()));
			LookUp waterRebateAdvancePayment = null;
   			try {
   				waterRebateAdvancePayment = CommonMasterUtility.getValueFromPrefixLookUp("APR", "WRB", UserSession.getCurrent().getOrganisation());
   			}catch (Exception exception) {
   			}
   			if(waterRebateAdvancePayment != null) {
   				entity.setBmTotalOutstanding(Math.floor(outPutData.getTotalPayableAmount()));
   				if(outPutData.getRebateAmount() > 0) {
   					setRebateMessage(MainetConstants.FlagY);
   				}
   			}
			setTaxes(outPutData.getTaxes());
			setExcessAmount(outPutData.getExcessAmount());
			setBalanceExcessAmount(outPutData.getBalanceExcessAmount());
			setAdjustmentEntry(outPutData.getAdjustmentEntry());
			/* setApmApplicationId(outPutData.getApplicationNumber()); */
			setApplicantDetailDto(outPutData.getApplicantDto());
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "RPF")){
				if(getApplicantDetailDto().getMobileNo() != null && !getApplicantDetailDto().getMobileNo().isEmpty()) {
					setMobileNo(getApplicantDetailDto().getMobileNo());
				}
				if(getApplicantDetailDto().getEmailId() != null && !getApplicantDetailDto().getEmailId().isEmpty()) {
					setEmailId(getApplicantDetailDto().getEmailId());
				}
			}
			setRebateAmount(outPutData.getRebateAmount());
			setSurchargeAmount(outPutData.getSurchargeAmount());
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
				setCurSurchargeAmount(outPutData.getCurrSurchargeAmount()); 
			} else {
				//setCurSurchargeAmount(outPutData.getCurrSurchargeAmount()); 
			}
			
	        this.setShowMode(MainetConstants.FlagY);
			return true;
		} else if (MainetConstants.FlagN.equals(outPutData.getStatus())) {
			addValidationError(getAppSession().getMessage("water.bill.search"));
			return false;
		} else if (MainetConstants.FlagS.equals(outPutData.getStatus())) {
			entity.setOrgid(outPutData.getApplicantDto().getOrgId());
			entity.setCsIdn(outPutData.getCsIdn());
			/* setApmApplicationId(outPutData.getApplicationNumber()); */
			setApplicantDetailDto(outPutData.getApplicantDto());
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "RPF")) {
				if (getApplicantDetailDto().getMobileNo() != null && !getApplicantDetailDto().getMobileNo().isEmpty()) {
					setMobileNo(getApplicantDetailDto().getMobileNo());
				}
				if (getApplicantDetailDto().getEmailId() != null && !getApplicantDetailDto().getEmailId().isEmpty()) {
					setEmailId(getApplicantDetailDto().getEmailId());
				}
			}
					
			
			setMessage(getAppSession().getMessage("water.bill.nodue.advancemsg"));
			this.setShowMode(MainetConstants.FlagY);
			return true;
		} else {
			throw new FrameworkException("Some problem while search for water bill payment");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.web.core.ui.model.AbstractFormModel#saveForm()
	 */
	@Override
	public boolean saveForm() {
		if ((getPayAmount() == null) || getPayAmount().equals(0D)) {
			addValidationError(getAppSession().getMessage("water.billPayment.amount"));
		}
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)) {
			if(getSurchargeAmount() > 0d || getCurSurchargeAmount() > 0d) {
				if (getPayAmount()!=null && getPayAmount() < (getSurchargeAmount() + getCurSurchargeAmount())) {
					addValidationError(getAppSession().getMessage("Amount should be greater than or equal to surcharge"));
				}
			}
		}
		
		if ((getCcnNumber() == null) || getCcnNumber().isEmpty()) {
		  addValidationError(getAppSession().getMessage("water.billPayment.ccnNumber")) ;
		}
		
		TbCsmrInfoDTO tbCsmrInfoDTO = ApplicationContextProvider.getApplicationContext()
				.getBean(NewWaterConnectionService.class).getConnectionDetailsById(getBillMas().getCsIdn());
		List<TbBillMas> billMasList = billGenerationService.getBillMasListByCsidn(getBillMas().getCsIdn(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		List<TbBillMas> unPaidBillMasList = billMasList.stream().filter(billMas -> billMas.getBmTotalBalAmount() > 0).collect(Collectors.toList());
		LookUp minimumAmntValidation = null;
		try {
			minimumAmntValidation = CommonMasterUtility.getValueFromPrefixLookUp("MCV", "BPV", UserSession.getCurrent().getOrganisation());
		}catch (Exception exception) {
		}
		TbBillMas lastBillMas = null;
		if(CollectionUtils.isNotEmpty(unPaidBillMasList)) {
			lastBillMas = unPaidBillMasList.get(unPaidBillMasList.size() - 1);
		}
		if (minimumAmntValidation != null && StringUtils.isNotBlank(minimumAmntValidation.getOtherField())
				&& StringUtils.equals(minimumAmntValidation.getOtherField(), MainetConstants.FlagY)
				&& CollectionUtils.isNotEmpty(unPaidBillMasList)) {
			final LookUp meterType = CommonMasterUtility.getNonHierarchicalLookUpObject(tbCsmrInfoDTO.getCsMeteredccn(),
					UserSession.getCurrent().getOrganisation());
			List<TbWtBillSchedule> currentBillSchedule = ApplicationContextProvider.getApplicationContext()
					.getBean(TbWtBillScheduleService.class).getBillScheduleByFinYearId(lastBillMas.getBmYear(),
							UserSession.getCurrent().getOrganisation().getOrgid(), meterType.getLookUpCode());

			LookUp billFrequencyLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
					currentBillSchedule.get(0).getCnsCpdid(), UserSession.getCurrent().getOrganisation());
			int billDurationMnths = monthsBetweenDates(lastBillMas.getBmFromdt(), lastBillMas.getBmTodt()) + 1;
			double oneMonthAmount = 0;
			if (billDurationMnths > 1) {
				oneMonthAmount = lastBillMas.getBmTotalAmount() / billDurationMnths;
			} else {
				oneMonthAmount = lastBillMas.getBmTotalAmount();
			}
			
			if(lastBillMas.getBmTotalBalAmount() < lastBillMas.getBmTotalAmount()) {
				oneMonthAmount = 0;
			}
			if (billFrequencyLookUp != null && StringUtils.equals(billFrequencyLookUp.getLookUpCode(), "12")
					&& getPayAmount() != null && getPayAmount() < getSurchargeAmount()) {
				addValidationError(getAppSession()
						.getMessage("Please pay the minimum  amount surcharge i.e., " + getSurchargeAmount()));
			} else if (billFrequencyLookUp != null && !StringUtils.equals(billFrequencyLookUp.getLookUpCode(), "12")
					&& getPayAmount() != null && getPayAmount() < (getSurchargeAmount() + oneMonthAmount)) {
				double minAmntDue = getSurchargeAmount() + oneMonthAmount;
				if (getSurchargeAmount() > 0) {
					addValidationError(getAppSession()
							.getMessage("Please pay the minimum  amount (surcharge + one month bill) i.e., "
									+ getSurchargeAmount() + "+ " + oneMonthAmount + "= " + minAmntDue));
				} else {
					addValidationError(getAppSession()
							.getMessage("Please pay the minimum  amount one month bill i.e., " + oneMonthAmount));
				}
			}
		}
		/*if (getPayAmount() != null && (getPayAmount() < getSurchargeAmount())) {
			addValidationError(getAppSession().getMessage("water.billpayment.validate.surcharge"));
		}*/
		
		final CommonChallanDTO offline = getOfflineDTO();
		final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
		offline.setOfflinePaymentText(modeDesc);
		
		final ServiceMaster service = serviceMaster.getServiceByShortName(PrefixConstants.NewWaterServiceConstants.BPW,
				UserSession.getCurrent().getOrganisation().getOrgid());
		//#133603 set service shortcode
		offline.setServiceCode(service.getSmShortdesc());
		//validateBean(offline, CommonOfflineMasterValidator.class);
		//User Story #97963-Customization for SKDCL-Manish Chaurasiya
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "RPF")) {
			validateBeanForMultiModePayment(offline);
		} else {
			validateBean(offline, CommonOfflineMasterValidator.class);
		}
		List<DocumentDetailsVO> docs = null;
		if(StringUtils.equals(getReceiptType(), MainetConstants.FlagM)) {
			if (getManualReceiptdate() == null) {
				addValidationError(getAppSession().getMessage("Please enter manual receipt date"));
			}else {
				
				if(CollectionUtils.isNotEmpty(billMasList) && billMasList.get(billMasList.size() - 1).getBmFromdt() != null) {
					if(Utility.compareDate(getManualReceiptdate(), billMasList.get(billMasList.size() - 1).getBmFromdt())) {
						addValidationError(getAppSession().getMessage("Manual receipt date should be greater than bill date"));
					}
				}
			}
			
			if (StringUtils.isEmpty(getManualReceiptNo())) {
				addValidationError(ApplicationSession.getInstance().getMessage("Please.enter.manual.receipt.number"));
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
                addValidationError(ApplicationSession.getInstance().getMessage("Please.upload.manual.receipt"));
            }
			
		}
		
		if (hasValidationErrors()) {
			return false;
		}
		if(StringUtils.equals(getReceiptType(), MainetConstants.FlagM)) {
			saveUploadedFiles(service, tbCsmrInfoDTO,docs);
		}
		if (getMessage() != null) {
			setAdvancePayment(MainetConstants.NewWaterServiceConstants.YES);
		}
		Map<Long, Double> details = new HashMap<>(0);
		final Map<Long, Long> billDetails = new HashMap<>(0);
		
		//final long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		if ((getAdvancePayment() == null) || getAdvancePayment().isEmpty()) {
			/*
			 * final List<TbBillMas> billMasList = billGenerationService
			 * .getBillMasterListByUniqueIdentifier(getBillMas().getCsIdn(), orgid);
			 * billMasterCommonService.updateBillData(billMasList,
			 * getPayAmount().doubleValue(), details, billDetails,
			 * UserSession.getCurrent().getOrganisation(), null);
			 */
		} else if (MainetConstants.NewWaterServiceConstants.YES.equals(getAdvancePayment())) {
			final Organisation org = UserSession.getCurrent().getOrganisation();
			final Long advanceTaxId = billMasterCommonService.getAdvanceTaxId(org.getOrgid(),
					MainetConstants.WATER_DEPARTMENT_CODE, null);
			details.put(advanceTaxId, getPayAmount());

		}
		setService(service);
		
		//Defect #145339
		if(CollectionUtils.isNotEmpty(billMasList) ) {
			String bmNo = billMasList.get(billMasList.size() - 1).getBmNo();
			String fromDt = new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(billMasList.get(billMasList.size() - 1).getBmFromdt());
			String toDt = new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(billMasList.get(billMasList.size() - 1).getBmTodt());
			Map<String, String> billYearDetailsMap = new HashMap<String, String>();
			billYearDetailsMap.put(bmNo, fromDt + MainetConstants.HYPHEN + toDt);
			if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL) && 
					(MainetConstants.WATER_DEPARTMENT_CODE.equals(service.getTbDepartment().getDpDeptcode()) || 
							MainetConstants.Dms.COMMON_DEPT.equals(service.getTbDepartment().getDpDeptcode())  )) {
				if (getAdvancePayment() != null && !getAdvancePayment().isEmpty() &&
						MainetConstants.NewWaterServiceConstants.YES.equals(getAdvancePayment())) {
					billYearDetailsMap.put(bmNo, StringUtils.EMPTY);
				}
			}
			offline.setBillYearDetails(billYearDetailsMap);
		}
        
		setChallanDToandSaveChallanData(offline, getApplicantDetailDto(), details, billDetails, tbCsmrInfoDTO);
		return true;
	}

	/**
	 * @param offline
	 */
	private void validateBeanForMultiModePayment(final CommonChallanDTO offline) {
			validateBean(offline, CommonOfflineMasterValidator.class);
	}

	private void setChallanDToandSaveChallanData(final CommonChallanDTO offline, final ApplicantDetailDTO applicantDto,
			final Map<Long, Double> details, final Map<Long, Long> billDetails,TbCsmrInfoDTO tbCsmrInfoDTO) {
		final UserSession session = UserSession.getCurrent();
		LookUp printManualReceiptYear = null;
		try {
			printManualReceiptYear = CommonMasterUtility.getValueFromPrefixLookUp("PMY", "WEV",
					UserSession.getCurrent().getOrganisation());
		} catch (Exception e) {
			LOGGER.error("No prefix found for WEV(PMY)");
		}
		/* if (getApmApplicationId() != 0) */
			/* offline.setApplNo(getApmApplicationId()); */
		offline.setAmountToPay(String.valueOf(getPayAmount()));
		offline.setEmailId(getApplicantDetailDto().getEmailId());
		offline.setMobileNumber(getApplicantDetailDto().getMobileNo());
		//Defect-117767
		String guardianName="C/O"+ MainetConstants.WHITE_SPACE+ getGuardianName();
		String userName = (applicantDto.getApplicantFirstName() == null ? MainetConstants.BLANK
				: applicantDto.getApplicantFirstName() + MainetConstants.WHITE_SPACE);
		userName += applicantDto.getApplicantMiddleName() == null ? MainetConstants.BLANK
				: applicantDto.getApplicantMiddleName() + MainetConstants.WHITE_SPACE;
		userName += applicantDto.getApplicantLastName() == null ? MainetConstants.BLANK
				: applicantDto.getApplicantLastName();
		userName += getGuardianName() == null ? MainetConstants.BLANK
				: guardianName;
		offline.setApplicantName(userName);
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED);

		offline.setDocumentUploaded(false);

		offline.setUniquePrimaryId(String.valueOf(getBillMas().getCsIdn()));
		offline.setPropNoConnNoEstateNoL(getAppSession().getMessage("water.ConnectionNo"));
		offline.setPropNoConnNoEstateNoV(tbCsmrInfoDTO.getCsCcn());
		offline.setPlotNo(tbCsmrInfoDTO.getHouseNumber());
		offline.setApplicantAddress(tbCsmrInfoDTO.getCsAdd());
		/* if (getApmApplicationId() == 0) { */
			WardZoneBlockDTO wardDto = new WardZoneBlockDTO();
			if (tbCsmrInfoDTO.getCodDwzid1() != null)
				wardDto.setAreaDivision1(tbCsmrInfoDTO.getCodDwzid1());
			if (tbCsmrInfoDTO.getCodDwzid2() != null)
				wardDto.setAreaDivision2(tbCsmrInfoDTO.getCodDwzid2());
			if (tbCsmrInfoDTO.getCodDwzid3() != null)
				wardDto.setAreaDivision3(tbCsmrInfoDTO.getCodDwzid3());
			if (tbCsmrInfoDTO.getCodDwzid4() != null)
				wardDto.setAreaDivision4(tbCsmrInfoDTO.getCodDwzid4());
			if (tbCsmrInfoDTO.getCodDwzid5() != null)
				wardDto.setAreaDivision5(tbCsmrInfoDTO.getCodDwzid5());
			offline.setDwzDTO(wardDto);
		/* } */
		String tarrifCate = null;
		final List<LookUp> categorylist = CommonMasterUtility.getLevelData("TRF", MainetConstants.ENGLISH,
				UserSession.getCurrent().getOrganisation());
		for (final LookUp lookup : categorylist) {
		
			if (lookup.getLookUpId() == tbCsmrInfoDTO.getTrmGroup1()) {
				tarrifCate = lookup.getLookUpDesc();
			}
		}
	
		offline.setUsageType(tarrifCate);

		if ((getAdvancePayment() != null) && getAdvancePayment().equals(MainetConstants.NewWaterServiceConstants.YES)) {
			offline.setPaymentCategory(MainetConstants.MENU.A);
		} else {
			offline.setPaymentCategory(MainetConstants.NewWaterServiceConstants.BILL_SCHEDULE_DATE);
		}
		offline.setUserId(session.getEmployee().getEmpId());
		offline.setOrgId(session.getOrganisation().getOrgid());
		offline.setLangId(session.getLanguageId());
		offline.setLgIpMac(session.getEmployee().getEmppiservername());
		offline.setLoggedLocId(session.getLoggedLocId());
		offline.setFaYearId(session.getFinYearId());
		offline.setFinYearStartDate(session.getFinStartDate());
		if(getManualReceiptdate() != null && printManualReceiptYear != null && org.apache.commons.lang.StringUtils.equals(MainetConstants.FlagY, printManualReceiptYear.getOtherField())) {
        	Long manualYearId = ApplicationContextProvider.getApplicationContext().getBean(IFinancialYearService.class).getFinanceYearId(getManualReceiptdate());
        	offline.setFaYearId(String.valueOf(manualYearId));
        }
		offline.setEmpType(session.getEmployee().getEmplType());
		offline.setFinYearEndDate(session.getFinEndDate());
		offline.setManualReceiptNo(getManualReceiptNo());
		offline.setManualReeiptDate(getManualReceiptdate());
		if ((details != null) && !details.isEmpty()) {
			offline.setFeeIds(details);
		}
		if ((billDetails != null) && !billDetails.isEmpty()) {
			offline.setBillDetIds(billDetails);
		}
		// offline.setUniquePrimaryIdName(getAppSession().getMessage("water.ConnectionNo"));
		if(StringUtils.isNotBlank(tbCsmrInfoDTO.getCsOldccn())) {
		offline.setReferenceNo(tbCsmrInfoDTO.getCsOldccn());
		}
		
		LookUp printPropNo = null;
		try {
			printPropNo = CommonMasterUtility.getValueFromPrefixLookUp("PPN", "WEV", UserSession.getCurrent().getOrganisation());
		}catch (Exception exception) {
			LOGGER.error("No Prefix founf for WEV(PPN)");
		}
		//D#148057-to show counter details on payment receipt
		try {
			CFCSchedulingCounterDet counterDet = null;
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "SKDCL")) {
				counterDet = tbCfcservice.getCounterDetByEmpId(UserSession.getCurrent().getEmployee().getEmpId(),
						UserSession.getCurrent().getOrganisation().getOrgid());
				if (counterDet != null) {
					tbCfcservice.setRecieptCfcAndCounterCount(counterDet);
					offline.setCfcCenter(counterDet.getCollcntrno());
					offline.setCfcCounterNo(counterDet.getCounterno());
				}

			}
		} catch (Exception e) {
			logger.info("Exception occure while seting the Counter scheduling info in Water bill payment model:" + e);
		}
	
		if (printPropNo != null && StringUtils.equals(printPropNo.getOtherField(), MainetConstants.FlagY)
				&& tbCsmrInfoDTO != null && StringUtils.isNotBlank(tbCsmrInfoDTO.getPropertyNo())) {
			offline.setReferenceNo(tbCsmrInfoDTO.getPropertyNo());
		}
		
		if(tbCsmrInfoDTO.getArv()!=null && !tbCsmrInfoDTO.getArv().equals(MainetConstants.BLANK)) {
			offline.setPdRv(Double.valueOf(tbCsmrInfoDTO.getArv()));
		}
		if(tbCsmrInfoDTO.getCsOflatno()!=null && !tbCsmrInfoDTO.getCsOflatno().equals(MainetConstants.BLANK)) {
			offline.setPlotNo(tbCsmrInfoDTO.getCsOflatno());
		}		
		offline.setApplicantFullName(userName);
		//Defect #123786-Manish Chauraisya
		offline.setPayeeName(userName);
		if(org.apache.commons.lang.StringUtils.isNotBlank(payeeName)) {
	        	 offline.setPayeeName(payeeName);
	     }
		offline.setServiceId(getService().getSmServiceId());
		offline.setDeptId(getService().getTbDepartment().getDpDeptid());
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), session.getOrganisation())
				.getLookUpCode());
		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.OFFLINE)) {
			final ChallanMaster master = iChallanService.InvokeGenerateChallan(offline);
			offline.setChallanValidDate(master.getChallanValiDate());
			offline.setChallanNo(master.getChallanNo());
			setSuccessMessage(getAppSession().getMessage("challan.msg"));
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
			final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline, null);
			printDto.setDate(MainetConstants.BLANK);// to avoid challan date as application/Loi Date in challan print
			setReceiptDTO(printDto);
			
			//US#102672					 // pushing document to DMS
			/*String URL = ServiceEndpoints.BIRT_REPORT_DMS_URL + "="
					+ ApplicationSession.getInstance().getMessage("birtName.billPayment")
					+ "&__format=pdf&RP_ORGID=" + offline.getOrgId() + "&RP_RCPTID=" + printDto.getReceiptId();
			Utility.pushDocumentToDms(URL, tbCsmrInfoDTO.getCsCcn() , MainetConstants.DEPT_SHORT_NAME.WATER,fileUploadService);*/
			
			setSuccessMessage(getAppSession().getMessage("water.receipt"));
			
		}
		

		// Manual Receipt Entry Mobile No Add/Update in case of SKDCL-Manish Chaurasiya
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "RPF")
				&& applicantDetailDto != null
				&& (StringUtils.isNotBlank(getApplicantDetailDto().getMobileNo())
						&& !StringUtils.equals(getMobileNo(), getApplicantDetailDto().getMobileNo())
						|| (StringUtils.isNotBlank(getApplicantDetailDto().getEmailId())
								&& !StringUtils.equals(getEmailId(), getApplicantDetailDto().getEmailId())))) {
			
			newWaterConnectionService.updateMobileNumberOfConMaster(tbCsmrInfoDTO.getCsIdn(),
					getApplicantDetailDto().getMobileNo(), offline.getOrgId(),getApplicantDetailDto().getEmailId());
		}
		setOfflineDTO(offline);
	}
	
	private void saveUploadedFiles(ServiceMaster service,TbCsmrInfoDTO tbCsmrInfoDTO,List<DocumentDetailsVO> documents ) {
		
			/*
			 * List<DocumentDetailsVO> documents = getCheckList(); documents
			 * =fileUploadService.prepareFileUpload(documents);
			 */
	        RequestDTO requestDTO = new RequestDTO();
	        requestDTO.setApplicationId(tbCsmrInfoDTO.getCsIdn());
	        requestDTO.setReferenceId(tbCsmrInfoDTO.getCsCcn());
	        requestDTO.setServiceId(service.getSmServiceId());
	        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
	        requestDTO.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
	        fileUploadService.doFileUpload(documents, requestDTO);
		
	}
	
	private void validateInput() {	
		
		if (getManualReceiptdate() == null) {
		addValidationError(getAppSession().getMessage("water.manual.receipt.date"));
	} else {
		setManualReceiptDateString(Utility.dateToString(getManualReceiptdate()));

		TbKCsmrInfoMH consumerDto = null;
		List<TbBillMas> billMasList = null;
		if (StringUtils.isNotBlank(getCcnNumber())) {
			consumerDto = tbWtBillMasJpaRepository.getCsIdnByConnectionNumber(getCcnNumber(),
					UserSession.getCurrent().getOrganisation().getOrgid());
		} else if ((consumerDto == null)
				&& (StringUtils.isNotBlank(getOldccnNumber()))) {
			consumerDto = tbWtBillMasJpaRepository.getCsIdnByOldConnectionNumber(getOldccnNumber(),
					UserSession.getCurrent().getOrganisation().getOrgid());
		}
		if (consumerDto != null) {
			billMasList = billGenerationService.getBillMasListByCsidn(consumerDto.getCsIdn(),
					UserSession.getCurrent().getOrganisation().getOrgid());
		}

		if (CollectionUtils.isNotEmpty(billMasList)
				&& billMasList.get(billMasList.size() - 1).getBmFromdt() != null) {
			if (Utility.compareDate(getManualReceiptdate(),
					billMasList.get(billMasList.size() - 1).getBmFromdt())) {
				addValidationError(getAppSession()
						.getMessage("Manual receipt date should be greater than or equal to last bill date"));
			} else {
				if (Utility.compareDate(new Date(), getManualReceiptdate())) {
					addValidationError(
							getAppSession().getMessage("Manual receipt date cannot be greater than current date"));
				}
				TbServiceReceiptMasEntity receipt = ApplicationContextProvider.getApplicationContext()
						.getBean(IReceiptEntryService.class)
						.getLatestReceiptDetailByAddRefNo(UserSession.getCurrent().getOrganisation().getOrgid(),
								String.valueOf(consumerDto.getCsIdn()));

				if (receipt != null && (receipt.getRmDate().after(getManualReceiptdate()))) {
					addValidationError(getAppSession().getMessage(
							"Manual receipt date should be greater than or equal to last receipt date"));
				}
			}
		}

	}}

	 static int monthsBetweenDates(Date fromDate, Date toDate) {
	        Calendar startCalendar = Calendar.getInstance();
	        startCalendar.setTime(fromDate);
	        Calendar endCalendar = Calendar.getInstance();
	        endCalendar.setTime(toDate);

	        int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
	        int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
	        return diffMonth;
	    }
	 
	/**
	 * @return the billMas
	 */
	public TbBillMas getBillMas() {
		return billMas;
	}

	/**
	 * @param billMas
	 *            the billMas to set
	 */
	public void setBillMas(final TbBillMas billMas) {
		this.billMas = billMas;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(final Double payAmount) {
		this.payAmount = payAmount;
	}

	public ServiceMaster getService() {
		return service;
	}

	public void setService(final ServiceMaster service) {
		this.service = service;
	}

	public String getCcnNumber() {
		return ccnNumber;
	}

	public void setCcnNumber(final String ccnNumber) {
		this.ccnNumber = ccnNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public String getAdvancePayment() {
		return advancePayment;
	}

	public void setAdvancePayment(final String advancePayment) {
		this.advancePayment = advancePayment;
	}

	public BillTaxDTO getRebateAndAdvanceDto() {
		return rebateAndAdvanceDto;
	}

	public void setRebateAndAdvanceDto(final BillTaxDTO rebateAndAdvanceDto) {
		this.rebateAndAdvanceDto = rebateAndAdvanceDto;
	}

	public double getRebateAmount() {
		return rebateAmount;
	}

	public void setRebateAmount(final double rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	public double getExcessAmount() {
		return excessAmount;
	}

	public void setExcessAmount(final double excessAmount) {
		this.excessAmount = excessAmount;
	}

	public List<WaterBillTaxDTO> getTaxes() {
		return taxes;
	}

	public void setTaxes(final List<WaterBillTaxDTO> taxes) {
		this.taxes = taxes;
	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}

	public double getSurchargeAmount() {
		return surchargeAmount;
	}

	public void setSurchargeAmount(double surchargeAmount) {
		this.surchargeAmount = surchargeAmount;
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

	public Date getManualReceiptdate() {
		return manualReceiptdate;
	}

	public void setManualReceiptdate(Date manualReceiptdate) {
		this.manualReceiptdate = manualReceiptdate;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public String getOldccnNumber() {
		return oldccnNumber;
	}

	public void setOldccnNumber(String oldccnNumber) {
		this.oldccnNumber = oldccnNumber;
	}

	public WaterDataEntrySearchDTO getSearchDTO() {
		return searchDTO;
	}

	public void setSearchDTO(WaterDataEntrySearchDTO searchDTO) {
		this.searchDTO = searchDTO;
	}
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getShowMode() {
		return showMode;
	}

	public void setShowMode(String showMode) {
		this.showMode = showMode;
	}

	public String getManualReceiptDateString() {
		return manualReceiptDateString;
	}

	public void setManualReceiptDateString(String manualReceiptDateString) {
		this.manualReceiptDateString = manualReceiptDateString;
	}

	public String getRebateMessage() {
		return rebateMessage;
	}

	public void setRebateMessage(String rebateMessage) {
		this.rebateMessage = rebateMessage;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public double getCurSurchargeAmount() {
		return curSurchargeAmount;
	}

	public void setCurSurchargeAmount(double curSurchargeAmount) {
		this.curSurchargeAmount = curSurchargeAmount;
	}

	public String getGuardianName() {
		return guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public double getAdjustmentEntry() {
		return adjustmentEntry;
	}

	public void setAdjustmentEntry(double adjustmentEntry) {
		this.adjustmentEntry = adjustmentEntry;
	}

	public String getSkdclEnv() {
		return skdclEnv;
	}

	public void setSkdclEnv(String skdclEnv) {
		this.skdclEnv = skdclEnv;
	}
	
}
