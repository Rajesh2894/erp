package com.abm.mainet.bpm.service;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.bpm.common.dto.TaskSearchRequest;
import com.abm.mainet.bpm.common.dto.WorkflowTask;
import com.abm.mainet.bpm.common.workflow.dto.WorkflowTaskDto;
import com.abm.mainet.bpm.domain.WorkflowAction;
import com.abm.mainet.bpm.domain.WorkflowUserTask;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * IWorkflowRequestService is provided to manage DB operations on WorkflowTask entity. This service provide API to retrieve
 * WorkflowTask by various fields.
 * 
 * 
 * This interface is CXF enabled service, which is exposed as REST and SOPA API's
 * 
 * @author sanket.joshi
 * @author Jeetendra.Pal
 *
 */
@WebService(endpointInterface = "com.abm.mainet.bpm.service.IWorkflowTaskService")
@Api("Workflow Task Service")
@Path("/workflow/task")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IWorkflowTaskService {

    /**
     * This method will send task creation notification to all actors related to each task in task list. This method will make
     * REST API to MainetService to send template specific notification(Email, SMS) to users.
     * 
     * @param list
     * @param action
     */
    void notifyUser(List<WorkflowUserTask> list, WorkflowAction action);

    /**
     * This method will retrieve task list by applicationId/referenceId(UUID)
     * 
     * 
     * @param uuid
     * @return
     */
    @POST
    @Path("/list/uuid/{uuid}")
    @ApiOperation(value = "Get the task by uuid", notes = "Get the task by uuid", response = Map.class)
    List<WorkflowTask> findAllPendingByUUId(@ApiParam(value = "UUID", required = true) @PathParam("uuid") String uuid);

    /**
     * This method will search task by various fields of TaskSearchRequest, where all not null fields will act as an AND in query
     * 
     * @param searchRequest
     * @return
     */
    @POST
    @Path("/search")
    List<WorkflowTask> searchTask(
            @ApiParam(value = "Task Search", required = true) @RequestBody TaskSearchRequest searchRequest);

    /**
     * This method will retrieve task list by applicationId and taskId
     * 
     * @param taskId
     * @param applicationId
     * @return
     */
    @POST
    @Path("/taskId/{taskId}")
    WorkflowTaskDto findByTaskId(@PathParam("taskId") Long taskId);
    
    /**
     * This method will retrieve task list by referenceId and taskId 
     * 
     * @param referenceId
     * @param taskId
     * @return
     */
    @POST
    @Path("/taskId/{taskId}/{referenceId}")
    WorkflowTaskDto findByReferenceIdAndTaskId(@PathParam("referenceId") String referenceId,@PathParam("taskId") Long taskId);
}
