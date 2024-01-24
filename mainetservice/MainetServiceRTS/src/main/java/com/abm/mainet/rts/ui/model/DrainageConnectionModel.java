package com.abm.mainet.rts.ui.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.ui.model.LoiGenerationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.rts.constant.RtsConstants;
import com.abm.mainet.rts.dto.DrainageConnectionDto;
import com.abm.mainet.rts.dto.MediaChargeAmountDTO;
import com.abm.mainet.rts.service.DrainageConnectionService;
import com.abm.mainet.rts.ui.validator.DrainageConnectionFormValidator;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
public class DrainageConnectionModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private DrainageConnectionService drainageConnectionService;

	@Autowired
	private ServiceMasterService serviceMaster;

	@Autowired
	private CommonService commonService;

	@Autowired
	private IFileUploadService fileUploadService;

	@Autowired
	private ISMSAndEmailService ismsAndEmailService;

	@Autowired
	private IChallanService iChallanService;
	
	@Autowired
	private TbTaxMasService tbTaxMasService;

	private Map<Long, String> depList = null;
	private Map<Long, String> serviceList = null;
	private RequestDTO requestDTO = new RequestDTO();
	private Map<Long, String> wardList = null;
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private List<Long> applicationNo = new ArrayList<Long>();
	private List<RequestDTO> requestDtoList = new ArrayList<>();
	private DrainageConnectionDto drainageConnectionDto = new DrainageConnectionDto();
	private String saveMode;
	private boolean noCheckListFound;
	private String errorMessage;
	private Map<Long, Double> chargesMap = new HashMap<>();
	private boolean free;
	private Double charges = 0.0d;
	private ServiceMaster serviceMasterData = new ServiceMaster();
	private Long parentOrgId;
	private Long serviceId;
	private Long deptId;
	private CommonChallanDTO offlineDTO = new CommonChallanDTO();
	private List<MediaChargeAmountDTO> chargesList = new ArrayList<MediaChargeAmountDTO>();
	private ApplicantDetailDTO applicantDTO = new ApplicantDetailDTO();
	private String checkListApplFlag;
	private String applicationchargeApplFlag;
	private WorkflowTaskAction WorkflowTaskActionDTO = new WorkflowTaskAction();
	private List<CFCAttachment> documentList = new ArrayList<>();
	private String applicantName;
	private Long applicationId;
	private String formName;
	private String orgName;
	private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();
	
	private boolean lastChecker;
	private String loiChargeApplFlag = MainetConstants.FlagN;
	private Double totalLoiAmount;
	private String taxDesc;
	private String showFlag = MainetConstants.FlagN;
	private List<TbLoiDet> loiDetail = new ArrayList<>();
	private String loiNo;
	private long levelcheck;
	private String viewFlag;
	private String appCheck;
	
	private List<ChargeDetailDTO> chargesInfo;

	@Override
	public boolean saveForm() {
		boolean flag = false;
		CommonChallanDTO offline = getOfflineDTO();
		DrainageConnectionDto dto = this.getDrainageConnectionDto();
		final UserSession session = UserSession.getCurrent();
		List<DocumentDetailsVO> documents = getCheckList();
		List<DocumentDetailsVO> dtoDocuments = getDrainageConnectionDto().getDocumentList();
		// #148917
       /* validateInputs(documents);
		if (!this.isFree()) {
			validateBean(offline, CommonOfflineMasterValidator.class);
		}*/
	

		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.RightToService.DCS, session.getOrganisation().getOrgid());

		this.getRequestDTO().setServiceId(sm.getSmServiceId());
		this.setServiceName(sm.getSmServiceName());
		this.setDeptId(sm.getTbDepartment().getDpDeptid());

		this.getRequestDTO().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		this.getRequestDTO().setLangId(new Long(UserSession.getCurrent().getLanguageId()));
		this.getRequestDTO().setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		this.getRequestDTO().setDeptId(this.getDeptId());
		// this.getRequestDTO().setPayStatus(MainetConstants.FlagF);

		dto.setDocumentList(documents);

		Double paymentAmount = offline.getAmountToShow();

		final Date sysDate = UtilityService.getSQLDate(new Date());

		dto.setLgIpMac(getClientIpAddress());
		dto.setOrgId(session.getOrganisation().getOrgid());
		dto.setCreatedBy(session.getEmployee().getEmpId());
		dto.setCreatedDate(new Date());

		dto = drainageConnectionService.saveDrainageConnection(this);

		if (dto.getApmApplicationId() != null && dto.getApmApplicationId() != 0) {

			if (!this.isFree()) {
				dto.setChargesMap(this.getChargesMap());
				setOfflineDetailsDTO(offline, this);
			}

			setSuccessMessage(getAppSession().getMessage("DrainageConnectionDTO.successMsg") + this.getDrainageConnectionDto().getApmApplicationId());
			String paymentUrl = "drainageConnection.html";
			sendSmsEmail(this, paymentUrl, PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED);
			flag = true;
		}
		return flag;
	}

	public void sendSmsEmail(DrainageConnectionModel model, String paymentUrl, String msgType) {
		SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
		Organisation org = UserSession.getCurrent().getOrganisation();
		int langId = Utility.getDefaultLanguageId(org);
		smsDto.setOrgId(model.getDrainageConnectionDto().getOrgId());
		smsDto.setLangId(UserSession.getCurrent().getLanguageId());
		smsDto.setAppNo(this.getDrainageConnectionDto().getApmApplicationId().toString());
		smsDto.setServName(MainetConstants.RightToService.DRAINAGECONNECTION);
		smsDto.setMobnumber(model.getRequestDTO().getMobileNo());
		String fullName = String.join(" ",
				Arrays.asList(model.getRequestDTO().getfName(), model.getRequestDTO().getmName(), model.getRequestDTO().getlName()));
		smsDto.setAppName(fullName);
		smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		if (StringUtils.isNotBlank(model.getRequestDTO().getEmail())) {
			smsDto.setEmail(model.getRequestDTO().getEmail());
		}

		ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class)
				.sendEmailSMS(RtsConstants.RTS, paymentUrl, msgType, smsDto, org, langId);

	}

	private void setOfflineDetailsDTO(CommonChallanDTO offline, DrainageConnectionModel model) {
		RequestDTO requestDTO = model.getRequestDTO();

		// offline.setAmountToPay(Double.toString(model.getAmountCharged()));
		offline.setAmountToPay(offline.getAmountToShow().toString());
		// offline.setAmountToPay(Double.toString(100));
		Double paymentAmount = offline.getAmountToShow();
		// Double paymentAmount=100d ;
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setOrgId(requestDTO.getOrgId());
		offline.setDeptId(Long.valueOf(requestDTO.getDeptId()));
		// offline.setDeptId(10l);
		offline.setApplNo(model.getDrainageConnectionDto().getApmApplicationId());

		String fullName = String.join(" ",
				Arrays.asList(requestDTO.getfName(), requestDTO.getmName(), requestDTO.getlName()));
		offline.setApplicantName(fullName);
		String applicantAddress = String.join(" ", Arrays.asList(requestDTO.getBldgName(), requestDTO.getBlockName(),
				requestDTO.getRoadName(), requestDTO.getCityName()));
		offline.setApplicantAddress(applicantAddress);
		offline.setMobileNumber(requestDTO.getMobileNo());
		offline.setEmailId(requestDTO.getEmail());
		offline.setServiceId(requestDTO.getServiceId());
		offline.setUserId(requestDTO.getUserId());
		offline.setLgIpMac(Utility.getMacAddress());
		offline.setLangId(UserSession.getCurrent().getLanguageId());

		if (model.getCheckListApplFlag().equalsIgnoreCase("Y")) {
			offline.setDocumentUploaded(true);
		} else {
			offline.setDocumentUploaded(false);
		}

		for (final Map.Entry<Long, Double> entry : this.getDrainageConnectionDto().getChargesMap().entrySet()) {
			offline.getFeeIds().put(entry.getKey(), entry.getValue());
		}
		setOfflineDTO(offline);

		if (!requestDTO.isFree()) { // offline or pay@ULB
			offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(
					offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());
			offline.setAmountToPay(paymentAmount.toString());

			if (offline.getOnlineOfflineCheck() != null
					&& MainetConstants.N_FLAG.equalsIgnoreCase(offline.getOnlineOfflineCheck())) {
				final ChallanMaster master = iChallanService.InvokeGenerateChallan(offline);
				offline.setChallanValidDate(master.getChallanValiDate());
				offline.setChallanNo(master.getChallanNo());
				setOfflineDTO(offline);

			} else if ((offline.getOnlineOfflineCheck() != null)
					&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
				final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline, "DCS");
				setReceiptDTO(printDto);

			}
			setSuccessMessage(getAppSession().getMessage("Form saved successfully.") + " Application Number : "
					+ this.getRequestDTO().getApplicationId());
		}

//		if (CollectionUtils.isNotEmpty(getCheckList())) {
//			offline.setDocumentUploaded(true);
//		} else {
//			offline.setDocumentUploaded(false);
//		}
//		offline.setLgIpMac(getClientIpAddress());
//		offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
//		offline.setDeptId(Long.valueOf(requestDTO.getDeptId()));
//		offline.setOfflinePaymentText(CommonMasterUtility
//				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
//				.getLookUpCode());
//
//		if ((offline.getOnlineOfflineCheck() != null)
//				&& offline.getOnlineOfflineCheck().equals(MainetConstants.MENU.N)) {
//
//			final ChallanMaster responseChallan = iChallanService.InvokeGenerateChallan(offline);
//
//			offline.setChallanNo(responseChallan.getChallanNo());
//			offline.setChallanValidDate(responseChallan.getChallanValiDate());
//
//			setOfflineDTO(offline);
//		} else if ((offline.getOnlineOfflineCheck() != null)
//				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
//
//			final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline,
//					MainetConstants.RightToService.DCS);
//			setReceiptDTO(printDto);
//
//			setSuccessMessage(getAppSession().getMessage("adh.receipt"));
//		}
//		setOfflineDTO(offline);
//		final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline,
//				MainetConstants.RightToService.DCS);
//		setReceiptDTO(printDto);

	}

	public boolean callWorkFlow(DrainageConnectionModel model) {

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		WorkflowTaskAction workFlowActionDto = getWorkflowActionDto();
		Employee emp = UserSession.getCurrent().getEmployee();
		workFlowActionDto.setOrgId(orgId);
		workFlowActionDto.setEmpId(emp.getEmpId());
		workFlowActionDto.setDecision(getWorkflowActionDto().getDecision());

		List<Long> attacheMentIds = ApplicationContextProvider.getApplicationContext()
				.getBean(IChecklistVerificationService.class)
				.fetchAttachmentIdByAppid(getWorkflowActionDto().getApplicationId(), orgId);

		workFlowActionDto.setAttachementId(attacheMentIds);
		// this code is for document upload start
		ServiceMaster sm = serviceMaster.getServiceMasterByShortCode(MainetConstants.RightToService.DCS, orgId);
		RequestDTO requestDTO = model.getRequestDTO();
		requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
		requestDTO.setOrgId(orgId);
		requestDTO.setDepartmentName(MainetConstants.RightToService.DCS);
		requestDTO.setServiceId(sm.getSmServiceId());
		requestDTO.setUserId(emp.getEmpId());
		requestDTO.setApplicationId(model.getDrainageConnectionDto().getApmApplicationId());
		// requestDTO.setDeptId(10l); //change here
		requestDTO.setDeptId(sm.getTbDepartment().getDpDeptid());
		List<DocumentDetailsVO> docs = new ArrayList<>();
		DocumentDetailsVO document = new DocumentDetailsVO();
		document.setDocumentSerialNo(1L);
		docs.add(document);
		docs = fileUploadService.prepareFileUpload(docs);
		getDrainageConnectionDto().setDocumentList(docs);
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL) && this.lastChecker) {
			saveLoiData();
		}
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL) && model.getLevelcheck()==1 && model.getDrainageConnectionDto().getRoadType()!=null && model.getDrainageConnectionDto().getLenRoad()!=null )
			drainageConnectionService.updateRoadLength(model.getDrainageConnectionDto().getApmApplicationId(),model.getDrainageConnectionDto().getOrgId(),
					model.getDrainageConnectionDto().getRoadType(),model.getDrainageConnectionDto().getLenRoad());
		// this code is for document upload end
		return drainageConnectionService.saveDecision(model, workFlowActionDto, emp);

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

	
	public boolean saveLoiData() {
		boolean status = false;
        getWorkflowActionDto().setApplicationId(drainageConnectionDto.getApmApplicationId());
		ServiceMaster serviceMas = serviceMaster.getServiceByShortName(MainetConstants.RightToService.DCS,
				UserSession.getCurrent().getOrganisation().getOrgid());
		List<TbLoiDet> loiDet = new ArrayList<>();

		setServiceId(serviceMas.getSmServiceId());
		if (MapUtils.isNotEmpty(getChargesMap())) {
			setChargeMap(this, this.getChargesList());
			String comments=getWorkflowActionDto().getComments();
			getWorkflowActionDto().setComments(this.getDrainageConnectionDto().getPropertyIndexNo());
			TbLoiMas tbLoiMas = ApplicationContextProvider.getApplicationContext().getBean(LoiGenerationModel.class)
					.saveLOIAppData(getChargesMap(), getServiceId(), loiDet,false, getWorkflowActionDto());
			if (StringUtils.isNotBlank(tbLoiMas.getLoiNo())) {
				setLoiNo(tbLoiMas.getLoiNo());
			}
			getWorkflowActionDto().setComments(comments);
			status = true;
		}else {
			/*model.addValidationError(
					getApplicationSession().getMessage("Problrm occured while fetching LOI Charges from BRMS Sheet"));*/
		}
		return status;

	}
	
	private void setChargeMap(DrainageConnectionModel model, final List<MediaChargeAmountDTO> charges) {
		final Map<Long, Double> chargesMap = new HashMap<>();
		for (final MediaChargeAmountDTO dto : charges) {
			chargesMap.put(dto.getTaxId(), dto.getChargeAmount());
		}
		model.setChargesList(charges);
		model.setChargesMap(chargesMap);
	}
	/*public boolean validateInputs() {
		validateBean(this, DrainageConnectionFormValidator.class);

		if (hasValidationErrors()) {
			return false;
		}
		return true;
	}*/
	
	public boolean validateInputs() {
		if (getServiceMasterData().getSmFeesSchedule().longValue() != 0l) {
			if (getServiceMasterData().getSmAppliChargeFlag().equals(MainetConstants.FlagY)) {
				validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);
			}
		}
		if (hasValidationErrors()) {
			return false;
		}
		return true;
	}

	public Map<Long, String> getDepList() {
		return depList;
	}

	public void setDepList(Map<Long, String> depList) {
		this.depList = depList;
	}

	public Map<Long, String> getServiceList() {
		return serviceList;
	}

	public void setServiceList(Map<Long, String> serviceList) {
		this.serviceList = serviceList;
	}

	public RequestDTO getRequestDTO() {
		return requestDTO;
	}

	public void setRequestDTO(RequestDTO requestDTO) {
		this.requestDTO = requestDTO;
	}

	public DrainageConnectionDto getDrainageConnectionDto() {
		return drainageConnectionDto;
	}

	public void setDrainageConnectionDto(DrainageConnectionDto drainageConnectionDto) {
		this.drainageConnectionDto = drainageConnectionDto;
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

	public Map<Long, Double> getChargesMap() {
		return chargesMap;
	}

	public void setChargesMap(Map<Long, Double> chargesMap) {
		this.chargesMap = chargesMap;
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

	public CommonChallanDTO getOfflineDTO() {
		return offlineDTO;
	}

	public void setOfflineDTO(CommonChallanDTO offlineDTO) {
		this.offlineDTO = offlineDTO;
	}

	public List<MediaChargeAmountDTO> getChargesList() {
		return chargesList;
	}

	public void setChargesList(List<MediaChargeAmountDTO> chargesList) {
		this.chargesList = chargesList;
	}

	public ApplicantDetailDTO getApplicantDTO() {
		return applicantDTO;
	}

	public void setApplicantDTO(ApplicantDetailDTO applicantDTO) {
		this.applicantDTO = applicantDTO;
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

	public WorkflowTaskAction getWorkflowTaskActionDTO() {
		return WorkflowTaskActionDTO;
	}

	public void setWorkflowTaskActionDTO(WorkflowTaskAction workflowTaskActionDTO) {
		WorkflowTaskActionDTO = workflowTaskActionDTO;
	}

	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	public TbCfcApplicationMstEntity getCfcEntity() {
		return cfcEntity;
	}

	public void setCfcEntity(TbCfcApplicationMstEntity cfcEntity) {
		this.cfcEntity = cfcEntity;
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

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public boolean isLastChecker() {
		return lastChecker;
	}

	public void setLastChecker(boolean lastChecker) {
		this.lastChecker = lastChecker;
	}

	public String getLoiChargeApplFlag() {
		return loiChargeApplFlag;
	}

	public void setLoiChargeApplFlag(String loiChargeApplFlag) {
		this.loiChargeApplFlag = loiChargeApplFlag;
	}

	public Double getTotalLoiAmount() {
		return totalLoiAmount;
	}

	public void setTotalLoiAmount(Double totalLoiAmount) {
		this.totalLoiAmount = totalLoiAmount;
	}

	public String getTaxDesc() {
		return taxDesc;
	}

	public void setTaxDesc(String taxDesc) {
		this.taxDesc = taxDesc;
	}

	public String getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(String showFlag) {
		this.showFlag = showFlag;
	}

	public List<TbLoiDet> getLoiDetail() {
		return loiDetail;
	}

	public void setLoiDetail(List<TbLoiDet> loiDetail) {
		this.loiDetail = loiDetail;
	}

	public String getLoiNo() {
		return loiNo;
	}

	public void setLoiNo(String loiNo) {
		this.loiNo = loiNo;
	}

	public List<ChargeDetailDTO> getChargesInfo() {
		return chargesInfo;
	}

	public void setChargesInfo(List<ChargeDetailDTO> chargesInfo) {
		this.chargesInfo = chargesInfo;
	}

	public long getLevelcheck() {
		return levelcheck;
	}

	public void setLevelcheck(long levelcheck) {
		this.levelcheck = levelcheck;
	}

	public String getViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(String viewFlag) {
		this.viewFlag = viewFlag;
	}

	public String getAppCheck() {
		return appCheck;
	}

	public void setAppCheck(String appCheck) {
		this.appCheck = appCheck;
	}

}
