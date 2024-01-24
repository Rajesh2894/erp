package com.abm.mainet.rnl.ui.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ApplicationPortalMaster;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.abm.mainet.rnl.dto.BookingReqDTO;
import com.abm.mainet.rnl.dto.BookingResDTO;
import com.abm.mainet.rnl.dto.EstateBookingDTO;
import com.abm.mainet.rnl.dto.PropInfoDTO;
import com.abm.mainet.rnl.service.IRNLChecklistAndChargeService;
import com.abm.mainet.rnl.validator.EstateBookingFormValidator;

/**
 * @author ritesh.patil
 *
 */
@Component
@Scope("session")
public class EstateBookingModel extends AbstractFormModel {


	private static final long serialVersionUID = 8013260512013574634L;
	private BookingReqDTO bookingReqDTO = new BookingReqDTO();
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private Double charges = 0.0d;
	private String free = "O";
	private boolean enableSubmit;
	private boolean enableCheckList = true;
	private boolean accept;
	private String isFree;
	private List<ChargeDetailDTO> chargesInfo;
	private double amountToPay;
	private Long deptId;
	private Long applicationNo;
	private PropInfoDTO propInfoDTO;
	private String docName;
	private Long propId;
	private String acceptAgree;
	private String paymentMode;
	private double gstPercentage;
	private double cgstPercenatge;
	private double sgtPercenatge;
	private String orgShowHide;
	private String isBplShowHide;

	private String serviceURL;

	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();

	public Long getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(final Long applicationNo) {
		this.applicationNo = applicationNo;
	}

	private Long serviceId;
	private List<String> fromAndToDate;
	private ServiceMaster service;

private String checkBookingFlag;

	@Autowired
	private IPortalServiceMasterService iPortalServiceMasterService;

	@Autowired
	private IRNLChecklistAndChargeService serviceChallanFor;

	@Autowired
	private IChallanService challanService;


	@Resource
	private IFileUploadService fileUpload;

	/**
	 * @return the bookingReqDTO
	 */
	public BookingReqDTO getBookingReqDTO() {
		return bookingReqDTO;
	}

	/**
	 * @param bookingReqDTO the bookingReqDTO to set
	 */
	public void setBookingReqDTO(final BookingReqDTO bookingReqDTO) {
		this.bookingReqDTO = bookingReqDTO;
	}

	/**
	 * @return the checkList
	 */
	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	/**
	 * @param checkList the checkList to set
	 */
	public void setCheckList(final List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	/**
	 * @return the charges
	 */
	public Double getCharges() {
		return charges;
	}

	/**
	 * @param charges the charges to set
	 */
	public void setCharges(final Double charges) {
		this.charges = charges;
	}

	/**
	 * @return the free
	 */
	public String getFree() {
		return free;
	}

	/**
	 * @param free the free to set
	 */
	public void setFree(final String free) {
		this.free = free;
	}

	public boolean validateInputs() {


		if (hasValidationErrors()) {
			return false;
		}
		return true;
	}

	public boolean isEnableSubmit() {
		return enableSubmit;
	}

	public void setEnableSubmit(final boolean enableSubmit) {
		this.enableSubmit = enableSubmit;
	}

	/**
	 * @return the isFree
	 */
	public String getIsFree() {
		return isFree;
	}

	/**
	 * @param isFree the isFree to set
	 */
	public void setIsFree(final String isFree) {
		this.isFree = isFree;
	}

	/**
	 * @return the chargesInfo
	 */
	@Override
	public List<ChargeDetailDTO> getChargesInfo() {
		return chargesInfo;
	}

	/**
	 * @param chargesInfo the chargesInfo to set
	 */
	@Override
	public void setChargesInfo(final List<ChargeDetailDTO> chargesInfo) {
		this.chargesInfo = chargesInfo;
	}

	/**
	 * @return the amountToPay
	 */
	public double getAmountToPay() {
		return amountToPay;
	}

	/**
	 * @param amountToPay the amountToPay to set
	 */
	public void setAmountToPay(final double amountToPay) {
		this.amountToPay = amountToPay;
	}

	public ServiceMaster getService() {
		return service;
	}

	public void setService(final ServiceMaster service) {
		this.service = service;
	}

	@Override
	public boolean saveForm() {

		final CommonChallanDTO offline = getOfflineDTO();
		final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
		offline.setOfflinePaymentText(modeDesc);
		List<DocumentDetailsVO> docs = getCheckList();
		docs = fileUpload.prepareFileUpload(docs);
		getBookingReqDTO().setDocumentList(docs);
		validateBean(this, EstateBookingFormValidator.class);
		validateBean(offline, CommonOfflineMasterValidator.class);

		if (getAmountToPay() > 0.0)
if ((getOfflineDTO().getOnlineOfflineCheck() == null) || getOfflineDTO().getOnlineOfflineCheck().isEmpty()) {
				addValidationError(getAppSession().getMessage("Payment Mode is Empty"));
			}

//		if (!accept) {
//			addValidationError(getAppSession().getMessage("Please accept Terms & Conditions"));
//		}
		fileUpload.validateUpload(getBindingResult());

		if (hasValidationErrors()) {
			return false;
		}

		try {
			final PortalService portalServiceMaster = iPortalServiceMasterService.getService(getServiceId(),
					UserSession.getCurrent().getOrganisation().getOrgid());
			final EstateBookingDTO estateBookingDTO = getBookingReqDTO().getEstateBookingDTO();
			setGender(getApplicantDetailDto());
			getBookingReqDTO().getApplicantDetailDto();
			estateBookingDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			estateBookingDTO.setLangId(UserSession.getCurrent().getLanguageId());
			estateBookingDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			estateBookingDTO.setCreatedDate(new Date());
			estateBookingDTO.setUpdatedDate(new Date());

			estateBookingDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			getBookingReqDTO().setPayAmount(getAmountToPay());

			getBookingReqDTO().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			getBookingReqDTO().setServiceId(portalServiceMaster.getServiceId());
			
			getBookingReqDTO().setDeptId(getDeptId());
			getBookingReqDTO().setApplicationId(getApmApplicationId());
			getBookingReqDTO().setApplicationId(getApplicationNo());
			getBookingReqDTO().setDocumentList(getFileUploadList(getCheckList(), FileUploadUtility.getCurrent().getFileMap()));
			getBookingReqDTO().getEstateBookingDTO().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			BookingResDTO outPutObject = new BookingResDTO();
			if ((Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENVIRNMENT_VARIABLE.ENV_PSCL))
					&& (portalServiceMaster.getServiceName().equals("Water Tanker Booking")
							|| portalServiceMaster.getShortName().equals("WTB"))) {
				outPutObject = serviceChallanFor.saveOrUpdateWaterTanker(getBookingReqDTO());
				
			} else {
				 outPutObject = serviceChallanFor.saveOrUpdateChangeUsage(getBookingReqDTO());
			}
			if (outPutObject.getApplicationNo() != null) {

				setApmApplicationId(outPutObject.getApplicationNo());
				getBookingReqDTO().setApplicationId(outPutObject.getApplicationNo());

				if (getAmountToPay() > 0D) {
					getChallanFromServiceSite(offline, outPutObject);
				}


				savePortalMaster(getBookingReqDTO());
				setSuccessMessage(ApplicationSession.getInstance().getMessage("rnl.succ.app.no")
						+ outPutObject.getApplicationNo() + MainetConstants.WHITE_SPACE
						+ ApplicationSession.getInstance().getMessage("rnl.succ.booking.no")
						+ outPutObject.getBookingNo() + MainetConstants.WHITE_SPACE
					    + ApplicationSession.getInstance().getMessage("rnl.succ.proceed"));



			} else {
				throw new FrameworkException("web service call failed to save change of Usage");
			}
		} catch (final IOException e) {
			logger.error("Exception occured  when  saving form:", e);
			return false;
		}

		return true;

	}


	private void getChallanFromServiceSite(CommonChallanDTO offline, BookingResDTO bookingResDTO) {

		offline.setApplNo(bookingResDTO.getApplicationNo());
		offline.setAmountToPay(Double.toString(getAmountToPay()));
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setDeptId(getBookingReqDTO().getDeptId());
		offline.setServiceId(getServiceId());
		String aplicantName = getBookingReqDTO().getApplicantDetailDto().getApplicantFirstName()
				+ MainetConstants.WHITE_SPACE;
		aplicantName += getBookingReqDTO().getApplicantDetailDto().getApplicantMiddleName() == null
				? MainetConstants.BLANK
: getBookingReqDTO().getApplicantDetailDto().getApplicantMiddleName()
 + MainetConstants.WHITE_SPACE;
		aplicantName += getBookingReqDTO().getApplicantDetailDto().getApplicantLastName();
		offline.setApplicantName(aplicantName);
		offline.setMobileNumber(getBookingReqDTO().getApplicantDetailDto().getMobileNo());
		offline.setEmailId(getBookingReqDTO().getApplicantDetailDto().getEmailId());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setApplicantAddress(getBookingReqDTO().getApplicantDetailDto().getAreaName() + ","
				+ getBookingReqDTO().getApplicantDetailDto().getVillageTownSub());
		offline.setEmailId(getApplicantDetailDto().getEmailId());
		for (final ChargeDetailDTO dto : getChargesInfo()) {
			offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
		}
		offline.setDeptId(getDeptId());
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

	public void savePortalMaster(final BookingReqDTO outPutObject) {

		final ApplicationPortalMaster applicationMaster = new ApplicationPortalMaster();
		applicationMaster.setPamApplicationId(outPutObject.getApplicationId());
		applicationMaster.setPamApplicationDate(new Date());
		applicationMaster.setSmServiceId(outPutObject.getServiceId());
		applicationMaster.setDeleted(false);
		applicationMaster.setOrgId(UserSession.getCurrent().getOrganisation());
		applicationMaster.setLangId(UserSession.getCurrent().getLanguageId());
		applicationMaster.setUserId(UserSession.getCurrent().getEmployee());
		applicationMaster.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		applicationMaster.setLmodDate(new Date());
		iPortalServiceMasterService.saveApplicationMaster(applicationMaster, outPutObject.getPayAmount(),
				outPutObject.getDocumentList().size());

	}

	private void setGender(final ApplicantDetailDTO applicant) {

		final List<LookUp> lookUps = CommonMasterUtility.getLookUps(PrefixConstants.Prefix.GENDER,
				UserSession.getCurrent().getOrganisation());
		for (final LookUp lookUp : lookUps) {
			if ((applicant.getGender() != null) && !applicant.getGender().isEmpty()
					&& (lookUp.getLookUpId() == Long.parseLong(applicant.getGender()))) {

				applicant.setGender(lookUp.getLookUpCode());
				break;
			}
		}

	}

	public boolean validateInputs(final List<DocumentDetailsVO> dto) {
		boolean flag = true;
		if ((dto != null) && !dto.isEmpty()) {
			for (final DocumentDetailsVO doc : dto) {
				if (doc.getCheckkMANDATORY().equals(MainetConstants.FlagY)) {
					if (doc.getDocumentByteCode() == null) {
						addValidationError(
								ApplicationSession.getInstance().getMessage("Please Upload Madatory Documents"));
						flag = false;
						break;

					}
				}
			}

		}

		return flag;
	}


	/**
	 * @return the deptId
	 */
	public Long getDeptId() {
		return deptId;
	}

	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(final Long deptId) {
		this.deptId = deptId;
	}

	@Override
	public Long getServiceId() {
		return serviceId;
	}

	@Override
	public void setServiceId(final Long serviceId) {
		this.serviceId = serviceId;
	}

	public List<String> getFromAndToDate() {
		return fromAndToDate;
	}

	public void setFromAndToDate(final List<String> fromAndToDate) {
		this.fromAndToDate = fromAndToDate;
	}

	@Override
	public void redirectToPayDetails(final HttpServletRequest httpServletRequest,
			final PaymentRequestDTO payURequestDTO) {

		final BookingReqDTO bookingReqDTO = getBookingReqDTO();
        String userName = (bookingReqDTO.getApplicantDetailDto()
                .getApplicantFirstName() == null ? " "
                        : bookingReqDTO
                                .getApplicantDetailDto().getApplicantFirstName())
                + " ";
        userName += bookingReqDTO.getApplicantDetailDto()
                .getApplicantMiddleName() == null ? " "
                        : bookingReqDTO
                                .getApplicantDetailDto().getApplicantMiddleName() + " ";
        userName += bookingReqDTO.getApplicantDetailDto()
                .getApplicantLastName() == null ? " "
                        : bookingReqDTO
                                .getApplicantDetailDto().getApplicantLastName();
        final PortalService portalServiceMaster = iPortalServiceMasterService
                .getService(getServiceId(), UserSession.getCurrent()
                        .getOrganisation().getOrgid());
		payURequestDTO.setUdf3("CitizenHome.html");
		payURequestDTO.setUdf5(portalServiceMaster.getShortName());
		payURequestDTO.setUdf7(String.valueOf(getApplicationNo()));
		payURequestDTO.setApplicantName(userName);
		payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
		payURequestDTO.setUdf2(String.valueOf(getApplicationNo()));
		payURequestDTO.setMobNo(bookingReqDTO.getApplicantDetailDto()
				.getMobileNo());
		payURequestDTO.setDueAmt(new BigDecimal(getAmountToPay()));
		payURequestDTO.setEmail(bookingReqDTO.getApplicantDetailDto().getEmailId());
		payURequestDTO.setApplicationId(String.valueOf(bookingReqDTO.getApplicationId()));
		payURequestDTO.setOrgId(bookingReqDTO.getEstateBookingDTO().getOrgId());
		// Adding department Id in udf10 of payURequestDTO object
		if (portalServiceMaster != null) {
			payURequestDTO.setUdf10(portalServiceMaster.getPsmDpDeptid()
					.toString());
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
				payURequestDTO.setServiceName(portalServiceMaster
						.getServiceName());
			} else {
				payURequestDTO.setServiceName(portalServiceMaster
						.getServiceNameReg());
			}
		}
	}

	public boolean isEnableCheckList() {
		return enableCheckList;
	}

	public void setEnableCheckList(final boolean enableCheckList) {
		this.enableCheckList = enableCheckList;
	}

	public boolean getAccept() {
		return accept;
	}

	public void setAccept(final boolean accept) {
		this.accept = accept;
	}

	public PropInfoDTO getPropInfoDTO() {
		return propInfoDTO;
	}

	public void setPropInfoDTO(final PropInfoDTO propInfoDTO) {
		this.propInfoDTO = propInfoDTO;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(final String docName) {
		this.docName = docName;
	}

	public Long getPropId() {
		return propId;
	}

	public void setPropId(Long propId) {
		this.propId = propId;
	}

	public String getAcceptAgree() {
		return acceptAgree;
	}

	public void setAcceptAgree(String acceptAgree) {
		this.acceptAgree = acceptAgree;
	}

	public double getGstPercentage() {
		return gstPercentage;
	}

	public void setGstPercentage(double gstPercentage) {
		this.gstPercentage = gstPercentage;
	}

	public double getCgstPercenatge() {
		return cgstPercenatge;
	}

	public void setCgstPercenatge(double cgstPercenatge) {
		this.cgstPercenatge = cgstPercenatge;
	}

	public double getSgtPercenatge() {
		return sgtPercenatge;
	}

	public void setSgtPercenatge(double sgtPercenatge) {
		this.sgtPercenatge = sgtPercenatge;
	}

	public IPortalServiceMasterService getiPortalServiceMasterService() {
		return iPortalServiceMasterService;
	}

	public void setiPortalServiceMasterService(IPortalServiceMasterService iPortalServiceMasterService) {
		this.iPortalServiceMasterService = iPortalServiceMasterService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getOrgShowHide() {
		return orgShowHide;
	}

	public void setOrgShowHide(String orgShowHide) {
		this.orgShowHide = orgShowHide;
	}

	public String getIsBplShowHide() {
		return isBplShowHide;
	}

	public void setIsBplShowHide(String isBplShowHide) {
		this.isBplShowHide = isBplShowHide;
	}

	public String getCheckBookingFlag() {
		return checkBookingFlag;
	}

	public void setCheckBookingFlag(String checkBookingFlag) {
		this.checkBookingFlag = checkBookingFlag;
	}

	public String getServiceURL() {
		return serviceURL;
	}

	public void setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}



}
