package com.abm.mainet.common.workflow.dto;

import java.io.Serializable;
import java.util.Date;

public class BpmBrmDeploymentMasterDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    private String groupId;
    private String artifactId;
    private String version;
    private String processId;
    private String ruleFlowGroup;
    private String status;
    private String bpmRuntime;
    private String artifactType;
    private boolean notifyUsers;
    private String notifyToDepartment;
    private Long createdBy;
    private Date createDate;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getRuleFlowGroup() {
        return ruleFlowGroup;
    }

    public void setRuleFlowGroup(String ruleFlowGroup) {
        this.ruleFlowGroup = ruleFlowGroup;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBpmRuntime() {
        return bpmRuntime;
    }

    public void setBpmRuntime(String bpmRuntime) {
        this.bpmRuntime = bpmRuntime;
    }

    public String getArtifactType() {
        return artifactType;
    }

    public void setArtifactType(String artifactType) {
        this.artifactType = artifactType;
    }

    public boolean isNotifyUsers() {
        return notifyUsers;
    }

    public void setNotifyUsers(boolean notifyUsers) {
        this.notifyUsers = notifyUsers;
    }

    public String getNotifyToDepartment() {
        return notifyToDepartment;
    }

    public void setNotifyToDepartment(String notifyToDepartment) {
        this.notifyToDepartment = notifyToDepartment;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

}
