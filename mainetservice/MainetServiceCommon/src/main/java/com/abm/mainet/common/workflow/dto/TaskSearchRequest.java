package com.abm.mainet.common.workflow.dto;

import java.io.Serializable;

/**
 * 
 * @author sanket.joshi
 *
 */
public class TaskSearchRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long empId;
    private Long empltype;
    private Long orgId;
    private int langId;
    private String status;
    private Long eventId;
    private String eventName;
    private Long applicationId;
    private String referenceId;
    private Long workFlowRequestId;
    private String decision;
    //##161047
    private String referenceMode;
    
    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getEmpltype() {
        return empltype;
    }

    public void setEmpltype(Long empltype) {
        this.empltype = empltype;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @Override
    public String toString() {
        return "TaskRequesterDTO [empId=" + empId + ", orgId=" + orgId + ", langId=" + langId + "]";
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public Long getWorkFlowRequestId() {
        return workFlowRequestId;
    }

    public void setWorkFlowRequestId(Long workFlowRequestId) {
        this.workFlowRequestId = workFlowRequestId;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

	public String getReferenceMode() {
		return referenceMode;
	}

	public void setReferenceMode(String referenceMode) {
		this.referenceMode = referenceMode;
	}

}
