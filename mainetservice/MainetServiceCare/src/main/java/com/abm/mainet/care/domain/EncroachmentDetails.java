package com.abm.mainet.care.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_ENCROACH_DET")
public class EncroachmentDetails implements Serializable {

    private static final long serialVersionUID = -5409971975687402402L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ENCROACH_ID")
    private Long encroachmentId;

    /*
     * @Column(name = "LN_INSP_ID", nullable = false) private Long lnInspId;
     */
    @ManyToOne
    @JoinColumn(name = "LN_INSP_ID", nullable = false)
    private LandInspection landInspection;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "ADDRESS", nullable = true)
    private String address;

    @Column(name = "CONTACT_NO", nullable = false)
    private String contactNo;

    @Column(name = "ROAD", nullable = true)
    private String road;

    @Column(name = "LANDMARK", nullable = true)
    private String landmark;

    // Direction Side
    @Column(name = "DIRECTION", length = 30, nullable = false)
    private String direction;

    @Column(name = "ENCR_TYPE", nullable = false)
    private String encrType;// (S)SINGLE or (M)MULTIPLE (in DB S|M)

    @Column(name = "FLAG_STATUS", nullable = false)
    private String flagStatus;// A or I

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY", nullable = true, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    public Long getEncroachmentId() {
        return encroachmentId;
    }

    public void setEncroachmentId(Long encroachmentId) {
        this.encroachmentId = encroachmentId;
    }

    public LandInspection getLandInspection() {
        return landInspection;
    }

    public void setLandInspection(LandInspection landInspection) {
        this.landInspection = landInspection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getEncrType() {
        return encrType;
    }

    public void setEncrType(String encrType) {
        this.encrType = encrType;
    }

    public String getFlagStatus() {
        return flagStatus;
    }

    public void setFlagStatus(String flagStatus) {
        this.flagStatus = flagStatus;
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

    public static String[] getPkValues() {
        return new String[] { "COM", "TB_ENCROACH_DET", "ENCROACH_ID" };
    }

}
