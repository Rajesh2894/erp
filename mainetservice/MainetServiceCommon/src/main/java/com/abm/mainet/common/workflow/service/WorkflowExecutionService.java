package com.abm.mainet.common.workflow.service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.workflow.dto.TaskSearchRequest;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * WorkflowExecutionService is the main entry point to interact with the MAINET_BPM_SERVICE. WorkflowExecutionService Entry Point
 * serves as facade of all the services like initiate, update, abort work-flow.
 * 
 * @author sanket.joshi
 * @see WorkflowProcessParameter
 */
@Service
@SuppressWarnings("unchecked")
public class WorkflowExecutionService implements IWorkflowExecutionService {

    Logger logger = Logger.getLogger(WorkflowExecutionService.class);

    @Override
    public WorkflowTaskActionResponse initiateWorkflow(final WorkflowProcessParameter workflowProcessParameter) throws Exception {
        WorkflowTaskActionResponse response = null;
        HashMap<Long, Object> responseVo = (HashMap<Long, Object>) RestClient.callJbossBPM(workflowProcessParameter,
                ServiceEndpoints.WorkflowExecutionURLS.INITIATE);
        if (responseVo != null) {
            String d = new JSONObject(responseVo).toString();
            response = new ObjectMapper().readValue(d, WorkflowTaskActionResponse.class);
        }
        return response;
    }

    @Override
    public WorkflowTaskActionResponse updateWorkflow(final WorkflowProcessParameter workflowProcessParameter) throws Exception {
        WorkflowTaskActionResponse response = null;
        logger.info("Going call to BPM");
        HashMap<Long, Object> responseVo = (HashMap<Long, Object>) RestClient.callJbossBPM(workflowProcessParameter,
                ServiceEndpoints.WorkflowExecutionURLS.UPDATE);
        logger.info("Ended call to BPM");
        if (responseVo != null) {
        	logger.info("BPM call success");
            String d = new JSONObject(responseVo).toString();
            response = new ObjectMapper().readValue(d, WorkflowTaskActionResponse.class);
        }
        return response;
    }

    @Override
    public Map<String, Object> getTaskData(Long taskId) throws Exception {
        Map<String, Object> taskdata = new LinkedHashMap<>();

        DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
        Map<String, String> requestParam = new HashMap<>();
        requestParam.put("taskId", taskId.toString());
        URI uri = dd.expand(ServiceEndpoints.WorkflowExecutionURLS.TASK_CONTENT, requestParam);

        Object o = RestClient.callJbossBPM(null, uri.toString());
        if (o == null)
            return taskdata;
        if (o instanceof Map<?, ?>) {
            taskdata = (Map<String, Object>) o;
        }
        return taskdata;
    }

    @Override
    public List<UserTaskDTO> getTaskList(TaskSearchRequest requester) throws Exception {
        logger.info("Attempting to fetch tasks assigned to " + requester);
        DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
        Map<String, String> requestParam = new HashMap<>();
        requestParam.put("userId", requester.getEmpId().toString());
        URI url = dd.expand(ServiceEndpoints.WorkflowExecutionURLS.TASK_LIST_USER, requestParam);
        return getTaskList(url.toString(), null);
    }

    @Override
    public List<UserTaskDTO> searchTask(TaskSearchRequest searchRequest) throws Exception {
        logger.info("Attempting to fetch tasks for " + searchRequest);
        return getTaskList(ServiceEndpoints.WorkflowExecutionURLS.TASK_LIST_SEARCH, searchRequest);
    }

    @Override
    public List<UserTaskDTO> getTaskList(String uuid) throws Exception {
        DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
        Map<String, String> requestParam = new HashMap<>();
        requestParam.put("uuid", uuid);
        URI url = dd.expand(ServiceEndpoints.WorkflowExecutionURLS.TASK_LIST_UUID, requestParam);
        return getTaskList(url.toString(), null);
    }

    @Override
    public void signalWorkFlow(WorkflowProcessParameter workflowProcessParameter) throws Exception {
        RestClient.callJbossBPM(workflowProcessParameter, ServiceEndpoints.WorkflowExecutionURLS.SIGNAL);
    }

    /**
     * This method will do REST call with MainetBpmService to get task list.
     * 
     * @param url
     * @param data
     * @return
     * @throws Exception
     */
    private List<UserTaskDTO> getTaskList(String url, Object data) throws Exception {
        List<UserTaskDTO> taskList = new ArrayList<>();
        List<Map<?, ?>> response = (List<Map<?, ?>>) RestClient.callJbossBPM(data, url);
        response.stream().forEach(r -> {
            String d = new JSONObject(r).toString();
            try {
                UserTaskDTO task = new ObjectMapper().readValue(d, UserTaskDTO.class);
                // D#126512 column task status display in Regional
                if(MainetConstants.WorkFlow.Status.PENDING.equalsIgnoreCase(task.getTaskStatus())) {
                	task.setTaskStatusDesc(ApplicationSession.getInstance().getMessage("dashboard.status.pendingStatus"));
                }else if(MainetConstants.WorkFlow.Status.EXITED.equalsIgnoreCase(task.getTaskStatus())) {
                	task.setTaskStatusDesc(ApplicationSession.getInstance().getMessage("dashboard.status.exitedStatus"));
                }else {
                	task.setTaskStatusDesc(ApplicationSession.getInstance().getMessage("dashboard.status.completedStatus"));
                }
                
               /* task.setTaskStatusDesc(task.getTaskStatus().equalsIgnoreCase(MainetConstants.WorkFlow.Status.PENDING)
                        ? ApplicationSession.getInstance().getMessage("dashboard.status.pendingStatus")
                        : ApplicationSession.getInstance().getMessage("dashboard.status.completedStatus"));*/
                taskList.add(task);
            } catch (IOException e) {
                logger.warn("Unable to mapp task object", e);
            }
        });
        return taskList;
    }
}
