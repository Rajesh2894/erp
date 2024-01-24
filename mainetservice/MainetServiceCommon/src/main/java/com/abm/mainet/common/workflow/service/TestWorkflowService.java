package com.abm.mainet.common.workflow.service;

import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;

import io.swagger.annotations.Api;


@Service
@WebService(endpointInterface = "com.abm.mainet.common.workflow.service.TestWorkflowController")
@Api("Workflow Action Service")
@Path("/workflow")
public class TestWorkflowService {

    @Autowired
    IWorkflowExecutionService workflowExecutionService;
    
   
    @POST
    @Path("/initiate")
    @Produces(MediaType.APPLICATION_JSON)
    public WorkflowTaskActionResponse initiateWorkflow(@RequestBody WorkflowProcessParameter workflowProcessParameter) throws Exception {
	WorkflowTaskActionResponse response = workflowExecutionService.initiateWorkflow(workflowProcessParameter);
        return response;
    }
    

}
