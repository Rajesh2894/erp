package com.abm.mainet.mrm.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class AppointmentDTO implements Serializable {

    private static final long serialVersionUID = 7419954719623879993L;

    private Long appointmentId;

    @JsonBackReference
    private MarriageDTO marId;

    private Date appointmentDate;

    private String appointmentTime;

    private Long volume;

    private Long pageNo;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Date appointmentDateSearch;

    private String appointmentIdResc;

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public MarriageDTO getMarId() {
        return marId;
    }

    public void setMarId(MarriageDTO marId) {
        this.marId = marId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Long getPageNo() {
        return pageNo;
    }

    public void setPageNo(Long pageNo) {
        this.pageNo = pageNo;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Date getAppointmentDateSearch() {
        return appointmentDateSearch;
    }

    public void setAppointmentDateSearch(Date appointmentDateSearch) {
        this.appointmentDateSearch = appointmentDateSearch;
    }

    public String getAppointmentIdResc() {
        return appointmentIdResc;
    }

    public void setAppointmentIdResc(String appointmentIdResc) {
        this.appointmentIdResc = appointmentIdResc;
    }

}
