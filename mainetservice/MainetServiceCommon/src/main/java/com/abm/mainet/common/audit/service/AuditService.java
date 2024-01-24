package com.abm.mainet.common.audit.service;

import java.util.List;

/**
 * @author ritesh.patil
 *
 */
public interface AuditService {

    /**
     * this method will call by AOP pointcut using seprate thread in background
     * @param sourceObject
     * @param targetObject
     */
    void createHistory(Object sourceObject, Object targetObject);

    /**
     * this Method save the history Object & passed the history object
     *
     * @param obj
     */
    void createHistoryForObject(Object obj);

    /**
     * this method is used to save List Of Object for Audit
     * 
     */
    void createHistoryForListObj(List<Object> objectList);
        
	void saveDataForProperty(String appId, Long serviceId, String workflowDecision, Long orgId, Long empId,
			String lgIpMac, long level);

}
