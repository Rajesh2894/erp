package com.abm.mainet.common.workflow.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.abm.mainet.common.domain.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author umashanker.kanaujiya
 *
 */
public class WorkflowEventDTO implements Serializable {

    private static final long serialVersionUID = 2264692409323306902L;

    private long weId;

    private Long weStepNo;

    private Long weServiceEvent;

    private Long weOrgid;

    private String weGmId;

    private String weIsrequire;

    private Long weDependsOnSteps;

    private Long weCondiatinalFalseNextStep;

    private Long weSla;

    private String weStatus;

    private Date lmodDate;

    private Long createdBy;

    private Date updatedDate;

    private Employee updatedBy;

    private String isDeleted;

    @JsonIgnore
    @Size(max=100)
    private String lgIpMac;

    @JsonIgnore
    @Size(max=100)
    private String lgIpMacUpd;
    private List<Long> evntRolelist;
    private String roleId;

    

    /**
     * @return the roleId
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * @param roleId the roleId to set
     */
    public void setRoleId(final String roleId) {
        this.roleId = roleId;
    }

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

    /**
     * @return the evntRolelist
     */
    public List<Long> getEvntRolelist() {
        return evntRolelist;
    }

    /**
     * @param evntRolelist the evntRolelist to set
     */
    public void setEvntRolelist(final List<Long> evntRolelist) {
        this.evntRolelist = evntRolelist;
    }

}
