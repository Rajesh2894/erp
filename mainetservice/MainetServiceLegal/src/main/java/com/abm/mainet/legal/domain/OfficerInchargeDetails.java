package com.abm.mainet.legal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author pooja.maske
 * @since 17 Aug 2019
 */
@Entity
@Table(name = "TB_LGL_CASE_OIC")
public class OfficerInchargeDetails implements Serializable {

    /**
     * The persistent class for the tb_lgl_case_oic database table.
     */
    private static final long serialVersionUID = 2879764094224018898L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "OIC_ID", unique = true, nullable = false)
    private Long oicId;

    /*
     * @Column(name = "CSE_ID", nullable = false) private Long cseId;
     */

    @Column(name = "OIC_Name", nullable = false)
    private String oicName;

    @Column(name = "OIC_DSG", nullable = false)
    private String oicDesg;

    @Column(name = "OIC_DPDEPTID", nullable = true)
    private String oicDept;

    @Column(name = "OIC_ADDRESS", nullable = true)
    private String oicAddress;

    @Column(name = "OIC_PHONENO", nullable = false)
    private String oicPhoneNo;

    @Column(name = "OIC_EMAILID", nullable = false)
    private String oicEmailId;

    @Temporal(TemporalType.DATE)
    @Column(name = "OIC_APPOINT_DATE", nullable = true)
    private Date oicAppointmentDate;

    @Column(name = "OIC_ORDER_NO", nullable = true)
    private String oicOrderNo;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = false, length = 100)
    private String lgIpMac;

    public CaseEntry getTbLglCaseMa() {
        return tbLglCaseMa;
    }

    public void setTbLglCaseMa(CaseEntry tbLglCaseMa) {
        this.tbLglCaseMa = tbLglCaseMa;
    }

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @ManyToOne
    @JoinColumn(name = "CSE_ID", nullable = false)
    private CaseEntry tbLglCaseMa;

    public Long getOicId() {
        return oicId;
    }

    public void setOicId(Long oicId) {
        this.oicId = oicId;
    }

    /*
     * public Long getCseId() { return cseId; } public void setCseId(Long cseId) { this.cseId = cseId; }
     */

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

    public String getOicAddress() {
        return oicAddress;
    }

    public void setOicAddress(String oicAddress) {
        this.oicAddress = oicAddress;
    }

    public String getOicDept() {
        return oicDept;
    }

    public void setOicDept(String oicDept) {
        this.oicDept = oicDept;
    }

    public Date getOicAppointmentDate() {
        return oicAppointmentDate;
    }

    public void setOicAppointmentDate(Date oicAppointmentDate) {
        this.oicAppointmentDate = oicAppointmentDate;
    }

    public String getOicOrderNo() {
        return oicOrderNo;
    }

    public void setOicOrderNo(String oicOrderNo) {
        this.oicOrderNo = oicOrderNo;
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

    public String[] getPkValues() {
        return new String[] { "LGL", "TB_LGL_CASE_OIC", "OIC_ID" };
    }

}
