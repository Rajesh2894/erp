package com.abm.mainet.common.workflow.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Vivek.Kumar
 * @since 23 November 2015
 */

public class ServicesEventDTO implements Serializable {

    private static final long serialVersionUID = -4320403411531566711L;
    private long serviceEventId;
    private Long smServiceId;
    private Department deptId;
    private Organisation orgId;
    private int langId;
    private Date createdDate;
    private Employee createdBy;
    private Date updatedDate;
    private Employee updatedBy;
    private String isdeleted;
    @JsonIgnore
    @Size(max=100)
    private String lgIpMac;
    @JsonIgnore
    @Size(max=100)
    private String lgIpMacUpd;
    private Long uniqueId;
    private List<String> eventMapId;
    private List<String> eventMapSelectedId;

    private List<EventMasterDTO> eventMasterList;

    private String smServiceName;

    public long getServiceEventId() {
        return serviceEventId;
    }

    public void setServiceEventId(final long serviceEventId) {
        this.serviceEventId = serviceEventId;
    }

    public Long getSmServiceId() {
        return smServiceId;
    }

    public void setSmServiceId(final Long smServiceId) {
        this.smServiceId = smServiceId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(final String isdeleted) {
        this.isdeleted = isdeleted;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    /**
     * @return the deptId
     */
    public Department getDeptId() {
        return deptId;
    }

    /**
     * @param deptId the deptId to set
     */
    public void setDeptId(final Department deptId) {
        this.deptId = deptId;
    }

    public Organisation getOrgId() {
        return orgId;
    }

    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    public Employee getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Employee createdBy) {
        this.createdBy = createdBy;
    }

    public Employee getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    public List<EventMasterDTO> getEventMasterList() {
        return eventMasterList;
    }

    public void setEventMasterList(final List<EventMasterDTO> eventMasterList) {
        this.eventMasterList = eventMasterList;
    }

    public Long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(final Long uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * @return the eventMapId
     */
    public List<String> getEventMapId() {
        return eventMapId;
    }

    /**
     * @param eventMapId the eventMapId to set
     */
    public void setEventMapId(final List<String> eventMapId) {
        this.eventMapId = eventMapId;
    }

    /**
     * @return the eventMapSelectedId
     */
    public List<String> getEventMapSelectedId() {
        return eventMapSelectedId;
    }

    /**
     * @param eventMapSelectedId the eventMapSelectedId to set
     */
    public void setEventMapSelectedId(final List<String> eventMapSelectedId) {
        this.eventMapSelectedId = eventMapSelectedId;
    }

    public String getSmServiceName() {
        return smServiceName;
    }

    public void setSmServiceName(final String smServiceName) {
        this.smServiceName = smServiceName;
    }

}