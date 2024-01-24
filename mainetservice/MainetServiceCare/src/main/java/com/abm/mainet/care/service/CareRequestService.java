package com.abm.mainet.care.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.care.dao.IComplaintDAO;
import com.abm.mainet.care.domain.CareDepartmentAction;
import com.abm.mainet.care.domain.CareRequest;
import com.abm.mainet.care.domain.ComplaintAcknowledgementModel;
import com.abm.mainet.care.domain.EscalationDetailsList;
import com.abm.mainet.care.dto.CareRequestDTO;
import com.abm.mainet.care.dto.ComplaintSearchDTO;
import com.abm.mainet.care.dto.ComplaintTaskDTO;
import com.abm.mainet.care.dto.DepartmentComplaintDTO;
import com.abm.mainet.care.dto.DepartmentComplaintTypeDTO;
import com.abm.mainet.care.dto.DepartmentDTO;
import com.abm.mainet.care.dto.PotHoleComplaintDTO;
import com.abm.mainet.care.dto.SWMComplaintDTO;
import com.abm.mainet.care.dto.SWMComplaintFilesDTO;
import com.abm.mainet.care.repository.CareDepartmentActionRepository;
import com.abm.mainet.care.repository.CareRequestRepository;
import com.abm.mainet.care.utility.CareUtility;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.CommonConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dao.IOrganisationDAO;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.ComplaintType;
import com.abm.mainet.common.domain.DepartmentComplaint;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.domain.TbComparentMasEntity;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.OrganisationDTO;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbComparamMas;
import com.abm.mainet.common.master.dto.TbComparentMas;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.mapper.TbComparentMasServiceMapper;
import com.abm.mainet.common.master.repository.LocationMasJpaRepository;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.repository.TbComparentMasJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.repository.CFCApplicationAddressRepository;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IComplaintTypeService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.TbComparamMasService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.common.workflow.domain.ActionResponse;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.TaskSearchRequest;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.dto.WorkflowAction;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTask;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.repository.WorkFlowTypeRepository;
import com.abm.mainet.common.workflow.service.ITaskService;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;

@Service
@Repository
@WebService(endpointInterface = "com.abm.mainet.care.service.ICareRequestService")
@Api("/care")
@Path("/care")
public class CareRequestService implements ICareRequestService {

    private static final Logger LOGGER = Logger.getLogger(CareRequestService.class);

    @Autowired
    private ApplicationSession applicationSession;

    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    private IComplaintDAO complaintDAO;

    @Autowired
    private CareRequestRepository careRequestRepository;

    @Autowired
    private IOrganisationService organisationService;

    @Resource
    private TbDepartmentService tbDepartmentService;

    @Autowired
    private WorkFlowTypeRepository workFlowTypeRepository;

    @Autowired
    private CareDepartmentActionRepository careDepartmentActionRepository;

    @Autowired
    private ICFCApplicationMasterService CFCApplicationMasterService;

    @Autowired
    private ICFCApplicationAddressService CFCApplicationAddressService;

    @Autowired
    private IWorkflowTyepResolverService workflowTyepResolverService;

    @Autowired
    private IWorkflowExecutionService workflowExecutionService;

    @Autowired
    private ICareWorkflowService careWorkflowService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private ITaskService taskService;

    @Autowired
    private IWorkflowActionService workflowActionService;


    @Autowired
    private CFCApplicationAddressRepository cfcApplicationAddressRepository;

    @Autowired
    private IWorkflowRequestService workflowRequestService;

    @Autowired
    private ISMSAndEmailService sMSAndEmailService;

    @Autowired
    private IOrganisationDAO organisationDAO;

    @Autowired
    IComplaintTypeService iComplaintTypeService;

    @Autowired
    TbServicesMstService tbServicesMstService;

    @Resource
    private TbComparentMasServiceMapper tbComparentMasServiceMapper;

    @Resource
    private TbComparentMasJpaRepository tbComparentMasJpaRepository;

    @Resource
    private TbComparamMasService tbComparamMasService;

    @Autowired
    private DepartmentService departmentService;
    @Resource
    private LocationMasJpaRepository locationMasJpaRepository;

    @Resource
    private TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;
    
    @Resource
    private ILocationMasService iLocationMasService;
    
    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    @Override
    @WebMethod(exclude = true)
    @Transactional
    public ActionResponse startCareProces(CareRequest careRequest, WorkflowTaskAction startAction,Long loggedInLocId) throws Exception {
        ActionResponse response = null;
        careRequest.setLastDateOfAction(new Date());
        String complaintId = generateComplaintNo(careRequest,loggedInLocId);
        careRequest.setComplaintId(complaintId);
        startAction.setApplicationId(careRequest.getApplicationId());
        startAction.setReferenceId(careRequest.getComplaintId());
        startAction.setDecision(MainetConstants.WorkFlow.Decision.SUBMITTED);
        startAction.setTaskId(0L);
        startAction.setTaskName(MainetConstants.START);

        /*
         * WorkflowMas workflowType = workflowTyepResolverService.resolveComplaintWorkflowType(careRequest.getOrgId (),
         * careRequest.getDepartmentComplaint(), careRequest.getComplaintType(), careRequest.getLocation().getLocId());
         */
        // this code is only specific to suda for word office as told by rajesh sir
        WorkflowMas workflowType = null;
        if (careRequest.getApplnType().equalsIgnoreCase(MainetConstants.FlagR)) {
            workflowType = workflowTyepResolverService.resolveServiceWorkflowType(careRequest.getOrgId(),
                    careRequest.getDepartmentComplaint(), careRequest.getSmServiceId(), careRequest.getWard1(), null,
                    null, null, null);
        } else {
            workflowType = workflowTyepResolverService.resolveComplaintWorkflowType(careRequest.getOrgId(),
                    careRequest.getDepartmentComplaint(), careRequest.getComplaintType(), careRequest.getWard1(), careRequest.getWard2(),
                    careRequest.getWard3(), careRequest.getWard4(), careRequest.getWard5());
        }
        RequestDTO applicantDetailDto = this.getApplicationDetails(careRequest);
        applicantDetailDto.setUserId(startAction.getEmpId());
        WorkflowProcessParameter workflowProcessParameter = careWorkflowService
                .prepareInitCareWorkflowProcessParameter(applicantDetailDto, careRequest, workflowType, startAction);

        /* D#111227 -> ref no added to get application no value from vw_citizen_dashboard */
        TbCfcApplicationMstEntity tbCfcApplicationMstEntity = tbCfcApplicationMstJpaRepository
                .findByApmApplicationId(careRequest.getApplicationId()).get();
        tbCfcApplicationMstEntity.setRefNo(complaintId);

        try {
            //user story 130147 by saraswati
            careRequestRepository.save(careRequest);
            //If Env variable of doon and complaint type external Flag is Y then this if should executed
            ComplaintType complaintType = iComplaintTypeService.findComplaintTypeById(careRequest.getComplaintType());
            if ((CareUtility.isENVCodePresent(MainetConstants.ENV_DSCL, careRequest.getOrgId()) && complaintType.getExternalWorkFlowFlag()!=null &&
            		complaintType.getExternalWorkFlowFlag().equalsIgnoreCase(MainetConstants.Y_FLAG))) {
            	 TbDepartment dept = tbDepartmentService.findById(careRequest.getDepartmentComplaint());
            	 boolean status=false;
				/*if (dept.getDpDeptcode()!=null && dept.getDpDeptcode().equalsIgnoreCase(MainetConstants.WorksManagement.WORKS_MANAGEMENT)) {
					status = callPotHoleAPI(careRequest);
				}*/ 

				if(dept.getDpDeptcode()!=null && dept.getDpDeptcode().equalsIgnoreCase(MainetConstants.WorksManagement.WORKS_MANAGEMENT)
						|| dept.getDpDeptcode().equalsIgnoreCase(MainetConstants.WorksManagement.VPCA)
						|| dept.getDpDeptcode().equalsIgnoreCase(MainetConstants.WorksManagement.LAND)
						|| dept.getDpDeptcode().equalsIgnoreCase(MainetConstants.WorksManagement.Water_Sewarage)){
					//As per requirement of Sachin Naughtiyal posting complaint on workflow and on external API at same time
		            tbCfcApplicationMstJpaRepository.save(tbCfcApplicationMstEntity);
		            workflowExecutionService.initiateWorkflow(workflowProcessParameter);
		            WorkflowRequest workflowRequest = workflowRequestService
		                    .getWorkflowRequestByAppIdOrRefId(careRequest.getApplicationId(), null, careRequest.getOrgId());
		            if (workflowRequest != null) {
		                sendSmsAndEmail(applicantDetailDto, workflowRequest, workflowType, careRequest);
		            }
		            
					String SwacchataComplaintId = callSwacchBharatAPI(careRequest);
					if(StringUtils.isNotEmpty(SwacchataComplaintId)){
						
						LOGGER.info("Complaint Successfully posted on SwacchaBharat Portal");
						LOGGER.info("ComplaintId: " + SwacchataComplaintId);
						
						try{
						String trimmedSwacchataComplaintId = SwacchataComplaintId.trim();
						careRequestRepository.updateExtRefNo(trimmedSwacchataComplaintId, careRequest.getApplicationId(), careRequest.getComplaintId(), careRequest.getOrgId());;
						LOGGER.info("SwacchataComplaint no succefully stored in tbCareRequest");
						}
						
						catch(Exception E){
							LOGGER.error("Exception in updating extRefeNo for SwacchataApp: " + careRequest.getApplicationId() + "Complaint No: " + careRequest.getComplaintId());
				    		E.printStackTrace();
							throw new FrameworkException(E);
							
						}
					}
				}
				
				else {
				 status = callSWMWorkForceAPI(careRequest);
				}
            		if(!status) {
            			tbCfcApplicationMstEntity.setApmMode(MainetConstants.MRM.STATUS.FORM_STATUS_DRAFT);
            		}
            		tbCfcApplicationMstJpaRepository.save(tbCfcApplicationMstEntity);
            		
            }else {
            tbCfcApplicationMstJpaRepository.save(tbCfcApplicationMstEntity);
            workflowExecutionService.initiateWorkflow(workflowProcessParameter);
            WorkflowRequest workflowRequest = workflowRequestService
                    .getWorkflowRequestByAppIdOrRefId(careRequest.getApplicationId(), null, careRequest.getOrgId());
            if (workflowRequest != null) {
                sendSmsAndEmail(applicantDetailDto, workflowRequest, workflowType, careRequest);
            }
           }
            
        } catch (Exception e) {
            LOGGER.error("Unsuccessful initiation of task for application : " + applicantDetailDto.getApplicationId());
            throw new Exception(
                    "Unsuccessful initiation of task for application : " + applicantDetailDto.getApplicationId(), e);
        }

        response = new ActionResponse(MainetConstants.COMMON_STATUS.SUCCESS);
        response.addResponseData(MainetConstants.RESPONSE, MainetConstants.ALERT_SUBMIT_SUCCESS);
        response.addResponseData(MainetConstants.DECISION, MainetConstants.WorkFlow.Decision.SUBMITTED);
        response.addResponseData(MainetConstants.REQUEST_NO, careRequest.getApplicationId().toString());
        LOGGER.info(
                "Successsfully Submitted CARE Request For  RequestNo: " + careRequest.getApplicationId().toString());
        return response;
    }

    @Override
    @WebMethod(exclude = true)
    @Transactional
    public ActionResponse updateCareProces(CareRequest careRequest, WorkflowTaskAction updateAction)
            throws EntityNotFoundException, Exception {

        ActionResponse response = null;
        Long taskId = updateAction.getTaskId();
        CareDepartmentAction careDepartmentAction = CareUtility.toCareDepartmentAction(updateAction);
        careDepartmentAction.setCareRequest(careRequest);
        RequestDTO applicantDetailDto = this.getApplicationDetails(careRequest);
        WorkflowProcessParameter workflowProcessParameter = careWorkflowService
                .prepareUpdateCareWorkflowProcessParameter(updateAction);
        // D#111346
        ApplicationMetadata applicationMetadata = new ApplicationMetadata();
        applicationMetadata.setIsCallCenterApplicable(MainetConstants.FlagY);
        List<LookUp> envLookUpList = CommonMasterUtility.lookUpListByPrefix(MainetConstants.ENV, careRequest.getOrgId());
        boolean kdmcEnv = envLookUpList.stream()
                .anyMatch(env -> Arrays.asList(MainetConstants.ENV_PRODUCT).contains(env.getLookUpCode().toUpperCase()) && StringUtils.equals(env.getOtherField(),
                        MainetConstants.FlagY));
        if (kdmcEnv) {
            applicationMetadata.setIsCallCenterApplicable(MainetConstants.FlagN);
        }

        workflowProcessParameter.setApplicationMetadata(applicationMetadata);
        try {
        	WorkflowTaskActionResponse workflowResponse = workflowExecutionService.updateWorkflow(workflowProcessParameter);
        	/*D#129972 - Setting error response with error code TNF if task is autoescalated by timer*/
        	if(StringUtils.isNotBlank(workflowResponse.getCode()) && workflowResponse.getMessage().equals(MainetConstants.InputError.TASK_NOT_FOUND)
        			&& workflowResponse.getCode().equals(MainetConstants.InputError.NO_CONTENT)) {
                response = new ActionResponse(MainetConstants.COMMON_STATUS.FAIL.toUpperCase());
                response.addResponseData(MainetConstants.DECISION, MainetConstants.InputError.TASK_NOT_FOUND);
                return response;
        	}
            WorkflowRequest workflowRequest = workflowRequestService
                    .getWorkflowRequestByAppIdOrRefId(careRequest.getApplicationId(), null, careRequest.getOrgId());
            if (workflowRequest != null) {
                Optional<WorkflowTask> task = workflowRequest.getWorkFlowTaskList().stream()
                        .filter(t -> t.getTaskId().equals(taskId)).findFirst();
                if (task.isPresent()) {
                    Optional<WorkflowAction> action = task.get().getWorkFlowActionList().stream()
                            .filter(a -> a.getEmpId().equals(updateAction.getEmpId())).findFirst();
                    careDepartmentAction.setWorkflowActionId(action.get().getId());
                }
                careRequest.setModifiedDate(new Date());
                careRequest.setLastDateOfAction(new Date());
                careRequestRepository.save(careRequest);
                careDepartmentActionRepository.save(careDepartmentAction);
                WorkflowMas workflowType = workFlowTypeRepository.findOne(workflowRequest.getWorkflowTypeId());
                sendSmsAndEmail(applicantDetailDto, workflowRequest, workflowType, careRequest);
            }

        } catch (Exception e) {
            LOGGER.error("Unsuccessful Completion of task of id " + taskId + "for application : "
                    + applicantDetailDto.getApplicationId());

            /* Defect #115163 */
            response = new ActionResponse(MainetConstants.COMMON_STATUS.FAIL.toUpperCase());
            response.addResponseData(MainetConstants.DECISION, MainetConstants.COMMON_STATUS.FAILURE);
            return response;
            /*
             * throw new FrameworkException("Unsuccessful Completion of task of id " + taskId + "for application : " +
             * applicantDetailDto.getApplicationId());
             */
        }
        response = new ActionResponse(MainetConstants.COMMON_STATUS.SUCCESS.toUpperCase());
        response.addResponseData(MainetConstants.RESPONSE, MainetConstants.COMMON_STATUS.SUCCESS);
        response.addResponseData(MainetConstants.DECISION,updateAction.getDecision());

        response.addResponseData(MainetConstants.REQUEST_NO, careRequest.getApplicationId().toString());
        response.addResponseData(MainetConstants.REQUIRED_PG_PARAM.COMPLAINT_NO, careRequest.getComplaintId());
        LOGGER.info("Department Level Review Action # Request No : " + careRequest.getApplicationId() + " Decision : "
                + updateAction.getDecision());
        return response;
    }

    @Override
    @WebMethod(exclude = true)
    @Transactional
    public ActionResponse restartCareProces(CareRequest careRequest, WorkflowTaskAction reopenAction) throws Exception {

        ActionResponse response = null;
        Date reopenEndDate = null;
        Date currentDate = new Date();
        String reopenDay = null;
        Organisation organisation = new Organisation();
        organisation.setOrgid(careRequest.getOrgId());
        List<UserTaskDTO> list = taskService.getTaskList(careRequest.getApplicationId().toString());
        if (list == null || list.isEmpty()) {
            LOGGER.info("Unable to reopen CARE Request due to task not found For  RequestNo: "
                    + careRequest.getApplicationId());
            throw new Exception("Unable to reopen CARE Request due to task not found For  RequestNo: "
                    + careRequest.getApplicationId());
        }
        /*D123919 start If reopen count days completed then complaint should not be able to reopen*/
        UserTaskDTO task = list.get(0);
        Optional<LookUp> lookup = CommonMasterUtility
                .getLookUps(PrefixConstants.ComplaintPrefix.COMPLAINT_EXPIRY_DURATION_DAYS_PREFIX,
                		organisation).stream().findFirst();
        if (lookup.isPresent()) {
        	 reopenDay = lookup.get().getLookUpCode();
             reopenEndDate = Utility.getAddedDateBy2(task.getCreatedDate(), Integer.parseInt(reopenDay));
        }
        /*Checked if reopend days are completed or not*/
        if(currentDate.after(reopenEndDate)) {
        	/*setting error code & error msg in eng & reg in response if reopend days are completed*/
        	response = new ActionResponse(MainetConstants.COMMON_STATUS.FAILURE.toUpperCase());
        	response.setError(MainetConstants.ERROR_CODE.CARE_REOPEN_EXPIRED_ERROR);
        	String reopenErrMsgEng = applicationSession.getMessage(MainetConstants.ServiceCareCommon.CARE_REOPEN_ERROR, 
        			MainetConstants.ServiceCareCommon.CARE_REOPEN_ERROR,new Locale(MainetConstants.REG_ENG.ENGLISH),
        			new Object[] {reopenDay});
        	String reopenErrMsgReg = applicationSession.getMessage(MainetConstants.ServiceCareCommon.CARE_REOPEN_ERROR, 
        			MainetConstants.ServiceCareCommon.CARE_REOPEN_ERROR,new Locale(MainetConstants.REG_ENG.REGIONAL),
        			new Object[] {reopenDay});
        	response.addResponseData(MainetConstants.ERROR_MESSAGE, reopenErrMsgEng);
        	response.addResponseData(MainetConstants.ERROR_MESSAGE_REG, reopenErrMsgReg);
        	return response;
        }
        /*D123919 end*/
        
        Long taskId = task.getTaskId();
        reopenAction.setTaskId(taskId);
        RequestDTO applicantDetailDto = this.getApplicationDetails(careRequest);
        WorkflowProcessParameter workflowProcessParameter = careWorkflowService
                .prepareReopenCareWorkflowProcessParameter(reopenAction);
        try {
            workflowExecutionService.updateWorkflow(workflowProcessParameter);
            careRequest.setModifiedDate(new Date());
            careRequest.setLastDateOfAction(new Date());
            careRequestRepository.save(careRequest);
            WorkflowRequest workflowRequest = workflowRequestService
                    .getWorkflowRequestByAppIdOrRefId(careRequest.getApplicationId(), null, careRequest.getOrgId());
            if (workflowRequest != null) {
                WorkflowMas workflowType = workFlowTypeRepository.findOne(workflowRequest.getWorkflowTypeId());
                sendSmsAndEmail(applicantDetailDto, workflowRequest, workflowType, careRequest);
            }

        } catch (Exception e) {
            LOGGER.error("Unsuccessful Completion of task of id " + taskId + "for application : "
                    + applicantDetailDto.getApplicationId());
            throw new Exception("Unsuccessful Completion of task of id " + taskId + "for application : "
                    + applicantDetailDto.getApplicationId(), e);
        }

        response = new ActionResponse(MainetConstants.COMMON_STATUS.SUCCESS.toUpperCase());
        response.addResponseData(MainetConstants.RESPONSE, MainetConstants.COMMON_STATUS.SUCCESS);
        response.addResponseData(MainetConstants.DECISION, MainetConstants.WorkFlow.Decision.REOPENED);
        response.addResponseData(MainetConstants.REQUEST_NO, careRequest.getApplicationId().toString());
        LOGGER.info("Successsfully Reopend CARE Request For  RequestNo: " + careRequest.getApplicationId());
        return response;
    }

    @Override
    @WebMethod(exclude = true)
    @Transactional
    public ActionResponse resubmitCareProces(CareRequest careRequest, WorkflowTaskAction resubmitAction)
            throws Exception {

        ActionResponse response = null;

        List<UserTaskDTO> list = taskService.getTaskList(careRequest.getApplicationId().toString());
        if (list == null || list.isEmpty()) {
            LOGGER.info("Unable to resubmit CARE Request due to task not found For  RequestNo: "
                    + careRequest.getApplicationId());
            throw new Exception("Unable to resubmit CARE Request due to task not found For  RequestNo: "
                    + careRequest.getApplicationId());
        }
        UserTaskDTO task = list.get(0);
        Long taskId = task.getTaskId();
        resubmitAction.setTaskId(taskId);
        RequestDTO applicantDetailDto = this.getApplicationDetails(careRequest);
        WorkflowProcessParameter workflowProcessParameter = careWorkflowService
                .prepareReopenCareWorkflowProcessParameter(resubmitAction);
        try {
            workflowExecutionService.updateWorkflow(workflowProcessParameter);
            careRequest.setModifiedDate(new Date());
            careRequest.setLastDateOfAction(new Date());
            careRequestRepository.save(careRequest);
            WorkflowRequest workflowRequest = workflowRequestService
                    .getWorkflowRequestByAppIdOrRefId(careRequest.getApplicationId(), null, careRequest.getOrgId());
            if (workflowRequest != null) {
                WorkflowMas workflowType = workFlowTypeRepository.findOne(workflowRequest.getWorkflowTypeId());
                sendSmsAndEmail(applicantDetailDto, workflowRequest, workflowType, careRequest);
            }

        } catch (Exception e) {
            LOGGER.error("Unsuccessful Completion of task of id " + taskId + "for application : "
                    + applicantDetailDto.getApplicationId());
            throw new Exception("Unsuccessful Completion of task of id " + taskId + "for application : "
                    + applicantDetailDto.getApplicationId(), e);
        }
        response = new ActionResponse(MainetConstants.COMMON_STATUS.SUCCESS.toUpperCase());
        response.addResponseData(MainetConstants.RESPONSE, MainetConstants.COMMON_STATUS.SUCCESS);
        response.addResponseData(MainetConstants.DECISION, MainetConstants.WorkFlow.Decision.SUBMITTED);
        response.addResponseData(MainetConstants.REQUEST_NO, careRequest.getApplicationId().toString());
        LOGGER.info("Successsfully Resubmited CARE Request For  RequestNo: " + careRequest.getApplicationId());
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CareRequestDTO> getCareRequestsByEmpIdAndEmplType(Long empId, Long emplType) {
        ComplaintSearchDTO filter = new ComplaintSearchDTO();
        filter.setEmpId(empId);
        filter.setEmplType(emplType);
        List<Object[]> objList = complaintDAO.searchComplaints(filter);
        return CareUtility.toCareRequestDTOS(objList);
    }

    @Override
    @Transactional(readOnly = true)
    public RequestDTO getApplicationDetails(CareRequest careRequest) {
        TbCfcApplicationMstEntity app = CFCApplicationMasterService
                .getCFCApplicationByApplicationId(careRequest.getApplicationId(), careRequest.getOrgId());
        CFCApplicationAddressEntity add = CFCApplicationAddressService
                .getApplicationAddressByAppId(careRequest.getApplicationId(), careRequest.getOrgId());
        return CareUtility.getApplicationDetails(app, add); // Method moved to utility class to use from anywhere.
    }

    @Override
    @Transactional(readOnly = true)
    public RequestDTO getApplicationDetailsByMobile(String mobileNumber) {

        RequestDTO applicationDetail = null;

        Optional<CFCApplicationAddressEntity> add = cfcApplicationAddressRepository
                .findTopByApaMobilnoOrderByLmodDateDesc(mobileNumber);
        if (add.isPresent()) {
            CFCApplicationAddressEntity address = add.get();
            TbCfcApplicationMstEntity app = CFCApplicationMasterService
                    .getCFCApplicationByApplicationId(address.getApmApplicationId(), address.getOrgId().getOrgid());
            applicationDetail = CareUtility.getApplicationDetails(app, address);
        }
        return applicationDetail;
    }

    @Override
    @Transactional(readOnly = true)
    public CareRequest findByApplicationId(Long applicationId) {
        return careRequestRepository.findByApplicationId(applicationId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CareDepartmentAction> findByCareId(Long id) {
        return careDepartmentActionRepository.findByCareId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public CareRequest findByComplaintId(String complaintId) {
        return careRequestRepository.findByComplaintId(complaintId);
    }

    @Override
    @Transactional(readOnly = true)
    public CareRequestDTO findById(Long id) {
        return CareUtility.toCareRequestDTO(careRequestRepository.findOne(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CareRequestDTO> findComplaint(ComplaintSearchDTO filter) {
        List<Object[]> objList = complaintDAO.searchComplaints(filter);
        List<CareRequestDTO> careRequestList = CareUtility.toCareRequestDTOS(objList);
        return careRequestList;

    }

    @Override
    @Transactional(readOnly = true)
    public String resolveWorkflowTypeDefinition(Long orgId, Long compTypeId) {
        String workflowTypeDefinition = "";
        try {
        WorkflowMas workflowType = workflowTyepResolverService.resolveComplaintWorkflowTypeDefination(orgId,
                compTypeId);
        if (workflowType != null)
            workflowTypeDefinition = workflowType.getType();
        }catch(Exception e) {
        	LOGGER.error("No workflow defination found for OrgId - "+orgId+" And complaintTypeId- "+compTypeId);
        }
        
        return workflowTypeDefinition;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<DepartmentComplaintDTO> getDepartmentComplaintsByOrgId(Long orgId) {
        Set<DepartmentComplaintDTO> departmentComplaintsDTO = new HashSet<>();
        List<WorkflowMas> workflowTypes = workFlowTypeRepository.getActiveComplaintWorkFlowTypeByOrgId(orgId,
                MainetConstants.MASTER.Y);
        workflowTypes.forEach(wt -> {
            departmentComplaintsDTO
                    .add(CareUtility.toDepartmentComplaintDTO(wt.getComplaint().getDepartmentComplaint()));
        });
        return departmentComplaintsDTO;

    }

    @Override
    @Transactional(readOnly = true)
    public Set<DepartmentComplaintTypeDTO> getDepartmentComplaintTypeByDepartmentId(Long deptId, Long orgId) {
        Set<DepartmentComplaintTypeDTO> careWorkflowDefinedComListDTO = new HashSet<>();
        List<WorkflowMas> workflowTypes = workFlowTypeRepository.getActiveComplaintWorkFlowTypeByOrgId(orgId,
                MainetConstants.MASTER.Y);
        workflowTypes.forEach(wt -> {
            if (wt.getComplaint().getDepartmentComplaint().getDepartment().getDpDeptid().equals(deptId)
                    || deptId == -1) {
                careWorkflowDefinedComListDTO.add(CareUtility.toDepartmentComplaintTypeDTO(wt.getComplaint()));
            }
        });
        
        // D#134038
        DepartmentComplaint depComplaint = iComplaintTypeService.findComplainedDepartmentByDeptId(deptId, orgId);
        depComplaint.getComplaintTypes().forEach(comSub -> {
            if (null != comSub.getExternalWorkFlowFlag() && comSub.getExternalWorkFlowFlag().equals(MainetConstants.FlagY)) {
                careWorkflowDefinedComListDTO.add(CareUtility.toDepartmentComplaintTypeDTO(comSub));
            }
        });

        careWorkflowDefinedComListDTO.stream()
                .filter(distinctByKey(DepartmentComplaintTypeDTO::getCompId))// distinct by object attributes(compId)
                .collect(Collectors.toSet());

        return careWorkflowDefinedComListDTO;
    }
    
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    /**
     * List only those location for which ward and zones are configured.
     * 
     */
    @Override
    @Transactional(readOnly = true)
    public List<LocationMasEntity> getDepartmentCompalintLocations(Long orgId, Long deptId) {
        List<LocationMasEntity> locations = new ArrayList<>();
        locations = locationMasJpaRepository.findAllLocationByDeptid(deptId, orgId);
        // locations =
        // operationWZMappingJpaRepository.findWZMappedLocationByOrgIdAndDeptId(orgId,
        // deptId);
        return locations;
    }

    @Override
    @WebMethod(exclude = true)
    public List<EscalationDetailsList> getEscalationDetailsList(WorkflowMas workflowType,
            WorkflowRequest workflowRequest, long langId, Long applicationId) {
        List<EscalationDetailsList> escalationDetailsList = new ArrayList<EscalationDetailsList>();

        List<WorkflowTask> taskList = workflowRequest.getWorkFlowTaskList();

        for (int i = 0; i < workflowType.getWorkflowDetList().size(); i++) {
            Set<Employee> employeeList = new LinkedHashSet<>();

            Set<String> emails = new LinkedHashSet<String>();
            Set<String> empNames = new LinkedHashSet<String>();
            Set<String> designations = new LinkedHashSet<String>();
            Set<String> mobileNos = new LinkedHashSet<String>();

            EscalationDetailsList escalationDetails = new EscalationDetailsList();
            escalationDetails.setLevel(i + 1);
            Optional<WorkflowTask> task = taskList.stream()
                    .filter(t -> t.getCurrentEscalationLevel().equals(escalationDetails.getLevel().longValue()))
                    .findFirst();
            if (task.isPresent()) {
                escalationDetails.setAssignmentDate(
                        Utility.dateToString(task.get().getDateOfAssignment(), MainetConstants.DATE_HOUR_FORMAT));
                escalationDetails
                        .setStatus(task.get().getTaskStatus().equalsIgnoreCase(MainetConstants.WorkFlow.Status.PENDING)
                                ? MainetConstants.WorkFlow.Status.IN_PROGRESS
                                : task.get().getTaskStatus());
            } else {
                escalationDetails.setStatus(MainetConstants.WorkFlow.Status.NOT_ASSIGNED);
                escalationDetails.setAssignmentDate(MainetConstants.BLANK);
            }
            if (langId == MainetConstants.MARATHI) {
                escalationDetails
                        .setDepartment(workflowType.getWorkflowDetList().get(i).getEventDepartment().getDpNameMar());
            } else {
                escalationDetails
                        .setDepartment(workflowType.getWorkflowDetList().get(i).getEventDepartment().getDpDeptdesc());
            }

            if (workflowType.getWorkflowDetList().get(i).getRoleOrEmpIds() != null) {
                if (workflowType.getWorkflowDetList().get(i).getRoleType()
                        .equals(MainetConstants.Common_Constant.ROLE)) {
                    List<String> roleIds = Arrays.asList(workflowType.getWorkflowDetList().get(i).getRoleOrEmpIds()
                            .split(MainetConstants.operator.COMMA));
                    roleIds.forEach(id -> employeeList.addAll(employeeService.getByGmId(Long.parseLong(id))));
                } else {
                    List<String> empIds = Arrays.asList(workflowType.getWorkflowDetList().get(i).getRoleOrEmpIds()
                            .split(MainetConstants.operator.COMMA));
                    empIds.forEach(id -> employeeList.add(employeeService.findEmployeeById(Long.parseLong(id))));
                }
            }

            employeeList.forEach(emp -> {
                emails.add(emp.getEmpemail());
                empNames.add(emp.getEmpname() + MainetConstants.WHITE_SPACE + emp.getEmplname());
                mobileNos.add(emp.getEmpmobno());
                if (langId == MainetConstants.MARATHI) {
                    designations.add(emp.getDesignation().getDsgnameReg());
                } else {
                    designations.add(emp.getDesignation().getDsgname());
                }
            });

            final List<LookUp> lookUps = CommonMasterUtility.getLookUps(PrefixConstants.UTS,
                    workflowType.getOrganisation());
            if (workflowType.getWorkflowDetList().get(i).getSla() != null) {
                for (final LookUp lookUp : lookUps) {
                    final String lid = Long.toString(lookUp.getLookUpId());
                    if (lid.equals(workflowType.getWorkflowDetList().get(i).getUnit().toString())) {
                        escalationDetails.setSla(formatSla(workflowType.getWorkflowDetList().get(i).getSla())
                                .concat(MainetConstants.WHITE_SPACE)
                                .concat((langId == MainetConstants.MARATHI) ? lookUp.getDescLangSecond()
                                        : lookUp.getDescLangFirst()));
                        break;
                    }
                }
                
            }

            escalationDetails.setEmail(Utility.iterableToString(emails, MainetConstants.operator.ORR));
            escalationDetails.setEmpName(Utility.iterableToString(empNames, MainetConstants.operator.ORR));
            escalationDetails.setMobile(Utility.iterableToString(mobileNos, MainetConstants.operator.ORR));
            escalationDetails.setDesignation(Utility.iterableToString(designations, MainetConstants.operator.ORR));
            escalationDetailsList.add(escalationDetails);
        }
        return escalationDetailsList;
    }
    
    // D#126775 issue no 9
    String formatSla(String sla) {
        String sub = sla.substring(sla.indexOf(".")).substring(1);
        Long num = Long.parseLong(sub);
        double p = Double.valueOf(sla);
        if (num > 0) {
            // 1.50 to 1.5
            sla = String.valueOf(p);
        } else {
            // parse in integer 1.00 to 1
            sla = Integer.toString((int) p);
        }
        return sla;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComplaintTaskDTO> getOpenComplaintTaskByEmployeeId(TaskSearchRequest taskSearchRequest)
            throws Exception {
        List<ComplaintTaskDTO> careTask = new ArrayList<>();
        taskSearchRequest.setStatus(MainetConstants.WorkFlow.Status.PENDING);
        List<UserTaskDTO> userTasks = taskService.getTaskList(taskSearchRequest);
        if (CollectionUtils.isNotEmpty(userTasks)) {
            userTasks.forEach(task -> {
                if (task.getApplicationId() != null) {
                    ComplaintTaskDTO ctask = new ComplaintTaskDTO();
                    BeanUtils.copyProperties(task, ctask);
                    Organisation org = new Organisation(ctask.getOrgId());
                    CareRequest careRequest = new CareRequest();
                    careRequest.setApplicationId(ctask.getApplicationId());
                    careRequest.setOrgId(ctask.getOrgId());

                    TbCfcApplicationMstEntity app = CFCApplicationMasterService
                            .getCFCApplicationByApplicationId(careRequest.getApplicationId(), careRequest.getOrgId());
                    CFCApplicationAddressEntity add = CFCApplicationAddressService
                            .getApplicationAddressByAppId(careRequest.getApplicationId(), careRequest.getOrgId());

                    careRequest = careRequestRepository.findByApplicationId(task.getApplicationId());
                    ctask.setCareRequest(CareUtility.toCareRequestDTO(careRequest));

                    // age of request
                    if (ctask.getCareRequest() != null) {

                        Date currentdate = new Date();
                        Long diff = Math
                                .abs(currentdate.getTime() - ctask.getCareRequest().getDateOfRequest().getTime());
                        Long ageOfRequest = diff / (24 * 60 * 60 * 1000);
                        ctask.setAgeOfRequest(ageOfRequest);
                        // age of request
                        ctask.setApmFname(app.getApmFname());
                        ctask.setApmLname(app.getApmLname());
                        ctask.setApmSex(app.getApmSex());
                        if(StringUtils.isNotBlank(app.getApmSex()) && StringUtils.isNumeric(app.getApmSex())) {
                        	ctask.setApmSexDesc(CommonMasterUtility
                                .getNonHierarchicalLookUpObject(Long.valueOf(app.getApmSex()), org).getDescLangFirst());
                        	//#135509, #142867
                        	ctask.setApmSexDescReg(CommonMasterUtility.
                        			getNonHierarchicalLookUpObject(Long.valueOf(app.getApmSex()),org).getDescLangSecond());
                        	
                        }else {
                        	ctask.setApmSexDesc(app.getApmSex());
                        }
                        ctask.setApaEmail(add.getApaEmail());
                        ctask.setApaAreanm(add.getApaAreanm());
                        ctask.setApaMobilno(add.getApaMobilno());
                        ctask.setPincode(String.valueOf(add.getApaPincode()));
                        if(ctask.getCareRequest().getReferenceDate()!=null) {
                        String input = ctask.getCareRequest().getReferenceDate().toString();
                        String output = input.substring(0, 10);
                        ctask.setReferenceDate(output);
                        }

                    }
                    careTask.add(ctask);
                }

            });

        }
        return careTask;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<DepartmentDTO> getCareWorkflowMasterDefinedDepartmentListByOrgId(Long orgId) {

        Set<DepartmentDTO> departments = new HashSet<>();
        List<DepartmentComplaint> careWorkflowDefinedDeptList = new ArrayList<>();
        List<WorkflowMas> workflowTypes = workFlowTypeRepository.getActiveComplaintWorkFlowTypeByOrgId(orgId,
                MainetConstants.MASTER.Y);
        workflowTypes.forEach(wt -> {
            careWorkflowDefinedDeptList.add(wt.getComplaint().getDepartmentComplaint());
        });
        careWorkflowDefinedDeptList.forEach(dc -> {
            departments.add(CareUtility.toDepartmentDTO(dc.getDepartment()));
        });
        return departments;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkflowTaskActionWithDocs> getCareWorkflowActionLogByApplicationId(final Long applicationId,
            Long orgId, long langId) {
        List<WorkflowTaskActionWithDocs> actions = workflowActionService
                .getWorkflowActionLogByApplicationId(applicationId.toString(), (short) langId);

        List<WorkflowTaskActionWithDocs> completedActions = new ArrayList<>();

        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        /*List<LookUp> decisionLookUps = CommonMasterUtility
                .getLookUps(PrefixConstants.ComplaintPrefix.COMPLAINT_DECISION_PREFIX, organisation);*/
        actions.stream().forEach(action -> {
            /* D#115566 setting full employee name */

            LOGGER.info("Getting Emp Details for empId :" + action.getEmpId());
            Employee employee = employeeService.findEmployeeByIdAndOrgId(action.getEmpId(), action.getOrgId());
            if (employee != null)
                action.setEmpName(employee.getEmpname() + " " + employee.getEmplname());
            String decisionCode = action.getTaskDecision();
            if (!decisionCode.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.PENDING)) {
                LOGGER.info("Action is : " + decisionCode + " for EmpId " + action.getEmpId());
                
                /*Code commented because decision value is already coming based on lang Id from getWorkflowActionLogByApplicationId method*/
                /*Optional<LookUp> decisionLookUp = decisionLookUps.stream()
                        .filter(l -> l.getLookUpCode().equals(decisionCode)).findFirst();
                if (decisionLookUp.isPresent()) {
                    LookUp lookUp = decisionLookUps.stream().filter(l -> l.getLookUpCode().equals(decisionCode))
                            .findFirst().get();
                    if (langId == MainetConstants.MARATHI)
                        action.setDecision(lookUp.getDescLangSecond());
                    else if (langId == MainetConstants.ENGLISH)
                        action.setDecision(lookUp.getDescLangFirst());
                } else {
                    String code = MainetConstants.WorkFlow.Remarks.DECISION_ROOT + action.getDecision().toLowerCase();
                    if (langId == MainetConstants.MARATHI)
                        action.setDecision(applicationSession.getMessage(code, code,
                                new Locale(MainetConstants.REG_ENG.REGIONAL)));
                    else if (langId == MainetConstants.ENGLISH)
                        action.setDecision(
                                applicationSession.getMessage(code, code, new Locale(MainetConstants.REG_ENG.ENGLISH)));
                }*/
                LOGGER.info("Adding action object in list for Id >>" + action.getId());
                completedActions.add(action);
            }
        });
        return completedActions;
    }

    @Override
    @WebMethod(exclude = true)
    public ComplaintAcknowledgementModel getComplaintAcknowledgementModel(CareRequest careRequest, long langId) {
        if (careRequest == null)
            return null;
        Organisation organisation = organisationService.getOrganisationById(careRequest.getOrgId());
        RequestDTO applicantDetailDto = getApplicationDetails(careRequest);
        WorkflowRequest workflowRequest = workflowRequestService
                .getWorkflowRequestByAppIdOrRefId(careRequest.getApplicationId(), null, careRequest.getOrgId());
        ComplaintAcknowledgementModel complaintAcknowledgementModel = new ComplaintAcknowledgementModel();
        complaintAcknowledgementModel.setApplicationId(careRequest.getApplicationId());
        //#142788
        complaintAcknowledgementModel.setComplainantName(applicantDetailDto.getfName().concat(MainetConstants.WHITE_SPACE)
        		.concat(applicantDetailDto.getmName() != null ? applicantDetailDto.getmName().concat(MainetConstants.WHITE_SPACE) : MainetConstants.BLANK)
        		.concat(applicantDetailDto.getlName()));
        
        complaintAcknowledgementModel.setComplainantMobileNo(applicantDetailDto.getMobileNo());
        complaintAcknowledgementModel.setComplainantEmail(applicantDetailDto.getEmail());
        complaintAcknowledgementModel.setDescription(careRequest.getDescription());
        complaintAcknowledgementModel.setLandmark(careRequest.getLandmark());
        complaintAcknowledgementModel.setAddress(applicantDetailDto.getAreaName());
        complaintAcknowledgementModel.setOrgShortNm(organisation.getOrgShortNm());
        
        if (CareUtility.isENVCodePresent(MainetConstants.ENV_DSCL,
        		careRequest.getOrgId())) {
        
        LookUp rfmLookup = CommonMasterUtility.getValueFromPrefixLookUp("DT", "RFM",
        		organisation);
        
        if(careRequest.getReferenceMode().equalsIgnoreCase(String.valueOf(rfmLookup.getLookUpId()))){
        	complaintAcknowledgementModel.setRfmMode(rfmLookup.getLookUpCode() + "");
        	}        
        
        }
        
        LOGGER.info(
                "Setting organisation short code: " + organisation.getOrgShortNm());
        if (langId == MainetConstants.DEFAULT_LANGUAGE_ID) {
            if (careRequest.getWard1() != null) {
                complaintAcknowledgementModel.setWard(CommonMasterUtility
                        .getHierarchicalLookUp(careRequest.getWard1(), organisation).getDescLangFirst());
            }
            
            if(careRequest.getWard2() != null){
            	complaintAcknowledgementModel.setWard1(CommonMasterUtility.getHierarchicalLookUp(careRequest.getWard2(),organisation).getDescLangFirst());
            }

            if(careRequest.getWard3() != null){
            	complaintAcknowledgementModel.setWard2(CommonMasterUtility.getHierarchicalLookUp(careRequest.getWard3(),organisation).getDescLangFirst());
            }

            if(careRequest.getWard4() != null){
            	complaintAcknowledgementModel.setWard3(CommonMasterUtility.getHierarchicalLookUp(careRequest.getWard4(),organisation).getDescLangFirst());
            }

            if(careRequest.getWard5() != null){
            	complaintAcknowledgementModel.setWard4(CommonMasterUtility.getHierarchicalLookUp(careRequest.getWard5(),organisation).getDescLangFirst());
            }
        } else if (langId == MainetConstants.MARATHI) {
            if (careRequest.getWard1() != null) {
                complaintAcknowledgementModel.setWard(CommonMasterUtility
                        .getHierarchicalLookUp(careRequest.getWard1(), organisation).getDescLangSecond());
            }
            
            if(careRequest.getWard2() != null){
            	complaintAcknowledgementModel.setWard1(CommonMasterUtility.getHierarchicalLookUp(careRequest.getWard2(),organisation).getDescLangSecond());
            }

            if(careRequest.getWard3() != null){
            	complaintAcknowledgementModel.setWard2(CommonMasterUtility.getHierarchicalLookUp(careRequest.getWard3(),organisation).getDescLangSecond());
            }

            if(careRequest.getWard4() != null){
            	complaintAcknowledgementModel.setWard3(CommonMasterUtility.getHierarchicalLookUp(careRequest.getWard4(),organisation).getDescLangSecond());
            }

            if(careRequest.getWard5() != null){
            	complaintAcknowledgementModel.setWard4(CommonMasterUtility.getHierarchicalLookUp(careRequest.getWard5(),organisation).getDescLangSecond());
            }
        }
        /*
         * if (careRequest.getComplaintId() != null) complaintAcknowledgementModel.setTokenNumber(careRequest.getComplaintId());
         * else complaintAcknowledgementModel.setTokenNumber(careRequest.getApplicationId().toString());
         */
        // String complaintId=generateComplaintNo(careRequest);
        complaintAcknowledgementModel.setTokenNumber(careRequest.getComplaintId());
        complaintAcknowledgementModel.setDate(careRequest.getCreatedDate());
        complaintAcknowledgementModel
                .setFormattedDate(Utility.dateToString(careRequest.getCreatedDate(), MainetConstants.DATE_HOUR_FORMAT));
        TbDepartment department = tbDepartmentService.findById(careRequest.getDepartmentComplaint());
        ComplaintType compType = null;
        TbServicesMst serviceMast = null;
        
        if (null!=department.getDpDeptcode() && department.getDpDeptcode().equalsIgnoreCase(MainetConstants.DEPT_SHORT_NAME.PROPERTY)) {
        	complaintAcknowledgementModel.setComplaintDeptCode(department.getDpDeptcode());
        	complaintAcknowledgementModel.setExtReferenceNo(careRequest.getExtReferNumber());
		}
        else if(null!=department.getDpDeptcode() && department.getDpDeptcode().equalsIgnoreCase(MainetConstants.DEPT_SHORT_NAME.WATER)){
        	complaintAcknowledgementModel.setComplaintDeptCode(department.getDpDeptcode());
        	complaintAcknowledgementModel.setExtReferenceNo(careRequest.getExtReferNumber());
        }
        
        
        if (langId == MainetConstants.MARATHI) {
            complaintAcknowledgementModel.setOrganizationName(organisation.getoNlsOrgnameMar());

            complaintAcknowledgementModel.setDepartment(department.getDpNameMar());
            if (careRequest.getApplnType() != null
                    && careRequest.getApplnType().equalsIgnoreCase(MainetConstants.FlagR)) {
                serviceMast = tbServicesMstService.findById(careRequest.getSmServiceId());
                complaintAcknowledgementModel.setComplaintSubType(serviceMast.getSmServiceNameMar());
            } else {
                compType = iComplaintTypeService.findComplaintTypeById(careRequest.getComplaintType());
                complaintAcknowledgementModel.setComplaintSubType(compType.getComplaintDescreg());
            }

        } else {
            complaintAcknowledgementModel.setOrganizationName(organisation.getONlsOrgname());
            complaintAcknowledgementModel.setDepartment(department.getDpDeptdesc());
            if (careRequest.getApplnType() != null
                    && careRequest.getApplnType().equalsIgnoreCase(MainetConstants.FlagR)) {
                serviceMast = tbServicesMstService.findById(careRequest.getSmServiceId());
                complaintAcknowledgementModel.setComplaintSubType(serviceMast.getSmServiceName());
            } else {
                compType = iComplaintTypeService.findComplaintTypeById(careRequest.getComplaintType());
                complaintAcknowledgementModel.setComplaintSubType(compType.getComplaintDesc());
            }
        }

        if (workflowRequest != null) {
            WorkflowMas wt = workFlowTypeRepository.findOne(workflowRequest.getWorkflowTypeId());
            complaintAcknowledgementModel.setStatus(workflowRequest.getStatus());
            complaintAcknowledgementModel.setLastDecision(workflowRequest.getLastDecision());
            List<EscalationDetailsList> escalationDetailsList = getEscalationDetailsList(wt, workflowRequest, langId,
                    careRequest.getApplicationId());
            complaintAcknowledgementModel.setEscalationDetailsList(escalationDetailsList);
        }

        List<WorkflowTaskActionWithDocs> actions = getCareWorkflowActionLogByApplicationId(
                careRequest.getApplicationId(), organisation.getOrgid(), langId);
        
        complaintAcknowledgementModel.setActions(actions);
        /* complaintAcknowledgementModel.setActionsPending(actionsPending); */
        return complaintAcknowledgementModel;
    }

    private String generateComplaintNo(CareRequest careRequest,Long loggedInLocId) {
        String wordCode = null;
        Organisation org = organisationService.getOrganisationById(careRequest.getOrgId());
        final Date sysDate = UtilityService.getSQLDate(new Date());
        final String date = sysDate.toString();
        final String[] dateParts = date.split(MainetConstants.HYPHEN);
        final String year = dateParts[0];
        final String month = dateParts[1];
        final String day = dateParts[2];
        final String ddmmyyyy = day.concat(month).concat(year);

        String orgId = null;
        String zero = MainetConstants.ZERO;

        orgId = String.valueOf(org.getOrgid());
        if (org.getOrgid() <= 9) {
            orgId = zero.concat(orgId);
        }
        // User Story #96687
        SequenceConfigMasterDTO configMasterDTO = null;

        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER,
                PrefixConstants.STATUS_ACTIVE_PREFIX);

        configMasterDTO = seqGenFunctionUtility.loadSequenceData(org.getOrgid(), deptId,
                MainetConstants.ServiceCareCommon.SQLTables.TB_CARE_REQUEST,
                MainetConstants.ServiceCareCommon.SQLTables.COMPLAINT_NO);

        if (configMasterDTO.getSeqConfigId() == null) {
            final long number = seqGenFunctionUtility.generateSequenceNo(CommonConstants.COM,
                    MainetConstants.ServiceCareCommon.SQLTables.TB_CARE_REQUEST,
                    MainetConstants.ServiceCareCommon.SQLTables.COMPLAINT_NO, careRequest.getOrgId(), MainetConstants.FlagF,
                    null);
            final String paddingComplaintNo = String.format(MainetConstants.CommonMasterUi.PADDING_SIX, number);

            if (careRequest.getWard1() != null) {
                wordCode = CommonMasterUtility.getHierarchicalLookUp(careRequest.getWard1(), org).getLookUpCode();
            }
            //D#125745 Adding -1 because ALL option for other than 1st level internally set -1 value
            if (careRequest.getWard2() != null && careRequest.getWard2()!=-1) {
                wordCode = CommonMasterUtility.getHierarchicalLookUp(careRequest.getWard2(), org).getLookUpCode();
            }
            if (careRequest.getWard3() != null && careRequest.getWard3()!=-1) {
                wordCode = CommonMasterUtility.getHierarchicalLookUp(careRequest.getWard3(), org).getLookUpCode();
            }
            if (careRequest.getWard4() != null && careRequest.getWard4()!=-1) {
                wordCode = CommonMasterUtility.getHierarchicalLookUp(careRequest.getWard4(), org).getLookUpCode();
            }
            if (careRequest.getWard5() != null && careRequest.getWard5()!=-1) {
                wordCode = CommonMasterUtility.getHierarchicalLookUp(careRequest.getWard5(), org).getLookUpCode();
            }
            return ddmmyyyy.concat(org.getOrgShortNm()).concat(wordCode).concat(paddingComplaintNo);
        } else {
            CommonSequenceConfigDto commonDto = new CommonSequenceConfigDto();
            /*
             * D#134535 if (loggedInLocId != null) { if (CareUtility.isENVCodePresent(MainetConstants.ENV_SKDCL,
             * careRequest.getOrgId()) && loggedInLocId != null) { Department dept =
             * ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
             * .findDepartmentByCode(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER); LocOperationWZMappingDto
             * locOperationWZMappingDto = iLocationMasService.findOperLocationAndDeptId( loggedInLocId, dept.getDpDeptid()); if
             * (locOperationWZMappingDto != null) { if (locOperationWZMappingDto.getCodIdOperLevel1() != null) {
             * commonDto.setLevel1Id(locOperationWZMappingDto.getCodIdOperLevel1()); } if
             * (locOperationWZMappingDto.getCodIdOperLevel2() != null) {
             * commonDto.setLevel2Id(locOperationWZMappingDto.getCodIdOperLevel2()); } if
             * (locOperationWZMappingDto.getCodIdOperLevel3() != null) {
             * commonDto.setLevel3Id(locOperationWZMappingDto.getCodIdOperLevel3()); } if
             * (locOperationWZMappingDto.getCodIdOperLevel4() != null) {
             * commonDto.setLevel4Id(locOperationWZMappingDto.getCodIdOperLevel4()); } if
             * (locOperationWZMappingDto.getCodIdOperLevel5() != null) {
             * commonDto.setLevel5Id(locOperationWZMappingDto.getCodIdOperLevel5()); } } } }
             */

            String number = seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, commonDto);
            // change here for ASCL
            if (CareUtility.isENVCodePresent(MainetConstants.ENV_ASCL, careRequest.getOrgId())) {
                return number;
            } else if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT, careRequest.getOrgId())) {
                return MainetConstants.ServiceCareCommon.CARE_CN.concat(number);
            } else {
                return orgId.concat(number);
            }
			
        }
    }

    private void sendSmsAndEmail(final RequestDTO applicantDetailDto, final WorkflowRequest workflowRequest,
            final WorkflowMas workflowType, CareRequest careRequest) {

        final String decision = workflowRequest.getLastDecision();
        final String SMS_TYEP = PrefixConstants.SMS_EMAIL_ALERT_TYPE_BY_DECISION.getSmsEmailAlertType(decision);
        final int lanId = applicantDetailDto.getLangId().intValue();
        String compTypeDesc = "";
		if (careRequest.getComplaintType() != null) {
			ComplaintType cmType = iComplaintTypeService.findComplaintTypeById(careRequest.getComplaintType());
			if (cmType != null) {
				if (lanId == MainetConstants.MARATHI) {
					compTypeDesc = cmType.getComplaintDescreg();
				} else {
					compTypeDesc = cmType.getComplaintDesc();
				}
			}
		}
        // Return if SMS/Email type is unknown
        if (SMS_TYEP == null || SMS_TYEP.isEmpty())
            return;

        /*
         * In case of multiple approvals application status will be closed, which means Work-flow predefined approval count
         * reached to numbers of 'APPROVED' decision gathered, and all pending task has been closed with final decision as
         * "'APPROVED'.
         */
        if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED)
                && !workflowRequest.getStatus().equalsIgnoreCase(MainetConstants.WorkFlow.Status.CLOSED))
            return;

        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        String userName = applicantDetailDto.getfName() == null ? MainetConstants.BLANK
                : applicantDetailDto.getfName() + MainetConstants.WHITE_SPACE;
        userName += applicantDetailDto.getmName() == null ? MainetConstants.BLANK
                : applicantDetailDto.getmName() + MainetConstants.WHITE_SPACE;
        userName += applicantDetailDto.getlName() == null ? MainetConstants.BLANK : applicantDetailDto.getlName();

        dto.setOrgId(workflowType.getOrganisation().getOrgid());
        dto.setOrganizationName(workflowType.getOrganisation().getONlsOrgname());
        dto.setOrgName(workflowType.getOrganisation().getONlsOrgname());
        dto.setServName(workflowType.getService().getSmServiceName());
        dto.setServNameMar(workflowType.getService().getSmServiceNameMar());

        dto.setAppName(userName);
        dto.setEmail(applicantDetailDto.getEmail());
        dto.setMobnumber(applicantDetailDto.getMobileNo());
        dto.setUserId(applicantDetailDto.getUserId());
        dto.setLangId(lanId);

        dto.setTokenNumber(careRequest.getComplaintId());
        dto.setAppNo(careRequest.getComplaintId());
        ComplaintType compType = null;
        TbServicesMst serviceMast = null;
        if (applicantDetailDto.getLangId() != null
                && applicantDetailDto.getLangId() == MainetConstants.DEFAULT_LANGUAGE_ID) {
            if (careRequest.getApplnType().equalsIgnoreCase(MainetConstants.FlagR)) {
                serviceMast = tbServicesMstService.findById(careRequest.getSmServiceId());
                dto.setType(serviceMast.getSmServiceName());
            } else {
                compType = iComplaintTypeService.findComplaintTypeById(careRequest.getComplaintType());
                dto.setType(compType.getComplaintDesc());
            }

        } else if (applicantDetailDto.getLangId() != null
                && applicantDetailDto.getLangId() == MainetConstants.MARATHI) {
            if (careRequest.getApplnType().equalsIgnoreCase(MainetConstants.FlagR)) {
                serviceMast = tbServicesMstService.findById(careRequest.getSmServiceId());
                dto.setType(serviceMast.getSmServiceNameMar());
            } else {
                compType = iComplaintTypeService.findComplaintTypeById(careRequest.getComplaintType());
                dto.setType(compType.getComplaintDescreg());
            }

        }
        dto.setAppDate(Utility.dateToString(workflowRequest.getDateOfRequest(), MainetConstants.DATE_HOUR_FORMAT));

        dto.setDeptShortCode(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER);
        dto.setDecision(SMS_TYEP);
        dto.setTemplateType(SMS_TYEP);
        dto.setServiceUrl(MainetConstants.ServiceCareCommon.SMS_EMAIL_URL.GRIEVANCE_DEPARTMENT_REGISTRATION);
        dto.setServiceId(workflowType.getService().getSmServiceId());
        dto.setSubject(compTypeDesc);
        sMSAndEmailService.sendEmailSMS(dto.getDeptShortCode(), dto.getServiceUrl(), dto.getDecision(), dto,
                workflowType.getOrganisation(), lanId);

    }

    private String getNewComplaintId() {
        final long number = seqGenFunctionUtility.generateJavaSequenceNo(CommonConstants.COM,
                MainetConstants.ServiceCareCommon.SQLTables.TB_CARE_REQUEST,
                MainetConstants.ServiceCareCommon.SQLTables.COMPLAINT_NO, MainetConstants.CommonMasterUi.D, null);
        final Date sysDate = UtilityService.getSQLDate(new Date());
        final String date = sysDate.toString();
        final String[] dateParts = date.split(MainetConstants.HYPHEN);
        final String year = dateParts[0];
        final String month = dateParts[1];
        final String day = dateParts[2];
        final String subString = year.substring(2);
        final String YYMMDDDate = subString.concat(month).concat(day);
        final String paddingAppNo = String.format(MainetConstants.ServiceCareCommon.SQLTables.COMPLAINT_NO_FORMAT,
                number);
        final String complaintId = YYMMDDDate.concat(paddingAppNo);
        return complaintId;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrganisationDTO> getOrganisationByDistrict(Long districtCpdId) {
        List<OrganisationDTO> organisations = new ArrayList<>();
        List<Organisation> organisationList = organisationDAO.getOrganisations("", MainetConstants.STATUS.ACTIVE,
                districtCpdId);
        organisationList.forEach(o -> {
            organisations.add(CareUtility.toOrganisationDTO(o));
        });
        return organisations;
    }

    @Override
    @Consumes("application/json")
    @ApiOperation(value = "findLevel", notes = "findLevel")
    @POST
    @Path("/getPrefixLevel/{prefix}/{orgId}")
    @Transactional
    public Long getPrefixLevel(@PathParam("prefix") String prefix, @PathParam("orgId") Long orgId) {

        return careRequestRepository.getPrefixLevelCount(prefix, orgId);
    }

    @Override
    @CrossOrigin(origins = "*")
    @Consumes("application/json")
    @ApiOperation(value = "findLevelData", notes = "findLevelData")
    @POST
    @Path("/getPrefixLevelData/{prefix}/{orgId}")
    @Transactional
    public List<TbComparentMas> getPrefixLevelData(@PathParam("prefix") String prefix, @PathParam("orgId") Long orgId) {

        TbComparamMas tbComparamMas = tbComparamMasService.findComparamDetDataByCpmId(prefix);

        final List<TbComparentMasEntity> entities = tbComparentMasJpaRepository
                .findComparentMasDataById(tbComparamMas.getCpmId(), orgId);
        final List<TbComparentMas> beans = new ArrayList<>();

        for (final TbComparentMasEntity tbComparentMasEntity : entities) {
            beans.add(tbComparentMasServiceMapper.mapTbComparentMasEntityToTbComparentMas(tbComparentMasEntity));

        }
        return beans;

    }

    @Override
    @CrossOrigin(origins = "*")
    @Consumes("application/json")
    @ApiOperation(value = "fetch complaint no by filter", notes = "fetch complaint no by filter")
    @POST
    @Path("/fetchComplaints")
    public List<CareRequestDTO> findComplaintDetails(@RequestBody ComplaintSearchDTO filter) {
        List<Object[]> objList = complaintDAO.searchComplaintsDetail(filter);
        List<CareRequestDTO> careRequestList = CareUtility.toCareRequestDTOS(objList);
        return careRequestList;

    }
    
    @Override
    public List<CareRequestDTO> findComplaintDetailsForCareOperatorRole(ComplaintSearchDTO filter) {
        List<Object[]> objList = complaintDAO.searchComplaintsDetailForCareOperatorRole(filter);
        List<CareRequestDTO> careRequestList = CareUtility.toCareRequestDTOSForCareOperator(objList);
        return careRequestList;

    }
    
    @Override
    public Object getAllEmployeeByDept(Long orgId, Long deptId){
    	ActionResponse response = new ActionResponse();
    	List<Employee> empList = new ArrayList<>();
    	List<Employee> empData= employeeService.findAllEmployeeByDept(orgId, deptId);
    	if(CollectionUtils.isNotEmpty(empData)) {
    	for(Employee emp : empData) {
    		Employee e = new Employee();
    		e.setEmpId(emp.getEmpId());
    		e.setEmpname(emp.getEmpname());
    		e.setEmplname(emp.getEmplname());
    		e.setEmpmname(emp.getEmpmname());
    		e.setEmpmobno(emp.getEmpmobno());
    		empList.add(e);
    	}
    		response.setDataList(empList);
    	}
    	else
    		response.setError("No record found");
    	return response;
    }
    
    @Override
    public Object getOperationalWardZonePrefixName(Long deptId,Long orgId) {
    	String prefixName = null;
    	
    	/*In SKDCL wardzone wise workflow is defined on basis of selected department Id's prefix
    	 And other environment wardzone wise workflow is defined on basis of CFC department Id(CWZ prefix)*/
    	if(CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT, orgId)) {
    		prefixName = tbDepartmentService.findDepartmentPrefixName(deptId,
				orgId);
    	}else {
    		prefixName=MainetConstants.COMMON_PREFIX.CWZ;
    	}
		ActionResponse response = new ActionResponse();
    	if(StringUtils.isNotBlank(prefixName))
    		response.setResponse(prefixName);
    	else
    		response.setError("No record found");
    	return response;
    }
    
    @Override
    public Object resolveWorkflowAndGetLocType(Long orgId, Long compTypeId) {
    	ActionResponse response = new ActionResponse();
        String workflowTypeDefinition = resolveWorkflowTypeDefinition(orgId,compTypeId);
        if(StringUtils.isNotBlank(workflowTypeDefinition))
    		response.setResponse(workflowTypeDefinition);
    	else
    		response.setError("No Workflow is defined");
    	return response;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public boolean callSWMWorkForceAPI(CareRequest careRequest) {
    	try {
    		LOGGER.info(" Complaint With SWM Started ---------------->" ); 
				RequestDTO applicantDetailDto = this.getApplicationDetails(careRequest);
				ResponseEntity<?> responseEntity = null;
				Map<String, String> requestParam = new HashMap<>();
				ResponseEntity<?> Response = null;
				String token = null;
				requestParam.put("authorizationKey",ApplicationSession.getInstance().getMessage("authorizationKey"));
				requestParam.put("password", ApplicationSession.getInstance().getMessage("password"));
				requestParam.put("username", ApplicationSession.getInstance().getMessage("username"));
				responseEntity = RestClient.callRestTemplateClient(requestParam,ApplicationSession.getInstance().getMessage("swm.complaint.api.tokenUrl"));
				LOGGER.info("Complaint Token Response ---------------->"+ responseEntity.getStatusCode() );
			if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {
				Map<String, String> response = (Map<String, String>) responseEntity.getBody();
				if (response.containsKey("access_token")) {
					token = response.get("access_token");
					LOGGER.info(" Token Value ---------------->" + token);
				}
				ComplaintType complaintType = iComplaintTypeService
						.findComplaintTypeById(careRequest.getComplaintType());
				List<CFCAttachment> docs = new ArrayList<>();
				docs = iChecklistVerificationService.findAttachmentsForAppId(careRequest.getApplicationId(), null,
						careRequest.getOrgId());
				SWMComplaintDTO dto = new SWMComplaintDTO();
				SWMComplaintFilesDTO files = new SWMComplaintFilesDTO();
				dto.setName(applicantDetailDto.getfName().concat(MainetConstants.WHITE_SPACE)
						.concat(applicantDetailDto.getlName()));
				dto.setAddress(applicantDetailDto.getAreaName());
				dto.setEmail(applicantDetailDto.getEmail());
				dto.setPhoneNo(applicantDetailDto.getMobileNo());
				if (StringUtils.isNotEmpty(careRequest.getLatitude())) {
					dto.setLatitude(careRequest.getLatitude());
					LOGGER.info(
			                "Latitude when it is not empty : " + careRequest.getLatitude());
				} else {
					dto.setLatitude(String.valueOf(30.3176066));// hardcoded just for testing API functionality. This
																// need to be removed
					LOGGER.info("In else part of Latitude: " + dto.getLatitude());
				}
				if (StringUtils.isNotEmpty(careRequest.getLongitude())) {
					dto.setLongitude(careRequest.getLongitude());
					LOGGER.info(
			                "Longitude when it is not empty : " + careRequest.getLongitude());
				} else {
					dto.setLongitude(String.valueOf(78.0291381));// hardcoded just for testing API functionality. This
																	// need to be removed
					LOGGER.info("In else part of Longitude: " + dto.getLongitude());
				}
				dto.setDescription(careRequest.getDescription());
				dto.setComplaintId(careRequest.getComplaintId());
				dto.setIncidentSubTypeId(careRequest.getComplaintType());
				dto.setIncidentSubType(complaintType.getComplaintDesc());
				List<String> images = new ArrayList<>();
				if (!docs.isEmpty()) {
					docs.forEach(d -> {
						images.add(d.getAttPath());
					});
				}
				files.setImages(images);
				dto.setFiles(files);
				
				images.forEach(img->
				LOGGER.info("Image Byte Code for image: " + img.getBytes()));
				
				
				LOGGER.info("SWMComplaintDTO Printed dto.toString(): " + dto.toString());
				LOGGER.info("SWMComplaintFilesDTO Printed files.toString(): " + files.toString());
				
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "application/json");
				headers.add("Authorization", "Bearer " + token);
				Response = RestClient.callRestTemplateClientWithHeaders(dto,
						ApplicationSession.getInstance().getMessage("swm.complaint.api.saveUrl"), HttpMethod.POST,
						String.class, headers);
				LOGGER.info("Complaint Save Response ---------------->" + Response.getStatusCode());
				LOGGER.info("Complaint Save Response ---------------->" + Response.getBody());
				if ((Response != null) && (Response.getStatusCode() == HttpStatus.OK)) {
					Map<String, Object> saveDesponse = null;
					ObjectMapper mapper = Utility.getMapper();
					saveDesponse = mapper.readValue((String) Response.getBody(), Map.class);
					if ((boolean)saveDesponse.containsKey("status")) {
						boolean status = (boolean) saveDesponse.get("status");
						if (status) {
							return true;
						}
					}
					
				}
			}
			
		} catch (Exception ex) {
			LOGGER.info("Exception while SWM Complaint Register ------------->" + ex);
			return false;
		}
		return false;
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
	public boolean callPotHoleAPI(CareRequest careRequest) {
    	try {
    		LOGGER.info(" Complaint With Pot Hole Started ---------------->" ); 
				RequestDTO applicantDetailDto = this.getApplicationDetails(careRequest);
				ResponseEntity<?> responseEntity = null;
				Map<String, String> requestParam = new HashMap<>();
				ResponseEntity<?> Response = null;
				String token = null;
				requestParam.put("authorizationKey",ApplicationSession.getInstance().getMessage("authorizationKey"));
				requestParam.put("password", ApplicationSession.getInstance().getMessage("password"));
				requestParam.put("username", ApplicationSession.getInstance().getMessage("username"));
				responseEntity = RestClient.callRestTemplateClient(requestParam,ApplicationSession.getInstance().getMessage("swm.complaint.api.tokenUrl"));
				LOGGER.info("Complaint Token Response ---------------->"+ responseEntity.getStatusCode() );
			   if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {
				Map<String, String> response = (Map<String, String>) responseEntity.getBody();
				if (response.containsKey("access_token")) {
					token = response.get("access_token");
					LOGGER.info(" Token Value ---------------->" + token);
				}
				List<CFCAttachment> docs = new ArrayList<>();
				docs = iChecklistVerificationService.findAttachmentsForAppId(careRequest.getApplicationId(), null,careRequest.getOrgId());
				PotHoleComplaintDTO dto = new PotHoleComplaintDTO();
				SWMComplaintFilesDTO files = new SWMComplaintFilesDTO();
				dto.setAdditionalDetails("citizen Event details");
				if (StringUtils.isNotEmpty(careRequest.getLatitude())) {
					dto.setLatitude(careRequest.getLatitude());
					LOGGER.info(
			                "Latitude when it is not empty : " + careRequest.getLatitude());
				} else {
					dto.setLatitude(String.valueOf(30.3176066));// hardcoded just for testing API functionality. This need to be removed
					LOGGER.info("In else part of Latitude: " + dto.getLatitude());
				}
				if (StringUtils.isNotEmpty(careRequest.getLongitude())) {
					dto.setLongitude(careRequest.getLongitude());
					LOGGER.info(
			                "Longitude when it is not empty : " + careRequest.getLongitude());
				} else {
					dto.setLongitude(String.valueOf(78.0291381));// hardcoded just for testing API functionality. This need to be removed
					LOGGER.info("In else part of Longitude: " + dto.getLongitude());
				}
				dto.setPhone(applicantDetailDto.getMobileNo());
				List<String> images = new ArrayList<>();
				if (!docs.isEmpty()) {
					docs.forEach(d -> {
						images.add(d.getAttPath());
					});
				}
				files.setImages(images);
				dto.setFiles(files);
				
				images.forEach(img->
				LOGGER.info("Image Byte Code for image: " + img.getBytes()));
				
				dto.setEventSubTypeId(careRequest.getComplaintType());
				dto.setComplaintId(careRequest.getComplaintId());
				dto.setNameContact(applicantDetailDto.getfName().concat(MainetConstants.WHITE_SPACE).concat(applicantDetailDto.getlName()));
				dto.setLocation(careRequest.getLocation().getLocNameEng());
				dto.setEmailId(applicantDetailDto.getEmail());
				dto.setTenantCode(MainetConstants.APP_NAME.DSCL);
				
				LOGGER.info("SWMComplaintDTO Printed dto.toString(): " + dto.toString());
				LOGGER.info("SWMComplaintFilesDTO Printed files.toString(): " + files.toString());
				
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "application/json");
				headers.add("Authorization", "Bearer " + token);
				Response = RestClient.callRestTemplateClientWithHeaders(dto,
						ApplicationSession.getInstance().getMessage("potHole.complaint.api.saveUrl"), HttpMethod.POST,String.class, headers);
				LOGGER.info("Complaint Save Response ---------------->" + Response.getStatusCode());
				LOGGER.info("Complaint Save Response ---------------->" + Response.getBody());
				if ((Response != null) && (Response.getStatusCode() == HttpStatus.OK)) {
					Map<String, Object> saveDesponse = null;
					ObjectMapper mapper = Utility.getMapper();
					saveDesponse = mapper.readValue((String) Response.getBody(), Map.class);
					if ((boolean)saveDesponse.containsKey("status")) {
						boolean status = (boolean) saveDesponse.get("status");
						LOGGER.info("Status ---------------->" + status);
						if (status) {
							return true;
						}
					}
				}
			}
			
		} catch (Exception ex) {
			LOGGER.info("Exception while Pot Hole Complaint Register ------------->" + ex);
			return false;
		}
		return false;
    }
    
    
    @Override
	public String callSwacchBharatAPI(CareRequest careRequest) {
			
		
		LOGGER.info(" Complaint With callSwacchBharatAPI Started ---------------->" );
		
		RequestDTO applicantDetailDto = this.getApplicationDetails(careRequest);
		
		String complaintUrl = ApplicationSession.getInstance().getMessage("swacchaBharatApp.Url");
		String vendor_name = ApplicationSession.getInstance().getMessage("swacchaBharatApp.vendorName");
		String access_key = ApplicationSession.getInstance().getMessage("swacchaBharatApp.accessKey");
		String deviceOs = ApplicationSession.getInstance().getMessage("swacchaBharatApp.deviceOS");
		
		ComplaintType complaint = iComplaintTypeService.findComplaintTypeById(careRequest.getComplaintType());
		String categoryId = MainetConstants.ComplaintType.getComplaintId(complaint.getComplaintDesc());
		
		String mobileNumber = applicantDetailDto.getMobileNo();
		
		String complaintLatitude= careRequest.getLatitude(); // "37.4226711";
		String complaintLongitude= careRequest.getLongitude(); // "-122.0849872";
		
		String complaintLocation=  applicantDetailDto.getAreaName();
		String complaintLandmark = careRequest.getLandmark();
		String fullName = applicantDetailDto.getfName() + MainetConstants.WHITE_SPACE + applicantDetailDto.getlName();
		
		String userLatitude = careRequest.getLatitude(); // "37.4226711";
		String userLongitude = careRequest.getLongitude(); // "-122.0849872";
		
		String userLocation = careRequest.getLocation().getLocNameEng();
	
		/*String file = "https://cdn.pixabay.com/photo/2018/01/14/23/12/nature-3082832_960_720.jpg";*/
		RestTemplate webLineReq = new RestTemplate();
		HttpHeaders webLineHeaders = new HttpHeaders();
		
		webLineHeaders.add("Content-Type", "application/json");
		HttpEntity<String> webLinbeHttpentity = new HttpEntity<>(webLineHeaders);
		
		String webMailUri = complaintUrl + "?vendor_name=" + vendor_name + "&access_key=" + access_key + "&mobileNumber="
				+ mobileNumber + "&categoryId=" + categoryId + "&complaintLatitude=" + complaintLatitude + "&complaintLongitude="
				+ complaintLongitude + "&complaintLocation=" + complaintLocation + "&complaintLandmark=" + complaintLandmark +
				"&fullName=" + fullName + "&userLatitude=" + userLatitude + "&userLongitude=" + userLongitude +
				"&userLocation=" + userLocation  + "&macAddress=&deviceToken=&deviceOs=" + deviceOs + "&file=";
		
		
		LOGGER.info("webMailUri :- " + webMailUri);
		
		String swacchaComplaintNo="";
		try {
			ResponseEntity<String> resp = webLineReq.exchange(webMailUri, HttpMethod.POST, webLinbeHttpentity,
					String.class);
			
			HttpStatus statusCode  =resp.getStatusCode();
			if(statusCode== HttpStatus.CREATED)
			{
				
				LOGGER.info("Complaint posted sucessfully on SwacchaBharat App");
				LOGGER.info("response for swaccha API :- " + resp.toString());

				ObjectMapper mapper = Utility.getMapper();
				JsonNode node = mapper.readValue((String) resp.getBody(), JsonNode.class);
				
				JsonNode node1 = node.get("complaint").get("generic_id");	
				LOGGER.info("complaint ---------------->" + node1.toString());
				
				
				if(StringUtils.isNotEmpty(node1.toString())){
					String complaintId = node1.toString();
					swacchaComplaintNo = complaintId.substring(1,complaintId.length()-1);
					
					LOGGER.info("Swacchata complaintId ---------------->" + swacchaComplaintNo);	
					return swacchaComplaintNo;
				}
			}
			
			
		} catch (Exception e) {
			LOGGER.error("Exception occured while calling SwacchaBharatAPI " + e);
		}
		
		
		return swacchaComplaintNo;
	}
    
}
//