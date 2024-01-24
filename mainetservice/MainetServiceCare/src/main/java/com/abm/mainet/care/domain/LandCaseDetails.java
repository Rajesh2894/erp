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
@Table(name = "TB_LNCASE_DET")
public class LandCaseDetails implements Serializable {

    private static final long serialVersionUID = -3046492790091755930L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CASE_ID")
    private Long caseId;

    @ManyToOne
    @JoinColumn(name = "LN_INSP_ID", nullable = false)
    // @Column(name = "LN_INSP_ID", nullable = false)
    // private Long lnInspId;
    private LandInspection landInspection;

    @Column(name = "CRT_NAME", nullable = false)
    private String crtName;

    @Column(name = "CRT_ADD", nullable = true)
    private String crtAdd;

    @Column(name = "CRT_DATE", nullable = true)
    private Date crtDate;

    @Column(name = "LITIGANT", nullable = false)
    private String litigant;

    @Column(name = "RESPONDENT", nullable = false)
    private String respondent;

    @Column(name = "ADVOCATE_NAME", nullable = false)
    private String advocateName;

    @Column(name = "CONTACT_NO", nullable = false)
    private String contactNo;//

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

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public LandInspection getLandInspection() {
        return landInspection;
    }

    public void setLandInspection(LandInspection landInspection) {
        this.landInspection = landInspection;
    }

    public String getCrtName() {
        return crtName;
    }

    public void setCrtName(String crtName) {
        this.crtName = crtName;
    }

    public String getCrtAdd() {
        return crtAdd;
    }

    public void setCrtAdd(String crtAdd) {
        this.crtAdd = crtAdd;
    }

    public Date getCrtDate() {
        return crtDate;
    }

    public void setCrtDate(Date crtDate) {
        this.crtDate = crtDate;
    }

    public String getLitigant() {
        return litigant;
    }

    public void setLitigant(String litigant) {
        this.litigant = litigant;
    }

    public String getRespondent() {
        return respondent;
    }

    public void setRespondent(String respondent) {
        this.respondent = respondent;
    }

    public String getAdvocateName() {
        return advocateName;
    }

    public void setAdvocateName(String advocateName) {
        this.advocateName = advocateName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
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
        return "LandCaseDetails [caseId=" + caseId + ", landInspection=" + landInspection + ", crtName=" + crtName + ", crtAdd="
                + crtAdd + ", crtDate=" + crtDate + ", litigant=" + litigant + ", respondent=" + respondent + ", advocateName="
                + advocateName + ", contactNo=" + contactNo + ", orgId=" + orgId + ", createdBy=" + createdBy + ", createdDate="
                + createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac
                + ", lgIpMacUpd=" + lgIpMacUpd + "]";
    }

    public static String[] getPkValues() {
        return new String[] { "COM", "TB_LNCASE_DET", "CASE_ID" };
    }

}
