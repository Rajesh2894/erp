/**
 * 
 */
package com.abm.mainet.adh.ui.model;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.AgencyRegistrationRequestDto;
import com.abm.mainet.adh.dto.AgencyRegistrationResponseDto;
import com.abm.mainet.adh.service.IAgencyRegistrationService;
import com.abm.mainet.adh.ui.validator.AgencyRegistrationFormValidator;
import com.abm.mainet.adh.ui.validator.AgencyRegistrationValidatorOwner;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.IDepartmentService;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.StringUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.dms.domain.CFCAttachment;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author cherupelli.srikanth
 * @since 17 October 2019
 */
@Component
@Scope("session")
public class AgencyRegistrationFormModel extends AbstractFormModel {

	private static final long serialVersionUID = 1112297664900849159L;

	private static Logger log = Logger.getLogger(AgencyRegistrationFormModel.class);
	@Autowired
	IAgencyRegistrationService agencyRegistrationService;

	@Resource
	IFileUploadService fileUpload;

	@Autowired
	private IChallanService challanService;

	private AgencyRegistrationRequestDto agencyRequestDto = new AgencyRegistrationRequestDto();

	private List<DocumentDetailsVO> checkList = new ArrayList<>();

	private List<CFCAttachment> documentList = new ArrayList<>();

	private List<AdvertiserMasterDto> masterDtoList = new ArrayList<>();

	private PortalService service = new PortalService();

	private LookUp agenctCategoryLookUp = new LookUp();
	
	private AdvertiserMasterDto advertiserMasterDto = new AdvertiserMasterDto();

	private Long applicationId;

	private String applicantName;

	private String checkListApplFlag;

	private String applicationchargeApplFlag;

	private Long licMaxTenureDays;

	private Long licMinTenureDays;

	private double amountToPay;

	private String formDisplayFlag;

	private String paymentMode;

	private String payableFlag;
	
	private String saveMode = "C";
	
	private String ownershipPrefix;
	
    private String viewMode;
    
    private String openMode;

	public boolean saveForm() throws JsonParseException, JsonMappingException, IOException {
		boolean status = false;
		List<DocumentDetailsVO> documents = getFileUploadList(getCheckList(),
				FileUploadUtility.getCurrent().getFileMap());
		validateInputs(documents);
		if(status==true) {
		validateBean(getAgencyRequestDto().getMasterDto(), AgencyRegistrationFormValidator.class);
		}else {
			validateBean(getAgencyRequestDto().getMasterDto(), AgencyRegistrationValidatorOwner.class);
		}
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
		requestDto.setServiceId(getService().getServiceId());
		requestDto.getMasterDto().setAgencyStatus(MainetConstants.AdvertisingAndHoarding.FLAGT);
		requestDto.getMasterDto().setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		requestDto.getMasterDto().setCreatedDate(new Date());
		requestDto.getMasterDto().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		requestDto.getMasterDto().setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		requestDto.setApplicantDetailDto(getApplicantDetailDto());
		if(requestDto.getMasterDto().getAgencyCategory()== null) {
			requestDto.getMasterDto().setAgencyCategory(getAgenctCategoryLookUp().getLookUpId());
		}
		AgencyRegistrationResponseDto agencyRespondeDto = agencyRegistrationService
				.saveAgencyRegistrationData(requestDto);
		if (agencyRespondeDto.getApplicationId() > 0) {
			getAgencyRequestDto().setApplicationId(agencyRespondeDto.getApplicationId());
			if (!requestDto.isFree()) {
				requestDto.getMasterDto().setApmApplicationId(agencyRespondeDto.getApplicationId());
				setAndSaveChallanDto(getOfflineDTO(), requestDto.getMasterDto());
			}
			status = true;
		}

		return status;
	}

	@Override
	public void redirectToPayDetails(final HttpServletRequest httpServletRequest,
			final PaymentRequestDTO payURequestDTO) {
		payURequestDTO.setUdf1(getService().getServiceId().toString());
		payURequestDTO.setUdf2(getApplicationId().toString());
		final ApplicantDetailDTO applicant = getApplicantDetailDto();
		payURequestDTO.setUdf3("CitizenHome.html");
		payURequestDTO.setUdf5(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_SHORT_CODE);
		payURequestDTO.setUdf7(getApplicationId().toString());
		payURequestDTO.setServiceId(getService().getServiceId());
		if (getApplicationId() != 0) {
			payURequestDTO.setApplicationId(getApplicationId().toString());
		}

		if (getOfflineDTO().getAmountToShow() != null) {
			payURequestDTO.setDueAmt(new BigDecimal(getOfflineDTO().getAmountToShow()));
		}
		payURequestDTO.setEmail(applicant.getEmailId());
		payURequestDTO.setMobNo(applicant.getMobileNo());
		String userName = (applicant.getApplicantFirstName() == null ? MainetConstants.BLANK
				: applicant.getApplicantFirstName().trim() + MainetConstants.WHITE_SPACE);
		userName += applicant.getApplicantMiddleName() == null ? MainetConstants.BLANK
				: applicant.getApplicantMiddleName().trim() + MainetConstants.WHITE_SPACE;
		userName += applicant.getApplicantLastName() == null ? MainetConstants.BLANK
				: applicant.getApplicantLastName().trim();
		payURequestDTO.setApplicantName(userName.trim());
		payURequestDTO.setUdf10(getService().getPsmDpDeptid().toString());
		if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
			payURequestDTO.setServiceName(getService().getServiceName());
		} else {
			payURequestDTO.setServiceName(getService().getServiceNameReg());
		}
		//Added for The Urban Pay Issue
		//payURequestDTO.setOrgId(applicant.getOrgId());
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

	private void setAgencyRegistrationData(AgencyRegistrationRequestDto requestDto) {

		Department department = ApplicationContextProvider.getApplicationContext().getBean(IDepartmentService.class)
				.getDepartment(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE, MainetConstants.MENU.A);
		requestDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
		requestDto.setDeptId(department.getDpDeptid());
		requestDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		requestDto.getMasterDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDto.getMasterDto().setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
		requestDto.setServiceId(getService().getServiceId());
		requestDto.getMasterDto().setAgencyStatus(MainetConstants.MENU.A);
		requestDto.getMasterDto().setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		requestDto.getMasterDto().setCreatedDate(new Date());
		requestDto.getMasterDto().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		requestDto.setApplicantDetailDto(getApplicantDetailDto());
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
		setApplicationId(masterDto.getApmApplicationId());
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

	public AgencyRegistrationRequestDto getAgencyRequestDto() {
		return agencyRequestDto;
	}

	public void setAgencyRequestDto(AgencyRegistrationRequestDto agencyRequestDto) {
		this.agencyRequestDto = agencyRequestDto;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	/**
	 * @return the masterDtoList
	 */
	public List<AdvertiserMasterDto> getMasterDtoList() {
		return masterDtoList;
	}

	/**
	 * @param masterDtoList
	 *            the masterDtoList to set
	 */
	public void setMasterDtoList(List<AdvertiserMasterDto> masterDtoList) {
		this.masterDtoList = masterDtoList;
	}

	public PortalService getService() {
		return service;
	}

	public void setService(PortalService service) {
		this.service = service;
	}

	/**
	 * @return the agenctCategoryLookUp
	 */
	public LookUp getAgenctCategoryLookUp() {
		return agenctCategoryLookUp;
	}

	/**
	 * @param agenctCategoryLookUp
	 *            the agenctCategoryLookUp to set
	 */
	public void setAgenctCategoryLookUp(LookUp agenctCategoryLookUp) {
		this.agenctCategoryLookUp = agenctCategoryLookUp;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getCheckListApplFlag() {
		return checkListApplFlag;
	}

	public void setCheckListApplFlag(String checkListApplFlag) {
		this.checkListApplFlag = checkListApplFlag;
	}

	public String getApplicationchargeApplFlag() {
		return applicationchargeApplFlag;
	}

	public void setApplicationchargeApplFlag(String applicationchargeApplFlag) {
		this.applicationchargeApplFlag = applicationchargeApplFlag;
	}

	/**
	 * @return the licMaxTenureDays
	 */
	public Long getLicMaxTenureDays() {
		return licMaxTenureDays;
	}

	/**
	 * @param licMaxTenureDays
	 *            the licMaxTenureDays to set
	 */
	public void setLicMaxTenureDays(Long licMaxTenureDays) {
		this.licMaxTenureDays = licMaxTenureDays;
	}

	/**
	 * @return the licMinTenureDays
	 */
	public Long getLicMinTenureDays() {
		return licMinTenureDays;
	}

	/**
	 * @param licMinTenureDays
	 *            the licMinTenureDays to set
	 */
	public void setLicMinTenureDays(Long licMinTenureDays) {
		this.licMinTenureDays = licMinTenureDays;
	}

	/**
	 * @return the formDisplayFlag
	 */
	public String getFormDisplayFlag() {
		return formDisplayFlag;
	}

	/**
	 * @param formDisplayFlag
	 *            the formDisplayFlag to set
	 */
	public void setFormDisplayFlag(String formDisplayFlag) {
		this.formDisplayFlag = formDisplayFlag;
	}
	
	

	/**
	 * @return the amountToPay
	 */
	public double getAmountToPay() {
		return amountToPay;
	}

	/**
	 * @param amountToPay
	 *            the amountToPay to set
	 */
	public void setAmountToPay(double amountToPay) {
		this.amountToPay = amountToPay;
	}

	/**
	 * @return the paymentMode
	 */
	public String getPaymentMode() {
		return paymentMode;
	}

	/**
	 * @param paymentMode
	 *            the paymentMode to set
	 */
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	/**
	 * @return the payableFlag
	 */
	public String getPayableFlag() {
		return payableFlag;
	}

	/**
	 * @param payableFlag
	 *            the payableFlag to set
	 */
	public void setPayableFlag(String payableFlag) {
		this.payableFlag = payableFlag;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}
	
	public AdvertiserMasterDto getAdvertiserMasterDto() {
        return advertiserMasterDto;
    }

    public void setAdvertiserMasterDto(AdvertiserMasterDto advertiserMasterDto) {
        this.advertiserMasterDto = advertiserMasterDto;
    }
	
	public String getOwnershipPrefix() {
        return ownershipPrefix;
    }

    public void setOwnershipPrefix(String ownershipPrefix) {
        this.ownershipPrefix = ownershipPrefix;
    }
    
    public String getViewMode() {
        return viewMode;
    }

    public void setViewMode(String viewMode) {
        this.viewMode = viewMode;
    }
    
    
    public String getOpenMode() {
        return openMode;
    }

    public void setOpenMode(String openMode) {
        this.openMode = openMode;
    }
	
	

}
