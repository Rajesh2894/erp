package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.Date;

public class ExecutantsDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2325990095245503564L;

    private String mutdId;

    private Long mutId;

    private String address;

    private String fatherName;

    private Long genderId;

    private String gender;

    private String mobileno;

    private String personName;

    private String regtype;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long updatedBy;

    private Date updatedDate;

    public String getMutdId() {
        return mutdId;
    }

    public void setMutdId(String mutdId) {
        this.mutdId = mutdId;
    }

    public Long getMutId() {
        return mutId;
    }

    public void setMutId(Long mutId) {
        this.mutId = mutId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getRegtype() {
        return regtype;
    }

    public void setRegtype(String regtype) {
        this.regtype = regtype;
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

    public Long getGenderId() {
        return genderId;
    }

    public void setGenderId(Long genderId) {
        this.genderId = genderId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "ExecutantsDto [mutdId=" + mutdId + ", mutId=" + mutId + ", address=" + address + ", fatherName=" + fatherName
                + ", genderId=" + genderId + ", gender=" + gender + ", mobileno=" + mobileno + ", personName=" + personName
                + ", regtype=" + regtype + ", orgId=" + orgId + ", createdBy=" + createdBy + ", createdDate=" + createdDate
                + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", updatedBy=" + updatedBy + ", updatedDate="
                + updatedDate + "]";
    }

}
