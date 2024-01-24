package com.abm.mainet.bpm.common.dto;

import java.io.Serializable;

public class TaskNotificationRequest implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long orgId;
    private Long workflowId;
    private String actorId;
    private String applicationId;
    private String referenceId;
    private String serviceEventUrl;
    private String status;
    private String lastDecision;
    private String comments;
    private Long slaCal; // sending user sla in MS

    public Long getOrgId() {
        return orgId;
    }
 
    public Long getSlaCal() {
		return slaCal;
	}


	public void setSlaCal(Long slaCal) {
		this.slaCal = slaCal;
	}

	public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getServiceEventUrl() {
        return serviceEventUrl;
    }

    public void setServiceEventUrl(String serviceEventUrl) {
        this.serviceEventUrl = serviceEventUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastDecision() {
        return lastDecision;
    }

    public void setLastDecision(String lastDecision) {
        this.lastDecision = lastDecision;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
