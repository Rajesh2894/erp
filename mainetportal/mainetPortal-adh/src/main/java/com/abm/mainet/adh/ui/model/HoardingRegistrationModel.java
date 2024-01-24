package com.abm.mainet.adh.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.adh.dto.HoardingMasterDto;
import com.abm.mainet.adh.dto.NewAdvertisementReqDto;
import com.abm.mainet.adh.service.HoardingRegistrationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;

@Component
@Scope("session")
public class HoardingRegistrationModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IChallanService challanService;
	@Autowired
	private HoardingRegistrationService hoardingRegistrationService;

	private NewAdvertisementReqDto advertisementReqDto = new NewAdvertisementReqDto();
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private List<String[]> hoardingNumberList = new ArrayList<>();
	private List<HoardingMasterDto> hoardingMasterList = new ArrayList<>();
	private double amountToPay;
	private boolean payable;
	List<LookUp> listOfLookUp;
	private String checkListApplFlag;
	private String applicationchargeApplFlag;
	private String paymentMode;
	private String enableSubmit;
    private String scrutinyViewMode;
    private String saveMode="C";
	private PortalService service = new PortalService();
	
	private Long licMaxTenureDays;
    private Long licMinTenureDays;

	@Override
	public boolean saveForm() {
		boolean status = true;
		final CommonChallanDTO offline = getOfflineDTO();
		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT.OFFLINE)) {
			offline.setOfflinePaymentText("PCU");
		}
		final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
		offline.setOfflinePaymentText(modeDesc);
		if (getAmountToPay() > 0d) {
			validateBean(offline, CommonOfflineMasterValidator.class);
		}
		// fileUpload. validateUpload(getBindingResult());
		if (hasValidationErrors()) {
			return false;
		}

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		List<DocumentDetailsVO> docs = getCheckList();
		/*
		 * if (docs != null) { docs = fileUpload.prepareFileUpload(docs); }
		 */
		advertisementReqDto
				.setDocumentList(getFileUploadList(getCheckList(), FileUploadUtility.getCurrent().getFileMap()));
		NewAdvertisementReqDto advertisementReqDto = getAdvertisementReqDto();
		advertisementReqDto.setDocumentList(docs);
		advertisementReqDto.setNewOrRenewalApp("NEW");
		if (advertisementReqDto.getAdvertisementDto().getCreatedBy() == null) {
			advertisementReqDto.setOrgId(orgId);
			setGender(advertisementReqDto.getApplicantDetailDTO());
			advertisementReqDto.setLangId((long) UserSession.getCurrent().getLanguageId());
			advertisementReqDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			advertisementReqDto.getAdvertisementDto().setOrgId(orgId);
			advertisementReqDto.getAdvertisementDto().setLgIpMac(lgIp);
			advertisementReqDto.getAdvertisementDto().setCreatedBy(createdBy);
			advertisementReqDto.getAdvertisementDto().setCreatedDate(newDate);
			// advertisementReqDto.setFree(true);
			if (!advertisementReqDto.getAdvertisementDto().getNewAdvertDetDtos().isEmpty()) {
				advertisementReqDto.getAdvertisementDto().getNewAdvertDetDtos().forEach(newAdvApp -> {
					newAdvApp.setCreatedBy(createdBy);
					newAdvApp.setCreatedDate(newDate);
					newAdvApp.setOrgId(orgId);
					newAdvApp.setLgIpMac(lgIp);
				});

				NewAdvertisementReqDto newAdvertisementReqDto = hoardingRegistrationService
						.saveNewHoardingApplication(advertisementReqDto);
				if (newAdvertisementReqDto.getAdvertisementDto().getApmApplicationId() != null
						&& !newAdvertisementReqDto.isFree()) {
					setAndSaveChallanDto(offline, newAdvertisementReqDto);
				}
				this.setApmApplicationId(newAdvertisementReqDto.getAdvertisementDto().getApmApplicationId());
				setSuccessMessage(ApplicationSession.getInstance().getMessage(
						MainetConstants.AdvertisingAndHoarding.YOUR_APPLICATION_NO) + MainetConstants.WHITE_SPACE
						+ newAdvertisementReqDto.getAdvertisementDto().getApmApplicationId());

			}
		}
		return status;

	}

	public boolean validateInputs() {
        if (hasValidationErrors()) {
            return false;
        }
        return true;
    }
	/**
	 * This method is used for set Gender from Hirerichal prefix
	 * 
	 * @param applicant
	 */
	private void setGender(final ApplicantDetailDTO applicant) {
		final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER,
				UserSession.getCurrent().getOrganisation());
		for (final LookUp lookUp : lookUps) {
			if ((applicant.getGender() != null) && !applicant.getGender().isEmpty()) {
				if (lookUp.getLookUpId() == Long.parseLong(applicant.getGender())) {
					applicant.setGender(lookUp.getLookUpCode());
					break;
				}
			}

		}
	}

	private void setAndSaveChallanDto(CommonChallanDTO offline, NewAdvertisementReqDto advertisementReqDto) {
		offline.setApplNo(advertisementReqDto.getApplicationId());
		offline.setAmountToPay(Double.toString(getAmountToPay()));
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		// offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		// offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		// offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		if ((getCheckList() != null) && (getCheckList().size() > 0)) {
			offline.setDocumentUploaded(true);
		} else {
			offline.setDocumentUploaded(false);
		}
		offline.setServiceId(advertisementReqDto.getServiceId());
		/*
		 * ServiceMaster hoardindServiceMaster =
		 * servicesMstService.findShortCodeByOrgId("HBP",
		 * UserSession.getCurrent().getOrganisation().getOrgid());
		 * offline.setServiceId(hoardindServiceMaster.getSmServiceId());
		 */
		String applicantName = getAdvertisementReqDto().getApplicantDetailDTO().getApplicantFirstName()
				+ MainetConstants.WHITE_SPACE;
		applicantName += getAdvertisementReqDto().getApplicantDetailDTO().getApplicantMiddleName() == null
				? MainetConstants.BLANK
				: getAdvertisementReqDto().getApplicantDetailDTO().getApplicantMiddleName()
						+ MainetConstants.WHITE_SPACE;
		applicantName += getAdvertisementReqDto().getApplicantDetailDTO().getApplicantLastName();
		offline.setApplicantName(applicantName);
		offline.setApplicantAddress(getAdvertisementReqDto().getApplicantDetailDTO().getAreaName() + ","
				+ getAdvertisementReqDto().getApplicantDetailDTO().getVillageTownSub());
		offline.setMobileNumber(getAdvertisementReqDto().getApplicantDetailDTO().getMobileNo());
		offline.setEmailId(getAdvertisementReqDto().getApplicantDetailDTO().getEmailId());
		offline.setEmpType(UserSession.getCurrent().getEmplType());

		for (ChargeDetailDTO chargeDetailDTO : getChargesInfo()) {
			offline.getFeeIds().put(chargeDetailDTO.getChargeCode(), chargeDetailDTO.getChargeAmount());
		}
		if (CollectionUtils.isNotEmpty(getCheckList())) {
			offline.setDocumentUploaded(true);
		} else {
			offline.setDocumentUploaded(false);
		}
		// offline.setLgIpMac(getClientIpAddress());
		// offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
		offline.setDeptId(advertisementReqDto.getDeptId());
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());
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

	

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public List<String[]> getHoardingNumberList() {
		return hoardingNumberList;
	}

	public void setHoardingNumberList(List<String[]> hoardingNumberList) {
		this.hoardingNumberList = hoardingNumberList;
	}

	public List<HoardingMasterDto> getHoardingMasterList() {
		return hoardingMasterList;
	}

	public void setHoardingMasterList(List<HoardingMasterDto> hoardingMasterList) {
		this.hoardingMasterList = hoardingMasterList;
	}

	public double getAmountToPay() {
		return amountToPay;
	}

	public void setAmountToPay(double amountToPay) {
		this.amountToPay = amountToPay;
	}

	public boolean isPayable() {
		return payable;
	}

	public void setPayable(boolean payable) {
		this.payable = payable;
	}

	public List<LookUp> getListOfLookUp() {
		return listOfLookUp;
	}

	public void setListOfLookUp(List<LookUp> listOfLookUp) {
		this.listOfLookUp = listOfLookUp;
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

	public NewAdvertisementReqDto getAdvertisementReqDto() {
		return advertisementReqDto;
	}

	public void setAdvertisementReqDto(NewAdvertisementReqDto advertisementReqDto) {
		this.advertisementReqDto = advertisementReqDto;
	}

	public PortalService getService() {
		return service;
	}

	public void setService(PortalService service) {
		this.service = service;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getEnableSubmit() {
		return enableSubmit;
	}

	public void setEnableSubmit(String enableSubmit) {
		this.enableSubmit = enableSubmit;
	}

	public String getScrutinyViewMode() {
		return scrutinyViewMode;
	}

	public void setScrutinyViewMode(String scrutinyViewMode) {
		this.scrutinyViewMode = scrutinyViewMode;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}
	
	    public Long getLicMaxTenureDays() {
	        return licMaxTenureDays;
	    }

	    public void setLicMaxTenureDays(Long licMaxTenureDays) {
	        this.licMaxTenureDays = licMaxTenureDays;
	    }

	    public Long getLicMinTenureDays() {
	        return licMinTenureDays;
	    }

	    public void setLicMinTenureDays(Long licMinTenureDays) {
	        this.licMinTenureDays = licMinTenureDays;
	    }

	    @Override
		public void redirectToPayDetails(final HttpServletRequest httpServletRequest,
				final PaymentRequestDTO payURequestDTO) {
			payURequestDTO.setUdf1(getService().getServiceId().toString());
			payURequestDTO.setUdf2(getOfflineDTO().getApplNo().toString());
			final ApplicantDetailDTO applicant = getAdvertisementReqDto().getApplicantDetailDTO();
			payURequestDTO.setUdf3("CitizenHome.html");
			payURequestDTO.setUdf5(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
			payURequestDTO.setUdf7(getOfflineDTO().getApplNo().toString());
			payURequestDTO.setServiceId(getService().getServiceId());
			if (getApmApplicationId() != 0) {
				payURequestDTO.setApplicationId(getOfflineDTO().getApplNo().toString());
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

}
