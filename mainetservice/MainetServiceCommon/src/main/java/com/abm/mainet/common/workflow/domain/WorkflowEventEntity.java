package com.abm.mainet.common.workflow.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Rahul.Yadav
 * @since 10 Mar 2016
 */
@Entity
@Table(name = "TB_WORKFLOW_EVENT")
public class WorkflowEventEntity implements Serializable {
    
    private static final long serialVersionUID = 4512394948532133841L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "WE_ID", precision = 12, scale = 0, nullable = false)
    private long weId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "WE_WD_ID", referencedColumnName = "WD_ID")
    private WorkflowDefEntity workflowDefEntity;

    @Column(name = "WE_STEP_NO", precision = 3, scale = 0, nullable = true)
    private Long weStepNo;

    @Column(name = "WE_SERVICE_EVENT", precision = 12, scale = 0, nullable = false)
    private Long weServiceEvent;


    @Column(name = "WE_ORGID", precision = 4, scale = 0, nullable = false)
    private Long weOrgid;

    @Column(name = "WE_GM_ID", length = 20, nullable = true)
    private String weGmId;

    @Column(name = "WE_ISREQUIRE", length = 1, nullable = false)
    private String weIsrequire;

    @Column(name = "WE_DEPENDS_ON_STEPS", precision = 3, scale = 0, nullable = true)
    private Long weDependsOnSteps;

    @Column(name = "WE_CONDIATINAL_FALSE_NEXT_STEP", precision = 3, scale = 0, nullable = true)
    private Long weCondiatinalFalseNextStep;

    @Column(name = "WE_SLA", precision = 3, scale = 0, nullable = true)
    private Long weSla;

    @Column(name = "WE_STATUS", length = 1, nullable = true)
    private String weStatus;

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

    /**
     * @return the weId
     */
    public long getWeId() {
        return weId;
    }

    /**
     * @param weId the weId to set
     */
    public void setWeId(final long weId) {
        this.weId = weId;
    }

    /**
     * @return the workflowDefEntityold
     */
    public WorkflowDefEntity getWorkflowDefEntity() {
        return workflowDefEntity;
    }

    /**
     * @param workflowDefEntityold the workflowDefEntityold to set
     */
    public void setWorkflowDefEntity(final WorkflowDefEntity workflowDefEntity) {
        this.workflowDefEntity = workflowDefEntity;
    }

    /**
     * @return the weStepNo
     */
    public Long getWeStepNo() {
        return weStepNo;
    }

    /**
     * @param weStepNo the weStepNo to set
     */
    public void setWeStepNo(final Long weStepNo) {
        this.weStepNo = weStepNo;
    }

    /**
     * @return the weServiceEvent
     */
    public Long getWeServiceEvent() {
        return weServiceEvent;
    }

    /**
     * @param weServiceEvent the weServiceEvent to set
     */
    public void setWeServiceEvent(final Long weServiceEvent) {
        this.weServiceEvent = weServiceEvent;
    }

    

    /**
     * @return the weOrgid
     */
    public Long getWeOrgid() {
        return weOrgid;
    }

    /**
     * @param weOrgid the weOrgid to set
     */
    public void setWeOrgid(final Long weOrgid) {
        this.weOrgid = weOrgid;
    }

    /**
     * @return the weGmId
     */
    public String getWeGmId() {
        return weGmId;
    }

    /**
     * @param weGmId the weGmId to set
     */
    public void setWeGmId(final String weGmId) {
        this.weGmId = weGmId;
    }

    /**
     * @return the weIsrequire
     */
    public String getWeIsrequire() {
        return weIsrequire;
    }

    /**
     * @param weIsrequire the weIsrequire to set
     */
    public void setWeIsrequire(final String weIsrequire) {
        this.weIsrequire = weIsrequire;
    }

    /**
     * @return the weDependsOnSteps
     */
    public Long getWeDependsOnSteps() {
        return weDependsOnSteps;
    }

    /**
     * @param weDependsOnSteps the weDependsOnSteps to set
     */
    public void setWeDependsOnSteps(final Long weDependsOnSteps) {
        this.weDependsOnSteps = weDependsOnSteps;
    }

    /**
     * @return the weCondiatinalFalseNextStep
     */
    public Long getWeCondiatinalFalseNextStep() {
        return weCondiatinalFalseNextStep;
    }

    /**
     * @param weCondiatinalFalseNextStep the weCondiatinalFalseNextStep to set
     */
    public void setWeCondiatinalFalseNextStep(final Long weCondiatinalFalseNextStep) {
        this.weCondiatinalFalseNextStep = weCondiatinalFalseNextStep;
    }

    /**
     * @return the weSla
     */
    public Long getWeSla() {
        return weSla;
    }

    /**
     * @param weSla the weSla to set
     */
    public void setWeSla(final Long weSla) {
        this.weSla = weSla;
    }

    /**
     * @return the weStatus
     */
    public String getWeStatus() {
        return weStatus;
    }

    /**
     * @param weStatus the weStatus to set
     */
    public void setWeStatus(final String weStatus) {
        this.weStatus = weStatus;
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

    public String[] getPkValues() {
        return new String[] { "COM", "TB_WORKFLOW_EVENT", "WE_ID" };
    }
}