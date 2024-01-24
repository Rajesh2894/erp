package com.abm.mainet.bpm.common.dto;

public class TaskDetail {

    private Long orgId;
    private Long escalationIndex;
    private Long taskSlaDurationInMS;
    private Long applicationSlaDurationInMS;
    private Long serviceEventId;
    private String serviceEventName;
    private String serviceEventNameReg;
    private Long deptId;
    private String deptName;
    private String deptNameReg;
    private Long serviceId;
    private String serviceName;
    private String serviceNameReg;
    private Integer checkerLevel;
    private Integer checkerGroup;
    private Integer approverCount;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getEscalationIndex() {
        return escalationIndex;
    }

    public void setEscalationIndex(Long escalationIndex) {
        this.escalationIndex = escalationIndex;
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

    public String getServiceEventNameReg() {
        return serviceEventNameReg;
    }

    public void setServiceEventNameReg(String serviceEventNameReg) {
        this.serviceEventNameReg = serviceEventNameReg;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptNameReg() {
        return deptNameReg;
    }

    public void setDeptNameReg(String deptNameReg) {
        this.deptNameReg = deptNameReg;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceNameReg() {
        return serviceNameReg;
    }

    public void setServiceNameReg(String serviceNameReg) {
        this.serviceNameReg = serviceNameReg;
    }

    public Integer getCheckerLevel() {
        return checkerLevel;
    }

    public void setCheckerLevel(Integer checkerLevel) {
        this.checkerLevel = checkerLevel;
    }

    public Integer getCheckerGroup() {
        return checkerGroup;
    }

    public void setCheckerGroup(Integer checkerGroup) {
        this.checkerGroup = checkerGroup;
    }

    public Integer getApproverCount() {
        return approverCount;
    }

    public void setApproverCount(Integer approverCount) {
        this.approverCount = approverCount;
    }

    public TaskDetail() {
    }

}
