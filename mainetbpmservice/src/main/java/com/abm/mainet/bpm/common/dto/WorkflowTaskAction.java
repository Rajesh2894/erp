package com.abm.mainet.bpm.common.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class WorkflowTaskAction implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long applicationId;
    private String referenceId;
    private String decision;
    private String comments;
    private Long orgId;
    private Long empId;
    private Long empType;
    private Long taskId;
    private Long eventId;
    private String taskName;
    private Date dateOfAction;
    private Date createdDate;
    private Long createdBy;
    private Date modifiedDate;
    private Long modifiedBy;
    private String empName;
    private String empEmail;
    private String empGroupDescEng;
    private String empGroupDescReg;
    private String forwardDepartment;
    private String forwardComplaintType;
    private String forwardToEmployee;
    private String forwardToEmployeeType;
    private Integer sendBackToGroup;
    private Integer sendBackToLevel;
    private String sendBackToEmployee;
    private Long codIdOperLevel1;
    private Long codIdOperLevel2;
    private Long codIdOperLevel3;
    private Long codIdOperLevel4;
    private Long codIdOperLevel5;
    private Boolean isLoiGenerated;
    private Boolean isPaymentGenerated;
    private Boolean isFinalApproval;
    private Boolean isObjectionAppealApplicable;
    private Double loiAmount;
    private String paymentMode;
    private Long taskSlaDurationInMS;
    private Long applicationSlaDurationInMS;
    private String signalExpiryDelay;
    private String signalExpiryDelayUnit;
    private List<Long> attachementId;
    private List<LoiDetail> loiDetails;
    private Long workflowId;
    private Long serviceId;
    /**
     * If decision will be favor of citizen 
     * It should be C other than null
     */
    private String decisionFavorFlag;
    /*D#127199*/
    private String deptName;
    /*D#127199*/
    /*private String deptNameMar;*/

    private String empLoginName;
    
    private String applicantName;
    
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(final String decision) {
        this.decision = decision;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(final String comments) {
        this.comments = comments;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(final Long empId) {
        this.empId = empId;
    }

    public Long getEmpType() {
        return empType;
    }

    public void setEmpType(final Long empType) {
        this.empType = empType;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(final Long taskId) {
        this.taskId = taskId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(final String taskName) {
        this.taskName = taskName;
    }

    public Date getDateOfAction() {
        return dateOfAction;
    }

    public void setDateOfAction(final Date dateOfAction) {
        this.dateOfAction = dateOfAction;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(final Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(final Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(final String empName) {
        this.empName = empName;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(final String empEmail) {
        this.empEmail = empEmail;
    }

    public String getEmpGroupDescEng() {
        return empGroupDescEng;
    }

    public void setEmpGroupDescEng(final String empGroupDescEng) {
        this.empGroupDescEng = empGroupDescEng;
    }

    public String getEmpGroupDescReg() {
        return empGroupDescReg;
    }

    public void setEmpGroupDescReg(final String empGroupDescReg) {
        this.empGroupDescReg = empGroupDescReg;
    }

    public String getForwardDepartment() {
        return forwardDepartment;
    }

    public void setForwardDepartment(String forwardDepartment) {
        this.forwardDepartment = forwardDepartment;
    }

    public String getForwardComplaintType() {
        return forwardComplaintType;
    }

    public void setForwardComplaintType(String forwardComplaintType) {
        this.forwardComplaintType = forwardComplaintType;
    }

    public String getForwardToEmployee() {
        return forwardToEmployee;
    }

    public void setForwardToEmployee(String forwardToEmployee) {
        this.forwardToEmployee = forwardToEmployee;
    }

    public String getForwardToEmployeeType() {
        return forwardToEmployeeType;
    }

    public void setForwardToEmployeeType(String forwardToEmployeeType) {
        this.forwardToEmployeeType = forwardToEmployeeType;
    }

    public Integer getSendBackToGroup() {
        return sendBackToGroup;
    }

    public void setSendBackToGroup(Integer sendBackToGroup) {
        this.sendBackToGroup = sendBackToGroup;
    }

    public Integer getSendBackToLevel() {
        return sendBackToLevel;
    }

    public void setSendBackToLevel(Integer sendBackToLevel) {
        this.sendBackToLevel = sendBackToLevel;
    }

    public String getSendBackToEmployee() {
        return sendBackToEmployee;
    }

    public void setSendBackToEmployee(String sendBackToEmployee) {
        this.sendBackToEmployee = sendBackToEmployee;
    }

    public Long getCodIdOperLevel1() {
        return codIdOperLevel1;
    }

    public void setCodIdOperLevel1(Long codIdOperLevel1) {
        this.codIdOperLevel1 = codIdOperLevel1;
    }

    public Long getCodIdOperLevel2() {
        return codIdOperLevel2;
    }

    public void setCodIdOperLevel2(Long codIdOperLevel2) {
        this.codIdOperLevel2 = codIdOperLevel2;
    }

    public Long getCodIdOperLevel3() {
        return codIdOperLevel3;
    }

    public void setCodIdOperLevel3(Long codIdOperLevel3) {
        this.codIdOperLevel3 = codIdOperLevel3;
    }

    public Long getCodIdOperLevel4() {
        return codIdOperLevel4;
    }

    public void setCodIdOperLevel4(Long codIdOperLevel4) {
        this.codIdOperLevel4 = codIdOperLevel4;
    }

    public Long getCodIdOperLevel5() {
        return codIdOperLevel5;
    }

    public void setCodIdOperLevel5(Long codIdOperLevel5) {
        this.codIdOperLevel5 = codIdOperLevel5;
    }

    public Boolean getIsLoiGenerated() {
        return isLoiGenerated;
    }

    public void setIsLoiGenerated(Boolean isLoiGenerated) {
        this.isLoiGenerated = isLoiGenerated;
    }

    public Boolean getIsPaymentGenerated() {
        return isPaymentGenerated;
    }

    public void setIsPaymentGenerated(Boolean isPaymentGenerated) {
        this.isPaymentGenerated = isPaymentGenerated;
    }

    public Boolean getIsFinalApproval() {
        return isFinalApproval;
    }

    public void setIsFinalApproval(Boolean isFinalApproval) {
        this.isFinalApproval = isFinalApproval;
    }

    public Boolean getIsObjectionAppealApplicable() {
        return isObjectionAppealApplicable;
    }

    public void setIsObjectionAppealApplicable(Boolean isObjectionAppealApplicable) {
        this.isObjectionAppealApplicable = isObjectionAppealApplicable;
    }

    public Double getLoiAmount() {
        return loiAmount;
    }

    public void setLoiAmount(Double loiAmount) {
        this.loiAmount = loiAmount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Long getTaskSlaDurationInMS() {
        return taskSlaDurationInMS;
    }

    public void setTaskSlaDurationInMS(Long taskSlaDurationInMS) {
        this.taskSlaDurationInMS = taskSlaDurationInMS;
    }

    public Long getApplicationSlaDurationInMS() {
        return applicationSlaDurationInMS;
    }

    public void setApplicationSlaDurationInMS(Long applicationSlaDurationInMS) {
        this.applicationSlaDurationInMS = applicationSlaDurationInMS;
    }

    public String getSignalExpiryDelay() {
        return signalExpiryDelay;
    }

    public void setSignalExpiryDelay(String signalExpiryDelay) {
        this.signalExpiryDelay = signalExpiryDelay;
    }

    public String getSignalExpiryDelayUnit() {
        return signalExpiryDelayUnit;
    }

    public void setSignalExpiryDelayUnit(String signalExpiryDelayUnit) {
        this.signalExpiryDelayUnit = signalExpiryDelayUnit;
    }

    public List<Long> getAttachementId() {
        return attachementId;
    }

    public void setAttachementId(List<Long> attachementId) {
        this.attachementId = attachementId;
    }

    public List<LoiDetail> getLoiDetails() {
        return loiDetails;
    }

    public void setLoiDetails(List<LoiDetail> loiDetails) {
        this.loiDetails = loiDetails;
    }

	public Long getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getDecisionFavorFlag() {
		return decisionFavorFlag;
	}

	public void setDecisionFavorFlag(String decisionFavorFlag) {
		this.decisionFavorFlag = decisionFavorFlag;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getEmpLoginName() {
		return empLoginName;
	}

	public void setEmpLoginName(String empLoginName) {
		this.empLoginName = empLoginName;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	/*public String getDeptNameMar() {
		return deptNameMar;
	}

	public void setDeptNameMar(String deptNameMar) {
		this.deptNameMar = deptNameMar;
	}*/
	
	

}