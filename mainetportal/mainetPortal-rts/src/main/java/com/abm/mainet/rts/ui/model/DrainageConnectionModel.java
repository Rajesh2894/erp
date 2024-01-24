package com.abm.mainet.rts.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.dms.domain.CFCAttachment;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.abm.mainet.rts.dto.DrainageConnectionDto;
import com.abm.mainet.rts.service.DrainageConnectionService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class DrainageConnectionModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2970607705154432514L;

	@Autowired
	private IPortalServiceMasterService iPortalService;

	@Autowired
	private IFileUploadService fileUploadService;

	@Autowired
	private ISMSAndEmailService ismsAndEmailService;

	@Autowired
	private IChallanService iChallanService;

	@Autowired
	private DrainageConnectionService drainageConnectionService;

	private Map<Long, String> depList = null;
	private List<Object[]> serviceList = new ArrayList<>();
	private RequestDTO reqDTO = new RequestDTO();
	private Map<Long, String> wardList = null;
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private List<DocumentDetailsVO> documentList = new ArrayList<>();
	private List<Long> applicationNo = new ArrayList<Long>();
	private List<RequestDTO> requestDtoList = new ArrayList<>();
	private DrainageConnectionDto drainageConnectionDto = new DrainageConnectionDto();
	private String saveMode;
	private boolean noCheckListFound;
	private String errorMessage;
	private Map<Long, Double> chargesMap = new HashMap<>();
	private String isFree;
	private Double charges = 0.0d;
	private ServiceMaster serviceMasterData = new ServiceMaster();
	private Long parentOrgId;
	private Long serviceId;
	private Long deptId;
	private String serviceCode;
	private String checkListApplFlag;
	private String applicationchargeApplFlag;
	private boolean free;

	/*
	 * public ServiceMasterService getServiceMaster() { return serviceMaster; }
	 * public void setServiceMaster(ServiceMasterService serviceMaster) {
	 * this.serviceMaster = serviceMaster; }
	 */
	public IFileUploadService getFileUploadService() {
		return fileUploadService;
	}

	public void setFileUploadService(IFileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public ISMSAndEmailService getIsmsAndEmailService() {
		return ismsAndEmailService;
	}

	public void setIsmsAndEmailService(ISMSAndEmailService ismsAndEmailService) {
		this.ismsAndEmailService = ismsAndEmailService;
	}

	public IChallanService getiChallanService() {
		return iChallanService;
	}

	public void setiChallanService(IChallanService iChallanService) {
		this.iChallanService = iChallanService;
	}

	public Map<Long, String> getDepList() {
		return depList;
	}

	public void setDepList(Map<Long, String> depList) {
		this.depList = depList;
	}

	public List<Object[]> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<Object[]> serviceList) {
		this.serviceList = serviceList;
	}

	public void setReqDTO(RequestDTO reqDTO) {
		this.reqDTO = reqDTO;
	}

	public RequestDTO getReqDTO() {
		return reqDTO;
	}

	public void setRequestDTO(RequestDTO reqDTO) {
		this.reqDTO = reqDTO;
	}

	public Map<Long, String> getWardList() {
		return wardList;
	}

	public void setWardList(Map<Long, String> wardList) {
		this.wardList = wardList;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public List<Long> getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(List<Long> applicationNo) {
		this.applicationNo = applicationNo;
	}

	public List<RequestDTO> getRequestDtoList() {
		return requestDtoList;
	}

	public void setRequestDtoList(List<RequestDTO> requestDtoList) {
		this.requestDtoList = requestDtoList;
	}

	public DrainageConnectionDto getDrainageConnectionDto() {
		return drainageConnectionDto;
	}

	public void setDrainageConnectionDto(DrainageConnectionDto drainageConnectionDto) {
		this.drainageConnectionDto = drainageConnectionDto;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public boolean isNoCheckListFound() {
		return noCheckListFound;
	}

	public void setNoCheckListFound(boolean noCheckListFound) {
		this.noCheckListFound = noCheckListFound;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Map<Long, Double> getChargesMap() {
		return chargesMap;
	}

	public void setChargesMap(Map<Long, Double> chargesMap) {
		this.chargesMap = chargesMap;
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}

	public Double getCharges() {
		return charges;
	}

	public void setCharges(Double charges) {
		this.charges = charges;
	}

	public ServiceMaster getServiceMasterData() {
		return serviceMasterData;
	}

	public void setServiceMasterData(ServiceMaster serviceMasterData) {
		this.serviceMasterData = serviceMasterData;
	}

	public Long getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(Long parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	@Override
	public boolean saveForm() {

		boolean flag = false;
		CommonChallanDTO offline = getOfflineDTO();
		DrainageConnectionDto dto = this.getDrainageConnectionDto();
		final UserSession session = UserSession.getCurrent();
		List<DocumentDetailsVO> documents = getCheckList();
		List<DocumentDetailsVO> dtoDocuments = getDrainageConnectionDto().getDocumentList();
		validateInputs(documents);
		final Date sysDate = new java.sql.Date(new Date().getTime());
		this.getReqDTO().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		this.getReqDTO().setLangId(new Long(UserSession.getCurrent().getLanguageId()));
		this.getReqDTO().setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		this.getReqDTO().setDeptId(this.getDeptId());
		this.getReqDTO().setServiceId(getServiceId());
		this.getReqDTO().setPayAmount(getCharges());

		dto.setDocumentList(documents);

		Double paymentAmount = offline.getAmountToShow();

		dto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		dto.setOrgId(session.getOrganisation().getOrgid());
		dto.setCreatedBy(session.getEmployee().getEmpId());
		dto.setCreatedDate(new Date());
		dto.setWard(this.getReqDTO().getWardNo());
		dto = drainageConnectionService.saveDrainageConnection(this);
		this.setDrainageConnectionDto(dto);
		if (!this.isFree()) {
			dto.setChargesMap(this.getChargesMap());
			setOfflineDetailsDTO(offline, this);
		}

		setSuccessMessage(getAppSession().getMessage("DrainageConnectionDTO.successMsg")+ dto.getApmApplicationId());
		RtsServiceFormModel model = ApplicationContextProvider.getApplicationContext().getBean(RtsServiceFormModel.class);
        model.setApmApplicationId(dto.getApmApplicationId());
        model.setCheckList(this.getCheckList());
        model.setOfflineDTO(this.getOfflineDTO());
		String paymentUrl = "drainageConnection.html";
		// sendSmsEmail(this, paymentUrl,
		// PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED);

		final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();

		smsDto.setMobnumber(this.getReqDTO().getMobileNo().toString());
		smsDto.setAppNo(dto.getApmApplicationId().toString());
		// smsDto.setAppNo(getApplicationformdto().getApplicationId().toString());

		smsDto.setServName(this.getServiceName());
		String url = "drainageConnection.html";
		smsDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		int langId = UserSession.getCurrent().getLanguageId();
		ismsAndEmailService.sendEmailSMS("RTS", url, "AS", smsDto, UserSession.getCurrent().getOrganisation(), langId);
		flag = true;

		return flag;
	}

	private void setOfflineDetailsDTO(CommonChallanDTO offline, DrainageConnectionModel model) {
		/* Setting Offline DTO */
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setOrgId(this.getDrainageConnectionDto().getOrgId());
		offline.setDeptId(Long.valueOf(this.getDeptId()));
		offline.setApplNo(this.getDrainageConnectionDto().getApmApplicationId());
		offline.setAmountToPay(Double.toString(offline.getAmountToShow()));
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		String fullName = String.join(" ", Arrays.asList(reqDTO.getfName(), reqDTO.getmName(), reqDTO.getlName()));
		offline.setApplicantName(fullName);
		String applicantAddress = String.join(" ",
				Arrays.asList(reqDTO.getBldgName(), reqDTO.getBlockName(), reqDTO.getRoadName(), reqDTO.getCityName()));
		offline.setApplicantAddress(applicantAddress);
		offline.setMobileNumber(reqDTO.getMobileNo());
		offline.setEmailId(reqDTO.getEmail());
		offline.setServiceId(this.getServiceId());
		offline.setUserId(reqDTO.getUserId());
		offline.setLgIpMac(Utility.getMacAddress());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);

		for (final Map.Entry<Long, Double> entry : this.getDrainageConnectionDto().getChargesMap().entrySet()) {
			offline.getFeeIds().put(entry.getKey(), entry.getValue());
		}
		offline.setLangId(UserSession.getCurrent().getLanguageId());

		if (model.getCheckListApplFlag().equalsIgnoreCase("Y")) {
			offline.setDocumentUploaded(true);
		} else {
			offline.setDocumentUploaded(false);
		}
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());
		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.NewWaterServiceConstants.NO)) {

			offline = iChallanService.generateChallanNumber(offline);
			setOfflineDTO(offline);
		}
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

//method for Setting data for online payment Defect #85065
	@Override
	public void redirectToPayDetails(final HttpServletRequest httpServletRequest,
			final PaymentRequestDTO payURequestDTO) {
		final DrainageConnectionDto reqDTO = this.getDrainageConnectionDto();
		final PortalService portalServiceMaster = iPortalService.getService(getServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		payURequestDTO.setUdf3("CitizenHome.html");
		payURequestDTO.setUdf5(portalServiceMaster.getShortName());
		payURequestDTO.setUdf7(String.valueOf(reqDTO.getApmApplicationId()));
		String fullName = String.join(" ", Arrays.asList(reqDTO.getReqDTO().getfName(), reqDTO.getReqDTO().getmName(),
				reqDTO.getReqDTO().getlName()));
		payURequestDTO.setApplicantName(fullName);
		payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
		payURequestDTO.setUdf2(String.valueOf(reqDTO.getApmApplicationId()));
		payURequestDTO.setMobNo(reqDTO.getReqDTO().getMobileNo());
		if (getCharges() != null) {
			payURequestDTO.setDueAmt(new BigDecimal(getCharges()));
		}
		// payURequestDTO.setDueAmt(paymentAmount);
		payURequestDTO.setEmail(reqDTO.getReqDTO().getEmail());
		payURequestDTO.setApplicationId(reqDTO.getApmApplicationId().toString());
		if (portalServiceMaster != null) {
			payURequestDTO.setUdf10(portalServiceMaster.getPsmDpDeptid().toString());
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
			} else {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceNameReg());
			}
		}
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

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public List<DocumentDetailsVO> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<DocumentDetailsVO> documentList) {
		this.documentList = documentList;
	}

}
