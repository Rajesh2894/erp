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
@Table(name = "TB_DEMARCAT_DET")
public class DemarcationDetails implements Serializable {

    private static final long serialVersionUID = 2658925372073942065L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "DEMAR_ID")
    private Long demarcationId;

    @ManyToOne
    @JoinColumn(name = "LN_INSP_ID", nullable = false)
    // @Column(name = "LN_INSP_ID", nullable = false)
    private LandInspection landInspection;

    // North Side
    @Column(name = "DEMAR_SIDE", length = 30, nullable = false)
    private String demarSide;

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

    @Column(name = "FLAG_STATUS", nullable = false)
    private String flagStatus;// A or I

    @Column(name = "SAME_AS_ABOVE", nullable = true)
    private String sameAsAbove;// Y OR N

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

    public Long getDemarcationId() {
        return demarcationId;
    }

    public void setDemarcationId(Long demarcationId) {
        this.demarcationId = demarcationId;
    }

    public LandInspection getLandInspection() {
        return landInspection;
    }

    public void setLandInspection(LandInspection landInspection) {
        this.landInspection = landInspection;
    }

    public String getDemarSide() {
        return demarSide;
    }

    public void setDemarSide(String demarSide) {
        this.demarSide = demarSide;
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

    public String getFlagStatus() {
        return flagStatus;
    }

    public void setFlagStatus(String flagStatus) {
        this.flagStatus = flagStatus;
    }

    public String getSameAsAbove() {
        return sameAsAbove;
    }

    public void setSameAsAbove(String sameAsAbove) {
        this.sameAsAbove = sameAsAbove;
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

    @Override
    public String toString() {
        return "DemarcationDetails [demarcationId=" + demarcationId + ", landInspection=" + landInspection + ", demarSide="
                + demarSide + ", name=" + name + ", address=" + address + ", contactNo=" + contactNo + ", road=" + road
                + ", landmark=" + landmark + ", flagStatus=" + flagStatus + ", sameAsAbove=" + sameAsAbove + ", orgId=" + orgId
                + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate="
                + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + "]";
    }

    public static String[] getPkValues() {
        return new String[] { "COM", "TB_DEMARCAT_DET", "DEMAR_ID" };
    }

}
