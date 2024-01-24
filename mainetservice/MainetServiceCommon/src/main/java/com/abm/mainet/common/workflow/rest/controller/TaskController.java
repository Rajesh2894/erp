package com.abm.mainet.common.workflow.rest.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;

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

import com.abm.mainet.common.workflow.dto.TaskSearchRequest;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.service.ITaskService;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;

@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/workflow")
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @Autowired
    private IWorkflowExecutionService workflowExecutionService;

    @RequestMapping(value = "/tasks", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseBody
    public List<UserTaskDTO> getTasks(@RequestBody final TaskSearchRequest requester) throws Exception {
        return workflowExecutionService.getTaskList(requester);
    }

    @RequestMapping(value = "/tasks/content/{taskId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Map<?, ?> getDataByTaskId(
            @PathVariable("taskId") Long taskId) throws Exception {
        return workflowExecutionService.getTaskData(taskId);
    }

    @RequestMapping(value = "/tasks/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseBody
    public List<UserTaskDTO> getOpenTaskByEmployeeId(@RequestBody final TaskSearchRequest requester) throws Exception {
        return taskService.getTaskList(requester);
    }

}
