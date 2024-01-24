package com.abm.mainet.bpm.service;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestParam;

import com.abm.mainet.bpm.common.dto.TaskSearchRequest;
import com.abm.mainet.bpm.common.dto.WorkflowTaskActionWithDocs;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@WebService(endpointInterface = "com.abm.mainet.bpm.service.IWorkflowActionService")
@Api("Workflow Action Service")
@Path("/workflow/action")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IWorkflowActionService {

    /**
     * Used to find Task Details By application/reference and workflow request ID with details and attachments.Instead of orgId
     * workflow request ID is used to retrieve action taken across organizations
     * 
     * @param applicationId
     * @param workflowReqId
     * @return
     */
    @POST
    @Path("/list")
    @ApiOperation(value = "Get the actions by application/reference id ,status and orgId", notes = "Get the actions by application/reference id (uuid),status and orgId", response = WorkflowTaskActionWithDocs.class)
    public List<WorkflowTaskActionWithDocs> findByApplicationIdWithDetails(@RequestParam TaskSearchRequest taskRequest);

    /**
     * Used to fine All Pending Action By application/reference
     * 
     * @param uuid
     * @param lang
     * @return
     */
    @POST
    @Path("/list/pending")
    @ApiOperation(value = "Get the actions by application/reference id (uuid) and orgId", notes = "Get the actions by application/reference id (uuid) and orgId", response = WorkflowTaskActionWithDocs.class)
    public List<WorkflowTaskActionWithDocs> findPendingActionByUuid(@RequestParam TaskSearchRequest taskRequest);

}
