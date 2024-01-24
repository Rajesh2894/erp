package com.abm.mainet.property.ui.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.repository.LocationMasJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dao.IWorkflowTypeDAO;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.repository.SelfAssessmentMasJpaRepository;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 * @author Arun Shinde
 *
 */
@Component
@Scope("session")
public class BillingMethodChangeModel extends AbstractFormModel {

	private final Logger LOGGER = Logger.getLogger(BillingMethodChangeModel.class);

	@Autowired
	private ILocationMasService locationService;

	@Resource
	private LocationMasJpaRepository locationMasJpaRepository;

	@Resource
	private SelfAssessmentMasJpaRepository selfAssessmentJpaRepository;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private IWorkflowTypeDAO iWorkflowTypeDAO;

	@Autowired
	private IWorkflowTyepResolverService workflowTyepResolverService;

	@Autowired
	private IWorkflowExecutionService workflowExecutionService;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;

	@Autowired
	private ICFCApplicationAddressService iCFCApplicationAddressService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private SelfAssessmentService selfAssessmentService;

	@Autowired
	private IFileUploadService fileUploadService;

	@Autowired
	private ISMSAndEmailService ismsAndEmailService;

	@Resource
	private ICFCApplicationAddressService addressService;

	@Resource
	private ICFCApplicationMasterService applicationMasterService;

	private static final long serialVersionUID = 1L;
	private String ownershipPrefix;
	private String billMethodCode;
	private String billMethodDesc;
	private Long serviceId;
	private String provBillPresent;
    private String countOfRow;
    private Long flatNoOfRow;
    private Long noOfDetailRows;
    private String applicantName;
    private String serviceName;
    private String deptName;
    private String currentTime;
	private ProvisionalAssesmentMstDto provisionalAssesmentMstDto = new ProvisionalAssesmentMstDto();
	private List<LookUp> location = new ArrayList<>(0);
	private Map<Long, String> financialYearMap = new LinkedHashMap<>();
	private List<DocumentDetailsVO> checklist = new ArrayList<>();
	private List<TbBillMas> billMasList = new ArrayList<>();
	private List<LookUp> collectionDetails = new ArrayList<>(0);
	private ProperySearchDto searchDto = new ProperySearchDto();
	private List<TbBillMas> newBillMasList = new LinkedList<>();
	private List<TbBillMas> oldBillMasList = new ArrayList<>();
	private Map<Long, String> financialYearMapForTax = new LinkedHashMap<>();
	private List<Long> finYearList = new ArrayList<>(0);
	private Map<Long, String> taxMasterMap = new LinkedHashMap<>();
	private Map<String, List<TbBillMas>> flatWiseBillmap = new LinkedHashMap<>();
	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
	private ScrutinyLableValueDTO lableValueDTO = new ScrutinyLableValueDTO();	
	private Map<Long, Double> buildAreaAndUsageMap = new LinkedHashMap<>();

	@Override
	public boolean saveForm() {
		LOGGER.info("saveForm() method start");
		ProvisionalAssesmentMstDto asseMstDto = this.getProvisionalAssesmentMstDto();
		try {
			setCustomViewName("BillingMethodChangeEdit");
			UserSession session = UserSession.getCurrent();
			RequestDTO reqDto = new RequestDTO();
			CommonChallanDTO offline = getOfflineDTO();

			Department dept = departmentService.getDepartment(MainetConstants.DEPT_SHORT_NAME.PROPERTY,
					MainetConstants.FlagA);
			setRequestApplicantDetails(reqDto, asseMstDto, session.getOrganisation().getOrgid(), dept.getDpDeptid(),
					session.getLanguageId(), session.getEmployee().getEmpId(), getServiceId(), offline);

			List<DocumentDetailsVO> docs = fileUploadService.prepareFileUpload(getChecklist());
			if (CollectionUtils.isNotEmpty(docs)) {
				fileUploadService.doFileUpload(docs, reqDto);
				offline.setDocumentUploaded(true);
			}

			LOGGER.info("save into provisional tables");
			asseMstDto.setSmServiceId(getServiceId());
			selfAssessmentService.saveChangedBillingMethod(asseMstDto, offline.getApplNo(),
					session.getEmployee().getEmpId(), session.getEmployee().getEmppiservername(),
					session.getOrganisation().getOrgid());

			// Call workflow
			LOGGER.info("Calling workflow method starts");
			initiateWorkFlowBasedOnAmount(asseMstDto, offline, session.getOrganisation().getOrgid());

			sendSmsMail(asseMstDto, UserSession.getCurrent().getOrganisation(),
					UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getEmployee().getEmpId());

		} catch (Exception e) {
			LOGGER.error("Exception occurred whie saving :" + e.getMessage());
			throw new FrameworkException("Exception occurred whie saving :", e);
//			addValidationError(getAppSession().getMessage("property.saveWorkflowError"));
//			return false;
		}
		setSuccessMessage(getAppSession().getMessage("prop.no.dues.save1") + " "
				+ provisionalAssesmentMstDto.getApmApplicationId());
		return true;
	}

	private void setRequestApplicantDetails(final RequestDTO reqDto, ProvisionalAssesmentMstDto asseMstDto, Long orgId,
			Long deptId, int langId, Long empId, Long serviceId, CommonChallanDTO offline) {
		ProvisionalAssesmentOwnerDtlDto ownDtlDto = asseMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0);
		asseMstDto.setSmServiceId(serviceId);
		//asseMstDto.setBillMethodChngFlag(MainetConstants.FlagY);// Flag set to identify bill method has changed
		reqDto.setfName(ownDtlDto.getAssoOwnerName());
		reqDto.setMobileNo(ownDtlDto.getAssoMobileno());
		reqDto.setEmail(asseMstDto.getAssEmail());
		reqDto.setDeptId(deptId);
		reqDto.setOrgId(orgId);
		reqDto.setServiceId(serviceId);
		reqDto.setLangId(Long.valueOf(langId));
		reqDto.setUserId(empId);
		reqDto.setCityName(asseMstDto.getAssCorrAddress());
		reqDto.setZoneNo(asseMstDto.getAssWard1());
		reqDto.setWardNo(asseMstDto.getAssWard2());
		reqDto.setPayStatus(MainetConstants.FlagF);// This flag need to set otherwise checklist won't show
		Long applNo = applicationService.createApplication(reqDto);
		reqDto.setApplicationId(applNo);
		offline.setApplNo(applNo);
		offline.setOrgId(orgId);
		offline.setUserId(empId);
		offline.setMobileNumber(ownDtlDto.getAssoMobileno());
		WardZoneBlockDTO dwzDto = new WardZoneBlockDTO();
		if (asseMstDto.getAssWard1() != null) {
			dwzDto.setAreaDivision1(asseMstDto.getAssWard1());
		}
		if (asseMstDto.getAssWard2() != null) {
			dwzDto.setAreaDivision2(asseMstDto.getAssWard2());
		}
		if (asseMstDto.getAssWard3() != null) {
			dwzDto.setAreaDivision3(asseMstDto.getAssWard3());
		}
		if (asseMstDto.getAssWard4() != null) {
			dwzDto.setAreaDivision4(asseMstDto.getAssWard4());
		}
		if (asseMstDto.getAssWard5() != null) {
			dwzDto.setAreaDivision5(asseMstDto.getAssWard5());
		}
		offline.setDwzDTO(dwzDto);
		offline.setServiceId(serviceId);
	}

	private void initiateWorkFlowBasedOnAmount(ProvisionalAssesmentMstDto provAsseMstDto, CommonChallanDTO offline,
			Long orgid) throws Exception {
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgid);
		ServiceMaster service = serviceMasterService.getServiceByShortName(MainetConstants.Property.BMC, orgid);
		if (service != null) {
			List<WorkflowMas> worKFlowList = iWorkflowTypeDAO.getAllWorkFlows(service.getOrgid(),
					service.getTbDepartment().getDpDeptid(), service.getSmServiceId());
			WorkflowMas workflowMas = null;
			WardZoneBlockDTO dwzDto = null;
			dwzDto = offline.getDwzDTO();

			Double totalRv = 0.0;
			if (CollectionUtils.isNotEmpty(provAsseMstDto.getProvisionalAssesmentDetailDtoList())) {
				for (ProvisionalAssesmentDetailDto detail : provAsseMstDto.getProvisionalAssesmentDetailDtoList()) {
					if (detail.getAssdRv() != null)
						totalRv += detail.getAssdRv();
				}
			}
			workflowMas = getWorkflowMaster(worKFlowList, totalRv, service, dwzDto);
			if (workflowMas != null) {
				try {
					initiateWorkflow(offline, workflowMas, service);
				} catch (Exception e) {
					LOGGER.error("Unsuccessful initiation/updation of task for application : " + e);
					throw new FrameworkException("Exception while calling work flow", e);
				}
			}
		}
	}

	private WorkflowMas getWorkflowMaster(List<WorkflowMas> worKFlowList, Double amount, ServiceMaster service,
			WardZoneBlockDTO dwzDto) throws Exception {
		WorkflowMas workflowMas = null;
		if (CollectionUtils.isNotEmpty(worKFlowList)) {
			for (WorkflowMas mas : worKFlowList) {
				if (mas.getStatus().equalsIgnoreCase("Y")) {
					if (mas.getToAmount() != null && amount != null) {
						workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
								service.getTbDepartment().getDpDeptid(), service.getSmServiceId(),
								new BigDecimal(amount), null, dwzDto.getAreaDivision1(), dwzDto.getAreaDivision2(),
								dwzDto.getAreaDivision3(), dwzDto.getAreaDivision4(), dwzDto.getAreaDivision5());
						break;
					} else {
						workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
								service.getTbDepartment().getDpDeptid(), service.getSmServiceId(),
								dwzDto.getAreaDivision1(), dwzDto.getAreaDivision2(), dwzDto.getAreaDivision3(),
								dwzDto.getAreaDivision4(), dwzDto.getAreaDivision5());
						break;
					}
				}
			}
		} else {
			throw new FrameworkException("Workflow Not Found");
		}
		return workflowMas;
	}

	private void initiateWorkflow(CommonChallanDTO offline, WorkflowMas workflowMas, ServiceMaster service)
			throws Exception {
		WorkflowProcessParameter processParameter = prepareInitProcessParameter(offline, workflowMas, service);
		TaskAssignment assignment = setRequesterTask(offline);
		processParameter.setRequesterTaskAssignment(assignment);
		workflowExecutionService.initiateWorkflow(processParameter);
	}

	TaskAssignment setRequesterTask(CommonChallanDTO offline) {
		TaskAssignment assignment = new TaskAssignment();
		Set<String> actorIds = new HashSet<>();
		CFCApplicationAddressEntity addressEntity = iCFCApplicationAddressService
				.getApplicationAddressByAppId(offline.getApplNo(), offline.getOrgId());
		assignment.setActorId(addressEntity.getUserId() + MainetConstants.operator.COMMA + offline.getMobileNumber());
		actorIds.add(addressEntity.getUserId().toString());
		actorIds.add(offline.getMobileNumber());
		assignment.setActorIds(actorIds);
		assignment.setOrgId(addressEntity.getOrgId().getOrgid());
		return assignment;
	}

	WorkflowProcessParameter prepareInitProcessParameter(CommonChallanDTO offline, WorkflowMas workflowMas,
			ServiceMaster serviceMaster) {
		Organisation org = new Organisation();
		org.setOrgid(offline.getOrgId());
		WorkflowProcessParameter processParameter = new WorkflowProcessParameter();
		ApplicationMetadata applicationMetadata = new ApplicationMetadata();
		WorkflowTaskAction workflowAction = new WorkflowTaskAction();
		workflowAction.setReferenceId(offline.getReferenceNo());
		workflowAction.setApplicationId(offline.getApplNo());
		workflowAction.setDateOfAction(new Date());
		workflowAction.setOrgId(offline.getOrgId());
		workflowAction.setEmpId(offline.getUserId());
		workflowAction.setEmpType(offline.getEmpType());
		workflowAction.setEmpName(offline.getApplicantName());
		workflowAction.setCreatedBy(offline.getUserId());
		workflowAction.setCreatedDate(new Date());
		if (StringUtils.isEmpty(offline.getChallanNo())) {
			workflowAction.setPaymentMode(MainetConstants.PAYMENT.ONLINE);
		} else {
			workflowAction.setPaymentMode(MainetConstants.PAYMENT.OFFLINE);
		}
		if (offline.getLoiNo() != null && !offline.getLoiNo().isEmpty()) {
			workflowAction.setIsLoiGenerated(true);
			workflowAction.setTaskId(offline.getTaskId());
			workflowAction.setModifiedBy(offline.getUserId());
			workflowAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
		} else {
			if (offline.isDocumentUploaded()) {
				List<Long> attachmentId = iChecklistVerificationService.fetchAttachmentIdByAppid(offline.getApplNo(),
						offline.getOrgId());
				workflowAction.setAttachementId(attachmentId);
			}
			workflowAction.setDecision(MainetConstants.WorkFlow.Decision.SUBMITTED);
		}

		String processName = serviceMasterService.getProcessName(offline.getServiceId(), offline.getOrgId());
		processParameter.setProcessName(processName);
		applicationMetadata.setWorkflowId(workflowMas.getWfId());
		applicationMetadata.setOrgId(offline.getOrgId());
		if (StringUtils.isEmpty(offline.getChallanNo())) {
			applicationMetadata.setPaymentMode(MainetConstants.PAYMENT.ONLINE);
		} else {
			applicationMetadata.setPaymentMode(MainetConstants.PAYMENT.OFFLINE);
		}
		applicationMetadata.setIsCheckListApplicable(false);
		if (offline.isDocumentUploaded()) {
			String checklistFlag = null;
			final List<LookUp> lookUps = CommonMasterUtility.getLookUps("APL", org);
			for (final LookUp lookUp : lookUps) {
				if (serviceMaster.getSmChklstVerify() != null
						&& lookUp.getLookUpId() == serviceMaster.getSmChklstVerify().longValue()) {
					checklistFlag = lookUp.getLookUpCode();
					break;
				}
			}
			if (serviceMaster != null && serviceMaster.getSmCheckListReq().equals(MainetConstants.FlagN)
					&& MainetConstants.Common_Constant.ACTIVE_FLAG.equalsIgnoreCase(checklistFlag)) {
				applicationMetadata.setIsCheckListApplicable(false);
			}
			if (serviceMaster != null && serviceMaster.getSmCheckListReq().equals(MainetConstants.FlagY)
					&& MainetConstants.Common_Constant.ACTIVE_FLAG.equalsIgnoreCase(checklistFlag)) {
				applicationMetadata.setIsCheckListApplicable(true);
			}
		}
		applicationMetadata.setReferenceId(offline.getReferenceNo());
		applicationMetadata.setApplicationId(offline.getApplNo());
		applicationMetadata.setIsFreeService(false);
		if (MainetConstants.Y_FLAG.equalsIgnoreCase(serviceMaster.getSmScrutinyApplicableFlag())) {
			applicationMetadata.setIsScrutinyApplicable(true);
		} else {
			applicationMetadata.setIsScrutinyApplicable(false);
		}
		if (MainetConstants.Y_FLAG.equalsIgnoreCase(serviceMaster.getSmScrutinyChargeFlag())) {
			applicationMetadata.setIsLoiApplicable(true);
		} else {
			applicationMetadata.setIsLoiApplicable(false);
		}
		processParameter.setApplicationMetadata(applicationMetadata);
		processParameter.setWorkflowTaskAction(workflowAction);
		return processParameter;
	}

	@Override
	public void populateApplicationData(final long applicationId) {
		Organisation org = UserSession.getCurrent().getOrganisation();
		final ApplicantDetailDTO applicantDetailsDTO = new ApplicantDetailDTO();
		final TbCfcApplicationMstEntity applicantMasterDetails = applicationMasterService
				.getCFCApplicationByApplicationId(applicationId, org.getOrgid());
		applicantDetailsDTO.setApplicantFirstName(applicantMasterDetails.getApmFname());
		final CFCApplicationAddressEntity applicantDetails = addressService.getApplicationAddressByAppId(applicationId,
				org.getOrgid());
		applicantDetailsDTO.setMobileNo(applicantDetails.getApaMobilno());
		applicantDetailsDTO.setAreaName(applicantDetails.getApaCityName());
		applicantDetailsDTO.setDwzid1(applicantDetails.getApaZoneNo());
		applicantDetailsDTO.setDwzid2(applicantDetails.getApaWardNo());
		provisionalAssesmentMstDto.setAssWard1(applicantDetails.getApaZoneNo());
		provisionalAssesmentMstDto.setAssWard2(applicantDetails.getApaWardNo());
		this.setProvisionalAssesmentMstDto(provisionalAssesmentMstDto);
		this.setApplicantDetailDto(applicantDetailsDTO);
	}

	public void setDropDownValues(Organisation org) {

		provisionalAssesmentMstDto.setProAssOwnerTypeName(
				CommonMasterUtility.getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getAssOwnerType(),
						UserSession.getCurrent().getOrganisation()).getDescLangFirst());

		if (provisionalAssesmentMstDto.getLocId() != null) {
			this.getProvisionalAssesmentMstDto().setLocationName(
					locationService.getLocationNameById(provisionalAssesmentMstDto.getLocId(), org.getOrgid()));
		}

		if (provisionalAssesmentMstDto.getPropLvlRoadType() != null) {
			this.getProvisionalAssesmentMstDto()
					.setProAssdRoadfactorDesc(CommonMasterUtility
							.getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getPropLvlRoadType(), org)
							.getDescLangFirst());
		}

		this.getProvisionalAssesmentMstDto().setAssWardDesc1(CommonMasterUtility
				.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard1(), org).getDescLangFirst());

		if (provisionalAssesmentMstDto.getAssWard2() != null) {
			this.getProvisionalAssesmentMstDto().setAssWardDesc2(CommonMasterUtility
					.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard2(), org).getDescLangFirst());
		}

		if (provisionalAssesmentMstDto.getAssWard3() != null) {
			this.getProvisionalAssesmentMstDto().setAssWardDesc3(CommonMasterUtility
					.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard3(), org).getDescLangFirst());
		}

		if (provisionalAssesmentMstDto.getAssWard4() != null) {
			this.getProvisionalAssesmentMstDto().setAssWardDesc4(CommonMasterUtility
					.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard4(), org).getDescLangFirst());
		}

		if (provisionalAssesmentMstDto.getAssWard5() != null) {
			this.getProvisionalAssesmentMstDto().setAssWardDesc5(CommonMasterUtility
					.getHierarchicalLookUp(provisionalAssesmentMstDto.getAssWard5(), org).getDescLangFirst());
		}
		if (provisionalAssesmentMstDto.getBillMethod() != null) {
			this.setBillMethodDesc(
					CommonMasterUtility.getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getBillMethod(), org)
							.getDescLangFirst());
		}
		for (ProvisionalAssesmentDetailDto detaildto : getProvisionalAssesmentMstDto()
				.getProvisionalAssesmentDetailDtoList()) {
			if (detaildto.getAssdBuildupArea() != null) {
				for (Map.Entry<Long, String> entry : this.getFinancialYearMap().entrySet()) {
					if (entry.getKey().toString().equals(detaildto.getFaYearId().toString())) {
						detaildto.setProFaYearIdDesc(entry.getValue());
					}

					SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FRMAT);

					if (detaildto.getAssdYearConstruction() != null) {
						detaildto.setProAssdConstructionDate(formatter.format(detaildto.getAssdYearConstruction()));
					} else {
						detaildto.setAssdYearConstruction(new Date());
						detaildto.setProAssdConstructionDate(formatter.format(detaildto.getAssdYearConstruction()));
					}
					detaildto.setProAssdUsagetypeDesc(
							CommonMasterUtility.getHierarchicalLookUp(detaildto.getAssdUsagetype1(),
									UserSession.getCurrent().getOrganisation()).getDescLangFirst());

					if (detaildto.getAssdUsagetype2() != null) {
						detaildto.setProAssdUsagetypeDesc2(CommonMasterUtility
								.getHierarchicalLookUp(detaildto.getAssdUsagetype2(), org).getDescLangFirst());
					}
					if (detaildto.getAssdUsagetype3() != null) {
						detaildto.setProAssdUsagetypeDesc3(CommonMasterUtility
								.getHierarchicalLookUp(detaildto.getAssdUsagetype3(), org).getDescLangFirst());
					}
					if (detaildto.getAssdUsagetype4() != null) {
						detaildto.setProAssdUsagetypeDesc4(CommonMasterUtility
								.getHierarchicalLookUp(detaildto.getAssdUsagetype4(), org).getDescLangFirst());
					}
					if (detaildto.getAssdUsagetype5() != null) {
						detaildto.setProAssdUsagetypeDesc5(CommonMasterUtility
								.getHierarchicalLookUp(detaildto.getAssdUsagetype5(), org).getDescLangFirst());
					}

					detaildto.setAssdNatureOfpropertyDesc1(CommonMasterUtility
							.getHierarchicalLookUp(detaildto.getAssdNatureOfproperty1(), org).getDescLangFirst());

					if (detaildto.getAssdNatureOfproperty2() != null) {
						detaildto.setAssdNatureOfpropertyDesc2(CommonMasterUtility
								.getHierarchicalLookUp(detaildto.getAssdNatureOfproperty2(), org).getDescLangFirst());
					}
					if (detaildto.getAssdNatureOfproperty3() != null) {
						detaildto.setAssdNatureOfpropertyDesc3(CommonMasterUtility
								.getHierarchicalLookUp(detaildto.getAssdNatureOfproperty3(), org).getDescLangFirst());
					}
					if (detaildto.getAssdNatureOfproperty4() != null) {
						detaildto.setAssdNatureOfpropertyDesc4(CommonMasterUtility
								.getHierarchicalLookUp(detaildto.getAssdNatureOfproperty4(), org).getDescLangFirst());
					}
					if (detaildto.getAssdNatureOfproperty5() != null) {
						detaildto.setAssdNatureOfpropertyDesc5(CommonMasterUtility
								.getHierarchicalLookUp(detaildto.getAssdNatureOfproperty5(), org).getDescLangFirst());
					}
					if (detaildto.getAssdFloorNo() != null) {
						detaildto.setProFloorNo(CommonMasterUtility
								.getNonHierarchicalLookUpObject(detaildto.getAssdFloorNo(), org).getDescLangFirst());
					}
					detaildto.setProAssdConstruTypeDesc(CommonMasterUtility
							.getNonHierarchicalLookUpObject(detaildto.getAssdConstruType(), org).getDescLangFirst());

					detaildto.setProAssdOccupancyTypeDesc(CommonMasterUtility
							.getNonHierarchicalLookUpObject(detaildto.getAssdOccupancyType(), org).getDescLangFirst());
				}
			}
		}

	}

	public void sendSmsMail(ProvisionalAssesmentMstDto provAsseMstDto, Organisation organisation, int langId,
			Long userId) {
		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		ProvisionalAssesmentOwnerDtlDto ownerDetail = provAsseMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0);
		dto.setEmail(ownerDetail.geteMail());
		dto.setMobnumber(ownerDetail.getAssoMobileno());
		dto.setUserId(userId);
		ServiceMaster service = serviceMasterService.getServiceMasterByShortCode(MainetConstants.Property.BMC,
				provAsseMstDto.getOrgId());
		dto.setServName(service.getSmServiceName());
		if (provAsseMstDto.getApmApplicationId() != null) {
			dto.setAppNo(provAsseMstDto.getApmApplicationId().toString());
		}
		dto.setPropertyNo(provAsseMstDto.getAssNo());
		ismsAndEmailService.sendEmailSMS(MainetConstants.Property.PROP_DEPT_SHORT_CODE, "BillingMethodChange.html",
				PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, dto, organisation, langId);
	}

	public ProvisionalAssesmentMstDto getProvisionalAssesmentMstDto() {
		return provisionalAssesmentMstDto;
	}

	public void setProvisionalAssesmentMstDto(ProvisionalAssesmentMstDto provisionalAssesmentMstDto) {
		this.provisionalAssesmentMstDto = provisionalAssesmentMstDto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOwnershipPrefix() {
		return ownershipPrefix;
	}

	public void setOwnershipPrefix(String ownershipPrefix) {
		this.ownershipPrefix = ownershipPrefix;
	}

	public List<LookUp> getLocation() {
		return location;
	}

	public void setLocation(List<LookUp> location) {
		this.location = location;
	}

	public Map<Long, String> getFinancialYearMap() {
		return financialYearMap;
	}

	public void setFinancialYearMap(Map<Long, String> financialYearMap) {
		this.financialYearMap = financialYearMap;
	}

	public String getBillMethodCode() {
		return billMethodCode;
	}

	public void setBillMethodCode(String billMethodCode) {
		this.billMethodCode = billMethodCode;
	}

	public String getBillMethodDesc() {
		return billMethodDesc;
	}

	public void setBillMethodDesc(String billMethodDesc) {
		this.billMethodDesc = billMethodDesc;
	}

	public List<DocumentDetailsVO> getChecklist() {
		return checklist;
	}

	public void setChecklist(List<DocumentDetailsVO> checklist) {
		this.checklist = checklist;
	}

	public List<TbBillMas> getBillMasList() {
		return billMasList;
	}

	public void setBillMasList(List<TbBillMas> billMasList) {
		this.billMasList = billMasList;
	}

	public List<LookUp> getCollectionDetails() {
		return collectionDetails;
	}

	public void setCollectionDetails(List<LookUp> collectionDetails) {
		this.collectionDetails = collectionDetails;
	}

	public ProperySearchDto getSearchDto() {
		return searchDto;
	}

	public void setSearchDto(ProperySearchDto searchDto) {
		this.searchDto = searchDto;
	}

	public List<TbBillMas> getNewBillMasList() {
		return newBillMasList;
	}

	public void setNewBillMasList(List<TbBillMas> newBillMasList) {
		this.newBillMasList = newBillMasList;
	}

	public Long getFlatNoOfRow() {
		return flatNoOfRow;
	}

	public void setFlatNoOfRow(Long flatNoOfRow) {
		this.flatNoOfRow = flatNoOfRow;
	}

	public Map<Long, String> getFinancialYearMapForTax() {
		return financialYearMapForTax;
	}

	public void setFinancialYearMapForTax(Map<Long, String> financialYearMapForTax) {
		this.financialYearMapForTax = financialYearMapForTax;
	}

	public List<Long> getFinYearList() {
		return finYearList;
	}

	public void setFinYearList(List<Long> finYearList) {
		this.finYearList = finYearList;
	}

	public Map<Long, String> getTaxMasterMap() {
		return taxMasterMap;
	}

	public void setTaxMasterMap(Map<Long, String> taxMasterMap) {
		this.taxMasterMap = taxMasterMap;
	}

	public Map<String, List<TbBillMas>> getFlatWiseBillmap() {
		return flatWiseBillmap;
	}

	public void setFlatWiseBillmap(Map<String, List<TbBillMas>> flatWiseBillmap) {
		this.flatWiseBillmap = flatWiseBillmap;
	}

	public List<TbBillMas> getOldBillMasList() {
		return oldBillMasList;
	}

	public void setOldBillMasList(List<TbBillMas> oldBillMasList) {
		this.oldBillMasList = oldBillMasList;
	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}

	public ScrutinyLableValueDTO getLableValueDTO() {
		return lableValueDTO;
	}

	public void setLableValueDTO(ScrutinyLableValueDTO lableValueDTO) {
		this.lableValueDTO = lableValueDTO;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getProvBillPresent() {
		return provBillPresent;
	}

	public void setProvBillPresent(String provBillPresent) {
		this.provBillPresent = provBillPresent;
	}

	public String getCountOfRow() {
		return countOfRow;
	}

	public void setCountOfRow(String countOfRow) {
		this.countOfRow = countOfRow;
	}

	public Map<Long, Double> getBuildAreaAndUsageMap() {
		return buildAreaAndUsageMap;
	}

	public void setBuildAreaAndUsageMap(Map<Long, Double> buildAreaAndUsageMap) {
		this.buildAreaAndUsageMap = buildAreaAndUsageMap;
	}

	public Long getNoOfDetailRows() {
		return noOfDetailRows;
	}

	public void setNoOfDetailRows(Long noOfDetailRows) {
		this.noOfDetailRows = noOfDetailRows;
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

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}	
		
	
}
