package com.abm.mainet.common.workflow.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.workflow.dao.IWorkflowTypeDAO;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTask;
import com.abm.mainet.common.workflow.repository.WorkFlowTypeRepository;

@Service
public class WorkflowTaskServiceImpl implements IWorkflowTaskService {

    @Autowired
    private IWorkflowRequestService iWorkflowRequestService;
    
    @Autowired    
    private IWorkflowTypeDAO iWorkflowTypeDAO;
    
    @Autowired    
    private WorkFlowTypeRepository workFlowTypeRepository;

    @Override
    public List<UserTaskDTO> findByUUId(Long uuid) {

        List<UserTaskDTO> taskList = new ArrayList<>();
        WorkflowRequest wr = iWorkflowRequestService.findByApplicationId(uuid);
        wr.getWorkFlowTaskList().forEach(t -> {
            UserTaskDTO taskObj = new UserTaskDTO();
            BeanUtils.copyProperties(t, taskObj);
            taskList.add(taskObj);
        });
        return taskList;
    }

    @Override
    public UserTaskDTO findByTaskId(Long taskId) {

        WorkflowTask workflowTask = null;
        ResponseEntity<?> responseEntity = null;
        UserTaskDTO dto = null;
        Map<String, String> requestParam = new HashMap<>();
        if (taskId != null) {
            requestParam.put(MainetConstants.TASK_ID, taskId.toString());
        } else {
            requestParam.put(MainetConstants.TASK_ID, null);
        }
        DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
        dd.setParsePath(true);
        URI uri = dd.expand(ServiceEndpoints.WorkflowExecutionURLS.WORKFLOW_TASK_BY_TASK_AND_APP_ID, requestParam);
        try {
            responseEntity = RestClient.callRestTemplateClient(workflowTask, uri.toString());
            HttpStatus statusCode = responseEntity.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                workflowTask = (WorkflowTask) RestClient.castResponse(responseEntity, WorkflowTask.class);
            }
        } catch (Exception ex) {
            throw new FrameworkException(
                    "Exception occured while calling method findByTaskIdAndApplicationId :" + requestParam, ex);
        }
        if (workflowTask != null) {
            dto = new UserTaskDTO();
            BeanUtils.copyProperties(workflowTask, dto);
        }
        return dto;
    }

    @Override
    public UserTaskDTO findByTaskIdAndReferenceId(Long taskId, String referenceId) {
        WorkflowTask workflowTask = null;
        ResponseEntity<?> responseEntity = null;
        UserTaskDTO dto = null;
        Map<String, String> requestParam = new HashMap<>();
        if (taskId != null) {
            requestParam.put(MainetConstants.TASK_ID, taskId.toString());
            requestParam.put(MainetConstants.REFE_ID, referenceId);
        }
        DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
        dd.setParsePath(true);
        URI uri = dd.expand(ServiceEndpoints.WorkflowExecutionURLS.WORKFLOW_TASK_BY_TASK_AND_REF_ID, requestParam);
        try {
            responseEntity = RestClient.callRestTemplateClient(workflowTask, uri.toString());
            HttpStatus statusCode = responseEntity.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                workflowTask = (WorkflowTask) RestClient.castResponse(responseEntity, WorkflowTask.class);
            }
        } catch (Exception ex) {
            throw new FrameworkException(
                    "Exception occured while calling method findByTaskIdAndReferenceId :" + requestParam, ex);
        }
        if (workflowTask != null) {
            dto = new UserTaskDTO();
            BeanUtils.copyProperties(workflowTask, dto);
        }
        return dto;
	}
    
    /*
		 * User Story #134664 Mobile Application "LOI to be Paid" and
		 * "Application Payment Pending"
		 */
    @Override
    public Long getTaskIdByAppIdAndOrgId(Long appId, Long orgId) {
    	return iWorkflowTypeDAO.getTaskIdByAppIdAndOrgId(appId, orgId);
    }
    
    @Override
    public List<String> getTaskByAppId(Long appId, Long orgId) {
    	return workFlowTypeRepository.getTaskByAppId(appId,orgId);
    }
    
}
