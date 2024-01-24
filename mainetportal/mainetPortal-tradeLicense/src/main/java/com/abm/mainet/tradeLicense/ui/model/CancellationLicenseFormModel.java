package com.abm.mainet.tradeLicense.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.EncryptionAndDecryptionAapleSarkar;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.ICancellationLicenseService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;

@Component
@Scope("session")
public class CancellationLicenseFormModel extends AbstractFormModel {

	/**
	 * 
	 */

	@Autowired
	private ICommonBRMSService brmsCommonService;
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private String paymentCheck;
	private static final long serialVersionUID = 1L;
	private List<TradeMasterDetailDTO> tradeDetaildto = new ArrayList<>();
	private TradeMasterDetailDTO tradeMasterDetailDTO = new TradeMasterDetailDTO();
	private String otpBtnShowFlag;
	private String licFromDateDesc;
	private String licToDateDesc;
	private String licenseBtnFlag;
	private String userOtp;
	private String otp;
	private String licenseDetails;
	private String checklistCheck;
	private String appChargeFlag;
	private String checkListApplFlag;
	private String viewMode;
	private String envFlag;
	private Date appDate;
	private String appTime;
	private String departmentName;
	private Date dueDate;
	private String helpLine;
	private Long applicationId;
	private String applicantName;
	private String serviceName;
	private PortalService serviceMaster = new PortalService();
	private String tenant;


	@Override
	public boolean saveForm() {

		TradeMasterDetailDTO masDto = getTradeMasterDetailDTO();
		String lgIpMacUpd = UserSession.getCurrent().getEmployee().getEmppiservername();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		//124306 - trdstaus changed to transit till last approval
		LookUp lookUpCancel = CommonMasterUtility.getValueFromPrefixLookUp("T", "LIS");
		if (lookUpCancel != null) {
			masDto.setTrdStatus(lookUpCancel.getLookUpId());
		}
		masDto.setOrgid(orgId);
		masDto.setOrgId(orgId);
		masDto.setLgIpMacUpd(lgIpMacUpd);
		masDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		masDto.setUserId((UserSession.getCurrent().getEmployee().getEmpId()));
		masDto.setDocumentList(getFileUploadList(getCheckList(), FileUploadUtility.getCurrent().getFileMap()));
		TradeMasterDetailDTO masDto1 = ApplicationContextProvider.getApplicationContext()
				.getBean(ICancellationLicenseService.class).saveCancellationLicense(masDto);
		if(null!=masDto1.getTenant() && null!=masDto1.getApmApplicationId()){
			try{
				ApplicationContextProvider.getApplicationContext().getBean(ITradeLicenseApplicationService.class).aapaleSarakarPortalEntry(masDto);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		this.setSuccessMessage(getAppSession().getMessage("trade.cancellation.successMsg") + masDto1.getApmApplicationId());

		return true;
	}

	public boolean validateInputs() {

		// validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);

		FileUploadServiceValidator.getCurrent().validateUpload(getBindingResult());
		if (hasValidationErrors()) {
			// this.isValidationError = MainetConstants.Y_FLAG;
			return false;
		}
		return true;
	}

	public String getOtpBtnShowFlag() {
		return otpBtnShowFlag;
	}

	public void setOtpBtnShowFlag(String otpBtnShowFlag) {
		this.otpBtnShowFlag = otpBtnShowFlag;
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

	public String getLicenseBtnFlag() {
		return licenseBtnFlag;
	}

	public void setLicenseBtnFlag(String licenseBtnFlag) {
		this.licenseBtnFlag = licenseBtnFlag;
	}

	public String getUserOtp() {
		return userOtp;
	}

	public String getOtp() {
		return otp;
	}

	public void setUserOtp(String userOtp) {
		this.userOtp = userOtp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public List<TradeMasterDetailDTO> getTradeDetaildto() {
		return tradeDetaildto;
	}

	public TradeMasterDetailDTO getTradeMasterDetailDTO() {
		return tradeMasterDetailDTO;
	}

	public void setTradeDetaildto(List<TradeMasterDetailDTO> tradeDetaildto) {
		this.tradeDetaildto = tradeDetaildto;
	}

	public void setTradeMasterDetailDTO(TradeMasterDetailDTO tradeMasterDetailDTO) {
		this.tradeMasterDetailDTO = tradeMasterDetailDTO;
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
			checkListModel.setServiceCode(MainetConstants.TradeLicense.CANCELLATION_LICENSE_FORM);
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

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public String getPaymentCheck() {
		return paymentCheck;
	}

	public String getAppChargeFlag() {
		return appChargeFlag;
	}

	public void setAppChargeFlag(String appChargeFlag) {
		this.appChargeFlag = appChargeFlag;
	}

	public String getCheckListApplFlag() {
		return checkListApplFlag;
	}

	public void setCheckListApplFlag(String checkListApplFlag) {
		this.checkListApplFlag = checkListApplFlag;
	}

	public void setPaymentCheck(String paymentCheck) {
		this.paymentCheck = paymentCheck;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public Date getAppDate() {
		return appDate;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	public String getAppTime() {
		return appTime;
	}

	public void setAppTime(String appTime) {
		this.appTime = appTime;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getHelpLine() {
		return helpLine;
	}

	public void setHelpLine(String helpLine) {
		this.helpLine = helpLine;
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

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public PortalService getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(PortalService serviceMaster) {
		this.serviceMaster = serviceMaster;
	}


	public String getEnvFlag() {
		return envFlag;
	}

	public void setEnvFlag(String envFlag) {
		this.envFlag = envFlag;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}
	
}
