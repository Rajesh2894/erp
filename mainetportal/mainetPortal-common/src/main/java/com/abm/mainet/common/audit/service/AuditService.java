package com.abm.mainet.common.audit.service;

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

}
