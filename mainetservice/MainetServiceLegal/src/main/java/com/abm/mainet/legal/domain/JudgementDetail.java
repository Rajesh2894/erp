package com.abm.mainet.legal.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the tb_lgl_casejudgement_detail database table.
 * 
 */
@Entity
@Table(name = "TB_LGL_CASEJUDGEMENT_DETAIL")
public class JudgementDetail implements Serializable {

    private static final long serialVersionUID = -8353703239385418460L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CJD_ID")
    private Long cjdId;

    @Column(name = "CJD_ACTIONTAKEN")
    private String cjdActiontaken;

    @Column(name = "CJD_ATTENDEE")
    private String cjdAttendee;

    @Temporal(TemporalType.DATE)
    @Column(name = "CJD_DATE")
    private Date cjdDate;

    @Column(name = "CJD_DETAILS")
    private String cjdDetails;

    @Column(name = "CJD_TYPE")
    private Long cjdType;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "CSE_ID")
    private Long cseId;

    @Column(name = "NAME_OF_IMPLEMENTER")
    private String implementerName;

    @Column(name = "DESIG_OF_IMPLEMENTER")
    private String desigOfImplementer;

    @Column(name = "IMPLE_STATUS")
    private String impleStatus;

    @Column(name = "PHONE_NO")
    private Long implementerPhoneNo;

    @Column(name = "EMAIL_ID")
    private String implementerEmail;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "IMPLEMENTATION_START_DATE")
    private Date implementationStartDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "IMPLEMENTATION_END_DATE")
    private Date implementationEndDate;

    @OneToMany(mappedBy = "tbCaseJudgeDetail", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<AttendeeDetails> tbAttendeeDetails;

    public JudgementDetail() {
    }

    public Long getCjdId() {
        return this.cjdId;
    }

    public void setCjdId(Long cjdId) {
        this.cjdId = cjdId;
    }

    public String getCjdActiontaken() {
        return this.cjdActiontaken;
    }

    public void setCjdActiontaken(String cjdActiontaken) {
        this.cjdActiontaken = cjdActiontaken;
    }

    public String getCjdAttendee() {
        return this.cjdAttendee;
    }

    public void setCjdAttendee(String cjdAttendee) {
        this.cjdAttendee = cjdAttendee;
    }

    public Date getCjdDate() {
        return this.cjdDate;
    }

    public void setCjdDate(Date cjdDate) {
        this.cjdDate = cjdDate;
    }

    public String getCjdDetails() {
        return this.cjdDetails;
    }

    public void setCjdDetails(String cjdDetails) {
        this.cjdDetails = cjdDetails;
    }

    public Long getCjdType() {
        return this.cjdType;
    }

    public void setCjdType(Long cjdType) {
        this.cjdType = cjdType;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLgIpMac() {
        return this.lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return this.lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getCseId() {
        return cseId;
    }

    public void setCseId(Long cseId) {
        this.cseId = cseId;
    }

    public String getImplementerName() {
        return implementerName;
    }

    public void setImplementerName(String implementerName) {
        this.implementerName = implementerName;
    }

    public Long getImplementerPhoneNo() {
        return implementerPhoneNo;
    }

    public void setImplementerPhoneNo(Long implementerPhoneNo) {
        this.implementerPhoneNo = implementerPhoneNo;
    }

    public String getImplementerEmail() {
        return implementerEmail;
    }

    public void setImplementerEmail(String implementerEmail) {
        this.implementerEmail = implementerEmail;
    }

    public Date getImplementationStartDate() {
        return implementationStartDate;
    }

    public void setImplementationStartDate(Date implementationStartDate) {
        this.implementationStartDate = implementationStartDate;
    }

    public Date getImplementationEndDate() {
        return implementationEndDate;
    }

    public void setImplementationEndDate(Date implementationEndDate) {
        this.implementationEndDate = implementationEndDate;
    }

    public Set<AttendeeDetails> getTbAttendeeDetails() {
        return tbAttendeeDetails;
    }

    public void setTbAttendeeDetails(Set<AttendeeDetails> tbAttendeeDetails) {
        this.tbAttendeeDetails = tbAttendeeDetails;
    }

    public String getDesigOfImplementer() {
        return desigOfImplementer;
    }

    public void setDesigOfImplementer(String desigOfImplementer) {
        this.desigOfImplementer = desigOfImplementer;
    }

    public String getImpleStatus() {
        return impleStatus;
    }

    public void setImpleStatus(String impleStatus) {
        this.impleStatus = impleStatus;
    }

    public String[] getPkValues() {
        return new String[] { "LGL", "TB_LGL_CASEJUDGEMENT_DETAIL", "CJD_ID" };
    }
}