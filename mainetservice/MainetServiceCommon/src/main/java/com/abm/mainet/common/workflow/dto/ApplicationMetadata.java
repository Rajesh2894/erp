package com.abm.mainet.common.workflow.dto;

import java.io.Serializable;

public class ApplicationMetadata implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long applicationId;
    private Long orgId;
    private Long workflowId;
    private String applicationExpiryDuration;
    private String paymentMode;
    private Boolean isAutoEscalate;
    private Boolean isCheckListApplicable;
    private Boolean isFreeService;
    private Boolean isScrutinyApplicable;
    private Boolean isLoiApplicable;
    private Boolean isLoiGenerated;
    private Boolean isApprovalLetterGeneration;
    private String referenceId;
    private String status;
    private String lastDecision;
    private String appTypeFlag;
    private String isCallCenterApplicable;//Y->call center need//N->call center task not required
    /*
     * Wanted to be executed Approval task in Objection process
     * It should be always Y from service side 
     */
    private String isApprReqInObjection="N";
    
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

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
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
