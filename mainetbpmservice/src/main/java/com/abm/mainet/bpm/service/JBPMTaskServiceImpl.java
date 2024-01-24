package com.abm.mainet.bpm.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.OrganizationalEntity;
import org.kie.api.task.model.Task;
import org.kie.api.task.model.TaskSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.bpm.common.dto.ApplicationMetadata;
import com.abm.mainet.bpm.common.dto.TaskAssignment;
import com.abm.mainet.bpm.common.dto.WorkflowProcessParameter;
import com.abm.mainet.bpm.common.dto.WorkflowTask;
import com.abm.mainet.bpm.common.dto.WorkflowTaskActionResponse;
import com.abm.mainet.bpm.domain.BpmDeployment;
import com.abm.mainet.bpm.domain.WorkflowRequest;
import com.abm.mainet.bpm.domain.WorkflowUserTask;
import com.abm.mainet.bpm.repository.BpmDeploymentRepository;
import com.abm.mainet.bpm.repository.WorkflowRequestRepository;
import com.abm.mainet.bpm.repository.WorkflowTaskRepository;
import com.abm.mainet.bpm.utility.ConversionUtility;
import com.abm.mainet.bpm.utility.RunTimeManager;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.exception.MapFrameworkException;
import com.abm.mainet.common.exception.WorkflowFrameworkException;
import com.abm.mainet.config.ApplicationProperties;
import com.abm.mainet.constant.MainetConstants;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JBPM service provider.
 * 
 * This class use jBPM remote runtime library to communicate with jBPM runtime instance. This class provides implementation for
 * jBPM BPM.
 * 
 * @author Jasvinder.Bhomra
 * @author sanket.joshi
 * 
 * @see IBpmService
 */
@Service(MainetConstants.WorkFlow.ImplementationService.JBPM)
public class JBPMTaskServiceImpl implements IBpmService {

    private static final Logger LOGGER = Logger.getLogger(JBPMTaskServiceImpl.class);

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private BpmDeploymentRepository bpmDeploymentRepository;

    @Autowired
    private IWorkflowRequestService workflowService;

    @Autowired
    private WorkflowRequestRepository workflowRequestRepository;

    @Autowired
	private WorkflowTaskRepository workflowTaskRepository;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public WorkflowTaskActionResponse initiateProcess(WorkflowProcessParameter parameter)
            throws WorkflowFrameworkException {
    	
    	 //Increase logging, Exception Handling & Code Change Done by Rajesh Sir for this method for Workflow issues

        RuntimeEngine engine = null;
        KieSession ksession = null;
        BpmDeployment bpmDeployment = null;
        WorkflowRequest workflowRequest = null;
        Long processInstanceId = null;

        try {
            // Validating request for UUID
            if (workflowService.isWorkflowExist(parameter))
                throw new FrameworkException("Workflow already exist for requested uuId and orgId");
            
           

            // Identifying BPM deployment version
            LOGGER.error("Attempting to start new process for type - " + parameter.getProcessName());
            bpmDeployment = bpmDeploymentRepository.findByArtifactIdAndBpmRuntime(parameter.getProcessName(),
                    MainetConstants.WorkFlow.ImplementationService.JBPM);
            
            // validating Workflow Id
            if (parameter.getApplicationMetadata().getWorkflowId() == null) {
            	throw new FrameworkException("Workflow ID Should not be Null");
            } 
            
            if (bpmDeployment == null)
                throw new FrameworkException(
                        "Unable to identify the KIE Deployment for artifact id | " + parameter.getProcessName());

            // Initiate process
            engine = RunTimeManager.getRuntimeEngineInstance(applicationProperties, bpmDeployment);
            ksession = engine.getKieSession();
            Map<String, Object> parameterMap = ConversionUtility.workflowProcessParameterToMap(parameter);
            LOGGER.error("Initiating new process with process id " + bpmDeployment.getProcessId() +" "+ parameterMap);
            ProcessInstance processInstance = ksession.startProcess(bpmDeployment.getProcessId(), parameterMap);
            processInstanceId = processInstance.getId();
            LOGGER.error("New process instantiated with instance id " + processInstanceId+" "+processInstance.toString());

            // Save Workflow transactional data
            parameter.getApplicationMetadata().setProcessInstanceId(processInstanceId);
            workflowRequest = workflowService.saveWorkflowRequest(parameter, bpmDeployment);

        } catch (Throwable t) {
            if (processInstanceId != null && ksession != null) {
                ksession.abortProcessInstance(processInstanceId);
                LOGGER.warn("Process aborted successfully for processInstanceId " + processInstanceId);
            }
            LOGGER.error("Unable to initiate process for deployment " + bpmDeployment
                    + " with error " + t.getMessage()
                    + " with cause " + t.getCause());
            throw new WorkflowFrameworkException("Unable to initiate process for deployment " + bpmDeployment, t);
        }
        return getActionSuccessResponse(parameter, workflowRequest);
    }

    @Override
    public WorkflowTaskActionResponse signalProcess(WorkflowProcessParameter parameter) throws WorkflowFrameworkException {

    	
    	//Increase logging, Exception Handling & Code Change Done by Rajesh Sir for this method for Workflow issues
    	
        if (parameter.getSignalName() == null || parameter.getWorkflowTaskAction() == null
                || (parameter.getWorkflowTaskAction().getApplicationId() == null
                        && parameter.getWorkflowTaskAction().getReferenceId() == null))
            return null;

        RuntimeEngine engine = null;
        KieSession ksession = null;
        BpmDeployment bpmDeployment = null;
        WorkflowRequest workflowRequest = null;
        Long processInstanceId = null;
        Long wrBpmDeploymentId = null;
        ApplicationMetadata applicationMetadata = null;
        
        try {
        	
        	if("RTI".equals(parameter.getWorkflowTaskAction().getDeptName()))
        	{
            workflowRequest = workflowRequestRepository
                    .findByApplicationIdOrReferenceIdAndOrgIdRTI(parameter.getWorkflowTaskAction().getApplicationId(),
                    		parameter.getWorkflowTaskAction().getComments(),
                            parameter.getWorkflowTaskAction().getOrgId());
        	}
        	
        	else{
        		workflowRequest = workflowRequestRepository
                        .findByApplicationIdOrReferenceIdAndOrgId(parameter.getWorkflowTaskAction().getApplicationId(),
                        		parameter.getWorkflowTaskAction().getReferenceId(),
                                parameter.getWorkflowTaskAction().getOrgId());
        	}
        	
        	
            wrBpmDeploymentId = workflowRequest.getDeploymentId();
           
            bpmDeployment = bpmDeploymentRepository.findById(wrBpmDeploymentId);
            
            /*
             * bpmDeployment = bpmDeploymentRepository.findByArtifactIdAndBpmRuntime(parameter.getProcessName(),
             * MainetConstants.WorkFlow.ImplementationService.JBPM);
             */
            if (bpmDeployment == null)
                throw new FrameworkException(
                        "Unable to identify the KIE Deployment for artifact id | " + parameter.getProcessName());

            engine = RunTimeManager.getRuntimeEngineInstance(applicationProperties, bpmDeployment);
            ksession = engine.getKieSession();
            
            if("RTI".equals(parameter.getWorkflowTaskAction().getDeptName()) )
        	{
            workflowRequest = workflowRequestRepository
                    .findByApplicationIdOrReferenceIdAndOrgIdRTI(parameter.getWorkflowTaskAction().getApplicationId(),
                            parameter.getWorkflowTaskAction().getComments(),
                            parameter.getWorkflowTaskAction().getOrgId());
        	}
            
            else
            {
            	workflowRequest = workflowRequestRepository
                        .findByApplicationIdOrReferenceIdAndOrgId(parameter.getWorkflowTaskAction().getApplicationId(),
                                parameter.getWorkflowTaskAction().getReferenceId(),
                                parameter.getWorkflowTaskAction().getOrgId());
            }
            processInstanceId = workflowRequest.getProcessSessionId();
            LOGGER.error("Attempting to trigger signal " + parameter.getSignalName() + " for process tnstance id "
                    + processInstanceId);
            ksession.signalEvent(parameter.getSignalName(), parameter.getWorkflowTaskAction(), processInstanceId);
            LOGGER.error("Signal triggered successfully");

            // Save Workflow transactional data
            applicationMetadata = new ApplicationMetadata();
            applicationMetadata.setOrgId(workflowRequest.getOrgId());
            applicationMetadata.setProcessInstanceId(processInstanceId);
            applicationMetadata.setIsProcessAlive(isProcessAlive(ksession, processInstanceId));
            applicationMetadata.setWorkflowReqId(workflowRequest.getId());// to identify unique work flow request
            parameter.setApplicationMetadata(applicationMetadata);
            workflowRequest = workflowService.updateWorkflowRequest(parameter);
        } catch (Throwable t) {
            LOGGER.error("Unable to signal process for processInstanceId " + processInstanceId
                    + " with error " + t.getMessage()
                    + " with cause " + t.getCause());
            throw new WorkflowFrameworkException("Unable to signal process for processInstanceId " + processInstanceId, t);
        }
        return getActionSuccessResponse(parameter, workflowRequest);
    }

    @Override
    public WorkflowTaskActionResponse completeTasks(WorkflowProcessParameter parameter) throws FrameworkException {

    	//Increase logging, Exception Handling & Code Change Done by Rajesh Sir for this method for Workflow issues
    	
        RuntimeEngine engine = null;
        KieSession ksession = null;
        BpmDeployment bpmDeployment = null;
        WorkflowRequest workflowRequest = null;
        TaskService taskService = null;
        Long wrBpmDeploymentId = null;
        TaskAssignment taskAssignment = null;
        ApplicationMetadata metadata = null;
        Long wfId=null;
    	WorkflowUserTask wfTask= null;
        
        try {

            /*
             * bpmDeployment = bpmDeploymentRepository.findByArtifactIdAndBpmRuntime(parameter.getProcessName(),
             * MainetConstants.WorkFlow.ImplementationService.JBPM);
             */
        	
        	//Added to get Workflow Id
        	if(parameter.getWorkflowTaskAction().getReferenceId()!=null && parameter.getWorkflowTaskAction().getApplicationId()!=null)
        	{
        		wfTask=workflowTaskRepository.findByReferenceIdAndTaskId(parameter.getWorkflowTaskAction().getReferenceId(), parameter.getWorkflowTaskAction().getTaskId());
            	wfId= wfTask.getWorkflowId();
        	}else if(parameter.getWorkflowTaskAction().getReferenceId()!=null && parameter.getWorkflowTaskAction().getApplicationId()==null)
        	{
        		wfTask=workflowTaskRepository.findByReferenceIdAndTaskId(parameter.getWorkflowTaskAction().getReferenceId(), parameter.getWorkflowTaskAction().getTaskId());
            	wfId= wfTask.getWorkflowId();
        	}      	
        	else{       
            		wfTask=workflowTaskRepository.findByApplicationIdAndTaskId(parameter.getWorkflowTaskAction().getApplicationId(), parameter.getWorkflowTaskAction().getTaskId());
                	wfId= wfTask.getWorkflowId();
            	}	   	
        
        	if(wfId!= null)
        	{
        		//Suggested by Rajesh Sir
        		workflowRequest = workflowRequestRepository
                        .findByApplicationIdOrReferenceIdAndWorkflowId(parameter.getWorkflowTaskAction().getApplicationId(),
                                parameter.getWorkflowTaskAction().getReferenceId(), wfId);
        		wrBpmDeploymentId= workflowRequest.getDeploymentId();
        	}
        	else {
            wrBpmDeploymentId = workflowRequestRepository.findDeploymentIdByApplicationIdOrReferenceIdAndOrgId(
            		parameter.getWorkflowTaskAction().getApplicationId(), parameter.getWorkflowTaskAction().getReferenceId());
        	}
            LOGGER.error("wrBpmDeploymentId is " + wrBpmDeploymentId);
            bpmDeployment = bpmDeploymentRepository.findById(wrBpmDeploymentId);
            LOGGER.error("bpmDeployment is " + bpmDeployment);
            if (bpmDeployment == null)
                throw new FrameworkException(
                        "Unable to identify the KIE Deployment for artifact id | " + parameter.getProcessName());

            LOGGER.error("Attempting to complete task " + parameter.getWorkflowTaskAction().getTaskId());
            engine = RunTimeManager.getRuntimeEngineInstance(applicationProperties, bpmDeployment);
            taskService = engine.getTaskService();
            ksession = engine.getKieSession();
            Long processInstanceId = null;
            Long taskId = parameter.getWorkflowTaskAction().getTaskId();
            if (taskId == null)
                throw new FrameworkException("Task Id cannot be null");

            String taskOwners = parameter.getWorkflowTaskAction().getEmpId().toString();
            /*Task task = Optional.ofNullable(taskService.getTaskById(taskId))
                    .orElseThrow(() -> new FrameworkException("Task not found for id " + taskId));*/
            
            Task task = taskService.getTaskById(taskId);
            /*D#129972 - Setting error response with error code TNF if task is autoescalated by kie timer*/
            if(task == null) {
            	WorkflowTaskActionResponse response = new WorkflowTaskActionResponse();
            	response.setCode(MainetConstants.StatusCode.NO_CONTENT);
            	response.setMessage(MainetConstants.Rules.ErrorMessages.TASK_NOT_FOUND);
            	return response;
            }

            if (applicationProperties.isJbpmTaskInsecure()) {
                List<String> potentialOwners = task.getPeopleAssignments().getPotentialOwners().stream()
                        .map(OrganizationalEntity::getId)
                        .collect(Collectors.toList());
                if (!potentialOwners.contains(taskOwners)) {
                    LOGGER.warn("Task owner not found, continuing with first task owner");
                    taskOwners = task.getPeopleAssignments().getPotentialOwners().get(0).getId();
                    parameter.getWorkflowTaskAction().setEmpGroupDescReg(MainetConstants.Rules.ErrorMessages.UNKNOWN_USER);
                    LOGGER.warn("Setting Unknown user...");
                }
            }

            parameter.getWorkflowTaskAction().setTaskName(task.getName());
            // Maintain application SLA duration and current task SLA duration with transaction data
            Map<String, Object> content = taskService.getTaskContent(taskId);
            if (content.containsKey(MainetConstants.WorkFlow.ProcessVariables.TASK_ASSIGNMENT)) {
               taskAssignment = (TaskAssignment) content
                        .get(MainetConstants.WorkFlow.ProcessVariables.TASK_ASSIGNMENT);
                parameter.getWorkflowTaskAction().setApplicationSlaDurationInMS(taskAssignment.getApplicationSlaDurationInMS());
                parameter.getWorkflowTaskAction().setTaskSlaDurationInMS(taskAssignment.getTaskSlaDurationInMS());
            }
            // Retrieve ApplicationMetadata to identify workflow transaction request
            if (content.containsKey(MainetConstants.WorkFlow.WorkflowExecutionParameters.APPLICATION_METADATA)) {
                metadata = (ApplicationMetadata) content
                        .get(MainetConstants.WorkFlow.WorkflowExecutionParameters.APPLICATION_METADATA);
                processInstanceId = metadata.getProcessInstanceId();
                parameter.setApplicationMetadata(metadata);
            }

            Map<String, Object> parameterMap = ConversionUtility.workflowProcessParameterToMap(parameter);
            String status = task.getTaskData().getStatus().toString();

            if (status.equalsIgnoreCase(MainetConstants.WorkFlow.ImplementationServiceStatuses.Jbpm.READY)) {
                taskService.claim(taskId, taskOwners);
                taskService.start(taskId, taskOwners);
                taskService.complete(taskId, taskOwners, parameterMap);
            }
            if (status.equalsIgnoreCase(MainetConstants.WorkFlow.ImplementationServiceStatuses.Jbpm.RESERVED)) {
                taskService.start(taskId, taskOwners);
                taskService.complete(taskId, taskOwners, parameterMap);
            }
            if (status.equalsIgnoreCase(MainetConstants.WorkFlow.ImplementationServiceStatuses.Jbpm.INPROGRESS)) {
            	// LOGGER.info("taskId " + taskId);
            	// LOGGER.info("taskOwners " + taskOwners);
            	// LOGGER.info("parameterMap " + parameterMap.toString());
            	taskService.complete(taskId, taskOwners, parameterMap);
            }
            LOGGER.info("Task " + taskId + " completed successfully");
            parameter.getApplicationMetadata().setIsProcessAlive(isProcessAlive(ksession, processInstanceId));
            workflowRequest = workflowService.updateWorkflowRequest(parameter);
        } catch (FrameworkException e) {
            LOGGER.error("Unable to complete task for deployment " + bpmDeployment + " with error " + e.getMessage()
                    + " with cause " + e.getCause());
            throw new WorkflowFrameworkException(e);
        }
        return getActionSuccessResponse(parameter, workflowRequest);
    }

    @Override
    public WorkflowTaskActionResponse updateTasks(WorkflowProcessParameter parameter) throws WorkflowFrameworkException {

        LOGGER.info("Attempting to update task " + parameter.getWorkflowTaskAction().getTaskId());
        RuntimeEngine defaultEngine = RunTimeManager.getRuntimeEngineInstance(applicationProperties, null);
        TaskService defaultTaskService = defaultEngine.getTaskService();
        Long taskId = parameter.getWorkflowTaskAction().getTaskId();
        String taskOwners = parameter.getWorkflowTaskAction().getEmpId().toString();
        Task task = defaultTaskService.getTaskById(taskId);
        long processInstanceId = task.getTaskData().getProcessInstanceId();
        Map<String, Object> parameterMap = ConversionUtility.workflowProcessParameterToMap(parameter);
        defaultTaskService.start(taskId, taskOwners);
        defaultTaskService.complete(taskId, taskOwners, parameterMap);
        LOGGER.info("Task updated successfully with status" + task.getTaskData().getStatus());

        WorkflowTaskActionResponse response = new WorkflowTaskActionResponse();
        response.setProcessInstanceId(processInstanceId);
        response.setTaskId(taskId);
        return response;

    }

    @Override
    public WorkflowTaskActionResponse deleteTasks(WorkflowProcessParameter parameter) throws WorkflowFrameworkException {

        LOGGER.info("Attempting to exit task " + parameter.getWorkflowTaskAction().getTaskId());
        RuntimeEngine defaultEngine = RunTimeManager.getRuntimeEngineInstance(applicationProperties, null);
        TaskService defaultTaskService = defaultEngine.getTaskService();
        KieSession defaultKsession = defaultEngine.getKieSession();
        Long taskId = parameter.getWorkflowTaskAction().getTaskId();
        String taskOwners = parameter.getWorkflowTaskAction().getEmpId().toString();
        defaultTaskService.start(taskId, taskOwners);
        defaultTaskService.exit(taskId, taskOwners);
        LOGGER.info("Task exited successfully");
        return getActionSuccessResponse(defaultKsession, parameter);
    }

    @Override
    public WorkflowTaskActionResponse claimTasks(WorkflowProcessParameter parameter) throws WorkflowFrameworkException {

        LOGGER.info("Attempting to claim task " + parameter.getWorkflowTaskAction().getTaskId());
        RuntimeEngine defaultEngine = RunTimeManager.getRuntimeEngineInstance(applicationProperties, null);
        TaskService defaultTaskService = defaultEngine.getTaskService();
        KieSession defaultKsession = defaultEngine.getKieSession();
        Long taskId = parameter.getWorkflowTaskAction().getTaskId();
        String taskOwners = parameter.getWorkflowTaskAction().getEmpId().toString();
        Task task = defaultTaskService.getTaskById(taskId);
        defaultTaskService.start(taskId, taskOwners);
        defaultTaskService.claim(taskId, taskOwners);
        LOGGER.info("Task claimed successfully with status" + task.getTaskData().getStatus());
        return getActionSuccessResponse(defaultKsession, parameter);

    }

    @Override
    public WorkflowTaskActionResponse delegateTasks(WorkflowProcessParameter parameter) throws WorkflowFrameworkException {

        LOGGER.info("Attempting to delegate task " + parameter.getWorkflowTaskAction().getTaskId());
        RuntimeEngine defaultEngine = RunTimeManager.getRuntimeEngineInstance(applicationProperties, null);
        TaskService defaultTaskService = defaultEngine.getTaskService();
        KieSession defaultKsession = defaultEngine.getKieSession();
        Map<String, Object> parameterMap = ConversionUtility.workflowProcessParameterToMap(parameter);
        Long taskId = parameter.getWorkflowTaskAction().getTaskId();
        String taskOwners = parameter.getWorkflowTaskAction().getEmpId().toString();
        String targetUserId = (String) parameterMap.get(MainetConstants.WorkFlow.ProcessVariables.TARGET_USER_ID);
        Task task = defaultTaskService.getTaskById(taskId);
        defaultTaskService.start(taskId, taskOwners);
        defaultTaskService.delegate(taskId, taskOwners, targetUserId);
        task = defaultTaskService.getTaskById(taskId);
        LOGGER.info("Task delegated successfully with status" + task.getTaskData().getStatus());
        return getActionSuccessResponse(defaultKsession, parameter);
    }

    @Override
    public WorkflowTaskActionResponse forwardToTasks(WorkflowProcessParameter parameter) throws WorkflowFrameworkException {

        LOGGER.info("Attempting to forward task " + parameter.getWorkflowTaskAction().getTaskId());
        RuntimeEngine defaultEngine = RunTimeManager.getRuntimeEngineInstance(applicationProperties, null);
        TaskService defaultTaskService = defaultEngine.getTaskService();
        KieSession defaultKsession = defaultEngine.getKieSession();
        Map<String, Object> parameterMap = ConversionUtility.workflowProcessParameterToMap(parameter);
        Long taskId = parameter.getWorkflowTaskAction().getTaskId();
        String taskOwners = parameter.getWorkflowTaskAction().getEmpId().toString();
        String targetUserId = (String) parameterMap.get(MainetConstants.WorkFlow.ProcessVariables.TARGET_USER_ID);
        Task task = defaultTaskService.getTaskById(taskId);
        defaultTaskService.start(taskId, taskOwners);
        defaultTaskService.forward(taskId, taskOwners, targetUserId);
        task = defaultTaskService.getTaskById(taskId);
        LOGGER.info("Task forwarded successfully to user +" + targetUserId + "+ with status" + task.getTaskData().getStatus());
        return getActionSuccessResponse(defaultKsession, parameter);
    }

    @Override
    public WorkflowTaskActionResponse releaseTask(WorkflowProcessParameter parameter) throws WorkflowFrameworkException {

        LOGGER.info("Attempting to release task " + parameter.getWorkflowTaskAction().getTaskId());
        RuntimeEngine defaultEngine = RunTimeManager.getRuntimeEngineInstance(applicationProperties, null);
        TaskService defaultTaskService = defaultEngine.getTaskService();
        KieSession defaultKsession = defaultEngine.getKieSession();
        Long taskId = parameter.getWorkflowTaskAction().getTaskId();
        String taskOwners = parameter.getWorkflowTaskAction().getEmpId().toString();
        Task task = defaultTaskService.getTaskById(taskId);
        defaultTaskService.start(taskId, taskOwners);
        defaultTaskService.release(taskId, taskOwners);
        task = defaultTaskService.getTaskById(taskId);
        LOGGER.info("Task released successfully with status" + task.getTaskData().getStatus());
        return getActionSuccessResponse(defaultKsession, parameter);
    }

    @Override
    public WorkflowTaskActionResponse suspendTask(WorkflowProcessParameter parameter) throws WorkflowFrameworkException {

        LOGGER.info("Attempting to suspend task " + parameter.getWorkflowTaskAction().getTaskId());
        RuntimeEngine defaultEngine = RunTimeManager.getRuntimeEngineInstance(applicationProperties, null);
        TaskService defaultTaskService = defaultEngine.getTaskService();
        KieSession defaultKsession = defaultEngine.getKieSession();
        Long taskId = parameter.getWorkflowTaskAction().getTaskId();
        String taskOwners = parameter.getWorkflowTaskAction().getEmpId().toString();
        Task task = defaultTaskService.getTaskById(taskId);
        defaultTaskService.start(taskId, taskOwners);
        defaultTaskService.suspend(taskId, taskOwners);
        task = defaultTaskService.getTaskById(taskId);
        LOGGER.info("Task suspened successfully with status" + task.getTaskData().getStatus());
        return getActionSuccessResponse(defaultKsession, parameter);

    }

    @Override
    public WorkflowTaskActionResponse resumeTask(WorkflowProcessParameter parameter) throws WorkflowFrameworkException {

        LOGGER.info("Attempting to resume task " + parameter.getWorkflowTaskAction().getTaskId());
        RuntimeEngine defaultEngine = RunTimeManager.getRuntimeEngineInstance(applicationProperties, null);
        TaskService defaultTaskService = defaultEngine.getTaskService();
        KieSession defaultKsession = defaultEngine.getKieSession();
        Long taskId = parameter.getWorkflowTaskAction().getTaskId();
        String taskOwners = parameter.getWorkflowTaskAction().getEmpId().toString();
        Task task = defaultTaskService.getTaskById(taskId);
        defaultTaskService.start(taskId, taskOwners);
        defaultTaskService.resume(taskId, taskOwners);
        task = defaultTaskService.getTaskById(taskId);
        LOGGER.info("Task resumed successfully with status" + task.getTaskData().getStatus());
        return getActionSuccessResponse(defaultKsession, parameter);
    }

    @Override
    public WorkflowTaskActionResponse skipTask(WorkflowProcessParameter parameter) throws WorkflowFrameworkException {

        LOGGER.info("Attempting to skip task " + parameter.getWorkflowTaskAction().getTaskId());
        RuntimeEngine defaultEngine = RunTimeManager.getRuntimeEngineInstance(applicationProperties, null);
        TaskService defaultTaskService = defaultEngine.getTaskService();
        KieSession defaultKsession = defaultEngine.getKieSession();
        Long taskId = parameter.getWorkflowTaskAction().getTaskId();
        String taskOwners = parameter.getWorkflowTaskAction().getEmpId().toString();
        Task task = defaultTaskService.getTaskById(taskId);
        defaultTaskService.start(taskId, taskOwners);
        // Skipping a task (if the task has been marked as skippable), in which case the task will not be executed
        if (task.getTaskData().isSkipable())
            defaultTaskService.skip(taskId, taskOwners);
        task = defaultTaskService.getTaskById(taskId);
        LOGGER.info("Task skiped successfully with status" + task.getTaskData().getStatus());
        return getActionSuccessResponse(defaultKsession, parameter);
    }

    @Override
    public WorkflowTaskActionResponse stopTask(WorkflowProcessParameter parameter) throws WorkflowFrameworkException {

        LOGGER.info("Attempting to stop task " + parameter.getWorkflowTaskAction().getTaskId());
        RuntimeEngine defaultEngine = RunTimeManager.getRuntimeEngineInstance(applicationProperties, null);
        TaskService defaultTaskService = defaultEngine.getTaskService();
        KieSession defaultKsession = defaultEngine.getKieSession();
        Long taskId = parameter.getWorkflowTaskAction().getTaskId();
        String taskOwners = parameter.getWorkflowTaskAction().getEmpId().toString();
        Task task = defaultTaskService.getTaskById(taskId);
        defaultTaskService.start(taskId, taskOwners);
        defaultTaskService.stop(taskId, taskOwners);
        task = defaultTaskService.getTaskById(taskId);
        LOGGER.info("Task stoped successfully with status" + task.getTaskData().getStatus());
        return getActionSuccessResponse(defaultKsession, parameter);

    }

    @SuppressWarnings("unchecked")
    @Override
    public WorkflowTaskActionResponse failTask(WorkflowProcessParameter parameter) throws WorkflowFrameworkException {

        LOGGER.info("Attempting to fail task " + parameter.getWorkflowTaskAction().getTaskId());
        RuntimeEngine defaultEngine = RunTimeManager.getRuntimeEngineInstance(applicationProperties, null);
        TaskService defaultTaskService = defaultEngine.getTaskService();
        KieSession defaultKsession = defaultEngine.getKieSession();

        Map<String, Object> parameterMap = ConversionUtility.workflowProcessParameterToMap(parameter);
        Long taskId = parameter.getWorkflowTaskAction().getTaskId();
        String taskOwners = parameter.getWorkflowTaskAction().getEmpId().toString();
        Task task = defaultTaskService.getTaskById(taskId);
        defaultTaskService.start(taskId, taskOwners);
        Map<String, Object> faultData = null;
        if (parameterMap.containsKey(MainetConstants.WorkFlow.ProcessVariables.FAULT_DATA))
            faultData = (Map<String, Object>) parameterMap.get(MainetConstants.WorkFlow.ProcessVariables.FAULT_DATA);
        defaultTaskService.fail(taskId, taskOwners, faultData);
        task = defaultTaskService.getTaskById(taskId);
        LOGGER.info("Task failed successfully with status" + task.getTaskData().getStatus());

        return getActionSuccessResponse(defaultKsession, parameter);

    }

    @Override
    public Map<String, Object> getTaskContent(Long taskId) throws MapFrameworkException {

        LOGGER.info("Attempting to fetch task content for task Id " + taskId);
        RuntimeEngine engine = RunTimeManager.getRuntimeEngineInstance(applicationProperties, null);
        TaskService taskService = engine.getTaskService();
        Map<String, Object> content = null;
        try {
            content = taskService.getTaskContent(taskId);
            if (content.containsKey(MainetConstants.WorkFlow.WorkflowExecutionParameters.CHECKER_LEVELS)) {
                String temp = (String) content.get(MainetConstants.WorkFlow.WorkflowExecutionParameters.CHECKER_LEVELS);
                HashMap<?, ?> j = new ObjectMapper().readValue(temp, HashMap.class);
                content.put(MainetConstants.WorkFlow.WorkflowExecutionParameters.CHECKER_LEVELS, j);
            }
            if (content.containsKey(MainetConstants.WorkFlow.WorkflowExecutionParameters.CHECKER_LEVEL_GROUPS)) {
                String temp = (String) content.get(MainetConstants.WorkFlow.WorkflowExecutionParameters.CHECKER_LEVEL_GROUPS);
                HashMap<?, ?> j = new ObjectMapper().readValue(temp, HashMap.class);
                content.put(MainetConstants.WorkFlow.WorkflowExecutionParameters.CHECKER_LEVEL_GROUPS, j);
            }
        } catch (FrameworkException e) {
            LOGGER.error("Unable to fetch task content", e);
            throw new MapFrameworkException(e);
        } catch (JsonParseException e) {
            LOGGER.error("Unable to fetch task content", e);
            throw new MapFrameworkException(e);
        } catch (JsonMappingException e) {
            LOGGER.error("Unable to fetch task content", e);
            throw new MapFrameworkException(e);
        } catch (IOException e) {
            LOGGER.error("Unable to fetch task content", e);
            throw new MapFrameworkException(e);
        }
        return content;
    }

    @Override
    public List<WorkflowTask> getTaskListByUser(String userId) throws FrameworkException {

        List<WorkflowTask> tasksList = new ArrayList<>();
        RuntimeEngine engine = RunTimeManager.getRuntimeEngineInstance(applicationProperties, null);
        TaskService taskService = engine.getTaskService();
        LOGGER.info("Attempting to fetch tasks assigned as potential owner " + userId);
        List<TaskSummary> tasks = taskService.getTasksAssignedAsPotentialOwner(userId, "en-UK");

        tasks.parallelStream().forEach(t -> {

            WorkflowTask task = new WorkflowTask();
            task.setTaskId(t.getId());
            task.setTaskName(t.getName());
            
			/* task.setOrgId(Long.parseLong(t.getDescription())); */
            task.setTaskStatus(t.getStatus().toString());
            task.setRequestDate(ConversionUtility.dateToString(t.getCreatedOn(), MainetConstants.Common.DATE_TIME_FORMAT));

            Map<String, Object> content = taskService.getTaskContent(t.getId());

            if (content.containsKey(MainetConstants.WorkFlow.WorkflowExecutionParameters.APPLICATION_METADATA)) {
                ApplicationMetadata metadata = (ApplicationMetadata) content
                        .get(MainetConstants.WorkFlow.WorkflowExecutionParameters.APPLICATION_METADATA);
                task.setApplicationId(metadata.getApplicationId());
                task.setReferenceId(metadata.getReferenceId());
                task.setWorkflowId(metadata.getWorkflowId());
                task.setOrgId(metadata.getOrgId());
            }
            if (content.containsKey(MainetConstants.WorkFlow.WorkflowExecutionParameters.TASK_ASSIGNMENT)) {
                TaskAssignment assignment = (TaskAssignment) content
                        .get(MainetConstants.WorkFlow.WorkflowExecutionParameters.TASK_ASSIGNMENT);
                task.setServiceEventUrl(assignment.getUrl());
            }
            if (content.containsKey(MainetConstants.WorkFlow.WorkflowExecutionParameters.REQUESTER_TASK_ASSIGNMENT)) {
                TaskAssignment assignment = (TaskAssignment) content
                        .get(MainetConstants.WorkFlow.WorkflowExecutionParameters.REQUESTER_TASK_ASSIGNMENT);
                task.setServiceEventUrl(assignment.getUrl());
            }
            tasksList.add(task);

        });

        return tasksList;
    }

    @Override
    public List<WorkflowTask> getTaskListByUuid(String uuid) throws FrameworkException {

        List<WorkflowTask> tasksList = new ArrayList<>();
        RuntimeEngine engine = RunTimeManager.getRuntimeEngineInstance(applicationProperties, null);
        TaskService taskService = engine.getTaskService();
        LOGGER.info(
                "Attempting to fetch tasks assigned as business administrator "
                        + applicationProperties.getJbpmTaskAdministratorUser());
        List<TaskSummary> tasks = taskService
                .getTasksAssignedAsBusinessAdministrator(applicationProperties.getJbpmTaskAdministratorUser(), null);

        tasks.parallelStream().forEach(t -> {
            Map<String, Object> content = taskService.getTaskContent(t.getId());
            if (content.containsKey(MainetConstants.WorkFlow.WorkflowExecutionParameters.APPLICATION_METADATA)) {

                ApplicationMetadata metadata = (ApplicationMetadata) content
                        .get(MainetConstants.WorkFlow.WorkflowExecutionParameters.APPLICATION_METADATA);

                String id = (metadata.getApplicationId() != null) ? metadata.getApplicationId().toString()
                        : metadata.getReferenceId();
                if (id != null && id.equals(uuid)) {
                    WorkflowTask task = new WorkflowTask();
                    task.setTaskId(t.getId());
                    task.setTaskName(t.getName());
					/* task.setOrgId(Long.parseLong(t.getDescription())); */
                    task.setOrgId(metadata.getOrgId());
                    task.setTaskStatus(t.getStatus().toString());
                    task.setRequestDate(
                            ConversionUtility.dateToString(t.getCreatedOn(), MainetConstants.Common.DATE_TIME_FORMAT));
                    task.setApplicationId(metadata.getApplicationId());
                    task.setReferenceId(metadata.getReferenceId());
                    task.setWorkflowId(metadata.getWorkflowId());

                    if (content.containsKey(MainetConstants.WorkFlow.WorkflowExecutionParameters.TASK_ASSIGNMENT)) {
                        TaskAssignment assignment = (TaskAssignment) content
                                .get(MainetConstants.WorkFlow.WorkflowExecutionParameters.TASK_ASSIGNMENT);
                        task.setServiceEventUrl(assignment.getUrl());
                    }
                    if (content.containsKey(MainetConstants.WorkFlow.WorkflowExecutionParameters.REQUESTER_TASK_ASSIGNMENT)) {
                        TaskAssignment assignment = (TaskAssignment) content
                                .get(MainetConstants.WorkFlow.WorkflowExecutionParameters.REQUESTER_TASK_ASSIGNMENT);
                        task.setServiceEventUrl(assignment.getUrl());
                    }
                    tasksList.add(task);
                }
            }
        });

        return tasksList;
    }

    private WorkflowTaskActionResponse getActionSuccessResponse(KieSession ksession, WorkflowProcessParameter parameter) {
        WorkflowRequest workflowRequest = workflowRequestRepository
                .findByApplicationIdOrReferenceIdAndOrgId(parameter.getWorkflowTaskAction().getApplicationId(),
                        parameter.getWorkflowTaskAction().getReferenceId(),
                        parameter.getWorkflowTaskAction().getOrgId());
        parameter.getApplicationMetadata().setProcessInstanceId(workflowRequest.getProcessSessionId());
        parameter.getApplicationMetadata().setIsProcessAlive(isProcessAlive(ksession, workflowRequest.getProcessSessionId()));
        return getActionSuccessResponse(parameter, workflowRequest);

    }

    private WorkflowTaskActionResponse getActionSuccessResponse(WorkflowProcessParameter parameter,
            WorkflowRequest workflowRequest) {
        WorkflowTaskActionResponse response = new WorkflowTaskActionResponse();
        response.setSignalName(parameter.getSignalName());
        response.setTaskId(parameter.getWorkflowTaskAction().getTaskId());
        response.setWorkflowRequestId(workflowRequest.getId());
        response.setProcessInstanceId(parameter.getApplicationMetadata().getProcessInstanceId());
        response.setIsProcessAlive(parameter.getApplicationMetadata().getIsProcessAlive());
        return response;

    }

    private boolean isProcessAlive(KieSession ksession, long processInstanceId) {
        return !(null == ksession.getProcessInstance(processInstanceId));
    }

}
