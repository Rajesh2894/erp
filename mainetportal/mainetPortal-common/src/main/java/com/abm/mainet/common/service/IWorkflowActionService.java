package com.abm.mainet.common.service;

import java.util.List;

import com.abm.mainet.common.dto.ActionDTOWithDoc;

/**
 * @author hiren.poriya
 * @Since 08-Jan-2018
 */

public interface IWorkflowActionService {
    /**
     * this service is used to get citizen application's action history.
     * @param applicationId
     * @return List of Actions of application
     */
    List<ActionDTOWithDoc> getWorkflowActionLogByApplicationId(String applicationId, int langId);
	List<ActionDTOWithDoc> getWorkflowActionLogByApplicationId(Long applicationId);
	/*getting actionHistory by referenceId only*/
	List<ActionDTOWithDoc> getWorkflowActionLogByReferenceId(String referenceId, int langId);
}
