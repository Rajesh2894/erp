package com.abm.mainet.bpm.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.math.BigInteger;

import javax.jws.WebMethod;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.bpm.common.dto.SchedulerDetails;
import com.abm.mainet.bpm.common.dto.TaskDetail;
import com.abm.mainet.bpm.common.dto.WorkflowProcessParameter;
import com.abm.mainet.bpm.common.dto.WorkflowTaskAction;
import com.abm.mainet.bpm.common.workflow.dto.WorkflowActionDto;
import com.abm.mainet.bpm.common.workflow.dto.WorkflowRequestDto;
import com.abm.mainet.bpm.common.workflow.dto.WorkflowTaskDto;
import com.abm.mainet.bpm.domain.BpmDeployment;
import com.abm.mainet.bpm.domain.TaskView;
import com.abm.mainet.bpm.domain.WorkflowAction;
import com.abm.mainet.bpm.domain.WorkflowRequest;
import com.abm.mainet.bpm.domain.WorkflowUserTask;
import com.abm.mainet.bpm.repository.WorkflowRequestRepository;
import com.abm.mainet.bpm.utility.ConversionUtility;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.constant.MainetConstants;
import com.abm.mainet.constant.MainetConstants.WorkFlow;
import com.abm.mainet.constant.MainetConstants.WorkFlow.Decision;
import com.abm.mainet.constant.MainetConstants.WorkFlow.Status;
import com.abm.mainet.constant.MainetConstants.WorkFlow.Task;

@Service
public class WorkflowRequestServiceImpl implements IWorkflowRequestService {

    private static final Logger LOGGER = Logger.getLogger(WorkflowRequestServiceImpl.class);

    @Autowired
    private WorkflowRequestRepository WorkflowRequestRepository;

    @Autowired
    private IWorkflowTaskService workflowTaskService;

    @Autowired
    private ITaskViewService taskViewService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @WebMethod(exclude = true)
    public WorkflowRequest saveWorkflowRequest(WorkflowProcessParameter parameter, BpmDeployment bpmDeployment)
            throws FrameworkException {
    	
    	//Increase logging, Exception Handling & Code Change Done by Rajesh Sir for this method for Workflow issues 
    	LOGGER.error("----- save Workflow transactional data-----");
        WorkflowRequest wr = new WorkflowRequest();
        WorkflowAction action = null;
        if ((parameter.getApplicationMetadata().getApplicationId() != null
                || parameter.getApplicationMetadata().getReferenceId() != null)) {
            wr.setOrgId(parameter.getApplicationMetadata().getOrgId());
            wr.setApplicationId(parameter.getApplicationMetadata().getApplicationId());
            if("RTI".equals(parameter.getWorkflowTaskAction().getDeptName()))
        	{
            	wr.setReferenceId(parameter.getApplicationMetadata().getIsCallCenterApplicable());
        	}
            else{
            	wr.setReferenceId(parameter.getApplicationMetadata().getReferenceId());
            }
            wr.setProcessSessionId(parameter.getApplicationMetadata().getProcessInstanceId());
            wr.setStatus(Status.PENDING.getValue()); // for every new service initiation status should be pending
            wr.setWorkflowTypeId(parameter.getApplicationMetadata().getWorkflowId());
            wr.setProcessName(parameter.getProcessName());
            wr.setDeploymentId(bpmDeployment.getId());

            LOGGER.error("---saving Workflow transactional data Application Id: " + wr.getApplicationId() + " "
    				+ " referencenumber: " + wr.getReferenceId() + "process Id :" + parameter.getApplicationMetadata().getProcessInstanceId());

            // used to get all task from view based on application or reference id and by
            // workFlow id
            List<TaskView> taskviewList = taskViewService.getAllTaskById(wr.getApplicationId(), wr.getReferenceId(),
                    parameter.getApplicationMetadata().getWorkflowId());

            action = ConversionUtility.toAction(parameter.getWorkflowTaskAction());

            if (parameter.getWorkflowTaskAction().getAttachementId() != null
                    && !parameter.getWorkflowTaskAction().getAttachementId().isEmpty())
                action.setAttachmentIds(parameter.getWorkflowTaskAction().getAttachementId().stream()
                        .map(a -> Long.toString(a)).collect(Collectors.joining(MainetConstants.OPERATOR.COMMA))); // get
                                                                                                                  // all
                                                                                                                  // attachment
                                                                                                                  // comma
                                                                                                                  // separated
                                                                                                                  // and
                                                                                                                  // saved
                                                                                                                  // in
                                                                                                                  // action
                                                                                                                  // table

            if (parameter.getWorkflowTaskAction() != null) {

                // create a dummy task for first action
                WorkflowUserTask proxyTask = prepareProxyTask(action, wr);
                /*List<Object[]> apm = WorkflowRequestRepository.getDeptIdAndServiceIdByAppOrRefNo(wr.getApplicationId(),
						wr.getReferenceId());
                proxyTask.setServiceId(((BigInteger)apm.get(0)[0]).longValue());
				proxyTask.setDeptId(((BigInteger)apm.get(0)[1]).longValue());*/
                LOGGER.info("-----From WorkflowRequestServiceImpl printing DeptId: " + parameter.getRequesterTaskAssignment().getDeptId() +
                		"ServiceId: " + parameter.getRequesterTaskAssignment().getServiceId() + "URL: " + parameter.getRequesterTaskAssignment().getUrl());
                
                
                LOGGER.info("DeptId: " + parameter.getRequesterTaskAssignment().getDeptId());
                if(parameter.getRequesterTaskAssignment().getDeptId() != null){
                proxyTask.setDeptId(parameter.getRequesterTaskAssignment().getDeptId());
             
                }
                
                LOGGER.info("Service Id: " + parameter.getRequesterTaskAssignment().getServiceId());
                
                if(parameter.getRequesterTaskAssignment().getServiceId() != null){
                proxyTask.setServiceId(parameter.getRequesterTaskAssignment().getServiceId());
                }
                
                LOGGER.info("URL: "  + parameter.getRequesterTaskAssignment().getUrl());
                
                if(parameter.getRequesterTaskAssignment().getUrl() != null){
                proxyTask.setServiceEventUrl(parameter.getRequesterTaskAssignment().getUrl());
                
                }
                
                
                proxyTask.setWorkFlowRequest(wr);
                proxyTask.getWorkflowActionList().add(action);
                wr.getWorkFlowTaskList().add(proxyTask);
                action.setWorkFlowTask(proxyTask);
                action.setWorkFlowRequestId(proxyTask.getWorkFlowRequest().getId());
                action.setCreatedDate(new Date());
                action.setDecision(Decision.SUBMITTED.getValue()); // at initiation level current status of action
                                                                   // SUBMITTED
                action.setTaskId(-1l);
                wr.setCreatedBy(action.getCreatedBy());
                wr.setCreatedDate(new Date());
                wr.setLastDateOfAction(new Date());
                wr.setLastDecision(Decision.SUBMITTED.getValue());// at initiation level request status SUBMITTED
                wr.setEmpId(action.getEmpId());
                wr.setEmpType(action.getEmpType());

                // Commented below code because setting applicationSlaDurationInMs from WorkflowTaskAction for Application SLA
                /*
                 * wr.setApplicationSlaDurationInMS( (parameter.getWorkflowTaskAction().getApplicationSlaDurationInMS() != null) ?
                 * parameter.getWorkflowTaskAction().getApplicationSlaDurationInMS() : 0l);
                 */

                // setting applicationSlaDurationInMs from ApplicationMetadata for Application SLA
                wr.setApplicationSlaDurationInMS(
                        (parameter.getApplicationMetadata().getApplicationExpiryDuration() != null)
                                ? Long.valueOf(parameter.getApplicationMetadata().getApplicationExpiryDuration())
                                : 0l);

            }

            List<WorkflowUserTask> taskList = prepareAllNewlyTask(taskviewList, wr);
            wr.getWorkFlowTaskList().addAll(taskList);

            WorkflowRequestRepository.save(wr);

            wr.getWorkFlowTaskList().stream().forEach(taskObj -> {
                taskObj.getWorkflowActionList().stream().forEach(actionObj -> {
                    actionObj.setWorkFlowRequestId(wr.getId());
                });
            });
            WorkflowRequest wy = WorkflowRequestRepository.save(wr); // for 2nd time save(new to update request id in action
                                                                     // table)
            LOGGER.error("---- save method execution completed---- ");

            workflowTaskService.notifyUser(wy.getWorkFlowTaskList(), action); // used to notify user for newly created
            // task
            LOGGER.error("------- notofication completed---------");
        }

        return wr;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @WebMethod(exclude = true)
    public WorkflowRequest updateWorkflowRequest(WorkflowProcessParameter parameter) throws FrameworkException {

        LOGGER.info("-----Trying to update Workflow transactional data-----");
        WorkflowRequest wr = null;
        WorkflowAction action = null;
        List<TaskView> taskviewList = null;
        if (parameter.getWorkflowTaskAction() != null) {
            if (parameter.getApplicationMetadata().getWorkflowReqId() != null)
                wr = WorkflowRequestRepository.getOne(parameter.getApplicationMetadata().getWorkflowReqId());
            else {
                Long applicationId = parameter.getWorkflowTaskAction().getApplicationId();
                String referenceId = parameter.getWorkflowTaskAction().getReferenceId();
                wr = WorkflowRequestRepository.findByApplicationIdOrReferenceIdAndOrgIdAndWorkflowTypeId(applicationId,
                        referenceId, parameter.getApplicationMetadata().getOrgId(),
                        parameter.getApplicationMetadata().getWorkflowId());
            }

            Set<String> oldAttachementIds = new HashSet<>();

            // getting all old attachment id from all previous action
            wr.getWorkFlowTaskList().forEach(t -> {
                t.getWorkflowActionList().stream().forEach(a -> {
                    if (a.getAttachmentIds() != null)
                        oldAttachementIds
                                .addAll(Arrays.asList(a.getAttachmentIds().split(MainetConstants.OPERATOR.COMMA)));
                });
            });
            action = ConversionUtility.toAction(parameter.getWorkflowTaskAction());
            if (parameter.getWorkflowTaskAction().getAttachementId() != null
                    && !parameter.getWorkflowTaskAction().getAttachementId().isEmpty()) {
                String newAttachments = parameter.getWorkflowTaskAction().getAttachementId().stream().map(a -> {
                    // filtering old attachment id from attachment list to get new attachment from
                    // current action
                    String newAttachmentId = Long.toString(a);
                    if (!oldAttachementIds.contains(newAttachmentId))
                        return newAttachmentId;
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.joining(MainetConstants.OPERATOR.COMMA));
                action.setAttachmentIds(newAttachments.isEmpty() ? null : newAttachments);
            }
            
            if((parameter.getWorkflowTaskAction().getDecision().equals(Decision.HOLD.getValue())) && 
            		parameter.getWorkflowTaskAction().getAttachementId() != null && 
            		!parameter.getWorkflowTaskAction().getAttachementId().isEmpty()){
        	   String holdedAttachments = parameter.getWorkflowTaskAction().getAttachementId().stream().map(a -> {
                   
                   String holdedAttachmentId = Long.toString(a);
                   if (oldAttachementIds.contains(holdedAttachmentId))
                       return holdedAttachmentId;
                   return null;
               }).filter(Objects::nonNull).collect(Collectors.joining(MainetConstants.OPERATOR.COMMA));
               action.setAttachmentIds(holdedAttachments.isEmpty() ? null : holdedAttachments);		   
            }
            
            /*get taskviewlist data if task is forwarded to orgnaisation or Forwarded to Location with new workflowId*/
            if(parameter.getWorkflowTaskAction().getWorkflowId()!= null &&
            		(parameter.getWorkflowTaskAction().getDecision().equals(Decision.FORWARD_TO_ORGANISATION.getValue())			
            				|| (parameter.getWorkflowTaskAction().getForwardToEmployeeType()!=null 
            				&& parameter.getWorkflowTaskAction().getForwardToEmployeeType().equals(Decision.LOCATION.getValue()))
            				|| (parameter.getWorkflowTaskAction().getForwardDepartment()!= null &&    
            				parameter.getWorkflowTaskAction().getDecision().equals(Decision.FORWARD_TO_DEPARTMENT.getValue())))) {
            	taskviewList = taskViewService.getAllTaskById(wr.getApplicationId(), wr.getReferenceId(),
            			parameter.getWorkflowTaskAction().getWorkflowId());
            	/*if Task is forwarded to organisation or Forward to Location then add workflowId & orgId from respective organisation*/
            	wr.setWorkflowTypeId(parameter.getWorkflowTaskAction().getWorkflowId());
            	wr.setOrgId(parameter.getWorkflowTaskAction().getOrgId());
            }else
                taskviewList = taskViewService.getAllTaskById(wr.getApplicationId(), wr.getReferenceId(),
                        wr.getWorkflowTypeId());
            
            prepareWorkFlowTaskentity(taskviewList, wr, action, parameter);

            wr.setModifiedDate(new Date());
            wr.setLastDateOfAction(new Date());
            wr.setLastDecision(action.getDecision());

            // Commented below code because while updating Workflow request wrong data sla was getting updated.
            /*
             * if (parameter.getWorkflowTaskAction().getApplicationSlaDurationInMS() != null &&
             * parameter.getWorkflowTaskAction().getApplicationSlaDurationInMS() > 0)
             * wr.setApplicationSlaDurationInMS(parameter.getWorkflowTaskAction().getApplicationSlaDurationInMS());
             */

            // used to check process status if all process and task are completed then
            // request status updated to CLOSED and all
            // pending task will be EXITED
            setWorklfowStatus(wr, parameter);

            wr = WorkflowRequestRepository.save(wr);
            LOGGER.info("---- update method execution completed---- ");
            workflowTaskService.notifyUser(wr.getWorkFlowTaskList(), action);
            LOGGER.info("------- notofication completed---------");
        }
        return wr;
    }

    /**
     * used to prepare work flow task Entity
     * 
     * @param taskviewList
     * @param wr
     * @param action
     */
    private void prepareWorkFlowTaskentity(List<TaskView> taskviewList, WorkflowRequest wr, WorkflowAction action, WorkflowProcessParameter parameter) {

        Date newDate = new Date();

        // Complete current task
        Optional<WorkflowUserTask> currentTask = wr.getWorkFlowTaskList().stream()
                .filter(t -> t.getTaskId().equals(action.getTaskId())).findFirst();
        if (currentTask.isPresent()) {
            WorkflowUserTask task = currentTask.get();
            task.setTaskStatus(Status.COMPLETED.getValue()); // set current task as completed
            action.setWorkFlowTask(task);
            task.setDateOfCompletion(newDate);
            task.setModifiedDate(newDate);
            task.setModifiedBy(action.getEmpId());
            /*Hidden_Task Requester(Care Reopend) can be completed by any user other than existing empId from task record*/
            if(task.getTaskName().contains(WorkFlow.Task.HIDDEN.getValue()) &&
            		!task.getActorId().equals(String.valueOf(action.getEmpId()))) {
            	task.setActorId(String.valueOf(action.getEmpId()));
            }
            //This block is related to Road Cutting for Prayagraj REJECT all the in-progress tasks
            //EmpEmail -> Env &&  EmpGroupDescReg -> Service Code
            if(StringUtils.equalsIgnoreCase(parameter.getWorkflowTaskAction().getEmpGroupDescReg(),"RCP") && 
            		StringUtils.equalsIgnoreCase(parameter.getWorkflowTaskAction().getEmpEmail(),"PSCL") && StringUtils.equalsIgnoreCase(parameter.getWorkflowTaskAction().getDecision(),Decision.REJECTED.getValue())){
            		
            		LOGGER.info("Task name equals: " +parameter.getWorkflowTaskAction().getEmpGroupDescReg());
            		LOGGER.info("Empemail equals: " +parameter.getWorkflowTaskAction().getEmpEmail());
            		
            		for (WorkflowAction c : task.getWorkflowActionList()) {
            			c.setDecision((action.getDecision()));
                        c.setComments(MainetConstants.Common.REJECTED_MSG +parameter.getWorkflowTaskAction().getEmpName());
                        c.setAttachmentIds(action.getAttachmentIds());
                        /*c.setEmpId(action.getEmpId());*/
                        break;
					}
            			
            }
            else if(task.getTaskName().contains(WorkFlow.Task.LOI_Payment.getValue()) && 
            		StringUtils.equalsIgnoreCase(parameter.getWorkflowTaskAction().getEmpGroupDescReg(),MainetConstants.Rules.ErrorMessages.UNKNOWN_USER)){
            		LOGGER.info("Inside unkown user for LOI Payment: " +action.getEmpId());
            			for (WorkflowAction c : task.getWorkflowActionList()) {
            					c.setDecision((action.getDecision()));
            					c.setComments(action.getComments());
            					c.setAttachmentIds(action.getAttachmentIds());
            					c.setEmpId(action.getEmpId());
            					break;
            			}
            }
            else{
                task.getWorkflowActionList().stream().forEach(c -> {// set current action
                    /*Hidden_Task Requester(Care Reopend) can be completed by any user other than existing empId from task record*/	
                    	if (c.getEmpId().equals(action.getEmpId())) {
                            c.setDecision((action.getDecision()));
                            c.setComments(action.getComments());
                            c.setAttachmentIds(action.getAttachmentIds());
                    	}
                            else if(c.getTaskName().contains(WorkFlow.Task.HIDDEN.getValue())) {
                    		c.setDecision((action.getDecision()));
                            c.setComments(action.getComments());
                            c.setAttachmentIds(action.getAttachmentIds());
                            c.setEmpId(action.getEmpId());
                    	} 
                    });
            }
            // taskList.add(task);
        }

        // Identify new task from task view
        Set<Long> dbTaskIds = wr.getWorkFlowTaskList().stream().map(WorkflowUserTask::getTaskId)
                .collect(Collectors.toSet());
        List<TaskView> newTaskviewList = taskviewList.stream().filter(t -> !dbTaskIds.contains(t.getTaskId()))
                .collect(Collectors.toList());

        // Add new task in database
        List<WorkflowUserTask> taskList = prepareAllNewlyTask(newTaskviewList, wr);
        wr.getWorkFlowTaskList().addAll(taskList);
    }

    /**
     * used to create dummy task for action at initiation level
     * 
     * @param action
     * @param wr
     * @return
     */
    private WorkflowUserTask prepareProxyTask(WorkflowAction action, WorkflowRequest wr) {

        WorkflowUserTask taskObj = new WorkflowUserTask();
        taskObj.setTaskId(-1l);
        taskObj.setTaskName(Task.START.getValue());
        taskObj.setTaskStatus(Status.COMPLETED.getValue());
        taskObj.setApplicationId(wr.getApplicationId());
        taskObj.setReferenceId(wr.getReferenceId());
        taskObj.setWorkflowId(wr.getWorkflowTypeId());
        taskObj.setOrgId(action.getOrgId());
        taskObj.setActorId(action.getEmpId().toString());
        taskObj.setCurrentEscalationLevel(0l);
        taskObj.setCreatedBy(wr.getCreatedBy());
        taskObj.setCreatedDate(wr.getCreatedDate());
        taskObj.setDateOfAssignment(wr.getCreatedDate());
        taskObj.setDateOfCompletion(wr.getCreatedDate());
        return taskObj;

    }

    /**
     * used to prepare all new task fetched from view and action list to each task
     * 
     * @param taskviewList
     * @param wr
     * @param taskList
     */
    private List<WorkflowUserTask> prepareAllNewlyTask(List<TaskView> taskviewList, WorkflowRequest wr) {
    	
    	//Increase logging, Exception Handling & Code Change Done by Rajesh Sir for this method for Workflow issues
        List<WorkflowUserTask> taskList = new ArrayList<>();
        
        if(taskviewList!=null && !taskviewList.isEmpty()) {
        	LOGGER.error("Started method for prepare new task list for application Id" +wr.getApplicationId() );
        taskviewList.stream().forEach(t -> {
            WorkflowUserTask taskObj = new WorkflowUserTask();
            ObjectMapper mapper = new ObjectMapper();
            TaskDetail tsakDetails = null;
            List<WorkflowAction> actionList = new ArrayList<>();
            try {
                tsakDetails = mapper.readValue(t.getTaskData(), TaskDetail.class);
            } catch (IOException e) {
            	LOGGER.error("exception while reading task detals from mapper");
                throw new FrameworkException("exception while reading task detals from mapper", e);
            }
            taskObj.setTaskId(t.getTaskId());
            taskObj.setTaskName(t.getTaskName());

            taskObj.setTaskStatus(MainetConstants.WorkFlow.BpmTaskStatus.getStatus(t.getTaskStatus()));
            taskObj.setApplicationId(t.getApplicationId());
            taskObj.setReferenceId(t.getReferenceId());
            /*if Task is forwarded to organisation then add workflowId from respective organisation*/
				if (wr.getWorkflowTypeId() != t.getWorkflowId()) {
					taskObj.setWorkflowId(t.getWorkflowId());
				} else
					taskObj.setWorkflowId(wr.getWorkflowTypeId());
            	
            taskObj.setOrgId(tsakDetails.getOrgId());
            taskObj.setDeptId(tsakDetails.getDeptId());
            taskObj.setDeptName(tsakDetails.getDeptName());
            taskObj.setDeptNameReg(tsakDetails.getDeptNameReg());
            taskObj.setServiceId(tsakDetails.getServiceId());
            taskObj.setServiceName(tsakDetails.getServiceName());
            taskObj.setServiceNameReg(tsakDetails.getServiceNameReg());

            taskObj.setServiceEventUrl(t.getTaskUrl());

            taskObj.setServiceEventId(tsakDetails.getServiceEventId());
            taskObj.setServiceEventName(tsakDetails.getServiceEventName());
            taskObj.setServiceEventNameReg(tsakDetails.getServiceEventNameReg());

            if (tsakDetails.getCheckerGroup() != null)
                taskObj.setCurrentCheckerGroup(tsakDetails.getCheckerGroup().longValue());

            if (tsakDetails.getCheckerLevel() != null)
                taskObj.setCurentCheckerLevel(tsakDetails.getCheckerLevel().longValue());

            taskObj.setActorId(t.getTaskActorId());
            taskObj.setTaskSlaDurationInMS(tsakDetails.getTaskSlaDurationInMS());
            taskObj.setCurrentEscalationLevel(tsakDetails.getEscalationIndex());
            taskObj.setCreatedBy(wr.getCreatedBy());
            taskObj.setCreatedDate(new Date());
            taskObj.setDateOfAssignment(new Date());

            // used to prepare action for each newly created task based on actor id
            List<String> actorId = Arrays.asList(taskObj.getActorId().split(MainetConstants.Regex.CSV));
            actorId.forEach(actor -> {
                if (actor.matches(MainetConstants.Regex.NUMERIC_PATTERN)) {
                    WorkflowAction action = new WorkflowAction();
                    action.setApplicationId(taskObj.getApplicationId());
                    action.setReferenceId(taskObj.getReferenceId());
                    action.setDecision(Status.PENDING.getValue());// for every newly created action decision is pending
                    action.setOrgId(taskObj.getOrgId());
                    action.setEmpId(Long.parseLong(actor));
                    action.setTaskId(taskObj.getTaskId());
                    action.setTaskName(taskObj.getTaskName());
                    action.setCreatedDate(taskObj.getCreatedDate());
                    action.setCreatedBy(taskObj.getCreatedBy());
                    action.setModifiedDate(taskObj.getModifiedDate());
                    action.setModifiedBy(taskObj.getModifiedBy());
                    action.setTaskSlaDurationInMS(
                            (taskObj.getTaskSlaDurationInMS() != null) ? taskObj.getTaskSlaDurationInMS() : 0l);
                    action.setWorkFlowTask(taskObj);
                    action.setWorkFlowRequestId(wr.getId());
                    actionList.add(action);
                }
            });
            taskObj.setWorkflowActionList(actionList);

            taskObj.setWorkFlowRequest(wr);

            taskList.add(taskObj);
        });
        }else {
        	LOGGER.info("Found null taskview list for application Id" +wr.getApplicationId());
        }
        return taskList;
    }

    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public boolean isWorkflowExist(WorkflowProcessParameter parameter) {
        boolean isWorklfowExist = false;
        if (parameter.getWorkflowTaskAction() != null) {
            List<WorkflowRequest> wr = WorkflowRequestRepository.findByApplicationIdOrReferenceIdAndOrgIdAndWorkflowTypeIdDesc(
                    parameter.getWorkflowTaskAction().getApplicationId(),
                    parameter.getWorkflowTaskAction().getReferenceId(), parameter.getWorkflowTaskAction().getOrgId(),
                    parameter.getApplicationMetadata().getWorkflowId());
            
			if (!CollectionUtils.isEmpty(wr)) {
				if (!StringUtils.isEmpty(parameter.getWorkflowTaskAction().getReferenceId())) {
					WorkflowRequest workflowRequest = wr.get(0);
					if (!StringUtils.isEmpty(String.valueOf(parameter.getWorkflowTaskAction().getApplicationId()))) {
						if (workflowRequest != null && !workflowRequest.getStatus()
								.equalsIgnoreCase(MainetConstants.WorkFlow.Status.CLOSED.getValue())) {
							
							LOGGER.info("Both ApplicationId and ReferenceID is present");
							LOGGER.info("Application Status is: " + workflowRequest.getApplicationId() + ":"
									+ workflowRequest.getStatus());
							isWorklfowExist = true;
						
						}
					} else {
						LOGGER.info("ReferenceID is present");
						LOGGER.info("Application Status is: " + workflowRequest.getReferenceId());
						isWorklfowExist = true;

					}
				} else {
					LOGGER.info("Application Id is present");
					isWorklfowExist = true;
				}
        }
    }
        return isWorklfowExist;
    }

    @Override
    @Transactional(readOnly = true)
    public WorkflowRequestDto getWorkflowRequestByAppIdOrRefId(final String uuid, final Long orgId) {
        WorkflowRequestDto wrDto = null;
        if (uuid == null)
            return wrDto;
        String referenceId = null;
        Long applicationId = null;
        if (!uuid.matches(MainetConstants.Regex.NUMERIC_PATTERN))
            referenceId = uuid;
        else
            applicationId = Long.parseLong(uuid);
        WorkflowRequest wr = WorkflowRequestRepository.findByApplicationIdOrReferenceIdAndOrgId(applicationId,
                referenceId, orgId);
        wrDto = mapEnityToDto(wr);
        return wrDto;
    }

    @Override
    @Transactional(readOnly = true)
    public WorkflowRequestDto findByApplicationId(final Long applicationId) {
        WorkflowRequestDto wrDto = null;
        WorkflowRequest wr = WorkflowRequestRepository.findByApplicationId(applicationId);
        if (wr != null && wr.getId() != null) {
            wrDto = new WorkflowRequestDto();
            wrDto = mapEnityToDto(wr);
        }
        return wrDto;
    }

    private WorkflowRequestDto mapEnityToDto(WorkflowRequest wr) {
        if (null == wr)
            return null;
        WorkflowRequestDto wrDto = new WorkflowRequestDto();
        BeanUtils.copyProperties(wr, wrDto);
        List<WorkflowTaskDto> taskList = StreamSupport.stream(wr.getWorkFlowTaskList().spliterator(), false).map(t -> {
            List<WorkflowActionDto> workFlowActionList = StreamSupport
                    .stream(t.getWorkflowActionList().spliterator(), false).map(a -> {
                        WorkflowActionDto action = new WorkflowActionDto();
                        BeanUtils.copyProperties(a, action);
                        return action;
                    }).collect(Collectors.toList());
            WorkflowTaskDto task = new WorkflowTaskDto();
            BeanUtils.copyProperties(t, task);
            task.setWorkFlowActionList(workFlowActionList);
            return task;
        }).collect(Collectors.toList());
        wrDto.setWorkFlowTaskList(taskList);
        return wrDto;
    }

    private void setWorklfowStatus(WorkflowRequest wr, WorkflowProcessParameter parameter) {
        WorkflowTaskAction action = parameter.getWorkflowTaskAction();
        if (!parameter.getApplicationMetadata().getIsProcessAlive()) {
            wr.setStatus(MainetConstants.WorkFlow.Status.CLOSED.getValue());
        } else {
            Optional<WorkflowUserTask> currentTask = wr.getWorkFlowTaskList().stream()
                    .filter(t -> t.getTaskId().equals(action.getTaskId())).findFirst();
            if (currentTask.isPresent()) {
                if (currentTask.get().getTaskName().equalsIgnoreCase(WorkFlow.Task.CSO.getValue())
                        && (action.getDecision().equals(Decision.APPROVED.getValue())
                                || action.getDecision().equals(Decision.REJECTED.getValue()))) {
                    wr.setStatus(MainetConstants.WorkFlow.Status.CLOSED.getValue());
                }else if(parameter.getApplicationMetadata() != null 
                		&& parameter.getApplicationMetadata().getIsCallCenterApplicable() != null) {
                	/*D#111346 - To reopen task if only grievance task is present for kdmc*/
                	if(parameter.getApplicationMetadata().getIsCallCenterApplicable().equals("N")) {

                        if (currentTask.get().getTaskName().equalsIgnoreCase(WorkFlow.Task.GRIEVANCE.getValue())
                                && (action.getDecision().equals(Decision.APPROVED.getValue())
                                        || action.getDecision().equals(Decision.REJECTED.getValue())
                                        || action.getDecision().equals(Decision.FORCE_CLOSURE.getValue()))) {
                            wr.setStatus(MainetConstants.WorkFlow.Status.CLOSED.getValue());
                        }
                    
                	}
                	
                }
            }
            if (action.getDecision().equals(Decision.REOPENED.getValue())) {
                wr.setStatus(MainetConstants.WorkFlow.Status.PENDING.getValue());
            }
        }
        if (wr.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED.getValue())) {
            wr.getWorkFlowTaskList().forEach(t -> {
                if (t.getTaskStatus().equals(MainetConstants.WorkFlow.Status.PENDING.getValue())
                        && t.getCurrentEscalationLevel() > 0) {
                    t.setTaskStatus(MainetConstants.WorkFlow.Status.EXITED.getValue());
                }
            });
        }

    }

    @Override
    @Transactional(readOnly = true)
    public WorkflowRequestDto findByApplicationIdAndWorkflowId(Long applicationId, Long workFlowId) {
        WorkflowRequestDto wrDto = null;
        WorkflowRequest wr = WorkflowRequestRepository.findByApplicationIdAndWorkFlowId(applicationId, workFlowId);
        if (wr != null && wr.getId() != null) {
            wrDto = new WorkflowRequestDto();
            wrDto = mapEnityToDto(wr);
        }
        return wrDto;
    }

    @Override
    @Transactional
    public void updateWorkflowAutoEscalationTask(SchedulerDetails schedulerDetails) {

        LOGGER.info(" --- job schedular started to update all Auto Escalation Task ---");
        List<Long> workfFlowId = schedulerDetails.getWorkFlowId();
        List<WorkflowRequest> wr = new ArrayList<>();
        List<WorkflowRequest> wr1 = new ArrayList<>();
        if (workfFlowId != null && !workfFlowId.isEmpty()) {
            wr = WorkflowRequestRepository.getWorkflowRequestListByWorkFlowIds(workfFlowId);
            if (!wr.isEmpty()) {
                wr.forEach(w -> {
                    w.getWorkFlowTaskList().forEach(t -> {
                    	
                    	String status =  WorkflowRequestRepository.getWorkTaskListBytaskIds(t.getTaskId());
                    	
                        if (t.getTaskStatus().equals(MainetConstants.WorkFlow.Status.PENDING.getValue())) {
                            Long totalDate = t.getDateOfAssignment().getTime() + t.getTaskSlaDurationInMS();

                            // Escalate those task only whose having sla greater than Zero (0).
                            if (totalDate <= new Date().getTime() && t.getTaskSlaDurationInMS() > 0 && MainetConstants.WorkFlow.Status.EXITED.getValue().equalsIgnoreCase(status)) {
                                t.setTaskStatus(MainetConstants.WorkFlow.Status.EXITED.getValue());
                            }
                            /*
                             * Long totalReopenTaskDate = t.getDateOfAssignment().getTime() + w.getApplicationSlaDurationInMS();
                             * if (totalReopenTaskDate <= new Date().getTime()) {
                             * t.setTaskStatus(MainetConstants.WorkFlow.Status.EXITED.getValue()); }
                             */
                        }
                    });
                    Set<Long> dbTaskIds = w.getWorkFlowTaskList().stream().map(WorkflowUserTask::getTaskId)
                            .collect(Collectors.toSet());
                    List<TaskView> taskviewList = taskViewService.getAllTaskById(w.getApplicationId(),
                            w.getReferenceId(), w.getWorkflowTypeId());
                    List<TaskView> newTaskviewList = taskviewList.stream()
                            .filter(t -> !dbTaskIds.contains(t.getTaskId())).collect(Collectors.toList());
                    List<WorkflowUserTask> wt = prepareAllNewlyTask(newTaskviewList, w);
                    /*send escalated sms & email*/ 
                    if(CollectionUtils.isNotEmpty(wt)) {
                    	List<WorkflowUserTask> wfu = prepareAllNewlyTask(newTaskviewList, w);
                    	sendSmsEmailForEscalation(wfu);
                    }
                    w.getWorkFlowTaskList().addAll(wt);
                });
            }
            LOGGER.info(" --- updated task count ---" + wr1.size());
            WorkflowRequestRepository.save(wr1);
        }
        LOGGER.info(" --- job schedular End to update all Auto Escalation Task ---");
    }

    @Override
    @Transactional
    public void updateWorkflowObjectionTask(SchedulerDetails schedulerDetails) {

        LOGGER.info(" --- job schedular started to update all Objection Task ---");
        List<Long> appId = schedulerDetails.getApplicationId();
        List<WorkflowRequest> wr = new ArrayList<>();
        if (appId != null && !appId.isEmpty()) {
            wr = WorkflowRequestRepository.getWorkflowRequestListByApplicationId(appId);
            if (!wr.isEmpty()) {
                wr.forEach(w -> {
                    Set<Long> dbTaskIds = w.getWorkFlowTaskList().stream().map(WorkflowUserTask::getTaskId)
                            .collect(Collectors.toSet());
                    List<TaskView> taskviewList = taskViewService.getAllTaskById(w.getApplicationId(),
                            w.getReferenceId(), w.getWorkflowTypeId());
                    List<TaskView> newTaskviewList = taskviewList.stream()
                            .filter(t -> !dbTaskIds.contains(t.getTaskId())).collect(Collectors.toList());
                    List<WorkflowUserTask> wt = prepareAllNewlyTask(newTaskviewList, w);
                    w.getWorkFlowTaskList().addAll(wt);

                });
            }
            WorkflowRequestRepository.save(wr);
        }
        LOGGER.info(" --- job schedular End to update all Objection Task ---");

    }
    
	public void sendSmsEmailForEscalation(List<WorkflowUserTask> wfut) {
		List<WorkflowUserTask> wtDtoForNotification = new ArrayList<>();
		WorkflowAction wfa = new WorkflowAction();
		wfa.setDecision(MainetConstants.WorkFlow.Decision.ESCALATED.getValue());
		wtDtoForNotification.addAll(wfut);
		/*
		 * Passing different url to get sms message only for esaclation at service side
		 */
		wtDtoForNotification.forEach(wtd -> wtd.setServiceEventUrl(MainetConstants.ESCALATION_URL));

		workflowTaskService.notifyUser(wtDtoForNotification, wfa);
		wtDtoForNotification.clear();
		LOGGER.info("------- notofication completed---------");
	}

	
}
