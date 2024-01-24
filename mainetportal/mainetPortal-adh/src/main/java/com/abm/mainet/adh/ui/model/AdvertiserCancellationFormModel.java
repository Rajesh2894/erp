package com.abm.mainet.adh.ui.model;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.AgencyRegistrationRequestDto;
import com.abm.mainet.adh.dto.AgencyRegistrationResponseDto;
import com.abm.mainet.adh.service.IAgencyRegistrationService;
import com.abm.mainet.adh.ui.validator.AgencyRegistrationFormValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.StringUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.domain.CFCAttachment;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 * @author Sharvan kumar Mandal
 * @since 29 April 2021
 */

@Component
@Scope("session")
public class AdvertiserCancellationFormModel extends AbstractFormModel {

	private static final long serialVersionUID = 8187986486520652402L;

	private static Logger log = Logger.getLogger(AdvertiserCancellationFormModel.class);

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	IAgencyRegistrationService agencyRegistrationService;

	@Autowired
	private IChallanService challanService;

	@Autowired
	private IPortalServiceMasterService iPortalServiceMasterService;


	@Autowired
	ISMSAndEmailService iSMSAndEmailService;

	@Autowired
	private ICommonBRMSService iCommonBRMSService;

	// Added new Model class for User Story 112154 
	
	private AdvertiserMasterDto advertiserDto = new AdvertiserMasterDto();

	private AgencyRegistrationRequestDto agencyRequestDto = new AgencyRegistrationRequestDto();

	private List<AdvertiserMasterDto> advertiserMasterDtoList = new ArrayList<>();

	private List<String[]> agencyLicNoAndNameList = new ArrayList<>();

	private List<AdvertiserMasterDto> masterDtoList = new ArrayList<>();

	private PortalService service = new PortalService();

	private List<DocumentDetailsVO> checkList = new ArrayList<>();

	private String paymentCheck;

	private String checklistCheck;

	private String checkListApplFlag;

	private String appChargeFlag;

	private BigDecimal totalApplicationFee;

	private String applicationchargeApplFlag;

	private LookUp agenctCategoryLookUp = new LookUp();

	private String payableFlag;

	private double amountToPay;

	private String paymentMode;

	private String saveMode = "C";

	private String formDisplayFlag;

	public LookUp getAgenctCategoryLookUp() {
		return agenctCategoryLookUp;
	}

	public void setAgenctCategoryLookUp(LookUp agenctCategoryLookUp) {
		this.agenctCategoryLookUp = agenctCategoryLookUp;
	}

	public List<AdvertiserMasterDto> getAdvertiserMasterDtoList() {
		return advertiserMasterDtoList;
	}

	public void setAdvertiserMasterDtoList(List<AdvertiserMasterDto> advertiserMasterDtoList) {
		this.advertiserMasterDtoList = advertiserMasterDtoList;
	}

	public List<String[]> getAgencyLicNoAndNameList() {
		return agencyLicNoAndNameList;
	}

	public void setAgencyLicNoAndNameList(List<String[]> agencyLicNoAndNameList) {
		this.agencyLicNoAndNameList = agencyLicNoAndNameList;
	}

	// private PortalService service = new PortalService();

	public AdvertiserMasterDto getAdvertiserDto() {
		return advertiserDto;
	}

	public void setAdvertiserDto(AdvertiserMasterDto advertiserDto) {
		this.advertiserDto = advertiserDto;
	}

	public PortalService getService() {
		return service;
	}

	public void setService(PortalService service) {
		this.service = service;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public AgencyRegistrationRequestDto getAgencyRequestDto() {
		return agencyRequestDto;
	}

	public void setAgencyRequestDto(AgencyRegistrationRequestDto agencyRequestDto) {
		this.agencyRequestDto = agencyRequestDto;
	}

	public String getPaymentCheck() {
		return paymentCheck;
	}

	public void setPaymentCheck(String paymentCheck) {
		this.paymentCheck = paymentCheck;
	}

	public String getChecklistCheck() {
		return checklistCheck;
	}

	public void setChecklistCheck(String checklistCheck) {
		this.checklistCheck = checklistCheck;
	}

	public String getCheckListApplFlag() {
		return checkListApplFlag;
	}

	public void setCheckListApplFlag(String checkListApplFlag) {
		this.checkListApplFlag = checkListApplFlag;
	}

	public String getAppChargeFlag() {
		return appChargeFlag;
	}

	public void setAppChargeFlag(String appChargeFlag) {
		this.appChargeFlag = appChargeFlag;
	}

	public BigDecimal getTotalApplicationFee() {
		return totalApplicationFee;
	}

	public void setTotalApplicationFee(BigDecimal totalApplicationFee) {
		this.totalApplicationFee = totalApplicationFee;
	}

	public String getApplicationchargeApplFlag() {
		return applicationchargeApplFlag;
	}

	public void setApplicationchargeApplFlag(String applicationchargeApplFlag) {
		this.applicationchargeApplFlag = applicationchargeApplFlag;
	}

	public boolean saveForm() {

		boolean status = false;
		List<DocumentDetailsVO> documents = getFileUploadList(getCheckList(),
				FileUploadUtility.getCurrent().getFileMap());
		validateInputs(documents);
		validateBean(getAgencyRequestDto().getMasterDto(), AgencyRegistrationFormValidator.class);
		if (hasValidationErrors()) {
			return false;
		}
		AgencyRegistrationRequestDto requestDto = getAgencyRequestDto();

		requestDto.setDocumentList(documents);

		requestDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
		requestDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		requestDto.getMasterDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDto.getMasterDto().setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
		// requestDto.setDeptId(department.getDpDeptid());
		requestDto.setServiceId(getService().getServiceId());
		requestDto.getMasterDto().setAgencyStatus(MainetConstants.AdvertisingAndHoarding.FLAGT);
		requestDto.getMasterDto().setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		requestDto.getMasterDto().setCreatedDate(new Date());
		requestDto.getMasterDto().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		requestDto.setApplicantDetailDto(getApplicantDetailDto());

		AgencyRegistrationResponseDto agencyRespondeDto = agencyRegistrationService
				.saveAndUpdateApplication(requestDto);
		if (agencyRespondeDto.getApplicationId() > 0) {
			getAgencyRequestDto().setApplicationId(agencyRespondeDto.getApplicationId());
			requestDto.getMasterDto().setApmApplicationId(agencyRespondeDto.getApplicationId());
			// setAndSaveChallanDto(getOfflineDTO(), requestDto.getMasterDto());
			/*
			 * if (requestDto.isFree()) {
			 * requestDto.getMasterDto().setApmApplicationId(agencyRespondeDto.
			 * getApplicationId()); setAndSaveChallanDto(getOfflineDTO(),
			 * requestDto.getMasterDto()); }
			 */
			status = true;
		}

		return status;
		
	}

	

	public boolean validateInputs(final List<DocumentDetailsVO> dto) {
		boolean flag = false;
		if ((dto != null) && !dto.isEmpty()) {
			for (final DocumentDetailsVO doc : dto) {
				if (doc.getCheckkMANDATORY().equals(MainetConstants.FlagY)) {
					if (doc.getDocumentByteCode() == null) {
						addValidationError(
								ApplicationSession.getInstance().getMessage("adh.upload.mandatory.documents"));
						flag = true;
						break;
					}
				}
			}

		}

		return flag;
	}

	public void getCheckListFromBrms() {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		// Long orgId =6L;
		final WSRequestDTO initRequestDto = new WSRequestDTO();
		initRequestDto.setModelName(MainetConstants.AdvertisingAndHoarding.CHECK_LIST);
		WSResponseDTO response = iCommonBRMSService.initializeModel(initRequestDto);
		List<DocumentDetailsVO> checkListList = new ArrayList<>();
		if (response.getWsStatus() != null
				&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			List<Object> checklist = JersyCall.castResponse(response, CheckListModel.class, 0);
			CheckListModel checkListModel = (CheckListModel) checklist.get(0);
			checkListModel.setOrgId(orgId);
			checkListModel.setServiceCode(MainetConstants.AdvertisingAndHoarding.AGENCY_CAN_SHORT_CODE);
			final WSRequestDTO checkRequestDto = new WSRequestDTO();
			checkRequestDto.setDataModel(checkListModel);
			checkListList = iCommonBRMSService.getChecklist(checkListModel);
			if (checkListList != null && !checkListList.isEmpty()) {
				Long fileSerialNo = 1L;
				for (final DocumentDetailsVO docSr : checkListList) {
					docSr.setDocumentSerialNo(fileSerialNo);
					fileSerialNo++;
				}
				setCheckList(checkListList);

			}
		}
	}

	private void setAndSaveChallanDto(CommonChallanDTO offline, AdvertiserMasterDto masterDto) {
		offline.setOfflinePaymentText(getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode());
		offline.setApplNo(masterDto.getApmApplicationId());

		offline.setAmountToPay(Double.toString(getAmountToPay()));
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);

		if ((getCheckList() != null) && (getCheckList().size() > 0)) {
			offline.setDocumentUploaded(true);
		} else {
			offline.setDocumentUploaded(false);
		}
		offline.setServiceId(getService().getServiceId());

		offline.setApplicantName((getApplicantDetailDto().getApplicantFirstName() != null
				? getApplicantDetailDto().getApplicantFirstName() + " "
				: MainetConstants.BLANK)
				+ (getApplicantDetailDto().getApplicantMiddleName() != null
						? getApplicantDetailDto().getApplicantMiddleName() + " "
						: MainetConstants.WHITE_SPACE)
				+ (getApplicantDetailDto().getApplicantLastName() != null
						? getApplicantDetailDto().getApplicantLastName()
						: MainetConstants.BLANK));
		offline.setMobileNumber(getApplicantDetailDto().getMobileNo());
		offline.setEmailId(getApplicantDetailDto().getEmailId());
		for (final ChargeDetailDTO chargeDetailDTO : getChargesInfo()) {
			offline.getFeeIds().put(chargeDetailDTO.getChargeCode(), chargeDetailDTO.getChargeAmount());
		}
		offline.setDeptId(getService().getPsmDpDeptid());
		if ((offline.getOnlineOfflineCheck() != null) && offline.getOnlineOfflineCheck().equals("Y")) {
			setPaymentMode("Online");
		}
		if ((offline.getOnlineOfflineCheck() != null) && offline.getOnlineOfflineCheck().equals("N")) {
			setPaymentMode(CommonMasterUtility.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(),
					UserSession.getCurrent().getOrganisation()).getLookUpDesc());
			offline = challanService.generateChallanNumber(offline);
			setOfflineDTO(offline);
		}
	}

	public List<CFCAttachment> preparePreviewOfFileUpload(final List<CFCAttachment> downloadDocs,
			List<DocumentDetailsVO> docs) {
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			long count = 1;
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final List<File> list = new ArrayList<>(entry.getValue());
				for (final File file : list) {
					try {
						CFCAttachment c = new CFCAttachment();
						String path = StringUtility.staticStringBeforeChar(MainetConstants.FILE_PATH_SEPARATOR,
								file.getPath());
						c.setAttPath(path);
						c.setAttFname(file.getName());
						c.setClmSrNo(count);
						docs.stream().filter(doc -> doc.getDocumentSerialNo().equals(entry.getKey() + 1))
								.forEach(doc -> {
									c.setClmDesc(doc.getDoc_DESC_ENGL());
									c.setClmId(doc.getDocumentId());
									c.setClmDesc(doc.getDoc_DESC_Mar());
									c.setClmDesc(doc.getDoc_DESC_ENGL());

								});
						count++;
						downloadDocs.add(c);
					} catch (final Exception e) {
						log.error("Exception has been occurred in file byte to string conversions", e);
					}
				}
			}
		}

		return downloadDocs;
	}

	public double getAmountToPay() {
		return amountToPay;
	}

	public void setAmountToPay(double amountToPay) {
		this.amountToPay = amountToPay;
	}

	public String getPayableFlag() {
		return payableFlag;
	}

	public void setPayableFlag(String payableFlag) {
		this.payableFlag = payableFlag;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getFormDisplayFlag() {
		return formDisplayFlag;
	}

	public void setFormDisplayFlag(String formDisplayFlag) {
		this.formDisplayFlag = formDisplayFlag;
	}

	public List<AdvertiserMasterDto> getMasterDtoList() {
		return masterDtoList;
	}

	public void setMasterDtoList(List<AdvertiserMasterDto> masterDtoList) {
		this.masterDtoList = masterDtoList;
	}

}
