package com.abm.mainet.bpm.service;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.bpm.common.dto.SchedulerDetails;
import com.abm.mainet.bpm.common.dto.WorkflowProcessParameter;
import com.abm.mainet.bpm.common.workflow.dto.WorkflowRequestDto;
import com.abm.mainet.bpm.domain.BpmDeployment;
import com.abm.mainet.bpm.domain.WorkflowRequest;
import com.abm.mainet.common.exception.FrameworkException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * IWorkflowRequestService is provided to manage DB operations on WorkflowRequest entity. This service provide API to save/update
 * WorkflowRequest, to retrieve WorkflowRequest by various fields.
 * 
 * This interface is CXF enabled service, which is exposed as REST and SOPA API's
 * 
 * @author sanket.joshi
 * @author Jeetendra.Pal
 *
 */
@WebService(endpointInterface = "com.abm.mainet.bpm.service.IWorkflowRequestService")
@Api("Workflow Request Service")
@Path("/workflow/request")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IWorkflowRequestService {

    /**
     * This method will save WorkflowRequest
     * 
     * @param parameter
     * @return
     * @throws FrameworkException
     */
    WorkflowRequest saveWorkflowRequest(WorkflowProcessParameter parameter, BpmDeployment bpmDeployment)
            throws FrameworkException;

    /**
     * This method will update WorkflowRequest
     * 
     * @param parameter
     * @return
     * @throws FrameworkException
     */
    WorkflowRequest updateWorkflowRequest(WorkflowProcessParameter parameter) throws FrameworkException;

    /*
     * @WebMethod(exclude = true) void updateWorkflowRequest(ApplicationMetadata metadata);
     * @WebMethod(exclude = true) void closeWorkflowRequest(Long applicationId, Optional<String> decision);
     * @WebMethod(exclude = true) void expireWorkflowRequest(Long applicationId);
     * @WebMethod(exclude = true) void openWorkflowRequest(Long applicationId, Optional<String> decision);
     * @WebMethod(exclude = true) void addWorkflowTaskAction(Long workflowReqId, WorkflowAction action);
     * @WebMethod(exclude = true) void updateWorkflowRequestSetProcessInstanceId(Long processInstanceId, Long workflowRequestId);
     */

    /**
     * This method try to retrieve WorkflowRequest by applicationId/referenceId and orgId, if found then this method will return
     * true else false.
     * 
     * @param parameter
     * @return
     */
    boolean isWorkflowExist(WorkflowProcessParameter parameter);

    /**
     * This method will retrieve WorkflowRequest by applicationId/referenceId(UUID) and orgId
     * 
     * @param uuid
     * @param orgId
     * @return
     */
    @POST
    @Path("/uuid/{uuid}/orgId/{orgId}")
    @ApiOperation(value = "Get the request by application/reference id (uuid) and orgId", notes = "Get the request by application/reference id (uuid) and orgId", response = WorkflowRequest.class)
    WorkflowRequestDto getWorkflowRequestByAppIdOrRefId(@PathParam("uuid") String uuid, @PathParam("orgId") Long orgId);

    /**
     * This method will retrieve WorkflowRequest by applicationId
     * 
     * @param applicationId
     * @return
     */
    @POST
    @Path("/applicationId/{applicationId}")
    @ApiOperation(value = "Get the request by application Id", notes = "Get the request by application Id", response = WorkflowRequest.class)
    WorkflowRequestDto findByApplicationId(@PathParam("applicationId") Long applicationId);

    /**
     * This method will retrieve WorkflowRequest by applicationId( overloaded method to get work flow request by work flow id)
     * 
     * @param applicationId
     * @return
     */
    @POST
    @Path("/applicationId/{applicationId}/workFlowId/{workFlowId}")
    @ApiOperation(value = "Get the request by application Id", notes = "Get the request by application Id", response = WorkflowRequest.class)
    WorkflowRequestDto findByApplicationIdAndWorkflowId(@PathParam("applicationId") Long applicationId,
            @PathParam("workFlowId") Long workFlowId);

    /**
     * used to update Work flow Auto Escalation Task
     * @param workFlowId
     */
    @POST
    @Path("/workflowAutoEscalationTask")
    void updateWorkflowAutoEscalationTask(
            @ApiParam(value = "SchedulerDetails", required = true) @RequestBody SchedulerDetails schedulerDetails);

    /**
     * used to update Work flow Objection Task
     * @param applicationId
     */
    @POST
    @Path("/workflowObjectionTask")
    void updateWorkflowObjectionTask(
            @ApiParam(value = "SchedulerDetails", required = true) @RequestBody SchedulerDetails schedulerDetails);

}
