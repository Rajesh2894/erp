package com.abm.mainet.bpm.service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.jws.WebMethod;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.abm.mainet.bpm.common.dto.TaskAssignment;
import com.abm.mainet.bpm.common.dto.TaskAssignmentRequest;
import com.abm.mainet.bpm.common.dto.TaskNotificationRequest;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.exception.MapFrameworkException;
import com.abm.mainet.config.ApplicationProperties;
import com.abm.mainet.config.RestClient;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * To identify task owner at runtime BPM process can make REST calls, back to service layer. Service layer is place where we
 * configure workflow and it's related task with its owner details as master data. TaskAssignmentService service provide API's to
 * retrieve task owner details from workflow master. All API provided by this service are in used by BPM process depending on the
 * business logic and runtime data.
 * 
 * Task owner identification is depends on various business rules. This service enables the dynamic task assignment feature where
 * task owner configuration and identification logic decoupled from BPM processes.
 * 
 * @author sanket.joshi
 * @see TaskAssignmentRequest
 * @see TaskAssignment
 *
 */
@Service
public class TaskAssignmentServiceImpl implements ITaskAssignmentService {

    private static final Logger LOGGER = Logger.getLogger(ITaskAssignmentService.class);

    @Autowired
    private RestClient restClient;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Override
    public Map<?, ?> getInitialTaskAassignment(TaskAssignmentRequest taskAssignmentRequest)
            throws MapFrameworkException {
        LOGGER.info("Request to get initial TaskAssignment By WorkflowType | " + taskAssignmentRequest.getWorkflowTypeId());
        Map<?, ?> taskAassignment = getTaskAssignment(applicationProperties.getTaskAssignmentInitialUrl(), taskAssignmentRequest);
        LOGGER.info("TaskAssignment Details >>>"+taskAassignment);
        return taskAassignment;
    }

    @Override
    public Map<?, ?> nextEscalationTaskAassignment(TaskAssignmentRequest taskAssignmentRequest) throws MapFrameworkException {
        LOGGER.info("Request to get nextEscalationTaskAassignment");
        Map<?, ?> taskAassignment = getTaskAssignment(applicationProperties.getTaskAssignmentNextEscalationUrl(),
                taskAssignmentRequest);
        return taskAassignment;
    }

    @Override
    public Map<?, ?> getTaskAassignmentByWorkflowTypeAndServiceEventName(TaskAssignmentRequest taskAssignmentRequest)
            throws MapFrameworkException {
        LOGGER.info(
                "Request to get ServicesEvent TaskAssignment by WorkflowTypeId " + taskAssignmentRequest.getWorkflowTypeId()
                        + "and ServiceEventName | "
                        + taskAssignmentRequest.getServiceEventName());
        Map<?, ?> taskAassignment = getTaskAssignment(applicationProperties.getTaskAssignmentServiceEventNameUrl(),
                taskAssignmentRequest);
        LOGGER.info("TaskAssignment Details >>>"+taskAassignment);
        return taskAassignment;
    }

    @Override
    public Map<?, ?> getTaskAassignmentLevelsByWorkflowTypeAndEventName(TaskAssignmentRequest taskAssignmentRequest)
            throws MapFrameworkException {
        LOGGER.info("Request to get Event Levels with TaskAssignment By WorkflowType | "
                + taskAssignmentRequest.getWorkflowTypeId());
        Map<?, ?> taskAassignment = getTaskAssignment(applicationProperties.getTaskAssignmentEventLevelsUrl(),
                taskAssignmentRequest);
        return taskAassignment;
    }

    @Override
    public Map<?, ?> getTaskAassignmentGroupsByWorkflowTypeAndEventName(TaskAssignmentRequest taskAssignmentRequest)
            throws MapFrameworkException {
        LOGGER.info("Request to get Event Levels with TaskAssignment By WorkflowType | "
                + taskAssignmentRequest.getWorkflowTypeId());
        Map<?, ?> taskAassignment = getTaskAssignment(applicationProperties.getTaskAssignmentEventLevelGroupsUrl(),
                taskAssignmentRequest);
        return taskAassignment;
    }

    @Override
    public Map<?, ?> resolveServiceWorkflowTypeAndGetInitialTaskAassignment(TaskAssignmentRequest taskAssignmentRequest)
            throws MapFrameworkException {
        Map<?, ?> taskAassignment = getTaskAssignment(applicationProperties.getTaskAssignmentDepartmentServiceUrl(),
                taskAssignmentRequest);
        return taskAassignment;
    }

    @Override
    public Map<?, ?> resolveComplaintWorkflowTypeAndGetInitialTaskAassignment(TaskAssignmentRequest taskAssignmentRequest)
            throws MapFrameworkException {
        Map<?, ?> taskAassignment = getTaskAssignment(applicationProperties.getTaskAssignmentDepartmentComplaintUrl(),
                taskAssignmentRequest);
        return taskAassignment;
    }

    @Async
    @Override
    @WebMethod(exclude = true)
    public void notifyUser(TaskNotificationRequest taskNotificationRequest) {
        try {
            restClient.callRestTemplateClient(taskNotificationRequest,
                    applicationProperties.getTaskAssignmentNotifyUrl());
        } catch (Exception e) {
            LOGGER.error("Exception while sending notification to user " + taskNotificationRequest.getActorId()
                    + " with error " + e.getMessage()
                    + " with cause " + e.getCause());
            throw new MapFrameworkException(e);
        }
    }

    /**
     * REST call to service end point URL's to retrieve service event level groups with TaskAssignment for current
     * TaskAssignmentRequest.
     * <p>
     * This method will return the response as is which will received from rest call. For Example: {@code TaskAssignment} for
     * singular, {@code Map<String,TaskAssignmentRequest>} multiple levels with level count as key,
     * {@code Map<String,Map<String,TaskAssignmentRequest>>} groups of multiple levels with group count as key
     * </p>
     * 
     * @param url
     * @param taskAssignmentRequest
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @WebMethod(exclude = true)
    private Map<?, ?> getTaskAssignment(String url, TaskAssignmentRequest taskAssignmentRequest)
            throws FrameworkException {
        LinkedHashMap<?, ?> responseData = (LinkedHashMap<String, Map<String, Object>>) restClient
                .callRestTemplateClient(taskAssignmentRequest, url);
        return responseData;
    }
    
    @Override
    public Map<?, ?> resolveServiceWorkflowTypeByWardZoneAndServiceEventName(TaskAssignmentRequest taskAssignmentRequest)
            throws MapFrameworkException {
        Map<?, ?> taskAassignment = getTaskAssignment(applicationProperties.getTaskAssignmentDepartmentLocationServiceUrl(),
                taskAssignmentRequest);
        LOGGER.info("TaskAssignment Details >>>"+taskAassignment);
        return taskAassignment;
    }

}
