package com.abm.mainet.bpm.common.dto;

import java.io.Serializable;

public class ApplicationMetadata implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long processInstanceId;
    private Long applicationId;
    private Long orgId;
    private Long workflowId;
    private Long workflowReqId;
    private String referenceId;
    private String applicationExpiryDuration;
    private String paymentMode;
    private Boolean isAutoEscalate;
    private Boolean isCheckListApplicable;
    private Boolean isFreeService;
    private Boolean isScrutinyApplicable;
    private Boolean isLoiApplicable;
    private Boolean isLoiGenerated;
    private Boolean isApprovalLetterGeneration;
    private Boolean isProcessAlive;
    private String status;
    private String lastDecision;
    private String appTypeFlag;
    private String isCallCenterApplicable;
    /*
     * Wanted to be executed Approval task in Objection process
     * It should be always Y from service side 
     */
    private String isApprReqInObjection;  

    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getOrgId() {
        return orgId;
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

    public Long getWorkflowReqId() {
        return workflowReqId;
    }

    public void setWorkflowReqId(Long workflowReqId) {
        this.workflowReqId = workflowReqId;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getApplicationExpiryDuration() {
        return applicationExpiryDuration;
    }

    public void setApplicationExpiryDuration(String applicationExpiryDuration) {
        this.applicationExpiryDuration = applicationExpiryDuration;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Boolean getIsAutoEscalate() {
        return isAutoEscalate;
    }

    public void setIsAutoEscalate(Boolean isAutoEscalate) {
        this.isAutoEscalate = isAutoEscalate;
    }

    public Boolean getIsCheckListApplicable() {
        return isCheckListApplicable;
    }

    public void setIsCheckListApplicable(Boolean isCheckListApplicable) {
        this.isCheckListApplicable = isCheckListApplicable;
    }

    public Boolean getIsFreeService() {
        return isFreeService;
    }

    public void setIsFreeService(Boolean isFreeService) {
        this.isFreeService = isFreeService;
    }

    public Boolean getIsScrutinyApplicable() {
        return isScrutinyApplicable;
    }

    public void setIsScrutinyApplicable(Boolean isScrutinyApplicable) {
        this.isScrutinyApplicable = isScrutinyApplicable;
    }

    public Boolean getIsLoiApplicable() {
        return isLoiApplicable;
    }

    public void setIsLoiApplicable(Boolean isLoiApplicable) {
        this.isLoiApplicable = isLoiApplicable;
    }

    public Boolean getIsLoiGenerated() {
        return isLoiGenerated;
    }

    public void setIsLoiGenerated(Boolean isLoiGenerated) {
        this.isLoiGenerated = isLoiGenerated;
    }

    public Boolean getIsApprovalLetterGeneration() {
        return isApprovalLetterGeneration;
    }

    public void setIsApprovalLetterGeneration(Boolean isApprovalLetterGeneration) {
        this.isApprovalLetterGeneration = isApprovalLetterGeneration;
    }

    public Boolean getIsProcessAlive() {
        return isProcessAlive;
    }

    public void setIsProcessAlive(Boolean isProcessAlive) {
        this.isProcessAlive = isProcessAlive;
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

	@Override
	public String toString() {
		return "ApplicationMetadata [processInstanceId=" + processInstanceId + ", applicationId=" + applicationId
				+ ", orgId=" + orgId + ", workflowId=" + workflowId + ", workflowReqId=" + workflowReqId
				+ ", referenceId=" + referenceId + ", applicationExpiryDuration=" + applicationExpiryDuration
				+ ", paymentMode=" + paymentMode + ", isAutoEscalate=" + isAutoEscalate + ", isCheckListApplicable="
				+ isCheckListApplicable + ", isFreeService=" + isFreeService + ", isScrutinyApplicable="
				+ isScrutinyApplicable + ", isLoiApplicable=" + isLoiApplicable + ", isLoiGenerated=" + isLoiGenerated
				+ ", isApprovalLetterGeneration=" + isApprovalLetterGeneration + ", isProcessAlive=" + isProcessAlive
				+ ", status=" + status + ", lastDecision=" + lastDecision + ", appTypeFlag=" + appTypeFlag
				+ ", isCallCenterApplicable=" + isCallCenterApplicable + ", isApprReqInObjection="
				+ isApprReqInObjection + "]";
	}

	public String getAppTypeFlag() {
		return appTypeFlag;
	}

	public void setAppTypeFlag(String appTypeFlag) {
		this.appTypeFlag = appTypeFlag;
	}

	public String getIsCallCenterApplicable() {
		return isCallCenterApplicable;
	}

	public void setIsCallCenterApplicable(String isCallCenterApplicable) {
		this.isCallCenterApplicable = isCallCenterApplicable;
	}


	public String getIsApprReqInObjection() {
		return isApprReqInObjection;
	}

	public void setIsApprReqInObjection(String isApprReqInObjection) {
		this.isApprReqInObjection = isApprReqInObjection;
	}
	
	
    
}
