package com.abm.mainet.workManagement.roadcutting.ui.model;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.ITaskService;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.workManagement.roadcutting.service.IRoadCuttingService;
import com.abm.mainet.workManagement.roadcutting.ui.dto.RoadCuttingDto;
import com.abm.mainet.workManagement.roadcutting.ui.dto.RoadRouteDetailsDto;
import com.abm.mainet.workManagement.roadcutting.ui.validator.RoadCuttingValidator;

/**
 * @author satish.rathore
 *
 */
@Component
@Scope("session")
public class RoadCuttingModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4888156501781601514L;

	@Autowired
	private IRoadCuttingService roadCuttingService;

	@Autowired
	private IChallanService iChallanService;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private IChecklistVerificationService checklistVerificationService;

	@Autowired
	private TbLoiMasService iTbLoiMasService;

	@Autowired
	private IWorkFlowTypeService workFlowTypeService;

	@Autowired
	private IWorkflowActionService iWorkflowActionService;

	@Autowired
	private ILocationMasService iLocationMasService;

	@Autowired
	private CommonService commonService;
	
	 @Autowired
	 private IWorkflowRequestService requestService;
	 
	 @Autowired
	 private ITaskService taskService;
	    

	private String appliChargeFlag;
	private String roadCuttingLevelState;

	private RoadCuttingDto roadCuttingDto = new RoadCuttingDto();
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private List<CFCAttachment> documentList = new ArrayList<>();
	private String isFree;
	private List<ChargeDetailDTO> chargesInfo;
	private Double charges = 0.0d;
	private CommonChallanDTO offlineDTO = new CommonChallanDTO();
	private Map<Long, Double> chargesMap = new HashMap<>();
	private String free = "O";
	private BigDecimal amountToPaid;
	private ServiceMaster serviceMaster = new ServiceMaster();
	private Long orgId;
	private List<LookUp> listTEC = new ArrayList<>();
	private List<LookUp> listROT = new ArrayList<>();
	private Map<Long, Double> loiCharges;
	private Map<String, Double> chargeDesc = new HashMap<>(0);
	private Double total = new Double(0d);
	private Map<Integer, AttachDocs> projectLocationDoc = new HashMap<>(0);

	private String checkNOCApplicable;
	private List<RoadCuttingDto> roadCuttingDtoList = new ArrayList<>();
	
	 @Autowired
	 private IWorkflowTaskService iWorkflowTaskService;

	private String hideScrutinyBtn;
	private String viewMap;
	private String authFlag;// when auth is Yes
	private String saveButFlag;
	private String checkListFlag;
	private String chargeService;
	private String purpose;
	private String purposeValue;
	private Long empZone;
	private boolean allChildWorkFlowInitiated;
    private boolean allChildWorkFlowCompleted;

	/**
	 * @return the listTEC
	 */
	public List<LookUp> getListTEC() {
		return listTEC;
	}

	/**
	 * @param listTEC
	 *            the listTEC to set
	 */
	public void setListTEC(List<LookUp> listTEC) {
		this.listTEC = listTEC;
	}

	/**
	 * @return the listROT
	 */
	public List<LookUp> getListROT() {
		return listROT;
	}

	/**
	 * @param listROT
	 *            the listROT to set
	 */
	public void setListROT(List<LookUp> listROT) {
		this.listROT = listROT;
	}

	/**
	 * @return the serviceMaster
	 */
	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	/**
	 * @param serviceMaster
	 *            the serviceMaster to set
	 */
	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId
	 *            the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the free
	 */
	public String getFree() {
		return free;
	}

	/**
	 * @param free
	 *            the free to set
	 */
	public void setFree(String free) {
		this.free = free;
	}

	/**
	 * @return the chargesMap
	 */
	public Map<Long, Double> getChargesMap() {
		return chargesMap;
	}

	/**
	 * @param chargesMap
	 *            the chargesMap to set
	 */
	public void setChargesMap(Map<Long, Double> chargesMap) {
		this.chargesMap = chargesMap;
	}

	/**
	 * @return the isFree
	 */
	public String getIsFree() {
		return isFree;
	}

	/**
	 * @param isFree
	 *            the isFree to set
	 */
	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}

	/**
	 * @return the chargesInfo
	 */
	public List<ChargeDetailDTO> getChargesInfo() {
		return chargesInfo;
	}

	/**
	 * @param chargesInfo
	 *            the chargesInfo to set
	 */
	public void setChargesInfo(List<ChargeDetailDTO> chargesInfo) {
		this.chargesInfo = chargesInfo;
	}

	/**
	 * @return the charges
	 */
	public Double getCharges() {
		return charges;
	}

	/**
	 * @param charges
	 *            the charges to set
	 */
	public void setCharges(Double charges) {
		this.charges = charges;
	}

	/**
	 * @return the offlineDTO
	 */
	public CommonChallanDTO getOfflineDTO() {
		return offlineDTO;
	}

	/**
	 * @param offlineDTO
	 *            the offlineDTO to set
	 */
	public void setOfflineDTO(CommonChallanDTO offlineDTO) {
		this.offlineDTO = offlineDTO;
	}

	/**
	 * @return the roadCuttingDto
	 */
	public RoadCuttingDto getRoadCuttingDto() {
		return roadCuttingDto;
	}

	/**
	 * @param roadCuttingDto
	 *            the roadCuttingDto to set
	 */
	public void setRoadCuttingDto(RoadCuttingDto roadCuttingDto) {
		this.roadCuttingDto = roadCuttingDto;
	}

	/**
	 * @return the checkList
	 */
	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	/**
	 * @param checkList
	 *            the checkList to set
	 */
	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	/**
	 * @return the amountToPaid
	 */
	public BigDecimal getAmountToPaid() {
		return amountToPaid;
	}

	/**
	 * @param amountToPaid
	 *            the amountToPaid to set
	 */
	public void setAmountToPaid(BigDecimal amountToPaid) {
		this.amountToPaid = amountToPaid;
	}

	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	public Map<Long, Double> getLoiCharges() {
		return loiCharges;
	}

	public void setLoiCharges(Map<Long, Double> loiCharges) {
		this.loiCharges = loiCharges;
	}

	public Map<String, Double> getChargeDesc() {
		return chargeDesc;
	}

	public void setChargeDesc(Map<String, Double> chargeDesc) {
		this.chargeDesc = chargeDesc;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	/**
	 * @return the projectLocationDoc
	 */
	public Map<Integer, AttachDocs> getProjectLocationDoc() {
		return projectLocationDoc;
	}

	/**
	 * @param projectLocationDoc
	 *            the projectLocationDoc to set
	 */
	protected void initializeModel() {
		// initializeLookupFields("RCZ");
	}

	public void setProjectLocationDoc(Map<Integer, AttachDocs> projectLocationDoc) {
		this.projectLocationDoc = projectLocationDoc;
	}

	/* for validating Inputs and other functional validation */
	public boolean validateInputs() {
		roadCuttingDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		validateBean(roadCuttingDto, RoadCuttingValidator.class);
		if (!roadCuttingDto.isFree() && "Y".equals(this.getAppliChargeFlag()))
			validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);
		if (hasValidationErrors()) {
			// this.isValidationError = MainetConstants.Y_FLAG;
			return false;
		}
		return true;
	}
	/* end of validation method */

	public boolean validateInputWithNoConfig() {
		roadCuttingDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		validateBean(roadCuttingDto, RoadCuttingValidator.class);
		if (hasValidationErrors()) {
			// this.isValidationError = MainetConstants.Y_FLAG;
			return false;
		}
		return true;
	}

	@Override
	public boolean saveForm() {

		if (null != getAuthFlag() && getAuthFlag().equals("Y")) {

			String wfIdstr = UserSession.getCurrent().getScrutinyCommonParamMap()
					.get(MainetConstants.SCRUTINY_COMMON_PARAM.WORK_FLOW_ID);
			if (wfIdstr != null) {
				Long wfId = Long.parseLong(wfIdstr);
				WorkflowMas wfmass = workFlowTypeService.getWorkFlowById(wfId);
				getWorkflowActionDto().setApplicationId(getRoadCuttingDto().getApplicationId());
				getWorkflowActionDto().setTaskId(getTaskId());
				iWorkflowActionService.updateWorkFlow(this.getWorkflowActionDto(),
						UserSession.getCurrent().getEmployee(), UserSession.getCurrent().getOrganisation().getOrgid(),
						wfmass.getService().getSmServiceId());
			}
		}

		Employee employee = UserSession.getCurrent().getEmployee();
		roadCuttingDto.setUpdatedBy(employee.getEmpId());
		roadCuttingDto.setUpdatedDate(new Date());
		roadCuttingDto.setLgIpMacUpd(employee.getEmppiservername());

		roadCuttingService.updateRoadCutting(roadCuttingDto);

		setSuccessMessage(getAppSession().getMessage("roadcuttingsavesuccess"));

		return true;
	};

	public boolean saveRoadCuttingForm() {

		setCommonFields(this);

		// Add Project Location Image Save Code
		prepareDocumentsData();

		RoadCuttingDto requestDTO = roadCuttingService.saveRoadCutting(roadCuttingDto);

		CommonChallanDTO offline = getOfflineDTO();
		// RoadCuttingDto requestDTO = this.getRoadCuttingDto();
		requestDTO.setChargesMap(this.getChargesMap());
		setOfflineDetailsDTO(offline, getRoadCuttingDto());
		offline.setApplicantAddress(requestDTO.getAreaName());
		offline.setDocumentUploaded(!FileUploadUtility.getCurrent().getFileMap().isEmpty());
		if ("N".equals(this.appliChargeFlag)) {
			initializeWorkFlowForFreeService(roadCuttingDto);

		} else { // offline or pay@ULB

			offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(
					offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());
			offline.setAmountToPay(amountToPaid.toString());

			if (offline.getOnlineOfflineCheck() != null
					&& MainetConstants.N_FLAG.equalsIgnoreCase(offline.getOnlineOfflineCheck())) {
				final ChallanMaster challanNumber = iChallanService.InvokeGenerateChallan(offline);
				offline.setChallanNo(challanNumber.getChallanNo());
				offline.setChallanValidDate(challanNumber.getChallanValiDate());
				setOfflineDTO(offline);
			} else if ((offline.getOnlineOfflineCheck() != null)
					&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
				ChallanReceiptPrintDTO receiptDTO = iChallanService.savePayAtUlbCounter(offline,
						requestDTO.getServiceShortCode());
				setReceiptDTO(receiptDTO);

			}
		}
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA))
			setSuccessMessage(getAppSession().getMessage("roadcuttingsavesuccess") + MainetConstants.WHITE_SPACE
					+ getAppSession().getMessage("roadcutting.application.number") + MainetConstants.COLON_SEPARATOR
					+ requestDTO.getApplicationId());
		else
			setSuccessMessage(getAppSession().getMessage("roadcuttingsavesuccess") + getAppSession().getMessage("roadcutting.application.number")
				+ requestDTO.getReferenceId());
		return true;
	}

	private void initializeWorkFlowForFreeService(RoadCuttingDto requestDto) {
		boolean checkList = false;
		if (requestDto.getDocumentList() != null && !requestDto.getDocumentList().isEmpty()) {
			checkList = true;
		}
		
		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();

		ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		applicantDto.setApplicantFirstName(requestDto.getfName());
		applicantDto.setDepartmentId(
				departmentService.getDepartmentIdByDeptCode(MainetConstants.WorksManagement.WORKS_MANAGEMENT));
		applicantDto.setMobileNo("NA");
		applicantDto.setUserId(requestDto.getUserId());
		String serviceShortCode=null;
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)){
			if ("N".equals(this.getPurposeValue())) {
				List<LookUp> lookUpList = CommonMasterUtility.getNextLevelData("RCZ", 1,
					UserSession.getCurrent().getOrganisation().getOrgid());
				for (LookUp lookUp2 : lookUpList) {
					if ("ZP".equals(lookUp2.getLookUpCode())) {
						applicantDto.setDwzid1(lookUp2.getLookUpId());
						break;
					}
				}
			// applicantDto.setDwzid2(requestDto.getRoadList().get(0).getCodZoneward2());
				serviceShortCode=MainetConstants.RoadCuttingConstant.RCP;
			} else {
				applicantDto.setDwzid1(requestDto.getRoadList().get(0).getCodZoneward1());
				// applicantDto.setDwzid2(requestDto.getRoadList().get(0).getCodZoneward2());
				serviceShortCode=MainetConstants.RoadCuttingConstant.RCW;
			}
		}
		else{
			serviceShortCode=MainetConstants.RoadCuttingConstant.RCP;
		}
		// applicantDto.setDwzid1(requestDto.getCodWard1());
		// applicantDto.setDwzid2(requestDto.getCodWard2());
		applicationMetaData.setApplicationId(requestDto.getApplicationId());
		applicationMetaData.setReferenceId(requestDto.getReferenceId());
		applicationMetaData.setIsCheckListApplicable(checkList);
		applicationMetaData.setOrgId(requestDto.getOrgId());
		applicationMetaData.setIsApprReqInObjection("Y");
		ServiceMaster service = serviceMasterService.getServiceByShortName(serviceShortCode,
				UserSession.getCurrent().getOrganisation().getOrgid());
		applicantDto.setServiceId(service.getSmServiceId());
		
		try {
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}
	}

	private void setOfflineDetailsDTO(CommonChallanDTO offline, RoadCuttingDto requestDTO) {
		/* Setting Offline DTO */
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setOrgId(requestDTO.getOrgId());
		offline.setDeptId(requestDTO.getWmsDeptId());
		offline.setApplNo(requestDTO.getApplicationId());

		String fullName = String.join(" ",
				Arrays.asList(requestDTO.getfName(), requestDTO.getmName(), requestDTO.getlName()));
		offline.setApplicantName(fullName);
		String applicantAddress = String.join(" ", Arrays.asList(requestDTO.getFlatBuildingNo(),
				requestDTO.getBlockName(), requestDTO.getRoadName(), requestDTO.getCityName()));
		offline.setApplicantAddress(applicantAddress);
		offline.setMobileNumber(requestDTO.getMobileNo());
		offline.setEmailId(requestDTO.getEmail());
		offline.setServiceId(requestDTO.getServiceId());
		offline.setUserId(requestDTO.getUserId());
		offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		for (final Map.Entry<Long, Double> entry : requestDTO.getChargesMap().entrySet()) {
			offline.getFeeIds().put(entry.getKey(), entry.getValue());
		}
		setOfflineDTO(offline);

	}

	private void setCommonFields(RoadCuttingModel model) {
		final Date sysDate = new Date();
		final RoadCuttingDto dto = model.getRoadCuttingDto();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		dto.setOrgId(orgId);
		Organisation org = new Organisation();
		org.setOrgid(orgId);
		model.setOrgId(orgId);
		String serviceShortCode=null;
		if ("N".equals(this.getPurposeValue()) || !Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) {
			dto.setMobileNo(dto.getPersonMobileNo2() != null ? dto.getPersonMobileNo2().toString() : "");
			dto.setAreaName(dto.getPersonAddress2());
			dto.setEmail(dto.getPersonEmailId2());
			dto.setfName(dto.getPersonName1());
			dto.setmName("");
			dto.setlName("");
			serviceShortCode=MainetConstants.RoadCuttingConstant.RCP;
		} else {
			dto.setMobileNo(dto.getApplicantDetailDto().getMobileNo());
			dto.setAreaName(dto.getApplicantDetailDto().getAreaName());
			dto.setEmail("");
			dto.setfName(dto.getApplicantDetailDto().getApplicantFirstName());
			dto.setmName(dto.getApplicantDetailDto().getApplicantMiddleName());
			dto.setlName(dto.getApplicantDetailDto().getApplicantLastName());
			serviceShortCode=MainetConstants.RoadCuttingConstant.RCW;
		}
		final ServiceMaster service = serviceMasterService
				.getServiceByShortName(serviceShortCode, orgId);
		model.setServiceMaster(service);
		model.setServiceId(service.getSmServiceId());
		model.getRoadCuttingDto().setServiceId(service.getSmServiceId());
		dto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
		dto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		dto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		dto.setLgIpMac(dto.getLgIpMac());
		dto.setUserId(dto.getCreatedBy());
		dto.setlModDate(sysDate);
		dto.setApmApplicationDate(sysDate);
		dto.setCreatedDate(sysDate);
		dto.setServiceShortCode(
				UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH ? service.getSmServiceName()
						: service.getSmServiceName());
		long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
		dto.setDeptId(deptId);
		dto.setWmsDeptId(deptId);
	}
	
	/*As discussed with Samadhan Sir, we are now not showing scrutiny button zone wise*/
	/*@Override
	public void populateApplicationData(long applicationId){

		long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.setRoadCuttingDto(roadCuttingService.getRoadCuttingApplicationData(applicationId, orgId));
		this.setPurposeValue(roadCuttingDto.getPurposeValue());
		if ("N".equals(this.getRoadCuttingDto().getPurposeValue())) {
			long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
			LocationMasEntity entity = UserSession.getCurrent().getEmployee().getTbLocationMas();
			if(iLocationMasService.findOperLocationAndDeptId(entity.getLocId(),deptId) == null){
			throw new FrameworkException("Employee is not mapped with Public Works Department");
			}
			this.setEmpZone(iLocationMasService.findOperLocationAndDeptId(entity.getLocId(),deptId).getCodIdOperLevel1());
			// List<RoadRouteDetailsDto> detailsList =
			// this.getRoadCuttingDto().getRoadList();
			List<RoadRouteDetailsDto> scrutinyPerformList = this.getRoadCuttingDto().getRoadList().stream()
					.filter(r -> r.getCodZoneward1().equals(this.getEmpZone())).collect(Collectors.toList());
			List<RoadRouteDetailsDto> disableDeatilsList = this.getRoadCuttingDto().getRoadList().stream()
					.filter(r -> !r.getCodZoneward1().equals(this.getEmpZone())).collect(Collectors.toList());
			
			Optional<RoadRouteDetailsDto> details = this.getRoadCuttingDto().getRoadList().stream()
					.filter(r -> !"I".equals(r.getApmAppStatus())).findAny();
			if (details.isPresent())
				this.setAllChildWorkFlowInitiated(false);
			else
				this.setAllChildWorkFlowInitiated(true);
			List<String> statusList =new ArrayList<>();
			for (RoadRouteDetailsDto roadRouteDetailsDto : scrutinyPerformList) {
				if("I".equals(roadRouteDetailsDto.getApmAppStatus())) {
			      roadRouteDetailsDto.setWorkFlowAppStatus(requestService.getWorkflowRequestByAppIdOrRefId(null, roadRouteDetailsDto.getRefId(), orgId).getStatus());
				}
				statusList.add(roadRouteDetailsDto.getWorkFlowAppStatus() == null ? "" :roadRouteDetailsDto.getWorkFlowAppStatus());
			}
			for (RoadRouteDetailsDto roadRouteDetailsDto : disableDeatilsList) { 
				if("I".equals(roadRouteDetailsDto.getApmAppStatus())) {
					roadRouteDetailsDto.setWorkFlowAppStatus(requestService.getWorkflowRequestByAppIdOrRefId(null, roadRouteDetailsDto.getRefId(), orgId).getStatus());
				}
				statusList.add(roadRouteDetailsDto.getWorkFlowAppStatus() == null ? "" :roadRouteDetailsDto.getWorkFlowAppStatus());
			}
			Optional<String> details = statusList.stream()
                    .filter(status -> !"CLOSED".equals(status)).findAny();
            if (details.isPresent())
                this.setAllChildWorkFlowCompleted(false);
            else
                this.setAllChildWorkFlowCompleted(true);
            this.setAllChildWorkFlowInitiated(true);
            for (RoadRouteDetailsDto roadRouteDetailsDto : scrutinyPerformList) {
                if(!"I".equals(roadRouteDetailsDto.getApmAppStatus())) {
                    this.setAllChildWorkFlowInitiated(false);
                    break;
                }
            }
			
			this.getRoadCuttingDto().setScrutinyPerformList(scrutinyPerformList);
			this.getRoadCuttingDto().setDisableDetailsList(disableDeatilsList);
			
			final ServiceMaster service = serviceMasterService.getServiceByShortName(
					MainetConstants.RoadCuttingConstant.RCP, UserSession.getCurrent().getOrganisation().getOrgid());
			//UserTaskDTO userTaskdto= iWorkflowTaskService.findByTaskId(Long.parseLong(taskId));
			if(MainetConstants.RoadCuttingConstant.RCP.equals(service.getSmShortdesc())){
				List<UserTaskDTO> list =null;
				//List<UserTaskDTO> taskId = new ArrayList<>();
		         try {
					list = taskService.getTaskList(String.valueOf(applicationId));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
				
				WorkflowProcessParameter workflowdto = null;
				WorkflowTaskAction workflowAction = null;
				List<WorkflowProcessParameter> WorkflowProcessParameterList =new ArrayList<>();
		        for (UserTaskDTO userTaskDTO : list) {
					workflowdto = new WorkflowProcessParameter();
					workflowAction = new WorkflowTaskAction();
					workflowAction.setIsLoiGenerated(false);
					workflowAction.setTaskId(userTaskDTO.getTaskId());
					workflowAction.setApplicationId(userTaskDTO.getApplicationId());
					workflowAction.setDateOfAction(new Date());
					workflowAction.setDecision("REJECTED");
					workflowAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
					workflowAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
					workflowAction.setModifiedBy(UserSession.getCurrent().getEmployee().getEmpId());
					workflowAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
					workflowAction.setEmpName(UserSession.getCurrent().getEmployee().getEmpname());
					workflowAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
					workflowAction.setCreatedDate(new Date());
					workflowAction.setEmpGroupDescReg("RCP");
					workflowAction.setEmpEmail("PSCL");
					workflowdto.setProcessName("scrutiny");
					workflowdto.setWorkflowTaskAction(workflowAction);
					WorkflowProcessParameterList.add(workflowdto);
				}
				UserSession.getCurrent().getObjectMap().put("RejectTask", WorkflowProcessParameterList);
				}
		}
		this.documentList = checklistVerificationService.getDocumentUploaded(applicationId, orgId);
		
		final ServiceMaster service = serviceMasterService.getServiceByShortName(
				MainetConstants.RoadCuttingConstant.RCP, UserSession.getCurrent().getOrganisation().getOrgid());

		// Reset Session Data
		Map<Long, Double> charges = new HashMap<>();
		this.chargeDesc = new HashMap<>();
		this.setTotal(0D);
		if (service.getSmScrutinyChargeFlag().equals(MainetConstants.Common_Constant.YES) && "Y".equals(this.getRoadCuttingDto().getPurposeValue())) {
			try {

				this.setListTEC(CommonMasterUtility.getLookUps("TEC", UserSession.getCurrent().getOrganisation()));
				this.setListROT(CommonMasterUtility.getLookUps("ROT", UserSession.getCurrent().getOrganisation()));

				charges = roadCuttingService.getLoiCharges(applicationId, service.getSmServiceId(), orgId);

				setLoiCharges(charges);

				if (charges != null && !charges.isEmpty()) {
					final List<Long> ids = new ArrayList<>(0);
					for (final Entry<Long, Double> entry : charges.entrySet()) {
						ids.add(entry.getKey());
					}
					final Map<Long, String> chargedesc = iTbLoiMasService.getChargeDescByChgId(ids, orgId, null);
					if ((chargedesc != null) && !chargedesc.isEmpty()) {
						for (final Entry<Long, Double> entry : charges.entrySet()) {
							for (final Entry<Long, String> entrydesc : chargedesc.entrySet()) {
								if (entry.getKey().equals(entrydesc.getKey())) {
									chargeDesc.put(entrydesc.getValue(), entry.getValue());
									if (entry.getValue() != null) {
										setTotal(getTotal() + entry.getValue());
									}
								}
							}
						}
					}
				}
			} catch (CloneNotSupportedException e) {
				throw new FrameworkException(e);
			}
		}
		IAttachDocsService attachDocsService = (IAttachDocsService) ApplicationContextProvider.getApplicationContext()
				.getBean("attachDocsServiceImpl");
		Map<Integer, AttachDocs> docs = attachDocsService
				.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
						MainetConstants.RoadCuttingConstant.PROJECT_LOCATION + MainetConstants.DOUBLE_BACK_SLACE
								+ applicationId)
				.stream().collect(Collectors.toMap(AttachDocs::getSerialNo, Function.identity()));
		this.setProjectLocationDoc(docs);

		String lookUp = CommonMasterUtility.getDefaultValueByOrg("NOA", UserSession.getCurrent().getOrganisation())
				.getLookUpCode();
		this.setCheckNOCApplicable(lookUp);
		this.setRoadCuttingDtoList(roadCuttingService.getNOCDepartmentTable(orgId));
		List<RoadCuttingDto> dtos = getRoadCuttingDtoList();
		this.setRoadCuttingDtoList(roadCuttingService.getNOCViewStatusDetail(dtos, applicationId));

		// this code will be change after proper design
		String wfIdstr = UserSession.getCurrent().getScrutinyCommonParamMap()
				.get(MainetConstants.SCRUTINY_COMMON_PARAM.WORK_FLOW_ID);
		if (wfIdstr != null) {
			Long wfId = Long.parseLong(wfIdstr);
			WorkflowMas wfmass = workFlowTypeService.getWorkFlowById(wfId);
			if (wfmass.getService().getSmShortdesc().contains("NOC")) {
				setHideScrutinyBtn("N");
				setViewMap("Y");
				this.setAuthFlag("Y");
			} else {
				setHideScrutinyBtn("Y");
				setViewMap("N");
			}
		} else {
			setHideScrutinyBtn("Y");
			setViewMap("N");
		}
		
	}*/
	
	@Override
	public void populateApplicationData(long applicationId){

		long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.setRoadCuttingDto(roadCuttingService.getRoadCuttingApplicationData(applicationId, orgId));
		this.setPurposeValue(roadCuttingDto.getPurposeValue());
		if ("N".equals(this.getRoadCuttingDto().getPurposeValue())) {
			long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
			LocationMasEntity entity = UserSession.getCurrent().getEmployee().getTbLocationMas();
			String taskId = UserSession.getCurrent().getScrutinyCommonParamMap()
                    .get(MainetConstants.SCRUTINY_COMMON_PARAM.TASK_ID);
			UserTaskDTO userTaskdto= iWorkflowTaskService.findByTaskId(Long.parseLong(taskId));
			/*try{
				this.setEmpZone(iLocationMasService.findOperLocationAndDeptId(entity.getLocId(),deptId).getCodIdOperLevel1());
			}
			catch(Exception E){
				throw new FrameworkException("Employee is not mapped", E);
			}*/
			this.setEmpZone(iLocationMasService.findOperLocationAndDeptId(entity.getLocId(),deptId).getCodIdOperLevel1());
			List<RoadRouteDetailsDto> scrutinyPerformList = null;
			if(userTaskdto.getReferenceId().startsWith("Z")){
			scrutinyPerformList = this.getRoadCuttingDto().getRoadList().stream()
					.filter(r -> r.getCodZoneward1().equals(this.getEmpZone())).collect(Collectors.toList());
			}else{
			scrutinyPerformList = this.getRoadCuttingDto().getRoadList();
			}
			/*List<RoadRouteDetailsDto> disableDeatilsList = this.getRoadCuttingDto().getRoadList().stream()
					.filter(r -> !r.getCodZoneward1().equals(this.getEmpZone())).collect(Collectors.toList());*/
			
			/*Optional<RoadRouteDetailsDto> details = this.getRoadCuttingDto().getRoadList().stream()
					.filter(r -> !"I".equals(r.getApmAppStatus())).findAny();
			if (details.isPresent())
				this.setAllChildWorkFlowInitiated(false);
			else
				this.setAllChildWorkFlowInitiated(true);*/
			List<String> statusList =new ArrayList<>();
			for (RoadRouteDetailsDto roadRouteDetailsDto : scrutinyPerformList) {
				if("I".equals(roadRouteDetailsDto.getApmAppStatus())) {
			      roadRouteDetailsDto.setWorkFlowAppStatus(requestService.getWorkflowRequestByAppIdOrRefId(null, roadRouteDetailsDto.getRefId(), orgId).getStatus());
				}
				statusList.add(roadRouteDetailsDto.getWorkFlowAppStatus() == null ? "" :roadRouteDetailsDto.getWorkFlowAppStatus());
			}
			/*for (RoadRouteDetailsDto roadRouteDetailsDto : disableDeatilsList) { 
				if("I".equals(roadRouteDetailsDto.getApmAppStatus())) {
					roadRouteDetailsDto.setWorkFlowAppStatus(requestService.getWorkflowRequestByAppIdOrRefId(null, roadRouteDetailsDto.getRefId(), orgId).getStatus());
				}
				statusList.add(roadRouteDetailsDto.getWorkFlowAppStatus() == null ? "" :roadRouteDetailsDto.getWorkFlowAppStatus());
			}*/
			Optional<String> details = statusList.stream()
                    .filter(status -> !"CLOSED".equals(status)).findAny();
            if (details.isPresent())
                this.setAllChildWorkFlowCompleted(false);
            else
                this.setAllChildWorkFlowCompleted(true);
            this.setAllChildWorkFlowInitiated(true);
            for (RoadRouteDetailsDto roadRouteDetailsDto : scrutinyPerformList) {
                if(!"I".equals(roadRouteDetailsDto.getApmAppStatus())) {
                    this.setAllChildWorkFlowInitiated(false);
                    break;
                }
            }
			
			this.getRoadCuttingDto().setScrutinyPerformList(scrutinyPerformList);
			/*this.getRoadCuttingDto().setDisableDetailsList(disableDeatilsList);*/
			
			final ServiceMaster service = serviceMasterService.getServiceByShortName(
					MainetConstants.RoadCuttingConstant.RCP, UserSession.getCurrent().getOrganisation().getOrgid());
			//UserTaskDTO userTaskdto= iWorkflowTaskService.findByTaskId(Long.parseLong(taskId));
			if(MainetConstants.RoadCuttingConstant.RCP.equals(service.getSmShortdesc())){
				List<UserTaskDTO> list =null;
				//List<UserTaskDTO> taskId = new ArrayList<>();
		         try {
					list = taskService.getTaskList(String.valueOf(applicationId));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
				
				WorkflowProcessParameter workflowdto = null;
				WorkflowTaskAction workflowAction = null;
				List<WorkflowProcessParameter> WorkflowProcessParameterList =new ArrayList<>();
		        for (UserTaskDTO userTaskDTO : list) {
					workflowdto = new WorkflowProcessParameter();
					workflowAction = new WorkflowTaskAction();
					workflowAction.setIsLoiGenerated(false);
					workflowAction.setTaskId(userTaskDTO.getTaskId());
					workflowAction.setApplicationId(userTaskDTO.getApplicationId());
					workflowAction.setDateOfAction(new Date());
					workflowAction.setDecision("REJECTED");
					workflowAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
					workflowAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
					workflowAction.setModifiedBy(UserSession.getCurrent().getEmployee().getEmpId());
					workflowAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
					workflowAction.setEmpName(UserSession.getCurrent().getEmployee().getEmpname());
					workflowAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
					workflowAction.setCreatedDate(new Date());
					workflowAction.setEmpGroupDescReg("RCP");
					workflowAction.setEmpEmail("PSCL");
					workflowdto.setProcessName("scrutiny");
					workflowdto.setWorkflowTaskAction(workflowAction);
					WorkflowProcessParameterList.add(workflowdto);
				}
				UserSession.getCurrent().getObjectMap().put("RejectTask", WorkflowProcessParameterList);
				}
		}
		this.documentList = checklistVerificationService.getDocumentUploaded(applicationId, orgId);
		
		final ServiceMaster service = serviceMasterService.getServiceByShortName(
				MainetConstants.RoadCuttingConstant.RCP, UserSession.getCurrent().getOrganisation().getOrgid());

		// Reset Session Data
		Map<Long, Double> charges = new HashMap<>();
		this.chargeDesc = new HashMap<>();
		this.setTotal(0D);
		/*if (service.getSmScrutinyChargeFlag().equals(MainetConstants.Common_Constant.YES) && "Y".equals(this.getRoadCuttingDto().getPurposeValue())) {
			try {

				this.setListTEC(CommonMasterUtility.getLookUps("TEC", UserSession.getCurrent().getOrganisation()));
				this.setListROT(CommonMasterUtility.getLookUps("ROT", UserSession.getCurrent().getOrganisation()));

				charges = roadCuttingService.getLoiCharges(applicationId, service.getSmServiceId(), orgId);

				setLoiCharges(charges);

				if (charges != null && !charges.isEmpty()) {
					final List<Long> ids = new ArrayList<>(0);
					for (final Entry<Long, Double> entry : charges.entrySet()) {
						ids.add(entry.getKey());
					}
					final Map<Long, String> chargedesc = iTbLoiMasService.getChargeDescByChgId(ids, orgId, null);
					if ((chargedesc != null) && !chargedesc.isEmpty()) {
						for (final Entry<Long, Double> entry : charges.entrySet()) {
							for (final Entry<Long, String> entrydesc : chargedesc.entrySet()) {
								if (entry.getKey().equals(entrydesc.getKey())) {
									chargeDesc.put(entrydesc.getValue(), entry.getValue());
									if (entry.getValue() != null) {
										setTotal(getTotal() + entry.getValue());
									}
								}
							}
						}
					}
				}
			} catch (CloneNotSupportedException e) {
				throw new FrameworkException(e);
			}
		}*/
		IAttachDocsService attachDocsService = (IAttachDocsService) ApplicationContextProvider.getApplicationContext()
				.getBean("attachDocsServiceImpl");
		Map<Integer, AttachDocs> docs = attachDocsService
				.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
						MainetConstants.RoadCuttingConstant.PROJECT_LOCATION + MainetConstants.DOUBLE_BACK_SLACE
								+ applicationId)
				.stream().collect(Collectors.toMap(AttachDocs::getSerialNo, Function.identity()));
		this.setProjectLocationDoc(docs);

		String lookUp = CommonMasterUtility.getDefaultValueByOrg("NOA", UserSession.getCurrent().getOrganisation())
				.getLookUpCode();
		this.setCheckNOCApplicable(lookUp);
		this.setRoadCuttingDtoList(roadCuttingService.getNOCDepartmentTable(orgId));
		List<RoadCuttingDto> dtos = getRoadCuttingDtoList();
		this.setRoadCuttingDtoList(roadCuttingService.getNOCViewStatusDetail(dtos, applicationId));

		// this code will be change after proper design
		String wfIdstr = UserSession.getCurrent().getScrutinyCommonParamMap()
				.get(MainetConstants.SCRUTINY_COMMON_PARAM.WORK_FLOW_ID);
		if (wfIdstr != null) {
			Long wfId = Long.parseLong(wfIdstr);
			WorkflowMas wfmass = workFlowTypeService.getWorkFlowById(wfId);
			if (wfmass.getService().getSmShortdesc().contains("NOC")) {
				setHideScrutinyBtn("N");
				setViewMap("Y");
				this.setAuthFlag("Y");
			} else {
				setHideScrutinyBtn("Y");
				setViewMap("N");
			}
		} else {
			setHideScrutinyBtn("Y");
			setViewMap("N");
		}
		
	}

	// Add Project Location Image Save Code
	private void prepareDocumentsData() {
		List<DocumentDetailsVO> list = new ArrayList<>();

		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			if (entry.getKey() >= 100L) {
				Base64 base64 = null;
				List<File> fileList = null;
				fileList = new ArrayList<>(entry.getValue());
				for (final File file : fileList) {
					try {
						base64 = new Base64();
						final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));

						DocumentDetailsVO d = new DocumentDetailsVO();
						d.setDocumentName(file.getName());
						d.setDocumentByteCode(bytestring);
						d.setDocumentSerialNo(entry.getKey());
						list.add(d);

					} catch (final IOException e) {
						logger.error("Exception has been occurred in file byte to string conversions", e);
					}
				}
			}
		}

		roadCuttingDto.setProjectLocation(list);

	}

	public String getCheckNOCApplicable() {
		return checkNOCApplicable;
	}

	public void setCheckNOCApplicable(String checkNOCApplicable) {
		this.checkNOCApplicable = checkNOCApplicable;
	}

	public List<RoadCuttingDto> getRoadCuttingDtoList() {
		return roadCuttingDtoList;
	}

	public void setRoadCuttingDtoList(List<RoadCuttingDto> roadCuttingDtoList) {
		this.roadCuttingDtoList = roadCuttingDtoList;
	}

	public String getHideScrutinyBtn() {
		return hideScrutinyBtn;
	}

	public void setHideScrutinyBtn(String hideScrutinyBtn) {
		this.hideScrutinyBtn = hideScrutinyBtn;
	}

	public String getViewMap() {
		return viewMap;
	}

	public void setViewMap(String viewMap) {
		this.viewMap = viewMap;
	}

	public String getAuthFlag() {
		return authFlag;
	}

	public void setAuthFlag(String authFlag) {
		this.authFlag = authFlag;
	}

	public String getAppliChargeFlag() {
		return appliChargeFlag;
	}

	public void setAppliChargeFlag(String appliChargeFlag) {
		this.appliChargeFlag = appliChargeFlag;
	}

	public String getRoadCuttingLevelState() {
		return roadCuttingLevelState;
	}

	public void setRoadCuttingLevelState(String roadCuttingLevelState) {
		this.roadCuttingLevelState = roadCuttingLevelState;
	}

	public String getSaveButFlag() {
		return saveButFlag;
	}

	public void setSaveButFlag(String saveButFlag) {
		this.saveButFlag = saveButFlag;
	}

	public String getCheckListFlag() {
		return checkListFlag;
	}

	public void setCheckListFlag(String checkListFlag) {
		this.checkListFlag = checkListFlag;
	}

	public String getChargeService() {
		return chargeService;
	}

	public void setChargeService(String chargeService) {
		this.chargeService = chargeService;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getPurposeValue() {
		return purposeValue;
	}

	public void setPurposeValue(String purposeValue) {
		this.purposeValue = purposeValue;
	}

	public Long getEmpZone() {
		return empZone;
	}

	public void setEmpZone(Long empZone) {
		this.empZone = empZone;
	}

	public boolean saveRoadCuttingZoneWise(long zone) {
		String zid = CommonMasterUtility
				.getHierarchicalLookUp(zone,
						UserSession.getCurrent().getOrganisation())
				.getLookUpCode();
		String refid = zid + "_" + this.roadCuttingDto.getReferenceId();
		this.roadCuttingDto.setReferenceId(refid);
		Employee employee = UserSession.getCurrent().getEmployee();
		this.roadCuttingDto.setUpdatedBy(employee.getEmpId());
		this.roadCuttingDto.setUpdatedDate(new Date());
		this.roadCuttingDto.setLgIpMacUpd(employee.getEmppiservername());
		this.roadCuttingDto.setCodWard1(zone);
		//roadCuttingService.saveZoneWiseStatus(this.roadCuttingDto);
		roadCuttingService.updateRefId(roadCuttingDto,zone);
		initializeWorkFlowForZoneWise(this.roadCuttingDto);
		
		this.setSuccessMessage("Scrutiny initiated");
		return true;
	}
	
	public boolean editSrutinyForm() {
		Employee employee = UserSession.getCurrent().getEmployee();
		this.roadCuttingDto.setUpdatedBy(employee.getEmpId());
		this.roadCuttingDto.setUpdatedDate(new Date());
		this.roadCuttingDto.setLgIpMacUpd(employee.getEmppiservername());
		this.roadCuttingService.saveZoneWiseDetails(this.roadCuttingDto);
		this.setSuccessMessage("Edit Done");
		return true;
	}

	private void initializeWorkFlowForZoneWise(RoadCuttingDto requestDto) {
		ServiceMaster service = serviceMasterService.getServiceByShortName(MainetConstants.RoadCuttingConstant.RCP,
				UserSession.getCurrent().getOrganisation().getOrgid());
		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();
		ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		applicantDto.setApplicantFirstName(requestDto.getfName());
		applicantDto.setServiceId(service.getSmServiceId());
		applicantDto.setDepartmentId(
				departmentService.getDepartmentIdByDeptCode(MainetConstants.WorksManagement.WORKS_MANAGEMENT));
		applicantDto.setMobileNo("NA");
		applicantDto.setUserId(requestDto.getCreatedBy());
		applicantDto.setDwzid1(requestDto.getCodWard1());
		applicationMetaData.setApplicationId(requestDto.getApplicationId());
		applicationMetaData.setReferenceId(requestDto.getReferenceId());
		applicationMetaData.setIsCheckListApplicable(false);
		applicationMetaData.setOrgId(requestDto.getOrgId());
		applicationMetaData.setIsLoiApplicable(false);
		applicationMetaData.setIsApprReqInObjection("Y");
		try {
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}
	}

	public boolean isAllChildWorkFlowInitiated() {
		return allChildWorkFlowInitiated;
	}

	public void setAllChildWorkFlowInitiated(boolean allChildWorkFlowInitiated) {
		this.allChildWorkFlowInitiated = allChildWorkFlowInitiated;
	}

	public boolean isAllChildWorkFlowCompleted() {
		return allChildWorkFlowCompleted;
	}

	public void setAllChildWorkFlowCompleted(boolean allChildWorkFlowCompleted) {
		this.allChildWorkFlowCompleted = allChildWorkFlowCompleted;
	}

}
