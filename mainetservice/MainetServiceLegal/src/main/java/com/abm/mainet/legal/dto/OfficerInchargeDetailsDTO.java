package com.abm.mainet.legal.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author pooja.maske
 * @since 17 Aug 2019
 *
 */
public class OfficerInchargeDetailsDTO implements Serializable {

    private static final long serialVersionUID = 8303693365208391393L;

    private Long oicId;

    private Long cseId;

    private String oicName;

    private String oicDesg;

    private String oicDept;

    private String oicAddress;

    private String oicPhoneNo;

    private String oicEmailId;

    private Date oicAppointmentDate;

    private String oicOrderNo;

    private Long orgid;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    public CaseEntryDTO getTbLglCaseMa() {
        return tbLglCaseMa;
    }

    public void setTbLglCaseMa(CaseEntryDTO tbLglCaseMa) {
        this.tbLglCaseMa = tbLglCaseMa;
    }

    private String lgIpMacUpd;

    @JsonIgnore
    private CaseEntryDTO tbLglCaseMa;

    public Long getOicId() {
        return oicId;
    }

    public void setOicId(Long oicId) {
        this.oicId = oicId;
    }

    public Long getCseId() {
        return cseId;
    }

    public void setCseId(Long cseId) {
        this.cseId = cseId;
    }

    public String getOicName() {
        return oicName;
    }

    public void setOicName(String oicName) {
        this.oicName = oicName;
    }

    public String getOicDesg() {
        return oicDesg;
    }

    public void setOicDesg(String oicDesg) {
        this.oicDesg = oicDesg;
    }

    public String getOicPhoneNo() {
        return oicPhoneNo;
    }

    public void setOicPhoneNo(String oicPhoneNo) {
        this.oicPhoneNo = oicPhoneNo;
    }

    public String getOicEmailId() {
        return oicEmailId;
    }

    public void setOicEmailId(String oicEmailId) {
        this.oicEmailId = oicEmailId;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
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

    public String getOicAddress() {
        return oicAddress;
    }

    public void setOicAddress(String oicAddress) {
        this.oicAddress = oicAddress;
    }

    public Date getOicAppointmentDate() {
        return oicAppointmentDate;
    }

    public void setOicAppointmentDate(Date oicAppointmentDate) {
        this.oicAppointmentDate = oicAppointmentDate;
    }

    public String getOicDept() {
        return oicDept;
    }

    public void setOicDept(String oicDept) {
        this.oicDept = oicDept;
    }

    public String getOicOrderNo() {
        return oicOrderNo;
    }

    public void setOicOrderNo(String oicOrderNo) {
        this.oicOrderNo = oicOrderNo;
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
