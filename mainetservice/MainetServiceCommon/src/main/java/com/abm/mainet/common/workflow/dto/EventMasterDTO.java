package com.abm.mainet.common.workflow.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.UserSession;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Vivek.Kumar
 * @since 23 November 2015
 */
public class EventMasterDTO implements Serializable {

    private static final long serialVersionUID = -5484379732725314795L;

    private long eventId;
    private String eventDesc;
    private String eventName;
    private String eventNameReg;
    private String serviceUrl;
    private Long deptId;
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
    private List<ServicesEventDTO> serviceEventDTOList;

    public Long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(final Long uniqueId) {

        this.uniqueId = uniqueId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(final long eventId) {
        this.eventId = eventId;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(final String eventDesc) {
        this.eventDesc = eventDesc;
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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(final Long deptId) {
        this.deptId = deptId;
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

    public String getEventName() {
        return eventName;
    }

    public void setEventName(final String eventName) {
        this.eventName = eventName;
    }

    public String getServiceUrl() {

        return serviceUrl;
    }

    public void setServiceUrl(final String serviceUrl) {
        final Long uqNumber = UserSession.getCurrent().getEmployee().getEmpId() + new Date().getTime();
        setUniqueId(uqNumber);
        this.serviceUrl = serviceUrl;
    }

    public List<ServicesEventDTO> getServiceEventDTOList() {
        return serviceEventDTOList;
    }

    public void setServiceEventDTOList(final List<ServicesEventDTO> serviceEventDTOList) {
        this.serviceEventDTOList = serviceEventDTOList;
    }

    /**
     * @return the eventNameReg
     */
    public String getEventNameReg() {
        return eventNameReg;
    }

    /**
     * @param eventNameReg the eventNameReg to set
     */
    public void setEventNameReg(final String eventNameReg) {
        this.eventNameReg = eventNameReg;
    }

}