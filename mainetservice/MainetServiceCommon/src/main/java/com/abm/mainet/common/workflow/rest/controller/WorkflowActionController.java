package com.abm.mainet.common.workflow.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.jws.WebService;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.ws.rs.Path;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.workflow.dto.TaskSearchRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@WebService
@Api(value = "/workflow")
@RequestMapping("/workflow")
public class WorkflowActionController {

    private static final Logger log = LoggerFactory.getLogger(WorkflowActionController.class);

    @Autowired
    private IWorkflowActionService workflowActionService;
    
    /*Get workflowAction data using application_id value*/
    @RequestMapping(value = "/workflowActions/log/applicationId/{applicationId}/lang/{langId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @Path("/workflowActions/log/applicationId/{applicationId}/lang/{langId}")
    @ApiOperation(value = "Get WorkflowAction Log By Application Id", notes = "Get WorkflowAction Log By Application Id", response = WorkflowTaskActionWithDocs.class, responseContainer = "List")
    public List<WorkflowTaskActionWithDocs> getWorkflowActionLogByApplicationId(
            @PathVariable("applicationId") String applicationId, @PathVariable("langId") short langId) {
        log.info("REST call to get WorkflowAction Audit Log By ApplicationId");
        
        List<WorkflowTaskActionWithDocs> workflowTask = new ArrayList<>();
        workflowTask = workflowActionService.getWorkflowActionLogByApplicationId(applicationId, langId);
        if(CollectionUtils.isNotEmpty(workflowTask)) {
        	/*D#127224 - As per suggested by Rajesh Sir-> showing only records in action history whose action has been taken by user*/
            List<WorkflowTaskActionWithDocs> actionHistory = workflowTask.stream()
            		.filter(action -> !(action.getTaskDecision().equals("PENDING"))).collect(Collectors.toList());
            
        	return actionHistory;
        }
        else {
			WorkflowTaskActionWithDocs wfTaskAction = new WorkflowTaskActionWithDocs();
			if (langId == MainetConstants.ENGLISH)
				wfTaskAction.setErrorMsg(ApplicationContextProvider.getApplicationContext().getBean(ApplicationSession.class).getMessage(MainetConstants.WorkFlow.APPLICATIONID_ERROR, 
						MainetConstants.WorkFlow.APPLICATIONID_ERROR, new Locale(MainetConstants.REG_ENG.ENGLISH)));
			else {
				wfTaskAction.setErrorMsg(ApplicationContextProvider.getApplicationContext().getBean(ApplicationSession.class).getMessage(MainetConstants.WorkFlow.APPLICATIONID_ERROR, 
						MainetConstants.WorkFlow.APPLICATIONID_ERROR, new Locale(MainetConstants.REG_ENG.REGIONAL)));
				workflowTask.add(wfTaskAction);
			}
			return workflowTask;
		}
    }
    
    /*Get workflowAction data using reference_id value*/
    @RequestMapping(value = "/workflowActions/log/referenceId/{referenceId}/lang/{langId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @Path("/workflowActions/log/referenceId/{referenceId}/lang/{langId}")
    @ApiOperation(value = "Get WorkflowAction Log By ReferenceId Id", notes = "Get WorkflowAction Log By ReferenceId Id", response = WorkflowTaskActionWithDocs.class, responseContainer = "List")
    public List<WorkflowTaskActionWithDocs> getWorkflowActionLogByReferenceId(
            @PathVariable("referenceId") String referenceId, @PathVariable("langId") short langId) {
        log.info("REST call to get WorkflowAction Audit Log By ReferenceId");
        List<WorkflowTaskActionWithDocs> workflowTask = new ArrayList<>();
        workflowTask = workflowActionService.getWorkflowActionLogByReferenceId(referenceId, langId);
        
        if(CollectionUtils.isNotEmpty(workflowTask)) {
        	/*D#127224 - As per suggested by Rajesh Sir-> showing only records in action history whose action has been taken by user*/
            List<WorkflowTaskActionWithDocs> actionHistory = workflowTask.stream()
            		.filter(action -> !(action.getTaskDecision().equals("PENDING"))).collect(Collectors.toList());
            
        	return actionHistory;
        }
        else {
			WorkflowTaskActionWithDocs wfTaskAction = new WorkflowTaskActionWithDocs();
			if (langId == MainetConstants.ENGLISH)
				wfTaskAction.setErrorMsg(ApplicationContextProvider.getApplicationContext().getBean(ApplicationSession.class).getMessage(MainetConstants.WorkFlow.APPLICATIONID_ERROR, 
						MainetConstants.WorkFlow.REFERENCEID_ERROR, new Locale(MainetConstants.REG_ENG.ENGLISH)));
			else {
				wfTaskAction.setErrorMsg(ApplicationContextProvider.getApplicationContext().getBean(ApplicationSession.class).getMessage(MainetConstants.WorkFlow.APPLICATIONID_ERROR, 
						MainetConstants.WorkFlow.REFERENCEID_ERROR, new Locale(MainetConstants.REG_ENG.REGIONAL)));
				workflowTask.add(wfTaskAction);
			}
			return workflowTask;
		
		}
    }
    
    /*D#138451*/
    @RequestMapping(value = "/workflowActions/log", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @Path("/workflowActions/log")
    @ApiOperation(value = "Get WorkflowAction Log By Request", notes = "Get WorkflowAction Log By Request", response = WorkflowTaskActionWithDocs.class, responseContainer = "List")
    public List<WorkflowTaskActionWithDocs> getWorkflowActionLogByRequest(@RequestBody TaskSearchRequest request) {
        log.info("REST call to get WorkflowAction Audit Log By ReferenceId");
        List<WorkflowTaskActionWithDocs> workflowTask = new ArrayList<>();
        workflowTask = workflowActionService.getWorkflowActionLogByReferenceId(request.getReferenceId(), (short)request.getLangId());
        
        if(CollectionUtils.isNotEmpty(workflowTask)) {
                /*D#127224 - As per suggested by Rajesh Sir-> showing only records in action history whose action has been taken by user*/
            List<WorkflowTaskActionWithDocs> actionHistory = workflowTask.stream()
                        .filter(action -> !(action.getTaskDecision().equals("PENDING"))).collect(Collectors.toList());
            
                return actionHistory;
        }
        else {
                        WorkflowTaskActionWithDocs wfTaskAction = new WorkflowTaskActionWithDocs();
                        if (request.getLangId() == MainetConstants.ENGLISH)
                                wfTaskAction.setErrorMsg(ApplicationContextProvider.getApplicationContext().getBean(ApplicationSession.class).getMessage(MainetConstants.WorkFlow.APPLICATIONID_ERROR, 
                                                MainetConstants.WorkFlow.REFERENCEID_ERROR, new Locale(MainetConstants.REG_ENG.ENGLISH)));
                        else {
                                wfTaskAction.setErrorMsg(ApplicationContextProvider.getApplicationContext().getBean(ApplicationSession.class).getMessage(MainetConstants.WorkFlow.APPLICATIONID_ERROR, 
                                                MainetConstants.WorkFlow.REFERENCEID_ERROR, new Locale(MainetConstants.REG_ENG.REGIONAL)));
                                workflowTask.add(wfTaskAction);
                        }
                        return workflowTask;
                
                }
    }

    @RequestMapping(value = "/workflowActions/pending/{uuid}/lang/{langId}", method = { RequestMethod.POST,
            RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<WorkflowTaskActionWithDocs> getWorkflowPendingActionByUuid(
            @PathVariable("uuid") String uuid, @PathVariable("langId") short langId) {
        log.info("REST call to get WorkflowAction Audit Log By ApplicationId");
        return workflowActionService.getWorkflowPendingActionByUuid(uuid, langId);
    }
}
