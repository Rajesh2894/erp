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
@Table(name = "tb_inspection_mas")
public class InspectionMasterEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4106205658734977008L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "INS_ID")
    private Long insHaerId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "empid")
    private Long empId;

    @Column(name = "INS_ACTUALDATE")
    private Date actualdate;

    @Column(name = "INS_ATT_EMAILID")
    private String emailid;

    @Column(name = "INS_ATT_MOBNO")
    private String mobno;

    @Column(name = "INS_ATT_NAME")
    private String name;

    @Column(name = "INS_AVAIL_PERSON")
    private String availPerson;

    @Column(name = "INS_DATE")
    private Date insHearDate;

    @Column(name = "INS_NO")
    private String insHearNo;

    @Column(name = "INS_REMARK")
    private String remark;

    @Column(name = "INS_STATUS")
    private String inspStatus;

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

    public String[] getPkValues() {
        return new String[] { "COM", "tb_notice_mas", "NOT_ID" };
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

    public Date getActualdate() {
        return actualdate;
    }

    public void setActualdate(Date actualdate) {
        this.actualdate = actualdate;
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

    public Long getInsHaerId() {
        return insHaerId;
    }

    public void setInsHaerId(Long insHaerId) {
        this.insHaerId = insHaerId;
    }

    public Date getInsHearDate() {
        return insHearDate;
    }

    public void setInsHearDate(Date insHearDate) {
        this.insHearDate = insHearDate;
    }

    public String getInspStatus() {
        return inspStatus;
    }

    public void setInspStatus(String inspStatus) {
        this.inspStatus = inspStatus;
    }

}
