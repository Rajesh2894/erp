package com.abm.mainet.bpm.service;

import java.util.Map;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.bpm.common.dto.TaskAssignment;
import com.abm.mainet.bpm.common.dto.TaskAssignmentRequest;
import com.abm.mainet.bpm.common.dto.TaskNotificationRequest;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.exception.MapFrameworkException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

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
 * @author sanket.joshi
 * @see TaskAssignmentRequest
 * @see TaskAssignment
 *
 */
@WebService(endpointInterface = "com.abm.mainet.bpm.service.ITaskAssignmentService")
@Api("Task Assignment Service")
@Path("/task/assignment")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ITaskAssignmentService {

    /**
     * Return initial task assignment details. This API will be call by all BPM process which are configured to manage auto
     * escalation mechanism. Initial task assignment is the first task defined in workflow master.
     * 
     * {@code workflowTypeId} is the mandatory field in {@code taskAssignmentRequest}
     * 
     * @param taskAssignmentRequest
     * @return TaskAssignment field map
     * @throws FrameworkException
     */
    @POST
    @Path("/initial")
    Map<?, ?> getInitialTaskAassignment(
            @ApiParam(value = "Task Assignment Request", required = true) @RequestBody TaskAssignmentRequest taskAssignmentRequest)
            throws MapFrameworkException;

    /**
     * Return next escalation task assignment details. This API will be call by all BPM process which are configured to manage
     * auto escalation mechanism. next escalation task assignment is the task defined in workflow master at
     * '{@code (currentEscalationIndex + 1)}' position.
     * 
     * {@code workflowTypeId & currentEscalationIndex} are the mandatory field in {@code taskAssignmentRequest}
     * 
     * @param taskAssignmentRequest
     * @return TaskAssignment field map
     * @throws FrameworkException
     */
    @POST
    @Path("/nextEscalation")
    Map<?, ?> nextEscalationTaskAassignment(
            @ApiParam(value = "Task Assignment Request", required = true) @RequestBody TaskAssignmentRequest taskAssignmentRequest)
            throws MapFrameworkException;

    /**
     * Return task assignment details by service event name. {@code workflowTypeId & serviceEventName} are the mandatory field in
     * {@code taskAssignmentRequest}
     * 
     * serviceEventName can by any standard workflow event defined under properties
     * 
     * @param taskAssignmentRequest
     * @return TaskAssignment field map
     * @throws FrameworkException
     */
    @POST
    @Path("/workflowType/servicesEvent/name")
    Map<?, ?> getTaskAassignmentByWorkflowTypeAndServiceEventName(
            @ApiParam(value = "Task Assignment Request", required = true) @RequestBody TaskAssignmentRequest taskAssignmentRequest)
            throws MapFrameworkException;

    /**
     * Return task assignment level details by service event name. {@code workflowTypeId & serviceEventName} are the mandatory
     * field in {@code taskAssignmentRequest}.
     * 
     * This API is in use to get all scrutiny levels where serviceEventName will be 'Scrutiny' 'serviceEventName' can by any
     * standard workflow event defined under application properties.
     * 
     * @param taskAssignmentRequest
     * @return Map of levels with TaskAssignment field map as values
     * @throws FrameworkException
     */
    @POST
    @Path("/eventLevels")
    Map<?, ?> getTaskAassignmentLevelsByWorkflowTypeAndEventName(
            @ApiParam(value = "Task Assignment Request", required = true) @RequestBody TaskAssignmentRequest taskAssignmentRequest)
            throws MapFrameworkException;

    /**
     * Return task assignment level groups details by service event name. {@code workflowTypeId & serviceEventName} are the
     * mandatory field in {@code taskAssignmentRequest}.
     * 
     * This API is in use to get all maker-checker levels where serviceEventName will be 'Checker'. 'serviceEventName' can by any
     * standard workflow event defined under application properties.
     * 
     * @param taskAssignmentRequest
     * @return Map of groups with TaskAssignment levels map as values
     * @throws FrameworkException
     */
    @POST
    @Path("/eventLevelGroups")
    Map<?, ?> getTaskAassignmentGroupsByWorkflowTypeAndEventName(
            @ApiParam(value = "Task Assignment Request", required = true) @RequestBody TaskAssignmentRequest taskAssignmentRequest)
            throws MapFrameworkException;

    /**
     * 
     * This API will identify service type workflow from workflow master and return initial task assignment details.
     * {@code orgId, departmentId, serviceId} are the mandatory and {@code codIdOperLevels} are optional filed in
     * {@code taskAssignmentRequest}.
     * 
     * @param taskAssignmentRequest
     * @return TaskAssignment field map
     * @throws FrameworkException
     */
    @POST
    @Path("/organization/department/service/wardZoneLevels")
    Map<?, ?> resolveServiceWorkflowTypeAndGetInitialTaskAassignment(
            @ApiParam(value = "Task Assignment Request", required = true) @RequestBody TaskAssignmentRequest taskAssignmentRequest)
            throws MapFrameworkException;

    /**
     * This API will identify complaint type workflow from workflow master and return initial task assignment details.
     * {@code orgId, departmentId, compTypeId} are the mandatory and {@code codIdOperLevels} are optional filed in
     * {@code taskAssignmentRequest}.
     * 
     * @param taskAssignmentRequest
     * @return TaskAssignment field map
     * @throws FrameworkException
     */
    @POST
    @Path("/organization/department/complaint/wardZoneLevels")
    Map<?, ?> resolveComplaintWorkflowTypeAndGetInitialTaskAassignment(
            @ApiParam(value = "Task Assignment Request", required = true) @RequestBody TaskAssignmentRequest taskAssignmentRequest)
            throws MapFrameworkException;

    /**
     * REST call to service end point URL's to notify task potential owner by Email and SMS Asynchronous service to register
     * request and return quickly with void.
     * 
     * @param taskNotificationRequest
     */
    void notifyUser(TaskNotificationRequest taskNotificationRequest);
    
    
    /**
     * This API will identify service type workflow from workflow master based on ward zone wise & service event Name
     * {@code orgId, departmentId, serviceId,codIdOperLevel,serviceEventName} are the mandatory in
     * {@code taskAssignmentRequest}.
     * 
     * @param taskAssignmentRequest
     * @return TaskAssignment
     */
    @POST
    @Path("/wardZoneLevels/servicesEventName")
    public Map<?, ?> resolveServiceWorkflowTypeByWardZoneAndServiceEventName(
    		@ApiParam(value = "Task Assignment Request", required = true) @RequestBody TaskAssignmentRequest taskAssignmentRequest);

}
