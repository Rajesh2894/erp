package com.abm.mainet.bpm.common.dto;

import java.io.Serializable;

/**
 * Task assignment request parameter DTO.
 * 
 * @author sanket.joshi
 *
 */
public class TaskAssignmentRequest implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long workflowTypeId;
    private Integer currentEscalationIndex;
    private Integer currentEscalationGroupIndex;
	private Long serviceEventId;
    private String serviceEventName;
    private Long orgId;
    private Long serviceId;
    private Long compTypeId;
    private Long deptId;
    private Long codIdOperLevel1;
    private Long codIdOperLevel2;
    private Long codIdOperLevel3;
    private Long codIdOperLevel4;
    private Long codIdOperLevel5;
    private Long applicationId;
    private String referenceId;

    public Long getWorkflowTypeId() {
        return workflowTypeId;
    }

    public void setWorkflowTypeId(Long workflowTypeId) {
        this.workflowTypeId = workflowTypeId;
    }

    public Integer getCurrentEscalationIndex() {
        return currentEscalationIndex;
    }

    public void setCurrentEscalationIndex(Integer currentEscalationIndex) {
        this.currentEscalationIndex = currentEscalationIndex;
    }
    public Integer getCurrentEscalationGroupIndex() {
		return currentEscalationGroupIndex;
	}

	public void setCurrentEscalationGroupIndex(Integer currentEscalationGroupIndex) {
		this.currentEscalationGroupIndex = currentEscalationGroupIndex;
	}

	public Long getServiceEventId() {
        return serviceEventId;
    }

    public void setServiceEventId(Long serviceEventId) {
        this.serviceEventId = serviceEventId;
    }

    public String getServiceEventName() {
        return serviceEventName;
    }

    public void setServiceEventName(String serviceEventName) {
        this.serviceEventName = serviceEventName;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getCompTypeId() {
        return compTypeId;
    }

    public void setCompTypeId(Long compTypeId) {
        this.compTypeId = compTypeId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
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

    @Override
    public String toString() {
        return "TaskAssignmentRequestDTO [workflowTypeId=" + workflowTypeId + ", currentEscalationIndex=" + currentEscalationIndex
                + ", serviceEventId=" + serviceEventId + ", serviceEventName=" + serviceEventName + ", orgId=" + orgId
                + ", serviceId=" + serviceId + ", compTypeId=" + compTypeId + ", deptId=" + deptId + ", codIdOperLevel1="
                + codIdOperLevel1 + ", codIdOperLevel2=" + codIdOperLevel2 + ", codIdOperLevel3=" + codIdOperLevel3
                + ", codIdOperLevel4=" + codIdOperLevel4 + ", codIdOperLevel5=" + codIdOperLevel5 + "]";
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

}
