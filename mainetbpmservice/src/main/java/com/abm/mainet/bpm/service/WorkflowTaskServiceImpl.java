package com.abm.mainet.bpm.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.jws.WebMethod;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.bpm.common.dto.TaskNotificationRequest;
import com.abm.mainet.bpm.common.dto.TaskSearchRequest;
import com.abm.mainet.bpm.common.dto.WorkflowTask;
import com.abm.mainet.bpm.common.workflow.dto.WorkflowTaskDto;
import com.abm.mainet.bpm.domain.TaskDetailView;
import com.abm.mainet.bpm.domain.WorkflowAction;
import com.abm.mainet.bpm.domain.WorkflowUserTask;
import com.abm.mainet.bpm.repository.TaskDetailsRepository;
import com.abm.mainet.bpm.repository.WorkflowRequestRepository;
import com.abm.mainet.bpm.repository.WorkflowTaskRepository;
import com.abm.mainet.bpm.utility.ConversionUtility;
import com.abm.mainet.bpm.utility.CustomJpaSpecifications.TaskSpecification;
import com.abm.mainet.config.ApplicationProperties;
import com.abm.mainet.constant.MainetConstants;
import com.abm.mainet.constant.MainetConstants.WorkFlow.Status;
import org.apache.log4j.Logger;

@Service
public class WorkflowTaskServiceImpl implements IWorkflowTaskService {
	
    private static final Logger LOGGER = Logger.getLogger(WorkflowTaskServiceImpl.class);


	@Autowired
	private WorkflowTaskRepository workflowTaskRepository;

	@Autowired
	private ApplicationProperties applicationProperties;

	@Autowired
	private ITaskAssignmentService taskAssignmentService;

	@Autowired
	private TaskDetailsRepository taskDetailsRepository;

	@Override
	@Async
	@Transactional
	@WebMethod(exclude = true)
	public void notifyUser(List<WorkflowUserTask> list, WorkflowAction action) {

		list.forEach(taskDto -> {
			// Condition taskDto.getTaskId() > 0 used for removing Start task Id (-1) from
			// notification
			if (applicationProperties.isTaskAddNotifiaction() && taskDto.getTaskId() > 0) {
				TaskNotificationRequest notificationRequest = new TaskNotificationRequest();
				notificationRequest.setOrgId(taskDto.getOrgId());
				notificationRequest.setWorkflowId(taskDto.getWorkflowId());
				if (taskDto.getApplicationId() != null)
					notificationRequest.setApplicationId(taskDto.getApplicationId().toString());
				notificationRequest.setReferenceId(taskDto.getReferenceId());
				notificationRequest.setServiceEventUrl(taskDto.getServiceEventUrl());
				notificationRequest.setLastDecision(action.getDecision());
				notificationRequest.setComments(action.getComments());
				notificationRequest.setActorId(taskDto.getActorId());

				// Sending TaskSlaDurationInMS to mainetService for Notification purpose
				if (taskDto.getTaskSlaDurationInMS() == null) {
					taskDto.setTaskSlaDurationInMS(0l);
				}
				notificationRequest.setSlaCal(taskDto.getTaskSlaDurationInMS());
				taskAssignmentService.notifyUser(notificationRequest);
			}
		});
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkflowTask> findAllPendingByUUId(String uuid) {
		String referenceId = null;
		Long applicationId = null;
		if (!uuid.matches(MainetConstants.Regex.NUMERIC_PATTERN))
			referenceId = uuid;
		else
			applicationId = Long.parseLong(uuid);
		List<WorkflowTask> activeUserListDTOs = StreamSupport
				.stream(workflowTaskRepository.findByApplicationIdOrReferenceId(applicationId, referenceId)
						.spliterator(), false)
				.filter(t -> t.getTaskStatus().equals(Status.PENDING.getValue())).map(entity -> {
					WorkflowTask dto = new WorkflowTask();
					BeanUtils.copyProperties(entity, dto);
					dto.setRequestDate(ConversionUtility.dateToString(entity.getDateOfAssignment(),
							MainetConstants.Common.DATE_TIME_FORMAT));
					return dto;
				}).collect(Collectors.toList());
		return activeUserListDTOs;
	}

	/*
	 * @Override
	 * 
	 * @Transactional(readOnly = true) public List<WorkflowTask>
	 * searchTask(TaskSearchRequest searchRequest) { List<WorkflowTask> tasks =
	 * StreamSupport.stream(
	 * taskDetailsRepository.findAll(TaskSpecification.likeTaskSearchRequest(
	 * searchRequest)).spliterator(), false).map(entity -> { WorkflowTask dto =
	 * null; dto = new WorkflowTask(); BeanUtils.copyProperties(entity, dto);
	 * dto.setRequestDate(ConversionUtility.dateToString(entity.getDateOfAssignment(
	 * ), MainetConstants.Common.DATE_TIME_FORMAT)); if (searchRequest.getLangId()
	 * == MainetConstants.Lang.REG) { dto.setDeptName(dto.getDeptNameReg());
	 * dto.setServiceName(dto.getServiceNameReg());
	 * dto.setServiceEventName(dto.getServiceEventNameReg()); } return dto;
	 * }).collect(Collectors.toList());
	 * 
	 * 
	 * return tasks; }
	 */

	@Override
	@Transactional(readOnly = true)
	public List<WorkflowTask> searchTask(TaskSearchRequest searchRequest) {
		List<String> list =new ArrayList<>();
		// getting pending task list using query instead of VW_Workflowtask_detail
		if(null != searchRequest.getStatus()  && MainetConstants.tasks.COMPLETED.equalsIgnoreCase(searchRequest.getStatus())) {
		    list =Arrays.asList(searchRequest.getStatus(),"EXITED");
		}else
		    list =Arrays.asList(searchRequest.getStatus());
		
		LOGGER.error("Calling findTasksForEmployee with " +searchRequest.getEmpId() + "  " + list +  " " 
				+ searchRequest.getOrgId());
		List<Object[]> taskList = workflowTaskRepository.findTasksForEmployee(searchRequest.getEmpId(),
				list,searchRequest.getOrgId());
		LOGGER.error("Got Response from workflowTaskRepository.");
		
		
		List<WorkflowTask> tasks = taskList.parallelStream().map(objArray ->{
			WorkflowTask dto = new WorkflowTask();
			//BeanUtils.copyProperties(entity, dto);
			if(objArray[0]!=null)
				dto.setApplicationId(((BigInteger) objArray[0]).longValue());
			dto.setOrgId(((BigInteger) objArray[1]).longValue());
			if(objArray[2]!=null)
				dto.setDeptId(((BigInteger) objArray[2]).longValue());
			dto.setDeptName((String) objArray[3]);
			if(objArray[5]!=null)
				dto.setServiceId(((BigInteger) objArray[5]).longValue());
			dto.setServiceName((String) objArray[6]);
			if(objArray[8]!=null)
				dto.setServiceEventId(((BigInteger) objArray[8]).longValue());
			dto.setServiceEventName((String) objArray[9]);
			if(objArray[11]!=null)
				dto.setTaskId(((BigInteger) objArray[11]).longValue());
			dto.setTaskName((String) objArray[12]);
			dto.setServiceEventUrl((String) objArray[13]);
			dto.setRequestDate(ConversionUtility.dateToString((Date)objArray[14],MainetConstants.Common.DATE_TIME_FORMAT));
			if(objArray[15]!=null)
				dto.setWorkflowReqId(((BigInteger) objArray[15]).longValue());
			dto.setTaskStatus((String) objArray[16]);
			dto.setLastDecision((String) objArray[17]);
			if(objArray[19]!=null)
				dto.setReferenceId((String) objArray[19]);
			if(objArray[20]!=null)
				dto.setWorkflowId(((BigInteger) objArray[20]).longValue());
			if(objArray[21]!=null)
				dto.setTaskSlaDurationInMS(((BigInteger) objArray[21]).longValue());
			if (searchRequest.getLangId() == MainetConstants.Lang.REG) {
				dto.setDeptName((String) objArray[4]);
				dto.setServiceName((String) objArray[7]);
				dto.setServiceEventName((String) objArray[10]);
			}
			if(objArray[22]!=null) {
				dto.setDateOfAssignment( ( java.sql.Timestamp) objArray[22]);
			}
			if(objArray[23]!=null) {
				dto.setDateOfCompletion( ( java.sql.Timestamp) objArray[23]);
			}
			if(objArray[24]!=null) {
			dto.setSmShortCode((String) objArray[24]);
			}
			return dto;
		}).collect(Collectors.toList());
		
		return tasks;
	}

	@Override
	@Transactional(readOnly = true)
	public WorkflowTaskDto findByTaskId(Long taskId) {
		WorkflowTaskDto dto = null;
		WorkflowUserTask ts = workflowTaskRepository.findByTaskId(taskId);
		if (ts != null) {
			dto = new WorkflowTaskDto();
			BeanUtils.copyProperties(ts, dto);
		}
		return dto;
	}

	@Override
	public WorkflowTaskDto findByReferenceIdAndTaskId(String referenceId, Long taskId) {
		WorkflowTaskDto dto = null;
		WorkflowUserTask ts = workflowTaskRepository.findByReferenceIdAndTaskId(referenceId,taskId);
		if (ts != null) {
			dto = new WorkflowTaskDto();
			BeanUtils.copyProperties(ts, dto);
		}
		return dto;
	}

	

	

}
