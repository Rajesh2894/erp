package com.abm.mainet.common.workflow.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.jws.WebService;
import javax.ws.rs.Path;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.SystemModuleFunction;
import com.abm.mainet.common.entitlement.service.IEntitlementService;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dao.IWorkflowTypeDAO;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.TaskSearchRequest;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.dto.WorkflowAction;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.repository.IWorkflowRepository;
import com.abm.mainet.common.workflow.repository.WorkFlowTypeRepository;

import io.swagger.annotations.Api;

@Service
@WebService(endpointInterface = "com.abm.mainet.common.workflow.service.IWorkflowActionService")
@Api("Workflow Action Service")
@Path("/workflow/action")
public class WorkflowActionService implements IWorkflowActionService {

	private static final Logger LOGGER = Logger.getLogger(WorkflowActionService.class);
	
    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private WorkFlowTypeRepository workFlowTypeRepository;

    @Autowired
    private ServiceMasterService serviceMasterService;

    @Autowired
    private IWorkflowExecutionService workflowExecutionService;
    @Autowired
    private IWorkflowRepository workflowRepository;

    @Autowired
    private ApplicationSession applicationSession;

    @Autowired
    private IEntitlementService entitlementService;

    @Autowired
    ICFCApplicationMasterService cfcApplicationMasterService;

    @Autowired
    private IWorkflowTypeDAO workflowTypeDAO;

    /* Get workflowAction data using application_id value */
    @Override
    public List<WorkflowTaskActionWithDocs> getWorkflowActionLogByApplicationId(final String applicationId, final short lang) {
        TaskSearchRequest tr = new TaskSearchRequest();
        tr.setApplicationId(Long.parseLong(applicationId));
        tr.setLangId(lang);
        return prepareBmpData(tr, ServiceEndpoints.WorkflowExecutionURLS.WORKFLOW_ACTION);
    }

    /* Get workflowAction data using reference_id value */
    @Override
    public List<WorkflowTaskActionWithDocs> getWorkflowActionLogByReferenceId(final String referenceId, final short lang) {
        TaskSearchRequest tr = new TaskSearchRequest();
        tr.setReferenceId(referenceId);
        tr.setLangId(lang);
        return prepareBmpData(tr, ServiceEndpoints.WorkflowExecutionURLS.WORKFLOW_ACTION);
    }

    @Override
    public List<WorkflowTaskActionWithDocs> getActionLogByUuidAndWorkflowId(final String uuid, final Long workflowReqId,
            final short lang) {
        TaskSearchRequest tr = new TaskSearchRequest();
        if (!uuid.matches(MainetConstants.NUMERIC_PATTERN))
            tr.setReferenceId(uuid);
        else
            tr.setApplicationId(Long.parseLong(uuid));
        tr.setWorkFlowRequestId(workflowReqId);
        tr.setLangId(lang);
        return prepareBmpData(tr, ServiceEndpoints.WorkflowExecutionURLS.WORKFLOW_ACTION);
    }

    @Override
    public List<WorkflowTaskActionWithDocs> getWorkflowPendingActionByUuid(String uuid, short lang) {
        TaskSearchRequest tr = new TaskSearchRequest();
        tr.setReferenceId(uuid);
        tr.setLangId(lang);
        return prepareBmpData(tr, ServiceEndpoints.WorkflowExecutionURLS.WORKFLOW_ACTION_PENDING);
    }

    @Override
    public Map<String, String> getEmpByworkFlowId(final Long workFlowId, final Long orgId) {
        List<Object[]> empList = null;
        Map<String, String> map = new HashMap<>();
        WorkflowMas workflowMas = workFlowTypeRepository.findOne(workFlowId);
        empList = iEmployeeService.findActiveEmployeeByDeptId(workflowMas.getDepartment().getDpDeptid(), orgId);
        for (Object[] emp : empList) {
            map.put(emp[0].toString(), emp[1].toString() + MainetConstants.WHITE_SPACE + emp[3].toString());
        }
        return map;
    }

    @Override
    public Map<String, String> getEmpsByEvent(final Set<LookUp> lookList, final String serEventId, Long orgId) {
        List<Long> empIdList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        lookList.parallelStream().filter(lookup -> lookup.getLookUpCode().equals(serEventId)).forEach(lookup -> {
            if (lookup.getOtherField() != null) {
                String[] empIds = lookup.getOtherField().split(MainetConstants.operator.COMMA);
                for (String s : empIds) {
                    empIdList.add(Long.valueOf(s));
                }
            }
        });
        if (!empIdList.isEmpty()) {
            List<Employee> empList = iEmployeeService.getEmpDetailListByEmpIdList(empIdList, orgId);
            empList.forEach(emp -> {
                map.put(emp.getEmpId().toString(), emp.getEmpname() + MainetConstants.WHITE_SPACE + emp.getEmplname());
            });
        }
        return map;
    }

    @Override
    public void updateWorkFlow(WorkflowTaskAction workFlowActionDto, Employee emp, Long orgId, Long serviceId) {

        String processName = serviceMasterService.getProcessName(serviceId,
                orgId);
        if (processName != null) {
            WorkflowProcessParameter workflowdto = new WorkflowProcessParameter();
            workflowdto.setProcessName(processName);
            try {
                workFlowActionDto.setDateOfAction(new Date());
                workFlowActionDto.setOrgId(orgId);
                workFlowActionDto.setEmpType(emp.getEmplType());
                workFlowActionDto.setEmpName(emp.getEmpname());
                workFlowActionDto.setEmpId(emp.getEmpId());
                workFlowActionDto.setCreatedBy(emp.getEmpId());
                workFlowActionDto.setCreatedDate(new Date());
                if (workFlowActionDto.getIsFinalApproval() == null)
                    workFlowActionDto.setIsFinalApproval(false);
                if (workFlowActionDto.getIsObjectionAppealApplicable() == null)
                    workFlowActionDto.setIsObjectionAppealApplicable(false);
                workflowdto.setWorkflowTaskAction(workFlowActionDto);
                LOGGER.info("Calling BPm method");
                workflowExecutionService.updateWorkflow(workflowdto);
                LOGGER.info("Ended calling BPm method");
            } catch (Exception e) {
                throw new FrameworkException("Exception while calling jbpm workflow :" + e);
            }
        }
    }

    @Override
    public void signalWorkFlow(WorkflowTaskAction workFlowActionDto, Employee emp, Long orgId, Long serviceId,
            String signalName) {
        String processName = serviceMasterService.getProcessName(serviceId,
                orgId);
        if (processName != null) {
            WorkflowProcessParameter workflowdto = new WorkflowProcessParameter();
            workflowdto.setProcessName(processName);
            try {
                workFlowActionDto.setDateOfAction(new Date());
                workFlowActionDto.setOrgId(orgId);
                workFlowActionDto.setEmpType(emp.getEmplType());
                workFlowActionDto.setEmpName(emp.getEmpname());
                workFlowActionDto.setEmpId(emp.getEmpId());
                workFlowActionDto.setCreatedBy(emp.getEmpId());
                workFlowActionDto.setCreatedDate(new Date());
                if (workFlowActionDto.getIsFinalApproval() == null)
                    workFlowActionDto.setIsFinalApproval(false);
                if (workFlowActionDto.getIsObjectionAppealApplicable() == null)
                    workFlowActionDto.setIsObjectionAppealApplicable(false);
                workflowdto.setWorkflowTaskAction(workFlowActionDto);
                workflowdto.setSignalName(signalName);
                workflowExecutionService.signalWorkFlow(workflowdto);
            } catch (Exception e) {
                throw new FrameworkException("Exception while calling jbpm workflow :" + e);
            }
        }
    }

    private List<WorkflowTaskActionWithDocs> prepareBmpData(TaskSearchRequest tr, String url) {
        List<WorkflowTaskActionWithDocs> workflowTask = new ArrayList<>();
        List<WorkflowTaskActionWithDocs> workflowTask1 = new ArrayList<>();
        ResponseEntity<?> responseEntity = null;
        List<LookUp> decisionLookUps = null;
        CFCApplicationAddressEntity cfcContactDetails = null;
        DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
        dd.setParsePath(true);
        try {
            responseEntity = RestClient.callRestTemplateClient(tr, url);
            HttpStatus statusCode = responseEntity.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                workflowTask = (List<WorkflowTaskActionWithDocs>) castRequestToDataModels(responseEntity,
                        WorkflowTaskActionWithDocs.class);

            }
            if (CollectionUtils.isNotEmpty(workflowTask)) {
                Organisation organisation = new Organisation();
                organisation.setOrgid(workflowTask.get(0).getOrgId());
                decisionLookUps = CommonMasterUtility
                        .getLookUps(PrefixConstants.ComplaintPrefix.COMPLAINT_DECISION_PREFIX, organisation);
                }
            for (WorkflowTaskActionWithDocs workflowTaskAction : workflowTask) {
                Employee employee = iEmployeeService.findEmployeeByIdAndOrgId(workflowTaskAction.getEmpId(),
                        workflowTaskAction.getOrgId());
                if (null != employee) {
                    String EmployeName = employee.getEmpname() + MainetConstants.WHITE_SPACE;
                    Organisation org = new Organisation();
                	org.setOrgid(workflowTaskAction.getOrgId());
                    if (employee.getEmplname() !=null)
                    EmployeName += employee.getEmplname();
                    workflowTaskAction.setEmpName(EmployeName);
                    workflowTaskAction.setEmpEmail(employee.getEmpemail());
                    if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TCP) 
                    		&& workflowTaskAction.getTaskName().equalsIgnoreCase(MainetConstants.START)) {
						workflowTaskAction.setEmpGroupDescEng(MainetConstants.BLANK);
					}else{
						workflowTaskAction.setEmpGroupDescEng(employee.getDesignation().getDsgname());
					}
                    workflowTaskAction.setEmpGroupDescReg(employee.getDesignation().getDsgnameReg());
                    workflowTaskAction.setEmpMobile(employee.getEmpmobno());
                    
                    //#165556 For FORWARD_TO_EMPLOYEE Decision setting Department of the Submitted user
                    if(workflowTaskAction.getTaskName().equalsIgnoreCase(MainetConstants.START) ||
                    		workflowTaskAction.getDecision().equalsIgnoreCase(MainetConstants.WorkFlow.Decision.FORWARD_TO_EMPLOYEE)){
                    	//workflowTaskAction.setDeptName(MainetConstants.DEPT_SHORT_NAME.CFC);
                    	
						if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TCP)) {
							workflowTaskAction.setDeptName(MainetConstants.BLANK);
						} else {
							// #157754 As asked by Samadhan Sir to display dept
							// of Submitted USer
							if (UserSession.getCurrent().getLanguageId() == 1) {
								workflowTaskAction.setDeptName(employee.getTbDepartment().getDpDeptdesc());
							} else {
								workflowTaskAction.setDeptName(employee.getTbDepartment().getDpNameMar());
							}
						}
					}
                    
                } else {
                     if(workflowTaskAction.getTaskName().equalsIgnoreCase(MainetConstants.START) ||
                    		 workflowTaskAction.getTaskName().equalsIgnoreCase(MainetConstants.Hidden_Task_Requester)){
                     	workflowTaskAction.setDeptName(
                                ApplicationSession.getInstance().getMessage(MainetConstants.WorkFlow.Designation.CITIZEN));
                     }
                    workflowTaskAction.setEmpName(
                            ApplicationSession.getInstance().getMessage(MainetConstants.WorkFlow.Designation.CITIZEN));
                    /*
                     * if employee is null that means application must be saved from portal or mobile. So setting mobile number of
                     * applicant for contact detail
                     */
                    if (workflowTaskAction.getApplicationId() != null) {
                        cfcContactDetails = cfcApplicationMasterService
                                .getApplicantsDetails(workflowTaskAction.getApplicationId());
                    }
                    if (cfcContactDetails != null && StringUtils.isNotBlank(cfcContactDetails.getApaMobilno())) {
                        workflowTaskAction.setEmpMobile(cfcContactDetails.getApaMobilno());
                    }
                }

                /* Adding descion based on LangId */
                String decisionCode = workflowTaskAction.getDecision();
                /* Adding decision for eng lang */
                workflowTaskAction.setTaskDecision(workflowTaskAction.getDecision());
                Optional<LookUp> decisionLookUp = decisionLookUps.stream()
                        .filter(l -> l.getLookUpCode().equals(decisionCode)).findFirst();
                if (decisionLookUp.isPresent()
                        && (workflowTaskAction.getTaskName().toLowerCase().contains(MainetConstants.TaskName.COMPLAINT)
                                || workflowTaskAction.getTaskName().toLowerCase().contains(MainetConstants.TaskName.CALL)
                                || workflowTaskAction.getTaskName().toLowerCase().contains(MainetConstants.TaskName.GRIEVANCE))) {
                    LookUp lookUp = decisionLookUp.get();
                    if (tr.getLangId() == MainetConstants.ENGLISH)
                        workflowTaskAction.setDecision(lookUp.getDescLangFirst());
                    else
                        workflowTaskAction.setDecision(lookUp.getDescLangSecond());
                } else {
                    String code = MainetConstants.WorkFlow.Remarks.DECISION_ROOT + decisionCode.toLowerCase();
                    if (tr.getLangId() == MainetConstants.ENGLISH)
                        workflowTaskAction.setDecision(
                                applicationSession.getMessage(code, code, new Locale(MainetConstants.REG_ENG.ENGLISH)));
                    else
                        workflowTaskAction.setDecision(applicationSession.getMessage(code, code,
                                new Locale(MainetConstants.REG_ENG.REGIONAL)));
                }

                /* Adding task_name based on langId */
                if (workflowTaskAction.getEventId() != null && workflowTaskAction.getEventId() > 0) {
                    SystemModuleFunction systemModuleFunction = entitlementService
                            .findById(workflowTaskAction.getEventId());
                    if (systemModuleFunction != null) {
                        if (tr.getLangId() == MainetConstants.ENGLISH)
                            workflowTaskAction.setTaskName(systemModuleFunction.getSmfname());
                        else
                            workflowTaskAction.setTaskName(systemModuleFunction.getSmfname_mar());
                    }
                } else {

                    String eventName = MainetConstants.WorkFlow.EventLabels.EVENT_ROOT
                            + workflowTaskAction.getTaskName().replaceAll("\\s", "").replace("_", "").toLowerCase();
                    if (tr.getLangId() == MainetConstants.ENGLISH)
                        workflowTaskAction.setTaskName(
                                applicationSession.getMessage(eventName, eventName, new Locale(MainetConstants.REG_ENG.ENGLISH)));
                    else
                        workflowTaskAction.setTaskName(applicationSession.getMessage(eventName, eventName,
                                new Locale(MainetConstants.REG_ENG.REGIONAL)));
                }
                workflowTask1.add(workflowTaskAction);
            }
        } catch (Exception ex) {
            throw new FrameworkException(
                    "Exception occured while calling method workflow request :" + tr, ex);
        }
        return workflowTask1;
    }

    @SuppressWarnings("unchecked")
    private List<WorkflowTaskActionWithDocs> castRequestToDataModels(ResponseEntity<?> responseEntity, Class<?> clazz) {
        LinkedHashMap<Long, Object> requestMap = null;
        List<WorkflowTaskActionWithDocs> dataModelList = new ArrayList<>();
        WorkflowTaskActionWithDocs dto = null;
        try {
            List<?> list = (List<?>) responseEntity.getBody();
            for (Object object : list) {
                requestMap = (LinkedHashMap<Long, Object>) object;
                String jsonString = new JSONObject(requestMap).toString();
                dto = (WorkflowTaskActionWithDocs) new ObjectMapper().readValue(jsonString,
                        clazz);
                dataModelList.add(dto);
            }
        } catch (IOException e) {
            throw new FrameworkException(
                    "Exception occured while calling method workflow request :" + responseEntity, e);
        }
        return dataModelList;
    }

    @Override
    public List<WorkflowTaskActionWithDocs> getActionLogByAppIdOrRefIdAndWorkflowId(String applicationId, String referenceId,
            Long workflowReqId, short lang) {
        TaskSearchRequest tr = new TaskSearchRequest();
        if (!applicationId.equals(MainetConstants.NULL)) {
            tr.setApplicationId(Long.parseLong(applicationId));
        } else {
            tr.setApplicationId(null);
        }
        if (!referenceId.equals(MainetConstants.NULL)) {
            tr.setReferenceId(referenceId);
        }

        tr.setWorkFlowRequestId(workflowReqId);
        tr.setLangId(lang);
        return prepareBmpData(tr, ServiceEndpoints.WorkflowExecutionURLS.WORKFLOW_ACTION);
    }

    @Override
    public List<WorkflowTaskActionWithDocs> getWorkflowActionLogByApplicationIdandLang(final String uuid, final short lang) {
        TaskSearchRequest tr = new TaskSearchRequest();
        /* getting only application Id from method param so only application Id is setting to get action History */
        if (!uuid.equals(MainetConstants.NULL)) {
            tr.setApplicationId(Long.parseLong(uuid));
        }
        tr.setLangId(lang);
        return prepareBmpData(tr, ServiceEndpoints.WorkflowExecutionURLS.WORKFLOW_ACTION);
    }

    @Override
    public List<String> getWorkflowActionCommentsByAppIdAndEmpId(Long applicationId, Long empId, Long orgId) {
        return workflowRepository.findWorkflowActionCommentsByApplicationIdAndEmpId(applicationId, empId, orgId);
    }

    @Override
    public List<UserTaskDTO> findComplaintTaskForEmployee(TaskSearchRequest taskSearchRequest) {
        List<Object[]> complaintTask = workflowTypeDAO.findComplaintTaskForEmployee(taskSearchRequest);
        List<UserTaskDTO> complaintTaskList = new ArrayList<>();
        // getting pending task list using query instead of VW_Workflowtask_detail

        complaintTaskList = complaintTask.stream().map(objArray -> {
            UserTaskDTO dto = new UserTaskDTO();
            // BeanUtils.copyProperties(entity, dto);
            if (objArray[0] != null)
                dto.setApplicationId(((BigInteger) objArray[0]).longValue());
            dto.setOrgId(((BigInteger) objArray[1]).longValue());
            if (objArray[2] != null)
                dto.setDeptId(((BigInteger) objArray[2]).longValue());
            dto.setDeptName((String) objArray[3]);
            if (objArray[5] != null)
                dto.setServiceId(((BigInteger) objArray[5]).longValue());
            dto.setServiceName((String) objArray[6]);
            if (objArray[8] != null)
                dto.setServiceEventId(((BigInteger) objArray[8]).longValue());
            dto.setServiceEventName((String) objArray[9]);
            if (objArray[11] != null)
                dto.setTaskId(((BigInteger) objArray[11]).longValue());
            dto.setTaskName((String) objArray[12]);
            dto.setServiceEventUrl((String) objArray[13]);
            dto.setRequestDate(Utility.dateToString((Date) objArray[14], MainetConstants.DATE_HOUR_FORMAT));
            if (objArray[15] != null)
                dto.setWorkflowReqId(((BigInteger) objArray[15]).longValue());
            dto.setTaskStatus((String) objArray[16]);
            dto.setLastDecision((String) objArray[17]);
            if (objArray[19] != null)
                dto.setReferenceId((String) objArray[19]);
            if (objArray[20] != null)
                dto.setWorkflowId(((BigInteger) objArray[20]).longValue());
            if (objArray[21] != null)
                dto.setTaskSlaDurationInMS(((BigInteger) objArray[21]).longValue());
            if (taskSearchRequest.getLangId() == MainetConstants.REGIONAL_LANGUAGE_ID) {
                dto.setDeptName((String) objArray[4]);
                dto.setServiceName((String) objArray[7]);
                dto.setServiceEventName((String) objArray[10]);
            }
            if (objArray[22] != null) {
                dto.setDateOfAssignment((java.sql.Timestamp) objArray[22]);
            }
            if (objArray[23] != null) {
                dto.setDateOfCompletion((java.sql.Timestamp) objArray[23]);
            }
            dto.setMobileNo((objArray[24]!=null) ? objArray[24].toString() : MainetConstants.BLANK);
            
            dto.setComments((objArray[25]!=null) ? objArray[25].toString() : MainetConstants.BLANK);
            
            dto.setAttPath((objArray[26]!=null) ? objArray[26].toString() : MainetConstants.BLANK);
            
            // D#126512 column task status display in Regional
            dto.setTaskStatusDesc(dto.getTaskStatus().equalsIgnoreCase(MainetConstants.WorkFlow.Status.PENDING)
                    ? ApplicationSession.getInstance().getMessage("dashboard.status.pendingStatus")
                    : ApplicationSession.getInstance().getMessage("dashboard.status.completedStatus"));
            // taskList.add(task);
            return dto;
        }).collect(Collectors.toList());

        return complaintTaskList;
    }

    //#141902->to fetch comment  and decision  of approval 
    @Override
     public WorkflowAction getCommentAndDecisionByAppId(Long applicationId, Long orgId) {
    	 List<Object[]> entityList = workflowRepository.findWorkflowActionCommentAndDecisionByAppId(applicationId, orgId);
    	 WorkflowAction dto = new WorkflowAction();
    	if(CollectionUtils.isNotEmpty(entityList)) {
    	 entityList.forEach(list ->{
    		 if (StringUtils.isNotEmpty((String) list[0]))
    		 dto.setComments((String) list[0]);
    		 if (StringUtils.isNotEmpty((String) list[1]))
    		 dto.setDecision((String) list[1]);
    	 });
    	}
    	 return dto;
     }
    
    
    @Override
    public WorkflowRequest findWorkflowActionCommentAndDecisionByAppId(Long applicationId, Long orgId) {
   	 List<Object[]> entityList = workflowRepository.findWorkflowRequestCommentAndDecisionByAppId(applicationId, orgId);
   	WorkflowRequest dto = new WorkflowRequest();
   	if(CollectionUtils.isNotEmpty(entityList)) {
   	 entityList.forEach(list ->{
   		 if (StringUtils.isNotEmpty((String) list[0]))
   		 dto.setLastDecision((String) list[0]);
   		 if (StringUtils.isNotEmpty((String) list[1]))
   		 dto.setStatus((String) list[1]);
   	 });
   	}
   	 return dto;
    }
    
    @Override
    public WorkflowRequest findWorkflowActionRequest(Long applicationId, Long orgId) {
   	 List<Object[]> entityList = workflowRepository.findWorkflowActionRequest(applicationId, orgId);
   	WorkflowRequest dto = new WorkflowRequest();
   	if(CollectionUtils.isNotEmpty(entityList)) {
   	 entityList.forEach(list ->{
   		 if (StringUtils.isNotEmpty((String) list[0]))
   		 dto.setLastDecision((String) list[0]);
   		 if (StringUtils.isNotEmpty((String) list[1]))
   		 dto.setStatus((String) list[1]);
   	 });
   	}
   	 return dto;
    }
    
}
