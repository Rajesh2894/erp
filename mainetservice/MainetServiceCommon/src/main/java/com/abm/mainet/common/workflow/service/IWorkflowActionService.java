package com.abm.mainet.common.workflow.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.workflow.dto.TaskSearchRequest;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.dto.WorkflowAction;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface IWorkflowActionService {

    /**
     * used to get Workflow Action Log By Application Id/Reference Id
     * @param uuid
     * @param lang
     * @return
     */
    @POST
    @ApiOperation(value = "Fetch workflow action by uuid", notes = "Fetch workflow action by uuid", response = WorkflowTaskActionWithDocs.class)
    @Path("/uuid/{uuid}/lang/{langId}")
    List<WorkflowTaskActionWithDocs> getWorkflowActionLogByApplicationIdandLang(
            @ApiParam(value = "Application/Reference Id", required = true) @PathParam("uuid") String uuid,
            @ApiParam(value = "Language Id", required = true) @PathParam("langId") short lang);

    List<WorkflowTaskActionWithDocs> getWorkflowActionLogByApplicationId(
            @ApiParam(value = "Application Id", required = true) @PathParam("uuid") String uuid,
            @ApiParam(value = "Language Id", required = true) @PathParam("langId") short lang);
    
    @POST
    @ApiOperation(value = "Fetch workflow action by referenceId", notes = "Fetch workflow action by referenceId", response = WorkflowTaskActionWithDocs.class)
    @Path("/referenceId/{referenceId}/lang/{langId}")
    List<WorkflowTaskActionWithDocs> getWorkflowActionLogByReferenceId(
            @ApiParam(value = "Reference Id", required = true) @PathParam("referenceId") String referenceId,
            @ApiParam(value = "Language Id", required = true) @PathParam("langId") short lang);

    /**
     * used to get Action Log By UUID And Workflow Id Or by application Id
     * @param applicationId
     * @param workflowReqId
     * @param lang
     * @return
     */
    List<WorkflowTaskActionWithDocs> getActionLogByUuidAndWorkflowId(String applicationId, Long workflowReqId, short lang);

    /**
     * used to get Workflow Pending Action By Uuid
     * @param uuid
     * @param lang
     * @return
     */
    List<WorkflowTaskActionWithDocs> getWorkflowPendingActionByUuid(String uuid, short lang);

    /**
     * used to update workflow action and task
     * @param workFlowActionDto
     * @param emp
     * @param orgId
     * @param serviceId
     */
    void updateWorkFlow(WorkflowTaskAction workFlowActionDto, Employee emp, Long orgId, Long serviceId);

    /**
     * used to get all Employee By workFlowId
     * @param workFlowId
     * @param orgId
     * @return
     */
    Map<String, String> getEmpByworkFlowId(Long workFlowId, Long orgId);

    /**
     * used to get Employee By Event Id
     * @param lookList
     * @param serEventId
     * @param orgId
     * @return
     */
    Map<String, String> getEmpsByEvent(Set<LookUp> lookList, String serEventId, Long orgId);

    /**
     * used to process signal work flow request, this is common method used in property and Rti module .
     * @param workFlowActionDto
     * @param emp
     * @param orgId
     * @param serviceId
     * @param signalName
     */
    void signalWorkFlow(WorkflowTaskAction workFlowActionDto, Employee emp, Long orgId, Long serviceId, String signalName);

    /**
     * used to get Action Log By AppId or RefId And Workflow Id Or by application Id
     * @param applicationId
     * @param referenceId
     * @param workflowReqId
     * @param lang
     * @return
     */
    public List<WorkflowTaskActionWithDocs> getActionLogByAppIdOrRefIdAndWorkflowId(final String applicationId,
            final String referenceId, final Long workflowReqId,
            final short lang);

    /**
     * used to get Workflow Action Log By Application Id
     * @param uuid
     * @param lang
     * @return
     */
    // List<WorkflowTaskActionWithDocs> getWorkflowActionLogByApplicationIdandLang(String uuid, short lang);
    List<String> getWorkflowActionCommentsByAppIdAndEmpId(Long applicationId, Long empId, Long orgId);
    
    @POST
    @ApiOperation(value = "Fetch Complaint Dashboard data by empid", notes = "Fetch Complaint Dashboard data by empid", response = UserTaskDTO.class)
    @Path("/complaint-dashboard")
    List<UserTaskDTO> findComplaintTaskForEmployee(TaskSearchRequest taskSearchRequest);

	WorkflowAction getCommentAndDecisionByAppId(Long applicationId, Long orgId);

	WorkflowRequest findWorkflowActionCommentAndDecisionByAppId(Long applicationId, Long orgId);

	WorkflowRequest findWorkflowActionRequest(Long applicationId, Long orgId);
    
    
}
