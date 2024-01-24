package com.abm.mainet.tradeLicense.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.EncryptionAndDecryptionAapleSarkar;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.IDuplicateLicenseApplicationService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;

@Component
@Scope("session")
public class DuplicateLicenseFormModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TradeMasterDetailDTO tradeMasterDetailDTO = new TradeMasterDetailDTO();
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private String paymentCheck;
	private String licenseDetails;
	private String checklistCheck;
	private String licFromDateDesc;
	private String licToDateDesc;
	private String userOtp;
	private String otp;
	private PortalService serviceMaster = new PortalService();
	private String issuanceDateDesc;
	private String viewMode;
	private String licenseBtnFlag;
	private String otpBtnShowFlag;
	private String ownerName;
	private String envFlag;
	private String checkListApplFlag = null;
	private String AppChargeFlag = null;
	
	private Date appDate;
	private String appTime;
	private String departmentName;
	private Date dueDate;
	private String helpLine;
	private Long applicationId;
	private String applicantName;
	private String serviceName;
    private String tenant;
	@Autowired
	private ICommonBRMSService brmsCommonService;

	@Autowired
	private IPortalServiceMasterService iPortalServiceMasterService;

	@Autowired
	private IChallanService challanService;

	@Autowired
	private IDuplicateLicenseApplicationService duplicateLicenseApplicationService;

	@Override
	public boolean saveForm() {

		setCommonFields(this);
		TradeMasterDetailDTO masDto = getTradeMasterDetailDTO();
		masDto.setLangId(new Long(UserSession.getCurrent().getLanguageId()));
		masDto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		masDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		masDto.setDocumentList(getFileUploadList(getCheckList(), FileUploadUtility.getCurrent().getFileMap()));
		masDto = duplicateLicenseApplicationService.saveDuplicateLicense(masDto);
		setTradeMasterDetailDTO(masDto);
		if (null != masDto.getTenant() && null != masDto.getApmApplicationId()) {
			try {
				ApplicationContextProvider.getApplicationContext().getBean(ITradeLicenseApplicationService.class)
						.aapaleSarakarPortalEntry(masDto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		final CommonChallanDTO offline = getOfflineDTO();
		Map<Long, Double> details = new HashMap<>(0);
		final Map<Long, Long> billDetails = new HashMap<>(0);
		setChallanDToandSaveChallanData(offline, details, billDetails, getTradeMasterDetailDTO());
		RequestDTO requestDTO1 = new RequestDTO();
		requestDTO1.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO1.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		requestDTO1.setDepartmentName(MainetConstants.TradeLicense.DUPLICATE_SERVICE_SHORT_CODE);
		requestDTO1.setStatus(MainetConstants.Common_Constant.ACTIVE_FLAG);
		requestDTO1.setIdfId(masDto.getApmApplicationId().toString());
		requestDTO1.setDepartmentName("ML");
		requestDTO1.setApplicationId(masDto.getApmApplicationId());
		requestDTO1.setDeptId(getServiceMaster().getPsmDpDeptid());
		requestDTO1.setLangId(new Long(UserSession.getCurrent().getLanguageId()));
		requestDTO1.setServiceId(getServiceMaster().getServiceId());
		requestDTO1.setReferenceId(masDto.getApmApplicationId().toString());
		this.setSuccessMessage(getAppSession().getMessage("trade.successMsg") + masDto.getApmApplicationId());
		return true;

	}

	private void setChallanDToandSaveChallanData(CommonChallanDTO offline, final Map<Long, Double> details,
			final Map<Long, Long> billDetails, TradeMasterDetailDTO tradeMaster) {

		final UserSession session = UserSession.getCurrent();
		final PortalService portalServiceMaster = iPortalServiceMasterService.getService(getServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		// setServiceMaster(portalServiceMaster);
		if(tradeMaster.getTradeLicenseOwnerdetailDTO() != null) {
		TradeLicenseOwnerDetailDTO ownDtlDto = tradeMaster.getTradeLicenseOwnerdetailDTO().get(0);
		offline.setEmailId(ownDtlDto.getTroEmailid());
		offline.setApplicantAddress(ownDtlDto.getTroAddress());
		offline.setMobileNumber(ownDtlDto.getTroMobileno());
		}
		offline.setAmountToPay(tradeMaster.getTotalApplicationFee().toString());
		offline.setUserId(session.getEmployee().getEmpId());
		offline.setOrgId(session.getOrganisation().getOrgid());
		offline.setLangId(session.getLanguageId());
		offline.setLgIpMac(session.getEmployee().getEmppiservername());
		offline.setServiceName(portalServiceMaster.getServiceName());
		offline.setServiceNameMar(portalServiceMaster.getServiceNameReg());
		if ((details != null) && !details.isEmpty()) {
			offline.setFeeIds(details);
		}
		if ((billDetails != null) && !billDetails.isEmpty()) {
			offline.setBillDetIds(billDetails);
		}
		/*
		 * offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		 * offline.setPaymentCategory(MainetConstants.TradeLicense.PAYMENT_CATEGORY);
		 * offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		 * offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		 */
		offline.setApplicantName(this.getOwnerName());
		offline.setApplicantFullName(this.getOwnerName());
		offline.setApplNo(tradeMaster.getApmApplicationId());
		offline.setUniquePrimaryId(tradeMaster.getApmApplicationId().toString());
		offline.setDeptId(portalServiceMaster.getPsmDpDeptid());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setServiceId(portalServiceMaster.getServiceId());
		offline.setLicNo(tradeMaster.getTrdLicno());
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			offline.setDocumentUploaded(true);
		}
		offline.setFeeIds(tradeMaster.getFeeIds());
		tradeMaster.setFeeIds(tradeMaster.getFeeIds());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());
		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.NewWaterServiceConstants.NO)) {
			offline = challanService.generateChallanNumber(offline);
			/*
			 * offline.setChallanValidDate(master .getChallanValiDate());
			 * offline.setChallanNo(master.getChallanNo());
			 */
		}
		setOfflineDTO(offline);
	}

	public void getCheckListFromBrms() {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final WSRequestDTO initRequestDto = new WSRequestDTO();
		initRequestDto.setModelName(MainetConstants.TradeLicense.CHECKLIST_MODEL);
		WSResponseDTO response = brmsCommonService.initializeModel(initRequestDto);
		List<DocumentDetailsVO> checkListList = new ArrayList<>();
		if (response.getWsStatus() != null
				&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			List<Object> checklist = JersyCall.castResponse(response, CheckListModel.class, 0);
			CheckListModel checkListModel = (CheckListModel) checklist.get(0);
			checkListModel.setOrgId(orgId);
			checkListModel.setServiceCode(MainetConstants.TradeLicense.DUPLICATE_SERVICE_SHORT_CODE);
			final WSRequestDTO checkRequestDto = new WSRequestDTO();
			checkRequestDto.setDataModel(checkListModel);
			checkListList = brmsCommonService.getChecklist(checkListModel);
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

	private void setCommonFields(DuplicateLicenseFormModel model) {
		final Date sysDate = new Date();
		RequestDTO requestDTO = new RequestDTO();
		final TradeMasterDetailDTO dto = model.getTradeMasterDetailDTO();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		dto.setOrgid(orgId);
		final Long serviceId = iPortalServiceMasterService
				.getServiceId(MainetConstants.TradeLicense.DUPLICATE_SERVICE_SHORT_CODE, orgId);
		final PortalService service = iPortalServiceMasterService.getService(serviceId, orgId);
		model.setServiceMaster(service);
		model.setServiceId(service.getServiceId());
		model.getTradeMasterDetailDTO().setServiceId(service.getServiceId());
		dto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		dto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		dto.setPrimaryOwnerName(dto.getPrimaryOwnerName());
		dto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
		dto.setLgIpMac(dto.getLgIpMac());
		requestDTO.setUserId(dto.getCreatedBy());
		dto.setCreatedDate(sysDate);
		long deptId = service.getPsmDpDeptid();
		requestDTO.setDeptId(deptId);
	}

	@Override
	public void redirectToPayDetails(final HttpServletRequest httpServletRequest,
			final PaymentRequestDTO payURequestDTO) {
		final TradeMasterDetailDTO tradeMasterDetailDTO = this.getTradeMasterDetailDTO();
		final PortalService portalServiceMaster = iPortalServiceMasterService.getService(getServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		payURequestDTO.setUdf3("CitizenHome.html");
		payURequestDTO.setUdf5(portalServiceMaster.getShortName());
		payURequestDTO.setUdf7(String.valueOf(tradeMasterDetailDTO.getApmApplicationId()));
		// String fullName = String.join(" ", Arrays.asList(reqDTO.getfName(),
		// reqDTO.getmName(), reqDTO.getlName()));
		payURequestDTO.setApplicantName(this.getOwnerName());
		payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
		payURequestDTO.setUdf2(String.valueOf(tradeMasterDetailDTO.getApmApplicationId()));
		payURequestDTO
				.setMobNo(tradeMasterDetailDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroMobileno().toString());
		payURequestDTO.setDueAmt(tradeMasterDetailDTO.getTotalApplicationFee());
		payURequestDTO.setEmail(tradeMasterDetailDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroEmailid());
		payURequestDTO.setApplicationId(tradeMasterDetailDTO.getApmApplicationId().toString());
		payURequestDTO.setFeeIds(tradeMasterDetailDTO.getFeeIds().toString());
		if (portalServiceMaster != null) {
			payURequestDTO.setUdf10(portalServiceMaster.getPsmDpDeptid().toString());
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
			} else {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceNameReg());
			}
		}
	}

	public boolean validateInputs() {
		// validateBean(this, TradeApplicationFormValidator.class);
		FileUploadServiceValidator.getCurrent().validateUpload(getBindingResult());
		validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);
		if (hasValidationErrors()) {
			return false;
		}
		return true;
	}

	public TradeMasterDetailDTO getTradeMasterDetailDTO() {
		return tradeMasterDetailDTO;
	}

	public void setTradeMasterDetailDTO(TradeMasterDetailDTO tradeMasterDetailDTO) {
		this.tradeMasterDetailDTO = tradeMasterDetailDTO;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public String getPaymentCheck() {
		return paymentCheck;
	}

	public void setPaymentCheck(String paymentCheck) {
		this.paymentCheck = paymentCheck;
	}

	public String getLicFromDateDesc() {
		return licFromDateDesc;
	}

	public void setLicFromDateDesc(String licFromDateDesc) {
		this.licFromDateDesc = licFromDateDesc;
	}

	public String getLicToDateDesc() {
		return licToDateDesc;
	}

	public void setLicToDateDesc(String licToDateDesc) {
		this.licToDateDesc = licToDateDesc;
	}

	public PortalService getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(PortalService serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public String getLicenseDetails() {
		return licenseDetails;
	}

	public void setLicenseDetails(String licenseDetails) {
		this.licenseDetails = licenseDetails;
	}

	public String getChecklistCheck() {
		return checklistCheck;
	}

	public void setChecklistCheck(String checklistCheck) {
		this.checklistCheck = checklistCheck;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getIssuanceDateDesc() {
		return issuanceDateDesc;
	}

	public void setIssuanceDateDesc(String issuanceDateDesc) {
		this.issuanceDateDesc = issuanceDateDesc;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public String getUserOtp() {
		return userOtp;
	}

	public void setUserOtp(String userOtp) {
		this.userOtp = userOtp;
	}

	public String getOtpBtnShowFlag() {
		return otpBtnShowFlag;
	}

	public void setOtpBtnShowFlag(String otpBtnShowFlag) {
		this.otpBtnShowFlag = otpBtnShowFlag;
	}

	public String getLicenseBtnFlag() {
		return licenseBtnFlag;
	}

	public void setLicenseBtnFlag(String licenseBtnFlag) {
		this.licenseBtnFlag = licenseBtnFlag;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getEnvFlag() {
		return envFlag;
	}

	public void setEnvFlag(String envFlag) {
		this.envFlag = envFlag;
	}

	public String getCheckListApplFlag() {
		return checkListApplFlag;
	}

	public void setCheckListApplFlag(String checkListApplFlag) {
		this.checkListApplFlag = checkListApplFlag;
	}

	public String getAppChargeFlag() {
		return AppChargeFlag;
	}

	public void setAppChargeFlag(String appChargeFlag) {
		AppChargeFlag = appChargeFlag;
	}

	public Date getAppDate() {
		return appDate;
	}

	public String getAppTime() {
		return appTime;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	public void setAppTime(String appTime) {
		this.appTime = appTime;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getHelpLine() {
		return helpLine;
	}

	public void setHelpLine(String helpLine) {
		this.helpLine = helpLine;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}
	
	

}
