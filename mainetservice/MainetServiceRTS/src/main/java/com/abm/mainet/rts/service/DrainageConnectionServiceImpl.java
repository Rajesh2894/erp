package com.abm.mainet.rts.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.LocOperationWZMappingDto;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.GroupMasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.rts.constant.RtsConstants;
import com.abm.mainet.rts.dao.DrainageConnectionServiceDAO;
import com.abm.mainet.rts.domain.DrainageConnectionEntity;
import com.abm.mainet.rts.domain.DrainageConnectionHistoryEntity;
import com.abm.mainet.rts.domain.DrainageConnectionRoadDetEntity;
import com.abm.mainet.rts.dto.DrainageConnectionDto;
import com.abm.mainet.rts.dto.DrainageConnectionRoadDetDTO;
import com.abm.mainet.rts.repository.DrainageConnectionDetailRepository;
import com.abm.mainet.rts.repository.DrainageConnectionRepository;
import com.abm.mainet.rts.ui.model.DrainageConnectionModel;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author rahul.chaubey
 * 
 */

@Produces({ "application/xml", "application/json" })
@WebService(endpointInterface = "com.abm.mainet.rts.service.DrainageConnectionServiceImpl")
@Api(value = "/drainageConnectionService")
@Path("/drainageConnectionService")
@Service(value = "DrainageConnectionService")
public class DrainageConnectionServiceImpl implements DrainageConnectionService {

	private static final Logger logger = Logger.getLogger(DrainageConnectionService.class);
	@Autowired
	TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;
	@Autowired
	private GroupMasterService groupMasterService;

	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private AuditService auditService;

	@Autowired
	@Resource
	private DrainageConnectionRepository drainageConnectionRepository;

	@Autowired
	private CommonService commonService;

	@Autowired
	private IFileUploadService fileUploadService;

	@Autowired
	private ISMSAndEmailService ismsAndEmailService;

	@Autowired
	private ServiceMasterService serviceMaster;

	@Autowired
	private IChallanService challanService;

	@Autowired
	private IWorkflowActionService iWorkflowActionService;
	@Autowired
	private IWorkFlowTypeService iWorkFlowTypeService;

	@Autowired
	DepartmentService departmentService;

	@Autowired
	private TbServicesMstService tbServicesMstService;

	@Autowired
	private ICFCApplicationMasterService cfcService;

	@Autowired
	private DrainageConnectionServiceDAO drainageConnectionServiceDAO;

	@Autowired
	private IChecklistVerificationService ichckService;

	@Autowired
	private IRtsService irtsService;
	
	@Autowired
    private ILocationMasService locationMasService;
	
	@Autowired
	private DrainageConnectionDetailRepository connectionDetailRepository;
	
	/*
	 * @Override
	 * 
	 * @POST
	 * 
	 * @ApiOperation(value = "Save RTS Data", notes = "Save RTS Data")
	 * 
	 * @Path("/saveDraiangeData")
	 * 
	 * @Transactional
	 */

	@Override
	@Transactional
	public DrainageConnectionDto saveDrainageConnection(@RequestBody DrainageConnectionModel model) {

		DrainageConnectionDto drainageConnectionDto = model.getDrainageConnectionDto();
		DrainageConnectionEntity entity = new DrainageConnectionEntity();
		DrainageConnectionHistoryEntity historyEntity = new DrainageConnectionHistoryEntity();
		model.getRequestDTO().setAreaName(model.getRequestDTO().getBldgName()+" "+model.getRequestDTO().getBlockName()+" "+model.getRequestDTO().getRoadName()+" "+model.getRequestDTO().getCityName());
		final Long applicationNo = applicationService.createApplication(model.getRequestDTO());
		model.getRequestDTO().setApplicationId(applicationNo);
		model.getRequestDTO().setPayStatus(MainetConstants.FlagF);
		model.getDrainageConnectionDto().setApmApplicationId(applicationNo);
		boolean checklist = false;
		boolean flag = true;
		try {
			BeanUtils.copyProperties(drainageConnectionDto, entity);
			drainageConnectionRepository.save(entity);
			historyEntity.sethStatus(MainetConstants.FlagA);

			auditService.createHistory(entity, historyEntity);

			if ((drainageConnectionDto.getDocumentList() != null)
					&& !drainageConnectionDto.getDocumentList().isEmpty()) {
				if (!model.getCheckListApplFlag().equalsIgnoreCase("N")) {
					fileUploadService.doFileUpload(drainageConnectionDto.getDocumentList(), model.getRequestDTO());
					checklist = true;
				}
			}
			
			if(drainageConnectionDto.getWard() != null) {
			       TbDepartment deptObj = ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
			                .findDeptByCode(model.getDrainageConnectionDto().getOrgId(), MainetConstants.FlagA, "CFC");
				
	            final LocOperationWZMappingDto wzMapping = locationMasService.findOperLocationAndDeptId(drainageConnectionDto.getWard(),deptObj.getDpDeptid());
				
	            if (wzMapping != null) {
	                if (wzMapping.getCodIdOperLevel1() != null) {
	                	model.getApplicantDTO().setDwzid1(wzMapping.getCodIdOperLevel1());
	                }
	                if (wzMapping.getCodIdOperLevel2() != null) {
	                	model.getApplicantDTO().setDwzid2(wzMapping.getCodIdOperLevel2());
	                }
	                if (wzMapping.getCodIdOperLevel3() != null) {
	                	model.getApplicantDTO().setDwzid3(wzMapping.getCodIdOperLevel3());
	                }
	                if (wzMapping.getCodIdOperLevel4() != null) {
	                	model.getApplicantDTO().setDwzid4(wzMapping.getCodIdOperLevel4());
	                }
	                if (wzMapping.getCodIdOperLevel5() != null) {
	                	model.getApplicantDTO().setDwzid5(wzMapping.getCodIdOperLevel5());
	                }
	            }

				         }
			// fileUploadService.doFileUpload(drainageConnectionDto.getDocumentList(),
			// model.getRequestDTO());
			logger.info("IsFree----------------->"+model.isFree());
			//if (model.isFree()) {
				model.getOfflineDTO().setDocumentUploaded(checklist);
				ApplicationMetadata applicationData = new ApplicationMetadata();
				applicationData.setApplicationId(model.getDrainageConnectionDto().getApmApplicationId());
				applicationData.setOrgId(model.getDrainageConnectionDto().getOrgId());
				applicationData.setIsCheckListApplicable(checklist);
				model.getApplicantDTO().setUserId(model.getRequestDTO().getUserId());
				model.getApplicantDTO().setServiceId(model.getRequestDTO().getServiceId());
				model.getApplicantDTO().setDepartmentId(model.getDeptId());
				commonService.initiateWorkflowfreeService(applicationData, model.getApplicantDTO());
			//}

		} catch (Exception e) {
			flag = true;
			throw new FrameworkException("Unable to Save the Drainage Connection Details", e);
		}
		return drainageConnectionDto;
	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public void updateDrainageConnection(DrainageConnectionDto drainageConnectionDto) {
		DrainageConnectionEntity entity = new DrainageConnectionEntity();
		DrainageConnectionHistoryEntity historyEntity = new DrainageConnectionHistoryEntity();
		try {
			BeanUtils.copyProperties(drainageConnectionDto, entity);
			drainageConnectionRepository.save(entity);
			historyEntity.sethStatus(MainetConstants.FlagU);
			auditService.createHistory(entity, historyEntity);
		} catch (Exception e) {
			throw new FrameworkException("Unable to Update the Drainage Connection Details", e);
		}
	}

	@Override
	public Long saveApplicationData(RequestDTO requestDTO) {
		Long applicationId = 0l;
		return applicationId = applicationService.createApplication(requestDTO);
	}

	@Override
	public List<Long> getApplicationNo(Long orgId, Long serviceId) {

		List<TbCfcApplicationMstEntity> applicationNo = drainageConnectionRepository.loadSummaryData(orgId, serviceId);
		List<Long> appNo = new ArrayList<Long>();
		applicationNo.forEach(no -> {
			appNo.add(no.getApmApplicationId());

		});
		return appNo;

	}

	@Override
	public List<RequestDTO> loadSummaryData(Long orgId, String deptCode) {
		Long deptId = departmentService.getDepartmentIdByDeptCode(deptCode);
		List<TbServicesMst> services = tbServicesMstService.findALlActiveServiceByDeptId(deptId, orgId);
		List<Long> rtsServiceIds = services.stream().map(TbServicesMst::getSmServiceId).collect(Collectors.toList());
		List<TbCfcApplicationMstEntity> entities = cfcService.fetchCfcApplicationsByServiceIds(rtsServiceIds, orgId);
		List<RequestDTO> summaryData = new ArrayList<RequestDTO>();
		for (TbCfcApplicationMstEntity entity : entities) {
			RequestDTO dto = new RequestDTO();
			dto.setTitleId(entity.getApmTitle());
			dto.setfName(entity.getApmFname());
			dto.setmName(entity.getApmMname());
			dto.setlName(entity.getApmLname());
			dto.setGender(entity.getApmSex());
			dto.setServiceId(entity.getTbServicesMst().getSmServiceId());
			dto.setApplicationId(entity.getApmApplicationId());
			dto.setServiceShortCode(tbServicesMstService.getServiceNameByServiceId(dto.getServiceId()));
			summaryData.add(dto);
		}

		return summaryData;
	}

	@Override
	@POST
	@ApiOperation(value = "fetch first appeal data by application no", notes = "fetch first appeal data by application no", response = RequestDTO.class)
	@Path("/getAppealData/{applicationId}/{orgId}")
	public RequestDTO getApplicationFormData(
			@ApiParam(value = "applicationId", required = true) @PathParam("applicationId") Long applicationId,
			@ApiParam(value = "orgId", required = true) @PathParam("orgId") Long orgId) {

		RequestDTO requestDto = new RequestDTO();

		TbCfcApplicationMstEntity mstEntity = cfcService.getCFCApplicationByApplicationId(applicationId, orgId);
		CFCApplicationAddressEntity adrEntity = drainageConnectionRepository.getApplicationAddressData(orgId,
				applicationId);

		requestDto.setApplicationId(mstEntity.getApmApplicationId());

		requestDto.setTitleId(mstEntity.getApmTitle());
		requestDto.setfName(mstEntity.getApmFname());
		requestDto.setmName(mstEntity.getApmMname());
		requestDto.setlName(mstEntity.getApmLname());
		requestDto.setGender(mstEntity.getApmSex());

		requestDto.setCityName(adrEntity.getApaCityName());
		requestDto.setBldgName(adrEntity.getApaBldgnm());
		requestDto.setBlockName(adrEntity.getApaBlockName());
		requestDto.setRoadName(adrEntity.getApaRoadnm());
		requestDto.setWardNo(adrEntity.getApaWardNo());
		requestDto.setPincodeNo(adrEntity.getApaPincode());
		requestDto.setMobileNo(adrEntity.getApaMobilno());
		requestDto.setEmail(adrEntity.getApaEmail());
		mstEntity.getTbServicesMst();

		requestDto.setServiceId(mstEntity.getTbServicesMst().getSmServiceId());
		requestDto.setServiceShortCode(mstEntity.getTbServicesMst().getSmServiceName());

		return requestDto;
	}
	@Transactional(readOnly = true)
	@Override
	@POST
	@ApiOperation(value = "fetch drainage connection data by application no", notes = "fetch drainage connection data by application no", response = DrainageConnectionDto.class)
	@Path("/getDrainageConnData/{applicationId}/{orgId}")
	public DrainageConnectionDto getDrainageConnectionData(
			@ApiParam(value = "applicationId", required = true) @PathParam("applicationId") Long applicationId,
			@ApiParam(value = "orgId", required = true) @PathParam("orgId") Long orgId) {

		DrainageConnectionDto dto = new DrainageConnectionDto();
		DrainageConnectionEntity entity = new DrainageConnectionEntity();
		entity = drainageConnectionRepository.getDrainageConnectionData(orgId, applicationId);
		BeanUtils.copyProperties(entity, dto);
		
		List<DrainageConnectionRoadDetDTO> roadDetDTOList = new ArrayList<>();
		if(null!=entity.getRoadDetEntities()) {
			
			  for( DrainageConnectionRoadDetEntity
					  detailEntity:entity.getRoadDetEntities()) {
					  DrainageConnectionRoadDetDTO roadDetDTO = new DrainageConnectionRoadDetDTO();
					  BeanUtils.copyProperties(detailEntity, roadDetDTO);
					  roadDetDTOList.add(roadDetDTO); //}); }
					  }
			
		}
		
		 
		
		dto.setRoadDetDto(roadDetDTOList);
		return dto;
	}
		  

	@Override
	public boolean saveDecision(DrainageConnectionModel model, WorkflowTaskAction workFlowActionDto, Employee emp) {
		boolean status = false;
		try {
			iWorkflowActionService.updateWorkFlow(workFlowActionDto, emp, model.getDrainageConnectionDto().getOrgId(),
					model.getRequestDTO().getServiceId());
			// for saving the documents
			if ((model.getDrainageConnectionDto().getDocumentList() != null)
					&& !model.getDrainageConnectionDto().getDocumentList().isEmpty()) {
				/*
				 * fileUploadService.doFileUpload(model.getDrainageConnectionDto().
				 * getDocumentList(), model.getRequestDTO());
				 */
				// code change for storing upload file in tb_attach_document table
				FileUploadDTO uploadDTO = new FileUploadDTO();

				uploadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				uploadDTO.setStatus(MainetConstants.FlagA);
				uploadDTO.setDepartmentName(RtsConstants.RTS);
				uploadDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				uploadDTO.setIdfId(model.getDrainageConnectionDto().getApmApplicationId().toString());
				fileUploadService.doMasterFileUpload(model.getDrainageConnectionDto().getDocumentList(), uploadDTO);
			}

			WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
					.getBean(IWorkflowRequestService.class)
					.getWorkflowRequestByAppIdOrRefId(model.getDrainageConnectionDto().getApmApplicationId(), null,
							model.getDrainageConnectionDto().getOrgId());

			// sms n email when application gets rejected

			if (workflowRequest != null
					&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
				// sms and email (when application form rejects (workflow))
				final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
				Organisation org = UserSession.getCurrent().getOrganisation();
				smsDto.setMobnumber(model.getRequestDTO().getMobileNo().toString());
				smsDto.setAppNo(model.getDrainageConnectionDto().getApmApplicationId().toString());
				// added for set email
				smsDto.setEmail(model.getRequestDTO().getEmail());
				ServiceMaster sm = ApplicationContextProvider.getApplicationContext()
						.getBean(ServiceMasterService.class).getServiceMasterByShortCode(
								MainetConstants.RightToService.DCS, model.getDrainageConnectionDto().getOrgId());
				// setServiceMaster(sm);
				smsDto.setServName(sm.getSmServiceName());
				String url = "drainageConnection.html";
				org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
				String fullName = String.join(" ",
						Arrays.asList(model.getRequestDTO().getfName(), model.getRequestDTO().getmName(), model.getRequestDTO().getlName()));
				smsDto.setAppName(fullName);
				smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				// set CC
				smsDto.setCc(model.getWorkflowActionDto().getDecision());
				int langId = UserSession.getCurrent().getLanguageId();
				// ismsAndEmailService.sendEmailSMS(RtsConstants.RTS, url,
				// MainetConstants.SocialSecurity.REJECTED, smsDto,
				// org, langId);
				ismsAndEmailService.sendEmailSMS(RtsConstants.RTS, url,
						PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, smsDto, org, langId);

				/*
				 * drainageConnectionRepository.rejectPension(
				 * model.getDrainageConnectionDto().getApmApplicationId().toString(),
				 * model.getDrainageConnectionDto().getOrgId());
				 */

			}
			// if user is last who reject or approve according to that we can update our own
			// table flag
			else if (iWorkFlowTypeService.isLastTaskInCheckerTaskList(workFlowActionDto.getTaskId())) {

				// sms and email (when application form approves (workflow))
				final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
				Organisation org = UserSession.getCurrent().getOrganisation();
				smsDto.setMobnumber(model.getRequestDTO().getMobileNo().toString());
				smsDto.setAppNo(model.getDrainageConnectionDto().getApmApplicationId().toString());
				// for set email
				smsDto.setEmail(model.getRequestDTO().getEmail());
				ServiceMaster sm = ApplicationContextProvider.getApplicationContext()
						.getBean(ServiceMasterService.class).getServiceMasterByShortCode(
								MainetConstants.RightToService.DCS, model.getDrainageConnectionDto().getOrgId());
				// setServiceMaster(sm);
				smsDto.setServName(sm.getSmServiceName());
				String url = "drainageConnection.html";
				org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
				smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				String fullName = String.join(" ",
						Arrays.asList(model.getRequestDTO().getfName(), model.getRequestDTO().getmName(), model.getRequestDTO().getlName()));
				smsDto.setAppName(fullName);
				int langId = UserSession.getCurrent().getLanguageId();
				// set Cc
				smsDto.setCc(model.getWorkflowActionDto().getDecision());
				// ismsAndEmailService.sendEmailSMS(RtsConstants.RTS, url,
				// PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL,
				// smsDto, org, langId);
				ismsAndEmailService.sendEmailSMS(RtsConstants.RTS, url,
						PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, smsDto, org, langId);

			}
			status = true;
		} catch (Exception ex) {
			throw new FrameworkException("Exception occurs while updating workflow,upload docs,updating table", ex);
		}
		return status;
	}

	@Override
	@POST
	@ApiOperation(value = "get department", notes = "get department")
	@Path("/getDept/{orgId}")
	@Transactional
	public Map<Long, String> getDept(@PathParam("orgId") Long orgId) {
		// TODO Auto-generated method stub
		final Map<Long, String> deptMap = new LinkedHashMap<>(0);
		List<Object[]> department = null;
		department = departmentService.getAllDeptTypeNames();
		for (final Object[] dep : department) {
			if (dep[0] != null) {
				deptMap.put((Long) (dep[0]), (String) dep[1]);
			}
		}
		return deptMap;
	}

	@Override
	@POST
	@ApiOperation(value = "get service", notes = "get service")
	@Path("/getService/{orgId}/{deptId}/{activeStatus}")
	@Transactional
	public Map<Long, String> getService(@PathParam("orgId") Long orgId, @PathParam("deptId") Long deptId,
			@PathParam("activeStatus") String activeStatus) {

		List<Object[]> service = null;

		final Map<Long, String> serviceMap = new LinkedHashMap<Long, String>();
		service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.findAllActiveServicesForDepartment(orgId, deptId);

		for (final Object[] obj : service) {
			if (obj[0] != null) {
				serviceMap.put((Long) (obj[0]), (String) (obj[1]));
			}
		}
		return serviceMap;
	}

	@Override
	public List<RequestDTO> searchData(Long applicationId, Long serviceId, Long orgId) {

		List<RequestDTO> requestDTOList = new ArrayList<RequestDTO>();
		RequestDTO dto;

		ServiceMaster serviceMasterData = serviceMaster.getServiceMaster(serviceId, orgId);
		List<TbCfcApplicationMstEntity> mstData = drainageConnectionServiceDAO.searchData(applicationId, serviceId,
				orgId);
		String serviceName = serviceMasterData.getSmServiceName();
		for (TbCfcApplicationMstEntity data : mstData) {
			dto = new RequestDTO();
			dto.setfName(data.getApmFname());
			dto.setlName(data.getApmLname());
			dto.setApplicationId(data.getApmApplicationId());
			dto.setServiceShortCode(serviceName);

			requestDTOList.add(dto);
		}

		return requestDTOList;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@WebMethod(exclude = true)
	public void setAndSaveChallanDtoOffLine(CommonChallanDTO offline, DrainageConnectionDto model) {
		ServiceMaster serviceMas = serviceMaster.getServiceMasterByShortCode(MainetConstants.RightToService.DCS,
				model.getOrgId());
		offline.setApplNo(model.getApmApplicationId());
		// offline.setAmountToPay(model.getCharges().toString());
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setServiceId(serviceMas.getSmServiceId());
		offline.setApplicantName(model.getReqDTO().getfName());
		offline.setMobileNumber(model.getReqDTO().getMobileNo());
		offline.setEmailId(model.getReqDTO().getEmail());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setApplicantAddress(model.getReqDTO().getAreaName());
//	        if (model.getChargesInfo() != null) {
//	            for (ChargeDetailDTO dto : model.getChargesInfo()) {
//	                offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
//	            }
//	        }
//	        if (CollectionUtils.isNotEmpty(model.getCheckList())) {
//	            offline.setDocumentUploaded(true);
//	        } else {
//	            offline.setDocumentUploaded(false);
//	        }
		offline.setLgIpMac(UserSession.getCurrent().getEmployee().getLgIpMac());
		offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
		offline.setDeptId(serviceMas.getTbDepartment().getDpDeptid());
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());

		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.MENU.N)) {

			final ChallanMaster responseChallan = challanService.InvokeGenerateChallan(offline);

			offline.setChallanNo(responseChallan.getChallanNo());
			offline.setChallanValidDate(responseChallan.getChallanValiDate());

			model.setOfflineDTO(offline);
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {

			final ChallanReceiptPrintDTO printDto = challanService.savePayAtUlbCounter(offline,
					serviceMas.getSmServiceName());
			// model.setReceiptDTO(printDto);
			// model.setSuccessMessage(model.getAppSession().getMessage("adh.receipt"));
		}

	}

	@Override
	@Transactional
	public Boolean getEmployeeRole(UserSession ses) {
		@SuppressWarnings("deprecation")
		LookUp lookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(RtsConstants.ROLE_CODE,
				RtsConstants.DESIGN_PRIFIX, ses.getLanguageId());
		GroupMaster groupMaster = groupMasterService.findByGmId(ses.getEmployee().getGmid(),
				ses.getOrganisation().getOrgid());
		boolean checkFinalAproval = false;
		if (lookup.getLookUpCode().equalsIgnoreCase(groupMaster.getGrCode())) {
			checkFinalAproval = true;
		}
		return checkFinalAproval;
	}

	/*
	 * @Override public List<DocumentDetailsVO> getCheckListData(Long appId,
	 * FileNetApplicationClient fClient, UserSession session) {
	 * List<DocumentDetailsVO> attachsList = new ArrayList<>(); List<CFCAttachment>
	 * cfcAtt = new ArrayList(); cfcAtt =
	 * ichckService.findAttachmentsForAppId(appId, null,
	 * UserSession.getCurrent().getOrganisation().getOrgid()); if
	 * (!cfcAtt.isEmpty()) { int count = 0; int count1 = 0; for (int j = count; j <=
	 * cfcAtt.size() - 1 + count; j++) {
	 * 
	 * DocumentDetailsVO dvo = new DocumentDetailsVO(); if
	 * (cfcAtt.get(count1).getClmDesc() != null &&
	 * !cfcAtt.get(count1).getClmDesc().isEmpty()) {
	 * dvo.setAttachmentId(cfcAtt.get(count1).getAttId());
	 * dvo.setCheckkMANDATORY(cfcAtt.get(count1).getMandatory());
	 * dvo.setDocumentSerialNo(cfcAtt.get(count1).getClmSrNo());
	 * dvo.setDocumentName(cfcAtt.get(count1).getAttFname());
	 * dvo.setUploadedDocumentPath(cfcAtt.get(count1).getAttPath());
	 * attachsList.add(dvo); count1++; }
	 * 
	 * } } return attachsList; }
	 */

	@Override
	@POST
	@ApiOperation(value = "fetch uploaded docs  data by application no", notes = "fetch uploaded docs  data by application no", response = DocumentDetailsVO.class)
	@Path("/getDrnPortalDocs")
	public List<DocumentDetailsVO> getDrnPortalDocs(
			@ApiParam(value = "Citizen dash board request", required = true) @RequestBody final DrainageConnectionDto request) {
		List<DocumentDetailsVO> attachsList = new ArrayList<>();
		attachsList = irtsService.getRtsUploadedCheckListDocuments(request.getApmApplicationId(), request.getOrgId());
		return attachsList;
	}

	@Override
	@Transactional
	public void updateRoadLength(Long apmApplicationId, Long orgId, Long roadType, Long lenRoad) {
		drainageConnectionRepository.updateRoadLength(apmApplicationId,orgId,roadType,lenRoad);
	}

	
	@Override
	@Transactional
	public void saveRoadDetailList(List<DrainageConnectionRoadDetDTO> roadDetDTOList) {
		DrainageConnectionEntity entity = new DrainageConnectionEntity();
		entity.setConnectionId(roadDetDTOList.get(0).getConnectionId());
		List<DrainageConnectionRoadDetEntity> roadDetEntities = new ArrayList<>();

		   for(DrainageConnectionRoadDetDTO dto :roadDetDTOList) {
			DrainageConnectionRoadDetEntity roadDetEntity = new DrainageConnectionRoadDetEntity();
			BeanUtils.copyProperties(dto, roadDetEntity);
			roadDetEntity.setDrainageConnectionId(entity);
			roadDetEntities.add(roadDetEntity);			
		}
		    connectionDetailRepository.save(roadDetEntities);
	}

}
