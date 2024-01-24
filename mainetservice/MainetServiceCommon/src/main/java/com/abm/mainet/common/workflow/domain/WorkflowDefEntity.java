package com.abm.mainet.common.workflow.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Rahul.Yadav
 * @since 10 Mar 2016
 */
@Entity
@Table(name = "TB_WORKFLOW_DEFINATION")
public class WorkflowDefEntity implements Serializable {

    private static final long serialVersionUID = 1794225612813988237L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "WD_ID", precision = 0, scale = 0, nullable = false)
    private Long wdId;

    @Column(name = "WD_NAME", length = 100, nullable = true)
    private String wdName;

    @Column(name = "WD_NAME_REGIONAL", length = 100, nullable = true)
    private String wdNameRegional;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WD_ORGID", nullable = false, updatable = false)
    private Organisation orgId;

    @Column(name = "WD_SERVICE_ID", precision = 12, scale = 0, nullable = false)
    private Long wdServiceId;

    @Column(name = "WD_MODE", length = 1, nullable = false)
    private String wdMode;

    @Column(name = "WD_LANG", precision = 2, scale = 0, nullable = false)
    private Long wdLang;

    @Column(name = "WD_STATUS", length = 1, nullable = false)
    private String wdStatus;

    @Column(name = "WD_ISACTIVE", length = 1, nullable = false)
    private String wdIsactive;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmodDate;

    @Column(name = "CREATED_BY", precision = 12, scale = 0, nullable = false)
    private Long createdBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = false)
    private Employee updatedBy;

    @Column(name = "ISDELETED", length = 1, nullable = false)
    private String isDeleted;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "WD_DESC", length = 500, nullable = true)
    private String wdDesc;

    @Column(name = "WARD_LEVEL_1", length = 12, nullable = true)
    private Long dwzId1;

    @Column(name = "WARD_LEVEL_2", length = 12, nullable = true)
    private Long dwzId2;

    @Column(name = "WARD_LEVEL_3", length = 12, nullable = true)
    private Long dwzId3;

    @Column(name = "WARD_LEVEL_4", length = 12, nullable = true)
    private Long dwzId4;

    @Column(name = "WARD_LEVEL_5", length = 12, nullable = true)
    private Long dwzId5;

    @Column(name = "COMPLAINT_TYPE", length = 12, nullable = true)
    private Long complaintType;

    @Column(name = "COMPLAIN_SUB_TYPE", length = 12, nullable = true)
    private Long complaintSubType;

    @Column(name = "WARD_ZONE_TYPE", length = 1, nullable = false)
    private String wardZoneType;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WD_DEPT_ID", nullable = false, updatable = false)
    // comments : Department Id
    private Department wdDeptId;

    @OneToMany(mappedBy = "workflowDefEntity", targetEntity = WorkflowEventEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<WorkflowEventEntity> workflowEventEntity;

    /**
     * @return the wdId
     */
    public Long getWdId() {
        return wdId;
    }

    /**
     * @param wdId the wdId to set
     */
    public void setWdId(final Long wdId) {
        this.wdId = wdId;
    }

    /**
     * @return the wdName
     */
    public String getWdName() {
        return wdName;
    }

    /**
     * @param wdName the wdName to set
     */
    public void setWdName(final String wdName) {
        this.wdName = wdName;
    }

    /**
     * @return the wdNameRegional
     */
    public String getWdNameRegional() {
        return wdNameRegional;
    }

    /**
     * @param wdNameRegional the wdNameRegional to set
     */
    public void setWdNameRegional(final String wdNameRegional) {
        this.wdNameRegional = wdNameRegional;
    }

    /**
     * @return the orgId
     */
    public Organisation getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the wdServiceId
     */
    public Long getWdServiceId() {
        return wdServiceId;
    }

    /**
     * @param wdServiceId the wdServiceId to set
     */
    public void setWdServiceId(final Long wdServiceId) {
        this.wdServiceId = wdServiceId;
    }

    /**
     * @return the wdMode
     */
    public String getWdMode() {
        return wdMode;
    }

    /**
     * @param wdMode the wdMode to set
     */
    public void setWdMode(final String wdMode) {
        this.wdMode = wdMode;
    }

    /**
     * @return the wdLang
     */
    public Long getWdLang() {
        return wdLang;
    }

    /**
     * @param wdLang the wdLang to set
     */
    public void setWdLang(final Long wdLang) {
        this.wdLang = wdLang;
    }

    /**
     * @return the wdStatus
     */
    public String getWdStatus() {
        return wdStatus;
    }

    /**
     * @param wdStatus the wdStatus to set
     */
    public void setWdStatus(final String wdStatus) {
        this.wdStatus = wdStatus;
    }

    /**
     * @return the wdIsactive
     */
    public String getWdIsactive() {
        return wdIsactive;
    }

    /**
     * @param wdIsactive the wdIsactive to set
     */
    public void setWdIsactive(final String wdIsactive) {
        this.wdIsactive = wdIsactive;
    }

    /**
     * @return the lmodDate
     */
    public Date getLmodDate() {
        return lmodDate;
    }

    /**
     * @param lmodDate the lmodDate to set
     */
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    /**
     * @return the createdBy
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the updatedBy
     */
    public Employee getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the isDeleted
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * @return the lgIpMac
     */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    /**
     * @return the wdDesc
     */
    public String getWdDesc() {
        return wdDesc;
    }

    /**
     * @param wdDesc the wdDesc to set
     */
    public void setWdDesc(final String wdDesc) {
        this.wdDesc = wdDesc;
    }

    /**
     * @return the wdDeptId
     */
    public Department getWdDeptId() {
        return wdDeptId;
    }

    /**
     * @param wdDeptId the wdDeptId to set
     */
    public void setWdDeptId(final Department wdDeptId) {
        this.wdDeptId = wdDeptId;
    }

    /**
     * @return the workflowEventEntityolds
     */
    public List<WorkflowEventEntity> getWorkflowEventEntities() {
        return workflowEventEntity;
    }

    /**
     * @param workflowEventEntityolds the workflowEventEntityolds to set
     */
    public void setWorkflowEventEntities(
            final List<WorkflowEventEntity> workflowEventEntity) {
        this.workflowEventEntity = workflowEventEntity;
    }

    /**
     * @return the workflowEventEntity
     */
    public List<WorkflowEventEntity> getWorkflowEventEntity() {
        return workflowEventEntity;
    }

    /**
     * @param workflowEventEntity the workflowEventEntity to set
     */
    public void setWorkflowEventEntity(final List<WorkflowEventEntity> workflowEventEntity) {
        this.workflowEventEntity = workflowEventEntity;
    }

    public Long getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(final Long complaintType) {
        this.complaintType = complaintType;
    }

    public Long getComplaintSubType() {
        return complaintSubType;
    }

    public void setComplaintSubType(final Long complaintSubType) {
        this.complaintSubType = complaintSubType;
    }

    public Long getDwzId1() {
        return dwzId1;
    }

    public void setDwzId1(final Long dwzId1) {
        this.dwzId1 = dwzId1;
    }

    public Long getDwzId2() {
        return dwzId2;
    }

    public void setDwzId2(final Long dwzId2) {
        this.dwzId2 = dwzId2;
    }

    public Long getDwzId3() {
        return dwzId3;
    }

    public void setDwzId3(final Long dwzId3) {
        this.dwzId3 = dwzId3;
    }

    public Long getDwzId4() {
        return dwzId4;
    }

    public void setDwzId4(final Long dwzId4) {
        this.dwzId4 = dwzId4;
    }

    public Long getDwzId5() {
        return dwzId5;
    }

    public void setDwzId5(final Long dwzId5) {
        this.dwzId5 = dwzId5;
    }

    public String getWardZoneType() {
        return wardZoneType;
    }

    public void setWardZoneType(final String wardZoneType) {
        this.wardZoneType = wardZoneType;
    }

    public String[] getPkValues() {
        return new String[] { "COM", "TB_WORKFLOW_DEFINATION", "WD_ID" };
    }

}