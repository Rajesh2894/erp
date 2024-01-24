package com.abm.mainet.common.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.ActionDTOWithDoc;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.integration.ws.JersyCall;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author hiren.poriya
 * @Since 08-Jan-2018
 */

@Service
public class WorkFlowActionServiceImpl implements IWorkflowActionService {
    /**
     * this service is used to get citizen application's action history.
     * @param applicationId
     * @return List of Actions of application
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<ActionDTOWithDoc> getWorkflowActionLogByApplicationId(Long applicationId) {
        List<LinkedHashMap<Long, Object>> requestList = (List<LinkedHashMap<Long, Object>>) JersyCall.callRestTemplateClient(null,
                ServiceEndpoints.WORFLOW_ACTION_LOG_URL + MainetConstants.WINDOWS_SLASH + applicationId);
        List<ActionDTOWithDoc> actionLog = new ArrayList<>();
        requestList.forEach(obj -> {
            String d = new JSONObject(obj).toString();
            try {
                ActionDTOWithDoc action = new ObjectMapper().readValue(d, ActionDTOWithDoc.class);
                actionLog.add(action);
            } catch (Exception ex) {
                throw new FrameworkException("Exception while getting applicaiton history : " + ex);
            }
        });
        return actionLog;
    }
	
	@Override
    @SuppressWarnings("unchecked")
    public List<ActionDTOWithDoc> getWorkflowActionLogByApplicationId(String applicationId, int langId) {
        List<LinkedHashMap<Long, Object>> requestList = (List<LinkedHashMap<Long, Object>>) JersyCall.callRestTemplateClient(null,
                ServiceEndpoints.WORFLOW_ACTION_LOG_URL + MainetConstants.WINDOWS_SLASH + applicationId + "/lang/" + langId);
        List<ActionDTOWithDoc> actionLog = new ArrayList<>();
        requestList.forEach(obj -> {
            String d = new JSONObject(obj).toString();
            try {
                ActionDTOWithDoc action = new ObjectMapper().readValue(d, ActionDTOWithDoc.class);
                actionLog.add(action);
            } catch (Exception ex) {
                throw new FrameworkException("Exception while getting applicaiton history : " + ex);
            }
        });
        return actionLog;
    }
	
	/*getting actionHistory by referenceId only*/
	@Override
    @SuppressWarnings("unchecked")
    public List<ActionDTOWithDoc> getWorkflowActionLogByReferenceId(String referenceId, int langId) {
        List<LinkedHashMap<Long, Object>> requestList = (List<LinkedHashMap<Long, Object>>) JersyCall.callRestTemplateClient(null,
                ServiceEndpoints.WORFLOW_ACTION_LOG_BY_REFERENCEID + MainetConstants.WINDOWS_SLASH + referenceId + "/lang/" + langId);
        List<ActionDTOWithDoc> actionLog = new ArrayList<>();
        requestList.forEach(obj -> {
            String d = new JSONObject(obj).toString();
            try {
                ActionDTOWithDoc action = new ObjectMapper().readValue(d, ActionDTOWithDoc.class);
                actionLog.add(action);
            } catch (Exception ex) {
                throw new FrameworkException("Exception while getting applicaiton history : " + ex);
            }
        });
        return actionLog;
    }
}
