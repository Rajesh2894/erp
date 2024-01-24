package com.abm.mainet.common.workflow.service;

import java.util.LinkedHashMap;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.TaskAssignmentRequest;

/**
 * To identify task owner at runtime BPM process can make REST calls, back to service layer. Service layer is place where we
 * configure workflow and it's related task with its owner details as master data. TaskAssignmentService provide API's to retrieve
 * task owner details from workflow master. All API provided by this service are in use by BPM process depending on the business
 * logic and runtime data.
 * 
 * Task owner identification is depends on various business rules. This service enables the dynamic task assignment feature where
 * task owner configuration and identification logic decoupled from BPM processes.
 * 
 * This interface is CXF enabled service, which is exposed as REST and SOPA API's
 * 
 * 
 * @author sanket.joshi
 * @see TaskAssignmentServiceImpl
 *
 */
@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface ITaskAssignmentService {

    /**
     * Return initial task assignment details. This API will be call by all BPM process which are configured to manage auto
     * escalation mechanism. Initial task assignment is the first task defined in workflow master.
     * 
     * {@code workflowTypeId} is the mandatory field in {@code taskAssignmentRequest}
     * 
     * @param taskAssignmentRequest
     * @return TaskAssignment
     */
    TaskAssignment getInitialByWorkflowType(TaskAssignmentRequest taskAssignmentRequest);

    /**
     * Return next escalation task assignment details. This API will be call by all BPM process which are configured to manage
     * auto escalation mechanism. next escalation task assignment is the task defined in workflow master at
     * '{@code (currentEscalationIndex + 1)}' position.
     * 
     * {@code workflowTypeId & currentEscalationIndex} are the mandatory field in {@code taskAssignmentRequest}
     * 
     * @param taskAssignmentRequest
     * @return TaskAssignment
     */
    TaskAssignment getNextEscalationByWorkflowTypeAndCurrentEscalationIndex(TaskAssignmentRequest taskAssignmentRequest);

    /**
     * Return task assignment details by service event name. {@code workflowTypeId & serviceEventName} are the mandatory field in
     * {@code taskAssignmentRequest}
     * 
     * serviceEventName can by any standard workflow event defined under properties
     * 
     * @param taskAssignmentRequest
     * @return TaskAssignment
     */
    TaskAssignment getServicesEventByWorkflowTypeAndServiceEventName(TaskAssignmentRequest taskAssignmentRequest);

    /**
     * Return task assignment level details by service event name. {@code workflowTypeId & serviceEventName} are the mandatory
     * field in {@code taskAssignmentRequest}.
     * 
     * This API is in use to get all scrutiny levels where serviceEventName will be 'Scrutiny' 'serviceEventName' can by any
     * standard workflow event defined under application properties.
     * 
     * @param taskAssignmentRequest
     * @return Map of levels with TaskAssignment field map as values
     */
    LinkedHashMap<String, TaskAssignment> getEventLevelsByWorkflowTypeAndEventName(TaskAssignmentRequest taskAssignmentRequest);

    /**
     * Return task assignment level groups details by service event name. {@code workflowTypeId & serviceEventName} are the
     * mandatory field in {@code taskAssignmentRequest}.
     * 
     * This API is in use to get all maker-checker levels where serviceEventName will be 'Checker'. 'serviceEventName' can by any
     * standard workflow event defined under application properties.
     * 
     * @param taskAssignmentRequest
     * @return Map of groups with TaskAssignment levels map as values
     */
    LinkedHashMap<String, LinkedHashMap<String, TaskAssignment>> getEventLevelGroupsByWorkflowTypeAndEventName(
            TaskAssignmentRequest taskAssignmentRequest);

    /**
     * This API will identify service type workflow from workflow master and return initial task assignment details.
     * {@code orgId, departmentId, serviceId} are the mandatory and {@code codIdOperLevels} are optional filed in
     * {@code taskAssignmentRequest}.
     * 
     * @param taskAssignmentRequest
     * @return TaskAssignment
     */
    TaskAssignment resolveServiceWorkflowTypeAndGetInitialTaskAassignment(TaskAssignmentRequest taskAssignmentRequest);

    /**
     * This API will identify complaint type workflow from workflow master and return initial task assignment details.
     * {@code orgId, departmentId, compTypeId} are the mandatory and {@code codIdOperLevels} are optional filed in
     * {@code taskAssignmentRequest}.
     * 
     * @param taskAssignmentRequest
     * @return TaskAssignment
     */
    TaskAssignment resolveComplaintWorkflowTypeAndGetInitialTaskAassignment(TaskAssignmentRequest taskAssignmentRequest);
    
    /**
     * This API will identify service type workflow from workflow master based on ward zone wise & service event Name
     * {@code orgId, departmentId, serviceId,codIdOperLevel,serviceEventName} are the mandatory in
     * {@code taskAssignmentRequest}.
     * 
     * @param taskAssignmentRequest
     * @return TaskAssignment
     */
    TaskAssignment resolveServiceWorkflowTypeByWardZoneAndServiceEventName(TaskAssignmentRequest taskAssignmentRequest);
}
