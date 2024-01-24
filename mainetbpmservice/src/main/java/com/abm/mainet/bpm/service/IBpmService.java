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

import com.abm.mainet.bpm.common.dto.WorkflowProcessParameter;
import com.abm.mainet.bpm.common.dto.WorkflowTask;
import com.abm.mainet.bpm.common.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.exception.MapFrameworkException;
import com.abm.mainet.common.exception.WorkflowFrameworkException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * BpmService is the main entry point to interact with the process engine and task services. The Task Service Entry Point serves
 * as facade of all the other services, providing a single entry point to access to all the services providers like JBPM, ORACLE,
 * ACTIVITY etc.
 * 
 * This interface is CXF enabled service, which is exposed as REST and SOPA API's
 * 
 * @author Jasvinder.Bhomra
 * @author sanket.joshi
 */
@WebService(endpointInterface = "com.abm.mainet.bpm.service.IBpmService")
@Api("BPM Service")
@Path("/bpm")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IBpmService {

    /**
     * Initiate Process with process Parameter
     * 
     * @param parameter
     * @return
     * @throws FrameworkException
     */
    @POST
    @Path("/process/initiate")
    WorkflowTaskActionResponse initiateProcess(
            @ApiParam(value = "Workflow Process Parameter", required = true) @RequestBody WorkflowProcessParameter parameter)
            throws WorkflowFrameworkException;

    /**
     * This method will trigger the requested Signal to target process instance. This method will retrieve process instance id
     * from workflow request.
     * 
     * @param userId
     * @param orgId
     * @return
     * @throws FrameworkException
     */
    @POST
    @Path("/process/signal")
    WorkflowTaskActionResponse signalProcess(
            @ApiParam(value = "Workflow Process Parameter", required = true) @RequestBody WorkflowProcessParameter parameter)
            throws WorkflowFrameworkException;

    /**
     * 
     * @param parameter
     * @return
     * @throws FrameworkException
     */
    @POST
    @Path("/task/complete")
    WorkflowTaskActionResponse completeTasks(
            @ApiParam(value = "Workflow Process Parameter", required = true) @RequestBody WorkflowProcessParameter parameter)
            throws WorkflowFrameworkException;

    /**
     * Update Tasks with Process Parameter
     * 
     * @param parameter
     * @return
     * @throws FrameworkException
     */
    @POST
    @Path("/task/update")
    WorkflowTaskActionResponse updateTasks(WorkflowProcessParameter parameter) throws WorkflowFrameworkException;

    /**
     * Delete Tasks with Process Parameter and Target Entity Id
     * 
     * @param parameter
     * @return
     * @throws FrameworkException
     */
    @POST
    @Path("/task/delete")
    WorkflowTaskActionResponse deleteTasks(
            @ApiParam(value = "Workflow Process Parameter", required = true) @RequestBody WorkflowProcessParameter parameter)
            throws WorkflowFrameworkException;

    /**
     * Claim Tasks with Process Parameter
     * 
     * @param parameter
     * @return
     * @throws FrameworkException
     */
    @POST
    @Path("/task/claim")
    WorkflowTaskActionResponse claimTasks(
            @ApiParam(value = "Workflow Process Parameter", required = true) @RequestBody WorkflowProcessParameter parameter)
            throws WorkflowFrameworkException;

    /**
     * Delegate to Tasks with Process Parameter and Target Entity Id
     * 
     * @param parameter
     * @return
     * @throws FrameworkException
     */
    @POST
    @Path("/task/delegate")
    WorkflowTaskActionResponse delegateTasks(
            @ApiParam(value = "Workflow Process Parameter", required = true) @RequestBody WorkflowProcessParameter parameter)
            throws WorkflowFrameworkException;

    /**
     * Forward to Tasks with Process Parameter and Target Entity Id
     * 
     * @param parameter
     * @return
     * @throws FrameworkException
     */
    @POST
    @Path("/task/forwardTo")
    WorkflowTaskActionResponse forwardToTasks(
            @ApiParam(value = "Workflow Process Parameter", required = true) @RequestBody WorkflowProcessParameter parameter)
            throws WorkflowFrameworkException;

    /**
     * Release claimed tasks with process parameter
     * 
     * @param parameter
     * @return
     * @throws FrameworkException
     */
    @POST
    @Path("/task/release")
    WorkflowTaskActionResponse releaseTask(
            @ApiParam(value = "Workflow Process Parameter", required = true) @RequestBody WorkflowProcessParameter parameter)
            throws WorkflowFrameworkException;

    /**
     * Suspend tasks with process parameter
     * 
     * @param parameter
     * @return
     * @throws FrameworkException
     */
    @POST
    @Path("/task/suspend")
    WorkflowTaskActionResponse suspendTask(
            @ApiParam(value = "Workflow Process Parameter", required = true) @RequestBody WorkflowProcessParameter parameter)
            throws WorkflowFrameworkException;

    /**
     * 
     * @param parameter
     * @return
     * @throws FrameworkException
     */
    @POST
    @Path("/task/resume")
    WorkflowTaskActionResponse resumeTask(
            @ApiParam(value = "Workflow Process Parameter", required = true) @RequestBody WorkflowProcessParameter parameter)
            throws WorkflowFrameworkException;

    /**
     * 
     * @param parameter
     * @return
     * @throws FrameworkException
     */
    @POST
    @Path("/task/skip")
    WorkflowTaskActionResponse skipTask(
            @ApiParam(value = "Workflow Process Parameter", required = true) @RequestBody WorkflowProcessParameter parameter)
            throws WorkflowFrameworkException;

    /**
     * 
     * @param parameter
     * @return
     * @throws FrameworkException
     */
    @POST
    @Path("/task/stop")
    WorkflowTaskActionResponse stopTask(
            @ApiParam(value = "Workflow Process Parameter", required = true) @RequestBody WorkflowProcessParameter parameter)
            throws WorkflowFrameworkException;

    /**
     * 
     * @param parameter
     * @return
     * @throws FrameworkException
     */
    @POST
    @Path("/task/fail")
    WorkflowTaskActionResponse failTask(
            @ApiParam(value = "Workflow Process Parameter", required = true) @RequestBody WorkflowProcessParameter parameter)
            throws WorkflowFrameworkException;

    /**
     * This method will retrieve task input parameters for target runtime task services.
     * 
     * @param taskId
     * @return
     * @throws FrameworkException
     */
    @POST
    @Path("/task/{taskId}/content")
    @ApiOperation(value = "Get the task content by task id", notes = "Get the task content by task id", response = Map.class)
    Map<String, Object> getTaskContent(@ApiParam(value = "Task Id", required = true) @PathParam("taskId") Long taskId)
            throws MapFrameworkException;

    /**
     * 
     * @param userId
     * @return
     * @throws FrameworkException
     */
    @POST
    @Path("/task/list/user/{userId}")
    @ApiOperation(value = "Get the task by user id", notes = "Get the task by user id", response = Map.class)
    List<WorkflowTask> getTaskListByUser(@ApiParam(value = "User Id", required = true) @PathParam("userId") String userId)
            throws FrameworkException;

    /**
     * 
     * @param uuid
     * @return
     * @throws FrameworkException
     */
    @POST
    @Path("/task/list/uuid/{uuid}")
    @ApiOperation(value = "Get the task by uuid", notes = "Get the task by uuid", response = Map.class)
    List<WorkflowTask> getTaskListByUuid(@ApiParam(value = "UUID", required = true) @PathParam("uuid") String uuid)
            throws FrameworkException;

}
