package com.abm.mainet.cfc.objection.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_hearing_mas")
public class HearingMasterEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 69086994697585658L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "HR_ID")
    private Long insHaerId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "empid")
    private Long empId;

    @Column(name = "HR_ATT_EMAILID")
    private String emailid;

    @Column(name = "HR_ATT_MOBNO")
    private String mobno;

    @Column(name = "HR_ATT_NAME")
    private String name;

    @Column(name = "HR_AVAIL_PERSON")
    private String availPerson;

    @Column(name = "HR_DATE")
    private Date insHearDate;

    @Column(name = "HR_NO")
    private String insHearNo;

    @Column(name = "HR_REMARK")
    private String remark;

    @Column(name = "HR_STATUS")
    private Long hearingStatus;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "OBJ_ID")
    private Long objId;

    @Column(name = "orgid")
    private Long orgId;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "HR_DECISION")
    private Long decisionInFavorOf;
    
    @Column(name = "DSGID")
    private Long dsgid;

    public String[] getPkValues() {
        return new String[] { "COM", "tb_hearing_mas", "HR_ID" };
    }

    public Long getInsHaerId() {
        return insHaerId;
    }

    public void setInsHaerId(Long insHaerId) {
        this.insHaerId = insHaerId;
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

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getMobno() {
        return mobno;
    }

    public void setMobno(String mobno) {
        this.mobno = mobno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvailPerson() {
        return availPerson;
    }

    public void setAvailPerson(String availPerson) {
        this.availPerson = availPerson;
    }

    public String getInsHearNo() {
        return insHearNo;
    }

    public void setInsHearNo(String insHearNo) {
        this.insHearNo = insHearNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Long getObjId() {
        return objId;
    }

    public void setObjId(Long objId) {
        this.objId = objId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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

    public Date getInsHearDate() {
        return insHearDate;
    }

    public void setInsHearDate(Date insHearDate) {
        this.insHearDate = insHearDate;
    }

    public Long getHearingStatus() {
        return hearingStatus;
    }

    public void setHearingStatus(Long hearingStatus) {
        this.hearingStatus = hearingStatus;
    }

    public Long getDecisionInFavorOf() {
        return decisionInFavorOf;
    }

    public void setDecisionInFavorOf(Long decisionInFavorOf) {
        this.decisionInFavorOf = decisionInFavorOf;
    }

	public Long getDsgid() {
		return dsgid;
	}

	public void setDsgid(Long dsgid) {
		this.dsgid = dsgid;
	}

}
